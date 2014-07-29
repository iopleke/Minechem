package minechem.item.molecule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import minechem.item.element.Element;
import minechem.potion.PotionChemical;
import net.minecraft.util.StatCollector;
import static minechem.item.element.ElementEnum.*;

// MOLECULE IDS MUST BE CONTINIOUS OTHERWISE THE ARRAY WILL BE MISALIGNED.

public enum MoleculeEnum 
{
    cellulose(0, 0, 1, 0, 0, 0.25F, 0, new Element(C, 6), new Element(H, 10), new Element(O, 5)),
    water(1, 0, 0, 1, 0, 0, 1, new Element(H, 2), new Element(O)),
    carbonDioxide(2, 0.5F, 0.5F, 0.5F, 0.25F, 0.25F, 0.25F, new Element(C), new Element(O, 2)),
    nitrogenDioxide(3, 1, 0.65F, 0, 0.5F, 0.1412F, 0.1843F, new Element(N), new Element(O, 2)),
    toluene(4, 1, 1, 1, 0.8F, 0.8F, 0.8F, new Element(C, 7), new Element(H, 8)),
    potassiumNitrate(5, 0.9F, 0.9F, 0.9F, 0.8F, 0.8F, 0.8F, new Element(K), new Element(N), new Element(O, 3)),
    tnt(6, 1, 1, 0, 1, 0.65F, 0, new Element(C, 6), new Element(H, 2), new Molecule(nitrogenDioxide, 3), new Molecule(toluene)),
    siliconDioxide(7, 1, 1, 1, 1, 1, 1, new Element(Si), new Element(O, 2)),
    calcite(8, new Element(Ca), new Element(C), new Element(O, 3)), // TODO: Remove this as Calcium Carbonate is the exact same
    pyrite(9, new Element(Fe), new Element(S, 2)),
    nepheline(10, new Element(Al), new Element(Si), new Element(O, 4)),
    sulfate(11, new Element(S), new Element(O, 4)),
    noselite(12, new Element(Na, 8), new Molecule(nepheline, 6), new Molecule(sulfate)),
    sodalite(13, new Element(Na, 8), new Molecule(nepheline, 6), new Element(Cl, 2)),
    nitrate(14, new Element(N), new Element(O, 3)),
    carbonate(15, new Element(C), new Element(O, 3)),
    cyanide(16, new Element(K), new Element(C), new Element(N)),
    phosphate(17, new Element(P), new Element(O, 4)),
    acetate(18, new Element(C, 2), new Element(H, 3), new Element(O, 2)),
    chromate(19, new Element(Cr), new Element(O, 4)),
    hydroxide(20, new Element(O), new Element(H)),
    ammonium(21, new Element(N), new Element(H, 4)),
    hydronium(22, new Element(H, 3), new Element(O)),
    peroxide(23, new Element(O, 2)), 
    calciumOxide(24, new Element(Ca), new Element(O)),
    calciumCarbonate(25, new Element(Ca), new Molecule(carbonate)),
    magnesiumCarbonate(26, new Element(Mg), new Molecule(carbonate)),
    lazurite(27, new Element(Na, 8), new Molecule(nepheline), new Molecule(sulfate)),
    isoprene(28, new Element(C, 5), new Element(H, 8)),
    butene(29, new Element(C, 4), new Element(H, 8)),
    polyisobutylene(30, new Molecule(butene, 16), new Molecule(isoprene)),
    malicAcid(31, new Element(C, 4), new Element(H, 6), new Element(O, 5)),
    vinylChloride(32, new Element(C, 2), new Element(H, 3), new Element(Cl)),
    polyvinylChloride(33, new Molecule(vinylChloride, 64)),
    methamphetamine(34, new Element(C, 10), new Element(H, 15), new Element(N)),
    psilocybin(35, new Element(C, 12), new Element(H, 17), new Element(N, 2), new Element(O, 4), new Element(P)),
    iron3oxide(36, new Element(Fe, 2), new Element(O, 3)),
    strontiumNitrate(37, new Element(Sr), new Molecule(nitrate, 2)),
    magnetite(38, new Element(Fe, 3), new Element(O, 4)),
    magnesiumOxide(39, new Element(Mg), new Element(O)),
    cucurbitacin(40, new Element(C, 30), new Element(H, 42), new Element(O, 7)),
    asparticAcid(41, new Element(C, 4), new Element(H, 7), new Element(N), new Element(O, 4)),
    hydroxylapatite(42, new Element(Ca, 5), new Molecule(phosphate, 3), new Element(O), new Element(H)),
    alinine(43, new Element(C, 3), new Element(H, 7), new Element(N), new Element(O, 2)),
    glycine(44, new Element(C, 2), new Element(H, 5), new Element(N), new Element(O, 2)),
    serine(45, new Element(C, 3), new Element(H, 7), new Molecule(nitrate)),
    mescaline(46, new Element(C, 11), new Element(H, 17), new Molecule(nitrate)),
    methyl(47, new Element(C), new Element(H, 3)),
    methylene(48, new Element(C), new Element(H, 2)),
    memethacrylate(49, new Molecule(methyl, 3), new Element(C, 2), new Element(O, 2)),
    pmma(50, new Molecule(memethacrylate, 3)), // The amount of hydrogens is not 100% right for the polymerized form. But its no big deal.
    redPigment(51, new Element(Co), new Molecule(nitrate, 2)),
    orangePigment(52, new Element(K, 2), new Element(Cr, 2), new Element(O, 7)),
    yellowPigment(53, new Element(Cr), new Element(K, 2), new Element(O, 4)),
    limePigment(54, new Element(Ni), new Element(Cl, 2)),
    lightbluePigment(55, new Element(Cu), new Molecule(sulfate)),
    purplePigment(56, new Element(K), new Element(Mn), new Element(O, 4)),
    greenPigment(57, new Element(Co), new Element(Zn), new Element(O, 2)),
    blackPigment(58, new Element(C), new Element(H, 2), new Element(O)),
    whitePigment(59, new Element(Ti), new Element(O, 2)),
    metasilicate(60, new Element(Si), new Element(O, 3)),
    beryl(61, new Element(Be, 3), new Element(Al, 2), new Molecule(metasilicate, 6)),
    ethanol(62, new Element(C, 2), new Element(H, 5), new Molecule(hydroxide)),
    amphetamine(63, new Element(C, 9), new Element(H, 13), new Element(N)),
    theobromine(64, new Element(C, 7), new Element(H, 8), new Element(N, 4), new Element(O, 2)),
    starch(65, new Molecule(cellulose, 3)),
    sucrose(66, new Element(C, 12), new Element(H, 22), new Element(O, 11)),
    pantherine(67, new Element(C, 4), new Element(H, 6), new Element(N, 2), new Element(O, 2)),  
    aluminiumOxide(68, new Element(Al, 2), new Element(O, 3)),
    fullrene(69, 0.47F, 0.47F, 0.47F, 0.47F, 0.47F, 0.47F, new Element(C, 64), new Element(C, 64), new Element(C, 64), new Element(C, 64)),
    valine(70, new Element(C, 5), new Element(H, 11), new Element(N), new Element(O, 2)), 
    penicillin(71, new Element(C, 16), new Element(H, 18), new Element(N, 2), new Element(O, 4), new Element(S)),
    testosterone(72, new Element(C, 19), new Element(H, 28), new Element(O, 2)),
    kaolinite(73, new Element(Al, 2), new Element(Si, 2), new Element(O, 5), new Molecule(hydroxide, 4)),
    fingolimod(74, new Element(C, 19), new Element(H, 33), new Molecule(nitrogenDioxide)),
    arginine(75, new Element(C, 6), new Element(H, 14), new Element(N, 4), new Element(O, 2)),
    shikimicAcid(76, new Element(C, 7), new Element(H, 10), new Element(O, 5)),
    sulfuricAcid(77, new Element(H, 2), new Element(S), new Element(O, 4)),
    glyphosate(78, new Element(C, 3), new Element(H, 8), new Element(N), new Element(O, 5), new Element(P)),
    asprin(79, new Element(C, 9), new Element(H, 8), new Element(O, 4)),
    ddt(80, new Element(C, 14), new Element(H, 9), new Element(Cl, 5)),
    dota(81, new Element(C, 16), new Element(H, 28), new Element(N, 4), new Element(O, 8)),
    mycotoxin(82, 0.89F, 0.83F, 0.07F, 0.89F, 0.83F, 0.07F, new Element(C, 24), new Element(H, 34), new Element(O, 9)),
    salt(83, new Element(Na), new Element(Cl)),
    ammonia(84, new Element(N), new Element(H, 3)),
    nodularin(85, new Element(C, 41), new Element(H, 60), new Element(N, 8), new Element(O, 10)),
    tetrodotoxin(86, new Element(C, 11), new Element(H, 11), new Element(N, 3), new Element(O, 8)),
    thc(87, new Element(C, 21), new Element(H, 30), new Element(O, 2)),
    mt(88, new Element(C, 9), new Element(H, 7), new Element(Mn, 1), new Element(O, 3)), // Level 1
    buli(89, new Element(Li), new Element(C, 4), new Element(H, 9)), // Level 2
    plat(90, new Element(H, 2), new Element(Pt, 1), new Element(Cl, 6)), // Level 3
    phosgene(91, new Element(C), new Element(O), new Element(Cl, 2)),
    aalc(92, new Element(C, 3), new Element(H, 5), new Molecule(hydroxide)),
    hist(93, new Element(C, 17), new Element(H, 21), new Element(N), new Element(O)),
    pal2(94, new Element(C, 31), new Element(H, 42), new Element(N, 2), new Element(O, 6)),
    retinol(95, new Element(C, 20), new Element(H, 29), new Molecule(hydroxide)),
    xylitol(96, new Element(C, 5), new Element(H, 12), new Element(O, 5)),
    weedex(97, new Element(C, 8), new Element(H, 8), new Element(Cl), new Element(N, 3), new Element(O, 2)),
    xanax(98, new Element(C, 17), new Element(H, 13), new Element(Cl), new Element(N, 4)),
    hcl(99, new Element(H), new Element(Cl)),
    cocaine(100, new Element(C, 17), new Element(H, 21), new Element(N), new Element(O, 4)),
    cocainehcl(101, new Molecule(cocaine), new Molecule(hcl)),
    blueorgodye(102, new Element(C, 15), new Element(H, 18)),
    redorgodye(103, new Element(C, 15), new Element(H, 11), new Element(O, 11)),
    purpleorgodye(104, new Element(C, 15), new Element(H, 11), new Element(O, 7)),
    olivine(105, new Element(Fe, 2), new Element(Si), new Element(O, 4)),
    metblue(106, new Element(C, 16), new Element(H, 18), new Element(N, 3), new Element(S), new Element(Cl)),
    meoh(107, new Molecule(methyl), new Molecule(hydroxide)),
    lcd(108, new Element(C, 34), new Element(H, 50), new Element(O, 2)),
    radchlor(109, new Element(Ra), new Element(Cl, 2)),
    caulerpenyne(110, new Element(C, 21), new Element(H, 26), new Element(O, 6)),
    latropine(111, new Element(C, 17), new Element(H, 23), new Element(N), new Element(O, 4)),
    gallicacid(112, new Element(C, 7), new Element(H, 17), new Element(O, 5)),
    glucose(113, new Element(C, 6), new Element(H, 12), new Element(O, 6)),
    tannicacid(114, new Molecule(gallicacid, 10), new Molecule(glucose)),
    hperox(115, new Element(H, 2), new Molecule(peroxide)),
    galliumarsenide(116, new Element(Ga), new Element(As)),
    fibroin(117, new Molecule(glycine), new Molecule(serine), new Molecule(glycine), new Molecule(alinine), new Molecule(glycine), new Molecule(alinine)),
    aluminiumPhosphate(118, new Element(Al), new Molecule(phosphate)),
    potassiumOxide(119, new Element(K, 2), new Element(O)),
    sodiumOxide(120, new Element(Na, 2), new Element(O)),
    
