# Contributing

Keep the runtime contract deliberately narrow: one visible Activity, a pure-black surface, tap-to-dismiss, and Activity-owned screen-awake state.

Before opening a pull request:

```bash
./gradlew --no-daemon clean lint test assembleDebug
```

Do not add analytics, ads, network access, background services, overlays, broad permissions, global brightness changes, device-specific IDs, or persistent wake locks.
