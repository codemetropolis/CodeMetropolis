---
layout: page
title: Inter tool XML format
excerpt: 
modified: 2014-08-08T19:44:38.564948-04:00
image:
  feature: inside_the_city_of_glass.png
---

CodeMetropolis tools are using XML files to communicate with each other. Mapping Tool generates an output XML file which is used by placing tool as input. Similarly, the Placing Tool creates an XML for Rendering Tool.
These XML files are using the same format defined in an XML Schema.
Here is an exmaple from a placing output XML:     

~~~ xml
<children>
    <buildable id="L168" name="WordCount" type="garden">
        <position x="9" y="62" z="70"/>
        <size x="40" y="139" z="25"/>
        <attributes>
              <attribute name="flower-ratio" value="0.0"/>
        </attributes>
        <children>
            <buildable id="L237" name="void performExp()" type="floor">
                 <position x="16" y="128" z="77"/>
                 <size x="9" y="17" z="11"/>
                 <attributes>
                         <attribute name="external_character" value="metal"/>
                         <attribute name="character" value="glass"/>
                         <attribute name="torches" value="1"/>
                 </attributes>
~~~   
 
`Buildable` tags are representing objects of the city, which are located in hierarchical structure. These objects have some parameters with specific meaning:

+ **id:** identifies buildable, for example "L176".
+ **name:** name of the buildable, it can be a method, a function etc.
+ **type:** buildable type can be ground, garden, floor or cellar.
+ **position:** position of buildable by x, y and z coordinates.
+ **size:** size of buildable by x, y and z length. Size can be only a positive integer number.
+ **attributes:** all additional attributes like the material the object is made of. It has a name and a value parameter, for example name="character", value="glass".
+ **children:** to represent the hierarchy, some buildables can contain additional buildables. That means the "child" object is part of the "parent". Grounds can contain grounds or houses and houses can contain floors or cellars. No other relation is allowed.

Mapping to Placing XML contains only the structure of the city, the attributes of it and the size of inner buildings like floor or cellar, while Placing to Rendering XML defines the position and the size of all buildings. 

During placing phase, the position of simple elements (which does not contain any other element) are set and the size of the compound elements are calculated and propagated up along the containment relation. Of course the position of the compound elements are also updated. For example in a garden place (set the position of) two floors in the two opposite corner, then the garden should scaled up (set the size) to be able to contain these floors.

These XMLs are built up on the same [XML schema][XML]. 

[XML]: <https://github.com/geryxyz/CodeMetropolis/blob/master/sources/codemetropolis-toolchain-commons/src/main/resources/cmxml_scheme.xsd>





