# Ewon Ignition 7.9 Connector Changelog

## v1.1.3

### Major Changes

* Tags are now highlighted and returned to original value if a write fails
* Applicable tags are now marked as stale when realtime updating fails

### Minor Changes

* Added option to disable strict checking for allowed tag name characters

## v1.1.2

### Major Changes

* Added Flexy realtime read support
* Module now listed in the Ignition Showcase

### Minor Changes

* Ewon tag names are verified for compatibility with Ignition
* Module ID has changed
* Fixed an issue that caused the connector to fault when initially installed and no credentials configured
* Fixed a security issue by removing user credentials from Ignition log files
* Fixed an issue with writing boolean tag values
* Improved configuration page
* Improved readme/documentation

## v1.1.1

### Major Changes

* Module is now signed

## v1.1.0

### Major Changes

* Ignition to Flexy writes
* Support for tag deletion
* Configuration parameters are now URL friendly
* Support for String tag values

### Minor Changes

* Support for periods in tag names
* Included CHANGELOG.md in release
