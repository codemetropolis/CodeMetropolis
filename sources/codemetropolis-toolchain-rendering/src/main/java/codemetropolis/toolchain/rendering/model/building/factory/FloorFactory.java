package codemetropolis.toolchain.rendering.model.building.factory;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.model.Themes;
import codemetropolis.toolchain.rendering.model.building.Floor;
import codemetropolis.toolchain.rendering.model.building.theme.minimalist.MinimalistFloor;
import codemetropolis.toolchain.rendering.model.building.theme.railway.RailwayFloor;
import codemetropolis.toolchain.rendering.model.building.theme.town.TownFloor;

/**
 * Create the proper {@link Floor} subclass depending on the {@link Themes}.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class FloorFactory {

	/**
	 * Depending on the {@link Themes} parameter create a proper {@link Floor} subclass.
	 * 
	 * @param buildable A {@link Buildable} object to be used for instantiation.
	 * @param theme The requested style, defined as {@link Themes}.
	 * @return The proper {@link Floor} subclass.
	 * @throws BuildingTypeMismatchException If type of {@code buildable} is incorrect.
	 */
	public static Floor createFloor(Buildable buildable, Themes theme) throws BuildingTypeMismatchException {
		switch (theme) {
			case MINIMALIST:
				return new MinimalistFloor(buildable);
			case RAILWAY:
				return new RailwayFloor(buildable);
			case TOWN:
				return new TownFloor(buildable);
			case BASIC:
			default:
				return new Floor(buildable);
		}
	}

}
