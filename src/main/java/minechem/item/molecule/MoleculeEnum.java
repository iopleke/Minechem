package minechem.item.molecule;

import minechem.item.ChemicalRoomStateEnum;
import minechem.item.MinechemChemicalType;
import minechem.item.element.Element;
import minechem.potion.PotionChemical;
import minechem.radiation.RadiationEnum;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import static minechem.item.ChemicalRoomStateEnum.*;
import static minechem.item.element.ElementEnum.*;

public class MoleculeEnum extends MinechemChemicalType
{
    public static final MoleculeEnum[] molecules = new MoleculeEnum[4096];
	
    public static final MoleculeEnum cellulose=new MoleculeEnum("cellulose",0, 0, 1, 0, 0, 0.25F, 0, solid,new Element(C, 6), new Element(H, 10), new Element(O, 5));
    public static final MoleculeEnum water=new MoleculeEnum("water",1, 0, 0, 1, 0, 0, 1, liquid,new Element(H, 2), new Element(O));
    public static final MoleculeEnum carbonDioxide=new MoleculeEnum("carbonDioxide",2, 0.5F, 0.5F, 0.5F, 0.25F, 0.25F, 0.25F, gas,new Element(C), new Element(O, 2));
    public static final MoleculeEnum nitrogenDioxide=new MoleculeEnum("nitrogenDioxide",3, 1, 0.65F, 0, 0.5F, 0.1412F, 0.1843F, gas,new Element(N), new Element(O, 2));
    public static final MoleculeEnum toluene=new MoleculeEnum("toluene",4, 1, 1, 1, 0.8F, 0.8F, 0.8F, liquid,new Element(C, 7), new Element(H, 8));
    public static final MoleculeEnum potassiumNitrate=new MoleculeEnum("potassiumNitrate",5, 0.9F, 0.9F, 0.9F, 0.8F, 0.8F, 0.8F,solid, new Element(K), new Element(N), new Element(O, 3));
    public static final MoleculeEnum tnt=new MoleculeEnum("tnt",6, 1, 1, 0, 1, 0.65F, 0,solid, new Element(C, 6), new Element(H, 2), new Molecule(nitrogenDioxide, 3), new Molecule(toluene));
    public static final MoleculeEnum siliconDioxide=new MoleculeEnum("siliconDioxide",7, 1, 1, 1, 1, 1, 1,solid, new Element(Si), new Element(O, 2));
    public static final MoleculeEnum calcite=new MoleculeEnum("calcite",8,solid, new Element(Ca), new Element(C), new Element(O, 3)); // TODO: Remove this as Calcium Carbonate is the exact same
    public static final MoleculeEnum pyrite=new MoleculeEnum("pyrite",9,solid, new Element(Fe), new Element(S, 2));
    public static final MoleculeEnum nepheline=new MoleculeEnum("nepheline",10,solid, new Element(Al), new Element(Si), new Element(O, 4));
    public static final MoleculeEnum sulfate=new MoleculeEnum("sulfate",11,solid, new Element(S), new Element(O, 4));
    public static final MoleculeEnum noselite=new MoleculeEnum("noselite",12,solid, new Element(Na, 8), new Molecule(nepheline, 6), new Molecule(sulfate));
    public static final MoleculeEnum sodalite=new MoleculeEnum("sodalite",13,solid, new Element(Na, 8), new Molecule(nepheline, 6), new Element(Cl, 2));
    public static final MoleculeEnum nitrate=new MoleculeEnum("nitrate",14,solid, new Element(N), new Element(O, 3));
    public static final MoleculeEnum carbonate=new MoleculeEnum("carbonate",15,solid, new Element(C), new Element(O, 3));
    public static final MoleculeEnum cyanide=new MoleculeEnum("cyanide",16,liquid, new Element(K), new Element(C), new Element(N));
    public static final MoleculeEnum phosphate=new MoleculeEnum("phosphate",17,solid, new Element(P), new Element(O, 4));
    public static final MoleculeEnum acetate=new MoleculeEnum("acetate",18,solid, new Element(C, 2), new Element(H, 3), new Element(O, 2));
    public static final MoleculeEnum chromate=new MoleculeEnum("chromate",19,solid, new Element(Cr), new Element(O, 4));
    public static final MoleculeEnum hydroxide=new MoleculeEnum("hydroxide",20,liquid, new Element(O), new Element(H));
    public static final MoleculeEnum ammonium=new MoleculeEnum("ammonium",21,liquid, new Element(N), new Element(H, 4));
    public static final MoleculeEnum hydronium=new MoleculeEnum("hydronium",22,liquid, new Element(H, 3), new Element(O));
    public static final MoleculeEnum peroxide=new MoleculeEnum("peroxide",23,liquid, new Element(O, 2)); 
    public static final MoleculeEnum calciumOxide=new MoleculeEnum("calciumOxide",24,solid, new Element(Ca), new Element(O));
    public static final MoleculeEnum calciumCarbonate=new MoleculeEnum("calciumCarbonate",25,solid, new Element(Ca), new Molecule(carbonate));
    public static final MoleculeEnum magnesiumCarbonate=new MoleculeEnum("magnesiumCarbonate",26,solid, new Element(Mg), new Molecule(carbonate));
    public static final MoleculeEnum lazurite=new MoleculeEnum("lazurite",27,solid, new Element(Na, 8), new Molecule(nepheline), new Molecule(sulfate));
    public static final MoleculeEnum isoprene=new MoleculeEnum("isoprene",28,solid, new Element(C, 5), new Element(H, 8));
    public static final MoleculeEnum butene=new MoleculeEnum("butene",29,gas, new Element(C, 4), new Element(H, 8));
    public static final MoleculeEnum polyisobutylene=new MoleculeEnum("polyisobutylene",30,liquid, new Molecule(butene, 16), new Molecule(isoprene));
    public static final MoleculeEnum malicAcid=new MoleculeEnum("malicAcid",31,solid, new Element(C, 4), new Element(H, 6), new Element(O, 5));
    public static final MoleculeEnum vinylChloride=new MoleculeEnum("vinylChloride",32,gas, new Element(C, 2), new Element(H, 3), new Element(Cl));
    public static final MoleculeEnum polyvinylChloride=new MoleculeEnum("polyvinylChloride",33,solid, new Molecule(vinylChloride, 64));
    public static final MoleculeEnum methamphetamine=new MoleculeEnum("methamphetamine",34,solid, new Element(C, 10), new Element(H, 15), new Element(N));
    public static final MoleculeEnum psilocybin=new MoleculeEnum("psilocybin",35,solid, new Element(C, 12), new Element(H, 17), new Element(N, 2), new Element(O, 4), new Element(P));
    public static final MoleculeEnum iron3oxide=new MoleculeEnum("iron3oxide",36,solid, new Element(Fe, 2), new Element(O, 3));
    public static final MoleculeEnum strontiumNitrate=new MoleculeEnum("strontiumNitrate",37,solid, new Element(Sr), new Molecule(nitrate, 2));
    public static final MoleculeEnum magnetite=new MoleculeEnum("magnetite",38,solid, new Element(Fe, 3), new Element(O, 4));
    public static final MoleculeEnum magnesiumOxide=new MoleculeEnum("magnesiumOxide",39,solid, new Element(Mg), new Element(O));
    public static final MoleculeEnum cucurbitacin=new MoleculeEnum("cucurbitacin",40,solid, new Element(C, 30), new Element(H, 42), new Element(O, 7));
    public static final MoleculeEnum asparticAcid=new MoleculeEnum("asparticAcid",41,solid, new Element(C, 4), new Element(H, 7), new Element(N), new Element(O, 4));
    public static final MoleculeEnum hydroxylapatite=new MoleculeEnum("hydroxylapatite",42,solid, new Element(Ca, 5), new Molecule(phosphate, 3), new Element(O), new Element(H));
    public static final MoleculeEnum alinine=new MoleculeEnum("alinine",43,solid, new Element(C, 3), new Element(H, 7), new Element(N), new Element(O, 2));
    public static final MoleculeEnum glycine=new MoleculeEnum("glycine",44,solid, new Element(C, 2), new Element(H, 5), new Element(N), new Element(O, 2));
    public static final MoleculeEnum serine=new MoleculeEnum("serine",45,solid, new Element(C, 3), new Element(H, 7), new Molecule(nitrate));
    public static final MoleculeEnum mescaline=new MoleculeEnum("mescaline",46,solid, new Element(C, 11), new Element(H, 17), new Molecule(nitrate));
    public static final MoleculeEnum methyl=new MoleculeEnum("methyl",47,liquid, new Element(C), new Element(H, 3));
    public static final MoleculeEnum methylene=new MoleculeEnum("methylene",48,liquid, new Element(C), new Element(H, 2));
    public static final MoleculeEnum memethacrylate=new MoleculeEnum("memethacrylate",49,liquid, new Molecule(methyl, 3), new Element(C, 2), new Element(O, 2));
    public static final MoleculeEnum pmma=new MoleculeEnum("pmma",50,solid, new Molecule(memethacrylate, 3)); // The amount of hydrogens is not 100% right for the polymerized form. But its no big deal.
    public static final MoleculeEnum redPigment=new MoleculeEnum("redPigment",51,solid, new Element(Co), new Molecule(nitrate, 2));
    public static final MoleculeEnum orangePigment=new MoleculeEnum("orangePigment",52,solid, new Element(K, 2), new Element(Cr, 2), new Element(O, 7));
    public static final MoleculeEnum yellowPigment=new MoleculeEnum("yellowPigment",53,solid, new Element(Cr), new Element(K, 2), new Element(O, 4));
    public static final MoleculeEnum limePigment=new MoleculeEnum("limePigment",54,solid, new Element(Ni), new Element(Cl, 2));
    public static final MoleculeEnum lightbluePigment=new MoleculeEnum("lightbluePigment",55,solid, new Element(Cu), new Molecule(sulfate));
    public static final MoleculeEnum purplePigment=new MoleculeEnum("purplePigment",56,solid, new Element(K), new Element(Mn), new Element(O, 4));
    public static final MoleculeEnum greenPigment=new MoleculeEnum("greenPigment",57,solid, new Element(Co), new Element(Zn), new Element(O, 2));
    public static final MoleculeEnum blackPigment=new MoleculeEnum("blackPigment",58,gas, new Element(C), new Element(H, 2), new Element(O));
    public static final MoleculeEnum whitePigment=new MoleculeEnum("whitePigment",59,solid, new Element(Ti), new Element(O, 2));
    public static final MoleculeEnum metasilicate=new MoleculeEnum("metasilicate",60,solid, new Element(Si), new Element(O, 3));
    public static final MoleculeEnum beryl=new MoleculeEnum("beryl",61,solid, new Element(Be, 3), new Element(Al, 2), new Molecule(metasilicate, 6));
    public static final MoleculeEnum ethanol=new MoleculeEnum("ethanol",62,liquid, new Element(C, 2), new Element(H, 5), new Molecule(hydroxide));
    public static final MoleculeEnum amphetamine=new MoleculeEnum("amphetamine",63,liquid, new Element(C, 9), new Element(H, 13), new Element(N));
    public static final MoleculeEnum theobromine=new MoleculeEnum("theobromine",64,solid, new Element(C, 7), new Element(H, 8), new Element(N, 4), new Element(O, 2));
    public static final MoleculeEnum starch=new MoleculeEnum("starch",65,solid, new Molecule(cellulose, 3));
    public static final MoleculeEnum sucrose=new MoleculeEnum("sucrose",66,solid, new Element(C, 12), new Element(H, 22), new Element(O, 11));
    public static final MoleculeEnum pantherine=new MoleculeEnum("pantherine",67,solid, new Element(C, 4), new Element(H, 6), new Element(N, 2), new Element(O, 2));
    public static final MoleculeEnum aluminiumOxide=new MoleculeEnum("aluminiumOxide",68,solid, new Element(Al, 2), new Element(O, 3));
    public static final MoleculeEnum fullrene=new MoleculeEnum("fullrene",69, 0.47F, 0.47F, 0.47F, 0.47F, 0.47F, 0.47F,solid, new Element(C, 64), new Element(C, 64), new Element(C, 64), new Element(C, 64));
    public static final MoleculeEnum valine=new MoleculeEnum("valine",70,solid, new Element(C, 5), new Element(H, 11), new Element(N), new Element(O, 2));
    public static final MoleculeEnum penicillin=new MoleculeEnum("penicillin",71,solid, new Element(C, 16), new Element(H, 18), new Element(N, 2), new Element(O, 4), new Element(S));
    public static final MoleculeEnum testosterone=new MoleculeEnum("testosterone",72,liquid, new Element(C, 19), new Element(H, 28), new Element(O, 2));
    public static final MoleculeEnum kaolinite=new MoleculeEnum("kaolinite",73,solid, new Element(Al, 2), new Element(Si, 2), new Element(O, 5), new Molecule(hydroxide, 4));
    public static final MoleculeEnum fingolimod=new MoleculeEnum("fingolimod",74,solid, new Element(C, 19), new Element(H, 33), new Molecule(nitrogenDioxide));
    public static final MoleculeEnum arginine=new MoleculeEnum("arginine",75,solid, new Element(C, 6), new Element(H, 14), new Element(N, 4), new Element(O, 2));
    public static final MoleculeEnum shikimicAcid=new MoleculeEnum("shikimicAcid",76,solid, new Element(C, 7), new Element(H, 10), new Element(O, 5));
    public static final MoleculeEnum sulfuricAcid=new MoleculeEnum("sulfuricAcid",77,liquid, new Element(H, 2), new Element(S), new Element(O, 4));
    public static final MoleculeEnum glyphosate=new MoleculeEnum("glyphosate",78,liquid, new Element(C, 3), new Element(H, 8), new Element(N), new Element(O, 5), new Element(P));
    public static final MoleculeEnum asprin=new MoleculeEnum("asprin",79,solid, new Element(C, 9), new Element(H, 8), new Element(O, 4));
    public static final MoleculeEnum ddt=new MoleculeEnum("ddt",80,solid, new Element(C, 14), new Element(H, 9), new Element(Cl, 5));
    public static final MoleculeEnum dota=new MoleculeEnum("dota",81,solid, new Element(C, 16), new Element(H, 28), new Element(N, 4), new Element(O, 8));
    public static final MoleculeEnum mycotoxin=new MoleculeEnum("mycotoxin",82, 0.89F, 0.83F, 0.07F, 0.89F, 0.83F, 0.07F,solid, new Element(C, 24), new Element(H, 34), new Element(O, 9));
    public static final MoleculeEnum salt=new MoleculeEnum("salt",83,solid, new Element(Na), new Element(Cl));
    public static final MoleculeEnum ammonia=new MoleculeEnum("ammonia",84,gas, new Element(N), new Element(H, 3));
    public static final MoleculeEnum nodularin=new MoleculeEnum("nodularin",85,solid, new Element(C, 41), new Element(H, 60), new Element(N, 8), new Element(O, 10));
    public static final MoleculeEnum tetrodotoxin=new MoleculeEnum("tetrodotoxin",86,solid, new Element(C, 11), new Element(H, 11), new Element(N, 3), new Element(O, 8));
    public static final MoleculeEnum thc=new MoleculeEnum("thc",87,solid, new Element(C, 21), new Element(H, 30), new Element(O, 2));
    public static final MoleculeEnum mt=new MoleculeEnum("mt",88,liquid, new Element(C, 9), new Element(H, 7), new Element(Mn, 1), new Element(O, 3)); // Level 1
    public static final MoleculeEnum buli=new MoleculeEnum("buli",89,solid, new Element(Li), new Element(C, 4), new Element(H, 9)); // Level 2
    public static final MoleculeEnum plat=new MoleculeEnum("plat",90,solid, new Element(H, 2), new Element(Pt, 1), new Element(Cl, 6)); // Level 3
    public static final MoleculeEnum phosgene=new MoleculeEnum("phosgene",91,gas, new Element(C), new Element(O), new Element(Cl, 2));
    public static final MoleculeEnum aalc=new MoleculeEnum("aalc",92,liquid, new Element(C, 3), new Element(H, 5), new Molecule(hydroxide));
    public static final MoleculeEnum hist=new MoleculeEnum("hist",93,solid, new Element(C, 17), new Element(H, 21), new Element(N), new Element(O));
    public static final MoleculeEnum pal2=new MoleculeEnum("pal2",94,solid, new Element(C, 31), new Element(H, 42), new Element(N, 2), new Element(O, 6));
    public static final MoleculeEnum retinol=new MoleculeEnum("retinol",95,solid, new Element(C, 20), new Element(H, 29), new Molecule(hydroxide));
    public static final MoleculeEnum xylitol=new MoleculeEnum("xylitol",96,solid, new Element(C, 5), new Element(H, 12), new Element(O, 5));
    public static final MoleculeEnum weedex=new MoleculeEnum("weedex",97,solid, new Element(C, 8), new Element(H, 8), new Element(Cl), new Element(N, 3), new Element(O, 2));
    public static final MoleculeEnum xanax=new MoleculeEnum("xanax",98,solid, new Element(C, 17), new Element(H, 13), new Element(Cl), new Element(N, 4));
    public static final MoleculeEnum hcl=new MoleculeEnum("hcl",99,liquid, new Element(H), new Element(Cl));
    public static final MoleculeEnum cocaine=new MoleculeEnum("cocaine",100,solid, new Element(C, 17), new Element(H, 21), new Element(N), new Element(O, 4));
    public static final MoleculeEnum cocainehcl=new MoleculeEnum("cocainehcl",101,solid, new Molecule(cocaine), new Molecule(hcl));
    public static final MoleculeEnum blueorgodye=new MoleculeEnum("blueorgodye",102,liquid, new Element(C, 15), new Element(H, 18));
    public static final MoleculeEnum redorgodye=new MoleculeEnum("redorgodye",103,solid, new Element(C, 15), new Element(H, 11), new Element(O, 11));
    public static final MoleculeEnum purpleorgodye=new MoleculeEnum("purpleorgodye",104,solid, new Element(C, 15), new Element(H, 11), new Element(O, 7));
    public static final MoleculeEnum olivine=new MoleculeEnum("olivine",105,solid, new Element(Fe, 2), new Element(Si), new Element(O, 4));
    public static final MoleculeEnum metblue=new MoleculeEnum("metblue",106,solid, new Element(C, 16), new Element(H, 18), new Element(N, 3), new Element(S), new Element(Cl));
    public static final MoleculeEnum meoh=new MoleculeEnum("meoh",107,liquid, new Molecule(methyl), new Molecule(hydroxide));
    public static final MoleculeEnum lcd=new MoleculeEnum("lcd",108,solid, new Element(C, 34), new Element(H, 50), new Element(O, 2));
    public static final MoleculeEnum radchlor=new MoleculeEnum("radchlor",109,solid, new Element(Ra), new Element(Cl, 2));
    public static final MoleculeEnum caulerpenyne=new MoleculeEnum("caulerpenyne",110,solid, new Element(C, 21), new Element(H, 26), new Element(O, 6));
    public static final MoleculeEnum latropine=new MoleculeEnum("latropine",111,solid, new Element(C, 17), new Element(H, 23), new Element(N), new Element(O, 4));
    public static final MoleculeEnum gallicacid=new MoleculeEnum("gallicacid",112,solid, new Element(C, 7), new Element(H, 17), new Element(O, 5));
    public static final MoleculeEnum glucose=new MoleculeEnum("glucose",113,solid, new Element(C, 6), new Element(H, 12), new Element(O, 6));
    public static final MoleculeEnum tannicacid=new MoleculeEnum("tannicacid",114,solid, new Molecule(gallicacid, 10), new Molecule(glucose));
    public static final MoleculeEnum hperox=new MoleculeEnum("hperox",115,liquid, new Element(H, 2), new Molecule(peroxide));
    public static final MoleculeEnum galliumarsenide=new MoleculeEnum("galliumarsenide",116,solid, new Element(Ga), new Element(As));
    public static final MoleculeEnum fibroin=new MoleculeEnum("fibroin",117,liquid, new Molecule(glycine), new Molecule(serine), new Molecule(glycine), new Molecule(alinine), new Molecule(glycine), new Molecule(alinine));
    public static final MoleculeEnum aluminiumPhosphate=new MoleculeEnum("aluminiumPhosphate",118,solid, new Element(Al), new Molecule(phosphate));
    public static final MoleculeEnum potassiumOxide=new MoleculeEnum("potassiumOxide",119,solid, new Element(K, 2), new Element(O));
    public static final MoleculeEnum sodiumOxide=new MoleculeEnum("sodiumOxide",120,solid, new Element(Na, 2), new Element(O));

