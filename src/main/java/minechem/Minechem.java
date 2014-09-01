package minechem;

import java.util.Arrays;

import cpw.mods.fml.common.*;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import minechem.fluid.FluidHelper;
import minechem.gui.CreativeTabMinechem;
import minechem.gui.GuiHandler;
import minechem.gui.GuiTabEnergy;
import minechem.gui.GuiTabHelp;
import minechem.gui.GuiTabStateControl;
import minechem.gui.GuiTabTable;
import minechem.item.blueprint.MinechemBlueprint;
import minechem.item.chemistjournal.ChemistJournalTab;
import minechem.item.polytool.PolytoolEventHandler;
import minechem.network.PacketDispatcher;
import minechem.network.packet.*;
import minechem.network.server.CommonProxy;
import minechem.potion.PotionCoatingRecipe;
import minechem.potion.PotionCoatingSubscribe;
import minechem.potion.PotionEnchantmentCoated;
import minechem.potion.PotionInjector;
import minechem.tick.ScheduledTickHandler;
import minechem.tick.TickHandler;
import minechem.tileentity.synthesis.SynthesisTabStateControl;
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


import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.init.Items;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Minechem.ID, name = Minechem.NAME, version = Minechem.VERSION_FULL, useMetadata = false, acceptedMinecraftVersions = "[1.7.10,)", dependencies = "required-after:Forge@[10.13.0.1180,);after:BuildCraft|Energy;after:factorization;after:IC2;after:Railcraft;after:ThermalExpansion")
public class Minechem
{
    // Internal mod name used for reference purposes and resource gathering.
    public static final String ID = "minechem";

    // Network name that is used in the NetworkMod.
    public static final String CHANNEL_NAME = ID;

    // User friendly version of our mods name.
    public static final String NAME = "MineChem";

    // Main version information that will be displayed in mod listing and for other purposes.
    public static final String V_MAJOR = "@MAJOR@";
    public static final String V_MINOR = "@MINOR@";
    public static final String V_REVIS = "@REVIS@";
    public static final String V_BUILD = "@BUILD@";
    public static final String VERSION_FULL = V_MAJOR + "." + V_MINOR + V_REVIS + "." + V_BUILD;
    
    // Misc variables
    public static final String textureBase = "minechem:";

    // Instancing
    @Instance(value = CHANNEL_NAME)
    public static Minechem INSTANCE;

    // Provides standard logging from the Forge.
    // public static Logger LOGGER;

    // Public extra data about our mod that Forge uses in the mods listing page for more information.
    @Mod.Metadata(Minechem.ID)
    public static ModMetadata metadata;

    @SidedProxy(clientSide = "minechem.network.client.ClientProxy", serverSide = "minechem.network.server.CommonProxy")
    public static CommonProxy PROXY;
    
    // Register a SimpleNetworkWrapper
    public static PacketDispatcher network;

    // Creative mode tab that shows up in Minecraft.
    public static CreativeTabs CREATIVE_TAB = new CreativeTabMinechem(Minechem.NAME, Items.book);//TODO: set item for creative tab

    // Provides standardized configuration file offered by the Forge.
    private static Configuration CONFIG;

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
        
        // Initialize the network wrapper
        network = new PacketDispatcher(Minechem.ID);
		
        // Load configuration.
        LOGGER.info("Loading configuration...");
        CONFIG = new Configuration(event.getSuggestedConfigurationFile());
        Settings.load(CONFIG);
         
        // Setup Mod Metadata for players to see in mod list with other mods.
        metadata.modId = Minechem.ID;
        metadata.name = Minechem.NAME;
        metadata.description = Minechem.NAME + " is a mod about chemistry, allowing you to research blocks and items, and then break them down into their base compounds and elements.";
        metadata.url = "http://www.minechemmod.com/";
        metadata.logoFile = "assets/" + Minechem.ID + "/logo.png";
        metadata.version = V_MAJOR + "." + V_MINOR + V_REVIS;
        metadata.authorList = Arrays.asList(new String[]
        {
            "pixlepix", "jakimfett", "maxwolf"
        });
        metadata.credits = "View a full list of contributors on our site!";
        metadata.autogenerated = false;

        // Register items and blocks.
        LOGGER.info("Registering Items...");
        MinechemItemsRegistration.init();

        LOGGER.info("Registering Blocks...");
        MinechemBlocksGeneration.registerBlocks();

        LOGGER.info("Registering Blueprints...");
        MinechemBlueprint.registerBlueprints();

        LOGGER.info("Registering Recipe Handlers...");
        MinechemRecipes.getInstance().RegisterHandlers();
        MinechemRecipes.getInstance().RegisterRecipes();
        MinechemRecipes.getInstance().registerFluidRecipies();

        LOGGER.info("Registering OreDict Compatability...");
        MinechemItemsRegistration.registerToOreDictionary();

        LOGGER.info("Registering Minechem Recipes...");
        MinecraftForge.EVENT_BUS.register(MinechemRecipes.getInstance());

        LOGGER.info("Registering Chemical Effects...");
        MinecraftForge.EVENT_BUS.register(new PotionCoatingSubscribe());

        LOGGER.info("Registering Polytool Event Handler...");
        MinecraftForge.EVENT_BUS.register(new PolytoolEventHandler());

        LOGGER.info("PREINT PASSED");
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        LOGGER.info("Registering Proxy Hooks...");
        PROXY.registerHooks();

        LOGGER.info("Activating Potion Injector...");
        PotionInjector.inject();

        LOGGER.info("Matching Pharmacology Effects to Chemicals...");
        CraftingManager.getInstance().getRecipeList().add(new PotionCoatingRecipe());

        LOGGER.info("Registering fluids...");
        FluidHelper.registerFluids();

	    LOGGER.info("Registering Packets...");
	    network.registerPacket(0, Side.CLIENT, SynthesisPacketUpdate.class);
	    network.registerPacket(1, Side.SERVER, ChemistJournalPacketActiveItem.class);
	    network.registerPacket(2, Side.CLIENT, GhostBlockPacket.class);
	    network.registerPacket(3, Side.CLIENT, DecomposerPacketUpdate.class);
	    network.registerPacket(4, Side.CLIENT, PolytoolTypePacket.class);

        LOGGER.info("Registering Ore Generation...");
        GameRegistry.registerWorldGenerator(new MinechemGeneration(), 0);

        LOGGER.info("Registering GUI and Container handlers...");
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

        LOGGER.info("Register Tick Events for chemical effects tracking...");
	    FMLCommonHandler.instance().bus().register(new ScheduledTickHandler());
	    FMLCommonHandler.instance().bus().register(new TickHandler());

        LOGGER.info("Registering ClientProxy Rendering Hooks...");
        PROXY.registerRenderers();

        if (!Loader.isModLoaded("UniversalElectricity"))
        {
            LOGGER.warn("Universal Electricity Core NOT installed. The energy system will not function as intended.");
        }

        LOGGER.info("INIT PASSED");
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        // Adds blueprints to dungeon loot.
		LOGGER.info("Adding blueprints to dungeon loot...");
        addDungeonLoot();

        LOGGER.info("Activating Chemical Effect Layering (Coatings)...");
        PotionEnchantmentCoated.registerCoatings();

        LOGGER.info("POSTINIT PASSED");
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
        GuiTabStateControl.unpoweredIcon = icon.registerIcon(Reference.UNPOWERED_ICON);
        SynthesisTabStateControl.noRecipeIcon = icon.registerIcon(Reference.NO_RECIPE_ICON);
        GuiTabEnergy.powerIcon = icon.registerIcon(Reference.POWER_ICON);
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
