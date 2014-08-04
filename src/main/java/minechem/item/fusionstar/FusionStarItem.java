package minechem.item.fusionstar;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class FusionStarItem extends Item
{

    public FusionStarItem(int id)
    {
        this.maxStackSize = 1;
        this.setMaxDamage(2000);
        this.setNoRepair();
        this.setUnlocalizedName("minechem.itemFusionStar");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean par4)
    {
        int damage = itemStack.getItemDamage();
        int usesLeft = itemStack.getMaxDamage() - damage;
        list.add(usesLeft + " Uses Left");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int par1)
    {
        return Items.nether_star.getIconFromDamage(par1);
    }

    @Override
    public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
        return false;
    }

}
