package minechem.registry;

import java.util.HashMap;
import java.util.Map;

import minechem.collections.ItemStackMap;
import minechem.item.augment.IAugmentItem;
import minechem.item.augment.augments.*;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.IFluidContainerItem;

public class AugmentRegistry
{
    private static Map<String, IAugment> augmentKeyMap = new HashMap<String, IAugment>();
    private static Map<Fluid, IAugment> fluidAugmentMap = new HashMap<Fluid, IAugment>();
    private static ItemStackMap<IAugment> itemStackAugmentMap = new ItemStackMap<IAugment>(true);

    /**
     * Returns the augment for an unlocalized item name
     *
     * @param name unlocalized item name
     * @return
     */
    public static IAugment getAugment(String name)
    {
        return augmentKeyMap.get(name);
    }

    public static IAugment getAugment(ItemStack augmentItem)
    {
        if (augmentItem == null) return null;
        Item item = augmentItem.getItem();
        if (item instanceof IFluidContainerItem)
        {
            return fluidAugmentMap.get(((IFluidContainerItem)item).getFluid(augmentItem));
        }
        else if (item instanceof IAugmentItem)
        {
            return getAugment(((IAugmentItem)item).getAugmentKey(augmentItem));
        }
        else
        {
            return itemStackAugmentMap.get(augmentItem);
        }
    }

    /**
     * Register an augment
     *
     * @param augment
     */
    public static void registerAugment(IAugment augment)
    {
        if (!augmentKeyMap.containsKey(augment.getKey()))
        {
            augmentKeyMap.put(augment.getKey(), augment);
        }
    }

    /**
     * Register an augment
     *
     * @param augment
     */
    public static void registerAugment(Fluid fluid, IAugment augment)
    {
        if (!fluidAugmentMap.containsKey(fluid))
        {
            registerAugment(augment);
            fluidAugmentMap.put(fluid,augment);
        }
    }

    public static void registerAugment(ItemStack stack, IAugment augment)
    {
        if (!itemStackAugmentMap.containsKey(stack))
        {
            registerAugment(augment);
            itemStackAugmentMap.put(stack,augment);
        }
    }

    /**
     * Remove an augment from the registry
     *
     * @param name unlocalized item name
     * @return was the augment successfully removed
     */
    public static boolean unregisterAugment(String name)
    {
        return augmentKeyMap.remove(name) != null;
    }

    public static void init()
    {
        registerAugment(new ItemStack(Blocks.tnt),new AugmentTnt());
        registerAugment(new ItemStack(Blocks.glowstone),new AugmentLight());
        registerAugment(new ItemStack(Items.flint_and_steel),new AugmentFlint());
        registerAugment(new ItemStack(Items.redstone),new AugmentRedstone());
    }
}
