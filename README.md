# Utilcord 
A multipurpose Discord bot that offers a variety of utility tools: **translator, dictionary, currency converter, view user info, and timer**.\
```❗ Currently, translation feature are unavailable in the hosted version. Consider using the local version with your own API key configured in the .env file.```

## 🔗 Bot Invite link (Hosted Version)
https://discord.com/oauth2/authorize?client_id=978649404816916521&permissions=8&scope=bot

## 👋 Get Started
- **Prefix:** To use any of the bot's commands, type `e!` in front of the command.
- **Help:** To get the help menu, type `e!help`.
- **Language Help:** To get a list of supported languages for the translator feature, type `e!helpts`.
- **Currency Help:** To get a list of supported currencies for the currency converter feature, type `e!helpct`.
- **Time Help:** To get a list of supported time zones for the time feature, type `e!helptime`.

## 🤖 Commands Menu
- **Translator:** To translate a message to a specific language, type `e!ts targetLanguage message`. The bot will automatically detect the source language.
- **Dictionary:** To get the definition of an English word, type `e!d word`.
- **Currency Converter:** To convert one currency to another, type `e!e baseCurrency targetCurrency`.
- **Time:** To get the current time from a specific time zone, type `e!time timeZoneName` or `e!time timeZoneCode`.
- **Timer:** To set a timer, type `e!timer minutes`. The bot will send a message when the timer is up.
- **User Info:** To get information about a specific user, type `e!info @user`.
- **About:** To get information about the bot and its developer, type `e!about`.

## 📥 Installation
### 💻 Prerequisite
- Java Development Kit (JDK) 18 (Do not use later version until Discloud supports it)
- Maven 3.8.5 or later

### ⚙️ Setup
1. Clone the repository: Open a terminal and clone the IAmNotARobot repository using the following command:
   ```bash 
   git clone https://github.com/tansweeyang/IAmNotARobot.git
   ```
2. Install dependencies: Navigate to the cloned directory and run the following command to download all necessary libraries:
   ```bash
   mvn clean install
   ```
3. Follow the instructions from the websites to get your unique API keys:
   - Discord Jda API: https://discord.com/developers/docs/intro
   - Currency Converter API: https://currencyapi.com/
4. Configure environment variables: Create a ```.env``` file in the root directory and add the following lines, replacing the placeholders with your actual values:
   ```env
   DISCORD_JDA_KEY=YourTokenHere
   CURRENCY_TRANSLATOR_API_KEY=YourTokenHere    
   GOOGLE_SCRIPT_TRANSLATOR_DEPLOYMENT_ID=YourTokenHere
   ```
5. Package the application: Generate a JAR file:
   ```bash
   mvn clean package
   ```
6. Run the application: Execute the main class with the following command:
   ```bash
   java -cp bot.jar com.eislyn.utilcord.app.IAmNotARobot-version
   ```

## 📤 Deployment
1. Change the release version in pom.xml.
   ```xml
   <version>Released Version</version>
   ```
2. Generate zip folder to deploy:
   - Run the following command to generate an application zip file ready to be deployed.
     ```bash
     mvn clean package
     ```
   - A zipped application folder ```bot.zip``` will be created in your desktop. The zip folder will contain the following three files: ```IAmNotARobot-Version.jar```, ```.env```, and ```discloud.config```
3. Join the Discloud Server:
   - https://discord.gg/discloud-584490943034425391.
4. Upload and Start:
   - In the Discloud server, type the following command in the commands channel and follow the prompts to upload your zipped ```bot.zip``` file.
     ```bash
     .upc
     ```
   - Once uploaded, type the following command to launch the bot!
     ```bash
     .start
     ```
   - If you face any issue with deployment using Discloud, feel free to reach out to Discloud's highly active community or moderators in the server.

## 👥 Contribute
If you would like to contribute to this project, please fork the repository and submit a pull request.

## 🦻 Support
If you have any questions or issues, please feel free to contact me or open an issue in this repository.

## 📃 License
This project is licensed under the MIT License - see the LICENSE file for details.
