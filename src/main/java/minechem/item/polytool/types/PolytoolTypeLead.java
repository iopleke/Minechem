package minechem.item.polytool.types;

import java.util.Iterator;
import java.util.List;
import minechem.item.element.ElementEnum;
import minechem.item.polytool.PolytoolUpgradeType;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class PolytoolTypeLead extends PolytoolUpgradeType
{

	public PolytoolTypeLead()
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
		if (!target.worldObj.isRemote)
		{
			List targets = target.worldObj.getEntitiesWithinAABB(EntityLivingBase.class,
					AxisAlignedBB.getBoundingBox(target.posX - power * 3, target.posY - power * 3, target.posZ - power * 3, target.posX + power * 3, target.posY + power * 3, target.posZ + power * 3));
			Iterator iter = targets.iterator();
			while (iter.hasNext())
			{
				EntityLivingBase entity = (EntityLivingBase) iter.next();
				if (entity != player)
				{
					entity.motionY = -50;
				}
			}
		}
	}

	@Override
	public void onBlockDestroyed(ItemStack itemStack, World world, Block block, int x, int y, int z, EntityLivingBase entityLiving)
	{
	}

	@Override
	public ElementEnum getElement()
	{

		return ElementEnum.Pb;
	}

	@Override
	public void onTick()
	{
	}

	@Override
	public String getDescription()
	{

		return "Sends nearby entities flying to the ground";
	}

}