    // For underground biomes support
    // http://flexiblelearning.auckland.ac.nz/rocks_minerals/
    plagioclaseAnorthite(121, new Element(Ca), new Element(Al, 2), new Element(Si, 2), new Element(O, 8)),
    plagioclaseAlbite(122, new Element(Na), new Element(Al, 2), new Element(Si, 3), new Element(O, 8)),
    orthoclase(123, new Element(K), new Element(Al), new Element(Si, 3), new Element(O, 8)),
    biotite(124, new Element(K), new Element(Fe, 3), new Element(Al), new Element(Si, 3), new Element(O, 10), new Element(F, 2)),
    augite(125, new Element(Na), new Element(Fe), new Element(Al, 2), new Element(O, 6)),
    talc(126, new Element(Mg, 3), new Element(Si, 4), new Element(O, 10)),
    
    // Metallurgy
    propane(127, new Element(C, 3), new Element(H, 8)),
    peridot(128, new Element(Mg, 2), new Element(O, 4), new Element(Si)),
    topaz(129, new Element(Al, 2), new Element(O, 4), new Element(F, 2)),
    zoisite(130, new Element(Ca, 2), new Element(Al, 3), new Element(Si, 3), new Element(O, 13), new Element(H)),

    //
    cysteine(131, new Element(C, 3), new Element(H, 7), new Element(N), new Element(O, 2), new Element(S)),
    threonine(132, new Element(C, 4), new Element(H, 9), new Element(N), new Element(O, 3)),
    lysine(133, new Element(C, 6), new Element(H, 14), new Element(N, 2), new Element(O, 2)),
    methionine(134, new Element(C, 5), new Element(H, 11), new Element(N), new Element(O, 2), new Element(S)),
    tyrosine(135, new Element(C, 9), new Element(H, 11), new Element(N), new Element(O, 3)),
    histidine(136, new Element(C, 6), new Element(H, 9), new Element(N, 3), new Element(O, 2)),
    phenylalanine(137, new Element(C, 9), new Element(H, 11), new Element(N), new Element(O, 2)),
    glutamine(138, new Element(C, 5), new Element(H, 10), new Element(N, 2), new Element(O, 3)),
    proline(139, new Element(C, 5), new Element(H, 9), new Element(N), new Element(O, 2)),
    leucine(140, new Element(C, 6), new Element(H, 13), new Element(N), new Element(O, 2)),
    tryptophan(141, new Element(C, 11), new Element(H, 12), new Element(N, 2), new Element(O, 2)),
    aspartate(142, new Element(C, 4), new Element(H, 7), new Element(N), new Element(O, 4)),
    isoleucine(143, new Element(C, 6), new Element(H, 13), new Element(N), new Element(O, 2)),
    glutamates(144, new Element(C, 5), new Element(H, 9), new Element(N), new Element(O, 4)),
    asparagine(145, new Element(C, 4), new Element(H, 8), new Element(N, 2), new Element(O, 3)),
    keratin(146, new Molecule(threonine), new Molecule(cysteine), new Molecule(proline), new Molecule(threonine), new Molecule(proline), new Molecule(cysteine), new Molecule(proline));
      
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

    //* Allows full definition of a given molecule down to even the colors that will be used. */
    MoleculeEnum(int id, float colorRed, float colorGreen, float colorBlue, float colorRed2, float colorGreen2, float colorBlue2, PotionChemical... chemicals) {
        this.id = id;
        this.components = new ArrayList<PotionChemical>();
        this.localizationKey = "minechem.molecule." + name();
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
    }

    /** Used to give random colors for elements so they don't have to be manually specified. */
    MoleculeEnum(int id, PotionChemical... chemicals) 
    {
        this(id, getRandomColor(), getRandomColor(), getRandomColor(), getRandomColor(), getRandomColor(), getRandomColor(), chemicals);
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
