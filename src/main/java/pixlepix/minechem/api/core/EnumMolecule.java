package pixlepix.minechem.api.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import net.minecraft.util.StatCollector;
import static pixlepix.minechem.api.core.EnumElement.*;

// import pixlepix.minechem.api.recipe.DecomposerRecipe;
// import pixlepix.minechem.api.recipe.SynthesisRecipe;

// MOLECULE IDS MUST BE CONTINIOUS OTHERWISE THE ARRAY WILL BE MISALIGNED.

public enum EnumMolecule
{
    cellulose(0, "Cellulose", 0, 1, 0, 0, 0.25F, 0, new Element(C, 6), new Element(H, 10), new Element(O, 5)), water(1, "Water", 0, 0, 1, 0, 0, 1, new Element(H, 2), new Element(O)), carbonDioxide(2, "Carbon Dioxide", 0.5F, 0.5F, 0.5F, 0.25F, 0.25F,
            0.25F, new Element(C), new Element(O, 2)), nitrogenDioxide(3, "Nitrogen Dioxide", 1, 0.65F, 0, 0.5F, 0.1412F, 0.1843F, new Element(N), new Element(O, 2)), toluene(4, "Toluene", 1, 1, 1, 0.8F, 0.8F, 0.8F, new Element(C, 7), new Element(H, 8)), potassiumNitrate(
            5, "Potassium Nitrate", 0.9F, 0.9F, 0.9F, 0.8F, 0.8F, 0.8F, new Element(K), new Element(N), new Element(O, 3)), tnt(6, "Trinitrotoluene", 1, 1, 0, 1, 0.65F, 0, new Element(C, 6), new Element(H, 2), new Molecule(nitrogenDioxide, 3),
            new Molecule(toluene)), siliconDioxide(7, "Silicon Dioxide", 1, 1, 1, 1, 1, 1, new Element(Si), new Element(O, 2)), calcite(8, "Calcite", new Element(Ca), new Element(C), new Element(O, 3)), // TODO: Remove this as Calcium Carbonate is the
                                                                                                                                                                                                           // exact same
    pyrite(9, "Pyrite", new Element(Fe), new Element(S, 2)), nepheline(10, "Nepheline", new Element(Al), new Element(Si), new Element(O, 4)), sulfate(11, "Sulfate (ion)", new Element(S), new Element(O, 4)), noselite(12, "Noselite", new Element(Na, 8),
            new Molecule(nepheline, 6), new Molecule(sulfate)), sodalite(13, "Sodalite", new Element(Na, 8), new Molecule(nepheline, 6), new Element(Cl, 2)), nitrate(14, "Nitrate (ion)", new Element(N), new Element(O, 3)), carbonate(15, "Carbonate (ion)",
            new Element(C), new Element(O, 3)), cyanide(16, "Potassium Cyanide", new Element(K), new Element(C), new Element(N)), phosphate(17, "Phosphate (ion)", new Element(P), new Element(O, 4)), acetate(18, "Acetate (ion)", new Element(C, 2),
            new Element(H, 3), new Element(O, 2)), chromate(19, "Chromate (ion)", new Element(Cr), new Element(O, 4)), hydroxide(20, "Hydroxide (ion)", new Element(O), new Element(H)), ammonium(21, "Ammonium (ion)", new Element(N), new Element(H, 4)), hydronium(
            22, "Hydronium (ion)", new Element(H, 3), new Element(O)), peroxide(23, "Peroxide (ion)", new Element(O, 2)), calciumOxide(24, "Calcium Oxide", new Element(Ca), new Element(O)), calciumCarbonate(25, "Calcium Carbonate", new Element(Ca),
            new Molecule(carbonate)), magnesiumCarbonate(26, "Magnesium Carbonate", new Element(Mg), new Molecule(carbonate)), lazurite(27, "Lazurite", new Element(Na, 8), new Molecule(nepheline), new Molecule(sulfate)), isoprene(28, "Isoprene",
            new Element(C, 5), new Element(H, 8)), butene(29, "Butene", new Element(C, 4), new Element(H, 8)), polyisobutylene(30, "Polyisobutylene Rubber", new Molecule(butene, 16), new Molecule(isoprene)), malicAcid(31, "Malic Acid", new Element(C, 4),
            new Element(H, 6), new Element(O, 5)), vinylChloride(32, "Vinyl Chloride Monomer", new Element(C, 2), new Element(H, 3), new Element(Cl)), polyvinylChloride(33, "Polyvinyl Chloride", new Molecule(vinylChloride, 64)), methamphetamine(34,
            "Methamphetamine", new Element(C, 10), new Element(H, 15), new Element(N)), psilocybin(35, "Psilocybin", new Element(C, 12), new Element(H, 17), new Element(N, 2), new Element(O, 4), new Element(P)), iron3oxide(36, "Iron (III) Oxide",
            new Element(Fe, 2), new Element(O, 3)), strontiumNitrate(37, "Strontium Nitrate", new Element(Sr), new Molecule(nitrate, 2)), magnetite(38, "Magnetite", new Element(Fe, 3), new Element(O, 4)), magnesiumOxide(39, "Magnesium Oxide", new Element(
            Mg), new Element(O)), cucurbitacin(40, "Cucurbitacin", new Element(C, 30), new Element(H, 42), new Element(O, 7)), asparticAcid(41, "Aspartic Acid", new Element(C, 4), new Element(H, 7), new Element(N), new Element(O, 4)), hydroxylapatite(42,
            "Hydroxylapatite", new Element(Ca, 5), new Molecule(phosphate, 3), new Element(O), new Element(H)), alinine(43, "Alanine (Amino acid)", new Element(C, 3), new Element(H, 7), new Element(N), new Element(O, 2)), glycine(44,
            "Glycine (Amino acid)", new Element(C, 2), new Element(H, 5), new Element(N), new Element(O, 2)), serine(45, "Serine  (Amino acid)", new Element(C, 3), new Element(H, 7), new Molecule(nitrate)), mescaline(46, "Mescaline", new Element(C, 11),
            new Element(H, 17), new Molecule(nitrate)), methyl(47, "Methyl (ion)", new Element(C), new Element(H, 3)), methylene(48, "Methylene (ion)", new Element(C), new Element(H, 2)), memethacrylate(49, "Methyl methacrylate", new Molecule(methyl, 3),
            new Element(C, 2), new Element(O, 2)), pmma(50, "Poly(methyl methacrylate)", new Molecule(memethacrylate, 3)), // The amount of hydrogens is not 100% right for the polymerized form. But its no big deal.
    redPigment(51, "Cobalt(II) nitrate", new Element(Co), new Molecule(nitrate, 2)), orangePigment(52, "Potassium Dichromate", new Element(K, 2), new Element(Cr, 2), new Element(O, 7)), yellowPigment(53, "Potassium Chromate", new Element(Cr), new Element(
            K, 2), new Element(O, 4)), limePigment(54, "Nickel(II) Chloride", new Element(Ni), new Element(Cl, 2)), lightbluePigment(55, "Copper(II) Sulfate", new Element(Cu), new Molecule(sulfate)), purplePigment(56, "Potassium Permanganate",
            new Element(K), new Element(Mn), new Element(O, 4)), greenPigment(57, "Zinc Green", new Element(Co), new Element(Zn), new Element(O, 2)), blackPigment(58, "Carbon Black", new Element(C), new Element(H, 2), new Element(O)), whitePigment(59,
            "Titanium Dioxide", new Element(Ti), new Element(O, 2)), metasilicate(60, "Metasilicate", new Element(Si), new Element(O, 3)), beryl(61, "Beryl", new Element(Be, 3), new Element(Al, 2), new Molecule(metasilicate, 6)), ethanol(62, "Ethanol",
            new Element(C, 2), new Element(H, 5), new Molecule(hydroxide)), amphetamine(63, "Amphetamine", new Element(C, 9), new Element(H, 13), new Element(N)), theobromine(64, "Theobromine", new Element(C, 7), new Element(H, 8), new Element(N, 4),
            new Element(O, 2)), starch(65, "Starch", new Molecule(cellulose, 3)), sucrose(66, "Sucrose", new Element(C, 12), new Element(H, 22), new Element(O, 11)), pantherine(67, "Pantherine", new Element(C, 4), new Element(H, 6), new Element(N, 2),
            new Element(O, 2)), aluminiumOxide(68, "Aluminium Oxide", new Element(Al, 2), new Element(O, 3)), fullrene(69, "Carbon Nanotubes", 0.47F, 0.47F, 0.47F, 0.47F, 0.47F, 0.47F, new Element(C, 64), new Element(C, 64), new Element(C, 64),
            new Element(C, 64)), valine(70, "Valine (Amino acid)", new Element(C, 5), new Element(H, 11), new Element(N), new Element(O, 2)), penicillin(71, "Penicillin", new Element(C, 16), new Element(H, 18), new Element(N, 2), new Element(O, 4),
            new Element(S)), testosterone(72, "Testosterone", new Element(C, 19), new Element(H, 28), new Element(O, 2)), kaolinite(73, "Kaolinite", new Element(Al, 2), new Element(Si, 2), new Element(O, 5), new Molecule(hydroxide, 4)), fingolimod(74,
            "Fingolimod", new Element(C, 19), new Element(H, 33), new Molecule(nitrogenDioxide)), arginine(75, "Arginine (Amino acid)", new Element(C, 6), new Element(H, 14), new Element(N, 4), new Element(O, 2)), shikimicAcid(76, "Shikimic Acid",
            new Element(C, 7), new Element(H, 10), new Element(O, 5)), sulfuricAcid(77, "Sulfuric Acid", new Element(H, 2), new Element(S), new Element(O, 4)), glyphosate(78, "Glyphosate", new Element(C, 3), new Element(H, 8), new Element(N), new Element(
            O, 5), new Element(P)), asprin(79, "Aspirin", new Element(C, 9), new Element(H, 8), new Element(O, 4)), ddt(80, "DDT", new Element(C, 14), new Element(H, 9), new Element(Cl, 5)), dota(81, "DOTA", new Element(C, 16), new Element(H, 28),
            new Element(N, 4), new Element(O, 8)), mycotoxin(82, "T-2 Mycotoxin", 0.89F, 0.83F, 0.07F, 0.89F, 0.83F, 0.07F, new Element(C, 24), new Element(H, 34), new Element(O, 9)), salt(83, "Salt", new Element(Na), new Element(Cl)), nhthree(84,
            "Ammonia", new Element(N), new Element(H, 3)), nod(85, "Nodularin", new Element(C, 41), new Element(H, 60), new Element(N, 8), new Element(O, 10)), ttx(86, "TTX (Tetrodotoxin)", new Element(C, 11), new Element(H, 11), new Element(N, 3),
            new Element(O, 8)), afroman(87, "THC", new Element(C, 21), new Element(H, 30), new Element(O, 2)), mt(88, "Methylcyclopentadienyl Manganese Tricarbonyl", new Element(C, 9), new Element(H, 7), new Element(Mn, 1), new Element(O, 3)), // Level 1
    buli(89, "Tert-Butyllithium", new Element(Li), new Element(C, 4), new Element(H, 9)), // Level 2
    plat(90, "Chloroplatinic acid", new Element(H, 2), new Element(Pt, 1), new Element(Cl, 6)), // Level 3
    phosgene(91, "Phosgene", new Element(C), new Element(O), new Element(Cl, 2)), aalc(92, "Allyl alcohol", new Element(C, 3), new Element(H, 5), new Molecule(hydroxide)), hist(93, "Diphenhydramine", new Element(C, 17), new Element(H, 21), new Element(N),
            new Element(O)), pal2(94, "Batrachotoxin", new Element(C, 31), new Element(H, 42), new Element(N, 2), new Element(O, 6)), retinol(95, "Retinol", new Element(C, 20), new Element(H, 29), new Molecule(hydroxide)), xylitol(96, "Xylitol",
            new Element(C, 5), new Element(H, 12), new Element(O, 5)), weedex(97, "Aminocyclopyrachlor", new Element(C, 8), new Element(H, 8), new Element(Cl), new Element(N, 3), new Element(O, 2)), xanax(98, "Alprazolam", new Element(C, 17), new Element(
            H, 13), new Element(Cl), new Element(N, 4)), hcl(99, "Hydrogen Chloride", new Element(H), new Element(Cl)), cocaine(100, "Cocaine", new Element(C, 17), new Element(H, 21), new Element(N), new Element(O, 4)), cocainehcl(101,
            "Cocaine Hydrochloride", new Molecule(cocaine), new Molecule(hcl)), blueorgodye(102, "Guaiazulene", new Element(C, 15), new Element(H, 18)), redorgodye(103, "Pelargonidin", new Element(C, 15), new Element(H, 11), new Element(O, 11)), purpleorgodye(
            104, "Delphinidin", new Element(C, 15), new Element(H, 11), new Element(O, 7)), olivine(105, "Olivine", new Element(Fe, 2), new Element(Si), new Element(O, 4)), metblue(106, "Methylene Blue", new Element(C, 16), new Element(H, 18),
            new Element(N, 3), new Element(S), new Element(Cl)), meoh(107, "Methanol", new Molecule(methyl), new Molecule(hydroxide)), lcd(108, "Cholesteryl benzoate", new Element(C, 34), new Element(H, 50), new Element(O, 2)), radchlor(109,
            "Radium Chloride", new Element(Ra), new Element(Cl, 2)), caulerpenyne(110, "Caulerpenyne", new Element(C, 21), new Element(H, 26), new Element(O, 6)), latropine(111, "Hyoscyamine", new Element(C, 17), new Element(H, 23), new Element(N),
            new Element(O, 4)), gallicacid(112, "Gallic Acid", new Element(C, 7), new Element(H, 17), new Element(O, 5)), glucose(113, "Glucose", new Element(C, 6), new Element(H, 12), new Element(O, 6)), tannicacid(114, "Tannic Acid", new Molecule(
            gallicacid, 10), new Molecule(glucose)), hperox(115, "Hydrogen Peroxide", new Element(H, 2), new Molecule(peroxide)), galliumarsenide(116, "Gallium Arsenide", new Element(Ga), new Element(As)), fibroin(117, "Fibroin", new Molecule(glycine),
            new Molecule(serine), new Molecule(glycine), new Molecule(alinine), new Molecule(glycine), new Molecule(alinine)), aluminiumPhosphate(118, "Aluminium Phosphate", new Element(Al), new Molecule(phosphate)), potassiumOxide(119, "Potassium oxide",
            new Element(K, 2), new Element(O)), sodiumOxide(120, "Sodium oxide", new Element(Na, 2), new Element(O)),
    // For underground biomes support
    // http://flexiblelearning.auckland.ac.nz/rocks_minerals/
    plagioclaseAnorthite(121, "Anorthite", new Element(Ca), new Element(Al, 2), new Element(Si, 2), new Element(O, 8)), plagioclaseAlbite(122, "Albite", new Element(Na), new Element(Al, 2), new Element(Si, 3), new Element(O, 8)), orthoclase(123,
            "Orthoclase", new Element(K), new Element(Al), new Element(Si, 3), new Element(O, 8)), biotite(124, "Biotite", new Element(K), new Element(Fe, 3), new Element(Al), new Element(Si, 3), new Element(O, 10), new Element(F, 2)), augite(125,
            "Augite", new Element(Na), new Element(Fe), new Element(Al, 2), new Element(O, 6)), talc(126, "Talc", new Element(Mg, 3), new Element(Si, 4), new Element(O, 10)),
    // Metallurgy
    propane(127, "Propane", new Element(C, 3), new Element(H, 8)), peridot(128, "Peridot", new Element(Mg, 2), new Element(O, 4), new Element(Si)), topaz(129, "Topaz", new Element(Al, 2), new Element(O, 4), new Element(F, 2)), zoisite(130, "Zoisite",
            new Element(Ca, 2), new Element(Al, 3), new Element(Si, 3), new Element(O, 13), new Element(H)),
    //
    cysteine(131, "Cysteine (Amino acid)", new Element(C, 3), new Element(H, 7), new Element(N), new Element(O, 2), new Element(S)), threonine(132, "Threonine (Amino acid)", new Element(C, 4), new Element(H, 9), new Element(N), new Element(O, 3)), lysine(
            133, "Lysine (Amino acid)", new Element(C, 6), new Element(H, 14), new Element(N, 2), new Element(O, 2)), methionine(134, "Methionine (Amino acid)", new Element(C, 5), new Element(H, 11), new Element(N), new Element(O, 2), new Element(S)), tyrosine(
            135, "Tyrosine (Amino acid)", new Element(C, 9), new Element(H, 11), new Element(N), new Element(O, 3)), histidine(136, "Histidine (Amino acid)", new Element(C, 6), new Element(H, 9), new Element(N, 3), new Element(O, 2)), phenylalanine(137,
            "Phenylalanine (Amino acid)", new Element(C, 9), new Element(H, 11), new Element(N), new Element(O, 2)), glutamine(138, "Glutamine (Amino acid)", new Element(C, 5), new Element(H, 10), new Element(N, 2), new Element(O, 3)), proline(139,
            "Proline (Amino acid)", new Element(C, 5), new Element(H, 9), new Element(N), new Element(O, 2)), leucine(140, "Leucine (Amino acid)", new Element(C, 6), new Element(H, 13), new Element(N), new Element(O, 2)), tryptophan(141,
            "Tryptophan (Amino acid)", new Element(C, 11), new Element(H, 12), new Element(N, 2), new Element(O, 2)), aspartate(142, "Aspartic acid (Amino acid)", new Element(C, 4), new Element(H, 7), new Element(N), new Element(O, 4)), isoleucine(143,
            "Isoleucine (Amino acid)", new Element(C, 6), new Element(H, 13), new Element(N), new Element(O, 2)), glutamates(144, "Glutamic acid (Amino acid)", new Element(C, 5), new Element(H, 9), new Element(N), new Element(O, 4)), asparagine(145,
            "Asparagine(Amino acid)", new Element(C, 4), new Element(H, 8), new Element(N, 2), new Element(O, 3)), keratin(146, "Keratin (Peptide)", new Molecule(threonine), new Molecule(cysteine), new Molecule(proline), new Molecule(threonine),
            new Molecule(proline, 2), new Molecule(cysteine, 3), new Molecule(proline), new Molecule(threonine), new Molecule(cysteine), new Molecule(proline));
    //
    public static EnumMolecule[] molecules = values();
    // Descriptive name, in en_US. Should not be used; instead, use a
    // localized string from a .properties file.
    private final String descriptiveName; // TODO: Update all language files to reflect most recent changes.
    // Localization key.
    private final String localizationKey;
    private final ArrayList<Chemical> components;
    private int id;
    public float red;
    public float green;
    public float blue;
    public float red2;
    public float green2;
    public float blue2;

