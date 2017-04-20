package codemetropolis.toolchain.placing.layout;

import codemetropolis.toolchain.commons.cmxml.BuildableTree;
import codemetropolis.toolchain.placing.exceptions.LayoutException;
import codemetropolis.toolchain.placing.exceptions.NonExistentLayoutException;
import codemetropolis.toolchain.placing.layout.pack.PackLayout;
import codemetropolis.toolchain.placing.layout.railway.RailwayLayout;
//import codemetropolis.toolchain.placing.layout.tetris.TetrisLayout;
import codemetropolis.toolchain.placing.layout.town.TownLayout;

public abstract class Layout {

	protected static final int GROUND_LEVEL = 60;
	protected static final int MIN_HEIGHT = 10;
	protected static final int MAX_HEIGHT = 200;
	
	public static Layout parse(String algorithm) throws NonExistentLayoutException {
		try{
			switch(LayoutAlgorithm.valueOf(algorithm.toUpperCase())) {
				case PACK:
					return new PackLayout();
				case RAILWAY:
					return new RailwayLayout();
				case TOWN:
					return new TownLayout();
				//TODO tetris layout is out of date and needs to be updated
				//case TETRIS:
				//	return new TetrisLayout();
			}	
		} catch (IllegalArgumentException e) {
			throw new NonExistentLayoutException(algorithm);
		}
		return null;
	}
	
	public abstract void apply(BuildableTree buildables) throws LayoutException;
	
}
