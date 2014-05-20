package minechem.oredictionary;

import minechem.item.element.Element;
import minechem.item.element.EnumElement;
import minechem.item.molecule.EnumMolecule;
import minechem.item.molecule.Molecule;
import minechem.potion.Chemical;

public enum EnumOre {
    iron("Copper", new Element(EnumElement.Fe, 16)),
    gold("Tin", new Element(EnumElement.Au, 16)),
    copper("Copper", new Element(EnumElement.Cu, 16)),
    tin("Tin", new Element(EnumElement.Sn, 16)),
    silver("Silver", new Element(EnumElement.Ag, 16)),
    aluminum("Aluminum", new Element(EnumElement.Al, 16)),
    titanium("Titanium", new Element(EnumElement.Ti, 16)),
    chrome("Chrome", new Element(EnumElement.Al, 16)),
    tungsten("Tungsten", new Element(EnumElement.W, 16)),
    lead("Lead", new Element(EnumElement.Pb, 16)),
    zinc("Zinc", new Element(EnumElement.Zn, 16)),
    platinum("Platinum", new Element(EnumElement.Pt, 16)),
    thorium("Thorium", new Element(EnumElement.Th, 16)),
    nickel("Nickel", new Element(EnumElement.Ni, 16)),
    osmium("Osmium", new Element(EnumElement.Os, 16)),
    antimony("Antimony", new Element(EnumElement.Sb, 16)),
    iridium("Iridium", new Element(EnumElement.Ir, 16)),
    uranium("Uranium", new Element(EnumElement.U, 16)),
    plutonium("Plutonium", new Element(EnumElement.Pu, 16)),
    manganese("Manganese", new Element(EnumElement.Mn, 16)),

        phosphorus("Phosphorus", new Element(EnumElement.P, 16)),

        bitumen("Bitumen", new Molecule(EnumMolecule.propane, 16)),

        prometheum("Prometheum", new Element(EnumElement.Pm, 16)),

        amethyst("Amethyst", new Molecule(EnumMolecule.siliconDioxide, 16)),

        peridot("Peridot", new Molecule(EnumMolecule.peridot, 16)),

        topaz("Topaz", new Molecule(EnumMolecule.topaz, 16)),

        tanzanite("Tanzanite", new Molecule(EnumMolecule.zoisite, 16)),
        potash("Potash", new Molecule(EnumMolecule.potassiumNitrate, 16)),
        sulfur("Sulfur", new Element(EnumElement.S, 16)),
        saphire("Sapphire", new Molecule(EnumMolecule.aluminiumOxide, 1)),
    ruby("Ruby", new Molecule(EnumMolecule.aluminiumOxide, 1), new Element(EnumElement.Cr, 1)),

    cobalt("Cobalt", new Element(EnumElement.Co, 16)),

        ardite("Ardite", new Element(EnumElement.Ar, 16));

        private String name;
    private Chemical[] composition;

    public String getName() {
        return this.name;
    }

    public Chemical[] getComposition() {
        return this.composition;
    }

    EnumOre(String name, Chemical... composition) {
        this.name = name;
        this.composition = composition;
    }
}