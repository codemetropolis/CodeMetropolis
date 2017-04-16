package codemetropolis.toolchain.rendering.model.building.factory;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.model.Themes;
import codemetropolis.toolchain.rendering.model.building.Floor;
import codemetropolis.toolchain.rendering.model.building.theme.minimalist.MinimalistFloor;

public class FloorFactory {
	
	public static Floor createFloor(Buildable buildable, Themes theme) throws BuildingTypeMismatchException {
		switch(theme) {
			case MINIMALIST:
				return new MinimalistFloor(buildable);
			case BASIC:
			default:
				return new Floor(buildable);
		}
	}
	
}
