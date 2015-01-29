package minechem.item.molecule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import minechem.MinechemItemsRegistration;
import minechem.MinechemRecipes;
import minechem.fluid.FluidHelper;
import minechem.item.ChemicalRoomStateEnum;
import static minechem.item.ChemicalRoomStateEnum.gas;
import static minechem.item.ChemicalRoomStateEnum.liquid;
import static minechem.item.ChemicalRoomStateEnum.solid;
import minechem.item.MinechemChemicalType;
import minechem.item.element.Element;
import static minechem.item.element.ElementEnum.Al;
import static minechem.item.element.ElementEnum.As;
import static minechem.item.element.ElementEnum.Be;
import static minechem.item.element.ElementEnum.C;
import static minechem.item.element.ElementEnum.Ca;
import static minechem.item.element.ElementEnum.Cl;
import static minechem.item.element.ElementEnum.Co;
import static minechem.item.element.ElementEnum.Cr;
import static minechem.item.element.ElementEnum.Cs;
import static minechem.item.element.ElementEnum.Cu;
import static minechem.item.element.ElementEnum.F;
import static minechem.item.element.ElementEnum.Fe;
import static minechem.item.element.ElementEnum.Fr;
import static minechem.item.element.ElementEnum.Ga;
import static minechem.item.element.ElementEnum.H;
import static minechem.item.element.ElementEnum.K;
import static minechem.item.element.ElementEnum.Li;
import static minechem.item.element.ElementEnum.Mg;
import static minechem.item.element.ElementEnum.Mn;
import static minechem.item.element.ElementEnum.N;
import static minechem.item.element.ElementEnum.Na;
import static minechem.item.element.ElementEnum.Ni;
import static minechem.item.element.ElementEnum.O;
import static minechem.item.element.ElementEnum.P;
import static minechem.item.element.ElementEnum.Pt;
import static minechem.item.element.ElementEnum.Ra;
import static minechem.item.element.ElementEnum.Rb;
import static minechem.item.element.ElementEnum.S;
import static minechem.item.element.ElementEnum.Si;
import static minechem.item.element.ElementEnum.Sr;
import static minechem.item.element.ElementEnum.Ti;
import static minechem.item.element.ElementEnum.Zn;
import minechem.potion.PotionChemical;
import minechem.radiation.RadiationEnum;
import minechem.tileentity.decomposer.DecomposerRecipe;
import minechem.tileentity.synthesis.SynthesisRecipe;
import net.minecraft.item.ItemStack;

public class MoleculeEnum extends MinechemChemicalType
{
    public static Map<Integer, MoleculeEnum> molecules = new LinkedHashMap<Integer, MoleculeEnum>();
    public static Map<String, MoleculeEnum> nameToMolecules = new HashMap<String, MoleculeEnum>();
    public static int baseMolecules = 172;

