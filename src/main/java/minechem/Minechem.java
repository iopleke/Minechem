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
import cpw.mods.fml.common.network.NetworkRegistry;
import minechem.handler.ElementHandler;
import minechem.handler.GuiHandler;
import minechem.handler.MessageHandler;
import minechem.handler.MoleculeHandler;
import minechem.helper.LogHelper;
import minechem.proxy.CommonProxy;
import minechem.registry.*;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = Compendium.Naming.id, name = Compendium.Naming.name, version = Compendium.Version.full, useMetadata = false, guiFactory = "minechem.proxy.client.gui.GuiFactory", acceptedMinecraftVersions = "[1.7.10,)", dependencies = "required-after:Forge@[10.13.0.1180,)")
public class Minechem
{
    public static boolean isCoFHAAPILoaded;

    // Instancing
    @Instance(value = Compendium.Naming.id)
    public static Minechem INSTANCE;

    // Public extra data about our mod that Forge uses in the mods listing page for more information.
    @Mod.Metadata(Compendium.Naming.id)
    public static ModMetadata metadata;

    @SidedProxy(clientSide = "minechem.proxy.client.ClientProxy", serverSide = "minechem.proxy.CommonProxy")
    public static CommonProxy proxy;

    public static ElementHandler elementHandler;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        // Register instance.
        INSTANCE = this;

        try
        {
            // Shouldn't we be using Loader.isModLoaded here?
            Class.forName("cofh.api.energy.IEnergyHandler");
            isCoFHAAPILoaded = true;
        } catch (Exception e)
        {
            isCoFHAAPILoaded = false;
        }

        // Load configuration.
        LogHelper.debug("Loading configuration...");
        Config.init();
        FMLCommonHandler.instance().bus().register(new Config());

        LogHelper.debug("Registering Packets...");
        MessageHandler.init();

        LogHelper.debug("Setting up ModMetaData");
        metadata = Compendium.MetaData.init(metadata);

        // Register items and blocks.
        LogHelper.debug("Registering Items...");
        ItemRegistry.init();

        LogHelper.debug("Registering Blocks...");
        BlockRegistry.init();

        LogHelper.debug("Registering Augments...");
        AugmentRegistry.init();

        LogHelper.debug("Registering CreativeTabs...");
        CreativeTabRegistry.init();

        // Register the proxy with the eventhandler
        LogHelper.debug("Registering proxies...");
        MinecraftForge.EVENT_BUS.register(proxy);

    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        LogHelper.debug("Registering GUI and Container handlers...");
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

        LogHelper.debug("Registering ClientProxy Rendering Hooks...");
        proxy.registerRenderers();

        LogHelper.debug("Registering Elements...");
        ElementHandler.init();

        LogHelper.debug("Registering Molecules...");
        MoleculeHandler.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        LogHelper.debug("Registering Recipes...");
        RecipeRegistry.getInstance().init();

        LogHelper.info("Minechem has loaded");
    }
}
