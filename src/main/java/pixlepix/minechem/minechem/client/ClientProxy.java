package pixlepix.minechem.minechem.client;

import pixlepix.minechem.minechem.client.render.item.ItemBlueprintProjectorRenderer;
import pixlepix.minechem.minechem.client.render.item.ItemChemicalStorageRenderer;
import pixlepix.minechem.minechem.client.render.item.ItemDecomposerRenderer;
import pixlepix.minechem.minechem.client.render.item.ItemElementRenderer;
import pixlepix.minechem.minechem.client.render.item.ItemMicroscopeRenderer;
import pixlepix.minechem.minechem.client.render.item.ItemMoleculeRenderer;
import pixlepix.minechem.minechem.client.render.item.ItemSynthesisRenderer;
import pixlepix.minechem.minechem.client.render.tileentity.TileEntityBlueprintProjectorRenderer;
import pixlepix.minechem.minechem.client.render.tileentity.TileEntityChemicalStorageRenderer;
import pixlepix.minechem.minechem.client.render.tileentity.TileEntityDecomposerRenderer;
import pixlepix.minechem.minechem.client.render.tileentity.TileEntityMicroscopeRenderer;
import pixlepix.minechem.minechem.client.render.tileentity.TileEntitySynthesisRenderer;
import pixlepix.minechem.minechem.client.sound.MinechemSoundEvent;
import pixlepix.minechem.minechem.common.CommonProxy;
import pixlepix.minechem.minechem.common.MinechemBlocks;
import pixlepix.minechem.minechem.common.MinechemItems;
import pixlepix.minechem.minechem.common.tileentity.TileEntityBlueprintProjector;
import pixlepix.minechem.minechem.common.tileentity.TileEntityChemicalStorage;
import pixlepix.minechem.minechem.common.tileentity.TileEntityDecomposer;
import pixlepix.minechem.minechem.common.tileentity.TileEntityMicroscope;
import pixlepix.minechem.minechem.common.tileentity.TileEntitySynthesis;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenderers() {
    	//As far as I can tell, this isn't needed in 1.6
        /*MinecraftForgeClient.preloadTexture(DECOMPOSER_GUI);
        MinecraftForgeClient.preloadTexture(MICROSCOPE_GUI);
        MinecraftForgeClient.preloadTexture(SYNTHESIS_GUI);
        MinecraftForgeClient.preloadTexture(MICROSCOPE_MODEL);
        MinecraftForgeClient.preloadTexture(DECOMPOSER_MODEL_ON);
        MinecraftForgeClient.preloadTexture(DECOMPOSER_MODEL_OFF);
        MinecraftForgeClient.preloadTexture(SYNTHESIS_MODEL);
        MinecraftForgeClient.preloadTexture(PRINTER_MODEL);
        MinecraftForgeClient.preloadTexture(PROJECTOR_MODEL_OFF);
        MinecraftForgeClient.preloadTexture(PROJECTOR_MODEL_ON);
        MinecraftForgeClient.preloadTexture(FUSION_GUI);
        MinecraftForgeClient.preloadTexture(PROJECTOR_GUI);
        MinecraftForgeClient.preloadTexture(JOURNAL_GUI);
        MinecraftForgeClient.preloadTexture(HAZMAT_TEX);
        MinecraftForgeClient.preloadTexture(CHEMICAL_STORAGE_MODEL);
        */
        CUSTOM_RENDER_ID = RenderingRegistry.getNextAvailableRenderId();
        //Possible future feature
        //MinecraftForgeClient.registerItemRenderer(MinechemItems.polytool.itemID, new PolytoolInventoryRender());
        MinecraftForgeClient.registerItemRenderer(MinechemItems.element.itemID, new ItemElementRenderer());
        MinecraftForgeClient.registerItemRenderer(MinechemItems.molecule.itemID, new ItemMoleculeRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.itemsList[MinechemBlocks.microscope.blockID].itemID, new ItemMicroscopeRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.itemsList[MinechemBlocks.decomposer.blockID].itemID, new ItemDecomposerRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.itemsList[MinechemBlocks.synthesis.blockID].itemID, new ItemSynthesisRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.itemsList[MinechemBlocks.blueprintProjector.blockID].itemID, new ItemBlueprintProjectorRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.itemsList[MinechemBlocks.chemicalStorage.blockID].itemID, new ItemChemicalStorageRenderer());
        //MinecraftForgeClient.registerItemRenderer(Item.itemsList[MinechemBlocks.printer.blockID].itemID, new ItemBlueprintPrinterRenderer());
        
        TickRegistry.registerTickHandler(new TickHandler(), Side.CLIENT);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMicroscope.class, new TileEntityMicroscopeRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDecomposer.class, new TileEntityDecomposerRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySynthesis.class, new TileEntitySynthesisRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBlueprintProjector.class, new TileEntityBlueprintProjectorRenderer());
        //ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGhostBlock.class, new TileEntityGhostBlockRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChemicalStorage.class, new TileEntityChemicalStorageRenderer());
        //ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBluePrintPrinter.class, new TileEntityBluePrintPrinterRenderer());
    }

    @Override
    public void registerHooks() {
        MinecraftForge.EVENT_BUS.register(new MinechemSoundEvent());
    }

    @Override
    public World getClientWorld() {
        return FMLClientHandler.instance().getClient().theWorld;
    }

}
