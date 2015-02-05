package minechem.registry;

import minechem.element.Element;

import java.util.Map;
import java.util.TreeMap;

public class ElementRegistry
{
    private Map<Integer, Element> elementMap;
    private static ElementRegistry instance;

    /**
     * Get the registry
     * @return
     */
    public static ElementRegistry getInstance()
    {
        if (instance == null) instance = new ElementRegistry();
        return instance;
    }
    
    private ElementRegistry()
    {
        elementMap = new TreeMap<Integer, Element>();
    }

    /**
     * Register an element
     * @param element
     */
    public void registerElement(Element element)
    {
        elementMap.put(element.atomicNumber, element);
        element.log();
    }

    /**
     * Register an element using construction inputs
     * @param atomicNumber
     * @param fullName
     * @param shortName
     * @param form
     * @param neutrons
     */
    public void registerElement(int atomicNumber, String fullName, String shortName, String form, int neutrons)
    {
        registerElement(new Element(atomicNumber, fullName, shortName, form, neutrons));
    }

    /**
     * Get an element by atomicNumber
     * @param atomicNumber
     * @return can return null
     */
    public Element getElement(int atomicNumber)
    {
        return elementMap.get(atomicNumber);
    }
}
