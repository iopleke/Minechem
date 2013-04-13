package ljdp.minechem.common.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import ljdp.minechem.common.ModMinechem;
import ljdp.minechem.common.entity.EntityTableOfElements;
import ljdp.minechem.common.utils.ConstantValue;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemHangableTableOfElements extends Item{

	public ItemHangableTableOfElements(int par1) {
		super(par1);
		setCreativeTab(ModMinechem.minechemTab);
		setUnlocalizedName("minechem.itemBlueprint");
	}
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l)
    {
        if (l == 0)
        {
            return false;
        }
        if (l == 1)
        {
            return false;
        }
        byte byte0 = 0;
        if (l == 4)
        {
            byte0 = 1;
        }
        if (l == 3)
        {
            byte0 = 2;
        }
        if (l == 5)
        {
            byte0 = 3;
        }
        if (!entityplayer.canPlayerEdit(i, j, k, l, itemstack))
        {
            return false;
        }
        EntityTableOfElements entityTable = new EntityTableOfElements(world, i, j, k, byte0);
        if (entityTable.canStay())
        {
            if (!world.isRemote)
            {
                world.spawnEntityInWorld(entityTable);
            }
            itemstack.stackSize--;
        }
        return true;
    }
	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister ir) {
        itemIcon = ir.registerIcon(ConstantValue.table_HEX);
    }

}
