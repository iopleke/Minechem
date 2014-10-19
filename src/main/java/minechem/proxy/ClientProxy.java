package minechem.proxy;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import minechem.MinechemBlocksGeneration;
import minechem.MinechemItemsRegistration;
import minechem.fluid.FluidTextureStitchHandler;
import minechem.fluid.MinechemFluid;
import minechem.fluid.MinechemFluidBlock;
import minechem.item.element.ElementItemRenderer;
import minechem.item.molecule.MoleculeItemRenderer;
import minechem.render.ChemicalFluidBlockRenderingHandler;
import minechem.render.FluidItemRenderingHandler;
import minechem.sound.MinechemSoundEvent;
import minechem.tileentity.blueprintprojector.BlueprintProjectorItemRenderer;
import minechem.tileentity.blueprintprojector.BlueprintProjectorTileEntity;
import minechem.tileentity.blueprintprojector.BlueprintProjectorTileEntityRenderer;
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
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy
{
	public static IIcon clay;
	public static IIcon coal;
	public static IIcon concentrated;
	public static IIcon seed;
	public static IIcon split;

	public static IIcon sand;

	public FluidItemRenderingHandler fluidItemRenderingHandler;
	
	@Override
	public void registerRenderers()
	{
		RENDER_ID=RenderingRegistry.getNextAvailableRenderId();
		MinechemFluidBlock.RENDER_ID = RenderingRegistry.getNextAvailableRenderId();

		MinecraftForgeClient.registerItemRenderer(MinechemItemsRegistration.element, new ElementItemRenderer());
		MinecraftForgeClient.registerItemRenderer(MinechemItemsRegistration.molecule, new MoleculeItemRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(MinechemBlocksGeneration.microscope), new MicroscopeItemRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(MinechemBlocksGeneration.decomposer), new DecomposerItemRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(MinechemBlocksGeneration.synthesis), new SynthesisItemRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(MinechemBlocksGeneration.blueprintProjector), new BlueprintProjectorItemRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(MinechemBlocksGeneration.leadChest), new LeadedChestItemRenderer());

		ClientRegistry.bindTileEntitySpecialRenderer(MicroscopeTileEntity.class, new MicroscopeTileEntityRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(DecomposerTileEntity.class, new DecomposerTileEntityRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(SynthesisTileEntity.class, new SynthesisTileEntityRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(BlueprintProjectorTileEntity.class, new BlueprintProjectorTileEntityRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(LeadedChestTileEntity.class, new LeadedChestTileEntityRenderer());

		RenderingRegistry.registerBlockHandler(MinechemFluidBlock.RENDER_ID, new ChemicalFluidBlockRenderingHandler());
	}

	@Override
	public void registerHooks()
	{
		MinecraftForge.EVENT_BUS.register(new MinechemSoundEvent());
		MinecraftForge.EVENT_BUS.register(new FluidTextureStitchHandler());
	}

	@Override
	public World getClientWorld()
	{
		return FMLClientHandler.instance().getClient().theWorld;
	}

	@Override
	public void registerTickHandlers()
	{
		super.registerTickHandlers();

	}

	@Override
	public EntityPlayer getPlayer(MessageContext context)
	{
		return Minecraft.getMinecraft().thePlayer;
	}
    
    @Override
	public void onAddFluid(MinechemFluid fluid,MinechemFluidBlock block){
    	super.onAddFluid(fluid, block);
    	
    	if (fluidItemRenderingHandler==null){
    		fluidItemRenderingHandler=new FluidItemRenderingHandler();
    	}
    	MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(block),fluidItemRenderingHandler);
    }
}
