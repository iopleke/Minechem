package minechem.item.element;

import minechem.fluid.FluidHelper;
import minechem.item.ChemicalRoomStateEnum;
import minechem.item.MinechemChemicalType;
import minechem.radiation.RadiationEnum;
import net.minecraft.util.StatCollector;
import static minechem.item.ChemicalRoomStateEnum.*;
import static minechem.item.element.ElementClassificationEnum.*;
import static minechem.radiation.RadiationEnum.*;

public class ElementEnum extends MinechemChemicalType
{

    public static final int heaviestMass = 113;
    public static final ElementEnum[] elements = new ElementEnum[heaviestMass];

	public static final ElementEnum H=new ElementEnum (0,"H","Hydrogen", nonmetal, gas, stable);//Done
    public static final ElementEnum He=new ElementEnum (1,"He","Helium", inertGas, gas, stable);//Done
    public static final ElementEnum Li=new ElementEnum (2,"Li","Lithium", alkaliMetal, solid, stable);//Done
    public static final ElementEnum Be=new ElementEnum (3,"Be","Beryllium", alkalineEarthMetal, solid, stable);//Done
    public static final ElementEnum B=new ElementEnum (4,"B","Boron", semimetallic, solid, stable);//Done
    public static final ElementEnum C=new ElementEnum (5,"C","Carbon", nonmetal, solid, stable);//Done
    public static final ElementEnum N=new ElementEnum (6,"N","Nitrogen", nonmetal, gas, stable);//Done
    public static final ElementEnum O=new ElementEnum (7,"O","Oxygen", nonmetal, gas, stable);//Done
    public static final ElementEnum F=new ElementEnum (8,"F","Fluorine", halogen, gas, stable);//Done
    public static final ElementEnum Ne=new ElementEnum (9,"Ne","Neon", inertGas, gas, stable);//Done
    public static final ElementEnum Na=new ElementEnum (10,"Na","Sodium", alkaliMetal, solid, stable);//Done
    public static final ElementEnum Mg=new ElementEnum (11,"Mg","Magnesium", alkalineEarthMetal, solid, stable);//Done
    public static final ElementEnum Al=new ElementEnum (12,"Al","Aluminium", otherMetal, solid, stable);//Alloy
    public static final ElementEnum Si=new ElementEnum (13,"Si","Silicon", otherMetal, solid, stable);//Done
    public static final ElementEnum P=new ElementEnum (14,"P","Phosphorus", nonmetal, solid, stable);//Done
    public static final ElementEnum S=new ElementEnum (15,"S","Sulfur", nonmetal, solid, stable);//Done
    public static final ElementEnum Cl=new ElementEnum (16,"Cl","Chlorine", halogen, gas, stable);//Done
    public static final ElementEnum Ar=new ElementEnum (17,"Ar","Argon", inertGas, gas, stable);//Done
    public static final ElementEnum K=new ElementEnum (18,"K","Potassium", alkaliMetal, solid, stable);
    public static final ElementEnum Ca=new ElementEnum (19,"Ca","Calcium", alkalineEarthMetal, solid, stable);//Done
    public static final ElementEnum Sc=new ElementEnum (20,"Sc","Scandium", transitionMetal, solid, stable);//Alloy
    public static final ElementEnum Ti=new ElementEnum (21,"Ti","Titanium", transitionMetal, solid, stable);//Done
    public static final ElementEnum V=new ElementEnum (22,"V","Vanadium", transitionMetal, solid, stable);//Alloy
    public static final ElementEnum Cr=new ElementEnum (23,"Cr","Chromium", transitionMetal, solid, stable);//Done
    public static final ElementEnum Mn=new ElementEnum (24,"Mn","Manganese", transitionMetal, solid, stable);//Alloy
    public static final ElementEnum Fe=new ElementEnum (25,"Fe","Iron", transitionMetal, solid, stable);//Done
    public static final ElementEnum Co=new ElementEnum (26,"Co","Cobalt", transitionMetal, solid, stable);//Alloy
    public static final ElementEnum Ni=new ElementEnum (27,"Ni","Nickel", transitionMetal, solid, stable);//Done
    public static final ElementEnum Cu=new ElementEnum (28,"Cu","Copper", transitionMetal, solid, stable);
    public static final ElementEnum Zn=new ElementEnum (29,"Zn","Zinc", transitionMetal, solid, stable);
    public static final ElementEnum Ga=new ElementEnum (30,"Ga","Gallium", otherMetal, solid, stable);//Alloy
    public static final ElementEnum Ge=new ElementEnum (31,"Ge","Germanium", semimetallic, solid, stable);
    public static final ElementEnum As=new ElementEnum (32,"As","Arsenic", semimetallic, solid, stable);
    public static final ElementEnum Se=new ElementEnum (33,"Se","Selenium", nonmetal, solid, stable);//Alloy
    public static final ElementEnum Br=new ElementEnum (34,"Br","Bromine", halogen, liquid, stable);//Done
    public static final ElementEnum Kr=new ElementEnum (35,"Kr","Krypton", inertGas, gas, stable);//Done
    public static final ElementEnum Rb=new ElementEnum (36,"Rb","Rubidium", alkaliMetal, solid, stable);//Done
    public static final ElementEnum Sr=new ElementEnum (37,"Sr","Strontium", alkalineEarthMetal, solid, stable);//Alloy
    public static final ElementEnum Y=new ElementEnum (38,"Y","Yttrium", transitionMetal, solid, stable);//Alloy
    public static final ElementEnum Zr=new ElementEnum (39,"Zr","Zirconium", transitionMetal, solid, stable);//Done
    public static final ElementEnum Nb=new ElementEnum (40,"Nb","Niobium", transitionMetal, solid, stable);//Alloy
    public static final ElementEnum Mo=new ElementEnum (41,"Mo","Molybdenum", transitionMetal, solid, stable);//Alloy
    public static final ElementEnum Tc=new ElementEnum (42,"Tc","Technetium", transitionMetal, solid, hardlyRadioactive);
    public static final ElementEnum Ru=new ElementEnum (43,"Ru","Ruthenium", transitionMetal, solid, stable);//Alloy
    public static final ElementEnum Rh=new ElementEnum (44,"Rh","Rhodium", transitionMetal, solid, stable);//Alloy
    public static final ElementEnum Pd=new ElementEnum (45,"Pd","Palladium", transitionMetal, solid, stable);//Alloy
    public static final ElementEnum Ag=new ElementEnum (46,"Ag","Silver", transitionMetal, solid, stable);//Done
    public static final ElementEnum Cd=new ElementEnum (47,"Cd","Cadmium", transitionMetal, solid, stable);//Alloy
    public static final ElementEnum In=new ElementEnum (48,"In","Indium", otherMetal, solid, stable);
    public static final ElementEnum Sn=new ElementEnum (49,"Sn","Tin", otherMetal, solid, stable);
    public static final ElementEnum Sb=new ElementEnum (50,"Sb","Antimony", semimetallic, solid, stable);
    public static final ElementEnum Te=new ElementEnum (51,"Te","Tellurium", semimetallic, solid, stable);
    public static final ElementEnum I=new ElementEnum (52,"I","Iodine", halogen, solid, stable);
    public static final ElementEnum Xe=new ElementEnum (53,"Xe","Xenon", inertGas, gas, stable);
    public static final ElementEnum Cs=new ElementEnum (54,"Cs","Caesium", alkaliMetal, solid, stable);//Done
    public static final ElementEnum Ba=new ElementEnum (55,"Ba","Barium", alkalineEarthMetal, solid, stable);
    public static final ElementEnum La=new ElementEnum (56,"La","Lanthanum", lanthanide, solid, stable);
    public static final ElementEnum Ce=new ElementEnum (57,"Ce","Cerium", lanthanide, solid, stable);
    public static final ElementEnum Pr=new ElementEnum (58,"Pr","Praseodymium", lanthanide, solid, stable);
    public static final ElementEnum Nd=new ElementEnum (59,"Nd","Neodymium", lanthanide, solid, stable);
    public static final ElementEnum Pm=new ElementEnum (60,"Pm","Promethium", lanthanide, solid, radioactive);
    public static final ElementEnum Sm=new ElementEnum (61,"Sm","Samarium", lanthanide, solid, stable);
    public static final ElementEnum Eu=new ElementEnum (62,"Eu","Europium", lanthanide, solid, stable);
    public static final ElementEnum Gd=new ElementEnum (63,"Gd","Gadolinium", lanthanide, solid, stable);
    public static final ElementEnum Tb=new ElementEnum (64,"Tb","Terbium", lanthanide, solid, stable);
    public static final ElementEnum Dy=new ElementEnum (65,"Dy","Dyprosium", lanthanide, solid, stable);
    public static final ElementEnum Ho=new ElementEnum (66,"Ho","Holmium", lanthanide, solid, stable);
    public static final ElementEnum Er=new ElementEnum (67,"Er","Erbium", lanthanide, solid, stable);
    public static final ElementEnum Tm=new ElementEnum (68,"Tm","Thulium", lanthanide, solid, stable);
    public static final ElementEnum Yb=new ElementEnum (69,"Yb","Ytterbium", lanthanide, solid, stable);
    public static final ElementEnum Lu=new ElementEnum (70,"Lu","Lutetium", lanthanide, solid, stable);
    public static final ElementEnum Hf=new ElementEnum (71,"Hf","Hafnium", transitionMetal, solid, stable);
    public static final ElementEnum Ta=new ElementEnum (72,"Ta","Tantalum", transitionMetal, solid, stable);
    public static final ElementEnum W=new ElementEnum (73,"W","Tungsten", transitionMetal, solid, stable);
    public static final ElementEnum Re=new ElementEnum (74,"Re","Rhenium", transitionMetal, solid, stable);
    public static final ElementEnum Os=new ElementEnum (75,"Os","Osmium", transitionMetal, solid, stable);
    public static final ElementEnum Ir=new ElementEnum (76,"Ir","Iridium", transitionMetal, solid, stable);
    public static final ElementEnum Pt=new ElementEnum (77,"Pt","Platinum", transitionMetal, solid, stable);//Done
    public static final ElementEnum Au=new ElementEnum (78,"Au","Gold", transitionMetal, solid, stable);//Done
    public static final ElementEnum Hg=new ElementEnum (79,"Hg","Mercury", transitionMetal, liquid, stable);//Done
    public static final ElementEnum Tl=new ElementEnum (80,"Tl","Thallium", otherMetal, solid, stable);
    public static final ElementEnum Pb=new ElementEnum (81,"Pb","Lead", otherMetal, solid, stable);//Done
    public static final ElementEnum Bi=new ElementEnum (82,"Bi","Bismuth", otherMetal, solid, hardlyRadioactive);
    public static final ElementEnum Po=new ElementEnum (83,"Po","Polonium", semimetallic, solid, radioactive);
    public static final ElementEnum At=new ElementEnum (84,"At","Astantine", halogen, solid, highlyRadioactive);
    public static final ElementEnum Rn=new ElementEnum (85,"Rn","Radon", inertGas, gas, radioactive);
    public static final ElementEnum Fr=new ElementEnum (86,"Fr","Francium", alkaliMetal, solid, highlyRadioactive);//Done
    public static final ElementEnum Ra=new ElementEnum (87,"Ra","Radium", alkalineEarthMetal, solid, slightlyRadioactive);
    public static final ElementEnum Ac=new ElementEnum (88,"Ac","Actinium", actinide, solid, radioactive);
    public static final ElementEnum Th=new ElementEnum (89,"Th","Thorium", actinide, solid, hardlyRadioactive);
    public static final ElementEnum Pa=new ElementEnum (90,"Pa","Protactinium", actinide, solid, slightlyRadioactive);
    public static final ElementEnum U=new ElementEnum (91,"U","Uranium", actinide, solid, hardlyRadioactive);//Done
    public static final ElementEnum Np=new ElementEnum (92,"Np","Neptunium", actinide, solid, hardlyRadioactive);
    public static final ElementEnum Pu=new ElementEnum (93,"Pu","Plutonium", actinide, solid, hardlyRadioactive);
    public static final ElementEnum Am=new ElementEnum (94,"Am","Americium", actinide, solid, slightlyRadioactive);
    public static final ElementEnum Cm=new ElementEnum (95,"Cm","Curium", actinide, solid, hardlyRadioactive);
    public static final ElementEnum Bk=new ElementEnum (96,"Bk","Berkelium", actinide, solid, slightlyRadioactive);
    public static final ElementEnum Cf=new ElementEnum (97,"Cf","Californium", actinide, solid, slightlyRadioactive);
    public static final ElementEnum Es=new ElementEnum (98,"Es","Einsteinium", actinide, solid, radioactive);
    public static final ElementEnum Fm=new ElementEnum (99,"Fm","Fermium", actinide, solid, radioactive);
    public static final ElementEnum Md=new ElementEnum (100,"Md","Mendelenium", actinide, solid, radioactive);
    public static final ElementEnum No=new ElementEnum (101,"No","Nobelium", actinide, solid, highlyRadioactive);
    public static final ElementEnum Lr=new ElementEnum (102,"Lr","Lawrencium", actinide, solid, highlyRadioactive);
    public static final ElementEnum Rf=new ElementEnum (103,"Rf","Rutherfordium", transitionMetal, solid, highlyRadioactive);
    public static final ElementEnum Db=new ElementEnum (104,"Db","Dubnium", transitionMetal, solid, radioactive);
    public static final ElementEnum Sg=new ElementEnum (105,"Sg","Seaborgium", transitionMetal, solid, highlyRadioactive);
    public static final ElementEnum Bh=new ElementEnum (106,"Bh","Bohrium", transitionMetal, solid, highlyRadioactive);
    public static final ElementEnum Hs=new ElementEnum (107,"Hs","Hassium", transitionMetal, solid, highlyRadioactive);
    public static final ElementEnum Mt=new ElementEnum (108,"Mt","Meitnerium", transitionMetal, solid, extremelyRadioactive);
    public static final ElementEnum Ds=new ElementEnum (109,"Ds","Darmstadtium", transitionMetal, solid, extremelyRadioactive);
    public static final ElementEnum Rg=new ElementEnum (110,"Rg","Roenthenium", transitionMetal, solid, extremelyRadioactive);
    public static final ElementEnum Cn=new ElementEnum (111,"Cn","Copernicium", transitionMetal, solid, extremelyRadioactive);
    public static final ElementEnum Uut=new ElementEnum (112,"Uut","Ununtrium", transitionMetal, solid, extremelyRadioactive);

    // Descriptive name, in en_US. Should not be used; instead, use a
    // localized string from a .properties file.
    private final String descriptiveName;
    // Localization key.
    private final String localizationKey;
    private final ElementClassificationEnum classification;
    
    private final String name;
    private final int id;

    public ElementEnum(int id,String name,String descriptiveName, ElementClassificationEnum classification, ChemicalRoomStateEnum roomState,RadiationEnum radioactivity)
    {
    	super(roomState,radioactivity);
    	
    	if (elements[id]!=null){
    		throw new IllegalArgumentException("id "+id+" is used");
    	}
    	
    	elements[id]=this;
    	this.id=id;
    	
    	this.name=name;
        this.descriptiveName = descriptiveName;
        this.localizationKey = "element." + name;
        this.classification = classification;
        
        FluidHelper.registerElement(this);
    }

    public ElementClassificationEnum classification() 
    {
        return classification;
    }

    public int atomicNumber() 
    {
        return id + 1;
    }
    
    public int ordinal(){
    	return id;
    }
    
    public String name(){
    	return name;
    }
    
    @Override
	public String toString(){
    	return name();
    }
}