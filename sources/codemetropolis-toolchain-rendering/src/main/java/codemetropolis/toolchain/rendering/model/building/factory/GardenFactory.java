package codemetropolis.toolchain.rendering.model.building.factory;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.model.Themes;
import codemetropolis.toolchain.rendering.model.building.Garden;
import codemetropolis.toolchain.rendering.model.building.theme.minimalist.MinimalistGarden;
import codemetropolis.toolchain.rendering.model.building.theme.railway.RailwayGarden;

/**
 * Create the proper {@link Garden} subclass depending on the {@link Themes}.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class GardenFactory {
	
	/**
	 * Depending on the {@link Themes} parameter create a proper {@link Garden} subclass.
	 * 
	 * @param buildable A {@link Buildable} object to be used for instantiation.
	 * @param theme The requested style, defined as {@link Themes}.
	 * @return The proper {@link Garden} subclass.
	 * @throws BuildingTypeMismatchException If type of {@code buildable} is incorrect.
	 */	
	public static Garden createGarden(Buildable buildable, Themes theme) throws BuildingTypeMismatchException {
		switch(theme) {
			case MINIMALIST:
				return new MinimalistGarden(buildable);
			case RAILWAY:
				return new RailwayGarden(buildable);
			case BASIC:
			default:
				return new Garden(buildable);
		}
	}

}
