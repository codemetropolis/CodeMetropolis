package codemetropolis.toolchain.placing.layout.railway;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.BuildableTree;
import codemetropolis.toolchain.placing.exceptions.LayoutException;
import codemetropolis.toolchain.placing.layout.Layout;

/**
 * An easy, clear representation of {@link Layout}.
 * Each garden is represented as a rail, with trains on top of them. Rails are laid next to each other.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class RailwayLayout extends Layout {
	
	private final int GROUND_LEVEL = 61;

	/**
	 * An easier, clearer representation of Gardens, without wrapper.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void apply(BuildableTree buildables) throws LayoutException {
		createTrains(buildables);
		preparePackages(buildables.getRoot(), buildables.getRoot());
		removeEmptyPackages(buildables.getRoot());
		prepareGardens(buildables.getRoot());
		findGardens(buildables.getRoot(), 0);
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
	 * A recursive function for discovering all of the gardens in {@link BuildableTree}.
	 * Whenever a garden is found a rail is laid down, and all the houses are laid on them like wagons.
	 * The next garden is laid next to the previous one.
	 * 
	 * @param node The current {@link Buildable}.
	 * @param position For setting the position of each rail.
	 * @return With the {@code position}, so next rail will be next to the previous one.
	 */
	private int findGardens(Buildable node, int position) {
		if(node.getType() == Buildable.Type.CONTAINER || node.getType() == Buildable.Type.GROUND) {
			node.setSizeX(1);
			node.setSizeY(11);
			node.setSizeZ(11 * node.getChildrenList().size() + 5 * (node.getChildrenList().size() - 1));
			node.setPositionX(-1);
			node.setPositionY(GROUND_LEVEL);
			node.setPositionZ(position * 16);
		}
		Buildable[] children = node.getChildren();
		for(Buildable c : children) {
			if(c.getType() == Buildable.Type.GARDEN) {
				layDownRail(c, position);
				position++;
			} else {
				position = findGardens(c, position);
			}
		}
		return position;
		
	}
	
	/**
	 * It lays down all along one rail, and puts down all the wagons which are included in this rail.
	 * 
	 * @param rail The current garden, as a rail.
	 * @param position Where next rail goes.
	 */
	private void layDownRail(Buildable rail, int position) {
		rail.setPositionX(0);
		rail.setPositionY(GROUND_LEVEL);
		rail.setPositionZ(position * 16);
		rail.setSizeY(2);
		rail.setSizeZ(11);
		int previous = 0;
		for(Buildable wagon : rail.getChildren()) {
			wagon.setSizeX(wagon.getSizeY());
			wagon.setSizeY(9);
			wagon.setSizeZ(9);
			wagon.setPositionX(previous + 2);
			wagon.setPositionY(GROUND_LEVEL + 2);
			wagon.setPositionZ(position * 16 + 1);
			previous = wagon.getPositionX() + wagon.getSizeX();
		}
		rail.setSizeX(previous + (previous%3));
	}

	/**
	 * Collecting all the train elements.
	 * It collects a city's elements if it is a {@link Buildable.Type#FLOOR},
	 * a {@link Buildable.Type#CELLAR} or a {@link Buildable.Type#DECORATION_FLOOR}.
	 * 
	 * @param buildables All the elements of the city.
	 * @return A list of the wagons of one train.
	 */
	private List<Buildable> getTrains(BuildableTree buildables) {
		List<Buildable> result = new ArrayList<Buildable>();
		for(Buildable b : buildables.getBuildables()) {
			if(b.getType() != Buildable.Type.FLOOR && b.getType() != Buildable.Type.CELLAR &&
					b.getType() != Buildable.Type.DECORATION_FLOOR) continue;
			result.add(b);
		}
		return result;
	}

	/**
	 * Creates a train by putting all the wagons one-by-one after each other.
	 * Also adds at the beginning of the train an engine.
	 * 
	 * @param buildables All the train wagons are added in this {@link BuildableTree}.
	 * @return The created list of wagons, as a train.
	 * @throws LayoutException Throws exception, if wagon's type is incorrect.
	 */
	private Map<Buildable, List<Train>> createTrains(BuildableTree buildables) throws LayoutException {
		Map<Buildable, List<Train>> trains = new HashMap<Buildable, List<Train>>();
		for(Buildable b : getTrains(buildables)) {
			List<Train> trainsOfParent = trains.get(b.getParent());
			if(trainsOfParent == null) {
				trainsOfParent = new ArrayList<Train>();
				trains.put(b.getParent(), trainsOfParent);
			}
			
			boolean addedSuccessfully = false;
			for(Train t : trainsOfParent) {
				if(t.addWagon(b)) {
					addedSuccessfully = true;
					break;
				}
			}
			
			if(!addedSuccessfully) {
				Train t = new Train();
				t.addWagon(b);
				trainsOfParent.add(t);
			}
		}
		
		Collection<List<Train>> collectionOfTrains = trains.values();
		Buildable firstWagon;
		for(List<Train> listOfTrains : collectionOfTrains) {
			for(Train train : listOfTrains) {
				firstWagon = train.getFirstWagon();
				Buildable engine = new Buildable(UUID.randomUUID().toString(),
						"engine", Buildable.Type.DECORATION_FLOOR);
				engine.setSizeX(9);
				engine.setSizeY(15);
				engine.setSizeZ(9);
				engine.setAttributes(Arrays.asList(firstWagon.getAttributes()));
				engine.setCdfNames(firstWagon.getCdfNames());
				firstWagon.getParent().addFirstChild(engine);
				firstWagon.setParent(engine);
				train.addEngine(engine);
			}
		}
		
		return trains;
	}

}
