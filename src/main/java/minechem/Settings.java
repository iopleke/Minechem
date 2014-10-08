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
	public static boolean generateOre = true;

	// Size of Uranium ore clusters
	public static int UraniumOreClusterSize = 3;

	// How many times per chunk will uranium attempt to generate?
	public static int UraniumOreDensity = 5;

	// Determines if the mod will print out tons of extra information while running.
	public static boolean DebugMode = false;

	// Determines how far away in blocks a packet will be sent to players in a given dimension to reduce packet spam.
	public static int UpdateRadius = 20;

	// Enabling automation can allow duping. Enabled by default.
	public static boolean AllowAutomation = true;

	// Disabling of enchants, spikes and fluidEffects
	public static boolean FoodSpiking = true;
	public static boolean SwordEffects = true;
    public static boolean fluidEffects = true;

	// Power usage
	public static boolean powerUseEnabled = true;
	public static int costDecomposition = 1000;
	public static int synthesisMultiplier = 10;
	public static int fusionMultiplier = 100;
	public static int fissionMultiplier = 100;
    public static int energyPacketSize = 100;

	// Power base storage values
	public static int maxSynthesizerStorage = 100000;
	public static int maxDecomposerStorage = 10000;
	public static int maxFissionStorage = 100000;
	public static int maxFusionStorage = 100000;

	// Chemical Explosion
	public static boolean reactionItemMeetFluid=true;
	public static boolean reactionFluidMeetFluid=true;
	
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

		config.addCustomCategoryComment("worldgen", StatCollector.translateToLocal("config.worldgen.description"));
		config.addCustomCategoryComment("blacklist", StatCollector.translateToLocal("config.blacklist.description"));
		config.addCustomCategoryComment("power", StatCollector.translateToLocal("config.power.description"));
		config.addCustomCategoryComment(Configuration.CATEGORY_GENERAL, StatCollector.translateToLocal("config.general.description"));

		prop = config.get("worldgen", "generateOre", Settings.generateOre);
		prop.comment = StatCollector.translateToLocal("config.worldgen.ore.description");
		prop.setLanguageKey("config.worldgen.ore.tooltip");
		generateOre = prop.getBoolean();
		configList.add(prop.getName());

		prop = config.get("worldgen", "uraniumOreClusterSize", Settings.UraniumOreClusterSize);
		prop.setMinValue(1).setMaxValue(10);
		prop.comment = StatCollector.translateToLocal("config.uraniumoreclustersize.description");
		prop.setLanguageKey("config.uraniumoreclustersize");
		UraniumOreClusterSize = prop.getInt();
		configList.add(prop.getName());

		prop = config.get("worldgen", "uraniumoredensity", Settings.UraniumOreDensity);
		prop.setMinValue(1).setMaxValue(64);
		prop.comment = StatCollector.translateToLocal("config.uraniumoredensity.description");
		prop.setLanguageKey("config.uraniumoredensity");
		UraniumOreDensity = prop.getInt();
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

        prop = config.get(Configuration.CATEGORY_GENERAL, "fluidEffects", Settings.SwordEffects);
        prop.comment = StatCollector.translateToLocal("config.fluideffects.description");
        prop.setLanguageKey("config.fluideffects");
        fluidEffects = prop.getBoolean();
        configList.add(prop.getName());
        
        prop = config.get(Configuration.CATEGORY_GENERAL, "reactionItemMeetFluid", Settings.reactionItemMeetFluid);
        prop.comment = StatCollector.translateToLocal("config.reactionItemMeetFluid.description");
        prop.setLanguageKey("config.reactionItemMeetFluid");
        reactionItemMeetFluid = prop.getBoolean();
        configList.add(prop.getName());
        
        prop = config.get(Configuration.CATEGORY_GENERAL, "reactionFluidMeetFluid", Settings.reactionFluidMeetFluid);
        prop.comment = StatCollector.translateToLocal("config.reactionFluidMeetFluid.description");
        prop.setLanguageKey("config.reactionFluidMeetFluid");
        reactionFluidMeetFluid = prop.getBoolean();
        configList.add(prop.getName());

		prop = config.get("blacklist", "decomposition", new String[]
		{
			"dirt",
			"gravel"
		});
		prop.setLanguageKey("config.blacklist.decomposition.tooltip").setRequiresMcRestart(true);
		prop.comment = StatCollector.translateToLocal("config.blacklist.decomposition.example");
		DecomposerBlacklist = prop.getStringList();
		configList.add(prop.getName());

		prop = config.get("blacklist", "synthesis", new String[]
		{
			"diamond",
			"emerald"
		});
		prop.setLanguageKey("config.blacklist.synthesis.tooltip").setRequiresMcRestart(true);
		prop.comment = StatCollector.translateToLocal("config.blacklist.synthesis.example");
		SynthesisMachineBlacklist = prop.getStringList();
		configList.add(prop.getName());

		prop = config.get("power", "enable", Settings.powerUseEnabled);
		prop.comment = StatCollector.translateToLocal("config.power.enable.description");
		prop.setLanguageKey("config.power.enable.name").setRequiresMcRestart(true);
		powerUseEnabled = prop.getBoolean();
		configList.add(prop.getName());

		prop = config.get("power", "maxDecomposerStorage", Settings.maxDecomposerStorage);
		prop.comment = StatCollector.translateToLocal("config.power.decomposer.max.description");
		prop.setLanguageKey("config.power.decomposer.max.name");
		maxDecomposerStorage = prop.getInt();
		configList.add(prop.getName());

		prop = config.get("power", "costDecomposition", Settings.costDecomposition);
		prop.setMinValue(1).setMaxValue(Settings.maxDecomposerStorage);
		prop.comment = StatCollector.translateToLocal("config.power.decomposer.cost.description");
		prop.setLanguageKey("config.power.decomposer.cost.name");
		costDecomposition = prop.getInt();
		configList.add(prop.getName());

		prop = config.get("power", "maxSynthesizerStorage", Settings.maxSynthesizerStorage);
		prop.comment = StatCollector.translateToLocal("config.power.synthesizer.max.description");
		prop.setLanguageKey("config.power.synthesizer.max.name");
		maxSynthesizerStorage = prop.getInt();
		configList.add(prop.getName());

        prop = config.get("power", "energyPacketSize", Settings.energyPacketSize);
        prop.comment = StatCollector.translateToLocal("config.power.energyPacketSize.description");
        prop.setLanguageKey("config.power.energyPacketSize.max.name");
        energyPacketSize = prop.getInt();
        configList.add(prop.getName());

		prop = config.get("power", "costSythesisMultiplier", Settings.synthesisMultiplier);
		prop.setMinValue(1).setMaxValue(100);
		prop.comment = StatCollector.translateToLocal("config.power.synthesizer.cost.description");
		prop.setLanguageKey("config.power.synthesizer.cost.name");
		synthesisMultiplier = prop.getInt();
		configList.add(prop.getName());

		prop = config.get("power", "costFissionMultiplier", Settings.fissionMultiplier);
		prop.setMinValue(1).setMaxValue(100);
		prop.comment = StatCollector.translateToLocal("config.power.fission.cost.description");
		prop.setLanguageKey("config.power.fusion.cost.name");
		fissionMultiplier = prop.getInt();
		configList.add(prop.getName());

		prop = config.get("power", "maxFissionStorage", Settings.maxFissionStorage);
		prop.comment = StatCollector.translateToLocal("config.power.fission.max.description");
		prop.setLanguageKey("config.power.fission.max.name");
		maxFissionStorage = prop.getInt();
		configList.add(prop.getName());

		prop = config.get("power", "costFusionMultiplier", Settings.fusionMultiplier);
		prop.setMinValue(1).setMaxValue(100);
		prop.comment = StatCollector.translateToLocal("config.power.fusion.cost.description");
		prop.setLanguageKey("config.power.fusion.cost.name");
		fusionMultiplier = prop.getInt();
		configList.add(prop.getName());

		prop = config.get("power", "maxFusionStorage", Settings.maxFusionStorage);
		prop.comment = StatCollector.translateToLocal("config.power.fusion.max.description");
		prop.setLanguageKey("config.power.fusion.max.name");
		maxFusionStorage = prop.getInt();
		configList.add(prop.getName());

		if (config.hasChanged())
		{
			config.save();
		}
	}

	public static List<IConfigElement> getConfigElements()
	{
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		list.addAll(new ConfigElement(config.getCategory("worldgen")).getChildElements());
		list.addAll(new ConfigElement(config.getCategory("blacklist")).getChildElements());
		list.addAll(new ConfigElement(config.getCategory("power")).getChildElements());
		list.addAll(new ConfigElement(config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements());
		return list;
	}
}
