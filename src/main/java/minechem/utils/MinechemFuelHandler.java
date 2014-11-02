package minechem.utils;

import java.util.LinkedHashMap;
import java.util.Map;

import minechem.MinechemItemsRegistration;
import minechem.item.MinechemChemicalType;
import minechem.item.bucket.MinechemBucketItem;
import minechem.item.element.ElementEnum;
import minechem.item.element.ElementItem;
import minechem.item.molecule.MoleculeEnum;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.IFuelHandler;

public class MinechemFuelHandler implements IFuelHandler
{

	private static Map<String, Integer> fuels = new LinkedHashMap<String,Integer>();
	
	@Override
	public int getBurnTime(ItemStack fuel)
    {
		Integer result = fuels.get(getKey(fuel));
        int times = isBucket(fuel) ? 8 : 1;
		return result != null ? result * times : 0;
	}

	private static String getKey(ItemStack itemStack)
	{
		if (itemStack != null && itemStack.getItem() != null)
		{
            if (itemStack.getItem() instanceof MinechemBucketItem)
            {
                MinechemChemicalType chemical = ((MinechemBucketItem) itemStack.getItem()).chemical;
                if (chemical instanceof ElementEnum)
                {
                    itemStack =  new ItemStack(MinechemItemsRegistration.element, 1, ((ElementEnum) chemical).ordinal());
                }
                else if (chemical instanceof MoleculeEnum)
                {
                    itemStack =  new ItemStack(MinechemItemsRegistration.molecule, 1, ((MoleculeEnum) chemical).id());
                }
            }
			return itemStack.getItem().getUnlocalizedName(itemStack) + "@" + itemStack.getItemDamage();
		}
		return null;
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
			Integer result = fuels.put(getKey(itemStack), value);
			return result != null ? result : 0;
		}
		return 0;
	}
	
	public static int removeFuel(ItemStack itemStack)
	{
		String key = getKey(itemStack);
		if (fuels.containsKey(key))
		{
			Integer result = fuels.remove(key);
			return result != null ? result : 0;
		}
		return 0;
	}
	
}
