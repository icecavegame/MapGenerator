package com.java.icecaveMapGenerator.utils.bundle;

import com.java.icecaveMapGenerator.general.EDifficulty;
import com.java.icecaveMapGenerator.general.EDirection;
import com.java.icecaveMapGenerator.utils.Point;

/**
 * Interface for bundle meta data.
 * @author Tom
 *
 */
public interface IBundleMetaData
{
	/**
	 * Get the number of boulders on the board.
	 * @return The number of boulders on the board.
	 */
	int getBoulderNum();
	
	/**
	 * Get the width of the board.
	 * @return The width of the board in tiles.
	 */
	int getBoardWidth();
	
	/**
	 * Get the height of the board.
	 * @return The height of the board in tiles.
	 */
	int getBoardHeight();
	
	/**
	 * Get the width of walls of the board.
	 * @return The width of walls of the board in tiles.
	 */
	int getWallWidth();
	
	/**
	 * Get the map version.
	 * @return The map version.
	 */
	String getVersion();
	
	/**
	 * Get the map difficulty.
	 * @return The map difficulty.
	 */
	EDifficulty getDifficulty();
	
	/**
	 * Get the first move.
	 * @return The first move.
	 */
	EDirection getFirstMove();
	
	/**
	 * Get the starting location of the player.
	 * @return The Starting location of the player.
	 */
	Point getPlayerStart();
}
