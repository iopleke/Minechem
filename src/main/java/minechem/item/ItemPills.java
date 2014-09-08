package minechem.item;

import minechem.Minechem;
import minechem.utils.Reference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemPills extends ItemFood
{
    public ItemPills(int id, int heal)
    {
        super(heal, 0.4F, false);
        setMaxDamage(0);
        setMaxStackSize(32);
        this.setUnlocalizedName("minechem.itempill");
        this.setCreativeTab(Minechem.CREATIVE_TAB);
        this.setAlwaysEdible();
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer player)
    {
        if (player.canEat(true))
        {
            player.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        }

        return par1ItemStack;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack itemstack)
    {
        return 15;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir)
    {
        itemIcon = ir.registerIcon(Reference.PILL_TEX);
    }
}
