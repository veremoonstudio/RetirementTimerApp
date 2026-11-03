# Google Play Data Safety Declaration for Retirement Timer

**Application Name:** Retirement Timer (Countdown Days)  
**Application ID:** `com.veremoonstudio.retirementtimer`  
**Developer:** Veremoon Studio  
**Contact Email:** `veremoonstudio@gmail.com`  
**Last Verified:** October 29, 2026  

---

## 1. Executive Summary & Verification Overview

This document provides a verified, accurate guide for completing the **Data Safety** section in Google Play Console for the **Retirement Timer** application.

### Verified Project Facts:
- The app integrates **Google AdMob SDK** (`play-services-ads:23.6.0`) for ad serving in the free version.
- The app integrates **Google UMP SDK** (`user-messaging-platform:3.1.0`) for regional advertising consent management.
- The app integrates **Google Play Billing SDK** (`billing-ktx:9.1.0`) for in-app purchases (`premium_lifetime`, `premium_yearly`, `premium_monthly`).
- The app uses **Jetpack DataStore** for local preference storage (retirement target date, work schedule, vacation hours, holidays, background selection).
- **NO** custom external backend database, Firebase Analytics, or Crashlytics exist.
- All communications with Google SDKs are **encrypted in transit (HTTPS/TLS)**.

---

## 2. High-Level Questionnaire Answers for Google Play Console

1. **Does your app collect or share any of the required user data types?**  
   👉 **Yes** *(due to Google AdMob SDK and Google Play Billing SDK)*.

2. **Is all of the user data collected by your app encrypted in transit?**  
   👉 **Yes** *(all SDK network traffic uses HTTPS/TLS)*.

3. **Does your app allow users to create an account?**  
   👉 **No** *(The app has no user account registration system)*.

4. **Do you provide a way for users to request that their data be deleted?**  
   👉 **No**  
   *(Official Google Play Policy Guidance: Apps without user account systems that do not retain user data on custom developer servers answer "No". The app stores user preferences locally via Jetpack DataStore, which are erased when the user clears local app storage or uninstalls the app. For privacy questions, users can contact `veremoonstudio@gmail.com`)*.

---

## 3. Detailed Data Types Declaration Table

### Category 1: App Activity

| Declaration Field | Specified Value | Supporting Evidence & Official SDK Guidance |
| :--- | :--- | :--- |
| **Data Type** | **App interactions** | Automatically collected by Google AdMob SDK when user views or interacts with ad banners. |
| **Collected?** | **Yes** | Confirmed by Google Mobile Ads SDK Data Safety documentation. |
| **Shared?** | **Yes** | Processed by Google AdMob as third-party ad provider. |
| **Processed Ephemerally?** | No | Retained according to Google AdMob privacy practices. |
| **Required or Optional?** | Required | Required for free ad-supported tier. |
| **Purposes of Collection** | Advertising or marketing, Analytics | Ad performance measurement and ad serving. |

---

### Category 2: Device or Other IDs

| Declaration Field | Specified Value | Supporting Evidence & Official SDK Guidance |
| :--- | :--- | :--- |
| **Data Type** | **Device or other IDs** (Android Advertising ID / AAID) | Automatically collected by Google AdMob SDK. |
| **Collected?** | **Yes** | Confirmed by Google Mobile Ads SDK. |
| **Shared?** | **Yes** | Processed by Google AdMob. |
| **Processed Ephemerally?** | No | Retained per Google AdMob privacy policy. |
| **Required or Optional?** | Required | Required for ad serving and frequency capping. |
| **Purposes of Collection** | Advertising or marketing, Analytics, Fraud prevention / security | Ad serving, analytics, preventing ad fraud. |

---

### Category 3: Location

| Declaration Field | Specified Value | Supporting Evidence & Official SDK Guidance |
| :--- | :--- | :--- |
| **Data Type** | **Approximate location** (IP-based) | Inferred from network IP address by Google AdMob SDK. |
| **Collected?** | **Yes** | Confirmed by Google Mobile Ads SDK. |
| **Shared?** | **Yes** | Processed by Google AdMob. |
| **Processed Ephemerally?** | No | Retained per Google AdMob policy. |
| **Required or Optional?** | Required | Required for regional ad targeting. |
| **Purposes of Collection** | Advertising or marketing | Relevant geographic ad serving. |

---

### Category 4: App Information and Performance

| Declaration Field | Specified Value | Supporting Evidence & Official SDK Guidance |
| :--- | :--- | :--- |
| **Data Type** | **Diagnostics** (Crash & Performance logs) | Collected automatically by Google AdMob SDK. |
| **Collected?** | **Yes** | Confirmed by Google Mobile Ads SDK. |
| **Shared?** | **Yes** | Processed by Google AdMob. |
| **Processed Ephemerally?** | No | Used for SDK performance diagnostics. |
| **Required or Optional?** | Required | Diagnostic tracking for ad SDK stability. |
| **Purposes of Collection** | Analytics, App functionality | App stability and ad SDK analytics. |

---

### Category 5: Financial Info

| Declaration Field | Specified Value | Supporting Evidence & Official SDK Guidance |
| :--- | :--- | :--- |
| **Data Type** | **Purchase history** (In-app purchase tokens) | Processed via Google Play Billing for Premium products. |
| **Collected?** | **Yes** | Purchase tokens processed by Google Play. |
| **Shared?** | **No** | Google Play is the Android OS platform store. Purchase tokens are processed locally on device to unlock premium access without custom server upload. |
| **Processed Ephemerally?** | No | Local purchase state checked to maintain Premium access. |
| **Required or Optional?** | Optional | Only triggered if user initiates a Premium purchase. |
| **Purposes of Collection** | App functionality | Unlocking and restoring Premium access. |

---

## 4. Excluded / Non-Collected Data Categories

Do **NOT** declare collection for:
- ❌ **Personal Info:** Name, Email address, Phone number, Address.
- ❌ **Contacts / Calendar / Photos / Videos / Audio / Files:** Not collected.
- ❌ **Precise Location:** GPS permission is not requested or declared.

---

## 5. Official References

- **Google Mobile Ads Data Safety Guidance:** `https://developers.google.com/admob/android/data-safety`
- **Google Play Billing Integration:** `https://developer.android.com/google/play/billing`
- **Google Play Data Safety Help:** `https://support.google.com/googleplay/android-developer/answer/10787469`
