package minechem.item.element;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import minechem.fluid.FluidHelper;
import minechem.item.ChemicalRoomStateEnum;
import static minechem.item.ChemicalRoomStateEnum.gas;
import static minechem.item.ChemicalRoomStateEnum.liquid;
import static minechem.item.ChemicalRoomStateEnum.solid;
import minechem.item.MinechemChemicalType;
import static minechem.item.element.ElementClassificationEnum.actinide;
import static minechem.item.element.ElementClassificationEnum.alkaliMetal;
import static minechem.item.element.ElementClassificationEnum.alkalineEarthMetal;
import static minechem.item.element.ElementClassificationEnum.halogen;
import static minechem.item.element.ElementClassificationEnum.inertGas;
import static minechem.item.element.ElementClassificationEnum.lanthanide;
import static minechem.item.element.ElementClassificationEnum.nonmetal;
import static minechem.item.element.ElementClassificationEnum.otherMetal;
import static minechem.item.element.ElementClassificationEnum.semimetallic;
import static minechem.item.element.ElementClassificationEnum.transitionMetal;
import minechem.radiation.RadiationEnum;
import static minechem.radiation.RadiationEnum.extremelyRadioactive;
import static minechem.radiation.RadiationEnum.hardlyRadioactive;
import static minechem.radiation.RadiationEnum.highlyRadioactive;
import static minechem.radiation.RadiationEnum.radioactive;
import static minechem.radiation.RadiationEnum.slightlyRadioactive;
import static minechem.radiation.RadiationEnum.stable;
import minechem.utils.MinechemUtil;

public class ElementEnum extends MinechemChemicalType
{

    public static int heaviestMass = 0;
    public static Map<Integer, ElementEnum> elements = new Hashtable<Integer, ElementEnum>();
    public static Map<String, ElementEnum> nameToElements = new HashMap<String, ElementEnum>();

