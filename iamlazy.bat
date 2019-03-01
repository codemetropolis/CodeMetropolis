if not exist "jars\lib" mkdir "jars\lib"

copy /y "sources\codemetropolis-toolchain-converter\target\codemetropolis-toolchain-converter-1.4.0.jar" "jars\converter.jar"
copy /y "sources\codemetropolis-toolchain-converter\target\lib\*.jar" "jars\lib"

copy /y "sources\codemetropolis-toolchain-mapping\target\codemetropolis-toolchain-mapping-1.4.0.jar" "jars\mapping.jar"
copy /y "sources\codemetropolis-toolchain-mapping\target\lib\*.jar" "jars\lib"

copy /y "sources\codemetropolis-toolchain-placing\target\codemetropolis-toolchain-placing-1.4.0.jar" "jars\placing.jar"
copy /y "sources\codemetropolis-toolchain-placing\target\lib\*.jar" "jars\lib"


copy /y "sources\codemetropolis-toolchain-rendering\target\codemetropolis-toolchain-rendering-1.4.0.jar" "jars\rendering.jar"
copy /y "sources\codemetropolis-toolchain-rendering\target\lib\*.jar" "jars\lib"