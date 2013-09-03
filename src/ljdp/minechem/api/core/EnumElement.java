package ljdp.minechem.api.core;

import static ljdp.minechem.api.core.EnumClassification.*;
import static ljdp.minechem.api.core.EnumRadioactivity.*;

public enum EnumElement {
    H("Hydrogen", nonmetal, gas, stable),
    He("Helium", inertGas, gas, stable),
    Li("Lithium", alkaliMetal, solid, stable),
    Be("Beryllium", alkalineEarthMetal, solid, stable),
    B("Boron", semimetallic, solid, stable),
    C("Carbon", nonmetal, solid, stable),
    N("Nitrogen", nonmetal, gas, stable),
    O("Oxygen", nonmetal, gas, stable),
    F("Flourine", halogen, gas, stable),
    Ne("Neon", inertGas, gas, stable),
    Na("Sodium", alkaliMetal, solid, stable),
    Mg("Magnesium", alkalineEarthMetal, solid, stable),
    Al("Aluminium", otherMetal, solid, stable),
    Si("Silicon", otherMetal, solid, stable),
    P("Phosphorus", nonmetal, solid, stable),
    S("Sulfur", nonmetal, solid, stable),
    Cl("Chlorine", halogen, gas, stable),
    Ar("Argon", inertGas, gas, stable),
    K("Potassium", alkaliMetal, solid, stable),
    Ca("Calcium", alkalineEarthMetal, solid, stable),
    Sc("Scandium", transitionMetal, solid, stable),
    Ti("Titanium", transitionMetal, solid, stable),
    V("Vanadium", transitionMetal, solid, stable),
    Cr("Chromium", transitionMetal, solid, stable),
    Mn("Manganese", transitionMetal, solid, stable),
    Fe("Iron", transitionMetal, solid, stable),
    Co("Cobalt", transitionMetal, solid, stable),
    Ni("Nickel", transitionMetal, solid, stable),
    Cu("Copper", transitionMetal, solid, stable),
    Zn("Zinc", transitionMetal, solid, stable),
    Ga("Gallium", otherMetal, solid, stable),
    Ge("Germanium", semimetallic, solid, stable),
    As("Arsenic", semimetallic, solid, stable),
    Se("Selenium", nonmetal, solid, stable),
    Br("Bromine", halogen, liquid, stable),
    Kr("Krypton", inertGas, gas, stable),
    Rb("Rubidium", alkaliMetal, solid, stable),
    Sr("Strontium", alkalineEarthMetal, solid, stable),
    Y("Yttrium", transitionMetal, solid, stable),
    Zr("Zirconium", transitionMetal, solid, stable),
    Nb("Niobium", transitionMetal, solid, stable),
    Mo("Molybdenum", transitionMetal, solid, stable),
    Tc("Technetium", transitionMetal, solid, hardlyRadioactive),
    Ru("Ruthenium", transitionMetal, solid, stable),
    Rh("Rhodium", transitionMetal, solid, stable),
    Pd("Palladium", transitionMetal, solid, stable),
    Ag("Silver", transitionMetal, solid, stable),
    Cd("Cadmium", transitionMetal, solid, stable),
    In("Indium", otherMetal, solid, stable),
    Sn("Tin", otherMetal, solid, stable),
    Sb("Antimony", semimetallic, solid, stable),
    Te("Tellurium", semimetallic, solid, stable),
    I("Iodine", halogen, solid, stable),
    Xe("Xenon", inertGas, gas, stable),
    Cs("Caesium", alkaliMetal, solid, stable),
    Ba("Barium", alkalineEarthMetal, solid, stable),
    La("Lanthanum", lanthanide, solid, stable),
    Ce("Cerium", lanthanide, solid, stable),
    Pr("Praseodymium", lanthanide, solid, stable),
    Nd("Neodymium", lanthanide, solid, stable),
    Pm("Promethium", lanthanide, solid, radioactive),
    Sm("Samarium", lanthanide, solid, stable),
    Eu("Europium", lanthanide, solid, stable),
    Gd("Gadolinium", lanthanide, solid, stable),
    Tb("Terbium", lanthanide, solid, stable),
    Dy("Dyprosium", lanthanide, solid, stable),
    Ho("Holmium", lanthanide, solid, stable),
    Er("Erbium", lanthanide, solid, stable),
    Tm("Thulium", lanthanide, solid, stable),
    Yb("Ytterbium", lanthanide, solid, stable),
    Lu("Lutetium", lanthanide, solid, stable),
    Hf("Hafnium", transitionMetal, solid, stable),
    Ta("Tantalum", transitionMetal, solid, stable),
    W("Tungsten", transitionMetal, solid, stable),
    Re("Rhenium", transitionMetal, solid, stable),
    Os("Osmium", transitionMetal, solid, stable),
    Ir("Iridium", transitionMetal, solid, stable),
    Pt("Platinum", transitionMetal, solid, stable),
    Au("Gold", transitionMetal, solid, stable),
    Hg("Mercury", transitionMetal, liquid, stable),
    Tl("Thallium", otherMetal, solid, stable),
    Pb("Lead", otherMetal, solid, stable),
    Bi("Bismuth", otherMetal, solid, hardlyRadioactive),
    Po("Polonium", semimetallic, solid, radioactive),
    At("Astantine", halogen, solid, highlyRadioactive),
    Rn("Radon", inertGas, gas, radioactive),
    Fr("Francium", alkaliMetal, solid, highlyRadioactive),
    Ra("Radium", alkalineEarthMetal, solid, slightlyRadioactive),
    Ac("Actinium", actinide, solid, radioactive),
    Th("Thorium", actinide, solid, hardlyRadioactive),
    Pa("Protactinium", actinide, solid, slightlyRadioactive),
    U("Uranium", actinide, solid, hardlyRadioactive),
    Np("Neptunium", actinide, solid, hardlyRadioactive),
    Pu("Plutonium", actinide, solid, hardlyRadioactive),
    Am("Americium", actinide, solid, slightlyRadioactive),
    Cm("Curium", actinide, solid, hardlyRadioactive),
    Bk("Berkelium", actinide, solid, slightlyRadioactive),
    Cf("Californium", actinide, solid, slightlyRadioactive),
    Es("Einsteinium", actinide, solid, radioactive),
    Fm("Fermium", actinide, solid, radioactive),
    Md("Mendelenium", actinide, solid, radioactive),
    No("Nobelium", actinide, solid, highlyRadioactive),
    Lr("Lawrencium", actinide, solid, highlyRadioactive),
    Rf("Rutherfordium", transitionMetal, solid, highlyRadioactive),
    Db("Dubnium", transitionMetal, solid, radioactive),
    Sg("Seaborgium", transitionMetal, solid, highlyRadioactive),
    Bh("Bohrium", transitionMetal, solid, highlyRadioactive),
    Hs("Hassium", transitionMetal, solid, highlyRadioactive),
    Mt("Meitnerium", transitionMetal, solid, extremelyRadioactive),
    Ds("Darmstadtium", transitionMetal, solid, extremelyRadioactive),
    Rg("Roenthenium", transitionMetal, solid, extremelyRadioactive),
    Cn("Copernicium", transitionMetal, solid, extremelyRadioactive),
    Uut("Ununtrium", transitionMetal, solid, extremelyRadioactive);

    public static EnumElement[] elements = EnumElement.values();
    public static int heaviestMass = 113;
    private final String descriptiveName;
    private final EnumClassification classification;
    private final EnumClassification roomState;
    private final EnumRadioactivity radioactivity;

    EnumElement(String descriptiveName, EnumClassification classification, EnumClassification roomState, EnumRadioactivity radioactivity) {
        this.descriptiveName = descriptiveName;
        this.classification = classification;
        this.roomState = roomState;
        this.radioactivity = radioactivity;
    }

    public String descriptiveName() {
        return descriptiveName;
    }

    public EnumClassification classification() {
        return classification;
    }

    public EnumClassification roomState() {
        return roomState;
    }

    public EnumRadioactivity radioactivity() {
        return radioactivity;
    }

    public int atomicNumber() {
        return ordinal() + 1;
    }
}
