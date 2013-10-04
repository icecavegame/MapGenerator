package com.java.icecaveMapGenerator.configFile;

import java.io.IOException;
import java.util.prefs.Preferences;

import com.java.icecaveMapGenerator.general.EDifficulty;
import com.java.icecaveMapGenerator.general.EDirection;
import com.java.icecaveMapGenerator.utils.IniFile;
import com.java.icecaveMapGenerator.utils.Point;

/**
 * Class used to parse the IceCave config files.
 * @author Tom
 *
 */
public class IceCaveMapIniConfigFile implements IIceCaveMapConfigFile
{
	IniFile mIniFile;
	
	/**
	 * Create a new instance of the IceCaveMapConfigFile object.
	 * 
	 *  @param iniFilePath - Path to the INI file.
	 * @throws IOException 
	 */
	public IceCaveMapIniConfigFile(String iniFilePath) throws IOException
	{
		mIniFile = new IniFile(iniFilePath);
	}

	@Override
	public int getBoulderNum()
	{
		return mIniFile.getInt("BOARD", "boulders", getBoardHeight() * getBoardWidth() / 7);
	}

	@Override
	public EDifficulty getDifficulty()
	{
		String enumString = mIniFile.getString("BOARD", "difficulty", "");

		for (EDifficulty difficulty : EDifficulty.values())
		{
			if(enumString.toLowerCase().equals(difficulty.name().toLowerCase()))
			{
				return difficulty;
			}
		}
		
		return null;
	}

	@Override
	public String getVersion()
	{
		return mIniFile.getString("GENERAL", "version", "no version");
	}

	@Override
	public int getBoardWidth()
	{
		return mIniFile.getInt("BOARD", "width", 10);
	}

	@Override
	public int getBoardHeight()
	{
		return mIniFile.getInt("BOARD", "height", 10);
	}

	@Override
	public int getWallWidth()
	{
		return mIniFile.getInt("BOARD", "wall_width", 1);
	}

	@Override
	public Point getPlayerStartLocation()
	{
		int x = mIniFile.getInt("PLAYER", "start_x", 1);
		int y = mIniFile.getInt("PLAYER", "start_y", 1);
		
		return new Point(x,y);
	}

	@Override
	public EDirection getStartingMove()
	{
		String enumString = mIniFile.getString("BOARD", "direction", "");

		for (EDirection direction : EDirection.values())
		{
			if(enumString.toLowerCase().equals(direction.name().toLowerCase()))
			{
				return direction;
			}
		}
		
		return null;
	}
}
