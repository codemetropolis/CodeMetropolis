package codemetropolis.toolchain.placing.layout.tetris;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.Buildable.Type;
import codemetropolis.toolchain.commons.cmxml.BuildableTree;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.commons.cmxml.comparators.BuildableDescendantLevelComparator;
import codemetropolis.toolchain.commons.cmxml.comparators.BuildableWidthComparator;
import codemetropolis.toolchain.placing.layout.Layout;

public class TetrisLayout extends Layout {
	
	private static final int DEFAULT_SPACE = 3;
	private static final int GARDEN_SIZE = 6;
	private int space = DEFAULT_SPACE;

	@Override
	public void apply(BuildableTree buildables) {
		List<Buildable> buildableList = buildables.getBuildables();
		prepareBuildables(buildableList);
		Map<Buildable, List<Buildable>> floorsByParent = new HashMap<>();
		List<Buildable> groundsAndGardens = new ArrayList<>();
		
		for (Buildable b : buildableList) {
			if(b.getParent() != null && b.getParent().getType() == Type.GARDEN) {
				if(b.getParent().getParent().getType() != Type.GARDEN) {
					if(!floorsByParent.containsKey(b.getParent())) {
						floorsByParent.put(b.getParent(), new ArrayList<Buildable>());
					}
					floorsByParent.get(b.getParent()).add(b);
				}
			} else {
				groundsAndGardens.add(b);
			}
		}
		
		for(List<Buildable> f : floorsByParent.values()) {
			placeVertical(f);	
		}
		
		Collections.sort(groundsAndGardens, new BuildableDescendantLevelComparator());
		Collections.reverse(groundsAndGardens);
		Collection<Buildable> siblings = new ArrayList<>();
		List<Buildable> alreadyPlaced = new ArrayList<>();
		
		for( Buildable b : groundsAndGardens) {
			if(!alreadyPlaced.contains(b)) {
				siblings.add(b);
				siblings.addAll(b.getSiblings());
				placeHorizontal(siblings);
				alreadyPlaced.addAll(siblings);
				siblings.clear();
			}
		}
	}
	
	public Collection<Buildable> prepareBuildables(Collection<Buildable> buildables) {
		for(Buildable b : buildables) {
			if(b.getParent() == null) {
	    		b.setPositionYR(GROUND_LEVEL);
	    	} else if(b.getType() == Type.GARDEN || b.getType() == Type.GROUND){
	    		b.setPositionYR(b.getParent().getPositionY() + 1);
	    	}
		}
		return buildables;
	}
	
	public Collection<Buildable> placeHorizontal(Collection<Buildable> buildables) {
		if(buildables.isEmpty()) return buildables;
		buildables = sortByWidthDescending(buildables);
		
		if(buildables.iterator().next().getParent() != null && buildables.iterator().next().getParent().getType() == Type.GARDEN) {
			space = GARDEN_SIZE;
		} else {
			space = DEFAULT_SPACE;
		}
		
		int width = 0;
		int height = 0;
		int newX = 0;
		int newZ = 0;
		
		NavigableMap<Integer, Integer> corners = new TreeMap<Integer, Integer>();
		corners.put(space, space);
			
		for(Buildable b : buildables) {
			int bSizeX = b.getSizeX() + space;
			int bSizeZ = b.getSizeZ() + space;
			Boolean alreadyPlaced = false;
			
			for (Map.Entry<Integer, Integer> entry : corners.entrySet()) {
				if(!alreadyPlaced) {
				    int cornerX = entry.getKey();
				    int cornerZ = entry.getValue();
				    int nextCornerX = cornerX;
				    int previousCornerX = cornerX;
				    int previousCornerZ = cornerZ;
					
					if(corners.ceilingKey(cornerX + 1) != null) {
						nextCornerX = corners.ceilingKey(cornerX + 1);
					}
					
					if(corners.floorKey(cornerX - 1) != null) {
						previousCornerX = corners.floorKey(cornerX - 1);
						previousCornerZ = corners.get(previousCornerX);
					}
					
					if(
							cornerZ < previousCornerZ &&
							Math.abs(nextCornerX - cornerX) >= bSizeX &&
							Math.abs(height - cornerZ) >= bSizeZ
					) {
						newX = cornerX;
				    	newZ = cornerZ;
				    	
				    	for(int i = corners.floorKey(newX) + 1; i < newX + bSizeX; i++) {
				    		if(corners.get(i) != null && corners.get(i) > newZ) {
				    			newZ = corners.get(i);
				    		}
				    	}
				    	
				    	if(newZ + b.getSizeZ() <= height)
				    		alreadyPlaced = true;
					} 
				}
			}
			
			if(!alreadyPlaced) {
				for (Map.Entry<Integer, Integer> entry : corners.entrySet()) {
					if(!alreadyPlaced) {
					    int cornerX = entry.getKey();
					    int cornerZ = entry.getValue();
					    int nextCornerX = cornerX;				
						
						if(corners.ceilingKey(cornerX + 1) != null) {
							nextCornerX = corners.ceilingKey(cornerX + 1);
						}
					
						while(
								corners.ceilingKey(nextCornerX + 1) != null &&
								corners.get(corners.ceilingKey(nextCornerX + 1)) <= cornerZ )
						{
							nextCornerX = corners.ceilingKey(nextCornerX + 1);
						}
						
						if(Math.abs(nextCornerX - cornerX) >= bSizeX && Math.abs(height - cornerZ) >= bSizeZ) {
							newX = cornerX;
					    	newZ = cornerZ;
					    	
					    	for(int i = corners.floorKey(newX) + 1; i < newX + bSizeX; i++) {
					    		if(corners.get(i) != null && corners.get(i) > newZ) {
					    			newZ = corners.get(i);
					    		}
					    	}
					    	
					    	if(newZ + b.getSizeZ() <= height)
					    		alreadyPlaced = true;
						}
					}
				}
			}
			
			if(!alreadyPlaced) {
				if(width > height) {
			    	newX = space;
			    	newZ = height + space;
			    	if(bSizeX > width) width = bSizeX;
			    	height += bSizeZ;
			    } else {
			    	newX = width + space;
			    	newZ = space;
			    	if(bSizeZ > height) height = bSizeZ;
			    	width += bSizeX;
			    }
			}
			
			b.setPositionXR(newX);
	    	b.setPositionZR(newZ);
	    	
	    	// Upper left and lower right
	    	if(corners.get(newX) == null || corners.get(newX) < newZ + bSizeZ)
	    		corners.put(newX, newZ + bSizeZ);
	    	if (corners.get(newX + bSizeX) == null || corners.get(newX + bSizeX) < newZ)
	    		corners.put(newX + bSizeX , newZ);
	    	
	    	Integer lastCornerZ = null;
	    	
	    	for(int i = corners.floorKey(newX) + 1; i < newX + bSizeX; i++) {
	    		if(corners.get(i) != null) {
	    			lastCornerZ = corners.get(i);
	    		}
	    	}
	    	
	    	for(int i = corners.floorKey(newX) + 1; i < newX + bSizeX; i++) {
	    		if(corners.get(i) != null) {
	    			corners.remove(i);
	    		}
	    	}
	    	
	    	if(lastCornerZ != null)
	    		if(corners.get(newX + bSizeX) == null)
	    			corners.put(newX + bSizeX , lastCornerZ);
		}
		
		if(buildables.iterator().next().getParent() != null) {
			Buildable parent = buildables.iterator().next().getParent();
			parent.setSizeX(width + space);
			parent.setSizeZ(height + space);
		}
		
		return buildables;
	}

