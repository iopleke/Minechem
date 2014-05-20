package minechem;

import minechem.block.MaterialGas;
import minechem.block.OreUranium;
import minechem.item.element.Element;
import minechem.item.element.EnumElement;
import minechem.tileentity.blueprintprojector.BlockBlueprintProjector;
import minechem.tileentity.blueprintprojector.TileEntityBlueprintProjector;
import minechem.tileentity.decomposer.BlockDecomposer;
import minechem.tileentity.decomposer.DecomposerRecipe;
import minechem.tileentity.decomposer.TileEntityDecomposer;
import minechem.tileentity.fission.TileEntityFission;
import minechem.tileentity.fusion.BlockFusion;
import minechem.tileentity.fusion.ItemBlockFusion;
import minechem.tileentity.fusion.TileEntityFusion;
import minechem.tileentity.ghostblock.BlockGhostBlock;
import minechem.tileentity.ghostblock.ItemGhostBlock;
import minechem.tileentity.ghostblock.TileEntityGhostBlock;
import minechem.tileentity.leadedchest.BlockLeadedChest;
import minechem.tileentity.leadedchest.TileEntityLeadedChest;
import minechem.tileentity.microscope.BlockMicroscope;
import minechem.tileentity.microscope.TileEntityMicroscope;
import minechem.tileentity.prefab.TileEntityProxy;
import minechem.tileentity.synthesis.BlockSynthesis;
import minechem.tileentity.synthesis.TileEntitySynthesis;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialTransparent;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;

public class MinechemBlockGeneration
{
    public static Block decomposer;
    public static Block microscope;
    public static Block synthesis;
    public static Block ghostBlock;
    public static Block blueprintProjector;
    public static Block fusion;
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
