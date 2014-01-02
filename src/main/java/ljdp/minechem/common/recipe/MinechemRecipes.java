package ljdp.minechem.common.recipe;

import java.util.ArrayList;
import java.util.Iterator;

import ljdp.minechem.api.core.Chemical;
import ljdp.minechem.api.core.Element;
import ljdp.minechem.api.core.EnumElement;
import ljdp.minechem.api.core.EnumMolecule;
import ljdp.minechem.api.core.Molecule;
import ljdp.minechem.api.recipe.DecomposerRecipe;
import ljdp.minechem.api.recipe.DecomposerRecipeChance;
import ljdp.minechem.api.recipe.DecomposerRecipeSelect;
import ljdp.minechem.api.recipe.SynthesisRecipe;
import ljdp.minechem.api.util.Util;
import ljdp.minechem.common.MinechemBlocks;
import ljdp.minechem.common.MinechemItems;
import ljdp.minechem.common.blueprint.MinechemBlueprint;
import ljdp.minechem.common.items.ItemBlueprint;
import ljdp.minechem.common.items.ItemElement;
import ljdp.minechem.common.recipe.handlers.AppliedEnergisticsOreDictionaryHandler;
import ljdp.minechem.common.recipe.handlers.DefaultOreDictionaryHandler;
import ljdp.minechem.common.recipe.handlers.GregTechOreDictionaryHandler;
import ljdp.minechem.common.recipe.handlers.IC2OreDictionaryHandler;
import ljdp.minechem.common.recipe.handlers.MekanismOreDictionaryHandler;
import ljdp.minechem.common.recipe.handlers.UndergroundBiomesOreDictionaryHandler;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

public class MinechemRecipes {

    private static final MinechemRecipes instance = new MinechemRecipes();
    public ArrayList unbondingRecipes = new ArrayList();
    public ArrayList synthesisRecipes = new ArrayList();

    public static MinechemRecipes getInstance() {
        return instance;
    }
        
