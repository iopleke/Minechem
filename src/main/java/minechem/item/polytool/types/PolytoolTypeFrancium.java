package minechem.item.polytool.types;

import minechem.item.element.ElementEnum;
import minechem.item.polytool.PolytoolUpgradeType;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PolytoolTypeFrancium extends PolytoolUpgradeType
{
    @Override
    public void hitEntity(ItemStack itemStack, EntityLivingBase target, EntityLivingBase player)
    {
        explode(target.worldObj, target.posX, target.posY, target.posZ);
    }

    @Override
    public void onBlockDestroyed(ItemStack itemStack, World world, Block block, int x, int y, int z, EntityLivingBase target)
    {
        explode(world, x + 0.5D, y + 0.5D, +0.5D);
    }

    private void explode(World world, double x, double y, double z)
    {
        if (world.isRaining())
        {
            world.createExplosion(null, x, y, z, power, true);
        }
    }

    @Override
    public ElementEnum getElement()
    {
        return ElementEnum.Fr;
    }

    @Override
    public String getDescription()
    {

        return "Creates explosions when raining";
    }

}
