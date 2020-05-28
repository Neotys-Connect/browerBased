<p align="center"><img src="/screenshot/BrowserBased.png" width="40%" alt="Browserbased Logo" /></p>

# Browser based	Integration for NeoLoad (experimental)

## Overview

More and more web applicationS are java script heavy using framework such as Angular, JQuery that are CPU and memory intensive on the browser side.
Moreover, badly optimized client side programming qnd inefficient use of the ajax calls or caching can lead to lot of netyork traffic leading to poor 
end user experience especially over lower bandwidth connexion.
 
Traditional load testing techniques that simulate the protocol level traffic that the browser generate do not capture the client side 
behavior, hence the need to introduce browser based techniques that simulate user behaviours with using headless browsers.

These Advanced Actions allows you to leverage a browser based approach with  [NeoLoad](https://www.neotys.com/neoload/overview) 
 
* **Browser based advanced actions**

Actions are based on the Htmlunit, a headless browser that allows to mimic user actions on the browser such as: 
   
* OpenPage: Loads a web page in the browser.
* ClickOnElementByXpath: Clicks on a Web Component by Xpath.
* ClickOnElementByID: Clicks on a Web Component by ID.
* ClickOnElementByName: Clicks on a Web Component by name.
* ClickOnElementByHref: Clicks on a Web Component by Href.
* SelectItemFromListByID: Selects an element in a dropdown list by name.
* SelectItemFromListByName: Selects an element in a dropdown list by name.
* SetTextOnElementByID: Sets Text on a Web Component by id.
* CloseBrowser: Closes the browser.

| Property | Value |
| -----| -------------- |
| Maturity | Experimental |
| Author   | Neotys Partner Team |
| License  | Htmlunit is based on Apache license 2.0 |
| NeoLoad  | 7.3+ (Enterprise or Professional Edition w/ Integration & Advanced Usage and NeoLoad Web option required)|
| Requirements | NeoLoad Web |
| Bundled in NeoLoad | No
| Download Binaries | <ul><li>[latest release](https://github.com/NeotysLab/browerBased/releases/latest) is only compatible with NeoLoad from version 6.7</li><li> Use this [release](https://github.com/Neotys-Labs/Dynatrace/releases/tag/Neotys-Labs%2FDynatrace.git-2.0.10) for previous NeoLoad versions</li></ul>|

## Installation

1. Download the [latest release](https://github.com/NeotysLab/browerBased/releases/latest) for NeoLoad from version 7.3
1. Read the NeoLoad documentation to see [How to install a custom Advanced Action](https://www.neotys.com/documents/doc/neoload/latest/en/html/#25928.htm).

<p align="center"><img src="/screenshot/advanced_action.png" alt="Browser Based Advanced Action" /></p>

## NeoLoad Set-up

Once installed, how to use in a given NeoLoad project:

1. Create a “Browser Based” User Path.
1. Insert one of the browser based advanced actions in the ‘Actions’ block. A typical sequence will be openpage, interactions with web pages content, and finally closeBrowser.
   <p align="center"><img src="/screenshot/BBAction.png" alt="Browser based User Path" /></p>
1. For each of the browser based actions, set the parameters to simulate user interactions with the web applications you are about to load test.
   <p align="center"><img src="/screenshot/OpenPage.png" alt="Action's parameters" /></p>
1. Select the Actions container and set the runtime, pacing, and general settings.  
1. Create a "PopulationBrowserBased" Population that contains 100% of "Browser Based" User Path.
   <p align="center"><img src="/screenshot/population.png" alt="Browser based Population" /></p>
1. In the **Runtime** section, select your scenario, select the "BrowserBasedScenario" population and define the parameters of your load test.
   <p align="center"><img src="/screenshot/scenario.png" alt="Load Variation Policy" /></p>
1. Do not use multiple load generators. Good practice should be to keep only the local one.

## Parameters for Browser Based testing

Tip: Get NeoLoad API information in NeoLoad preferences: Project Preferences / REST API.

* Parameters in common for all the advanced actions

| Name             | Description |
| -----            | ----- |
| Performance (optional)  | enable the UX measurement : true: enable ; false : Disable Default value is "enable the measurement of UX ".|
| TraceMode (optional) | enable loggin details  : true: enable ; false : Disable Default value is "enable logging ".|
| ClearCache (optional)  | if true it will clear the cache before interacting with the browser . Value possible true/false Default value is "if true it will clear the cache before interacting with the browser . Value possible true/false".|
| ClearCookie (optional) | if true it will clear the cookie before interacting with the browser. Value possible true/false  Default value is "if true it will clear the cookies before interacting with the browser. Value possible true/false".|

* OpenPage: Loads a web page in the browser.

| Name             | Description |
| -----            | ----- |
| URL (required) | Url Of the website to test Default value is "Url Of the website to test". |
| BrowserVersion (required) | Address of the account that will send the transaction Default value is "Address of the account that will send the transaction". |
| ProxyHost (optional) | Keystore of the from account  Default value is "Keystore of the from account ".|
| ProxyPORT (optional)  | Keystore of the from account  Default value is "Keystore of the from account ".|

* ClickOnElementByXpath: Clicks on a Web Component by Xpath.

| Name             | Description |
| -----            | ----- |
| Xpath (required) | xpath  of the element of the dom Default value is "xpath to click on the  dom element".|

* ClickOnElementByID: Clicks on a Web Component by ID.

| Name             | Description |
| -----            | ----- |
| Elementid (required)| Element id of the dom Default value is "Element id of the dom to click on".|

* ClickOnElementByName: Clicks on a Web Component by name.

| Name             | Description |
| -----            | ----- |
| Name (required) | Name of the element of the dom Default value is "class of the element of the dom by name".|

* ClickOnLinkByHref: Clicks on a Web Component by Href.

| Name             | Description |
| -----            | ----- |
| Href (required) | Url of the link to click Default value is "Url of the link to click".|

* SelectItemFromListByID: Selects an element in a dropdown list by name.

| Name             | Description |
| -----            | ----- |
| ElementID (required) | Element id of the dom Default value is "Element id of the dom to set text".|
| Value (required) | Text to set in the dom element Default value is "Text to set in the dom element".|

* SelectItemFromListByName: Selects an element in a dropdown list by name.

| Name             | Description |
| -----            | ----- |
| Name (required) | Name of the element of the dom Default value is "Name of the dom to set text".|
| Value (required) | Text to set in the dom element Default value is "Text to set in the dom element".|

* SetTextOnElementByID: Sets Text on a Web Component by id.

| Name             | Description |
| -----            | ----- |
| Elementid (required) | Element id of the dom Default value is "Element id of the dom to set text".|
| Texte (required) | Text to set in the dom element Default value is "Text to set in the dom element".|

* CloseBrowser: Closes the browser.

## Status Codes
* Browser based monitoring
    * ???