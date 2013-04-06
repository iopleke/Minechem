package ljdp.minechem.api.core;

public class Element extends Chemical {

    public EnumElement element;

    public Element(EnumElement element, int amount) {
        super(amount);
        this.element = element;
    }

    public Element(EnumElement element) {
        super(1);
        this.element = element;
    }

    @Override
    public boolean sameAs(Chemical chemical) {
        return chemical instanceof Element && ((Element) chemical).element == element;
    }

}
