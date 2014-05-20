package minechem.item.element;

import minechem.utils.Chemical;

public class Element extends Chemical
{

    public EnumElement element;

    public Element(EnumElement element, int amount)
    {
        super(amount);
        this.element = element;
    }

    @Override
    public Chemical copy()
    {
        return new Element(element, amount);
    }

    ;

    public Element(EnumElement element)
    {
        super(1);
        this.element = element;
    }

    @Override
    public boolean sameAs(Chemical chemical)
    {
        return chemical instanceof Element && ((Element) chemical).element == element;
    }

}
