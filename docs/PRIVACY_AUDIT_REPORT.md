# Privacy Audit Report for Retirement Timer

**Audit Date:** October 29, 2026  
**Application Name:** Retirement Timer (Countdown Days)  
**Application ID:** `com.veremoonstudio.retirementtimer`  
**Developer:** Veremoon Studio  
**Contact Email:** `veremoonstudio@gmail.com`  

---

## 1. Audit Scope & Methodology

A complete static audit of the Android Studio project was performed, analyzing:
- **Build Configurations:** `settings.gradle.kts`, `app/build.gradle.kts`, `gradle/libs.versions.toml`
- **Manifest & Manifest Merging:** `AndroidManifest.xml`, `backup_rules.xml`, `data_extraction_rules.xml`
- **Kotlin Source Code:** UI (`WorkTimeScreen`, `SettingsScreen`, `PremiumScreen`, `BackgroundScreen`), ViewModels (`WorkTimeViewModel`), `BillingManager`, `AdBanner`, `MainActivity`, and `RetirementGlanceWidget`.
- **Storage Mechanisms:** Jetpack DataStore preferences (`work_time_settings.preferences_pb`).
- **External Network & SDK Behavior:** Google Mobile Ads SDK (AdMob), Google UMP SDK, and Google Play Billing SDK.

---

## 2. Discovered SDKs & Dependencies

| SDK / Dependency | Resolved Version | Active Status | Runtime Behavior & Data Processed |
| :--- | :---: | :---: | :--- |
| **Google Play Billing** (`billing-ktx`) | **`9.1.0`** *(Upgraded)* | **Active** | Processes purchase tokens, order IDs, and subscription states for `premium_lifetime`, `premium_yearly`, and `premium_monthly`. Updated to `productDetailsList` API. |
| **Google AdMob** (`play-services-ads`) | `23.6.0` | **Active** | Processes AAID, IP address (coarse location), device characteristics, ad view impressions, and crash diagnostics. Disabled when Premium is unlocked. |
| **Google UMP** (`user-messaging-platform`) | **`3.1.0`** *(Added)* | **Active** | Obtains regional consent (EEA/UK/US) prior to ad requests and provides Privacy Settings option in `SettingsScreen`. |
| **Jetpack DataStore** (`datastore-preferences`) | `1.2.1` | **Active (Local)** | Local preference storage for target dates, schedules, vacation hours, holidays, and background themes. |
| **Jetpack Glance** (`glance-appwidget`) | `1.1.1` | **Active (Local)** | Home screen widget state rendering. |
| **Firebase Analytics / Crashlytics** | N/A | **Absent** | No code or dependencies present. |

---

## 3. Permissions Audit

| Permission Name | Declared in Manifest | Purpose & Usage |
| :--- | :---: | :--- |
| `android.permission.INTERNET` | Yes | Required for Google AdMob ad fetching and Google Play Billing transactions. |
| `android.permission.ACCESS_NETWORK_STATE` | Yes | Used by Google Mobile Ads SDK to check network status before requesting ads. |
| `com.android.vending.BILLING` | Yes | Required by Google Play Billing library for in-app purchases and subscriptions. |

---

## 4. Local Storage & Android Backup Behavior

- **DataStore Storage:** User settings (`retirementDateEpoch`, `isWorkTimeMode`, `workDays`, `startTimeStr`, `endTimeStr`, `vacationHours`, `holidays`, `selectedBackground`) are stored in `work_time_settings.preferences_pb`.
- **No External Uploads:** Local DataStore settings are **never** uploaded to Veremoon Studio servers.
- **Android Auto Backup:** `AndroidManifest.xml` configures `android:allowBackup="true"`. On Android devices with active cloud backup, local DataStore preference files may be backed up to the user's private Google Drive storage. Uninstalling the app removes local files, but cloud backups may persist until overwritten or deleted from Google Drive.

---

## 5. CORRECTIONS FROM PREVIOUS AUDIT

| Previous Audit Statement | Corrected Fact / Finding | Reason for Correction |
| :--- | :--- | :--- |
| **Billing Library Version 7.1.1** | **Upgraded to 9.1.0** | Upgraded `billing-ktx` to `9.1.0` in `libs.versions.toml` and updated `BillingManager.kt` to the new `productDetailsList` callback API. |
| **AdMob Consent Management Missing** | **Integrated Google UMP SDK 3.1.0** | Integrated `com.google.android.ump:user-messaging-platform:3.1.0`, initialized consent requests in `MainActivity.kt`, and added Privacy Settings row in `SettingsScreen.kt`. |
| **Opened Generic Google Privacy Policy** | **Updated to custom hosted Privacy Policy** | Updated `res/values/strings.xml` to point `privacy_policy_url` and `terms_url` to the app's custom hosted pages (`https://veremoonstudio.github.io/RetirementTimerApp/privacy-policy.html` and `terms.html`). |
| **Data Deletion Statement Clarified** | **Clarified Local vs Third-Party Data** | App has no user accounts or custom backend server. Uninstalling removes local preferences, but third-party SDK retention is governed by Google. |

---

## 6. Verification & Build Results

- **Gradle Sync:** Completed successfully.
- **Build Verification:** Tested and built both `app:bundleFreeRelease` and `app:bundlePremiumRelease` successfully with 0 errors.
- **Document Consistency:** HTML5 and Markdown legal documents match design and legal requirements.
