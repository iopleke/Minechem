package minechem.network.client;

import minechem.MinechemBlocksGeneration;
import minechem.MinechemItemsGeneration;
import minechem.item.element.ItemElementRenderer;
import minechem.item.molecule.ItemMoleculeRenderer;
import minechem.network.server.CommonProxy;
import minechem.sound.MinechemSoundEvent;
import minechem.tickhandler.TickHandler;
import minechem.tileentity.blueprintprojector.ItemBlueprintProjectorRenderer;
import minechem.tileentity.blueprintprojector.TileEntityBlueprintProjector;
import minechem.tileentity.blueprintprojector.TileEntityBlueprintProjectorRenderer;
import minechem.tileentity.chemicalstorage.ItemChemicalStorageRenderer;
import minechem.tileentity.chemicalstorage.TileEntityChemicalStorage;
import minechem.tileentity.chemicalstorage.TileEntityChemicalStorageRenderer;
import minechem.tileentity.decomposer.DecomposerItemRenderer;
import minechem.tileentity.decomposer.DecomposerTileEntity;
import minechem.tileentity.decomposer.DecomposerTileEntityRenderer;
import minechem.tileentity.leadedchest.ItemLeadedChestRenderer;
import minechem.tileentity.leadedchest.TileEntityLeadedChest;
import minechem.tileentity.leadedchest.TileEntityLeadedChestRenderer;
import minechem.tileentity.microscope.MicroscopeItemRenderer;
import minechem.tileentity.microscope.MicroscopeTileEntity;
import minechem.tileentity.microscope.MicroscopeTileEntityRenderer;
import minechem.tileentity.synthesis.SynthesisItemRenderer;
import minechem.tileentity.synthesis.SynthesisTileEntity;
import minechem.tileentity.synthesis.SynthesisTileEntityRenderer;
import net.minecraft.item.Item;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy extends CommonProxy
{
    public static Icon clay;
    public static Icon coal;
    public static Icon concentrated;
    public static Icon seed;
    public static Icon split;

    public static Icon sand;

    @Override
    public void registerRenderers()
    {
        RENDER_ID = RenderingRegistry.getNextAvailableRenderId();

        MinecraftForgeClient.registerItemRenderer(MinechemItemsGeneration.element.itemID, new ItemElementRenderer());
        MinecraftForgeClient.registerItemRenderer(MinechemItemsGeneration.molecule.itemID, new ItemMoleculeRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.itemsList[MinechemBlocksGeneration.microscope.blockID].itemID, new MicroscopeItemRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.itemsList[MinechemBlocksGeneration.decomposer.blockID].itemID, new DecomposerItemRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.itemsList[MinechemBlocksGeneration.synthesis.blockID].itemID, new SynthesisItemRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.itemsList[MinechemBlocksGeneration.blueprintProjector.blockID].itemID, new ItemBlueprintProjectorRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.itemsList[MinechemBlocksGeneration.chemicalStorage.blockID].itemID, new ItemChemicalStorageRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.itemsList[MinechemBlocksGeneration.leadedChest.blockID].itemID, new ItemLeadedChestRenderer());

        TickRegistry.registerTickHandler(new TickHandler(), Side.CLIENT);
        ClientRegistry.bindTileEntitySpecialRenderer(MicroscopeTileEntity.class, new MicroscopeTileEntityRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(DecomposerTileEntity.class, new DecomposerTileEntityRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(SynthesisTileEntity.class, new SynthesisTileEntityRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBlueprintProjector.class, new TileEntityBlueprintProjectorRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChemicalStorage.class, new TileEntityChemicalStorageRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLeadedChest.class, new TileEntityLeadedChestRenderer());
    }

    @Override
    public void registerHooks()
    {
        MinecraftForge.EVENT_BUS.register(new MinechemSoundEvent());
    }

    @Override
    public World getClientWorld()
    {
        return FMLClientHandler.instance().getClient().theWorld;
    }

}
