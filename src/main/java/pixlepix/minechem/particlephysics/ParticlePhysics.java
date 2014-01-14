package pixlepix.particlephysics.common;

import net.minecraft.item.Item;
import pixlepix.particlephysics.common.gui.GuiHandler;
import pixlepix.particlephysics.common.helper.BetterLoader;
import pixlepix.particlephysics.common.helper.CommonProxy;
import pixlepix.particlephysics.common.helper.PacketHandler;
import pixlepix.particlephysics.common.helper.ParticlePhysicsTab;
import pixlepix.particlephysics.common.helper.ParticleRegistry;
import pixlepix.particlephysics.common.item.PotentialReader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "particlephysics", name = "Particle Physics", version = "0.1.1")
@NetworkMod(clientSideRequired = true, serverSideRequired = false,  channels={"Particle"}, packetHandler = PacketHandler.class)
public class ParticlePhysics {

	
	public static BetterLoader loader;
	
	public static ParticlePhysicsTab creativeTab = new ParticlePhysicsTab();
	

    public final static Item potentialReader=new PotentialReader(24567);
	public void loadBlocks(){
	

	
	}
	
	// The instance of your mod that Forge uses.
	@Instance("particlephysics")
	public static ParticlePhysics instance;
	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide = "pixlepix.particlephysics.common.helper.ClientProxy", serverSide = "pixlepix.particlephysics.common.helper.CommonProxy")
	public static CommonProxy proxy;



	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ParticleRegistry.populateParticleList();
		loader=new BetterLoader();
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
		LanguageRegistry.addName(potentialReader, "Potential Reader");
		ParticleRegistry.registerEntities();
		
		new GuiHandler();
	}

	
	
}
