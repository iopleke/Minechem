package minechem;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class Settings
{
    // Config file
    public static Configuration config;

    // --------
    // FEATURES
    // --------
    // Determines if the mod will generate ore at all.
    public static boolean WorldGenOre = true;

    // Size of Uranium ore clusters
    public static int UraniumOreClusterSize = 3;

    // How many times per chunk will uranium attempt to generate?
    public static int UraniumOreDensity = 5;

    // Can Minechem uranium be crafted from other uranium?
    public static boolean UraniumOreCraftable = true;

    // Determines if the mod will print out tons of extra information while running.
    public static boolean DebugMode = false;

    // Determines how far away in blocks a packet will be sent to players in a given dimension to reduce packet spam.
    public static int UpdateRadius = 20;

    // Enabling automation can allow duping. Disabled by default.
    public static boolean AllowAutomation = false;

    // Disabling of enchants and spikes
    public static boolean FoodSpiking = true;
    public static boolean SwordEffects = true;

    public static void init(File configFile)
    {
        if (config == null)
        {
            config = new Configuration(configFile);
            loadConfig();
        }
    }

    @SubscribeEvent
    public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.modID.equalsIgnoreCase(Minechem.ID))
        {
            loadConfig();
        }
    }

    private static void loadConfig()
    {
        WorldGenOre = config.getBoolean(StatCollector.translateToLocal("minechem.gui.config.worldgenore"), Configuration.CATEGORY_GENERAL, true, StatCollector.translateToLocal("minechem.gui.config.worldgenore.description"));
        UraniumOreClusterSize = config.getInt(StatCollector.translateToLocal("minechem.gui.config.uraniumoreclustersize"), Configuration.CATEGORY_GENERAL, 3, 1, 10, StatCollector.translateToLocal("minechem.gui.config.uraniumoreclustersize.description"));
        UraniumOreDensity = config.getInt(StatCollector.translateToLocal("minechem.gui.config.uraniumoredensity"), Configuration.CATEGORY_GENERAL, 5, 1, 64, StatCollector.translateToLocal("minechem.gui.config.uraniumoredensity.description"));
        UraniumOreCraftable = config.getBoolean(StatCollector.translateToLocal("minechem.gui.config.uraniumorecraftable"), Configuration.CATEGORY_GENERAL, true, StatCollector.translateToLocal("minechem.gui.config.uraniumorecraftable.description"));
        DebugMode = config.getBoolean(StatCollector.translateToLocal("minechem.gui.config.debugmode"), Configuration.CATEGORY_GENERAL, false, StatCollector.translateToLocal("minechem.gui.config.debugmode.description"));
        UpdateRadius = config.getInt(StatCollector.translateToLocal("minechem.gui.config.updateradius"), Configuration.CATEGORY_GENERAL, 20, 1, 50, StatCollector.translateToLocal("minechem.gui.config.updateradius.description"));
        AllowAutomation = config.getBoolean(StatCollector.translateToLocal("minechem.gui.config.allowautomation"), Configuration.CATEGORY_GENERAL, false, StatCollector.translateToLocal("minechem.gui.config.allowautomation"));
        FoodSpiking = config.getBoolean(StatCollector.translateToLocal("minechem.gui.config.foodspiking"), Configuration.CATEGORY_GENERAL, true, StatCollector.translateToLocal("minechem.gui.config.foodspiking.description"));
        SwordEffects = config.getBoolean(StatCollector.translateToLocal("minechem.gui.config.swordeffects"), Configuration.CATEGORY_GENERAL, true, StatCollector.translateToLocal("minechem.gui.config.swordeffects.description"));

        if (config.hasChanged())
        {
            config.save();
        }
    }
}
