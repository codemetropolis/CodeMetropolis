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

public class RailwayLayout extends Layout {
	
	private final int GROUND_LEVEL = 61;

	@Override
	public void apply(BuildableTree buildables) throws LayoutException {
		createTrains(buildables);
		findGardens(buildables.getRoot(), 0);
	}
	
	private int findGardens(Buildable root, int position) {
		if(root.getType() == Buildable.Type.CONTAINER || root.getType() == Buildable.Type.GROUND) {
			root.setPositionY(GROUND_LEVEL);
		}
		Buildable[] children = root.getChildren();
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

	private List<Buildable> getTrains(BuildableTree buildables) {
		List<Buildable> result = new ArrayList<Buildable>();
		for(Buildable b : buildables.getBuildables()) {
			if(b.getType() != Buildable.Type.FLOOR && b.getType() != Buildable.Type.CELLAR &&
					b.getType() != Buildable.Type.DECORATION_FLOOR) continue;
			result.add(b);
		}
		return result;
	}

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
