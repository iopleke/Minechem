package minechem.registry;

import java.util.Map;
import java.util.TreeMap;
import minechem.chemical.Element;

public class ElementRegistry
{
    private Map<Integer, Element> atomicElementMap;
    private Map<String, Element> abbrElementMap;
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
        element.log();// TODO: make this debug only later
    }

    /**
     * Register an element using construction inputs
     *
     * @param atomicNumber the element's atomic number and proton count
     * @param fullName     the full name, eg "Gold"
     * @param shortName    the abbreviation, eg "Au"
     * @param form         solid, liquid, gas, or plasma
     * @param neutrons     the number of neutrons in the element's nucleus
     */
    public void registerElement(int atomicNumber, String fullName, String shortName, int neutrons, int meltingPoint, int boilingPoint, int temp)
    {
        registerElement(new Element(atomicNumber, fullName, shortName, neutrons, meltingPoint, boilingPoint, temp));
    }
    
    public void registerElement(int atomicNumber, String fullName, String shortName, int neutrons, int meltingPoint, int boilingPoint)
    {
        registerElement(new Element(atomicNumber, fullName, shortName, neutrons, meltingPoint, boilingPoint));
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
        return abbrElementMap.get(abbr);
    }
}
