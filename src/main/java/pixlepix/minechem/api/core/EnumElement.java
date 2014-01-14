package pixlepix.minechem.api.core;

import cpw.mods.fml.common.registry.LanguageRegistry;

import static pixlepix.minechem.api.core.EnumClassification.*;
import static pixlepix.minechem.api.core.EnumRadioactivity.*;

public enum EnumElement {
    H("Hydrogen", nonmetal, gas, stable),//Done
    He("Helium", inertGas, gas, stable),//Done
    Li("Lithium", alkaliMetal, solid, stable),//Done
    Be("Beryllium", alkalineEarthMetal, solid, stable),//Done
    B("Boron", semimetallic, solid, stable),//Done
    C("Carbon", nonmetal, solid, stable),//Done
    N("Nitrogen", nonmetal, gas, stable),//Done
    O("Oxygen", nonmetal, gas, stable),//Done
    F("Fluorine", halogen, gas, stable),//Done
    Ne("Neon", inertGas, gas, stable),//Done
    Na("Sodium", alkaliMetal, solid, stable),//Done
    Mg("Magnesium", alkalineEarthMetal, solid, stable),//Done
    Al("Aluminium", otherMetal, solid, stable),//Alloy
    Si("Silicon", otherMetal, solid, stable),//Done
    P("Phosphorus", nonmetal, solid, stable),//Done
    S("Sulfur", nonmetal, solid, stable),//Done
    Cl("Chlorine", halogen, gas, stable),//Done
    Ar("Argon", inertGas, gas, stable),//Done
    K("Potassium", alkaliMetal, solid, stable),
    Ca("Calcium", alkalineEarthMetal, solid, stable),//Done
    Sc("Scandium", transitionMetal, solid, stable),//Alloy
    Ti("Titanium", transitionMetal, solid, stable),//Done
    V("Vanadium", transitionMetal, solid, stable),//Alloy
    Cr("Chromium", transitionMetal, solid, stable),//Done
    Mn("Manganese", transitionMetal, solid, stable),//Alloy
    Fe("Iron", transitionMetal, solid, stable),//Done
    Co("Cobalt", transitionMetal, solid, stable),//Alloy
    Ni("Nickel", transitionMetal, solid, stable),//Done
    Cu("Copper", transitionMetal, solid, stable),
    Zn("Zinc", transitionMetal, solid, stable),
    Ga("Gallium", otherMetal, solid, stable),//Alloy
    Ge("Germanium", semimetallic, solid, stable),
    As("Arsenic", semimetallic, solid, stable),
    Se("Selenium", nonmetal, solid, stable),//Alloy
    Br("Bromine", halogen, liquid, stable),//Done
    Kr("Krypton", inertGas, gas, stable),//Done
    Rb("Rubidium", alkaliMetal, solid, stable),//Done
    Sr("Strontium", alkalineEarthMetal, solid, stable),//Alloy
    Y("Yttrium", transitionMetal, solid, stable),//Alloy
    Zr("Zirconium", transitionMetal, solid, stable),//Done
    Nb("Niobium", transitionMetal, solid, stable),//Alloy
    Mo("Molybdenum", transitionMetal, solid, stable),//Alloy
    Tc("Technetium", transitionMetal, solid, hardlyRadioactive),
    Ru("Ruthenium", transitionMetal, solid, stable),//Alloy
    Rh("Rhodium", transitionMetal, solid, stable),//Alloy
    Pd("Palladium", transitionMetal, solid, stable),//Alloy
    Ag("Silver", transitionMetal, solid, stable),//Done
    Cd("Cadmium", transitionMetal, solid, stable),//Alloy
    In("Indium", otherMetal, solid, stable),
    Sn("Tin", otherMetal, solid, stable),
    Sb("Antimony", semimetallic, solid, stable),
    Te("Tellurium", semimetallic, solid, stable),
    I("Iodine", halogen, solid, stable),
    Xe("Xenon", inertGas, gas, stable),
    Cs("Caesium", alkaliMetal, solid, stable),//Done
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
    Pt("Platinum", transitionMetal, solid, stable),//Done
    Au("Gold", transitionMetal, solid, stable),//Done
    Hg("Mercury", transitionMetal, liquid, stable),//Done
    Tl("Thallium", otherMetal, solid, stable),
    Pb("Lead", otherMetal, solid, stable),//Done
    Bi("Bismuth", otherMetal, solid, hardlyRadioactive),
    Po("Polonium", semimetallic, solid, radioactive),
    At("Astantine", halogen, solid, highlyRadioactive),
    Rn("Radon", inertGas, gas, radioactive),
    Fr("Francium", alkaliMetal, solid, highlyRadioactive),//Done
    Ra("Radium", alkalineEarthMetal, solid, slightlyRadioactive),
    Ac("Actinium", actinide, solid, radioactive),
    Th("Thorium", actinide, solid, hardlyRadioactive),
    Pa("Protactinium", actinide, solid, slightlyRadioactive),
    U("Uranium", actinide, solid, hardlyRadioactive),//Done
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
    // Descriptive name, in en_US. Should not be used; instead, use a
    // localized string from a .properties file.
    private final String descriptiveName;
    // Localization key.
    private final String localizationKey;
    private final EnumClassification classification;
    private final EnumClassification roomState;
    private final EnumRadioactivity radioactivity;

    EnumElement(String descriptiveName, EnumClassification classification, EnumClassification roomState, EnumRadioactivity radioactivity) {
        this.descriptiveName = descriptiveName;
        this.localizationKey = "minechem.element." + name();
        this.classification = classification;
        this.roomState = roomState;
        this.radioactivity = radioactivity;
    }

    /**
     * Returns the localized name of this element, or an en_US-based placeholder
     * if no localization was found.
     *
     * @return Localized name of this element.
     */
    public String descriptiveName() {
        String localizedName =
                LanguageRegistry.instance().getStringLocalization(
                        this.localizationKey);
        if (localizedName.isEmpty()) {
            return localizationKey;
        }
        return localizedName;
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
