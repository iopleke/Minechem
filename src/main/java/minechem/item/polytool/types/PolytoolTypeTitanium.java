package minechem.item.polytool.types;

import minechem.item.element.ElementEnum;
import minechem.item.polytool.PolytoolUpgradeType;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PolytoolTypeTitanium extends PolytoolUpgradeType
{
    @Override
    public void hitEntity(ItemStack itemStack, EntityLivingBase target, EntityLivingBase player)
    {
        if (target instanceof EntityLiving)
        {
            ((EntityLiving)target).setEquipmentDropChance(0,0.8F);
        }
    }

    @Override
    public ElementEnum getElement()
    {
        return ElementEnum.Ti;
    }

    @Override
    public String getDescription()
    {
        return "Better chance to collect tools from mob drops";
    }

}
