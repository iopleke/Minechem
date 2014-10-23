package minechem;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import minechem.item.blueprint.ItemBlueprint;
import minechem.item.blueprint.MinechemBlueprint;
import minechem.item.chemistjournal.ChemistJournalRecipeCloning;
import minechem.item.element.Element;
import minechem.item.element.ElementEnum;
import minechem.item.element.ElementItem;
import minechem.item.molecule.Molecule;
import minechem.item.molecule.MoleculeEnum;
import minechem.oredictionary.OreDictionaryAppliedEnergisticsHandler;
import minechem.oredictionary.OreDictionaryDefaultHandler;
import minechem.oredictionary.OreDictionaryGregTechHandler;
import minechem.oredictionary.OreDictionaryHandler;
import minechem.oredictionary.OreDictionaryIC2Handler;
import minechem.oredictionary.OreDictionaryMekanismHandler;
import minechem.oredictionary.OreDictionaryUndergroundBiomesHandler;
import minechem.potion.PotionChemical;
import minechem.tileentity.decomposer.DecomposerFluidRecipe;
import minechem.tileentity.decomposer.DecomposerFluidRecipeSelect;
import minechem.tileentity.decomposer.DecomposerRecipe;
import minechem.tileentity.decomposer.DecomposerRecipeChance;
import minechem.tileentity.decomposer.DecomposerRecipeSelect;
import minechem.tileentity.synthesis.SynthesisRecipe;
import minechem.utils.Compare;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

@SuppressWarnings("RedundantArrayCreation")
public class MinechemRecipes
{

	private static final MinechemRecipes recipes = new MinechemRecipes();
	public ArrayList decomposition = new ArrayList();
	public ArrayList synthesis = new ArrayList();

	/*
	 * Energy costs for synthesizing (without multiplier)
	 */
	public static int BLOCK_MULTIPLIER = 8;
	public static int COST_INGOT = 300;
	public static int COST_BLOCK = 15;
	public static int COST_ITEM = 10;
	public static int COST_METALBLOCK = COST_INGOT * BLOCK_MULTIPLIER;
	public static int COST_PLANK = 20;
	public static int COST_LAPIS = 20;
	public static int COST_LAPISBLOCK = COST_LAPIS * BLOCK_MULTIPLIER;
	public static int COST_GRASS = 40;
	public static int COST_SMOOTH = 30;
	public static int COST_STAR = 6000;
	public static int COST_SUGAR = 30;
	public static int COST_GLOW = 70;
	public static int COST_GLOWBLOCK = COST_GLOW * 4;
	public static int COST_TEAR = 3000;
	public static int COST_OBSIDIAN = 100;
	public static int COST_PLANT = 200;
	public static int COST_FOOD = 250;
	public static int COST_WOOD = COST_PLANK * 4;
	public static int COST_GLASS = 300;
	public static int COST_PANE = COST_GLASS / 3;
	public static int COST_WOOL = 200;
	public static int COST_CARPET = COST_WOOL / 2;
	public static int COST_GEM = 1000;
	public static int COST_GEMBLOCK = COST_GEM * BLOCK_MULTIPLIER;

	/*
	 * Amount of fluid for given unit
	 */
	private static final int INGOT_AMOUNT = 144;
	private static final int BUCKET_AMOUNT = 10000;

	public static MinechemRecipes getInstance()
	{
		return recipes;
	}

