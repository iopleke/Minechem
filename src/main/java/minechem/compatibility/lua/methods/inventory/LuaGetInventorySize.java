package minechem.compatibility.lua.methods.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;

public class LuaGetInventorySize extends LuaInventoryMethod
{
    public LuaGetInventorySize()
    {
        super("getInventorySize");
    }

    @Override
    public Object[] action(TileEntity te, Object[] args) throws Exception
    {
        return new Object[]
        {
            ((IInventory) te).getSizeInventory()
        };
    }
}
