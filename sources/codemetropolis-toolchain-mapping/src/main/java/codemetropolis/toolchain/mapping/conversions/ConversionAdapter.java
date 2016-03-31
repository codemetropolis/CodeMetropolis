package codemetropolis.toolchain.mapping.conversions;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import codemetropolis.toolchain.mapping.model.Parameter;

public class ConversionAdapter extends XmlAdapter<ConversionAdapter.AdaptedConversion, Conversion> {
	
	public static class AdaptedConversion {
		 
        @XmlAttribute
        public String type;
 
        @XmlElement(name="parameter")
    	protected List<Parameter> parameters;
 
    }

	@Override
	public AdaptedConversion marshal(Conversion conversion) throws Exception {
		if(conversion == null) return null;
		AdaptedConversion adaptedConversion = new AdaptedConversion();
		adaptedConversion.type = (String)conversion.getClass().getDeclaredField("NAME").get(null);
		adaptedConversion.parameters = conversion.parameters;
		return adaptedConversion;
	}

	@Override
	public Conversion unmarshal(AdaptedConversion adaptedConversion) throws Exception {
		Conversion conversion = Conversion.createFromName(adaptedConversion.type);
		conversion.parameters = adaptedConversion.parameters;
		return conversion;
	}
	
}
