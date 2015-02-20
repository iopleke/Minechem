package minechem.item.polytool.types;

import minechem.item.element.ElementEnum;
import minechem.item.polytool.PolytoolUpgradeType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class PolytoolTypeSulfur extends PolytoolUpgradeType
{
    @Override
    public void hitEntity(ItemStack itemStack, EntityLivingBase target, EntityLivingBase player)
    {
        target.setFire((int)(power + 1));
    }

    @Override
    public ElementEnum getElement()
    {
        return ElementEnum.S;
    }

    @Override
    public String getDescription()
    {
        return "Sets entities on fire";
    }

}
