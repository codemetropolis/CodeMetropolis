package codemetropolis.toolchain.rendering.model.building.factory;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.model.Themes;
import codemetropolis.toolchain.rendering.model.building.DecorationFloor;
import codemetropolis.toolchain.rendering.model.building.Floor;
import codemetropolis.toolchain.rendering.model.building.theme.minimalist.MinimalistDecorationFloor;
import codemetropolis.toolchain.rendering.model.building.theme.railway.RailwayDecorationFloor;
import codemetropolis.toolchain.rendering.model.building.theme.town.TownDecorationFloor;
import junit.framework.Assert;

/**
 * Test class for {@link DecorationFactory} for properly handling {@link Buildable.Type} and {@link Themes}.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class DecorationFloorFactoryTest {
	
	/**
	 * Checks if {@link DecorationFloor} was created properly.
	 */
	@Test
	public void testCreateBasicDecorationFloor() {
		try {
			Assert.assertEquals(
					DecorationFloorFactory.createDecorationFloor( new Buildable(UUID.randomUUID().toString(),
							"test", Buildable.Type.DECORATION_FLOOR), Themes.BASIC).getClass(), DecorationFloor.class
					);
		} catch (BuildingTypeMismatchException e) {
			fail("Shouldn't throw exception.");
		}
	}
	
	/**
	 * Checks if {@link MinimalistDecorationFloor} was created properly.
	 */
	@Test
	public void testCreateMinimalistDecorationFloor() {
		try {
			Assert.assertEquals(
					DecorationFloorFactory.createDecorationFloor( new Buildable(UUID.randomUUID().toString(),
							"test", Buildable.Type.DECORATION_FLOOR), Themes.MINIMALIST).getClass(), MinimalistDecorationFloor.class
					);
		} catch (BuildingTypeMismatchException e) {
			fail("Shouldn't throw exception.");
		}
	}
	
	/**
	 * Checks if {@linkRailwayDecorationFloor} was created properly.
	 */
	@Test
	public void testCreateRailwayDecorationFloor() {
		try {
			Assert.assertEquals(
					DecorationFloorFactory.createDecorationFloor( new Buildable(UUID.randomUUID().toString(),
							"test", Buildable.Type.DECORATION_FLOOR), Themes.RAILWAY).getClass(), RailwayDecorationFloor.class
					);
		} catch (BuildingTypeMismatchException e) {
			fail("Shouldn't throw exception.");
		}
	}
	
	/**
	 * Checks if {@linkTownDecorationFloor} was created properly.
	 */
	@Test
	public void testCreateTownDecorationFloor() {
		try {
			Assert.assertEquals(
					DecorationFloorFactory.createDecorationFloor( new Buildable(UUID.randomUUID().toString(),
							"test", Buildable.Type.DECORATION_FLOOR), Themes.TOWN).getClass(), TownDecorationFloor.class
					);
		} catch (BuildingTypeMismatchException e) {
			fail("Shouldn't throw exception.");
		}
	}
	
	/**
	 * Checks if {@link Cellar} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a decorationFloor of cellar.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateBasicCellarAsDecorationFloor() throws BuildingTypeMismatchException {
		DecorationFloorFactory.createDecorationFloor( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.CELLAR), Themes.BASIC);
	}
	
	/**
	 * Checks if {@link MinimalistCellar} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a decorationFloor of cellar.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateMinimalistCellarAsDecorationFloor() throws BuildingTypeMismatchException {
		DecorationFloorFactory.createDecorationFloor( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.CELLAR), Themes.MINIMALIST);
	}
	
	/**
	 * Checks if {@link RailwayCellar} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a decorationFloor of cellar.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateRailwayCellarAsDecorationFloor() throws BuildingTypeMismatchException {
		DecorationFloorFactory.createDecorationFloor( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.CELLAR), Themes.RAILWAY);
	}
	
	/**
	 * Checks if {@link TownCellar} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a decorationFloor of cellar.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateTownCellarAsDecorationFloor() throws BuildingTypeMismatchException {
		DecorationFloorFactory.createDecorationFloor( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.CELLAR), Themes.TOWN);
	}
	
	/**
	 * Checks if {@link Floor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a decorationFloor of floor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateBasicFloorAsDecorationFloor() throws BuildingTypeMismatchException {
		DecorationFloorFactory.createDecorationFloor( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.FLOOR), Themes.BASIC);
	}
	
	/**
	 * Checks if {@link MinimalistFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a decorationFloor of floor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateMinimalistFloorAsDecorationFloor() throws BuildingTypeMismatchException {
		DecorationFloorFactory.createDecorationFloor( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.FLOOR), Themes.MINIMALIST);
	}
	
	/**
	 * Checks if {@link RailwayFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a decorationFloor of floor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateRailwayFloorAsDecorationFloor() throws BuildingTypeMismatchException {
		DecorationFloorFactory.createDecorationFloor( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.FLOOR), Themes.RAILWAY);
	}
	
	/**
	 * Checks if {@link TownFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a decorationFloor of floor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateTownFloorAsDecorationFloor() throws BuildingTypeMismatchException {
		DecorationFloorFactory.createDecorationFloor( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.FLOOR), Themes.TOWN);
	}
	

	/**
	 * Checks if {@link Garden} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a decorationFloor of garden.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateBasicGardenAsDecorationFloor() throws BuildingTypeMismatchException {
		DecorationFloorFactory.createDecorationFloor( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.GARDEN), Themes.BASIC);
	}
	
	/**
	 * Checks if {@link MinimalistGarden} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a decorationFloor of garden.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateMinimalistGardenAsDecorationFloor() throws BuildingTypeMismatchException {
		DecorationFloorFactory.createDecorationFloor( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.GARDEN), Themes.MINIMALIST);
	}
	
	/**
	 * Checks if {@link RailwayGarden} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a decorationFloor of garden.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateRailwayGardenAsDecorationFloor() throws BuildingTypeMismatchException {
		DecorationFloorFactory.createDecorationFloor( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.GARDEN), Themes.RAILWAY);
	}
	
	/**
	 * Checks if {@link TownGarden} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a decorationFloor of garden.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateTownGardenAsDecorationFloor() throws BuildingTypeMismatchException {
		DecorationFloorFactory.createDecorationFloor( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.GARDEN), Themes.TOWN);
	}
	
	/**
	 * Checks if {@link Ground} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a decorationFloor of ground.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateBasicGroundAsDecorationFloor() throws BuildingTypeMismatchException {
		DecorationFloorFactory.createDecorationFloor( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.GROUND), Themes.BASIC);
	}
	
	/**
	 * Checks if {@link MinimalistGround} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a decorationFloor of ground.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateMinimalistGroundAsDecorationFloor() throws BuildingTypeMismatchException {
		DecorationFloorFactory.createDecorationFloor( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.GROUND), Themes.MINIMALIST);
	}
	
	/**
	 * Checks if {@link RailwayGround} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a decorationFloor of ground.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateRailwayGroundAsDecorationFloor() throws BuildingTypeMismatchException {
		DecorationFloorFactory.createDecorationFloor( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.GROUND), Themes.RAILWAY);
	}
	
	/**
	 * Checks if {@link TownGround} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a decorationFloor of ground.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateTownGroundAsDecorationFloor() throws BuildingTypeMismatchException {
		DecorationFloorFactory.createDecorationFloor( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.GROUND), Themes.TOWN);
	}

}
