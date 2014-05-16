package minechem.common;

import minechem.api.core.Element;
import minechem.api.core.EnumElement;
import minechem.api.recipe.DecomposerRecipe;
import minechem.common.blocks.BlockBlueprintProjector;
import minechem.common.blocks.BlockChemicalStorage;
import minechem.common.blocks.BlockDecomposer;
import minechem.common.blocks.BlockFusion;
import minechem.common.blocks.BlockGhostBlock;
import minechem.common.blocks.BlockLeadedChest;
import minechem.common.blocks.BlockMicroscope;
import minechem.common.blocks.BlockSynthesis;
import minechem.common.blocks.MaterialGas;
import minechem.common.blocks.OreUranium;
import minechem.common.items.ItemBlockFusion;
import minechem.common.items.ItemGhostBlock;
import minechem.common.tileentity.TileEntityBlueprintProjector;
import minechem.common.tileentity.TileEntityChemicalStorage;
import minechem.common.tileentity.TileEntityDecomposer;
import minechem.common.tileentity.TileEntityFission;
import minechem.common.tileentity.TileEntityFusion;
import minechem.common.tileentity.TileEntityGhostBlock;
import minechem.common.tileentity.TileEntityLeadedChest;
import minechem.common.tileentity.TileEntityMicroscope;
import minechem.common.tileentity.TileEntityProxy;
import minechem.common.tileentity.TileEntitySynthesis;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialTransparent;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;

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
        GameRegistry.registerTileEntity(TileEntityDecomposer.class, "minechem.tileEntityDecomposer");

        // Microscope.
        microscope = new BlockMicroscope(Settings.Microscope);
        GameRegistry.registerBlock(microscope, "minechem.blockMicroscope");
        GameRegistry.registerTileEntity(TileEntityMicroscope.class, "minechem.tileEntityMicroscope");

        // Chemical Synthesis Machine.
        synthesis = new BlockSynthesis(Settings.Synthesis);
        GameRegistry.registerBlock(synthesis, "minechem.blockSynthesis");
        GameRegistry.registerTileEntity(TileEntitySynthesis.class, "minechem.tileEntitySynthesis");

        // Fusion Reactor.
        fusion = new BlockFusion(Settings.FusionChamber);
        GameRegistry.registerBlock(fusion, ItemBlockFusion.class, "minechem.blockFusion");
        GameRegistry.registerTileEntity(TileEntityFusion.class, "minechem.tileEntityFusion");

        // Ghost Block.
        ghostBlock = new BlockGhostBlock(Settings.GhostBlock);
        GameRegistry.registerBlock(ghostBlock, ItemGhostBlock.class, "minechem.blockGhostBlock");
        GameRegistry.registerTileEntity(TileEntityGhostBlock.class, "minechem.tileEntityGhostBock");

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
        leadedChest = new BlockLeadedChest(Settings.LeadedChest);
        GameRegistry.registerBlock(leadedChest, "minechem.leadedChest");
        GameRegistry.registerTileEntity(TileEntityLeadedChest.class, "minechem.tileEntityLeadedChest");

        // Fission Reactor.
        GameRegistry.registerTileEntity(TileEntityFission.class, "minechem.tileEntityFission");

        // Tile Entity Proxy.
        GameRegistry.registerTileEntity(TileEntityProxy.class, "minchem.tileEntityProxy");
    }
}
