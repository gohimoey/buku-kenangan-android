# Buku Kenangan - Native Android 16 App

Aplikasi native untuk Android 4.1+ (API 16) - Buku Kenangan SMK.

## Fitur
- WebView utama ke server lokal ZimaOS
- QR Scanner (Camera API legacy)
- Offline storage (SQLite backup data)
- Error handling + retry
- Back button navigation

## Config
Edit `MainActivity.java` untuk ganti URL:
```java
private static final String SERVER_URL = "http://192.168.3.24:2343";
```

## Build via GitHub Actions
Push to repo → automatic build.

## Build Lokal (Android Studio)
```bash
# Open project, install SDK API 16 + Build Tools 28.0.3
./gradlew assembleDebug
```

## APK Output
- Debug: `app/build/outputs/apk/debug/app-debug.apk`
- Release: `app/build/outputs/apk/release/app-release.apk`