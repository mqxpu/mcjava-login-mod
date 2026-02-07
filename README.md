# MCJava Login Mod

A login mod for Minecraft Java Edition 1.18.2 Fabric servers that requires password authentication **before connecting** to the server. Players with incorrect passwords will be denied connection immediately.

## Features

- **Pre-Connection Authentication**: Players must enter a password before connecting to the server
- **Immediate Rejection**: Incorrect passwords result in immediate connection denial
- **Client-Side Integration**: Requires the mod to be installed on both server and client
- **Password Management**: Players can set their own passwords using commands
- **Persistent Storage**: Passwords are saved to a file and loaded on server restart

## Commands

- `/login setpassword <password>` - Set or change your password

## Installation

1. **Prerequisites**:
   - Minecraft 1.18.2
   - Fabric Loader
   - Fabric API

2. **Installation Steps**:
   - Download the mod JAR file
   - Place it in **both** the server's `mods` folder **and** all clients' `mods` folders
   - Start the server
   - The mod will create a `config/mcjava-login-mod/passwords.txt` file to store passwords

3. **First Time Setup**:
   - Start the server with the mod installed
   - Edit the `config/mcjava-login-mod/passwords.txt` file manually to add player passwords in the format `playerName:password`
   - Save the file and restart the server

4. **Connecting to the Server**:
   - When attempting to connect, a password input screen will appear
   - Enter the correct password to join the server
   - Incorrect passwords will result in immediate connection rejection

## Security Notes

- Passwords are stored in plain text in the passwords.txt file
- For production servers, consider using a more secure password storage method
- Regularly backup the passwords.txt file
- Use strong passwords and encourage players to do the same

## Configuration

The mod automatically creates a configuration directory at `config/mcjava-login-mod/` where it stores the passwords.txt file.

## Building From Source

1. Clone the repository
2. Run `gradlew build` to build the mod
3. The built JAR file will be in the `build/libs` directory

## License

This mod is licensed under the MIT License.