    public static final MoleculeEnum cellulose = addMolecule("cellulose", 0, 0, 1, 0, 0, 0.25F, 0, solid, new Element(C, 6), new Element(H, 10), new Element(O, 5));
    public static final MoleculeEnum water = addMolecule("water", 1, 0, 0, 1, 0, 0, 1, liquid, new Element(H, 2), new Element(O));
    public static final MoleculeEnum carbonDioxide = addMolecule("carbonDioxide", 2, 0.5F, 0.5F, 0.5F, 0.25F, 0.25F, 0.25F, gas, new Element(C), new Element(O, 2));
    public static final MoleculeEnum nitrogenDioxide = addMolecule("nitrogenDioxide", 3, 1, 0.65F, 0, 0.5F, 0.1412F, 0.1843F, gas, new Element(N), new Element(O, 2));
    public static final MoleculeEnum toluene = addMolecule("toluene", 4, 1, 1, 1, 0.8F, 0.8F, 0.8F, liquid, new Element(C, 7), new Element(H, 8));
    public static final MoleculeEnum potassiumNitrate = addMolecule("potassiumNitrate", 5, 0.9F, 0.9F, 0.9F, 0.8F, 0.8F, 0.8F, solid, new Element(K), new Element(N), new Element(O, 3));
    public static final MoleculeEnum tnt = addMolecule("tnt", 6, 1, 1, 0, 1, 0.65F, 0, solid, new Element(C, 6), new Element(H, 2), new Molecule(nitrogenDioxide, 3), new Molecule(toluene));
    public static final MoleculeEnum siliconDioxide = addMolecule("siliconDioxide", 7, 1, 1, 1, 1, 1, 1, solid, new Element(Si), new Element(O, 2));
    // Pulled from bottom to replace calcite, which was a duplicate of another molecules
    public static final MoleculeEnum calcicPyroxene = addMolecule("calcicPyroxene", 8, solid, new Element(Ca, 1), new Element(Cr, 1), new Element(Si, 2), new Element(O, 6));
    public static final MoleculeEnum pyrite = addMolecule("pyrite", 9, solid, new Element(Fe), new Element(S, 2));
    public static final MoleculeEnum nepheline = addMolecule("nepheline", 10, solid, new Element(Al), new Element(Si), new Element(O, 4));
    public static final MoleculeEnum sulfate = addMolecule("sulfate", 11, solid, new Element(S), new Element(O, 4));
    public static final MoleculeEnum noselite = addMolecule("noselite", 12, solid, new Element(Na, 8), new Molecule(nepheline, 6), new Molecule(sulfate));
    public static final MoleculeEnum sodalite = addMolecule("sodalite", 13, solid, new Element(Na, 8), new Molecule(nepheline, 6), new Element(Cl, 2));
    public static final MoleculeEnum nitrate = addMolecule("nitrate", 14, solid, new Element(N), new Element(O, 3));
    public static final MoleculeEnum carbonate = addMolecule("carbonate", 15, solid, new Element(C), new Element(O, 3));
    public static final MoleculeEnum cyanide = addMolecule("cyanide", 16, liquid, new Element(K), new Element(C), new Element(N));
    public static final MoleculeEnum phosphate = addMolecule("phosphate", 17, solid, new Element(P), new Element(O, 4));
    public static final MoleculeEnum acetate = addMolecule("acetate", 18, solid, new Element(C, 2), new Element(H, 3), new Element(O, 2));
    public static final MoleculeEnum chromate = addMolecule("chromate", 19, solid, new Element(Cr), new Element(O, 4));
    public static final MoleculeEnum hydroxide = addMolecule("hydroxide", 20, liquid, new Element(O), new Element(H));
    public static final MoleculeEnum ammonium = addMolecule("ammonium", 21, liquid, new Element(N), new Element(H, 4));
    public static final MoleculeEnum hydronium = addMolecule("hydronium", 22, liquid, new Element(H, 3), new Element(O));
    public static final MoleculeEnum peroxide = addMolecule("peroxide", 23, liquid, new Element(O, 2));
    public static final MoleculeEnum calciumOxide = addMolecule("calciumOxide", 24, solid, new Element(Ca), new Element(O));
    public static final MoleculeEnum calciumCarbonate = addMolecule("calciumCarbonate", 25, solid, new Element(Ca), new Molecule(carbonate));
    public static final MoleculeEnum magnesiumCarbonate = addMolecule("magnesiumCarbonate", 26, solid, new Element(Mg), new Molecule(carbonate));
    public static final MoleculeEnum lazurite = addMolecule("lazurite", 27, solid, new Element(Na, 8), new Molecule(nepheline), new Molecule(sulfate));
    public static final MoleculeEnum isoprene = addMolecule("isoprene", 28, solid, new Element(C, 5), new Element(H, 8));
    public static final MoleculeEnum butene = addMolecule("butene", 29, gas, new Element(C, 4), new Element(H, 8));
    public static final MoleculeEnum polyisobutylene = addMolecule("polyisobutylene", 30, liquid, new Molecule(butene, 16), new Molecule(isoprene));
    public static final MoleculeEnum malicAcid = addMolecule("malicAcid", 31, solid, new Element(C, 4), new Element(H, 6), new Element(O, 5));
    public static final MoleculeEnum vinylChloride = addMolecule("vinylChloride", 32, gas, new Element(C, 2), new Element(H, 3), new Element(Cl));
    public static final MoleculeEnum polyvinylChloride = addMolecule("polyvinylChloride", 33, solid, new Molecule(vinylChloride, 64));
    public static final MoleculeEnum methamphetamine = addMolecule("methamphetamine", 34, solid, new Element(C, 10), new Element(H, 15), new Element(N));
    public static final MoleculeEnum psilocybin = addMolecule("psilocybin", 35, solid, new Element(C, 12), new Element(H, 17), new Element(N, 2), new Element(O, 4), new Element(P));
    public static final MoleculeEnum iron3oxide = addMolecule("iron3oxide", 36, solid, new Element(Fe, 2), new Element(O, 3));
    public static final MoleculeEnum strontiumNitrate = addMolecule("strontiumNitrate", 37, solid, new Element(Sr), new Molecule(nitrate, 2));
    public static final MoleculeEnum magnetite = addMolecule("magnetite", 38, solid, new Element(Fe, 3), new Element(O, 4));
    public static final MoleculeEnum magnesiumOxide = addMolecule("magnesiumOxide", 39, solid, new Element(Mg), new Element(O));
    public static final MoleculeEnum cucurbitacin = addMolecule("cucurbitacin", 40, solid, new Element(C, 30), new Element(H, 42), new Element(O, 7));
    public static final MoleculeEnum asparticAcid = addMolecule("asparticAcid", 41, solid, new Element(C, 4), new Element(H, 7), new Element(N), new Element(O, 4));
    public static final MoleculeEnum hydroxylapatite = addMolecule("hydroxylapatite", 42, solid, new Element(Ca, 5), new Molecule(phosphate, 3), new Element(O), new Element(H));
    public static final MoleculeEnum alinine = addMolecule("alinine", 43, solid, new Element(C, 3), new Element(H, 7), new Element(N), new Element(O, 2));
    public static final MoleculeEnum glycine = addMolecule("glycine", 44, solid, new Element(C, 2), new Element(H, 5), new Element(N), new Element(O, 2));
    public static final MoleculeEnum serine = addMolecule("serine", 45, solid, new Element(C, 3), new Element(H, 7), new Molecule(nitrate));
    public static final MoleculeEnum mescaline = addMolecule("mescaline", 46, solid, new Element(C, 11), new Element(H, 17), new Molecule(nitrate));
    public static final MoleculeEnum methyl = addMolecule("methyl", 47, liquid, new Element(C), new Element(H, 3));
    public static final MoleculeEnum methylene = addMolecule("methylene", 48, liquid, new Element(C), new Element(H, 2));
    public static final MoleculeEnum memethacrylate = addMolecule("memethacrylate", 49, liquid, new Molecule(methyl, 3), new Element(C, 2), new Element(O, 2));
    public static final MoleculeEnum pmma = addMolecule("pmma", 50, solid, new Molecule(memethacrylate, 3)); // The amount of hydrogens is not 100% right for the polymerized form. But its no big deal.
    public static final MoleculeEnum redPigment = addMolecule("redPigment", 51, solid, new Element(Co), new Molecule(nitrate, 2));
    public static final MoleculeEnum orangePigment = addMolecule("orangePigment", 52, solid, new Element(K, 2), new Element(Cr, 2), new Element(O, 7));
    public static final MoleculeEnum yellowPigment = addMolecule("yellowPigment", 53, solid, new Element(Cr), new Element(K, 2), new Element(O, 4));
    public static final MoleculeEnum limePigment = addMolecule("limePigment", 54, solid, new Element(Ni), new Element(Cl, 2));
    public static final MoleculeEnum lightbluePigment = addMolecule("lightbluePigment", 55, solid, new Element(Cu), new Molecule(sulfate));
    public static final MoleculeEnum purplePigment = addMolecule("purplePigment", 56, solid, new Element(K), new Element(Mn), new Element(O, 4));
    public static final MoleculeEnum greenPigment = addMolecule("greenPigment", 57, solid, new Element(Co), new Element(Zn), new Element(O, 2));
    public static final MoleculeEnum blackPigment = addMolecule("blackPigment", 58, gas, new Element(C), new Element(H, 2), new Element(O));
    public static final MoleculeEnum whitePigment = addMolecule("whitePigment", 59, solid, new Element(Ti), new Element(O, 2));
    public static final MoleculeEnum metasilicate = addMolecule("metasilicate", 60, solid, new Element(Si), new Element(O, 3));
    public static final MoleculeEnum beryl = addMolecule("beryl", 61, solid, new Element(Be, 3), new Element(Al, 2), new Molecule(metasilicate, 6));
    public static final MoleculeEnum ethanol = addMolecule("ethanol", 62, liquid, new Element(C, 2), new Element(H, 5), new Molecule(hydroxide));
    public static final MoleculeEnum amphetamine = addMolecule("amphetamine", 63, liquid, new Element(C, 9), new Element(H, 13), new Element(N));
    public static final MoleculeEnum theobromine = addMolecule("theobromine", 64, solid, new Element(C, 7), new Element(H, 8), new Element(N, 4), new Element(O, 2));
    public static final MoleculeEnum starch = addMolecule("starch", 65, solid, new Molecule(cellulose, 3));
    public static final MoleculeEnum sucrose = addMolecule("sucrose", 66, solid, new Element(C, 12), new Element(H, 22), new Element(O, 11));
    public static final MoleculeEnum pantherine = addMolecule("pantherine", 67, solid, new Element(C, 4), new Element(H, 6), new Element(N, 2), new Element(O, 2));
    public static final MoleculeEnum aluminiumOxide = addMolecule("aluminiumOxide", 68, solid, new Element(Al, 2), new Element(O, 3));
    public static final MoleculeEnum fullrene = addMolecule("fullrene", 69, 0.47F, 0.47F, 0.47F, 0.47F, 0.47F, 0.47F, solid, new Element(C, 64), new Element(C, 64), new Element(C, 64), new Element(C, 64));
    public static final MoleculeEnum valine = addMolecule("valine", 70, solid, new Element(C, 5), new Element(H, 11), new Element(N), new Element(O, 2));
    public static final MoleculeEnum penicillin = addMolecule("penicillin", 71, solid, new Element(C, 16), new Element(H, 18), new Element(N, 2), new Element(O, 4), new Element(S));
    public static final MoleculeEnum testosterone = addMolecule("testosterone", 72, liquid, new Element(C, 19), new Element(H, 28), new Element(O, 2));
    public static final MoleculeEnum kaolinite = addMolecule("kaolinite", 73, solid, new Element(Al, 2), new Element(Si, 2), new Element(O, 5), new Molecule(hydroxide, 4));
    public static final MoleculeEnum fingolimod = addMolecule("fingolimod", 74, solid, new Element(C, 19), new Element(H, 33), new Molecule(nitrogenDioxide));
    public static final MoleculeEnum arginine = addMolecule("arginine", 75, solid, new Element(C, 6), new Element(H, 14), new Element(N, 4), new Element(O, 2));
    public static final MoleculeEnum shikimicAcid = addMolecule("shikimicAcid", 76, solid, new Element(C, 7), new Element(H, 10), new Element(O, 5));
    public static final MoleculeEnum sulfuricAcid = addMolecule("sulfuricAcid", 77, liquid, new Element(H, 2), new Element(S), new Element(O, 4));
    public static final MoleculeEnum glyphosate = addMolecule("glyphosate", 78, liquid, new Element(C, 3), new Element(H, 8), new Element(N), new Element(O, 5), new Element(P));
    public static final MoleculeEnum asprin = addMolecule("asprin", 79, solid, new Element(C, 9), new Element(H, 8), new Element(O, 4));
    public static final MoleculeEnum ddt = addMolecule("ddt", 80, solid, new Element(C, 14), new Element(H, 9), new Element(Cl, 5));
    public static final MoleculeEnum dota = addMolecule("dota", 81, solid, new Element(C, 16), new Element(H, 28), new Element(N, 4), new Element(O, 8));
    public static final MoleculeEnum mycotoxin = addMolecule("mycotoxin", 82, 0.89F, 0.83F, 0.07F, 0.89F, 0.83F, 0.07F, solid, new Element(C, 24), new Element(H, 34), new Element(O, 9));
    public static final MoleculeEnum salt = addMolecule("salt", 83, solid, new Element(Na), new Element(Cl));
    public static final MoleculeEnum ammonia = addMolecule("ammonia", 84, gas, new Element(N), new Element(H, 3));
    public static final MoleculeEnum nodularin = addMolecule("nodularin", 85, solid, new Element(C, 41), new Element(H, 60), new Element(N, 8), new Element(O, 10));
    public static final MoleculeEnum tetrodotoxin = addMolecule("tetrodotoxin", 86, solid, new Element(C, 11), new Element(H, 11), new Element(N, 3), new Element(O, 8));
    public static final MoleculeEnum thc = addMolecule("thc", 87, solid, new Element(C, 21), new Element(H, 30), new Element(O, 2));
    public static final MoleculeEnum mt = addMolecule("mt", 88, liquid, new Element(C, 9), new Element(H, 7), new Element(Mn, 1), new Element(O, 3)); // Level 1
    public static final MoleculeEnum buli = addMolecule("buli", 89, solid, new Element(Li), new Element(C, 4), new Element(H, 9)); // Level 2
    public static final MoleculeEnum plat = addMolecule("plat", 90, solid, new Element(H, 2), new Element(Pt, 1), new Element(Cl, 6)); // Level 3
    public static final MoleculeEnum phosgene = addMolecule("phosgene", 91, gas, new Element(C), new Element(O), new Element(Cl, 2));
    public static final MoleculeEnum aalc = addMolecule("aalc", 92, liquid, new Element(C, 3), new Element(H, 5), new Molecule(hydroxide));
    public static final MoleculeEnum hist = addMolecule("hist", 93, solid, new Element(C, 17), new Element(H, 21), new Element(N), new Element(O));
    public static final MoleculeEnum pal2 = addMolecule("pal2", 94, solid, new Element(C, 31), new Element(H, 42), new Element(N, 2), new Element(O, 6));
    public static final MoleculeEnum retinol = addMolecule("retinol", 95, solid, new Element(C, 20), new Element(H, 29), new Molecule(hydroxide));
    public static final MoleculeEnum xylitol = addMolecule("xylitol", 96, solid, new Element(C, 5), new Element(H, 12), new Element(O, 5));
    public static final MoleculeEnum weedex = addMolecule("weedex", 97, solid, new Element(C, 8), new Element(H, 8), new Element(Cl), new Element(N, 3), new Element(O, 2));
    public static final MoleculeEnum xanax = addMolecule("xanax", 98, solid, new Element(C, 17), new Element(H, 13), new Element(Cl), new Element(N, 4));
    public static final MoleculeEnum hcl = addMolecule("hcl", 99, liquid, new Element(H), new Element(Cl));
    public static final MoleculeEnum cocaine = addMolecule("cocaine", 100, solid, new Element(C, 17), new Element(H, 21), new Element(N), new Element(O, 4));
    public static final MoleculeEnum cocainehcl = addMolecule("cocainehcl", 101, solid, new Molecule(cocaine), new Molecule(hcl));
    public static final MoleculeEnum blueorgodye = addMolecule("blueorgodye", 102, liquid, new Element(C, 15), new Element(H, 18));
    public static final MoleculeEnum redorgodye = addMolecule("redorgodye", 103, solid, new Element(C, 15), new Element(H, 11), new Element(O, 11));
    public static final MoleculeEnum purpleorgodye = addMolecule("purpleorgodye", 104, solid, new Element(C, 15), new Element(H, 11), new Element(O, 7));
    public static final MoleculeEnum olivine = addMolecule("olivine", 105, solid, new Element(Fe, 2), new Element(Si), new Element(O, 4));
    public static final MoleculeEnum metblue = addMolecule("metblue", 106, solid, new Element(C, 16), new Element(H, 18), new Element(N, 3), new Element(S), new Element(Cl));
    public static final MoleculeEnum meoh = addMolecule("meoh", 107, liquid, new Molecule(methyl), new Molecule(hydroxide));
    public static final MoleculeEnum lcd = addMolecule("lcd", 108, solid, new Element(C, 34), new Element(H, 50), new Element(O, 2));
    public static final MoleculeEnum radchlor = addMolecule("radchlor", 109, solid, new Element(Ra), new Element(Cl, 2));
    public static final MoleculeEnum caulerpenyne = addMolecule("caulerpenyne", 110, solid, new Element(C, 21), new Element(H, 26), new Element(O, 6));
    public static final MoleculeEnum latropine = addMolecule("latropine", 111, solid, new Element(C, 17), new Element(H, 23), new Element(N), new Element(O, 4));
    public static final MoleculeEnum gallicacid = addMolecule("gallicacid", 112, solid, new Element(C, 7), new Element(H, 17), new Element(O, 5));
    public static final MoleculeEnum glucose = addMolecule("glucose", 113, solid, new Element(C, 6), new Element(H, 12), new Element(O, 6));
    public static final MoleculeEnum tannicacid = addMolecule("tannicacid", 114, solid, new Molecule(gallicacid, 10), new Molecule(glucose));
    public static final MoleculeEnum hperox = addMolecule("hperox", 115, liquid, new Element(H, 2), new Molecule(peroxide));
    public static final MoleculeEnum galliumarsenide = addMolecule("galliumarsenide", 116, solid, new Element(Ga), new Element(As));
    public static final MoleculeEnum fibroin = addMolecule("fibroin", 117, liquid, new Molecule(glycine), new Molecule(serine), new Molecule(glycine), new Molecule(alinine), new Molecule(glycine), new Molecule(alinine));
    public static final MoleculeEnum aluminiumPhosphate = addMolecule("aluminiumPhosphate", 118, solid, new Element(Al), new Molecule(phosphate));
    public static final MoleculeEnum potassiumOxide = addMolecule("potassiumOxide", 119, solid, new Element(K, 2), new Element(O));
    public static final MoleculeEnum sodiumOxide = addMolecule("sodiumOxide", 120, solid, new Element(Na, 2), new Element(O));

