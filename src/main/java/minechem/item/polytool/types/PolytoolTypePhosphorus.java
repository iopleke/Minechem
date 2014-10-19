package minechem.item.polytool.types;

import minechem.item.element.ElementEnum;
import minechem.item.polytool.PolytoolUpgradeType;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class PolytoolTypePhosphorus extends PolytoolUpgradeType
{

	public PolytoolTypePhosphorus()
	{
		super();
	}

	@Override
	public float getStrVsBlock(ItemStack itemStack, Block block)
	{

		return 0;
	}

	@Override
	public void hitEntity(ItemStack itemStack, EntityLivingBase target, EntityLivingBase player)
	{

	}

	@Override
	public void onBlockDestroyed(ItemStack itemStack, World world, Block block, int x, int y, int z, EntityLivingBase target)
	{
		if (!target.worldObj.isRemote)
		{
			if (block.isFlammable(world, x, y, z, ForgeDirection.UP))
			{
				for (int i = (int) (x - power); i < x + power; i++)
				{
					for (int j = (int) (y - power); j < y + power; j++)
					{
						for (int k = (int) (z - power); k < z + power; k++)
						{
							if (world.getBlock(i, j, k) == Blocks.air)
							{
								world.setBlock(i, j, k, Blocks.fire);
							}
						}
					}
				}
			}

		}
	}

	@Override
	public ElementEnum getElement()
	{

		return ElementEnum.P;
	}

	@Override
	public void onTick()
	{
	}

	@Override
	public String getDescription()
	{

		return "Sets nearby blocks on fire";
	}

}