    // For underground biomes support
    // http://flexiblelearning.auckland.ac.nz/rocks_minerals/
    public static final MoleculeEnum plagioclaseAnorthite=new MoleculeEnum("plagioclaseAnorthite",121,solid, new Element(Ca), new Element(Al, 2), new Element(Si, 2), new Element(O, 8));
    public static final MoleculeEnum plagioclaseAlbite=new MoleculeEnum("plagioclaseAlbite",122,solid, new Element(Na), new Element(Al, 2), new Element(Si, 3), new Element(O, 8));
    public static final MoleculeEnum orthoclase=new MoleculeEnum("orthoclase",123,solid, new Element(K), new Element(Al), new Element(Si, 3), new Element(O, 8));
    public static final MoleculeEnum biotite=new MoleculeEnum("biotite",124,solid, new Element(K), new Element(Fe, 3), new Element(Al), new Element(Si, 3), new Element(O, 10), new Element(F, 2));
    public static final MoleculeEnum augite=new MoleculeEnum("augite",125,solid, new Element(Na), new Element(Fe), new Element(Al, 2), new Element(O, 6));
    public static final MoleculeEnum talc=new MoleculeEnum("talc",126,solid, new Element(Mg, 3), new Element(Si, 4), new Element(O, 10));

	// Metallurgy
    public static final MoleculeEnum propane=new MoleculeEnum("propane",127,gas, new Element(C, 3), new Element(H, 8));
    public static final MoleculeEnum peridot=new MoleculeEnum("peridot",128,solid, new Element(Mg, 2), new Element(O, 4), new Element(Si));
    public static final MoleculeEnum topaz=new MoleculeEnum("topaz",129,solid, new Element(Al, 2), new Element(O, 4), new Element(F, 2));
    public static final MoleculeEnum zoisite=new MoleculeEnum("zoisite",130,solid, new Element(Ca, 2), new Element(Al, 3), new Element(Si, 3), new Element(O, 13), new Element(H));
    //
    public static final MoleculeEnum cysteine=new MoleculeEnum("cysteine",131,solid, new Element(C, 3), new Element(H, 7), new Element(N), new Element(O, 2), new Element(S));
    public static final MoleculeEnum threonine=new MoleculeEnum("threonine",132,solid, new Element(C, 4), new Element(H, 9), new Element(N), new Element(O, 3));
    public static final MoleculeEnum lysine=new MoleculeEnum("lysine",133,solid, new Element(C, 6), new Element(H, 14), new Element(N, 2), new Element(O, 2));
    public static final MoleculeEnum methionine=new MoleculeEnum("methionine",134,solid, new Element(C, 5), new Element(H, 11), new Element(N), new Element(O, 2), new Element(S));
    public static final MoleculeEnum tyrosine=new MoleculeEnum("tyrosine",135,solid, new Element(C, 9), new Element(H, 11), new Element(N), new Element(O, 3));
    public static final MoleculeEnum histidine=new MoleculeEnum("histidine",136,solid, new Element(C, 6), new Element(H, 9), new Element(N, 3), new Element(O, 2));
    public static final MoleculeEnum phenylalanine=new MoleculeEnum("phenylalanine",137,solid, new Element(C, 9), new Element(H, 11), new Element(N), new Element(O, 2));
    public static final MoleculeEnum glutamine=new MoleculeEnum("glutamine",138,solid, new Element(C, 5), new Element(H, 10), new Element(N, 2), new Element(O, 3));
    public static final MoleculeEnum proline=new MoleculeEnum("proline",139,solid, new Element(C, 5), new Element(H, 9), new Element(N), new Element(O, 2));
    public static final MoleculeEnum leucine=new MoleculeEnum("leucine",140,solid, new Element(C, 6), new Element(H, 13), new Element(N), new Element(O, 2));
    public static final MoleculeEnum tryptophan=new MoleculeEnum("tryptophan",141,solid, new Element(C, 11), new Element(H, 12), new Element(N, 2), new Element(O, 2));
    public static final MoleculeEnum aspartate=new MoleculeEnum("aspartate",142,solid, new Element(C, 4), new Element(H, 7), new Element(N), new Element(O, 4));
    public static final MoleculeEnum isoleucine=new MoleculeEnum("isoleucine",143,solid, new Element(C, 6), new Element(H, 13), new Element(N), new Element(O, 2));
    public static final MoleculeEnum glutamates=new MoleculeEnum("glutamates",144,solid, new Element(C, 5), new Element(H, 9), new Element(N), new Element(O, 4));
    public static final MoleculeEnum asparagine=new MoleculeEnum("asparagine",145,solid, new Element(C, 4), new Element(H, 8), new Element(N, 2), new Element(O, 3));
    public static final MoleculeEnum keratin=new MoleculeEnum("keratin",146,solid, new Molecule(threonine), new Molecule(cysteine), new Molecule(proline), new Molecule(threonine), new Molecule(proline), new Molecule(cysteine), new Molecule(proline));
  
