package minechem.compatibility.lua;

import cpw.mods.fml.common.registry.GameRegistry;
import minechem.reference.NBTTags;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.LinkedHashMap;
import java.util.Map;

public class LuaParser
{
    public static Object toLua(ItemStack stack)
    {
        if (stack != null)
        {
            Map<String, Object> result = new LinkedHashMap<String, Object>();
            if (stack.getItem() == null) return null;
            GameRegistry.UniqueIdentifier id = GameRegistry.findUniqueIdentifierFor(stack.getItem());
            if (id == null) return null;
            result.put(NBTTags.ITEM, id.toString());
            result.put(NBTTags.AMOUNT, stack.stackSize);
            result.put(NBTTags.DAMAGE, stack.getItemDamage());
            if (stack.hasTagCompound())
                result.put(NBTTags.NBT, stack.getTagCompound().toString());
            return result;
        }
        return null;
    }

    public static Object toLua(int[] array)
    {
        if (array!=null)
        {
            Map<Number, Object> result = new LinkedHashMap<Number, Object>();
            for (int i = 0; i<array.length; i++)
            {
                result.put(i+1, array[i]);
            }
            return result;
        }
        return null;
    }
}