    // For underground biomes support
    // http://flexiblelearning.auckland.ac.nz/rocks_minerals/
    public static final MoleculeEnum plagioclaseAnorthite = addMolecule("plagioclaseAnorthite", 121, solid, new Element(Ca), new Element(Al, 2), new Element(Si, 2), new Element(O, 8));
    public static final MoleculeEnum plagioclaseAlbite = addMolecule("plagioclaseAlbite", 122, solid, new Element(Na), new Element(Al, 2), new Element(Si, 3), new Element(O, 8));
    public static final MoleculeEnum orthoclase = addMolecule("orthoclase", 123, solid, new Element(K), new Element(Al), new Element(Si, 3), new Element(O, 8));
    public static final MoleculeEnum biotite = addMolecule("biotite", 124, solid, new Element(K), new Element(Fe, 3), new Element(Al), new Element(Si, 3), new Element(O, 10), new Element(F, 2));
    public static final MoleculeEnum augite = addMolecule("augite", 125, solid, new Element(Na), new Element(Fe), new Element(Al, 2), new Element(O, 6));
    public static final MoleculeEnum talc = addMolecule("talc", 126, solid, new Element(Mg, 3), new Element(Si, 4), new Element(O, 10));

    // Metallurgy
    public static final MoleculeEnum propane = addMolecule("propane", 127, gas, new Element(C, 3), new Element(H, 8));
    public static final MoleculeEnum peridot = addMolecule("peridot", 128, solid, new Element(Mg, 2), new Element(O, 4), new Element(Si));
    public static final MoleculeEnum topaz = addMolecule("topaz", 129, solid, new Element(Al, 2), new Element(O, 4), new Element(F, 2));
    public static final MoleculeEnum zoisite = addMolecule("zoisite", 130, solid, new Element(Ca, 2), new Element(Al, 3), new Element(Si, 3), new Element(O, 13), new Element(H));
    //
    public static final MoleculeEnum cysteine = addMolecule("cysteine", 131, solid, new Element(C, 3), new Element(H, 7), new Element(N), new Element(O, 2), new Element(S));
    public static final MoleculeEnum threonine = addMolecule("threonine", 132, solid, new Element(C, 4), new Element(H, 9), new Element(N), new Element(O, 3));
    public static final MoleculeEnum lysine = addMolecule("lysine", 133, solid, new Element(C, 6), new Element(H, 14), new Element(N, 2), new Element(O, 2));
    public static final MoleculeEnum methionine = addMolecule("methionine", 134, solid, new Element(C, 5), new Element(H, 11), new Element(N), new Element(O, 2), new Element(S));
    public static final MoleculeEnum tyrosine = addMolecule("tyrosine", 135, solid, new Element(C, 9), new Element(H, 11), new Element(N), new Element(O, 3));
    public static final MoleculeEnum histidine = addMolecule("histidine", 136, solid, new Element(C, 6), new Element(H, 9), new Element(N, 3), new Element(O, 2));
    public static final MoleculeEnum phenylalanine = addMolecule("phenylalanine", 137, solid, new Element(C, 9), new Element(H, 11), new Element(N), new Element(O, 2));
    public static final MoleculeEnum glutamine = addMolecule("glutamine", 138, solid, new Element(C, 5), new Element(H, 10), new Element(N, 2), new Element(O, 3));
    public static final MoleculeEnum proline = addMolecule("proline", 139, solid, new Element(C, 5), new Element(H, 9), new Element(N), new Element(O, 2));
    public static final MoleculeEnum leucine = addMolecule("leucine", 140, solid, new Element(C, 6), new Element(H, 13), new Element(N), new Element(O, 2));
    public static final MoleculeEnum tryptophan = addMolecule("tryptophan", 141, solid, new Element(C, 11), new Element(H, 12), new Element(N, 2), new Element(O, 2));
    public static final MoleculeEnum isoleucine = addMolecule("isoleucine", 143, solid, new Element(C, 6), new Element(H, 13), new Element(N), new Element(O, 2));
    public static final MoleculeEnum glutamates = addMolecule("glutamates", 144, solid, new Element(C, 5), new Element(H, 9), new Element(N), new Element(O, 4));
    public static final MoleculeEnum asparagine = addMolecule("asparagine", 145, solid, new Element(C, 4), new Element(H, 8), new Element(N, 2), new Element(O, 3));
    public static final MoleculeEnum keratin = addMolecule("keratin", 146, solid, new Molecule(threonine), new Molecule(cysteine), new Molecule(proline), new Molecule(threonine), new Molecule(proline), new Molecule(cysteine), new Molecule(proline));

