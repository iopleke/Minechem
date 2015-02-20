package minechem.item.polytool.types;

import minechem.item.element.ElementEnum;
import minechem.item.polytool.PolytoolUpgradeType;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class PolytoolTypeHydrogen extends PolytoolUpgradeType
{

    @Override
    public void hitEntity(ItemStack itemStack, EntityLivingBase target, EntityLivingBase player)
    {
        if (!target.worldObj.isRemote && target instanceof EntityLiving && target.isBurning())
        {
            target.worldObj.createExplosion(target, target.posX, target.posY, target.posZ, power, true);
        }
    }

    @Override
    public ElementEnum getElement()
    {
        return ElementEnum.H;
    }

    @Override
    public String getDescription()
    {
        return "Creates explosion if hit entity is on fire";
    }

}
