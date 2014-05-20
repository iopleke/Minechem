package minechem;

import java.util.Arrays;
import java.util.logging.Logger;

import minechem.fluid.FluidHelper;
import minechem.gui.CreativeTabMinechem;
import minechem.gui.GuiHandler;
import minechem.gui.GuiTabEnergy;
import minechem.gui.GuiTabHelp;
import minechem.gui.GuiTabStateControl;
import minechem.item.blueprint.MinechemBlueprint;
import minechem.item.chemistjournal.TabJournal;
import minechem.item.polytool.PolytoolEventHandler;
import minechem.network.MinechemPacketHandler;
import minechem.network.server.CommonProxy;
import minechem.oredictionary.MinechemRecipes;
import minechem.potion.CoatingRecipe;
import minechem.potion.CoatingSubscribe;
import minechem.potion.EnchantmentCoated;
import minechem.potion.PotionInjector;
import minechem.tick.ScheduledTickHandler;
import minechem.tick.TickHandler;
import minechem.tileentity.synthesis.TabStateControlSynthesis;
import minechem.utils.Reference;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;

import org.modstats.ModstatInfo;
import org.modstats.Modstats;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = ModMinechem.ID, name = ModMinechem.NAME, version = ModMinechem.VERSION, useMetadata = false, acceptedMinecraftVersions = "[1.6.4,)", dependencies = "required-after:Forge@[9.11.1.953,);after:BuildCraft|Energy;after:factorization;after:IC2;after:Railcraft;after:ThermalExpansion")
@ModstatInfo(prefix = ModMinechem.ID)
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels =
{ ModMinechem.CHANNEL_NAME }, packetHandler = MinechemPacketHandler.class)
public class ModMinechem
{
    /** Internal mod name used for reference purposes and resource gathering. **/
    public static final String ID = "minechem";

    /** Network name that is used in the NetworkMod. **/
    public static final String CHANNEL_NAME = ID;

    /** User friendly version of our mods name. **/
    public static final String NAME = "MineChem";
    
    /** Main version information that will be displayed in mod listing and for other purposes. **/
    public static final String VERSION = "5.0.4";

    /** Provides logging **/
    @Instance(value = CHANNEL_NAME)
    public static ModMinechem INSTANCE;

    /** Provides standard logging from the Forge. **/
    public static Logger LOGGER;
    
    /** Public extra data about our mod that Forge uses in the mods listing page for more information. **/
    @Mod.Metadata(ModMinechem.ID)
    public static ModMetadata metadata;

    @SidedProxy(clientSide = "minechem.client.ClientProxy", serverSide = "minechem.common.CommonProxy")
    public static CommonProxy PROXY;

    /** Creative mode tab that shows up in Minecraft. **/
    public static CreativeTabs CREATIVE_TAB = new CreativeTabMinechem(ModMinechem.NAME);

    /** Provides standardized configuration file offered by the Forge. **/
    private static Configuration CONFIG;

    public static final ResourceLocation ICON_ENERGY = new ResourceLocation(ModMinechem.ID, Reference.ICON_BASE + "i_power.png");
    public static final ResourceLocation ICON_FULL_ENERGY = new ResourceLocation(ModMinechem.ID, Reference.ICON_BASE + "i_fullEower.png");
    public static final ResourceLocation ICON_HELP = new ResourceLocation(ModMinechem.ID, Reference.ICON_BASE + "i_help.png");
    public static final ResourceLocation ICON_JAMMED = new ResourceLocation(ModMinechem.ID, Reference.ICON_BASE + "i_jammed.png");
    public static final ResourceLocation ICON_NO_BOTTLES = new ResourceLocation(ModMinechem.ID, Reference.ICON_BASE + "i_noBottles.png");
    public static final ResourceLocation ICON_NO_RECIPE = new ResourceLocation(ModMinechem.ID, Reference.ICON_BASE + "i_noRecipe.png");
    public static final ResourceLocation ICON_NO_ENERGY = new ResourceLocation(ModMinechem.ID, Reference.ICON_BASE + "i_unpowered.png");

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        // Register instance.
        INSTANCE = this;

        // Setup logging.
        LOGGER = event.getModLog();
        LOGGER.setParent(FMLLog.getLogger());

