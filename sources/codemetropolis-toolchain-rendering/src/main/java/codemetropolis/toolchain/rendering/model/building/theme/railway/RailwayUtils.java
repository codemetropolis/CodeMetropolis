package codemetropolis.toolchain.rendering.model.building.theme.railway;

import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.util.Orientation;

/**
 * Some frequently used functions for Railway theme. So source code is clearer.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class RailwayUtils {

	/**
	 * Transpose matrices which can't be handled by {@link Orientation}.
	 * 
	 * @param blocks The blocks which have to be transposed.
	 * @return The transposed matrix.
	 */
	public static BasicBlock[][][] transpose(BasicBlock[][][] blocks) {
		int xLength = blocks.length;
		int yLength = blocks[0][0].length;
		int zLength = blocks[0].length;

		BasicBlock[][][] transpose = new BasicBlock[xLength][yLength][zLength];

		for (int x = 0; x < yLength; x++) {
			for (int y = 0; y < zLength; y++) {
				for (int z = 0; z < xLength; z++) {
					transpose[z][x][y] = blocks[z][y][x];
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
	public static BasicBlock[][][] createWagonBase(int sizeX, int sizeY, int sizeZ) {
		BasicBlock[][][] base = new BasicBlock[sizeX][sizeY][sizeZ];

		for (int k = 0; k < sizeX; k++) {
			for (int j = 0; j < sizeY; j++) {
				for (int i = 0; i < sizeZ; i++) {
					base[1][j][i] = RailwayBlocks.EMPTY_BLOCK;
					base[0][j][i] = RailwayBlocks.RAIL_IRON;
				}
			}
		}

		for (int i = 0; i < sizeY; i++) {
			base[0][i][0] = RailwayBlocks.EMPTY_BLOCK;
			base[0][i][sizeZ - 1] = RailwayBlocks.EMPTY_BLOCK;
		}

		base[0][sizeY / 2][0] = RailwayBlocks.RAIL_IRON;
		base[0][sizeY / 2][sizeZ - 1] = RailwayBlocks.RAIL_IRON;
		base[1][0][2] = RailwayBlocks.RAIL_IRON;
		base[1][sizeY - 1][2] = RailwayBlocks.RAIL_IRON;
		base[1][0][sizeZ - 3] = RailwayBlocks.RAIL_IRON;
		base[1][sizeY - 1][sizeZ - 3] = RailwayBlocks.RAIL_IRON;

		return RailwayUtils.transpose(base);
	}

}
