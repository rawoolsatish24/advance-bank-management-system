# Advance Bank Management System
<p align="justify" width="100%">Welcome to my awesome repository! If you find this project useful or interesting, please consider giving it a star ⭐. You can also leave comments or open issues if you have questions or suggestions. Feel free to share this repository with others who might benefit from it. Let's make this project even better together!</p>

## Table of Contents
- [Description](#description)
- [Tools & Technologies](#tools--technologies)
- [Functionalities](#functionalities)
- [User Roles](#user-roles)
- [Pages](#pages)
- [Installation](#installation)
- [Usage](#usage)
- [Screenshots](#screenshots)

## Description
<p align="justify" width="100%">The <strong>Advance Bank Management System</strong> is a comprehensive software solution developed using Java, JavaFX, and Scene Builder. The system is designed for a bank named <strong>Sapphire Horizon Bank</strong>. This bank application offers customers the ability to open various types of accounts, including current accounts (basic or reward) and savings accounts (including ISAs). The software provides its bank employees with a user-friendly interface to manage customers and their accounts efficiently. The project encompasses the implementation of JavaFX-based Graphical User Interfaces (GUIs) and a robust domain model.</p>

## Tools & Technologies

- Java
- JavaFX
- CSV
- Scene Builder
- Eclipse

## Functionalities

- **Object-Oriented Design:** Demonstrates knowledge and understanding of object-oriented modeling and design concepts, including inheritance, interfaces, and abstract classes.
- **JavaFX GUI:** Implements JavaFX-based graphical user interfaces for efficient user interactions.
- **Advanced Programming Techniques:** Utilizes advanced programming techniques, including streams and structured text files (CSV), for tasks such as searching in the Customer Manager scene.
- **Data Persistence:** Implements structured text files (CSV) for data persistence, allowing information to persist between runs of the application.
- **Theme Options:** Enhances user experience by providing theme options, allowing users to customize the application's appearance.
- **Account Management:** Allows viewing, updating, and adding customers through the Account Manager scene.
- **Customer Details:** Enables viewing and updating individual customer details through the Customer scene.
- **Account Operations:** Supports various account operations, including viewing account details, depositing, and withdrawing funds.
- **Error Handling:** Incorporates appropriate error handling throughout the application for a user-friendly experience.

## User Roles

- **General User:** Accesses various functionalities of the application, including viewing, updating, adding new customers, adding new bank accounts, and performing bank operations such as deposit, withdraw, etc.

## Pages

- **Account Manager:** Displays a list of all customers, allows selecting and viewing individual customer details, and provides the option to add a new customer. Also allows users to customize the application's appearance by choosing from different theme options.
- **Customer Dashboard:** Shows individual customer details, including accounts, and allows operations such as depositing, withdrawing funds, and opening new accounts.

## Installation

1. Clone this repository.
2. Download the latest JavaFX SDK from [here](https://gluonhq.com/products/javafx/).
3. Extract the downloaded SDK. Now open a new folder in C:\ drive named '<strong>Java</strong>' and copy that extracted SDK folder into the Java folder.
4. Now download Scene Builder from [here](https://gluonhq.com/products/scene-builder/) and install it.
5. Open Eclipse IDE, if not installed, download it from [here](https://www.eclipse.org/downloads/packages/installer).
6. From the menu bar, go to Help->Eclipse Marketplace.
7. Search for '<strong>fx</strong>' and press enter. Install the plugin with the name '<strong>e(fx)clipse</strong>'. You have to accept all the terms while installing.
8. Wait for the installation to complete and then restart the IDE.
9. From the menu bar, go to Window->Preferences.
10. In the Preferences window, search and click '<strong>JavaFX</strong>'.
11. The opened tab will require 2 input fields to fill as below:<br />
    a. SceneBuilder executable: Browse your installed SceneBuilder application (.exe) location.<br />
    E.g. **C:\Users\Lenovo\AppData\Local\SceneBuilder\SceneBuilder.exe**<br />
    b. JavaFX SDK: Go to your copied JavaFX SDK folder, open the '<strong>lib</strong>' folder in that, and copy-paste that directory path into this textbox.<br />
    E.g. **C:\Java\javafx-sdk-21.0.1\lib**
12. Restart the Eclipse IDE and open the cloned repository.
13. Go to [here](https://openjfx.io/openjfx-docs/). On the left panel of the website, look for the '<strong>JavaFX and Eclipse</strong>' tab and click it.
14. Scroll down to the 3rd point named '<strong>3. Add VM arguments</strong>'.
15. Switch to the '<strong>Windows</strong>' tab if you are a Windows user and copy the entire command.<br />
    E.g. **--module-path "\path\to\javafx-sdk-21.0.1\lib" --add-modules javafx.controls,javafx.fxml**
16. Come back to Eclipse, click on the down arrow located at the right of the '**Run**' button, and then click on '**Run Configurations...**'.
17. In a new window, click on the '**Java Application**' tab. See if any project configuration already exists. If doesn't, create a new configuration named '**MainApp**'.
18. Click on the configuration under the '**Java Application**' tab. Now switch to the '**Arguments**' tab on the right panel.
19. Paste that previously copied command in the '**VM arguments:**' textbox. In that command replace '**\path\to\javafx-sdk-21.0.1\lib**' with your actual JavaFX SDK lib location as previously found in 11.b.
20. Your command now will look something like '**--module-path "C:\Java\javafx-sdk-21.0.1\lib" --add-modules javafx.controls,javafx.fxml**'.
21. Finally click on the '**Apply**' button and you are now ready to run the application.

## Usage

1. Launch the Advance Bank Management System application using Eclipse IDE.
2. The Account Manager scene will be opened by default to view a list of customers or add a new customer.
3. Explore the Customer scene to view and update individual customer details, manage accounts, and perform account operations.
4. Customize the application's appearance by shrinking the **Add Customer** section located in the Account Manager scene and selecting your preferred theme.

## Screenshots

![Screenshot 1](/images/screenshots/1.png)<br />
*1. Pink theme: Account manager scene to add new customers, and view existing customers and total balance bank hold from every customer*<hr>

![Screenshot 2](/images/screenshots/2.png)<br />
*2. Dark theme: Shrink **Add Customer** section to set the application theme or see the live date and time*<hr>

![Screenshot 3](/images/screenshots/3.png)<br />
*3. Red theme: Customer scene to view/update customer details*<hr>

![Screenshot 4](/images/screenshots/4.png)<br />
*4. Red theme: Customer scene to add new bank account as per its requirements*<hr>

![Screenshot 5](/images/screenshots/5.png)<br />
*5. Red theme: Customer scene to perform operations on existing bank account as per account category*<hr>

![Screenshot 6](/images/screenshots/6.png)<br />
*6. Red theme: Customer scene to perform withdrawal on particular bank account*<hr>

![Screenshot 7](/images/screenshots/7.png)<br />
*7. Orange theme: Customer scene to perform applying an interest rate to customers' particular bank balance as per set rate*<hr>

![Screenshot 8](/images/screenshots/8.png)<br />
*8. Orange theme: Customer scene updated bank accounts will be reflected immediately, user total balance from all bank accounts can also be seen*<hr>

![Screenshot 9](/images/screenshots/9.png)<br />
*9. Light theme: Account manager scene to search customers by name*<hr>

![Screenshot 10](/images/screenshots/10.png)<br />
*10. Light theme: Account manager scene to view the searched customers matching with search string*<hr>
