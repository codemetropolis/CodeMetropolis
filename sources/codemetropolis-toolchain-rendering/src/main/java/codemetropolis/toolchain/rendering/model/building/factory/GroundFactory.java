package codemetropolis.toolchain.rendering.model.building.factory;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.model.Themes;
import codemetropolis.toolchain.rendering.model.building.Ground;
import codemetropolis.toolchain.rendering.model.building.theme.minimalist.MinimalistGround;

public class GroundFactory {

	public static Ground createGround(Buildable buildable, String theme) throws BuildingTypeMismatchException {
		switch(theme) {
			case Themes.MINIMALIST:
				return new MinimalistGround(buildable);
			case Themes.BASIC:
			default:
				return new Ground(buildable);
		}
	}
	
}