    //Thermal Expansion
    public static final MoleculeEnum asbestos = addMolecule("asbestos", 147, solid, new Element(Mg, 3), new Element(Si, 2), new Element(O, 5), new Molecule(hydroxide, 4));

    //
    public static final MoleculeEnum lithiumHydroxide = addMolecule("lithiumHydroxide", 148, solid, new Element(Li, 1), new Molecule(hydroxide, 1));
    public static final MoleculeEnum sodiumHydroxide = addMolecule("sodiumHydroxide", 149, solid, new Element(Na, 1), new Molecule(hydroxide, 1));
    public static final MoleculeEnum potassiumHydroxide = addMolecule("potassiumHydroxide", 150, solid, new Element(K, 1), new Molecule(hydroxide, 1));
    public static final MoleculeEnum rubidiumHydroxide = addMolecule("rubidiumHydroxide", 151, solid, new Element(Rb, 1), new Molecule(hydroxide, 1));
    public static final MoleculeEnum cesiumHydroxide = addMolecule("cesiumHydroxide", 152, solid, new Element(Cs, 1), new Molecule(hydroxide, 1));
    public static final MoleculeEnum franciumHydroxide = addMolecule("franciumHydroxide", 153, solid, new Element(Fr, 1), new Molecule(hydroxide, 1));

