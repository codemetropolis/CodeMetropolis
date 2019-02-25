package codemetropolis.toolchain.placing.layout.railway;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.BuildableTree;
import codemetropolis.toolchain.placing.exceptions.LayoutException;
import codemetropolis.toolchain.placing.layout.Layout;
import codemetropolis.toolchain.placing.layout.LayoutUtils;

/**
 * An easy, clear representation of {@link Layout}. Each garden is represented as a rail, with trains on top of them.
 * Rails are laid next to each other.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class RailwayLayout extends Layout {

	private static final int GROUND_LEVEL = 61;

	/**
	 * An easier, clearer representation of Gardens, without wrapper.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void apply(BuildableTree buildables) throws LayoutException {
		createTrains(buildables);
		
		LayoutUtils.preparePackages(buildables.getRoot(), buildables.getRoot());
		LayoutUtils.removeEmptyPackages(buildables.getRoot());
		LayoutUtils.prepareGardens(buildables.getRoot());
		
		findGardens(buildables.getRoot(), 0);
	}

	/**
	 * A recursive function for discovering all of the gardens in {@link BuildableTree}. Whenever a garden is found a
	 * rail is laid down, and all the houses are laid on them like wagons. The next garden is laid next to the previous
	 * one.
	 * 
	 * @param node The current {@link Buildable}.
	 * @param position For setting the position of each rail.
	 * @return With the {@code position}, so next rail will be next to the previous one.
	 */
	private int findGardens(Buildable node, int position) {
		if (node.getType() == Buildable.Type.CONTAINER || node.getType() == Buildable.Type.GROUND) {
			node.setSizeX(1);
			node.setSizeY(11);
			node.setSizeZ(11 * node.getChildrenList().size() + 5 * (node.getChildrenList().size() - 1));
			node.setPositionX(0);
			node.setPositionY(GROUND_LEVEL);
			node.setPositionZ(position * 16);
		}

		Buildable[] children = node.getChildren();
		for (Buildable child : children) {
			if (child.getType() == Buildable.Type.GARDEN) {
				layDownRail(child, position);
				position++;
			} else {
				position = findGardens(child, position);
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
		int previous = 2;
		for (Buildable wagon : rail.getChildren()) {
			wagon.setSizeX(wagon.getSizeY());
			wagon.setSizeY(9);
			wagon.setSizeZ(9);
			wagon.setPositionX(previous + 2);
			wagon.setPositionY(GROUND_LEVEL + 2);
			wagon.setPositionZ(position * 16 + 1);
			previous = wagon.getPositionX() + wagon.getSizeX();
		}

		rail.setPositionX(0);
		rail.setPositionY(GROUND_LEVEL);
		rail.setPositionZ(position * 16);
		rail.setSizeX(previous + (previous % 3));
		rail.setSizeY(2);
		rail.setSizeZ(11);
	}

	/**
	 * Collecting all the train elements. It collects a city's elements if it is a {@link Buildable.Type#FLOOR}, a
	 * {@link Buildable.Type#CELLAR} or a {@link Buildable.Type#DECORATION_FLOOR}.
	 * 
	 * @param buildables All the elements of the city.
	 * @return A list of the wagons of one train.
	 */
	private List<Buildable> getTrains(BuildableTree buildables) {
		List<Buildable> result = new ArrayList<Buildable>();

		for (Buildable b : buildables.getBuildables()) {
			if (b.getType() == Buildable.Type.FLOOR ||
				b.getType() == Buildable.Type.DECORATION_FLOOR ||
				b.getType() == Buildable.Type.CELLAR) {

				result.add(b);
			}
		}

		return result;
	}

	/**
	 * Creates a train by putting all the wagons one-by-one after each other. Also adds at the beginning of the train an
	 * engine.
	 * 
	 * @param buildables All the train wagons are added in this {@link BuildableTree}.
	 * @return The created list of wagons, as a train.
	 * @throws LayoutException Throws exception, if wagon's type is incorrect.
	 */
	private Map<Buildable, List<Train>> createTrains(BuildableTree buildables) throws LayoutException {
		Map<Buildable, List<Train>> trains = new HashMap<Buildable, List<Train>>();
		for (Buildable b : getTrains(buildables)) {
			List<Train> trainsOfParent = trains.get(b.getParent());
			if (trainsOfParent == null) {
				trainsOfParent = new ArrayList<Train>();
				trains.put(b.getParent(), trainsOfParent);
			}

			boolean addedSuccessfully = false;
			for (Train t : trainsOfParent) {
				if (t.addWagon(b)) {
					addedSuccessfully = true;
					break;
				}
			}

			if (!addedSuccessfully) {
				Train t = new Train();
				t.addWagon(b);
				trainsOfParent.add(t);
			}
		}

		addEngines(trains);

		return trains;
	}

	/**
	 * Adds an engine at the beginning of the train.
	 * 
	 * @param houses The trains.
	 */
	private void addEngines(Map<Buildable, List<Train>> trains) {
		for (List<Train> listOfTrains : trains.values()) {
			for (Train train : listOfTrains) {
				Buildable firstWagon = train.getFirstWagon();

				Buildable engine = new Buildable(UUID.randomUUID().toString(), "engine",
					Buildable.Type.DECORATION_FLOOR);
				engine.setSizeX(9);
				engine.setSizeY(15);
				engine.setSizeZ(9);
				engine.setAttributes(Arrays.asList(firstWagon.getAttributes()));
				engine.setCdfNames(firstWagon.getCdfNames());
				firstWagon.getParent().addFirstChild(engine);
				firstWagon.setParent(engine);

				try {
					train.addEngine(engine);
				} catch (LayoutException e) {
					// Should not happen, as we explicitly add a Buildable.Type.DECORATION_FLOOR
				}
			}
		}
	}

}
