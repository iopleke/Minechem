package minechem.utils;

import java.util.LinkedHashMap;
import java.util.Map;

import minechem.MinechemItemsRegistration;
import minechem.item.MinechemChemicalType;
import minechem.item.bucket.MinechemBucketItem;
import minechem.item.element.ElementEnum;
import minechem.item.molecule.MoleculeEnum;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.IFuelHandler;

public class MinechemFuelHandler implements IFuelHandler
{

	private static Map<MapKey, Integer> fuels = new LinkedHashMap<MapKey,Integer>();
	
	@Override
	public int getBurnTime(ItemStack fuel)
    {
		Integer result = fuels.get(new MapKey(fuel));
		if (result == null) return 0;
        int mult = isBucket(fuel) ? 8 : 1;
		return result * mult;
	}

    private static boolean isBucket(ItemStack itemStack)
    {
        if (itemStack != null && itemStack.getItem() != null)
        {
            if (itemStack.getItem() instanceof MinechemBucketItem) return true;
        }
        return false;
    }
	
	public static int addFuel(ItemStack itemStack, int value)
	{
		if (value > 0)
		{
			Integer result = fuels.put(new MapKey(itemStack), value);
			return result != null ? result : 0;
		}
		return 0;
	}
	
	public static int removeFuel(ItemStack itemStack)
	{
		MapKey key = new MapKey(itemStack);
		if (fuels.containsKey(key))
		{
			Integer result = fuels.remove(key);
			return result != null ? result : 0;
		}
		return 0;
	}
	
}
