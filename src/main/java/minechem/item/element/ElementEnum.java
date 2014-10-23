package minechem.item.element;

import static minechem.item.ChemicalRoomStateEnum.gas;
import static minechem.item.ChemicalRoomStateEnum.liquid;
import static minechem.item.ChemicalRoomStateEnum.solid;
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
import static minechem.radiation.RadiationEnum.extremelyRadioactive;
import static minechem.radiation.RadiationEnum.hardlyRadioactive;
import static minechem.radiation.RadiationEnum.highlyRadioactive;
import static minechem.radiation.RadiationEnum.radioactive;
import static minechem.radiation.RadiationEnum.slightlyRadioactive;
import static minechem.radiation.RadiationEnum.stable;

import java.util.Hashtable;
import java.util.Map;

import cpw.mods.fml.relauncher.Side;
import minechem.fluid.FluidHelper;
import minechem.item.ChemicalRoomStateEnum;
import minechem.item.MinechemChemicalType;
import minechem.radiation.RadiationEnum;
import minechem.utils.MinechemHelper;

public class ElementEnum extends MinechemChemicalType
{

	public static int heaviestMass = 0;
	//public static ElementEnum[] elements = new ElementEnum[heaviestMass];
	public static Map<Integer,ElementEnum> elements = new Hashtable<Integer,ElementEnum>();

