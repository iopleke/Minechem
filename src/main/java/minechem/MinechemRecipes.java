package minechem;

import java.util.ArrayList;
import java.util.Iterator;

import minechem.item.blueprint.ItemBlueprint;
import minechem.item.blueprint.MinechemBlueprint;
import minechem.item.chemistjournal.ChemistJournalRecipeCloning;
import minechem.item.element.Element;
import minechem.item.element.ElementEnum;
import minechem.item.element.ElementItem;
import minechem.item.molecule.MoleculeEnum;
import minechem.item.molecule.Molecule;
import minechem.oredictionary.OreDictionaryAppliedEnergisticsHandler;
import minechem.oredictionary.OreDictionaryDefaultHandler;
import minechem.oredictionary.OreDictionaryGregTechHandler;
import minechem.oredictionary.OreDictionaryHandler;
import minechem.oredictionary.OreDictionaryIC2Handler;
import minechem.oredictionary.OreDictionaryMekanismHandler;
import minechem.oredictionary.OreDictionaryUndergroundBiomesHandler;
import minechem.potion.PotionChemical;
import minechem.tileentity.decomposer.DecomposerFluidRecipe;
import minechem.tileentity.decomposer.DecomposerRecipe;
import minechem.tileentity.decomposer.DecomposerRecipeChance;
import minechem.tileentity.decomposer.DecomposerRecipeSelect;
import minechem.tileentity.synthesis.SynthesisRecipe;
import minechem.utils.Compare;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

public class MinechemRecipes
{

    private static final MinechemRecipes instance = new MinechemRecipes();
    public ArrayList unbondingRecipes = new ArrayList();
    public ArrayList synthesisRecipes = new ArrayList();

    public static MinechemRecipes getInstance()
    {
        return instance;
    }

