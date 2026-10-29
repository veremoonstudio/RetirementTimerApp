# Google Play Data Safety Declaration for Retirement Timer

**Application Name:** Retirement Timer (Countdown Days)  
**Application ID:** `com.veremoonstudio.retirementtimer`  
**Developer:** Veremoon Studio  
**Contact Email:** `veremoonstudio@gmail.com`  
**Last Verified:** October 29, 2026  

---

## 1. Executive Summary

This document provides a verified, step-by-step guide for completing the **Data Safety** section in Google Play Console for the **Retirement Timer** application.

Based on a complete static code and dependency audit:
- The app uses **Google AdMob** (`play-services-ads:23.6.0`) for ad serving in the free tier.
- The app uses **Google UMP SDK** (`user-messaging-platform:3.1.0`) for regional advertising consent management (EEA/UK/US).
- The app uses **Google Play Billing** (`billing-ktx:9.1.0`) for in-app purchases and subscriptions (`premium_lifetime`, `premium_yearly`, `premium_monthly`).
- The app uses **Jetpack DataStore** for local preference storage (retirement dates, schedules, vacation hours, holidays).
- **NO** custom external backend database, Firebase Analytics, or Crashlytics exist.
- All communications with Google services use **HTTPS/TLS encryption in transit**.

---

## 2. High-Level Questionnaire Answers

1. **Does your app collect or share any of the required user data types?**  
   👉 **Yes** *(due to Google AdMob and Google Play Billing SDKs)*.

2. **Is all of the user data collected by your app encrypted in transit?**  
   👉 **Yes** *(all SDK communications use HTTPS/TLS)*.

3. **Do you provide a way for users to request that their data be deleted?**  
   👉 **No** *(The app does not create user accounts or maintain a custom developer server database. Clearing local app storage or uninstalling removes device preferences, but third-party SDK data processed independently by Google is managed under Google's privacy policies. Contact email `veremoonstudio@gmail.com` is available for privacy inquiries)*.

---

## 3. Data Types Declaration Table

### Category 1: Device or Other IDs

| Declaration Field | Specified Value |
| :--- | :--- |
| **Data Type** | Device or other IDs (Android Advertising ID / AAID) |
| **Collected?** | **Yes** (Collected by Google AdMob SDK) |
| **Shared?** | **Yes** (Processed by Google AdMob as ad provider) |
| **Processed Ephemerally?** | No |
| **Required or Optional?** | Required (for free ad-supported tier) |
| **Purposes of Collection** | Advertising or marketing, Analytics, Fraud prevention / security |

---

### Category 2: Location (Approximate Location / IP Address)

| Declaration Field | Specified Value |
| :--- | :--- |
| **Data Type** | Approximate location (Inferred from IP address by Google AdMob) |
| **Collected?** | **Yes** |
| **Shared?** | **Yes** (Processed by Google AdMob) |
| **Processed Ephemerally?** | No |
| **Required or Optional?** | Required |
| **Purposes of Collection** | Advertising or marketing |

---

### Category 3: Financial Info

| Declaration Field | Specified Value |
| :--- | :--- |
| **Data Type** | Purchase history (In-App Purchase tokens) |
| **Collected?** | **Yes** (Processed via Google Play Billing) |
| **Shared?** | **No** (Google Play is the Android OS payment platform; purchase tokens are processed locally on device to grant premium access) |
| **Processed Ephemerally?** | No |
| **Required or Optional?** | Optional (only when user initiates a Premium purchase) |
| **Purposes of Collection** | App functionality (fulfilling and restoring purchases) |

---

### Category 4: App Information and Performance

| Declaration Field | Specified Value |
| :--- | :--- |
| **Data Type** | Diagnostics & Crash logs |
| **Collected?** | **Yes** (Collected automatically by Google AdMob SDK) |
| **Shared?** | **Yes** (Shared with Google AdMob) |
| **Processed Ephemerally?** | No |
| **Required or Optional?** | Required |
| **Purposes of Collection** | Analytics, App functionality |

---

## 4. Excluded / Non-Collected Data Categories

Do **NOT** declare collection for:
- ❌ **Personal Info:** Name, Email address, Phone number, Address.
- ❌ **Contacts / Calendar / Photos / Videos / Audio / Files:** Not collected.
- ❌ **Precise Location:** GPS permission is not requested.

---

## 5. Official References

- **Google Mobile Ads Data Safety Guidance:** `https://developers.google.com/admob/android/data-safety`
- **Google Play Billing Integration:** `https://developer.android.com/google/play/billing`
- **Google Play Data Safety Help:** `https://support.google.com/googleplay/android-developer/answer/10787469`
