package minechem.item.polytool.types;

import minechem.item.element.ElementEnum;
import minechem.item.polytool.PolytoolUpgradeType;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PolytoolTypeFluorine extends PolytoolUpgradeType
{
    @Override
    public void onBlockDestroyed(ItemStack itemStack, World world, Block block, int x, int y, int z, EntityLivingBase entityLiving)
    {
        if (entityLiving instanceof EntityPlayer && world.rand.nextInt(10) < this.power)
        {
            ((EntityPlayer) entityLiving).getFoodStats().addStats(((ItemFood) Items.carrot).func_150905_g(new ItemStack(Items.carrot)), ((ItemFood) Items.carrot).func_150906_h(new ItemStack(Items.carrot)));
        }
    }

    @Override
    public ElementEnum getElement()
    {
        return ElementEnum.F;
    }

    @Override
    public String getDescription()
    {
        return "Preserves teeth to give you bonus food when mining";
    }

}
