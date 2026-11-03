# Google Play Publication Readiness Checklist for Retirement Timer

**Application Name:** Retirement Timer (Countdown Days)  
**Application ID:** `com.veremoonstudio.retirementtimer`  
**Developer:** Veremoon Studio  
**Contact Email:** `veremoonstudio@gmail.com`  
**Verification Date:** October 29, 2026  

---

## Publication Status Overview

| Checklist Item | Status | Supporting Evidence & Verification Details |
| :--- | :---: | :--- |
| **1. Billing Library Version Upgraded** | **PASS** | Upgraded from `7.1.1` to latest supported `com.android.billingclient:billing-ktx:9.1.0`. `BillingManager.kt` updated to `productDetailsList` API and verified. |
| **2. AdMob Consent & UMP Integration** | **PASS** | Integrated `com.google.android.ump:user-messaging-platform:3.1.0`. `MainActivity.kt` handles `initUmpConsentAndAds()` before ad requests, and `SettingsScreen.kt` provides Privacy Settings row. |
| **3. UMP Regional Consent UI Testing** | **REQUIRES MANUAL TESTING** | Actual rendering of regional consent popups (EEA/UK/US) requires testing on a physical test device or emulator configured with UMP debug geography (`DEBUG_GEOGRAPHY_EEA`) and active message forms in AdMob Console. |
| **4. Privacy Policy HTML/MD Created** | **PASS** | `docs/privacy-policy.html` created in dark-and-gold design matching exact user specification. |
| **5. Terms & Conditions HTML/MD Created** | **PASS** | `docs/terms.html` created matching design and legal structure. |
| **6. Public Privacy Policy Hosting** | **REQUIRES CONFIRMATION** | `privacy-policy.html` and `terms.html` are created in `docs/`. GitHub Pages must be activated by developer in repository settings (`Settings > Pages > Branch: main /docs`). |
| **7. App UI Integration Verified** | **PASS** | `res/values/strings.xml` configures `privacy_policy_url` and `terms_url`. Both `SettingsScreen` and `PremiumScreen` open URLs via `LocalUriHandler`. |
| **8. Data Safety Declaration Prepared** | **PASS** | `GOOGLE_PLAY_DATA_SAFETY.md` updated with exact definitions for AdMob (AAID, App interactions, Diagnostics, Coarse location), Play Billing, and DataStore. |
| **9. Android Backup Behavior Documented** | **PASS** | `android:allowBackup="true"` identified in `AndroidManifest.xml`. DataStore local storage behavior and Android Auto Backup documented in Privacy Policy. |
| **10. Account & Data Deletion Mechanism** | **PASS** | App has no account system. Declared "No" for account deletion in DataSafety. Local DataStore data cleared upon app uninstall or clearing app storage. |
| **11. Release Build Verification** | **PASS** | Executed `app:bundleFreeRelease` and `app:bundlePremiumRelease`. Both Release AAB bundles compiled cleanly with 0 errors. |

---

## Action Items Before Submitting to Google Play Console

1. **Activate GitHub Pages:**
   - In GitHub repository settings: `Settings > Pages > Source: Deploy from a branch > Branch: main > Folder: /docs > Save`.
   - Confirm URLs load live in browser:
     - `https://veremoonstudio.github.io/RetirementTimerApp/privacy-policy.html`
     - `https://veremoonstudio.github.io/RetirementTimerApp/terms.html`

2. **Configure AdMob Consent Messages:**
   - In AdMob Console (`Privacy & messaging`): Ensure GDPR form and US state privacy forms are published for your AdMob App ID.

3. **Submit Privacy Policy URL in Play Console:**
   - Navigate to `Store presence > App content > Privacy policy` and paste:
     `https://veremoonstudio.github.io/RetirementTimerApp/privacy-policy.html`

4. **Complete Data Safety Form in Play Console:**
   - Fill out Google Play Console Data Safety questionnaire using the exact answers provided in `GOOGLE_PLAY_DATA_SAFETY.md`.

5. **Verify Products in Play Console:**
   - In `Monetize > Products`: Ensure `premium_lifetime` (In-App Product) and `premium_yearly`, `premium_monthly` (Subscriptions) are active and match product IDs in `BillingManager.kt`.
