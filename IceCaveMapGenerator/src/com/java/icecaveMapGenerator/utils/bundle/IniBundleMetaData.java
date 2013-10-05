package com.java.icecaveMapGenerator.utils.bundle;

import com.tas.icecaveLibrary.utils.IIceCaveMapConfigFile;
import com.tas.icecaveLibrary.utils.Point;
import com.tas.icecaveLibrary.utils.bundle.BaseBundleMetaData;

/**
 * A bundle meta data initialized with Ini files.
 * @author Tom
 *
 */
public class IniBundleMetaData extends BaseBundleMetaData
{
	/**
	 * Create a new BaseBundleMetaData instance.
	 * @param configFile - Config file to get paramters from.
	 */
	public IniBundleMetaData(IIceCaveMapConfigFile configFile)
	{
		super(new Point(configFile.getPlayerStartLocation()),
				configFile.getDifficulty(),
				configFile.getStartingMove(),
				configFile.getBoardHeight(),
				configFile.getBoardWidth(),
				configFile.getBoulderNum(),
				configFile.getVersion(),
				configFile.getWallWidth());
	}
}
