package ljdp.minechem.common.items;

import java.util.List;

import ljdp.minechem.common.CreativeTabMinechem;
import ljdp.minechem.common.ModMinechem;
import ljdp.minechem.common.utils.ConstantValue;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class pillz extends ItemFood
{
    
    public pillz(int id, int heal)
    {
        super(id, heal, 0.4F, false);
        setMaxDamage(0);
        setMaxStackSize(16);
        this.setUnlocalizedName("minechem.itempill");
        this.setCreativeTab(ModMinechem.minechemTab);
        this.setAlwaysEdible();
    }

    public ItemStack onItemRightClick (ItemStack par1ItemStack, World par2World, EntityPlayer player)
    {
        if (player.canEat(true) && player.getFoodStats().getSaturationLevel() < 18F)
        {
            player.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        }

        return par1ItemStack;
    }

    @Override
    public int getMaxItemUseDuration (ItemStack itemstack)
    {
        return 10;
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister ir) {
        itemIcon = ir.registerIcon(ConstantValue.PILL_TEX);
    }
}
