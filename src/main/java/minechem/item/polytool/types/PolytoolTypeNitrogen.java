package minechem.item.polytool.types;

import minechem.item.element.ElementEnum;
import minechem.item.polytool.PolytoolUpgradeType;

public class PolytoolTypeNitrogen extends PolytoolUpgradeType
{
    @Override
    public ElementEnum getElement()
    {
        return ElementEnum.N;
    }

    @Override
    public String getDescription()
    {

        return "Preservation allows retrieving of fresh meat from rotten flesh";
    }

}
