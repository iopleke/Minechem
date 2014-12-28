package minechem.tileentity.decomposer;

import minechem.Minechem;
import minechem.block.BlockSimpleContainer;
import minechem.gui.CreativeTabMinechem;
import minechem.proxy.CommonProxy;
import minechem.reference.Textures;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.ArrayList;

public class DecomposerBlock extends BlockSimpleContainer
{
	public DecomposerBlock()
	{
		super(Material.iron);
		setBlockName("chemicalDecomposer");
		setCreativeTab(CreativeTabMinechem.CREATIVE_TAB_ITEMS);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int par6, float par7, float par8, float par9)
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity == null || entityPlayer.isSneaking())
		{
			return false;
		}
		entityPlayer.openGui(Minechem.INSTANCE, 0, world, x, y, z);
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i)
	{
		return new DecomposerTileEntity();
	}

	//TODO: Find replacement
	@Override
	public void addStacksDroppedOnBlockBreak(TileEntity tileEntity, ArrayList<ItemStack> itemStacks)
	{
		DecomposerTileEntity decomposer = (DecomposerTileEntity) tileEntity;
		for (int slot = 0; slot < decomposer.getSizeInventory(); slot++)
		{
			ItemStack itemstack = decomposer.getStackInSlot(slot);
			if (itemstack != null)
			{
				itemStacks.add(itemstack);
			}
		}
	}

	@Override
	public void registerBlockIcons(IIconRegister ir)
	{
		this.blockIcon = ir.registerIcon(Textures.IIcon.DECOMPOSER);
	}

	@Override
	public IIcon getIcon(int side, int meta)
	{
		return this.blockIcon;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public int getRenderType()
	{
		return CommonProxy.RENDER_ID;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
}
