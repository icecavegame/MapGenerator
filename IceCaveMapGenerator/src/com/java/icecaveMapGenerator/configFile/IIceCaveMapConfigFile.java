package com.java.icecaveMapGenerator.configFile;

import com.java.icecaveMapGenerator.general.EDifficulty;
import com.java.icecaveMapGenerator.general.EDirection;
import com.java.icecaveMapGenerator.utils.Point;

public interface IIceCaveMapConfigFile
{
	/**
	 * Get the number of boulders.
	 * @return
	 */
	int getBoulderNum();
	
	/**
	 * Get the difficulty.
	 * @return
	 */
	EDifficulty getDifficulty();
	
	/**
	 * Get the game version.
	 * @return
	 */
	String getVersion();
	
	int getBoardWidth();
	int getBoardHeight();
	int getWallWidth();
	Point getPlayerStartLocation();
	EDirection getStartingMove();
}
