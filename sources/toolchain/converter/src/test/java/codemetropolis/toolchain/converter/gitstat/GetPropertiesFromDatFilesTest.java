package codemetropolis.toolchain.converter.gitstat;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import codemetropolis.toolchain.commons.cdf.CdfProperty;

class GetPropertiesFromDatFilesTest {
	List<CdfProperty> properties;
	

	@Test
	void testGetPropertiesFromDatFiles() {
		CdfProperty elso = new CdfProperty(".\\src\\test\\java\\codemetropolis\\toolchain\\converter\\gitstat\\test_resource\\TestDatParser\\a.dat elso", "34",
				CdfProperty.Type.INT);
		CdfProperty masodik = new CdfProperty(".\\src\\test\\java\\codemetropolis\\toolchain\\converter\\gitstat\\test_resource\\TestDatParser\\a.dat masodik", "56",
				CdfProperty.Type.INT);
		CdfProperty harmadik = new CdfProperty(".\\src\\test\\java\\codemetropolis\\toolchain\\converter\\gitstat\\test_resource\\TestDatParser\\b.dat harmadik",
				"65", CdfProperty.Type.INT);
		CdfProperty negyedik = new CdfProperty(".\\src\\test\\java\\codemetropolis\\toolchain\\converter\\gitstat\\test_resource\\TestDatParser\\b.dat negyedik",
				"18", CdfProperty.Type.INT);

		List<CdfProperty> guardProperties = new ArrayList<CdfProperty>();

		guardProperties.add(elso);
		guardProperties.add(masodik);
		guardProperties.add(harmadik);
		guardProperties.add(negyedik);

		properties = GitStatDatParser.getPropertiesFromDatFiles(".\\src\\test\\java\\codemetropolis\\toolchain\\converter\\gitstat\\test_resource\\TestDatParser");

		assertNotEquals(null, properties);
		for (CdfProperty propi : properties) {
			
		
			
			assertEquals(propi.getName(), guardProperties.get(properties.indexOf(propi)).getName());
			assertEquals(propi.getValue(), guardProperties.get(properties.indexOf(propi)).getValue());
		}

	}

}