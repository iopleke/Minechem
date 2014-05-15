package pixlepix.minechem.common;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialTransparent;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.oredict.OreDictionary;
import pixlepix.minechem.api.core.Element;
import pixlepix.minechem.api.core.EnumElement;
import pixlepix.minechem.api.recipe.DecomposerRecipe;
import pixlepix.minechem.common.blocks.*;
import pixlepix.minechem.common.items.ItemBlockFusion;
import pixlepix.minechem.common.items.ItemGhostBlock;
import pixlepix.minechem.common.tileentity.*;
import pixlepix.minechem.common.utils.MinechemHelper;

public class MinechemBlocks
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
        decomposer = new BlockDecomposer(Settings.Decomposer);
        GameRegistry.registerBlock(decomposer, "minechem.blockDecomposer");
        LanguageRegistry.addName(decomposer, MinechemHelper.getLocalString("block.name.decomposer"));
        GameRegistry.registerTileEntity(TileEntityDecomposer.class, "minechem.tileEntityDecomposer");
        
        // Microscope.
        microscope = new BlockMicroscope(Settings.Microscope);
        GameRegistry.registerBlock(microscope, "minechem.blockMicroscope");
        LanguageRegistry.addName(microscope, MinechemHelper.getLocalString("block.name.microscope"));
        GameRegistry.registerTileEntity(TileEntityMicroscope.class, "minechem.tileEntityMicroscope");
        
        // Chemical Synthesis Machine.
        synthesis = new BlockSynthesis(Settings.Synthesis);
        GameRegistry.registerBlock(synthesis, "minechem.blockSynthesis");
        LanguageRegistry.addName(synthesis, MinechemHelper.getLocalString("block.name.synthesis"));
        GameRegistry.registerTileEntity(TileEntitySynthesis.class, "minechem.tileEntitySynthesis");
        
        // Fusion Reactor.
        fusion = new BlockFusion(Settings.FusionChamber);
        GameRegistry.registerBlock(fusion, ItemBlockFusion.class, "minechem.blockFusion");
        LanguageRegistry.addName(fusion, MinechemHelper.getLocalString("block.name.fusion"));
        GameRegistry.registerTileEntity(TileEntityFusion.class, "minechem.tileEntityFusion");
        
        // Ghost Block.
        ghostBlock = new BlockGhostBlock(Settings.GhostBlock);
        GameRegistry.registerBlock(ghostBlock, ItemGhostBlock.class, "minechem.blockGhostBlock");
        LanguageRegistry.addName(ghostBlock, "minechem.blockGhostBlock");
        GameRegistry.registerTileEntity(TileEntityGhostBlock.class, "minechem.tileEntityGhostBock");
        
        // Blueprint Projector.
        blueprintProjector = new BlockBlueprintProjector(Settings.BlueprintProjector);
        GameRegistry.registerBlock(blueprintProjector, "minechem.blockBlueprintProjector");
        LanguageRegistry.addName(blueprintProjector, MinechemHelper.getLocalString("block.name.blueprintProjector"));
        GameRegistry.registerTileEntity(TileEntityBlueprintProjector.class, "minechem.tileEntityBlueprintProjector");
        
        // Chemical Storage Chest.
        chemicalStorage = new BlockChemicalStorage(Settings.ChemicalStorage);
        GameRegistry.registerBlock(chemicalStorage, "minechem.blockChemicalStorage");
        LanguageRegistry.addName(chemicalStorage, MinechemHelper.getLocalString("block.name.chemicalStorage"));
        GameRegistry.registerTileEntity(TileEntityChemicalStorage.class, "minechem.tileEntityChemicalStorage");
        
        // Uranium Ore (World Gen).
        uranium = new OreUranium(Settings.UraniumOre);
        GameRegistry.registerBlock(uranium, "minechem.uraniumOre");
        LanguageRegistry.addName(uranium, MinechemHelper.getLocalString("block.name.uraniumOre"));
        OreDictionary.registerOre("oreUranium", new ItemStack(uranium));
        DecomposerRecipe.add(new DecomposerRecipe(new ItemStack(uranium), new Element(EnumElement.U, 48)));
        
        // Leaded Chest (for storing radioactive isotopes).
        leadedChest = new BlockLeadedChest(Settings.LeadedChest);
        GameRegistry.registerBlock(leadedChest, "minechem.leadedChest");
        LanguageRegistry.addName(leadedChest, MinechemHelper.getLocalString("block.name.leadedChest"));
        GameRegistry.registerTileEntity(TileEntityLeadedChest.class, "minechem.tileEntityLeadedChest");
        
        // Fission Reactor.
        GameRegistry.registerTileEntity(TileEntityFission.class, "minechem.tileEntityFission");
        
        // Tile Entity Proxy.
        GameRegistry.registerTileEntity(TileEntityProxy.class, "minchem.tileEntityProxy");
    }
}
