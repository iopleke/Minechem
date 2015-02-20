package minechem.proxy.client;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import minechem.apparatus.prefab.renderer.BasicItemRenderer;
import minechem.apparatus.tier1.electricCrucible.ElectricCrucibleTileEntity;
import minechem.apparatus.tier1.electricCrucible.ElectricCrucibleTileEntityRenderer;
import minechem.apparatus.tier1.electrolysis.ElectrolysisTileEntity;
import minechem.apparatus.tier1.electrolysis.ElectrolysisTileEntityRenderer;
import minechem.apparatus.tier1.opticalMicroscope.OpticalMicroscopeTileEntity;
import minechem.apparatus.tier1.opticalMicroscope.OpticalMicroscopeTileEntityRenderer;
import minechem.blocks.LightRenderer;
import minechem.handler.ResourceReloadListener;
import minechem.helper.LogHelper;
import minechem.item.chemical.ChemicalItemRenderer;
import minechem.proxy.CommonProxy;
import minechem.registry.BlockRegistry;
import minechem.registry.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy
{
    @Override
    public World getClientWorld()
    {
        return FMLClientHandler.instance().getClient().theWorld;
    }

    @Override
    public void registerRenderers()
    {
        RENDER_ID = RenderingRegistry.getNextAvailableRenderId();
        ISBRH_ID = RenderingRegistry.getNextAvailableRenderId();

        OpticalMicroscopeTileEntityRenderer opticalMicroscopeRenderer = new OpticalMicroscopeTileEntityRenderer();
        ClientRegistry.bindTileEntitySpecialRenderer(OpticalMicroscopeTileEntity.class, opticalMicroscopeRenderer);
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.opticalMicroscope), new BasicItemRenderer(opticalMicroscopeRenderer, new OpticalMicroscopeTileEntity()));

        ElectrolysisTileEntityRenderer electrolysisRenderer = new ElectrolysisTileEntityRenderer();
        ClientRegistry.bindTileEntitySpecialRenderer(ElectrolysisTileEntity.class, electrolysisRenderer);
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.electrolysisBlock), new BasicItemRenderer(electrolysisRenderer, new ElectrolysisTileEntity()));

        ElectricCrucibleTileEntityRenderer electricCrucibleRenderer = new ElectricCrucibleTileEntityRenderer();
        ClientRegistry.bindTileEntitySpecialRenderer(ElectricCrucibleTileEntity.class, electricCrucibleRenderer);
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.electricCrucibleBlock), new BasicItemRenderer(electricCrucibleRenderer, new ElectricCrucibleTileEntity()));

        RenderingRegistry.registerBlockHandler(BlockRegistry.blockLight.getRenderType(), new LightRenderer());
        MinecraftForgeClient.registerItemRenderer(ItemRegistry.chemicalItem, new ChemicalItemRenderer());
    }

    @Override
    public void registerResourcesListener()
    {
        LogHelper.debug("Registering Resource Reload Listener...");
        ((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(new ResourceReloadListener());
    }

    /**
     * Get the current lang code
     *
     * @return eg. 'en_US'
     */
    public String getCurrentLanguage()
    {
        return Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode();
    }

    @Override
    public World getWorld(MessageContext context)
    {
        return Minecraft.getMinecraft().theWorld;
    }

    @Override
    public EntityPlayer getPlayer(MessageContext context)
    {
        return Minecraft.getMinecraft().thePlayer;
    }
}
