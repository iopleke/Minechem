package minechem.item.molecule;

import minechem.item.ChemicalRoomStateEnum;
import minechem.item.element.Element;
import minechem.potion.PotionChemical;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import static minechem.item.ChemicalRoomStateEnum.*;
import static minechem.item.element.ElementEnum.*;

// MOLECULE IDS MUST BE CONTINIOUS OTHERWISE THE ARRAY WILL BE MISALIGNED.

public enum MoleculeEnum 
{
    cellulose(0, 0, 1, 0, 0, 0.25F, 0, solid,new Element(C, 6), new Element(H, 10), new Element(O, 5)),
    water(1, 0, 0, 1, 0, 0, 1, liquid,new Element(H, 2), new Element(O)),
    carbonDioxide(2, 0.5F, 0.5F, 0.5F, 0.25F, 0.25F, 0.25F, gas,new Element(C), new Element(O, 2)),
    nitrogenDioxide(3, 1, 0.65F, 0, 0.5F, 0.1412F, 0.1843F, gas,new Element(N), new Element(O, 2)),
    toluene(4, 1, 1, 1, 0.8F, 0.8F, 0.8F, liquid,new Element(C, 7), new Element(H, 8)),
    potassiumNitrate(5, 0.9F, 0.9F, 0.9F, 0.8F, 0.8F, 0.8F,solid, new Element(K), new Element(N), new Element(O, 3)),
    tnt(6, 1, 1, 0, 1, 0.65F, 0,solid, new Element(C, 6), new Element(H, 2), new Molecule(nitrogenDioxide, 3), new Molecule(toluene)),
    siliconDioxide(7, 1, 1, 1, 1, 1, 1,solid, new Element(Si), new Element(O, 2)),
    calcite(8,solid, new Element(Ca), new Element(C), new Element(O, 3)), // TODO: Remove this as Calcium Carbonate is the exact same
    pyrite(9,solid, new Element(Fe), new Element(S, 2)),
    nepheline(10,solid, new Element(Al), new Element(Si), new Element(O, 4)),
    sulfate(11,solid, new Element(S), new Element(O, 4)),
    noselite(12,solid, new Element(Na, 8), new Molecule(nepheline, 6), new Molecule(sulfate)),
    sodalite(13,solid, new Element(Na, 8), new Molecule(nepheline, 6), new Element(Cl, 2)),
    nitrate(14,solid, new Element(N), new Element(O, 3)),
    carbonate(15,solid, new Element(C), new Element(O, 3)),
    cyanide(16,liquid, new Element(K), new Element(C), new Element(N)),
    phosphate(17,solid, new Element(P), new Element(O, 4)),
    acetate(18,solid, new Element(C, 2), new Element(H, 3), new Element(O, 2)),
    chromate(19,solid, new Element(Cr), new Element(O, 4)),
    hydroxide(20,liquid, new Element(O), new Element(H)),
    ammonium(21,liquid, new Element(N), new Element(H, 4)),
    hydronium(22,liquid, new Element(H, 3), new Element(O)),
    peroxide(23,liquid, new Element(O, 2)), 
    calciumOxide(24,solid, new Element(Ca), new Element(O)),
    calciumCarbonate(25,solid, new Element(Ca), new Molecule(carbonate)),
    magnesiumCarbonate(26,solid, new Element(Mg), new Molecule(carbonate)),
    lazurite(27,solid, new Element(Na, 8), new Molecule(nepheline), new Molecule(sulfate)),
    isoprene(28,solid, new Element(C, 5), new Element(H, 8)),
    butene(29,gas, new Element(C, 4), new Element(H, 8)),
    polyisobutylene(30,liquid, new Molecule(butene, 16), new Molecule(isoprene)),
    malicAcid(31,solid, new Element(C, 4), new Element(H, 6), new Element(O, 5)),
    vinylChloride(32,gas, new Element(C, 2), new Element(H, 3), new Element(Cl)),
    polyvinylChloride(33,solid, new Molecule(vinylChloride, 64)),
    methamphetamine(34,solid, new Element(C, 10), new Element(H, 15), new Element(N)),
    psilocybin(35,solid, new Element(C, 12), new Element(H, 17), new Element(N, 2), new Element(O, 4), new Element(P)),
    iron3oxide(36,solid, new Element(Fe, 2), new Element(O, 3)),
    strontiumNitrate(37,solid, new Element(Sr), new Molecule(nitrate, 2)),
    magnetite(38,solid, new Element(Fe, 3), new Element(O, 4)),
    magnesiumOxide(39,solid, new Element(Mg), new Element(O)),
    cucurbitacin(40,solid, new Element(C, 30), new Element(H, 42), new Element(O, 7)),
    asparticAcid(41,solid, new Element(C, 4), new Element(H, 7), new Element(N), new Element(O, 4)),
    hydroxylapatite(42,solid, new Element(Ca, 5), new Molecule(phosphate, 3), new Element(O), new Element(H)),
    alinine(43,solid, new Element(C, 3), new Element(H, 7), new Element(N), new Element(O, 2)),
    glycine(44,solid, new Element(C, 2), new Element(H, 5), new Element(N), new Element(O, 2)),
    serine(45,solid, new Element(C, 3), new Element(H, 7), new Molecule(nitrate)),
    mescaline(46,solid, new Element(C, 11), new Element(H, 17), new Molecule(nitrate)),
    methyl(47,liquid, new Element(C), new Element(H, 3)),
    methylene(48,liquid, new Element(C), new Element(H, 2)),
    memethacrylate(49,liquid, new Molecule(methyl, 3), new Element(C, 2), new Element(O, 2)),
    pmma(50,solid, new Molecule(memethacrylate, 3)), // The amount of hydrogens is not 100% right for the polymerized form. But its no big deal.
    redPigment(51,solid, new Element(Co), new Molecule(nitrate, 2)),
    orangePigment(52,solid, new Element(K, 2), new Element(Cr, 2), new Element(O, 7)),
    yellowPigment(53,solid, new Element(Cr), new Element(K, 2), new Element(O, 4)),
    limePigment(54,solid, new Element(Ni), new Element(Cl, 2)),
    lightbluePigment(55,solid, new Element(Cu), new Molecule(sulfate)),
    purplePigment(56,solid, new Element(K), new Element(Mn), new Element(O, 4)),
    greenPigment(57,solid, new Element(Co), new Element(Zn), new Element(O, 2)),
    blackPigment(58,gas, new Element(C), new Element(H, 2), new Element(O)),
    whitePigment(59,solid, new Element(Ti), new Element(O, 2)),
    metasilicate(60,solid, new Element(Si), new Element(O, 3)),
    beryl(61,solid, new Element(Be, 3), new Element(Al, 2), new Molecule(metasilicate, 6)),
    ethanol(62,liquid, new Element(C, 2), new Element(H, 5), new Molecule(hydroxide)),
    amphetamine(63,liquid, new Element(C, 9), new Element(H, 13), new Element(N)),
    theobromine(64,solid, new Element(C, 7), new Element(H, 8), new Element(N, 4), new Element(O, 2)),
    starch(65,solid, new Molecule(cellulose, 3)),
    sucrose(66,solid, new Element(C, 12), new Element(H, 22), new Element(O, 11)),
    pantherine(67,solid, new Element(C, 4), new Element(H, 6), new Element(N, 2), new Element(O, 2)),  
    aluminiumOxide(68,solid, new Element(Al, 2), new Element(O, 3)),
    fullrene(69, 0.47F, 0.47F, 0.47F, 0.47F, 0.47F, 0.47F,solid, new Element(C, 64), new Element(C, 64), new Element(C, 64), new Element(C, 64)),
    valine(70,solid, new Element(C, 5), new Element(H, 11), new Element(N), new Element(O, 2)), 
    penicillin(71,solid, new Element(C, 16), new Element(H, 18), new Element(N, 2), new Element(O, 4), new Element(S)),
    testosterone(72,liquid, new Element(C, 19), new Element(H, 28), new Element(O, 2)),
    kaolinite(73,solid, new Element(Al, 2), new Element(Si, 2), new Element(O, 5), new Molecule(hydroxide, 4)),
    fingolimod(74,solid, new Element(C, 19), new Element(H, 33), new Molecule(nitrogenDioxide)),
    arginine(75,solid, new Element(C, 6), new Element(H, 14), new Element(N, 4), new Element(O, 2)),
    shikimicAcid(76,solid, new Element(C, 7), new Element(H, 10), new Element(O, 5)),
    sulfuricAcid(77,liquid, new Element(H, 2), new Element(S), new Element(O, 4)),
    glyphosate(78,liquid, new Element(C, 3), new Element(H, 8), new Element(N), new Element(O, 5), new Element(P)),
    asprin(79,solid, new Element(C, 9), new Element(H, 8), new Element(O, 4)),
    ddt(80,solid, new Element(C, 14), new Element(H, 9), new Element(Cl, 5)),
    dota(81,solid, new Element(C, 16), new Element(H, 28), new Element(N, 4), new Element(O, 8)),
    mycotoxin(82, 0.89F, 0.83F, 0.07F, 0.89F, 0.83F, 0.07F,solid, new Element(C, 24), new Element(H, 34), new Element(O, 9)),
    salt(83,solid, new Element(Na), new Element(Cl)),
    ammonia(84,gas, new Element(N), new Element(H, 3)),
    nodularin(85,solid, new Element(C, 41), new Element(H, 60), new Element(N, 8), new Element(O, 10)),
    tetrodotoxin(86,solid, new Element(C, 11), new Element(H, 11), new Element(N, 3), new Element(O, 8)),
    thc(87,solid, new Element(C, 21), new Element(H, 30), new Element(O, 2)),
    mt(88,liquid, new Element(C, 9), new Element(H, 7), new Element(Mn, 1), new Element(O, 3)), // Level 1
    buli(89,solid, new Element(Li), new Element(C, 4), new Element(H, 9)), // Level 2
    plat(90,solid, new Element(H, 2), new Element(Pt, 1), new Element(Cl, 6)), // Level 3
    phosgene(91,gas, new Element(C), new Element(O), new Element(Cl, 2)),
    aalc(92,liquid, new Element(C, 3), new Element(H, 5), new Molecule(hydroxide)),
    hist(93,solid, new Element(C, 17), new Element(H, 21), new Element(N), new Element(O)),
    pal2(94,solid, new Element(C, 31), new Element(H, 42), new Element(N, 2), new Element(O, 6)),
    retinol(95,solid, new Element(C, 20), new Element(H, 29), new Molecule(hydroxide)),
    xylitol(96,solid, new Element(C, 5), new Element(H, 12), new Element(O, 5)),
    weedex(97,solid, new Element(C, 8), new Element(H, 8), new Element(Cl), new Element(N, 3), new Element(O, 2)),
    xanax(98,solid, new Element(C, 17), new Element(H, 13), new Element(Cl), new Element(N, 4)),
    hcl(99,liquid, new Element(H), new Element(Cl)),
    cocaine(100,solid, new Element(C, 17), new Element(H, 21), new Element(N), new Element(O, 4)),
    cocainehcl(101,solid, new Molecule(cocaine), new Molecule(hcl)),
    blueorgodye(102,liquid, new Element(C, 15), new Element(H, 18)),
    redorgodye(103,solid, new Element(C, 15), new Element(H, 11), new Element(O, 11)),
    purpleorgodye(104,solid, new Element(C, 15), new Element(H, 11), new Element(O, 7)),
    olivine(105,solid, new Element(Fe, 2), new Element(Si), new Element(O, 4)),
    metblue(106,solid, new Element(C, 16), new Element(H, 18), new Element(N, 3), new Element(S), new Element(Cl)),
    meoh(107,liquid, new Molecule(methyl), new Molecule(hydroxide)),
    lcd(108,solid, new Element(C, 34), new Element(H, 50), new Element(O, 2)),
    radchlor(109,solid, new Element(Ra), new Element(Cl, 2)),
    caulerpenyne(110,solid, new Element(C, 21), new Element(H, 26), new Element(O, 6)),
    latropine(111,solid, new Element(C, 17), new Element(H, 23), new Element(N), new Element(O, 4)),
    gallicacid(112,solid, new Element(C, 7), new Element(H, 17), new Element(O, 5)),
    glucose(113,solid, new Element(C, 6), new Element(H, 12), new Element(O, 6)),
    tannicacid(114,solid, new Molecule(gallicacid, 10), new Molecule(glucose)),
    hperox(115,liquid, new Element(H, 2), new Molecule(peroxide)),
    galliumarsenide(116,solid, new Element(Ga), new Element(As)),
    fibroin(117,liquid, new Molecule(glycine), new Molecule(serine), new Molecule(glycine), new Molecule(alinine), new Molecule(glycine), new Molecule(alinine)),
    aluminiumPhosphate(118,solid, new Element(Al), new Molecule(phosphate)),
    potassiumOxide(119,solid, new Element(K, 2), new Element(O)),
    sodiumOxide(120,solid, new Element(Na, 2), new Element(O)),
    
