
# Ignition Ewon® Connector

## [NOTICE]

The Ignition Ewon Connector is currently undergoing development for Version 2.0.0.
During this time, documentation and source code may be relocated, unavailable, or incomplete.

## [Table of Contents](#table-of-contents)

1. [Description](#description)
2. [Version](#version)
3. [Support](#support)
4. [Features](#features)
5. [Download](#download)
6. [Installation](#installation)
7. [Ewon Setup](#ewon-setup)
8. [Connector Setup](#connector-setup)
9. [Connector Usage](#connector-usage)
10. [Troubleshooting](#troubleshooting)
11. [FAQs](#faqs)


## [Description](#table-of-contents)

Synchronize Ewon Flexy data to Ignition's Tag Historian.

<sup>[Back to top](#table-of-contents)</sup>

## [Version](#table-of-contents)

This module version is compatible with Ignition 8.  For an Ignition 7.9 compatible module go to [Ignition-7.9-main](https://github.com/hms-networks/IgnitionEwonConnector/tree/Ignition-7.9-main) branch. The Ignition 7.9 module is no longer supported and does not receive updates.

<sup>[Back to top](#table-of-contents)</sup>

## [Support](#table-of-contents)

This application is supported by HMS Networks' North American offices.

For support please visit our [Support Portal](https://support.hms-networks.com/hc/en-us).

<sup>[Back to top](#table-of-contents)</sup>

## [Features](#table-of-contents)

* Read Functionality
   * Historical (via DataMailbox) (default option)
      * Updated up to once per minute
   * Realtime (via M2Web)
      * Updated up to once per second
      * Can be enabled per-tag, per-Ewon or system-wide
* Write Functionality (via M2Web)
   * Modify Ewon tag values from Ignition

<sup>[Back to top](#table-of-contents)</sup>

## [Download](#table-of-contents)

The module can be downloaded from the [releases page](https://github.com/hms-networks/IgnitionEwonConnector/releases).

To run and install the connector, you need to download the latest asset named `EwonConnector-X_Y-A.B.C.zip` where X_Y and A.B.C are the Ignition and module version numbers.

<img src="https://raw.githubusercontent.com/hms-networks/IgnitionEwonConnector/main/images/release_download.JPG" alt="Release Download Page" width="1000"/>

## [Installation](#table-of-contents)

1.  On the Gateway Webpage, select `Config` > `SYSTEM` > `Modules` to open the Module Configuration page.

      <img src="https://raw.githubusercontent.com/hms-networks/IgnitionEwonConnector/main/images/module_config_page.JPG" alt="Ignition Module Page" width="1000"/>

2. Scroll to the bottom on the list, find the blue arrow, and click the `Install or Upgrade a Module` link.

      <img src="https://raw.githubusercontent.com/hms-networks/IgnitionEwonConnector/main/images/module_install_link.JPG" alt="Ignition Module Install Link" width="1000"/>

3. Click `Choose File`, select a .modl file that you have previously downloaded.

      <img src="https://raw.githubusercontent.com/hms-networks/IgnitionEwonConnector/main/images/module_install_page.JPG" alt="Ignition Module Install Page" width="1000"/>

4. Click Install.

   * When the page reloads you can now see the module you installed in the list of modules.

### Upgrading

Upgrading the module to a newer version can be done by following the [Installation](#installation) steps above. Once installed, restart Ignition for the changes to fully take effect.

<sup>[Back to top](#table-of-contents)</sup>

## [Ewon Setup](#table-of-contents)

When setting up an Ewon, follow all standard Ewon Flexy documentation and Install guides. [https://websupport.ewon.biz/support](https://websupport.ewon.biz/support "https://websupport.ewon.biz/support")

In addition to the standard documentation, follow these additional steps.

### Link Ewon to Talk2M
Open the Ewon webserver and navigate to the VPN Wizard page (At the top right of the page click `Wizards`, then on the right hand menu bar select `VPN`). Select `Configure Talk2M connectivity` and follow the prompts to connect the Ewon to Talk2M.

<img src="https://raw.githubusercontent.com/hms-networks/IgnitionEwonConnector/main/images/vpn_wizard.JPG" alt="Ewon VPN Wizard" width="1000"/>

### Setup Historical Logging for Tags

Open the Ewon webserver and navigate to the `Values` page (On the left hand menu click `Tags` > `Values`). Change the `MODE` to `SETUP`. For each tag that requires logged data, double click the tag to open the `Tag configuration` menu. Scroll down to the `Historical Logging` section and check the `Historical Logging Enabled` checkbox. Enable either deadband or interval logging.

>**Deadband Logging**
>
>The Logging Deadband field triggers logging when the tags value changes by a particular amount. For example, a logging deadband of 2 means that a tag will be logged when the value changes by 2 or more. A negative value in the logging deadband field disables logging on value change.
>
>**Interval Logging**
>
>The Logging Interval field triggers time based logging. For example, a logging interval of 60 means that the tag will be logged every 60 seconds. A logging interval of 0 disables time based logging.

Once configured, click `Update Tag` on the bottom right of the page.

<img src="https://raw.githubusercontent.com/hms-networks/IgnitionEwonConnector/main/images/tag_config_page.JPG" alt="Ewon Tag Configuration Page" width="1000"/>

### Enable Historical Data In DataMailbox

Open the Ewon webserver and navigate to the `Data Management` page ( On the left hand menu click `Setup` > `System` > `Main` > `Data Management`). Check the `Historical Data` checkbox and set the `DataMailbox upload interval` to a reasonable number for your application. The `DataMailbox upload interval` sets how often the Flexy will send batches of historical data to DataMailbox and does not affect the frequency at which tag values are logged. When done, click `Update` at the bottom of the page.

<img src="https://raw.githubusercontent.com/hms-networks/IgnitionEwonConnector/main/images/data_management_page.JPG" alt="Ewon Data Management Page" width="1000"/>

<sup>[Back to top](#table-of-contents)</sup>

## [Connector Setup](#table-of-contents)

With the module successfully installed, a new `Ewon Connector` page will be added to your Ignition Gateway webpages. Navigate to this page by selecting `Config` > `TAGS` > `Ewon Connector`.

   <img src="https://raw.githubusercontent.com/hms-networks/IgnitionEwonConnector/main/images/config_page.JPG" alt="Ewon Module Configuration Page" width="1000"/>


* **Main**
   * **Name** - Unique name of the tag provider created in Ignition.
   * **Enabled** - Option to turn on/off the Ewon Connector functionality.
   * **Tag Names Contain Periods** - Check this if any Ewon tag names contain periods. If any tag names of the connected Ewons contain periods ".", tag names on the connected Ewons may not contain the underscore "_" character. HMS suggests not using periods in tag names when using Ignition.
   * **Poll Rate in Minutes** - Interval for how often data will be pulled from DataMailbox.
   * **Realtime Poll Rate in Seconds** - Interval for how often data will be pulled from an Ewon when realtime reads are activated.

* **Talk2M Account Information**
   * **Account** - Your Talk2M account name.
   * **Username** - Your Talk2M user name.
   * **Password** - Your Talk2M password.
   * **Change Password?** - Check this box to change the password stored in Ignition.
   * **Talk2M Token** - Your Talk2M token generated using eCatcher.
   * **Talk2M Developer ID** - Your Talk2M Developer ID. Include all dashes.
      * If you do not have a Talk2M Developer ID, you may request one here: [https://developer.ewon.biz/registration](https://developer.ewon.biz/registration "https://developer.ewon.biz/registration").

* **Ewon Device Information** - All Ewons must have at least once common user account to enable realtime and write functionality. The username and password for this account must be identical for all linked Ewons.
   * **Ewon Username** - Common Ewon username.
   * **Ewon Password** - Common Ewon password.
   * **Change Password?** - Check this box to change the password stored in Ignition.

* **History**
   * **History Enabled** - Option to turn off historical logging.
   * **Target History Provider** - Ignition History Provider used to log Ewon data.

* **Advanced**
    * **Read all values in realtime** - Option to always read values in realtime.
      * **Note:** This disables DataMailbox reads. Values logged on the Ewon are not logged in Ignition when this option is enabled.

### Saving Changes

Once all the configuration information has been entered, click `Save Changes`, then restart Ignition.

### Generating a Talk2M Token

To learn more about Talk2M tokens and how to generate them, please refer to Section 4.6 of the DMWeb API reference guide found at: [DMWeb API Reference Guide](https://developer.ewon.biz/system/files_force/rg-0005-00-en-reference-guide-for-dmweb-api.pdf).

<sup>[Back to top](#table-of-contents)</sup>

## [Connector Usage](#table-of-contents)

Tags will be created automatically in Ignition on startup. Tags will be visible under the provider name assigned during the configuration process. Each Ewon tied to the Talk2M account will be a unique tag directory.

<img src="https://raw.githubusercontent.com/hms-networks/IgnitionEwonConnector/main/images/tag_browser.JPG" alt="Ignition Tag Browser" width="600"/>

### **Allowed Tag Names**

The tags synchronized by the connector must adhere to the following rules.

By default, and with the `Tag Names Contain Periods` option disabled, tags must begin with an alphanumeric or underscore. Additional characters in tag names must be an alphanumeric, underscore, space, or any of the following: `' - : ( )`.

With the `Tag Names Contain Periods` option enabled, tags must begin with an alphanumeric or period. Additional characters in tag names must be an alphanumeric, period, space, or any of the following: `' - : ( )`. Note that this option does not support tag names that contain underscores.

### **Reading Values**

The tags created by the connector are native Ignition tags and are updated cyclically by the connector based on the polling rates set during the [Setup](#setup) step.

The `Tag Browser` tool in Ignition Designer can be used to read tag values.

### **Writing Values**

Ewon tag values can be written from Ignition. When attempting to write a tag value, be sure that the the communication mode is set to `Comm Read/Write` in the Ignition Designer `Project` menu.

The `Tag Browser` tool in Ignition Designer can be used to write tag values.

After a tag value is written in Ignition, the displayed value my revert back to its previous state. This is because new DataMailbox values were received. DataMailbox values will always lag the live tag value. This behavior can be avoided by temporarily enabling realtime mode for the tag/Ewon being written to.

### **Realtime Values**

The realtime value functionality allows Ignition to read live tag values from an Ewon up to once per second.

This functionality can be enabled in three ways.

**1. Individual Tag**

   * Open Designer and navigate to the `Tag Browser`
   * Expand the `All Providers` directory.
   * Expand the directory associated with your Ewon Connector Tag Provider.
   * Expand the directory associated with the Ewon you want to read a live tag value from.
   * Expand the tag name to show the tag's properties.
   * Find the `Realtime` property and enable it.

**2. Individual Ewon**

   * Open Designer and navigate to the `Tag Browser`
   * Expand the `All Providers` directory.
   * Expand the directory associated with your Ewon Connector Tag Provider.
   * Expand the directory associated with the Ewon you want to read live tag values from.
   * Expand the `_config` directory.
   * Find the `AllRealtime` tag and enable it.

**3. All Ewons**

   * Navigate to the Ewon Connector Configuration webpage discussed in the [Setup](#setup) section.
   * Enable the `Read all values in realtime` option.
   * Click `Save Changes`, then restart Ignition.

_Note: The realtime functionality counts against your Talk2M account's monthly bandwidth._

### **Deleting Tags**

Tags can be deleted by right clicking the tag in the `Tag Browser` then selecting `Delete`. If the tag still exists on the Ewon or in DataMailbox the tag will be recreated in Ignition on the next DataMailbox update.

<sup>[Back to top](#table-of-contents)</sup>

## [Troubleshooting](#table-of-contents)

### Troubleshooting Topics

* [Tags do not appear in Ignition](#Tags-do-not-appear-in-Ignition)
* [Changes on the Ewon Connector page do not take effect](#Changes-on-the-Ewon-Connector-page-do-not-take-effect)

### Tags do not appear in Ignition

There are several reasons tags may not be populated into Ignition. Please follow the following steps in order.

1. Verify that all steps in the [Ewon Setup](#ewon-setup) section were followed.
2. If the system has just been configured, wait one hour for data to be registered in DataMailbox.
3. Verify your Talk2M credentials are correctly entered into the Ewon Connector settings in Ignition.
   * If your credentials were incorrectly entered, the Ignition logs will indicate a Authentication Error.  Check the logs on the Ignition webserver by clicking `Status` > `Logs`. If your credentials are incorrect, you will see log entries in this format.

   <img src="https://raw.githubusercontent.com/hms-networks/IgnitionEwonConnector/main/images/auth_error.JPG" alt="Ignition Authentication Error" width="1000"/>

   * If you see this log error, check your Talk2M Account information entered on the `Ewon Connector` configuration page. _Note: Any changes done on this page require a restart of the connector module or Ignition. You can restart the module by navigating to `Config` > `Modules` and clicking `restart` next to the module._
4. If tag values are still not populated in Ignition, please contact [support](#support).

### Changes on the Ewon Connector page do not take effect

Any changes done on this page require a restart of the connector module or Ignition. You can restart the module by navigating to `Config` > `Modules` and clicking `restart` next to the module.

<sup>[Back to top](#table-of-contents)</sup>

## [FAQs](#table-of-contents)

* [How do I check my Talk2M data usage?](#How-do-I-check-my-Talk2M-data-usage?)
* [If Ignition is not running will my data still be logged?](#If-Ignition-is-not-running-will-my-data-still-be-logged?)

### How do I check my Talk2M data usage?

Your Talk2M data usage can be accessed through [eCatcher](https://ewon.biz/cloud-services/talk2m/ecatcher "https://ewon.biz/cloud-services/talk2m/ecatcher").

1. Open eCatcher and login.
2. On the left hand menu bar click `Account`.
3. Once in the account properties click `Reports` near the top of the page.
4. From here you will be able to download the current or past monthly connection log reports.

### If Ignition is not running will my data still be logged?

If Ignition is offline, data will still be logged by the Flexy and stored in DataMailbox. The amount of data points stored in DataMailbox depends on your [account type](https://ewon.biz/cloud-services/talk2m/plan-pricing "https://ewon.biz/cloud-services/talk2m/plan-pricing"). Once Ignition is online again, all logged points will be fetched from DataMailbox by Ignition.

<sup>[Back to top](#table-of-contents)</sup>
