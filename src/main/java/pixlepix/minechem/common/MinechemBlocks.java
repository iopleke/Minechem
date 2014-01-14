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

public class MinechemBlocks {
    public static Block decomposer;
    public static Block microscope;
    public static Block synthesis;
    public static Block ghostBlock;
    public static Block blueprintProjector;
    public static Block fusion;
    public static Block chemicalStorage;
    public static Block printer;

    public static Block uranium;
    public static Material materialGas = new MaterialGas();
    public static Material materialGhost = new MaterialTransparent(MapColor.airColor);
    private static int microscopeID;
    private static int decomposerID;
    private static int synthesisID;
    private static int ghostBlockID;
    private static int blueprintProjectorID;
    private static int fusionID;
    private static int chemicalStorageID;
    private static int printerID;

    private static int uraniumID;

    public static void loadConfig(Configuration config) {
        int baseID = 4012;
        microscopeID = getBlockConfig(config, "Microscope", baseID++);
        decomposerID = getBlockConfig(config, "Decomposer", baseID++);
        synthesisID = getBlockConfig(config, "Synthesis", baseID++);
        blueprintProjectorID = getBlockConfig(config, "BlueprintProjector", baseID++);
        ghostBlockID = getBlockConfig(config, "GhostBlock", baseID++);
        fusionID = getBlockConfig(config, "FusionChamber", baseID++);
        chemicalStorageID = getBlockConfig(config, "ChemicalStorage", baseID++);

        printerID = getBlockConfig(config, "BluePrintPrinter", baseID++);
        uraniumID = getBlockConfig(config, "Uranium Ore", baseID++);
    }

    private static int getBlockConfig(Configuration config, String key, int defaultID) {
        return config.get(Configuration.CATEGORY_BLOCK, key, defaultID).getInt(defaultID);
    }

    public static void registerBlocks() {
        decomposer = new BlockDecomposer(decomposerID);
        microscope = new BlockMicroscope(microscopeID);
        synthesis = new BlockSynthesis(synthesisID);
        fusion = new BlockFusion(fusionID);
        ghostBlock = new BlockGhostBlock(ghostBlockID);
        blueprintProjector = new BlockBlueprintProjector(blueprintProjectorID);
        chemicalStorage = new BlockChemicalStorage(chemicalStorageID);
        uranium = new OreUranium(uraniumID);


        GameRegistry.registerBlock(uranium, "minechem.uraniumOre");
        OreDictionary.registerOre("oreUranium", new ItemStack(uranium));

        DecomposerRecipe.add(new DecomposerRecipe(new ItemStack(uranium), new Element(EnumElement.U, 48)));

        LanguageRegistry.addName(uranium, MinechemHelper.getLocalString("block.name.uraniumOre"));
        GameRegistry.registerBlock(decomposer, "minechem.blockDecomposer");
        LanguageRegistry.addName(decomposer, MinechemHelper.getLocalString("block.name.decomposer"));
        GameRegistry.registerBlock(microscope, "minechem.blockMicroscope");
        LanguageRegistry.addName(microscope, MinechemHelper.getLocalString("block.name.microscope"));
        GameRegistry.registerBlock(synthesis, "minechem.blockSynthesis");
        LanguageRegistry.addName(synthesis, MinechemHelper.getLocalString("block.name.synthesis"));
        //GameRegistry.registerBlock(printer, "minechem.blockBlueprintPrinter");
        //LanguageRegistry.addName(printer, "Blueprint Printer (WIP)");
        GameRegistry.registerBlock(ghostBlock, ItemGhostBlock.class, "minechem.blockGhostBlock");
        LanguageRegistry.addName(ghostBlock, "minechem.blockGhostBlock");

        GameRegistry.registerBlock(fusion, ItemBlockFusion.class, "minechem.blockFusion");
        LanguageRegistry.addName(fusion, MinechemHelper.getLocalString("block.name.fusion"));

        GameRegistry.registerBlock(blueprintProjector, "minechem.blockBlueprintProjector");
        LanguageRegistry.addName(blueprintProjector, MinechemHelper.getLocalString("block.name.blueprintProjector"));

        GameRegistry.registerBlock(chemicalStorage, "minechem.blockChemicalStorage");
        LanguageRegistry.addName(chemicalStorage, MinechemHelper.getLocalString("block.name.chemicalStorage"));

        GameRegistry.registerTileEntity(TileEntityFission.class, "minechem.tileEntityFission");
        GameRegistry.registerTileEntity(TileEntityMicroscope.class, "minechem.tileEntityMicroscope");
        GameRegistry.registerTileEntity(TileEntitySynthesis.class, "minechem.tileEntitySynthesis");
        GameRegistry.registerTileEntity(TileEntityDecomposer.class, "minechem.tileEntityDecomposer");
        GameRegistry.registerTileEntity(TileEntityBlueprintProjector.class, "minechem.tileEntityBlueprintProjector");
        GameRegistry.registerTileEntity(TileEntityFusion.class, "minechem.tileEntityFusion");
        GameRegistry.registerTileEntity(TileEntityProxy.class, "minchem.tileEntityProxy");
        GameRegistry.registerTileEntity(TileEntityGhostBlock.class, "minechem.tileEntityGhostBock");
        GameRegistry.registerTileEntity(TileEntityChemicalStorage.class, "minechem.tileEntityChemicalStorage");

    }

}
