---
layout: page
title: User Guide
---

TheRealDeal is a **desktop app for real estate agents in Singapore, optimised for use via a Command Line Interface** (CLI) to streamline contact management by providing **quick access to contact preferences and available properties** within the local market. Dealing with Singaporean properties and clients, the app enables you to work efficiently - the faster you type, the faster TheRealDeal can help you find exactly what you need.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------
## Legend
These boxes in the User Guide has additional information that you should take note of.

<div markdown="block" class="alert alert-info">
**:information_source: Important:**<br>
Highlights important details to be aware of.
</div>

<div markdown="span" class="alert alert-primary">
:bulb: **Tip:**<br>
Provides you with helpful advice like keyboard shortcuts to use the application more effectively.
</div>

<div markdown="span" class="alert alert-warning">
:exclamation: **Caution:**<br>
Warns you of potential issues to should watch out for.
</div>

--------------------------------------------------------------------------------------------------------------------

## GUI Overview

TheRealDeal Graphical User Interface (GUI) is organised into **five** key components:

![GUI Overview](images/GUI-UI.png)

* `Menu Bar`: Located at the top left, this includes options such as `File` and `Help` for managing settings and accessing support.
* `Command Box`: The main area where users can enter commands to interact with the app.
* `Console Display`: Provides immediate feedback, displaying success or failure messages based on the user’s command.
* `List Card`: Displays key information about contacts or properties, depending on the user's command.
* `Status Bar`: Displays whether contacts or properties are currently being shown.

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Windows users:** Tutorial to download [here](https://se-education.org/guides/tutorials/javaInstallationWindows.html)<br>
   **Linux users:** Tutorial to download [here](https://se-education.org/guides/tutorials/javaInstallationLinux.html)<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

2. Download the latest `TheRealDeal.jar` file from [here](https://github.com/AY2526S1-CS2103T-W10-2/tp/releases).

3. Copy the file to the folder you want to use as the _home folder_ for TheRealDeal.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the <br>
   `java -jar TheRealDeal.jar` command to run the application.<br><br>
   A window similar to the picture below should appear in a few seconds. Note how the app contains some sample data.<br>

![Sample GUI Contacts](images/sampleGUIContacts.png)

*Confused? Refer to [**Basic Command Terminal Navigation**](#basic-command-terminal-navigation) at the bottom of the user guide.*

<div markdown="span" class="alert alert-primary">
:bulb: **Tip:**<br>
You can search for relevant information in the User Guide with this shortcut:<br><br>
Windows & Linux Users: <code>Ctrl + F</code><br>
Mac Users: <code>⌘ + F</code>
</div>

Type a command in the command box and press Enter to execute it. e.g. typing `help` and pressing the `Enter` key on the keyboard will open the help window.<br>
Some example commands you can try:

* `list` : Lists all contacts.

* `addcontact n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the Address Book.

* `deletecontact 3` : Deletes the 3rd contact shown in the current list.

* `clear` : Deletes all contacts and properties.

* `exit` : Exits the app.

To see the detailed explanation of every feature, click [here](#features)<br>
To see the summary of every feature, click [here](#command-summary)

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `addcontact n/NAME`, `NAME` is a parameter which can be used as `addcontact n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [notes/TEXT]` can be used as `n/John Doe notes/VIP` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times. Items can be used zero times if they are also wrapped in square brackets.<br>
  e.g. `[c/ID...]` can be used as ` ` (i.e. 0 times), `c/1`, `c/1 c/2` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Viewing help : `help`

Opens a separate window containing the link to this User Guide.

Format: `help`

![help message](images/helpMessage.png)

<div markdown="span" class="alert alert-primary">
:bulb: **Tip:**<br>
You can also press the <code>F1</code> key to open the help window
</div>

Refer to [Command Summary](#command-summary) for the list of commands and their formats.

### Listing all contacts and properties: `list`

Resets any active filters and shows the complete list of contacts or properties.

Both contact and property cards display an ID field which represents the Universally Unique Identifier (`UUID`) of that contact or property.
This `UUID` will be used for other commands.

Format: `list`

<div markdown="block" class="alert alert-info">
**:information_source: Important:**<br>
To switch to the list of contacts type: <code>filtercontact</code><br>
To switch to the list of properties type: <code>filterproperty</code>
</div>

**Contact List**
![list result contacts](images/listResultContacts.png)

**Property List**
![list result property](images/listResultProperties.png)

### Adding a contact : `addcontact`

Adds a new contact into the system.

Format: `addcontact n/NAME p/PHONE_NUMBER [e/EMAIL] [a/ADDRESS] [min/AMOUNT] [max/AMOUNT] [notes/TEXT] [status/STATUS]`

* Duplicate contacts (same name and phone number) will not be allowed to be added.
* Each new contact is assigned a (Universally Uniquely Identifier) UUID automatically.

Examples:
* `addcontact n/Charlie p/91236789 a/982 Tampines Road status/active`
* `addcontact n/Xi Mi p/65738475 e/ximi@example.com min/800000 max/1000000`

<div markdown="block" class="alert alert-info">
**:information_source: Important:**<br>
If not provided, minimum budget will be $0 and maximum budget will be $200,000,000,000.<br><br>
If not provided, the optional parameters will be empty.
</div>

For more information on the parameters, click [here](#command-parameters).

### Editing a contact : `editcontact`

Edits an existing contact in the address book.

Format: `editcontact CONTACT_ID [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [min/AMOUNT] [max/AMOUNT] [notes/TEXT] [status/STATUS]`

* Edits the contact that has the `CONTACT_ID` as specified which refers to the ID number shown in the displayed contact list.
* At least one of the optional fields must be provided.
* If the newly edited contact matches an existing contact (same name and phone number), it will be rejected.

Examples:
*  `editcontact 1 p/91234567 e/johndoe@example.com`
*  `editcontact 2 n/Betsy Crower`

<div markdown="block" class="alert alert-info">
**:information_source: Important:**<br>
You are able to edit valid contacts even if they are not currently shown on the GUI (but are stored in the address book). <br><br>
i.e. If you use <code>filtercontact</code> to remove a person from the GUI, you can still edit that person using <code>editcontact</code>.<br><br>
Use the <code>list</code> command to see the changes.
</div>

For more information on the parameters, click [here](#command-parameters).

### Filtering contacts : `filtercontact`

Filters the contacts based on the fields given.

Format: `filtercontact [n/NAME...] [p/PHONE...] [e/EMAIL...] [a/ADDRESS...] [min/AMOUNT] [max/AMOUNT] [notes/TEXT...] [status/STATUS...] [limit/LIMIT] [offset/OFFSET]`

* The search is case-insensitive. e.g `hans` will match `Hans`.
* Substring words will be matched e.g. `Bob` will match `Bobby`.
* Contacts matching any one of the keyword will be shown (meeting only one of the criteria is enough).
* Filtering is cumulative. Once you filter by name, you can filter by address subsequently.

Examples:
* `filtercontact a/yishun`
* `filtercontact n/Tan status/active inactive`

<div markdown="block" class="alert alert-info">
**:information_source: Important:**<br>
To reset all filters, you can type <code>list</code>
</div>

<div markdown="span" class="alert alert-primary">
:bulb: **Tip:**<br>
To filter for the different parameters (Name contains `Tan` and `Alex`)<br>
Type: `filtercontact n/Tan` followed by `filtercontact n/Alex`<br><br>
To filter for different parameters at the same time (Name contains `Tan` or `Alex`)<br>
Type: `filtercontact n/Tan Alex`
</div>

For more information on the parameters, click [here](#command-parameters).

### Deleting a contact : `deletecontact`

Deletes the specified contact from the address book.

Format: `deletecontact CONTACT_ID`

* Deletes the contact with the specified `CONTACT_ID`.
* The UUID refers to the ID number shown in the displayed contact list.
* The command only works on contacts currently visible in the property list panel. <br>Use the [list](#listing-all-contacts-and-properties-list) command first if needed.

Examples:
* `deletecontact 1`

<div markdown="span" class="alert alert-warning">
:exclamation: **Caution:**<br>
<code>deletecontact</code> is irreversible! Please use it carefully as you will not be able to retrieve the contact back.
</div>

<div markdown="block" class="alert alert-info">
**:information_source: Important:**<br>
You are able to edit delete contacts even if they are not currently shown on the GUI (but are stored in the address book). <br><br>
i.e. If you use <code>filtercontact</code> to remove a person from the GUI, you can still delete that person using <code>deletecontact</code>.<br><br>
Use the <code>list</code> command to see the changes.
</div>

### Adding a property: `addproperty`

Adds a property to the property list.

Format: `addproperty a/ADDRESS p/POSTAL price/PRICE t/TYPE status/STATUS bed/BEDROOM bath/BATHROOM f/FLOOR_AREA l/LISTING o/CONTACT_ID`

* Duplicate properties (same address and postal code) will not be allowed to be added.
* Each new property is assigned a (Universally Uniquely Identifier) UUID automatically.

Examples:
* `addproperty a/123 Orchard Rd p/238888 price/1950000 t/condo status/unavailable bed/3 bath/2 f/1023 l/sale o/1`
* `addproperty a/55 Pasir Ris Dr 1 p/519884 price/450000 t/hdb status/available bed/4 bath/2 f/1050 l/rent o/5`

For more information on the parameters, click [here](#command-parameters).

### Filtering properties : `filterproperty`

Filters the properties based on the fields given.

Format: `filterproperty [a/ADDRESS] [p/POSTAL] [t/TYPE] [bed/BEDROOM] [bath/BATHROOM] [f/FLOORAREA] [status/STATUS] [price/PRICE] [l/LISTING] [o/CONTACT_ID] [limit/LIMIT] [offset/OFFSET]`

* The search is case-insensitive. e.g `clementi` will match `Clementi`.
* Substring property addresses will be matched e.g. `Clementi` will match `CLementi Avenue 8`.
* Properties matching all the filters will be returned.
* Filtering is cumulative. Once you filter by `BATHROOM`, you can filter by `TYPE` subsequently.
* Only one filter per field is accepted. e.g. `t/condo hdb` is not accepted.

Examples:
* `filterproperty a/yishun`
* `filterproperty bed/2 f/100`
  
<div markdown="block" class="alert alert-info">
**:information_source: Important:**<br>
To reset all filters, you can type <code>list</code>
</div>

For more information on the parameters, click [here](#command-parameters).

### Deleting a property: `deleteproperty`

Deletes a property identified by its UUID.

Format: `deleteproperty PROPERTY_ID`

* Deletes the property with the specified `PROPERTY_ID`.
* The UUID refers to the ID number shown in the displayed property list.
* The command only works on properties currently visible in the property list panel. Use the [list](#listing-all-contacts-and-properties-list) command first if needed.

Examples:
* `deleteproperty 12`
* `deleteproperty 3`

<div markdown="span" class="alert alert-warning">
:exclamation: **Caution:**<br>
<code>deleteproperty</code> is irreversible! Please use it carefully as you will not be able to retrieve the property back.
</div>

### Mark property as sold : `sold`

Marks the statuses of the properties given as unavailable.

Format: `sold p/PROPERTY_ID...`

* `PROPERTY_ID` refers to the UUID of the property shown in the property list.

Examples:
* `sold p/1`
* `sold p/2 p/3`

<div markdown="span" class="alert alert-warning">
:exclamation: **Caution:**<br>
If one of the <code>PROPERTY_ID</code> given is invalid or the property is already marked as sold, the whole command is aborted.
The statuses of properties with valid <code>PROPERTY_ID</code> will not be changed.
</div>

For more information on the parameters, click [here](#command-parameters).

### Mark property as unsold : `unsold`

Marks the statuses of the properties given as available.

Format: `unsold p/PROPERTY_ID...`

* `PROPERTY_ID` refers to the UUID of the property shown in the property list.

Examples:
* `unsold p/1`
* `unsold p/2 p/3`

<div markdown="span" class="alert alert-warning">
:exclamation: **Caution:**<br>
If one of the <code>PROPERTY_ID</code> given is invalid or the property is already marked as unsold, the whole command is aborted.
The statuses of properties with valid <code>PROPERTY_ID</code> will not be changed.
</div>

For more information on the parameters, click [here](#command-parameters).

### Linking people and properties : `link`

Links people to properties as buyers or sellers by their UUIDs.

Format: `link c/CONTACT_ID... r/RELATIONSHIP p/PROPERTY_ID...`

* `RELATIONSHIP` **must be either `buyer` or `seller`**
* `CONTACT_ID` and `PROPERTY_ID` refer to the UUIDs of the people and properties being linked respectively.
* `link` can link any number of properties and people at once (excluding none).

<div markdown="block" class="alert alert-info">
**:information_source: Important:**<br>
A contact cannot be linked to a property as both a buyer and seller.<br>
An error will be thrown if this is attempted!
</div>

For more information on the parameters, click [here](#command-parameters).

### Unlinking people and properties : `unlink`

Unlinks people from properties as buyers and sellers, at the same time, by their UUIDs.

Format: `link c/CONTACT_ID... p/PROPERTY_ID...`

* `CONTACT_ID` and `PROPERTY_ID` refer to the UUIDs of the people and properties being linked respectively.
* `unlink` can unlink any number of properties and people at once (excluding none).

For more information on the parameters, click [here](#command-parameters).

### Showing properties associated with a contact : `showproperties`

Displays all properties associated with a specific contact by their UUID.

Format: `showproperties CONTACT_ID`

* `CONTACT_ID` refers to the UUID of the contact shown in the contact list.
* The view automatically switches to show the property list.
* If no properties are found, suggestions will be provided.

Examples:
* `showproperties 1`
* `showproperties 123`

### Showing contacts associated with a property : `showcontacts`

Displays all contacts associated with a specific property by their UUID.

Format: `showcontacts PROPERTY_ID`

* `PROPERTY_ID` refers to the UUID of the property shown in the property list.
* The view automatically switches to show the contact list.
* If no contacts are found, suggestions will be provided.

Examples:
* `showcontacts 1`
* `showcontacts 12`

For more information on the parameters, click [here](#command-parameters).

### Clearing all entries : `clear`

Clears ***ALL*** contacts and properties from the application.

Format: `clear`

<div markdown="span" class="alert alert-warning">
:exclamation: **Caution:**<br>
<code>clear</code> is irreversible! Please use it carefully as you will not be able to retrieve the data back.
</div>

**Expected Output**
![clear result](images/clearResult.png)

### Exiting the program : `exit`

Exits the program.

Format: `exit`

<div markdown="block" class="alert alert-info">
**:information_source: Important:**<br>
<code>exit</code> will also close any help windows that are open.
</div>


### Saving the data

TheRealDeal data is saved in the hard disk automatically after any command that changes the data. There is no need to save manually.


### Editing the data file

TheRealDeal data is saved automatically as two JSON files
1. `[JAR file location]/data/addressbook.json`
2. `[JAR file location]/data/propertybook.json`

Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">
:exclamation: **Caution:**
If your changes to the data file makes its format invalid, TheRealDeal will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br><br>
Furthermore, certain edits can cause the TheRealDeal to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the application in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous TheRealDeal home folder.
<br><br>
**Q**: Does TheRealDeal require internet connection?<br>
**A**: The application will only require internet connection if you want to access the User Guide. Every other command works without internet connection.
<br><br>
**Q**: Where is the data for TheRealDeal stored?<br>
**A**: Refer to [this](#editing-the-data-file) section to learn more.
<br><br>
**Q**: How do I back up my data?<br>
**A**: You can save a copy of the `addressbook.json` and `propertybook.json` to a back up location.
<br><br>
**Q**: Why isn't my command working for a contact/property that I have already input?<br>
**A**: Ensure that the contact/property you are affecting with the command is visible on the GUI.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

2. **If you minimise the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimised, and no new Help Window will appear. The remedy is to manually restore the minimised Help Window.

3. **Contact names do not support special characters.** The current validation for contact names requires it to only consist of alphanumeric characters and spaces. The does not support names with special characters like `/` or `-` (e.g. `s/o`, `John-Mary`). This becomes a drawback for property agents who wish to store their contacts full name. The current workaround will be to only use alphanumeric characters (e.g. `son of` instead of `s/o`). Future improvements aim to support this functionality.

--------------------------------------------------------------------------------------------------------------------

## Command summary
To see the detailed explanation of every feature, click [here](#features)<br>

| Action                      | Format, Examples                                                                                                                                                                                                                                |
|-----------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add Contact**             | `addcontact n/NAME p/PHONE [e/EMAIL] [a/ADDRESS] [min/AMOUNT] [max/AMOUNT] [notes/TEXT] [s/STATUS]` <br> <br> e.g., `addcontact n/Alex p/91423123 a/982 Yishun Road s/active notes/wants near school min/100000 max/300000`      |
| **Edit Contact**            | `editcontact UUID [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [min/AMOUNT] [max/AMOUNT] [notes/TEXT] [s/STATUS]`<br> <br> e.g.,`editcontact 2 n/Bobby a/Block 321 Punggol`                                                                 |
| **Filter Contact**          | `filtercontact [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [min/AMOUNT] [max/AMOUNT] [notes/TEXT] [s/STATUS] [limit/LIMIT] [offset/OFFSET]`<br> <br> e.g.,`filtercontact n/Tan s/active`                                                   |
| **Delete Contact**          | `deletecontact UUID`<br> <br> e.g., `deletecontact 3`                                                                                                                                                                                           |
| **Add Property**            | `addproperty a/ADDRESS p/POSTAL price/PRICE t/TYPE status/STATUS bed/BEDROOM bath/BATHROOM f/FLOOR_AREA l/LISTING o/CONTACT_ID`<br> <br> e.g., `addproperty a/123 Orchard Rd p/238888 price/1950000 t/condo status/unavailable bed/3 bath/2 f/1023 l/sale o/1`|
| **Filter Property**         | `filterproperty [a/ADDRESS] [p/POSTAL] [t/TYPE] [bed/BEDROOM] [bath/BATHROOM] [f/FLOORAREA] [status/STATUS] [price/PRICE] [l/LISTING] [o/CONTACT_ID] [limit/LIMIT] [offset/OFFSET]`<br> <br> e.g., `filterproperty bed/2 price/2000`            |
| **Delete Property**         | `deleteproperty UUID`<br> <br>  e.g., `deleteproperty 12`                                                                                                                                                                                       |
| **Mark Property as Sold**   | `sold p/UUID...` <br><br> e.g. `sold p/1 p/2`                                                                                                                                                                                                   |
| **Mark Property as Unsold** | `unsold p/UUID...` <br><br> e.g. `unsold p/2 p/3`                                                                                                                                                                                               |
| **Link**                    | `link c/CONTACT_ID... r/RELATIONSHIP p/PROPERTY_ID...`<br> <br>  e.g., `link c/12 r/buyer p/12 p/4`                                                                                                                                             |
| **Unlink**                  | `unlink c/CONTACT_ID... p/PROPERTY_ID...`<br> <br> e.g., `unlink c/1 p/14 c/2`                                                                                                                                                                  |
| **Show linked properties**  | `showproperties CONTACT_ID`<br> <br>  e.g., `showproperties 2`                                                                                                                                                                                  |
| **Show linked contacts**    | `showcontacts PROPERTY_ID`<br> <br> e.g., `showcontacts 3`                                                                                                                                                                                      |
| **List**                    | `list`                                                                                                                                                                                                                                          |
| **Clear**                   | `clear`                                                                                                                                                                                                                                         |
| **Help**                    | `help`                                                                                                                                                                                                                                          |

--------------------------------------------------------------------------------------------------------------------

## Command Parameters
This table shows every parameter and prefix used in TheRealDeal.

<div markdown="block" class="alert alert-info">
**:information_source: Important:**<br>
If the command states that the prefix is optional e.g. <code>n/NAME [notes/TEXT]</code><br>
an empty parameter will be the same as not having the prefix<br>
e.g. <code>n/NAME notes/</code> is the same as <code>n/NAME</code>
</div>

### Contact Management
These are prefixes for purely contact related commands.
Related commands: [`addcontact`](#adding-a-contact--addcontact), [`filtercontact`](#filtering-contacts--filtercontact), [`editcontact`](#editing-a-contact--editcontact)

| Parameter      | Prefix  | Constraints                                                                                                                 |
|----------------|---------|-----------------------------------------------------------------------------------------------------------------------------|
| Name           | n/      | Should only contain alphabetical characters (a-z, A-Z, 0-9) or spaces                                                       |
| Phone Number   | p/      | Should only contain numbers (0-9), and it should be at least 3 digits long                                                  |
| Email          | e/      | Should follow the format: name@example.com                                                                                  |
| Address        | a/      | Can take any value. Maximum of 200 characters                                                                               |
| Minimum Budget | min/    | Should be a non-negative integer. If not provided, will have a default of $0                                                |
| Maximum Budget | max/    | Should be a non-negative integer and more than the minimum budget. If not provided, will have a default of $200,000,000,000 |
| Notes          | notes/  | Can take any value. Maximum of 500 characters                                                                               |
| Status         | status/ | Should only be these (case-insensitive): active, inactive                                                                   |

### Property Management
These are prefixes for purely property related commands.
Related commands: [`addproperty`](#adding-a-property-addproperty), [`filterproperty`](#filtering-properties--filterproperty)

| Parameter      | Prefix  | Constraints                                                                                                       |
|----------------|---------|-------------------------------------------------------------------------------------------------------------------|
| Address        | a/      | Should only contain alphabetical 5 to 200 characters (a-z, A-Z, 0-9) or spaces, with at least 1 letter and 1 digit|
| Postal code    | p/      | Should only contain numbers (0-9), and it should be exactly least 6 digits long. (Singaporean Postal Code)        |
| Price          | price/  | Should be an integer from 1 to 1,000,000,000,000                                                                  |
| Type           | t/      | Should only be these (case-insensitive): hdb, condo, landed, apartment, office, others                            |
| Status         | status/ | Should only be these (case-insensitive): available, unavailable                                                   |
| Bedroom count  | bed/    | Should be an integer from 0 to 20                                                                                 |
| Bathroom count | bath/   | Should be an integer from 0 to 20                                                                                 |
| Floor area     | f/      | Should be an integer from 50 to 100,000                                                                           |
| Listing        | l/      | Should only be these (case-insensitive): sale, rent                                                               |
| Owner ID       | o/      | Should be a valid Contact UUID                                                                                    |

### Others
These are prefixes that are used over multiple commands.
Related commands: [`filtercontact`](#filtering-contacts--filtercontact), [`filterproperty`](#filtering-properties--filterproperty), [`sold`](#mark-property-as-sold--sold), [`unsold`](#mark-property-as-unsold--unsold), [`link`](#linking-people-and-properties--link), [`unlink`](#unlinking-people-and-properties--unlink), [`showproperties`](#showing-properties-associated-with-a-contact--showproperties), [`showcontacts`](#showing-contacts-associated-with-a-property--showcontacts)

| Parameter      | Prefix  | Constraints                                            |
|----------------|---------|--------------------------------------------------------|
| Limit          | limit/  | An integer more than 0                                 |
| Offset         | offset/ | An integer more than or equals to 0                    |
| Contact UUID   | c/      | Should be a valid Contact UUID                         |
| Property UUID  | p/      | Should be a valid Property UUID                        |
| Relationship   | r/      | Should only be these (case-insensitive): buyer, seller |

## Basic Command Terminal Navigation
1. Determine the address of the folder where TheRealDeal is installed.
2. Open "Powershell" on Windows or "Terminal" on MacOS and Linux.
3. Type `cd ADDRESS`, where `ADDRESS` is the address where TheRealDeal is installed, and hit enter.
4. Type `java -jar TheRealDeal.jar`, and hit enter, to run the application.

## Glossary of Terms

| Term         | Definition                                                                                                                                      |
|--------------|-------------------------------------------------------------------------------------------------------------------------------------------------|
| **CLI**      | Command Line Interface (CLI) is an interface where the user interacts with the application via directly typed commands.                         |
| **GUI**      | Graphical User Interface (GUI) is a visual interface that users can interact with.                                                              |
| **UUID**     | Universally unique identifier (UUID) of a contact or a property. It is generated by the application and can be referenced from the application. |
| **Contact**  | A contact whose details are stored in the system. The contact can either be a buyer, seller, tenant or landlord.                                  |
| **Property** | A building whose details are stored in the system. The building can either be a HDB, condominium, landed, apartment or office.                  |
| **JSON**     | JavaScript Object Notation (JSON) is a text-based data storage format that is used to store the data of the application.                        |
