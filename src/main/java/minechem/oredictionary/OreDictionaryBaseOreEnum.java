package minechem.oredictionary;

import minechem.item.element.Element;
import minechem.item.element.ElementEnum;
import minechem.item.molecule.Molecule;
import minechem.item.molecule.MoleculeEnum;
import minechem.potion.PotionChemical;

public enum OreDictionaryBaseOreEnum
{
	iron("Copper", new Element(ElementEnum.Fe, 16)),
	gold("Gold", new Element(ElementEnum.Au, 16)),
	copper("Copper", new Element(ElementEnum.Cu, 16)),
	tin("Tin", new Element(ElementEnum.Sn, 16)),
	silver("Silver", new Element(ElementEnum.Ag, 16)),
	aluminum("Aluminum", new Element(ElementEnum.Al, 16)),
	aluminium("Aluminium", new Element(ElementEnum.Al, 16)),
	titanium("Titanium", new Element(ElementEnum.Ti, 16)),
	chrome("Chrome", new Element(ElementEnum.Cr, 16)),
	tungsten("Tungsten", new Element(ElementEnum.W, 16)),
	lead("Lead", new Element(ElementEnum.Pb, 16)),
	zinc("Zinc", new Element(ElementEnum.Zn, 16)),
	platinum("Platinum", new Element(ElementEnum.Pt, 16)),
	thorium("Thorium", new Element(ElementEnum.Th, 16)),
	nickel("Nickel", new Element(ElementEnum.Ni, 16)),
	osmium("Osmium", new Element(ElementEnum.Os, 16)),
	antimony("Antimony", new Element(ElementEnum.Sb, 16)),
	iridium("Iridium", new Element(ElementEnum.Ir, 16)),
	uranium("Uranium", new Element(ElementEnum.U, 16)),
	plutonium("Plutonium", new Element(ElementEnum.Pu, 16)),
	manganese("Manganese", new Element(ElementEnum.Mn, 16)),
	phosphorus("Phosphorus", new Element(ElementEnum.P, 16)),
	bitumen("Bitumen", new Molecule(MoleculeEnum.propane, 16)),
	prometheum("Prometheum", new Element(ElementEnum.Pm, 16)),
	amethyst("Amethyst", new Molecule(MoleculeEnum.siliconDioxide, 16)),
	peridot("Peridot", new Molecule(MoleculeEnum.peridot, 16)),
	topaz("Topaz", new Molecule(MoleculeEnum.topaz, 16)),
	tanzanite("Tanzanite", new Molecule(MoleculeEnum.zoisite, 16)),
	potash("Potash", new Molecule(MoleculeEnum.potassiumNitrate, 16)),
	sulfur("Sulfur", new Element(ElementEnum.S, 16)),
	saphire("Sapphire", new Molecule(MoleculeEnum.aluminiumOxide, 1)),
	ruby("Ruby", new Molecule(MoleculeEnum.aluminiumOxide, 1), new Element(ElementEnum.Cr, 1)),
	cobalt("Cobalt", new Element(ElementEnum.Co, 16)),
	ardite("Ardite", new Element(ElementEnum.Ar, 16)),
	salpeter("Salpeter", new Element(ElementEnum.K), new Element(ElementEnum.N), new Element(ElementEnum.O, 3)),
	salt("Salt", new Element(ElementEnum.Na), new Element(ElementEnum.Cl)),
	limestone("Limestone", new Molecule(MoleculeEnum.calciumCarbonate, 1)),
	obsidian("Obsidian", new Molecule(MoleculeEnum.siliconDioxide, 16), new Molecule(MoleculeEnum.magnesiumOxide, 8)),
	diamond("Diamond", new Molecule(MoleculeEnum.fullrene, 3)),
	NetherQuartz("NetherQuartz", new Molecule(MoleculeEnum.aluminiumPhosphate));

	private String name;
	private PotionChemical[] composition;

	public String getName()
	{
		return this.name;
	}

	public PotionChemical[] getComposition()
	{
		return this.composition;
	}

	OreDictionaryBaseOreEnum(String name, PotionChemical... composition)
	{
		this.name = name;
		this.composition = composition;
	}
}
