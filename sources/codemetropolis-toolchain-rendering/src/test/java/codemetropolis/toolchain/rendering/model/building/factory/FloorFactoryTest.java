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
import codemetropolis.toolchain.rendering.model.building.theme.railway.RailwayDecorationFloor;
import codemetropolis.toolchain.rendering.model.building.theme.railway.RailwayFloor;
import codemetropolis.toolchain.rendering.model.building.theme.railway.RailwayGarden;
import codemetropolis.toolchain.rendering.model.building.theme.railway.RailwayGround;
import codemetropolis.toolchain.rendering.model.building.theme.town.TownDecorationFloor;
import codemetropolis.toolchain.rendering.model.building.theme.town.TownFloor;
import codemetropolis.toolchain.rendering.model.building.theme.town.TownGarden;
import codemetropolis.toolchain.rendering.model.building.theme.town.TownGround;

/**
 * Test class for {@link FloorFactory} for properly handling {@link Buildable.Type} and {@link Themes}.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class FloorFactoryTest {

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
	 * Checks if {@link Floor} was created properly.
	 */
	@Test
	public void testCreateBasicFloor() {
		try {
			Floor floor = FloorFactory.createFloor(floorBuildable, Themes.BASIC);
			Assert.assertEquals(floor.getClass(), Floor.class);
		} catch (BuildingTypeMismatchException e) {
			Assert.fail("Shouldn't throw exception.");
		}
	}

	/**
	 * Checks if {@link MinimalistFloor} was created properly.
	 */
	@Test
	public void testCreateMinimalistFloor() {
		try {
			Floor floor = FloorFactory.createFloor(floorBuildable, Themes.MINIMALIST);
			Assert.assertEquals(floor.getClass(), MinimalistFloor.class);
		} catch (BuildingTypeMismatchException e) {
			Assert.fail("Shouldn't throw exception.");
		}
	}

	/**
	 * Checks if {@linkRailwayFloor} was created properly.
	 */
	@Test
	public void testCreateRailwayFloor() {
		try {
			Floor floor = FloorFactory.createFloor(floorBuildable, Themes.RAILWAY);
			Assert.assertEquals(floor.getClass(), RailwayFloor.class);
		} catch (BuildingTypeMismatchException e) {
			Assert.fail("Shouldn't throw exception.");
		}
	}

	/**
	 * Checks if {@linkTownFloor} was created properly.
	 */
	@Test
	public void testCreateTownFloor() {
		try {
			Floor floor = FloorFactory.createFloor(floorBuildable, Themes.TOWN);
			Assert.assertEquals(floor.getClass(), TownFloor.class);
		} catch (BuildingTypeMismatchException e) {
			Assert.fail("Shouldn't throw exception.");
		}
	}

	/**
	 * Checks if {@link Cellar} was created properly.
	 */
	@Test
	public void testCreateBasicCellarAsFloor() throws BuildingTypeMismatchException {
		try {
			Floor floor = FloorFactory.createFloor(cellarBuildable, Themes.BASIC);
			Assert.assertEquals(floor.getClass(), Floor.class);
		} catch (BuildingTypeMismatchException e) {
			Assert.fail("Shouldn't throw exception.");
		}
	}

	/**
	 * Checks if {@link MinimalistCellar} was created properly.
	 */
	@Test
	public void testCreateMinimalistCellarAsFloor() {
		try {
			Floor floor = FloorFactory.createFloor(cellarBuildable, Themes.MINIMALIST);
			Assert.assertEquals(floor.getClass(), MinimalistFloor.class);
		} catch (BuildingTypeMismatchException e) {
			Assert.fail("Shouldn't throw exception.");
		}
	}

	/**
	 * Checks if {@link RailwayFloor} was created properly.
	 */
	@Test
	public void testCreateRailwayCellarAsFloor() {
		try {
			Floor floor = FloorFactory.createFloor(cellarBuildable, Themes.RAILWAY);
			Assert.assertEquals(floor.getClass(), RailwayFloor.class);
		} catch (BuildingTypeMismatchException e) {
			Assert.fail("Shouldn't throw exception.");
		}
	}

	/**
	 * Checks if {@link TownFloor} was created properly.
	 */
	@Test
	public void testCreateTownCellarAsFloor() throws BuildingTypeMismatchException {
		try {
			Floor floor = FloorFactory.createFloor(cellarBuildable, Themes.TOWN);
			Assert.assertEquals(floor.getClass(), TownFloor.class);
		} catch (BuildingTypeMismatchException e) {
			Assert.fail("Shouldn't throw exception.");
		}
	}

	/**
	 * Checks if {@link DecorationFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a floor of decorationFloor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateBasicDecorationFloorAsFloor() throws BuildingTypeMismatchException {
		FloorFactory.createFloor(decorationBuildable, Themes.BASIC);
	}

	/**
	 * Checks if {@link MinimalistDecorationFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a floor of decorationFloor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateMinimalistDecorationFloorAsFloor() throws BuildingTypeMismatchException {
		FloorFactory.createFloor(decorationBuildable, Themes.MINIMALIST);
	}

	/**
	 * Checks if {@link RailwayDecorationFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a floor of decorationFloor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateRailwayDecorationFloorAsFloor() throws BuildingTypeMismatchException {
		FloorFactory.createFloor(decorationBuildable, Themes.RAILWAY);
	}

	/**
	 * Checks if {@link TownDecorationFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a floor of decorationFloor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateTownDecorationFloorAsFloor() throws BuildingTypeMismatchException {
		FloorFactory.createFloor(decorationBuildable, Themes.TOWN);
	}

	/**
	 * Checks if {@link Garden} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a floor of garden.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateBasicGardenAsFloor() throws BuildingTypeMismatchException {
		FloorFactory.createFloor(gardenBuildable, Themes.BASIC);
	}

	/**
	 * Checks if {@link MinimalistGarden} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a floor of garden.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateMinimalistGardenAsFloor() throws BuildingTypeMismatchException {
		FloorFactory.createFloor(gardenBuildable, Themes.MINIMALIST);
	}

	/**
	 * Checks if {@link RailwayGarden} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a floor of garden.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateRailwayGardenAsFloor() throws BuildingTypeMismatchException {
		FloorFactory.createFloor(gardenBuildable, Themes.RAILWAY);
	}

	/**
	 * Checks if {@link TownGarden} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a floor of garden.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateTownGardenAsFloor() throws BuildingTypeMismatchException {
		FloorFactory.createFloor(gardenBuildable, Themes.TOWN);
	}

	/**
	 * Checks if {@link Ground} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a floor of ground.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateBasicGroundAsFloor() throws BuildingTypeMismatchException {
		FloorFactory.createFloor(groundBuildable, Themes.BASIC);
	}

	/**
	 * Checks if {@link MinimalistGround} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a floor of ground.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateMinimalistGroundAsFloor() throws BuildingTypeMismatchException {
		FloorFactory.createFloor(groundBuildable, Themes.MINIMALIST);
	}

	/**
	 * Checks if {@link RailwayGround} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a floor of ground.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateRailwayGroundAsFloor() throws BuildingTypeMismatchException {
		FloorFactory.createFloor(groundBuildable, Themes.RAILWAY);
	}

	/**
	 * Checks if {@link TownGround} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a floor of ground.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateTownGroundAsFloor() throws BuildingTypeMismatchException {
		FloorFactory.createFloor(groundBuildable, Themes.TOWN);
	}

}
