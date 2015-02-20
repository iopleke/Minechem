package minechem.item.polytool;

import minechem.item.element.ElementAlloyEnum;
import minechem.item.element.ElementEnum;
import minechem.item.polytool.types.*;

import java.util.HashMap;

public class PolytoolHelper
{

    public static HashMap<ElementEnum, PolytoolUpgradeType> types = new HashMap<ElementEnum, PolytoolUpgradeType>();

    public static PolytoolUpgradeType getTypeFromElement(ElementEnum element, float power)
    {
        if (types.containsKey(element))
        {
            return types.get(element).setPower(power);
        }
        return null;
    }

    static
    {
        register(new PolytoolTypeArgon());
        register(new PolytoolTypeBeryllium());
        register(new PolytoolTypeBoron());
        register(new PolytoolTypeBromine());
        register(new PolytoolTypeCaesium());
        register(new PolytoolTypeCalcium());
        register(new PolytoolTypeCarbon());
        register(new PolytoolTypeChlorine());
        register(new PolytoolTypeChromium());
        register(new PolytoolTypeFluorine());
        register(new PolytoolTypeFrancium());
        register(new PolytoolTypeGold());
        register(new PolytoolTypeHelium());
        register(new PolytoolTypeHydrogen());
        register(new PolytoolTypeIron());
        register(new PolytoolTypeKrypton());
        register(new PolytoolTypeLead());
        register(new PolytoolTypeMagnesium());
        register(new PolytoolTypeMercury());
        register(new PolytoolTypeNeon());
        register(new PolytoolTypeNickel());
        register(new PolytoolTypeNitrogen());
        register(new PolytoolTypeOxygen());
        register(new PolytoolTypePhosphorus());
        register(new PolytoolTypePlatnium());
        register(new PolytoolTypeRubidium());
        register(new PolytoolTypeSilicon());
        register(new PolytoolTypeSilver());
        register(new PolytoolTypeSodium());
        register(new PolytoolTypeSulfur());
        register(new PolytoolTypeTitanium());
        register(new PolytoolTypeUranium());
        register(new PolytoolTypeZirconium());
        for (ElementAlloyEnum alloy : ElementAlloyEnum.values())
            register(new PolytoolTypeAlloy(alloy));
    }

    private static void register(PolytoolUpgradeType upgradeType)
    {
        if (!types.containsKey(upgradeType.getElement()))
        {
            types.put(upgradeType.getElement(), upgradeType);
        }
    }
}