	//Thermal Expansion
    public static final MoleculeEnum asbestos=new MoleculeEnum("asbestos",147,solid, new Element(Mg, 3), new Element(Si, 2), new Element(O, 5), new Molecule(hydroxide, 4));
    //
    public static final MoleculeEnum lithiumHydroxide=new MoleculeEnum("lithiumHydroxide",148,solid,new Element(Li, 1),new Molecule(hydroxide, 1));
    public static final MoleculeEnum sodiumHydroxide=new MoleculeEnum("sodiumHydroxide",149,solid,new Element(Na, 1),new Molecule(hydroxide, 1));
    public static final MoleculeEnum potassiumHydroxide=new MoleculeEnum("potassiumHydroxide",150,solid,new Element(K, 1),new Molecule(hydroxide, 1));
    public static final MoleculeEnum rubidiumHydroxide=new MoleculeEnum("rubidiumHydroxide",151,solid,new Element(Rb, 1),new Molecule(hydroxide, 1));
    public static final MoleculeEnum cesiumHydroxide=new MoleculeEnum("cesiumHydroxide",152,solid,new Element(Cs, 1),new Molecule(hydroxide, 1));
    public static final MoleculeEnum franciumHydroxide=new MoleculeEnum("franciumHydroxide",153,solid,new Element(Fr, 1),new Molecule(hydroxide, 1));
    
