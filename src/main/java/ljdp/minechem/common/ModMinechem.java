package ljdp.minechem.common;

import java.util.logging.Logger;

import ljdp.minechem.api.core.Chemical;
import ljdp.minechem.api.core.Element;
import ljdp.minechem.api.core.EnumElement;
import ljdp.minechem.api.recipe.DecomposerRecipe;
import ljdp.minechem.client.TickHandler;
import ljdp.minechem.client.gui.tabs.TabEnergy;
import ljdp.minechem.client.gui.tabs.TabHelp;
import ljdp.minechem.client.gui.tabs.TabJournel;
import ljdp.minechem.client.gui.tabs.TabStateControl;
import ljdp.minechem.client.gui.tabs.TabStateControlSynthesis;
import ljdp.minechem.client.gui.tabs.TabTable;
import ljdp.minechem.common.blueprint.MinechemBlueprint;
import ljdp.minechem.common.coating.CoatingRecipe;
import ljdp.minechem.common.coating.CoatingSubscribe;
import ljdp.minechem.common.coating.EnchantmentCoated;
import ljdp.minechem.common.gates.MinechemTriggers;
import ljdp.minechem.common.network.PacketHandler;
import ljdp.minechem.common.recipe.ConfigurableRecipies;
import ljdp.minechem.common.recipe.MinechemRecipes;
import ljdp.minechem.common.utils.ConstantValue;
import ljdp.minechem.computercraft.ICCMain;
import ljdp.minechem.fluid.FluidHelper;
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

import universalelectricity.prefab.TranslationHelper;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
// import ljdp.minechem.common.plugins.BOPModule;
// import cpw.mods.fml.common.Loader;

@Mod(modid = "minechem", name = "MineChem", version = "@VERSION@")
@ModstatInfo(prefix="minechem")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = { PacketHandler.MINECHEM_PACKET_CHANNEL }, packetHandler = PacketHandler.class)

public class ModMinechem {
    @Instance("minechem")
    public static ModMinechem instance;
    
    public static Logger logger;
    
	public static final ResourceLocation ICON_ENERGY = new ResourceLocation(ConstantValue.MOD_ID,ConstantValue.ICON_BASE+"i_power.png");

	public static final ResourceLocation ICON_FULL_ENERGY = new ResourceLocation(ConstantValue.MOD_ID,ConstantValue.ICON_BASE+"i_fullEower.png");

	public static final ResourceLocation ICON_HELP = new ResourceLocation(ConstantValue.MOD_ID,ConstantValue.ICON_BASE+"i_help.png");

	public static final ResourceLocation ICON_JAMMED = new ResourceLocation(ConstantValue.MOD_ID,ConstantValue.ICON_BASE+"i_jammed.png");

	public static final ResourceLocation ICON_NO_BOTTLES = new ResourceLocation(ConstantValue.MOD_ID,ConstantValue.ICON_BASE+"i_noBottles.png");

	public static final ResourceLocation ICON_NO_RECIPE = new ResourceLocation(ConstantValue.MOD_ID,ConstantValue.ICON_BASE+"i_noRecipe.png");

	public static final ResourceLocation ICON_NO_ENERGY = new ResourceLocation(ConstantValue.MOD_ID,ConstantValue.ICON_BASE+"i_unpowered.png");
    
    @SidedProxy(clientSide = "ljdp.minechem.client.ClientProxy", serverSide = "ljdp.minechem.common.CommonProxy")
    public static CommonProxy proxy;
    public static CreativeTabs minechemTab = new CreativeTabMinechem("MineChem");
    private Configuration config;
    private static final String[] LANGUAGES_SUPPORTED = new String[] { "en_US", "zh_CN", "de_DE" };
    public static String GUITABLEID = "2";
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = Logger.getLogger("minechem");
        logger.setParent(FMLLog.getLogger());

        System.out.println(TranslationHelper.loadLanguages(ConstantValue.LANG_DIR, LANGUAGES_SUPPORTED));

        loadConfig(event);

        MinechemItems.registerItems();

        MinechemBlocks.registerBlocks();
        MinechemBlueprint.registerBlueprints();
        MinechemRecipes.getInstance().RegisterHandlers();
        MinechemRecipes.getInstance().RegisterRecipes();