    // For underground biomes support
    // http://flexiblelearning.auckland.ac.nz/rocks_minerals/
    plagioclaseAnorthite(121,solid, new Element(Ca), new Element(Al, 2), new Element(Si, 2), new Element(O, 8)),
    plagioclaseAlbite(122,solid, new Element(Na), new Element(Al, 2), new Element(Si, 3), new Element(O, 8)),
    orthoclase(123,solid, new Element(K), new Element(Al), new Element(Si, 3), new Element(O, 8)),
    biotite(124,solid, new Element(K), new Element(Fe, 3), new Element(Al), new Element(Si, 3), new Element(O, 10), new Element(F, 2)),
    augite(125,solid, new Element(Na), new Element(Fe), new Element(Al, 2), new Element(O, 6)),
    talc(126,solid, new Element(Mg, 3), new Element(Si, 4), new Element(O, 10)),
    
    // Metallurgy
    propane(127,gas, new Element(C, 3), new Element(H, 8)),
    peridot(128,solid, new Element(Mg, 2), new Element(O, 4), new Element(Si)),
    topaz(129,solid, new Element(Al, 2), new Element(O, 4), new Element(F, 2)),
    zoisite(130,solid, new Element(Ca, 2), new Element(Al, 3), new Element(Si, 3), new Element(O, 13), new Element(H)),

