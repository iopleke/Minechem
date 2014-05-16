package minechem.common.coating;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class CoatingSubscribe
{

    @ForgeSubscribe
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
                NBTTagCompound enchantmentTag = (NBTTagCompound) list.tagAt(i);
                Enchantment enchant = Enchantment.enchantmentsList[enchantmentTag.getShort("id")];
                if (enchant instanceof EnchantmentCoated)
                {
                    ((EnchantmentCoated) enchant).applyEffect(event.entityLiving);
                }
            }
        }
    }
}
