package ljdp.minechem.common.recipe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import ljdp.minechem.common.items.ItemElement;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.oredict.OreDictionary;
import biomesoplenty.api.BlockReferences;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.Loader;

public class MinechemRecipes {

    private static final MinechemRecipes instance = new MinechemRecipes();
    public ArrayList unbondingRecipes = new ArrayList();
    public ArrayList synthesisRecipes = new ArrayList();

    private SynthesisRecipe recipeIron;
    private SynthesisRecipe recipeGold;
    private SynthesisRecipe recipeCoalChunk;

    public static MinechemRecipes getInstance() {
        return instance;
    }
    public void RegisterRecipes() {
        ItemStack var1 = new ItemStack(Block.stone);
        new ItemStack(Block.cobblestone);
        ItemStack var3 = new ItemStack(Block.dirt);
        ItemStack var4 = new ItemStack(Block.sand);
        ItemStack var5 = new ItemStack(Block.gravel);
        ItemStack var6 = new ItemStack(Block.glass);
        ItemStack var7 = new ItemStack(Block.thinGlass);
        ItemStack oreIron = new ItemStack(Block.oreIron);
        ItemStack oreGold = new ItemStack(Block.oreGold);
        ItemStack var10 = new ItemStack(Block.oreDiamond);
        ItemStack var11 = new ItemStack(Block.oreEmerald);
        ItemStack oreCoal = new ItemStack(Block.oreCoal);
        ItemStack var13 = new ItemStack(Block.oreRedstone);
        ItemStack var14 = new ItemStack(Block.oreLapis);
        ItemStack ingotIron = new ItemStack(Item.ingotIron);
        ItemStack blockIron = new ItemStack(Block.blockIron);
        ItemStack var17 = new ItemStack(MinechemItems.atomicManipulator);
        ItemStack var18 = new ItemStack(Item.redstone);
        ItemStack var19 = new ItemStack(MinechemItems.testTube, 16);
        GameRegistry.addRecipe(var19, new Object[] { " G ", " G ", " G ", Character.valueOf('G'), var6 });
        GameRegistry.addRecipe(MinechemItems.concaveLens, new Object[] { "G G", "GGG", "G G", Character.valueOf('G'), var6 });
        GameRegistry.addRecipe(MinechemItems.convexLens, new Object[] { " G ", "GGG", " G ", Character.valueOf('G'), var6 });
        GameRegistry.addRecipe(MinechemItems.microscopeLens, new Object[] { "A", "B", "A", Character.valueOf('A'), MinechemItems.convexLens, Character.valueOf('B'), MinechemItems.concaveLens });
        GameRegistry.addRecipe(new ItemStack(MinechemBlocks.microscope), new Object[] { " LI", " PI", "III", Character.valueOf('L'), MinechemItems.microscopeLens, Character.valueOf('P'), var7, Character.valueOf('I'), ingotIron });
        GameRegistry.addRecipe(new ItemStack(MinechemItems.atomicManipulator), new Object[] { "PPP", "PIP", "PPP", Character.valueOf('P'), new ItemStack(Block.pistonBase), Character.valueOf('I'), blockIron });
        GameRegistry.addRecipe(new ItemStack(MinechemBlocks.decomposer), new Object[] { "III", "IAI", "IRI", Character.valueOf('A'), var17, Character.valueOf('I'), ingotIron, Character.valueOf('R'), var18 });
        GameRegistry.addRecipe(new ItemStack(MinechemBlocks.synthesis), new Object[] { "IRI", "IAI", "IDI", Character.valueOf('A'), var17, Character.valueOf('I'), ingotIron, Character.valueOf('R'), var18, Character.valueOf('D'), new ItemStack(Item.diamond) });
        GameRegistry.addRecipe(new ItemStack(MinechemBlocks.fusion, 16, 0), new Object[] { "ILI", "ILI", "ILI", Character.valueOf('I'), ingotIron, Character.valueOf('L'), ItemElement.createStackOf(EnumElement.Pb, 1) });
        GameRegistry.addRecipe(new ItemStack(MinechemBlocks.fusion, 16, 1), new Object[] { "IWI", "IBI", "IWI", Character.valueOf('I'), ingotIron, Character.valueOf('W'), ItemElement.createStackOf(EnumElement.W, 1), Character.valueOf('B'), ItemElement.createStackOf(EnumElement.Be, 1) });
        GameRegistry.addRecipe(MinechemItems.projectorLens, new Object[] { "ABA", Character.valueOf('A'), MinechemItems.concaveLens, Character.valueOf('B'), MinechemItems.convexLens });
        GameRegistry.addRecipe(new ItemStack(MinechemBlocks.blueprintProjector), new Object[] { " I ", "GPL", " I ", Character.valueOf('I'), ingotIron, Character.valueOf('P'), var7, Character.valueOf('L'), MinechemItems.projectorLens, Character.valueOf('G'), new ItemStack(Block.redstoneLampIdle) });
        ItemStack var20 = new ItemStack(MinechemItems.molecule, 1, EnumMolecule.polyvinylChloride.ordinal());
        GameRegistry.addRecipe(new ItemStack(MinechemItems.hazmatFeet), new Object[] { "   ", "P P", "P P", Character.valueOf('P'), var20 });
        GameRegistry.addRecipe(new ItemStack(MinechemItems.hazmatLegs), new Object[] { "PPP", "P P", "P P", Character.valueOf('P'), var20 });
        GameRegistry.addRecipe(new ItemStack(MinechemItems.hazmatTorso), new Object[] { " P ", "PPP", "PPP", Character.valueOf('P'), var20 });
        GameRegistry.addRecipe(new ItemStack(MinechemItems.hazmatHead), new Object[] { "PPP", "P P", "   ", Character.valueOf('P'), var20 });
        GameRegistry.addRecipe(new ItemStack(MinechemBlocks.chemicalStorage), new Object[] { "LLL", "LCL", "LLL", Character.valueOf('L'), new ItemStack(MinechemItems.element, 1, EnumElement.Pb.ordinal()), Character.valueOf('C'), new ItemStack(Block.chest) });
        GameRegistry.addRecipe(new ItemStack(MinechemItems.IAintAvinit), new Object[] { "ZZZ", "ZSZ", " S ", Character.valueOf('Z'), new ItemStack(Item.ingotIron), Character.valueOf('S'), new ItemStack(Item.stick) });
        GameRegistry.addShapelessRecipe(new ItemStack(MinechemItems.journal), new Object[] { new ItemStack(Item.book), new ItemStack(MinechemItems.testTube) });
        for (EnumElement element : EnumElement.values()) {
            GameRegistry.addShapelessRecipe(new ItemStack(MinechemItems.testTube), new Object[] { new ItemStack(MinechemItems.element, element.ordinal()) });
        }
        
        GameRegistry.addRecipe(new RecipeJournalCloning());
        Element var21 = this.element(EnumElement.C, 64);
        // DecomposerRecipe.add(new DecomposerRecipe(var8, new
        // Chemical[]{this.element(EnumElement.Fe, 4)}));
        DecomposerRecipe.add(new DecomposerRecipe(oreIron, new Chemical[] { this.element(EnumElement.Fe, 32) }));
        // DecomposerRecipe.add(new DecomposerRecipe(var9, new
        // Chemical[]{this.element(EnumElement.Au, 4)}));
        DecomposerRecipe.add(new DecomposerRecipe(oreGold, new Chemical[] { this.element(EnumElement.Au, 32) }));
        DecomposerRecipe.add(new DecomposerRecipe(var10, new Chemical[] { this.molecule(EnumMolecule.fullrene, 6) }));
        DecomposerRecipe.add(new DecomposerRecipe(oreCoal, new Chemical[] { this.element(EnumElement.C, 32) }));
        DecomposerRecipe.add(new DecomposerRecipe(var11, new Chemical[] { this.molecule(EnumMolecule.beryl, 4), this.element(EnumElement.Cr, 4), this.element(EnumElement.V, 4) }));
        DecomposerRecipe.add(new DecomposerRecipe(var14, new Chemical[] { this.molecule(EnumMolecule.lazurite, 4), this.molecule(EnumMolecule.sodalite), this.molecule(EnumMolecule.noselite), this.molecule(EnumMolecule.calcite), this.molecule(EnumMolecule.pyrite) }));
        ItemStack ingotGold = new ItemStack(Item.ingotGold);
        ItemStack var23 = new ItemStack(Item.diamond);
        ItemStack var24 = new ItemStack(Item.emerald);
        ItemStack chunkCoal = new ItemStack(Item.coal);

        ItemStack fusionblue = new ItemStack(MinechemItems.blueprint, 1, MinechemBlueprint.fusion.id);
        ItemStack fusionBlock1 = new ItemStack(MinechemBlocks.fusion, 0);
        ItemStack fusionBlock2 = new ItemStack(MinechemBlocks.fusion, 1);
        // DecomposerRecipe.add(new DecomposerRecipe(var15, new
        // Chemical[]{this.element(EnumElement.Fe, 2)})); 
        DecomposerRecipe.add(new DecomposerRecipe(ingotIron, new Chemical[] { this.element(EnumElement.Fe, 16) }));
        // DecomposerRecipe.add(new DecomposerRecipe(var22, new
        // Chemical[]{this.element(EnumElement.Au, 2)}));
        DecomposerRecipe.add(new DecomposerRecipe(ingotGold, new Chemical[] { this.element(EnumElement.Au, 16) }));
        DecomposerRecipe.add(new DecomposerRecipe(var23, new Chemical[] { this.molecule(EnumMolecule.fullrene, 4) }));
        DecomposerRecipe.add(new DecomposerRecipe(var24, new Chemical[] { this.molecule(EnumMolecule.beryl, 2), this.element(EnumElement.Cr, 2), this.element(EnumElement.V, 2) }));
        // DecomposerRecipe.add(new DecomposerRecipe(var25, new
        // Chemical[]{this.element(EnumElement.C, 8)}));
        DecomposerRecipe.add(new DecomposerRecipe(chunkCoal, new Chemical[] { this.element(EnumElement.C, 16) }));
        // SynthesisRecipe.add(new SynthesisRecipe(var15, false, 1000, new
        // Chemical[]{this.element(EnumElement.Fe, 2)}));
        // SynthesisRecipe.add(new SynthesisRecipe(var22, false, 1000, new
        // Chemical[]{this.element(EnumElement.Au, 2)}));

        this.recipeIron = new SynthesisRecipe(ingotIron, false, 1000, new Chemical[] { this.element(EnumElement.Fe, 16) });
        this.recipeGold = new SynthesisRecipe(ingotGold, false, 1000, new Chemical[] { this.element(EnumElement.Au, 16) });
        SynthesisRecipe.add(recipeIron);
        SynthesisRecipe.add(recipeGold);
       
        SynthesisRecipe.add(new SynthesisRecipe(var23, true, '\uea60', new Chemical[] { null, this.molecule(EnumMolecule.fullrene), null, this.molecule(EnumMolecule.fullrene), null, this.molecule(EnumMolecule.fullrene), null, this.molecule(EnumMolecule.fullrene), null }));
        SynthesisRecipe.add(new SynthesisRecipe(var24, true, 80000, new Chemical[] { null, this.element(EnumElement.Cr), null, this.element(EnumElement.V), this.molecule(EnumMolecule.beryl, 2), this.element(EnumElement.V), null, this.element(EnumElement.Cr), null }));
        // DecomposerRecipe.add(new DecomposerRecipe(new
        // ItemStack(Block.blockSteel), new
        // Chemical[]{this.element(EnumElement.Fe, 18)}));
        DecomposerRecipe.add(new DecomposerRecipe(new ItemStack(Block.blockIron), new Chemical[] { this.element(EnumElement.Fe, 144) }));
        // DecomposerRecipe.add(new DecomposerRecipe(new
        // ItemStack(Block.blockGold), new
        // Chemical[]{this.element(EnumElement.Au, 18)}));
        DecomposerRecipe.add(new DecomposerRecipe(new ItemStack(Block.blockGold), new Chemical[] { this.element(EnumElement.Au, 144) }));
        DecomposerRecipe.add(new DecomposerRecipe(new ItemStack(Block.blockDiamond), new Chemical[] { this.molecule(EnumMolecule.fullrene, 36) }));
        DecomposerRecipe.add(new DecomposerRecipe(new ItemStack(Block.blockEmerald), new Chemical[] { this.molecule(EnumMolecule.beryl, 18), this.element(EnumElement.Cr, 18), this.element(EnumElement.V, 18) }));
        // SynthesisRecipe.add(new SynthesisRecipe(new
        // ItemStack(Block.blockSteel), true, 5000, new
        // Chemical[]{this.element(EnumElement.Fe, 2),
        // this.element(EnumElement.Fe, 2), this.element(EnumElement.Fe, 2),
        // this.element(EnumElement.Fe, 2), this.element(EnumElement.Fe, 2),
        // this.element(EnumElement.Fe, 2), this.element(EnumElement.Fe, 2),
        // this.element(EnumElement.Fe, 2), this.element(EnumElement.Fe, 2)}));
        // SynthesisRecipe.add(new SynthesisRecipe(new
        // ItemStack(Block.blockGold), true, 5000, new
        // Chemical[]{this.element(EnumElement.Au, 2),
        // this.element(EnumElement.Au, 2), this.element(EnumElement.Au, 2),
        // this.element(EnumElement.Au, 2), this.element(EnumElement.Au, 2),
        // this.element(EnumElement.Au, 2), this.element(EnumElement.Au, 2),
        // this.element(EnumElement.Au, 2), this.element(EnumElement.Au, 2)}));
        SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Block.blockDiamond), true, 120000, new Chemical[] { this.molecule(EnumMolecule.fullrene, 4), this.molecule(EnumMolecule.fullrene, 4), this.molecule(EnumMolecule.fullrene, 4), this.molecule(EnumMolecule.fullrene, 4), this.molecule(EnumMolecule.fullrene, 4), this.molecule(EnumMolecule.fullrene, 4), this.molecule(EnumMolecule.fullrene, 4), this.molecule(EnumMolecule.fullrene, 4), this.molecule(EnumMolecule.fullrene, 4) }));
        SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Block.blockEmerald), true, 150000, new Chemical[] { this.element(EnumElement.Cr, 3), this.element(EnumElement.Cr, 3), this.element(EnumElement.Cr, 3), this.element(EnumElement.V, 9), this.molecule(EnumMolecule.beryl, 18), this.element(EnumElement.V, 9), this.element(EnumElement.Cr, 3), this.element(EnumElement.Cr, 3), this.element(EnumElement.Cr, 3) }));
        ItemStack var26 = new ItemStack(Block.sandStone);
        ItemStack var27 = new ItemStack(Item.flint);
        DecomposerRecipe.add(new DecomposerRecipe(var26, new Chemical[] { this.molecule(EnumMolecule.siliconDioxide, 16) }));
        DecomposerRecipe.add(new DecomposerRecipe(var4, new Chemical[] { this.molecule(EnumMolecule.siliconDioxide, 16) }));
        DecomposerRecipe.add(new DecomposerRecipe(var6, new Chemical[] { this.molecule(EnumMolecule.siliconDioxide, 16) }));
        DecomposerRecipe.add(new DecomposerRecipe(var7, new Chemical[] { this.molecule(EnumMolecule.siliconDioxide, 1) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(var5, 0.35F, new Chemical[] { this.molecule(EnumMolecule.siliconDioxide) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(var27, 0.5F, new Chemical[] { this.molecule(EnumMolecule.siliconDioxide) }));
        Molecule var28 = this.molecule(EnumMolecule.siliconDioxide, 4);
        Molecule var29 = this.molecule(EnumMolecule.siliconDioxide, 4);
        SynthesisRecipe.add(new SynthesisRecipe(var6, true, 500, new Chemical[] { var28, null, var28, null, null, null, var28, null, var28 }));
        SynthesisRecipe.add(new SynthesisRecipe(var4, true, 200, new Chemical[] { var28, var28, var28, var28 }));
        SynthesisRecipe.add(new SynthesisRecipe(var27, true, 100, new Chemical[] { null, var29, null, var29, var29, var29, null, null, null }));
        SynthesisRecipe.add(new SynthesisRecipe(var7, true, 50, new Chemical[] { null, null, null, this.molecule(EnumMolecule.siliconDioxide), null, null, null, null, null }));
        SynthesisRecipe.add(new SynthesisRecipe(var5, true, 30, new Chemical[] { null, null, null, null, null, null, null, null, this.molecule(EnumMolecule.siliconDioxide) }));
        ItemStack var30 = new ItemStack(Item.feather);
        DecomposerRecipe.add(new DecomposerRecipe(var30, new Chemical[] { this.molecule(EnumMolecule.water, 8), this.element(EnumElement.N, 6) }));
        SynthesisRecipe.add(new SynthesisRecipe(var30, true, 800, new Chemical[] { this.element(EnumElement.N), this.molecule(EnumMolecule.water, 2), this.element(EnumElement.N), this.element(EnumElement.N), this.molecule(EnumMolecule.water, 1), this.element(EnumElement.N), this.element(EnumElement.N), this.molecule(EnumMolecule.water, 5), this.element(EnumElement.N) }));
        ItemStack var31 = new ItemStack(Item.arrow);
        ItemStack var32 = new ItemStack(Item.paper);
        ItemStack var33 = new ItemStack(Item.leather);
        ItemStack var34 = new ItemStack(Item.snowball);
        ItemStack var35 = new ItemStack(Item.brick);
        ItemStack var36 = new ItemStack(Item.clay);
        ItemStack var37 = new ItemStack(Block.mycelium);
        ItemStack var38 = new ItemStack(Block.sapling, 1, -1);
        DecomposerRecipe.add(new DecomposerRecipe(var31, new Chemical[] { this.element(EnumElement.Si), this.element(EnumElement.O, 2), this.element(EnumElement.N, 6) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(var36, 0.3F, new Chemical[] { this.molecule(EnumMolecule.kaolinite) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(var35, 0.5F, new Chemical[] { this.molecule(EnumMolecule.kaolinite) }));
        DecomposerRecipe.add(new DecomposerRecipe(var34, new Chemical[] { this.molecule(EnumMolecule.water) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(var37, 0.09F, new Chemical[] { this.molecule(EnumMolecule.fingolimod) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(var33, 0.5F, new Chemical[] { this.molecule(EnumMolecule.arginine), this.molecule(EnumMolecule.glycine), this.molecule(EnumMolecule.keratin) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(var38, 0.25F, new Chemical[] { this.molecule(EnumMolecule.cellulose) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(new ItemStack(Block.sapling, 1, 1), 0.25F, new Chemical[] { this.molecule(EnumMolecule.cellulose) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(new ItemStack(Block.sapling, 1, 2), 0.25F, new Chemical[] { this.molecule(EnumMolecule.cellulose) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(new ItemStack(Block.sapling, 1, 3), 0.25F, new Chemical[] { this.molecule(EnumMolecule.cellulose) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(var32, 0.25F, new Chemical[] { this.molecule(EnumMolecule.cellulose) }));
        SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Item.clay, 12), false, 100, new Chemical[] { this.molecule(EnumMolecule.kaolinite) }));
        SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Item.brick, 8), true, 400, new Chemical[] { this.molecule(EnumMolecule.kaolinite), this.molecule(EnumMolecule.kaolinite), null, this.molecule(EnumMolecule.kaolinite), this.molecule(EnumMolecule.kaolinite), null }));
        SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Item.snowball, 5), true, 20, new Chemical[] { this.molecule(EnumMolecule.water), null, this.molecule(EnumMolecule.water), null, this.molecule(EnumMolecule.water), null, this.molecule(EnumMolecule.water), null, this.molecule(EnumMolecule.water) }));
        SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Block.mycelium, 16), false, 300, new Chemical[] { this.molecule(EnumMolecule.fingolimod) }));
        SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Item.leather, 5), true, 700, new Chemical[] { this.molecule(EnumMolecule.arginine), null, null, null, this.molecule(EnumMolecule.keratin), null, null, null, this.molecule(EnumMolecule.glycine) }));
        SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Block.sapling, 1, 0), true, 20, new Chemical[] { null, null, null, null, null, null, null, null, this.molecule(EnumMolecule.cellulose) }));
        SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Block.sapling, 1, 1), true, 20, new Chemical[] { null, null, null, null, null, null, null, this.molecule(EnumMolecule.cellulose), null }));
        SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Block.sapling, 1, 2), true, 20, new Chemical[] { null, null, null, null, null, null, this.molecule(EnumMolecule.cellulose), null, null }));
        SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Block.sapling, 1, 3), true, 20, new Chemical[] { null, null, null, null, null, this.molecule(EnumMolecule.cellulose), null, null, null }));
        SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Item.paper, 16), true, 150, new Chemical[] { null, this.molecule(EnumMolecule.cellulose), null, null, this.molecule(EnumMolecule.cellulose), null, null, this.molecule(EnumMolecule.cellulose), null }));
        ItemStack var39 = new ItemStack(Item.slimeBall);
        ItemStack var40 = new ItemStack(Item.blazeRod);
        ItemStack var41 = new ItemStack(Item.blazePowder);
        ItemStack var42 = new ItemStack(Item.magmaCream);
        ItemStack var43 = new ItemStack(Item.ghastTear);
        ItemStack var44 = new ItemStack(Item.netherStar);
        ItemStack var45 = new ItemStack(Item.spiderEye);
        ItemStack var46 = new ItemStack(Item.fermentedSpiderEye);
        ItemStack var47 = new ItemStack(Item.netherStalkSeeds);
        ItemStack var48 = new ItemStack(Block.glowStone);
        ItemStack var49 = new ItemStack(Item.lightStoneDust);
        ItemStack var50 = new ItemStack(Item.potion, 1, 0);
        ItemStack var51 = new ItemStack(Item.bucketWater);
        DecomposerRecipe.add(new DecomposerRecipeSelect(var39, 0.9F, new DecomposerRecipe[] { new DecomposerRecipe(new Chemical[] { this.molecule(EnumMolecule.polycyanoacrylate) }), new DecomposerRecipe(new Chemical[] { this.element(EnumElement.Hg) }), new DecomposerRecipe(new Chemical[] { this.molecule(EnumMolecule.water, 10) }) }));
        DecomposerRecipe.add(new DecomposerRecipe(var40, new Chemical[] { this.element(EnumElement.Pu, 3) }));
        DecomposerRecipe.add(new DecomposerRecipe(var41, new Chemical[] { this.element(EnumElement.Pu) }));
        DecomposerRecipe.add(new DecomposerRecipe(var42, new Chemical[] { this.element(EnumElement.Hg), this.element(EnumElement.Pu), this.molecule(EnumMolecule.polycyanoacrylate, 3) }));
        DecomposerRecipe.add(new DecomposerRecipe(var43, new Chemical[] { this.element(EnumElement.Yb, 4), this.element(EnumElement.No, 4) }));
        Element var52 = this.element(EnumElement.H, 64);
        Element var53 = this.element(EnumElement.He, 64);
        DecomposerRecipe.add(new DecomposerRecipe(var44, new Chemical[] { this.element(EnumElement.Cn, 16), var52, var52, var52, var53, var53, var53, var21, var21 }));
        DecomposerRecipe.add(new DecomposerRecipeChance(var45, 0.2F, new Chemical[] { this.molecule(EnumMolecule.ttx) }));
        DecomposerRecipe.add(new DecomposerRecipe(var46, new Chemical[] { this.element(EnumElement.Po), this.molecule(EnumMolecule.ethanol) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(var47, 0.5F, new Chemical[] { this.molecule(EnumMolecule.amphetamine) }));
        DecomposerRecipe.add(new DecomposerRecipe(var48, new Chemical[] { this.element(EnumElement.P, 4) }));
        DecomposerRecipe.add(new DecomposerRecipe(var49, new Chemical[] { this.element(EnumElement.P) }));
        DecomposerRecipe.add(new DecomposerRecipe(var50, new Chemical[] { this.molecule(EnumMolecule.water, 8) }));
        DecomposerRecipe.add(new DecomposerRecipe(var51, new Chemical[] { this.molecule(EnumMolecule.water, 16) }));
        SynthesisRecipe.add(new SynthesisRecipe(var40, true, 15000, new Chemical[] { this.element(EnumElement.Pu), null, null, this.element(EnumElement.Pu), null, null, this.element(EnumElement.Pu), null, null }));
        SynthesisRecipe.add(new SynthesisRecipe(var42, true, 5000, new Chemical[] { null, this.element(EnumElement.Pu), null, this.molecule(EnumMolecule.polycyanoacrylate), this.element(EnumElement.Hg), this.molecule(EnumMolecule.polycyanoacrylate), null, this.molecule(EnumMolecule.polycyanoacrylate), null }));
        SynthesisRecipe.add(new SynthesisRecipe(var43, true, 15000, new Chemical[] { this.element(EnumElement.Yb), this.element(EnumElement.Yb), this.element(EnumElement.No), null, this.element(EnumElement.Yb, 2), this.element(EnumElement.No, 2), null, this.element(EnumElement.No), null }));
        SynthesisRecipe.add(new SynthesisRecipe(var44, true, 500000, new Chemical[] { var53, var53, var53, var21, this.element(EnumElement.Cn, 16), var53, var52, var52, var52 }));
        SynthesisRecipe.add(new SynthesisRecipe(var45, true, 2000, new Chemical[] { this.element(EnumElement.C), null, null, null, this.molecule(EnumMolecule.ttx), null, null, null, this.element(EnumElement.C) }));
        SynthesisRecipe.add(new SynthesisRecipe(var48, true, 500, new Chemical[] { this.element(EnumElement.P), null, this.element(EnumElement.P), this.element(EnumElement.P), null, this.element(EnumElement.P), null, null, null }));
        ItemStack var54 = new ItemStack(Item.sugar);
        ItemStack var55 = new ItemStack(Item.reed);
        ItemStack var56 = new ItemStack(Block.pumpkin);
        ItemStack var57 = new ItemStack(Block.melon);
        ItemStack var58 = new ItemStack(Item.speckledMelon);
        ItemStack var59 = new ItemStack(Item.melon);
        ItemStack var60 = new ItemStack(Item.carrot);
        ItemStack var61 = new ItemStack(Item.goldenCarrot);
        ItemStack var62 = new ItemStack(Item.dyePowder, 1, 3);
        ItemStack var63 = new ItemStack(Item.potato);
        ItemStack var64 = new ItemStack(Item.bread);
        ItemStack var65 = new ItemStack(Item.appleRed);
        ItemStack var66 = new ItemStack(Item.appleGold, 1, 0);
       //  new ItemStack(Item.appleGold, 1, 1);
        ItemStack var68 = new ItemStack(Item.chickenCooked);
        DecomposerRecipe.add(new DecomposerRecipeChance(var54, 0.75F, new Chemical[] { this.molecule(EnumMolecule.sucrose) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(var55, 0.65F, new Chemical[] { this.molecule(EnumMolecule.sucrose), this.element(EnumElement.H, 2), this.element(EnumElement.O) }));
        DecomposerRecipe.add(new DecomposerRecipe(var62, new Chemical[] { this.molecule(EnumMolecule.theobromine) }));
        DecomposerRecipe.add(new DecomposerRecipe(var56, new Chemical[] { this.molecule(EnumMolecule.cucurbitacin) }));
        DecomposerRecipe.add(new DecomposerRecipe(var57, new Chemical[] { this.molecule(EnumMolecule.cucurbitacin), this.molecule(EnumMolecule.asparticAcid), this.molecule(EnumMolecule.water, 16) }));
        DecomposerRecipe.add(new DecomposerRecipe(var58, new Chemical[] { this.molecule(EnumMolecule.water, 4), this.molecule(EnumMolecule.whitePigment), this.element(EnumElement.Au, 1) }));
        DecomposerRecipe.add(new DecomposerRecipe(var59, new Chemical[] { this.molecule(EnumMolecule.water) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(var60, 0.05F, new Chemical[] { this.molecule(EnumMolecule.ret) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(var61, 0.2F, new Chemical[] { this.molecule(EnumMolecule.ret), this.element(EnumElement.Au, 4) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(var63, 0.4F, new Chemical[] { this.molecule(EnumMolecule.water, 8), this.element(EnumElement.K, 2), this.molecule(EnumMolecule.cellulose) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(var64, 0.1F, new Chemical[] { this.molecule(EnumMolecule.starch), this.molecule(EnumMolecule.sucrose) }));
        DecomposerRecipe.add(new DecomposerRecipe(var65, new Chemical[] { this.molecule(EnumMolecule.malicAcid) }));
        DecomposerRecipe.add(new DecomposerRecipe(var66, new Chemical[] { this.molecule(EnumMolecule.malicAcid), this.element(EnumElement.Au, 8) }));
        DecomposerRecipe.add(new DecomposerRecipe(var66, new Chemical[] { this.molecule(EnumMolecule.malicAcid), this.element(EnumElement.Au, 64), this.element(EnumElement.Np) }));
        DecomposerRecipe.add(new DecomposerRecipe(var68, new Chemical[] { this.element(EnumElement.K), this.element(EnumElement.Na), this.element(EnumElement.C, 2) }));
        SynthesisRecipe.add(new SynthesisRecipe(var54, false, 400, new Chemical[] { this.molecule(EnumMolecule.sucrose) }));
        SynthesisRecipe.add(new SynthesisRecipe(var65, false, 400, new Chemical[] { this.molecule(EnumMolecule.malicAcid), this.molecule(EnumMolecule.water, 2) }));
        SynthesisRecipe.add(new SynthesisRecipe(var62, false, 400, new Chemical[] { this.molecule(EnumMolecule.theobromine) }));
        SynthesisRecipe.add(new SynthesisRecipe(var56, false, 400, new Chemical[] { this.molecule(EnumMolecule.cucurbitacin) }));
        SynthesisRecipe.add(new SynthesisRecipe(var68, true, 5000, new Chemical[] { this.element(EnumElement.K, 16), this.element(EnumElement.Na, 16), this.element(EnumElement.C, 16) }));
        ItemStack var69 = new ItemStack(Item.gunpowder);
        ItemStack var70 = new ItemStack(Block.tnt);
        DecomposerRecipe.add(new DecomposerRecipe(var69, new Chemical[] { this.molecule(EnumMolecule.potassiumNitrate), this.element(EnumElement.S, 2), this.element(EnumElement.C) }));
        DecomposerRecipe.add(new DecomposerRecipe(var70, new Chemical[] { this.molecule(EnumMolecule.tnt) }));
        SynthesisRecipe.add(new SynthesisRecipe(var70, false, 1000, new Chemical[] { this.molecule(EnumMolecule.tnt) }));
        SynthesisRecipe.add(new SynthesisRecipe(var69, true, 600, new Chemical[] { this.molecule(EnumMolecule.potassiumNitrate), this.element(EnumElement.C), null, this.element(EnumElement.S, 2), null, null, null, null, null }));
        ItemStack var71 = new ItemStack(Block.wood, 1, -1);
        ItemStack var72 = new ItemStack(Block.planks, 1, -1);
        ItemStack var140 = new ItemStack(Block.planks, 1, 0);
        ItemStack var141 = new ItemStack(Block.planks, 1, 1);
        ItemStack var142 = new ItemStack(Block.planks, 1, 2);
        ItemStack var143 = new ItemStack(Block.planks, 1, 3);
        ItemStack var73 = new ItemStack(Item.stick);
        ItemStack var74 = new ItemStack(Block.wood, 1, 0);
        ItemStack var75 = new ItemStack(Block.wood, 1, 1);
        ItemStack var76 = new ItemStack(Block.wood, 1, 2);
        ItemStack var77 = new ItemStack(Block.wood, 1, 3);
        ItemStack var78 = new ItemStack(Item.doorWood);
        ItemStack var79 = new ItemStack(Block.pressurePlatePlanks, 1, -1);
        DecomposerRecipe.add(new DecomposerRecipeChance(var71, 0.5F, new Chemical[] { this.molecule(EnumMolecule.cellulose, 8) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(var74, 0.5F, new Chemical[] { this.molecule(EnumMolecule.cellulose, 8) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(var75, 0.5F, new Chemical[] { this.molecule(EnumMolecule.cellulose, 8) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(var76, 0.5F, new Chemical[] { this.molecule(EnumMolecule.cellulose, 8) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(var77, 0.5F, new Chemical[] { this.molecule(EnumMolecule.cellulose, 8) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(var72, 0.4F, new Chemical[] { this.molecule(EnumMolecule.cellulose, 2) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(var140, 0.4F, new Chemical[] { this.molecule(EnumMolecule.cellulose, 2) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(var141, 0.4F, new Chemical[] { this.molecule(EnumMolecule.cellulose, 2) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(var142, 0.4F, new Chemical[] { this.molecule(EnumMolecule.cellulose, 2) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(var143, 0.4F, new Chemical[] { this.molecule(EnumMolecule.cellulose, 2) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(var73, 0.3F, new Chemical[] { this.molecule(EnumMolecule.cellulose) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(var78, 0.4F, new Chemical[] { this.molecule(EnumMolecule.cellulose, 12) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(var79, 0.4F, new Chemical[] { this.molecule(EnumMolecule.cellulose, 4) }));
        Molecule var81 = this.molecule(EnumMolecule.cellulose, 1);
        SynthesisRecipe.add(new SynthesisRecipe(var74, true, 100, new Chemical[] { var81, var81, var81, null, var81, null, null, null, null }));
        SynthesisRecipe.add(new SynthesisRecipe(var75, true, 100, new Chemical[] { null, null, null, null, var81, null, var81, var81, var81 }));
        SynthesisRecipe.add(new SynthesisRecipe(var76, true, 100, new Chemical[] { var81, null, var81, null, null, null, var81, null, var81 }));
        SynthesisRecipe.add(new SynthesisRecipe(var77, true, 100, new Chemical[] { var81, null, null, var81, var81, null, var81, null, null }));
        ItemStack var82 = new ItemStack(Item.dyePowder, 1, 0);
        ItemStack var83 = new ItemStack(Item.dyePowder, 1, 1);
        ItemStack var84 = new ItemStack(Item.dyePowder, 1, 2);
        ItemStack var85 = new ItemStack(Item.dyePowder, 1, 4);
        ItemStack var86 = new ItemStack(Item.dyePowder, 1, 5);
        ItemStack var87 = new ItemStack(Item.dyePowder, 1, 6);
        ItemStack var88 = new ItemStack(Item.dyePowder, 1, 7);
        ItemStack var89 = new ItemStack(Item.dyePowder, 1, 8);
        ItemStack var90 = new ItemStack(Item.dyePowder, 1, 9);
        ItemStack var91 = new ItemStack(Item.dyePowder, 1, 10);
        ItemStack var92 = new ItemStack(Item.dyePowder, 1, 11); 
        ItemStack var93 = new ItemStack(Item.dyePowder, 1, 12);
        ItemStack var94 = new ItemStack(Item.dyePowder, 1, 13);
        ItemStack var95 = new ItemStack(Item.dyePowder, 1, 14);
        ItemStack var96 = new ItemStack(Item.dyePowder, 1, 15);
        DecomposerRecipe.add(new DecomposerRecipe(var82, new Chemical[] { this.molecule(EnumMolecule.blackPigment) }));
        DecomposerRecipe.add(new DecomposerRecipe(var83, new Chemical[] { this.molecule(EnumMolecule.redPigment) }));
        DecomposerRecipe.add(new DecomposerRecipe(var84, new Chemical[] { this.molecule(EnumMolecule.greenPigment) }));
        DecomposerRecipe.add(new DecomposerRecipe(var85, new Chemical[] { this.molecule(EnumMolecule.lazurite) }));
        DecomposerRecipe.add(new DecomposerRecipe(var86, new Chemical[] { this.molecule(EnumMolecule.purplePigment) }));
        DecomposerRecipe.add(new DecomposerRecipe(var87, new Chemical[] { this.molecule(EnumMolecule.lightbluePigment), this.molecule(EnumMolecule.whitePigment) }));
        DecomposerRecipe.add(new DecomposerRecipe(var88, new Chemical[] { this.molecule(EnumMolecule.whitePigment), this.molecule(EnumMolecule.blackPigment) }));
        DecomposerRecipe.add(new DecomposerRecipe(var89, new Chemical[] { this.molecule(EnumMolecule.whitePigment), this.molecule(EnumMolecule.blackPigment, 2) }));
        DecomposerRecipe.add(new DecomposerRecipe(var90, new Chemical[] { this.molecule(EnumMolecule.redPigment), this.molecule(EnumMolecule.whitePigment) }));
        DecomposerRecipe.add(new DecomposerRecipe(var91, new Chemical[] { this.molecule(EnumMolecule.limePigment) }));
        DecomposerRecipe.add(new DecomposerRecipe(var92, new Chemical[] { this.molecule(EnumMolecule.yellowPigment) }));
        DecomposerRecipe.add(new DecomposerRecipe(var93, new Chemical[] { this.molecule(EnumMolecule.lightbluePigment) }));
        DecomposerRecipe.add(new DecomposerRecipe(var94, new Chemical[] { this.molecule(EnumMolecule.lightbluePigment), this.molecule(EnumMolecule.redPigment) }));
        DecomposerRecipe.add(new DecomposerRecipe(var95, new Chemical[] { this.molecule(EnumMolecule.orangePigment) }));
        DecomposerRecipe.add(new DecomposerRecipe(var96, new Chemical[] { this.molecule(EnumMolecule.whitePigment) }));
        SynthesisRecipe.add(new SynthesisRecipe(var82, false, 50, new Chemical[] { this.molecule(EnumMolecule.blackPigment) }));
        SynthesisRecipe.add(new SynthesisRecipe(var83, false, 50, new Chemical[] { this.molecule(EnumMolecule.redPigment) }));
        SynthesisRecipe.add(new SynthesisRecipe(var84, false, 50, new Chemical[] { this.molecule(EnumMolecule.greenPigment) }));
        SynthesisRecipe.add(new SynthesisRecipe(var85, false, 50, new Chemical[] { this.molecule(EnumMolecule.lazurite) }));
        SynthesisRecipe.add(new SynthesisRecipe(var86, false, 50, new Chemical[] { this.molecule(EnumMolecule.purplePigment) }));
        SynthesisRecipe.add(new SynthesisRecipe(var87, false, 50, new Chemical[] { this.molecule(EnumMolecule.lightbluePigment), this.molecule(EnumMolecule.whitePigment) }));
        SynthesisRecipe.add(new SynthesisRecipe(var88, false, 50, new Chemical[] { this.molecule(EnumMolecule.whitePigment), this.molecule(EnumMolecule.blackPigment) }));
        SynthesisRecipe.add(new SynthesisRecipe(var89, false, 50, new Chemical[] { this.molecule(EnumMolecule.whitePigment), this.molecule(EnumMolecule.blackPigment, 2) }));
        SynthesisRecipe.add(new SynthesisRecipe(var90, false, 50, new Chemical[] { this.molecule(EnumMolecule.redPigment), this.molecule(EnumMolecule.whitePigment) }));
        SynthesisRecipe.add(new SynthesisRecipe(var91, false, 50, new Chemical[] { this.molecule(EnumMolecule.limePigment) }));
        SynthesisRecipe.add(new SynthesisRecipe(var92, false, 50, new Chemical[] { this.molecule(EnumMolecule.yellowPigment) }));
        SynthesisRecipe.add(new SynthesisRecipe(var93, false, 50, new Chemical[] { this.molecule(EnumMolecule.lightbluePigment) }));
        SynthesisRecipe.add(new SynthesisRecipe(var94, false, 50, new Chemical[] { this.molecule(EnumMolecule.lightbluePigment), this.molecule(EnumMolecule.redPigment) }));
        SynthesisRecipe.add(new SynthesisRecipe(var95, false, 50, new Chemical[] { this.molecule(EnumMolecule.orangePigment) }));
        SynthesisRecipe.add(new SynthesisRecipe(var96, false, 50, new Chemical[] { this.molecule(EnumMolecule.whitePigment) }));
        ItemStack var97 = new ItemStack(Block.cloth, 1, 0);
        ItemStack var98 = new ItemStack(Block.cloth, 1, 1);
        ItemStack var99 = new ItemStack(Block.cloth, 1, 2);
        ItemStack var100 = new ItemStack(Block.cloth, 1, 3);
        ItemStack var101 = new ItemStack(Block.cloth, 1, 4);
        ItemStack var102 = new ItemStack(Block.cloth, 1, 5);
        ItemStack var103 = new ItemStack(Block.cloth, 1, 6);
        ItemStack var104 = new ItemStack(Block.cloth, 1, 7);
        ItemStack var105 = new ItemStack(Block.cloth, 1, 8);
        ItemStack var106 = new ItemStack(Block.cloth, 1, 9);
        ItemStack var107 = new ItemStack(Block.cloth, 1, 10);
        ItemStack var108 = new ItemStack(Block.cloth, 1, 11);
        ItemStack var109 = new ItemStack(Block.cloth, 1, 12);
        ItemStack var110 = new ItemStack(Block.cloth, 1, 13);
        ItemStack var111 = new ItemStack(Block.cloth, 1, 14);
        ItemStack var112 = new ItemStack(Block.cloth, 1, 15);
        DecomposerRecipe.add(new DecomposerRecipeChance(var111, 0.6F, new Chemical[] { this.molecule(EnumMolecule.glycine), this.molecule(EnumMolecule.redPigment) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(var110, 0.6F, new Chemical[] { this.molecule(EnumMolecule.glycine), this.molecule(EnumMolecule.greenPigment) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(var108, 0.6F, new Chemical[] { this.molecule(EnumMolecule.glycine), this.molecule(EnumMolecule.lazurite) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(var107, 0.6F, new Chemical[] { this.molecule(EnumMolecule.glycine), this.molecule(EnumMolecule.purplePigment) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(var106, 0.6F, new Chemical[] { this.molecule(EnumMolecule.glycine), this.molecule(EnumMolecule.lightbluePigment), this.molecule(EnumMolecule.whitePigment) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(var105, 0.6F, new Chemical[] { this.molecule(EnumMolecule.glycine), this.molecule(EnumMolecule.whitePigment), this.molecule(EnumMolecule.blackPigment) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(var104, 0.6F, new Chemical[] { this.molecule(EnumMolecule.glycine), this.molecule(EnumMolecule.whitePigment), this.molecule(EnumMolecule.blackPigment, 2) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(var103, 0.6F, new Chemical[] { this.molecule(EnumMolecule.glycine), this.molecule(EnumMolecule.redPigment), this.molecule(EnumMolecule.whitePigment) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(var102, 0.6F, new Chemical[] { this.molecule(EnumMolecule.glycine), this.molecule(EnumMolecule.limePigment) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(var101, 0.6F, new Chemical[] { this.molecule(EnumMolecule.glycine), this.molecule(EnumMolecule.yellowPigment) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(var100, 0.6F, new Chemical[] { this.molecule(EnumMolecule.glycine), this.molecule(EnumMolecule.lightbluePigment) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(var99, 0.6F, new Chemical[] { this.molecule(EnumMolecule.glycine), this.molecule(EnumMolecule.lightbluePigment), this.molecule(EnumMolecule.redPigment) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(var98, 0.6F, new Chemical[] { this.molecule(EnumMolecule.glycine), this.molecule(EnumMolecule.orangePigment) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(var97, 0.6F, new Chemical[] { this.molecule(EnumMolecule.glycine), this.molecule(EnumMolecule.whitePigment) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(var112, 0.6F, new Chemical[] { this.molecule(EnumMolecule.glycine), this.molecule(EnumMolecule.blackPigment) }));
        SynthesisRecipe.add(new SynthesisRecipe(var111, false, 50, new Chemical[] { this.molecule(EnumMolecule.glycine), this.molecule(EnumMolecule.redPigment) }));
        SynthesisRecipe.add(new SynthesisRecipe(var110, false, 50, new Chemical[] { this.molecule(EnumMolecule.glycine), this.molecule(EnumMolecule.greenPigment) }));
        SynthesisRecipe.add(new SynthesisRecipe(var108, false, 50, new Chemical[] { this.molecule(EnumMolecule.glycine), this.molecule(EnumMolecule.lazurite) }));
        SynthesisRecipe.add(new SynthesisRecipe(var107, false, 50, new Chemical[] { this.molecule(EnumMolecule.glycine), this.molecule(EnumMolecule.purplePigment) }));
        SynthesisRecipe.add(new SynthesisRecipe(var106, false, 50, new Chemical[] { this.molecule(EnumMolecule.glycine), this.molecule(EnumMolecule.lightbluePigment), this.molecule(EnumMolecule.whitePigment) }));
        SynthesisRecipe.add(new SynthesisRecipe(var105, false, 50, new Chemical[] { this.molecule(EnumMolecule.glycine), this.molecule(EnumMolecule.whitePigment), this.molecule(EnumMolecule.blackPigment) }));
        SynthesisRecipe.add(new SynthesisRecipe(var104, false, 50, new Chemical[] { this.molecule(EnumMolecule.glycine), this.molecule(EnumMolecule.whitePigment), this.molecule(EnumMolecule.blackPigment, 2) }));
        SynthesisRecipe.add(new SynthesisRecipe(var103, false, 50, new Chemical[] { this.molecule(EnumMolecule.glycine), this.molecule(EnumMolecule.redPigment), this.molecule(EnumMolecule.whitePigment) }));
        SynthesisRecipe.add(new SynthesisRecipe(var102, false, 50, new Chemical[] { this.molecule(EnumMolecule.glycine), this.molecule(EnumMolecule.limePigment) }));
        SynthesisRecipe.add(new SynthesisRecipe(var101, false, 50, new Chemical[] { this.molecule(EnumMolecule.glycine), this.molecule(EnumMolecule.yellowPigment) }));
        SynthesisRecipe.add(new SynthesisRecipe(var100, false, 50, new Chemical[] { this.molecule(EnumMolecule.glycine), this.molecule(EnumMolecule.lightbluePigment) }));
        SynthesisRecipe.add(new SynthesisRecipe(var99, false, 50, new Chemical[] { this.molecule(EnumMolecule.glycine), this.molecule(EnumMolecule.lightbluePigment), this.molecule(EnumMolecule.redPigment) }));
        SynthesisRecipe.add(new SynthesisRecipe(var98, false, 50, new Chemical[] { this.molecule(EnumMolecule.glycine), this.molecule(EnumMolecule.orangePigment) }));
        SynthesisRecipe.add(new SynthesisRecipe(var97, false, 50, new Chemical[] { this.molecule(EnumMolecule.glycine), this.molecule(EnumMolecule.whitePigment) }));
        SynthesisRecipe.add(new SynthesisRecipe(var112, false, 50, new Chemical[] { this.molecule(EnumMolecule.glycine), this.molecule(EnumMolecule.blackPigment) }));
        Molecule var113 = this.molecule(EnumMolecule.polyvinylChloride);
        ItemStack var114 = new ItemStack(Item.record13);
        ItemStack var115 = new ItemStack(Item.recordCat);
        ItemStack var116 = new ItemStack(Item.recordFar);
        ItemStack var117 = new ItemStack(Item.recordMall);
        ItemStack var118 = new ItemStack(Item.recordMellohi);
        ItemStack var119 = new ItemStack(Item.recordStal);
        ItemStack var120 = new ItemStack(Item.recordStrad);
        ItemStack var121 = new ItemStack(Item.recordWard);
        ItemStack var122 = new ItemStack(Item.recordChirp);
        DecomposerRecipe.add(new DecomposerRecipe(var114, new Chemical[] { var113 }));
        DecomposerRecipe.add(new DecomposerRecipe(var115, new Chemical[] { var113 }));
        DecomposerRecipe.add(new DecomposerRecipe(var116, new Chemical[] { var113 }));
        DecomposerRecipe.add(new DecomposerRecipe(var117, new Chemical[] { var113 }));
        DecomposerRecipe.add(new DecomposerRecipe(var118, new Chemical[] { var113 }));
        DecomposerRecipe.add(new DecomposerRecipe(var119, new Chemical[] { var113 }));
        DecomposerRecipe.add(new DecomposerRecipe(var120, new Chemical[] { var113 }));
        DecomposerRecipe.add(new DecomposerRecipe(var121, new Chemical[] { var113 }));
        DecomposerRecipe.add(new DecomposerRecipe(var122, new Chemical[] { var113 }));
        SynthesisRecipe.add(new SynthesisRecipe(var114, false, 1000, new Chemical[] { var113 }));
        SynthesisRecipe.add(new SynthesisRecipe(var115, false, 1000, new Chemical[] { null, var113 }));
        SynthesisRecipe.add(new SynthesisRecipe(var116, false, 1000, new Chemical[] { null, null, var113 }));
        SynthesisRecipe.add(new SynthesisRecipe(var117, false, 1000, new Chemical[] { null, null, null, var113 }));
        SynthesisRecipe.add(new SynthesisRecipe(var118, false, 1000, new Chemical[] { null, null, null, null, var113 }));
        SynthesisRecipe.add(new SynthesisRecipe(var119, false, 1000, new Chemical[] { null, null, null, null, null, var113 }));
        SynthesisRecipe.add(new SynthesisRecipe(var120, false, 1000, new Chemical[] { null, null, null, null, null, null, var113 }));
        SynthesisRecipe.add(new SynthesisRecipe(var121, false, 1000, new Chemical[] { null, null, null, null, null, null, null, var113 }));
        SynthesisRecipe.add(new SynthesisRecipe(var122, false, 1000, new Chemical[] { null, null, null, null, null, null, null, null, var113 }));
        ItemStack var123 = new ItemStack(Block.mushroomBrown);
        ItemStack var124 = new ItemStack(Block.mushroomRed);
        ItemStack var125 = new ItemStack(Block.cactus);
        DecomposerRecipe.add(new DecomposerRecipe(var123, new Chemical[] { this.molecule(EnumMolecule.psilocybin), this.molecule(EnumMolecule.water, 2) }));
        DecomposerRecipe.add(new DecomposerRecipe(var124, new Chemical[] { this.molecule(EnumMolecule.pantherine), this.molecule(EnumMolecule.water, 2), this.molecule(EnumMolecule.nicotine)}));
        DecomposerRecipe.add(new DecomposerRecipe(var125, new Chemical[] { this.molecule(EnumMolecule.mescaline), this.molecule(EnumMolecule.water, 20) }));
        SynthesisRecipe.add(new SynthesisRecipe(var125, true, 200, new Chemical[] { this.molecule(EnumMolecule.water, 5), null, this.molecule(EnumMolecule.water, 5), null, this.molecule(EnumMolecule.mescaline), null, this.molecule(EnumMolecule.water, 5), null, this.molecule(EnumMolecule.water, 5) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(var13, 0.8F, new Chemical[] { this.molecule(EnumMolecule.iron3oxide, 6), this.molecule(EnumMolecule.strontiumNitrate, 6) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(var18, 0.42F, new Chemical[] { this.molecule(EnumMolecule.iron3oxide), this.molecule(EnumMolecule.strontiumNitrate) }));
        SynthesisRecipe.add(new SynthesisRecipe(var18, true, 100, new Chemical[] { null, null, this.molecule(EnumMolecule.iron3oxide), null, this.molecule(EnumMolecule.strontiumNitrate), null, null, null, null }));
        ItemStack var126 = new ItemStack(Item.enderPearl);
        DecomposerRecipe.add(new DecomposerRecipe(var126, new Chemical[] { this.element(EnumElement.Es), this.molecule(EnumMolecule.calciumCarbonate, 8) }));
        SynthesisRecipe.add(new SynthesisRecipe(var126, true, 5000, new Chemical[] { this.molecule(EnumMolecule.calciumCarbonate), this.molecule(EnumMolecule.calciumCarbonate), this.molecule(EnumMolecule.calciumCarbonate), this.molecule(EnumMolecule.calciumCarbonate), this.element(EnumElement.Es), this.molecule(EnumMolecule.calciumCarbonate), this.molecule(EnumMolecule.calciumCarbonate), this.molecule(EnumMolecule.calciumCarbonate), this.molecule(EnumMolecule.calciumCarbonate) }));
        ItemStack var127 = new ItemStack(Block.obsidian);
        DecomposerRecipe.add(new DecomposerRecipe(var127, new Chemical[] { this.molecule(EnumMolecule.siliconDioxide, 16), this.molecule(EnumMolecule.magnesiumOxide, 8) }));
        SynthesisRecipe.add(new SynthesisRecipe(var127, true, 1000, new Chemical[] { this.molecule(EnumMolecule.siliconDioxide, 4), this.molecule(EnumMolecule.siliconDioxide, 4), this.molecule(EnumMolecule.siliconDioxide, 4), this.molecule(EnumMolecule.magnesiumOxide, 2), null, this.molecule(EnumMolecule.siliconDioxide, 4), this.molecule(EnumMolecule.magnesiumOxide, 2), this.molecule(EnumMolecule.magnesiumOxide, 2), this.molecule(EnumMolecule.magnesiumOxide, 2) }));
        ItemStack var128 = new ItemStack(Item.bone);
        ItemStack var129 = new ItemStack(Item.silk);
       // new ItemStack(Block.cloth, 1, -1);
       // new ItemStack(Block.cloth, 1, 0);
        DecomposerRecipe.add(new DecomposerRecipe(var128, new Chemical[] { this.molecule(EnumMolecule.hydroxylapatite) }));
        DecomposerRecipe.add(new DecomposerRecipeChance(var129, 0.45F, new Chemical[] { this.molecule(EnumMolecule.serine), this.molecule(EnumMolecule.glycine), this.molecule(EnumMolecule.alinine) }));
        SynthesisRecipe.add(new SynthesisRecipe(var128, false, 100, new Chemical[] { this.molecule(EnumMolecule.hydroxylapatite) }));
        SynthesisRecipe.add(new SynthesisRecipe(var129, true, 150, new Chemical[] { this.molecule(EnumMolecule.serine), this.molecule(EnumMolecule.glycine), this.molecule(EnumMolecule.alinine) }));
        ItemStack var132 = new ItemStack(Block.cobblestone);
        DecomposerRecipe.add(new DecomposerRecipeSelect(var1, 0.2F, new DecomposerRecipe[] { new DecomposerRecipe(new Chemical[] { this.element(EnumElement.Si), this.element(EnumElement.O) }), new DecomposerRecipe(new Chemical[] { this.element(EnumElement.Fe), this.element(EnumElement.O) }), new DecomposerRecipe(new Chemical[] { this.element(EnumElement.Mg), this.element(EnumElement.O) }), new DecomposerRecipe(new Chemical[] { this.element(EnumElement.Ti), this.element(EnumElement.O) }), new DecomposerRecipe(new Chemical[] { this.element(EnumElement.Pb), this.element(EnumElement.O) }), new DecomposerRecipe(new Chemical[] { this.element(EnumElement.Zn), this.element(EnumElement.O) }) }));
        DecomposerRecipe.add(new DecomposerRecipeSelect(var132, 0.1F, new DecomposerRecipe[] { new DecomposerRecipe(new Chemical[] { this.element(EnumElement.Si), this.element(EnumElement.O) }), new DecomposerRecipe(new Chemical[] { this.element(EnumElement.Fe), this.element(EnumElement.O) }), new DecomposerRecipe(new Chemical[] { this.element(EnumElement.Mg), this.element(EnumElement.O) }), new DecomposerRecipe(new Chemical[] { this.element(EnumElement.Ti), this.element(EnumElement.O) }), new DecomposerRecipe(new Chemical[] { this.element(EnumElement.Pb), this.element(EnumElement.O) }), new DecomposerRecipe(new Chemical[] { this.element(EnumElement.Na), this.element(EnumElement.Cl) }) }));
        DecomposerRecipe.add(new DecomposerRecipeSelect(var3, 0.07F, new DecomposerRecipe[] { new DecomposerRecipe(new Chemical[] { this.element(EnumElement.Si), this.element(EnumElement.O) }), new DecomposerRecipe(new Chemical[] { this.element(EnumElement.Fe), this.element(EnumElement.O) }), new DecomposerRecipe(new Chemical[] { this.element(EnumElement.Mg), this.element(EnumElement.O) }), new DecomposerRecipe(new Chemical[] { this.element(EnumElement.Ti), this.element(EnumElement.O) }), new DecomposerRecipe(new Chemical[] { this.element(EnumElement.Pb), this.element(EnumElement.O) }), new DecomposerRecipe(new Chemical[] { this.element(EnumElement.Zn), this.element(EnumElement.O) }), new DecomposerRecipe(new Chemical[] { this.element(EnumElement.Ga), this.element(EnumElement.As) }) }));
        SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Block.cobblestone, 8), true, 50, new Chemical[] { this.element(EnumElement.Si), null, null, null, this.element(EnumElement.O, 2), null }));
        SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Block.stone, 7), true, 50, new Chemical[] { this.element(EnumElement.Si), null, null, this.element(EnumElement.O, 2), null, null }));
        SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Block.dirt, 16), true, 50, new Chemical[] { null, null, null, null, this.element(EnumElement.O, 2), this.element(EnumElement.Si) }));
        ItemStack var133 = new ItemStack(Block.netherrack);
        ItemStack var134 = new ItemStack(Block.slowSand);
        ItemStack var135 = new ItemStack(Block.whiteStone);
        DecomposerRecipe.add(new DecomposerRecipeSelect(var133, 0.1F, new DecomposerRecipe[] { new DecomposerRecipe(new Chemical[] { this.element(EnumElement.Si, 2), this.element(EnumElement.O), this.element(EnumElement.Fe) }), new DecomposerRecipe(new Chemical[] { this.element(EnumElement.Si, 2), this.element(EnumElement.Ni), this.element(EnumElement.Tc) }), new DecomposerRecipe(new Chemical[] { this.element(EnumElement.Si, 3), this.element(EnumElement.Ti), this.element(EnumElement.Fe) }), new DecomposerRecipe(new Chemical[] { this.element(EnumElement.Si, 1), this.element(EnumElement.W, 4), this.element(EnumElement.Cr, 2) }), new DecomposerRecipe(new Chemical[] { this.element(EnumElement.Si, 10), this.element(EnumElement.W, 1), this.element(EnumElement.Zn, 8), this.element(EnumElement.Be, 4) }) }));
        DecomposerRecipe.add(new DecomposerRecipeSelect(var134, 0.2F, new DecomposerRecipe[] { new DecomposerRecipe(new Chemical[] { this.element(EnumElement.Pb, 3), this.element(EnumElement.Be, 1), this.element(EnumElement.Si, 2), this.element(EnumElement.O) }), new DecomposerRecipe(new Chemical[] { this.element(EnumElement.Pb, 1), this.element(EnumElement.Si, 5), this.element(EnumElement.O, 2) }), new DecomposerRecipe(new Chemical[] { this.element(EnumElement.Si, 2), this.element(EnumElement.O) }), new DecomposerRecipe(new Chemical[] { this.element(EnumElement.Si, 6), this.element(EnumElement.O, 2) }), new DecomposerRecipe(new Chemical[] { this.element(EnumElement.Es, 1), this.element(EnumElement.O, 2) }) }));
        DecomposerRecipe.add(new DecomposerRecipeSelect(var135, 0.8F, new DecomposerRecipe[] { new DecomposerRecipe(new Chemical[] { this.element(EnumElement.Si, 2), this.element(EnumElement.O), this.element(EnumElement.H, 4), this.element(EnumElement.Li) }), new DecomposerRecipe(new Chemical[] { this.element(EnumElement.Es) }), new DecomposerRecipe(new Chemical[] { this.element(EnumElement.Pu) }), new DecomposerRecipe(new Chemical[] { this.element(EnumElement.Fr) }), new DecomposerRecipe(new Chemical[] { this.element(EnumElement.Nd) }), new DecomposerRecipe(new Chemical[] { this.element(EnumElement.Si, 2), this.element(EnumElement.O, 4) }), new DecomposerRecipe(new Chemical[] { this.element(EnumElement.H, 4) }), new DecomposerRecipe(new Chemical[] { this.element(EnumElement.Be, 8) }), new DecomposerRecipe(new Chemical[] { this.element(EnumElement.Li, 2) }), new DecomposerRecipe(new Chemical[] { this.element(EnumElement.Zr) }), new DecomposerRecipe(new Chemical[] { this.element(EnumElement.Na) }), new DecomposerRecipe(new Chemical[] { this.element(EnumElement.Rb) }), new DecomposerRecipe(new Chemical[] { this.element(EnumElement.Ga), this.element(EnumElement.As) }) }));
        ItemStack var136 = new ItemStack(Block.plantYellow);
        DecomposerRecipe.add(new DecomposerRecipeChance(var136, 0.3F, new Chemical[] { new Molecule(EnumMolecule.shikimicAcid, 2) }));
        ItemStack var137 = new ItemStack(Item.rottenFlesh);
        DecomposerRecipe.add(new DecomposerRecipeChance(var137, 0.05F, new Chemical[] { new Molecule(EnumMolecule.nod, 1) }));
        ItemStack var138 = new ItemStack(Block.tallGrass, 1, 2);
        DecomposerRecipe.add(new DecomposerRecipeChance(var138, 0.07F, new Chemical[] { new Molecule(EnumMolecule.biocide, 2) }));
        ItemStack var139 = new ItemStack(Block.tallGrass, 1, 1);
        DecomposerRecipe.add(new DecomposerRecipeChance(var139, 0.1F, new Chemical[] { new Molecule(EnumMolecule.afroman, 2) }));
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

   private void registerPoisonRecipes(EnumMolecule var1) {
      this.createPoisonedItemStack(Item.appleRed, 0, var1);
      this.createPoisonedItemStack(Item.porkCooked, 0, var1);
      this.createPoisonedItemStack(Item.beefCooked, 0, var1);
      this.createPoisonedItemStack(Item.carrot, 0, var1);
      this.createPoisonedItemStack(Item.bakedPotato, 0, var1);
      this.createPoisonedItemStack(Item.bread, 0, var1);
      this.createPoisonedItemStack(Item.potato, 0, var1);
      this.createPoisonedItemStack(Item.bucketMilk, 0, var1);
      this.createPoisonedItemStack(Item.fishCooked, 0, var1);
      this.createPoisonedItemStack(Item.cookie, 0, var1);
      this.createPoisonedItemStack(Item.pumpkinPie, 0, var1);
   }

   @ForgeSubscribe
   public void oreEvent(OreDictionary.OreRegisterEvent var1) {
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
		
		for (int i=0; i<compounds.length; i++){
		for (int j=0; j<itemTypes.length; j++){
			if(var1.Name.equals(itemTypes[j]+compounds[i])){
				System.out.print("Adding recipe for " + itemTypes[j] + compounds[i]);
				List<Chemical> _elems = new ArrayList<Chemical>();
				
				for (int k=0; k<elements[i].length; k++){
					_elems.add(this.element(elements[i][k], proportions[i][k]*sizeCoeff[j]));
				}
				
				DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, _elems.toArray(new Chemical[_elems.size()])));
				
				if(compounds[i].equals("Iron") && itemTypes[j].equals("dust")){
				      SynthesisRecipe.recipes.remove(recipeIron);
				}
				if(compounds[i].equals("Gold") && itemTypes[j].equals("dust")){
				      SynthesisRecipe.recipes.remove(recipeGold);
				}
				
				if(compounds[i].equals("Coal") && itemTypes[j].equals("dust")){
					SynthesisRecipe.remove(new ItemStack(Item.coal));
					SynthesisRecipe.remove(new ItemStack(Block.oreCoal));
					
					SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(Item.coal), true, 2000, new Chemical[]{this.element(EnumElement.C,4), null, this.element(EnumElement.C,4), 
						                                                                                         null, null, null, 
						                                                                                         this.element(EnumElement.C,4), null, this.element(EnumElement.C,4)}));
					
				}					
				
				if (craftable[j]){
					SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 1000*sizeCoeff[j], _elems.toArray(new Chemical[_elems.size()])));
				}
				return;					
				
				}
			}
		}	  
		
	 // BEGIN ORE DICTONARY BULLSHIT
      if(var1.Name.contains("uraniumOre")) {
         DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new Chemical[]{this.element(EnumElement.U, 4)}));
      } else if(var1.Name.contains("uraniumIngot")) {
         DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new Chemical[]{this.element(EnumElement.U, 2)}));
         SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 5000, new Chemical[]{this.element(EnumElement.U, 2)}));
      } else if(var1.Name.contains("itemDropUranium")) {
         DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new Chemical[]{this.element(EnumElement.U, 2)}));
         SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 5000, new Chemical[]{this.element(EnumElement.U, 2)}));
      } else if(var1.Name.contains("gemApatite")) {
         DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new Chemical[]{this.element(EnumElement.Ca, 5), this.molecule(EnumMolecule.phosphate, 4), this.element(EnumElement.Cl)}));
         SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 1000, new Chemical[]{this.element(EnumElement.Ca, 5), this.molecule(EnumMolecule.phosphate, 4), this.element(EnumElement.Cl)}));
//      } else if(var1.Name.contains("Iridium")) {
//         DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new Chemical[]{this.element(EnumElement.Ir, 2)}));
//         SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 1000, new Chemical[]{this.element(EnumElement.Ir, 2)}));
      } else if(var1.Name.contains("Ruby")) {
         DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new Chemical[]{this.molecule(EnumMolecule.aluminiumOxide), this.element(EnumElement.Cr)}));
         SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 1000, new Chemical[]{this.molecule(EnumMolecule.aluminiumOxide), this.element(EnumElement.Cr)}));
      } else if(var1.Name.contains("Sapphire")) {
         DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new Chemical[]{this.molecule(EnumMolecule.aluminiumOxide, 2)}));
         SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 1000, new Chemical[]{this.molecule(EnumMolecule.aluminiumOxide, 2)}));
      } else if(var1.Name.contains("plateSilicon")) {
         DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new Chemical[]{this.element(EnumElement.Si, 2)}));
         SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 1000, new Chemical[]{this.element(EnumElement.Si, 2)}));
      } else if(var1.Name.contains("xychoriumBlue")) {
         DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new Chemical[]{this.element(EnumElement.Zr, 2), this.element(EnumElement.Cu, 1)}));
         SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 300, new Chemical[]{this.element(EnumElement.Zr, 2), this.element(EnumElement.Cu, 1)}));
      } else if(var1.Name.contains("xychoriumRed")) {
         DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new Chemical[]{this.element(EnumElement.Zr, 2), this.element(EnumElement.Fe, 1)}));
         SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 300, new Chemical[]{this.element(EnumElement.Zr, 2), this.element(EnumElement.Fe, 1)}));
      } else if(var1.Name.contains("xychoriumGreen")) {
         DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new Chemical[]{this.element(EnumElement.Zr, 2), this.element(EnumElement.V, 1)}));
         SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 300, new Chemical[]{this.element(EnumElement.Zr, 2), this.element(EnumElement.V, 1)}));
      } else if(var1.Name.contains("xychoriumDark")) {
         DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new Chemical[]{this.element(EnumElement.Zr, 2), this.element(EnumElement.Si, 1)}));
         SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 300, new Chemical[]{this.element(EnumElement.Zr, 2), this.element(EnumElement.Si, 1)}));
      } else if(var1.Name.contains("xychoriumLight")) {
         DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new Chemical[]{this.element(EnumElement.Zr, 2), this.element(EnumElement.Ti, 1)}));
         SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 300, new Chemical[]{this.element(EnumElement.Zr, 2), this.element(EnumElement.Ti, 1)}));

     } else if(var1.Name.contains("ingotCobalt")) { // Tungsten - Cobalt Alloy
     DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new Chemical[]{this.element(EnumElement.Co, 2), this.element(EnumElement.W, 2)}));
     SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 5000, new Chemical[]{this.element(EnumElement.Co, 2), this.element(EnumElement.W, 2)}));
	
	} else if(var1.Name.contains("ingotArdite")) { // Tungsten - Iron - Silicon Alloy
     DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new Chemical[]{this.element(EnumElement.Fe, 2), this.element(EnumElement.W, 2), this.element(EnumElement.Si, 2)}));
     SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 5000, new Chemical[]{this.element(EnumElement.Fe, 2), this.element(EnumElement.W, 2), this.element(EnumElement.Si, 2)}));
	
	} else if(var1.Name.contains("ingotManyullyn")) { // Tungsten - Iron - Silicon - Cobalt Alloy
     DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new Chemical[]{this.element(EnumElement.Fe, 2), this.element(EnumElement.W, 2), this.element(EnumElement.Si, 2), this.element(EnumElement.Co, 2)}));
     SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 7000, new Chemical[]{this.element(EnumElement.Fe, 2), this.element(EnumElement.W, 2), this.element(EnumElement.Si, 2), this.element(EnumElement.Co, 2)}));
   } 
   else if(var1.Name.contains("gemRuby")) {
         DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new Chemical[]{this.molecule(EnumMolecule.aluminiumOxide), this.element(EnumElement.Cr)}));
         SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 1000, new Chemical[]{this.molecule(EnumMolecule.aluminiumOxide), this.element(EnumElement.Cr)}));
	} 
	   else if(var1.Name.contains("gemSapphire")) {
         DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new Chemical[]{this.molecule(EnumMolecule.aluminiumOxide)}));
         SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 1000, new Chemical[]{this.molecule(EnumMolecule.aluminiumOxide)}));
	} 
	else if(var1.Name.contains("gemPeridot")) {
         DecomposerRecipe.add(new DecomposerRecipe(var1.Ore, new Chemical[]{this.molecule(EnumMolecule.olivine)}));
         SynthesisRecipe.add(new SynthesisRecipe(var1.Ore, false, 1000, new Chemical[]{this.molecule(EnumMolecule.olivine)}));
	} 
	
   // cropStingberry   
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
} // EOF
