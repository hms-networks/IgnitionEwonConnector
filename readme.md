# eWON Connector

Synchronize DataMailbox data to Ignition's Tag Historian

## Version

This module version is compatible with Ignition 8.  For an Ignition 8 compatible module go to [Ignition-8-master](https://github.com/hms-networks/eWonConnector/tree/Ignition-8-master) branch.

## Download

The module can be downloaded from the [releases page](https://github.com/hms-networks/eWonConnector/releases).

## Features

* Data synchronization between eWON DataMail and Ignition
   * Tag value updated up to once per minute
* Write functionality
   * Allows Ignition to modify tag values on a Flexy

## Installation

The current version of the module is unsigned.  To allow unsigned modules to be installed the ignition.conf file must be modified.  The ignition.conf file can be found at YOUR_IGNITION_INSTALLATION_DIR/data.

The line below must be added to the "Java Additional Parameters" section

<pre>
wrapper.java.additional.<b>X</b>=-Dignition.allowunsignedmodules=true
</pre>
Where the X is the next unused number in the list

Example
```
# Java Additional Parameters
wrapper.java.additional.1=-XX:+UseConcMarkSweepGC
wrapper.java.additional.2=-Ddata.dir=data
wrapper.java.additional.3=-Dignition.allowunsignedmodules=true
```

Once the ignition.conf file has been edited save and close the file, then restart igntion.

The module can then be installed by navigating to the Ignition web server.  Once on the webserver go to the "Config" section then under the "System" category click the "Modules" page.  At the bottom of the "Modules" page click "Install or Upgrade a Module...".  Follow the prompts to install the module.

## Configuration

Once the module is installed navigate back to the "Config" page, then under the "Tags" category click the "eWon Connector" page.  On this page you will need to configure the connector.

### Main

These are the general setting for the connector and how it will appear in Ignition.

### eWon Account Information

This is your Talk2M account details

### eWon Device Information

This is your eWON local user name and password.  If more than one eWON is tied to your Talk2M account you will want to create a universal local username and password on each device.  This local login is needed for writing data from Ignition to your Flexy.

### Saving Changes

Once all the configuration information has been entered click "Save Changes" then restart Ignition.

## Usage

Tags will be populated automatically into Ignition on startup.  Tags will be visible under the provider name assigned during the configuration process.  Each eWON tied to the Talk2M account will be a unique tag directory.
