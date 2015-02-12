package minechem;

import cpw.mods.fml.client.config.IConfigElement;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class Config
{
    public static Configuration config;

    // turns on extra logging printouts
    public static boolean debugMode = true;

    // turns on to show Patreon tab in GUI
    public static boolean enablePatreon = true;

    // turns on to copy the newest elements list from jar
    public static boolean useDefaultElements = true;
    // turns on to copy the newest molecules list from jar
    public static boolean useDefaultMolecules = true;
    // turns on to copy the newest researchPages list from jar
    public static boolean useDefaultResearchPages = true;

    public static List<IConfigElement> getConfigElements()
    {
        List<IConfigElement> list = new ArrayList<IConfigElement>();
        list.addAll(new ConfigElement(config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements());
        return list;
    }

    public static void init()
    {

        if (config == null)
        {
            config = new Configuration(new File(Compendium.Config.configPrefix + "Minechem.cfg"));
            loadConfig();
        }
    }

    private static void loadConfig()
    {
        Property prop;
        List<String> configList = new ArrayList<String>();

        config.addCustomCategoryComment(Configuration.CATEGORY_GENERAL, StatCollector.translateToLocal("config.general.description"));

        prop = config.get(Configuration.CATEGORY_GENERAL, "debugMode", Config.debugMode);
        prop.comment = StatCollector.translateToLocal("config.debugMode");
        prop.setLanguageKey("config.debugMode.tooltip");
        debugMode = prop.getBoolean();
        configList.add(prop.getName());

        prop = config.get(Configuration.CATEGORY_GENERAL, "useDefaultElements", Config.useDefaultElements);
        prop.comment = StatCollector.translateToLocal("config.useDefaultElements");
        prop.setLanguageKey("config.useDefaultElements.tooltip");
        useDefaultElements = prop.getBoolean();
        configList.add(prop.getName());

        prop = config.get(Configuration.CATEGORY_GENERAL, "useDefaultMolecules", Config.useDefaultMolecules);
        prop.comment = StatCollector.translateToLocal("config.useDefaultMolecules");
        prop.setLanguageKey("config.useDefaultMolecules.tooltip");
        useDefaultMolecules = prop.getBoolean();
        configList.add(prop.getName());
        
        prop = config.get(Configuration.CATEGORY_GENERAL, "useDefaultResearchPages", Config.useDefaultResearchPages);
        prop.comment = StatCollector.translateToLocal("config.useDefaultResearchPages");
        prop.setLanguageKey("config.useDefaultResearchPages.tooltip");
        useDefaultResearchPages = prop.getBoolean();
        configList.add(prop.getName());

        prop = config.get(Configuration.CATEGORY_GENERAL, "enablePatreon", Config.enablePatreon);
        prop.comment = StatCollector.translateToLocal("config.enablePatreon");
        prop.setLanguageKey("config.enablePatreon.tooltip");
        enablePatreon = prop.getBoolean();
        configList.add(prop.getName());

        if (config.hasChanged())
        {
            config.save();
        }
    }

    @SubscribeEvent
    public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.modID.equalsIgnoreCase(Compendium.Naming.id))
        {
            loadConfig();
        }
    }
}
