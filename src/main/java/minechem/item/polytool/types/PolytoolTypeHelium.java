package minechem.item.polytool.types;

import minechem.item.element.ElementEnum;
import minechem.item.polytool.PolytoolUpgradeType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PolytoolTypeHelium extends PolytoolUpgradeType
{
    @Override
    public void hitEntity(ItemStack itemStack, EntityLivingBase target, EntityLivingBase player)
    {
        itemStack.stackTagCompound.setInteger("HeliumHitEntity", target.getEntityId());
    }

    @Override
    public ElementEnum getElement()
    {
        return ElementEnum.He;
    }

    @Override
    public void onTickFull(ItemStack itemStack, World world, Entity par3Entity, int par4, boolean par5)
    {
        int targetId = itemStack.stackTagCompound.getInteger("HeliumHitEntity");
        if (targetId != 0)
        {
            Entity target = world.getEntityByID(targetId);

            if (target != null)
            {
                target.motionY = Math.min(target.motionY + .1, 1);
                if (rand.nextInt((int) (10 * power)) == 0)
                {
                    itemStack.stackTagCompound.setInteger("HeliumHitEntity", 0);
                }
            }
        }
    }

    @Override
    public String getDescription()
    {
        return "Hit entities float away";
    }

}
