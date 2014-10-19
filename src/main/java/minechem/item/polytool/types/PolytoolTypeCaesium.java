package minechem.item.polytool.types;

import minechem.item.element.ElementEnum;
import minechem.item.polytool.PolytoolUpgradeType;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PolytoolTypeCaesium extends PolytoolUpgradeType
{

	public PolytoolTypeCaesium()
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
		if (block == Blocks.stone)
		{
			target.worldObj.createExplosion(target, target.posX, target.posY, target.posZ, power, true);
		}
	}

	@Override
	public ElementEnum getElement()
	{

		return ElementEnum.Cs;
	}

	@Override
	public void onTick()
	{
	}

	@Override
	public String getDescription()
	{

		return "Creates explosion when mining stone";
	}

}