	public void registerFluidRecipies()
	{
		// Fluids
		int fluidPerIngot = INGOT_AMOUNT;
		// Quick and dirty fix for the Thermal Expansion Resonant Ender 6x bug
		int threeQuarterFluidPerIngot = 180;
		DecomposerRecipe.add(new DecomposerFluidRecipe(new FluidStack(FluidRegistry.WATER, BUCKET_AMOUNT), new PotionChemical[]
		{
			this.element(ElementEnum.H, 2), this.element(ElementEnum.O)
		}));

		// Lava
		// This assumes lava is composed from cobblestone at a 4:1 ratio
		//   as well as having slightly higher purity
		DecomposerRecipe.add(new DecomposerFluidRecipeSelect("lava", 250, 0.2F, new DecomposerRecipe[]
		{
			new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Si), this.element(ElementEnum.O)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Fe), this.element(ElementEnum.O)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Mg), this.element(ElementEnum.O)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Ti), this.element(ElementEnum.O)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Pb), this.element(ElementEnum.O)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Na), this.element(ElementEnum.Cl)
			})
		}));

		// Mod fluids
		// Checks if the fluid exists
		DecomposerFluidRecipe.createAndAddFluidRecipeSafely("water", fluidPerIngot, new PotionChemical[]
		{
			this.element(ElementEnum.H, 2), this.element(ElementEnum.O)
		});
		DecomposerFluidRecipe.createAndAddFluidRecipeSafely("iron.molten", fluidPerIngot, new PotionChemical[]
		{
			this.element(ElementEnum.Fe, 16)
		});
		DecomposerFluidRecipe.createAndAddFluidRecipeSafely("gold.molten", fluidPerIngot, new PotionChemical[]
		{
			this.element(ElementEnum.Au, 16)
		});
		DecomposerFluidRecipe.createAndAddFluidRecipeSafely("copper.molten", fluidPerIngot, new PotionChemical[]
		{
			this.element(ElementEnum.Cu, 16)
		});
		DecomposerFluidRecipe.createAndAddFluidRecipeSafely("tin.molten", fluidPerIngot, new PotionChemical[]
		{
			this.element(ElementEnum.Sn, 16)
		});
		DecomposerFluidRecipe.createAndAddFluidRecipeSafely("aluminum.molten", fluidPerIngot, new PotionChemical[]
		{
			this.element(ElementEnum.Al, 16)
		});
		DecomposerFluidRecipe.createAndAddFluidRecipeSafely("cobalt.molten", fluidPerIngot, new PotionChemical[]
		{
			this.element(ElementEnum.Co, 16)
		});
		DecomposerFluidRecipe.createAndAddFluidRecipeSafely("ardite.molten", fluidPerIngot, new PotionChemical[]
		{
			this.element(ElementEnum.Fe, 2), this.element(ElementEnum.W, 2), this.element(ElementEnum.Si, 2)
		});

		DecomposerFluidRecipe.createAndAddFluidRecipeSafely("bronze.molten", fluidPerIngot, new PotionChemical[]
		{
			this.element(ElementEnum.Cu, 12), this.element(ElementEnum.Sn, 4)
		});
		DecomposerFluidRecipe.createAndAddFluidRecipeSafely("aluminumbrass.molten", fluidPerIngot, new PotionChemical[]
		{
			this.element(ElementEnum.Cu, 12), this.element(ElementEnum.Al, 4)
		});
		DecomposerFluidRecipe.createAndAddFluidRecipeSafely("manyullyn.molten", fluidPerIngot, new PotionChemical[]
		{
			this.element(ElementEnum.Co, 8), this.element(ElementEnum.Fe, 1), this.element(ElementEnum.W, 1), this.element(ElementEnum.Si, 1)
		});
		DecomposerFluidRecipe.createAndAddFluidRecipeSafely("alumite.molten", fluidPerIngot, new PotionChemical[]
		{
			this.element(ElementEnum.Al, 8), this.element(ElementEnum.Fe, 3), this.molecule(MoleculeEnum.siliconDioxide, 2), this.molecule(MoleculeEnum.magnesiumOxide, 1)
		});
		DecomposerFluidRecipe.createAndAddFluidRecipeSafely("obsidian.molten", fluidPerIngot, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.siliconDioxide, 16), this.molecule(MoleculeEnum.magnesiumOxide, 8)
		});
		DecomposerFluidRecipe.createAndAddFluidRecipeSafely("steel.molten", fluidPerIngot, new PotionChemical[]
		{
			this.element(ElementEnum.Fe, 14), this.element(ElementEnum.C, 2)
		}); // This ratio should be tested
		DecomposerFluidRecipe.createAndAddFluidRecipeSafely("stone.seared", fluidPerIngot, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.siliconOxide, 12), this.molecule(MoleculeEnum.ironOxide, 4)
		});
		DecomposerFluidRecipe.createAndAddFluidRecipeSafely("glass.molten", fluidPerIngot, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.siliconDioxide, 16)
		});
		DecomposerFluidRecipe.createAndAddFluidRecipeSafely("emerald.molten", fluidPerIngot, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.beryl, 6), this.element(ElementEnum.Cr, 6), this.element(ElementEnum.V, 6)
		});
		DecomposerFluidRecipe.createAndAddFluidRecipeSafely("blood.molten", fluidPerIngot, new PotionChemical[]
		{
			this.element(ElementEnum.O, 6), this.element(ElementEnum.Fe, 2), this.molecule(MoleculeEnum.ironOxide, 8)
		});
		DecomposerFluidRecipe.createAndAddFluidRecipeSafely("nickel.molten", fluidPerIngot, new PotionChemical[]
		{
			this.element(ElementEnum.Ni, 16)
		});
		DecomposerFluidRecipe.createAndAddFluidRecipeSafely("lead.molten", fluidPerIngot, new PotionChemical[]
		{
			this.element(ElementEnum.Pb, 16)
		});
		DecomposerFluidRecipe.createAndAddFluidRecipeSafely("silver.molten", fluidPerIngot, new PotionChemical[]
		{
			this.element(ElementEnum.Ag, 16)
		});
		DecomposerFluidRecipe.createAndAddFluidRecipeSafely("platinum.molten", fluidPerIngot, new PotionChemical[]
		{
			this.element(ElementEnum.Pt, 16)
		});
		DecomposerFluidRecipe.createAndAddFluidRecipeSafely("invar.molten", fluidPerIngot, new PotionChemical[]
		{
			this.element(ElementEnum.Fe, 10), this.element(ElementEnum.Ni, 6)
		});
		DecomposerFluidRecipe.createAndAddFluidRecipeSafely("electrum.molten", fluidPerIngot, new PotionChemical[]
		{
			this.element(ElementEnum.Ag, 8), this.element(ElementEnum.Au, 8)
		});
		DecomposerFluidRecipe.createAndAddFluidRecipeSafely(
				"ender",
				threeQuarterFluidPerIngot,
				new PotionChemical[]
				{
					this.molecule(MoleculeEnum.calciumCarbonate), this.molecule(MoleculeEnum.calciumCarbonate), this.molecule(MoleculeEnum.calciumCarbonate), this.molecule(MoleculeEnum.calciumCarbonate), this.element(ElementEnum.Es),
					this.molecule(MoleculeEnum.calciumCarbonate), this.molecule(MoleculeEnum.calciumCarbonate), this.molecule(MoleculeEnum.calciumCarbonate), this.molecule(MoleculeEnum.calciumCarbonate)
				});
		registerMFRFluidRecipes();
	}

	private void registerMFRFluidRecipes()
	{
		DecomposerFluidRecipe.createAndAddFluidRecipeSafely("mushroomsoup", BUCKET_AMOUNT, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.water, 4), this.molecule(MoleculeEnum.pantherine, 2)
		});
		DecomposerFluidRecipe.createAndAddFluidRecipeSafely("chocolatemilk", BUCKET_AMOUNT, new PotionChemical[]
		{
			this.element(ElementEnum.Ca, 4), this.molecule(MoleculeEnum.theobromine, 1)
		});
		// If someone figures out compositions for the other fluids, add them here.
	}

	public void registerVanillaChemicalRecipes()
	{

		// Molecules
		Molecule moleculeSiliconDioxide = this.molecule(MoleculeEnum.siliconDioxide, 4);
		Molecule moleculeCellulose = this.molecule(MoleculeEnum.cellulose, 1);
		Molecule moleculePolyvinylChloride = this.molecule(MoleculeEnum.polyvinylChloride);
		Molecule moleculeLazurite = this.molecule(MoleculeEnum.lazurite, 9);

		// Elements
		Element elementHydrogen = this.element(ElementEnum.H, 64);
		Element elementHelium = this.element(ElementEnum.He, 64);
		Element elementCarbon = this.element(ElementEnum.C, 64);

		// Section 1 - Blocks
		// Stone
		ItemStack blockStone = new ItemStack(Blocks.stone);
		DecomposerRecipe.add(new DecomposerRecipeSelect(blockStone, 0.2F, new DecomposerRecipe[]
		{
			new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Si), this.element(ElementEnum.O)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Fe), this.element(ElementEnum.O)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Mg), this.element(ElementEnum.O)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Ti), this.element(ElementEnum.O)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Pb), this.element(ElementEnum.O)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Zn), this.element(ElementEnum.O)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Al), this.element(ElementEnum.O)
			})
		}));
		SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Blocks.stone, 16), true, COST_SMOOTH, new PotionChemical[]
		{
			this.element(ElementEnum.Si), this.element(ElementEnum.O, 2), null, this.element(ElementEnum.Al, 2), this.element(ElementEnum.O, 3), null
		}));

		// Grass Block
		ItemStack blockGrass = new ItemStack(Blocks.grass, 1, 0);
		DecomposerRecipe.add(new DecomposerRecipeSelect(blockGrass, 0.07F, new DecomposerRecipe[]
		{
			new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Si), this.element(ElementEnum.O)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Fe), this.element(ElementEnum.O)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Mg), this.element(ElementEnum.O)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Ti), this.element(ElementEnum.O)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Pb), this.element(ElementEnum.O)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Zn), this.element(ElementEnum.O)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Ga), this.element(ElementEnum.As)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				moleculeCellulose
			})
		}));
		SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Blocks.grass, 16), true, COST_GRASS, new PotionChemical[]
		{
			null, moleculeCellulose, null, null, this.element(ElementEnum.O, 2), this.element(ElementEnum.Si)
		}));

		// Dirt
		ItemStack blockDirt = new ItemStack(Blocks.dirt, 1, 0);
		ItemStack blockPodzol = new ItemStack(Blocks.dirt, 1, 2);

		DecomposerRecipe.add(new DecomposerRecipeSelect(blockDirt, 0.07F, new DecomposerRecipe[]
		{
			new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Si), this.element(ElementEnum.O)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Fe), this.element(ElementEnum.O)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Mg), this.element(ElementEnum.O)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Ti), this.element(ElementEnum.O)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Pb), this.element(ElementEnum.O)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Zn), this.element(ElementEnum.O)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Ga), this.element(ElementEnum.As)
			})
		}));
		DecomposerRecipe.add(new DecomposerRecipeSelect(blockPodzol, 0.07F, new DecomposerRecipe[]
		{
			new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Si), this.element(ElementEnum.O)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Fe), this.element(ElementEnum.O)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Mg), this.element(ElementEnum.O)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Ti), this.element(ElementEnum.O)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Pb), this.element(ElementEnum.O)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Zn), this.element(ElementEnum.O)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Ga), this.element(ElementEnum.As)
			})
		}));
		SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Blocks.dirt, 1, 0), true, COST_BLOCK, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.siliconDioxide), null, null, null, null, null, null, null, null
		}));
		SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Blocks.dirt, 1, 2), true, COST_BLOCK, new PotionChemical[]
		{
			null, null, null, this.molecule(MoleculeEnum.siliconDioxide), null, null, null, null, null
		}));

		// Cobblestone
		ItemStack blockCobblestone = new ItemStack(Blocks.cobblestone);
		DecomposerRecipe.add(new DecomposerRecipeSelect(blockCobblestone, 0.1F, new DecomposerRecipe[]
		{
			new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Si), this.element(ElementEnum.O)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Fe), this.element(ElementEnum.O)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Mg), this.element(ElementEnum.O)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Ti), this.element(ElementEnum.O)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Pb), this.element(ElementEnum.O)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Na), this.element(ElementEnum.Cl)
			})
		}));
		SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Blocks.cobblestone,16), true, COST_SMOOTH, new PotionChemical[]
		{
			this.element(ElementEnum.Si,2), this.element(ElementEnum.O, 4), null
		}));

		// Planks
		ItemStack blockOakWoodPlanks = new ItemStack(Blocks.planks, 1, 0);
		ItemStack blockSpruceWoodPlanks = new ItemStack(Blocks.planks, 1, 1);
		ItemStack blockBirchWoodPlanks = new ItemStack(Blocks.planks, 1, 2);
		ItemStack blockJungleWoodPlanks = new ItemStack(Blocks.planks, 1, 3);
		ItemStack blockAcaciaWoodPlanks = new ItemStack(Blocks.planks, 1, 4);
		ItemStack blockDarkOakWoodPlanks = new ItemStack(Blocks.planks, 1, 5);

		DecomposerRecipe.add(new DecomposerRecipeChance(blockOakWoodPlanks, 0.4F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.cellulose, 2)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockSpruceWoodPlanks, 0.4F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.cellulose, 2)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockBirchWoodPlanks, 0.4F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.cellulose, 2)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockJungleWoodPlanks, 0.4F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.cellulose, 2)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockAcaciaWoodPlanks, 0.4F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.cellulose, 2)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockDarkOakWoodPlanks, 0.4F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.cellulose, 2)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockOakWoodPlanks, true, COST_PLANK, new PotionChemical[]
		{
			null, null, null, null, null, null, null, this.molecule(MoleculeEnum.cellulose), this.molecule(MoleculeEnum.cellulose)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockSpruceWoodPlanks, true, COST_PLANK, new PotionChemical[]
		{
			null, null, null, null, null, null, this.molecule(MoleculeEnum.cellulose), this.molecule(MoleculeEnum.cellulose), null
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockBirchWoodPlanks, true, COST_PLANK, new PotionChemical[]
		{
			null, null, null, null, null, this.molecule(MoleculeEnum.cellulose), this.molecule(MoleculeEnum.cellulose), null, null
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockJungleWoodPlanks, true, COST_PLANK, new PotionChemical[]
		{
			null, null, null, null, this.molecule(MoleculeEnum.cellulose), this.molecule(MoleculeEnum.cellulose), null, null, null
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockAcaciaWoodPlanks, true, COST_PLANK, new PotionChemical[]
		{
			null, null, null, this.molecule(MoleculeEnum.cellulose), this.molecule(MoleculeEnum.cellulose), null, null, null, null
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockDarkOakWoodPlanks, true, COST_PLANK, new PotionChemical[]
		{
			null, null, this.molecule(MoleculeEnum.cellulose), this.molecule(MoleculeEnum.cellulose), null, null, null, null, null
		}));

		// Wooden Slabs
		ItemStack blockOakWoodSlabs = new ItemStack(Blocks.wooden_slab, 1, 0);
		ItemStack blockSpruceWoodSlabs = new ItemStack(Blocks.wooden_slab, 1, 1);
		ItemStack blockBirchWoodSlabs = new ItemStack(Blocks.wooden_slab, 1, 2);
		ItemStack blockJungleWoodSlabs = new ItemStack(Blocks.wooden_slab, 1, 3);
		ItemStack blockAcaciaWoodSlabs = new ItemStack(Blocks.wooden_slab, 1, 4);
		ItemStack blockDarkOakWoodSlabs = new ItemStack(Blocks.wooden_slab, 1, 5);

		DecomposerRecipe.add(new DecomposerRecipeChance(blockOakWoodSlabs, 0.4F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.cellulose, 2)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockSpruceWoodSlabs, 0.4F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.cellulose, 2)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockBirchWoodSlabs, 0.4F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.cellulose, 2)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockJungleWoodSlabs, 0.4F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.cellulose, 2)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockAcaciaWoodSlabs, 0.4F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.cellulose, 2)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockDarkOakWoodSlabs, 0.4F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.cellulose, 2)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockOakWoodSlabs, true, COST_PLANK, new PotionChemical[]
		{
			null, null, null, null, null, null, this.molecule(MoleculeEnum.cellulose), null, this.molecule(MoleculeEnum.cellulose)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockSpruceWoodSlabs, true, COST_PLANK, new PotionChemical[]
		{
			null, null, null, null, null, this.molecule(MoleculeEnum.cellulose), null, this.molecule(MoleculeEnum.cellulose), null
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockBirchWoodSlabs, true, COST_PLANK, new PotionChemical[]
		{
			null, null, null, null, this.molecule(MoleculeEnum.cellulose), null, this.molecule(MoleculeEnum.cellulose), null, null
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockJungleWoodSlabs, true, COST_PLANK, new PotionChemical[]
		{
			null, null, null, this.molecule(MoleculeEnum.cellulose), null, this.molecule(MoleculeEnum.cellulose), null, null, null
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockAcaciaWoodSlabs, true, COST_PLANK, new PotionChemical[]
		{
			null, null, this.molecule(MoleculeEnum.cellulose), null, this.molecule(MoleculeEnum.cellulose), null, null, null, null
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockDarkOakWoodSlabs, true, COST_PLANK, new PotionChemical[]
		{
			null, this.molecule(MoleculeEnum.cellulose), null, this.molecule(MoleculeEnum.cellulose), null, null, null, null, null
		}));

		// Saplings
		ItemStack blockOakSapling = new ItemStack(Blocks.sapling, 1, 0);
		ItemStack blockSpruceSapling = new ItemStack(Blocks.sapling, 1, 1);
		ItemStack blockBirchSapling = new ItemStack(Blocks.sapling, 1, 2);
		ItemStack blockJungleSapling = new ItemStack(Blocks.sapling, 1, 3);
		ItemStack blockAcaciaSapling = new ItemStack(Blocks.sapling, 1, 4);
		ItemStack blockDarkOakSapling = new ItemStack(Blocks.sapling, 1, 5);
		DecomposerRecipe.add(new DecomposerRecipeChance(blockOakSapling, 0.25F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.cellulose)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockSpruceSapling, 0.25F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.cellulose)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockBirchSapling, 0.25F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.cellulose)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockJungleSapling, 0.25F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.cellulose)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockAcaciaSapling, 0.25F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.cellulose)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockDarkOakSapling, 0.25F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.cellulose)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockOakSapling, true, COST_PLANT, new PotionChemical[]
		{
			null, null, null, null, null, null, null, null, this.molecule(MoleculeEnum.cellulose)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockSpruceSapling, true, COST_PLANT, new PotionChemical[]
		{
			null, null, null, null, null, null, null, this.molecule(MoleculeEnum.cellulose), null
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockBirchSapling, true, COST_PLANT, new PotionChemical[]
		{
			null, null, null, null, null, null, this.molecule(MoleculeEnum.cellulose), null, null
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockJungleSapling, true, COST_PLANT, new PotionChemical[]
		{
			null, null, null, null, null, this.molecule(MoleculeEnum.cellulose), null, null, null
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockAcaciaSapling, true, COST_PLANT, new PotionChemical[]
		{
			null, null, null, null, this.molecule(MoleculeEnum.cellulose), null, null, null, null
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockDarkOakSapling, true, COST_PLANT, new PotionChemical[]
		{
			null, null, null, this.molecule(MoleculeEnum.cellulose), null, null, null, null, null
		}));

		// Sand
		ItemStack blockSand = new ItemStack(Blocks.sand);
		DecomposerRecipe.add(new DecomposerRecipe(blockSand, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.siliconDioxide, 16)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockSand, true, COST_BLOCK, new PotionChemical[]
		{
			moleculeSiliconDioxide, moleculeSiliconDioxide, moleculeSiliconDioxide, moleculeSiliconDioxide
		}));

		// Gravel
		ItemStack blockGravel = new ItemStack(Blocks.gravel);
		DecomposerRecipe.add(new DecomposerRecipeChance(blockGravel, 0.35F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.siliconDioxide)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockGravel, true, COST_BLOCK, new PotionChemical[]
		{
			null, null, null, null, null, null, null, null, this.molecule(MoleculeEnum.siliconDioxide)
		}));

		// Gold Ore
		ItemStack goldOre = new ItemStack(Blocks.gold_ore);
		DecomposerRecipe.add(new DecomposerRecipe(goldOre, new PotionChemical[]
		{
			this.element(ElementEnum.Au, 48)
		}));

		// Iron Ore
		ItemStack ironOre = new ItemStack(Blocks.iron_ore);
		DecomposerRecipe.add(new DecomposerRecipe(ironOre, new PotionChemical[]
		{
			this.element(ElementEnum.Fe, 48)
		}));

		// Coal Ore
		ItemStack coalOre = new ItemStack(Blocks.coal_ore);
		DecomposerRecipe.add(new DecomposerRecipe(coalOre, new PotionChemical[]
		{
			this.element(ElementEnum.C, 48)
		}));

		// Log
		ItemStack blockOakLog = new ItemStack(Blocks.log, 1, 0);
		ItemStack blockSpruceLog = new ItemStack(Blocks.log, 1, 1);
		ItemStack blockBirchLog = new ItemStack(Blocks.log, 1, 2);
		ItemStack blockJungleLog = new ItemStack(Blocks.log, 1, 3);
		ItemStack blockAcaciaLog = new ItemStack(Blocks.log2, 1, 0);
		ItemStack blockDarkOakLog = new ItemStack(Blocks.log2, 1, 1);
		DecomposerRecipe.add(new DecomposerRecipeChance(blockOakLog, 0.5F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.cellulose, 8)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockSpruceLog, 0.5F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.cellulose, 8)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockBirchLog, 0.5F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.cellulose, 8)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockJungleLog, 0.5F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.cellulose, 8)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockAcaciaLog, 0.5F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.cellulose, 8)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockDarkOakLog, 0.5F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.cellulose, 8)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockOakLog, true, COST_WOOD, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.cellulose, 2), this.molecule(MoleculeEnum.cellulose, 2), this.molecule(MoleculeEnum.cellulose, 2), null, this.molecule(MoleculeEnum.cellulose, 2), null, null, null, null
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockSpruceLog, true, COST_WOOD, new PotionChemical[]
		{
			null, null, null, null, this.molecule(MoleculeEnum.cellulose, 2), null, this.molecule(MoleculeEnum.cellulose, 2), this.molecule(MoleculeEnum.cellulose, 2), this.molecule(MoleculeEnum.cellulose, 2)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockBirchLog, true, COST_WOOD, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.cellulose, 2), null, this.molecule(MoleculeEnum.cellulose, 2), null, null, null, this.molecule(MoleculeEnum.cellulose, 2), null, this.molecule(MoleculeEnum.cellulose, 2)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockJungleLog, true, COST_WOOD, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.cellulose, 2), null, null, this.molecule(MoleculeEnum.cellulose, 2), this.molecule(MoleculeEnum.cellulose, 2), null, this.molecule(MoleculeEnum.cellulose, 2), null, null
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockAcaciaLog, true, COST_WOOD, new PotionChemical[]
		{
			null, null, this.molecule(MoleculeEnum.cellulose, 2), null, this.molecule(MoleculeEnum.cellulose, 2), this.molecule(MoleculeEnum.cellulose, 2), null, null, this.molecule(MoleculeEnum.cellulose, 2)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockDarkOakLog, true, COST_WOOD, new PotionChemical[]
		{
			null, this.molecule(MoleculeEnum.cellulose, 2), null, this.molecule(MoleculeEnum.cellulose, 2), null, this.molecule(MoleculeEnum.cellulose, 2), null, this.molecule(MoleculeEnum.cellulose, 2), null
		}));

		// Leaves
		ItemStack blockOakLeaves = new ItemStack(Blocks.leaves, 1, 0);
		ItemStack blockSpruceLeaves = new ItemStack(Blocks.leaves, 1, 1);
		ItemStack blockBirchLeaves = new ItemStack(Blocks.leaves, 1, 2);
		ItemStack blockJungleLeaves = new ItemStack(Blocks.leaves, 1, 3);
		ItemStack blockAcaciaLeaves = new ItemStack(Blocks.leaves2, 1, 0);
		ItemStack blockDarkOakLeaves = new ItemStack(Blocks.leaves2, 1, 1);
		DecomposerRecipe.add(new DecomposerRecipeChance(blockOakLeaves, 0.5F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.cellulose, 4)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockSpruceLeaves, 0.5F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.cellulose, 4)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockBirchLeaves, 0.5F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.cellulose, 4)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockJungleLeaves, 0.5F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.cellulose, 4)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockAcaciaLeaves, 0.5F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.cellulose, 4)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockDarkOakLeaves, 0.5F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.cellulose, 4)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockOakLeaves, true, COST_BLOCK, new PotionChemical[]
		{
			moleculeCellulose, moleculeCellulose, moleculeCellulose, null, moleculeCellulose, null, null, null, null
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockSpruceLeaves, true, COST_BLOCK, new PotionChemical[]
		{
			null, null, null, null, moleculeCellulose, null, moleculeCellulose, moleculeCellulose, moleculeCellulose
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockBirchLeaves, true, COST_BLOCK, new PotionChemical[]
		{
			moleculeCellulose, null, moleculeCellulose, null, null, null, moleculeCellulose, null, moleculeCellulose
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockJungleLeaves, true, COST_BLOCK, new PotionChemical[]
		{
			moleculeCellulose, null, null, moleculeCellulose, moleculeCellulose, null, moleculeCellulose, null, null
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockAcaciaLeaves, true, COST_BLOCK, new PotionChemical[]
		{
			null, null, moleculeCellulose, null, moleculeCellulose, moleculeCellulose, null, null, moleculeCellulose
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockDarkOakLeaves, true, COST_BLOCK, new PotionChemical[]
		{
			null, moleculeCellulose, null, moleculeCellulose, null, moleculeCellulose, null, moleculeCellulose, null
		}));

		// Glass
		ItemStack blockGlass = new ItemStack(Blocks.glass);
		ItemStack blockWhiteStainedGlass = new ItemStack(Blocks.stained_glass, 1, 0);
		ItemStack blockOrangeStainedGlass = new ItemStack(Blocks.stained_glass, 1, 1);
		ItemStack blockMagentaStainedGlass = new ItemStack(Blocks.stained_glass, 1, 2);
		ItemStack blockLiteBlueStainedGlass = new ItemStack(Blocks.stained_glass, 1, 3);
		ItemStack blockYellowStainedGlass = new ItemStack(Blocks.stained_glass, 1, 4);
		ItemStack blockLimeStainedGlass = new ItemStack(Blocks.stained_glass, 1, 5);
		ItemStack blockPinkStainedGlass = new ItemStack(Blocks.stained_glass, 1, 6);
		ItemStack blockGrayStainedGlass = new ItemStack(Blocks.stained_glass, 1, 7);
		ItemStack blockLiteGrayStainedGlass = new ItemStack(Blocks.stained_glass, 1, 8);
		ItemStack blockCyanStainedGlass = new ItemStack(Blocks.stained_glass, 1, 9);
		ItemStack blockPurpleStainedGlass = new ItemStack(Blocks.stained_glass, 1, 10);
		ItemStack blockBlueStainedGlass = new ItemStack(Blocks.stained_glass, 1, 11);
		ItemStack blockBrownStainedGlass = new ItemStack(Blocks.stained_glass, 1, 12);
		ItemStack blockGreenStainedGlass = new ItemStack(Blocks.stained_glass, 1, 13);
		ItemStack blockRedStainedGlass = new ItemStack(Blocks.stained_glass, 1, 14);
		ItemStack blockBlackStainedGlass = new ItemStack(Blocks.stained_glass, 1, 15);
		DecomposerRecipe.add(new DecomposerRecipe(blockGlass, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.siliconDioxide, 16)
		}));
		DecomposerRecipe.add(new DecomposerRecipe(blockWhiteStainedGlass, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.siliconDioxide, 16), this.molecule(MoleculeEnum.whitePigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipe(blockOrangeStainedGlass, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.siliconDioxide, 16), this.molecule(MoleculeEnum.orangePigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipe(blockMagentaStainedGlass, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.siliconDioxide, 16), this.molecule(MoleculeEnum.lightbluePigment), this.molecule(MoleculeEnum.redPigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipe(blockLiteBlueStainedGlass, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.siliconDioxide, 16), this.molecule(MoleculeEnum.lazurite), this.molecule(MoleculeEnum.whitePigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipe(blockYellowStainedGlass, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.siliconDioxide, 16), this.molecule(MoleculeEnum.yellowPigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipe(blockLimeStainedGlass, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.siliconDioxide, 16), this.molecule(MoleculeEnum.limePigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipe(blockPinkStainedGlass, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.siliconDioxide, 16), this.molecule(MoleculeEnum.redPigment), this.molecule(MoleculeEnum.whitePigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipe(blockGrayStainedGlass, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.siliconDioxide, 16), this.molecule(MoleculeEnum.blackPigment), this.molecule(MoleculeEnum.whitePigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipe(blockCyanStainedGlass, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.siliconDioxide, 16), this.molecule(MoleculeEnum.lightbluePigment), this.molecule(MoleculeEnum.whitePigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipe(blockPurpleStainedGlass, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.siliconDioxide, 16), this.molecule(MoleculeEnum.purplePigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipe(blockGreenStainedGlass, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.siliconDioxide, 16), this.molecule(MoleculeEnum.greenPigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipe(blockBrownStainedGlass, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.siliconDioxide, 16), this.molecule(MoleculeEnum.tannicacid)
		}));
		DecomposerRecipe.add(new DecomposerRecipe(blockRedStainedGlass, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.siliconDioxide, 16), this.molecule(MoleculeEnum.redPigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipe(blockBlackStainedGlass, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.siliconDioxide, 16), this.molecule(MoleculeEnum.blackPigment)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockGlass, true, COST_GLASS, new PotionChemical[]
		{
			moleculeSiliconDioxide, null, moleculeSiliconDioxide, null, null, null, moleculeSiliconDioxide, null, moleculeSiliconDioxide
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockWhiteStainedGlass, true, COST_GLASS, new PotionChemical[]
		{
			moleculeSiliconDioxide, null, moleculeSiliconDioxide, null, this.molecule(MoleculeEnum.whitePigment), null, moleculeSiliconDioxide, null, moleculeSiliconDioxide
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockOrangeStainedGlass, true, COST_GLASS, new PotionChemical[]
		{
			moleculeSiliconDioxide, null, moleculeSiliconDioxide, null, this.molecule(MoleculeEnum.orangePigment), null, moleculeSiliconDioxide, null, moleculeSiliconDioxide
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockMagentaStainedGlass, true, COST_GLASS, new PotionChemical[]
		{
			moleculeSiliconDioxide, null, moleculeSiliconDioxide, this.molecule(MoleculeEnum.lightbluePigment), null, this.molecule(MoleculeEnum.redPigment), moleculeSiliconDioxide, null, moleculeSiliconDioxide
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockLiteBlueStainedGlass, true, COST_GLASS, new PotionChemical[]
		{
			moleculeSiliconDioxide, null, moleculeSiliconDioxide, null, this.molecule(MoleculeEnum.lightbluePigment), null, moleculeSiliconDioxide, null, moleculeSiliconDioxide
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockYellowStainedGlass, true, COST_GLASS, new PotionChemical[]
		{
			moleculeSiliconDioxide, null, moleculeSiliconDioxide, null, this.molecule(MoleculeEnum.yellowPigment), null, moleculeSiliconDioxide, null, moleculeSiliconDioxide
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockLimeStainedGlass, true, COST_GLASS, new PotionChemical[]
		{
			moleculeSiliconDioxide, null, moleculeSiliconDioxide, null, this.molecule(MoleculeEnum.limePigment), null, moleculeSiliconDioxide, null, moleculeSiliconDioxide
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockPinkStainedGlass, true, COST_GLASS, new PotionChemical[]
		{
			moleculeSiliconDioxide, null, moleculeSiliconDioxide, this.molecule(MoleculeEnum.whitePigment), null, this.molecule(MoleculeEnum.redPigment), moleculeSiliconDioxide, null, moleculeSiliconDioxide
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockGrayStainedGlass, true, COST_GLASS, new PotionChemical[]
		{
			moleculeSiliconDioxide, null, moleculeSiliconDioxide, this.molecule(MoleculeEnum.whitePigment), null, this.molecule(MoleculeEnum.blackPigment), moleculeSiliconDioxide, null, moleculeSiliconDioxide
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockLiteGrayStainedGlass, true, COST_GLASS, new PotionChemical[]
		{
			moleculeSiliconDioxide, null, moleculeSiliconDioxide, this.molecule(MoleculeEnum.whitePigment), this.molecule(MoleculeEnum.whitePigment), this.molecule(MoleculeEnum.blackPigment), moleculeSiliconDioxide, null, moleculeSiliconDioxide
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockCyanStainedGlass, true, COST_GLASS, new PotionChemical[]
		{
			moleculeSiliconDioxide, null, moleculeSiliconDioxide, this.molecule(MoleculeEnum.whitePigment), null, this.molecule(MoleculeEnum.lightbluePigment), moleculeSiliconDioxide, null, moleculeSiliconDioxide
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockPurpleStainedGlass, true, COST_GLASS, new PotionChemical[]
		{
			moleculeSiliconDioxide, null, moleculeSiliconDioxide, null, this.molecule(MoleculeEnum.purplePigment), null, moleculeSiliconDioxide, null, moleculeSiliconDioxide
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockBlueStainedGlass, true, COST_GLASS, new PotionChemical[]
		{
			moleculeSiliconDioxide, null, moleculeSiliconDioxide, null, this.molecule(MoleculeEnum.lazurite), null, moleculeSiliconDioxide, null, moleculeSiliconDioxide
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockBrownStainedGlass, true, COST_GLASS, new PotionChemical[]
		{
			moleculeSiliconDioxide, null, moleculeSiliconDioxide, null, this.molecule(MoleculeEnum.tannicacid), null, moleculeSiliconDioxide, null, moleculeSiliconDioxide
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockGreenStainedGlass, true, COST_GLASS, new PotionChemical[]
		{
			moleculeSiliconDioxide, null, moleculeSiliconDioxide, null, this.molecule(MoleculeEnum.greenPigment), null, moleculeSiliconDioxide, null, moleculeSiliconDioxide
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockRedStainedGlass, true, COST_GLASS, new PotionChemical[]
		{
			moleculeSiliconDioxide, null, moleculeSiliconDioxide, null, this.molecule(MoleculeEnum.redPigment), null, moleculeSiliconDioxide, null, moleculeSiliconDioxide
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockBlackStainedGlass, true, COST_GLASS, new PotionChemical[]
		{
			moleculeSiliconDioxide, null, moleculeSiliconDioxide, null, this.molecule(MoleculeEnum.blackPigment), null, moleculeSiliconDioxide, null, moleculeSiliconDioxide
		}));

		// Glass Panes
		ItemStack blockGlassPane = new ItemStack(Blocks.glass_pane);
		ItemStack blockWhiteStainedGlassPane = new ItemStack(Blocks.stained_glass_pane, 1, 0);
		ItemStack blockOrangeStainedGlassPane = new ItemStack(Blocks.stained_glass_pane, 1, 1);
		ItemStack blockMagentaStainedGlassPane = new ItemStack(Blocks.stained_glass_pane, 1, 2);
		ItemStack blockLiteBlueStainedGlassPane = new ItemStack(Blocks.stained_glass_pane, 1, 3);
		ItemStack blockYellowStainedGlassPane = new ItemStack(Blocks.stained_glass_pane, 1, 4);
		ItemStack blockLimeStainedGlassPane = new ItemStack(Blocks.stained_glass_pane, 1, 5);
		ItemStack blockPinkStainedGlassPane = new ItemStack(Blocks.stained_glass_pane, 1, 6);
		ItemStack blockGrayStainedGlassPane = new ItemStack(Blocks.stained_glass_pane, 1, 7);
		ItemStack blockLiteGrayStainedGlassPane = new ItemStack(Blocks.stained_glass_pane, 1, 8);
		ItemStack blockCyanStainedGlassPane = new ItemStack(Blocks.stained_glass_pane, 1, 9);
		ItemStack blockPurpleStainedGlassPane = new ItemStack(Blocks.stained_glass_pane, 1, 10);
		ItemStack blockBlueStainedGlassPane = new ItemStack(Blocks.stained_glass_pane, 1, 11);
		ItemStack blockBrownStainedGlassPane = new ItemStack(Blocks.stained_glass_pane, 1, 12);
		ItemStack blockGreenStainedGlassPane = new ItemStack(Blocks.stained_glass_pane, 1, 13);
		ItemStack blockRedStainedGlassPane = new ItemStack(Blocks.stained_glass_pane, 1, 14);
		ItemStack blockBlackStainedGlassPane = new ItemStack(Blocks.stained_glass_pane, 1, 15);
		DecomposerRecipe.add(new DecomposerRecipe(blockGlassPane, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.siliconDioxide, 1)
		}));
		DecomposerRecipe.add(new DecomposerRecipe(blockWhiteStainedGlassPane, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.siliconDioxide, 1), this.molecule(MoleculeEnum.whitePigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipe(blockOrangeStainedGlassPane, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.siliconDioxide, 1), this.molecule(MoleculeEnum.orangePigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipe(blockMagentaStainedGlassPane, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.siliconDioxide, 1), this.molecule(MoleculeEnum.lightbluePigment), this.molecule(MoleculeEnum.redPigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipe(blockLiteBlueStainedGlassPane, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.siliconDioxide, 1), this.molecule(MoleculeEnum.lazurite), this.molecule(MoleculeEnum.whitePigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipe(blockYellowStainedGlassPane, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.siliconDioxide, 1), this.molecule(MoleculeEnum.yellowPigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipe(blockLimeStainedGlassPane, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.siliconDioxide, 1), this.molecule(MoleculeEnum.limePigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipe(blockPinkStainedGlassPane, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.siliconDioxide, 1), this.molecule(MoleculeEnum.redPigment), this.molecule(MoleculeEnum.whitePigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipe(blockGrayStainedGlassPane, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.siliconDioxide, 1), this.molecule(MoleculeEnum.blackPigment), this.molecule(MoleculeEnum.whitePigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipe(blockCyanStainedGlassPane, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.siliconDioxide, 1), this.molecule(MoleculeEnum.lightbluePigment), this.molecule(MoleculeEnum.whitePigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipe(blockPurpleStainedGlassPane, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.siliconDioxide, 1), this.molecule(MoleculeEnum.purplePigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipe(blockGreenStainedGlassPane, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.siliconDioxide, 1), this.molecule(MoleculeEnum.greenPigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipe(blockBrownStainedGlassPane, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.siliconDioxide, 1), this.molecule(MoleculeEnum.tannicacid)
		}));
		DecomposerRecipe.add(new DecomposerRecipe(blockRedStainedGlassPane, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.siliconDioxide, 1), this.molecule(MoleculeEnum.redPigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipe(blockBlackStainedGlassPane, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.siliconDioxide, 1), this.molecule(MoleculeEnum.blackPigment)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockGlassPane, true, COST_PANE, new PotionChemical[]
		{
			moleculeSiliconDioxide, null, null, null, null, null, null, null, null
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockWhiteStainedGlassPane, true, COST_PANE, new PotionChemical[]
		{
			moleculeSiliconDioxide, null, moleculeSiliconDioxide, null, this.molecule(MoleculeEnum.whitePigment), null, moleculeSiliconDioxide, null, moleculeSiliconDioxide
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockOrangeStainedGlassPane, true, COST_PANE, new PotionChemical[]
		{
			moleculeSiliconDioxide, null, moleculeSiliconDioxide, null, this.molecule(MoleculeEnum.orangePigment), null, moleculeSiliconDioxide, null, moleculeSiliconDioxide
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockMagentaStainedGlassPane, true, COST_PANE, new PotionChemical[]
		{
			moleculeSiliconDioxide, null, moleculeSiliconDioxide, this.molecule(MoleculeEnum.lightbluePigment), null, this.molecule(MoleculeEnum.redPigment), moleculeSiliconDioxide, null, moleculeSiliconDioxide
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockLiteBlueStainedGlassPane, true, COST_PANE, new PotionChemical[]
		{
			moleculeSiliconDioxide, null, moleculeSiliconDioxide, null, this.molecule(MoleculeEnum.lightbluePigment), null, moleculeSiliconDioxide, null, moleculeSiliconDioxide
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockYellowStainedGlassPane, true, COST_PANE, new PotionChemical[]
		{
			moleculeSiliconDioxide, null, moleculeSiliconDioxide, null, this.molecule(MoleculeEnum.yellowPigment), null, moleculeSiliconDioxide, null, moleculeSiliconDioxide
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockLimeStainedGlassPane, true, COST_PANE, new PotionChemical[]
		{
			moleculeSiliconDioxide, null, moleculeSiliconDioxide, null, this.molecule(MoleculeEnum.limePigment), null, moleculeSiliconDioxide, null, moleculeSiliconDioxide
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockPinkStainedGlassPane, true, COST_PANE, new PotionChemical[]
		{
			moleculeSiliconDioxide, null, moleculeSiliconDioxide, this.molecule(MoleculeEnum.whitePigment), null, this.molecule(MoleculeEnum.redPigment), moleculeSiliconDioxide, null, moleculeSiliconDioxide
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockGrayStainedGlassPane, true, COST_PANE, new PotionChemical[]
		{
			moleculeSiliconDioxide, null, moleculeSiliconDioxide, this.molecule(MoleculeEnum.whitePigment), null, this.molecule(MoleculeEnum.blackPigment), moleculeSiliconDioxide, null, moleculeSiliconDioxide
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockLiteGrayStainedGlassPane, true, COST_PANE, new PotionChemical[]
		{
			moleculeSiliconDioxide, null, moleculeSiliconDioxide, this.molecule(MoleculeEnum.whitePigment), this.molecule(MoleculeEnum.whitePigment), this.molecule(MoleculeEnum.blackPigment), moleculeSiliconDioxide, null, moleculeSiliconDioxide
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockCyanStainedGlassPane, true, COST_PANE, new PotionChemical[]
		{
			moleculeSiliconDioxide, null, moleculeSiliconDioxide, this.molecule(MoleculeEnum.whitePigment), null, this.molecule(MoleculeEnum.lightbluePigment), moleculeSiliconDioxide, null, moleculeSiliconDioxide
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockPurpleStainedGlassPane, true, COST_PANE, new PotionChemical[]
		{
			moleculeSiliconDioxide, null, moleculeSiliconDioxide, null, this.molecule(MoleculeEnum.purplePigment), null, moleculeSiliconDioxide, null, moleculeSiliconDioxide
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockBlueStainedGlassPane, true, COST_PANE, new PotionChemical[]
		{
			moleculeSiliconDioxide, null, moleculeSiliconDioxide, null, this.molecule(MoleculeEnum.lazurite), null, moleculeSiliconDioxide, null, moleculeSiliconDioxide
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockBrownStainedGlassPane, true, COST_PANE, new PotionChemical[]
		{
			moleculeSiliconDioxide, null, moleculeSiliconDioxide, null, this.molecule(MoleculeEnum.tannicacid), null, moleculeSiliconDioxide, null, moleculeSiliconDioxide
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockGreenStainedGlassPane, true, COST_PANE, new PotionChemical[]
		{
			moleculeSiliconDioxide, null, moleculeSiliconDioxide, null, this.molecule(MoleculeEnum.greenPigment), null, moleculeSiliconDioxide, null, moleculeSiliconDioxide
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockRedStainedGlassPane, true, COST_PANE, new PotionChemical[]
		{
			moleculeSiliconDioxide, null, moleculeSiliconDioxide, null, this.molecule(MoleculeEnum.redPigment), null, moleculeSiliconDioxide, null, moleculeSiliconDioxide
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockBlackStainedGlassPane, true, COST_PANE, new PotionChemical[]
		{
			moleculeSiliconDioxide, null, moleculeSiliconDioxide, null, this.molecule(MoleculeEnum.blackPigment), null, moleculeSiliconDioxide, null, moleculeSiliconDioxide
		}));

		// Lapis Lazuli Ore
		ItemStack blockOreLapis = new ItemStack(Blocks.lapis_ore);
		DecomposerRecipe.add(new DecomposerRecipe(blockOreLapis, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.lazurite, 6), this.molecule(MoleculeEnum.sodalite), this.molecule(MoleculeEnum.noselite), this.molecule(MoleculeEnum.calciumCarbonate), this.molecule(MoleculeEnum.pyrite)
		}));

		// Lapis Lazuli Block
		ItemStack blockLapis = new ItemStack(Blocks.lapis_block);
		DecomposerRecipe.add(new DecomposerRecipe(blockLapis, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.lazurite, 9)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockLapis, true, COST_LAPISBLOCK, new PotionChemical[]
		{
			moleculeLazurite, null, null, null, null, null, null, null, null
		}));

		// Cobweb
		ItemStack blockCobweb = new ItemStack(Blocks.web);
		DecomposerRecipe.add(new DecomposerRecipe(blockCobweb, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.fibroin)
		}));

		//double tall plants
		ItemStack blockSunFlower = new ItemStack(Blocks.double_plant, 1, 0);
		ItemStack blockLilac = new ItemStack(Blocks.double_plant, 1, 1);
		ItemStack blockTallGrass = new ItemStack(Blocks.double_plant, 1, 2);
		ItemStack blockLargeFern = new ItemStack(Blocks.double_plant, 1, 3);
		ItemStack blockRoseBush = new ItemStack(Blocks.double_plant, 1, 4);
		ItemStack blockPeony = new ItemStack(Blocks.double_plant, 1, 5);
		DecomposerRecipe.add(new DecomposerRecipeChance(blockSunFlower, 0.3F, new PotionChemical[]
		{
			new Molecule(MoleculeEnum.shikimicAcid, 2)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockLilac, 0.3F, new PotionChemical[]
		{
			new Molecule(MoleculeEnum.shikimicAcid, 2)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockTallGrass, 0.3F, new PotionChemical[]
		{
			new Molecule(MoleculeEnum.shikimicAcid, 2)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockLargeFern, 0.3F, new PotionChemical[]
		{
			new Molecule(MoleculeEnum.shikimicAcid, 2)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockRoseBush, 0.3F, new PotionChemical[]
		{
			new Molecule(MoleculeEnum.shikimicAcid, 2)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockPeony, 0.3F, new PotionChemical[]
		{
			new Molecule(MoleculeEnum.shikimicAcid, 2)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockSunFlower, true, COST_PLANT, new PotionChemical[]
		{
			new Molecule(MoleculeEnum.shikimicAcid, 2), new Molecule(MoleculeEnum.yellowPigment), null, null, null, null, null, null, null
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockLilac, true, COST_PLANT, new PotionChemical[]
		{
			new Molecule(MoleculeEnum.shikimicAcid, 2), new Molecule(MoleculeEnum.redPigment), new Molecule(MoleculeEnum.whitePigment, 2), null, null, null, null, null, null
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockTallGrass, true, COST_PLANT, new PotionChemical[]
		{
			new Molecule(MoleculeEnum.shikimicAcid, 2), null, null, null, null, null, null, null, null
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockLargeFern, true, COST_PLANT, new PotionChemical[]
		{
			null, new Molecule(MoleculeEnum.shikimicAcid, 2), null, null, null, null, null, null, null
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockRoseBush, true, COST_PLANT, new PotionChemical[]
		{
			new Molecule(MoleculeEnum.shikimicAcid, 2), new Molecule(MoleculeEnum.redPigment), null, null, null, null, null, null, null
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockPeony, true, COST_PLANT, new PotionChemical[]
		{
			new Molecule(MoleculeEnum.shikimicAcid, 2), new Molecule(MoleculeEnum.redPigment), new Molecule(MoleculeEnum.whitePigment), null, null, null, null, null, null
		}));

		// Sandstone
		ItemStack blockSandStone = new ItemStack(Blocks.sandstone, 1, 0);
		ItemStack blockChiseledSandStone = new ItemStack(Blocks.sandstone, 1, 1);
		ItemStack blockSmoothSandStone = new ItemStack(Blocks.sandstone, 1, 2);
		DecomposerRecipe.add(new DecomposerRecipe(blockSandStone, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.siliconDioxide, 16)
		}));
		DecomposerRecipe.add(new DecomposerRecipe(blockChiseledSandStone, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.siliconDioxide, 16)
		}));
		DecomposerRecipe.add(new DecomposerRecipe(blockSmoothSandStone, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.siliconDioxide, 16)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockSandStone, true, COST_BLOCK, new PotionChemical[]
		{
			null, null, null, null, this.molecule(MoleculeEnum.siliconDioxide, 16), null, null, null, null
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockChiseledSandStone, true, COST_BLOCK, new PotionChemical[]
		{
			null, null, null, null, null, null, null, this.molecule(MoleculeEnum.siliconDioxide, 16), null
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockSmoothSandStone, true, COST_BLOCK, new PotionChemical[]
		{
			null, this.molecule(MoleculeEnum.siliconDioxide, 16), null, null, null, null, null, null, null
		}));

		// Wool
		ItemStack blockWool = new ItemStack(Blocks.wool, 1, 0);
		ItemStack blockOrangeWool = new ItemStack(Blocks.wool, 1, 1);
		ItemStack blockMagentaWool = new ItemStack(Blocks.wool, 1, 2);
		ItemStack blockLightBlueWool = new ItemStack(Blocks.wool, 1, 3);
		ItemStack blockYellowWool = new ItemStack(Blocks.wool, 1, 4);
		ItemStack blockLimeWool = new ItemStack(Blocks.wool, 1, 5);
		ItemStack blockPinkWool = new ItemStack(Blocks.wool, 1, 6);
		ItemStack blockGrayWool = new ItemStack(Blocks.wool, 1, 7);
		ItemStack blockLightGrayWool = new ItemStack(Blocks.wool, 1, 8);
		ItemStack blockCyanWool = new ItemStack(Blocks.wool, 1, 9);
		ItemStack blockPurpleWool = new ItemStack(Blocks.wool, 1, 10);
		ItemStack blockBlueWool = new ItemStack(Blocks.wool, 1, 11);
		ItemStack blockBrownWool = new ItemStack(Blocks.wool, 1, 12);
		ItemStack blockGreenWool = new ItemStack(Blocks.wool, 1, 13);
		ItemStack blockRedWool = new ItemStack(Blocks.wool, 1, 14);
		ItemStack blockBlackWool = new ItemStack(Blocks.wool, 1, 15);
		DecomposerRecipe.add(new DecomposerRecipeChance(blockWool, 0.6F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine, 2), this.molecule(MoleculeEnum.whitePigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockOrangeWool, 0.6F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine, 2), this.molecule(MoleculeEnum.orangePigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockMagentaWool, 0.6F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine, 2), this.molecule(MoleculeEnum.lightbluePigment), this.molecule(MoleculeEnum.redPigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockLightBlueWool, 0.6F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine, 2), this.molecule(MoleculeEnum.lightbluePigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockYellowWool, 0.6F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine, 2), this.molecule(MoleculeEnum.yellowPigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockLimeWool, 0.6F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine, 2), this.molecule(MoleculeEnum.limePigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockPinkWool, 0.6F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine, 2), this.molecule(MoleculeEnum.redPigment), this.molecule(MoleculeEnum.whitePigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockGrayWool, 0.6F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine, 2), this.molecule(MoleculeEnum.whitePigment), this.molecule(MoleculeEnum.blackPigment, 2)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockLightGrayWool, 0.6F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine, 2), this.molecule(MoleculeEnum.whitePigment), this.molecule(MoleculeEnum.blackPigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockCyanWool, 0.6F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine, 2), this.molecule(MoleculeEnum.lightbluePigment), this.molecule(MoleculeEnum.whitePigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockPurpleWool, 0.6F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine, 2), this.molecule(MoleculeEnum.purplePigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockBlueWool, 0.6F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine, 2), this.molecule(MoleculeEnum.lazurite)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockBrownWool, 0.6F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine, 2), this.molecule(MoleculeEnum.tannicacid)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockGreenWool, 0.6F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine, 2), this.molecule(MoleculeEnum.greenPigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockRedWool, 0.6F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine, 2), this.molecule(MoleculeEnum.redPigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockBlackWool, 0.6F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine, 2), this.molecule(MoleculeEnum.blackPigment)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockWool, false, COST_WOOL, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine, 2), this.molecule(MoleculeEnum.whitePigment)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockOrangeWool, false, COST_WOOL, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine, 2), this.molecule(MoleculeEnum.orangePigment)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockMagentaWool, false, COST_WOOL, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine, 2), this.molecule(MoleculeEnum.lightbluePigment), this.molecule(MoleculeEnum.redPigment)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockLightBlueWool, false, COST_WOOL, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine, 2), this.molecule(MoleculeEnum.lightbluePigment)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockYellowWool, false, COST_WOOL, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine, 2), this.molecule(MoleculeEnum.yellowPigment)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockLimeWool, false, COST_WOOL, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine, 2), this.molecule(MoleculeEnum.limePigment)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockPinkWool, false, COST_WOOL, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine, 2), this.molecule(MoleculeEnum.redPigment), this.molecule(MoleculeEnum.whitePigment)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockGrayWool, false, COST_WOOL, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine, 2), this.molecule(MoleculeEnum.whitePigment), this.molecule(MoleculeEnum.blackPigment, 2)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockLightGrayWool, false, COST_WOOL, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine, 2), this.molecule(MoleculeEnum.whitePigment), this.molecule(MoleculeEnum.blackPigment)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockCyanWool, false, COST_WOOL, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine, 2), this.molecule(MoleculeEnum.lightbluePigment), this.molecule(MoleculeEnum.whitePigment)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockPurpleWool, false, COST_WOOL, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine, 2), this.molecule(MoleculeEnum.purplePigment)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockBlueWool, false, COST_WOOL, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine, 2), this.molecule(MoleculeEnum.lazurite)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockGreenWool, false, COST_WOOL, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine, 2), this.molecule(MoleculeEnum.greenPigment)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockRedWool, false, COST_WOOL, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine, 2), this.molecule(MoleculeEnum.redPigment)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockBlackWool, false, COST_WOOL, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine, 2), this.molecule(MoleculeEnum.blackPigment)
		}));

		// Wool carpet
		ItemStack carpetBlockWool = new ItemStack(Blocks.carpet, 1, 0);
		ItemStack carpetBlockOrangeWool = new ItemStack(Blocks.carpet, 1, 1);
		ItemStack carpetBlockMagentaWool = new ItemStack(Blocks.carpet, 1, 2);
		ItemStack carpetBlockLightBlueWool = new ItemStack(Blocks.carpet, 1, 3);
		ItemStack carpetBlockYellowWool = new ItemStack(Blocks.carpet, 1, 4);
		ItemStack carpetBlockLimeWool = new ItemStack(Blocks.carpet, 1, 5);
		ItemStack carpetBlockPinkWool = new ItemStack(Blocks.carpet, 1, 6);
		ItemStack carpetBlockGrayWool = new ItemStack(Blocks.carpet, 1, 7);
		ItemStack carpetBlockLightGrayWool = new ItemStack(Blocks.carpet, 1, 8);
		ItemStack carpetBlockCyanWool = new ItemStack(Blocks.carpet, 1, 9);
		ItemStack carpetBlockPurpleWool = new ItemStack(Blocks.carpet, 1, 10);
		ItemStack carpetBlockBlueWool = new ItemStack(Blocks.carpet, 1, 11);
		ItemStack carpetBlockBrownWool = new ItemStack(Blocks.carpet, 1, 12);
		ItemStack carpetBlockGreenWool = new ItemStack(Blocks.carpet, 1, 13);
		ItemStack carpetBlockRedWool = new ItemStack(Blocks.carpet, 1, 14);
		ItemStack carpetBlockBlackWool = new ItemStack(Blocks.carpet, 1, 15);
		DecomposerRecipe.add(new DecomposerRecipeChance(carpetBlockWool, 0.4F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.whitePigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(carpetBlockOrangeWool, 0.4F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.orangePigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(carpetBlockMagentaWool, 0.4F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.lightbluePigment), this.molecule(MoleculeEnum.redPigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(carpetBlockLightBlueWool, 0.2F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.lightbluePigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(carpetBlockYellowWool, 0.4F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.yellowPigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(carpetBlockLimeWool, 0.4F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.limePigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(carpetBlockPinkWool, 0.4F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.redPigment), this.molecule(MoleculeEnum.whitePigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(carpetBlockGrayWool, 0.4F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.whitePigment), this.molecule(MoleculeEnum.blackPigment, 2)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(carpetBlockLightGrayWool, 0.4F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.whitePigment), this.molecule(MoleculeEnum.blackPigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(carpetBlockCyanWool, 0.4F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.lightbluePigment), this.molecule(MoleculeEnum.whitePigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(carpetBlockPurpleWool, 0.4F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.purplePigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(carpetBlockBlueWool, 0.4F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.lazurite)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(carpetBlockBrownWool, 0.4F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.tannicacid)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(carpetBlockGreenWool, 0.4F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.greenPigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(carpetBlockRedWool, 0.4F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.redPigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(carpetBlockBlackWool, 0.4F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.blackPigment)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(carpetBlockWool, false, COST_CARPET, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.whitePigment)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(carpetBlockOrangeWool, false, COST_CARPET, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.orangePigment)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(carpetBlockMagentaWool, false, COST_CARPET, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.lightbluePigment), this.molecule(MoleculeEnum.redPigment)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(carpetBlockLightBlueWool, false, COST_CARPET, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.lightbluePigment)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(carpetBlockYellowWool, false, COST_CARPET, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.yellowPigment)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(carpetBlockLimeWool, false, COST_CARPET, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.limePigment)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(carpetBlockPinkWool, false, COST_CARPET, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.redPigment), this.molecule(MoleculeEnum.whitePigment)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(carpetBlockGrayWool, false, COST_CARPET, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.whitePigment), this.molecule(MoleculeEnum.blackPigment, 2)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(carpetBlockLightGrayWool, false, COST_CARPET, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.whitePigment), this.molecule(MoleculeEnum.blackPigment)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(carpetBlockCyanWool, false, COST_CARPET, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.lightbluePigment), this.molecule(MoleculeEnum.whitePigment)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(carpetBlockPurpleWool, false, COST_CARPET, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.purplePigment)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(carpetBlockBlueWool, false, COST_CARPET, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.lazurite)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(carpetBlockGreenWool, false, COST_CARPET, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.greenPigment)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(carpetBlockRedWool, false, COST_CARPET, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.redPigment)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(carpetBlockBlackWool, false, COST_CARPET, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.blackPigment)
		}));

		// Flowers
		ItemStack blockYellowFlower = new ItemStack(Blocks.yellow_flower);
		ItemStack blockPoppyFlower = new ItemStack(Blocks.red_flower, 1, 0);
		ItemStack blockBlueOrchid = new ItemStack(Blocks.red_flower, 1, 1);
		ItemStack blockAllium = new ItemStack(Blocks.red_flower, 1, 2);
		ItemStack blockAsureBluet = new ItemStack(Blocks.red_flower, 1, 3);
		ItemStack blockRedTulip = new ItemStack(Blocks.red_flower, 1, 4);
		ItemStack blockOrangeTulip = new ItemStack(Blocks.red_flower, 1, 5);
		ItemStack blockWhiteTulip = new ItemStack(Blocks.red_flower, 1, 6);
		ItemStack blockPinkTulip = new ItemStack(Blocks.red_flower, 1, 7);
		ItemStack blockOxeyeDaisy = new ItemStack(Blocks.red_flower, 1, 8);

		DecomposerRecipe.add(new DecomposerRecipeChance(blockYellowFlower, 0.3F, new PotionChemical[]
		{
			new Molecule(MoleculeEnum.shikimicAcid, 2), new Molecule(MoleculeEnum.yellowPigment, 1)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockPoppyFlower, 0.3F, new PotionChemical[]
		{
			new Molecule(MoleculeEnum.shikimicAcid, 2), new Molecule(MoleculeEnum.redPigment, 1)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockBlueOrchid, 0.3F, new PotionChemical[]
		{
			new Molecule(MoleculeEnum.shikimicAcid, 2), this.molecule(MoleculeEnum.lazurite)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockAllium, 0.3F, new PotionChemical[]
		{
			new Molecule(MoleculeEnum.shikimicAcid, 2), new Molecule(MoleculeEnum.purplePigment, 1)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockAsureBluet, 0.3F, new PotionChemical[]
		{
			new Molecule(MoleculeEnum.shikimicAcid, 2), new Molecule(MoleculeEnum.whitePigment, 1)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockRedTulip, 0.3F, new PotionChemical[]
		{
			new Molecule(MoleculeEnum.shikimicAcid, 2), new Molecule(MoleculeEnum.redPigment, 1)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockOrangeTulip, 0.3F, new PotionChemical[]
		{
			new Molecule(MoleculeEnum.shikimicAcid, 2), new Molecule(MoleculeEnum.orangePigment, 1)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockWhiteTulip, 0.3F, new PotionChemical[]
		{
			new Molecule(MoleculeEnum.shikimicAcid, 2), new Molecule(MoleculeEnum.whitePigment, 1)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockPinkTulip, 0.3F, new PotionChemical[]
		{
			new Molecule(MoleculeEnum.shikimicAcid, 2), new Molecule(MoleculeEnum.whitePigment, 1), new Molecule(MoleculeEnum.redPigment, 1)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(blockOxeyeDaisy, 0.3F, new PotionChemical[]
		{
			new Molecule(MoleculeEnum.shikimicAcid, 2), new Molecule(MoleculeEnum.whitePigment, 1)
		}));

		// Mushrooms
		ItemStack blockMushroomBrown = new ItemStack(Blocks.brown_mushroom);
		ItemStack blockMushroomRed = new ItemStack(Blocks.red_mushroom);
		DecomposerRecipe.add(new DecomposerRecipe(blockMushroomBrown, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.psilocybin), this.molecule(MoleculeEnum.water, 2)
		}));
		DecomposerRecipe.add(new DecomposerRecipe(blockMushroomRed, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.pantherine), this.molecule(MoleculeEnum.water, 2)
		}));

		// Block of Gold
		DecomposerRecipe.add(new DecomposerRecipe(new ItemStack(Blocks.gold_block), new PotionChemical[]
		{
			this.element(ElementEnum.Au, 144)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Blocks.gold_block), true, COST_METALBLOCK, new PotionChemical[]
		{
			this.element(ElementEnum.Au, 16), this.element(ElementEnum.Au, 16), this.element(ElementEnum.Au, 16), this.element(ElementEnum.Au, 16), this.element(ElementEnum.Au, 16), this.element(ElementEnum.Au, 16), this.element(ElementEnum.Au, 16), this.element(ElementEnum.Au, 16), this.element(ElementEnum.Au, 16)
		}));

		// Block of Iron
		DecomposerRecipe.add(new DecomposerRecipe(new ItemStack(Blocks.iron_block), new PotionChemical[]
		{
			this.element(ElementEnum.Fe, 144)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Blocks.iron_block), true, COST_METALBLOCK, new PotionChemical[]
		{
			this.element(ElementEnum.Fe, 16), this.element(ElementEnum.Fe, 16), this.element(ElementEnum.Fe, 16), this.element(ElementEnum.Fe, 16), this.element(ElementEnum.Fe, 16), this.element(ElementEnum.Fe, 16), this.element(ElementEnum.Fe, 16), this.element(ElementEnum.Fe, 16), this.element(ElementEnum.Fe, 16)
		}));

		// TNT
		ItemStack blockTnt = new ItemStack(Blocks.tnt);
		DecomposerRecipe.add(new DecomposerRecipe(blockTnt, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.tnt)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockTnt, false, COST_OBSIDIAN, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.tnt)
		}));

		// Obsidian
		ItemStack blockObsidian = new ItemStack(Blocks.obsidian);
		DecomposerRecipe.add(new DecomposerRecipe(blockObsidian, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.siliconDioxide, 16), this.molecule(MoleculeEnum.magnesiumOxide, 8)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockObsidian, true, COST_OBSIDIAN, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.siliconDioxide, 4), this.molecule(MoleculeEnum.siliconDioxide, 4), this.molecule(MoleculeEnum.siliconDioxide, 4), this.molecule(MoleculeEnum.magnesiumOxide, 2), null, this.molecule(MoleculeEnum.siliconDioxide, 4),
			this.molecule(MoleculeEnum.magnesiumOxide, 2), this.molecule(MoleculeEnum.magnesiumOxide, 2), this.molecule(MoleculeEnum.magnesiumOxide, 2)
		}));

		// Diamond Ore
		ItemStack blockOreDiamond = new ItemStack(Blocks.diamond_ore);
		DecomposerRecipe.add(new DecomposerRecipe(blockOreDiamond, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.fullrene, 6)
		}));

		// Block of Diamond
		ItemStack blockDiamond = new ItemStack(Blocks.diamond_block);
		DecomposerRecipe.add(new DecomposerRecipe(blockDiamond, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.fullrene, 36)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockDiamond, true, COST_GEMBLOCK, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.fullrene, 4), this.molecule(MoleculeEnum.fullrene, 4), this.molecule(MoleculeEnum.fullrene, 4), this.molecule(MoleculeEnum.fullrene, 4), this.molecule(MoleculeEnum.fullrene, 4), this.molecule(MoleculeEnum.fullrene, 4),
			this.molecule(MoleculeEnum.fullrene, 4), this.molecule(MoleculeEnum.fullrene, 4), this.molecule(MoleculeEnum.fullrene, 4)
		}));

		// Pressure Plate
		ItemStack blockPressurePlatePlanks = new ItemStack(Blocks.wooden_pressure_plate);
		DecomposerRecipe.add(new DecomposerRecipeChance(blockPressurePlatePlanks, 0.4F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.cellulose, 4)
		}));

		// Redstone Ore
		ItemStack blockOreRedstone = new ItemStack(Blocks.redstone_ore);
		DecomposerRecipe.add(new DecomposerRecipeChance(blockOreRedstone, 0.8F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.iron3oxide, 9), this.element(ElementEnum.Cu, 9)
		}));

		// Cactus
		ItemStack blockCactus = new ItemStack(Blocks.cactus);
		DecomposerRecipe.add(new DecomposerRecipe(blockCactus, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.mescaline), this.molecule(MoleculeEnum.water, 20)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockCactus, true, COST_PLANT, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.water, 5), null, this.molecule(MoleculeEnum.water, 5), null, this.molecule(MoleculeEnum.mescaline), null, this.molecule(MoleculeEnum.water, 5), null, this.molecule(MoleculeEnum.water, 5)
		}));

		// Pumpkin
		ItemStack blockPumpkin = new ItemStack(Blocks.pumpkin);
		DecomposerRecipe.add(new DecomposerRecipe(blockPumpkin, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.cucurbitacin)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockPumpkin, false, COST_PLANT, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.cucurbitacin)
		}));

		// Pumpkin seed
		ItemStack pumpkinSeed = new ItemStack(Items.pumpkin_seeds);
		DecomposerRecipe.add(new DecomposerRecipe(blockPumpkin, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.water)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockPumpkin, false, COST_PLANT, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.cucurbitacin)
		}));

		// Netherrack
		ItemStack blockNetherrack = new ItemStack(Blocks.netherrack);
		DecomposerRecipe.add(new DecomposerRecipeSelect(blockNetherrack, 0.1F, new DecomposerRecipe[]
		{
			new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Si, 2), this.element(ElementEnum.O), this.element(ElementEnum.Fe)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Si, 2), this.element(ElementEnum.Ni), this.element(ElementEnum.Tc)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Si, 3), this.element(ElementEnum.Ti), this.element(ElementEnum.Fe)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Si, 1), this.element(ElementEnum.W, 4), this.element(ElementEnum.Cr, 2)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Si, 10), this.element(ElementEnum.W, 1), this.element(ElementEnum.Zn, 8), this.element(ElementEnum.Be, 4)
			})
		}));

		// Nether Brick
		ItemStack itemNetherbrick = new ItemStack(Items.netherbrick);
		DecomposerRecipe.add(new DecomposerRecipeSelect(itemNetherbrick, 0.15F, new DecomposerRecipe[]
		{
			new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Si, 2), this.element(ElementEnum.C), this.element(ElementEnum.Fe)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Si, 2), this.element(ElementEnum.Ni), this.element(ElementEnum.Tc)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Si, 3), this.element(ElementEnum.Ti), this.element(ElementEnum.Fe)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Si, 1), this.element(ElementEnum.W, 4), this.element(ElementEnum.Cr, 2)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Si, 10), this.element(ElementEnum.W, 1), this.element(ElementEnum.Zn, 8), this.element(ElementEnum.Be, 4)
			})
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemNetherbrick, true, COST_SMOOTH, new PotionChemical[]
		{
			this.element(ElementEnum.Si, 2), this.element(ElementEnum.Si, 2), null, this.element(ElementEnum.Zn, 2), this.element(ElementEnum.W, 1), null, this.element(ElementEnum.Be, 2), this.element(ElementEnum.Be, 2), null
		}));

		// Water Bottle
		ItemStack itemPotion = new ItemStack(Items.potionitem, 1, 0);
		DecomposerRecipe.add(new DecomposerRecipe(itemPotion, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.water, 8)
		}));

		// Soul Sand
		ItemStack blockSlowSand = new ItemStack(Blocks.soul_sand);
		DecomposerRecipe.add(new DecomposerRecipeSelect(blockSlowSand, 0.2F, new DecomposerRecipe[]
		{
			new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Pb, 3), this.element(ElementEnum.Be, 1), this.element(ElementEnum.Si, 2), this.element(ElementEnum.O)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Pb, 1), this.element(ElementEnum.Si, 5), this.element(ElementEnum.O, 2)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Si, 2), this.element(ElementEnum.O)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Si, 6), this.element(ElementEnum.O, 2)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Es, 1), this.element(ElementEnum.O, 2)
			})
		}));

		// Glowstone
		ItemStack blockGlowStone = new ItemStack(Blocks.glowstone);
		DecomposerRecipe.add(new DecomposerRecipe(blockGlowStone, new PotionChemical[]
		{
			this.element(ElementEnum.P, 4)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockGlowStone, true, COST_GLOWBLOCK, new PotionChemical[]
		{
			this.element(ElementEnum.P), null, this.element(ElementEnum.P), this.element(ElementEnum.P), null, this.element(ElementEnum.P), null, null, null
		}));

		// Mycelium
		ItemStack blockMycelium = new ItemStack(Blocks.mycelium);
		DecomposerRecipe.add(new DecomposerRecipeChance(blockMycelium, 0.09F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.fingolimod)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Blocks.mycelium, 16), false, COST_GRASS, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.fingolimod)
		}));

		// End Stone
		ItemStack blockWhiteStone = new ItemStack(Blocks.end_stone);
		DecomposerRecipe.add(new DecomposerRecipeSelect(blockWhiteStone, 0.8F, new DecomposerRecipe[]
		{
			new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Si, 2), this.element(ElementEnum.O), this.element(ElementEnum.H, 4), this.element(ElementEnum.Li)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Es)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Pu)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Fr)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Nd)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Si, 2), this.element(ElementEnum.O, 4)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.H, 4)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Be, 8)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Li, 2)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Zr)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Na)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Rb)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Ga), this.element(ElementEnum.As)
			})
		}));

		// Emerald Ore
		ItemStack blockOreEmerald = new ItemStack(Blocks.emerald_ore);
		DecomposerRecipe.add(new DecomposerRecipe(blockOreEmerald, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.beryl, 6), this.element(ElementEnum.Cr, 6), this.element(ElementEnum.V, 6)
		}));

		// Emerald Block
		ItemStack blockEmerald = new ItemStack(Blocks.emerald_block);
		SynthesisRecipe.add(new SynthesisRecipe(blockEmerald, true, COST_GEMBLOCK, new PotionChemical[]
		{
			this.element(ElementEnum.Cr, 3), this.element(ElementEnum.Cr, 3), this.element(ElementEnum.Cr, 3), this.element(ElementEnum.V, 9), this.molecule(MoleculeEnum.beryl, 18), this.element(ElementEnum.V, 9), this.element(ElementEnum.Cr, 3),
			this.element(ElementEnum.Cr, 3), this.element(ElementEnum.Cr, 3)
		}));
		DecomposerRecipe.add(new DecomposerRecipe(blockEmerald, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.beryl, 18), this.element(ElementEnum.Cr, 18), this.element(ElementEnum.V, 18)
		}));

		// Section 2 - Items
		// Apple
		ItemStack itemAppleRed = new ItemStack(Items.apple);
		DecomposerRecipe.add(new DecomposerRecipe(itemAppleRed, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.malicAcid)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemAppleRed, false, COST_FOOD, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.malicAcid), this.molecule(MoleculeEnum.water, 2)
		}));

		// Arrow
		ItemStack itemArrow = new ItemStack(Items.arrow);
		DecomposerRecipe.add(new DecomposerRecipe(itemArrow, new PotionChemical[]
		{
			this.element(ElementEnum.Si), this.element(ElementEnum.O, 2), this.element(ElementEnum.N, 6)
		}));

		// Coal
		ItemStack itemCoal = new ItemStack(Items.coal);
		DecomposerRecipe.add(new DecomposerRecipeChance(itemCoal, 0.92F, new PotionChemical[]
		{
			this.element(ElementEnum.C, 8)
		}));

		// Coal Block
		ItemStack blockCoal = new ItemStack(Blocks.coal_block);
		DecomposerRecipe.add(new DecomposerRecipeChance(blockCoal, 0.82F, new PotionChemical[]
		{
			this.element(ElementEnum.C, 72)
		}));

		// Charcoal
		ItemStack itemChar = new ItemStack(Items.coal, 1, 1);
		DecomposerRecipe.add(new DecomposerRecipeChance(itemChar, 0.82F, new PotionChemical[]
		{
			this.element(ElementEnum.C, 1)
		}));

		// Diamond
		ItemStack itemDiamond = new ItemStack(Items.diamond);
		DecomposerRecipe.add(new DecomposerRecipe(itemDiamond, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.fullrene, 4)
		}));

		// Polytool
		SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(MinechemItemsRegistration.polytool), true, COST_STAR, new PotionChemical[]

		{
			null, this.molecule(MoleculeEnum.fullrene, 64), null, this.molecule(MoleculeEnum.fullrene, 64), null, this.molecule(MoleculeEnum.fullrene, 64), null, this.molecule(MoleculeEnum.fullrene, 64), null
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemDiamond, true, COST_GEM, new PotionChemical[]
		{
			null, this.molecule(MoleculeEnum.fullrene), null, this.molecule(MoleculeEnum.fullrene), null, this.molecule(MoleculeEnum.fullrene), null, this.molecule(MoleculeEnum.fullrene), null
		}));

		// Iron Ingot
		ItemStack itemIngotIron = new ItemStack(Items.iron_ingot);
		DecomposerRecipe.add(new DecomposerRecipe(itemIngotIron, new PotionChemical[]
		{
			this.element(ElementEnum.Fe, 16)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemIngotIron, false, COST_INGOT, new PotionChemical[]
		{
			this.element(ElementEnum.Fe, 16)
		}));

		// Gold Ingot
		ItemStack itemIngotGold = new ItemStack(Items.gold_ingot);
		DecomposerRecipe.add(new DecomposerRecipe(itemIngotGold, new PotionChemical[]
		{
			this.element(ElementEnum.Au, 16)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemIngotGold, false, COST_INGOT, new PotionChemical[]
		{
			this.element(ElementEnum.Au, 16)
		}));

		// Stick
		ItemStack itemStick = new ItemStack(Items.stick);
		DecomposerRecipe.add(new DecomposerRecipeChance(itemStick, 0.3F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.cellulose)
		}));

		// String
		ItemStack itemString = new ItemStack(Items.string);
		DecomposerRecipe.add(new DecomposerRecipeChance(itemString, 0.45F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.serine), this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.alinine)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemString, true, COST_ITEM, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.serine), this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.alinine)
		}));

		// Feather
		ItemStack itemFeather = new ItemStack(Items.feather);
		DecomposerRecipe.add(new DecomposerRecipe(itemFeather, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.water, 8), this.element(ElementEnum.N, 6)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemFeather, true, COST_ITEM, new PotionChemical[]
		{
			this.element(ElementEnum.N), this.molecule(MoleculeEnum.water, 2), this.element(ElementEnum.N), this.element(ElementEnum.N), this.molecule(MoleculeEnum.water, 1), this.element(ElementEnum.N), this.element(ElementEnum.N),
			this.molecule(MoleculeEnum.water, 5), this.element(ElementEnum.N)
		}));

		// Gunpowder
		ItemStack itemGunpowder = new ItemStack(Items.gunpowder);
		DecomposerRecipe.add(new DecomposerRecipe(itemGunpowder, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.potassiumNitrate), this.element(ElementEnum.S, 2), this.element(ElementEnum.C)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemGunpowder, true, COST_ITEM, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.potassiumNitrate), this.element(ElementEnum.C), null, this.element(ElementEnum.S, 2), null, null, null, null, null
		}));

		// Bread
		ItemStack itemBread = new ItemStack(Items.bread);
		DecomposerRecipe.add(new DecomposerRecipeChance(itemBread, 0.1F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.starch), this.molecule(MoleculeEnum.sucrose)
		}));

		// Flint
		ItemStack itemFlint = new ItemStack(Items.flint);
		DecomposerRecipe.add(new DecomposerRecipeChance(itemFlint, 0.5F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.siliconDioxide)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemFlint, true, COST_ITEM, new PotionChemical[]
		{
			null, moleculeSiliconDioxide, null, moleculeSiliconDioxide, moleculeSiliconDioxide, moleculeSiliconDioxide, null, null, null
		}));

		// Golden Apple
		ItemStack itemAppleGold = new ItemStack(Items.golden_apple, 1, 0);
		DecomposerRecipe.add(new DecomposerRecipe(itemAppleGold, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.malicAcid), this.element(ElementEnum.Au, 64)
		}));

		// Wooden Door
		ItemStack itemDoorWood = new ItemStack(Items.wooden_door);
		DecomposerRecipe.add(new DecomposerRecipeChance(itemDoorWood, 0.4F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.cellulose, 12)
		}));

		// Water Bucket
		ItemStack itemBucketWater = new ItemStack(Items.water_bucket);
		DecomposerRecipe.add(new DecomposerRecipe(itemBucketWater, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.water, 16), this.element(ElementEnum.Fe, 48)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemBucketWater, true, COST_FOOD, new PotionChemical[]
		{
			null, null, null, this.element(ElementEnum.Fe, 16), this.molecule(MoleculeEnum.water, 16), this.element(ElementEnum.Fe, 16), null, this.element(ElementEnum.Fe, 16), null
		}));

		// Redstone
		ItemStack itemRedstone = new ItemStack(Items.redstone);
		DecomposerRecipe.add(new DecomposerRecipeChance(itemRedstone, 0.42F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.iron3oxide), this.element(ElementEnum.Cu)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemRedstone, true, COST_LAPIS, new PotionChemical[]
		{
			null, null, this.molecule(MoleculeEnum.iron3oxide), null, this.element(ElementEnum.Cu), null, null, null, null
		}));
		// Redstone Block
		ItemStack blockRedstone = new ItemStack(Blocks.redstone_block);
		DecomposerRecipe.add(new DecomposerRecipeChance(blockRedstone, 0.42F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.iron3oxide, 9), this.element(ElementEnum.Cu, 9)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockRedstone, true, COST_LAPISBLOCK, new PotionChemical[]
		{
			null, null, this.molecule(MoleculeEnum.iron3oxide, 9), null, this.element(ElementEnum.Cu, 9), null, null, null, null
		}));

		// Snowball
		ItemStack itemSnowball = new ItemStack(Items.snowball);
		DecomposerRecipe.add(new DecomposerRecipe(itemSnowball, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.water)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Items.snowball, 5), true, COST_ITEM, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.water), null, this.molecule(MoleculeEnum.water), null, this.molecule(MoleculeEnum.water), null, this.molecule(MoleculeEnum.water), null, this.molecule(MoleculeEnum.water)
		}));

		// Leather
		ItemStack itemLeather = new ItemStack(Items.leather);
		DecomposerRecipe.add(new DecomposerRecipeChance(itemLeather, 0.5F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.keratin)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Items.leather, 5), true, COST_ITEM, new PotionChemical[]
		{
			null, null, null, null, this.molecule(MoleculeEnum.keratin), null, null, null, null
		}));

		// Brick
		ItemStack itemBrick = new ItemStack(Items.brick);
		DecomposerRecipe.add(new DecomposerRecipeChance(itemBrick, 0.5F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.kaolinite)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Items.brick, 8), true, COST_ITEM, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.kaolinite), this.molecule(MoleculeEnum.kaolinite), null, this.molecule(MoleculeEnum.kaolinite), this.molecule(MoleculeEnum.kaolinite), null
		}));

		// Clay
		ItemStack itemClayBall = new ItemStack(Items.clay_ball);
		DecomposerRecipe.add(new DecomposerRecipeChance(itemClayBall, 0.3F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.kaolinite)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Items.clay_ball, 12), false, COST_ITEM, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.kaolinite)
		}));

		// Reed
		ItemStack itemReed = new ItemStack(Items.reeds);
		DecomposerRecipe.add(new DecomposerRecipeChance(itemReed, 0.65F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.sucrose), this.element(ElementEnum.H, 2), this.element(ElementEnum.O)
		}));

		// Paper
		ItemStack itemPaper = new ItemStack(Items.paper);
		DecomposerRecipe.add(new DecomposerRecipeChance(itemPaper, 0.25F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.cellulose)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Items.paper, 16), true, COST_ITEM, new PotionChemical[]
		{
			null, this.molecule(MoleculeEnum.cellulose), null, null, this.molecule(MoleculeEnum.cellulose), null, null, this.molecule(MoleculeEnum.cellulose), null
		}));

		// Slimeball
		ItemStack itemSlimeBall = new ItemStack(Items.slime_ball);
		DecomposerRecipe.add(new DecomposerRecipeSelect(itemSlimeBall, 0.9F, new DecomposerRecipe[]
		{
			new DecomposerRecipe(new PotionChemical[]
			{
				this.molecule(MoleculeEnum.pmma)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.element(ElementEnum.Hg)
			}), new DecomposerRecipe(new PotionChemical[]
			{
				this.molecule(MoleculeEnum.water, 10)
			})
		}));

		// Glowstone Dust
		ItemStack itemGlowstone = new ItemStack(Items.glowstone_dust);
		DecomposerRecipe.add(new DecomposerRecipe(itemGlowstone, new PotionChemical[]
		{
			this.element(ElementEnum.P)
		}));

		// Dyes
		ItemStack itemDyePowderBlack = new ItemStack(Items.dye, 1, 0);
		ItemStack itemDyePowderRed = new ItemStack(Items.dye, 1, 1);
		ItemStack itemDyePowderGreen = new ItemStack(Items.dye, 1, 2);
		ItemStack itemDyePowderBrown = new ItemStack(Items.dye, 1, 3);
		ItemStack itemDyePowderBlue = new ItemStack(Items.dye, 1, 4);
		ItemStack itemDyePowderPurple = new ItemStack(Items.dye, 1, 5);
		ItemStack itemDyePowderCyan = new ItemStack(Items.dye, 1, 6);
		ItemStack itemDyePowderLightGray = new ItemStack(Items.dye, 1, 7);
		ItemStack itemDyePowderGray = new ItemStack(Items.dye, 1, 8);
		ItemStack itemDyePowderPink = new ItemStack(Items.dye, 1, 9);
		ItemStack itemDyePowderLime = new ItemStack(Items.dye, 1, 10);
		ItemStack itemDyePowderYellow = new ItemStack(Items.dye, 1, 11);
		ItemStack itemDyePowderLightBlue = new ItemStack(Items.dye, 1, 12);
		ItemStack itemDyePowderMagenta = new ItemStack(Items.dye, 1, 13);
		ItemStack itemDyePowderOrange = new ItemStack(Items.dye, 1, 14);
		ItemStack itemDyePowderWhite = new ItemStack(Items.dye, 1, 15);
		DecomposerRecipe.add(new DecomposerRecipe(itemDyePowderBlack, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.blackPigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipe(itemDyePowderRed, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.redPigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipe(itemDyePowderGreen, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.greenPigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipeChance(itemDyePowderBrown, 0.4F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.theobromine), this.molecule(MoleculeEnum.tannicacid)
		}));
		// Lapis
		DecomposerRecipe.add(new DecomposerRecipe(itemDyePowderBlue, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.lazurite)
		}));
		DecomposerRecipe.add(new DecomposerRecipe(itemDyePowderPurple, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.purplePigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipe(itemDyePowderCyan, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.lightbluePigment), this.molecule(MoleculeEnum.whitePigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipe(itemDyePowderLightGray, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.whitePigment), this.molecule(MoleculeEnum.blackPigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipe(itemDyePowderGray, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.whitePigment), this.molecule(MoleculeEnum.blackPigment, 2)
		}));
		DecomposerRecipe.add(new DecomposerRecipe(itemDyePowderPink, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.redPigment), this.molecule(MoleculeEnum.whitePigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipe(itemDyePowderLime, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.limePigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipe(itemDyePowderYellow, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.yellowPigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipe(itemDyePowderLightBlue, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.lightbluePigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipe(itemDyePowderMagenta, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.lightbluePigment), this.molecule(MoleculeEnum.redPigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipe(itemDyePowderOrange, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.orangePigment)
		}));
		DecomposerRecipe.add(new DecomposerRecipe(itemDyePowderWhite, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.whitePigment)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderBlack, false, COST_ITEM, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.blackPigment)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderRed, false, COST_ITEM, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.redPigment)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderGreen, false, COST_ITEM, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.greenPigment)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderBrown, false, COST_ITEM, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.theobromine)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderBlue, false, COST_ITEM, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.lazurite)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderPurple, false, COST_ITEM, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.purplePigment)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderCyan, false, COST_ITEM, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.lightbluePigment), this.molecule(MoleculeEnum.whitePigment)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderLightGray, false, COST_ITEM, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.whitePigment), this.molecule(MoleculeEnum.blackPigment)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderGray, false, COST_ITEM, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.whitePigment), this.molecule(MoleculeEnum.blackPigment, 2)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderPink, false, COST_ITEM, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.redPigment), this.molecule(MoleculeEnum.whitePigment)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderLime, false, COST_ITEM, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.limePigment)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderYellow, false, COST_ITEM, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.yellowPigment)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderLightBlue, false, COST_ITEM, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.lightbluePigment)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderMagenta, false, COST_ITEM, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.lightbluePigment), this.molecule(MoleculeEnum.redPigment)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderOrange, false, COST_ITEM, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.orangePigment)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderWhite, false, COST_ITEM, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.whitePigment)
		}));

		// Bone
		ItemStack itemBone = new ItemStack(Items.bone);
		DecomposerRecipe.add(new DecomposerRecipe(itemBone, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.hydroxylapatite)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemBone, false, COST_ITEM, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.hydroxylapatite)
		}));

		// Sugar
		ItemStack itemSugar = new ItemStack(Items.sugar);
		DecomposerRecipe.add(new DecomposerRecipeChance(itemSugar, 0.75F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.sucrose)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemSugar, false, COST_SUGAR, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.sucrose)
		}));

		// Melon Slice
		ItemStack itemMelon = new ItemStack(Items.melon);
		DecomposerRecipe.add(new DecomposerRecipe(itemMelon, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.water, 1)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Items.melon), false, COST_FOOD, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.cucurbitacin), this.molecule(MoleculeEnum.asparticAcid), this.molecule(MoleculeEnum.water, 1)
		}));

		// Melon
		ItemStack blockMelon = new ItemStack(Blocks.melon_block);
		DecomposerRecipe.add(new DecomposerRecipe(blockMelon, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.cucurbitacin), this.molecule(MoleculeEnum.asparticAcid), this.molecule(MoleculeEnum.water, 16)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Blocks.melon_block, 1), false, COST_FOOD, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.cucurbitacin), this.molecule(MoleculeEnum.asparticAcid), this.molecule(MoleculeEnum.water, 16)
		}));

		// Cooked Chicken
		ItemStack itemChickenCooked = new ItemStack(Items.cooked_chicken);
		DecomposerRecipe.add(new DecomposerRecipe(itemChickenCooked, new PotionChemical[]
		{
			this.element(ElementEnum.K), this.element(ElementEnum.Na), this.element(ElementEnum.C, 2)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemChickenCooked, true, COST_FOOD, new PotionChemical[]
		{
			this.element(ElementEnum.K, 16), this.element(ElementEnum.Na, 16), this.element(ElementEnum.C, 16)
		}));

		// Rotten Flesh
		ItemStack itemRottenFlesh = new ItemStack(Items.rotten_flesh);
		DecomposerRecipe.add(new DecomposerRecipeChance(itemRottenFlesh, 0.05F, new PotionChemical[]
		{
			new Molecule(MoleculeEnum.nodularin, 1)
		}));

		// Enderpearl
		ItemStack itemEnderPearl = new ItemStack(Items.ender_pearl);
		DecomposerRecipe.add(new DecomposerRecipe(itemEnderPearl, new PotionChemical[]
		{
			this.element(ElementEnum.Es), this.molecule(MoleculeEnum.calciumCarbonate, 8)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemEnderPearl, true, COST_TEAR, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.calciumCarbonate), this.molecule(MoleculeEnum.calciumCarbonate), this.molecule(MoleculeEnum.calciumCarbonate), this.molecule(MoleculeEnum.calciumCarbonate), this.element(ElementEnum.Es),
			this.molecule(MoleculeEnum.calciumCarbonate), this.molecule(MoleculeEnum.calciumCarbonate), this.molecule(MoleculeEnum.calciumCarbonate), this.molecule(MoleculeEnum.calciumCarbonate)
		}));

		// EnderDragon Egg
		ItemStack blockEnderDragonEgg = new ItemStack(Blocks.dragon_egg);
		DecomposerRecipe.add(new DecomposerRecipe(blockEnderDragonEgg, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.calciumCarbonate, 16), this.molecule(MoleculeEnum.hydroxylapatite, 6), this.element(ElementEnum.Pu, 18), this.element(ElementEnum.Fm, 8)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(blockEnderDragonEgg, true, COST_BLOCK * 2, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.calciumCarbonate, 18), this.molecule(MoleculeEnum.hydroxylapatite, 8), this.element(ElementEnum.Pu, 22), this.element(ElementEnum.Fm, 12)
		}));

		// Blaze Rod
		ItemStack itemBlazeRod = new ItemStack(Items.blaze_rod);
		DecomposerRecipe.add(new DecomposerRecipe(itemBlazeRod, new PotionChemical[]
		{
			this.element(ElementEnum.Pu, 6)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemBlazeRod, true, COST_TEAR, new PotionChemical[]
		{
			this.element(ElementEnum.Pu, 2), null, null, this.element(ElementEnum.Pu, 2), null, null, this.element(ElementEnum.Pu, 2), null, null
		}));

		// Blaze Powder
		ItemStack itemBlazePowder = new ItemStack(Items.blaze_powder);
		DecomposerRecipe.add(new DecomposerRecipe(itemBlazePowder, new PotionChemical[]
		{
			this.element(ElementEnum.Pu)
		}));

		// Ghast Tear
		ItemStack itemGhastTear = new ItemStack(Items.ghast_tear);
		DecomposerRecipe.add(new DecomposerRecipe(itemGhastTear, new PotionChemical[]
		{
			this.element(ElementEnum.Yb, 4), this.element(ElementEnum.No, 4)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemGhastTear, true, COST_TEAR, new PotionChemical[]
		{
			this.element(ElementEnum.Yb), this.element(ElementEnum.Yb), this.element(ElementEnum.No), null, this.element(ElementEnum.Yb, 2), this.element(ElementEnum.No, 2), null, this.element(ElementEnum.No), null
		}));

		// Nether Wart
		ItemStack itemNetherStalkSeeds = new ItemStack(Items.nether_wart);
		DecomposerRecipe.add(new DecomposerRecipeChance(itemNetherStalkSeeds, 0.5F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.cocainehcl)
		}));

		// Spider Eye
		ItemStack itemSpiderEye = new ItemStack(Items.spider_eye);
		DecomposerRecipe.add(new DecomposerRecipeChance(itemSpiderEye, 0.2F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.tetrodotoxin)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemSpiderEye, true, COST_ITEM, new PotionChemical[]
		{
			this.element(ElementEnum.C), null, null, null, this.molecule(MoleculeEnum.tetrodotoxin), null, null, null, this.element(ElementEnum.C)
		}));

		// Fermented Spider Eye
		ItemStack itemFermentedSpiderEye = new ItemStack(Items.fermented_spider_eye);
		DecomposerRecipe.add(new DecomposerRecipe(itemFermentedSpiderEye, new PotionChemical[]
		{
			this.element(ElementEnum.Po), this.molecule(MoleculeEnum.ethanol)
		}));

		// Magma Cream
		ItemStack itemMagmaCream = new ItemStack(Items.magma_cream);
		DecomposerRecipe.add(new DecomposerRecipe(itemMagmaCream, new PotionChemical[]
		{
			this.element(ElementEnum.Hg), this.element(ElementEnum.Pu), this.molecule(MoleculeEnum.pmma, 3)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemMagmaCream, true, COST_TEAR, new PotionChemical[]
		{
			null, this.element(ElementEnum.Pu), null, this.molecule(MoleculeEnum.pmma), this.element(ElementEnum.Hg), this.molecule(MoleculeEnum.pmma), null, this.molecule(MoleculeEnum.pmma), null,
		}));

		// Glistering Melon
		ItemStack itemSpeckledMelon = new ItemStack(Items.speckled_melon);
		DecomposerRecipe.add(new DecomposerRecipe(itemSpeckledMelon, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.water, 4), this.molecule(MoleculeEnum.whitePigment), this.element(ElementEnum.Au, 1)
		}));

		// Emerald
		ItemStack itemEmerald = new ItemStack(Items.emerald);
		DecomposerRecipe.add(new DecomposerRecipe(itemEmerald, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.beryl, 2), this.element(ElementEnum.Cr, 2), this.element(ElementEnum.V, 2)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemEmerald, true, 5000, new PotionChemical[]
		{
			null, this.element(ElementEnum.Cr), null, this.element(ElementEnum.V), this.molecule(MoleculeEnum.beryl, 2), this.element(ElementEnum.V), null, this.element(ElementEnum.Cr), null
		}));

		// Wheat
		ItemStack itemWheat = new ItemStack(Items.wheat);
		DecomposerRecipe.add(new DecomposerRecipeChance(itemWheat, 0.3F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.cellulose, 2)
		}));

		// Carrot
		ItemStack itemCarrot = new ItemStack(Items.carrot);
		DecomposerRecipe.add(new DecomposerRecipe(itemCarrot, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.retinol)
		}));

		// Potato
		ItemStack itemPotato = new ItemStack(Items.potato);
		DecomposerRecipe.add(new DecomposerRecipeChance(itemPotato, 0.4F, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.water, 8), this.element(ElementEnum.K, 2), this.molecule(MoleculeEnum.cellulose)
		}));

		// Golden Carrot
		ItemStack itemGoldenCarrot = new ItemStack(Items.golden_carrot);
		DecomposerRecipe.add(new DecomposerRecipe(itemGoldenCarrot, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.retinol), this.element(ElementEnum.Au, 4)
		}));

		// Nether Star
		ItemStack itemNetherStar = new ItemStack(Items.nether_star);
		DecomposerRecipe.add(new DecomposerRecipe(itemNetherStar, new PotionChemical[]
		{
			this.element(ElementEnum.Cn, 16), elementHydrogen, elementHydrogen, elementHydrogen, elementHelium, elementHelium, elementHelium, elementCarbon, elementCarbon
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemNetherStar, true, COST_STAR, new PotionChemical[]
		{
			elementHelium, elementHelium, elementHelium, elementCarbon, this.element(ElementEnum.Cn, 16), elementHelium, elementHydrogen, elementHydrogen, elementHydrogen
		}));

		// Nether Quartz
		ItemStack itemNetherQuartz = new ItemStack(Items.quartz);
		DecomposerRecipe.add(new DecomposerRecipe(itemNetherQuartz, new PotionChemical[]
		{
			this.molecule(MoleculeEnum.siliconDioxide, 4), this.molecule(MoleculeEnum.galliumarsenide, 1)
		}));

		// Music Records
		ItemStack itemRecord13 = new ItemStack(Items.record_13);
		ItemStack itemRecordCat = new ItemStack(Items.record_cat);
		ItemStack itemRecordFar = new ItemStack(Items.record_far);
		ItemStack itemRecordMall = new ItemStack(Items.record_mall);
		ItemStack itemRecordMellohi = new ItemStack(Items.record_mellohi);
		ItemStack itemRecordStal = new ItemStack(Items.record_stal);
		ItemStack itemRecordStrad = new ItemStack(Items.record_strad);
		ItemStack itemRecordWard = new ItemStack(Items.record_ward);
		ItemStack itemRecordChirp = new ItemStack(Items.record_chirp);
		DecomposerRecipe.add(new DecomposerRecipe(itemRecord13, new PotionChemical[]
		{
			moleculePolyvinylChloride
		}));
		DecomposerRecipe.add(new DecomposerRecipe(itemRecordCat, new PotionChemical[]
		{
			moleculePolyvinylChloride
		}));
		DecomposerRecipe.add(new DecomposerRecipe(itemRecordFar, new PotionChemical[]
		{
			moleculePolyvinylChloride
		}));
		DecomposerRecipe.add(new DecomposerRecipe(itemRecordMall, new PotionChemical[]
		{
			moleculePolyvinylChloride
		}));
		DecomposerRecipe.add(new DecomposerRecipe(itemRecordMellohi, new PotionChemical[]
		{
			moleculePolyvinylChloride
		}));
		DecomposerRecipe.add(new DecomposerRecipe(itemRecordStal, new PotionChemical[]
		{
			moleculePolyvinylChloride
		}));
		DecomposerRecipe.add(new DecomposerRecipe(itemRecordStrad, new PotionChemical[]
		{
			moleculePolyvinylChloride
		}));
		DecomposerRecipe.add(new DecomposerRecipe(itemRecordWard, new PotionChemical[]
		{
			moleculePolyvinylChloride
		}));
		DecomposerRecipe.add(new DecomposerRecipe(itemRecordChirp, new PotionChemical[]
		{
			moleculePolyvinylChloride
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemRecord13, true, COST_GEM, new PotionChemical[]
		{
			moleculePolyvinylChloride, null, null, null, null, null, null, null, null
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemRecordCat, true, COST_GEM, new PotionChemical[]
		{
			null, moleculePolyvinylChloride, null, null, null, null, null, null, null
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemRecordFar, true, COST_GEM, new PotionChemical[]
		{
			null, null, moleculePolyvinylChloride, null, null, null, null, null, null
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemRecordMall, true, COST_GEM, new PotionChemical[]
		{
			null, null, null, moleculePolyvinylChloride, null, null, null, null, null
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemRecordMellohi, true, COST_GEM, new PotionChemical[]
		{
			null, null, null, null, moleculePolyvinylChloride, null, null, null, null
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemRecordStal, true, COST_GEM, new PotionChemical[]
		{
			null, null, null, null, null, moleculePolyvinylChloride, null, null, null
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemRecordStrad, true, COST_GEM, new PotionChemical[]
		{
			null, null, null, null, null, null, moleculePolyvinylChloride, null, null
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemRecordWard, true, COST_GEM, new PotionChemical[]
		{
			null, null, null, null, null, null, null, moleculePolyvinylChloride, null
		}));
		SynthesisRecipe.add(new SynthesisRecipe(itemRecordChirp, true, COST_GEM, new PotionChemical[]
		{
			null, null, null, null, null, null, null, null, moleculePolyvinylChloride
		}));

		//Ironbars
		ItemStack bars = new ItemStack(Blocks.iron_bars);
		DecomposerRecipe.add(new DecomposerRecipe(bars, new PotionChemical[]
		{
			element(ElementEnum.Fe, 2)
		}));
		SynthesisRecipe.add(new SynthesisRecipe(bars, false, COST_BLOCK, new PotionChemical[]
		{
			element(ElementEnum.Fe, 2)
		}));
	}

	public void RegisterModRecipes()
	{
		//OreDict stuff
		DecomposerRecipe.createAndAddRecipeSafely("ingotIron", this.element(ElementEnum.Fe, 16));
		DecomposerRecipe.createAndAddRecipeSafely("ingotGold", this.element(ElementEnum.Au, 16));
		DecomposerRecipe.createAndAddRecipeSafely("ingotCopper", this.element(ElementEnum.Cu, 16));
		DecomposerRecipe.createAndAddRecipeSafely("ingotTin", this.element(ElementEnum.Sn, 16));
		DecomposerRecipe.createAndAddRecipeSafely("ingotSilver", this.element(ElementEnum.Ag, 16));
		DecomposerRecipe.createAndAddRecipeSafely("ingotLead", this.element(ElementEnum.Pb, 16));
		DecomposerRecipe.createAndAddRecipeSafely("ingotPlatinum", this.element(ElementEnum.Pt, 16));
		DecomposerRecipe.createAndAddRecipeSafely("ingotAluminium", this.element(ElementEnum.Au, 16));
		DecomposerRecipe.createAndAddRecipeSafely("ingotMagnesium", this.element(ElementEnum.Mg, 16));
		DecomposerRecipe.createAndAddRecipeSafely("ingotSteel", new PotionChemical[]
		{
			this.element(ElementEnum.Fe, 15), this.element(ElementEnum.C, 1)
		});
		DecomposerRecipe.createAndAddRecipeSafely("ingotHSLA", new PotionChemical[]
		{
			this.element(ElementEnum.Fe, 15), this.element(ElementEnum.C, 1)
		});
		DecomposerRecipe.createAndAddRecipeSafely("ingotBronze", new PotionChemical[]
		{
			this.element(ElementEnum.Cu, 12), this.element(ElementEnum.Sn, 4)
		});
		DecomposerRecipe.createAndAddRecipeSafely("ingotElectrum", new PotionChemical[]
		{
			this.element(ElementEnum.Ag, 8), this.element(ElementEnum.Au, 8)
		});
		DecomposerRecipe.createAndAddRecipeSafely("ingotInvar", new PotionChemical[]
		{
			this.element(ElementEnum.Fe, 10), this.element(ElementEnum.Ni, 6)
		});

		SynthesisRecipe.createAndAddRecipeSafely("ingotIron", false, COST_INGOT, this.element(ElementEnum.Fe));
		SynthesisRecipe.createAndAddRecipeSafely("ingotGold", false, COST_INGOT, this.element(ElementEnum.Au, 16));
		SynthesisRecipe.createAndAddRecipeSafely("ingotCopper", false, COST_INGOT, this.element(ElementEnum.Cu, 16));
		SynthesisRecipe.createAndAddRecipeSafely("ingotTin", false, COST_INGOT, this.element(ElementEnum.Sn, 16));
		SynthesisRecipe.createAndAddRecipeSafely("ingotSilver", false, COST_INGOT, this.element(ElementEnum.Ag, 16));
		SynthesisRecipe.createAndAddRecipeSafely("ingotLead", false, COST_INGOT, this.element(ElementEnum.Pb, 16));
		SynthesisRecipe.createAndAddRecipeSafely("ingotPlatinum", false, COST_INGOT, this.element(ElementEnum.Pt, 16));
		SynthesisRecipe.createAndAddRecipeSafely("ingotAluminium", false, COST_INGOT, this.element(ElementEnum.Au, 16));
		SynthesisRecipe.createAndAddRecipeSafely("ingotMagnesium", false, COST_INGOT, this.element(ElementEnum.Mg, 16));
		SynthesisRecipe.createAndAddRecipeSafely("ingotSteel", false, COST_INGOT, new PotionChemical[]
		{
			this.element(ElementEnum.Fe, 15), this.element(ElementEnum.C, 1)
		});
		SynthesisRecipe.createAndAddRecipeSafely("ingotHSLA", false, COST_INGOT, new PotionChemical[]
		{
			this.element(ElementEnum.Fe, 15), this.element(ElementEnum.C, 1)
		});
		SynthesisRecipe.createAndAddRecipeSafely("ingotBronze", false, COST_INGOT, new PotionChemical[]
		{
			this.element(ElementEnum.Cu, 12), this.element(ElementEnum.Sn, 4)
		});
		SynthesisRecipe.createAndAddRecipeSafely("ingotElectrum", false, COST_INGOT, new PotionChemical[]
		{
			this.element(ElementEnum.Ag, 8), this.element(ElementEnum.Au, 8)
		});
		SynthesisRecipe.createAndAddRecipeSafely("ingotInvar", false, COST_INGOT, new PotionChemical[]
		{
			this.element(ElementEnum.Fe, 10), this.element(ElementEnum.Ni, 6)
		});
		//Thermal Expansion
		if (Loader.isModLoaded("ThermalExpansion"))
		{
			Block rockwool = GameRegistry.findBlock("ThermalExpansion", "Rockwool");
			ItemStack blockRockWool = new ItemStack(rockwool, 1, 0);
			ItemStack blockRockOrangeWool = new ItemStack(rockwool, 1, 1);
			ItemStack blockRockMagentaWool = new ItemStack(rockwool, 1, 2);
			ItemStack blockRockLightBlueWool = new ItemStack(rockwool, 1, 3);
			ItemStack blockRockYellowWool = new ItemStack(rockwool, 1, 4);
			ItemStack blockRockLimeWool = new ItemStack(rockwool, 1, 5);
			ItemStack blockRockPinkWool = new ItemStack(rockwool, 1, 6);
			ItemStack blockRockGrayWool = new ItemStack(rockwool, 1, 7);
			ItemStack blockRockLightGrayWool = new ItemStack(rockwool, 1, 8);
			ItemStack blockRockCyanWool = new ItemStack(rockwool, 1, 9);
			ItemStack blockRockPurpleWool = new ItemStack(rockwool, 1, 10);
			ItemStack blockRockBlueWool = new ItemStack(rockwool, 1, 11);
			ItemStack blockRockBrownWool = new ItemStack(rockwool, 1, 12);
			ItemStack blockRockGreenWool = new ItemStack(rockwool, 1, 13);
			ItemStack blockRockRedWool = new ItemStack(rockwool, 1, 14);
			ItemStack blockRockBlackWool = new ItemStack(rockwool, 1, 15);
			DecomposerRecipe.add(new DecomposerRecipeChance(blockRockWool, 0.2F, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.asbestos, 2), this.molecule(MoleculeEnum.whitePigment)
			}));
			DecomposerRecipe.add(new DecomposerRecipeChance(blockRockOrangeWool, 0.2F, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.asbestos, 2), this.molecule(MoleculeEnum.orangePigment)
			}));
			DecomposerRecipe.add(new DecomposerRecipeChance(blockRockMagentaWool, 0.2F, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.asbestos, 2), this.molecule(MoleculeEnum.lightbluePigment), this.molecule(MoleculeEnum.redPigment)
			}));
			DecomposerRecipe.add(new DecomposerRecipeChance(blockRockLightBlueWool, 0.2F, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.asbestos, 2), this.molecule(MoleculeEnum.lightbluePigment)
			}));
			DecomposerRecipe.add(new DecomposerRecipeChance(blockRockYellowWool, 0.2F, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.asbestos, 2), this.molecule(MoleculeEnum.yellowPigment)
			}));
			DecomposerRecipe.add(new DecomposerRecipeChance(blockRockLimeWool, 0.2F, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.asbestos, 2), this.molecule(MoleculeEnum.limePigment)
			}));
			DecomposerRecipe.add(new DecomposerRecipeChance(blockRockPinkWool, 0.2F, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.asbestos, 2), this.molecule(MoleculeEnum.redPigment), this.molecule(MoleculeEnum.whitePigment)
			}));
			DecomposerRecipe.add(new DecomposerRecipeChance(blockRockGrayWool, 0.2F, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.asbestos, 2), this.molecule(MoleculeEnum.whitePigment), this.molecule(MoleculeEnum.blackPigment, 2)
			}));
			DecomposerRecipe.add(new DecomposerRecipeChance(blockRockLightGrayWool, 0.2F, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.asbestos, 2), this.molecule(MoleculeEnum.whitePigment), this.molecule(MoleculeEnum.blackPigment)
			}));
			DecomposerRecipe.add(new DecomposerRecipeChance(blockRockCyanWool, 0.2F, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.asbestos, 2), this.molecule(MoleculeEnum.lightbluePigment), this.molecule(MoleculeEnum.whitePigment)
			}));
			DecomposerRecipe.add(new DecomposerRecipeChance(blockRockPurpleWool, 0.2F, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.asbestos, 2), this.molecule(MoleculeEnum.purplePigment)
			}));
			DecomposerRecipe.add(new DecomposerRecipeChance(blockRockBlueWool, 0.2F, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.asbestos, 2), this.molecule(MoleculeEnum.lazurite)
			}));
			DecomposerRecipe.add(new DecomposerRecipeChance(blockRockBrownWool, 0.2F, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.asbestos, 2), this.molecule(MoleculeEnum.tannicacid)
			}));
			DecomposerRecipe.add(new DecomposerRecipeChance(blockRockGreenWool, 0.2F, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.asbestos, 2), this.molecule(MoleculeEnum.greenPigment)
			}));
			DecomposerRecipe.add(new DecomposerRecipeChance(blockRockRedWool, 0.2F, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.asbestos, 2), this.molecule(MoleculeEnum.redPigment)
			}));
			DecomposerRecipe.add(new DecomposerRecipeChance(blockRockBlackWool, 0.2F, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.asbestos, 2), this.molecule(MoleculeEnum.blackPigment)
			}));
			SynthesisRecipe.add(new SynthesisRecipe(blockRockWool, false, COST_WOOL, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.asbestos, 2), this.molecule(MoleculeEnum.whitePigment)
			}));
			SynthesisRecipe.add(new SynthesisRecipe(blockRockOrangeWool, false, COST_WOOL, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.asbestos, 2), this.molecule(MoleculeEnum.orangePigment)
			}));
			SynthesisRecipe.add(new SynthesisRecipe(blockRockMagentaWool, false, COST_WOOL, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.asbestos, 2), this.molecule(MoleculeEnum.lightbluePigment), this.molecule(MoleculeEnum.redPigment)
			}));
			SynthesisRecipe.add(new SynthesisRecipe(blockRockLightBlueWool, false, COST_WOOL, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.asbestos, 2), this.molecule(MoleculeEnum.lightbluePigment)
			}));
			SynthesisRecipe.add(new SynthesisRecipe(blockRockYellowWool, false, COST_WOOL, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.asbestos, 2), this.molecule(MoleculeEnum.yellowPigment)
			}));
			SynthesisRecipe.add(new SynthesisRecipe(blockRockLimeWool, false, COST_WOOL, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.asbestos, 2), this.molecule(MoleculeEnum.limePigment)
			}));
			SynthesisRecipe.add(new SynthesisRecipe(blockRockPinkWool, false, COST_WOOL, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.asbestos, 2), this.molecule(MoleculeEnum.redPigment), this.molecule(MoleculeEnum.whitePigment)
			}));
			SynthesisRecipe.add(new SynthesisRecipe(blockRockGrayWool, false, COST_WOOL, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.asbestos, 2), this.molecule(MoleculeEnum.whitePigment), this.molecule(MoleculeEnum.blackPigment, 2)
			}));
			SynthesisRecipe.add(new SynthesisRecipe(blockRockLightGrayWool, false, COST_WOOL, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.asbestos, 2), this.molecule(MoleculeEnum.whitePigment), this.molecule(MoleculeEnum.blackPigment)
			}));
			SynthesisRecipe.add(new SynthesisRecipe(blockRockCyanWool, false, COST_WOOL, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.asbestos, 2), this.molecule(MoleculeEnum.lightbluePigment), this.molecule(MoleculeEnum.whitePigment)
			}));
			SynthesisRecipe.add(new SynthesisRecipe(blockRockPurpleWool, false, COST_WOOL, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.asbestos, 2), this.molecule(MoleculeEnum.purplePigment)
			}));
			SynthesisRecipe.add(new SynthesisRecipe(blockRockBlueWool, false, COST_WOOL, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.asbestos, 2), this.molecule(MoleculeEnum.lazurite)
			}));
			SynthesisRecipe.add(new SynthesisRecipe(blockRockGreenWool, false, COST_WOOL, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.asbestos, 2), this.molecule(MoleculeEnum.greenPigment)
			}));
			SynthesisRecipe.add(new SynthesisRecipe(blockRockRedWool, false, COST_WOOL, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.asbestos, 2), this.molecule(MoleculeEnum.redPigment)
			}));
			SynthesisRecipe.add(new SynthesisRecipe(blockRockBlackWool, false, COST_WOOL, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.asbestos, 2), this.molecule(MoleculeEnum.blackPigment)
			}));
		}

		if (Loader.isModLoaded("ThermalFoundation"))
		{
			Item bucket = GameRegistry.findItem("ThermalFoundation", "bucket");
			Item material = GameRegistry.findItem("ThermalFoundation", "material");

			ItemStack redstoneBucket = new ItemStack(bucket, 1, 0);
			ItemStack glowstoneBucket = new ItemStack(bucket, 1, 1);
			ItemStack enderBucket = new ItemStack(bucket, 1, 2);
			ItemStack signalumBlend = new ItemStack(material, 1, 42);
			ItemStack lumiumBlend = new ItemStack(material, 1, 43);
			ItemStack enderiumBlend = new ItemStack(material, 1, 44);
			ItemStack signalumIngot = new ItemStack(material, 1, 74);
			ItemStack lumiumIngot = new ItemStack(material, 1, 75);
			ItemStack enderiumIngot = new ItemStack(material, 1, 76);

			DecomposerRecipe.add(new DecomposerRecipe(redstoneBucket, new PotionChemical[]
			{
				this.element(ElementEnum.Cu, 4), this.element(ElementEnum.Fe, 48), this.molecule(MoleculeEnum.iron3oxide, 4)
			}));
			DecomposerRecipe.add(new DecomposerRecipe(enderBucket, new PotionChemical[]
			{
				this.element(ElementEnum.Fe, 48), this.element(ElementEnum.Es, 4), this.molecule(MoleculeEnum.calciumCarbonate, 32)
			}));
			DecomposerRecipe.add(new DecomposerRecipe(glowstoneBucket, new PotionChemical[]
			{
				this.element(ElementEnum.Fe, 48), this.element(ElementEnum.P, 4)
			}));

			DecomposerRecipe.add(new DecomposerRecipe(signalumBlend, new PotionChemical[]
			{
				this.element(ElementEnum.Cu, 12), this.element(ElementEnum.Ag, 4), this.molecule(MoleculeEnum.iron3oxide)
			}));
			DecomposerRecipe.add(new DecomposerRecipe(signalumIngot, new PotionChemical[]
			{
				this.element(ElementEnum.Cu, 12), this.element(ElementEnum.Ag, 4), this.molecule(MoleculeEnum.iron3oxide)
			}));
			DecomposerRecipe.add(new DecomposerRecipe(lumiumBlend, new PotionChemical[]
			{
				this.element(ElementEnum.Sn, 12), this.element(ElementEnum.Ag, 4), this.element(ElementEnum.P)
			}));
			DecomposerRecipe.add(new DecomposerRecipe(lumiumIngot, new PotionChemical[]
			{
				this.element(ElementEnum.Sn, 12), this.element(ElementEnum.Ag, 4), this.element(ElementEnum.P)
			}));
			DecomposerRecipe.add(new DecomposerRecipe(enderiumBlend, new PotionChemical[]
			{
				this.element(ElementEnum.Sn, 8), this.element(ElementEnum.Ag, 4), this.element(ElementEnum.Pt, 4), this.element(ElementEnum.Es), this.molecule(MoleculeEnum.calciumCarbonate, 8)
			}));
			DecomposerRecipe.add(new DecomposerRecipe(enderiumIngot, new PotionChemical[]
			{
				this.element(ElementEnum.Sn, 8), this.element(ElementEnum.Ag, 4), this.element(ElementEnum.Pt, 4), this.element(ElementEnum.Es), this.molecule(MoleculeEnum.calciumCarbonate, 8)
			}));

			SynthesisRecipe.add(new SynthesisRecipe(signalumBlend, false, COST_INGOT, new PotionChemical[]
			{
				this.element(ElementEnum.Cu, 12), this.element(ElementEnum.Ag, 4), this.molecule(MoleculeEnum.iron3oxide)
			}));
			SynthesisRecipe.add(new SynthesisRecipe(signalumIngot, false, COST_INGOT, new PotionChemical[]
			{
				this.element(ElementEnum.Cu, 12), this.element(ElementEnum.Ag, 4), this.molecule(MoleculeEnum.iron3oxide)
			}));
			SynthesisRecipe.add(new SynthesisRecipe(lumiumBlend, false, COST_INGOT, new PotionChemical[]
			{
				this.element(ElementEnum.Sn, 12), this.element(ElementEnum.Ag, 4), this.element(ElementEnum.P)
			}));
			SynthesisRecipe.add(new SynthesisRecipe(lumiumIngot, false, COST_INGOT, new PotionChemical[]
			{
				this.element(ElementEnum.Sn, 12), this.element(ElementEnum.Ag, 4), this.element(ElementEnum.P)
			}));
			SynthesisRecipe.add(new SynthesisRecipe(enderiumBlend, false, COST_INGOT, new PotionChemical[]
			{
				this.element(ElementEnum.Sn, 8), this.element(ElementEnum.Ag, 4), this.element(ElementEnum.Pt, 4), this.element(ElementEnum.Es), this.molecule(MoleculeEnum.calciumCarbonate, 8)
			}));
			SynthesisRecipe.add(new SynthesisRecipe(enderiumIngot, false, COST_INGOT * 2, new PotionChemical[]
			{
				this.element(ElementEnum.Sn, 8), this.element(ElementEnum.Ag, 4), this.element(ElementEnum.Pt, 4), this.element(ElementEnum.Es), this.molecule(MoleculeEnum.calciumCarbonate, 8), this.molecule(MoleculeEnum.iron3oxide), this.element(ElementEnum.Pu), this.element(ElementEnum.C, 8), this.element(ElementEnum.S, 16)
			}));
		}

		//RailCraft
		if (Loader.isModLoaded("Railcraft"))
		{
			Block metalPost = GameRegistry.findBlock("Railcraft", "tile.railcraft.post.metal");
			Block metalPlatform = GameRegistry.findBlock("Railcraft", "tile.railcraft.post.metal.platform");
			Block post = GameRegistry.findBlock("Railcraft", "tile.railcraft.post");
			DecomposerRecipe.add(new DecomposerRecipe(new ItemStack(metalPost), this.element(ElementEnum.Fe, 5)));
			DecomposerRecipe.add(new DecomposerRecipe(new ItemStack(metalPlatform), this.element(ElementEnum.Fe, 5)));
			DecomposerRecipe.add(new DecomposerRecipe(new ItemStack(post), new PotionChemical[]
			{
			}));
		}

		//Mekanism
		if (Loader.isModLoaded("Mekanism"))
		{
//			System.out.println(GameData.getItemRegistry().getKeys().toString());
//			System.out.println(GameData.getBlockRegistry().getKeys().toString());
			//Item ingotOsmium = GameRegistry.findItem(modId, name)
		}
	}

	public void RegisterRecipes()
	{
		this.registerVanillaChemicalRecipes();

		ItemStack blockGlass = new ItemStack(Blocks.glass);
		ItemStack blockThinGlass = new ItemStack(Blocks.glass_pane);
		ItemStack blockIron = new ItemStack(Blocks.iron_block);
		ItemStack itemIngotIron = new ItemStack(Items.iron_ingot);
		ItemStack itemRedstone = new ItemStack(Items.redstone);
		ItemStack minechemItemsAtomicManipulator = new ItemStack(MinechemItemsRegistration.atomicManipulator);
		ItemStack moleculePolyvinylChloride = new ItemStack(MinechemItemsRegistration.molecule, 1, MoleculeEnum.polyvinylChloride.id());

		GameRegistry.addRecipe(MinechemItemsRegistration.concaveLens, new Object[]
		{
			"G G", "GGG", "G G", Character.valueOf('G'), blockGlass
		});
		GameRegistry.addRecipe(MinechemItemsRegistration.convexLens, new Object[]
		{
			" G ", "GGG", " G ", Character.valueOf('G'), blockGlass
		});
		GameRegistry.addRecipe(MinechemItemsRegistration.microscopeLens, new Object[]
		{
			"A", "B", "A", Character.valueOf('A'), MinechemItemsRegistration.convexLens, Character.valueOf('B'), MinechemItemsRegistration.concaveLens
		});
		GameRegistry.addRecipe(new ItemStack(MinechemBlocksGeneration.microscope), new Object[]
		{
			" LI", " PI", "III", Character.valueOf('L'), MinechemItemsRegistration.microscopeLens, Character.valueOf('P'), blockThinGlass, Character.valueOf('I'), itemIngotIron
		});
		GameRegistry.addRecipe(new ItemStack(MinechemBlocksGeneration.microscope), new Object[]
		{
			" LI", " PI", "III", Character.valueOf('L'), MinechemItemsRegistration.microscopeLens, Character.valueOf('P'), blockThinGlass, Character.valueOf('I'), itemIngotIron
		});
		GameRegistry.addRecipe(new ItemStack(MinechemItemsRegistration.atomicManipulator), new Object[]
		{
			"PPP", "PIP", "PPP", Character.valueOf('P'), new ItemStack(Blocks.piston), Character.valueOf('I'), blockIron
		});
		GameRegistry.addRecipe(new ItemStack(MinechemBlocksGeneration.decomposer), new Object[]
		{
			"III", "IAI", "IRI", Character.valueOf('A'), minechemItemsAtomicManipulator, Character.valueOf('I'), itemIngotIron, Character.valueOf('R'), itemRedstone
		});
		GameRegistry.addRecipe(new ItemStack(MinechemBlocksGeneration.synthesis), new Object[]
		{
			"IRI", "IAI", "IDI", Character.valueOf('A'), minechemItemsAtomicManipulator, Character.valueOf('I'), itemIngotIron, Character.valueOf('R'), itemRedstone, Character.valueOf('D'), new ItemStack(Items.diamond)
		});
		GameRegistry.addRecipe(new ItemStack(MinechemBlocksGeneration.fusion, 16, 0), new Object[]
		{
			"ILI", "ILI", "ILI", Character.valueOf('I'), itemIngotIron, Character.valueOf('L'), ElementItem.createStackOf(ElementEnum.Pb, 1)
		});
		GameRegistry.addRecipe(new ItemStack(MinechemBlocksGeneration.fusion, 16, 1), new Object[]
		{
			"IWI", "IBI", "IWI", Character.valueOf('I'), itemIngotIron, Character.valueOf('W'), ElementItem.createStackOf(ElementEnum.W, 1), Character.valueOf('B'), ElementItem.createStackOf(ElementEnum.Be, 1)
		});
		GameRegistry.addRecipe(MinechemItemsRegistration.projectorLens, new Object[]
		{
			"ABA", Character.valueOf('A'), MinechemItemsRegistration.concaveLens, Character.valueOf('B'), MinechemItemsRegistration.convexLens
		});
		GameRegistry.addRecipe(new ItemStack(MinechemBlocksGeneration.blueprintProjector), new Object[]
		{
			" I ", "GPL", " I ", Character.valueOf('I'), itemIngotIron, Character.valueOf('P'), blockThinGlass, Character.valueOf('L'), MinechemItemsRegistration.projectorLens, Character.valueOf('G'), new ItemStack(Blocks.redstone_lamp)
		});
		GameRegistry.addRecipe(new ItemStack(MinechemBlocksGeneration.leadChest), new Object[]
		{
			"LLL", "LCL", "LLL", Character.valueOf('L'), new ItemStack(MinechemItemsRegistration.element, 1, ElementEnum.Pb.ordinal()), Character.valueOf('C'), new ItemStack(Blocks.chest)
		});
		GameRegistry.addShapelessRecipe(new ItemStack(MinechemItemsRegistration.journal), new Object[]
		{
			new ItemStack(Items.book), new ItemStack(Blocks.glass)
		});
		// Fusion
		GameRegistry.addShapelessRecipe(ItemBlueprint.createItemStackFromBlueprint(MinechemBlueprint.fusion), new Object[]
		{
			new ItemStack(Items.paper), new ItemStack(Blocks.diamond_block)
		});
		// Fission
		GameRegistry.addShapelessRecipe(ItemBlueprint.createItemStackFromBlueprint(MinechemBlueprint.fission), new Object[]
		{
			new ItemStack(Items.paper), new ItemStack(Items.diamond)
		});

		GameRegistry.addRecipe(new ChemistJournalRecipeCloning());

		//
		this.addDecomposerRecipesFromMolecules();
		this.addSynthesisRecipesFromMolecules();
		this.addUnusedSynthesisRecipes();
		if (Settings.FoodSpiking)
		{
			this.registerPoisonRecipes(MoleculeEnum.mycotoxin);
			this.registerPoisonRecipes(MoleculeEnum.sucrose);
			this.registerPoisonRecipes(MoleculeEnum.psilocybin);
			this.registerPoisonRecipes(MoleculeEnum.methamphetamine);
			this.registerPoisonRecipes(MoleculeEnum.amphetamine);
			this.registerPoisonRecipes(MoleculeEnum.pantherine);
			this.registerPoisonRecipes(MoleculeEnum.ethanol);
			this.registerPoisonRecipes(MoleculeEnum.penicillin);
			this.registerPoisonRecipes(MoleculeEnum.testosterone);
			this.registerPoisonRecipes(MoleculeEnum.xanax);
			this.registerPoisonRecipes(MoleculeEnum.mescaline);
			this.registerPoisonRecipes(MoleculeEnum.asprin);
			this.registerPoisonRecipes(MoleculeEnum.sulfuricAcid);
			this.registerPoisonRecipes(MoleculeEnum.tetrodotoxin);
			this.registerPoisonRecipes(MoleculeEnum.pal2);
			this.registerPoisonRecipes(MoleculeEnum.nodularin);
			this.registerPoisonRecipes(MoleculeEnum.thc);
			this.registerPoisonRecipes(MoleculeEnum.radchlor); // Whoa, oh, oh, oh, I'm radioactive, radioactive
			this.registerPoisonRecipes(MoleculeEnum.cocaine);
			this.registerPoisonRecipes(MoleculeEnum.cocainehcl);
			this.registerPoisonRecipes(MoleculeEnum.theobromine);
			this.registerPoisonRecipes(MoleculeEnum.caulerpenyne);
			this.registerPoisonRecipes(MoleculeEnum.latropine);
		}
	}

	private void addDecomposerRecipesFromMolecules()
	{
//		List<MoleculeEnum> var1 = MoleculeEnum.molecules;
//		int var2 = var1.size();
//
//		for (int var3 = 0; var3 < var2; ++var3)
//		{
//			MoleculeEnum var4 = var1.get(var3);
		for (MoleculeEnum var4:MoleculeEnum.molecules.values())
		{
			if (var4 != null)
			{
				ArrayList var5 = var4.components();
				PotionChemical[] var6 = (PotionChemical[]) var5.toArray(new PotionChemical[var5.size()]);
				ItemStack var7 = new ItemStack(MinechemItemsRegistration.molecule, 1, var4.id());
				DecomposerRecipe.add(new DecomposerRecipe(var7, var6));
			}
		}

	}

	private void addSynthesisRecipesFromMolecules()
	{
		//List<MoleculeEnum> molecules = MoleculeEnum.molecules;

//		for (int i = 0; i < molecules.size(); ++i)
//		{
//			MoleculeEnum molecule = molecules.get(i);
		for (MoleculeEnum molecule:MoleculeEnum.molecules.values())
		{
			if (molecule != null)
			{
				ArrayList components = molecule.components();
				ItemStack moleculeItemStack = new ItemStack(MinechemItemsRegistration.molecule, 1, molecule.id());
				SynthesisRecipe.add(new SynthesisRecipe(moleculeItemStack, false, COST_ITEM, components));
			}
		}

	}

	private void addUnusedSynthesisRecipes()
	{
		Iterator decomposerRecipes = DecomposerRecipe.recipes.values().iterator();

		while (decomposerRecipes.hasNext())
		{
			DecomposerRecipe nextDecomposerRecipe = (DecomposerRecipe) decomposerRecipes.next();
			if (nextDecomposerRecipe.getInput().getItemDamage() != -1)
			{
				boolean check = false;
				Iterator synthesisRecipes = SynthesisRecipe.recipes.values().iterator();

				while (true)
				{
					if (synthesisRecipes.hasNext())
					{
						SynthesisRecipe nextSynthesisRecipe = (SynthesisRecipe) synthesisRecipes.next();
						if (!Compare.stacksAreSameKind(nextSynthesisRecipe.getOutput(), nextDecomposerRecipe.getInput()))
						{
							continue;
						}

						check = true;
					}

					if (!check)
					{
						ArrayList rawDecomposerRecipe = nextDecomposerRecipe.getOutputRaw();
						if (rawDecomposerRecipe != null)
						{
							if (shouldCreateSynthesis(nextDecomposerRecipe.getInput()))
							{
								SynthesisRecipe.add(new SynthesisRecipe(nextDecomposerRecipe.getInput(), false, 100, rawDecomposerRecipe));
							}
						}
					}
					break;
				}
			}
		}
	}

	public boolean shouldCreateSynthesis(ItemStack item)
	{
		if (item.getItem() instanceof ItemBlock)
		{
			return shouldCreateSynthesis((ItemBlock) item.getItem());
		}
		return true;
	}

	public boolean shouldCreateSynthesis(ItemBlock block)
	{
		// check if the block is an oreBlock
		if (block.field_150939_a instanceof BlockOre)
		{
			return false;
		}
		return true;
	}

	private ItemStack createPoisonedItemStack(Item item, int count, MoleculeEnum molecule)
	{
		ItemStack moleculeForShapelessRecipe = new ItemStack(MinechemItemsRegistration.molecule, 1, molecule.id());
		ItemStack var5 = new ItemStack(item, 1, count);
		ItemStack var6 = new ItemStack(item, 1, count);
		NBTTagCompound var7 = new NBTTagCompound();
		var7.setBoolean("minechem.isPoisoned", true);
		var7.setInteger("minechem.effectType", molecule.id());
		var6.setTagCompound(var7);
		GameRegistry.addShapelessRecipe(var6, new Object[]
		{
			moleculeForShapelessRecipe, var5
		});
		return var6;
	}

	private void registerPoisonRecipes(MoleculeEnum molecule)
	{
		Iterator<Item> it = Item.itemRegistry.iterator();

		while (it.hasNext())
		{
			Item item = it.next();
			if (item != null && item instanceof ItemFood)
			{
				this.createPoisonedItemStack(item, 0, molecule);
			}
		}
	}

	private ArrayList<OreDictionaryHandler> oreDictionaryHandlers;

	public void registerOreDictOres()
	{
		RegisterHandlers();
		for (String oreName : OreDictionary.getOreNames())
		{
			registerOre(oreName);
		}
	}

	public void registerOre(String oreName)
	{
		if (oreName.contains("gemApatite"))
		{
			DecomposerRecipe.createAndAddRecipeSafely(oreName, new PotionChemical[]
			{
				this.element(ElementEnum.Ca, 5), this.molecule(MoleculeEnum.phosphate, 4), this.element(ElementEnum.Cl)
			});
			SynthesisRecipe.createAndAddRecipeSafely(oreName, false, 1000, new PotionChemical[]
			{
				this.element(ElementEnum.Ca, 5), this.molecule(MoleculeEnum.phosphate, 4), this.element(ElementEnum.Cl)
			});
		} else if (oreName.contains("ingotBronze"))
		{
			if (Loader.isModLoaded("Mekanism"))
			{
				DecomposerRecipe.createAndAddRecipeSafely(oreName, new PotionChemical[]
				{
					this.element(ElementEnum.Cu, 16), this.element(ElementEnum.Sn, 2)
				});
				SynthesisRecipe.createAndAddRecipeSafely(oreName, false, 1000, new PotionChemical[]
				{
					this.element(ElementEnum.Cu, 16), this.element(ElementEnum.Sn, 2)
				});
			} else
			{
				DecomposerRecipe.createAndAddRecipeSafely(oreName, new PotionChemical[]
				{
					this.element(ElementEnum.Cu, 24), this.element(ElementEnum.Sn, 8)
				});
				SynthesisRecipe.createAndAddRecipeSafely(oreName, false, 1000, new PotionChemical[]
				{
					this.element(ElementEnum.Cu, 24), this.element(ElementEnum.Sn, 8)
				});
			}
		} else if (oreName.contains("plateSilicon"))
		{
			DecomposerRecipe.createAndAddRecipeSafely(oreName, new PotionChemical[]
			{
				this.element(ElementEnum.Si, 2)
			});
			SynthesisRecipe.createAndAddRecipeSafely(oreName, false, 1000, new PotionChemical[]
			{
				this.element(ElementEnum.Si, 2)
			});
		} else if (oreName.contains("xychoriumBlue"))
		{
			DecomposerRecipe.createAndAddRecipeSafely(oreName, new PotionChemical[]
			{
				this.element(ElementEnum.Zr, 2), this.element(ElementEnum.Cu, 1)
			});
			SynthesisRecipe.createAndAddRecipeSafely(oreName, false, 300, new PotionChemical[]
			{
				this.element(ElementEnum.Zr, 2), this.element(ElementEnum.Cu, 1)
			});
		} else if (oreName.contains("xychoriumRed"))
		{
			DecomposerRecipe.createAndAddRecipeSafely(oreName, new PotionChemical[]
			{
				this.element(ElementEnum.Zr, 2), this.element(ElementEnum.Fe, 1)
			});
			SynthesisRecipe.createAndAddRecipeSafely(oreName, false, 300, new PotionChemical[]
			{
				this.element(ElementEnum.Zr, 2), this.element(ElementEnum.Fe, 1)
			});
		} else if (oreName.contains("xychoriumGreen"))
		{
			DecomposerRecipe.createAndAddRecipeSafely(oreName, new PotionChemical[]
			{
				this.element(ElementEnum.Zr, 2), this.element(ElementEnum.V, 1)
			});
			SynthesisRecipe.createAndAddRecipeSafely(oreName, false, 300, new PotionChemical[]
			{
				this.element(ElementEnum.Zr, 2), this.element(ElementEnum.V, 1)
			});
		} else if (oreName.contains("xychoriumDark"))
		{
			DecomposerRecipe.createAndAddRecipeSafely(oreName, new PotionChemical[]
			{
				this.element(ElementEnum.Zr, 2), this.element(ElementEnum.Si, 1)
			});
			SynthesisRecipe.createAndAddRecipeSafely(oreName, false, 300, new PotionChemical[]
			{
				this.element(ElementEnum.Zr, 2), this.element(ElementEnum.Si, 1)
			});
		} else if (oreName.contains("xychoriumLight"))
		{
			DecomposerRecipe.createAndAddRecipeSafely(oreName, new PotionChemical[]
			{
				this.element(ElementEnum.Zr, 2), this.element(ElementEnum.Ti, 1)
			});
			SynthesisRecipe.createAndAddRecipeSafely(oreName, false, 300, new PotionChemical[]
			{
				this.element(ElementEnum.Zr, 2), this.element(ElementEnum.Ti, 1)
			});
		} else if (oreName.contains("gemPeridot"))
		{
			DecomposerRecipe.createAndAddRecipeSafely(oreName, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.olivine)
			});
			SynthesisRecipe.createAndAddRecipeSafely(oreName, false, 1000, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.olivine)
			});
		} else if (oreName.contains("cropMaloberry"))
		{
			DecomposerRecipe.createAndAddRecipeSafely(oreName, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.xylitol), this.molecule(MoleculeEnum.sucrose)
			});
			SynthesisRecipe.createAndAddRecipeSafely(oreName, false, 1000, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.xylitol), this.molecule(MoleculeEnum.sucrose)
			});
		} else if (oreName.contains("cropDuskberry"))
		{
			DecomposerRecipe.createAndAddRecipeSafely(oreName, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.psilocybin), this.element(ElementEnum.S, 2)
			});
			SynthesisRecipe.createAndAddRecipeSafely(oreName, false, 1000, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.psilocybin), this.element(ElementEnum.S, 2)
			});
		} else if (oreName.contains("cropSkyberry"))
		{
			DecomposerRecipe.createAndAddRecipeSafely(oreName, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.theobromine), this.element(ElementEnum.S, 2)
			});
			SynthesisRecipe.createAndAddRecipeSafely(oreName, false, 1000, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.theobromine), this.element(ElementEnum.S, 2)
			});
		} else if (oreName.contains("cropBlightberry"))
		{
			DecomposerRecipe.createAndAddRecipeSafely(oreName, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.asprin), this.element(ElementEnum.S, 2)
			});
			SynthesisRecipe.createAndAddRecipeSafely(oreName, false, 1000, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.asprin), this.element(ElementEnum.S, 2)
			});
		} else if (oreName.contains("cropBlueberry"))
		{
			DecomposerRecipe.createAndAddRecipeSafely(oreName, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.blueorgodye), this.molecule(MoleculeEnum.sucrose, 2)
			});
			SynthesisRecipe.createAndAddRecipeSafely(oreName, false, 1000, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.blueorgodye), this.molecule(MoleculeEnum.sucrose, 2)
			});
		} else if (oreName.contains("cropRaspberry"))
		{
			DecomposerRecipe.createAndAddRecipeSafely(oreName, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.redorgodye), this.molecule(MoleculeEnum.sucrose, 2)
			});
			SynthesisRecipe.createAndAddRecipeSafely(oreName, false, 1000, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.redorgodye), this.molecule(MoleculeEnum.sucrose, 2)
			});
		} else if (oreName.contains("cropBlackberry"))
		{
			DecomposerRecipe.createAndAddRecipeSafely(oreName, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.purpleorgodye), this.molecule(MoleculeEnum.sucrose, 2)
			});
			SynthesisRecipe.createAndAddRecipeSafely(oreName, false, 1000, new PotionChemical[]
			{
				this.molecule(MoleculeEnum.purpleorgodye), this.molecule(MoleculeEnum.sucrose, 2)
			});
		} else
		{
			for (OreDictionaryHandler handler : this.oreDictionaryHandlers)
			{
				if (handler.canHandle(oreName))
				{
					handler.handle(oreName);
					return;
				}
			}
		}
	}

	// END
	// BEGIN MISC FUNCTIONS
	private Element element(ElementEnum var1, int var2)
	{
		return new Element(var1, var2);
	}

	private Element element(ElementEnum var1)
	{
		return new Element(var1, 1);
	}

	private Molecule molecule(MoleculeEnum var1, int var2)
	{
		return new Molecule(var1, var2);
	}

	private Molecule molecule(MoleculeEnum var1)
	{
		return new Molecule(var1, 1);
	}

	// END
	public void RegisterHandlers()
	{
		this.oreDictionaryHandlers = new ArrayList<OreDictionaryHandler>();
		if (Loader.isModLoaded("Mekanism"))
		{
			this.oreDictionaryHandlers.add(new OreDictionaryMekanismHandler());
		}
		if (Loader.isModLoaded("UndergroundBiomes"))
		{
			this.oreDictionaryHandlers.add(new OreDictionaryUndergroundBiomesHandler());
		}
		if (Loader.isModLoaded("gregtech_addon"))
		{
			this.oreDictionaryHandlers.add(new OreDictionaryGregTechHandler());
		}
		if (Loader.isModLoaded("IC2"))
		{
			this.oreDictionaryHandlers.add(new OreDictionaryIC2Handler());
		}
		if (Loader.isModLoaded("appliedenergistics2"))
		{
			this.oreDictionaryHandlers.add(new OreDictionaryAppliedEnergisticsHandler());
		}
		this.oreDictionaryHandlers.add(new OreDictionaryDefaultHandler());
	}
} // EOF
