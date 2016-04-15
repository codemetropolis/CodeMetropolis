package codemetropolis.toolchain.commons.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class XmlStreamPrettyPrinter implements InvocationHandler {
	
	private final XMLStreamWriter target;
	private int depth = 0;
	private final Map<Integer, Boolean> hasChildElement = new HashMap<Integer, Boolean>();
	private static final String INDENT_CHAR = "\t";
	private static final String LINEFEED_CHAR = "\n";

	private XmlStreamPrettyPrinter(XMLStreamWriter target) {
		this.target = target;
	}
	
	public static XMLStreamWriter create(XMLStreamWriter writer) throws XMLStreamException {
		XmlStreamPrettyPrinter prettyPrinter = new XmlStreamPrettyPrinter(writer);
		return (XMLStreamWriter) Proxy.newProxyInstance(
				XMLStreamWriter.class.getClassLoader(),
				new Class[]{XMLStreamWriter.class},
				prettyPrinter );
	}
	  
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		
		switch(method.getName()) {
			case "writeStartElement":
				if (depth > 0) hasChildElement.put(depth - 1, true);
	    		hasChildElement.put(depth, false);
	    		target.writeCharacters(LINEFEED_CHAR);
	    		target.writeCharacters(repeat(depth, INDENT_CHAR));
	    		depth++;
	    		break;
	    	case "writeEndElement":
	    		depth--;
	    		if (hasChildElement.get(depth) == true) {
	    			target.writeCharacters(LINEFEED_CHAR);
	    			target.writeCharacters(repeat(depth, INDENT_CHAR));
	    		}
	    		break;
	    	case "writeEmptyElement":
	    		if (depth > 0) hasChildElement.put(depth - 1, true);
	    		target.writeCharacters(LINEFEED_CHAR);
	    		target.writeCharacters(repeat(depth, INDENT_CHAR));
	    		break;
	    }
	    
		method.invoke(target, args);
		return null;
	}
	
	private String repeat(int n, String str) {
		StringBuilder builder = new StringBuilder();
		while (n-- > 0) builder.append(str);
		return builder.toString();
	}

}
