package codemetropolis.toolchain.rendering.model.building.factory;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.model.Themes;
import codemetropolis.toolchain.rendering.model.building.Cellar;
import codemetropolis.toolchain.rendering.model.building.DecorationFloor;
import codemetropolis.toolchain.rendering.model.building.Floor;
import codemetropolis.toolchain.rendering.model.building.Garden;
import codemetropolis.toolchain.rendering.model.building.Ground;
import codemetropolis.toolchain.rendering.model.building.theme.minimalist.MinimalistCellar;
import codemetropolis.toolchain.rendering.model.building.theme.minimalist.MinimalistDecorationFloor;
import codemetropolis.toolchain.rendering.model.building.theme.minimalist.MinimalistFloor;
import codemetropolis.toolchain.rendering.model.building.theme.minimalist.MinimalistGarden;
import codemetropolis.toolchain.rendering.model.building.theme.minimalist.MinimalistGround;
import codemetropolis.toolchain.rendering.model.building.theme.railway.RailwayCellar;
import codemetropolis.toolchain.rendering.model.building.theme.railway.RailwayDecorationFloor;
import codemetropolis.toolchain.rendering.model.building.theme.railway.RailwayFloor;
import codemetropolis.toolchain.rendering.model.building.theme.railway.RailwayGarden;
import codemetropolis.toolchain.rendering.model.building.theme.railway.RailwayGround;
import codemetropolis.toolchain.rendering.model.building.theme.town.TownCellar;
import codemetropolis.toolchain.rendering.model.building.theme.town.TownDecorationFloor;
import codemetropolis.toolchain.rendering.model.building.theme.town.TownFloor;
import codemetropolis.toolchain.rendering.model.building.theme.town.TownGarden;
import codemetropolis.toolchain.rendering.model.building.theme.town.TownGround;