    public void registerVanillaChemicalRecipes() {

		// Molecules
		Molecule moleculeSiliconDioxide = this.molecule(
				EnumMolecule.siliconDioxide, 4);
		Molecule moleculeCellulose = this.molecule(EnumMolecule.cellulose, 1);
		Molecule moleculePolyvinylChloride = this
				.molecule(EnumMolecule.polyvinylChloride);

		// Elements
		Element elementHydrogen = this.element(EnumElement.H, 64);
		Element elementHelium = this.element(EnumElement.He, 64);
		Element elementCarbon = this.element(EnumElement.C, 64);

		// Section 1 - Blocks

		// Stone
		ItemStack blockStone = new ItemStack(Block.stone);
		DecomposerRecipe.add(new DecomposerRecipeSelect(blockStone, 0.2F,
				new DecomposerRecipe[] {
						new DecomposerRecipe(new Chemical[] {
								this.element(EnumElement.Si),
								this.element(EnumElement.O) }),
						new DecomposerRecipe(new Chemical[] {
								this.element(EnumElement.Fe),
								this.element(EnumElement.O) }),
						new DecomposerRecipe(new Chemical[] {
								this.element(EnumElement.Mg),
								this.element(EnumElement.O) }),
						new DecomposerRecipe(new Chemical[] {
								this.element(EnumElement.Ti),
								this.element(EnumElement.O) }),
						new DecomposerRecipe(new Chemical[] {
								this.element(EnumElement.Pb),
								this.element(EnumElement.O) }),
						new DecomposerRecipe(new Chemical[] {
								this.element(EnumElement.Zn),
								this.element(EnumElement.O) }) }));
		SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Block.stone, 7),
				true, 50, new Chemical[] { this.element(EnumElement.Si), null,
						null, this.element(EnumElement.O, 2), null, null }));

		// Grass Block
		ItemStack blockGrass = new ItemStack(Block.grass);
		DecomposerRecipe.add(new DecomposerRecipeSelect(blockGrass, 0.07F,
				new DecomposerRecipe[] {
						new DecomposerRecipe(new Chemical[] {
								this.element(EnumElement.Si),
								this.element(EnumElement.O) }),
						new DecomposerRecipe(new Chemical[] {
								this.element(EnumElement.Fe),
								this.element(EnumElement.O) }),
						new DecomposerRecipe(new Chemical[] {
								this.element(EnumElement.Mg),
								this.element(EnumElement.O) }),
						new DecomposerRecipe(new Chemical[] {
								this.element(EnumElement.Ti),
								this.element(EnumElement.O) }),
						new DecomposerRecipe(new Chemical[] {
								this.element(EnumElement.Pb),
								this.element(EnumElement.O) }),
						new DecomposerRecipe(new Chemical[] {
								this.element(EnumElement.Zn),
								this.element(EnumElement.O) }),
						new DecomposerRecipe(new Chemical[] {
								this.element(EnumElement.Ga),
								this.element(EnumElement.As) }),
						new DecomposerRecipe(
								new Chemical[] { moleculeCellulose }) }));
		SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Block.grass, 16),
				true, 50, new Chemical[] { null, moleculeCellulose, null, null,
						this.element(EnumElement.O, 2),
						this.element(EnumElement.Si) }));

		// Dirt
		ItemStack blockDirt = new ItemStack(Block.dirt);
		DecomposerRecipe.add(new DecomposerRecipeSelect(blockDirt, 0.07F,
				new DecomposerRecipe[] {
						new DecomposerRecipe(new Chemical[] {
								this.element(EnumElement.Si),
								this.element(EnumElement.O) }),
						new DecomposerRecipe(new Chemical[] {
								this.element(EnumElement.Fe),
								this.element(EnumElement.O) }),
						new DecomposerRecipe(new Chemical[] {
								this.element(EnumElement.Mg),
								this.element(EnumElement.O) }),
						new DecomposerRecipe(new Chemical[] {
								this.element(EnumElement.Ti),
								this.element(EnumElement.O) }),
						new DecomposerRecipe(new Chemical[] {
								this.element(EnumElement.Pb),
								this.element(EnumElement.O) }),
						new DecomposerRecipe(new Chemical[] {
								this.element(EnumElement.Zn),
								this.element(EnumElement.O) }),
						new DecomposerRecipe(new Chemical[] {
								this.element(EnumElement.Ga),
								this.element(EnumElement.As) }) }));
		SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Block.dirt, 16),
				true, 50, new Chemical[] { null, null, null, null,
						this.element(EnumElement.O, 2),
						this.element(EnumElement.Si) }));

		// Cobblestone
		ItemStack blockCobblestone = new ItemStack(Block.cobblestone);
		DecomposerRecipe.add(new DecomposerRecipeSelect(blockCobblestone, 0.1F,
				new DecomposerRecipe[] {
						new DecomposerRecipe(new Chemical[] {
								this.element(EnumElement.Si),
								this.element(EnumElement.O) }),
						new DecomposerRecipe(new Chemical[] {
								this.element(EnumElement.Fe),
								this.element(EnumElement.O) }),
						new DecomposerRecipe(new Chemical[] {
								this.element(EnumElement.Mg),
								this.element(EnumElement.O) }),
						new DecomposerRecipe(new Chemical[] {
								this.element(EnumElement.Ti),
								this.element(EnumElement.O) }),
						new DecomposerRecipe(new Chemical[] {
								this.element(EnumElement.Pb),
								this.element(EnumElement.O) }),
						new DecomposerRecipe(new Chemical[] {
								this.element(EnumElement.Na),
								this.element(EnumElement.Cl) }) }));
		SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(
				Block.cobblestone, 8), true, 50, new Chemical[] {
				this.element(EnumElement.Si), null, null, null,
				this.element(EnumElement.O, 2), null }));

		// Planks
		// TODO: Add synthesizer recipes?

		ItemStack blockOakWoodPlanks = new ItemStack(Block.planks, 1, 0);
		ItemStack blockSpruceWoodPlanks = new ItemStack(Block.planks, 1, 1);
		ItemStack blockBirchWoodPlanks = new ItemStack(Block.planks, 1, 2);
		ItemStack blockJungleWoodPlanks = new ItemStack(Block.planks, 1, 3);
		DecomposerRecipe.add(new DecomposerRecipeChance(blockOakWoodPlanks,
				0.4F,
				new Chemical[] { this.molecule(EnumMolecule.cellulose, 2) }));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockSpruceWoodPlanks,
				0.4F,
				new Chemical[] { this.molecule(EnumMolecule.cellulose, 2) }));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockBirchWoodPlanks,
				0.4F,
				new Chemical[] { this.molecule(EnumMolecule.cellulose, 2) }));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockJungleWoodPlanks,
				0.4F,
				new Chemical[] { this.molecule(EnumMolecule.cellulose, 2) }));

		// Saplings
		ItemStack blockOakSapling = new ItemStack(Block.sapling, 1, 0);
		ItemStack blockSpruceSapling = new ItemStack(Block.sapling, 1, 1);
		ItemStack blockBirchSapling = new ItemStack(Block.sapling, 1, 2);
		ItemStack blockJungleSapling = new ItemStack(Block.sapling, 1, 3);
		DecomposerRecipe.add(new DecomposerRecipeChance(blockOakSapling, 0.25F,
				new Chemical[] { this.molecule(EnumMolecule.cellulose) }));
		DecomposerRecipe
				.add(new DecomposerRecipeChance(
						blockSpruceSapling,
						0.25F,
						new Chemical[] { this.molecule(EnumMolecule.cellulose) }));
		DecomposerRecipe
				.add(new DecomposerRecipeChance(
						blockBirchSapling,
						0.25F,
						new Chemical[] { this.molecule(EnumMolecule.cellulose) }));
		DecomposerRecipe
				.add(new DecomposerRecipeChance(
						blockJungleSapling,
						0.25F,
						new Chemical[] { this.molecule(EnumMolecule.cellulose) }));
		SynthesisRecipe.add(new SynthesisRecipe(blockOakSapling, true, 20,
				new Chemical[] { null, null, null, null, null, null, null,
						null, this.molecule(EnumMolecule.cellulose) }));
		SynthesisRecipe.add(new SynthesisRecipe(blockSpruceSapling, true, 20,
				new Chemical[] { null, null, null, null, null, null, null,
						this.molecule(EnumMolecule.cellulose), null }));
		SynthesisRecipe.add(new SynthesisRecipe(blockBirchSapling, true, 20,
				new Chemical[] { null, null, null, null, null, null,
						this.molecule(EnumMolecule.cellulose), null, null }));
		SynthesisRecipe
				.add(new SynthesisRecipe(blockJungleSapling, true, 20,
						new Chemical[] { null, null, null, null, null,
								this.molecule(EnumMolecule.cellulose), null,
								null, null }));

		// Water
		ItemStack blockWaterSource = new ItemStack(Block.waterMoving);
		ItemStack blockWaterStill = new ItemStack(Block.waterStill);
		DecomposerRecipe.add(new DecomposerRecipe(blockWaterSource,
				new Chemical[] { this.molecule(EnumMolecule.water, 16) }));
		DecomposerRecipe.add(new DecomposerRecipe(blockWaterStill,
				new Chemical[] { this.molecule(EnumMolecule.water, 16) }));
		SynthesisRecipe.add(new SynthesisRecipe(blockWaterSource, false, 20,
				new Chemical[] { this.molecule(EnumMolecule.water, 16) }));

		// Lava
		// TODO: Add support for lava

		// Sand
		ItemStack blockSand = new ItemStack(Block.sand);
		DecomposerRecipe
				.add(new DecomposerRecipe(blockSand, new Chemical[] { this
						.molecule(EnumMolecule.siliconDioxide, 16) }));
		SynthesisRecipe.add(new SynthesisRecipe(blockSand, true, 200,
				new Chemical[] { moleculeSiliconDioxide,
						moleculeSiliconDioxide, moleculeSiliconDioxide,
						moleculeSiliconDioxide }));

		// Gravel
		ItemStack blockGravel = new ItemStack(Block.gravel);
		DecomposerRecipe.add(new DecomposerRecipeChance(blockGravel, 0.35F,
				new Chemical[] { this.molecule(EnumMolecule.siliconDioxide) }));
		SynthesisRecipe.add(new SynthesisRecipe(blockGravel, true, 30,
				new Chemical[] { null, null, null, null, null, null, null,
						null, this.molecule(EnumMolecule.siliconDioxide) }));

		// Gold Ore
		ItemStack oreGold = new ItemStack(Block.oreGold);
		DecomposerRecipe.add(new DecomposerRecipe(oreGold,
				new Chemical[] { this.element(EnumElement.Au, 48) }));

		// Iron Ore
		ItemStack oreIron = new ItemStack(Block.oreIron);
		DecomposerRecipe.add(new DecomposerRecipe(oreIron,
				new Chemical[] { this.element(EnumElement.Fe, 48) }));

		// Coal Ore
		ItemStack oreCoal = new ItemStack(Block.oreCoal);
		DecomposerRecipe.add(new DecomposerRecipe(oreCoal,
				new Chemical[] { this.element(EnumElement.C, 48) }));

		// Wood
		ItemStack blockOakWood = new ItemStack(Block.wood, 1, 0);
		ItemStack blockSpruceWood = new ItemStack(Block.wood, 1, 1);
		ItemStack blockBirchWood = new ItemStack(Block.wood, 1, 2);
		ItemStack blockJungleWood = new ItemStack(Block.wood, 1, 3);
		DecomposerRecipe.add(new DecomposerRecipeChance(blockOakWood, 0.5F,
				new Chemical[] { this.molecule(EnumMolecule.cellulose, 8) }));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockSpruceWood, 0.5F,
				new Chemical[] { this.molecule(EnumMolecule.cellulose, 8) }));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockBirchWood, 0.5F,
				new Chemical[] { this.molecule(EnumMolecule.cellulose, 8) }));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockJungleWood, 0.5F,
				new Chemical[] { this.molecule(EnumMolecule.cellulose, 8) }));
		SynthesisRecipe.add(new SynthesisRecipe(blockOakWood, true, 100,
				new Chemical[] { moleculeCellulose, moleculeCellulose,
						moleculeCellulose, null, moleculeCellulose, null, null,
						null, null }));
		SynthesisRecipe.add(new SynthesisRecipe(blockSpruceWood, true, 100,
				new Chemical[] { null, null, null, null, moleculeCellulose,
						null, moleculeCellulose, moleculeCellulose,
						moleculeCellulose }));
		SynthesisRecipe.add(new SynthesisRecipe(blockBirchWood, true, 100,
				new Chemical[] { moleculeCellulose, null, moleculeCellulose,
						null, null, null, moleculeCellulose, null,
						moleculeCellulose }));
		SynthesisRecipe.add(new SynthesisRecipe(blockJungleWood, true, 100,
				new Chemical[] { moleculeCellulose, null, null,
						moleculeCellulose, moleculeCellulose, null,
						moleculeCellulose, null, null }));

		// Leaves
		// TODO: Add support for leaves

		// Glass
		ItemStack blockGlass = new ItemStack(Block.glass);
		DecomposerRecipe
				.add(new DecomposerRecipe(blockGlass, new Chemical[] { this
						.molecule(EnumMolecule.siliconDioxide, 16) }));
		SynthesisRecipe
				.add(new SynthesisRecipe(blockGlass, true, 500, new Chemical[] {
						moleculeSiliconDioxide, null, moleculeSiliconDioxide,
						null, null, null, moleculeSiliconDioxide, null,
						moleculeSiliconDioxide }));

		// Lapis Lazuli Ore
		ItemStack blockOreLapis = new ItemStack(Block.oreLapis);
		DecomposerRecipe.add(new DecomposerRecipe(blockOreLapis,
				new Chemical[] { this.molecule(EnumMolecule.lazurite, 6),
						this.molecule(EnumMolecule.sodalite),
						this.molecule(EnumMolecule.noselite),
						this.molecule(EnumMolecule.calcite),
						this.molecule(EnumMolecule.pyrite) }));

		// Lapis Lazuli Block
		// TODO: Add support for Lapis Lazuli Block?

		// Cobweb
		ItemStack blockCobweb = new ItemStack(Block.web);
		DecomposerRecipe.add(new DecomposerRecipe(blockCobweb,
				new Chemical[] { this.molecule(EnumMolecule.fibroin) }));

		// Tall Grass
		ItemStack blockTallGrass = new ItemStack(Block.tallGrass, 1, 1);
		DecomposerRecipe.add(new DecomposerRecipeChance(blockTallGrass, 0.1F,
				new Chemical[] { new Molecule(EnumMolecule.afroman, 2) }));

		// Sandstone
		ItemStack blockSandStone = new ItemStack(Block.sandStone, 1, 0);
		ItemStack blockChiseledSandStone = new ItemStack(Block.sandStone, 1, 1);
		ItemStack blockSmoothSandStone = new ItemStack(Block.sandStone, 1, 2);
		DecomposerRecipe
				.add(new DecomposerRecipe(blockSandStone, new Chemical[] { this
						.molecule(EnumMolecule.siliconDioxide, 16) }));
		DecomposerRecipe
				.add(new DecomposerRecipe(blockChiseledSandStone,
						new Chemical[] { this.molecule(
								EnumMolecule.siliconDioxide, 16) }));
		DecomposerRecipe
				.add(new DecomposerRecipe(blockSmoothSandStone,
						new Chemical[] { this.molecule(
								EnumMolecule.siliconDioxide, 16) }));
		SynthesisRecipe.add(new SynthesisRecipe(blockSandStone, true, 20,
				new Chemical[] { null, null, null, null,
						this.molecule(EnumMolecule.siliconDioxide, 16), null,
						null, null, null }));
		SynthesisRecipe
				.add(new SynthesisRecipe(blockChiseledSandStone, true, 20,
						new Chemical[] { null, null, null, null, null, null,
								null,
								this.molecule(EnumMolecule.siliconDioxide, 16),
								null }));
		SynthesisRecipe.add(new SynthesisRecipe(blockSmoothSandStone, true, 20,
				new Chemical[] { null,
						this.molecule(EnumMolecule.siliconDioxide, 16), null,
						null, null, null, null, null, null }));

		// Wool
		ItemStack blockWool = new ItemStack(Block.cloth, 1, 0);
		ItemStack blockOrangeWool = new ItemStack(Block.cloth, 1, 1);
		ItemStack blockMagentaWool = new ItemStack(Block.cloth, 1, 2);
		ItemStack blockLightBlueWool = new ItemStack(Block.cloth, 1, 3);
		ItemStack blockYellowWool = new ItemStack(Block.cloth, 1, 4);
		ItemStack blockLimeWool = new ItemStack(Block.cloth, 1, 5);
		ItemStack blockPinkWool = new ItemStack(Block.cloth, 1, 6);
		ItemStack blockGrayWool = new ItemStack(Block.cloth, 1, 7);
		ItemStack blockLightGrayWool = new ItemStack(Block.cloth, 1, 8);
		ItemStack blockCyanWool = new ItemStack(Block.cloth, 1, 9);
		ItemStack blockPurpleWool = new ItemStack(Block.cloth, 1, 10);
		ItemStack blockBlueWool = new ItemStack(Block.cloth, 1, 11);
		ItemStack blockBrownWool = new ItemStack(Block.cloth, 1, 12);
		ItemStack blockGreenWool = new ItemStack(Block.cloth, 1, 13);
		ItemStack blockRedWool = new ItemStack(Block.cloth, 1, 14);
		ItemStack blockBlackWool = new ItemStack(Block.cloth, 1, 15);
		DecomposerRecipe.add(new DecomposerRecipeChance(blockWool, 0.6F,
				new Chemical[] { this.molecule(EnumMolecule.glycine),
						this.molecule(EnumMolecule.whitePigment) }));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockOrangeWool, 0.6F,
				new Chemical[] { this.molecule(EnumMolecule.glycine),
						this.molecule(EnumMolecule.orangePigment) }));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockMagentaWool, 0.6F,
				new Chemical[] { this.molecule(EnumMolecule.glycine),
						this.molecule(EnumMolecule.lightbluePigment),
						this.molecule(EnumMolecule.redPigment) }));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockLightBlueWool,
				0.6F, new Chemical[] { this.molecule(EnumMolecule.glycine),
						this.molecule(EnumMolecule.lightbluePigment) }));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockYellowWool, 0.6F,
				new Chemical[] { this.molecule(EnumMolecule.glycine),
						this.molecule(EnumMolecule.yellowPigment) }));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockLimeWool, 0.6F,
				new Chemical[] { this.molecule(EnumMolecule.glycine),
						this.molecule(EnumMolecule.limePigment) }));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockPinkWool, 0.6F,
				new Chemical[] { this.molecule(EnumMolecule.glycine),
						this.molecule(EnumMolecule.redPigment),
						this.molecule(EnumMolecule.whitePigment) }));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockGrayWool, 0.6F,
				new Chemical[] { this.molecule(EnumMolecule.glycine),
						this.molecule(EnumMolecule.whitePigment),
						this.molecule(EnumMolecule.blackPigment, 2) }));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockLightGrayWool,
				0.6F, new Chemical[] { this.molecule(EnumMolecule.glycine),
						this.molecule(EnumMolecule.whitePigment),
						this.molecule(EnumMolecule.blackPigment) }));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockCyanWool, 0.6F,
				new Chemical[] { this.molecule(EnumMolecule.glycine),
						this.molecule(EnumMolecule.lightbluePigment),
						this.molecule(EnumMolecule.whitePigment) }));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockPurpleWool, 0.6F,
				new Chemical[] { this.molecule(EnumMolecule.glycine),
						this.molecule(EnumMolecule.purplePigment) }));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockBlueWool, 0.6F,
				new Chemical[] { this.molecule(EnumMolecule.glycine),
						this.molecule(EnumMolecule.lazurite) }));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockBrownWool, 0.6F,
				new Chemical[] { this.molecule(EnumMolecule.glycine),
						this.molecule(EnumMolecule.tannicacid) }));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockGreenWool, 0.6F,
				new Chemical[] { this.molecule(EnumMolecule.glycine),
						this.molecule(EnumMolecule.greenPigment) }));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockRedWool, 0.6F,
				new Chemical[] { this.molecule(EnumMolecule.glycine),
						this.molecule(EnumMolecule.redPigment) }));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockBlackWool, 0.6F,
				new Chemical[] { this.molecule(EnumMolecule.glycine),
						this.molecule(EnumMolecule.blackPigment) }));
		SynthesisRecipe.add(new SynthesisRecipe(blockWool, false, 50,
				new Chemical[] { this.molecule(EnumMolecule.glycine),
						this.molecule(EnumMolecule.whitePigment) }));
		SynthesisRecipe.add(new SynthesisRecipe(blockOrangeWool, false, 50,
				new Chemical[] { this.molecule(EnumMolecule.glycine),
						this.molecule(EnumMolecule.orangePigment) }));
		SynthesisRecipe.add(new SynthesisRecipe(blockMagentaWool, false, 50,
				new Chemical[] { this.molecule(EnumMolecule.glycine),
						this.molecule(EnumMolecule.lightbluePigment),
						this.molecule(EnumMolecule.redPigment) }));
		SynthesisRecipe.add(new SynthesisRecipe(blockLightBlueWool, false, 50,
				new Chemical[] { this.molecule(EnumMolecule.glycine),
						this.molecule(EnumMolecule.lightbluePigment) }));
		SynthesisRecipe.add(new SynthesisRecipe(blockYellowWool, false, 50,
				new Chemical[] { this.molecule(EnumMolecule.glycine),
						this.molecule(EnumMolecule.yellowPigment) }));
		SynthesisRecipe.add(new SynthesisRecipe(blockLimeWool, false, 50,
				new Chemical[] { this.molecule(EnumMolecule.glycine),
						this.molecule(EnumMolecule.limePigment) }));
		SynthesisRecipe.add(new SynthesisRecipe(blockPinkWool, false, 50,
				new Chemical[] { this.molecule(EnumMolecule.glycine),
						this.molecule(EnumMolecule.redPigment),
						this.molecule(EnumMolecule.whitePigment) }));
		SynthesisRecipe.add(new SynthesisRecipe(blockGrayWool, false, 50,
				new Chemical[] { this.molecule(EnumMolecule.glycine),
						this.molecule(EnumMolecule.whitePigment),
						this.molecule(EnumMolecule.blackPigment, 2) }));
		SynthesisRecipe.add(new SynthesisRecipe(blockLightGrayWool, false, 50,
				new Chemical[] { this.molecule(EnumMolecule.glycine),
						this.molecule(EnumMolecule.whitePigment),
						this.molecule(EnumMolecule.blackPigment) }));
		SynthesisRecipe.add(new SynthesisRecipe(blockCyanWool, false, 50,
				new Chemical[] { this.molecule(EnumMolecule.glycine),
						this.molecule(EnumMolecule.lightbluePigment),
						this.molecule(EnumMolecule.whitePigment) }));
		SynthesisRecipe.add(new SynthesisRecipe(blockPurpleWool, false, 50,
				new Chemical[] { this.molecule(EnumMolecule.glycine),
						this.molecule(EnumMolecule.purplePigment) }));
		SynthesisRecipe.add(new SynthesisRecipe(blockBlueWool, false, 50,
				new Chemical[] { this.molecule(EnumMolecule.glycine),
						this.molecule(EnumMolecule.lazurite) }));
		SynthesisRecipe.add(new SynthesisRecipe(blockGreenWool, false, 50,
				new Chemical[] { this.molecule(EnumMolecule.glycine),
						this.molecule(EnumMolecule.greenPigment) }));
		SynthesisRecipe.add(new SynthesisRecipe(blockRedWool, false, 50,
				new Chemical[] { this.molecule(EnumMolecule.glycine),
						this.molecule(EnumMolecule.redPigment) }));
		SynthesisRecipe.add(new SynthesisRecipe(blockBlackWool, false, 50,
				new Chemical[] { this.molecule(EnumMolecule.glycine),
						this.molecule(EnumMolecule.blackPigment) }));

		// Flowers
		// TODO: Add support for Rose
		ItemStack blockPlantYellow = new ItemStack(Block.plantYellow);
		DecomposerRecipe.add(new DecomposerRecipeChance(blockPlantYellow, 0.3F,
				new Chemical[] { new Molecule(EnumMolecule.shikimicAcid, 2) }));

		// Mushrooms
		ItemStack blockMushroomBrown = new ItemStack(Block.mushroomBrown);
		ItemStack blockMushroomRed = new ItemStack(Block.mushroomRed);
		DecomposerRecipe.add(new DecomposerRecipe(blockMushroomBrown,
				new Chemical[] { this.molecule(EnumMolecule.psilocybin),
						this.molecule(EnumMolecule.water, 2) }));
		DecomposerRecipe.add(new DecomposerRecipe(blockMushroomRed,
				new Chemical[] { this.molecule(EnumMolecule.pantherine),
						this.molecule(EnumMolecule.water, 2) }));

		// Block of Gold
		DecomposerRecipe.add(new DecomposerRecipe(
				new ItemStack(Block.blockGold), new Chemical[] { this.element(
						EnumElement.Au, 144) }));

		// Block of Iron
		DecomposerRecipe.add(new DecomposerRecipe(
				new ItemStack(Block.blockIron), new Chemical[] { this.element(
						EnumElement.Fe, 144) }));

		// Slabs
		// TODO: Add support for slabs?

		// TNT
		ItemStack blockTnt = new ItemStack(Block.tnt);
		DecomposerRecipe.add(new DecomposerRecipe(blockTnt,
				new Chemical[] { this.molecule(EnumMolecule.tnt) }));
		SynthesisRecipe.add(new SynthesisRecipe(blockTnt, false, 1000,
				new Chemical[] { this.molecule(EnumMolecule.tnt) }));

		// Obsidian
		ItemStack blockObsidian = new ItemStack(Block.obsidian);
		DecomposerRecipe.add(new DecomposerRecipe(blockObsidian,
				new Chemical[] {
						this.molecule(EnumMolecule.siliconDioxide, 16),
						this.molecule(EnumMolecule.magnesiumOxide, 8) }));
		SynthesisRecipe.add(new SynthesisRecipe(blockObsidian, true, 1000,
				new Chemical[] { this.molecule(EnumMolecule.siliconDioxide, 4),
						this.molecule(EnumMolecule.siliconDioxide, 4),
						this.molecule(EnumMolecule.siliconDioxide, 4),
						this.molecule(EnumMolecule.magnesiumOxide, 2), null,
						this.molecule(EnumMolecule.siliconDioxide, 4),
						this.molecule(EnumMolecule.magnesiumOxide, 2),
						this.molecule(EnumMolecule.magnesiumOxide, 2),
						this.molecule(EnumMolecule.magnesiumOxide, 2) }));

		// Diamond Ore
		ItemStack blockOreDiamond = new ItemStack(Block.oreDiamond);
		DecomposerRecipe.add(new DecomposerRecipe(blockOreDiamond,
				new Chemical[] { this.molecule(EnumMolecule.fullrene, 6) }));

		// Block of Diamond
		ItemStack blockDiamond = new ItemStack(Block.blockDiamond);
		DecomposerRecipe.add(new DecomposerRecipe(blockDiamond,
				new Chemical[] { this.molecule(EnumMolecule.fullrene, 36) }));
		SynthesisRecipe.add(new SynthesisRecipe(blockDiamond, true, 120000,
				new Chemical[] { this.molecule(EnumMolecule.fullrene, 4),
						this.molecule(EnumMolecule.fullrene, 4),
						this.molecule(EnumMolecule.fullrene, 4),
						this.molecule(EnumMolecule.fullrene, 4),
						this.molecule(EnumMolecule.fullrene, 4),
						this.molecule(EnumMolecule.fullrene, 4),
						this.molecule(EnumMolecule.fullrene, 4),
						this.molecule(EnumMolecule.fullrene, 4),
						this.molecule(EnumMolecule.fullrene, 4) }));

		// Pressure Plate
		ItemStack blockPressurePlatePlanks = new ItemStack(
				Block.pressurePlatePlanks);
		DecomposerRecipe.add(new DecomposerRecipeChance(
				blockPressurePlatePlanks, 0.4F, new Chemical[] { this.molecule(
						EnumMolecule.cellulose, 4) }));

		// Redston Ore
		ItemStack blockOreRedstone = new ItemStack(Block.oreRedstone);
		DecomposerRecipe.add(new DecomposerRecipeChance(blockOreRedstone, 0.8F,
				new Chemical[] { this.molecule(EnumMolecule.iron3oxide, 9),
						this.element(EnumElement.Cu, 9) }));
		
		// Cactus
		ItemStack blockCactus = new ItemStack(Block.cactus);
		DecomposerRecipe.add(new DecomposerRecipe(blockCactus, new Chemical[] {
				this.molecule(EnumMolecule.mescaline),
				this.molecule(EnumMolecule.water, 20) }));
		SynthesisRecipe.add(new SynthesisRecipe(blockCactus, true, 200,
				new Chemical[] { this.molecule(EnumMolecule.water, 5), null,
						this.molecule(EnumMolecule.water, 5), null,
						this.molecule(EnumMolecule.mescaline), null,
						this.molecule(EnumMolecule.water, 5), null,
						this.molecule(EnumMolecule.water, 5) }));

		// Pumpkin
		ItemStack blockPumpkin = new ItemStack(Block.pumpkin);
		DecomposerRecipe.add(new DecomposerRecipe(blockPumpkin,
				new Chemical[] { this.molecule(EnumMolecule.cucurbitacin) }));
		SynthesisRecipe.add(new SynthesisRecipe(blockPumpkin, false, 400,
				new Chemical[] { this.molecule(EnumMolecule.cucurbitacin) }));

		// Netherrack
		ItemStack blockNetherrack = new ItemStack(Block.netherrack);
		DecomposerRecipe.add(new DecomposerRecipeSelect(blockNetherrack, 0.1F,
				new DecomposerRecipe[] {
						new DecomposerRecipe(new Chemical[] {
								this.element(EnumElement.Si, 2),
								this.element(EnumElement.O),
								this.element(EnumElement.Fe) }),
						new DecomposerRecipe(new Chemical[] {
								this.element(EnumElement.Si, 2),
								this.element(EnumElement.Ni),
								this.element(EnumElement.Tc) }),
						new DecomposerRecipe(new Chemical[] {
								this.element(EnumElement.Si, 3),
								this.element(EnumElement.Ti),
								this.element(EnumElement.Fe) }),
						new DecomposerRecipe(new Chemical[] {
								this.element(EnumElement.Si, 1),
								this.element(EnumElement.W, 4),
								this.element(EnumElement.Cr, 2) }),
						new DecomposerRecipe(new Chemical[] {
								this.element(EnumElement.Si, 10),
								this.element(EnumElement.W, 1),
								this.element(EnumElement.Zn, 8),
								this.element(EnumElement.Be, 4) }) }));

		// Water Bottle
		ItemStack itemPotion = new ItemStack(Item.potion, 1, 0);
		DecomposerRecipe.add(new DecomposerRecipe(itemPotion,
				new Chemical[] { this.molecule(EnumMolecule.water, 8) }));

		// Soul Sand
		ItemStack blockSlowSand = new ItemStack(Block.slowSand);
		DecomposerRecipe.add(new DecomposerRecipeSelect(blockSlowSand, 0.2F,
				new DecomposerRecipe[] {
						new DecomposerRecipe(new Chemical[] {
								this.element(EnumElement.Pb, 3),
								this.element(EnumElement.Be, 1),
								this.element(EnumElement.Si, 2),
								this.element(EnumElement.O) }),
						new DecomposerRecipe(new Chemical[] {
								this.element(EnumElement.Pb, 1),
								this.element(EnumElement.Si, 5),
								this.element(EnumElement.O, 2) }),
						new DecomposerRecipe(new Chemical[] {
								this.element(EnumElement.Si, 2),
								this.element(EnumElement.O) }),
						new DecomposerRecipe(new Chemical[] {
								this.element(EnumElement.Si, 6),
								this.element(EnumElement.O, 2) }),
						new DecomposerRecipe(new Chemical[] {
								this.element(EnumElement.Es, 1),
								this.element(EnumElement.O, 2) }) }));

		// Glowstone
		ItemStack blockGlowStone = new ItemStack(Block.glowStone);
		DecomposerRecipe.add(new DecomposerRecipe(blockGlowStone,
				new Chemical[] { this.element(EnumElement.P, 4) }));
		SynthesisRecipe.add(new SynthesisRecipe(blockGlowStone, true, 500,
				new Chemical[] { this.element(EnumElement.P), null,
						this.element(EnumElement.P),
						this.element(EnumElement.P), null,
						this.element(EnumElement.P), null, null, null }));

		// Glass Panes
		ItemStack blockThinGlass = new ItemStack(Block.thinGlass);
		DecomposerRecipe
				.add(new DecomposerRecipe(blockThinGlass, new Chemical[] { this
						.molecule(EnumMolecule.siliconDioxide, 1) }));
		SynthesisRecipe.add(new SynthesisRecipe(blockThinGlass, true, 50,
				new Chemical[] { null, null, null,
						this.molecule(EnumMolecule.siliconDioxide), null, null,
						null, null, null }));

		// Melon
		ItemStack blockMelon = new ItemStack(Block.melon);
		DecomposerRecipe.add(new DecomposerRecipe(blockMelon, new Chemical[] {
				this.molecule(EnumMolecule.cucurbitacin),
				this.molecule(EnumMolecule.asparticAcid),
				this.molecule(EnumMolecule.water, 16) }));

		// Mycelium
		ItemStack blockMycelium = new ItemStack(Block.mycelium);
		DecomposerRecipe.add(new DecomposerRecipeChance(blockMycelium, 0.09F,
				new Chemical[] { this.molecule(EnumMolecule.fingolimod) }));
		SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Block.mycelium,
				16), false, 300, new Chemical[] { this
				.molecule(EnumMolecule.fingolimod) }));

		// End Stone
		ItemStack blockWhiteStone = new ItemStack(Block.whiteStone);
		DecomposerRecipe.add(new DecomposerRecipeSelect(blockWhiteStone, 0.8F,
				new DecomposerRecipe[] {
						new DecomposerRecipe(new Chemical[] {
								this.element(EnumElement.Si, 2),
								this.element(EnumElement.O),
								this.element(EnumElement.H, 4),
								this.element(EnumElement.Li) }),
						new DecomposerRecipe(new Chemical[] { this
								.element(EnumElement.Es) }),
						new DecomposerRecipe(new Chemical[] { this
								.element(EnumElement.Pu) }),
						new DecomposerRecipe(new Chemical[] { this
								.element(EnumElement.Fr) }),
						new DecomposerRecipe(new Chemical[] { this
								.element(EnumElement.Nd) }),
						new DecomposerRecipe(new Chemical[] {
								this.element(EnumElement.Si, 2),
								this.element(EnumElement.O, 4) }),
						new DecomposerRecipe(new Chemical[] { this.element(
								EnumElement.H, 4) }),
						new DecomposerRecipe(new Chemical[] { this.element(
								EnumElement.Be, 8) }),
						new DecomposerRecipe(new Chemical[] { this.element(
								EnumElement.Li, 2) }),
						new DecomposerRecipe(new Chemical[] { this
								.element(EnumElement.Zr) }),
						new DecomposerRecipe(new Chemical[] { this
								.element(EnumElement.Na) }),
						new DecomposerRecipe(new Chemical[] { this
								.element(EnumElement.Rb) }),
						new DecomposerRecipe(new Chemical[] {
								this.element(EnumElement.Ga),
								this.element(EnumElement.As) }) }));

		// Emerald Ore
		ItemStack blockOreEmerald = new ItemStack(Block.oreEmerald);
		DecomposerRecipe.add(new DecomposerRecipe(blockOreEmerald,
				new Chemical[] { this.molecule(EnumMolecule.beryl, 6),
						this.element(EnumElement.Cr, 6),
						this.element(EnumElement.V, 6) }));

		// Emerald Block
		ItemStack blockEmerald = new ItemStack(Block.blockEmerald);
		SynthesisRecipe.add(new SynthesisRecipe(blockEmerald, true, 150000,
				new Chemical[] { this.element(EnumElement.Cr, 3),
						this.element(EnumElement.Cr, 3),
						this.element(EnumElement.Cr, 3),
						this.element(EnumElement.V, 9),
						this.molecule(EnumMolecule.beryl, 18),
						this.element(EnumElement.V, 9),
						this.element(EnumElement.Cr, 3),
						this.element(EnumElement.Cr, 3),
						this.element(EnumElement.Cr, 3) }));
		DecomposerRecipe.add(new DecomposerRecipe(blockEmerald, new Chemical[] {
				this.molecule(EnumMolecule.beryl, 18),
				this.element(EnumElement.Cr, 18),
				this.element(EnumElement.V, 18) }));

		// Section 2 - Items

		// Apple
		ItemStack itemAppleRed = new ItemStack(Item.appleRed);
		DecomposerRecipe.add(new DecomposerRecipe(itemAppleRed,
				new Chemical[] { this.molecule(EnumMolecule.malicAcid) }));
		SynthesisRecipe.add(new SynthesisRecipe(itemAppleRed, false, 400,
				new Chemical[] { this.molecule(EnumMolecule.malicAcid),
						this.molecule(EnumMolecule.water, 2) }));

		// Arrow
		ItemStack itemArrow = new ItemStack(Item.arrow);
		DecomposerRecipe.add(new DecomposerRecipe(itemArrow, new Chemical[] {
				this.element(EnumElement.Si), this.element(EnumElement.O, 2),
				this.element(EnumElement.N, 6) }));

		// Coal
		ItemStack itemCoal = new ItemStack(Item.coal);
		DecomposerRecipe.add(new DecomposerRecipe(itemCoal,
				new Chemical[] { this.element(EnumElement.C, 16) }));

		// Charcoal
		// Does 16 charcoal to 1 coal seem balanced? 
        ItemStack itemChar = new ItemStack(Item.coal,1,1);
		DecomposerRecipe.add(new DecomposerRecipe(itemChar, new Chemical[] { this.element(EnumElement.C, 30) }));
		
		// Diamond
		ItemStack itemDiamond = new ItemStack(Item.diamond);
		DecomposerRecipe.add(new DecomposerRecipe(itemDiamond,
				new Chemical[] { this.molecule(EnumMolecule.fullrene, 4) }));
		//	Polytool
		SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(MinechemItems.polytool), true, '\uea60',
				new Chemical[] { null, this.molecule(EnumMolecule.fullrene,64),
						null, this.molecule(EnumMolecule.fullrene,64), null,
						this.molecule(EnumMolecule.fullrene,64), null,
						this.molecule(EnumMolecule.fullrene,64), null }));
		SynthesisRecipe.add(new SynthesisRecipe(itemDiamond, true, '\uea60',
				new Chemical[] { null, this.molecule(EnumMolecule.fullrene),
						null, this.molecule(EnumMolecule.fullrene), null,
						this.molecule(EnumMolecule.fullrene), null,
						this.molecule(EnumMolecule.fullrene), null }));

		// Iron Ingot
		ItemStack itemIngotIron = new ItemStack(Item.ingotIron);
		DecomposerRecipe.add(new DecomposerRecipe(itemIngotIron,
				new Chemical[] { this.element(EnumElement.Fe, 16) }));
		SynthesisRecipe.add(new SynthesisRecipe(itemIngotIron, false, 1000,
				new Chemical[] { this.element(EnumElement.Fe, 16) }));

		// Gold Ingot
		ItemStack itemIngotGold = new ItemStack(Item.ingotGold);
		DecomposerRecipe.add(new DecomposerRecipe(itemIngotGold,
				new Chemical[] { this.element(EnumElement.Au, 16) }));
		SynthesisRecipe.add(new SynthesisRecipe(itemIngotGold, false, 1000,
				new Chemical[] { this.element(EnumElement.Au, 16) }));

		// Stick
		ItemStack itemStick = new ItemStack(Item.stick);
		DecomposerRecipe.add(new DecomposerRecipeChance(itemStick, 0.3F,
				new Chemical[] { this.molecule(EnumMolecule.cellulose) }));

		// String
		ItemStack itemSilk = new ItemStack(Item.silk);
		DecomposerRecipe.add(new DecomposerRecipeChance(itemSilk, 0.45F,
				new Chemical[] { this.molecule(EnumMolecule.serine),
						this.molecule(EnumMolecule.glycine),
						this.molecule(EnumMolecule.alinine) }));
		SynthesisRecipe.add(new SynthesisRecipe(itemSilk, true, 150,
				new Chemical[] { this.molecule(EnumMolecule.serine),
						this.molecule(EnumMolecule.glycine),
						this.molecule(EnumMolecule.alinine) }));

		// Feather
		ItemStack itemFeather = new ItemStack(Item.feather);
		DecomposerRecipe.add(new DecomposerRecipe(itemFeather, new Chemical[] {
				this.molecule(EnumMolecule.water, 8),
				this.element(EnumElement.N, 6) }));
		SynthesisRecipe.add(new SynthesisRecipe(itemFeather, true, 800,
				new Chemical[] { this.element(EnumElement.N),
						this.molecule(EnumMolecule.water, 2),
						this.element(EnumElement.N),
						this.element(EnumElement.N),
						this.molecule(EnumMolecule.water, 1),
						this.element(EnumElement.N),
						this.element(EnumElement.N),
						this.molecule(EnumMolecule.water, 5),
						this.element(EnumElement.N) }));

		// Gunpowder
		ItemStack itemGunpowder = new ItemStack(Item.gunpowder);
		DecomposerRecipe.add(new DecomposerRecipe(itemGunpowder,
				new Chemical[] { this.molecule(EnumMolecule.potassiumNitrate),
						this.element(EnumElement.S, 2),
						this.element(EnumElement.C) }));
		SynthesisRecipe.add(new SynthesisRecipe(itemGunpowder, true, 600,
				new Chemical[] { this.molecule(EnumMolecule.potassiumNitrate),
						this.element(EnumElement.C), null,
						this.element(EnumElement.S, 2), null, null, null, null,
						null }));

		// Bread
		ItemStack itemBread = new ItemStack(Item.bread);
		DecomposerRecipe.add(new DecomposerRecipeChance(itemBread, 0.1F,
				new Chemical[] { this.molecule(EnumMolecule.starch),
						this.molecule(EnumMolecule.sucrose) }));

		// Flint
		ItemStack itemFlint = new ItemStack(Item.flint);
		DecomposerRecipe.add(new DecomposerRecipeChance(itemFlint, 0.5F,
				new Chemical[] { this.molecule(EnumMolecule.siliconDioxide) }));
		SynthesisRecipe.add(new SynthesisRecipe(itemFlint, true, 100,
				new Chemical[] { null, moleculeSiliconDioxide, null,
						moleculeSiliconDioxide, moleculeSiliconDioxide,
						moleculeSiliconDioxide, null, null, null }));

		// Golden Apple
		ItemStack itemAppleGold = new ItemStack(Item.appleGold, 1, 0);
		DecomposerRecipe.add(new DecomposerRecipe(itemAppleGold,
				new Chemical[] { this.molecule(EnumMolecule.malicAcid),
						this.element(EnumElement.Au, 64) }));

		// Wooden Door
		ItemStack itemDoorWood = new ItemStack(Item.doorWood);
		DecomposerRecipe.add(new DecomposerRecipeChance(itemDoorWood, 0.4F,
				new Chemical[] { this.molecule(EnumMolecule.cellulose, 12) }));

		// Water Bucket
		ItemStack itemBucketWater = new ItemStack(Item.bucketWater);
		DecomposerRecipe.add(new DecomposerRecipe(itemBucketWater,
				new Chemical[] { this.molecule(EnumMolecule.water, 16),this.element(EnumElement.Fe, 48) }));

		// Redstone
		ItemStack itemRedstone = new ItemStack(Item.redstone);
		DecomposerRecipe.add(new DecomposerRecipeChance(itemRedstone, 0.42F,
				new Chemical[] { this.molecule(EnumMolecule.iron3oxide),
						this.element(EnumElement.Cu) }));
		SynthesisRecipe
				.add(new SynthesisRecipe(itemRedstone, true, 100,
						new Chemical[] { null, null,
								this.molecule(EnumMolecule.iron3oxide), null,
								this.element(EnumElement.Cu), null, null, null,
								null }));

		// Snowball
		ItemStack itemSnowball = new ItemStack(Item.snowball);
		DecomposerRecipe.add(new DecomposerRecipe(itemSnowball,
				new Chemical[] { this.molecule(EnumMolecule.water) }));
		SynthesisRecipe.add(new SynthesisRecipe(
				new ItemStack(Item.snowball, 5), true, 20, new Chemical[] {
						this.molecule(EnumMolecule.water), null,
						this.molecule(EnumMolecule.water), null,
						this.molecule(EnumMolecule.water), null,
						this.molecule(EnumMolecule.water), null,
						this.molecule(EnumMolecule.water) }));

		// Leather
		ItemStack itemLeather = new ItemStack(Item.leather);
		DecomposerRecipe.add(new DecomposerRecipeChance(itemLeather, 0.5F,
				new Chemical[] { this.molecule(EnumMolecule.arginine),
						this.molecule(EnumMolecule.glycine),
						this.molecule(EnumMolecule.keratin) }));
		SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Item.leather, 5),
				true, 700, new Chemical[] {
						this.molecule(EnumMolecule.arginine), null, null, null,
						this.molecule(EnumMolecule.keratin), null, null, null,
						this.molecule(EnumMolecule.glycine) }));

		// Brick
		ItemStack itemBrick = new ItemStack(Item.brick);
		DecomposerRecipe.add(new DecomposerRecipeChance(itemBrick, 0.5F,
				new Chemical[] { this.molecule(EnumMolecule.kaolinite) }));
		SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Item.brick, 8),
				true, 400, new Chemical[] {
						this.molecule(EnumMolecule.kaolinite),
						this.molecule(EnumMolecule.kaolinite), null,
						this.molecule(EnumMolecule.kaolinite),
						this.molecule(EnumMolecule.kaolinite), null }));

		// Clay
		ItemStack itemClay = new ItemStack(Item.clay);
		DecomposerRecipe.add(new DecomposerRecipeChance(itemClay, 0.3F,
				new Chemical[] { this.molecule(EnumMolecule.kaolinite) }));
		SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Item.clay, 12),
				false, 100, new Chemical[] { this
						.molecule(EnumMolecule.kaolinite) }));

		// Reed
		ItemStack itemReed = new ItemStack(Item.reed);
		DecomposerRecipe.add(new DecomposerRecipeChance(itemReed, 0.65F,
				new Chemical[] { this.molecule(EnumMolecule.sucrose),
						this.element(EnumElement.H, 2),
						this.element(EnumElement.O) }));

		// Paper
		ItemStack itemPaper = new ItemStack(Item.paper);
		DecomposerRecipe.add(new DecomposerRecipeChance(itemPaper, 0.25F,
				new Chemical[] { this.molecule(EnumMolecule.cellulose) }));
		SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Item.paper, 16),
				true, 150, new Chemical[] { null,
						this.molecule(EnumMolecule.cellulose), null, null,
						this.molecule(EnumMolecule.cellulose), null, null,
						this.molecule(EnumMolecule.cellulose), null }));

		// Slimeball
		ItemStack itemSlimeBall = new ItemStack(Item.slimeBall);
		DecomposerRecipe.add(new DecomposerRecipeSelect(itemSlimeBall, 0.9F,
				new DecomposerRecipe[] {
						new DecomposerRecipe(new Chemical[] { this
								.molecule(EnumMolecule.polycyanoacrylate) }),
						new DecomposerRecipe(new Chemical[] { this
								.element(EnumElement.Hg) }),
						new DecomposerRecipe(new Chemical[] { this.molecule(
								EnumMolecule.water, 10) }) }));

		// Glowstone Dust
		ItemStack itemGlowstone = new ItemStack(Item.glowstone);
		DecomposerRecipe.add(new DecomposerRecipe(itemGlowstone,
				new Chemical[] { this.element(EnumElement.P) }));

		// Dyes
		ItemStack itemDyePowderBlack = new ItemStack(Item.dyePowder, 1, 0);
		ItemStack itemDyePowderRed = new ItemStack(Item.dyePowder, 1, 1);
		ItemStack itemDyePowderGreen = new ItemStack(Item.dyePowder, 1, 2);
		ItemStack itemDyePowderBrown = new ItemStack(Item.dyePowder, 1, 3);
		ItemStack itemDyePowderBlue = new ItemStack(Item.dyePowder, 1, 4);
		ItemStack itemDyePowderPurple = new ItemStack(Item.dyePowder, 1, 5);
		ItemStack itemDyePowderCyan = new ItemStack(Item.dyePowder, 1, 6);
		ItemStack itemDyePowderLightGray = new ItemStack(Item.dyePowder, 1, 7);
		ItemStack itemDyePowderGray = new ItemStack(Item.dyePowder, 1, 8);
		ItemStack itemDyePowderPink = new ItemStack(Item.dyePowder, 1, 9);
		ItemStack itemDyePowderLime = new ItemStack(Item.dyePowder, 1, 10);
		ItemStack itemDyePowderYellow = new ItemStack(Item.dyePowder, 1, 11);
		ItemStack itemDyePowderLightBlue = new ItemStack(Item.dyePowder, 1, 12);
		ItemStack itemDyePowderMagenta = new ItemStack(Item.dyePowder, 1, 13);
		ItemStack itemDyePowderOrange = new ItemStack(Item.dyePowder, 1, 14);
		ItemStack itemDyePowderWhite = new ItemStack(Item.dyePowder, 1, 15);
		DecomposerRecipe.add(new DecomposerRecipe(itemDyePowderBlack,
				new Chemical[] { this.molecule(EnumMolecule.blackPigment) }));
		DecomposerRecipe.add(new DecomposerRecipe(itemDyePowderRed,
				new Chemical[] { this.molecule(EnumMolecule.redPigment) }));
		DecomposerRecipe.add(new DecomposerRecipe(itemDyePowderGreen,
				new Chemical[] { this.molecule(EnumMolecule.greenPigment) }));
		DecomposerRecipe.add(new DecomposerRecipeChance(itemDyePowderBrown,
				0.4F, new Chemical[] { this.molecule(EnumMolecule.theobromine),
						this.molecule(EnumMolecule.tannicacid) }));
		DecomposerRecipe.add(new DecomposerRecipe(itemDyePowderBlue,
				new Chemical[] { this.molecule(EnumMolecule.lazurite) }));
		DecomposerRecipe.add(new DecomposerRecipe(itemDyePowderPurple,
				new Chemical[] { this.molecule(EnumMolecule.purplePigment) }));
		DecomposerRecipe.add(new DecomposerRecipe(itemDyePowderCyan,
				new Chemical[] { this.molecule(EnumMolecule.lightbluePigment),
						this.molecule(EnumMolecule.whitePigment) }));
		DecomposerRecipe.add(new DecomposerRecipe(itemDyePowderLightGray,
				new Chemical[] { this.molecule(EnumMolecule.whitePigment),
						this.molecule(EnumMolecule.blackPigment) }));
		DecomposerRecipe.add(new DecomposerRecipe(itemDyePowderGray,
				new Chemical[] { this.molecule(EnumMolecule.whitePigment),
						this.molecule(EnumMolecule.blackPigment, 2) }));
		DecomposerRecipe.add(new DecomposerRecipe(itemDyePowderPink,
				new Chemical[] { this.molecule(EnumMolecule.redPigment),
						this.molecule(EnumMolecule.whitePigment) }));
		DecomposerRecipe.add(new DecomposerRecipe(itemDyePowderLime,
				new Chemical[] { this.molecule(EnumMolecule.limePigment) }));
		DecomposerRecipe.add(new DecomposerRecipe(itemDyePowderYellow,
				new Chemical[] { this.molecule(EnumMolecule.yellowPigment) }));
		DecomposerRecipe
				.add(new DecomposerRecipe(itemDyePowderLightBlue,
						new Chemical[] { this
								.molecule(EnumMolecule.lightbluePigment) }));
		DecomposerRecipe.add(new DecomposerRecipe(itemDyePowderMagenta,
				new Chemical[] { this.molecule(EnumMolecule.lightbluePigment),
						this.molecule(EnumMolecule.redPigment) }));
		DecomposerRecipe.add(new DecomposerRecipe(itemDyePowderOrange,
				new Chemical[] { this.molecule(EnumMolecule.orangePigment) }));
		DecomposerRecipe.add(new DecomposerRecipe(itemDyePowderWhite,
				new Chemical[] { this.molecule(EnumMolecule.whitePigment) }));
		SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderBlack, false, 50,
				new Chemical[] { this.molecule(EnumMolecule.blackPigment) }));
		SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderRed, false, 50,
				new Chemical[] { this.molecule(EnumMolecule.redPigment) }));
		SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderGreen, false, 50,
				new Chemical[] { this.molecule(EnumMolecule.greenPigment) }));
		SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderBrown, false, 400,
				new Chemical[] { this.molecule(EnumMolecule.theobromine) }));
		SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderBlue, false, 50,
				new Chemical[] { this.molecule(EnumMolecule.lazurite) }));
		SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderPurple, false, 50,
				new Chemical[] { this.molecule(EnumMolecule.purplePigment) }));
		SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderCyan, false, 50,
				new Chemical[] { this.molecule(EnumMolecule.lightbluePigment),
						this.molecule(EnumMolecule.whitePigment) }));
		SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderLightGray, false,
				50, new Chemical[] { this.molecule(EnumMolecule.whitePigment),
						this.molecule(EnumMolecule.blackPigment) }));
		SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderGray, false, 50,
				new Chemical[] { this.molecule(EnumMolecule.whitePigment),
						this.molecule(EnumMolecule.blackPigment, 2) }));
		SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderPink, false, 50,
				new Chemical[] { this.molecule(EnumMolecule.redPigment),
						this.molecule(EnumMolecule.whitePigment) }));
		SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderLime, false, 50,
				new Chemical[] { this.molecule(EnumMolecule.limePigment) }));
		SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderYellow, false, 50,
				new Chemical[] { this.molecule(EnumMolecule.yellowPigment) }));
		SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderLightBlue, false,
				50, new Chemical[] { this
						.molecule(EnumMolecule.lightbluePigment) }));
		SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderMagenta, false,
				50, new Chemical[] {
						this.molecule(EnumMolecule.lightbluePigment),
						this.molecule(EnumMolecule.redPigment) }));
		SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderOrange, false, 50,
				new Chemical[] { this.molecule(EnumMolecule.orangePigment) }));
		SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderWhite, false, 50,
				new Chemical[] { this.molecule(EnumMolecule.whitePigment) }));

		// Bone 
		ItemStack itemBone = new ItemStack(Item.bone);
		DecomposerRecipe
				.add(new DecomposerRecipe(itemBone, new Chemical[] { this
						.molecule(EnumMolecule.hydroxylapatite) }));
		SynthesisRecipe
				.add(new SynthesisRecipe(itemBone, false, 100,
						new Chemical[] { this
								.molecule(EnumMolecule.hydroxylapatite) }));

		// Sugar
		ItemStack itemSugar = new ItemStack(Item.sugar);
		DecomposerRecipe.add(new DecomposerRecipeChance(itemSugar, 0.75F,
				new Chemical[] { this.molecule(EnumMolecule.sucrose) }));
		SynthesisRecipe.add(new SynthesisRecipe(itemSugar, false, 400,
				new Chemical[] { this.molecule(EnumMolecule.sucrose) }));

		// Melon
		ItemStack itemMelon = new ItemStack(Item.melon);
		DecomposerRecipe.add(new DecomposerRecipe(itemMelon,
				new Chemical[] { this.molecule(EnumMolecule.water) }));

		// Cooked Chicken
        ItemStack itemChickenCooked = new ItemStack(Item.chickenCooked);
        DecomposerRecipe.add(new DecomposerRecipe(itemChickenCooked, new Chemical[] { this.element(EnumElement.K), this.element(EnumElement.Na), this.element(EnumElement.C, 2) }));
        SynthesisRecipe.add(new SynthesisRecipe(itemChickenCooked, true, 5000, new Chemical[] { this.element(EnumElement.K, 16), this.element(EnumElement.Na, 16), this.element(EnumElement.C, 16) }));
		
        // Rotten Flesh
		ItemStack itemRottenFlesh = new ItemStack(Item.rottenFlesh);
		DecomposerRecipe.add(new DecomposerRecipeChance(itemRottenFlesh, 0.05F,
				new Chemical[] { new Molecule(EnumMolecule.nod, 1) }));
		
		// Enderpearl
		ItemStack itemEnderPearl = new ItemStack(Item.enderPearl);
		DecomposerRecipe.add(new DecomposerRecipe(itemEnderPearl,
				new Chemical[] { this.element(EnumElement.Es),
						this.molecule(EnumMolecule.calciumCarbonate, 8) }));
		SynthesisRecipe.add(new SynthesisRecipe(itemEnderPearl, true, 5000,
				new Chemical[] { this.molecule(EnumMolecule.calciumCarbonate),
						this.molecule(EnumMolecule.calciumCarbonate),
						this.molecule(EnumMolecule.calciumCarbonate),
						this.molecule(EnumMolecule.calciumCarbonate),
						this.element(EnumElement.Es),
						this.molecule(EnumMolecule.calciumCarbonate),
						this.molecule(EnumMolecule.calciumCarbonate),
						this.molecule(EnumMolecule.calciumCarbonate),
						this.molecule(EnumMolecule.calciumCarbonate) }));

		// Blaze Rod
		ItemStack itemBlazeRod = new ItemStack(Item.blazeRod);
		DecomposerRecipe.add(new DecomposerRecipe(itemBlazeRod,
				new Chemical[] { this.element(EnumElement.Pu, 3) }));
		SynthesisRecipe.add(new SynthesisRecipe(itemBlazeRod, true, 15000,
				new Chemical[] { this.element(EnumElement.Pu), null, null,
						this.element(EnumElement.Pu), null, null,
						this.element(EnumElement.Pu), null, null }));

		// Ghast Tear
		ItemStack itemGhastTear = new ItemStack(Item.ghastTear);
		DecomposerRecipe.add(new DecomposerRecipe(itemGhastTear,
				new Chemical[] { this.element(EnumElement.Yb, 4),
						this.element(EnumElement.No, 4) }));
		SynthesisRecipe.add(new SynthesisRecipe(itemGhastTear, true, 15000,
				new Chemical[] { this.element(EnumElement.Yb),
						this.element(EnumElement.Yb),
						this.element(EnumElement.No), null,
						this.element(EnumElement.Yb, 2),
						this.element(EnumElement.No, 2), null,
						this.element(EnumElement.No), null }));

		// Nether Wart
		ItemStack itemNetherStalkSeeds = new ItemStack(Item.netherStalkSeeds);
		DecomposerRecipe.add(new DecomposerRecipeChance(itemNetherStalkSeeds,
				0.5F, new Chemical[] { this.molecule(EnumMolecule.coke) }));

		// Spider Eye
		ItemStack itemSpiderEye = new ItemStack(Item.spiderEye);
		DecomposerRecipe.add(new DecomposerRecipeChance(itemSpiderEye, 0.2F,
				new Chemical[] { this.molecule(EnumMolecule.ttx) }));
		SynthesisRecipe.add(new SynthesisRecipe(itemSpiderEye, true, 2000,
				new Chemical[] { this.element(EnumElement.C), null, null, null,
						this.molecule(EnumMolecule.ttx), null, null, null,
						this.element(EnumElement.C) }));

		// Fermented Spider Eye
		ItemStack itemFermentedSpiderEye = new ItemStack(
				Item.fermentedSpiderEye);
		DecomposerRecipe.add(new DecomposerRecipe(itemFermentedSpiderEye,
				new Chemical[] { this.element(EnumElement.Po),
						this.molecule(EnumMolecule.ethanol) }));

		// Blaze Powder
		ItemStack itemBlazePowder = new ItemStack(Item.blazePowder);
		DecomposerRecipe.add(new DecomposerRecipe(itemBlazePowder,
				new Chemical[] { this.element(EnumElement.Pu) }));

		// Magma Cream
		ItemStack itemMagmaCream = new ItemStack(Item.magmaCream);
		DecomposerRecipe.add(new DecomposerRecipe(itemMagmaCream,
				new Chemical[] { this.element(EnumElement.Hg),
						this.element(EnumElement.Pu),
						this.molecule(EnumMolecule.polycyanoacrylate, 3) }));
		SynthesisRecipe.add(new SynthesisRecipe(itemMagmaCream, true, 5000,
				new Chemical[] { null, this.element(EnumElement.Pu), null,
						this.molecule(EnumMolecule.polycyanoacrylate),
						this.element(EnumElement.Hg),
						this.molecule(EnumMolecule.polycyanoacrylate), null,
						this.molecule(EnumMolecule.polycyanoacrylate), null }));

		// Glistering Melon
		ItemStack itemSpeckledMelon = new ItemStack(Item.speckledMelon);
		DecomposerRecipe.add(new DecomposerRecipe(itemSpeckledMelon,
				new Chemical[] { this.molecule(EnumMolecule.water, 4),
						this.molecule(EnumMolecule.whitePigment),
						this.element(EnumElement.Au, 1) }));

		// Emerald
		ItemStack itemEmerald = new ItemStack(Item.emerald);
		DecomposerRecipe.add(new DecomposerRecipe(itemEmerald,
				new Chemical[] { this.molecule(EnumMolecule.beryl, 2),
						this.element(EnumElement.Cr, 2),
						this.element(EnumElement.V, 2) }));
		SynthesisRecipe.add(new SynthesisRecipe(itemEmerald, true, 80000,
				new Chemical[] { null, this.element(EnumElement.Cr), null,
						this.element(EnumElement.V),
						this.molecule(EnumMolecule.beryl, 2),
						this.element(EnumElement.V), null,
						this.element(EnumElement.Cr), null }));

		// Carrot
		ItemStack itemCarrot = new ItemStack(Item.carrot);
		DecomposerRecipe.add(new DecomposerRecipe(itemCarrot,
				new Chemical[] { this.molecule(EnumMolecule.ret) }));

		// Potato
		ItemStack itemPotato = new ItemStack(Item.potato);
		DecomposerRecipe.add(new DecomposerRecipeChance(itemPotato, 0.4F,
				new Chemical[] { this.molecule(EnumMolecule.water, 8),
						this.element(EnumElement.K, 2),
						this.molecule(EnumMolecule.cellulose) }));

		// Golden Carrot
		ItemStack itemGoldenCarrot = new ItemStack(Item.goldenCarrot);
		DecomposerRecipe.add(new DecomposerRecipe(itemGoldenCarrot,
				new Chemical[] { this.molecule(EnumMolecule.ret),
						this.element(EnumElement.Au, 4) }));

		// Nether Star
		ItemStack itemNetherStar = new ItemStack(Item.netherStar);
		DecomposerRecipe.add(new DecomposerRecipe(itemNetherStar,
				new Chemical[] { this.element(EnumElement.Cn, 16),
						elementHydrogen, elementHydrogen, elementHydrogen,
						elementHelium, elementHelium, elementHelium,
						elementCarbon, elementCarbon }));
		SynthesisRecipe.add(new SynthesisRecipe(itemNetherStar, true, 500000,
				new Chemical[] { elementHelium, elementHelium, elementHelium,
						elementCarbon, this.element(EnumElement.Cn, 16),
						elementHelium, elementHydrogen, elementHydrogen,
						elementHydrogen }));
		
		// Nether Quartz
		ItemStack itemNetherQuartz = new ItemStack(Item.netherQuartz);
		DecomposerRecipe.add(new DecomposerRecipe(itemNetherQuartz,
				new Chemical[] { this.molecule(EnumMolecule.siliconDioxide, 4),
						this.molecule(EnumMolecule.galliumarsenide, 1) }));

		// Music Records
		ItemStack itemRecord13 = new ItemStack(Item.record13);
		ItemStack itemRecordCat = new ItemStack(Item.recordCat);
		ItemStack itemRecordFar = new ItemStack(Item.recordFar);
		ItemStack itemRecordMall = new ItemStack(Item.recordMall);
		ItemStack itemRecordMellohi = new ItemStack(Item.recordMellohi);
		ItemStack itemRecordStal = new ItemStack(Item.recordStal);
		ItemStack itemRecordStrad = new ItemStack(Item.recordStrad);
		ItemStack itemRecordWard = new ItemStack(Item.recordWard);
		ItemStack itemRecordChirp = new ItemStack(Item.recordChirp);
		DecomposerRecipe.add(new DecomposerRecipe(itemRecord13,
				new Chemical[] { moleculePolyvinylChloride }));
		DecomposerRecipe.add(new DecomposerRecipe(itemRecordCat,
				new Chemical[] { moleculePolyvinylChloride }));
		DecomposerRecipe.add(new DecomposerRecipe(itemRecordFar,
				new Chemical[] { moleculePolyvinylChloride }));
		DecomposerRecipe.add(new DecomposerRecipe(itemRecordMall,
				new Chemical[] { moleculePolyvinylChloride }));
		DecomposerRecipe.add(new DecomposerRecipe(itemRecordMellohi,
				new Chemical[] { moleculePolyvinylChloride }));
		DecomposerRecipe.add(new DecomposerRecipe(itemRecordStal,
				new Chemical[] { moleculePolyvinylChloride }));
		DecomposerRecipe.add(new DecomposerRecipe(itemRecordStrad,
				new Chemical[] { moleculePolyvinylChloride }));
		DecomposerRecipe.add(new DecomposerRecipe(itemRecordWard,
				new Chemical[] { moleculePolyvinylChloride }));
		DecomposerRecipe.add(new DecomposerRecipe(itemRecordChirp,
				new Chemical[] { moleculePolyvinylChloride }));
		SynthesisRecipe.add(new SynthesisRecipe(itemRecord13, true, 1000,
				new Chemical[] { moleculePolyvinylChloride, null, null, null,
						null, null, null, null, null }));
		SynthesisRecipe.add(new SynthesisRecipe(itemRecordCat, true, 1000,
				new Chemical[] { null, moleculePolyvinylChloride, null, null,
						null, null, null, null, null }));
		SynthesisRecipe.add(new SynthesisRecipe(itemRecordFar, true, 1000,
				new Chemical[] { null, null, moleculePolyvinylChloride, null,
						null, null, null, null, null }));
		SynthesisRecipe.add(new SynthesisRecipe(itemRecordMall, true, 1000,
				new Chemical[] { null, null, null, moleculePolyvinylChloride,
						null, null, null, null, null }));
		SynthesisRecipe.add(new SynthesisRecipe(itemRecordMellohi, true, 1000,
				new Chemical[] { null, null, null, null,
						moleculePolyvinylChloride, null, null, null, null }));
		SynthesisRecipe.add(new SynthesisRecipe(itemRecordStal, true, 1000,
				new Chemical[] { null, null, null, null, null,
						moleculePolyvinylChloride, null, null, null }));
		SynthesisRecipe.add(new SynthesisRecipe(itemRecordStrad, true, 1000,
				new Chemical[] { null, null, null, null, null, null,
						moleculePolyvinylChloride, null, null }));
		SynthesisRecipe.add(new SynthesisRecipe(itemRecordWard, true, 1000,
				new Chemical[] { null, null, null, null, null, null, null,
						moleculePolyvinylChloride, null }));
		SynthesisRecipe.add(new SynthesisRecipe(itemRecordChirp, true, 1000,
				new Chemical[] { null, null, null, null, null, null, null,
						null, moleculePolyvinylChloride }));
    }
    
    public void RegisterRecipes() {    	
    	this.registerVanillaChemicalRecipes();
    	
		ItemStack blockGlass = new ItemStack(Block.glass);
		ItemStack blockThinGlass = new ItemStack(Block.thinGlass);
        ItemStack blockIron = new ItemStack(Block.blockIron);
        ItemStack itemIngotIron = new ItemStack(Item.ingotIron);        
        ItemStack itemRedstone = new ItemStack(Item.redstone);
        ItemStack minechemItemsAtomicManipulator = new ItemStack(MinechemItems.atomicManipulator);
        ItemStack mineChemItemsTestTube = new ItemStack(MinechemItems.testTube, 16);
        ItemStack moleculePolyvinylChloride = new ItemStack(MinechemItems.molecule, 1, EnumMolecule.polyvinylChloride.ordinal());
        
        GameRegistry.addRecipe(mineChemItemsTestTube, new Object[] { " G ", " G ", " G ", Character.valueOf('G'), blockGlass });
        GameRegistry.addRecipe(MinechemItems.concaveLens, new Object[] { "G G", "GGG", "G G", Character.valueOf('G'), blockGlass });
        GameRegistry.addRecipe(MinechemItems.convexLens, new Object[] { " G ", "GGG", " G ", Character.valueOf('G'), blockGlass });
        GameRegistry.addRecipe(MinechemItems.microscopeLens, new Object[] { "A", "B", "A", Character.valueOf('A'), MinechemItems.convexLens, Character.valueOf('B'), MinechemItems.concaveLens });
        GameRegistry.addRecipe(new ItemStack(MinechemBlocks.microscope), new Object[] { " LI", " PI", "III", Character.valueOf('L'), MinechemItems.microscopeLens, Character.valueOf('P'), blockThinGlass, Character.valueOf('I'), itemIngotIron });
        GameRegistry.addRecipe(new ItemStack(MinechemBlocks.microscope), new Object[] { " LI", " PI", "III", Character.valueOf('L'), MinechemItems.microscopeLens, Character.valueOf('P'), blockThinGlass, Character.valueOf('I'), itemIngotIron });
        GameRegistry.addRecipe(new ItemStack(MinechemItems.atomicManipulator), new Object[] { "PPP", "PIP", "PPP", Character.valueOf('P'), new ItemStack(Block.pistonBase), Character.valueOf('I'), blockIron });
        GameRegistry.addRecipe(new ItemStack(MinechemBlocks.decomposer), new Object[] { "III", "IAI", "IRI", Character.valueOf('A'), minechemItemsAtomicManipulator, Character.valueOf('I'), itemIngotIron, Character.valueOf('R'), itemRedstone });
        GameRegistry.addRecipe(new ItemStack(MinechemBlocks.synthesis), new Object[] { "IRI", "IAI", "IDI", Character.valueOf('A'), minechemItemsAtomicManipulator, Character.valueOf('I'), itemIngotIron, Character.valueOf('R'), itemRedstone, Character.valueOf('D'), new ItemStack(Item.diamond) });
        GameRegistry.addRecipe(new ItemStack(MinechemBlocks.fusion, 16, 0), new Object[] { "ILI", "ILI", "ILI", Character.valueOf('I'), itemIngotIron, Character.valueOf('L'), ItemElement.createStackOf(EnumElement.Pb, 1) });
        GameRegistry.addRecipe(new ItemStack(MinechemBlocks.fusion, 16, 1), new Object[] { "IWI", "IBI", "IWI", Character.valueOf('I'), itemIngotIron, Character.valueOf('W'), ItemElement.createStackOf(EnumElement.W, 1), Character.valueOf('B'), ItemElement.createStackOf(EnumElement.Be, 1) });
        GameRegistry.addRecipe(MinechemItems.projectorLens, new Object[] { "ABA", Character.valueOf('A'), MinechemItems.concaveLens, Character.valueOf('B'), MinechemItems.convexLens });
        GameRegistry.addRecipe(new ItemStack(MinechemBlocks.blueprintProjector), new Object[] { " I ", "GPL", " I ", Character.valueOf('I'), itemIngotIron, Character.valueOf('P'), blockThinGlass, Character.valueOf('L'), MinechemItems.projectorLens, Character.valueOf('G'), new ItemStack(Block.redstoneLampIdle) });
        GameRegistry.addRecipe(new ItemStack(MinechemItems.hazmatFeet), new Object[] { "   ", "P P", "P P", Character.valueOf('P'), moleculePolyvinylChloride });
        GameRegistry.addRecipe(new ItemStack(MinechemItems.hazmatLegs), new Object[] { "PPP", "P P", "P P", Character.valueOf('P'), moleculePolyvinylChloride });
        GameRegistry.addRecipe(new ItemStack(MinechemItems.hazmatTorso), new Object[] { " P ", "PPP", "PPP", Character.valueOf('P'), moleculePolyvinylChloride });
        GameRegistry.addRecipe(new ItemStack(MinechemItems.hazmatHead), new Object[] { "PPP", "P P", "   ", Character.valueOf('P'), moleculePolyvinylChloride });
        GameRegistry.addRecipe(new ItemStack(MinechemBlocks.chemicalStorage), new Object[] { "LLL", "LCL", "LLL", Character.valueOf('L'), new ItemStack(MinechemItems.element, 1, EnumElement.Pb.ordinal()), Character.valueOf('C'), new ItemStack(Block.chest) });
        GameRegistry.addRecipe(new ItemStack(MinechemItems.IAintAvinit), new Object[] { "ZZZ", "ZSZ", " S ", Character.valueOf('Z'), new ItemStack(Item.ingotIron), Character.valueOf('S'), new ItemStack(Item.stick) });
        GameRegistry.addShapelessRecipe(new ItemStack(MinechemItems.journal), new Object[] { new ItemStack(Item.book), new ItemStack(MinechemItems.testTube) });
        GameRegistry.addShapelessRecipe(new ItemStack(MinechemItems.EmptyPillz,4), new Object[] { new ItemStack(Item.sugar), new ItemStack(Item.slimeBall), new ItemStack(Item.slimeBall) });
        //Fusion
        GameRegistry.addShapelessRecipe(ItemBlueprint.createItemStackFromBlueprint(MinechemBlueprint.fusion), new Object[] { new ItemStack(Item.paper), new ItemStack(Block.blockDiamond)});
        //Fission
        GameRegistry.addShapelessRecipe(ItemBlueprint.createItemStackFromBlueprint(MinechemBlueprint.fission), new Object[] { new ItemStack(Item.paper), new ItemStack(Item.diamond)});
		for (EnumElement element : EnumElement.values()) {
            GameRegistry.addShapelessRecipe(new ItemStack(MinechemItems.testTube), new Object[] { new ItemStack(MinechemItems.element, element.ordinal()) });
        }
        
        GameRegistry.addRecipe(new RecipeJournalCloning());
        
       
        



        // 
	this.addDecomposerRecipesFromMolecules();
        this.addSynthesisRecipesFromMolecules();
        this.addUnusedSynthesisRecipes();
        this.registerPoisonRecipes(EnumMolecule.poison);
        this.registerPoisonRecipes(EnumMolecule.sucrose);
        this.registerPoisonRecipes(EnumMolecule.psilocybin);
        this.registerPoisonRecipes(EnumMolecule.methamphetamine);
        this.registerPoisonRecipes(EnumMolecule.amphetamine);
        this.registerPoisonRecipes(EnumMolecule.pantherine);
        this.registerPoisonRecipes(EnumMolecule.ethanol);
        this.registerPoisonRecipes(EnumMolecule.penicillin);
        this.registerPoisonRecipes(EnumMolecule.testosterone);
        this.registerPoisonRecipes(EnumMolecule.xanax);
        this.registerPoisonRecipes(EnumMolecule.mescaline);
        this.registerPoisonRecipes(EnumMolecule.asprin);
        this.registerPoisonRecipes(EnumMolecule.sulfuricAcid);
        this.registerPoisonRecipes(EnumMolecule.ttx);
        this.registerPoisonRecipes(EnumMolecule.pal2);
        this.registerPoisonRecipes(EnumMolecule.nod);
        this.registerPoisonRecipes(EnumMolecule.afroman);
	this.registerPoisonRecipes(EnumMolecule.radchlor); // Whoa, oh, oh, oh, I'm radioactive, radioactive
        this.registerPoisonRecipes(EnumMolecule.redrocks);
	this.registerPoisonRecipes(EnumMolecule.coke);
	this.registerPoisonRecipes(EnumMolecule.theobromine); 
	this.registerPoisonRecipes(EnumMolecule.ctaxifolia); 
	this.registerPoisonRecipes(EnumMolecule.latropine);
	}

    private void addDecomposerRecipesFromMolecules() {
        EnumMolecule[] var1 = EnumMolecule.molecules;
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            EnumMolecule var4 = var1[var3];
            ArrayList var5 = var4.components();
            Chemical[] var6 = (Chemical[]) var5.toArray(new Chemical[var5.size()]);
            ItemStack var7 = new ItemStack(MinechemItems.molecule, 1, var4.id());
            DecomposerRecipe.add(new DecomposerRecipe(var7, var6));
        }

    }

    private void addSynthesisRecipesFromMolecules() {
        EnumMolecule[] var1 = EnumMolecule.molecules;
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            EnumMolecule var4 = var1[var3];
            ArrayList var5 = var4.components();
            ItemStack var6 = new ItemStack(MinechemItems.molecule, 1, var4.id());
            SynthesisRecipe.add(new SynthesisRecipe(var6, false, 50, var5));
        }

    }

    private void addUnusedSynthesisRecipes() {
        Iterator var1 = DecomposerRecipe.recipes.iterator();

        while (var1.hasNext()) {
            DecomposerRecipe var2 = (DecomposerRecipe) var1.next();
            if (var2.getInput().getItemDamage() != -1) {
                boolean var3 = false;
                Iterator var4 = SynthesisRecipe.recipes.iterator();
                //What kind of crappy code is this?
                //I've got to fix it.....If I can figure out what it does
                while (true) {
                    if (var4.hasNext()) {
                        SynthesisRecipe var5 = (SynthesisRecipe) var4.next();
                        if (!Util.stacksAreSameKind(var5.getOutput(), var2.getInput())) {
                            continue;
                        }

                        var3 = true;
                    }

                    if (!var3) {
                        ArrayList var6 = var2.getOutputRaw();
                        if (var6 != null) {
                            SynthesisRecipe.add(new SynthesisRecipe(var2.getInput(), false, 100, var6));
                        }
                    }
                    break;
                }
            }
        }
   }

   private ItemStack createPoisonedItemStack(Item var1, int var2, EnumMolecule var3) {
      ItemStack var4 = new ItemStack(MinechemItems.molecule, 1, var3.id());
      ItemStack var5 = new ItemStack(var1, 1, var2);
      ItemStack var6 = new ItemStack(var1, 1, var2);
      NBTTagCompound var7 = new NBTTagCompound();
      var7.setBoolean("minechem.isPoisoned", true);
      var7.setInteger("minechem.effectType", var3.id());
      var6.setTagCompound(var7);
      GameRegistry.addShapelessRecipe(var6, new Object[]{var4, var5});
      return var6;
   }

   private void registerPoisonRecipes(EnumMolecule molecule) {

	     for(Item i: Item.itemsList) { // Thanks Adrian!

	       if(i != null && i instanceof ItemFood) { // Should allow for lacing of BOP and AquaCulture foodstuffs.

	         this.createPoisonedItemStack(i, 0, molecule);

	       }

	     }

	    }
   /* 
    * This stuff is unused and is replaced by ljdp.minechem.common.recipe.handlers.DefaultOreDictionaryHandler
   String[] compounds = {"Aluminium","Titanium","Chrome",   
	           "Tungsten", "Lead",    "Zinc",
	           "Platinum", "Nickel",  "Osmium",
	           "Iron",     "Gold",    "Coal",
	           "Copper",   "Tin",     "Silver",
	           "RefinedIron",
	           "Steel",
	           "Bronze",   "Brass",   "Electrum",
	           "Invar"};//,"Iridium"};
	
	EnumElement[][] elements = {{EnumElement.Al}, {EnumElement.Ti}, {EnumElement.Cr}, 
						   {EnumElement.W},  {EnumElement.Pb}, {EnumElement.Zn}, 
						   {EnumElement.Pt}, {EnumElement.Ni}, {EnumElement.Os}, 
						   {EnumElement.Fe}, {EnumElement.Au}, {EnumElement.C}, 
						   {EnumElement.Cu}, {EnumElement.Sn}, {EnumElement.Ag},
						   {EnumElement.Fe},
						   {EnumElement.Fe, EnumElement.C},		//Steel
						   {EnumElement.Sn, EnumElement.Cu},
						   {EnumElement.Cu},//Bronze
			               {EnumElement.Zn, EnumElement.Cu},	//Brass
			               {EnumElement.Ag, EnumElement.Au},	//Electrum
			               {EnumElement.Fe, EnumElement.Ni}		//Invar
			               };//, EnumElement.Ir
	
	int[][] proportions      = {{4},{4},{4},
						   {4},{4},{4},
						   {4},{4},{4},
						   {4},{4},{4},
						   {4},{4},{4},
						   {4},
						   {4,4},
						   {1,3},{1,3},{2,2},{2,1}};
	
	String[]  itemTypes = {"dustSmall", "dust", "ore" , "ingot", "block", "gear", "plate"}; //"nugget", "plate"
	boolean[] craftable = {true, true, false, false, false, false, false};
	int[] 	 sizeCoeff = {1, 4, 8, 4, 36, 16, 4};
    */
   
	private ArrayList<OreDictionaryHandler> oreDictionaryHandlers;
	
	@ForgeSubscribe
	public void oreEvent(OreDictionary.OreRegisterEvent var1) {
		if (var1.Name.contains("gemApatite")) {
			DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new Chemical[] {
					this.element(EnumElement.Ca, 5),
					this.molecule(EnumMolecule.phosphate, 4),
					this.element(EnumElement.Cl) }));
			SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 1000,
					new Chemical[] { this.element(EnumElement.Ca, 5),
							this.molecule(EnumMolecule.phosphate, 4),
							this.element(EnumElement.Cl) }));
		} else if (var1.Name.contains("Ruby")) {
			DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new Chemical[] {
					this.molecule(EnumMolecule.aluminiumOxide,3),
					this.element(EnumElement.Cr,3) }));
			SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 1000,
					new Chemical[] {
							this.molecule(EnumMolecule.aluminiumOxide,3),
							this.element(EnumElement.Cr,3) }));
		} else if (var1.Name.contains("Sapphire")) {
			DecomposerRecipe.add(new DecomposerRecipe(var1.Ore,
					new Chemical[] { this.molecule(EnumMolecule.aluminiumOxide,
							3) }));
			SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 1000,
					new Chemical[] { this.molecule(EnumMolecule.aluminiumOxide,
							3) }));
		} else if (var1.Name.contains("ingotBronze")) {
			if (Loader.isModLoaded("Mekanism")) {
				DecomposerRecipe.add(new DecomposerRecipe(var1.Ore,
						new Chemical[] { this.element(EnumElement.Cu, 16),
								this.element(EnumElement.Sn, 2) }));
				SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 1000,
						new Chemical[] { this.element(EnumElement.Cu, 16),
								this.element(EnumElement.Sn, 2) }));
			} else {
				DecomposerRecipe.add(new DecomposerRecipe(var1.Ore,
						new Chemical[] { this.element(EnumElement.Cu, 24),
								this.element(EnumElement.Sn, 8) }));
				SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 1000,
						new Chemical[] { this.element(EnumElement.Cu, 24),
								this.element(EnumElement.Sn, 8) }));
			}
		} else if (var1.Name.contains("plateSilicon")) {
			DecomposerRecipe.add(new DecomposerRecipe(var1.Ore,
					new Chemical[] { this.element(EnumElement.Si, 2) }));
			SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 1000,
					new Chemical[] { this.element(EnumElement.Si, 2) }));
		} else if (var1.Name.contains("xychoriumBlue")) {
			DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new Chemical[] {
					this.element(EnumElement.Zr, 2),
					this.element(EnumElement.Cu, 1) }));
			SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 300,
					new Chemical[] { this.element(EnumElement.Zr, 2),
							this.element(EnumElement.Cu, 1) }));
		} else if (var1.Name.contains("xychoriumRed")) {
			DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new Chemical[] {
					this.element(EnumElement.Zr, 2),
					this.element(EnumElement.Fe, 1) }));
			SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 300,
					new Chemical[] { this.element(EnumElement.Zr, 2),
							this.element(EnumElement.Fe, 1) }));
		} else if (var1.Name.contains("xychoriumGreen")) {
			DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new Chemical[] {
					this.element(EnumElement.Zr, 2),
					this.element(EnumElement.V, 1) }));
			SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 300,
					new Chemical[] { this.element(EnumElement.Zr, 2),
							this.element(EnumElement.V, 1) }));
		} else if (var1.Name.contains("xychoriumDark")) {
			DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new Chemical[] {
					this.element(EnumElement.Zr, 2),
					this.element(EnumElement.Si, 1) }));
			SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 300,
					new Chemical[] { this.element(EnumElement.Zr, 2),
							this.element(EnumElement.Si, 1) }));
		} else if (var1.Name.contains("xychoriumLight")) {
			DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new Chemical[] {
					this.element(EnumElement.Zr, 2),
					this.element(EnumElement.Ti, 1) }));
			SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 300,
					new Chemical[] { this.element(EnumElement.Zr, 2),
							this.element(EnumElement.Ti, 1) }));

		} else if (var1.Name.contains("ingotCobalt")) { // Tungsten - Cobalt
														// Alloy
			DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new Chemical[] {
					this.element(EnumElement.Co, 2),
					this.element(EnumElement.W, 2) }));
			SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 5000,
					new Chemical[] { this.element(EnumElement.Co, 2),
							this.element(EnumElement.W, 2) }));

		} else if (var1.Name.contains("ingotArdite")) { // Tungsten - Iron -
														// Silicon Alloy
			DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new Chemical[] {
					this.element(EnumElement.Fe, 2),
					this.element(EnumElement.W, 2),
					this.element(EnumElement.Si, 2) }));
			SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 5000,
					new Chemical[] { this.element(EnumElement.Fe, 2),
							this.element(EnumElement.W, 2),
							this.element(EnumElement.Si, 2) }));

		} else if (var1.Name.contains("ingotManyullyn")) { // Tungsten - Iron -
															// Silicon - Cobalt
															// Alloy
			DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new Chemical[] {
					this.element(EnumElement.Fe, 2),
					this.element(EnumElement.W, 2),
					this.element(EnumElement.Si, 2),
					this.element(EnumElement.Co, 2) }));
			SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 7000,
					new Chemical[] { this.element(EnumElement.Fe, 2),
							this.element(EnumElement.W, 2),
							this.element(EnumElement.Si, 2),
							this.element(EnumElement.Co, 2) }));
		} else if (var1.Name.contains("gemRuby")) {
			DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new Chemical[] {
					this.molecule(EnumMolecule.aluminiumOxide),
					this.element(EnumElement.Cr) }));
			SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 1000,
					new Chemical[] {
							this.molecule(EnumMolecule.aluminiumOxide),
							this.element(EnumElement.Cr) }));
		} else if (var1.Name.contains("gemSapphire")) {
			DecomposerRecipe
					.add(new DecomposerRecipe(var1.Ore, new Chemical[] { this
							.molecule(EnumMolecule.aluminiumOxide) }));
			SynthesisRecipe
					.add(new SynthesisRecipe(var1.Ore, false, 1000,
							new Chemical[] { this
									.molecule(EnumMolecule.aluminiumOxide) }));
		} else if (var1.Name.contains("gemPeridot")) {
			DecomposerRecipe.add(new DecomposerRecipe(var1.Ore,
					new Chemical[] { this.molecule(EnumMolecule.olivine) }));
			SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 1000,
					new Chemical[] { this.molecule(EnumMolecule.olivine) }));
		} else if (var1.Name.contains("cropMaloberry")) {
			DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new Chemical[] {
					this.molecule(EnumMolecule.stevenk),
					this.molecule(EnumMolecule.sucrose) }));
			SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 1000,
					new Chemical[] { this.molecule(EnumMolecule.stevenk),
							this.molecule(EnumMolecule.sucrose) }));
		} else if (var1.Name.contains("cropDuskberry")) {
			DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new Chemical[] {
					this.molecule(EnumMolecule.psilocybin),
					this.element(EnumElement.S, 2) }));
			SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 1000,
					new Chemical[] { this.molecule(EnumMolecule.psilocybin),
							this.element(EnumElement.S, 2) }));
		} else if (var1.Name.contains("cropSkyberry")) {
			DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new Chemical[] {
					this.molecule(EnumMolecule.theobromine),
					this.element(EnumElement.S, 2) }));
			SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 1000,
					new Chemical[] { this.molecule(EnumMolecule.theobromine),
							this.element(EnumElement.S, 2) }));
		} else if (var1.Name.contains("cropBlightberry")) {
			DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new Chemical[] {
					this.molecule(EnumMolecule.asprin),
					this.element(EnumElement.S, 2) }));
			SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 1000,
					new Chemical[] { this.molecule(EnumMolecule.asprin),
							this.element(EnumElement.S, 2) }));
		} else if (var1.Name.contains("cropBlueberry")) {
			DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new Chemical[] {
					this.molecule(EnumMolecule.blueorgodye),
					this.molecule(EnumMolecule.sucrose, 2) }));
			SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 1000,
					new Chemical[] { this.molecule(EnumMolecule.blueorgodye),
							this.molecule(EnumMolecule.sucrose, 2) }));
		} else if (var1.Name.contains("cropRaspberry")) {
			DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new Chemical[] {
					this.molecule(EnumMolecule.redorgodye),
					this.molecule(EnumMolecule.sucrose, 2) }));
			SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 1000,
					new Chemical[] { this.molecule(EnumMolecule.redorgodye),
							this.molecule(EnumMolecule.sucrose, 2) }));
		} else if (var1.Name.contains("cropBlackberry")) {
			DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new Chemical[] {
					this.molecule(EnumMolecule.purpleorgodye),
					this.molecule(EnumMolecule.sucrose, 2) }));
			SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 1000,
					new Chemical[] { this.molecule(EnumMolecule.purpleorgodye),
							this.molecule(EnumMolecule.sucrose, 2) }));
		} else {
			for (OreDictionaryHandler handler : this.oreDictionaryHandlers) {
				if (handler.canHandle(var1)) {
					handler.handle(var1);
					return;
				}
			}
		}
   } 
   // END 
   // BEGIN MISC FUNCTIONS
   private Element element(EnumElement var1, int var2) {
      return new Element(var1, var2);
   }

   private Element element(EnumElement var1) {
      return new Element(var1, 1);
   }

   private Molecule molecule(EnumMolecule var1, int var2) {
      return new Molecule(var1, var2);
   }

   private Molecule molecule(EnumMolecule var1) {
      return new Molecule(var1, 1);
   }
   // END

	public void RegisterHandlers() {
		this.oreDictionaryHandlers = new ArrayList<OreDictionaryHandler>();		
		if (Loader.isModLoaded("Mekanism"))
			this.oreDictionaryHandlers.add(new MekanismOreDictionaryHandler());
		if (Loader.isModLoaded("UndergroundBiomes"))
			this.oreDictionaryHandlers.add(new UndergroundBiomesOreDictionaryHandler());
		if (Loader.isModLoaded("gregtech_addon"))
			this.oreDictionaryHandlers.add(new GregTechOreDictionaryHandler());
		if (Loader.isModLoaded("IC2"))
			this.oreDictionaryHandlers.add(new IC2OreDictionaryHandler());
		if (Loader.isModLoaded("AppliedEnergistics"))
			this.oreDictionaryHandlers.add(new AppliedEnergisticsOreDictionaryHandler());
		this.oreDictionaryHandlers.add(new DefaultOreDictionaryHandler());
	}
} // EOF
