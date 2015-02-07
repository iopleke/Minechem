package minechem.compatibility.lua.methods.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;

public class LuaGetInventoryName extends LuaInventoryMethod
{
    public LuaGetInventoryName()
    {
        super("getInventoryName");
    }

    @Override
    public Object[] action(TileEntity te, Object[] args) throws Exception
    {
        return new Object[]
        {
            ((IInventory) te).getInventoryName()
        };
    }
}
