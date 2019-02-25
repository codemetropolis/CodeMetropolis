package codemetropolis.toolchain.rendering.model.building.factory;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.model.Themes;
import codemetropolis.toolchain.rendering.model.building.DecorationFloor;
import codemetropolis.toolchain.rendering.model.building.theme.minimalist.MinimalistDecorationFloor;
import codemetropolis.toolchain.rendering.model.building.theme.railway.RailwayDecorationFloor;
import codemetropolis.toolchain.rendering.model.building.theme.town.TownDecorationFloor;

/**
 * Create the proper {@link DecorationFloor} subclass depending on the {@link Themes}.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class DecorationFloorFactory {

	/**
	 * Depending on the {@link Themes} parameter create a proper {@link DecorationFloor} subclass.
	 * 
	 * @param buildable A {@link Buildable} object to be used for instantiation.
	 * @param theme The requested style, defined as {@link Themes}.
	 * @return The proper {@link DecorationFloor} subclass.
	 * @throws BuildingTypeMismatchException If type of {@code buildable} is incorrect.
	 */
	public static DecorationFloor createDecorationFloor(Buildable buildable, Themes theme)
		throws BuildingTypeMismatchException {

		switch (theme) {
			case MINIMALIST:
				return new MinimalistDecorationFloor(buildable);
			case RAILWAY:
				return new RailwayDecorationFloor(buildable);
			case TOWN:
				return new TownDecorationFloor(buildable);
			case BASIC:
			default:
				return new DecorationFloor(buildable);
		}
	}

}
