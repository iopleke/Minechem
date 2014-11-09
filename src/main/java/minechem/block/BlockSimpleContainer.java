package minechem.block;

import minechem.utils.MinechemUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.ArrayList;

public abstract class BlockSimpleContainer extends BlockContainer
{

	protected BlockSimpleContainer(Material material)
	{
		super(material);
		setHardness(2F);
		setResistance(50F);
	}

	public abstract void addStacksDroppedOnBlockBreak(TileEntity tileEntity, ArrayList<ItemStack> itemStacks);

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int metaData)
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity != null)
		{
			ArrayList<ItemStack> droppedStacks = new ArrayList<ItemStack>();
			addStacksDroppedOnBlockBreak(tileEntity, droppedStacks);
			for (ItemStack itemstack : droppedStacks)
			{
				MinechemUtil.throwItemStack(world, itemstack, x, y, z);
			}
			super.breakBlock(world, x, y, z, block, metaData);
		}

	}
}
