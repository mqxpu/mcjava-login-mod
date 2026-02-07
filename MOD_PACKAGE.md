# MCJava Login Mod - Complete Package

## Overview

This is a complete package for the MCJava Login Mod, which requires password authentication before players can connect to the server. Incorrect passwords will result in immediate connection denial.

## Package Contents

```
mcjava-login-mod/
├── src/
│   ├── main/
│   │   ├── java/com/example/loginmod/
│   │   │   ├── client/
│   │   │   │   └── LoginModClient.java     # Client-side code
│   │   │   ├── LoginCommand.java           # Command handling
│   │   │   ├── LoginManager.java           # Password management
│   │   │   ├── LoginMod.java               # Server-side main class
│   │   │   └── PlayerActionListener.java   # Action restriction
│   │   └── resources/
│   │       └── fabric.mod.json            # Mod configuration
├── build.gradle                           # Build configuration
├── gradle.properties                      # Version configuration
├── gradlew.bat                            # Gradle wrapper (Windows)
├── gradle/
│   └── wrapper/
│       └── gradle-wrapper.properties      # Gradle wrapper config
├── README.md                              # Usage instructions
├── MANUAL_BUILD.md                        # Manual build guide
└── MOD_PACKAGE.md                         # This file
```

## Quick Start Guide

### Option 1: Use with Existing Fabric Development Environment

1. **Prerequisites**:
   - Fabric 1.18.2 development environment
   - Java 17 or later
   - Fabric API and Loader installed

2. **Installation**:
   - Copy the entire `mcjava-login-mod` directory to your development environment
   - Run `gradlew build` in the mod directory
   - The built JAR file will be in `build/libs/`

### Option 2: Create New Development Environment

1. **Download Fabric Mod Template**:
   - Go to https://fabricmc.net/develop/template/
   - Select Minecraft 1.18.2
   - Download the template

2. **Replace Files**:
   - Extract the template
   - Delete the existing `src` directory
   - Copy our `src` directory into the template
   - Replace `build.gradle`, `gradle.properties`, and `fabric.mod.json` with our files

3. **Build**:
   - Run `gradlew build`
   - Get the JAR file from `build/libs/`

## Server Installation

1. **Copy JAR File**:
   - Copy the built JAR file to your server's `mods` folder

2. **First Run**:
   - Start the server once to generate configuration files
   - Server will create `config/mcjava-login-mod/passwords.txt`

3. **Configure Passwords**:
   - Stop the server
   - Edit `config/mcjava-login-mod/passwords.txt`
   - Add passwords in format: `playerName:password`
   - Example: `Steve:mypassword123`

4. **Restart Server**:
   - Start the server again
   - Server is now password-protected

## Client Installation

1. **Copy JAR File**:
   - Copy the same JAR file to all clients' `mods` folders

2. **Connect to Server**:
   - Start Minecraft
   - Add the server address
   - Click "Join Server"
   - Enter password when prompted

## Troubleshooting

### Common Issues

1. **Client can't connect**:
   - Ensure both server and client have the mod installed
   - Verify client is using correct Minecraft version (1.18.2)
   - Check that Fabric API is installed on both sides

2. **Password not working**:
   - Verify password format in `passwords.txt`
   - Check for typos in password
   - Ensure server was restarted after password changes

3. **Mod not loading**:
   - Verify JAR file is in correct `mods` folder
   - Check server logs for error messages
   - Ensure Java version is 17 or later

## Security Notes

- Passwords are stored in plain text in `passwords.txt`
- Use strong, unique passwords for each player
- Regularly update passwords
- Backup `passwords.txt` regularly

## Commands

- `/login setpassword <password>` - Set or change your password

## Support

If you encounter any issues:

1. Check the server logs for error messages
2. Verify all installation steps were followed
3. Ensure correct versions of all dependencies

## Final Notes

This mod provides a basic level of security for your Minecraft server. For production servers, consider additional security measures such as:

- Using a more secure password storage method
- Implementing IP whitelisting
- Using a dedicated authentication plugin
- Regularly updating server software

With this package, you should be able to successfully build, install, and use the MCJava Login Mod on your Fabric 1.18.2 server.
