package ljdp.minechem.common;
import ljdp.minechem.api.core.Chemical;
import ljdp.minechem.api.core.Element;
import ljdp.minechem.api.core.EnumElement;
import ljdp.minechem.api.core.EnumMolecule;
import ljdp.minechem.api.core.Molecule;
import ljdp.minechem.api.recipe.DecomposerRecipe;
import ljdp.minechem.api.recipe.DecomposerRecipeChance;
import ljdp.minechem.api.recipe.DecomposerRecipeSelect;
import ljdp.minechem.api.recipe.SynthesisRecipe;
import net.minecraft.item.ItemStack;
import biomesoplenty.api.BlockReferences; 
public class Exporter {
public void Init() {
ItemStack Algae = new BlockReferences.getBlockItemStack("algae");
DecomposerRecipe.add(new DecomposerRecipeChance(Algae, 0.1F, new Chemical[] { new Molecule(EnumMolecule.nod) }));
ItemStack IndigoCap = new BlockReferences.getBlockItemStack("bluemilk"); // THE BLUE ONES FUCK YOU UP BAD!
DecomposerRecipe.add(new DecomposerRecipe(IndigoCap, new Chemical[] { new Molecule(EnumMolecule.pantherine), new Molecule(EnumMolecule.psilocybin), new Molecule(EnumMolecule.blueorgodye) }));
ItemStack MagicShroom = new BlockReferences.getBlockItemStack("toadstool");
DecomposerRecipe.add(new DecomposerRecipeChance(MagicShroom, 0.4F, new Chemical[] { new Molecule(EnumMolecule.psilocybin) }));
ItemStack Willowasprin = new BlockReferences.getBlockItemStack("willowLog");
DecomposerRecipe.add(new DecomposerRecipeChance(Willowasprin, 0.4F, new Chemical[] { new Molecule(EnumMolecule.asprin, 2) }));
ItemStack FoxFire = new BlockReferences.getBlockItemStack("glowshroom");
DecomposerRecipe.add(new DecomposerRecipeChance(FoxFire, 0.8F, new Chemical[] { new Molecule(EnumMolecule.psilocybin, 2), new Element(EnumElement.P, 4) }));
ItemStack Daisy1 = new BlockReferences.getBlockItemStack("daisy");
DecomposerRecipe.add(new DecomposerRecipeChance(Daisy1, 0.3F, new Chemical[] { new Molecule(EnumMolecule.shikimicAcid, 2), new Molecule(EnumMolecule.water, 2) }));
}
}