    //
    cysteine(131,solid, new Element(C, 3), new Element(H, 7), new Element(N), new Element(O, 2), new Element(S)),
    threonine(132,solid, new Element(C, 4), new Element(H, 9), new Element(N), new Element(O, 3)),
    lysine(133,solid, new Element(C, 6), new Element(H, 14), new Element(N, 2), new Element(O, 2)),
    methionine(134,solid, new Element(C, 5), new Element(H, 11), new Element(N), new Element(O, 2), new Element(S)),
    tyrosine(135,solid, new Element(C, 9), new Element(H, 11), new Element(N), new Element(O, 3)),
    histidine(136,solid, new Element(C, 6), new Element(H, 9), new Element(N, 3), new Element(O, 2)),
    phenylalanine(137,solid, new Element(C, 9), new Element(H, 11), new Element(N), new Element(O, 2)),
    glutamine(138,solid, new Element(C, 5), new Element(H, 10), new Element(N, 2), new Element(O, 3)),
    proline(139,solid, new Element(C, 5), new Element(H, 9), new Element(N), new Element(O, 2)),
    leucine(140,solid, new Element(C, 6), new Element(H, 13), new Element(N), new Element(O, 2)),
    tryptophan(141,solid, new Element(C, 11), new Element(H, 12), new Element(N, 2), new Element(O, 2)),
    aspartate(142,solid, new Element(C, 4), new Element(H, 7), new Element(N), new Element(O, 4)),
    isoleucine(143,solid, new Element(C, 6), new Element(H, 13), new Element(N), new Element(O, 2)),
    glutamates(144,solid, new Element(C, 5), new Element(H, 9), new Element(N), new Element(O, 4)),
    asparagine(145,solid, new Element(C, 4), new Element(H, 8), new Element(N, 2), new Element(O, 3)),
    keratin(146,solid, new Molecule(threonine), new Molecule(cysteine), new Molecule(proline), new Molecule(threonine), new Molecule(proline), new Molecule(cysteine), new Molecule(proline)),
      
