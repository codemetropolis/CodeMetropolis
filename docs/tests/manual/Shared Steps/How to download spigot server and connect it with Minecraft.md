# The step by step guide to install Spigot server and start playing Minecraft with a Spigot plugin

1. **Download the Spigot server:**
   - Visit the SpigotMC website's download page at `https://www.spigotmc.org/wiki/buildtools/#latest`.
   - Download the latest version of the BuildTools.jar file.
   - Place the downloaded BuildTools.jar file into an empty directory.

2. **Build the Spigot server:**
   - Open a command prompt or terminal window.
   - Navigate to the directory where you placed the BuildTools.jar file.
   - Run the following command to build the Spigot server:
     ```bash
     java -jar BuildTools.jar
     ```
   - Wait for the process to complete. This will create a Spigot server jar file in the same directory.

3. **Set up the Spigot server:**
   - Move the Spigot server jar file to a new directory where you want your server to be located.
   - Create a new text file in the same directory. Name it `start.bat` on Windows or `start.sh` on Unix-based systems.
   - Open the file and add the following line:
     ```bash
     java -Xms1G -Xmx1G -XX:+UseConcMarkSweepGC -jar spigot-<version>.jar
     ```
   Replace `<version>` with the version of your Spigot server jar file.
   - Save and close the file.

4. **Start the Spigot server:**
   - Double-click the `start.bat` or `start.sh` file to start the server.
   - The first time you run the server, it will create some configuration files and then stop.

5. **Agree to the EULA:**
   - Open the `eula.txt` file that was created in the server directory.
   - Change `eula=false` to `eula=true` to agree to the EULA.
   - Save and close the file.

6. **Install the plugin:**
   - Open CodeMetropolis project in an IDE. 
   - Run the following command:
	 ```bash
     mvn clean package
     ```
   - Get the plugin's .jar file from `CodeMetropolis\sources\integration\spigot-plugin\target`.
   - Place the plugin's .jar file into the 'plugins' directory of your Spigot server.

8. **Start the Spigot server again:**
   - Double-click the `start.bat` or `start.sh` file to start the server.

9. **Connect to the server using the Minecraft client:**
   - Open the Minecraft client.
   - Click on "Multiplayer".
   - Click on "Add Server".
   - In the "Server Address" field, enter `localhost`.
   - Click "Done".
   - You should now see your server in the list. Click on it and then click "Join Server" to connect.

*Disclaimer: Please note that this guide assumes you have Java installed on your computer. If you don't, you'll need to install it first (Java 22). Also, the server will be accessible only on your local machine. If you want to make it accessible to others, you'll need to set up port forwarding on your router, which is beyond the scope of this guide.*