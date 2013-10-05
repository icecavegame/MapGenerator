package com.java.icecaveMapGenerator;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import com.java.icecaveMapGenerator.mapLogic.IceCaveBoard;
import com.java.icecaveMapGenerator.mapLogic.IceCaveGame;
import com.java.icecaveMapGenerator.utils.bundle.BaseBundleMetaData;
import com.java.icecaveMapGenerator.utils.bundle.BundleHasher;

public class MapWriter
{
	/**
	 * Map of output board hashes that were generated.
	 */
	ArrayList<String> mBoardHashes;
	
	/**
	 * Output path of the files.
	 */
	String mOutputPath;
	
	/**
	 * Name of the index file.
	 */
	final String INDEX_FILE_NAME = "index.ini";
	
	/**
	 * Section of hash values generated.
	 */
	final String INDEX_HASH_SECTIONS = "HASH";
	
	/**
	 * Used to hash the metadata.
	 */
	BundleHasher mHasher;
	
	/**
	 * Create a new instance of the MapWriter.
	 * 
	 * @param outputPath - The output path for the maps to be generated.
	 * @param hashAlgo - Algorithm to hash with.
	 * @throws NoSuchAlgorithmException 
	 */
	public MapWriter(String outputPath, String hashAlgo) throws NoSuchAlgorithmException
	{
		mOutputPath = new String(outputPath);
		mHasher = new BundleHasher(hashAlgo);
	}
	
	/**
	 * Generate maps for the ice cave.
	 * @param mapsToGenerate - Number of maps to generate.
	 * @param metaData - Meta data to hash by.
	 * @throws IOException 
	 * @throws NoSuchAlgorithmException 
	 */
	public void generateMaps(int mapsToGenerate, BaseBundleMetaData metaData) throws NoSuchAlgorithmException, IOException 
	{
		mBoardHashes = new ArrayList<String>();
		byte[] hashValue =
				mHasher.createMapBundleHash(metaData);
		String hash = BundleHasher.hashToString(hashValue);
		String filePath = mOutputPath + "\\" + hash + "\\";
		
		int startingMapIndex = getStartingMapIndex(filePath);
		
		IceCaveGame game = 
				new IceCaveGame(metaData.getBoulderNum(),
								metaData.getBoardWidth(),
								metaData.getBoardHeight(),
								metaData.getDifficulty());
		
		parseIndexFile(filePath);
		
		ArrayList<String> hashesToAdd = new ArrayList<String>();
		
		// Generate the maps.
		for (int i = 0; i < mapsToGenerate; i++)
		{
			generateSingleMap(metaData,
							  filePath, 
							  startingMapIndex + i, 
							  game,
							  hashesToAdd);
		}
		
		// Update index file.
		updateIndexFile(filePath, hashesToAdd);
	}

	/**
	 * @param metaData
	 * @param filePath
	 * @param mapIndex
	 * @param game
	 * @param hashesToAdd
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void generateSingleMap(BaseBundleMetaData metaData,
									String filePath,
									int mapIndex,
									IceCaveGame game,
									ArrayList<String> hashesToAdd)
			throws FileNotFoundException, IOException
	{
		String hashedMap = "";
		String fileName = String.valueOf(mapIndex);
		
		game.newStage(metaData.getPlayerStart(), metaData.getWallWidth());
		IceCaveBoard board = 
				new IceCaveBoard(game.getBoard(), 
								 metaData.getPlayerStart(), 
								 metaData.getFirstMove(),
								 game.getStageMoves());
		
		hashedMap =
				writeMapBoard(metaData,
					  filePath, 
					  fileName, 
					  board);
		
		// Check if to add the hash.
		if(hashedMap != null){
			hashesToAdd.add(hashedMap);
		}
	}

	/**
	 * @param filePath - Path to the index file.
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private void parseIndexFile(String filePath) throws IOException,
			FileNotFoundException
	{
		// Get the index file.
		File indexFile = new File(filePath + INDEX_FILE_NAME);
		
		// Create it if does not exists.
		indexFile.createNewFile();

		FileInputStream indexFileStream = null;
		
		try
		{
			// Open the index file.
			indexFileStream = new FileInputStream(indexFile);
			
			InputStreamReader indexReader = null;
			
			try
			{
				indexReader = new InputStreamReader(indexFileStream);
				
				BufferedReader indexBufferedReader = null;
				
				try
				{
					indexBufferedReader = new BufferedReader(indexReader);
					
					String hash = indexBufferedReader.readLine();
					
					// Read all the lines.
					while(hash != null)
					{
						// Add the hash.
						mBoardHashes.add(hash);
						
						hash = indexBufferedReader.readLine();
					}
				}
				finally
				{
					if(indexBufferedReader != null){
						indexBufferedReader.close();
					}
				}
			}
			finally
			{
				if(indexReader != null){
					indexReader.close();
				}
			}
		}
		finally
		{
			if(indexFileStream != null){
				indexFileStream.close();
			}
		}
	}
	
	/**
	 * @param filePath - Path to the index file.
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private void updateIndexFile(String filePath, ArrayList<String> hashesToAdd)
			throws IOException,
				   FileNotFoundException
	{
		// Get the index file.
		File indexFile = new File(filePath + INDEX_FILE_NAME);
		FileWriter fw = new FileWriter(indexFile, true);
		
		for (String hash : hashesToAdd)
		{
			// Write the hash.
			fw.write(hash + "\n");
		}
		
		fw.close();
	}
	
	/**
	 * Write a map board.
	 * @param metaData - Data to write.
	 * @param fullPath - Path to the file to write.
	 * @param fileName - File name to write the map.
	 * @param board - Board to write. 
	 * @return Hash value generated, null if already exists.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private String writeMapBoard(BaseBundleMetaData metaData, 
	                           String fullPath,
	                           String fileName, 
	                           IceCaveBoard board)
			throws FileNotFoundException, IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(board);
		byte[] hashedBoard = mHasher.hash(baos.toByteArray());
		oos.close();
		
		// Covert to string.
		String hashedBoardString = BundleHasher.hashToString(hashedBoard);
		
		// Check if hash already exists.
		if(mBoardHashes.contains(hashedBoardString)){
			return null;
		}
		
		// Add the value.
		mBoardHashes.add(hashedBoardString);
		
		String filePath = fullPath + fileName; 
		FileOutputStream fout = new FileOutputStream(filePath);
		oos = new ObjectOutputStream(fout);
		
		// Write the file.
		oos.writeObject(board);
		oos.close();
		
		return hashedBoardString;
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
}
