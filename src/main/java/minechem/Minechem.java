package minechem;

import com.chemspider.www.MassSpecAPIStub;
import com.chemspider.www.SearchStub;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import minechem.handler.*;
import minechem.helper.LogHelper;
import minechem.helper.WikipediaHelper;
import minechem.proxy.CommonProxy;
import minechem.registry.AugmentRegistry;
import minechem.registry.BlockRegistry;
import minechem.registry.CreativeTabRegistry;
import minechem.registry.ItemRegistry;
import minechem.registry.JournalRegistry;
import minechem.registry.RecipeRegistry;

import java.util.HashMap;
import java.util.Map;

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

        // Register Elements and Molecules before constructing items
        LogHelper.debug("Registering Elements...");
        ElementHandler.init();

//        String token = "adf48903-3723-48f3-9a0b-fb989594738e";
//        int[] SimpleSearchResults =  get_Search_SimpleSearch_Results("salt", token);
//        int[] inputCSIDs = new int[2];
//        inputCSIDs[0] = 236;
//        inputCSIDs[1] = 238;
//        Map GetExtendedCompoundInfoArrayResults = get_MassSpecAPI_GetExtendedCompoundInfoArray_Results(SimpleSearchResults, token);
        //WikipediaHelper.getObject("Aluminium trichloride");
        CompoundHandler.init();
        LogHelper.debug("Registering Molecules...");
        MoleculeHandler.init();

        // Register items and blocks.
        LogHelper.debug("Registering Items...");
        ItemRegistry.init();

        LogHelper.debug("Registering Blocks...");
        BlockRegistry.init();

        LogHelper.debug("Registering Augments...");
        AugmentRegistry.init();

        LogHelper.debug("Registering CreativeTabs...");
        CreativeTabRegistry.init();

        // Register Event Handlers
        LogHelper.debug("Registering Event Handlers...");
        proxy.registerEventHandlers();

        JournalRegistry.init();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        LogHelper.debug("Registering Recipes...");
        RecipeRegistry.getInstance().init();

        LogHelper.debug("Registering GUI and Container handlers...");
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

        LogHelper.debug("Registering ClientProxy Rendering Hooks...");
        proxy.registerRenderers();

        LogHelper.debug("Registering Journal Pages...");
        JournalHandler.init(proxy.getCurrentLanguage());

        LogHelper.debug("Registering Achievements...");
        AchievementHandler.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.registerResourcesListener();

        LogHelper.info("Minechem has loaded");
    }

    @EventHandler
    public void onServerStarted(FMLServerStartedEvent event)
    {
        JournalHandler.readPlayerResearch();
    }

    @EventHandler
    public void onServerStopping(FMLServerStoppingEvent event)
    {
        JournalHandler.saveResearch();
    }

    public static int[] get_Search_SimpleSearch_Results(String query, String token) {
        int[] Output = null;
        try {
            final SearchStub thisSearchStub = new SearchStub();
            com.chemspider.www.SearchStub.SimpleSearch SimpleSearchInput = new com.chemspider.www.SearchStub.SimpleSearch();
            SimpleSearchInput.setQuery(query);
            SimpleSearchInput.setToken(token);
            final SearchStub.SimpleSearchResponse thisSimpleSearchResponse = thisSearchStub.simpleSearch(SimpleSearchInput);
            Output = thisSimpleSearchResponse.getSimpleSearchResult().get_int();
        } catch (Exception e) {
        }
        return Output;
    }

    public static Map get_MassSpecAPI_GetExtendedCompoundInfoArray_Results(int[] CSIDs, String token) {
    Map Output = new HashMap();
    try {
        final MassSpecAPIStub thisMassSpecAPIStub = new MassSpecAPIStub();
        MassSpecAPIStub.ArrayOfInt inputCSIDsArrayofInt = new MassSpecAPIStub.ArrayOfInt();
        inputCSIDsArrayofInt.set_int(CSIDs);
        com.chemspider.www.MassSpecAPIStub.GetExtendedCompoundInfoArray getGetExtendedCompoundInfoArrayInput = new com.chemspider.www.MassSpecAPIStub.GetExtendedCompoundInfoArray();
        getGetExtendedCompoundInfoArrayInput.setCSIDs(inputCSIDsArrayofInt);
        getGetExtendedCompoundInfoArrayInput.setToken(token);
        final MassSpecAPIStub.GetExtendedCompoundInfoArrayResponse thisGetExtendedCompoundInfoArrayResponse = thisMassSpecAPIStub.getExtendedCompoundInfoArray(getGetExtendedCompoundInfoArrayInput);
        MassSpecAPIStub.ExtendedCompoundInfo[] thisExtendedCompoundInfo = thisGetExtendedCompoundInfoArrayResponse.getGetExtendedCompoundInfoArrayResult().getExtendedCompoundInfo();
        for (int i=0; i < thisExtendedCompoundInfo.length; i++)
        {
            Map thisCompoundExtendedCompoundInfoArrayOutput = new HashMap();
            thisCompoundExtendedCompoundInfoArrayOutput.put("CSID", Integer.toString(thisExtendedCompoundInfo[i].getCSID()));
            thisCompoundExtendedCompoundInfoArrayOutput.put("MF", thisExtendedCompoundInfo[i].getMF());
            thisCompoundExtendedCompoundInfoArrayOutput.put("SMILES", thisExtendedCompoundInfo[i].getSMILES());
            thisCompoundExtendedCompoundInfoArrayOutput.put("InChI", thisExtendedCompoundInfo[i].getInChI());
            thisCompoundExtendedCompoundInfoArrayOutput.put("InChIKey", thisExtendedCompoundInfo[i].getInChIKey());
            thisCompoundExtendedCompoundInfoArrayOutput.put("AverageMass", Double.toString(thisExtendedCompoundInfo[i].getAverageMass()));
            thisCompoundExtendedCompoundInfoArrayOutput.put("MolecularWeight", Double.toString(thisExtendedCompoundInfo[i].getMolecularWeight()));
            thisCompoundExtendedCompoundInfoArrayOutput.put("MonoisotopicMass", Double.toString(thisExtendedCompoundInfo[i].getMonoisotopicMass()));
            thisCompoundExtendedCompoundInfoArrayOutput.put("NominalMass", Double.toString(thisExtendedCompoundInfo[i].getNominalMass()));
            thisCompoundExtendedCompoundInfoArrayOutput.put("ALogP", Double.toString(thisExtendedCompoundInfo[i].getALogP()));
            thisCompoundExtendedCompoundInfoArrayOutput.put("XLogP", Double.toString(thisExtendedCompoundInfo[i].getXLogP()));
            thisCompoundExtendedCompoundInfoArrayOutput.put("CommonName", thisExtendedCompoundInfo[i].getCommonName());
            Output.put(thisExtendedCompoundInfo[i].getCSID(), thisCompoundExtendedCompoundInfoArrayOutput);
        }
        } catch (Exception e) {
        }
        return Output;
    }



}
