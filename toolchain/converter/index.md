---
layout: page
title: CDF Converter Tool
excerpt: 
modified: 2014-08-08T19:44:38.564948-04:00
image:
  feature: for_converter.png
---

This application is used to process the graph file generated by [SourceMeter][sm], which creates properties for each item, for exmaple functions that will be processed later by the Mapping Tool. 

**Usage**: `java -jar converter.jar -i <graph file> [-o <output.xml>] [-t <tpye>] [-p <parameter>]`

* `-i`: input, the path of the input graph file. Required. 
* `-o`: output, the path of the output XML file. Optional. 
* `-t`: type, the type of the conversion. In the current version it can be ‘sourcemeter’ or ‘sonarqube’.
* `-p`: parameter, a string value, for example: -p projects=project1;project2.

**About the output XML**

The output XML contains the name and the type of the elements and the properties of them. 

~~~ xml
<element name="void main(String[] args)" type="method">
    <children/>
       <properties>
           <property name="sourceid" type="string" value="L118"/>
           <property name="Name" type="string" value="void main(String[] args)"/>
           <property name="LongName" type="string" value="URLExpSimple.main([Ljava/lang/String;)V"/>
           <property name="CLLC" type="float" value="0.0"/>
           <property name="CC" type="float" value="0.0"/>
           <property name="CI" type="int" value="0"/>
           <property name="CCL" type="int" value="0"/>
           <property name="CCO" type="int" value="0"/>
           <property name="LLDC" type="float" value="0.0"/>
~~~

In the represented XML source a method type element called "void main(String[] args)" with several properties are shown. Every property has a name, a type and a value. Type can be string, float or int. 


[sm]: <https://www.sourcemeter.com/>