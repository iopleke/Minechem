package minechem.potion;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class PotionCoatingSubscribe
{

	@SubscribeEvent
	public void entityAttacked(LivingAttackEvent event)
	{
		if (event.source.getSourceOfDamage() instanceof EntityLivingBase)
		{
			EntityLivingBase entity = (EntityLivingBase) event.source.getSourceOfDamage();
			ItemStack weapon = entity.getHeldItem();
			if (weapon == null)
			{
				return;
			}
			NBTTagList list = weapon.getEnchantmentTagList();
			if (list == null)
			{
				return;
			}
			for (int i = 0; i < list.tagCount(); i++)
			{
				NBTTagCompound enchantmentTag = list.getCompoundTagAt(i);
				Enchantment enchant = Enchantment.enchantmentsList[enchantmentTag.getShort("id")];
				if (enchant instanceof PotionEnchantmentCoated)
				{
					((PotionEnchantmentCoated) enchant).applyEffect(event.entityLiving);
				}
			}
		}
	}
}
