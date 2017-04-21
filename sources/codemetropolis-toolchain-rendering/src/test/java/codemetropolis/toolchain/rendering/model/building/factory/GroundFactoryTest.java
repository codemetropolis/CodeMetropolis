package codemetropolis.toolchain.rendering.model.building.factory;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.model.Themes;
import codemetropolis.toolchain.rendering.model.building.Cellar;
import codemetropolis.toolchain.rendering.model.building.Floor;
import codemetropolis.toolchain.rendering.model.building.Ground;
import codemetropolis.toolchain.rendering.model.building.theme.minimalist.MinimalistCellar;
import codemetropolis.toolchain.rendering.model.building.theme.minimalist.MinimalistGround;
import codemetropolis.toolchain.rendering.model.building.theme.railway.RailwayCellar;
import codemetropolis.toolchain.rendering.model.building.theme.railway.RailwayGround;
import codemetropolis.toolchain.rendering.model.building.theme.town.TownCellar;
import codemetropolis.toolchain.rendering.model.building.theme.town.TownGround;
import junit.framework.Assert;

/**
 * Test class for {@link GroundFactory} for properly handling {@link Buildable.Type} and {@link Themes}.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class GroundFactoryTest {
	
	/**
	 * Checks if {@link Ground} was created properly.
	 */
	@Test
	public void testCreateBasicGround() {
		try {
			Assert.assertEquals(
					GroundFactory.createGround( new Buildable(UUID.randomUUID().toString(),
							"test", Buildable.Type.GROUND), Themes.BASIC).getClass(), Ground.class
					);
		} catch (BuildingTypeMismatchException e) {
			fail("Shouldn't throw exception.");
		}
	}
	
	/**
	 * Checks if {@link MinimalistGround} was created properly.
	 */
	@Test
	public void testCreateMinimalistGround() {
		try {
			Assert.assertEquals(
					GroundFactory.createGround( new Buildable(UUID.randomUUID().toString(),
							"test", Buildable.Type.GROUND), Themes.MINIMALIST).getClass(), MinimalistGround.class
					);
		} catch (BuildingTypeMismatchException e) {
			fail("Shouldn't throw exception.");
		}
	}
	
	/**
	 * Checks if {@linkRailwayGround} was created properly.
	 */
	@Test
	public void testCreateRailwayGround() {
		try {
			Assert.assertEquals(
					GroundFactory.createGround( new Buildable(UUID.randomUUID().toString(),
							"test", Buildable.Type.GROUND), Themes.RAILWAY).getClass(), RailwayGround.class
					);
		} catch (BuildingTypeMismatchException e) {
			fail("Shouldn't throw exception.");
		}
	}
	
	/**
	 * Checks if {@linkTownGround} was created properly.
	 */
	@Test
	public void testCreateTownGround() {
		try {
			Assert.assertEquals(
					GroundFactory.createGround( new Buildable(UUID.randomUUID().toString(),
							"test", Buildable.Type.GROUND), Themes.TOWN).getClass(), TownGround.class
					);
		} catch (BuildingTypeMismatchException e) {
			fail("Shouldn't throw exception.");
		}
	}
	
	/**
	 * Checks if {@link Floor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a ground of floor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateBasicFloorAsGround() throws BuildingTypeMismatchException {
		GroundFactory.createGround( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.FLOOR), Themes.BASIC);
	}
	
	/**
	 * Checks if {@link MinimalistFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a ground of floor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateMinimalistFloorAsGround() throws BuildingTypeMismatchException {
		GroundFactory.createGround( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.FLOOR), Themes.MINIMALIST);
	}
	
	/**
	 * Checks if {@link RailwayFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a ground of floor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateRailwayFloorAsGround() throws BuildingTypeMismatchException {
		GroundFactory.createGround( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.FLOOR), Themes.RAILWAY);
	}
	
	/**
	 * Checks if {@link TownFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a ground of floor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateTownFloorAsGround() throws BuildingTypeMismatchException {
		GroundFactory.createGround( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.FLOOR), Themes.TOWN);
	}
	
	/**
	 * Checks if {@link DecorationFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a ground of decorationFloor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateBasicDecorationFloorAsGround() throws BuildingTypeMismatchException {
		GroundFactory.createGround( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.DECORATION_FLOOR), Themes.BASIC);
	}
	
	/**
	 * Checks if {@link MinimalistDecorationFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a ground of decorationFloor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateMinimalistDecorationFloorAsGround() throws BuildingTypeMismatchException {
		GroundFactory.createGround( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.DECORATION_FLOOR), Themes.MINIMALIST);
	}
	
	/**
	 * Checks if {@link RailwayDecorationFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a ground of decorationFloor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateRailwayDecorationFloorAsGround() throws BuildingTypeMismatchException {
		GroundFactory.createGround( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.DECORATION_FLOOR), Themes.RAILWAY);
	}
	
	/**
	 * Checks if {@link TownDecorationFloor} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a ground of decorationFloor.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateTownDecorationFloorAsGround() throws BuildingTypeMismatchException {
		GroundFactory.createGround( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.DECORATION_FLOOR), Themes.TOWN);
	}

	/**
	 * Checks if {@link Garden} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a ground of garden.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateBasicGardenAsGround() throws BuildingTypeMismatchException {
		GroundFactory.createGround( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.GARDEN), Themes.BASIC);
	}
	
	/**
	 * Checks if {@link MinimalistGarden} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a ground of garden.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateMinimalistGardenAsGround() throws BuildingTypeMismatchException {
		GroundFactory.createGround( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.GARDEN), Themes.MINIMALIST);
	}
	
	/**
	 * Checks if {@link RailwayGarden} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a ground of garden.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateRailwayGardenAsGround() throws BuildingTypeMismatchException {
		GroundFactory.createGround( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.GARDEN), Themes.RAILWAY);
	}
	
	/**
	 * Checks if {@link TownGarden} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a ground of garden.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateTownGardenAsGround() throws BuildingTypeMismatchException {
		GroundFactory.createGround( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.GARDEN), Themes.TOWN);
	}
	
	/**
	 * Checks if {@link Cellar} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a ground of cellar.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateBasicCellarAsGround() throws BuildingTypeMismatchException {
		GroundFactory.createGround( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.CELLAR), Themes.BASIC);
	}
	
	/**
	 * Checks if {@link MinimalistCellar} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a ground of cellar.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateMinimalistCellarAsGround() throws BuildingTypeMismatchException {
		GroundFactory.createGround( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.CELLAR), Themes.MINIMALIST);
	}
	
	/**
	 * Checks if {@link RailwayCellar} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a ground of cellar.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateRailwayCellarAsGround() throws BuildingTypeMismatchException {
		GroundFactory.createGround( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.CELLAR), Themes.RAILWAY);
	}
	
	/**
	 * Checks if {@link TownCellar} was filtered properly.
	 * 
	 * @throws BuildingTypeMismatchException if testing is okay. So couldn't create a ground of cellar.
	 */
	@Test(expected = BuildingTypeMismatchException.class)
	public void testCreateTownCellarAsGround() throws BuildingTypeMismatchException {
		GroundFactory.createGround( new Buildable(UUID.randomUUID().toString(), "test", Buildable.Type.CELLAR), Themes.TOWN);
	}

}
