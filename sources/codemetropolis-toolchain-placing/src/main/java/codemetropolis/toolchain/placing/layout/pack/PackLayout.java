package codemetropolis.toolchain.placing.layout.pack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import codemetropolis.toolchain.commons.cmxml.Attribute;
import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.BuildableTree;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.commons.cmxml.comparators.BuildableSizeComparator;
import codemetropolis.toolchain.placing.exceptions.LayoutException;
import codemetropolis.toolchain.placing.layout.Layout;
import codemetropolis.toolchain.placing.layout.pack.RectanglePacker.Rectangle;

public class PackLayout extends Layout {
	
	public static final int LINKING_WIDTH = 2;
	public static final String LINKING_ATTRIBUTE_TARGET = "target";
	
	private final int SPACE = 3;
	
	@Override
	public void apply(BuildableTree buildables) throws LayoutException {
		prepareBuildables(buildables);
		Map<Buildable, List<House>> houses = createHouses(buildables);
		BuildableWrapper root = new BuildableWrapper(buildables.getRoot());
		packRecursive(root, houses);
		
		makeLinkings(buildables);
	}
	
	private void makeLinkings(BuildableTree buildables) {
		
		List<Buildable> extendedBuildables = new ArrayList<Buildable>();
		
		for(Buildable b : buildables.getBuildables()) {
			
			if (b.getType() != Buildable.Type.TUNNEL && b.getType() != Buildable.Type.BRIDGE) {
				continue;
			}
			
			Buildable parent = b.getParent();
			String id = b.getAttributeValue(LINKING_ATTRIBUTE_TARGET);
			
			if (id == null) {
				continue;
			}
			
			Buildable target = buildables.getBuildable(id);
			
			if (target == null) {
				continue;
			}
			
			if(target.getId() == parent.getId()) {
				buildables.getBuildables().remove(b);
			}

			Point parentCenter = new Point(parent.getPositionX() + parent.getSizeX()/2, 0, parent.getPositionZ() + parent.getSizeZ()/2);
			Point targetCenter = new Point(target.getPositionX() + target.getSizeX()/2, 0, target.getPositionZ() + target.getSizeZ()/2);
			
			if (parentCenter.getX() == targetCenter.getX()) {
				
				b.setPositionX(parentCenter.getX() - LINKING_WIDTH/2);
				b.setSizeX(LINKING_WIDTH);
				
				int distance = targetCenter.getZ() - parentCenter.getZ();
				
				if (parentCenter.getZ() < targetCenter.getZ()) {
					// P - parent, T - target, X - undefined
					// Position:
					// X X X
					// X P X
					// X T X
					// X X X
					
					b.setPositionZ(parentCenter.getZ());
					b.setSizeZ(distance);
					
					b.addAttribute(new Attribute("standalone", "true"));
					b.addAttribute(new Attribute("orientation", "SOUTH"));
					
				} else {
					// Position:
					// X X X
					// X T X
					// X P X
					// X X X
					
					b.setPositionZ(targetCenter.getZ());
					b.setSizeZ(-distance);
					
					b.addAttribute(new Attribute("standalone", "true"));
					b.addAttribute(new Attribute("orientation", "NORTH"));
					
				}
			} else if (parentCenter.getZ() == targetCenter.getZ()) {
				
				b.setPositionZ(parentCenter.getZ() - LINKING_WIDTH/2);
				b.setSizeZ(LINKING_WIDTH);
				
				int distance = targetCenter.getX() - parentCenter.getX();
				
				if (parent.getPositionX() < target.getPositionX()) {
					// Position:
					// X X X X
					// X P T X
					// X X X X
					
					b.setPositionX(parentCenter.getX());
					b.setSizeX(distance);
					
					b.addAttribute(new Attribute("standalone", "true"));
					b.addAttribute(new Attribute("orientation", "EAST"));
					
				} else {
					// Position:
					// X X X X
					// X T P X
					// X X X X
					
					b.setPositionX(targetCenter.getX());
					b.setSizeX(-distance);
					
					b.addAttribute(new Attribute("standalone", "true"));
					b.addAttribute(new Attribute("orientation", "WEST"));
					
				}
			} else if (parentCenter.getX() > targetCenter.getX()) {
				
				b.setPositionZ(parentCenter.getZ() - LINKING_WIDTH/2);
				b.setSizeZ(LINKING_WIDTH);
				
				b.setPositionX(targetCenter.getX() - LINKING_WIDTH/2);
				b.setSizeX(parentCenter.getX() - targetCenter.getX() + LINKING_WIDTH);
				
				b.addAttribute(new Attribute("standalone", "false"));
				b.addAttribute(new Attribute("orientation", "WEST"));
				
				Buildable new_b;
				if (b.getType() == Buildable.Type.TUNNEL) {
					new_b = new Buildable(UUID.randomUUID().toString(), "", Buildable.Type.TUNNEL);
				} else {
					new_b = new Buildable(UUID.randomUUID().toString(), "", Buildable.Type.BRIDGE);
				}
				
				new_b.setPositionX(targetCenter.getX() - LINKING_WIDTH/2);
				new_b.setSizeX(LINKING_WIDTH);
				
				int distance = parentCenter.getZ() - targetCenter.getZ();
				
				if(parentCenter.getZ() > targetCenter.getZ()) {
					// Position:
					// X X X X
					// X T X X
					// X X P X
					// X X X X
					
					new_b.setPositionZ(targetCenter.getZ());
					new_b.setSizeZ(distance + LINKING_WIDTH/2);
					new_b.addAttribute(new Attribute("orientation", "SOUTH"));
					
				} else {
					// Position:
					// X X X X
					// X X P X
					// X T X X
					// X X X X
					
					new_b.setPositionZ(parentCenter.getZ() - LINKING_WIDTH/2);
					new_b.setSizeZ(-distance + LINKING_WIDTH/2);
					new_b.addAttribute(new Attribute("orientation", "NORTH"));

				}
				
				new_b.addAttribute(new Attribute("standalone", "false"));
				
				extendedBuildables.add(new_b);
				
				target.addChild(new_b);
				
			} else {
				b.setPositionZ(parentCenter.getZ() - LINKING_WIDTH/2);
				b.setSizeZ(LINKING_WIDTH);
				
				b.setPositionX(parentCenter.getX() - LINKING_WIDTH/2);
				b.setSizeX(targetCenter.getX() - parentCenter.getX() + LINKING_WIDTH);
				
				b.addAttribute(new Attribute("standalone", "false"));
				b.addAttribute(new Attribute("orientation", "EAST"));
				
				Buildable new_b;
				if (b.getType() == Buildable.Type.TUNNEL) {
					new_b = new Buildable(UUID.randomUUID().toString(), "", Buildable.Type.TUNNEL);
				} else {
					new_b = new Buildable(UUID.randomUUID().toString(), "", Buildable.Type.BRIDGE);
				}
				
				new_b.setPositionX(targetCenter.getX() - LINKING_WIDTH/2);
				new_b.setSizeX(LINKING_WIDTH);
				
				int distance = targetCenter.getZ() - parentCenter.getZ();
				
				if (parentCenter.getZ() > targetCenter.getZ()) {
					// Position:
					// X X X X
					// X X T X
					// X P X X
					// X X X X
					
					new_b.setPositionZ(targetCenter.getZ());
					new_b.setSizeZ(-distance + LINKING_WIDTH/2);
					new_b.addAttribute(new Attribute("orientation", "SOUTH"));

				} else {
					// Position:
					// X X X X
					// X P X X
					// X X T X
					// X X X X
					
					new_b.setPositionZ(parentCenter.getZ() - LINKING_WIDTH/2);
					new_b.setSizeZ(distance + LINKING_WIDTH/2);
					new_b.addAttribute(new Attribute("orientation", "NORTH"));
					
				}
				
				new_b.addAttribute(new Attribute("standalone", "false"));
				
				extendedBuildables.add(new_b);
				
				target.addChild(new_b);
			}
		}
		buildables.getBuildables().addAll(extendedBuildables);
	}
	
	
	private void prepareBuildables(BuildableTree buildables) {
		for(Buildable b : buildables.getBuildables()) {
				if(b.isRoot()) continue;
				b.setPositionYR(b.getParent().getPositionY() + 1);
		}
		
		buildables.getRoot().setPositionYR(GROUND_LEVEL);
	}
	
