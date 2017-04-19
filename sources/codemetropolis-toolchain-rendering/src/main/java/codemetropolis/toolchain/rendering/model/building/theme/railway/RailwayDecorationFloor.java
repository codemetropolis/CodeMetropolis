package codemetropolis.toolchain.rendering.model.building.theme.railway;

import java.util.Random;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.model.Themes;
import codemetropolis.toolchain.rendering.model.building.DecorationFloor;
import codemetropolis.toolchain.rendering.model.pattern.RepeationPattern;
import codemetropolis.toolchain.rendering.model.primitive.SimpleBox;
import codemetropolis.toolchain.rendering.util.Orientation;

/**
 * A {@link DecorationFloor} subclass for the {@link Themes#RAILWAY} theme.
 * Adding an engine at the beginning of the train.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class RailwayDecorationFloor extends DecorationFloor {

	public RailwayDecorationFloor(Buildable innerBuildable) throws BuildingTypeMismatchException {
		super(innerBuildable);
	}
	
	/**
	 * Just a simple base of the engine.
	 */
	@Override
	protected void prepareCeiling() {
		BasicBlock[][][] wagonBase = createWagonBase();
		
		primitives.add(
			new SimpleBox(
				position,
				new Point( 17, 2, 9 ),
				new RepeationPattern( wagonBase ),
				Orientation.NearY ) );
	}
	
	/**
	 * Adding a good looking engine to the beginning of the train.
	 */
	@Override
	protected void prepareRoof() {
		BasicBlock[][][] engine = createEngine();
		
		primitives.add(
			new SimpleBox(
				position.translate( new Point(1, 2, 0) ),
				new Point( 15, 7, 9 ),
				new RepeationPattern( engine ),
				Orientation.NearY ) );
	}
	
	/**
	 * Creates the whole engine.
	 * 
	 * @return The requested layer.
	 */
	private BasicBlock[][][] createEngine() {
		Random rand = new Random();
		BasicBlock O;
		try {
			O = (BasicBlock)RailwayBlocks.class.getField("WAGON_PILLAR_" + (rand.nextInt(4 - 1 + 1) + 1)).get(null);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			O = RailwayBlocks.WAGON_PILLAR_1;
		}
		BasicBlock X = RailwayBlocks.WAGON_CHIMNEY;
		BasicBlock I = RailwayBlocks.EMPTY_BLOCK;
		
		BasicBlock[][][] engine = new BasicBlock[][][]
				{
					{
						{ I, I, I, I, I, I, I, I, I, I, I, I, I, I, I },
						{ I, I, I, I, I, I, I, I, I, I, I, I, I, I, I },
						{ X, X, X, X, I, I, I, I, I, I, I, I, I, I, I },
						{ X, X, X, X, I, I, I, I, I, I, I, I, I, I, I },
						{ X, X, X, X, I, I, I, I, I, I, I, I, I, X, I },
						{ X, X, X, X, I, I, I, I, I, I, I, I, I, I, I },
						{ X, X, X, X, I, I, I, I, I, I, I, I, I, I, I },
						{ I, I, I, I, I, I, I, I, I, I, I, I, I, I, I },
						{ I, I, I, I, I, I, I, I, I, I, I, I, I, I, I }
					},
					{
						{ I, I, I, I, I, I, I, I, I, I, I, I, I, I, I },
						{ O, O, O, O, I, I, I, I, I, I, I, I, I, I, I },
						{ O, I, I, O, I, I, I, I, I, I, I, I, I, I, I },
						{ O, I, I, O, I, I, I, I, I, I, I, I, I, I, I },
						{ O, I, I, O, I, I, I, I, I, I, I, I, I, X, I },
						{ O, I, I, O, I, I, I, I, I, I, I, I, I, I, I },
						{ O, I, I, O, I, I, I, I, I, I, I, I, I, I, I },
						{ O, O, O, O, I, I, I, I, I, I, I, I, I, I, I },
						{ I, I, I, I, I, I, I, I, I, I, I, I, I, I, I }
						
					},
					{
						{ O, O, O, O, I, I, I, I, I, I, I, I, I, I, I },
						{ O, I, I, I, O, I, I, I, I, I, I, I, I, I, I },
						{ O, I, I, I, O, O, I, I, I, I, I, I, I, I, I },
						{ O, I, I, I, O, O, O, O, I, I, I, I, I, I, I },
						{ O, I, I, I, O, O, O, O, O, O, O, O, X, X, X },
						{ O, I, I, I, O, O, O, O, I, I, I, I, I, I, I },
						{ O, I, I, I, O, O, I, I, I, I, I, I, I, I, I },
						{ O, I, I, I, O, I, I, I, I, I, I, I, I, I, I },
						{ O, O, O, O, I, I, I, I, I, I, I, I, I, I, I }
						
					},
					{
						{ O, O, O, O, I, I, I, I, I, I, I, I, I, I, I },
						{ O, I, I, I, O, O, O, I, I, I, I, I, I, I, I },
						{ O, I, I, I, O, O, O, O, O, I, I, I, I, I, I },
						{ O, I, I, I, I, I, I, I, O, O, O, O, X, X, X },
						{ O, I, I, I, I, I, I, I, I, I, I, I, I, I, X },
						{ O, I, I, I, I, I, I, I, O, O, O, O, X, X, X },
						{ O, I, I, I, O, O, O, O, O, I, I, I, I, I, I },
						{ O, I, I, I, O, O, O, I, I, I, I, I, I, I, I },
						{ O, O, O, O, I, I, I, I, I, I, I, I, I, I, I }
					},
					{
						{ O, O, O, O, O, O, O, O, I, I, I, I, I, I, I },
						{ O, I, I, I, I, O, I, O, O, O, O, I, I, I, I },
						{ O, I, I, I, I, I, I, I, I, O, O, O, X, X, X },
						{ O, I, I, I, I, I, I, I, I, I, I, I, I, I, X },
						{ O, I, I, I, I, I, I, I, I, I, I, I, I, I, X },
						{ O, I, I, I, I, I, I, I, I, I, I, I, I, I, X },
						{ O, I, I, I, I, I, I, I, I, O, O, O, X, X, X },
						{ O, I, I, I, I, O, I, O, O, O, O, I, I, I, I },
						{ O, O, O, O, O, O, O, O, I, I, I, I, I, I, I }
					},
					{
						{ O, O, O, O, O, O, O, O, O, O, O, O, O, I, I },
						{ O, I, I, I, I, I, I, I, I, I, I, O, O, O, O },
						{ O, I, I, I, I, I, I, I, I, I, I, I, I, I, X },
						{ O, I, I, I, I, I, I, I, I, I, I, I, I, I, X },
						{ O, I, I, I, I, I, I, I, I, I, I, I, I, I, X },
						{ O, I, I, I, I, I, I, I, I, I, I, I, I, I, X },
						{ O, I, I, I, I, I, I, I, I, I, I, I, I, I, X },
						{ O, I, I, I, I, I, I, I, I, I, I, O, O, O, O },
						{ O, O, O, O, O, O, O, O, O, O, O, O, O, I, I }
					},
					{
						{ O, O, O, O, O, O, O, O, O, O, O, O, O, O, O },
						{ O, I, I, I, I, I, I, I, I, I, I, I, I, I, O },
						{ O, I, I, I, I, I, I, I, I, I, I, I, I, I, O },
						{ O, I, I, I, I, I, I, I, I, I, I, I, I, I, X },
						{ O, I, I, I, I, I, I, I, I, I, I, I, I, I, X },
						{ O, I, I, I, I, I, I, I, I, I, I, I, I, I, X },
						{ O, I, I, I, I, I, I, I, I, I, I, I, I, I, O },
						{ O, I, I, I, I, I, I, I, I, I, I, I, I, I, O },
						{ O, O, O, O, O, O, O, O, O, O, O, O, O, O, O }
					}
				};
				
		BasicBlock[][][] transpose = new BasicBlock[7][15][9];
		for(int i = 0; i < 15; i++) {
			for(int j = 0; j < 9; j++) {
				for(int k = 0; k < 7; k++) {
					transpose[k][i][j] = engine[k][j][i];
				}
			}
		}
		
		return transpose;
	}
	
	/**
	 * Create the base of a wagon, with an iron base and wheels.
	 * 
	 * @return The requested layer.
	 */
	private BasicBlock[][][] createWagonBase() {
		BasicBlock O = RailwayBlocks.RAIL_IRON;
		BasicBlock X = RailwayBlocks.EMPTY_BLOCK;
		
		BasicBlock[][][] base = new BasicBlock[][][]
				{
					{
						{ X, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, X },
						{ X, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, X },
						{ X, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, X },
						{ X, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O },
						{ O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, X },
						{ X, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O },
						{ X, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, X },
						{ X, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, X },
						{ X, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, X }
					},
					{
						{ X, X, O, O, X, X, X, X, X, X, X, X, X, O, O, X, X },
						{ X, X, X, X, X, X, X, X, X, X, X, X, X, X, X, X, X },
						{ X, X, X, X, X, X, X, X, X, X, X, X, X, X, X, X, X },
						{ X, X, X, X, X, X, X, X, X, X, X, X, X, X, X, X, X },
						{ X, X, X, X, X, X, X, X, X, X, X, X, X, X, X, X, X },
						{ X, X, X, X, X, X, X, X, X, X, X, X, X, X, X, X, X },
						{ X, X, X, X, X, X, X, X, X, X, X, X, X, X, X, X, X },
						{ X, X, X, X, X, X, X, X, X, X, X, X, X, X, X, X, X },
						{ X, X, O, O, X, X, X, X, X, X, X, X, X, O, O, X, X }
						
					}
				};
				
		BasicBlock[][][] transpose = new BasicBlock[2][17][9];
		for(int i = 0; i < 17; i++) {
			for(int j = 0; j < 9; j++) {
				for(int k = 0; k < 2; k++) {
					transpose[k][i][j] = base[k][j][i];
				}
			}
		}
		
		return transpose;
	}

}
