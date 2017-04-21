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
import codemetropolis.toolchain.rendering.model.building.theme.railway.RailwayCellar;
import codemetropolis.toolchain.rendering.model.building.theme.town.TownCellar;
import junit.framework.Assert;

/**
 * Test class for {@link CellarFactory} for properly handling {@link Buildable.Type} and {@link Themes}.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class CellarFactoryTest {
	
	/**
	 * Checks if {@link Cellar} was created properly.
	 */
	@Test
	public void testCreateBasicCellar() {
		try {
			Assert.assertEquals(
					CellarFactory.createCellar( new Buildable(UUID.randomUUID().toString(),
							"test", Buildable.Type.CELLAR), Themes.BASIC).getClass(), Cellar.class
					);
		} catch (BuildingTypeMismatchException e) {
			fail("Shouldn't throw exception.");
		}
	}
	
	/**
	 * Checks if {@link MinimalistCellar} was created properly.
	 */
	@Test
	public void testCreateMinimalistCellar() {
		try {
			Assert.assertEquals(
					CellarFactory.createCellar( new Buildable(UUID.randomUUID().toString(),
							"test", Buildable.Type.CELLAR), Themes.MINIMALIST).getClass(), MinimalistCellar.class
					);
		} catch (BuildingTypeMismatchException e) {
			fail("Shouldn't throw exception.");
		}
	}
	
	/**
	 * Checks if {@linkRailwayCellar} was created properly.
	 */
	@Test
	public void testCreateRailwayCellar() {
		try {
			Assert.assertEquals(
					CellarFactory.createCellar( new Buildable(UUID.randomUUID().toString(),
							"test", Buildable.Type.CELLAR), Themes.RAILWAY).getClass(), RailwayCellar.class
					);
		} catch (BuildingTypeMismatchException e) {
			fail("Shouldn't throw exception.");
		}
	}
	
	/**
	 * Checks if {@linkTownCellar} was created properly.
	 */
	@Test
	public void testCreateTownCellar() {
		try {
			Assert.assertEquals(
					CellarFactory.createCellar( new Buildable(UUID.randomUUID().toString(),
							"test", Buildable.Type.CELLAR), Themes.TOWN).getClass(), TownCellar.class
					);
		} catch (BuildingTypeMismatchException e) {
			fail("Shouldn't throw exception.");
		}
	}
	
	/**
	 * Checks if {@link Floor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a cellar of floor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateBasicFloorAsCellar() throws BuildingTypeMismatchException {
		CellarFactory.createCellar( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.FLOOR), Themes.BASIC);
	}
	
	/**
	 * Checks if {@link MinimalistFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a cellar of floor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateMinimalistFloorAsCellar() throws BuildingTypeMismatchException {
		CellarFactory.createCellar( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.FLOOR), Themes.MINIMALIST);
	}
	
	/**
	 * Checks if {@link RailwayFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a cellar of floor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateRailwayFloorAsCellar() throws BuildingTypeMismatchException {
		CellarFactory.createCellar( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.FLOOR), Themes.RAILWAY);
	}
	
	/**
	 * Checks if {@link TownFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a cellar of floor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateTownFloorAsCellar() throws BuildingTypeMismatchException {
		CellarFactory.createCellar( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.FLOOR), Themes.TOWN);
	}
	
	/**
	 * Checks if {@link DecorationFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a cellar of decorationFloor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateBasicDecorationFloorAsCellar() throws BuildingTypeMismatchException {
		CellarFactory.createCellar( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.DECORATION_FLOOR), Themes.BASIC);
	}
	
	/**
	 * Checks if {@link MinimalistDecorationFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a cellar of decorationFloor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateMinimalistDecorationFloorAsCellar() throws BuildingTypeMismatchException {
		CellarFactory.createCellar( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.DECORATION_FLOOR), Themes.MINIMALIST);
	}
	
	/**
	 * Checks if {@link RailwayDecorationFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a cellar of decorationFloor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateRailwayDecorationFloorAsCellar() throws BuildingTypeMismatchException {
		CellarFactory.createCellar( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.DECORATION_FLOOR), Themes.RAILWAY);
	}
	
	/**
	 * Checks if {@link TownDecorationFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a cellar of decorationFloor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateTownDecorationFloorAsCellar() throws BuildingTypeMismatchException {
		CellarFactory.createCellar( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.DECORATION_FLOOR), Themes.TOWN);
	}

	/**
	 * Checks if {@link Garden} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a cellar of garden.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateBasicGardenAsCellar() throws BuildingTypeMismatchException {
		CellarFactory.createCellar( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.GARDEN), Themes.BASIC);
	}
	
	/**
	 * Checks if {@link MinimalistGarden} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a cellar of garden.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateMinimalistGardenAsCellar() throws BuildingTypeMismatchException {
		CellarFactory.createCellar( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.GARDEN), Themes.MINIMALIST);
	}
	
	/**
	 * Checks if {@link RailwayGarden} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a cellar of garden.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateRailwayGardenAsCellar() throws BuildingTypeMismatchException {
		CellarFactory.createCellar( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.GARDEN), Themes.RAILWAY);
	}
	
	/**
	 * Checks if {@link TownGarden} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a cellar of garden.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateTownGardenAsCellar() throws BuildingTypeMismatchException {
		CellarFactory.createCellar( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.GARDEN), Themes.TOWN);
	}
	
	/**
	 * Checks if {@link Ground} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a cellar of ground.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateBasicGroundAsCellar() throws BuildingTypeMismatchException {
		CellarFactory.createCellar( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.GROUND), Themes.BASIC);
	}
	
	/**
	 * Checks if {@link MinimalistGround} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a cellar of ground.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateMinimalistGroundAsCellar() throws BuildingTypeMismatchException {
		CellarFactory.createCellar( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.GROUND), Themes.MINIMALIST);
	}
	
	/**
	 * Checks if {@link RailwayGround} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a cellar of ground.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateRailwayGroundAsCellar() throws BuildingTypeMismatchException {
		CellarFactory.createCellar( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.GROUND), Themes.RAILWAY);
	}
	
	/**
	 * Checks if {@link TownGround} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a cellar of ground.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateTownGroundAsCellar() throws BuildingTypeMismatchException {
		CellarFactory.createCellar( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.GROUND), Themes.TOWN);
	}

}
