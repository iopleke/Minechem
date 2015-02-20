package minechem.item.polytool.types;

import minechem.item.element.ElementEnum;
import minechem.item.polytool.PolytoolUpgradeType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

public class PolytoolTypeNeon extends PolytoolUpgradeType
{
    @Override
    public void hitEntity(ItemStack itemStack, EntityLivingBase target, EntityLivingBase player)
    {
        player.addPotionEffect(new PotionEffect(16, (int)(power * 10)));
    }

    @Override
    public ElementEnum getElement()
    {
        return ElementEnum.Ne;
    }

    @Override
    public String getDescription()
    {
        return "Gives players night vision during combat";
    }

}
