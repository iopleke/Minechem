package minechem;

import minechem.block.uranium.BlockUraniumOre;
import minechem.item.element.Element;
import minechem.item.element.ElementEnum;
import minechem.tileentity.blueprintprojector.BlueprintProjectorBlock;
import minechem.tileentity.blueprintprojector.BlueprintProjectorTileEntity;
import minechem.tileentity.chemicalstorage.ChemicalStorageBlock;
import minechem.tileentity.chemicalstorage.ChemicalStorageTileEntity;
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
import minechem.tileentity.multiblock.ghostblock.GhostBlockItem;
import minechem.tileentity.multiblock.ghostblock.GhostBlockTileEntity;
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
    public static Material materialGhost = new MaterialTransparent(MapColor.airColor);

    public static void registerBlocks()
    {
        // Decomposer
        decomposer = new DecomposerBlock();
        GameRegistry.registerBlock(decomposer, "minechem.blockDecomposer");
        GameRegistry.registerTileEntity(DecomposerTileEntity.class, "minechem.tileEntityDecomposer");

        // Microscope.
        microscope = new MicroscopeBlock();
        GameRegistry.registerBlock(microscope, "minechem.blockMicroscope");
        GameRegistry.registerTileEntity(MicroscopeTileEntity.class, "minechem.tileEntityMicroscope");

        // Chemical Synthesis Machine.
        synthesis = new SynthesisBlock();
        GameRegistry.registerBlock(synthesis, "minechem.blockSynthesis");
        GameRegistry.registerTileEntity(SynthesisTileEntity.class, "minechem.tileEntitySynthesis");

        // Fusion Reactor.
        fusion = new FusionBlock();
        GameRegistry.registerBlock(fusion, FusionItemBlock.class, "minechem.blockFusion");
        GameRegistry.registerTileEntity(FusionTileEntity.class, "minechem.tileEntityFusion");

        // Ghost Block.
        ghostBlock = new GhostBlock();
        GameRegistry.registerBlock(ghostBlock, GhostBlockItem.class, "minechem.blockGhostBlock");
        GameRegistry.registerTileEntity(GhostBlockTileEntity.class, "minechem.tileEntityGhostBock");

        // Blueprint Projector.
        blueprintProjector = new BlueprintProjectorBlock();
        GameRegistry.registerBlock(blueprintProjector, "minechem.blockBlueprintProjector");
        GameRegistry.registerTileEntity(BlueprintProjectorTileEntity.class, "minechem.tileEntityBlueprintProjector");

        // Chemical Storage Chest.
        chemicalStorage = new ChemicalStorageBlock();
        GameRegistry.registerBlock(chemicalStorage, "minechem.blockChemicalStorage");
        GameRegistry.registerTileEntity(ChemicalStorageTileEntity.class, "minechem.tileEntityChemicalStorage");

        // Uranium Ore (World Gen).
        uranium = new BlockUraniumOre();
        GameRegistry.registerBlock(uranium, uranium.getUnlocalizedName());
        OreDictionary.registerOre(uranium.getUnlocalizedName(), new ItemStack(uranium));
        DecomposerRecipe.add(new DecomposerRecipe(new ItemStack(uranium), new Element(ElementEnum.U, 48)));

        // Leaded Chest (for storing radioactive isotopes).
        leadedChest = new LeadedChestBlock();
        GameRegistry.registerBlock(leadedChest, "minechem.leadedChest");
        GameRegistry.registerTileEntity(LeadedChestTileEntity.class, "minechem.tileEntityLeadedChest");

        // Fission Reactor.
        GameRegistry.registerTileEntity(FissionTileEntity.class, "minechem.tileEntityFission");

        // Tile Entity Proxy.
        GameRegistry.registerTileEntity(TileEntityProxy.class, "minchem.tileEntityProxy");
    }
}