        // Load configuration.
        LOGGER.info("Loading configuration...");
        CONFIG = new Configuration(event.getSuggestedConfigurationFile());
        Settings.load(CONFIG);
        
        // Setup Mod Metadata for players to see in mod list with other mods.
        metadata.modId = ModMinechem.ID;
        metadata.name = ModMinechem.NAME;
        metadata.description = ModMinechem.NAME + " is a mod about chemistry, allowing you to research blocks and items, and then break them down into their base compounds and elements.";
        metadata.url = "http://www.minechemmod.com/";
        metadata.logoFile = "assets/" + ModMinechem.ID + "/logo.png";
        metadata.version = ModMinechem.VERSION;
        metadata.authorList = Arrays.asList(new String[]
        { "pixlepix", "jakimfett", "maxwolf" });
        metadata.credits = "View a full list of contributors on our site!";
        metadata.autogenerated = false;

        // Register items and blocks.
        LOGGER.info("Registering Items...");
        MinechemItemGeneration.registerItems();

        LOGGER.info("Registering Blocks...");
        MinechemBlockGeneration.registerBlocks();

        LOGGER.info("Registering Blueprints...");
        MinechemBlueprint.registerBlueprints();

        LOGGER.info("Registering Recipe Handlers...");
        MinechemRecipes.getInstance().RegisterHandlers();
        MinechemRecipes.getInstance().RegisterRecipes();
        MinechemRecipes.getInstance().registerFluidRecipies();

        LOGGER.info("Registering OreDict Compatability...");
        MinechemItemGeneration.registerToOreDictionary();

        LOGGER.info("Registering Minechem Recipes...");
        MinecraftForge.EVENT_BUS.register(MinechemRecipes.getInstance());

        LOGGER.info("Registering Chemical Effects...");
        MinecraftForge.EVENT_BUS.register(new CoatingSubscribe());

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
        CraftingManager.getInstance().getRecipeList().add(new CoatingRecipe());

        LOGGER.info("Registering fluids...");
        FluidHelper.registerFluids();

        LOGGER.info("Registering Ore Generation...");
        GameRegistry.registerWorldGenerator(new MinechemOreGeneration());

        LOGGER.info("Registering GUI and Container handlers...");
        NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());

        LOGGER.info("Register Tick Handler for chemical effects tracking...");
        TickRegistry.registerScheduledTickHandler(new ScheduledTickHandler(), Side.SERVER);

        LOGGER.info("Registering ClientProxy Rendering Hooks...");
        PROXY.registerRenderers();

        LOGGER.info("Registering ModStats Usage Tracking...");
        Modstats.instance().getReporter().registerMod(this);

        LOGGER.info("INIT PASSED");
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        // Adds blueprints to dungeon loot.
        addonDungeonLoot();

        LOGGER.info("Activating Chemical Effect Layering (Coatings)...");
        EnchantmentCoated.registerCoatings();

        LOGGER.info("POSTINIT PASSED");
    }

    private void addonDungeonLoot()
    {
        LOGGER.info("Adding blueprints to dungeon loot...");
        ChestGenHooks ChestProvider = ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST);
        ItemStack A = new ItemStack(MinechemItemGeneration.blueprint, 1, 0);
        ItemStack B = new ItemStack(MinechemItemGeneration.blueprint, 1, 1);
        ChestProvider.addItem(new WeightedRandomChestContent(A, 10, 80, 1));
        ChestProvider.addItem(new WeightedRandomChestContent(B, 10, 80, 1));
    }

    @SideOnly(Side.CLIENT)
    public void textureHook(IconRegister icon)
    {
        GuiTabStateControl.unpoweredIcon = icon.registerIcon(Reference.UNPOWERED_ICON);
        TabStateControlSynthesis.noRecipeIcon = icon.registerIcon(Reference.NO_RECIPE_ICON);
        GuiTabEnergy.powerIcon = icon.registerIcon(Reference.POWER_ICON);
        GuiTabHelp.helpIcon = icon.registerIcon(Reference.HELP_ICON);
        TabJournal.helpIcon = icon.registerIcon(Reference.POWER_ICON);
    }

    @ForgeSubscribe
    public void onPreRender(RenderGameOverlayEvent.Pre e)
    {
        TickHandler.renderEffects();
    }
}