	public static final ElementEnum H = addElement(0, "H", "Hydrogen", nonmetal, gas, stable);//Done
	public static final ElementEnum He = addElement(1, "He", "Helium", inertGas, gas, stable);//Done
	public static final ElementEnum Li = addElement(2, "Li", "Lithium", alkaliMetal, solid, stable);//Done
	public static final ElementEnum Be = addElement(3, "Be", "Beryllium", alkalineEarthMetal, solid, stable);//Done
	public static final ElementEnum B = addElement(4, "B", "Boron", semimetallic, solid, stable);//Done
	public static final ElementEnum C = addElement(5, "C", "Carbon", nonmetal, solid, stable);//Done
	public static final ElementEnum N = addElement(6, "N", "Nitrogen", nonmetal, gas, stable);//Done
	public static final ElementEnum O = addElement(7, "O", "Oxygen", nonmetal, gas, stable);//Done
	public static final ElementEnum F = addElement(8, "F", "Fluorine", halogen, gas, stable);//Done
	public static final ElementEnum Ne = addElement(9, "Ne", "Neon", inertGas, gas, stable);//Done
	public static final ElementEnum Na = addElement(10, "Na", "Sodium", alkaliMetal, solid, stable);//Done
	public static final ElementEnum Mg = addElement(11, "Mg", "Magnesium", alkalineEarthMetal, solid, stable);//Done
	public static final ElementEnum Al = addElement(12, "Al", "Aluminium", otherMetal, solid, stable);//Alloy
	public static final ElementEnum Si = addElement(13, "Si", "Silicon", otherMetal, solid, stable);//Done
	public static final ElementEnum P = addElement(14, "P", "Phosphorus", nonmetal, solid, stable);//Done
	public static final ElementEnum S = addElement(15, "S", "Sulfur", nonmetal, solid, stable);//Done
	public static final ElementEnum Cl = addElement(16, "Cl", "Chlorine", halogen, gas, stable);//Done
	public static final ElementEnum Ar = addElement(17, "Ar", "Argon", inertGas, gas, stable);//Done
	public static final ElementEnum K = addElement(18, "K", "Potassium", alkaliMetal, solid, stable);
	public static final ElementEnum Ca = addElement(19, "Ca", "Calcium", alkalineEarthMetal, solid, stable);//Done
	public static final ElementEnum Sc = addElement(20, "Sc", "Scandium", transitionMetal, solid, stable);//Alloy
	public static final ElementEnum Ti = addElement(21, "Ti", "Titanium", transitionMetal, solid, stable);//Done
	public static final ElementEnum V = addElement(22, "V", "Vanadium", transitionMetal, solid, stable);//Alloy
	public static final ElementEnum Cr = addElement(23, "Cr", "Chromium", transitionMetal, solid, stable);//Done
	public static final ElementEnum Mn = addElement(24, "Mn", "Manganese", transitionMetal, solid, stable);//Alloy
	public static final ElementEnum Fe = addElement(25, "Fe", "Iron", transitionMetal, solid, stable);//Done
	public static final ElementEnum Co = addElement(26, "Co", "Cobalt", transitionMetal, solid, stable);//Alloy
	public static final ElementEnum Ni = addElement(27, "Ni", "Nickel", transitionMetal, solid, stable);//Done
	public static final ElementEnum Cu = addElement(28, "Cu", "Copper", transitionMetal, solid, stable);
	public static final ElementEnum Zn = addElement(29, "Zn", "Zinc", transitionMetal, solid, stable);
	public static final ElementEnum Ga = addElement(30, "Ga", "Gallium", otherMetal, solid, stable);//Alloy
	public static final ElementEnum Ge = addElement(31, "Ge", "Germanium", semimetallic, solid, stable);
	public static final ElementEnum As = addElement(32, "As", "Arsenic", semimetallic, solid, stable);
	public static final ElementEnum Se = addElement(33, "Se", "Selenium", nonmetal, solid, stable);//Alloy
	public static final ElementEnum Br = addElement(34, "Br", "Bromine", halogen, liquid, stable);//Done
	public static final ElementEnum Kr = addElement(35, "Kr", "Krypton", inertGas, gas, stable);//Done
	public static final ElementEnum Rb = addElement(36, "Rb", "Rubidium", alkaliMetal, solid, stable);//Done
	public static final ElementEnum Sr = addElement(37, "Sr", "Strontium", alkalineEarthMetal, solid, stable);//Alloy
	public static final ElementEnum Y = addElement(38, "Y", "Yttrium", transitionMetal, solid, stable);//Alloy
	public static final ElementEnum Zr = addElement(39, "Zr", "Zirconium", transitionMetal, solid, stable);//Done
	public static final ElementEnum Nb = addElement(40, "Nb", "Niobium", transitionMetal, solid, stable);//Alloy
	public static final ElementEnum Mo = addElement(41, "Mo", "Molybdenum", transitionMetal, solid, stable);//Alloy
	public static final ElementEnum Tc = addElement(42, "Tc", "Technetium", transitionMetal, solid, hardlyRadioactive);
	public static final ElementEnum Ru = addElement(43, "Ru", "Ruthenium", transitionMetal, solid, stable);//Alloy
	public static final ElementEnum Rh = addElement(44, "Rh", "Rhodium", transitionMetal, solid, stable);//Alloy
	public static final ElementEnum Pd = addElement(45, "Pd", "Palladium", transitionMetal, solid, stable);//Alloy
	public static final ElementEnum Ag = addElement(46, "Ag", "Silver", transitionMetal, solid, stable);//Done
	public static final ElementEnum Cd = addElement(47, "Cd", "Cadmium", transitionMetal, solid, stable);//Alloy
	public static final ElementEnum In = addElement(48, "In", "Indium", otherMetal, solid, stable);
	public static final ElementEnum Sn = addElement(49, "Sn", "Tin", otherMetal, solid, stable);
	public static final ElementEnum Sb = addElement(50, "Sb", "Antimony", semimetallic, solid, stable);
	public static final ElementEnum Te = addElement(51, "Te", "Tellurium", semimetallic, solid, stable);
	public static final ElementEnum I = addElement(52, "I", "Iodine", halogen, solid, stable);
	public static final ElementEnum Xe = addElement(53, "Xe", "Xenon", inertGas, gas, stable);
	public static final ElementEnum Cs = addElement(54, "Cs", "Caesium", alkaliMetal, solid, stable);//Done
	public static final ElementEnum Ba = addElement(55, "Ba", "Barium", alkalineEarthMetal, solid, stable);
	public static final ElementEnum La = addElement(56, "La", "Lanthanum", lanthanide, solid, stable);
	public static final ElementEnum Ce = addElement(57, "Ce", "Cerium", lanthanide, solid, stable);
	public static final ElementEnum Pr = addElement(58, "Pr", "Praseodymium", lanthanide, solid, stable);
	public static final ElementEnum Nd = addElement(59, "Nd", "Neodymium", lanthanide, solid, stable);
	public static final ElementEnum Pm = addElement(60, "Pm", "Promethium", lanthanide, solid, radioactive);
	public static final ElementEnum Sm = addElement(61, "Sm", "Samarium", lanthanide, solid, stable);
	public static final ElementEnum Eu = addElement(62, "Eu", "Europium", lanthanide, solid, stable);
	public static final ElementEnum Gd = addElement(63, "Gd", "Gadolinium", lanthanide, solid, stable);
	public static final ElementEnum Tb = addElement(64, "Tb", "Terbium", lanthanide, solid, stable);
	public static final ElementEnum Dy = addElement(65, "Dy", "Dyprosium", lanthanide, solid, stable);
	public static final ElementEnum Ho = addElement(66, "Ho", "Holmium", lanthanide, solid, stable);
	public static final ElementEnum Er = addElement(67, "Er", "Erbium", lanthanide, solid, stable);
	public static final ElementEnum Tm = addElement(68, "Tm", "Thulium", lanthanide, solid, stable);
	public static final ElementEnum Yb = addElement(69, "Yb", "Ytterbium", lanthanide, solid, stable);
	public static final ElementEnum Lu = addElement(70, "Lu", "Lutetium", lanthanide, solid, stable);
	public static final ElementEnum Hf = addElement(71, "Hf", "Hafnium", transitionMetal, solid, stable);
	public static final ElementEnum Ta = addElement(72, "Ta", "Tantalum", transitionMetal, solid, stable);
	public static final ElementEnum W = addElement(73, "W", "Tungsten", transitionMetal, solid, stable);
	public static final ElementEnum Re = addElement(74, "Re", "Rhenium", transitionMetal, solid, stable);
	public static final ElementEnum Os = addElement(75, "Os", "Osmium", transitionMetal, solid, stable);
	public static final ElementEnum Ir = addElement(76, "Ir", "Iridium", transitionMetal, solid, stable);
	public static final ElementEnum Pt = addElement(77, "Pt", "Platinum", transitionMetal, solid, stable);//Done
	public static final ElementEnum Au = addElement(78, "Au", "Gold", transitionMetal, solid, stable);//Done
	public static final ElementEnum Hg = addElement(79, "Hg", "Mercury", transitionMetal, liquid, stable);//Done
	public static final ElementEnum Tl = addElement(80, "Tl", "Thallium", otherMetal, solid, stable);
	public static final ElementEnum Pb = addElement(81, "Pb", "Lead", otherMetal, solid, stable);//Done
	public static final ElementEnum Bi = addElement(82, "Bi", "Bismuth", otherMetal, solid, hardlyRadioactive);
	public static final ElementEnum Po = addElement(83, "Po", "Polonium", semimetallic, solid, radioactive);
	public static final ElementEnum At = addElement(84, "At", "Astantine", halogen, solid, highlyRadioactive);
	public static final ElementEnum Rn = addElement(85, "Rn", "Radon", inertGas, gas, radioactive);
	public static final ElementEnum Fr = addElement(86, "Fr", "Francium", alkaliMetal, solid, highlyRadioactive);//Done
	public static final ElementEnum Ra = addElement(87, "Ra", "Radium", alkalineEarthMetal, solid, slightlyRadioactive);
	public static final ElementEnum Ac = addElement(88, "Ac", "Actinium", actinide, solid, radioactive);
	public static final ElementEnum Th = addElement(89, "Th", "Thorium", actinide, solid, hardlyRadioactive);
	public static final ElementEnum Pa = addElement(90, "Pa", "Protactinium", actinide, solid, slightlyRadioactive);
	public static final ElementEnum U = addElement(91, "U", "Uranium", actinide, solid, hardlyRadioactive);//Done
	public static final ElementEnum Np = addElement(92, "Np", "Neptunium", actinide, solid, hardlyRadioactive);
	public static final ElementEnum Pu = addElement(93, "Pu", "Plutonium", actinide, solid, hardlyRadioactive);
	public static final ElementEnum Am = addElement(94, "Am", "Americium", actinide, solid, slightlyRadioactive);
	public static final ElementEnum Cm = addElement(95, "Cm", "Curium", actinide, solid, hardlyRadioactive);
	public static final ElementEnum Bk = addElement(96, "Bk", "Berkelium", actinide, solid, slightlyRadioactive);
	public static final ElementEnum Cf = addElement(97, "Cf", "Californium", actinide, solid, slightlyRadioactive);
	public static final ElementEnum Es = addElement(98, "Es", "Einsteinium", actinide, solid, radioactive);
	public static final ElementEnum Fm = addElement(99, "Fm", "Fermium", actinide, solid, radioactive);
	public static final ElementEnum Md = addElement(100, "Md", "Mendelenium", actinide, solid, radioactive);
	public static final ElementEnum No = addElement(101, "No", "Nobelium", actinide, solid, highlyRadioactive);
	public static final ElementEnum Lr = addElement(102, "Lr", "Lawrencium", actinide, solid, highlyRadioactive);
	public static final ElementEnum Rf = addElement(103, "Rf", "Rutherfordium", transitionMetal, solid, highlyRadioactive);
	public static final ElementEnum Db = addElement(104, "Db", "Dubnium", transitionMetal, solid, radioactive);
	public static final ElementEnum Sg = addElement(105, "Sg", "Seaborgium", transitionMetal, solid, highlyRadioactive);
	public static final ElementEnum Bh = addElement(106, "Bh", "Bohrium", transitionMetal, solid, highlyRadioactive);
	public static final ElementEnum Hs = addElement(107, "Hs", "Hassium", transitionMetal, solid, highlyRadioactive);
	public static final ElementEnum Mt = addElement(108, "Mt", "Meitnerium", transitionMetal, solid, extremelyRadioactive);
	public static final ElementEnum Ds = addElement(109, "Ds", "Darmstadtium", transitionMetal, solid, extremelyRadioactive);
	public static final ElementEnum Rg = addElement(110, "Rg", "Roenthenium", transitionMetal, solid, extremelyRadioactive);
	public static final ElementEnum Cn = addElement(111, "Cn", "Copernicium", transitionMetal, solid, extremelyRadioactive);
	public static final ElementEnum Uut = addElement(112, "Uut", "Ununtrium", transitionMetal, solid, extremelyRadioactive);

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
		this.descriptiveName = MinechemHelper.capitalizeFully(descriptiveName);
		this.localizationKey = "element." + name;
		this.classification = classification;

	}
	
	public static ElementEnum addElement(int id, String name, String descriptiveName, ElementClassificationEnum classification, ChemicalRoomStateEnum roomState, RadiationEnum radioactivity)
	{
		ElementEnum element = new ElementEnum(id, name,descriptiveName, classification,roomState,radioactivity);
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
		if (elements.get(element.id) != null)
		{
			throw new IllegalArgumentException("id " + element.id + " is used");
		}
		if (element.id>=heaviestMass)heaviestMass=element.id+1;
		elements.put(element.id, element);
	}
	
	public static void unregisterElement(ElementEnum element)
	{
		elements.remove(element.id);
	}
	

	public ElementClassificationEnum classification()
	{
		return classification;
	}

	public int atomicNumber()
	{
		return id + 1;
	}

	public int ordinal()
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

	public static ElementEnum getByID(int atomicNumber) {
		return elements.get(atomicNumber);
	}
}
