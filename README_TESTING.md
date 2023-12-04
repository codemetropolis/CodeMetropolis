# CodeMetropolis Test Phase

## Getting started

### Setup for SourceMeter analysis

1. Download SourceMeter from its [webpage](https://sourcemeter.com/download).
2. Extract SourceMeter.
3. Download Minecraft Java version from its [webpage](https://www.minecraft.net/en-us/download).
4. Install Minecraft.
5. Optionally you could use the Minecraft starter to change the version of Minecraft to 1.8 (the officialy supported one), but newer versions could also open CodeMetropolis generated worlds.
6. Download the current [stable releas](https://github.com/codemetropolis/CodeMetropolis/releases/tag/latest) of CodeMetropolis.
7. Extract CodeMetropolis

### Visualize source code meterics

1. CodeMetropolis is able to visualize the static analysis results from SourceMeter. Currently Java is the most widely supported language. So to start, you have to locate a Java system, we will call this as target system.
2. Analyze the target system with SourceMeter. You could use any settings as long as it will produce a `graph` file. This graph file contains all the necessary data for the CodeMetropolis.
3. We will use the command line tools to create the visualization.
    1. Convert the data represented in the graph file into a more generic representations. Use the `converter` tool, with the sourcemeter conversion type option. It will produce an XML file which we call `CDF` file (Common Data Format)
    2. Create a `mapping XML` file to define the mapping between the properties of the source code items and the graphical attributes of CodeMetropolis. There is several sample files in this repository.
    3. Execute the `mapping` tool with the CDF file and the mapping XML to produce the first (yet incomplete) representation of the city. It will stored as an XML file we called `IXML` (Intermediate XML).
    4. The output of the `mapping` tool does not contain the placement and the size of the buildings. These data hava to be computed with the `placing` tool.
    5. In the final step we need to use the `rendering` tool to convert the final IXML file into a Minecraft world.
4. Installing the new visualization
    1. Start and close your Minecraft client (play the game) at least once to generate the necessary folder structure.
    2. Copy the generated world into the location of your saved worlds. On Windwos it is usually located under `C:\Users\<windows username>\AppData\Roaming\.minecraft\saves`. You have to copy the whole folder not just its content.
    3. Start the game and select the world from the list.

## Complie from source

1. clone repository
1. checkout `develop` branch
1. install Java 1.8
1. install Maven 3.8.3 or newer
1. navigate to `sources` folder
1. `mvn clean package`
1. The current distribution will be aviable under `source/distro`.
