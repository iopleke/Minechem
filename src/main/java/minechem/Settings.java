package minechem;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.logging.Level;

import minechem.utils.IDManager;
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

    // ** Auto-incrementing configuration IDs. Use this to make sure no config ID is the same. **/
    public static final IDManager idManager = new IDManager(256, 4097);

    public static int getNextBlockID()
    {
        return idManager.getNextBlockID();
    }

    public static int getNextItemID()
    {
        return idManager.getNextItemID();
    }

    // -----
    // ITEMS
    // -----
    public static @CfgId
    int Element = getNextItemID();
    public static @CfgId
    int Molecule = getNextItemID();
    public static @CfgId
    int Lens = getNextItemID();
    public static @CfgId
    int AtomicManipulator = getNextItemID();
    public static @CfgId
    int FusionStar = getNextItemID();
    public static @CfgId
    int Blueprint = getNextItemID();
    public static @CfgId
    int ChemistJournal = getNextItemID();
    public static @CfgId
    int Polytool = getNextItemID();

    // ------
    // BLOCKS
    // ------
    // Minechem
    public static @CfgId(block = true)
    int Microscope = getNextBlockID();
    public static @CfgId(block = true)
    int Decomposer = getNextBlockID();
    public static @CfgId(block = true)
    int Synthesis = getNextBlockID();
    public static @CfgId(block = true)
    int BlueprintProjector = getNextBlockID();
    public static @CfgId(block = true)
    int GhostBlock = getNextBlockID();
    public static @CfgId(block = true)
    int FusionChamber = getNextBlockID();
    public static @CfgId(block = true)
    int ChemicalStorage = getNextBlockID();
    public static @CfgId(block = true)
    int BluePrintPrinter = getNextBlockID();
    public static @CfgId(block = true)
    int UraniumOre = getNextBlockID();
    public static @CfgId(block = true)
    int LeadedChest = getNextBlockID();

    // --------
    // FEATURES
    // --------
    // Determines if the mod will generate ore at all.
    public static @CfgBool
    boolean WorldGenOre = true;

    // Determines if the mod will print out tons of extra information while running.
    public static @CfgBool
    boolean DebugMode = false;

    // Determines how far away in blocks a packet will be sent to players in a given dimension to reduce packet spam.
    public static @CfgInt
    int UpdateRadius = 20;

    // Enabling automation can allow duping. Disabled by default.
    public static @CfgBool
    boolean AllowAutomation = false;

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
                    if (annoBlock.block())
                    {
                        id = config.getBlock(field.getName(), id).getInt();
                    } else
                    {
                        id = config.getItem(field.getName(), id).getInt();
                    }
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
            ModMinechem.LOGGER.log(Level.WARNING, "Failed to load configuration file!");
        } finally
        {
            config.save();
        }
    }

}
