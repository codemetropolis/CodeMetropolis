package codemetropolis.toolchain.rendering.model.building.factory;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.model.Themes;
import codemetropolis.toolchain.rendering.model.building.Cellar;
import codemetropolis.toolchain.rendering.model.building.Floor;
import codemetropolis.toolchain.rendering.model.building.theme.minimalist.MinimalistCellar;
import codemetropolis.toolchain.rendering.model.building.theme.minimalist.MinimalistFloor;
import codemetropolis.toolchain.rendering.model.building.theme.railway.RailwayFloor;
import codemetropolis.toolchain.rendering.model.building.theme.town.TownFloor;
import junit.framework.Assert;

/**
 * Test class for {@link FloorFactory} for properly handling {@link Buildable.Type} and {@link Themes}.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class FloorFactoryTest {
	
	/**
	 * Checks if {@link Floor} was created properly.
	 */
	@Test
	public void testCreateBasicFloor() {
		try {
			Assert.assertEquals(
					FloorFactory.createFloor( new Buildable(UUID.randomUUID().toString(),
							"test", Buildable.Type.FLOOR), Themes.BASIC).getClass(), Floor.class
					);
		} catch (BuildingTypeMismatchException e) {
			fail("Shouldn't throw exception.");
		}
	}
	
	/**
	 * Checks if {@link MinimalistFloor} was created properly.
	 */
	@Test
	public void testCreateMinimalistFloor() {
		try {
			Assert.assertEquals(
					FloorFactory.createFloor( new Buildable(UUID.randomUUID().toString(),
							"test", Buildable.Type.FLOOR), Themes.MINIMALIST).getClass(), MinimalistFloor.class
					);
		} catch (BuildingTypeMismatchException e) {
			fail("Shouldn't throw exception.");
		}
	}
	
	/**
	 * Checks if {@linkRailwayFloor} was created properly.
	 */
	@Test
	public void testCreateRailwayFloor() {
		try {
			Assert.assertEquals(
					FloorFactory.createFloor( new Buildable(UUID.randomUUID().toString(),
							"test", Buildable.Type.FLOOR), Themes.RAILWAY).getClass(), RailwayFloor.class
					);
		} catch (BuildingTypeMismatchException e) {
			fail("Shouldn't throw exception.");
		}
	}
	
	/**
	 * Checks if {@linkTownFloor} was created properly.
	 */
	@Test
	public void testCreateTownFloor() {
		try {
			Assert.assertEquals(
					FloorFactory.createFloor( new Buildable(UUID.randomUUID().toString(),
							"test", Buildable.Type.FLOOR), Themes.TOWN).getClass(), TownFloor.class
					);
		} catch (BuildingTypeMismatchException e) {
			fail("Shouldn't throw exception.");
		}
	}
	
	/**
	 * Checks if {@link Cellar} was created properly.
	 */
	@Test
	public void testCreateBasicCellarAsFloor() throws BuildingTypeMismatchException {
		try {
			Assert.assertEquals(
					FloorFactory.createFloor( new Buildable(UUID.randomUUID().toString(),
							"test", Buildable.Type.CELLAR), Themes.BASIC).getClass(), Floor.class
					);
		} catch (BuildingTypeMismatchException e) {
			fail("Shouldn't throw exception.");
		}
	}
	
	/**
	 * Checks if {@link MinimalistCellar} was created properly.
	 */
	@Test
	public void testCreateMinimalistCellarAsFloor() {
		try {
			Assert.assertEquals(
					FloorFactory.createFloor( new Buildable(UUID.randomUUID().toString(), "test",
							Buildable.Type.CELLAR), Themes.MINIMALIST).getClass(), MinimalistFloor.class
					);
		} catch (BuildingTypeMismatchException e) {
			fail("Shouldn't throw exception.");
		}
	}
	
	/**
	 * Checks if {@link RailwayFloor} was created properly.
	 */
	@Test
	public void testCreateRailwayCellarAsFloor() {
		try {
			Assert.assertEquals(
					FloorFactory.createFloor( new Buildable(UUID.randomUUID().toString(), "test",
							Buildable.Type.CELLAR), Themes.RAILWAY).getClass(), RailwayFloor.class
					);
		} catch (BuildingTypeMismatchException e) {
			fail("Shouldn't throw exception.");
		}
	}
	
	/**
	 * Checks if {@link TownFloor} was created properly.
	 */
	@Test
	public void testCreateTownCellarAsFloor() throws BuildingTypeMismatchException {
		try {
			Assert.assertEquals(
					FloorFactory.createFloor( new Buildable(UUID.randomUUID().toString(), "test",
							Buildable.Type.CELLAR), Themes.TOWN).getClass(), TownFloor.class
					);
		} catch (BuildingTypeMismatchException e) {
			fail("Shouldn't throw exception.");
		}
	}
	
	/**
	 * Checks if {@link DecorationFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a floor of decorationFloor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateBasicDecorationFloorAsFloor() throws BuildingTypeMismatchException {
		FloorFactory.createFloor( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.DECORATION_FLOOR), Themes.BASIC);
	}
	
	/**
	 * Checks if {@link MinimalistDecorationFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a floor of decorationFloor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateMinimalistDecorationFloorAsFloor() throws BuildingTypeMismatchException {
		FloorFactory.createFloor( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.DECORATION_FLOOR), Themes.MINIMALIST);
	}
	
	/**
	 * Checks if {@link RailwayDecorationFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a floor of decorationFloor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateRailwayDecorationFloorAsFloor() throws BuildingTypeMismatchException {
		FloorFactory.createFloor( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.DECORATION_FLOOR), Themes.RAILWAY);
	}
	
	/**
	 * Checks if {@link TownDecorationFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a floor of decorationFloor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateTownDecorationFloorAsFloor() throws BuildingTypeMismatchException {
		FloorFactory.createFloor( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.DECORATION_FLOOR), Themes.TOWN);
	}

	/**
	 * Checks if {@link Garden} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a floor of garden.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateBasicGardenAsFloor() throws BuildingTypeMismatchException {
		FloorFactory.createFloor( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.GARDEN), Themes.BASIC);
	}
	
	/**
	 * Checks if {@link MinimalistGarden} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a floor of garden.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateMinimalistGardenAsFloor() throws BuildingTypeMismatchException {
		FloorFactory.createFloor( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.GARDEN), Themes.MINIMALIST);
	}
	
	/**
	 * Checks if {@link RailwayGarden} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a floor of garden.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateRailwayGardenAsFloor() throws BuildingTypeMismatchException {
		FloorFactory.createFloor( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.GARDEN), Themes.RAILWAY);
	}
	
	/**
	 * Checks if {@link TownGarden} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a floor of garden.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateTownGardenAsFloor() throws BuildingTypeMismatchException {
		FloorFactory.createFloor( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.GARDEN), Themes.TOWN);
	}
	
	/**
	 * Checks if {@link Ground} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a floor of ground.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateBasicGroundAsFloor() throws BuildingTypeMismatchException {
		FloorFactory.createFloor( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.GROUND), Themes.BASIC);
	}
	
	/**
	 * Checks if {@link MinimalistGround} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a floor of ground.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateMinimalistGroundAsFloor() throws BuildingTypeMismatchException {
		FloorFactory.createFloor( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.GROUND), Themes.MINIMALIST);
	}
	
	/**
	 * Checks if {@link RailwayGround} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a floor of ground.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateRailwayGroundAsFloor() throws BuildingTypeMismatchException {
		FloorFactory.createFloor( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.GROUND), Themes.RAILWAY);
	}
	
	/**
	 * Checks if {@link TownGround} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a floor of ground.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateTownGroundAsFloor() throws BuildingTypeMismatchException {
		FloorFactory.createFloor( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.GROUND), Themes.TOWN);
	}

}
