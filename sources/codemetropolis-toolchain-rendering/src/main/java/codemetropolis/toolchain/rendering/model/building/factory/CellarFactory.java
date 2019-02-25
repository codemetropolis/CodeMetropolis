package codemetropolis.toolchain.rendering.model.building.factory;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.model.Themes;
import codemetropolis.toolchain.rendering.model.building.Cellar;
import codemetropolis.toolchain.rendering.model.building.theme.minimalist.MinimalistCellar;
import codemetropolis.toolchain.rendering.model.building.theme.railway.RailwayCellar;
import codemetropolis.toolchain.rendering.model.building.theme.town.TownCellar;

/**
 * Create the proper {@link Cellar} subclass depending on the {@link Themes}.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class CellarFactory {

	/**
	 * Depending on the {@link Themes} parameter create a proper {@link Cellar} subclass.
	 * 
	 * @param buildable A {@link Buildable} object to be used for instantiation.
	 * @param theme The requested style, defined as {@link Themes}.
	 * @return The proper {@link Cellar} subclass.
	 * @throws BuildingTypeMismatchException If type of {@code buildable} is incorrect.
	 */
	public static Cellar createCellar(Buildable buildable, Themes theme) throws BuildingTypeMismatchException {
		switch (theme) {
			case MINIMALIST:
				return new MinimalistCellar(buildable);
			case RAILWAY:
				return new RailwayCellar(buildable);
			case TOWN:
				return new TownCellar(buildable);
			case BASIC:
			default:
				return new Cellar(buildable);
		}
	}

}
