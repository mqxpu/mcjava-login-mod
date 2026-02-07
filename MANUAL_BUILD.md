# Manual Build Instructions

Since we don't have a complete Gradle environment set up, here's how to manually build the mod:

## Method 1: Use an Existing Fabric Development Environment

1. **Prerequisites**:
   - A working Fabric 1.18.2 development environment
   - Java 17 or later
   - Gradle

2. **Steps**:
   - Copy the entire `mcjava-login-mod` directory to your Fabric development environment
   - Run `gradlew build` in the mod directory
   - The built JAR file will be in `build/libs/`

## Method 2: Use Fabric Mod Template

1. **Download Fabric Mod Template**:
   - Go to https://fabricmc.net/develop/template/
   - Select Minecraft 1.18.2
   - Download the template

2. **Replace Files**:
   - Replace the `src` directory with our `src` directory
   - Replace `build.gradle`, `gradle.properties`, and `fabric.mod.json` with our files

3. **Build**:
   - Run `gradlew build`
   - Get the JAR file from `build/libs/`

## Method 3: Manual JAR Creation (Advanced)

1. **Compile Java Files**:
   ```bash
   javac -cp "path/to/fabric-api.jar:path/to/minecraft.jar:path/to/fabric-loader.jar" -d bin src/main/java/com/example/loginmod/*.java src/main/java/com/example/loginmod/client/*.java
   ```

2. **Create JAR File**:
   ```bash
   jar cf mcjava-login-mod.jar -C bin . -C src/main/resources .
   ```

## Method 4: Use Online Build Service

1. **Create GitHub Repository**:
   - Create a new GitHub repository
   - Upload all files from `mcjava-login-mod` directory

2. **Use GitHub Actions**:
   - Create a GitHub Action workflow file for Fabric mod building
   - Trigger the workflow to build the mod
   - Download the built JAR from the workflow artifacts

## Files Included

The mod includes the following key files:

- `src/main/java/com/example/loginmod/LoginMod.java` - Main server mod class
- `src/main/java/com/example/loginmod/client/LoginModClient.java` - Main client mod class
- `src/main/java/com/example/loginmod/LoginManager.java` - Password management
- `src/main/java/com/example/loginmod/LoginCommand.java` - Command handling
- `src/main/java/com/example/loginmod/PlayerActionListener.java` - Action restriction
- `src/main/resources/fabric.mod.json` - Mod configuration
- `build.gradle` - Build configuration
- `gradle.properties` - Version configuration

## Installation

Once you have the JAR file:

1. Copy it to both server and client `mods` folders
2. Start the server to generate `config/mcjava-login-mod/passwords.txt`
3. Edit the passwords file with `playerName:password` entries
4. Restart the server
5. Clients can now connect by entering their password
