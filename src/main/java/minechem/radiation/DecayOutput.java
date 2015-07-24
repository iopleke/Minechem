package minechem.radiation;

import net.minecraft.item.ItemStack;

public class DecayOutput
{

    public ItemStack[] outputItems;
    public int damage;

    public DecayOutput(ItemStack[] outputItems, int damage)
    {
        this.outputItems = outputItems;
        this.damage = damage;
    }
}
