package ljdp.minechem.common.blocks;

import java.util.ArrayList;

import ljdp.minechem.common.CommonProxy;
import ljdp.minechem.common.ModMinechem;
import ljdp.minechem.common.tileentity.TileEntitySynthesis;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockSynthesis extends BlockMinechemContainer {

    public BlockSynthesis(int par1) {
        super(par1, Material.iron);
        setUnlocalizedName("minechem.blockSynthesis");
        setCreativeTab(ModMinechem.minechemTab);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase el, ItemStack is) {
        super.onBlockPlacedBy(world, x, y, z, el, is);
        int facing = MathHelper.floor_double((double) (el.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        world.setBlockMetadataWithNotify(x, y, z, facing, 2);
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
        return new TileEntitySynthesis();
    }

    @Override
	public void addStacksDroppedOnBlockBreak(TileEntity tileEntity, ArrayList itemStacks) {
		TileEntitySynthesis decomposer = (TileEntitySynthesis)tileEntity;
		for(int slot = 0; slot < decomposer.getSizeInventory(); slot++) {
			if(slot >= decomposer.kStartRecipe && slot < decomposer.kStartRecipe + decomposer.kSizeRecipe)
				continue;
			ItemStack itemstack = decomposer.getStackInSlot(slot);
			if(itemstack != null) {
				itemStacks.add(itemstack);
			}
		}
		return;
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
