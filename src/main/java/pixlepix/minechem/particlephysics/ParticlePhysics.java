package pixlepix.minechem.particlephysics;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import pixlepix.minechem.particlephysics.gui.GuiHandler;
import pixlepix.minechem.particlephysics.helper.*;

@Mod(modid = "minechem-particlephysics", name = "Minechem-Particle Physics", version = "5.0.3")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = {"Particle"}, packetHandler = PacketHandler.class)
public class ParticlePhysics {


    public static BetterLoader loader;

    public static ParticlePhysicsTab creativeTab = new ParticlePhysicsTab();

	//public final static Item potentialReader = new PotentialReader(24567);

	public void loadBlocks() {

    }

    // The instance of your mod that Forge uses.
    @Instance("minechem-particlephysics")
    public static ParticlePhysics instance;
    // Says where the client and server 'proxy' code is loaded.
    @SidedProxy(clientSide = "pixlepix.minechem.particlephysics.helper.ClientProxy", serverSide = "pixlepix.minechem.particlephysics.helper.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ParticleRegistry.populateParticleList();
        loader = new BetterLoader();
        loader.loadBlocks();
        loadBlocks();
    }

    @EventHandler
    public void load(FMLInitializationEvent event) {
        proxy.registerRenderers();

        loader.mainload();
        proxy.init();

        NetworkRegistry networkRegistry = NetworkRegistry.instance();

        LanguageRegistry.instance().addStringLocalization("itemGroup.tabParticlePhysics", "ParticlePhysics");
	    //LanguageRegistry.addName(potentialReader, "Potential Reader");
	    ParticleRegistry.registerEntities();

        new GuiHandler();
    }
}
