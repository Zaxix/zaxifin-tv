# Zaxifin (codename: Aurora)

A next-generation Jellyfin client concept for **Android TV / Fire TV**, built with **Kotlin + Jetpack Compose for TV + Media3**.

This repo currently contains a **runnable UI prototype** (offline / placeholder data) for:
- Home → Detail → Player navigation
- Core design tokens + the signature focus “lift” interaction (`FocusableCard`)
- A minimal Media3-backed player surface with an **auto-hiding overlay**

## Requirements

- Android Studio (recent stable)
- JDK 17

## Run

1. Open the folder `aurora-tv/` in Android Studio.
2. Let Gradle sync.
3. Run the `app` configuration on an Android TV emulator or device (Android TV 9+).

## Notes

- **Branding**: app name is **Zaxifin**. The launcher banner/icon are currently vector placeholders; we can swap in your provided logo asset next.
- **Jellyfin integration** (SDK, auth, rows, images, playback URLs) is the next step once the UI shell is validated.

