@echo off
echo CodeMetropolis example

echo Processing SourceMeter graph
java -jar CodeMetropolis_1.4-bin\codemetropolis-toolchain-converter-1.4.0.jar -t sourcemeter -i input\example.graph

echo Processing Map file
java -jar CodeMetropolis_1.4-bin\codemetropolis-toolchain-mapping-1.4.0.jar -i converterToMapping.xml -m examples\sourcemeter_mapping_example.xml

echo Placing
java -jar CodeMetropolis_1.4-bin\codemetropolis-toolchain-placing-1.4.0.jar -i mappingToPlacing.xml

echo Rendering
java -jar CodeMetropolis_1.4-bin\codemetropolis-toolchain-rendering-1.4.0.jar -i placingToRendering.xml -w worlds\demo

echo done.
