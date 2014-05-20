package minechem;

import minechem.block.MaterialGas;
import minechem.block.OreUranium;
import minechem.item.element.Element;
import minechem.item.element.EnumElement;
import minechem.tileentity.blueprintprojector.BlockBlueprintProjector;
import minechem.tileentity.blueprintprojector.TileEntityBlueprintProjector;
import minechem.tileentity.chemicalstorage.BlockChemicalStorage;
import minechem.tileentity.chemicalstorage.TileEntityChemicalStorage;
import minechem.tileentity.decomposer.DecomposerBlock;
import minechem.tileentity.decomposer.DecomposerRecipe;
import minechem.tileentity.decomposer.DecomposerTileEntity;
import minechem.tileentity.leadedchest.LeadedChestBlock;
import minechem.tileentity.leadedchest.LeadedChestTileEntity;
import minechem.tileentity.microscope.MicroscopeBlock;
import minechem.tileentity.microscope.MicroscopeTileEntity;
import minechem.tileentity.multiblock.fission.FissionTileEntity;
import minechem.tileentity.multiblock.fusion.FusionBlock;
import minechem.tileentity.multiblock.fusion.FusionItemBlock;
import minechem.tileentity.multiblock.fusion.FusionTileEntity;
import minechem.tileentity.multiblock.ghostblock.GhostBlock;
import minechem.tileentity.multiblock.ghostblock.GhostItemBlock;
import minechem.tileentity.multiblock.ghostblock.GhostTileEntityBlock;
import minechem.tileentity.prefab.TileEntityProxy;
import minechem.tileentity.synthesis.SynthesisBlock;
import minechem.tileentity.synthesis.SynthesisTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialTransparent;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;

public class MinechemBlocksGeneration
{
    public static Block decomposer;
    public static Block microscope;
    public static Block synthesis;
    public static Block ghostBlock;
    public static Block blueprintProjector;
    public static Block fusion;
    public static Block chemicalStorage;
    public static Block printer;
    public static Block leadedChest;

    public static Block uranium;
    public static Material materialGas = new MaterialGas();
    public static Material materialGhost = new MaterialTransparent(MapColor.airColor);

    public static void registerBlocks()
    {
        // Decomposer
        decomposer = new DecomposerBlock(Settings.Decomposer);
        GameRegistry.registerBlock(decomposer, "minechem.blockDecomposer");
        GameRegistry.registerTileEntity(DecomposerTileEntity.class, "minechem.tileEntityDecomposer");

        // Microscope.
        microscope = new MicroscopeBlock(Settings.Microscope);
        GameRegistry.registerBlock(microscope, "minechem.blockMicroscope");
        GameRegistry.registerTileEntity(MicroscopeTileEntity.class, "minechem.tileEntityMicroscope");

        // Chemical Synthesis Machine.
        synthesis = new SynthesisBlock(Settings.Synthesis);
        GameRegistry.registerBlock(synthesis, "minechem.blockSynthesis");
        GameRegistry.registerTileEntity(SynthesisTileEntity.class, "minechem.tileEntitySynthesis");

        // Fusion Reactor.
        fusion = new FusionBlock(Settings.FusionChamber);
        GameRegistry.registerBlock(fusion, FusionItemBlock.class, "minechem.blockFusion");
        GameRegistry.registerTileEntity(FusionTileEntity.class, "minechem.tileEntityFusion");

        // Ghost Block.
        ghostBlock = new GhostBlock(Settings.GhostBlock);
        GameRegistry.registerBlock(ghostBlock, GhostItemBlock.class, "minechem.blockGhostBlock");
        GameRegistry.registerTileEntity(GhostTileEntityBlock.class, "minechem.tileEntityGhostBock");

        // Blueprint Projector.
        blueprintProjector = new BlockBlueprintProjector(Settings.BlueprintProjector);
        GameRegistry.registerBlock(blueprintProjector, "minechem.blockBlueprintProjector");
        GameRegistry.registerTileEntity(TileEntityBlueprintProjector.class, "minechem.tileEntityBlueprintProjector");

        // Chemical Storage Chest.
        chemicalStorage = new BlockChemicalStorage(Settings.ChemicalStorage);
        GameRegistry.registerBlock(chemicalStorage, "minechem.blockChemicalStorage");
        GameRegistry.registerTileEntity(TileEntityChemicalStorage.class, "minechem.tileEntityChemicalStorage");

        // Uranium Ore (World Gen).
        uranium = new OreUranium(Settings.UraniumOre);
        GameRegistry.registerBlock(uranium, "minechem.uraniumOre");
        OreDictionary.registerOre("oreUranium", new ItemStack(uranium));
        DecomposerRecipe.add(new DecomposerRecipe(new ItemStack(uranium), new Element(EnumElement.U, 48)));

        // Leaded Chest (for storing radioactive isotopes).
        leadedChest = new LeadedChestBlock(Settings.LeadedChest);
        GameRegistry.registerBlock(leadedChest, "minechem.leadedChest");
        GameRegistry.registerTileEntity(LeadedChestTileEntity.class, "minechem.tileEntityLeadedChest");

        // Fission Reactor.
        GameRegistry.registerTileEntity(FissionTileEntity.class, "minechem.tileEntityFission");

        // Tile Entity Proxy.
        GameRegistry.registerTileEntity(TileEntityProxy.class, "minchem.tileEntityProxy");
    }
}
