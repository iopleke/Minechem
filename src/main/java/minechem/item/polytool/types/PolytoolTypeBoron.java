package minechem.item.polytool.types;

import minechem.item.element.ElementEnum;
import minechem.item.polytool.PolytoolUpgradeType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class PolytoolTypeBoron extends PolytoolUpgradeType
{
    @Override
    public void hitEntity(ItemStack itemStack, EntityLivingBase target, EntityLivingBase player)
    {
        if ((target instanceof EntitySpider || target instanceof EntityCaveSpider) && player instanceof EntityPlayer)
        {
            target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)player), 0.8F * power);
        }
    }

    @Override
    public ElementEnum getElement()
    {
        return ElementEnum.B;
    }

    @Override
    public String getDescription()
    {
        return "Kills spiders with pesticides";
    }

}
