package minechem.item.element;

import minechem.potion.PotionChemical;

public class Element extends PotionChemical
{

    public ElementEnum element;

    public Element(ElementEnum element, int amount)
    {
        super(amount);
        this.element = element;
    }

    @Override
    public PotionChemical copy()
    {
        return new Element(element, amount);
    }

    public Element(ElementEnum element)
    {
        super(1);
        this.element = element;
    }

    @Override
    public boolean sameAs(PotionChemical potionChemical)
    {
        return potionChemical instanceof Element && ((Element) potionChemical).element == element;
    }

}
