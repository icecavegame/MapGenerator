package com.java.icecaveMapGenerator.configFile;

import com.tas.icecaveLibrary.general.EDifficulty;
import com.tas.icecaveLibrary.general.EDirection;
import com.tas.icecaveLibrary.utils.Point;

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
