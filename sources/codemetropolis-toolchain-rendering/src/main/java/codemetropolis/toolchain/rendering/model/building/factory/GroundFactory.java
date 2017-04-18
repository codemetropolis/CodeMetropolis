package codemetropolis.toolchain.rendering.model.building.factory;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.model.Themes;
import codemetropolis.toolchain.rendering.model.building.Ground;
import codemetropolis.toolchain.rendering.model.building.theme.minimalist.MinimalistGround;
import codemetropolis.toolchain.rendering.model.building.theme.railway.RailwayGround;

/**
 * Create the proper {@link Ground} subclass depending on the {@link Themes}.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class GroundFactory {
	
	/**
	 * Depending on the {@link Themes} parameter create a proper {@link Ground} subclass.
	 * 
	 * @param buildable A {@link Buildable} object to be used for instantiation.
	 * @param theme The requested style, defined as {@link Themes}.
	 * @return The proper {@link Ground} subclass.
	 * @throws BuildingTypeMismatchException If type of {@code buildable} is incorrect.
	 */
	public static Ground createGround(Buildable buildable, Themes theme) throws BuildingTypeMismatchException {
		switch(theme) {
			case MINIMALIST:
				return new MinimalistGround(buildable);
			case RAILWAY:
				return new RailwayGround(buildable);
			case BASIC:
			default:
				return new Ground(buildable);
		}
	}
	
}
