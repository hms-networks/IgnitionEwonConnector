# Ewon Ignition 8 Connector Changelog

## v1.2.1

### Major Changes

* Tag provider name customization was removed to ensure consistent and stable connector functionality
  * Fixed an issue that caused the per-tag realtime override option to be missing

### Minor Changes

* Updated GSON dependency version

## v1.2.0

### Major Changes

* Updated to Ignition 8.1.1 SDK
* Fixed an issue that caused the AllRealtime tag value to be lost
* Fixed an issue that caused tag deletion to become disabled
* Update to HTTP POST requests for DataMailbox and M2Web APIs
* Update to new Talk2M token authentication for DataMailbox API

### Minor Changes

* Clarify files to download for releases
* Updated the settings page to automatically restart the connector when applying changes

## v1.1.12

### Major Changes

* Ewon tag descriptions are now synced to Ignition tag documentation and tooltip

## v1.1.11

### Major Changes

* Fixed an issue that caused realtime update errors on tags with underscores

## v.1.1.10

### Major Changes

* Tags are now highlighted and returned to original value if a write fails
* Applicable tags are now marked as stale when realtime updating fails

### Minor Changes

* Added option to disable strict checking for allowed tag name characters
* Updated user-friendly logging output
* Fixed an issue with realtime tag provider shutdown
* Fixed an issue with realtime poll rate value validation

## v1.1.9

### Major Changes

* Module now listed in the Ignition Showcase

### Minor Changes

* Ewon tag names are verified for compatibility with Ignition
* Module ID has changed
* Bugfix: The connector no longer faults when initially installed and no credentials are entered

## v1.1.8

### Major Changes

* Realtime values are now available on a per Ewon basis

### Minor Changes

* Created new readme
* Updated Configuration Webpage
* SecurityFix: Removed user credentials from Ignition log files

## v1.1.7

### Major Changes

### Minor Changes

* Bugfix: Enabled writing to boolean tags on the eWON
* Bugfix: Fixed precision loss when reading large number via realtime
* Bugfix: Correctly handle reading an empty string via realtime

## v1.1.6

### Major Changes

* Module is now signed

### Minor Changes

* Realtime string tags can handle " character
* Module gracefully handles tags that dont exist on the ewon but exist in datamailbox

## v1.1.5

### Major Changes

* None

### Minor Changes

* Included CHANGELOG.md in release
* Removed logging of "Updating Live Values"
* Updated license for module

## v1.1.4

### Major Changes

* None

### Minor Changes

* Tags can now be removed and created again without restarting the module
* Logging of "Updating Live Values" level changed to "Trace"

## v1.1.3
### Major Changes
* Realtime reads from the Flexy
* Ignition to Flexy writes
* Support for tag deletion
* Configuration parameters are now URL friendly
* Support for String tag values

### Minor Changes
* Support for periods in tag names
