package codemetropolis.toolchain.rendering.model.building.factory;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.model.Themes;
import codemetropolis.toolchain.rendering.model.building.Garden;
import codemetropolis.toolchain.rendering.model.building.theme.minimalist.MinimalistGarden;

public class GardenFactory {
	
	public static Garden createGarden(Buildable buildable, String theme) throws BuildingTypeMismatchException {
		switch(theme) {
			case Themes.MINIMALIST:
				return new MinimalistGarden(buildable);
			case Themes.BASIC:
			default:
				return new Garden(buildable);
		}
	}

}
