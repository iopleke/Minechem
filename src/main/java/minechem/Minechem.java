package minechem;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Arrays;
import minechem.fluid.FluidHelper;
import minechem.gui.CreativeTabMinechem;
import minechem.gui.GuiHandler;
import minechem.gui.GuiTabHelp;
import minechem.gui.GuiTabTable;
import minechem.item.blueprint.MinechemBlueprint;
import minechem.item.chemistjournal.ChemistJournalTab;
import minechem.item.polytool.PolytoolEventHandler;
import minechem.network.MessageHandler;
import minechem.potion.PotionCoatingRecipe;
import minechem.potion.PotionCoatingSubscribe;
import minechem.potion.PotionEnchantmentCoated;
import minechem.potion.PotionInjector;
import minechem.proxy.CommonProxy;
import minechem.tick.TickHandler;
import minechem.utils.Reference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Minechem.ID, name = Minechem.NAME, version = Minechem.VERSION_FULL, useMetadata = false, guiFactory = "minechem.gui.GuiFactory", acceptedMinecraftVersions = "[1.7.10,)", dependencies = "required-after:Forge@[10.13.0.1180,);after:BuildCraft|Energy;after:factorization;after:IC2;after:Railcraft;after:ThermalExpansion")
public class Minechem
{
	// Internal mod name used for reference purposes and resource gathering.
	public static final String ID = "minechem";

	// Network name that is used in the NetworkMod.
	public static final String CHANNEL_NAME = ID;

	// User friendly version of our mods name.
	public static final String NAME = "Minechem";

	// Main version information that will be displayed in mod listing and for other purposes.
	public static final String V_MAJOR = "@MAJOR@";
	public static final String V_MINOR = "@MINOR@";
	public static final String V_REVIS = "@REVIS@";
	public static final String V_BUILD = "@BUILD@";
	public static final String VERSION_FULL = V_MAJOR + "." + V_MINOR + "." + V_REVIS + "." + V_BUILD;

	// Misc variables
	public static final String textureBase = "minechem:";

	// Instancing
	@Instance(value = CHANNEL_NAME)
	public static Minechem INSTANCE;

	// Public extra data about our mod that Forge uses in the mods listing page for more information.
	@Mod.Metadata(Minechem.ID)
	public static ModMetadata metadata;

	@SidedProxy(clientSide = "minechem.proxy.ClientProxy", serverSide = "minechem.proxy.CommonProxy")
	public static CommonProxy PROXY;

	// Creative mode tab that shows up in Minecraft.
	public static CreativeTabs CREATIVE_TAB_ITEMS = new CreativeTabMinechem(Minechem.NAME, 0);
	public static CreativeTabs CREATIVE_TAB_ELEMENTS = new CreativeTabMinechem(Minechem.NAME + ".Elements", 1);

	public static final ResourceLocation ICON_ENERGY = new ResourceLocation(Minechem.ID, Reference.ICON_BASE + "i_power.png");
	public static final ResourceLocation ICON_FULL_ENERGY = new ResourceLocation(Minechem.ID, Reference.ICON_BASE + "i_fullEower.png");
	public static final ResourceLocation ICON_HELP = new ResourceLocation(Minechem.ID, Reference.ICON_BASE + "i_help.png");
	public static final ResourceLocation ICON_JAMMED = new ResourceLocation(Minechem.ID, Reference.ICON_BASE + "i_jammed.png");
	public static final ResourceLocation ICON_NO_BOTTLES = new ResourceLocation(Minechem.ID, Reference.ICON_BASE + "i_noBottles.png");
	public static final ResourceLocation ICON_NO_RECIPE = new ResourceLocation(Minechem.ID, Reference.ICON_BASE + "i_noRecipe.png");
	public static final ResourceLocation ICON_NO_ENERGY = new ResourceLocation(Minechem.ID, Reference.ICON_BASE + "i_unpowered.png");

	// Logging
	public static final Logger LOGGER = LogManager.getLogger(Minechem.ID);

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		// Register instance.
		INSTANCE = this;

		// Load configuration.
		if (Settings.DebugMode)
		{
			LOGGER.info("Loading configuration...");
		}
		Settings.init(event.getSuggestedConfigurationFile());
		FMLCommonHandler.instance().bus().register(new Settings());

		if (Settings.DebugMode)
		{
			LOGGER.info("Registering Packets...");
		}
		MessageHandler.init();

