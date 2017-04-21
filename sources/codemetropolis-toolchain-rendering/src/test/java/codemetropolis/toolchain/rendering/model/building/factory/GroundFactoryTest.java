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
 * Test class for {@link GroundFactory} for properly handling {@link Buildable.Type} and {@link Themes}.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class GroundFactoryTest {

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
	 * Checks if {@link Ground} was created properly.
	 */
	@Test
	public void testCreateBasicGround() {
		try {
			Ground ground = GroundFactory.createGround(groundBuildable, Themes.BASIC);
			Assert.assertEquals(ground.getClass(), Ground.class);
		} catch (BuildingTypeMismatchException e) {
			Assert.fail("Shouldn't throw exception.");
		}
	}

	/**
	 * Checks if {@link MinimalistGround} was created properly.
	 */
	@Test
	public void testCreateMinimalistGround() {
		try {
			Ground ground = GroundFactory.createGround(groundBuildable, Themes.MINIMALIST);
			Assert.assertEquals(ground.getClass(), MinimalistGround.class);
		} catch (BuildingTypeMismatchException e) {
			Assert.fail("Shouldn't throw exception.");
		}
	}

	/**
	 * Checks if {@linkRailwayGround} was created properly.
	 */
	@Test
	public void testCreateRailwayGround() {
		try {
			Ground ground = GroundFactory.createGround(groundBuildable, Themes.RAILWAY);
			Assert.assertEquals(ground.getClass(), RailwayGround.class);
		} catch (BuildingTypeMismatchException e) {
			Assert.fail("Shouldn't throw exception.");
		}
	}

	/**
	 * Checks if {@linkTownGround} was created properly.
	 */
	@Test
	public void testCreateTownGround() {
		try {
			Ground ground = GroundFactory.createGround(groundBuildable, Themes.TOWN);
			Assert.assertEquals(ground.getClass(), TownGround.class);
		} catch (BuildingTypeMismatchException e) {
			Assert.fail("Shouldn't throw exception.");
		}
	}

	/**
	 * Checks if {@link Floor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a ground of floor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateBasicFloorAsGround() throws BuildingTypeMismatchException {
		GroundFactory.createGround(floorBuildable, Themes.BASIC);
	}

	/**
	 * Checks if {@link MinimalistFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a ground of floor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateMinimalistFloorAsGround() throws BuildingTypeMismatchException {
		GroundFactory.createGround(floorBuildable, Themes.MINIMALIST);
	}

	/**
	 * Checks if {@link RailwayFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a ground of floor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateRailwayFloorAsGround() throws BuildingTypeMismatchException {
		GroundFactory.createGround(floorBuildable, Themes.RAILWAY);
	}

	/**
	 * Checks if {@link TownFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a ground of floor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateTownFloorAsGround() throws BuildingTypeMismatchException {
		GroundFactory.createGround(floorBuildable, Themes.TOWN);
	}

	/**
	 * Checks if {@link DecorationFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a ground of decorationFloor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateBasicDecorationFloorAsGround() throws BuildingTypeMismatchException {
		GroundFactory.createGround(decorationBuildable, Themes.BASIC);
	}

	/**
	 * Checks if {@link MinimalistDecorationFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a ground of decorationFloor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateMinimalistDecorationFloorAsGround() throws BuildingTypeMismatchException {
		GroundFactory.createGround(decorationBuildable, Themes.MINIMALIST);
	}

	/**
	 * Checks if {@link RailwayDecorationFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a ground of decorationFloor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateRailwayDecorationFloorAsGround() throws BuildingTypeMismatchException {
		GroundFactory.createGround(decorationBuildable, Themes.RAILWAY);
	}

	/**
	 * Checks if {@link TownDecorationFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a ground of decorationFloor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateTownDecorationFloorAsGround() throws BuildingTypeMismatchException {
		GroundFactory.createGround(decorationBuildable, Themes.TOWN);
	}

	/**
	 * Checks if {@link Garden} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a ground of garden.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateBasicGardenAsGround() throws BuildingTypeMismatchException {
		GroundFactory.createGround(gardenBuildable, Themes.BASIC);
	}

	/**
	 * Checks if {@link MinimalistGarden} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a ground of garden.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateMinimalistGardenAsGround() throws BuildingTypeMismatchException {
		GroundFactory.createGround(gardenBuildable, Themes.MINIMALIST);
	}

	/**
	 * Checks if {@link RailwayGarden} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a ground of garden.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateRailwayGardenAsGround() throws BuildingTypeMismatchException {
		GroundFactory.createGround(gardenBuildable, Themes.RAILWAY);
	}

	/**
	 * Checks if {@link TownGarden} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a ground of garden.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateTownGardenAsGround() throws BuildingTypeMismatchException {
		GroundFactory.createGround(gardenBuildable, Themes.TOWN);
	}

	/**
	 * Checks if {@link Cellar} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a ground of cellar.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateBasicCellarAsGround() throws BuildingTypeMismatchException {
		GroundFactory.createGround(cellarBuildable, Themes.BASIC);
	}

	/**
	 * Checks if {@link MinimalistCellar} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a ground of cellar.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateMinimalistCellarAsGround() throws BuildingTypeMismatchException {
		GroundFactory.createGround(cellarBuildable, Themes.MINIMALIST);
	}

	/**
	 * Checks if {@link RailwayCellar} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a ground of cellar.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateRailwayCellarAsGround() throws BuildingTypeMismatchException {
		GroundFactory.createGround(cellarBuildable, Themes.RAILWAY);
	}

	/**
	 * Checks if {@link TownCellar} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a ground of cellar.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateTownCellarAsGround() throws BuildingTypeMismatchException {
		GroundFactory.createGround(cellarBuildable, Themes.TOWN);
	}

}
