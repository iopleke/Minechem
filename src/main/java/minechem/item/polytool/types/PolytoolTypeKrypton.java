package minechem.item.polytool.types;

import minechem.item.element.ElementEnum;
import minechem.item.polytool.PolytoolUpgradeType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class PolytoolTypeKrypton extends PolytoolUpgradeType
{

    @Override
    public void hitEntity(ItemStack itemStack, EntityLivingBase target, EntityLivingBase player)
    {
        if (target instanceof EntityPlayer)
        {
            EntityPlayer entityPlayer = (EntityPlayer)target;
            if (entityPlayer.getTotalArmorValue() > 23)
            {
                entityPlayer.attackEntityFrom(DamageSource.outOfWorld, power);
            }
        }
    }

    @Override
    public ElementEnum getElement()
    {
        return ElementEnum.Kr;
    }

    @Override
    public String getDescription()
    {
        return "Does extra damage to heavily armored players";
    }

}
