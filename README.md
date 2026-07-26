# Android OLED Screen Saver — Pure Black Screen

A tiny open-source Android OLED/AMOLED screen saver that shows a genuinely pure-black, edge-to-edge surface, keeps the display session awake, and closes on one tap.

Useful for Android device labs, long-running QA, screen burn-in risk reduction, privacy between tests, and any workflow that needs a black OLED screen without locking the phone.

## Why this project is intentionally small

- **Pure black:** the app renders only `#000000` across its content and system bars.
- **Tap to dismiss:** any tap finishes the Activity and returns control to the phone.
- **No permissions:** no network, storage, accessibility, overlay, notification, or wake-lock permission.
- **No tracking:** no analytics, ads, telemetry, account, or persistent state.
- **No background process:** screen-awake ownership belongs only to the visible Activity and is released when it closes.
- **Hardware-independent:** standard Android window APIs; no device IDs or vendor-specific routing.
- **Android 6–17+:** min SDK 23, compile/target SDK 37.

> A black image can reduce OLED pixel emission and burn-in exposure, but this project does not claim to repair existing burn-in or prove a panel's electrical power draw.

## Behavior contract

`launch → black first frame → immersive system bars → keep screen awake while visible → tap → finish task → Android releases window flag`

The app deliberately does **not** change global brightness, disable the lock screen, acquire a wake lock, draw over other apps, or survive process death.

## Build

Requirements: JDK 17+ and Android SDK 37.

```bash
./gradlew --no-daemon clean lint test assembleDebug
```

APK output:

`app/build/outputs/apk/debug/app-debug.apk`

## Automatic GitHub verification

Every push runs lint, unit gates, and a debug APK build. The workflow uploads the exact APK and SHA-256 manifest as GitHub Actions artifacts.

Local auto-sync is opt-in and safe: after it is enabled, successful local commits are pushed to `origin`. An offline or rejected push never deletes the local commit.

```powershell
& .\scripts\enable-auto-sync.ps1
```

Use `scripts/sync-github.ps1` for an explicit fetch/rebase/push cycle.

## Privacy

The app processes no personal data. See [PRIVACY.md](PRIVACY.md).

## License

MIT — see [LICENSE](LICENSE).