	public Collection<Buildable> placeVertical(Collection<Buildable> buildables) {
		buildables = sortByWidthDescending(buildables);
		List<House> houses = new ArrayList<>();
		List<Buildable> gardens = new ArrayList<>();
		
		nextBuildable:
		for(Buildable b : buildables) {
			if(b.getType() == Type.GARDEN) {
				placeVertical(Arrays.asList(b.getChildren()));
				gardens.add(b);
			} else if (b.getType() == Type.FLOOR) {
				for(House h : houses) {
					if(h.addFloor(b)) {
						continue nextBuildable;
					}
				}
				House house = new House(MIN_HEIGHT, MAX_HEIGHT);
				house.addFloor(b);
				houses.add(house);
			} else if (b.getType() == Type.CELLAR) {
				for(House h : houses) {
					if(h.addCellar(b)) {
						continue nextBuildable;
					}
				}
				House house = new House(MIN_HEIGHT, MAX_HEIGHT);
				house.addCellar(b);
				houses.add(house);
			}
		}
					
		List<Object> temp = new ArrayList<>();
		temp.addAll(houses);
		temp.addAll(gardens);
		placeHorizontalInGardens(temp);
		
		return buildables;
	}
	
	private Collection<Buildable> sortByWidthDescending(Collection<Buildable> buildables) {
		List<Buildable> result = new ArrayList<>();
		result.addAll(buildables);
		Collections.sort(result, new BuildableWidthComparator());
		Collections.reverse(result);
		return result;
	}
	
	private void placeHorizontalInGardens(Collection<Object> housesAndGardens) {
		List<House> houses = new ArrayList<>();
		List<Buildable> objectsToPlace = new ArrayList<>();
		Map<Buildable, Point> realSizes = new HashMap<Buildable, Point>();
		
		for(Object o : housesAndGardens) {
			if(o.getClass() == House.class) {
				House h = (House)o;
				houses.add(h);
				Buildable bottom = h.getBottom();
				realSizes.put(bottom, new Point(bottom.getSizeX(), bottom.getSizeY(), bottom.getSizeZ()));
				bottom.setSizeX(h.getSizeX());
				bottom.setSizeZ(h.getSizeZ());
				objectsToPlace.add(bottom);
			} 
			else if(o.getClass() == Buildable.class) {
				Buildable b = (Buildable)o;
				if(b.getType() == Type.GARDEN) {
					objectsToPlace.add(b);
				}
			}
		}
		
		placeHorizontal(objectsToPlace);
		
		for(House h : houses) {
			for(Buildable b : h.getFloorsAndCellars()) {
				placeFloorHorizontal(b, h.getBottom());
			}
			h.getBottom().setSizeX(realSizes.get(h.getBottom()).getX());
			h.getBottom().setSizeZ(realSizes.get(h.getBottom()).getZ());
			placeFloorHorizontal(h.getBottom(), h.getTop());
		}	
	}
	
	private void placeFloorHorizontal(Buildable floor, Buildable bottom) {
		floor.setPositionXR(bottom.getPositionX() + bottom.getSizeX() / 2 - floor.getSizeX() / 2);
    	floor.setPositionZR(bottom.getPositionZ() + bottom.getSizeZ() / 2 - floor.getSizeZ() / 2);
	}
	
}
