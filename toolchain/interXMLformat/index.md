---
layout: page
title: Inter tool XML format
excerpt: 
modified: 2014-08-08T19:44:38.564948-04:00
image:
  feature: inside_the_city_of_glass.png
---

CodeMetropolis tools are using XML files to communicate with each other. Mapping Tool generates an output XML file which is used by placing tool as input. Similarly, the Placing Tool generates an XML for Rendering Tool.
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
 
A buildable is representing an object of the city in hierarchical structure. These objects have some parameters with specific meaning:

+ **id:** identifies buildable, for example "L176".
+ **name:** name of the buildable, it can be a method, a function etc.
+ **type:** buildable type can be ground, garden, floor or cellar.
+ **position:** position of buildable by x, y and z coordinates.
+ **size:** size of buildable by x, y and z length. Size can be only a positive integer number.
+ **attributes:** all additional attributes like the material the object is made of. It has a name and a value parameter, for example name="character", value="glass".
+ **children:** to represent the hierarchy, some buildables can contain additional buildables. That means the "child" object is part of the "parent". Grounds can contain grounds or houses and houses can contain floors or cellars. No other relation is allowed.

Mapping to Placing XML contains only the structure of the city, the attributes of it and the size of inner buildings like floor or cellar, while Placing to Rendering XML defines the position and the size of all buildings. 

As you can see on the following example, only position differs in these two XMLs. 

<img src="{{ site.url }}/images/difference_between_MtP_PtR.png"/>

These XMLs are built up on an [XML schema][XML]. 

[XML]: <https://github.com/geryxyz/CodeMetropolis/blob/master/sources/codemetropolis-toolchain-commons/src/main/resources/cmxml_scheme.xsd>





