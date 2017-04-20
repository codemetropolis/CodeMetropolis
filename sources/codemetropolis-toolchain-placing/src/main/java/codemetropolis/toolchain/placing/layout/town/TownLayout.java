package codemetropolis.toolchain.placing.layout.town;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.BuildableTree;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.placing.exceptions.LayoutException;
import codemetropolis.toolchain.placing.layout.Layout;
import codemetropolis.toolchain.placing.layout.town.BuildableWrapper;
import codemetropolis.toolchain.placing.layout.town.TownHouse;
import codemetropolis.toolchain.placing.layout.pack.PackLayout;
import codemetropolis.toolchain.placing.layout.pack.RectanglePacker;
import codemetropolis.toolchain.placing.layout.pack.RectanglePacker.Rectangle;

/**
 * {@link TownLayout} gives the look and feel of a real "small" town.
 * So houses are built not so high, they are in gardens, along streets.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class TownLayout extends Layout {
	
	private final int SPACE = 3;
	private final int GROUND_LEVEL = 61;
	private final int MAX_HEIGHT = 200;
	private Buildable leftRef = null;
	private Buildable rightRef = null;
	private boolean left = true;
	private int mostNegativeZ = 0;

	/**
	 * An easier, clearer representation of Gardens, without wrapper.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void apply(BuildableTree buildables) throws LayoutException {
		preparePackages(buildables.getRoot(), buildables.getRoot());
		removeEmptyPackages(buildables.getRoot());
		prepareGardens(buildables.getRoot());

		Map<Buildable, List<TownHouse>> houses = createHouses(buildables);
		BuildableWrapper wrapper = new BuildableWrapper(buildables.getRoot());
		findGardens(buildables.getRoot(), wrapper, houses);
		postProcess(buildables.getRoot());
	}
	
	/**
	 * Collect all the packages, and put them as a child of the root recursively.
	 * 
	 * @param node The current node, where we're searching for packages.
	 * @param root The root where to packages will be added.
	 */
	private void preparePackages(Buildable node, Buildable root) {
		List<Buildable> children = node.getChildrenList();
		for(int i = 0; i < children.size(); i++) {
			Buildable c = children.get(i);
			if(c.getType() == Buildable.Type.GROUND  && c.getParent().getType() != Buildable.Type.CONTAINER) {
				if(!c.getParent().getName().equals("<root_package>")) {
					c.setName(c.getParent().getName() + "." + c.getName());
				}
				children.remove(c);
				c.setParent(null);
				root.addChild(c);
				i--;
			}
			preparePackages(c, root);
		}
	}
	
	/**
	 * Removing from the package list the empty ones, where rails can't be found.
	 * 
	 * @param node The node whose children we're iterating over.
	 */
	private void removeEmptyPackages(Buildable node) {
		List<Buildable> children = node.getChildrenList();
		for(int i = 0; i < children.size(); i++) {
			Buildable c = children.get(i);
			if(c.getType() == Buildable.Type.GROUND  && c.getChildrenList().isEmpty()) {
				children.remove(c);
			}
		}
	}
	
	/**
	 * Collecting gardens to the same level in {@link BuildableTree} hierarchy.
	 * 
	 * @param node The current node.
	 */
	private void prepareGardens(Buildable node) {
		List<Buildable> children = node.getChildrenList();
		for(int i = 0; i < children.size(); i++) {
			Buildable c = children.get(i);
			if(c.getType() == Buildable.Type.GARDEN  && c.getParent().getType() == Buildable.Type.GARDEN) {
				c.setName(c.getParent().getName() + "#" + c.getName());
				children.remove(c);
				c.setParent(c.getParent().getParent());
				c.getParent().addChild(c);
				i--;
			}
			prepareGardens(c);
		}
	}
	
	/**
	 * A recursive function for discovering all of the gardens and grounds
	 * Grounds position is set in this function.
	 * To put gardens into their places {@code layDownGarden} function is called.
	 * 
	 * @param node The current {@link Buildable}, which we are positioning.
	 * @param wrapper Wrapper holds all of the houses.
	 * @param houses This holds all of the houses in a map.
	 * @throws LayoutException For {@link PackLayout}, it type of current element is incorrect.
	 */
	private void findGardens(Buildable node, BuildableWrapper wrapper, Map<Buildable, List<TownHouse>> houses) throws LayoutException {
		if(node.getType() == Buildable.Type.CONTAINER || node.getType() == Buildable.Type.GROUND) {
			node.setSizeY(1);
			node.setSizeZ(10);
			
			if(left) {
				if(leftRef == null) {
					node.setPositionX(0);
				} else {
					node.setPositionX(leftRef.getPositionX() + leftRef.getSizeX() + 5);
				}
				node.addAttribute("left", "true");
			} else {
				if(rightRef == null) {
					node.setPositionX(0);
				} else {
					node.setPositionX(rightRef.getPositionX() + rightRef.getSizeX() + 5);
				}
				node.addAttribute("left", "false");
			}
			node.setPositionZ(-5);
			node.setPositionY(GROUND_LEVEL - 1);
			
		}
		Buildable[] children = node.getChildren();
		for(Buildable c : children) {
			if(c.getType() == Buildable.Type.GARDEN) {
				layDownGarden(c, wrapper, houses);
			} else {
				findGardens(c, wrapper, houses);
			}
		}
		
		int leftEnd = leftRef != null ? leftRef.getPositionX() + leftRef.getSizeX() + 5 : 0;
		int rightEnd = rightRef != null ? rightRef.getPositionX() + rightRef.getSizeX() + 5: 0;
		int targetEnd = leftEnd < rightEnd ? rightEnd : leftEnd;

		node.setSizeX(targetEnd - node.getPositionX());
		
		left = !left;
	}
	
	/**
	 * Gardens in this theme are built up using {@link PackLayout} algorithm,
	 * but their height is limited more strictly.
	 * 
	 * @param garden The current garden which holds all of its houses.
	 * @throws LayoutException For {@link PackLayout}, it type of current element is incorrect.
	 */
	private void layDownGarden(Buildable garden, BuildableWrapper wrapper, Map<Buildable, List<TownHouse>> houses) throws LayoutException {				
		BuildableWrapper root = new BuildableWrapper(garden);
		packRecursive(root, houses, new Point(garden.getPositionX(), garden.getPositionY(), garden.getPositionZ()));
		
		if(left) {
			if(leftRef == null) {
				root.setPositionX(0);
			} else {
				root.setPositionX(leftRef.getPositionX() + leftRef.getSizeX() + 5);
			}
			root.setPositionZ(-(root.getSizeZ() + 5));
			mostNegativeZ = (root.getPositionZ() < mostNegativeZ) ? root.getPositionZ() : mostNegativeZ;
			leftRef = garden;
		} else {
			if(rightRef == null) {
				root.setPositionX(0);
			} else {
				root.setPositionX(rightRef.getPositionX() + rightRef.getSizeX() + 5);
			}
			root.setPositionZ(5);
			rightRef = garden;
		}
		garden.setPositionY(GROUND_LEVEL - 1);
	}

	/**
	 * Collecting all the house elements, including {@link Buildable.Type#CELLAR} as garage.
	 * It collects a city's elements if it is a {@link Buildable.Type#FLOOR},
	 * a {@link Buildable.Type#CELLAR} or a {@link Buildable.Type#DECORATION_FLOOR}.
	 * 
	 * @param buildables All the elements of the city.
	 * @return A list of the floors of one house.
	 */
	private List<Buildable> getHouses(BuildableTree buildables) {
		List<Buildable> result = new ArrayList<Buildable>();
		for(Buildable b : buildables.getBuildables()) {
			if(b.getType() != Buildable.Type.FLOOR && b.getType() != Buildable.Type.CELLAR &&
					b.getType() != Buildable.Type.DECORATION_FLOOR) continue;
			result.add(b);
		}
		return result;
	}

	/**
	 * Creates a house by putting all the floor on top of each other until the max height.
	 * Also adds decoration floor on top of the houses and cellars as garages.
	 * 
	 * @param buildables All the house elements are added in this {@link BuildableTree}.
	 * @return The created list of floors, as a train.
	 * @throws LayoutException Throws exception, if floor's type is incorrect.
	 */
	private Map<Buildable, List<TownHouse>> createHouses(BuildableTree buildables) throws LayoutException {
		Map<Buildable, List<TownHouse>> houses = new HashMap<Buildable, List<TownHouse>>();
		for(Buildable b : getHouses(buildables)) {
			List<TownHouse> housesOfParent = houses.get(b.getParent());
			if(housesOfParent == null) {
				housesOfParent = new ArrayList<TownHouse>();
				houses.put(b.getParent(), housesOfParent);
			}
			
			boolean addedSuccessfully = false;
			for(TownHouse h : housesOfParent) {
				if(h.add(b)) {
					addedSuccessfully = true;
					break;
				}
			}
			
			if(!addedSuccessfully) {
				TownHouse h = new TownHouse(GROUND_LEVEL, MAX_HEIGHT);
				h.add(b);
				housesOfParent.add(h);
			}
		}
		
		Collection<List<TownHouse>> collectionOfHouses = houses.values();
		Buildable oldTopFloor;
		for(List<TownHouse> listOfHouses : collectionOfHouses) {
			for(TownHouse house : listOfHouses) {
				if(house.getTopFloor() != null && house.getTopFloor().getType() != Buildable.Type.CELLAR) {	
					oldTopFloor = house.getTopFloor();
					Buildable decorationFloor = new Buildable(UUID.randomUUID().toString(),
							"decorationFloor", Buildable.Type.DECORATION_FLOOR);
					decorationFloor.setSizeX(oldTopFloor.getSizeX());
					decorationFloor.setSizeY(9);
					decorationFloor.setSizeZ(oldTopFloor.getSizeZ());
					decorationFloor.setAttributes(Arrays.asList(oldTopFloor.getAttributes()));
					decorationFloor.setCdfNames(oldTopFloor.getCdfNames());
					decorationFloor.setParent(oldTopFloor.getParent());
					oldTopFloor.getParent().addChild(decorationFloor);
					house.add(decorationFloor);
				}
			}
		}
	
		return houses;
	}
	
	/**
	 * Maximum sizes in 3 dimensions is determined depending on the expected size of the garden.
	 * 
	 * @param buildables A collection of {@link BuildableWrapper} object.
	 * @return A new point which contains maximum sizes in 3 dimensions.
	 */
	private Point getMaxSizes(Collection<BuildableWrapper> buildables) {
		int maxX = 0, maxZ = 0;
		for(BuildableWrapper b : buildables) {
			if(b.getSizeX() > maxX) maxX = b.getSizeX();
			if(b.getSizeZ() > maxZ) maxZ = b.getSizeZ();
		}
		return new Point(maxX, 0, maxZ);
	}
	
	/**
	 * We use {@link PackLayout}'s packing algorithm inside the garden.
	 * So in a garden houses are packed on the smallest possible space.
	 * 
	 * @param root The garden from where we start packing.
	 * @param houses All the houses we want to put into the same garden.
	 * @param startPos The garden's position.
	 */
	public void packRecursive(BuildableWrapper root, Map<Buildable, List<TownHouse>> houses, Point startPos) {
		List<BuildableWrapper> children = root.getTownChildren(houses);
		for(BuildableWrapper c : children) {
			if(!c.getTownChildren(houses).isEmpty()) {
				packRecursive(c, houses, startPos);
			}
		}
		Collections.sort(children);
		Collections.reverse(children);
		pack(children, SPACE, startPos);
	}
	
	/**
	 * Calculate the starting size of the garden.
	 * 
	 * @param buildables A collection of the houses in a garden.
	 * @param space The space between houses.
	 * @param startPos The starting position of the garden.
	 */
	private void pack(Collection<BuildableWrapper> buildables, int space, Point startPos) {
		Point startingSize = getMaxSizes(buildables);
		pack(buildables, startingSize.getX(), startingSize.getZ(), space, startPos);
	}
	
	/**
	 * A recursive algorithm, using {@link RectanglePacker} to minimize the space of a garden.
	 * 
	 * @param buildables The list of houses.
	 * @param sizeX The starting x size.
	 * @param sizeZ The starting z size.
	 * @param space The spaces between houses.
	 * @param startPos The starting point of the garden.
	 */
	private void pack(Collection<BuildableWrapper> buildables, int sizeX, int sizeZ, int space, Point startPos) {
		RectanglePacker<BuildableWrapper> packer = new RectanglePacker<BuildableWrapper>(sizeX, sizeZ, space);
		
		for(BuildableWrapper b : buildables) {
			if(packer.insert(b.getSizeX(), b.getSizeZ(), b) == null) {
				if(sizeX > sizeZ) {
					sizeZ++;
				} else {
					sizeX++;
				}
				pack(buildables, sizeX, sizeZ, space, startPos);
				return;
			};
		}
		
		for(BuildableWrapper b : buildables) {
			Rectangle r = packer.findRectangle(b);
			b.setPositionX(startPos.getX() + r.x + space);
			b.setPositionZ(startPos.getZ() + r.y + space);
		}
		
		if(!buildables.isEmpty()) {
			Point parentSize = calculateParentSize(buildables, space);
			Buildable parent = buildables.iterator().next().getParent();
			parent.setSizeX(parentSize.getX());
			parent.setSizeZ(parentSize.getZ());
		}
	}
	
	/**
	 * Set the size of the parent depending on the maximum sizes of the inside houses.
	 * 
	 * @param buildables The houses.
	 * @param space The space between houses.
	 * @return Parent element's size held a point, without y dimension size.
	 */
	private Point calculateParentSize(Collection<BuildableWrapper> buildables, int space) {
		BuildableWrapper firstChild = buildables.iterator().next();
		int minX = firstChild.getPositionX();
		int maxX = firstChild.getPositionX() + firstChild.getSizeX();
		int minZ = firstChild.getPositionZ();
		int maxZ = firstChild.getPositionZ() + firstChild.getSizeZ();
		
		for(BuildableWrapper b : buildables) {
			if(b.getPositionX() < minX) minX = b.getPositionX();
			if(b.getPositionX() + b.getSizeX() > maxX) maxX = b.getPositionX() + b.getSizeX();
			if(b.getPositionZ() < minZ) minZ = b.getPositionZ();
			if(b.getPositionZ() + b.getSizeZ() > maxZ) maxZ = b.getPositionZ() + b.getSizeZ();
		}
		
		return new Point(
				maxX - minX + 2 * space,
				0,
				maxZ - minZ + 2 * space
				);
	}
	
	/**
	 * Updates z dimensional positions, so all of the buildings will have a positive z position.
	 * 
	 * @param node The current node, whose children z dimensional position will be updated.
	 */
	private void postProcess(Buildable node) {
		List<Buildable> children = node.getChildrenList();
		for(int i = 0; i < children.size(); i++) {
			Buildable c = children.get(i);
			c.setPositionZ(c.getPositionZ() - mostNegativeZ);
			postProcess(c);
		}
	}
	
}
