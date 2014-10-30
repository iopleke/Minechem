package minechem.utils;

import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.IFuelHandler;

public class MinechemFuelHandler implements IFuelHandler{

	private static Map<String, Integer> fuels = new LinkedHashMap<String,Integer>();
	
	@Override
	public int getBurnTime(ItemStack fuel) {
		Integer result = fuels.get(getKey(fuel));
		return result!=null?result*fuel.stackSize:0;
	}

	private static String getKey(ItemStack itemStack)
	{
		if (itemStack != null && itemStack.getItem() != null)
		{
			ItemStack result = itemStack.copy();
			if (result.toString().contains("null"))
			{
				return result.getItem().getUnlocalizedName(result) + "@" + result.getItemDamage();
			}
			return result.toString();
		}
		return null;
	}
	
	public static int addFuel(ItemStack itemStack, int value)
	{
		if (value>0)
		{
			Integer result = fuels.put(getKey(itemStack), value);
			return result!=null?result:0;
		}
		return 0;
	}
	
	public static int removeFuel(ItemStack itemStack)
	{
		String key = getKey(itemStack);
		if (fuels.containsKey(key))
		{
			Integer result = fuels.remove(key);
			return result!=null?result:0;
		}
		return 0;
	}
	
}