        MinechemItems.registerToOreDictionary();

        proxy.registerHooks();

        MinecraftForge.EVENT_BUS.register(MinechemRecipes.getInstance());
        MinecraftForge.EVENT_BUS.register(this);
		
        PotionInjector.inject();
        
		logger.info("PREINT PASSED");

    }
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	CraftingManager.getInstance().getRecipeList().add(new CoatingRecipe());
		FluidHelper.registerFluids();
		GameRegistry.registerWorldGenerator(new MinechemGeneration());
        NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
        TickRegistry.registerScheduledTickHandler(new ScheduledTickHandler(), Side.SERVER);
        proxy.registerRenderers();
        logger.info("INIT PASSED");
        LanguageRegistry.instance().addStringLocalization("itemGroup.MineChem", "en_US", "MineChem");
        ConfigurableRecipies.loadConfigurableRecipies(this.config);
		Modstats.instance().getReporter().registerMod(this);
    }
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    initComputerCraftAddon(event);
	// initBOP();
	DoDungeon();
	//Flexible enchantment location
	EnchantmentCoated.registerCoatings();
	MinecraftForge.EVENT_BUS.register(new CoatingSubscribe());
	//Moved to enum ore
	//DecomposerRecipe.add(new DecomposerRecipe(new ItemStack(MinechemBlocks.uranium), new Chemical[] { new Element(EnumElement.U, 32) }));

    logger.info("POSTINIT PASSED");
    }

    private void DoDungeon() {
    ChestGenHooks ChestProvider = ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST);
    ItemStack A = new ItemStack(MinechemItems.blueprint,1,0);
    ItemStack B = new ItemStack(MinechemItems.blueprint,1,1);
    ChestProvider.addItem(new WeightedRandomChestContent(A, 10, 80, 1 ));
    ChestProvider.addItem(new WeightedRandomChestContent(B, 10, 80, 1 ));
    logger.info("Injected blueprints into loot chest generator");
	}
	private void initComputerCraftAddon(FMLPostInitializationEvent event) {
        Object ccMain = event.buildSoftDependProxy("CCTurtle", "ljdp.minechem.computercraft.CCMain");
        if (ccMain != null) {
            ICCMain iCCMain = (ICCMain) ccMain;
            iCCMain.loadConfig(config);
            iCCMain.init();
            logger.info("ComputerCraft interface loaded");
        }
		}
		
  /*
  private void initBOP (){
    if (Loader.isModLoaded("BiomesOPlenty"))
    { 
    logger.info("BOP support loaded");
	System.out.println("MineChem - If for any reason MineChem & Minecraft crashes. \n Try updating BOP \n");
	BOPModule.DoBopExport();
    }
  }
*/
	
    private void loadConfig(FMLPreInitializationEvent event) {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        MinechemBlocks.loadConfig(config);
        MinechemItems.loadConfig(config);
        config.save();
        this.config = config;
    }
    //Changed this from TextureStitchEvent.pre
    @SideOnly(Side.CLIENT)
    public void textureHook(IconRegister icon){
                TabStateControl.unpoweredIcon = icon.registerIcon(ConstantValue.UNPOWERED_ICON);
                MinechemTriggers.outputJammed.icon = icon.registerIcon(ConstantValue.JAMMED_ICON);
                MinechemTriggers.noTestTubes.icon = icon.registerIcon(ConstantValue.NO_BOTTLES_ICON);
                TabStateControlSynthesis.noRecipeIcon = icon.registerIcon(ConstantValue.NO_RECIPE_ICON);
                TabEnergy.powerIcon = icon.registerIcon(ConstantValue.POWER_ICON);
                TabHelp.helpIcon = icon.registerIcon(ConstantValue.HELP_ICON);
                TabTable.helpIcon = icon.registerIcon(ConstantValue.HELP_ICON);
                TabJournel.helpIcon = icon.registerIcon(ConstantValue.POWER_ICON);
                MinechemTriggers.fullEnergy.icon = icon.registerIcon(ConstantValue.FULL_ENERGY_ICON);
           }

    @ForgeSubscribe
    public void onPreRender(RenderGameOverlayEvent.Pre e) {
    	TickHandler.renderEffects();
    }
}
