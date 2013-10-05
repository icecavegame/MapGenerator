package com.java.icecaveMapGenerator.utils.bundle;

import com.java.icecaveMapGenerator.configFile.IIceCaveMapConfigFile;
import com.java.icecaveMapGenerator.general.EDifficulty;
import com.java.icecaveMapGenerator.general.EDirection;
import com.java.icecaveMapGenerator.utils.Point;

/**
 * Base implementation of the IBundleMetaData.
 * @author Tom
 *
 */
public class BaseBundleMetaData implements IBundleMetaData
{
	/**
	 * Width of the board in tiles.
	 */
	private int mBoardWidth;
	
	/**
	 * Height of the board in tiles.
	 */
	private int mBoardHeight;
	
	/**
	 * Difficulty of the map.
	 */
	private EDifficulty mDifficulty;
	
	/**
	 * The width of the board wall in tiles.
	 */
	private int mWallWidth;
	
	/**
	 * The starting player on the board.
	 */
	private Point mPlayerStart;
	
	/**
	 * Number of boulders in the map.
	 */
	private int mBouldersNum;
	
	/**
	 * Version of the map.
	 */
	private String mVersion;
	
	/**
	 * The starting move of the player on the map.
	 */
	private EDirection mStartingMove;
	
	/**
	 * Create a new instance of the BaseBunleMetaData object.
	 * @param playerStart - Starting location of the player.
	 * @param difficulty - Difficulty of the map.
	 * @param firstMove - The first move to take on the board.
	 * @param tilesHeight - Number of tiles in height 
	 * 					   (width will be calculated with resolution).
	 * @param tilesWidth - Number of tiles in width.
	 * @param boulderNum - number of boulders.
	 * @param version - Version of the map.
	 * @param wallWidth - Width of the wall in tiles.
	 */
	public BaseBundleMetaData(Point playerStart,
	                          EDifficulty difficulty,
	                          EDirection firstMove,
	                          int tilesHeight,
	                          int tilesWidth,
	                          int boulderNum,
	                          String version,
	                          int wallWidth)
	{
		mWallWidth = wallWidth;
		mBoardHeight = tilesHeight;
		mBoardWidth = tilesWidth;
		mBouldersNum = boulderNum;
		mVersion = new String(version);
		mStartingMove = firstMove;
		mDifficulty = difficulty;
		mPlayerStart = new Point(playerStart);
	}
	
	/**
	 * Create a new BaseBundleMetaData instance.
	 * @param configFile - Config file to get paramters from.
	 */
	public BaseBundleMetaData(IIceCaveMapConfigFile configFile)
	{
		mPlayerStart = new Point(configFile.getPlayerStartLocation());
		mDifficulty = configFile.getDifficulty();
		mStartingMove = configFile.getStartingMove();
		mBoardHeight = configFile.getBoardHeight();
		mBoardWidth = configFile.getBoardWidth(); 
		mBouldersNum = configFile.getBoulderNum(); 
		mVersion = configFile.getVersion();
	    mWallWidth = configFile.getWallWidth();
	}


	@Override
	public int getBoulderNum()
	{
		return mBouldersNum;
	}

	@Override
	public int getBoardWidth()
	{
		return mBoardWidth;
	}

	@Override
	public int getBoardHeight()
	{
		return mBoardHeight;
	}

	@Override
	public int getWallWidth()
	{
		return mWallWidth;
	}

	@Override
	public String getVersion()
	{
		return mVersion;
	}

	@Override
	public EDifficulty getDifficulty()
	{
		return mDifficulty;
	}

	@Override
	public EDirection getFirstMove()
	{
		return mStartingMove;
	}

	@Override
	public Point getPlayerStart()
	{
		return mPlayerStart;
	}
}