    public static final ElementEnum H = addElement(1, "H", "Hydrogen", nonmetal, gas, stable);//Done
    public static final ElementEnum He = addElement(2, "He", "Helium", inertGas, gas, stable);//Done
    public static final ElementEnum Li = addElement(3, "Li", "Lithium", alkaliMetal, solid, stable);//Done
    public static final ElementEnum Be = addElement(4, "Be", "Beryllium", alkalineEarthMetal, solid, stable);//Done
    public static final ElementEnum B = addElement(5, "B", "Boron", semimetallic, solid, stable);//Done
    public static final ElementEnum C = addElement(6, "C", "Carbon", nonmetal, solid, stable);//Done
    public static final ElementEnum N = addElement(7, "N", "Nitrogen", nonmetal, gas, stable);//Done
    public static final ElementEnum O = addElement(8, "O", "Oxygen", nonmetal, gas, stable);//Done
    public static final ElementEnum F = addElement(9, "F", "Fluorine", halogen, gas, stable);//Done
    public static final ElementEnum Ne = addElement(10, "Ne", "Neon", inertGas, gas, stable);//Done
    public static final ElementEnum Na = addElement(11, "Na", "Sodium", alkaliMetal, solid, stable);//Done
    public static final ElementEnum Mg = addElement(12, "Mg", "Magnesium", alkalineEarthMetal, solid, stable);//Done
    public static final ElementEnum Al = addElement(13, "Al", "Aluminium", otherMetal, solid, stable);//Alloy
    public static final ElementEnum Si = addElement(14, "Si", "Silicon", otherMetal, solid, stable);//Done
    public static final ElementEnum P = addElement(15, "P", "Phosphorus", nonmetal, solid, stable);//Done
    public static final ElementEnum S = addElement(16, "S", "Sulfur", nonmetal, solid, stable);//Done
    public static final ElementEnum Cl = addElement(17, "Cl", "Chlorine", halogen, gas, stable);//Done
    public static final ElementEnum Ar = addElement(18, "Ar", "Argon", inertGas, gas, stable);//Done
    public static final ElementEnum K = addElement(19, "K", "Potassium", alkaliMetal, solid, stable);
    public static final ElementEnum Ca = addElement(20, "Ca", "Calcium", alkalineEarthMetal, solid, stable);//Done
    public static final ElementEnum Sc = addElement(21, "Sc", "Scandium", transitionMetal, solid, stable);//Alloy
    public static final ElementEnum Ti = addElement(22, "Ti", "Titanium", transitionMetal, solid, stable);//Done
    public static final ElementEnum V = addElement(23, "V", "Vanadium", transitionMetal, solid, stable);//Alloy
    public static final ElementEnum Cr = addElement(24, "Cr", "Chromium", transitionMetal, solid, stable);//Done
    public static final ElementEnum Mn = addElement(25, "Mn", "Manganese", transitionMetal, solid, stable);//Alloy
    public static final ElementEnum Fe = addElement(26, "Fe", "Iron", transitionMetal, solid, stable);//Done
    public static final ElementEnum Co = addElement(27, "Co", "Cobalt", transitionMetal, solid, stable);//Alloy
    public static final ElementEnum Ni = addElement(28, "Ni", "Nickel", transitionMetal, solid, stable);//Done
    public static final ElementEnum Cu = addElement(29, "Cu", "Copper", transitionMetal, solid, stable);
    public static final ElementEnum Zn = addElement(30, "Zn", "Zinc", transitionMetal, solid, stable);
    public static final ElementEnum Ga = addElement(31, "Ga", "Gallium", otherMetal, solid, stable);//Alloy
    public static final ElementEnum Ge = addElement(32, "Ge", "Germanium", semimetallic, solid, stable);
    public static final ElementEnum As = addElement(33, "As", "Arsenic", semimetallic, solid, stable);
    public static final ElementEnum Se = addElement(34, "Se", "Selenium", nonmetal, solid, stable);//Alloy
    public static final ElementEnum Br = addElement(35, "Br", "Bromine", halogen, liquid, stable);//Done
    public static final ElementEnum Kr = addElement(36, "Kr", "Krypton", inertGas, gas, stable);//Done
    public static final ElementEnum Rb = addElement(37, "Rb", "Rubidium", alkaliMetal, solid, stable);//Done
    public static final ElementEnum Sr = addElement(38, "Sr", "Strontium", alkalineEarthMetal, solid, stable);//Alloy
    public static final ElementEnum Y = addElement(39, "Y", "Yttrium", transitionMetal, solid, stable);//Alloy
    public static final ElementEnum Zr = addElement(40, "Zr", "Zirconium", transitionMetal, solid, stable);//Done
    public static final ElementEnum Nb = addElement(41, "Nb", "Niobium", transitionMetal, solid, stable);//Alloy
    public static final ElementEnum Mo = addElement(42, "Mo", "Molybdenum", transitionMetal, solid, stable);//Alloy
    public static final ElementEnum Tc = addElement(43, "Tc", "Technetium", transitionMetal, solid, hardlyRadioactive);
    public static final ElementEnum Ru = addElement(44, "Ru", "Ruthenium", transitionMetal, solid, stable);//Alloy
    public static final ElementEnum Rh = addElement(45, "Rh", "Rhodium", transitionMetal, solid, stable);//Alloy
    public static final ElementEnum Pd = addElement(46, "Pd", "Palladium", transitionMetal, solid, stable);//Alloy
    public static final ElementEnum Ag = addElement(47, "Ag", "Silver", transitionMetal, solid, stable);//Done
    public static final ElementEnum Cd = addElement(48, "Cd", "Cadmium", transitionMetal, solid, stable);//Alloy
    public static final ElementEnum In = addElement(49, "In", "Indium", otherMetal, solid, stable);
    public static final ElementEnum Sn = addElement(50, "Sn", "Tin", otherMetal, solid, stable);
    public static final ElementEnum Sb = addElement(51, "Sb", "Antimony", semimetallic, solid, stable);
    public static final ElementEnum Te = addElement(52, "Te", "Tellurium", semimetallic, solid, stable);
    public static final ElementEnum I = addElement(53, "I", "Iodine", halogen, solid, stable);
    public static final ElementEnum Xe = addElement(54, "Xe", "Xenon", inertGas, gas, stable);
    public static final ElementEnum Cs = addElement(55, "Cs", "Caesium", alkaliMetal, solid, stable);//Done
    public static final ElementEnum Ba = addElement(56, "Ba", "Barium", alkalineEarthMetal, solid, stable);
    public static final ElementEnum La = addElement(57, "La", "Lanthanum", lanthanide, solid, stable);
    public static final ElementEnum Ce = addElement(58, "Ce", "Cerium", lanthanide, solid, stable);
    public static final ElementEnum Pr = addElement(59, "Pr", "Praseodymium", lanthanide, solid, stable);
    public static final ElementEnum Nd = addElement(60, "Nd", "Neodymium", lanthanide, solid, stable);
    public static final ElementEnum Pm = addElement(61, "Pm", "Promethium", lanthanide, solid, radioactive);
    public static final ElementEnum Sm = addElement(62, "Sm", "Samarium", lanthanide, solid, stable);
    public static final ElementEnum Eu = addElement(63, "Eu", "Europium", lanthanide, solid, stable);
    public static final ElementEnum Gd = addElement(64, "Gd", "Gadolinium", lanthanide, solid, stable);
    public static final ElementEnum Tb = addElement(65, "Tb", "Terbium", lanthanide, solid, stable);
    public static final ElementEnum Dy = addElement(66, "Dy", "Dyprosium", lanthanide, solid, stable);
    public static final ElementEnum Ho = addElement(67, "Ho", "Holmium", lanthanide, solid, stable);
    public static final ElementEnum Er = addElement(68, "Er", "Erbium", lanthanide, solid, stable);
    public static final ElementEnum Tm = addElement(69, "Tm", "Thulium", lanthanide, solid, stable);
    public static final ElementEnum Yb = addElement(70, "Yb", "Ytterbium", lanthanide, solid, stable);
    public static final ElementEnum Lu = addElement(71, "Lu", "Lutetium", lanthanide, solid, stable);
    public static final ElementEnum Hf = addElement(72, "Hf", "Hafnium", transitionMetal, solid, stable);
    public static final ElementEnum Ta = addElement(73, "Ta", "Tantalum", transitionMetal, solid, stable);
    public static final ElementEnum W = addElement(74, "W", "Tungsten", transitionMetal, solid, stable);
    public static final ElementEnum Re = addElement(75, "Re", "Rhenium", transitionMetal, solid, stable);
    public static final ElementEnum Os = addElement(76, "Os", "Osmium", transitionMetal, solid, stable);
    public static final ElementEnum Ir = addElement(77, "Ir", "Iridium", transitionMetal, solid, stable);
    public static final ElementEnum Pt = addElement(78, "Pt", "Platinum", transitionMetal, solid, stable);//Done
    public static final ElementEnum Au = addElement(79, "Au", "Gold", transitionMetal, solid, stable);//Done
    public static final ElementEnum Hg = addElement(80, "Hg", "Mercury", transitionMetal, liquid, stable);//Done
    public static final ElementEnum Tl = addElement(81, "Tl", "Thallium", otherMetal, solid, stable);
    public static final ElementEnum Pb = addElement(82, "Pb", "Lead", otherMetal, solid, stable);//Done
    public static final ElementEnum Bi = addElement(83, "Bi", "Bismuth", otherMetal, solid, hardlyRadioactive);
    public static final ElementEnum Po = addElement(84, "Po", "Polonium", semimetallic, solid, radioactive);
    public static final ElementEnum At = addElement(85, "At", "Astantine", halogen, solid, highlyRadioactive);
    public static final ElementEnum Rn = addElement(86, "Rn", "Radon", inertGas, gas, radioactive);
    public static final ElementEnum Fr = addElement(87, "Fr", "Francium", alkaliMetal, solid, highlyRadioactive);//Done
    public static final ElementEnum Ra = addElement(88, "Ra", "Radium", alkalineEarthMetal, solid, slightlyRadioactive);
    public static final ElementEnum Ac = addElement(89, "Ac", "Actinium", actinide, solid, radioactive);
    public static final ElementEnum Th = addElement(90, "Th", "Thorium", actinide, solid, hardlyRadioactive);
    public static final ElementEnum Pa = addElement(91, "Pa", "Protactinium", actinide, solid, slightlyRadioactive);
    public static final ElementEnum U = addElement(92, "U", "Uranium", actinide, solid, hardlyRadioactive);//Done
    public static final ElementEnum Np = addElement(93, "Np", "Neptunium", actinide, solid, hardlyRadioactive);
    public static final ElementEnum Pu = addElement(94, "Pu", "Plutonium", actinide, solid, hardlyRadioactive);
    public static final ElementEnum Am = addElement(95, "Am", "Americium", actinide, solid, slightlyRadioactive);
    public static final ElementEnum Cm = addElement(96, "Cm", "Curium", actinide, solid, hardlyRadioactive);
    public static final ElementEnum Bk = addElement(97, "Bk", "Berkelium", actinide, solid, slightlyRadioactive);
    public static final ElementEnum Cf = addElement(98, "Cf", "Californium", actinide, solid, slightlyRadioactive);
    public static final ElementEnum Es = addElement(99, "Es", "Einsteinium", actinide, solid, radioactive);
    public static final ElementEnum Fm = addElement(100, "Fm", "Fermium", actinide, solid, radioactive);
    public static final ElementEnum Md = addElement(101, "Md", "Mendelenium", actinide, solid, radioactive);
    public static final ElementEnum No = addElement(102, "No", "Nobelium", actinide, solid, highlyRadioactive);
    public static final ElementEnum Lr = addElement(103, "Lr", "Lawrencium", actinide, solid, highlyRadioactive);
    public static final ElementEnum Rf = addElement(104, "Rf", "Rutherfordium", transitionMetal, solid, highlyRadioactive);
    public static final ElementEnum Db = addElement(105, "Db", "Dubnium", transitionMetal, solid, radioactive);
    public static final ElementEnum Sg = addElement(106, "Sg", "Seaborgium", transitionMetal, solid, highlyRadioactive);
    public static final ElementEnum Bh = addElement(107, "Bh", "Bohrium", transitionMetal, solid, highlyRadioactive);
    public static final ElementEnum Hs = addElement(108, "Hs", "Hassium", transitionMetal, solid, highlyRadioactive);
    public static final ElementEnum Mt = addElement(109, "Mt", "Meitnerium", transitionMetal, solid, extremelyRadioactive);
    public static final ElementEnum Ds = addElement(110, "Ds", "Darmstadtium", transitionMetal, solid, extremelyRadioactive);
    public static final ElementEnum Rg = addElement(111, "Rg", "Roenthenium", transitionMetal, solid, extremelyRadioactive);
    public static final ElementEnum Cn = addElement(112, "Cn", "Copernicium", transitionMetal, solid, extremelyRadioactive);
    public static final ElementEnum Uut = addElement(113, "Uut", "Ununtrium", transitionMetal, solid, extremelyRadioactive);