    private final String localizationKey;
    private final ArrayList<PotionChemical> components;
    private int id;
    private String name;
    public float red;
    public float green;
    public float blue;
    public float red2;
    public float green2;
    public float blue2;

    //* Allows full definition of a given molecule down to even the colors that will be used. */
    public MoleculeEnum(String name,int id, float colorRed, float colorGreen, float colorBlue, float colorRed2, float colorGreen2, float colorBlue2,ChemicalRoomStateEnum roomState, PotionChemical... chemicals) {
    	super(roomState,computRadioactivity(chemicals));
    	
    	if (molecules[id]!=null){
    		throw new IllegalArgumentException("id "+id+" is used");
    	}
    	
    	this.id = id;
    	this.name=name;
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
        
        molecules[id]=this;
    }

    /** Used to give random colors for elements so they don't have to be manually specified. */
    public MoleculeEnum(String name,int id,ChemicalRoomStateEnum roomState, PotionChemical... chemicals) 
    {
        this(name,id, getRandomColor(), getRandomColor(), getRandomColor(), getRandomColor(), getRandomColor(), getRandomColor(), roomState,chemicals);
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

    public String name(){
    	return name;
    }
    
    private static RadiationEnum computRadioactivity(PotionChemical[] components){
    	RadiationEnum radiation=null;
    	for (PotionChemical chemical:components){
    		RadiationEnum anotherRadiation=null;
    		if (chemical instanceof Element){
    			anotherRadiation=((Element) chemical).element.radioactivity();
    		}else if (chemical instanceof Molecule){
    			anotherRadiation=((Molecule) chemical).molecule.radioactivity();
    		}
    		
    		if (anotherRadiation!=null&&anotherRadiation!=RadiationEnum.stable&&(radiation==null||radiation.getLife()>anotherRadiation.getLife())){
    			radiation=anotherRadiation;
    		}
    	}
    	
    	return radiation==null?RadiationEnum.stable:radiation;
    }
}
