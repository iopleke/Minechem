package minechem.item.polytool.types;

import java.util.Random;

import minechem.item.element.EnumElement;
import minechem.item.polytool.PolytoolUpgradeType;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PolytoolTypeHelium extends PolytoolUpgradeType
{

    public PolytoolTypeHelium()
    {
        super();
    }

    @Override
    public float getStrVsBlock(ItemStack itemStack, Block block)
    {

        return 0;
    }

    @Override
    public void hitEntity(ItemStack itemStack, EntityLivingBase target, EntityLivingBase player)
    {

        itemStack.stackTagCompound.setInteger("HeliumHitEntity", target.entityId);
    }

    @Override
    public void onBlockDestroyed(ItemStack itemStack, World world, int id, int x, int y, int z, EntityLivingBase entityLiving)
    {
    }

    @Override
    public EnumElement getElement()
    {

        return EnumElement.He;
    }

    @Override
    public void onTick()
    {

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
                Random rand = new Random();
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
