@echo off
setlocal EnableExtensions EnableDelayedExpansion

rem Generates a local release keystore + keystore.properties for Gradle signing.
rem Do NOT commit keystore\release.jks or keystore.properties (they are gitignored).

set "ROOT=%~dp0.."
pushd "%ROOT%" >nul

if not exist "keystore\" mkdir "keystore\"

set "KS=keystore\release.jks"
if exist "%KS%" (
  echo ERROR: %KS% already exists. Delete it first if you want to regenerate.
  exit /b 1
)

rem Generate a simple password without tricky delayed-expansion edge cases.
rem (Good enough for personal sideloading; rotate later if you want stricter entropy.)
set "PASS=zaxifin-%RANDOM%-%RANDOM%-%RANDOM%-%RANDOM%-%TIME:~0,2%%TIME:~3,2%%TIME:~6,2%"

where keytool >nul 2>nul
if errorlevel 1 (
  echo ERROR: keytool not found on PATH. Install a JDK and ensure keytool is available.
  exit /b 1
)

keytool -genkeypair -v ^
  -keystore "%KS%" ^
  -storetype JKS ^
  -alias release ^
  -keyalg RSA ^
  -keysize 2048 ^
  -validity 10000 ^
  -storepass "%PASS%" ^
  -keypass "%PASS%" ^
  -dname "CN=Zaxifin, OU=Engineering, O=Zaxifin, L=Unknown, S=Unknown, C=US" ^
  >nul 2>&1

if errorlevel 1 (
  echo ERROR: keytool failed. Re-run this script from a Developer Command Prompt, or generate the keystore manually.
  exit /b 1
)

for %%I in ("%CD%\%KS%") do set "KS_ABS=%%~fI"
set "KS_UNIX=!KS_ABS:\=/!"

>keystore.properties (
  echo storeFile=!KS_UNIX!
  echo storePassword=!PASS!
  echo keyAlias=release
  echo keyPassword=!PASS!
)

echo OK:
echo - Keystore: %KS_ABS%
echo - Properties: %CD%\keystore.properties
echo.
echo IMPORTANT: Back up keystore.properties + the .jks file somewhere safe. Losing them means you cannot update the same app install on Fire TV.

popd >nul
endlocal
exit /b 0
