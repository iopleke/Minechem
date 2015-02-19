package minechem.compatibility.lua.methods.liquids;

import java.util.HashMap;
import java.util.Map;
import minechem.Compendium;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class LuaGetTankInfo extends LuaFluidMethod
{
    public LuaGetTankInfo()
    {
        super("getTankInfo", "(Direction direction)", 0, 1, String.class);
    }

    @Override
    public Object[] action(TileEntity te, Object[] args) throws Exception
    {
        ForgeDirection direction;
        if (args.length == 0)
        {
            direction = ForgeDirection.UNKNOWN;
        } else
        {
            direction = ForgeDirection.valueOf((String) args[1]);
            if (direction == null)
            {
                throw new Exception("Invalid Direction");
            }
        }
        return new Object[]
        {
            tanksToMap(((IFluidHandler) te).getTankInfo(direction))
        };
    }

    public static Map<Number, Object> tanksToMap(FluidTankInfo[] tanks)
    {
        Map<Number, Object> result = new HashMap<Number, Object>();
        for (int i = 0; i < tanks.length; i++)
        {
            if (tanks[i] != null)
            {
                result.put(i, getTankMap(tanks[i]));
            }
        }
        return result;
    }

    public static Map<String, Object> getTankMap(FluidTankInfo tank)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        if (tank.fluid != null)
        {
            result.put(Compendium.NBTTags.fluid, tank.fluid.getFluid().getName());
            result.put(Compendium.NBTTags.amount, tank.fluid.amount);
        }
        result.put(Compendium.NBTTags.capacity, tank.capacity);
        return result;
    }
}
