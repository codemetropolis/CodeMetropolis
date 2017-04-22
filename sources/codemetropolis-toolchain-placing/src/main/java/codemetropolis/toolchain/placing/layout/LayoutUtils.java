package codemetropolis.toolchain.placing.layout;

import java.util.List;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.BuildableTree;
import codemetropolis.toolchain.placing.layout.railway.RailwayLayout;
import codemetropolis.toolchain.placing.layout.town.TownLayout;

/**
 * Frequently used preparing functions for the {@link RailwayLayout} and the {@link TownLayout}.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class LayoutUtils {

	private static final String ROOT_PACKAGE = "<root_package>";

	/**
	 * Collect all the packages, and put them as a child of the root recursively.
	 * 
	 * @param node The current node, where we're searching for packages.
	 * @param root The root where to packages will be added.
	 */
	public static void preparePackages(Buildable node, Buildable root) {
		List<Buildable> children = node.getChildrenList();
		for (int i = 0; i < children.size(); i++) {
			Buildable child = children.get(i);
			if (child.getType() == Buildable.Type.GROUND && child.getParent().getType() != Buildable.Type.CONTAINER) {
				if (!child.getParent().getName().equals(ROOT_PACKAGE)) {
					child.setName(child.getParent().getName() + "." + child.getName());
				}

				children.remove(child);
				child.setParent(null);
				root.addChild(child);
				i--;
			}

			preparePackages(child, root);
		}
	}

	/**
	 * Removing from the package list the empty ones, where rails can't be found.
	 * 
	 * @param node The node whose children we're iterating over.
	 */
	public static void removeEmptyPackages(Buildable node) {
		List<Buildable> children = node.getChildrenList();
		for (int i = 0; i < children.size(); i++) {
			Buildable child = children.get(i);
			if (child.getType() == Buildable.Type.GROUND && child.getChildrenList().isEmpty()) {
				children.remove(child);
				i--;
			}
		}
	}

	/**
	 * Collecting gardens to the same level in {@link BuildableTree} hierarchy.
	 * 
	 * @param node The current node.
	 */
	public static void prepareGardens(Buildable node) {
		List<Buildable> children = node.getChildrenList();
		for (int i = 0; i < children.size(); i++) {
			Buildable child = children.get(i);
			if (child.getType() == Buildable.Type.GARDEN && child.getParent().getType() == Buildable.Type.GARDEN) {
				child.setName(child.getParent().getName() + "#" + child.getName());
				children.remove(child);
				child.setParent(child.getParent().getParent());
				child.getParent().addChild(child);
				i--;
			}
			prepareGardens(child);
		}
	}

}
