SET PATH=D:\jdk1.8.0_311\bin;%PATH%

java -jar .\converter-1.4.0.jar -t sourcemeter -s .\log4j-1.2.17.graph
java -jar .\mapping-1.4.0.jar -i .\converterToMapping.xml -m ..\..\examples\mapping\sourcemeter_mapping_example_2_0.xml
java -jar .\placing-1.4.0.jar -i .\mappingToPlacing.xml 
java -jar .\rendering-1.4.0.jar -i .\placingToRendering.xml -world .\world\

:: This is a comment
:: Comment out any step you would like to skip