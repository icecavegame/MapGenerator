package com.java.icecaveMapGenerator.utils.bundle;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Used to hash bundles.
 * @author Tom
 *
 */
public class BundleHasher
{
	/**
	 * The hasher of the bundle hasher.
	 */
	MessageDigest mHasher;
	
	/**
	 * Create a new instance of the BundleHasher.
	 * @param hashAlgorithem - Algorithm to use for hashing.
	 * @throws NoSuchAlgorithmException 
	 */
	public BundleHasher(String hashAlgorithm) throws NoSuchAlgorithmException
	{
		mHasher = MessageDigest.getInstance(hashAlgorithm);
	}
	
	/**
	 * Hash a byte array.
	 * @param toHash - Array of bytes to hash.
	 * @return Hash value.
	 */
	public byte[] hash(byte[] toHash){
		return mHasher.digest(toHash);
	}
	
	/**
	 * @param hashValue
	 * @param bundleFolder
	 * @return
	 */
	public static String hashToString(byte[] hashValue)
	{
		String result = "";
		for (byte b : hashValue)
		{
			result += String.valueOf(b);
		}
		
		return result;
	}
	
	/** Hash the bundle metadata.
	 * @param bundleDataToHash - Meta data of the bundle to hash.
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public byte[] createMapBundleHash(IBundleMetaData bundleDataToHash)
			throws IOException, NoSuchAlgorithmException
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		byte[] toHash = null;
		try {
		  out = new ObjectOutputStream(bos);   
		  out.writeObject(bundleDataToHash.getVersion());
		  out.writeObject(bundleDataToHash.getBoulderNum());
		  out.writeObject(bundleDataToHash.getBoardWidth());
		  out.writeObject(bundleDataToHash.getBoardHeight());
		  out.writeObject(bundleDataToHash.getDifficulty());
		  out.writeObject(bundleDataToHash.getPlayerStart());
		  out.writeObject(bundleDataToHash.getWallWidth());
		  out.writeObject(bundleDataToHash.getFirstMove());
		  
		  toHash = bos.toByteArray();
		} finally {
		  out.close();
		  bos.close();
		}
		
		return mHasher.digest(toHash);
	}

}
