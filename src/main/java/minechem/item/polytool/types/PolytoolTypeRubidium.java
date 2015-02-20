package minechem.item.polytool.types;

import minechem.item.element.ElementEnum;
import minechem.item.polytool.PolytoolUpgradeType;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PolytoolTypeRubidium extends PolytoolUpgradeType
{

    @Override
    public void hitEntity(ItemStack itemStack, EntityLivingBase target, EntityLivingBase player)
    {
        if (target.isInWater())
        {
            target.worldObj.createExplosion(target, target.posX, target.posY, target.posZ, power, true);
        }
    }

    @Override
    public void onBlockDestroyed(ItemStack itemStack, World world, Block block, int x, int y, int z, EntityLivingBase target)
    {
        if (target.isInWater())
        {
            target.worldObj.createExplosion(target, target.posX, target.posY, target.posZ, power, true);
        }
    }

    @Override
    public ElementEnum getElement()
    {
        return ElementEnum.Rb;
    }

    @Override
    public String getDescription()
    {
        return "Creates explosion if in water";
    }

}