    // AE2
    public static final MoleculeEnum hypophosphite = addMolecule("hypophosphite", 154, solid, new Element(H, 2), new Element(P, 1), new Element(O, 2));
    public static final MoleculeEnum aluminiumHypophosphite = addMolecule("aluminiumHypophosphite", 155, solid, new Element(Al, 1), new Molecule(hypophosphite, 3));

    // More things for Underground Biomes
    public static final MoleculeEnum omphacite = addMolecule("omphacite", 156, solid, new Element(Ca, 1), new Element(Al, 1), new Element(Si, 2), new Element(O, 6));
    public static final MoleculeEnum silicate = addMolecule("silicate", 157, solid, new Element(Si, 1), new Element(O, 4));
    public static final MoleculeEnum pyrope = addMolecule("pyrope", 158, solid, new Element(Mg, 3), new Element(Al, 2), new Molecule(silicate, 3));
    public static final MoleculeEnum almadine = addMolecule("almadine", 159, solid, new Element(Fe, 3), new Element(Al, 2), new Molecule(silicate, 3));
    public static final MoleculeEnum spessartine = addMolecule("spessartine", 160, solid, new Element(Mn, 3), new Element(Al, 2), new Molecule(silicate, 3));
    public static final MoleculeEnum redGarnet = addMolecule("redGarnet", 161, solid, new Molecule(pyrope, 3), new Molecule(almadine, 5), new Molecule(spessartine, 8));
    public static final MoleculeEnum forsterite = addMolecule("forsterite", 162, solid, new Element(Mg, 2), new Molecule(silicate, 1));
    public static final MoleculeEnum chromite = addMolecule("chromite", 163, solid, new Element(Fe, 1), new Element(Cr, 2), new Element(O, 4));

