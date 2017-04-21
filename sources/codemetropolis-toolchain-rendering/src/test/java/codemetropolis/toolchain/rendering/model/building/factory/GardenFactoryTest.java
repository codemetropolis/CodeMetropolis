package codemetropolis.toolchain.rendering.model.building.factory;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.model.Themes;
import codemetropolis.toolchain.rendering.model.building.Floor;
import codemetropolis.toolchain.rendering.model.building.Garden;
import codemetropolis.toolchain.rendering.model.building.theme.minimalist.MinimalistGarden;
import codemetropolis.toolchain.rendering.model.building.theme.railway.RailwayGarden;
import codemetropolis.toolchain.rendering.model.building.theme.town.TownGarden;
import junit.framework.Assert;

/**
 * Test class for {@link GardenFactory} for properly handling {@link Buildable.Type} and {@link Themes}.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class GardenFactoryTest {
	
	/**
	 * Checks if {@link Garden} was created properly.
	 */
	@Test
	public void testCreateBasicGarden() {
		try {
			Assert.assertEquals(
					GardenFactory.createGarden( new Buildable(UUID.randomUUID().toString(),
							"test", Buildable.Type.GARDEN), Themes.BASIC).getClass(), Garden.class
					);
		} catch (BuildingTypeMismatchException e) {
			fail("Shouldn't throw exception.");
		}
	}
	
	/**
	 * Checks if {@link MinimalistGarden} was created properly.
	 */
	@Test
	public void testCreateMinimalistGarden() {
		try {
			Assert.assertEquals(
					GardenFactory.createGarden( new Buildable(UUID.randomUUID().toString(),
							"test", Buildable.Type.GARDEN), Themes.MINIMALIST).getClass(), MinimalistGarden.class
					);
		} catch (BuildingTypeMismatchException e) {
			fail("Shouldn't throw exception.");
		}
	}
	
	/**
	 * Checks if {@linkRailwayGarden} was created properly.
	 */
	@Test
	public void testCreateRailwayGarden() {
		try {
			Assert.assertEquals(
					GardenFactory.createGarden( new Buildable(UUID.randomUUID().toString(),
							"test", Buildable.Type.GARDEN), Themes.RAILWAY).getClass(), RailwayGarden.class
					);
		} catch (BuildingTypeMismatchException e) {
			fail("Shouldn't throw exception.");
		}
	}
	
	/**
	 * Checks if {@linkTownGarden} was created properly.
	 */
	@Test
	public void testCreateTownGarden() {
		try {
			Assert.assertEquals(
					GardenFactory.createGarden( new Buildable(UUID.randomUUID().toString(),
							"test", Buildable.Type.GARDEN), Themes.TOWN).getClass(), TownGarden.class
					);
		} catch (BuildingTypeMismatchException e) {
			fail("Shouldn't throw exception.");
		}
	}
	
	/**
	 * Checks if {@link Cellar} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a garden of cellar.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateBasicCellarAsGarden() throws BuildingTypeMismatchException {
		GardenFactory.createGarden( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.CELLAR), Themes.BASIC);
	}
	
	/**
	 * Checks if {@link MinimalistCellar} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a garden of cellar.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateMinimalistCellarAsGarden() throws BuildingTypeMismatchException {
		GardenFactory.createGarden( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.CELLAR), Themes.MINIMALIST);
	}
	
	/**
	 * Checks if {@link RailwayCellar} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a garden of cellar.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateRailwayCellarAsGarden() throws BuildingTypeMismatchException {
		GardenFactory.createGarden( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.CELLAR), Themes.RAILWAY);
	}
	
	/**
	 * Checks if {@link TownCellar} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a garden of cellar.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateTownCellarAsGarden() throws BuildingTypeMismatchException {
		GardenFactory.createGarden( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.CELLAR), Themes.TOWN);
	}
	
	/**
	 * Checks if {@link Floor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a garden of floor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateBasicFloorAsGarden() throws BuildingTypeMismatchException {
		GardenFactory.createGarden( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.FLOOR), Themes.BASIC);
	}
	
	/**
	 * Checks if {@link MinimalistFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a garden of floor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateMinimalistFloorAsGarden() throws BuildingTypeMismatchException {
		GardenFactory.createGarden( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.FLOOR), Themes.MINIMALIST);
	}
	
	/**
	 * Checks if {@link RailwayFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a garden of floor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateRailwayFloorAsGarden() throws BuildingTypeMismatchException {
		GardenFactory.createGarden( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.FLOOR), Themes.RAILWAY);
	}
	
	/**
	 * Checks if {@link TownFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a garden of floor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateTownFloorAsGarden() throws BuildingTypeMismatchException {
		GardenFactory.createGarden( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.FLOOR), Themes.TOWN);
	}
	
	/**
	 * Checks if {@link DecorationFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a garden of decorationFloor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateBasicDecorationFloorAsGarden() throws BuildingTypeMismatchException {
		GardenFactory.createGarden( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.DECORATION_FLOOR), Themes.BASIC);
	}
	
	/**
	 * Checks if {@link MinimalistDecorationFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a garden of decorationFloor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateMinimalistDecorationFloorAsGarden() throws BuildingTypeMismatchException {
		GardenFactory.createGarden( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.DECORATION_FLOOR), Themes.MINIMALIST);
	}
	
	/**
	 * Checks if {@link RailwayDecorationFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a garden of decorationFloor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateRailwayDecorationFloorAsGarden() throws BuildingTypeMismatchException {
		GardenFactory.createGarden( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.DECORATION_FLOOR), Themes.RAILWAY);
	}
	
	/**
	 * Checks if {@link TownDecorationFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a garden of decorationFloor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateTownDecorationFloorAsGarden() throws BuildingTypeMismatchException {
		GardenFactory.createGarden( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.DECORATION_FLOOR), Themes.TOWN);
	}
	
	/**
	 * Checks if {@link Ground} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a garden of ground.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateBasicGroundAsGarden() throws BuildingTypeMismatchException {
		GardenFactory.createGarden( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.GROUND), Themes.BASIC);
	}
	
	/**
	 * Checks if {@link MinimalistGround} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a garden of ground.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateMinimalistGroundAsGarden() throws BuildingTypeMismatchException {
		GardenFactory.createGarden( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.GROUND), Themes.MINIMALIST);
	}
	
	/**
	 * Checks if {@link RailwayGround} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a garden of ground.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateRailwayGroundAsGarden() throws BuildingTypeMismatchException {
		GardenFactory.createGarden( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.GROUND), Themes.RAILWAY);
	}
	
	/**
	 * Checks if {@link TownGround} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a garden of ground.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateTownGroundAsGarden() throws BuildingTypeMismatchException {
		GardenFactory.createGarden( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.GROUND), Themes.TOWN);
	}

}
