package minechem.item.polytool.types;

import minechem.item.element.ElementEnum;
import minechem.item.polytool.PolytoolUpgradeType;

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
