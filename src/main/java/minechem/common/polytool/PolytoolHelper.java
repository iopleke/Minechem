package minechem.common.polytool;

import java.util.HashMap;

import minechem.api.core.EnumElement;
import minechem.common.polytool.types.PolytoolTypeArgon;
import minechem.common.polytool.types.PolytoolTypeBeryllium;
import minechem.common.polytool.types.PolytoolTypeBoron;
import minechem.common.polytool.types.PolytoolTypeBromine;
import minechem.common.polytool.types.PolytoolTypeCaesium;
import minechem.common.polytool.types.PolytoolTypeCalcium;
import minechem.common.polytool.types.PolytoolTypeCarbon;
import minechem.common.polytool.types.PolytoolTypeChlorine;
import minechem.common.polytool.types.PolytoolTypeChromium;
import minechem.common.polytool.types.PolytoolTypeFluorine;
import minechem.common.polytool.types.PolytoolTypeFrancium;
import minechem.common.polytool.types.PolytoolTypeGold;
import minechem.common.polytool.types.PolytoolTypeHelium;
import minechem.common.polytool.types.PolytoolTypeHydrogen;
import minechem.common.polytool.types.PolytoolTypeIron;
import minechem.common.polytool.types.PolytoolTypeKrypton;
import minechem.common.polytool.types.PolytoolTypeLead;
import minechem.common.polytool.types.PolytoolTypeLithium;
import minechem.common.polytool.types.PolytoolTypeMagnesium;
import minechem.common.polytool.types.PolytoolTypeMercury;
import minechem.common.polytool.types.PolytoolTypeNeon;
import minechem.common.polytool.types.PolytoolTypeNickel;
import minechem.common.polytool.types.PolytoolTypeNitrogen;
import minechem.common.polytool.types.PolytoolTypeOxygen;
import minechem.common.polytool.types.PolytoolTypePhosphorus;
import minechem.common.polytool.types.PolytoolTypePlatnium;
import minechem.common.polytool.types.PolytoolTypeRubidium;
import minechem.common.polytool.types.PolytoolTypeSilicon;
import minechem.common.polytool.types.PolytoolTypeSilver;
import minechem.common.polytool.types.PolytoolTypeSodium;
import minechem.common.polytool.types.PolytoolTypeSulfur;
import minechem.common.polytool.types.PolytoolTypeTitanium;
import minechem.common.polytool.types.PolytoolTypeUranium;
import minechem.common.polytool.types.PolytoolTypeZirconium;

public class PolytoolHelper
{

    public static boolean loaded;
    public static HashMap<EnumElement, Class<PolytoolUpgradeType>> types = new HashMap();
    public static Class[] typeClasses =
    { PolytoolTypeArgon.class, PolytoolTypeLithium.class, PolytoolTypeBeryllium.class, PolytoolTypeMagnesium.class, PolytoolTypeBoron.class, PolytoolTypeMercury.class, PolytoolTypeBromine.class, PolytoolTypeNeon.class, PolytoolTypeCaesium.class,
            PolytoolTypeNickel.class, PolytoolTypeCalcium.class, PolytoolTypeNitrogen.class, PolytoolTypeCarbon.class, PolytoolTypeOxygen.class, PolytoolTypeChlorine.class, PolytoolTypePhosphorus.class, PolytoolTypeChromium.class,
            PolytoolTypePlatnium.class, PolytoolTypeFluorine.class, PolytoolTypeRubidium.class, PolytoolTypeFrancium.class, PolytoolTypeSilicon.class, PolytoolTypeGold.class, PolytoolTypeSilver.class, PolytoolTypeHelium.class, PolytoolTypeSodium.class,
            PolytoolTypeHydrogen.class, PolytoolTypeSulfur.class, PolytoolTypeIron.class, PolytoolTypeTitanium.class, PolytoolTypeKrypton.class, PolytoolTypeUranium.class, PolytoolTypeLead.class, PolytoolTypeZirconium.class };

    public static PolytoolUpgradeType getTypeFromElement(EnumElement element, float power)
    {
        if (!loaded)
        {
            loadTypes();
        }
        for (EnumAlloy alloy : EnumAlloy.values())
        {
            if (alloy.element == element)
            {
                return new PolytoolTypeAlloy(alloy, power);
            }

        }
        PolytoolUpgradeType upgrade = null;
        try
        {
            if (types.get(element) == null)
            {
                return null;
            }
            upgrade = types.get(element).newInstance();
        }
        catch (InstantiationException e)
        {

            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {

            e.printStackTrace();
        }
        upgrade.power = power;
        return upgrade;
    }

    private static void loadTypes()
    {
        for (Class clazz : typeClasses)
        {
            try
            {
                clazz.newInstance();
            }
            catch (InstantiationException e)
            {

                e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {

                e.printStackTrace();
            }
        }
        loaded = true;
    }

}
