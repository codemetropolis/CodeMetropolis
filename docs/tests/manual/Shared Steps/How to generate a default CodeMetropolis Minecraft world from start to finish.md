# The step by step guide to generate a default CodeMetropolis based Minecraft world

**Steps:**
1. Run the following command in the folder of converter-1.5.0.jar:
	```cmd
	java -jar converter-1.5.0.jar -t sourcemeter -s "[CodeMetropolis project folder path]
	```
	[[Legends and other additions]]
	```cmd
	\examples\mapping\sourcemeter_mapping_example_2_1.xml"
	```
2. Run the following command in the folder of mapping-1.5.0.jar:
	```cmd
	java -jar mapping-1.5.0.jar -i converterToMapping.xml -m "[CodeMetropolis project folder path]
	```
	[[Legends and other additions]]
	```cmd
	\examples\mapping\codemetropolis-toolchain-rendering.graph"
	```
3. Run the following command in the folder of placing-1.5.0.jar:
	```cmd
	java -jar placing-1.5.0.jar -i mappingToPlacing.xml
	```
4. Run the following command in the folder of rendering-1.5.0.jar:
	```cmd
	java -jar rendering-1.5.0.jar -i placingToRendering.xml -world world
	```