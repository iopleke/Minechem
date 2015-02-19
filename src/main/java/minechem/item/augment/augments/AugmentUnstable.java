package minechem.item.augment.augments;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class AugmentUnstable extends AugmentBase
{
    private final float multiplier;

    public AugmentUnstable(float multiplier)
    {
        super("unstable");
        this.multiplier = multiplier;
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, EntityItem entityItem, int level)
    {
        if (!entityItem.worldObj.isRemote && rand.nextFloat() < multiplier * 0.001)
        {
            consumeAugment(stack, level);
            if (level < 0)
            {
                return false;
            }
            entityItem.worldObj.createExplosion(null, entityItem.posX, entityItem.posY, entityItem.posZ, (level * multiplier) + 1, true);
        }
        return false;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean bool, int level)
    {
        if (!entity.worldObj.isRemote && rand.nextFloat() < multiplier * 0.001)
        {
            consumeAugment(stack, level);
            if (level < 0)
            {
                return;
            }
            entity.attackEntityFrom(DamageSource.causeIndirectMagicDamage(entity, null), multiplier * (level + 1));
        }
    }
}
