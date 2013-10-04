package com.java.icecaveMapGenerator.mapLogic.collision;

import com.java.icecaveMapGenerator.utils.Point;

/**
 * Just for order.
 * @author Tom
 *
 */
public interface ICollisionable {
	/**
	 * Get the location of the tile.
	 * @return Location of the tile.
	 */
	Point getLocation();
}
