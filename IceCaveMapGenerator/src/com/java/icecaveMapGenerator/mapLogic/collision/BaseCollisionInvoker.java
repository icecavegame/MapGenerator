package com.java.icecaveMapGenerator.mapLogic.collision;


import com.java.icecaveMapGenerator.general.IFunction;
import com.java.icecaveMapGenerator.utils.Point;

public class BaseCollisionInvoker<return_type> implements ICollisionInvoker<return_type> {
	
	IFunction<return_type> mFunction;
	
	public BaseCollisionInvoker(IFunction<return_type> function) {
		mFunction = function;
	}
	
	@Override
	public return_type onCollision(Point collisionPoint)
	{
		return mFunction.invoke(new Point(collisionPoint));
	}
}