    // Descriptive name, in en_US. Should not be used; instead, use a
    // localized string from a .properties file.
    private final String descriptiveName;

    // Localization key.
    private final String localizationKey;
    private final ElementClassificationEnum classification;

    private final String name;
    private final int id;

    public ElementEnum(int id, String name, String descriptiveName, ElementClassificationEnum classification, ChemicalRoomStateEnum roomState, RadiationEnum radioactivity)
    {
        super(roomState, radioactivity);
        this.id = id;

        this.name = name;
        this.descriptiveName = MinechemUtil.capitalizeFully(descriptiveName);
        this.localizationKey = "element." + name;
        this.classification = classification;

    }

    public static ElementEnum addElement(int id, String name, String descriptiveName, ElementClassificationEnum classification, ChemicalRoomStateEnum roomState, RadiationEnum radioactivity)
    {
        ElementEnum element = new ElementEnum(id, name, descriptiveName, classification, roomState, radioactivity);
        registerElement(element);
        FluidHelper.registerElement(element);
        return element;
    }

    public static ElementEnum addElement(ElementEnum element)
    {
        registerElement(element);
        FluidHelper.registerElement(element);
        return element;
    }

    public static void registerElement(ElementEnum element)
    {
        if (elements.containsKey(element.id))
        {
            throw new IllegalArgumentException("id " + element.id + " is used");
        }
        if (nameToElements.containsKey(element.name()))
        {
            throw new IllegalArgumentException("name" + element.name() + " is used");
        }
        if (element.id >= heaviestMass)
        {
            heaviestMass = element.id;
        }
        elements.put(element.id, element);
        nameToElements.put(element.name(), element);
    }

    public static void unregisterElement(ElementEnum element)
    {
        elements.remove(element.id);
        nameToElements.remove(element.name());
    }

    public ElementClassificationEnum classification()
    {
        return classification;
    }

    public int atomicNumber()
    {
        return id;
    }

    public String name()
    {
        return name;
    }

    @Override
    public String toString()
    {
        return name();
    }

    @Override
    public String getUnlocalizedName()
    {
        return localizationKey;
    }

    public String getLongName()
    {
        return descriptiveName;
    }

    public static ElementEnum getByID(int atomicNumber)
    {
        return elements.get(atomicNumber);
    }

    public static ElementEnum getByName(String name)
    {
        return nameToElements.get(name);
    }

    public static void init()
    {

    }
}