    //Thermal Expansion
    asbestos(147,solid, new Element(Mg, 3), new Element(Si, 2), new Element(O, 5), new Molecule(hydroxide, 4)),

    //
    lithiumHydroxide(148,solid,new Element(Li, 1),new Molecule(hydroxide, 1)),
    sodiumHydroxide(149,solid,new Element(Na, 1),new Molecule(hydroxide, 1)),
    potassiumHydroxide(150,solid,new Element(K, 1),new Molecule(hydroxide, 1)),
    rubidiumHydroxide(151,solid,new Element(Rb, 1),new Molecule(hydroxide, 1)),
    cesiumHydroxide(152,solid,new Element(Cs, 1),new Molecule(hydroxide, 1)),
    franciumHydroxide(153,solid,new Element(Fr, 1),new Molecule(hydroxide, 1)),
    ;
    
    public static MoleculeEnum[] molecules = values();
    private final String localizationKey;
    private final ArrayList<PotionChemical> components;
    private int id;
    public float red;
    public float green;
    public float blue;
    public float red2;
    public float green2;
    public float blue2;
    public ChemicalRoomStateEnum roomState;

    //* Allows full definition of a given molecule down to even the colors that will be used. */
    MoleculeEnum(int id, float colorRed, float colorGreen, float colorBlue, float colorRed2, float colorGreen2, float colorBlue2,ChemicalRoomStateEnum roomState, PotionChemical... chemicals) {
        this.id = id;
        this.components = new ArrayList<PotionChemical>();
        this.localizationKey = "molecule." + name();
        for (PotionChemical potionChemical : chemicals) 
        {
            this.components.add(potionChemical);
        }
        this.red = colorRed;
        this.green = colorGreen;
        this.blue = colorBlue;
        this.red2 = colorRed2;
        this.green2 = colorGreen2;
        this.blue2 = colorBlue2;
        this.roomState=roomState;
    }

