package minechem.item.polytool.types;

import java.util.Random;
import minechem.item.element.ElementEnum;
import minechem.item.polytool.PolytoolUpgradeType;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PolytoolTypeCarbon extends PolytoolUpgradeType
{

	private static final Random rand = new Random();

	public PolytoolTypeCarbon()
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
		if (!world.isRemote)
		{
			int bonus = (int) (rand.nextDouble() * Math.log(this.power));
			if (block == Blocks.coal_ore)
			{
				world.spawnEntityInWorld(new EntityItem(world, x + rand.nextDouble(), y + rand.nextDouble(), z + rand.nextDouble(), new ItemStack(Items.coal, bonus, 0)));
			}
			if (block == Blocks.diamond_ore)
			{
				world.spawnEntityInWorld(new EntityItem(world, x + rand.nextDouble(), y + rand.nextDouble(), z + rand.nextDouble(), new ItemStack(Items.diamond, bonus, 0)));
			}
		}
	}

	@Override
	public ElementEnum getElement()
	{

		return ElementEnum.C;
	}

	@Override
	public void onTick()
	{
	}

	@Override
	public String getDescription()
	{

		return "More drops from coal and diamond ores";
	}

}
