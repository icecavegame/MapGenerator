package com.java.icecaveMapGenerator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.java.icecaveMapGenerator.general.EDifficulty;
import com.java.icecaveMapGenerator.general.EDirection;
import com.java.icecaveMapGenerator.mapLogic.IceCaveBoard;
import com.java.icecaveMapGenerator.mapLogic.IceCaveGame;
import com.java.icecaveMapGenerator.utils.Point;

public class MapWriter
{
	String mOutputPath;
	
	/**
	 * Create a new instance of the MapWriter.
	 * 
	 * @param outputPath - The output path for the maps to be generated.
	 */
	public MapWriter(String outputPath)
	{
		mOutputPath = new String(outputPath);
	}
	
	public void generateMaps(int boulderNum, 
	                         int boardSizeX,
	                         int boardSizeY,
	                         EDifficulty difficulty,
	                         int mapsToGenerate,
	                         Point playerStart,
	                         int wallWidth,
	                         EDirection firstDirectionToMove,
	                         String version) throws NoSuchAlgorithmException, IOException
	{
		byte[] hashValue =
				createMapBundleHash(boulderNum, 
						    boardSizeX,
						    boardSizeY,
						    difficulty,
							playerStart, 
							wallWidth, 
							firstDirectionToMove,
							version);
		String bundleFolder = "";
		for (byte b : hashValue)
		{
			bundleFolder += String.valueOf(b);
		}
		
		String fullPath = mOutputPath + "\\" + bundleFolder + "\\";
		
		int startingMapIndex = getStartingMapIndex(fullPath);
		
		IceCaveGame game = 
				new IceCaveGame(boulderNum, 
								boardSizeX,
								boardSizeY,
								difficulty);
		
		// Generate the maps.
		for (int i = 0; i < mapsToGenerate; i++)
		{
			String filePath = fullPath + String.valueOf(i + startingMapIndex); 
			FileOutputStream fout = new FileOutputStream(filePath);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			
			game.newStage(playerStart, wallWidth);
			IceCaveBoard board = 
					new IceCaveBoard(game.getBoard(), 
									 playerStart, 
									 firstDirectionToMove,
									 game.getStageMoves());
			
			// Write the file.
			oos.writeObject(board);
			oos.close();
		}
	}

	/**
	 * @param fullPath
	 * @throws IOException 
	 */
	private int getStartingMapIndex(String fullPath) throws IOException
	{
		int startingMapIndex = 0;
		
		// Check if the path exists.
		Path path = new File(fullPath).toPath();
		if(!Files.isDirectory(path))
		{
			// Create the directory.
			Files.createDirectory(path);
			return startingMapIndex;
		}
		
		try
		{
			File folder = new File(fullPath);
			File[] listOfFiles = folder.listFiles();

			    for (int i = 0; i < listOfFiles.length; i++) {
			      if (listOfFiles[i].isFile()) 
			      {
			    	  try
			    	  {
			    		  int fileNameValue = Integer.parseInt(listOfFiles[i].getName());
			    		  if(startingMapIndex < fileNameValue)
			    		  {
			    			  startingMapIndex = fileNameValue + 1;
			    		  }
			    	  }
			    	  catch(NumberFormatException exception)
			    	  {
			    		  
			    	  }
			      }
			    }
		}
		catch(Exception exception)
		{
			
		}
		
		return startingMapIndex;
	}

	/**
	 * @param boulderNum
	 * @param boardSizeX
	 * @param boardSizeY
	 * @param difficulty
	 * @param playerStart
	 * @param wallWidth
	 * @param firstDirectionToMove
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	private byte[] createMapBundleHash(int boulderNum, int boardSizeX,
										int boardSizeY, EDifficulty difficulty,
										Point playerStart, int wallWidth,
										EDirection firstDirectionToMove, String version)
			throws IOException, NoSuchAlgorithmException
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		byte[] toHash = null;
		try {
		  out = new ObjectOutputStream(bos);   
		  out.writeObject(version);
		  out.writeObject(boulderNum);
		  out.writeObject(boardSizeX);
		  out.writeObject(boardSizeY);
		  out.writeObject(difficulty);
		  out.writeObject(playerStart);
		  out.writeObject(wallWidth);
		  out.writeObject(firstDirectionToMove);
		  
		  toHash = bos.toByteArray();
		} finally {
		  out.close();
		  bos.close();
		}
		
		MessageDigest hasher = MessageDigest.getInstance("md5");
		return hasher.digest(toHash);
	}
}