    // Tinkers Construct
    public static final MoleculeEnum siliconOxide = addMolecule("siliconOxide", 164, solid, new Element(Si, 1), new Element(O, 1));
    public static final MoleculeEnum ironOxide = addMolecule("ironOxide", 165, solid, new Element(Fe, 1), new Element(O, 1));

    // Extra utilities
    public static final MoleculeEnum galliumOxide = addMolecule("galliumOxide", 166, solid, new Element(Ga, 1), new Element(O, 2));
    public static final MoleculeEnum arsenicOxide = addMolecule("arsenicOxide", 167, solid, new Element(As, 1), new Element(O, 2));

    //
    public static final MoleculeEnum sulfurDioxide = addMolecule("sulfurDioxide", 168, gas, new Element(S), new Element(O, 2));
    public static final MoleculeEnum hydrogenSulfide = addMolecule("hydrogenSulfide", 169, gas, new Element(H, 2), new Element(S));
    public static final MoleculeEnum sodiumBisulfate = addMolecule("sodiumBisulfate", 170, solid, new Element(Na), new Element(H), new Molecule(sulfate));
    public static final MoleculeEnum sodiumSulfate = addMolecule("sodiumSulfate", 171, solid, new Element(Na, 2), new Molecule(sulfate));
    public static final MoleculeEnum dimethyltryptamine = addMolecule("dimethyltryptamine", 172, solid, new Element(C, 12), new Element(H, 16), new Element(N, 2));