    EnumMolecule(int id, String descriptiveName, float colorRed, float colorGreen, float colorBlue, float colorRed2, float colorGreen2, float colorBlue2, Chemical... chemicals)
    {
        this.id = id;
        this.components = new ArrayList<Chemical>();
        this.descriptiveName = descriptiveName;
        this.localizationKey = "minechem.molecule." + name();
        for (Chemical chemical : chemicals)
        {
            this.components.add(chemical);
        }
        this.red = colorRed;
        this.green = colorGreen;
        this.blue = colorBlue;
        this.red2 = colorRed2;
        this.green2 = colorGreen2;
        this.blue2 = colorBlue2;
    }

    @Deprecated
    // Why?
    EnumMolecule(int id, String descriptiveName, Chemical... chemicals)
    {
        this(id, descriptiveName, getRandomColor(), getRandomColor(), getRandomColor(), getRandomColor(), getRandomColor(), getRandomColor(), chemicals);
        // Your molecule will have random colors until you give it a proper color code.
    }

    private static float getRandomColor()
    {
        Random random = new Random();
        return random.nextFloat();
    }

    public int getSize()
    {
        int result = 0;

        Iterator iter = this.components().iterator();

        while (iter.hasNext())
        {
            result += ((Chemical) iter.next()).amount;
        }

        return result;
    }

    public static EnumMolecule getById(int id)
    {
        for (EnumMolecule molecule : molecules)
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

    /** Returns the localized name of this molecule, or an en_US-based placeholder if no localization was found.
     * 
     * @return Localized name of this molecule. */
    public String descriptiveName()
    {
        String localizedName = StatCollector.translateToLocal(this.localizationKey);
        if (localizedName.isEmpty())
        {
            return localizationKey;
        }
        return localizedName;
    }

    public ArrayList<Chemical> components()
    {
        return this.components;
    }

}
