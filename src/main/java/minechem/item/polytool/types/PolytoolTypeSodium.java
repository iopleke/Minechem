package minechem.item.polytool.types;

import minechem.item.element.ElementEnum;
import minechem.item.polytool.PolytoolUpgradeType;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PolytoolTypeSodium extends PolytoolUpgradeType
{

	public PolytoolTypeSodium()
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
	public void onBlockDestroyed(ItemStack itemStack, World world, Block block, int x, int y, int z, EntityLivingBase entityLiving)
	{
		if (world.rand.nextInt(35) < power && !world.isRemote)
		{
			world.setBlock((int) Math.floor(entityLiving.posX), (int) Math.floor(entityLiving.posY), (int) Math.floor(entityLiving.posZ), Blocks.torch, 0, 3);
		}
	}

	@Override
	public ElementEnum getElement()
	{

		return ElementEnum.Na;
	}

	@Override
	public void onTick()
	{
	}

	@Override
	public String getDescription()
	{

		return "Lights up area when mining";
	}

}