    public static final MoleculeEnum oleicAcid = addMolecule("oleicAcid", 173, solid, new Element(C,18), new Element(H,34), new Element(O,2));

    private final String localizationKey;
    private final ArrayList<PotionChemical> components;
    private final int id;
    private final String name;
    private final int size;
    private final String formula;
    public float red;
    public float green;
    public float blue;
    public float red2;
    public float green2;
    public float blue2;

    //* Allows full definition of a given molecules down to even the colors that will be used. */
    public MoleculeEnum(String name, int id, float colorRed, float colorGreen, float colorBlue, float colorRed2, float colorGreen2, float colorBlue2, ChemicalRoomStateEnum roomState, PotionChemical... chemicals)
    {
        super(roomState, computeRadioactivity(chemicals));

        this.id = id;
        this.name = name;
        this.components = new ArrayList<PotionChemical>();
        this.localizationKey = "molecule." + name;
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
        size = computSize();
        formula = computFormula();
    }

    public static MoleculeEnum addMolecule(String name, int id, float colorRed, float colorGreen, float colorBlue, float colorRed2, float colorGreen2, float colorBlue2, ChemicalRoomStateEnum roomState, PotionChemical... chemicals)
    {
        MoleculeEnum molecule = new MoleculeEnum(name, id, colorRed, colorGreen, colorBlue, colorRed2, colorGreen2, colorBlue2, roomState, chemicals);
        registerMolecule(molecule);
        return molecule;
    }