		// Setup Mod Metadata for players to see in mod list with other mods.
		metadata.modId = Minechem.ID;
		metadata.name = Minechem.NAME;
		metadata.description = Minechem.NAME + " is a mod about chemistry, allowing you to research blocks and items, and then break them down into their base compounds and elements.";
		metadata.url = "http://www.minechemmod.com/";
		metadata.logoFile = "assets/" + Minechem.ID + "/logo.png";
		metadata.version = V_MAJOR + "." + V_MINOR + "." + V_REVIS;
		metadata.authorList = Arrays.asList(new String[]
		{
			"pixlepix", "jakimfett", "maxwolf"
		});
		metadata.credits = "View a full list of contributors on our site!";
		metadata.autogenerated = false;

		// Register items and blocks.
		if (Settings.DebugMode)
		{
			LOGGER.info("Registering Items...");
		}
		MinechemItemsRegistration.init();

		if (Settings.DebugMode)
		{
			LOGGER.info("Registering Blocks...");
		}
		MinechemBlocksGeneration.registerBlocks();

		if (Settings.DebugMode)
		{
			LOGGER.info("Registering Blueprints...");
		}
		MinechemBlueprint.registerBlueprints();

		if (Settings.DebugMode)
		{
			LOGGER.info("Registering Recipe Handlers...");
		}
		MinechemRecipes.getInstance().RegisterHandlers();
		MinechemRecipes.getInstance().RegisterRecipes();
		MinechemRecipes.getInstance().registerFluidRecipies();

		if (Settings.DebugMode)
		{
			LOGGER.info("Registering OreDict Compatability...");
		}
		MinechemItemsRegistration.registerToOreDictionary();

		if (Settings.DebugMode)
		{
			LOGGER.info("Registering Minechem Recipes...");
		}
		MinecraftForge.EVENT_BUS.register(MinechemRecipes.getInstance());

		if (Settings.DebugMode)
		{
			LOGGER.info("Registering Chemical Effects...");
		}
		MinecraftForge.EVENT_BUS.register(new PotionCoatingSubscribe());

		if (Settings.DebugMode)
		{
			LOGGER.info("Registering Polytool Event Handler...");
		}
		MinecraftForge.EVENT_BUS.register(new PolytoolEventHandler());
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		if (Settings.DebugMode)
		{
			LOGGER.info("Registering Proxy Hooks...");
		}
		PROXY.registerHooks();

		if (Settings.DebugMode)
		{
			LOGGER.info("Activating Potion Injector...");
		}
		PotionInjector.inject();

		if (Settings.DebugMode)
		{
			LOGGER.info("Matching Pharmacology Effects to Chemicals...");
		}
		CraftingManager.getInstance().getRecipeList().add(new PotionCoatingRecipe());

		if (Settings.DebugMode)
		{
			LOGGER.info("Registering fluids...");
		}
		FluidHelper.registerFluids();

		if (Settings.DebugMode)
		{
			LOGGER.info("Registering Ore Generation...");
		}
		GameRegistry.registerWorldGenerator(new MinechemGeneration(), 0);

		if (Settings.DebugMode)
		{
			LOGGER.info("Registering GUI and Container handlers...");
		}
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

		if (Settings.DebugMode)
		{
			LOGGER.info("Register Tick Events for chemical effects tracking...");
		}
		PROXY.registerTickHandlers();

		if (Settings.DebugMode)
		{
			LOGGER.info("Registering ClientProxy Rendering Hooks...");
		}
		PROXY.registerRenderers();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		// Adds blueprints to dungeon loot.
		if (Settings.DebugMode)
		{
			LOGGER.info("Adding blueprints to dungeon loot...");
		}
		addDungeonLoot();

		if (Settings.DebugMode)
		{
			LOGGER.info("Activating Chemical Effect Layering (Coatings)...");
		}
		PotionEnchantmentCoated.registerCoatings();

		LOGGER.info("Minechem has loaded");
	}

	private void addDungeonLoot()
	{
		ChestGenHooks ChestProvider = ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST);
		ItemStack A = new ItemStack(MinechemItemsRegistration.blueprint, 1, 0);
		ItemStack B = new ItemStack(MinechemItemsRegistration.blueprint, 1, 1);
		ChestProvider.addItem(new WeightedRandomChestContent(A, 10, 80, 1));
		ChestProvider.addItem(new WeightedRandomChestContent(B, 10, 80, 1));
	}

	@SideOnly(Side.CLIENT)
	public void textureHook(IIconRegister icon)
	{
		GuiTabHelp.helpIcon = icon.registerIcon(Reference.HELP_ICON);
		GuiTabTable.helpIcon = icon.registerIcon(Reference.HELP_ICON);
		ChemistJournalTab.helpIcon = icon.registerIcon(Reference.POWER_ICON);
	}

	@SubscribeEvent
	public void onPreRender(RenderGameOverlayEvent.Pre e)
	{
		TickHandler.renderEffects();
	}
}
