package ljdp.minechem.common.blocks;

import java.util.ArrayList;

import ljdp.minechem.common.CommonProxy;
import ljdp.minechem.common.ModMinechem;
import ljdp.minechem.common.tileentity.TileEntityBluePrintPrinter;
import ljdp.minechem.common.tileentity.TileEntityDecomposer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockBluePrintPrinter extends BlockMinechemContainer{

	public BlockBluePrintPrinter(int par1) {
		super(par1, Material.iron);
		this.setCreativeTab(ModMinechem.minechemTab);
		this.setUnlocalizedName("Printer");
	}

	@Override
    public void addStacksDroppedOnBlockBreak(TileEntity tileEntity, ArrayList<ItemStack> itemStacks) {
        TileEntityBluePrintPrinter decomposer = (TileEntityBluePrintPrinter) tileEntity;
        for (int slot = 0; slot < decomposer.getSizeInventory(); slot++) {
            ItemStack itemstack = decomposer.getStackInSlot(slot);
            if (itemstack != null) {
                itemStacks.add(itemstack);
            }
        }
        return;
    }
	@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int par6, float par7, float par8, float par9) {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if (tileEntity == null || entityPlayer.isSneaking())
            return false;
        entityPlayer.openGui(ModMinechem.instance, 0, world, x, y, z);
        return true;
    }
	 @Override
	    public TileEntity createNewTileEntity(World var1) {
	        return new TileEntityBluePrintPrinter();
	    }
	 @Override
	    public boolean isOpaqueCube() {
	        return false;
	    }

	    @Override
	    public int getRenderType() {
	        return CommonProxy.CUSTOM_RENDER_ID;
	    }

	    @Override
	    public boolean renderAsNormalBlock() {
	        return false;
	    }
}
