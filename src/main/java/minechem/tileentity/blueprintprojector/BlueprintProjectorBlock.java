package minechem.tileentity.blueprintprojector;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minechem.Minechem;
import minechem.block.BlockSimpleContainer;
import minechem.gui.CreativeTabMinechem;
import minechem.item.blueprint.ItemBlueprint;
import minechem.item.blueprint.MinechemBlueprint;
import minechem.proxy.CommonProxy;
import minechem.reference.Textures;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.ArrayList;

public class BlueprintProjectorBlock extends BlockSimpleContainer
{

	public BlueprintProjectorBlock()
	{
		super(Material.iron);
		setBlockName("blueprintProjector");
		setCreativeTab(CreativeTabMinechem.CREATIVE_TAB_ITEMS);
		setLightLevel(0.7F);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase el, ItemStack is)
	{
		super.onBlockPlacedBy(world, x, y, z, el, is);
		int facing = MathHelper.floor_double(el.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		world.setBlockMetadataWithNotify(x, y, z, facing, 2);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float par7, float par8, float par9)
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity instanceof BlueprintProjectorTileEntity)
		{
			entityPlayer.openGui(Minechem.INSTANCE, 0, world, x, y, z);
			return true;
		}
		return false;
	}

	private ItemStack takeBlueprintFromProjector(BlueprintProjectorTileEntity projector)
	{
		MinechemBlueprint blueprint = projector.takeBlueprint();
		return ItemBlueprint.createItemStackFromBlueprint(blueprint);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i)
	{
		return new BlueprintProjectorTileEntity();
	}

	@Override
	public void addStacksDroppedOnBlockBreak(TileEntity tileEntity, ArrayList<ItemStack> itemStacks)
	{
		if (tileEntity instanceof BlueprintProjectorTileEntity)
		{
			BlueprintProjectorTileEntity projector = (BlueprintProjectorTileEntity) tileEntity;
			if (projector.hasBlueprint())
			{
				itemStacks.add(takeBlueprintFromProjector(projector));
			}
		}
	}

	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest)
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity instanceof BlueprintProjectorTileEntity)
		{
			BlueprintProjectorTileEntity projector = (BlueprintProjectorTileEntity) tileEntity;
			projector.destroyProjection();
		}
		return super.removedByPlayer(world, player, x, y, z, willHarvest);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		blockIcon = ir.registerIcon(Textures.IIcon.BLUEPRINTPROJECTOR);
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
