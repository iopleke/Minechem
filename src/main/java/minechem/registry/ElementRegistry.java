package minechem.registry;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import minechem.chemical.Element;

public class ElementRegistry
{
    private Map<Integer, Element> atomicElementMap;
    private Map<String, Element> abbrElementMap;
    private Map<String, Element> nameElementMap;
    private static ElementRegistry instance;

    /**
     * Get the registry
     *
     * @return
     */
    public static ElementRegistry getInstance()
    {
        if (instance == null)
        {
            instance = new ElementRegistry();
        }
        return instance;
    }

    private ElementRegistry()
    {
        atomicElementMap = new TreeMap<Integer, Element>();
        abbrElementMap = new TreeMap<String, Element>();
        nameElementMap = new TreeMap<String, Element>();
    }

    /**
     * Register an element
     *
     * @param element
     */
    public void registerElement(Element element)
    {
        atomicElementMap.put(element.atomicNumber, element);
        abbrElementMap.put(element.shortName, element);
        nameElementMap.put(element.fullName.toLowerCase(), element);
        element.log();// TODO: make this debug only later
    }

    /**
     * Register an element using construction inputs
     *
     * @param atomicNumber the element's atomic number and proton count
     * @param fullName     the full name, eg "Gold"
     * @param shortName    the abbreviation, eg "Au"
     * @param form         solid, liquid, gas, or plasma
     * @param type         alkaliMetal, alkalineEarth, transitionMetal, basicMetal, semiMetal, nonMetal, halogen, nobleGas, lanthanide or actinide
     * @param neutrons     the number of neutrons in the element's nucleus
     */
    public void registerElement(int atomicNumber, String fullName, String shortName, String form, String type, int neutrons)
    {
        registerElement(new Element(atomicNumber, fullName, shortName, form, type, neutrons));
    }

    /**
     * Get an element by atomicNumber
     *
     * @param atomicNumber
     * @return can return null
     */
    public Element getElement(int atomicNumber)
    {
        return atomicElementMap.get(atomicNumber);
    }

    /**
     * Get an element by abbreviation
     *
     * @param abbr the abbreviation for the Element
     * @return can return null
     */
    public Element getElement(String abbr)
    {
        if (abbr == null) return null;
        return abbrElementMap.get(abbr);
    }

    /**
     * Get an element by full name
     *
     * @param fullName the full name for the Element
     * @return can return null
     */
    public Element getElementByName(String fullName)
    {
        if (fullName == null) return null;
        return nameElementMap.get(fullName.toLowerCase());
    }

    public Collection<Element> getElements()
    {
        return atomicElementMap.values();
    }
}
