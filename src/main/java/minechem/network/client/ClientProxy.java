package minechem.network.client;

import minechem.MinechemBlocksGeneration;
import minechem.MinechemItemsGeneration;
import minechem.item.element.ElementItemRenderer;
import minechem.item.molecule.MoleculeItemRenderer;
import minechem.network.server.CommonProxy;
import minechem.sound.MinechemSoundEvent;
import minechem.tileentity.blueprintprojector.BlueprintProjectorItemRenderer;
import minechem.tileentity.blueprintprojector.BlueprintProjectorTileEntity;
import minechem.tileentity.blueprintprojector.BlueprintProjectorTileEntityRenderer;
import minechem.tileentity.chemicalstorage.ChemicalStorageItemRenderer;
import minechem.tileentity.chemicalstorage.ChemicalStorageTileEntity;
import minechem.tileentity.chemicalstorage.ChemicalStorageTileEntityRenderer;
import minechem.tileentity.decomposer.DecomposerItemRenderer;
import minechem.tileentity.decomposer.DecomposerTileEntity;
import minechem.tileentity.decomposer.DecomposerTileEntityRenderer;
import minechem.tileentity.leadedchest.LeadedChestItemRenderer;
import minechem.tileentity.leadedchest.LeadedChestTileEntity;
import minechem.tileentity.leadedchest.LeadedChestTileEntityRenderer;
import minechem.tileentity.microscope.MicroscopeItemRenderer;
import minechem.tileentity.microscope.MicroscopeTileEntity;
import minechem.tileentity.microscope.MicroscopeTileEntityRenderer;
import minechem.tileentity.synthesis.SynthesisItemRenderer;
import minechem.tileentity.synthesis.SynthesisTileEntity;
import minechem.tileentity.synthesis.SynthesisTileEntityRenderer;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
    public static IIcon clay;
    public static IIcon coal;
    public static IIcon concentrated;
    public static IIcon seed;
    public static IIcon split;

    public static IIcon sand;

    @Override
    public void registerRenderers()
    {
        RENDER_ID = RenderingRegistry.getNextAvailableRenderId();

        MinecraftForgeClient.registerItemRenderer(MinechemItemsGeneration.element, new ElementItemRenderer());
        MinecraftForgeClient.registerItemRenderer(MinechemItemsGeneration.molecule, new MoleculeItemRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(MinechemBlocksGeneration.microscope), new MicroscopeItemRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(MinechemBlocksGeneration.decomposer), new DecomposerItemRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(MinechemBlocksGeneration.synthesis), new SynthesisItemRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(MinechemBlocksGeneration.blueprintProjector), new BlueprintProjectorItemRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(MinechemBlocksGeneration.chemicalStorage), new ChemicalStorageItemRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(MinechemBlocksGeneration.leadChest), new LeadedChestItemRenderer());

        //TickRegistry.registerTickHandler(new TickHandler(), Side.CLIENT);//TODO:change so it will use events
        ClientRegistry.bindTileEntitySpecialRenderer(MicroscopeTileEntity.class, new MicroscopeTileEntityRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(DecomposerTileEntity.class, new DecomposerTileEntityRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(SynthesisTileEntity.class, new SynthesisTileEntityRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(BlueprintProjectorTileEntity.class, new BlueprintProjectorTileEntityRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(ChemicalStorageTileEntity.class, new ChemicalStorageTileEntityRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(LeadedChestTileEntity.class, new LeadedChestTileEntityRenderer());
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
