package minechem.item.augment.augments;

import net.minecraft.item.ItemStack;

public class AugmentReinforced extends AugmentBase
{
    public AugmentReinforced()
    {
        super("reinforced");
    }

    @Override
    public int getVolumeConsumed(int level)
    {
        return level*4 + 1;
    }

    @Override
    public float setDamageChance(ItemStack stack, int level)
    {
        consumeAugment(stack,level);
        return (level+1)*0.08F;
    }

    @Override
    public int getEntityLifespanModifier(ItemStack stack, int level)
    {
        return level * 1000;
    }
}
