package minechem.client;

import minechem.client.render.tileentity.TileEntityBlueprintProjectorRenderer;
import minechem.client.render.tileentity.TileEntityChemicalStorageRenderer;
import minechem.client.render.tileentity.TileEntityDecomposerRenderer;
import minechem.client.render.tileentity.TileEntityLeadedChestRenderer;
import minechem.client.render.tileentity.TileEntityMicroscopeRenderer;
import minechem.client.render.tileentity.TileEntitySynthesisRenderer;
import minechem.client.sound.MinechemSoundEvent;
import minechem.common.CommonProxy;
import minechem.common.MinechemBlocks;
import minechem.common.MinechemItems;
import minechem.item.element.ItemElementRenderer;
import minechem.item.molecule.ItemMoleculeRenderer;
import minechem.tileentity.blueprintprojector.ItemBlueprintProjectorRenderer;
import minechem.tileentity.blueprintprojector.TileEntityBlueprintProjector;
import minechem.tileentity.chemicalstorage.ItemChemicalStorageRenderer;
import minechem.tileentity.chemicalstorage.TileEntityChemicalStorage;
import minechem.tileentity.decomposer.ItemDecomposerRenderer;
import minechem.tileentity.decomposer.TileEntityDecomposer;
import minechem.tileentity.leadedchest.ItemLeadedChestRenderer;
import minechem.tileentity.leadedchest.TileEntityLeadedChest;
import minechem.tileentity.microscope.ItemMicroscopeRenderer;
import minechem.tileentity.microscope.TileEntityMicroscope;
import minechem.tileentity.synthesis.ItemSynthesisRenderer;
import minechem.tileentity.synthesis.TileEntitySynthesis;
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

        MinecraftForgeClient.registerItemRenderer(MinechemItems.element.itemID, new ItemElementRenderer());
        MinecraftForgeClient.registerItemRenderer(MinechemItems.molecule.itemID, new ItemMoleculeRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.itemsList[MinechemBlocks.microscope.blockID].itemID, new ItemMicroscopeRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.itemsList[MinechemBlocks.decomposer.blockID].itemID, new ItemDecomposerRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.itemsList[MinechemBlocks.synthesis.blockID].itemID, new ItemSynthesisRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.itemsList[MinechemBlocks.blueprintProjector.blockID].itemID, new ItemBlueprintProjectorRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.itemsList[MinechemBlocks.chemicalStorage.blockID].itemID, new ItemChemicalStorageRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.itemsList[MinechemBlocks.leadedChest.blockID].itemID, new ItemLeadedChestRenderer());

        TickRegistry.registerTickHandler(new TickHandler(), Side.CLIENT);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMicroscope.class, new TileEntityMicroscopeRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDecomposer.class, new TileEntityDecomposerRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySynthesis.class, new TileEntitySynthesisRenderer());
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
