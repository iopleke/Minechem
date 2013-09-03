package ljdp.minechem.common.blocks;

import java.util.ArrayList;
import java.util.Random;

import ljdp.minechem.common.CommonProxy;
import ljdp.minechem.common.ModMinechem;
import ljdp.minechem.common.tileentity.TileEntitySynthesis;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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
    Random random=new Random();
    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        TileEntitySynthesis tileentitychest = (TileEntitySynthesis)par1World.getBlockTileEntity(par2, par3, par4);

        if (tileentitychest != null)
        {
            for (int j1 = 0; j1 < tileentitychest.getSizeInventory(); ++j1)
            {
                ItemStack itemstack = tileentitychest.getStackInSlot(j1);

                if (itemstack != null)
                {
                    float f = this.random.nextFloat() * 0.8F + 0.1F;
                    float f1 = this.random.nextFloat() * 0.8F + 0.1F;
                    EntityItem entityitem;

                    for (float f2 = this.random.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; par1World.spawnEntityInWorld(entityitem))
                    {
                        int k1 = this.random.nextInt(21) + 10;

                        if (k1 > itemstack.stackSize)
                        {
                            k1 = itemstack.stackSize;
                        }

                        itemstack.stackSize -= k1;
                        entityitem = new EntityItem(par1World, (double)((float)par2 + f), (double)((float)par3 + f1), (double)((float)par4 + f2), new ItemStack(itemstack.itemID, k1, itemstack.getItemDamage()));
                        float f3 = 0.05F;
                        entityitem.motionX = (double)((float)this.random.nextGaussian() * f3);
                        entityitem.motionY = (double)((float)this.random.nextGaussian() * f3 + 0.2F);
                        entityitem.motionZ = (double)((float)this.random.nextGaussian() * f3);

                        if (itemstack.hasTagCompound())
                        {
                            entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                        }
                    }
                }
            }

            par1World.func_96440_m(par2, par3, par4, par5);
        }

        super.breakBlock(par1World, par2, par3, par4, par5, par6);
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