	private Point getMaxSizes(Collection<BuildableWrapper> buildables) {
		int maxX = 0, maxZ = 0;
		for(BuildableWrapper b : buildables) {
			if(b.getSizeX() > maxX) maxX = b.getSizeX();
			if(b.getSizeZ() > maxZ) maxZ = b.getSizeZ();
		}
		return new Point(maxX, 0, maxZ);
	}
	
	public void packRecursive(BuildableWrapper root, Map<Buildable, List<House>> houses) {
		List<BuildableWrapper> tempChildren = root.getChildren(houses);
		List<BuildableWrapper> children = new ArrayList<BuildableWrapper>();
		
		for(BuildableWrapper c : tempChildren) {
			if (c.buildable instanceof Buildable && (((Buildable)c.buildable).getType() == Buildable.Type.TUNNEL || ((Buildable)c.buildable).getType() == Buildable.Type.BRIDGE)) {
				continue;
			} 
			children.add(c);
		}
		
		for(BuildableWrapper c : children) {
			
			if(!c.getChildren(houses).isEmpty()) {
				packRecursive(c, houses);
			}
		}
		Collections.sort(children);
		Collections.reverse(children);
		pack(children, SPACE);
	}
	
	private void pack(Collection<BuildableWrapper> buildables, int space) {
		Point startingSize = getMaxSizes(buildables);
		pack(buildables, startingSize.getX(), startingSize.getZ(), space);
	}
	
