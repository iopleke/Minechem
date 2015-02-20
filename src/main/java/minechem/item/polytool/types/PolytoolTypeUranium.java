package minechem.item.polytool.types;

import minechem.item.element.ElementEnum;
import minechem.item.polytool.PolytoolUpgradeType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;

import java.util.Iterator;
import java.util.List;

public class PolytoolTypeUranium extends PolytoolUpgradeType
{

    public PolytoolTypeUranium()
    {
        super();
    }

    @Override
    public void hitEntity(ItemStack itemStack, EntityLivingBase target, EntityLivingBase player)
    {
        List targets = target.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(target.posX - power, target.posY - power, target.posZ - power, target.posX + power, target.posY + power, target.posZ + power));
        Iterator iter = targets.iterator();
        while (iter.hasNext())
        {
            EntityLivingBase entity = (EntityLivingBase)iter.next();
            entity.addPotionEffect(new PotionEffect(20, 200, 1));

        }
    }

    @Override
    public ElementEnum getElement()
    {
        return ElementEnum.U;
    }

    @Override
    public String getDescription()
    {
        return "Area of Effect wither";
    }

}
