package minechem.item.polytool;

import java.util.HashMap;
import minechem.item.element.ElementAlloyEnum;
import minechem.item.element.ElementEnum;
import minechem.item.polytool.types.PolytoolTypeArgon;
import minechem.item.polytool.types.PolytoolTypeBeryllium;
import minechem.item.polytool.types.PolytoolTypeBoron;
import minechem.item.polytool.types.PolytoolTypeBromine;
import minechem.item.polytool.types.PolytoolTypeCaesium;
import minechem.item.polytool.types.PolytoolTypeCalcium;
import minechem.item.polytool.types.PolytoolTypeCarbon;
import minechem.item.polytool.types.PolytoolTypeChlorine;
import minechem.item.polytool.types.PolytoolTypeChromium;
import minechem.item.polytool.types.PolytoolTypeFluorine;
import minechem.item.polytool.types.PolytoolTypeFrancium;
import minechem.item.polytool.types.PolytoolTypeGold;
import minechem.item.polytool.types.PolytoolTypeHelium;
import minechem.item.polytool.types.PolytoolTypeHydrogen;
import minechem.item.polytool.types.PolytoolTypeIron;
import minechem.item.polytool.types.PolytoolTypeKrypton;
import minechem.item.polytool.types.PolytoolTypeLead;
import minechem.item.polytool.types.PolytoolTypeMagnesium;
import minechem.item.polytool.types.PolytoolTypeMercury;
import minechem.item.polytool.types.PolytoolTypeNeon;
import minechem.item.polytool.types.PolytoolTypeNickel;
import minechem.item.polytool.types.PolytoolTypeNitrogen;
import minechem.item.polytool.types.PolytoolTypeOxygen;
import minechem.item.polytool.types.PolytoolTypePhosphorus;
import minechem.item.polytool.types.PolytoolTypePlatnium;
import minechem.item.polytool.types.PolytoolTypeRubidium;
import minechem.item.polytool.types.PolytoolTypeSilicon;
import minechem.item.polytool.types.PolytoolTypeSilver;
import minechem.item.polytool.types.PolytoolTypeSodium;
import minechem.item.polytool.types.PolytoolTypeSulfur;
import minechem.item.polytool.types.PolytoolTypeTitanium;
import minechem.item.polytool.types.PolytoolTypeUranium;
import minechem.item.polytool.types.PolytoolTypeZirconium;

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