    public void registerFluidRecipies()
    {
        // Fluids
        int fluidPerIngot = 144;
        // Quick and dirty fix for the Thermal Expansion Resonant Ender 6x bug
        int threeQuarterFluidPerIngot = 180;
        DecomposerRecipe.add(new DecomposerFluidRecipe(new FluidStack(FluidRegistry.WATER, 10000), new PotionChemical[]
        {
            this.element(ElementEnum.H, 2), this.element(ElementEnum.O)
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
        DecomposerFluidRecipe.createAndAddFluidRecipeSafely("obsidian.molten", fluidPerIngot, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.siliconDioxide, 16), this.molecule(MoleculeEnum.magnesiumOxide, 8)
        });
        DecomposerFluidRecipe.createAndAddFluidRecipeSafely("glass.molten", fluidPerIngot, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.siliconDioxide, 16)
        });
        DecomposerFluidRecipe.createAndAddFluidRecipeSafely("emerald.molten", fluidPerIngot, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.beryl, 6), this.element(ElementEnum.Cr, 6), this.element(ElementEnum.V, 6)
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
        DecomposerFluidRecipe.createAndAddFluidRecipeSafely(
                "ender",
                threeQuarterFluidPerIngot,
                new PotionChemical[]
                {
                    this.molecule(MoleculeEnum.calciumCarbonate), this.molecule(MoleculeEnum.calciumCarbonate), this.molecule(MoleculeEnum.calciumCarbonate), this.molecule(MoleculeEnum.calciumCarbonate), this.element(ElementEnum.Es),
                    this.molecule(MoleculeEnum.calciumCarbonate), this.molecule(MoleculeEnum.calciumCarbonate), this.molecule(MoleculeEnum.calciumCarbonate), this.molecule(MoleculeEnum.calciumCarbonate)
                });

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
        ItemStack blockStone = new ItemStack(Block.getBlockFromName("stone"));
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
            })
        }));
        SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Block.stone, 7), true, 50, new PotionChemical[]
        {
            this.element(ElementEnum.Si), null, null, this.element(ElementEnum.O, 2), null, null
        }));

        // Grass Block
        ItemStack blockGrass = new ItemStack(Block.grass);
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
        SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Block.grass, 16), true, 50, new PotionChemical[]
        {
            null, moleculeCellulose, null, null, this.element(ElementEnum.O, 2), this.element(ElementEnum.Si)
        }));

        // Dirt
        ItemStack blockDirt = new ItemStack(Block.dirt);
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
        SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Block.dirt, 16), true, 50, new PotionChemical[]
        {
            null, null, null, null, this.element(ElementEnum.O, 2), this.element(ElementEnum.Si)
        }));

        // Cobblestone
        ItemStack blockCobblestone = new ItemStack(Block.cobblestone);
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
        SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Block.cobblestone, 8), true, 50, new PotionChemical[]
        {
            this.element(ElementEnum.Si), null, null, null, this.element(ElementEnum.O, 2), null
        }));

        // Planks
        // TODO: Add synthesizer recipes?
        ItemStack blockOakWoodPlanks = new ItemStack(Block.planks, 1, 0);
        ItemStack blockSpruceWoodPlanks = new ItemStack(Block.planks, 1, 1);
        ItemStack blockBirchWoodPlanks = new ItemStack(Block.planks, 1, 2);
        ItemStack blockJungleWoodPlanks = new ItemStack(Block.planks, 1, 3);
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

        // Saplings
        ItemStack blockOakSapling = new ItemStack(Block.sapling, 1, 0);
        ItemStack blockSpruceSapling = new ItemStack(Block.sapling, 1, 1);
        ItemStack blockBirchSapling = new ItemStack(Block.sapling, 1, 2);
        ItemStack blockJungleSapling = new ItemStack(Block.sapling, 1, 3);
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
        SynthesisRecipe.add(new SynthesisRecipe(blockOakSapling, true, 20, new PotionChemical[]
        {
            null, null, null, null, null, null, null, null, this.molecule(MoleculeEnum.cellulose)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(blockSpruceSapling, true, 20, new PotionChemical[]
        {
            null, null, null, null, null, null, null, this.molecule(MoleculeEnum.cellulose), null
        }));
        SynthesisRecipe.add(new SynthesisRecipe(blockBirchSapling, true, 20, new PotionChemical[]
        {
            null, null, null, null, null, null, this.molecule(MoleculeEnum.cellulose), null, null
        }));
        SynthesisRecipe.add(new SynthesisRecipe(blockJungleSapling, true, 20, new PotionChemical[]
        {
            null, null, null, null, null, this.molecule(MoleculeEnum.cellulose), null, null, null
        }));

        // Lava
        // TODO: Add support for lava
        // Sand
        ItemStack blockSand = new ItemStack(Block.sand);
        DecomposerRecipe.add(new DecomposerRecipe(blockSand, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.siliconDioxide, 16)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(blockSand, true, 200, new PotionChemical[]
        {
            moleculeSiliconDioxide, moleculeSiliconDioxide, moleculeSiliconDioxide, moleculeSiliconDioxide
        }));

        // Gravel
        ItemStack blockGravel = new ItemStack(Block.gravel);
        DecomposerRecipe.add(new DecomposerRecipeChance(blockGravel, 0.35F, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.siliconDioxide)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(blockGravel, true, 30, new PotionChemical[]
        {
            null, null, null, null, null, null, null, null, this.molecule(MoleculeEnum.siliconDioxide)
        }));

        // Gold Ore
        ItemStack oreGold = new ItemStack(Block.oreGold);
        DecomposerRecipe.add(new DecomposerRecipe(oreGold, new PotionChemical[]
        {
            this.element(ElementEnum.Au, 48)
        }));

        // Iron Ore
        ItemStack oreIron = new ItemStack(Block.oreIron);
        DecomposerRecipe.add(new DecomposerRecipe(oreIron, new PotionChemical[]
        {
            this.element(ElementEnum.Fe, 48)
        }));

        // Coal Ore
        ItemStack oreCoal = new ItemStack(Block.oreCoal);
        DecomposerRecipe.add(new DecomposerRecipe(oreCoal, new PotionChemical[]
        {
            this.element(ElementEnum.C, 48)
        }));

        // Wood
        ItemStack blockOakWood = new ItemStack(Block.wood, 1, 0);
        ItemStack blockSpruceWood = new ItemStack(Block.wood, 1, 1);
        ItemStack blockBirchWood = new ItemStack(Block.wood, 1, 2);
        ItemStack blockJungleWood = new ItemStack(Block.wood, 1, 3);
        DecomposerRecipe.add(new DecomposerRecipeChance(blockOakWood, 0.5F, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.cellulose, 8)
        }));
        DecomposerRecipe.add(new DecomposerRecipeChance(blockSpruceWood, 0.5F, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.cellulose, 8)
        }));
        DecomposerRecipe.add(new DecomposerRecipeChance(blockBirchWood, 0.5F, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.cellulose, 8)
        }));
        DecomposerRecipe.add(new DecomposerRecipeChance(blockJungleWood, 0.5F, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.cellulose, 8)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(blockOakWood, true, 100, new PotionChemical[]
        {
            moleculeCellulose, moleculeCellulose, moleculeCellulose, null, moleculeCellulose, null, null, null, null
        }));
        SynthesisRecipe.add(new SynthesisRecipe(blockSpruceWood, true, 100, new PotionChemical[]
        {
            null, null, null, null, moleculeCellulose, null, moleculeCellulose, moleculeCellulose, moleculeCellulose
        }));
        SynthesisRecipe.add(new SynthesisRecipe(blockBirchWood, true, 100, new PotionChemical[]
        {
            moleculeCellulose, null, moleculeCellulose, null, null, null, moleculeCellulose, null, moleculeCellulose
        }));
        SynthesisRecipe.add(new SynthesisRecipe(blockJungleWood, true, 100, new PotionChemical[]
        {
            moleculeCellulose, null, null, moleculeCellulose, moleculeCellulose, null, moleculeCellulose, null, null
        }));

        // Leaves
        // TODO: Add support for leaves
        // Glass
        ItemStack blockGlass = new ItemStack(Block.glass);
        DecomposerRecipe.add(new DecomposerRecipe(blockGlass, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.siliconDioxide, 16)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(blockGlass, true, 500, new PotionChemical[]
        {
            moleculeSiliconDioxide, null, moleculeSiliconDioxide, null, null, null, moleculeSiliconDioxide, null, moleculeSiliconDioxide
        }));

        // Lapis Lazuli Ore
        ItemStack blockOreLapis = new ItemStack(Block.oreLapis);
        DecomposerRecipe.add(new DecomposerRecipe(blockOreLapis, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.lazurite, 6), this.molecule(MoleculeEnum.sodalite), this.molecule(MoleculeEnum.noselite), this.molecule(MoleculeEnum.calcite), this.molecule(MoleculeEnum.pyrite)
        }));

        // Lapis Lazuli Block
        ItemStack blockLapis = new ItemStack(Block.blockLapis);
        DecomposerRecipe.add(new DecomposerRecipe(blockLapis, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.lazurite, 9)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(blockLapis, true, 450, new PotionChemical[]
        {
            moleculeLazurite, null, null, null, null, null, null, null, null
        }));

        // Cobweb
        ItemStack blockCobweb = new ItemStack(Block.web);
        DecomposerRecipe.add(new DecomposerRecipe(blockCobweb, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.fibroin)
        }));

        // Tall Grass
        ItemStack blockTallGrass = new ItemStack(Block.tallGrass, 1, 1);
        DecomposerRecipe.add(new DecomposerRecipeChance(blockTallGrass, 0.1F, new PotionChemical[]
        {
            new Molecule(MoleculeEnum.thc, 2)
        }));

        // Sandstone
        ItemStack blockSandStone = new ItemStack(Block.sandStone, 1, 0);
        ItemStack blockChiseledSandStone = new ItemStack(Block.sandStone, 1, 1);
        ItemStack blockSmoothSandStone = new ItemStack(Block.sandStone, 1, 2);
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
        SynthesisRecipe.add(new SynthesisRecipe(blockSandStone, true, 20, new PotionChemical[]
        {
            null, null, null, null, this.molecule(MoleculeEnum.siliconDioxide, 16), null, null, null, null
        }));
        SynthesisRecipe.add(new SynthesisRecipe(blockChiseledSandStone, true, 20, new PotionChemical[]
        {
            null, null, null, null, null, null, null, this.molecule(MoleculeEnum.siliconDioxide, 16), null
        }));
        SynthesisRecipe.add(new SynthesisRecipe(blockSmoothSandStone, true, 20, new PotionChemical[]
        {
            null, this.molecule(MoleculeEnum.siliconDioxide, 16), null, null, null, null, null, null, null
        }));

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
        DecomposerRecipe.add(new DecomposerRecipeChance(blockWool, 0.6F, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.whitePigment)
        }));
        DecomposerRecipe.add(new DecomposerRecipeChance(blockOrangeWool, 0.6F, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.orangePigment)
        }));
        DecomposerRecipe.add(new DecomposerRecipeChance(blockMagentaWool, 0.6F, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.lightbluePigment), this.molecule(MoleculeEnum.redPigment)
        }));
        DecomposerRecipe.add(new DecomposerRecipeChance(blockLightBlueWool, 0.6F, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.lightbluePigment)
        }));
        DecomposerRecipe.add(new DecomposerRecipeChance(blockYellowWool, 0.6F, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.yellowPigment)
        }));
        DecomposerRecipe.add(new DecomposerRecipeChance(blockLimeWool, 0.6F, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.limePigment)
        }));
        DecomposerRecipe.add(new DecomposerRecipeChance(blockPinkWool, 0.6F, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.redPigment), this.molecule(MoleculeEnum.whitePigment)
        }));
        DecomposerRecipe.add(new DecomposerRecipeChance(blockGrayWool, 0.6F, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.whitePigment), this.molecule(MoleculeEnum.blackPigment, 2)
        }));
        DecomposerRecipe.add(new DecomposerRecipeChance(blockLightGrayWool, 0.6F, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.whitePigment), this.molecule(MoleculeEnum.blackPigment)
        }));
        DecomposerRecipe.add(new DecomposerRecipeChance(blockCyanWool, 0.6F, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.lightbluePigment), this.molecule(MoleculeEnum.whitePigment)
        }));
        DecomposerRecipe.add(new DecomposerRecipeChance(blockPurpleWool, 0.6F, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.purplePigment)
        }));
        DecomposerRecipe.add(new DecomposerRecipeChance(blockBlueWool, 0.6F, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.lazurite)
        }));
        DecomposerRecipe.add(new DecomposerRecipeChance(blockBrownWool, 0.6F, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.tannicacid)
        }));
        DecomposerRecipe.add(new DecomposerRecipeChance(blockGreenWool, 0.6F, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.greenPigment)
        }));
        DecomposerRecipe.add(new DecomposerRecipeChance(blockRedWool, 0.6F, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.redPigment)
        }));
        DecomposerRecipe.add(new DecomposerRecipeChance(blockBlackWool, 0.6F, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.blackPigment)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(blockWool, false, 50, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.whitePigment)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(blockOrangeWool, false, 50, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.orangePigment)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(blockMagentaWool, false, 50, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.lightbluePigment), this.molecule(MoleculeEnum.redPigment)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(blockLightBlueWool, false, 50, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.lightbluePigment)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(blockYellowWool, false, 50, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.yellowPigment)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(blockLimeWool, false, 50, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.limePigment)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(blockPinkWool, false, 50, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.redPigment), this.molecule(MoleculeEnum.whitePigment)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(blockGrayWool, false, 50, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.whitePigment), this.molecule(MoleculeEnum.blackPigment, 2)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(blockLightGrayWool, false, 50, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.whitePigment), this.molecule(MoleculeEnum.blackPigment)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(blockCyanWool, false, 50, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.lightbluePigment), this.molecule(MoleculeEnum.whitePigment)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(blockPurpleWool, false, 50, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.purplePigment)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(blockBlueWool, false, 50, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.lazurite)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(blockGreenWool, false, 50, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.greenPigment)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(blockRedWool, false, 50, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.redPigment)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(blockBlackWool, false, 50, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.blackPigment)
        }));

        // Wool carpet
        ItemStack carpetBlockWool = new ItemStack(Block.carpet, 1, 0);
        ItemStack carpetBlockOrangeWool = new ItemStack(Block.carpet, 1, 1);
        ItemStack carpetBlockMagentaWool = new ItemStack(Block.carpet, 1, 2);
        ItemStack carpetBlockLightBlueWool = new ItemStack(Block.carpet, 1, 3);
        ItemStack carpetBlockYellowWool = new ItemStack(Block.carpet, 1, 4);
        ItemStack carpetBlockLimeWool = new ItemStack(Block.carpet, 1, 5);
        ItemStack carpetBlockPinkWool = new ItemStack(Block.carpet, 1, 6);
        ItemStack carpetBlockGrayWool = new ItemStack(Block.carpet, 1, 7);
        ItemStack carpetBlockLightGrayWool = new ItemStack(Block.carpet, 1, 8);
        ItemStack carpetBlockCyanWool = new ItemStack(Block.carpet, 1, 9);
        ItemStack carpetBlockPurpleWool = new ItemStack(Block.carpet, 1, 10);
        ItemStack carpetBlockBlueWool = new ItemStack(Block.carpet, 1, 11);
        ItemStack carpetBlockBrownWool = new ItemStack(Block.carpet, 1, 12);
        ItemStack carpetBlockGreenWool = new ItemStack(Block.carpet, 1, 13);
        ItemStack carpetBlockRedWool = new ItemStack(Block.carpet, 1, 14);
        ItemStack carpetBlockBlackWool = new ItemStack(Block.carpet, 1, 15);
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
        SynthesisRecipe.add(new SynthesisRecipe(carpetBlockWool, false, 50, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.whitePigment)
        }));

        // Flowers
        // TODO: Add support for Rose
        ItemStack blockPlantYellow = new ItemStack(Block.plantYellow);
        DecomposerRecipe.add(new DecomposerRecipeChance(blockPlantYellow, 0.3F, new PotionChemical[]
        {
            new Molecule(MoleculeEnum.shikimicAcid, 2)
        }));

        // Mushrooms
        ItemStack blockMushroomBrown = new ItemStack(Block.mushroomBrown);
        ItemStack blockMushroomRed = new ItemStack(Block.mushroomRed);
        DecomposerRecipe.add(new DecomposerRecipe(blockMushroomBrown, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.psilocybin), this.molecule(MoleculeEnum.water, 2)
        }));
        DecomposerRecipe.add(new DecomposerRecipe(blockMushroomRed, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.pantherine), this.molecule(MoleculeEnum.water, 2)
        }));

        // Block of Gold
        DecomposerRecipe.add(new DecomposerRecipe(new ItemStack(Block.blockGold), new PotionChemical[]
        {
            this.element(ElementEnum.Au, 144)
        }));

        // Block of Iron
        DecomposerRecipe.add(new DecomposerRecipe(new ItemStack(Block.blockIron), new PotionChemical[]
        {
            this.element(ElementEnum.Fe, 144)
        }));

        // Slabs
        // TODO: Add support for slabs?
        // TNT
        ItemStack blockTnt = new ItemStack(Block.tnt);
        DecomposerRecipe.add(new DecomposerRecipe(blockTnt, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.tnt)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(blockTnt, false, 1000, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.tnt)
        }));

        // Obsidian
        ItemStack blockObsidian = new ItemStack(Block.obsidian);
        DecomposerRecipe.add(new DecomposerRecipe(blockObsidian, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.siliconDioxide, 16), this.molecule(MoleculeEnum.magnesiumOxide, 8)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(blockObsidian, true, 1000, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.siliconDioxide, 4), this.molecule(MoleculeEnum.siliconDioxide, 4), this.molecule(MoleculeEnum.siliconDioxide, 4), this.molecule(MoleculeEnum.magnesiumOxide, 2), null, this.molecule(MoleculeEnum.siliconDioxide, 4),
            this.molecule(MoleculeEnum.magnesiumOxide, 2), this.molecule(MoleculeEnum.magnesiumOxide, 2), this.molecule(MoleculeEnum.magnesiumOxide, 2)
        }));

        // Diamond Ore
        ItemStack blockOreDiamond = new ItemStack(Block.oreDiamond);
        DecomposerRecipe.add(new DecomposerRecipe(blockOreDiamond, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.fullrene, 6)
        }));

        // Block of Diamond
        ItemStack blockDiamond = new ItemStack(Block.blockDiamond);
        DecomposerRecipe.add(new DecomposerRecipe(blockDiamond, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.fullrene, 36)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(blockDiamond, true, 120000, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.fullrene, 4), this.molecule(MoleculeEnum.fullrene, 4), this.molecule(MoleculeEnum.fullrene, 4), this.molecule(MoleculeEnum.fullrene, 4), this.molecule(MoleculeEnum.fullrene, 4), this.molecule(MoleculeEnum.fullrene, 4),
            this.molecule(MoleculeEnum.fullrene, 4), this.molecule(MoleculeEnum.fullrene, 4), this.molecule(MoleculeEnum.fullrene, 4)
        }));

        // Pressure Plate
        ItemStack blockPressurePlatePlanks = new ItemStack(Block.pressurePlatePlanks);
        DecomposerRecipe.add(new DecomposerRecipeChance(blockPressurePlatePlanks, 0.4F, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.cellulose, 4)
        }));

        // Redston Ore
        ItemStack blockOreRedstone = new ItemStack(Block.oreRedstone);
        DecomposerRecipe.add(new DecomposerRecipeChance(blockOreRedstone, 0.8F, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.iron3oxide, 9), this.element(ElementEnum.Cu, 9)
        }));

        // Cactus
        ItemStack blockCactus = new ItemStack(Block.cactus);
        DecomposerRecipe.add(new DecomposerRecipe(blockCactus, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.mescaline), this.molecule(MoleculeEnum.water, 20)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(blockCactus, true, 200, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.water, 5), null, this.molecule(MoleculeEnum.water, 5), null, this.molecule(MoleculeEnum.mescaline), null, this.molecule(MoleculeEnum.water, 5), null, this.molecule(MoleculeEnum.water, 5)
        }));

        // Pumpkin
        ItemStack blockPumpkin = new ItemStack(Block.pumpkin);
        DecomposerRecipe.add(new DecomposerRecipe(blockPumpkin, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.cucurbitacin)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(blockPumpkin, false, 400, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.cucurbitacin)
        }));

        // Netherrack
        ItemStack blockNetherrack = new ItemStack(Block.netherrack);
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

        // Nether B
        ItemStack itemNetherbrick = new ItemStack(Item.netherrackBrick);
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
        SynthesisRecipe.add(new SynthesisRecipe(itemNetherbrick, true, 200, new PotionChemical[]
        {
            this.element(ElementEnum.Si, 2), this.element(ElementEnum.Si, 2), null, this.element(ElementEnum.Zn, 2), this.element(ElementEnum.W, 1), null, this.element(ElementEnum.Be, 2), this.element(ElementEnum.Be, 2), null
        }));

        // Water Bottle
        ItemStack itemPotion = new ItemStack(Item.potion, 1, 0);
        DecomposerRecipe.add(new DecomposerRecipe(itemPotion, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.water, 8)
        }));

        // Soul Sand
        ItemStack blockSlowSand = new ItemStack(Block.slowSand);
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
        ItemStack blockGlowStone = new ItemStack(Block.glowStone);
        DecomposerRecipe.add(new DecomposerRecipe(blockGlowStone, new PotionChemical[]
        {
            this.element(ElementEnum.P, 4)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(blockGlowStone, true, 500, new PotionChemical[]
        {
            this.element(ElementEnum.P), null, this.element(ElementEnum.P), this.element(ElementEnum.P), null, this.element(ElementEnum.P), null, null, null
        }));

        // Glass Panes
        ItemStack blockThinGlass = new ItemStack(Block.thinGlass);
        DecomposerRecipe.add(new DecomposerRecipe(blockThinGlass, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.siliconDioxide, 1)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(blockThinGlass, true, 50, new PotionChemical[]
        {
            null, null, null, this.molecule(MoleculeEnum.siliconDioxide), null, null, null, null, null
        }));

        // Melon
        ItemStack blockMelon = new ItemStack(Block.melon);
        DecomposerRecipe.add(new DecomposerRecipe(blockMelon, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.cucurbitacin), this.molecule(MoleculeEnum.asparticAcid), this.molecule(MoleculeEnum.water, 16)
        }));

        // Mycelium
        ItemStack blockMycelium = new ItemStack(Block.mycelium);
        DecomposerRecipe.add(new DecomposerRecipeChance(blockMycelium, 0.09F, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.fingolimod)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Block.mycelium, 16), false, 300, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.fingolimod)
        }));

        // End Stone
        ItemStack blockWhiteStone = new ItemStack(Block.whiteStone);
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
        ItemStack blockOreEmerald = new ItemStack(Block.oreEmerald);
        DecomposerRecipe.add(new DecomposerRecipe(blockOreEmerald, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.beryl, 6), this.element(ElementEnum.Cr, 6), this.element(ElementEnum.V, 6)
        }));

        // Emerald Block
        ItemStack blockEmerald = new ItemStack(Block.blockEmerald);
        SynthesisRecipe.add(new SynthesisRecipe(blockEmerald, true, 150000, new PotionChemical[]
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
        ItemStack itemAppleRed = new ItemStack(Item.appleRed);
        DecomposerRecipe.add(new DecomposerRecipe(itemAppleRed, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.malicAcid)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(itemAppleRed, false, 400, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.malicAcid), this.molecule(MoleculeEnum.water, 2)
        }));

        // Arrow
        ItemStack itemArrow = new ItemStack(Item.arrow);
        DecomposerRecipe.add(new DecomposerRecipe(itemArrow, new PotionChemical[]
        {
            this.element(ElementEnum.Si), this.element(ElementEnum.O, 2), this.element(ElementEnum.N, 6)
        }));

        // Coal
        ItemStack itemCoal = new ItemStack(Item.coal);
        DecomposerRecipe.add(new DecomposerRecipeChance(itemCoal, 0.92F, new PotionChemical[]
        {
            this.element(ElementEnum.C, 8)
        }));

        // Coal Block
        ItemStack blockCoal = new ItemStack(Block.coalBlock);
        DecomposerRecipe.add(new DecomposerRecipeChance(blockCoal, 0.82F, new PotionChemical[]
        {
            this.element(ElementEnum.C, 72)
        }));

        // Charcoal
        ItemStack itemChar = new ItemStack(Item.coal, 1, 1);
        DecomposerRecipe.add(new DecomposerRecipeChance(itemChar, 0.82F, new PotionChemical[]
        {
            this.element(ElementEnum.C, 1)
        }));

        // Diamond
        ItemStack itemDiamond = new ItemStack(Item.diamond);
        DecomposerRecipe.add(new DecomposerRecipe(itemDiamond, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.fullrene, 4)
        }));

        // Polytool
        SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(MinechemItemsGeneration.polytool), true, '\uea60', new PotionChemical[]
        {
            null, this.molecule(MoleculeEnum.fullrene, 64), null, this.molecule(MoleculeEnum.fullrene, 64), null, this.molecule(MoleculeEnum.fullrene, 64), null, this.molecule(MoleculeEnum.fullrene, 64), null
        }));
        SynthesisRecipe.add(new SynthesisRecipe(itemDiamond, true, '\uea60', new PotionChemical[]
        {
            null, this.molecule(MoleculeEnum.fullrene), null, this.molecule(MoleculeEnum.fullrene), null, this.molecule(MoleculeEnum.fullrene), null, this.molecule(MoleculeEnum.fullrene), null
        }));

        // Iron Ingot
        ItemStack itemIngotIron = new ItemStack(Item.ingotIron);
        DecomposerRecipe.add(new DecomposerRecipe(itemIngotIron, new PotionChemical[]
        {
            this.element(ElementEnum.Fe, 16)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(itemIngotIron, false, 1000, new PotionChemical[]
        {
            this.element(ElementEnum.Fe, 16)
        }));

        // Gold Ingot
        ItemStack itemIngotGold = new ItemStack(Item.ingotGold);
        DecomposerRecipe.add(new DecomposerRecipe(itemIngotGold, new PotionChemical[]
        {
            this.element(ElementEnum.Au, 16)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(itemIngotGold, false, 1000, new PotionChemical[]
        {
            this.element(ElementEnum.Au, 16)
        }));

        // Stick
        ItemStack itemStick = new ItemStack(Item.stick);
        DecomposerRecipe.add(new DecomposerRecipeChance(itemStick, 0.3F, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.cellulose)
        }));

        // String
        ItemStack itemSilk = new ItemStack(Item.silk);
        DecomposerRecipe.add(new DecomposerRecipeChance(itemSilk, 0.45F, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.serine), this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.alinine)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(itemSilk, true, 150, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.serine), this.molecule(MoleculeEnum.glycine), this.molecule(MoleculeEnum.alinine)
        }));

        // Feather
        ItemStack itemFeather = new ItemStack(Item.feather);
        DecomposerRecipe.add(new DecomposerRecipe(itemFeather, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.water, 8), this.element(ElementEnum.N, 6)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(itemFeather, true, 800, new PotionChemical[]
        {
            this.element(ElementEnum.N), this.molecule(MoleculeEnum.water, 2), this.element(ElementEnum.N), this.element(ElementEnum.N), this.molecule(MoleculeEnum.water, 1), this.element(ElementEnum.N), this.element(ElementEnum.N),
            this.molecule(MoleculeEnum.water, 5), this.element(ElementEnum.N)
        }));

        // Gunpowder
        ItemStack itemGunpowder = new ItemStack(Item.gunpowder);
        DecomposerRecipe.add(new DecomposerRecipe(itemGunpowder, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.potassiumNitrate), this.element(ElementEnum.S, 2), this.element(ElementEnum.C)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(itemGunpowder, true, 600, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.potassiumNitrate), this.element(ElementEnum.C), null, this.element(ElementEnum.S, 2), null, null, null, null, null
        }));

        // Bread
        ItemStack itemBread = new ItemStack(Item.bread);
        DecomposerRecipe.add(new DecomposerRecipeChance(itemBread, 0.1F, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.starch), this.molecule(MoleculeEnum.sucrose)
        }));

        // Flint
        ItemStack itemFlint = new ItemStack(Item.flint);
        DecomposerRecipe.add(new DecomposerRecipeChance(itemFlint, 0.5F, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.siliconDioxide)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(itemFlint, true, 100, new PotionChemical[]
        {
            null, moleculeSiliconDioxide, null, moleculeSiliconDioxide, moleculeSiliconDioxide, moleculeSiliconDioxide, null, null, null
        }));

        // Golden Apple
        ItemStack itemAppleGold = new ItemStack(Item.appleGold, 1, 0);
        DecomposerRecipe.add(new DecomposerRecipe(itemAppleGold, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.malicAcid), this.element(ElementEnum.Au, 64)
        }));

        // Wooden Door
        ItemStack itemDoorWood = new ItemStack(Item.doorWood);
        DecomposerRecipe.add(new DecomposerRecipeChance(itemDoorWood, 0.4F, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.cellulose, 12)
        }));

        // Water Bucket
        ItemStack itemBucketWater = new ItemStack(Item.bucketWater);
        DecomposerRecipe.add(new DecomposerRecipe(itemBucketWater, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.water, 16)
        }));

        // Redstone
        ItemStack itemRedstone = new ItemStack(Item.redstone);
        DecomposerRecipe.add(new DecomposerRecipeChance(itemRedstone, 0.42F, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.iron3oxide), this.element(ElementEnum.Cu)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(itemRedstone, true, 100, new PotionChemical[]
        {
            null, null, this.molecule(MoleculeEnum.iron3oxide), null, this.element(ElementEnum.Cu), null, null, null, null
        }));
        // Redstone Block
        ItemStack blockRedstone = new ItemStack(Block.blockRedstone);
        DecomposerRecipe.add(new DecomposerRecipeChance(blockRedstone, 0.42F, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.iron3oxide, 9), this.element(ElementEnum.Cu, 9)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(blockRedstone, true, 900, new PotionChemical[]
        {
            null, null, this.molecule(MoleculeEnum.iron3oxide, 9), null, this.element(ElementEnum.Cu, 9), null, null, null, null
        }));

        // Snowball
        ItemStack itemSnowball = new ItemStack(Item.snowball);
        DecomposerRecipe.add(new DecomposerRecipe(itemSnowball, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.water)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Item.snowball, 5), true, 20, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.water), null, this.molecule(MoleculeEnum.water), null, this.molecule(MoleculeEnum.water), null, this.molecule(MoleculeEnum.water), null, this.molecule(MoleculeEnum.water)
        }));

        // Leather
        ItemStack itemLeather = new ItemStack(Item.leather);
        DecomposerRecipe.add(new DecomposerRecipeChance(itemLeather, 0.5F, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.keratin)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Item.leather, 5), true, 700, new PotionChemical[]
        {
            null, null, null, null, this.molecule(MoleculeEnum.keratin), null, null, null, null
        }));

        // Brick
        ItemStack itemBrick = new ItemStack(Item.brick);
        DecomposerRecipe.add(new DecomposerRecipeChance(itemBrick, 0.5F, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.kaolinite)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Item.brick, 8), true, 400, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.kaolinite), this.molecule(MoleculeEnum.kaolinite), null, this.molecule(MoleculeEnum.kaolinite), this.molecule(MoleculeEnum.kaolinite), null
        }));

        // Clay
        ItemStack itemClay = new ItemStack(Item.clay);
        DecomposerRecipe.add(new DecomposerRecipeChance(itemClay, 0.3F, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.kaolinite)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Item.clay, 12), false, 100, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.kaolinite)
        }));

        // Reed
        ItemStack itemReed = new ItemStack(Item.reed);
        DecomposerRecipe.add(new DecomposerRecipeChance(itemReed, 0.65F, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.sucrose), this.element(ElementEnum.H, 2), this.element(ElementEnum.O)
        }));

        // Paper
        ItemStack itemPaper = new ItemStack(Item.paper);
        DecomposerRecipe.add(new DecomposerRecipeChance(itemPaper, 0.25F, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.cellulose)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Item.paper, 16), true, 150, new PotionChemical[]
        {
            null, this.molecule(MoleculeEnum.cellulose), null, null, this.molecule(MoleculeEnum.cellulose), null, null, this.molecule(MoleculeEnum.cellulose), null
        }));

        // Slimeball
        ItemStack itemSlimeBall = new ItemStack(Item.slimeBall);
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
        ItemStack itemGlowstone = new ItemStack(Item.glowstone);
        DecomposerRecipe.add(new DecomposerRecipe(itemGlowstone, new PotionChemical[]
        {
            this.element(ElementEnum.P)
        }));

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
        SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderBlack, false, 50, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.blackPigment)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderRed, false, 50, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.redPigment)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderGreen, false, 50, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.greenPigment)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderBrown, false, 400, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.theobromine)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderBlue, false, 50, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.lazurite)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderPurple, false, 50, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.purplePigment)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderCyan, false, 50, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.lightbluePigment), this.molecule(MoleculeEnum.whitePigment)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderLightGray, false, 50, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.whitePigment), this.molecule(MoleculeEnum.blackPigment)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderGray, false, 50, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.whitePigment), this.molecule(MoleculeEnum.blackPigment, 2)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderPink, false, 50, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.redPigment), this.molecule(MoleculeEnum.whitePigment)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderLime, false, 50, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.limePigment)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderYellow, false, 50, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.yellowPigment)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderLightBlue, false, 50, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.lightbluePigment)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderMagenta, false, 50, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.lightbluePigment), this.molecule(MoleculeEnum.redPigment)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderOrange, false, 50, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.orangePigment)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(itemDyePowderWhite, false, 50, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.whitePigment)
        }));

        // Bone
        ItemStack itemBone = new ItemStack(Item.bone);
        DecomposerRecipe.add(new DecomposerRecipe(itemBone, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.hydroxylapatite)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(itemBone, false, 100, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.hydroxylapatite)
        }));

        // Sugar
        ItemStack itemSugar = new ItemStack(Item.sugar);
        DecomposerRecipe.add(new DecomposerRecipeChance(itemSugar, 0.75F, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.sucrose)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(itemSugar, false, 400, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.sucrose)
        }));

        // Melon
        ItemStack itemMelon = new ItemStack(Item.melon);
        DecomposerRecipe.add(new DecomposerRecipe(itemMelon, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.water)
        }));

        // Cooked Chicken
        ItemStack itemChickenCooked = new ItemStack(Item.chickenCooked);
        DecomposerRecipe.add(new DecomposerRecipe(itemChickenCooked, new PotionChemical[]
        {
            this.element(ElementEnum.K), this.element(ElementEnum.Na), this.element(ElementEnum.C, 2)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(itemChickenCooked, true, 5000, new PotionChemical[]
        {
            this.element(ElementEnum.K, 16), this.element(ElementEnum.Na, 16), this.element(ElementEnum.C, 16)
        }));

        // Rotten Flesh
        ItemStack itemRottenFlesh = new ItemStack(Item.rottenFlesh);
        DecomposerRecipe.add(new DecomposerRecipeChance(itemRottenFlesh, 0.05F, new PotionChemical[]
        {
            new Molecule(MoleculeEnum.nodularin, 1)
        }));

        // Enderpearl
        ItemStack itemEnderPearl = new ItemStack(Item.enderPearl);
        DecomposerRecipe.add(new DecomposerRecipe(itemEnderPearl, new PotionChemical[]
        {
            this.element(ElementEnum.Es), this.molecule(MoleculeEnum.calciumCarbonate, 8)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(itemEnderPearl, true, 5000, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.calciumCarbonate), this.molecule(MoleculeEnum.calciumCarbonate), this.molecule(MoleculeEnum.calciumCarbonate), this.molecule(MoleculeEnum.calciumCarbonate), this.element(ElementEnum.Es),
            this.molecule(MoleculeEnum.calciumCarbonate), this.molecule(MoleculeEnum.calciumCarbonate), this.molecule(MoleculeEnum.calciumCarbonate), this.molecule(MoleculeEnum.calciumCarbonate)
        }));

        // Blaze Rod
        ItemStack itemBlazeRod = new ItemStack(Item.blazeRod);
        DecomposerRecipe.add(new DecomposerRecipe(itemBlazeRod, new PotionChemical[]
        {
            this.element(ElementEnum.Pu, 6)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(itemBlazeRod, true, 15000, new PotionChemical[]
        {
            this.element(ElementEnum.Pu, 2), null, null, this.element(ElementEnum.Pu, 2), null, null, this.element(ElementEnum.Pu, 2), null, null
        }));

        // Blaze Powder
        ItemStack itemBlazePowder = new ItemStack(Item.blazePowder);
        DecomposerRecipe.add(new DecomposerRecipe(itemBlazePowder, new PotionChemical[]
        {
            this.element(ElementEnum.Pu)
        }));

        // Ghast Tear
        ItemStack itemGhastTear = new ItemStack(Item.ghastTear);
        DecomposerRecipe.add(new DecomposerRecipe(itemGhastTear, new PotionChemical[]
        {
            this.element(ElementEnum.Yb, 4), this.element(ElementEnum.No, 4)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(itemGhastTear, true, 15000, new PotionChemical[]
        {
            this.element(ElementEnum.Yb), this.element(ElementEnum.Yb), this.element(ElementEnum.No), null, this.element(ElementEnum.Yb, 2), this.element(ElementEnum.No, 2), null, this.element(ElementEnum.No), null
        }));

        // Nether Wart
        ItemStack itemNetherStalkSeeds = new ItemStack(Item.netherStalkSeeds);
        DecomposerRecipe.add(new DecomposerRecipeChance(itemNetherStalkSeeds, 0.5F, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.cocainehcl)
        }));

        // Spider Eye
        ItemStack itemSpiderEye = new ItemStack(Item.spiderEye);
        DecomposerRecipe.add(new DecomposerRecipeChance(itemSpiderEye, 0.2F, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.tetrodotoxin)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(itemSpiderEye, true, 2000, new PotionChemical[]
        {
            this.element(ElementEnum.C), null, null, null, this.molecule(MoleculeEnum.tetrodotoxin), null, null, null, this.element(ElementEnum.C)
        }));

        // Fermented Spider Eye
        ItemStack itemFermentedSpiderEye = new ItemStack(Item.fermentedSpiderEye);
        DecomposerRecipe.add(new DecomposerRecipe(itemFermentedSpiderEye, new PotionChemical[]
        {
            this.element(ElementEnum.Po), this.molecule(MoleculeEnum.ethanol)
        }));

        // Magma Cream
        ItemStack itemMagmaCream = new ItemStack(Item.magmaCream);
        DecomposerRecipe.add(new DecomposerRecipe(itemMagmaCream, new PotionChemical[]
        {
            this.element(ElementEnum.Hg), this.element(ElementEnum.Pu), this.molecule(MoleculeEnum.pmma, 3)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(itemMagmaCream, true, 5000, new PotionChemical[]
        {
            null, this.element(ElementEnum.Pu), null, this.molecule(MoleculeEnum.pmma), this.element(ElementEnum.Hg), this.molecule(MoleculeEnum.pmma), null, this.molecule(MoleculeEnum.pmma), null,
        }));

        // Glistering Melon
        ItemStack itemSpeckledMelon = new ItemStack(Item.speckledMelon);
        DecomposerRecipe.add(new DecomposerRecipe(itemSpeckledMelon, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.water, 4), this.molecule(MoleculeEnum.whitePigment), this.element(ElementEnum.Au, 1)
        }));

        // Emerald
        ItemStack itemEmerald = new ItemStack(Item.emerald);
        DecomposerRecipe.add(new DecomposerRecipe(itemEmerald, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.beryl, 2), this.element(ElementEnum.Cr, 2), this.element(ElementEnum.V, 2)
        }));
        SynthesisRecipe.add(new SynthesisRecipe(itemEmerald, true, 5000, new PotionChemical[]
        {
            null, this.element(ElementEnum.Cr), null, this.element(ElementEnum.V), this.molecule(MoleculeEnum.beryl, 2), this.element(ElementEnum.V), null, this.element(ElementEnum.Cr), null
        }));

        // Carrot
        ItemStack itemCarrot = new ItemStack(Item.carrot);
        DecomposerRecipe.add(new DecomposerRecipe(itemCarrot, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.retinol)
        }));

        // Potato
        ItemStack itemPotato = new ItemStack(Item.potato);
        DecomposerRecipe.add(new DecomposerRecipeChance(itemPotato, 0.4F, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.water, 8), this.element(ElementEnum.K, 2), this.molecule(MoleculeEnum.cellulose)
        }));

        // Golden Carrot
        ItemStack itemGoldenCarrot = new ItemStack(Item.goldenCarrot);
        DecomposerRecipe.add(new DecomposerRecipe(itemGoldenCarrot, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.retinol), this.element(ElementEnum.Au, 4)
        }));

        // Nether Star
        ItemStack itemNetherStar = new ItemStack(Item.netherStar);
        DecomposerRecipe.add(new DecomposerRecipe(itemNetherStar, new PotionChemical[]
        {
            this.element(ElementEnum.Cn, 16), elementHydrogen, elementHydrogen, elementHydrogen, elementHelium, elementHelium, elementHelium, elementCarbon, elementCarbon
        }));
        SynthesisRecipe.add(new SynthesisRecipe(itemNetherStar, true, 5000, new PotionChemical[]
        {
            elementHelium, elementHelium, elementHelium, elementCarbon, this.element(ElementEnum.Cn, 16), elementHelium, elementHydrogen, elementHydrogen, elementHydrogen
        }));

        // Nether Quartz
        ItemStack itemNetherQuartz = new ItemStack(Item.netherQuartz);
        DecomposerRecipe.add(new DecomposerRecipe(itemNetherQuartz, new PotionChemical[]
        {
            this.molecule(MoleculeEnum.siliconDioxide, 4), this.molecule(MoleculeEnum.galliumarsenide, 1)
        }));

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
        SynthesisRecipe.add(new SynthesisRecipe(itemRecord13, true, 1000, new PotionChemical[]
        {
            moleculePolyvinylChloride, null, null, null, null, null, null, null, null
        }));
        SynthesisRecipe.add(new SynthesisRecipe(itemRecordCat, true, 1000, new PotionChemical[]
        {
            null, moleculePolyvinylChloride, null, null, null, null, null, null, null
        }));
        SynthesisRecipe.add(new SynthesisRecipe(itemRecordFar, true, 1000, new PotionChemical[]
        {
            null, null, moleculePolyvinylChloride, null, null, null, null, null, null
        }));
        SynthesisRecipe.add(new SynthesisRecipe(itemRecordMall, true, 1000, new PotionChemical[]
        {
            null, null, null, moleculePolyvinylChloride, null, null, null, null, null
        }));
        SynthesisRecipe.add(new SynthesisRecipe(itemRecordMellohi, true, 1000, new PotionChemical[]
        {
            null, null, null, null, moleculePolyvinylChloride, null, null, null, null
        }));
        SynthesisRecipe.add(new SynthesisRecipe(itemRecordStal, true, 1000, new PotionChemical[]
        {
            null, null, null, null, null, moleculePolyvinylChloride, null, null, null
        }));
        SynthesisRecipe.add(new SynthesisRecipe(itemRecordStrad, true, 1000, new PotionChemical[]
        {
            null, null, null, null, null, null, moleculePolyvinylChloride, null, null
        }));
        SynthesisRecipe.add(new SynthesisRecipe(itemRecordWard, true, 1000, new PotionChemical[]
        {
            null, null, null, null, null, null, null, moleculePolyvinylChloride, null
        }));
        SynthesisRecipe.add(new SynthesisRecipe(itemRecordChirp, true, 1000, new PotionChemical[]
        {
            null, null, null, null, null, null, null, null, moleculePolyvinylChloride
        }));
    }

    public void RegisterRecipes()
    {
        this.registerVanillaChemicalRecipes();

        ItemStack blockGlass = new ItemStack(Block.glass);
        ItemStack blockThinGlass = new ItemStack(Block.thinGlass);
        ItemStack blockIron = new ItemStack(Block.blockIron);
        ItemStack itemIngotIron = new ItemStack(Item.ingotIron);
        ItemStack itemRedstone = new ItemStack(Item.redstone);
        ItemStack minechemItemsAtomicManipulator = new ItemStack(MinechemItemsGeneration.atomicManipulator);
        ItemStack moleculePolyvinylChloride = new ItemStack(MinechemItemsGeneration.molecule, 1, MoleculeEnum.polyvinylChloride.ordinal());

        GameRegistry.addRecipe(MinechemItemsGeneration.concaveLens, new Object[]
        {
            "G G", "GGG", "G G", Character.valueOf('G'), blockGlass
        });
        GameRegistry.addRecipe(MinechemItemsGeneration.convexLens, new Object[]
        {
            " G ", "GGG", " G ", Character.valueOf('G'), blockGlass
        });
        GameRegistry.addRecipe(MinechemItemsGeneration.microscopeLens, new Object[]
        {
            "A", "B", "A", Character.valueOf('A'), MinechemItemsGeneration.convexLens, Character.valueOf('B'), MinechemItemsGeneration.concaveLens
        });
        GameRegistry.addRecipe(new ItemStack(MinechemBlocksGeneration.microscope), new Object[]
        {
            " LI", " PI", "III", Character.valueOf('L'), MinechemItemsGeneration.microscopeLens, Character.valueOf('P'), blockThinGlass, Character.valueOf('I'), itemIngotIron
        });
        GameRegistry.addRecipe(new ItemStack(MinechemBlocksGeneration.microscope), new Object[]
        {
            " LI", " PI", "III", Character.valueOf('L'), MinechemItemsGeneration.microscopeLens, Character.valueOf('P'), blockThinGlass, Character.valueOf('I'), itemIngotIron
        });
        GameRegistry.addRecipe(new ItemStack(MinechemItemsGeneration.atomicManipulator), new Object[]
        {
            "PPP", "PIP", "PPP", Character.valueOf('P'), new ItemStack(Block.pistonBase), Character.valueOf('I'), blockIron
        });
        GameRegistry.addRecipe(new ItemStack(MinechemBlocksGeneration.decomposer), new Object[]
        {
            "III", "IAI", "IRI", Character.valueOf('A'), minechemItemsAtomicManipulator, Character.valueOf('I'), itemIngotIron, Character.valueOf('R'), itemRedstone
        });
        GameRegistry.addRecipe(new ItemStack(MinechemBlocksGeneration.synthesis), new Object[]
        {
            "IRI", "IAI", "IDI", Character.valueOf('A'), minechemItemsAtomicManipulator, Character.valueOf('I'), itemIngotIron, Character.valueOf('R'), itemRedstone, Character.valueOf('D'), new ItemStack(Item.diamond)
        });
        GameRegistry.addRecipe(new ItemStack(MinechemBlocksGeneration.fusion, 16, 0), new Object[]
        {
            "ILI", "ILI", "ILI", Character.valueOf('I'), itemIngotIron, Character.valueOf('L'), ElementItem.createStackOf(ElementEnum.Pb, 1)
        });
        GameRegistry.addRecipe(new ItemStack(MinechemBlocksGeneration.fusion, 16, 1), new Object[]
        {
            "IWI", "IBI", "IWI", Character.valueOf('I'), itemIngotIron, Character.valueOf('W'), ElementItem.createStackOf(ElementEnum.W, 1), Character.valueOf('B'), ElementItem.createStackOf(ElementEnum.Be, 1)
        });
        GameRegistry.addRecipe(MinechemItemsGeneration.projectorLens, new Object[]
        {
            "ABA", Character.valueOf('A'), MinechemItemsGeneration.concaveLens, Character.valueOf('B'), MinechemItemsGeneration.convexLens
        });
        GameRegistry.addRecipe(new ItemStack(MinechemBlocksGeneration.blueprintProjector), new Object[]
        {
            " I ", "GPL", " I ", Character.valueOf('I'), itemIngotIron, Character.valueOf('P'), blockThinGlass, Character.valueOf('L'), MinechemItemsGeneration.projectorLens, Character.valueOf('G'), new ItemStack(Block.redstoneLampIdle)
        });
        GameRegistry.addRecipe(new ItemStack(MinechemBlocksGeneration.leadedChest), new Object[]
        {
            "LLL", "LCL", "LLL", Character.valueOf('L'), new ItemStack(MinechemItemsGeneration.element, 1, ElementEnum.Pb.ordinal()), Character.valueOf('C'), new ItemStack(Block.chest)
        });
        GameRegistry.addShapelessRecipe(new ItemStack(MinechemBlocksGeneration.leadedChest), new Object[]
        {
            new ItemStack(MinechemBlocksGeneration.chemicalStorage)
        });
        GameRegistry.addShapelessRecipe(new ItemStack(MinechemItemsGeneration.journal), new Object[]
        {
            new ItemStack(Item.book), new ItemStack(Block.glass)
        });
        // Fusion
        GameRegistry.addShapelessRecipe(ItemBlueprint.createItemStackFromBlueprint(MinechemBlueprint.fusion), new Object[]
        {
            new ItemStack(Item.paper), new ItemStack(Block.blockDiamond)
        });
        // Fission
        GameRegistry.addShapelessRecipe(ItemBlueprint.createItemStackFromBlueprint(MinechemBlueprint.fission), new Object[]
        {
            new ItemStack(Item.paper), new ItemStack(Item.diamond)
        });

        GameRegistry.addRecipe(new ChemistJournalRecipeCloning());

        //
        this.addDecomposerRecipesFromMolecules();
        this.addSynthesisRecipesFromMolecules();
        this.addUnusedSynthesisRecipes();
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

    private void addDecomposerRecipesFromMolecules()
    {
        MoleculeEnum[] var1 = MoleculeEnum.molecules;
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3)
        {
            MoleculeEnum var4 = var1[var3];
            ArrayList var5 = var4.components();
            PotionChemical[] var6 = (PotionChemical[]) var5.toArray(new PotionChemical[var5.size()]);
            ItemStack var7 = new ItemStack(MinechemItemsGeneration.molecule, 1, var4.id());
            DecomposerRecipe.add(new DecomposerRecipe(var7, var6));
        }

    }

    private void addSynthesisRecipesFromMolecules()
    {
        MoleculeEnum[] var1 = MoleculeEnum.molecules;
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3)
        {
            MoleculeEnum var4 = var1[var3];
            ArrayList var5 = var4.components();
            ItemStack var6 = new ItemStack(MinechemItemsGeneration.molecule, 1, var4.id());
            SynthesisRecipe.add(new SynthesisRecipe(var6, false, 50, var5));
        }

    }

    private void addUnusedSynthesisRecipes()
    {
        Iterator var1 = DecomposerRecipe.recipes.iterator();

        while (var1.hasNext())
        {
            DecomposerRecipe var2 = (DecomposerRecipe) var1.next();
            if (var2.getInput().getItemDamage() != -1)
            {
                boolean var3 = false;
                Iterator var4 = SynthesisRecipe.recipes.iterator();

                while (true)
                {
                    if (var4.hasNext())
                    {
                        SynthesisRecipe var5 = (SynthesisRecipe) var4.next();
                        if (!Compare.stacksAreSameKind(var5.getOutput(), var2.getInput()))
                        {
                            continue;
                        }

                        var3 = true;
                    }

                    if (!var3)
                    {
                        ArrayList var6 = var2.getOutputRaw();
                        if (var6 != null)
                        {
                            if (shouldCreateSynthesis(var2.getInput().itemID))
                            {
                                SynthesisRecipe.add(new SynthesisRecipe(var2.getInput(), false, 100, var6));
                            }
                        }
                    }
                    break;
                }
            }
        }
    }

    public boolean shouldCreateSynthesis(int id)
    {
        switch (id)
        {
        // Vanilla ores should not be synthesized
            // To prevent exploits
            case 14:
            case 15:
            case 16:
            case 56:
            case 73:
            case 153:
                return false;
        }
        return true;
    }

    private ItemStack createPoisonedItemStack(Item var1, int var2, MoleculeEnum var3)
    {
        ItemStack var4 = new ItemStack(MinechemItemsGeneration.molecule, 1, var3.id());
        ItemStack var5 = new ItemStack(var1, 1, var2);
        ItemStack var6 = new ItemStack(var1, 1, var2);
        NBTTagCompound var7 = new NBTTagCompound();
        var7.setBoolean("minechem.isPoisoned", true);
        var7.setInteger("minechem.effectType", var3.id());
        var6.setTagCompound(var7);
        GameRegistry.addShapelessRecipe(var6, new Object[]
        {
            var4, var5
        });
        return var6;
    }

    private void registerPoisonRecipes(MoleculeEnum molecule)
    {
        for (Item i : Item.itemsList)
        {
            // Should allow for lacing of BOP and AquaCulture foodstuffs.
            if (i != null && i instanceof ItemFood)
            {
                this.createPoisonedItemStack(i, 0, molecule);
            }
        }
    }

    private ArrayList<OreDictionaryHandler> oreDictionaryHandlers;

    @ForgeSubscribe
    public void oreEvent(OreDictionary.OreRegisterEvent var1)
    {
        if (var1.Name.contains("gemApatite"))
        {
            DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new PotionChemical[]
            {
                this.element(ElementEnum.Ca, 5), this.molecule(MoleculeEnum.phosphate, 4), this.element(ElementEnum.Cl)
            }));
            SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 1000, new PotionChemical[]
            {
                this.element(ElementEnum.Ca, 5), this.molecule(MoleculeEnum.phosphate, 4), this.element(ElementEnum.Cl)
            }));
        } else if (var1.Name.contains("ingotBronze"))
        {
            if (Loader.isModLoaded("Mekanism"))
            {
                DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new PotionChemical[]
                {
                    this.element(ElementEnum.Cu, 16), this.element(ElementEnum.Sn, 2)
                }));
                SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 1000, new PotionChemical[]
                {
                    this.element(ElementEnum.Cu, 16), this.element(ElementEnum.Sn, 2)
                }));
            } else
            {
                DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new PotionChemical[]
                {
                    this.element(ElementEnum.Cu, 24), this.element(ElementEnum.Sn, 8)
                }));
                SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 1000, new PotionChemical[]
                {
                    this.element(ElementEnum.Cu, 24), this.element(ElementEnum.Sn, 8)
                }));
            }
        } else if (var1.Name.contains("plateSilicon"))
        {
            DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new PotionChemical[]
            {
                this.element(ElementEnum.Si, 2)
            }));
            SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 1000, new PotionChemical[]
            {
                this.element(ElementEnum.Si, 2)
            }));
        } else if (var1.Name.contains("xychoriumBlue"))
        {
            DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new PotionChemical[]
            {
                this.element(ElementEnum.Zr, 2), this.element(ElementEnum.Cu, 1)
            }));
            SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 300, new PotionChemical[]
            {
                this.element(ElementEnum.Zr, 2), this.element(ElementEnum.Cu, 1)
            }));
        } else if (var1.Name.contains("xychoriumRed"))
        {
            DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new PotionChemical[]
            {
                this.element(ElementEnum.Zr, 2), this.element(ElementEnum.Fe, 1)
            }));
            SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 300, new PotionChemical[]
            {
                this.element(ElementEnum.Zr, 2), this.element(ElementEnum.Fe, 1)
            }));
        } else if (var1.Name.contains("xychoriumGreen"))
        {
            DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new PotionChemical[]
            {
                this.element(ElementEnum.Zr, 2), this.element(ElementEnum.V, 1)
            }));
            SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 300, new PotionChemical[]
            {
                this.element(ElementEnum.Zr, 2), this.element(ElementEnum.V, 1)
            }));
        } else if (var1.Name.contains("xychoriumDark"))
        {
            DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new PotionChemical[]
            {
                this.element(ElementEnum.Zr, 2), this.element(ElementEnum.Si, 1)
            }));
            SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 300, new PotionChemical[]
            {
                this.element(ElementEnum.Zr, 2), this.element(ElementEnum.Si, 1)
            }));
        } else if (var1.Name.contains("xychoriumLight"))
        {
            DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new PotionChemical[]
            {
                this.element(ElementEnum.Zr, 2), this.element(ElementEnum.Ti, 1)
            }));
            SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 300, new PotionChemical[]
            {
                this.element(ElementEnum.Zr, 2), this.element(ElementEnum.Ti, 1)
            }));
        } else if (var1.Name.contains("gemPeridot"))
        {
            DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new PotionChemical[]
            {
                this.molecule(MoleculeEnum.olivine)
            }));
            SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 1000, new PotionChemical[]
            {
                this.molecule(MoleculeEnum.olivine)
            }));
        } else if (var1.Name.contains("cropMaloberry"))
        {
            DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new PotionChemical[]
            {
                this.molecule(MoleculeEnum.xylitol), this.molecule(MoleculeEnum.sucrose)
            }));
            SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 1000, new PotionChemical[]
            {
                this.molecule(MoleculeEnum.xylitol), this.molecule(MoleculeEnum.sucrose)
            }));
        } else if (var1.Name.contains("cropDuskberry"))
        {
            DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new PotionChemical[]
            {
                this.molecule(MoleculeEnum.psilocybin), this.element(ElementEnum.S, 2)
            }));
            SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 1000, new PotionChemical[]
            {
                this.molecule(MoleculeEnum.psilocybin), this.element(ElementEnum.S, 2)
            }));
        } else if (var1.Name.contains("cropSkyberry"))
        {
            DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new PotionChemical[]
            {
                this.molecule(MoleculeEnum.theobromine), this.element(ElementEnum.S, 2)
            }));
            SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 1000, new PotionChemical[]
            {
                this.molecule(MoleculeEnum.theobromine), this.element(ElementEnum.S, 2)
            }));
        } else if (var1.Name.contains("cropBlightberry"))
        {
            DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new PotionChemical[]
            {
                this.molecule(MoleculeEnum.asprin), this.element(ElementEnum.S, 2)
            }));
            SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 1000, new PotionChemical[]
            {
                this.molecule(MoleculeEnum.asprin), this.element(ElementEnum.S, 2)
            }));
        } else if (var1.Name.contains("cropBlueberry"))
        {
            DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new PotionChemical[]
            {
                this.molecule(MoleculeEnum.blueorgodye), this.molecule(MoleculeEnum.sucrose, 2)
            }));
            SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 1000, new PotionChemical[]
            {
                this.molecule(MoleculeEnum.blueorgodye), this.molecule(MoleculeEnum.sucrose, 2)
            }));
        } else if (var1.Name.contains("cropRaspberry"))
        {
            DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new PotionChemical[]
            {
                this.molecule(MoleculeEnum.redorgodye), this.molecule(MoleculeEnum.sucrose, 2)
            }));
            SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 1000, new PotionChemical[]
            {
                this.molecule(MoleculeEnum.redorgodye), this.molecule(MoleculeEnum.sucrose, 2)
            }));
        } else if (var1.Name.contains("cropBlackberry"))
        {
            DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new PotionChemical[]
            {
                this.molecule(MoleculeEnum.purpleorgodye), this.molecule(MoleculeEnum.sucrose, 2)
            }));
            SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 1000, new PotionChemical[]
            {
                this.molecule(MoleculeEnum.purpleorgodye), this.molecule(MoleculeEnum.sucrose, 2)
            }));
        } else
        {
            for (OreDictionaryHandler handler : this.oreDictionaryHandlers)
            {
                if (handler.canHandle(var1))
                {
                    handler.handle(var1);
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
        if (Loader.isModLoaded("AppliedEnergistics"))
        {
            this.oreDictionaryHandlers.add(new OreDictionaryAppliedEnergisticsHandler());
        }
        this.oreDictionaryHandlers.add(new OreDictionaryDefaultHandler());
    }
} // EOF
