package codemetropolis.toolchain.rendering.model.building.factory;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.model.Themes;
import codemetropolis.toolchain.rendering.model.building.DecorationFloor;
import codemetropolis.toolchain.rendering.model.building.theme.minimalist.MinimalistDecorationFloor;

public class DecorationFloorFactory {
	
	public static DecorationFloor createDecorationFloor(Buildable buildable, Themes theme) throws BuildingTypeMismatchException {
		switch(theme) {
			case MINIMALIST:
				return new MinimalistDecorationFloor(buildable);
			case BASIC:
			default:
				return new DecorationFloor(buildable);
		}
	}

}
