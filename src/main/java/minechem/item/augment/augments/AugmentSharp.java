package minechem.item.augment.augments;

import com.google.common.collect.Multimap;
import minechem.item.augment.AugmentedItem;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class AugmentSharp extends AugmentBase
{
    public AugmentSharp()
    {
        super("sharp");
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase user, int level)
    {
        consumeAugment(stack, level);
        return false;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase entityLivingBase, int level)
    {
        consumeAugment(stack, (int) (level / 0.4));
        return super.onBlockDestroyed(stack, world, block, x, y, z, entityLivingBase, level);
    }

    @Override
    public int getHarvestLevelModifier(ItemStack stack, String toolClass, int level)
    {
        return (int) (level / 0.4) + 1;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(ItemStack stack, int level)
    {
        Multimap multimap = super.getAttributeModifiers(stack, level);
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(AugmentedItem.itemUUID, "Weapon modifier", (double) level, 0));
        return multimap;
    }
}
