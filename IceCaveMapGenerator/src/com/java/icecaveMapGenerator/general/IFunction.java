package com.java.icecaveMapGenerator.general;

import com.java.icecaveMapGenerator.utils.Point;

public interface IFunction<return_type> {
	/**
	 * Invoke the collision function.
	 * @param collisionPoint - Point of the tile the player collisioned with.
	 * @return result.
	 */
	public return_type invoke(Point collisionPoint);
}