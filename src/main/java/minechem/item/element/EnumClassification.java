package minechem.item.element;

public enum EnumClassification
{
    nonmetal("Non-metal"), inertGas("Inert gas"), halogen("Halogen"), alkaliMetal("Alkali metal"), alkalineEarthMetal("Alkaline earth metal"), semimetallic("Metalloid"), // Yes this is the proper name!
    otherMetal("Other metal"), transitionMetal("Transition metal"), lanthanide("Lanthanide"), actinide("Actinide"), solid("Solid"), gas("Gaseous"), liquid("Liquid");

    private final String descriptiveName;

    EnumClassification(String descriptiveName)
    {
        this.descriptiveName = descriptiveName;
    }

    public String descriptiveName()
    {
        return descriptiveName;
    }
}
