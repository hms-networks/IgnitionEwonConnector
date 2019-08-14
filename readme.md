
# Ignition Ewon Connector

## [Table of Contents](#table-of-contents)

1. [Description](#description)
2. [Version](#version)
3. [Support](#support)
4. [Features](#features)
5. [Download](#download)
6. [Installation](#installation)
7. [Setup](#setup)
8. [Connector Usage](#connector-usage)


## [Description](#table-of-contents)

Synchronize Ewon data to Ignition's Tag Historian

<sup>[Back to top](#table-of-contents)</sup>

## [Version](#table-of-contents)

This module version is compatible with Ignition 8.  For an Ignition 7.9 compatible module go to [Ignition-7.9-master](https://github.com/hms-networks/IgnitionEwonConnector/tree/Ignition-7.9-master) branch.

<sup>[Back to top](#table-of-contents)</sup>

## [Support](#table-of-contents)

This application is supported by HMS Networks' North American offices.

| Phone                                        | Forum                                      | Email                                           |
|:--------------------------------------------:|:------------------------------------------:|:-----------------------------------------------:|
| [+1 312 829 0601](tel:13128290601), Option 2 | [hms.how](https://forum.hms-networks.com/) | [support@hms.how](mailto:support@hms.how)       |

<sup>[Back to top](#table-of-contents)</sup>

## [Features](#table-of-contents)

* Read Functionality
   * Historical (via DataMailbox) (default option)
      * Updated up to once per minute
   * Realtime (via M2Web)
      * Updated up to once per second
      * Can be enabled per-tag, per-Ewon or system-wide
* Write Functionality
   * Modify Ewon tag values from Ignition

<sup>[Back to top](#table-of-contents)</sup>

## [Download](#table-of-contents)

The module can be downloaded from the [releases page](https://github.com/hms-networks/IgnitionEwonConnector/releases).

## [Installation](#table-of-contents)

1.  On the Gateway Webpage, select `Config` > `SYSTEM` > `Modules` to open the Module Configuration page.

      <img src="https://raw.githubusercontent.com/hms-networks/IgnitionEwonConnector/Ignition-8-master/images/module_config_page.JPG" alt="Ignition Module Page" width="1000"/>

2. Scroll to the bottom on the list, find the blue arrow, and click the `Install or Upgrade a Module` link.

      <img src="https://raw.githubusercontent.com/hms-networks/IgnitionEwonConnector/Ignition-8-README-master/images/module_install_link.JPG" alt="Ignition Module Install Link" width="1000"/>

3. Click `Choose File`, select a .modl file that you have previously downloaded.

      <img src="https://raw.githubusercontent.com/hms-networks/IgnitionEwonConnector/Ignition-8-master/images/module_install_page.JPG" alt="Ignition Module Install Page" width="1000"/>

4. Click Install.

   * When the page reloads you can now see the module you installed in the list of modules.

<sup>[Back to top](#table-of-contents)</sup>

## [Setup](#table-of-contents)

With the module successfully installed, a new `Ewon Connector` page will be added to your Ignition Gateway webpages. Navigate to this page by selecting `Config` > `TAGS` > `Ewon Connector`.

   <img src="https://raw.githubusercontent.com/hms-networks/IgnitionEwonConnector/Ignition-8-master/images/config_page.JPG" alt="Ewon Module Configuration Page" width="1000"/>


* **Main**
   * **Name** - Unique name of the tag provider created in Ignition.
   * **Enabled** - Option to turn on/off the Ewon Connector functionality.
   * **Tag Names Contain Periods** - Check this if your Ewon tag names contain periods. If your tag names contain periods they must not contain underscores.
   * **Poll Rate in Minutes** - Interval for how often data will be pulled from Datamailbox.
   * **Live Data Poll Rate in Seconds** - Interval for how often data will be pulled from an Ewon when realtime reads are activated.

* **Ewon Account Information**
   * **Account** - Your Talk2M account name.
   * **Username** - Your Talk2M user name.
   * **Password** - Your Talk2M password.
   * **API Key** - Your Talk2M Developer ID.
      * If you do not have a Talk2M Developer ID, you may request one here: [https://developer.ewon.biz/registration](https://developer.ewon.biz/registration "https://developer.ewon.biz/registration").

* **Ewon Device Information** - All Ewons must have at least once common user account to enable realtime and write functionality. The username and password for this account must be identical for all linked Ewons.
   * **Ewon Username** - Common Ewon username.
   * **Ewon Password** - Common Ewon password.

* **History**
   * **History Enabled** - Option to turn off historical logging.
   * **Target History Provider** - Ignition History Provider used to log Ewon data.

* **Advanced**
    * **Read all values in realtime** - Option to always read values in realtime.
      * **Note:** This disables Datamailbox reads. Values logged on the Ewon are not logged in Ignition when this option is enabled.

### Saving Changes

Once all the configuration information has been entered, click `Save Changes`, then restart Ignition.

<sup>[Back to top](#table-of-contents)</sup>

## [Connector Usage](#table-of-contents)

Tags will be created automatically in Ignition on startup. Tags will be visible under the provider name assigned during the configuration process. Each Ewon tied to the Talk2M account will be a unique tag directory.

<img src="https://raw.githubusercontent.com/hms-networks/IgnitionEwonConnector/Ignition-8-master/images/tag_browser.JPG" alt="Ignition Tag Browser" width="600"/>

### **Reading Values**

The tags created by the connector are native Ignition tags and are updated cyclically by the connector based on the polling rates set during the [Setup](#setup) step.

The `Tag Browser` tool in Ignition Designer can be used to read tag values.

### **Writing Values**

Ewon tag values can be written from Ignition. When attempting to write a tag value, be sure that the the communication mode is set to `Comm Read/Write` in the Ignition Designer `Project` menu.

The `Tag Browser` tool in Ignition Designer can be used to write tag values.

After a tag value is written in Ignition, the displayed value my revert back to its previous state. This is because new Datamailbox values were received. Datamailbox values will alway lag the live tag value. This behavior can be avoided by temporarily enabling realtime mode for the tag/Ewon being written to.

### **Realtime Values**

The realtime value functionality allows Ignition to read live tag values from an Ewon up to once per second.

This functionality can be enabled in three ways.

**1. Individual Tag**

   * Open Designer and navigate to the `Tag Browser`
   * Expand the `All Providers` directory.
   * Expand the directory associated with your Ewon Connector Tag Provider
   * Expand the directory associated with the Ewon you want to read a live tag value from
   * Expand the tag name to show the tag's properties.
   * Find the `Realtime` property and enable it.

**2. Individual Ewon**

   * Open Designer and navigate to the `Tag Browser`
   * Expand the `All Providers` directory.
   * Expand the directory associated with your Ewon Connector Tag Provider
   * Expand the directory associated with the Ewon you want to read live tag values from
   * Expand the `_config` directory.
   * Find the `AllRealtime` tag and enable it.

**3. All Ewons**

   * Navigate to the Ewon Connector Configuration webpage discussed in the [Setup](#setup) section.
   * Enable the `Read all values in realtime` option.
   * Click `Save Changes`, then restart Ignition.

_Note: The realtime functionality counts against your Talk2M account's monthly bandwidth._

### **Deleting Tags**

Tags can be deleted by right clicking the tag in the `Tag Browser` then selecting `Delete`. If the tag still exists on the Ewon or in Datamailbox the tag will be recreated in Ignition on the next Datamailbox update.

<sup>[Back to top](#table-of-contents)</sup>
