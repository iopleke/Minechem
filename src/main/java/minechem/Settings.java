package minechem;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.logging.Level;

import minechem.utils.IDManager;
import net.minecraft.launchwrapper.LogWrapper;
import net.minecraftforge.common.Configuration;

public class Settings
{
    // Modified from Source
    // http://www.minecraftforge.net/wiki/Reference_Mod_File

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
    public static final IDManager idManager = new IDManager(4012, 4736);

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
    int EmptyPills = getNextItemID();
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

    // Particle Physics
    public static @CfgId(block = true)
    int Emitter = getNextBlockID();
    public static @CfgId(block = true)
    int PolarizedGlass = getNextBlockID();
    public static @CfgId(block = true)
    int SeriesReceptor = getNextBlockID();
    public static @CfgId(block = true)
    int ControlGlass = getNextBlockID();
    public static @CfgId(block = true)
    int InfiniteEmitter = getNextBlockID();

    // --------
    // FEATURES
    // --------

    // Determines if the mod will generate ore at all.
    public static @CfgBool
    boolean WorldGenOre = true;

    // Determines if the mod will print out tons of extra information while running.
    public static @CfgBool
    boolean DebugMode = true;

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
                    }
                    else
                    {
                        id = config.getItem(field.getName(), id).getInt();
                    }
                    field.setInt(null, id);
                }
                else if (annoBool != null && annoBlock == null && annoInt == null)
                {
                    // Config property is bool.
                    if (field.isAnnotationPresent(CfgBool.class))
                    {
                        boolean bool = field.getBoolean(null);
                        bool = config.get(Configuration.CATEGORY_GENERAL, field.getName(), bool).getBoolean(bool);
                        field.setBoolean(null, bool);
                    }
                }
            }
        }
        catch (Exception e)
        {
            // failed to load config log
            LogWrapper.log(Level.WARNING, "Failed to load configuration file!");
        }
        finally
        {
            config.save();
        }
    }

}