	private void pack(Collection<BuildableWrapper> buildables, int sizeX, int sizeZ, int space) {
		RectanglePacker<BuildableWrapper> packer = new RectanglePacker<BuildableWrapper>(sizeX, sizeZ, space);
		
		for(BuildableWrapper b : buildables) {
			if(packer.insert(b.getSizeX(), b.getSizeZ(), b) == null) {
				if(sizeX > sizeZ) {
					sizeZ++;
				} else {
					sizeX++;
				}
				pack(buildables, sizeX, sizeZ,  space);
				return;
			};
		}

		for(BuildableWrapper b : buildables) {
			Rectangle r = packer.findRectangle(b);
			b.setPositionX(r.x + space);
			b.setPositionZ(r.y + space);
		}
		
		if(!buildables.isEmpty()) {
			Point parentSize = calculateParentSize(buildables, space);
			Buildable parent = buildables.iterator().next().getParent();
			parent.setSizeX(parentSize.getX());
			parent.setSizeZ(parentSize.getZ());
		}
	}
	
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
	
	private void sort(List<Buildable> buildables) {
		Collections.sort(buildables, new BuildableSizeComparator());
		Collections.reverse(buildables);
	}
	
	private List<Buildable> getFloorsAndCellarsSorted(BuildableTree buildables) {
		List<Buildable> result = new ArrayList<Buildable>();
		for(Buildable b : buildables.getBuildables()) {
			if(b.getType() != Buildable.Type.FLOOR && b.getType() != Buildable.Type.CELLAR) continue;
			result.add(b);
		}
		sort(result);
		return result;
	}
	
	private Map<Buildable, List<House>> createHouses(BuildableTree buildables) throws LayoutException {
		Map<Buildable, List<House>> houses = new HashMap<Buildable, List<House>>();
		for(Buildable b : getFloorsAndCellarsSorted(buildables)) {
			if(b.getType() != Buildable.Type.FLOOR && b.getType() != Buildable.Type.CELLAR) continue;
			List<House> housesOfParent = houses.get(b.getParent());
			if(housesOfParent == null) {
				housesOfParent = new ArrayList<House>();
				houses.put(b.getParent(), housesOfParent);
			}
			
			boolean addedSuccessfully = false;
			for(House h : housesOfParent) {
				if(h.add(b)) {
					addedSuccessfully = true;
					break;
				}
			}
			
			if(!addedSuccessfully) {
				House h = new House(MIN_HEIGHT, MAX_HEIGHT);
				h.add(b);
				housesOfParent.add(h);
			}
		}
		return houses;
	}
}
