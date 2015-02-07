package minechem.compatibility.lua.methods;

import net.minecraft.tileentity.TileEntity;

public abstract class LuaMethod
{

    private final String methodName;
    private final String args;
    private final Class[] classes;
    private final int minArgs, maxArgs;

    public LuaMethod(String methodName)
    {
        this(methodName, "()", 0, 0);
    }

    public LuaMethod(String methodName, String args, Class... classes)
    {
        this(methodName, args, classes.length, classes);
    }

    public LuaMethod(String methodName, String args, int numArgs, Class... classes)
    {
        this(methodName, args, numArgs, Math.max(numArgs, classes.length), classes);
    }

    public LuaMethod(String methodName, String args, int minArgs, int maxArgs, Class... classes)
    {
        this.methodName = methodName;
        this.args = args;
        this.classes = classes;
        this.minArgs = minArgs;
        this.maxArgs = maxArgs;
    }

    public Object[] call(TileEntity te, Object[] args) throws Exception
    {
        if (applies(te) && validArgs(args)) return action(te, args);
        return new Object[0];
    }

    public abstract Object[] action(TileEntity te, Object[] args) throws Exception;

    public String getMethodName()
    {
        return methodName;
    }

    public String getArgs()
    {
        return args;
    }

    public String[] getDetails()
    {
        return new String[0];
    }

    public abstract boolean applies(TileEntity te);

    public boolean validArgs(Object[] args)
    {
        if (args == null || args.length < minArgs || args.length > maxArgs) return false;
        for (int i = 0; i < args.length && i < classes.length; i++)
        {
            if (!classes[i].isInstance(args[i])) return false;
        }
        return true;
    }
}
