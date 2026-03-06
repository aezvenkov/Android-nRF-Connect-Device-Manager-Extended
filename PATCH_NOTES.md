# Release Notes - Settings Feature

## Overview
Added a new Settings management interface to the nRF Connect Device Manager sample application. This feature allows users to read, write, and delete device-level settings via the McuMgr protocol.

## How to Access
1. Launch the application.
2. Use the **Scanner** tab to find and connect to your McuMgr-compatible device.
3. Once connected, select the **Device** tab from the bottom navigation bar.
4. The **Settings** card will be visible in the list of available McuMgr features.
5. Enter a setting key (e.g., `fw_loader/adv_name`) to perform Read, Write, or Delete operations.

## Features
- **Settings UI**: A new card-based interface in the Device tab for interacting with device settings.
- **Read Operation**: Retrieve values for specific settings keys from the device.
- **Write Operation**: Update or create settings on the connected device.
- **Delete Operation**: Remove settings from the device.
- **Response Handling**: Real-time feedback for operations, including success messages and error codes retrieved from the McuMgr responses.

## Technical Changes
- Created `SettingsFragment` and its associated material layout `fragment_card_settings.xml`.
- Implemented `SettingsViewModel` for managing setting operations using `SettingsManager` from the core library.
- Integrated Settings components into the Dagger 2 dependency injection framework.
- Registered the new ViewModel in `McuMgrViewModelFactory` and `McuMgrViewModelSubComponent`.
- Registered the new Fragment in `McuMgrFragmentBuildersModule`.
- Updated `DeviceFragment` layout to include the new Settings UI container.
- Added localized string resources in `strings_settings.xml`.

## Environment and Build Fixes
- Configured Gradle to use a specific Local JDK path in `gradle.properties` to ensure `jlink` availability and build stability across different environments.
- Fixed missing Dagger imports and KSP processing errors that were causing compilation failures.
- Corrected McuMgr response handling logic to properly display return codes.
