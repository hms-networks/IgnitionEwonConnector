# Ewon Connector

Synchronize DataMailbox data to Ignition's Tag Historian

## Version

This module version is compatible with Ignition 8.  For an Ignition 7.9 compatible module go to [Ignition-7.9-master](https://github.com/hms-networks/eWonConnector/tree/Ignition-7.9-master) branch.

## Download

The module can be downloaded from the [releases page](https://github.com/hms-networks/eWonConnector/releases).

## Features

* Data synchronization between Ewon DataMail and Ignition
   * Tag value updated up to once per minute
* Realtime Read functionality
   * Faster tag value update rates via the Talk2M API
   * Supports up to once per second update rates
   * Configured on a tag by tag basis
* Write functionality
   * Allows Ignition to modify tag values on a Flexy

## Installation

The module can be installed by navigating to the Ignition web server.  Once on the webserver go to the "Config" section then under the "System" category click the "Modules" page.  At the bottom of the "Modules" page click "Install or Upgrade a Module...".  Follow the prompts to install the module.

## Configuration

Once the module is installed navigate back to the "Config" page, then under the "Tags" category click the "Ewon Connector" page.  On this page you will need to configure the connector.

### Main

These are the general setting for the connector and how it will appear in Ignition.

### Ewon Account Information

This is your Talk2M account details

### Ewon Device Information

This is your Ewon local user name and password.  If more than one Ewon is tied to your Talk2M account you will want to create a universal local username and password on each device.  This local login is needed for writing data from Ignition to your Flexy as well as using the "Realtime" functionality.

### Saving Changes

Once all the configuration information has been entered click "Save Changes" then restart Ignition.

## Usage

Tags will be populated automatically into Ignition on startup.  Tags will be visible under the provider name assigned during the configuration process.  Each Ewon tied to the Talk2M account will be a unique tag directory.
