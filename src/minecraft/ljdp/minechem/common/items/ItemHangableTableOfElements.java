package ljdp.minechem.common.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ljdp.minechem.common.ModMinechem;
import ljdp.minechem.common.entity.EntityTableOfElements;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.world.World;

public class ItemHangableTableOfElements extends Item {
	
	public ItemHangableTableOfElements(int id) {
		super(id);
		this.setUnlocalizedName("minechem.itemPeriodicTable");
		setCreativeTab(ModMinechem.minechemTab);
	}
	
	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityPlayer, World world, int x, int y, int z, int side, float par8, float par9, float par10)
    {
        if (side == 0)
            return false;
        else if (side == 1)
            return false;
        else
        {
            int orientation = Direction.facingToDirection[side];
            EntityHanging hangingEntity = this.createHangingEntity(world, x, y, z, orientation);

            if (!entityPlayer.canPlayerEdit(x, y, z, side, itemstack))
                return false;
            else
            {
                if (hangingEntity != null && hangingEntity.onValidSurface())
                {
                    if (!world.isRemote)
                        world.spawnEntityInWorld(hangingEntity);
                    --itemstack.stackSize;
                }
                return true;
            }
        }
    }
	
	private EntityHanging createHangingEntity(World world, int x, int y, int z, int orientation) {
		return new EntityTableOfElements(world, x, y, z, orientation);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemstack, EntityPlayer entityPlayer, List list, boolean par4) {
		list.add("Dimensions: 9 x 5");
	}


}