    /** Used to give random colors for elements so they don't have to be manually specified. */
    MoleculeEnum(int id,ChemicalRoomStateEnum roomState, PotionChemical... chemicals) 
    {
        this(id, getRandomColor(), getRandomColor(), getRandomColor(), getRandomColor(), getRandomColor(), getRandomColor(), roomState,chemicals);
    }

    private static float getRandomColor() 
    {
        Random random = new Random();
        return random.nextFloat();
    }

    public int getSize()
    {
        int result=0;

        Iterator iter=this.components().iterator();

        while(iter.hasNext())
        {
            result += ((PotionChemical)iter.next()).amount;
        }

        return result;
    }

    public static MoleculeEnum getById(int id) 
    {
        for (MoleculeEnum molecule : molecules) 
        {
            if (molecule.id == id)
                return molecule;
        }
        return null;
    }

    public int id() 
    {
        return this.id;
    }

    /**
     * Returns the localized name of this molecule, or an en_US-based
     * placeholder if no localization was found.
     *
     * @return Localized name of this molecule.
     */
    public String descriptiveName() 
    {
        String localizedName = StatCollector.translateToLocal(this.localizationKey);
        if (localizedName.isEmpty()) 
        {
            return localizationKey;
        }
        return localizedName;
    }

    public ArrayList<PotionChemical> components() 
    {
        return this.components;
    }

}
