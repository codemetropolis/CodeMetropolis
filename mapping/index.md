---
layout: page
title: Mapping Tool
excerpt: 
modified: 2014-08-08T19:44:38.564948-04:00
image:
  feature: town_01.png
---

**Usage**: java -jar mapping.jar -i <grapFile> -m <mappingFile> [-o <outputFile>]  

**Command line options:**  
+
* -h: help, shows the usage of the command line tool.  
* -i: input, the path of the input graph file. Required.  
* -o: output, which will be generated to the given path. Default: "mappingToPlacing.xml".  
* -m: mapping, path of the input mapping file. Required.  
* -s: scale. Set the scale of blocks. Default: 1.0  
* -n: nested. Default: false.  

**About the mapping file**  

The mapping file contains the parameters of build up the virtual world from the source code. 

<img src="{{ site.url }}/images/mapping_file_explanation.jpg"/>
 
In the <linking> tag we can set how a program element shoul be displayed in the world. We can set the attribute of it like LOC, NUMPAR or McCabe complexity, choose a representation form of it (for example building), the property of the graphical object, like the height of the building and the type of the transition. Parameters also can be customized. 

