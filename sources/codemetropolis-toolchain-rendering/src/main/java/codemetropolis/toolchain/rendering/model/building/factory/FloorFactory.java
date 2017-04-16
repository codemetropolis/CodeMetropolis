package codemetropolis.toolchain.rendering.model.building.factory;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.model.Themes;
import codemetropolis.toolchain.rendering.model.building.Floor;
import codemetropolis.toolchain.rendering.model.building.theme.minimalist.MinimalistFloor;

public class FloorFactory {
	
	public static Floor createFloor(Buildable buildable, String theme) throws BuildingTypeMismatchException {
		switch(theme) {
			case Themes.MINIMALIST:
				return new MinimalistFloor(buildable);
			case Themes.BASIC:
			default:
				return new Floor(buildable);
		}
	}
	
}
