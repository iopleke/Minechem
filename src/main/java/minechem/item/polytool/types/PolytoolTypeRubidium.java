package minechem.item.polytool.types;

import minechem.item.element.EnumElement;
import minechem.item.polytool.PolytoolUpgradeType;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PolytoolTypeRubidium extends PolytoolUpgradeType
{

    public PolytoolTypeRubidium()
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
        if (target.isInWater())
        {
            target.worldObj.createExplosion(target, target.posX, target.posY, target.posZ, power, true);
        }
    }

    @Override
    public void onBlockDestroyed(ItemStack itemStack, World world, int id, int x, int y, int z, EntityLivingBase target)
    {
        if (target.isInWater())
        {
            target.worldObj.createExplosion(target, target.posX, target.posY, target.posZ, power, true);
        }
    }

    @Override
    public EnumElement getElement()
    {

        return EnumElement.Rb;
    }

    @Override
    public void onTick()
    {
    }

    @Override
    public String getDescription()
    {

        return "Creates explosion if in water";
    }

}
