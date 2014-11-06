package minechem.computercraft.lua;

import net.minecraftforge.common.util.ForgeDirection;
import dan200.computercraft.api.turtle.ITurtleAccess;

public abstract class LuaMethod implements ILuaMethod{
    private final String methodName;

    public LuaMethod(String methodName){
        this.methodName = methodName;
    }

    @Override
    public String getMethodName(){
        return methodName;
    }
    
    @Override
    public String getArgs() {
    	return "()";
    }
    
    @Override
    public String[] getDetails() {
    	return new String[]{methodName+getArgs()};
    }
    
    protected ForgeDirection getDirForString(String side, ITurtleAccess turtle){
        if (side.equals("top"))
        	return ForgeDirection.UP;
        if (side.equals("bottom"))
        	return ForgeDirection.DOWN;
        if (side.equals("front"))
        	return ForgeDirection.getOrientation(turtle.getDirection());
        if (side.equals("back"))
        	return ForgeDirection.getOrientation(ForgeDirection.OPPOSITES[turtle.getDirection()]);
        if (side.equals("left"))
        	return ForgeDirection.getOrientation(ForgeDirection.ROTATION_MATRIX[1][turtle.getDirection()]);
        if (side.equals("right"))
        	return ForgeDirection.getOrientation(ForgeDirection.ROTATION_MATRIX[0][turtle.getDirection()]);
        throw new IllegalArgumentException("Side can only be top, bottom, front, back, left or right!");
    }
}
