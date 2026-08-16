# Plan: Ad-removal patches for 3B Meteo (`com.Meteosolutions.Meteo3b`)

Goal: build a Morphe patch bundle (`.mpp`) that removes ads from the **3B Meteo** Android app, test locally with Morphe-Desktop, and release it publicly so it can be installed via Morphe Manager.

> Legal note: ad removal in this app bypasses the paid **premium** in-app purchase that removes ads. This is a personal-use, ReVanced-style patch. Proceeding at the user's discretion.

- **Status: Phase 0–3 done. Phase 4 (Morphe-Desktop test) is the current step.**

## Target app facts (research)
- Package: `com.Meteosolutions.Meteo3b`
- Launcher name (from `app_name` string): `3BMeteo`
- Analyzed version: **4.9.15**, versionCode **323104240** (all 4 ABIs share the same code). Downloaded as XAPK from apkpure.net CDN -> split APK -> `ApkFileType.APKM`.
- Ads via paid premium subscription. Ad SDKs: Google Ads (GAM), Teads (`ViewIol*` wrapper), Outbrain, Prebid, Beintoo, iubenda/TCF2 consent.

## Decisions (confirmed with user)
1. APK source: **download the latest available working version** (ApkMirror/Aptoid), record exact version + version code.
2. Build environment: **set it up here** (install JDK 17+, Android SDK cmdline-tools, configure GitHub PAT in `~/.gradle/gradle.properties`).
3. End goal: **Both** — local dev patches flow + full public release via semantic-release.

---

## Phase 0 — Environment setup (prerequisite)
1. Install JDK 17+ (currently only Java 1.8 present). Set `JAVA_HOME`; use Temurin 17 packaged JDK.
2. Install Android SDK cmdline-tools; set `ANDROID_HOME`. Required by `extensions/extension` (compiles against `android.jar`) and the `app.morphe.patches` plugin.
3. Configure GitHub PAT with `read:packages` scope in `~/.gradle/gradle.properties`:
   ```
   gpr.user = <github username>
   gpr.key = ghp_xxxxxxxxxxxxxxxxxxxxxxxx
   ```
   (Alternative: `gh auth login` then `gh auth setup-git`.)
4. Verify baseline: `./gradlew buildAndroid` on the untouched template produces a valid `.mpp` (isolates env/config issues from patch logic).

## Phase 1 — Acquire + decompile (automated)
1. Script deterministic download of latest working 3B Meteo APK/APKM. Record exact version + version code per ABI.
2. Install **jadx** and decompile to Java; also `apktool d` to smali.
3. Output location: `analysis/` directory (gitignored) holding APK(s), jadx output, smali, and a manifest/metadata dump.

## Phase 2 — Static analysis to locate ad/premium logic (scripted)
Search jadx/smali output for candidate hooks:
- Ad SDK: `com.google.android.gms.ads.*`, and 3bmeteo wrappers (`AdManager`, `AdsHelper`, `AdsLoader`, `Banner`, `Interstitial`, `AdListener`).
- Premium/no-ads flag: `isPremium`, `removeAds`, `adFree`, `AD_FREE`, SharedPreferences keys, `onPurchasesUpdated`, billing classes.
- `loadAd()` / `onAdLoaded()` / banner/interstitial build sites suitable for a clean no-op or `return false`.
Produce a candidate-hook report (methods + fingerprint filters). **Deliverable**: list of verified hook methods.

## Phase 3 — Write the patches ~~DONE~~
Implemented (see `patches/src/main/kotlin/app/riky/patches/`):
- `Constants.kt`: `COMPATIBILITY_METEO3B` (name "3BMeteo", pkg `com.Meteosolutions.Meteo3b`, `ApkFileType.APKM`, appIconColor `0x30639B`, target 4.9.15).
- `Fingerprints.kt`: `evaluateProvider` (public), `evaluateFallback` (private) in `BannerManager`; `isPremium`, `isConsentlessPremium` in `data/models/User`.
- `HideAdsPatch.kt` (`default = true`): overrides `evaluateProvider` -> `"none"` (every banner/native/cover/interstitial type resolves to empty/invisible), `evaluateFallback` -> `"no_adv"` (so the two fallback handlers hide instead of null-deref), and forces both `User` premium methods to `true` (kills premium-gated ads, consent popup, and unlocks the ad-free/premium UI).
- No extension `.mpe` needed for these fixed-value overrides.
- Namespace renamed to a per-app scheme (`app.riky.patches.meteo3b`, group `app.riky`), future apps get their own package.
- `generatePatchesList` produces `patches-list.json` with the "Hide ads" entry (version 1.0.0).

4. ~~Extension (`.mpe`, Java)~~ — not required: simple fixed-value overrides. (The template `ExamplePatch.java` was removed.)
5. ~~Keep the `example` namespace~~ — renamed to `app.riky`.

## Phase 4 — Build & iterate
- `./gradlew buildAndroid` -> `patches/build/libs/patches-*.mpp`. **DONE** — the built `.mpp` was smoke-tested by applying it to the real base APK with the actual morphe-patcher 1.8.0: patch applied, dex compiled, and the patched methods verified via jadx (`evaluateProvider`/`evaluateFallback`/`isPremium`/`isConsentlessPremium` all return the forced values). Fingerprints match 4.9.15.
- Test with **Morphe-Desktop**: patch real APK, sideload, confirm ads gone + app stable. Iterate fingerprints on mismatch (patcher reports failing fingerprint name). **(NEXT STEP)**

## Phase 5 — Release (public)
- Update `patches/build.gradle.kts` `about { name, description, source, author, contact, website, license }`, `README.md`, `.github/ISSUE_TEMPLATE`.
- Work on `dev` branch; merge to `main`; let `release.yml` (semantic-release) build tags + patches-list.json + CHANGELOG + README patch list automatically.
- Do NOT hand-commit generated files (`patches-list.json`, `patches-bundle.json`, `CHANGELOG.md`).

---

## Automation levers (scripts to write)
- `scripts/fetch_apk.sh` — deterministic APK/APKM download + metadata dump.
- `scripts/analyze.sh` — recursive grep over jadx/smali for ad/premium hooks -> report.
- `scripts/build_verify.sh` — run `buildAndroid` and optionally re-patch a clean APK.

## Open items / needed from user
- GitHub account + PAT scoped `read:packages` (phase 0) and hosted public repo name (phase 5). **Account/PAT ready (riky-dev); repo name TBD.**
- Confirm public release location (this same repo promoted? separate repo?). **TBD — separate `morphe-patches` repo under riky-dev preferred.**

## Spoiler: approach likely to work
- Suppress an `AdListener`/`loadAd` entry point (return no-op) to prevent banner+interstitial rendering. **DONE — via `evaluateProvider` -> `"none"` + `evaluateFallback` -> `"no_adv"`.**
- Force premium branch (`isPremium()` -> `true` from extension) so in-app layout shows the ad-free/premium UI without billing. **DONE — `User.isPremium()`/`isConsentlessPremium()` forced via smali override.**