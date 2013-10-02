package ljdp.minechem.common.plugins;
import ljdp.minechem.api.core.Chemical;
import ljdp.minechem.api.core.Element;
import ljdp.minechem.api.core.EnumElement;
import ljdp.minechem.api.core.EnumMolecule;
import ljdp.minechem.api.core.Molecule;
import ljdp.minechem.api.recipe.DecomposerRecipe;
import ljdp.minechem.api.recipe.DecomposerRecipeChance;
import ljdp.minechem.api.recipe.DecomposerRecipeSelect;
import biomesoplenty.api.BlockReferences;
import biomesoplenty.api.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
public class BOPModule {
public static void DoBopExport() {
ItemStack Algae = BlockReferences.getBlockItemStack("algae");
DecomposerRecipe.add(new DecomposerRecipeChance(Algae, 0.08F, new Chemical[] { new Molecule(EnumMolecule.nod) }));
ItemStack IndigoCap = BlockReferences.getBlockItemStack("bluemilk"); // THE BLUE ONES FUCK YOU UP BAD!
DecomposerRecipe.add(new DecomposerRecipeSelect(IndigoCap, 0.9F, new DecomposerRecipe[] { new DecomposerRecipe(new Chemical[] { new Molecule(EnumMolecule.blueorgodye) }), new DecomposerRecipe(new Chemical[] { new Molecule(EnumMolecule.psilocybin, 2) }) }));
ItemStack MagicShroom = BlockReferences.getBlockItemStack("toadstool");
DecomposerRecipe.add(new DecomposerRecipeChance(MagicShroom, 0.8F, new Chemical[] { new Molecule(EnumMolecule.psilocybin) }));
ItemStack Willowasprin = BlockReferences.getBlockItemStack("willowLog");
DecomposerRecipe.add(new DecomposerRecipeChance(Willowasprin, 0.4F, new Chemical[] { new Molecule(EnumMolecule.asprin, 2) }));
ItemStack FoxFire = BlockReferences.getBlockItemStack("glowshroom");
DecomposerRecipe.add(new DecomposerRecipeChance(FoxFire, 0.6F, new Chemical[] { new Molecule(EnumMolecule.psilocybin, 2), new Element(EnumElement.P, 2) }));
ItemStack Daisy1 = BlockReferences.getBlockItemStack("daisy");
DecomposerRecipe.add(new DecomposerRecipeChance(Daisy1, 0.3F, new Chemical[] { new Molecule(EnumMolecule.shikimicAcid, 2), new Molecule(EnumMolecule.water, 2) }));
ItemStack WitherFlower = BlockReferences.getBlockItemStack("deathbloom");
DecomposerRecipe.add(new DecomposerRecipe(WitherFlower, new Chemical[] { new Molecule(EnumMolecule.poison, 2), new Molecule(EnumMolecule.water, 2) }));
ItemStack Sprout = BlockReferences.getBlockItemStack("sproutItem");
DecomposerRecipe.add(new DecomposerRecipeSelect(Sprout, 0.9F, new DecomposerRecipe[] { new DecomposerRecipe(new Chemical[] { new Molecule(EnumMolecule.asprin) }), new DecomposerRecipe(new Chemical[] { new Molecule(EnumMolecule.sucrose, 2) }) }));
ItemStack BlueXtal = BlockReferences.getBlockItemStack("crystal");
DecomposerRecipe.add(new DecomposerRecipe(BlueXtal, new Chemical[] {new Molecule(EnumMolecule.radchlor, 4)})); 
ItemStack CtaxStem = BlockReferences.getBlockItemStack("magicLog");
DecomposerRecipe.add(new DecomposerRecipeChance(CtaxStem, 0.4F, new Chemical[] { new Molecule(EnumMolecule.ctaxifolia, 1), new Molecule(EnumMolecule.cellulose, 2) }));
ItemStack CtaxLeaves = BlockReferences.getBlockItemStack("magicLeaves"); // Aritstic License here, C. Taxifolia is a algae, But this tree acts like C. Taxifolia as in it grows massive and spreads like fire.
DecomposerRecipe.add(new DecomposerRecipeChance(CtaxLeaves, 0.5F, new Chemical[] { new Molecule(EnumMolecule.ctaxifolia, 2), new Molecule(EnumMolecule.cellulose, 1) }));
ItemStack PyrophoricDirt = BlockReferences.getBlockItemStack("smolderingGrass");
DecomposerRecipe.add(new DecomposerRecipeChance(PyrophoricDirt, 0.9F, new Chemical[] { new Molecule(EnumMolecule.buli) }));
ItemStack LiquidCrystalFlower = BlockReferences.getBlockItemStack("rainbowflower");
DecomposerRecipe.add(new DecomposerRecipeChance(LiquidCrystalFlower, 0.9F, new Chemical[] { new Molecule(EnumMolecule.lcd) }));
}
}
