package minechem.item.polytool.types;

import minechem.item.element.ElementEnum;
import minechem.item.polytool.PolytoolUpgradeType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;

public class PolytoolTypeArgon extends PolytoolUpgradeType
{
    @Override
    public void hitEntity(ItemStack itemStack, EntityLivingBase target, EntityLivingBase player)
    {
        if (!target.worldObj.canBlockSeeTheSky(MathHelper.floor_double(target.posX), MathHelper.floor_double(target.posY), MathHelper.floor_double(target.posZ)))
        {
            target.attackEntityFrom(DamageSource.inWall, power);
        }
    }

    @Override
    public ElementEnum getElement()
    {
        return ElementEnum.Ar;
    }

    @Override
    public String getDescription()
    {
        return "Does extra suffocation damage when in a cave";
    }

}
