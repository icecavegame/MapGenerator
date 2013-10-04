package com.java;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import com.java.icecaveMapGenerator.MapWriter;
import com.java.icecaveMapGenerator.configFile.IceCaveMapIniConfigFile;
import com.java.icecaveMapGenerator.utils.IniFile;

public class IceCaveMapGenerator
{

	/**
	 * @param args
	 * @throws IOException 
	 * @throws NoSuchAlgorithmException 
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException
	{
		final String MAP_CONFIG_FILES_SECTION = "CONFIG_FILES";
		final int OUTPUT_PATH_ARGS_INDEX = 0;
		final int CONFIG_FILE_INDEX = 1;
		
		if(args.length != CONFIG_FILE_INDEX + 1){
			printHelp();
			return;
		}
		
		// Parse the config file.
		IniFile configFile = new IniFile(args[CONFIG_FILE_INDEX]);
		ArrayList<String> mapConfigFiles = configFile.getSections(MAP_CONFIG_FILES_SECTION);
		
		// Initialize the map writer.
		MapWriter writer =
				new MapWriter(args[OUTPUT_PATH_ARGS_INDEX]);
		
		for (String mapConfig : mapConfigFiles)
		{
			IceCaveMapIniConfigFile iniConfigFile;
			int numOfMapsToGenerate;
			try
			{
				iniConfigFile =
						new IceCaveMapIniConfigFile(mapConfig);
				
				numOfMapsToGenerate = 
						configFile.getInt(MAP_CONFIG_FILES_SECTION, 
										  mapConfig,
										  -1);
				
				if(numOfMapsToGenerate == -1){
					continue;
				}
						
			}
			catch(Exception e)
			{
				continue;
			}
			
			writer.generateMaps(iniConfigFile.getBoulderNum(),
								iniConfigFile.getBoardWidth(),
								iniConfigFile.getBoardHeight(),
								iniConfigFile.getDifficulty(),
								numOfMapsToGenerate,
								iniConfigFile.getPlayerStartLocation(),
								iniConfigFile.getWallWidth(),
								iniConfigFile.getStartingMove(),
								iniConfigFile.getVersion());
		}
	}

	private static void printHelp()
	{
		System.out.println("Ice cave map generator version  1.0");
		System.out.println("- - - - - - - - - - - - - - - - - -");
		System.out.println();
		System.out.println("Usage: outputPath version configFile");
		System.out.println();
		System.out.println("outputPath - The output path to write the maps to.");
		System.out.println("configFile - The path " +
							"to the file holding parameters for " +
							"map generation.");
	}

}
