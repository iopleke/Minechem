package minechem.item.polytool.types;

import minechem.item.element.ElementEnum;
import minechem.item.polytool.PolytoolUpgradeType;

public class PolytoolTypeSilicon extends PolytoolUpgradeType
{

    @Override
    public ElementEnum getElement()
    {
        return ElementEnum.Si;
    }

    @Override
    public String getDescription()
    {
        return "Advanced circuitry decomposes mob drops";
    }

}
