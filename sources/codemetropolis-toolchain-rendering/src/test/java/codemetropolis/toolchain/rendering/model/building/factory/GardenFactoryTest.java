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
 * Test class for {@link GardenFactory} for properly handling {@link Buildable.Type} and {@link Themes}.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class GardenFactoryTest {

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
	 * Checks if {@link Garden} was created properly.
	 */
	@Test
	public void testCreateBasicGarden() {
		try {
			Garden garden = GardenFactory.createGarden(gardenBuildable, Themes.BASIC);
			Assert.assertEquals(garden.getClass(), Garden.class);
		} catch (BuildingTypeMismatchException e) {
			Assert.fail("Shouldn't throw exception.");
		}
	}

	/**
	 * Checks if {@link MinimalistGarden} was created properly.
	 */
	@Test
	public void testCreateMinimalistGarden() {
		try {
			Garden garden = GardenFactory.createGarden(gardenBuildable, Themes.MINIMALIST);
			Assert.assertEquals(garden.getClass(), MinimalistGarden.class);
		} catch (BuildingTypeMismatchException e) {
			Assert.fail("Shouldn't throw exception.");
		}
	}

	/**
	 * Checks if {@linkRailwayGarden} was created properly.
	 */
	@Test
	public void testCreateRailwayGarden() {
		try {
			Garden garden = GardenFactory.createGarden(gardenBuildable, Themes.RAILWAY);
			Assert.assertEquals(garden.getClass(), RailwayGarden.class);
		} catch (BuildingTypeMismatchException e) {
			Assert.fail("Shouldn't throw exception.");
		}
	}

	/**
	 * Checks if {@linkTownGarden} was created properly.
	 */
	@Test
	public void testCreateTownGarden() {
		try {
			Garden garden = GardenFactory.createGarden(gardenBuildable, Themes.TOWN);
			Assert.assertEquals(garden.getClass(), TownGarden.class);
		} catch (BuildingTypeMismatchException e) {
			Assert.fail("Shouldn't throw exception.");
		}
	}

	/**
	 * Checks if {@link Cellar} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a garden of cellar.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateBasicCellarAsGarden() throws BuildingTypeMismatchException {
		GardenFactory.createGarden(cellarBuildable, Themes.BASIC);
	}

	/**
	 * Checks if {@link MinimalistCellar} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a garden of cellar.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateMinimalistCellarAsGarden() throws BuildingTypeMismatchException {
		GardenFactory.createGarden(cellarBuildable, Themes.MINIMALIST);
	}

	/**
	 * Checks if {@link RailwayCellar} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a garden of cellar.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateRailwayCellarAsGarden() throws BuildingTypeMismatchException {
		GardenFactory.createGarden(cellarBuildable, Themes.RAILWAY);
	}

	/**
	 * Checks if {@link TownCellar} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a garden of cellar.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateTownCellarAsGarden() throws BuildingTypeMismatchException {
		GardenFactory.createGarden(cellarBuildable, Themes.TOWN);
	}

	/**
	 * Checks if {@link Floor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a garden of floor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateBasicFloorAsGarden() throws BuildingTypeMismatchException {
		GardenFactory.createGarden(floorBuildable, Themes.BASIC);
	}

	/**
	 * Checks if {@link MinimalistFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a garden of floor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateMinimalistFloorAsGarden() throws BuildingTypeMismatchException {
		GardenFactory.createGarden(floorBuildable, Themes.MINIMALIST);
	}

	/**
	 * Checks if {@link RailwayFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a garden of floor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateRailwayFloorAsGarden() throws BuildingTypeMismatchException {
		GardenFactory.createGarden(floorBuildable, Themes.RAILWAY);
	}

	/**
	 * Checks if {@link TownFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a garden of floor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateTownFloorAsGarden() throws BuildingTypeMismatchException {
		GardenFactory.createGarden(floorBuildable, Themes.TOWN);
	}

	/**
	 * Checks if {@link DecorationFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a garden of decorationFloor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateBasicDecorationFloorAsGarden() throws BuildingTypeMismatchException {
		GardenFactory.createGarden(decorationBuildable, Themes.BASIC);
	}

	/**
	 * Checks if {@link MinimalistDecorationFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a garden of decorationFloor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateMinimalistDecorationFloorAsGarden() throws BuildingTypeMismatchException {
		GardenFactory.createGarden(decorationBuildable, Themes.MINIMALIST);
	}

	/**
	 * Checks if {@link RailwayDecorationFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a garden of decorationFloor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateRailwayDecorationFloorAsGarden() throws BuildingTypeMismatchException {
		GardenFactory.createGarden(decorationBuildable, Themes.RAILWAY);
	}

	/**
	 * Checks if {@link TownDecorationFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a garden of decorationFloor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateTownDecorationFloorAsGarden() throws BuildingTypeMismatchException {
		GardenFactory.createGarden(decorationBuildable, Themes.TOWN);
	}

	/**
	 * Checks if {@link Ground} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a garden of ground.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateBasicGroundAsGarden() throws BuildingTypeMismatchException {
		GardenFactory.createGarden(groundBuildable, Themes.BASIC);
	}

	/**
	 * Checks if {@link MinimalistGround} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a garden of ground.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateMinimalistGroundAsGarden() throws BuildingTypeMismatchException {
		GardenFactory.createGarden(groundBuildable, Themes.MINIMALIST);
	}

	/**
	 * Checks if {@link RailwayGround} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a garden of ground.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateRailwayGroundAsGarden() throws BuildingTypeMismatchException {
		GardenFactory.createGarden(groundBuildable, Themes.RAILWAY);
	}

	/**
	 * Checks if {@link TownGround} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a garden of ground.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateTownGroundAsGarden() throws BuildingTypeMismatchException {
		GardenFactory.createGarden(groundBuildable, Themes.TOWN);
	}

}
