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
 * Test class for {@link DecorationFactory} for properly handling {@link Buildable.Type} and {@link Themes}.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class DecorationFloorFactoryTest {

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
	 * Checks if {@link DecorationFloor} was created properly.
	 */
	@Test
	public void testCreateBasicDecorationFloor() {
		try {
			DecorationFloor floor = DecorationFloorFactory.createDecorationFloor(decorationBuildable, Themes.BASIC);
			Assert.assertEquals(floor.getClass(), DecorationFloor.class);
		} catch (BuildingTypeMismatchException e) {
			Assert.fail("Shouldn't throw exception.");
		}
	}

	/**
	 * Checks if {@link MinimalistDecorationFloor} was created properly.
	 */
	@Test
	public void testCreateMinimalistDecorationFloor() {
		try {
			DecorationFloor floor = DecorationFloorFactory.createDecorationFloor(decorationBuildable,
					Themes.MINIMALIST);
			Assert.assertEquals(floor.getClass(), MinimalistDecorationFloor.class);
		} catch (BuildingTypeMismatchException e) {
			Assert.fail("Shouldn't throw exception.");
		}
	}

	/**
	 * Checks if {@linkRailwayDecorationFloor} was created properly.
	 */
	@Test
	public void testCreateRailwayDecorationFloor() {
		try {
			DecorationFloor floor = DecorationFloorFactory.createDecorationFloor(decorationBuildable, Themes.RAILWAY);
			Assert.assertEquals(floor.getClass(), RailwayDecorationFloor.class);
		} catch (BuildingTypeMismatchException e) {
			Assert.fail("Shouldn't throw exception.");
		}
	}

	/**
	 * Checks if {@linkTownDecorationFloor} was created properly.
	 */
	@Test
	public void testCreateTownDecorationFloor() {
		try {
			DecorationFloor floor = DecorationFloorFactory.createDecorationFloor(decorationBuildable, Themes.TOWN);
			Assert.assertEquals(floor.getClass(), TownDecorationFloor.class);
		} catch (BuildingTypeMismatchException e) {
			Assert.fail("Shouldn't throw exception.");
		}
	}

	/**
	 * Checks if {@link Cellar} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a decorationFloor of cellar.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateBasicCellarAsDecorationFloor() throws BuildingTypeMismatchException {
		DecorationFloorFactory.createDecorationFloor(cellarBuildable, Themes.BASIC);
	}

	/**
	 * Checks if {@link MinimalistCellar} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a decorationFloor of cellar.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateMinimalistCellarAsDecorationFloor() throws BuildingTypeMismatchException {
		DecorationFloorFactory.createDecorationFloor(cellarBuildable, Themes.MINIMALIST);
	}

	/**
	 * Checks if {@link RailwayCellar} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a decorationFloor of cellar.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateRailwayCellarAsDecorationFloor() throws BuildingTypeMismatchException {
		DecorationFloorFactory.createDecorationFloor(cellarBuildable, Themes.RAILWAY);
	}

	/**
	 * Checks if {@link TownCellar} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a decorationFloor of cellar.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateTownCellarAsDecorationFloor() throws BuildingTypeMismatchException {
		DecorationFloorFactory.createDecorationFloor(cellarBuildable, Themes.TOWN);
	}

	/**
	 * Checks if {@link Floor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a decorationFloor of floor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateBasicFloorAsDecorationFloor() throws BuildingTypeMismatchException {
		DecorationFloorFactory.createDecorationFloor(floorBuildable, Themes.BASIC);
	}

	/**
	 * Checks if {@link MinimalistFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a decorationFloor of floor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateMinimalistFloorAsDecorationFloor() throws BuildingTypeMismatchException {
		DecorationFloorFactory.createDecorationFloor(floorBuildable, Themes.MINIMALIST);
	}

	/**
	 * Checks if {@link RailwayFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a decorationFloor of floor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateRailwayFloorAsDecorationFloor() throws BuildingTypeMismatchException {
		DecorationFloorFactory.createDecorationFloor(floorBuildable, Themes.RAILWAY);
	}

	/**
	 * Checks if {@link TownFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a decorationFloor of floor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateTownFloorAsDecorationFloor() throws BuildingTypeMismatchException {
		DecorationFloorFactory.createDecorationFloor(floorBuildable, Themes.TOWN);
	}

	/**
	 * Checks if {@link Garden} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a decorationFloor of garden.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateBasicGardenAsDecorationFloor() throws BuildingTypeMismatchException {
		DecorationFloorFactory.createDecorationFloor(gardenBuildable, Themes.BASIC);
	}

	/**
	 * Checks if {@link MinimalistGarden} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a decorationFloor of garden.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateMinimalistGardenAsDecorationFloor() throws BuildingTypeMismatchException {
		DecorationFloorFactory.createDecorationFloor(gardenBuildable, Themes.MINIMALIST);
	}

	/**
	 * Checks if {@link RailwayGarden} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a decorationFloor of garden.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateRailwayGardenAsDecorationFloor() throws BuildingTypeMismatchException {
		DecorationFloorFactory.createDecorationFloor(gardenBuildable, Themes.RAILWAY);
	}

	/**
	 * Checks if {@link TownGarden} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a decorationFloor of garden.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateTownGardenAsDecorationFloor() throws BuildingTypeMismatchException {
		DecorationFloorFactory.createDecorationFloor(gardenBuildable, Themes.TOWN);
	}

	/**
	 * Checks if {@link Ground} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a decorationFloor of ground.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateBasicGroundAsDecorationFloor() throws BuildingTypeMismatchException {
		DecorationFloorFactory.createDecorationFloor(groundBuildable, Themes.BASIC);
	}

	/**
	 * Checks if {@link MinimalistGround} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a decorationFloor of ground.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateMinimalistGroundAsDecorationFloor() throws BuildingTypeMismatchException {
		DecorationFloorFactory.createDecorationFloor(groundBuildable, Themes.MINIMALIST);
	}

	/**
	 * Checks if {@link RailwayGround} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a decorationFloor of ground.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateRailwayGroundAsDecorationFloor() throws BuildingTypeMismatchException {
		DecorationFloorFactory.createDecorationFloor(groundBuildable, Themes.RAILWAY);
	}

	/**
	 * Checks if {@link TownGround} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a decorationFloor of ground.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateTownGroundAsDecorationFloor() throws BuildingTypeMismatchException {
		DecorationFloorFactory.createDecorationFloor(groundBuildable, Themes.TOWN);
	}

}
