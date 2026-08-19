# Find Thợ Mobile App

## Overview
Android application cho nền tảng Find Thợ, dành cho khách hàng và thợ sửa chữa.

## Technology Stack
- Kotlin
- Jetpack Compose (Material 3)
- MVVM + Clean Architecture
- Dagger Hilt
- Retrofit & OkHttp
- Kotlin Coroutines & Flow
- Navigation Compose
- DataStore
- Coil

## Folder Structure (Clean Architecture)
- `core`: Network, navigation, theme, base components
- `data`: Repositories implementation, models (DTOs), remote APIs
- `domain`: Domain models, use cases, interfaces
- `presentation`: UI (Compose), ViewModels cho từng tính năng (Auth, Customer, Worker)

## Development
- Yêu cầu: Android Studio Hedgehog (hoặc mới hơn), JDK 17
- Cấu hình môi trường API: `local.properties` hoặc biến môi trường, API được lấy tại `BuildConfig.API_URL`
- Cài đặt `local.properties` (Không được commit file này):
  ```properties
  sdk.dir=/path/to/your/android/sdk
  ```

## Build
```bash
./gradlew assembleDebug
```
