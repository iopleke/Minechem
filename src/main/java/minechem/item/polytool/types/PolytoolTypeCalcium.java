package minechem.item.polytool.types;

import minechem.item.element.ElementEnum;
import minechem.item.polytool.PolytoolUpgradeType;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PolytoolTypeCalcium extends PolytoolUpgradeType
{
    @Override
    public ElementEnum getElement()
    {
        return ElementEnum.Ca;
    }

    @Override
    public String getDescription()
    {
        return "Get extra bones";
    }

}
