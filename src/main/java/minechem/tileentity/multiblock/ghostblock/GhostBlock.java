package minechem.tileentity.multiblock.ghostblock;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minechem.MinechemBlocksGeneration;
import minechem.reference.Textures;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class GhostBlock extends BlockContainer
{

	public GhostBlock()
	{
		super(Material.iron);
		setBlockName("ghostBlock");
		setLightLevel(0.5F);
		setHardness(1000F);
		setResistance(1000F);
	}

	public IIcon icon1;
	public IIcon icon2;

	@Override
	public IIcon getIcon(int par1, int metadata)
	{
		switch (metadata)
		{
			case 0:
				return icon1;
			case 1:
				return icon2;
		}
		return blockIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		blockIcon = ir.registerIcon(Textures.IIcon.DEFAULT);
		icon1 = ir.registerIcon(Textures.IIcon.BLUEPRINT1);
		icon2 = ir.registerIcon(Textures.IIcon.BLUEPRINT2);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float par7, float par8, float par9)
	{
		super.onBlockActivated(world, x, y, z, entityPlayer, side, par7, par8, par9);

		if (world.isRemote)
		{
			return true;
		}

		if (entityPlayer.getDistanceSq(x + 0.5D, y + 0.5D, z + 0.5D) > 64.0D)
		{
			return true;
		}

		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity instanceof GhostBlockTileEntity)
		{
			GhostBlockTileEntity ghostBlock = (GhostBlockTileEntity) tileEntity;
			ItemStack blockAsStack = ghostBlock.getBlockAsItemStack();
			if (playerIsHoldingItem(entityPlayer, blockAsStack))
			{
				world.setBlock(x, y, z, MinechemBlocksGeneration.fusion, blockAsStack.getItemDamage(), 3);
				if (!entityPlayer.capabilities.isCreativeMode)
				{
					entityPlayer.inventory.decrStackSize(entityPlayer.inventory.currentItem, 1);
				}
				return true;
			}
		}
		return false;
	}

	private boolean playerIsHoldingItem(EntityPlayer entityPlayer, ItemStack itemstack)
	{
		ItemStack helditem = entityPlayer.inventory.getCurrentItem();
		if (helditem != null && itemstack != null)
		{
			if (helditem.getItem() == itemstack.getItem())
			{
				if (helditem.getItemDamage() == itemstack.getItemDamage() || itemstack.getItemDamage() == -1)
				{
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Returns whether this block is collideable based on the arguments passed in Args: blockMetaData, unknownFlag
	 */
	@Override
	public boolean canCollideCheck(int par1, boolean par2)
	{
		return true;
	}

	@Override
	public int damageDropped(int par1)
	{
		return par1;
	}

	/**
	 * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
	 */
	@Override
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
	{
		// Todo
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been cleared to be reused)
	 */
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return null;
	}

	/**
	 * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given coordinates. Args: blockAccess, x, y, z, side
	 */
	@Override
	public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return true;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	/**
	 * Returns which pass should this block be rendered on. 0 for solids and 1 for alpha
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass()
	{
		return 1;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int i)
	{
		return new GhostBlockTileEntity();
	}

	/**
	 * When player places a ghost block delete it
	 */
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack)
	{
		if (entity instanceof EntityPlayer)
		{
			world.setBlockToAir(x, y, z);
		}
	}
}
