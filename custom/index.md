---
layout: page
title: Custom CDF Converter
---

# Custom CDF Converter Tutorial

Converters are responsibly for turning data into a CDF (Common Data Format) file which can be used as the input of the mapping component. CDF uses a simple XML-based syntax as it is described here. Fortunately, the CodeMetropolis toolchain already provides a few classes which help developers to deal with CDF files. I will talk about these soon.

Basically, there are two ways to implement a converter: to expand the codemetropolis-toolchain-converter project, or to create your own separated converter project. This tutorial will guide you through the first method as it is the recommended way if you are willing to share your converter with the community.

The first thing you have to do is to checkout the CodeMetropolis Git repository and load the Maven projects into Eclipse. For more information on this, please read the contribution guide. You need to add a new package to the codemetropolis-toolchain-converter project. Its name should be `codemetropolis.toolchain.converter.<name>` by convention where <name> is the name of the converter containing only lowercase letters of English alphabet. Please stick to our naming conventions if you are planning to (contrib link!) send a patch to us. I am going to call mine `MyCustomConverter` for the rest of this tutorial so in this case our package is 
`codemetropolis.toolchain.converter.mycustom`.

Now it’s time to create the main component of the converter. Create a new class, name it MyCustomConverter and place it in the package you’ve just created. Make this class extend the `codemetropolis.toolchain.commons.cdf.converter.CdfConverter`. You also have to override an abstract method:

~~~ java
@Override
public CdfTree createElements(String source) {
	//implement conversion logic here
}
~~~


This method is used to construct the structure of the output CDF document. The `source` parameter holds the path to the source of the data you want to convert and is passed as command line argument (or executor argument when using the CodeMetropolis API). This could be for example a file or a server, but you are free to use any kind of data source. The implementation should load the data from the source, create the CDF elements and place them in a tree structure. The method must return a CdfTree object which holds the (no surprise) CDF tree. All CDF related classes are placed in package `codemetropolis.toolchain.commons.cdf`. Since we will need all of them, we can import them like: 

~~~ java
import codemetropolis.toolchain.commons.cdf.*;
~~~

The data conversion logic fully depends on the data source and your own purposes, therefore I will only explain the creation of the CDF structure. To compose the `CdfTree`, first you have to transform your data into `CdfElement` objects. A `CdfElement` represents a data object with a name, a type, and a bunch of properties. All elements in a CdfTree must connect to each other through parent-child relations. There must be a single root element with no parent. Every other element must have exactly one parent and any number of children. You can construct an element as follows:

~~~ java
CdfElement element = new CdfElement("name", "type");
element.addProperty("property_1", "value", CdfProperty.Type.STRING);
element.addProperty("property_2", "10", CdfProperty.Type.INT);
element.addProperty("property_3", "3.14", CdfProperty.Type.FLOAT);
element.addChildElement(otherElement);
~~~

When you finished creating your `CdfElement` objects you have to place them in a `CdfTree`. To achieve this, you only need call the constructor of the `CdfTree` class passing the root `CdfElement` as an argument. Then you can simply return the newly created object.

~~~ java
return new CdfTree(rootElement);
~~~

The conversion might need some custom parameters coming for command line arguments. You can access these with the `getParameter("parameter_id")` method. In command line the syntax of custom parameters are the following:

`-p parameter1=value1 parameter2=value2 […]`

During the conversion process you might also want to print some information to the output stream (console when using with command line). You can do that by calling `fireConverterEvent("your message")`.

By now, you have already implemented the data conversion logic. The last thing to do is to register your converter so it becomes accessible as a valid converter type. Look up the `codemetropolis.toolchain.converter.control`  package and open the `ConverterType` enum for editing and add name of your converter to the list:

Now open the `ConverterLoader` class and add a new case statement to the load method:

That’s all. Now you can use your converter just like the default converters:

`java -jar converter.jar -t mycustom -s <source> -o <output_xml>`
