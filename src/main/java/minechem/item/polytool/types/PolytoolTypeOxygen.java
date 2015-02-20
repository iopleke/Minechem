package minechem.item.polytool.types;

import minechem.item.element.ElementEnum;
import minechem.item.polytool.PolytoolUpgradeType;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PolytoolTypeOxygen extends PolytoolUpgradeType
{
    @Override
    public void onBlockDestroyed(ItemStack itemStack, World world, Block block, int x, int y, int z, EntityLivingBase entityLiving)
    {
        entityLiving.setAir((int)(entityLiving.getAir() + power));
    }

    @Override
    public ElementEnum getElement()
    {
        return ElementEnum.O;
    }

    @Override
    public String getDescription()
    {
        return "Gives extra air when mining underwater";
    }

}
