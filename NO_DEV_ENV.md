# How to Use Without Development Environment

If you don't have a Fabric development environment set up, here's a simple way to get the mod working:

## Option 1: Use Online Fabric Mod Builder

1. **Go to Online Builder**:
   - Visit https://modrinth.com/mod/fabric-mod-template/generator
   - This is a web-based tool that can build Fabric mods without needing to set up a local environment

2. **Configure the Builder**:
   - Select Minecraft version: 1.18.2
   - Enter mod details:
     - Mod ID: `mcjava-login-mod`
     - Mod Name: `MCJava Login Mod`
     - Mod Version: `1.0.0`

3. **Upload Files**:
   - Replace the generated `src` directory with our `src` directory
   - Replace the generated `fabric.mod.json` with our `fabric.mod.json`
   - Replace the generated `build.gradle` with our `build.gradle`
   - Replace the generated `gradle.properties` with our `gradle.properties`

4. **Build the Mod**:
   - Click the build button
   - Download the generated JAR file

## Option 2: Use GitHub Actions

1. **Create GitHub Account**:
   - If you don't have one, create a free GitHub account at https://github.com

2. **Create New Repository**:
   - Click "New Repository"
   - Name it `mcjava-login-mod`
   - Make it public
   - Initialize with README

3. **Upload Files**:
   - Click "Add file" > "Upload files"
   - Upload all files from our `mcjava-login-mod` directory
   - Include:
     - All files in `src/` directory
     - `build.gradle`
     - `gradle.properties`
     - `fabric.mod.json`
     - `gradle/` directory
     - `gradlew.bat`

4. **Create GitHub Action**:
   - Click "Actions" tab
   - Click "New workflow"
   - Search for "Fabric mod"
   - Select "Fabric Mod Build"
   - Click "Set up this workflow"
   - Click "Start commit" > "Commit new file"

5. **Run the Action**:
   - The action should start automatically
   - Wait for it to complete (about 5-10 minutes)
   - Click on the completed run
   - Download the built JAR from "Artifacts"

## Option 3: Use Pre-Built Mod (Recommended)

If you don't want to go through the building process, you can:

1. **Find a Pre-Built Version**:
   - Search for "MCJava Login Mod 1.18.2" on mod hosting sites
   - Ensure it's from a trusted source

2. **Or Ask for Help**:
   - Post in Fabric modding communities
   - Ask someone with a development environment to build it for you
   - Provide a link to this repository

## Option 4: Manual Installation (Advanced)

If you're comfortable with basic file operations:

1. **Create Mod Structure**:
   - Create a new folder called `mcjava-login-mod`
   - Inside it, create `src/main/java/com/example/loginmod/` and `src/main/java/com/example/loginmod/client/` directories
   - Create `src/main/resources/` directory

2. **Copy Files**:
   - Copy all Java files to their respective directories
   - Copy `fabric.mod.json` to `src/main/resources/`
   - Copy `build.gradle` and `gradle.properties` to the root

3. **Use Build Service**:
   - Upload this structure to a service like https://github.com
   - Use GitHub Actions to build it (as described in Option 2)

## Once You Have the JAR File

### Server Setup:
1. Copy the JAR to your server's `mods` folder
2. Start the server once to generate `config/mcjava-login-mod/passwords.txt`
3. Stop the server, edit the passwords file
4. Restart the server

### Client Setup:
1. Copy the same JAR to your client's `mods` folder
2. Start Minecraft and connect to the server
3. Enter your password when prompted

## Troubleshooting

### If You Can't Get the JAR File:
- Check if there are any friends or community members who can help build it
- Consider using a different password protection method temporarily
- Look for existing login mods on mod platforms that might suit your needs

### Alternative Password Protection Methods:
1. **Use Whitelist**:
   - Enable server whitelist in `server.properties`
   - Add only trusted players

2. **Use IP Ban**:
   - Configure firewall to only allow specific IPs
   - This is more technical but very secure

3. **Use Existing Mods**:
   - Search for "Minecraft server login mod" on CurseForge or Modrinth
   - Many existing mods offer similar functionality

## Final Notes

While setting up without a development environment is possible, it does require some technical steps. If you're not comfortable with these methods, the easiest approach is to find someone with a development environment who can build the mod for you, or use an existing password protection solution.

The key benefit of this mod is that it requires password authentication before connection, which provides an extra layer of security compared to traditional whitelisting methods.
