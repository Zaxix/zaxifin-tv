# Zaxifin (codename: Aurora)

A next-generation Jellyfin client concept for **Android TV / Fire TV**, built with **Kotlin + Jetpack Compose for TV + Media3**.

This repo currently contains a **runnable UI prototype** (offline / placeholder data) for:
- Home → Detail → Player navigation
- Core design tokens + the signature focus “lift” interaction (`FocusableCard`)
- A minimal Media3-backed player surface with an **auto-hiding overlay**

## Requirements

- Android Studio (recent stable)
- Android SDK (installed via Android Studio)
- **JDK 17** for Gradle/Android Gradle Plugin compatibility

## Run

1. Open the folder `aurora-tv/` in Android Studio.
2. Let Gradle sync.
3. Run the `app` configuration on an Android TV emulator or device (Android TV 9+).

## Notes

- **Branding**: app name is **Zaxifin**, using the Android TV banner + launcher resources from your `zaxifin-android-resources.zip`.
- **Jellyfin integration** (SDK, auth, rows, images, playback URLs) is the next step once the UI shell is validated.

## Release APK (Fire TV / Downloader)

### Local signing (fast path)

1. Generate a local keystore + `keystore.properties` (gitignored):

`tools\\gen_release_keystore.cmd`

2. Build a signed release APK in Android Studio: **Build → Generate Signed App Bundle or APK → APK**.

### GitHub Actions (Downloader-friendly URL)

This repo includes `.github/workflows/release-apk.yml`.

1. Add these repository secrets:
   - `SIGNING_KEYSTORE_BASE64` (base64 of `keystore/release.jks`)
   - `SIGNING_STORE_PASSWORD`
   - `SIGNING_KEY_PASSWORD`
   - `SIGNING_KEY_ALIAS` (optional; defaults to `release`)

2. Push a tag like `v0.1.0` to run the workflow.
3. Download the uploaded APK from the workflow run artifacts (or the GitHub Release asset if you created a release from the tag).

### If Gradle fails on a “too new” JDK (Windows)

If your default `java` is newer than AGP/Kotlin DSL supports for script compilation, `gradlew.bat` will automatically prefer a repo-local JDK 17 if you unzip Temurin 17 to:

`jdk17/unpack/jdk-17.0.18+8/`

Alternatively, set `JAVA_HOME` to a JDK 17 install, or set `org.gradle.java.home` in `local.properties` (gitignored).

