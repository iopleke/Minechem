package minechem;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.logging.Level;
import net.minecraftforge.common.config.Configuration;

public class Settings
{
    @Retention(RetentionPolicy.RUNTIME)
    private static @interface CfgBool
    {
    }

    @Retention(RetentionPolicy.RUNTIME)
    private static @interface CfgId
    {
        public boolean block() default false;
    }

    @Retention(RetentionPolicy.RUNTIME)
    private static @interface CfgInt
    {
    }


    // --------
    // FEATURES
    // --------
    // Determines if the mod will generate ore at all.
    public static boolean WorldGenOre = true;

    // Determines if the mod will print out tons of extra information while running.
    public static boolean DebugMode = false;

    // Determines how far away in blocks a packet will be sent to players in a given dimension to reduce packet spam.
    public static int UpdateRadius = 20;

    // Enabling automation can allow duping. Disabled by default.
    public static boolean AllowAutomation = false;

    public static void load(Configuration config)
    {
        try
        {
            config.load();
            Field[] fields = Settings.class.getFields();
            for (Field field : fields)
            {
                CfgId annoBlock = field.getAnnotation(CfgId.class);
                CfgBool annoBool = field.getAnnotation(CfgBool.class);
                CfgInt annoInt = field.getAnnotation(CfgInt.class);

                // Config property is block or item.
                if (annoBlock != null && annoBool == null && annoInt == null)
                {
                    int id = field.getInt(null);
//                    if (annoBlock.block())
//                    {
                        id = config.get(Configuration.CATEGORY_GENERAL, field.getName(), id).getInt();//TODO: Why?
//                    } else
//                    {
//                        id = config.getItem(field.getName(), id).getInt();
//                    }
                    field.setInt(null, id);
                } else if (annoBool != null && annoBlock == null && annoInt == null)
                {
                    // Config property is bool.
                    if (field.isAnnotationPresent(CfgBool.class))
                    {
                        boolean bool = field.getBoolean(null);
                        bool = config.get(Configuration.CATEGORY_GENERAL, field.getName(), bool).getBoolean(bool);
                        field.setBoolean(null, bool);
                    }
                } else if (annoBool == null && annoBlock == null && annoInt != null)
                {
                    // Config property is int.
                    if (field.isAnnotationPresent(CfgInt.class))
                    {
                        int someInt = field.getInt(null);
                        someInt = config.get(Configuration.CATEGORY_GENERAL, field.getName(), someInt).getInt(someInt);
                        field.setInt(null, someInt);
                    }
                }
            }
        } catch (Exception e)
        {
            // failed to load config log
            ModMinechem.LOGGER.warn("Failed to load configuration file!");
        } finally
        {
            config.save();
        }
    }

}
