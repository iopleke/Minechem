package minechem.compatibility.lua.methods.inventory;

import minechem.compatibility.lua.methods.LuaMethod;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;

public abstract class LuaInventoryMethod extends LuaMethod
{
    public LuaInventoryMethod(String methodName)
    {
        super(methodName);
    }

    public LuaInventoryMethod(String methodName, String args, Class... classes)
    {
        super(methodName, args, classes);
    }

    public LuaInventoryMethod(String methodName, String args, int minArgs, int maxArgs, Class... classes)
    {
        super(methodName, args, minArgs, maxArgs, classes);
    }

    @Override
    public boolean applies(TileEntity te)
    {
        return te instanceof IInventory;
    }
}
