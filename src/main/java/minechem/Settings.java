package minechem;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
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
        WorldGenOre = config.getBoolean("WorldGenOre", Configuration.CATEGORY_GENERAL, true, "Turn on and off ore gen");
        UraniumOreClusterSize = config.getInt("UraniumOreClusterSize", Configuration.CATEGORY_GENERAL, 3, 1, 10, "Size of uranium clusters");
        UraniumOreDensity = config.getInt("UraniumOreDensity", Configuration.CATEGORY_GENERAL, 5, 1, 64, "How often will uranium be generated in a chuck");
        UraniumOreCraftable = config.getBoolean("UraniumOreCraftable", Configuration.CATEGORY_GENERAL, true, "Can minechem uranium be crafted from other uranium");
        DebugMode = config.getBoolean("DebugMode", Configuration.CATEGORY_GENERAL, false, "Mod will print tons of info while running");
        UpdateRadius = config.getInt("UpdateRadius", Configuration.CATEGORY_GENERAL, 20, 1, 50, "Determines how far away in blocks packets will be set to players");
        AllowAutomation = config.getBoolean("AllowAutomation", Configuration.CATEGORY_GENERAL, false, "Mod will print tons of info while running");
        FoodSpiking = config.getBoolean("FoodSpiking", Configuration.CATEGORY_GENERAL, true, "Allow food spiking");
        SwordEffects = config.getBoolean("SwordEffects", Configuration.CATEGORY_GENERAL, true, "Enable enchantments");

        if (config.hasChanged())
        {
            config.save();
        }
    }
}
