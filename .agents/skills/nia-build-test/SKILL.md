---
name: nia-build-test
description: >-
  Now in Android (NiA) 專屬高效構建、精準測試與除錯工作流。
  在需要編譯特定模組、執行局部測試、排查 Lint/編譯報錯或打包發行版產物時啟動，
  具備針對性的指令集與 token 最佳化策略。
---

# Now in Android (NiA) 高效構建與測試技能指南

本技能專為 NiA 專案設計，核心目標是**精準定位**、**節省 Token** 與**快速反饋**，避免動輒觸發全專案 3600+ 個 Gradle 任務。

---

## 1. 模組級精準編譯 (節省 80% 時間與 Token)

不要直接執行全局 `./gradlew assembleProdDebug`，請優先編譯受修改影響的特定模組：

- **App 模組 (Debug / Release)**：
  ```bash
  ./gradlew :app:compileProdDebugKotlin
  ./gradlew :app:compileProdReleaseKotlin
  ```
- **特定 Feature 模組 (例如 Settings / ForYou)**：
  ```bash
  ./gradlew :feature:settings:impl:compileProdDebugKotlin
  ./gradlew :feature:foryou:impl:compileProdDebugKotlin
  ```
- **Core 模組 (例如 Data / UI / Model)**：
  ```bash
  ./gradlew :core:data:compileProdDebugKotlin
  ./gradlew :core:ui:compileProdDebugKotlin
  ```

---

## 2. 測試與 Lint 精準驗證

- **單一單元測試類別**：
  ```bash
  ./gradlew :core:data:testProdDebugUnitTest --tests "com.wuc656.nowinandroid.core.data.repository.OfflineFirstUserDataRepositoryTest"
  ```
- **單一模組 Lint 檢查**：
  ```bash
  ./gradlew :app:lintProdDebug
  ./gradlew :core:analytics:lintProdDebug
  ```

---

## 3. 正式發行打包 (Release Bundle & APK)

- **產生 AAB 與 Release APK**：
  ```bash
  ./gradlew :app:bundleProdRelease :app:assembleProdRelease
  ```
- **產物路徑確認**：
  - AAB: `app/build/outputs/bundle/prodRelease/app-prod-release.aab`
  - APK: `app/build/outputs/apk/prod/release/app-prod-release.apk`

---

## 4. 排錯與輸出過濾技巧 (避免 Context Explosion)

- **捕獲關鍵編譯錯誤行**：
  ```powershell
  ./gradlew :app:compileProdDebugKotlin | Select-String "e: ", "FAILED", "Error:"
  ```
- **Spotless 代碼格式化快速修復**：
  ```bash
  ./gradlew spotlessApply
  ```

---

## 5. 常見地雷與規範

