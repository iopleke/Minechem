package minechem;

import cpw.mods.fml.client.config.IConfigElement;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.config.ConfigElement;
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

	// Power usage
	public static boolean powerUseEnabled = true;
	public static int decompositionCost = 80;
	
	// Power base storage values
	public static int synthesisMaxStorage = 10240;
	public static int decomposerMaxStorage = 1024;

	//Blacklisting
	public static String[] DecomposerBlacklist =
	{
	};
	public static String[] SynthesisMachineBlacklist =
	{
	};

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
		List<String> configList = new ArrayList<String>();

		config.addCustomCategoryComment(StatCollector.translateToLocal("config.worldgen.name"), StatCollector.translateToLocal("config.worldgen.description"));
		config.addCustomCategoryComment(StatCollector.translateToLocal("config.decomposer.blacklist.name"), StatCollector.translateToLocal("config.decomposer.blacklist.description"));
		config.addCustomCategoryComment(StatCollector.translateToLocal("config.synthesis.blacklist.name"), StatCollector.translateToLocal("config.synthesis.blacklist.description"));
		config.addCustomCategoryComment(Configuration.CATEGORY_GENERAL, StatCollector.translateToLocal("config.general.description"));

		prop = config.get(StatCollector.translateToLocal("config.worldgen.name"), "generateUraniumOre", Settings.WorldGenOre);
		prop.comment = StatCollector.translateToLocal("config.worldgenore.description");
		prop.setLanguageKey("config.worldgenore");
		WorldGenOre = prop.getBoolean();
		configList.add(prop.getName());

		prop = config.get(StatCollector.translateToLocal("config.worldgen.name"), "uraniumOreClusterSize", Settings.UraniumOreClusterSize);
		prop.setMinValue(1).setMaxValue(10);
		prop.comment = StatCollector.translateToLocal("config.uraniumoreclustersize.description");
		prop.setLanguageKey("config.uraniumoreclustersize");
		UraniumOreClusterSize = prop.getInt();
		configList.add(prop.getName());

		prop = config.get(StatCollector.translateToLocal("config.worldgen.name"), "uraniumoredensity", Settings.UraniumOreDensity);
		prop.setMinValue(1).setMaxValue(64);
		prop.comment = StatCollector.translateToLocal("config.uraniumoredensity.description");
		prop.setLanguageKey("config.uraniumoredensity");
		UraniumOreDensity = prop.getInt();
		configList.add(prop.getName());

		prop = config.get(StatCollector.translateToLocal("config.worldgen.name"), "uraniumOreCraftable", Settings.UraniumOreCraftable);
		prop.comment = StatCollector.translateToLocal("config.uraniumorecraftable.description");
		prop.setLanguageKey("config.uraniumorecraftable");
		UraniumOreCraftable = prop.getBoolean();
		configList.add(prop.getName());

		prop = config.get(Configuration.CATEGORY_GENERAL, "debugMode", Settings.DebugMode);
		prop.comment = StatCollector.translateToLocal("config.debugmode.description");
		prop.setLanguageKey("config.debugmode");
		DebugMode = prop.getBoolean();
		configList.add(prop.getName());

		prop = config.get(Configuration.CATEGORY_GENERAL, "updateRadius", Settings.UpdateRadius);
		prop.setMinValue(1).setMaxValue(50);
		prop.comment = StatCollector.translateToLocal("config.updateradius.description");
		prop.setLanguageKey("config.updateradius");
		UpdateRadius = prop.getInt();
		configList.add(prop.getName());

		prop = config.get(Configuration.CATEGORY_GENERAL, "allowAutomation", Settings.AllowAutomation);
		prop.comment = StatCollector.translateToLocal("config.allowautomation.description");
		prop.setLanguageKey("config.allowautomation");
		AllowAutomation = prop.getBoolean();
		configList.add(prop.getName());

		prop = config.get(Configuration.CATEGORY_GENERAL, "foodSpiking", Settings.FoodSpiking);
		prop.comment = StatCollector.translateToLocal("config.foodspiking.description");
		prop.setLanguageKey("config.foodspiking");
		FoodSpiking = prop.getBoolean();
		configList.add(prop.getName());

		prop = config.get(Configuration.CATEGORY_GENERAL, "swordEffects", Settings.SwordEffects);
		prop.comment = StatCollector.translateToLocal("config.swordeffects.description");
		prop.setLanguageKey("config.swordeffects");
		SwordEffects = prop.getBoolean();
		configList.add(prop.getName());

		prop = config.get("decomposer blacklist", "Decomposer Blacklist", new String[]
		{
			"dirt",
			"gravel"
		});
		prop.setLanguageKey("config.decomposerblacklist").setRequiresMcRestart(true);
		prop.comment = StatCollector.translateToLocal("config.decomposerblacklist.description");
		DecomposerBlacklist = prop.getStringList();
		configList.add(prop.getName());

		prop = config.get("synthesis blacklist", "Synthesis Blacklist", new String[]
		{
			"dirt",
			"gravel"
		});
		prop.setLanguageKey("config.synthesismachineblacklist").setRequiresMcRestart(true);
		prop.comment = StatCollector.translateToLocal("config.synthesismachineblacklist.description");
		SynthesisMachineBlacklist = prop.getStringList();
		configList.add(prop.getName());

		if (config.hasChanged())
		{
			config.save();
		}
	}

	public static List<IConfigElement> getConfigElements()
	{
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		list.addAll(new ConfigElement(config.getCategory(StatCollector.translateToLocal("config.worldgen.name"))).getChildElements());
		list.addAll(new ConfigElement(config.getCategory(StatCollector.translateToLocal("config.decomposer.blacklist.name"))).getChildElements());
		list.addAll(new ConfigElement(config.getCategory(StatCollector.translateToLocal("config.synthesis.blacklist.name"))).getChildElements());
		list.addAll(new ConfigElement(config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements());
		return list;
	}
}
