package minechem;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
        Property prop;
        List<String> propOrder = new ArrayList<String>();

        prop = config.get(Configuration.CATEGORY_GENERAL, "worldgenore", true);
        prop.comment = StatCollector.translateToLocal("minechem.gui.config.worldgenore.description");
        prop.setLanguageKey("minechem.gui.config.worldgenore");
        WorldGenOre = prop.getBoolean();
        propOrder.add(prop.getName());

        prop = config.get(Configuration.CATEGORY_GENERAL, "uraniumoreclustersize", 3);
        prop.setMinValue(1).setMaxValue(10);
        prop.comment = StatCollector.translateToLocal("minechem.gui.config.uraniumoreclustersize.description");
        prop.setLanguageKey("minechem.gui.config.uraniumoreclustersize");
        UraniumOreClusterSize = prop.getInt();
        propOrder.add(prop.getName());

        prop = config.get(Configuration.CATEGORY_GENERAL, "uraniumoredensity", 5);
        prop.setMinValue(1).setMaxValue(64);
        prop.comment = StatCollector.translateToLocal("minechem.gui.config.uraniumoredensity.description");
        prop.setLanguageKey("minechem.gui.config.uraniumoredensity");
        UraniumOreDensity = prop.getInt();
        propOrder.add(prop.getName());

        prop = config.get(Configuration.CATEGORY_GENERAL, "uraniumorecraftable", true);
        prop.comment = StatCollector.translateToLocal("minechem.gui.config.uraniumorecraftable.description");
        prop.setLanguageKey("minechem.gui.config.uraniumorecraftable");
        UraniumOreCraftable = prop.getBoolean();
        propOrder.add(prop.getName());

        prop = config.get(Configuration.CATEGORY_GENERAL, "debugmode", false);
        prop.comment = StatCollector.translateToLocal("minechem.gui.config.debugmode.description");
        prop.setLanguageKey("minechem.gui.config.debugmode");
        DebugMode = prop.getBoolean();
        propOrder.add(prop.getName());

        prop = config.get(Configuration.CATEGORY_GENERAL, "updateradius", 20);
        prop.setMinValue(1).setMaxValue(50);
        prop.comment = StatCollector.translateToLocal("minechem.gui.config.updateradius.description");
        prop.setLanguageKey("minechem.gui.config.updateradius");
        UpdateRadius = prop.getInt();
        propOrder.add(prop.getName());

        prop = config.get(Configuration.CATEGORY_GENERAL, "allowautomation", true);
        prop.comment = StatCollector.translateToLocal("minechem.gui.config.allowautomation.description");
        prop.setLanguageKey("minechem.gui.config.allowautomation");
        AllowAutomation = prop.getBoolean();
        propOrder.add(prop.getName());

        prop = config.get(Configuration.CATEGORY_GENERAL, "foodspiking", true);
        prop.comment = StatCollector.translateToLocal("minechem.gui.config.foodspiking.description");
        prop.setLanguageKey("minechem.gui.config.foodspiking");
        FoodSpiking = prop.getBoolean();
        propOrder.add(prop.getName());

        prop = config.get(Configuration.CATEGORY_GENERAL, "swordeffects", true);
        prop.comment = StatCollector.translateToLocal("minechem.gui.config.swordeffects.description");
        prop.setLanguageKey("minechem.gui.config.swordeffects");
        SwordEffects = prop.getBoolean();
        propOrder.add(prop.getName());

        config.setCategoryPropertyOrder(Configuration.CATEGORY_GENERAL, propOrder);

        if (config.hasChanged())
        {
            config.save();
        }
    }
}
