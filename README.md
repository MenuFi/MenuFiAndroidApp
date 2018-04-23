# MenuFiAndroidApp

This is part of a larger MenuFi solution that includes the following projects:
* https://github.com/MenuFi/MenuFiBackend
* https://github.com/MenuFi/MenuFiWebPortal

## Release Notes version 1.0

### NEW FEATURES

* Added ability to view restaurants and menu items
* Added ability to update allergies and preferences
* Added ability to filter by allergies and preferences
* Added ability to view detailed entries about menu items
* Added ability to rate and view ratings of menu items
* Added registering user clicks

### BUG FIXES

* Fixed stars and text overlapping
* Fixed images not loading
* Fixed rating not displayed for menu items with no ratings.

### KNOWN BUGS

* Allergies and Preferences no longer persist on reopening the app
* A particular pattern of flipping the filters on and off will cause some menu items to be incorrectly highlighted

## Install Guide

### PRE-REQUISITES

You must have the following installed before preceding:
1. Android Studio (Optional)
2. Gradle (bundled with Android Studio)

### DOWNLOAD

1. Navigate to https://github.com/MenuFi/MenuFiAndroidApp
2. Click `Clone or download`
3. Click `Download ZIP`

### DEPENDENCIES

1. Android SDK 16 or newer

### BUILD

#### BUILD DEBUG
* Run `gradlew assembleDebug` to build the project and create an APK.

#### BUILD PRODUCTION
* follow the instructions on 'https://developer.android.com/studio/publish/app-signing.html' to build a production APK

### RUNNING APPLICATION

#### ON DEVICE
* run `adb -d install path/to/your_app.apk` to install the app to your device

#### ON EMULATOR
1. run `emulator -avd avd_name` to start your emulator
2. run `adb install path/to/your_app.apk` to install to your emulator

### TROUBLESHOOTING

1. If there are compile errors make sure you have the correct Android SDK installed.
