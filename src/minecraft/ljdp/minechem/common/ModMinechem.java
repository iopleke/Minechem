package ljdp.minechem.common;

import java.util.logging.Logger;

import ljdp.minechem.client.gui.tabs.TabEnergy;
import ljdp.minechem.client.gui.tabs.TabHelp;
import ljdp.minechem.client.gui.tabs.TabJournel;
import ljdp.minechem.client.gui.tabs.TabStateControl;
import ljdp.minechem.client.gui.tabs.TabStateControlSynthesis;
import ljdp.minechem.client.gui.tabs.TabTable;
import ljdp.minechem.common.blueprint.MinechemBlueprint;
import ljdp.minechem.common.gates.MinechemTriggers;
import ljdp.minechem.common.network.PacketHandler;
import ljdp.minechem.common.recipe.MinechemRecipes;
import ljdp.minechem.common.utils.ConstantValue;
import ljdp.minechem.common.utils.Localization;
import ljdp.minechem.computercraft.ICCMain;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.event.ForgeSubscribe;

import org.modstats.ModstatInfo;
import org.modstats.Modstats;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

// Some help from DrZed

@Mod(modid = "minechem", name = "MineChem", version = "@VERSION@")
@ModstatInfo(prefix="minechem")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = { PacketHandler.MINECHEM_PACKET_CHANNEL }, packetHandler = PacketHandler.class)

public class ModMinechem {
    @Instance("minechem")
    public static ModMinechem instance;
    
    public static Logger logger;

    @SidedProxy(clientSide = "ljdp.minechem.client.ClientProxy", serverSide = "ljdp.minechem.common.CommonProxy")
    public static CommonProxy proxy;
    public static CreativeTabs minechemTab = new CreativeTabMinechem("MineChem");
    private Configuration config;
    private static final String[] LANGUAGES_SUPPORTED = new String[] { "en_GB", "zh_CN", "de_DE" };
    public static String GUITABLEID = "2";
    @PreInit
    public void preInit(FMLPreInitializationEvent event) {
        logger = Logger.getLogger("minechem");
		 
        logger.setParent(FMLLog.getLogger());

        Localization.loadLanguages(CommonProxy.LANG_DIR, LANGUAGES_SUPPORTED);

        loadConfig(event);

        MinechemItems.registerItems();

        MinechemBlocks.registerBlocks();
        MinechemBlueprint.registerBlueprints();
        MinechemRecipes.getInstance().RegisterRecipes();

        MinechemItems.registerToOreDictionary();

        proxy.registerHooks();

        MinecraftForge.EVENT_BUS.register(MinechemRecipes.getInstance());
        MinecraftForge.EVENT_BUS.register(this);
        
        

        logger.info("PREINT PASSED");

    }
    @Init
    public void init(FMLInitializationEvent event) {
        NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
        TickRegistry.registerScheduledTickHandler(new ScheduledTickHandler(), Side.SERVER);
        proxy.registerRenderers();
        logger.info("INIT PASSED");
        LanguageRegistry.instance().addStringLocalization("itemGroup.MineChem", "en_US", "MineChem");
        Modstats.instance().getReporter().registerMod(this);
    }

    @PostInit
    public void postInit(FMLPostInitializationEvent event) {
    initComputerCraftAddon(event);
	DoDungeon();
    logger.info("POSTINIT PASSED");
    }

    private void DoDungeon() {
    ChestGenHooks info = ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST);
    ItemStack A = new ItemStack(MinechemItems.blueprint,1,0);
    // ItemStack B = new ItemStack(MinechemItems.blueprint,1,1);
    ItemStack C = new ItemStack(MinechemItems.blueprint,1,2);
    info.addItem(new WeightedRandomChestContent(A, 1, 8, 1 ));
    // info.addItem(new WeightedRandomChestContent(B, 1, 8, 1 ));
    info.addItem(new WeightedRandomChestContent(C, 1, 8, 1 ));
	}
	private void initComputerCraftAddon(FMLPostInitializationEvent event) {
        Object ccMain = event.buildSoftDependProxy("CCTurtle", "ljdp.minechem.computercraft.CCMain");
        if (ccMain != null) {
            ICCMain iCCMain = (ICCMain) ccMain;
            iCCMain.loadConfig(config);
            iCCMain.init();
            logger.info("Computercraft interface layer loaded");
        }
		}
		

    private void loadConfig(FMLPreInitializationEvent event) {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        MinechemBlocks.loadConfig(config);
        MinechemItems.loadConfig(config);
        config.save();
        this.config = config;
    }
    
    @ForgeSubscribe
    @SideOnly(Side.CLIENT)
    public void textureHook(TextureStitchEvent.Pre event){
            if (event.map == Minecraft.getMinecraft().renderEngine.textureMapItems) {
                TabStateControl.unpoweredIcon = event.map.registerIcon(ConstantValue.UNPOWERED_ICON);
                MinechemTriggers.outputJammed.icon = event.map.registerIcon(ConstantValue.JAMMED_ICON);
                MinechemTriggers.noTestTubes.icon = event.map.registerIcon(ConstantValue.NO_BOTTLES_ICON);
                TabStateControlSynthesis.noRecipeIcon = event.map.registerIcon(ConstantValue.NO_RECIPE_ICON);
                TabEnergy.powerIcon = event.map.registerIcon(ConstantValue.POWER_ICON);
                TabHelp.helpIcon = event.map.registerIcon(ConstantValue.HELP_ICON);
                TabTable.helpIcon = event.map.registerIcon(ConstantValue.HELP_ICON);
                TabJournel.helpIcon = event.map.registerIcon(ConstantValue.POWER_ICON);
                MinechemTriggers.fullEnergy.icon = event.map.registerIcon(ConstantValue.FULL_ENERGY_ICON);
            }

    }
}
