package codemetropolis.toolchain.rendering.model.building.factory;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.model.Themes;
import codemetropolis.toolchain.rendering.model.building.Cellar;
import codemetropolis.toolchain.rendering.model.building.theme.minimalist.MinimalistCellar;

public class CellarFactory {

	public static Cellar createCellar(Buildable buildable, Themes theme) throws BuildingTypeMismatchException {
		switch(theme) {
			case MINIMALIST:
				return new MinimalistCellar(buildable);
			case BASIC:
			default:
				return new Cellar(buildable);
		}
	}
	
}