/**
 * Test class for {@link CellarFactory} for properly handling {@link Buildable.Type} and {@link Themes}.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class CellarFactoryTest {

	private Buildable decorationBuildable;
	private Buildable floorBuildable;
	private Buildable cellarBuildable;
	private Buildable gardenBuildable;
	private Buildable groundBuildable;

	/**
	 * Initializes the buildables used in the tests.
	 */
	@Before
	public void init() {
		decorationBuildable = new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.DECORATION_FLOOR);
		floorBuildable = new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.FLOOR);
		cellarBuildable = new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.CELLAR);
		gardenBuildable = new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.GARDEN);
		groundBuildable = new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.GROUND);
	}

	/**
	 * Checks if {@link Cellar} was created properly.
	 */
	@Test
	public void testCreateBasicCellar() {
		try {
			Cellar cellar = CellarFactory.createCellar(cellarBuildable, Themes.BASIC);
			Assert.assertEquals(cellar.getClass(), Cellar.class);
		} catch (BuildingTypeMismatchException e) {
			Assert.fail("Shouldn't throw exception.");
		}
	}

	/**
	 * Checks if {@link MinimalistCellar} was created properly.
	 */
	@Test
	public void testCreateMinimalistCellar() {
		try {
			Cellar cellar = CellarFactory.createCellar(cellarBuildable, Themes.MINIMALIST);
			Assert.assertEquals(cellar.getClass(), MinimalistCellar.class);
		} catch (BuildingTypeMismatchException e) {
			Assert.fail("Shouldn't throw exception.");
		}
	}

	/**
	 * Checks if {@linkRailwayCellar} was created properly.
	 */
	@Test
	public void testCreateRailwayCellar() {
		try {
			Cellar cellar = CellarFactory.createCellar(cellarBuildable, Themes.RAILWAY);
			Assert.assertEquals(cellar.getClass(), RailwayCellar.class);
		} catch (BuildingTypeMismatchException e) {
			Assert.fail("Shouldn't throw exception.");
		}
	}

	/**
	 * Checks if {@linkTownCellar} was created properly.
	 */
	@Test
	public void testCreateTownCellar() {
		try {
			Cellar cellar = CellarFactory.createCellar(cellarBuildable, Themes.TOWN);
			Assert.assertEquals(cellar.getClass(), TownCellar.class);
		} catch (BuildingTypeMismatchException e) {
			Assert.fail("Shouldn't throw exception.");
		}
	}

	/**
	 * Checks if {@link Floor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a cellar of floor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateBasicFloorAsCellar() throws BuildingTypeMismatchException {
		CellarFactory.createCellar(floorBuildable, Themes.BASIC);
	}

	/**
	 * Checks if {@link MinimalistFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a cellar of floor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateMinimalistFloorAsCellar() throws BuildingTypeMismatchException {
		CellarFactory.createCellar(floorBuildable, Themes.MINIMALIST);
	}

	/**
	 * Checks if {@link RailwayFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a cellar of floor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateRailwayFloorAsCellar() throws BuildingTypeMismatchException {
		CellarFactory.createCellar(floorBuildable, Themes.RAILWAY);
	}

	/**
	 * Checks if {@link TownFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a cellar of floor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateTownFloorAsCellar() throws BuildingTypeMismatchException {
		CellarFactory.createCellar(floorBuildable, Themes.TOWN);
	}

	/**
	 * Checks if {@link DecorationFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a cellar of decorationFloor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateBasicDecorationFloorAsCellar() throws BuildingTypeMismatchException {
		CellarFactory.createCellar(decorationBuildable, Themes.BASIC);
	}

	/**
	 * Checks if {@link MinimalistDecorationFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a cellar of decorationFloor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateMinimalistDecorationFloorAsCellar() throws BuildingTypeMismatchException {
		CellarFactory.createCellar(decorationBuildable, Themes.MINIMALIST);
	}

	/**
	 * Checks if {@link RailwayDecorationFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a cellar of decorationFloor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateRailwayDecorationFloorAsCellar() throws BuildingTypeMismatchException {
		CellarFactory.createCellar(decorationBuildable, Themes.RAILWAY);
	}

	/**
	 * Checks if {@link TownDecorationFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a cellar of decorationFloor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateTownDecorationFloorAsCellar() throws BuildingTypeMismatchException {
		CellarFactory.createCellar(decorationBuildable, Themes.TOWN);
	}

	/**
	 * Checks if {@link Garden} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a cellar of garden.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateBasicGardenAsCellar() throws BuildingTypeMismatchException {
		CellarFactory.createCellar(gardenBuildable, Themes.BASIC);
	}

	/**
	 * Checks if {@link MinimalistGarden} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a cellar of garden.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateMinimalistGardenAsCellar() throws BuildingTypeMismatchException {
		CellarFactory.createCellar(gardenBuildable, Themes.MINIMALIST);
	}

	/**
	 * Checks if {@link RailwayGarden} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a cellar of garden.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateRailwayGardenAsCellar() throws BuildingTypeMismatchException {
		CellarFactory.createCellar(gardenBuildable, Themes.RAILWAY);
	}

	/**
	 * Checks if {@link TownGarden} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a cellar of garden.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateTownGardenAsCellar() throws BuildingTypeMismatchException {
		CellarFactory.createCellar(gardenBuildable, Themes.TOWN);
	}

	/**
	 * Checks if {@link Ground} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a cellar of ground.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateBasicGroundAsCellar() throws BuildingTypeMismatchException {
		CellarFactory.createCellar(groundBuildable, Themes.BASIC);
	}

	/**
	 * Checks if {@link MinimalistGround} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a cellar of ground.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateMinimalistGroundAsCellar() throws BuildingTypeMismatchException {
		CellarFactory.createCellar(groundBuildable, Themes.MINIMALIST);
	}

	/**
	 * Checks if {@link RailwayGround} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a cellar of ground.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateRailwayGroundAsCellar() throws BuildingTypeMismatchException {
		CellarFactory.createCellar(groundBuildable, Themes.RAILWAY);
	}

	/**
	 * Checks if {@link TownGround} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a cellar of ground.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateTownGroundAsCellar() throws BuildingTypeMismatchException {
		CellarFactory.createCellar(groundBuildable, Themes.TOWN);
	}

}
