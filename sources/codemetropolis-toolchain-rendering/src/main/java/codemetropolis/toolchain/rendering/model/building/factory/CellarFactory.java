package codemetropolis.toolchain.rendering.model.building.factory;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.model.Themes;
import codemetropolis.toolchain.rendering.model.building.Cellar;
import codemetropolis.toolchain.rendering.model.building.theme.minimalist.MinimalistCellar;

public class CellarFactory {

	public static Cellar createCellar(Buildable buildable, String theme) throws BuildingTypeMismatchException {
		switch(theme) {
			case Themes.MINIMALIST:
				return new MinimalistCellar(buildable);
			case Themes.BASIC:
			default:
				return new Cellar(buildable);
		}
	}
	
}
