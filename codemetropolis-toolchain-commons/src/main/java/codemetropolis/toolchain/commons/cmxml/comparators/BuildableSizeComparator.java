package codemetropolis.toolchain.commons.cmxml.comparators;

import java.util.Comparator;

import codemetropolis.toolchain.commons.cmxml.Buildable;

public class BuildableSizeComparator implements Comparator<Buildable> {

	@Override
	public int compare(Buildable b1, Buildable b2) {
		int result = b1.getSizeX() - b2.getSizeX();
		return result == 0 ? b1.getSizeZ() - b2.getSizeZ() : result;
	}

}
