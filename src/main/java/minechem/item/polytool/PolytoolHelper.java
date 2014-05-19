package minechem.item.polytool;

import java.util.HashMap;

import minechem.api.core.EnumElement;
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
import minechem.item.polytool.types.PolytoolTypeLithium;
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