    public static MoleculeEnum addMolecule(String name, int id, ChemicalRoomStateEnum roomState, PotionChemical... chemicals)
    {
        return addMolecule(name, id, getRandomColor(name.hashCode()), getRandomColor(name.hashCode() * 2), getRandomColor(name.hashCode() * 3), getRandomColor(name.hashCode() * 4), getRandomColor(name.hashCode() * 5), getRandomColor(name.hashCode() * 6), roomState, chemicals);
    }

    public static void registerMolecule(MoleculeEnum molecule)
    {
        addMapping(molecule);
        if (!molecule.name.equals("water"))
        {
            FluidHelper.registerMolecule(molecule);
        }
    }

    public static void registerMTMolecule(MoleculeEnum molecule)
    {
        addMapping(molecule);
        ArrayList<PotionChemical> var5 = molecule.components();
        PotionChemical[] var6 = var5.toArray(new PotionChemical[var5.size()]);
        ItemStack var7 = new ItemStack(MinechemItemsRegistration.molecule, 1, molecule.id());
        DecomposerRecipe.add(new DecomposerRecipe(var7, var6));
        SynthesisRecipe.add(new SynthesisRecipe(var7, true, MinechemRecipes.COST_ITEM, var6));
    }

    public static void unregisterMolecule(MoleculeEnum molecule)
    {
        removeMapping(molecule);
        DecomposerRecipe.remove(new ItemStack(MinechemItemsRegistration.molecule, 1, molecule.id()));
        SynthesisRecipe.remove(new ItemStack(MinechemItemsRegistration.molecule, 1, molecule.id()));
    }

    /**
     * Used to give random colors for elements so they don't have to be manually specified.
     */
    public MoleculeEnum(String name, int id, ChemicalRoomStateEnum roomState, PotionChemical... chemicals)
    {
        this(name, id, getRandomColor(name.hashCode()), getRandomColor(name.hashCode() * 2), getRandomColor(name.hashCode() * 3), getRandomColor(name.hashCode() * 4), getRandomColor(name.hashCode() * 5), getRandomColor(name.hashCode() * 6), roomState, chemicals);
    }

    private static float getRandomColor(long seed)
    {
        Random random = new Random(seed);
        return random.nextFloat();
    }

    private int computSize()
    {
        int result = 0;

        Iterator iter = this.components().iterator();

        while (iter.hasNext())
        {
            result += ((PotionChemical) iter.next()).amount;
        }
        return result;
    }

    public int getSize()
    {
        return size;
    }

    public static MoleculeEnum getById(int id)
    {
        return molecules.get(id);
    }

    public static MoleculeEnum getByName(String name)
    {
        return nameToMolecules.get(name);
    }

    public int id()
    {
        return this.id;
    }

    public ArrayList<PotionChemical> components()
    {
        return this.components;
    }

    public String name()
    {
        return name;
    }

    private static RadiationEnum computeRadioactivity(PotionChemical[] components)
    {
        RadiationEnum radiation = null;
        for (PotionChemical chemical : components)
        {
            RadiationEnum anotherRadiation = null;
            if (chemical instanceof Element)
            {
                anotherRadiation = ((Element) chemical).element.radioactivity();
            } else if (chemical instanceof Molecule)
            {
                anotherRadiation = ((Molecule) chemical).molecule.radioactivity();
            }

            if (anotherRadiation != null && anotherRadiation != RadiationEnum.stable && (radiation == null || radiation.getLife() > anotherRadiation.getLife()))
            {
                radiation = anotherRadiation;
            }
        }

        return radiation == null ? RadiationEnum.stable : radiation;
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

    private static void addMapping(MoleculeEnum molecule)
    {
        if (molecules.containsKey(molecule.id()))
        {
            throw new IllegalArgumentException("id " + molecule.id() + " is used");
        }
        if (nameToMolecules.containsKey(molecule.name()))
        {
            throw new IllegalArgumentException("name " + molecule.name() + " is used");
        }
        molecules.put(molecule.id(), molecule);
        nameToMolecules.put(molecule.name(), molecule);
    }

    private static void removeMapping(MoleculeEnum molecule)
    {
        molecules.remove(molecule.id());
        nameToMolecules.remove(molecule.name());
    }

    public String getFormula()
    {
        return formula;
    }

    private String computFormula()
    {
        String formula = "";
        for (PotionChemical component : components)
        {
            if (component instanceof Element)
            {
                formula += ((Element) component).element.name();
                if (component.amount > 1)
                {
                    formula += component.amount;
                }
            } else if (component instanceof Molecule)
            {
                if (component.amount > 1)
                {
                    formula += "(";
                }
                formula += ((Molecule) component).molecule.getFormula();
                if (component.amount > 1)
                {
                    formula += ")" + component.amount;
                }
            }
        }
        return formula;
    }

    public static void init()
    {

    }

    public PotionChemical[] componentsArray()
    {
        return components.toArray(new PotionChemical[components.size()]);
    }
}
