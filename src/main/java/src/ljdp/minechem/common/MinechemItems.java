package ljdp.minechem.common;

import ljdp.minechem.api.core.EnumElement;
import ljdp.minechem.common.items.ItemArmorRadiationShield;
import ljdp.minechem.common.items.ItemAtomicManipulator;
import ljdp.minechem.common.items.ItemBlueprint;
import ljdp.minechem.common.items.ItemChemistJournal;
import ljdp.minechem.common.items.ItemElement;
import ljdp.minechem.common.items.ItemFusionStar;
import ljdp.minechem.common.items.ItemLens;
import ljdp.minechem.common.items.ItemMolecule;
import ljdp.minechem.common.items.ItemPills;
import ljdp.minechem.common.items.ItemTestTube;
import ljdp.minechem.common.items.PhotonicInduction;
import ljdp.minechem.common.utils.ConstantValue;
import ljdp.minechem.common.utils.MinechemHelper;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class MinechemItems {
    public static ItemElement element;
    public static ItemMolecule molecule;
    public static ItemLens lens;
    public static ItemAtomicManipulator atomicManipulator;
    public static ItemFusionStar fusionStar;
    public static ItemBlueprint blueprint;
    public static ItemTestTube testTube;
    public static ItemChemistJournal journal;
    public static ItemArmorRadiationShield hazmatFeet;
    public static ItemArmorRadiationShield hazmatLegs;
    public static ItemArmorRadiationShield hazmatTorso;
    public static ItemArmorRadiationShield hazmatHead;
    public static ItemStack convexLens;
    public static ItemStack concaveLens;
    public static ItemStack projectorLens;
    public static ItemStack microscopeLens;
    public static PhotonicInduction IAintAvinit;
    public static ItemPills EmptyPillz;
    public static ItemStack minechempills; 
    private static int elementID;
    private static int moleculeID;
    private static int atomicManipulatorID;
    private static int lensID;
    private static int fusionStarID;
    private static int blueprintID;
    private static int testTubeID;
    private static int journalID;
    private static int hazmatFeetID;
    private static int hazmatLegsID;
    private static int hazmatTorsoID;
    private static int hazmatHeadID;
    private static int photonID;
    private static int pillzID;

    public static void loadConfig(Configuration config) {
        int baseID = 4736;
        elementID = getItemConfig(config, "Element", baseID++);
        moleculeID = getItemConfig(config, "Molecule", baseID++);
        lensID = getItemConfig(config, "Lens", baseID++);
        atomicManipulatorID = getItemConfig(config, "AtomicManipulator", baseID++);
        fusionStarID = getItemConfig(config, "FusionStar", baseID++);
        blueprintID = getItemConfig(config, "Blueprint", baseID++);
        testTubeID = getItemConfig(config, "TestTube", baseID++);
        journalID = getItemConfig(config, "ChemistJournal", baseID++);
        hazmatFeetID = getItemConfig(config, "HazmatFeet", baseID++);
        hazmatLegsID = getItemConfig(config, "HazmatLegs", baseID++);
        hazmatTorsoID = getItemConfig(config, "HazmatTorso", baseID++);
        hazmatHeadID = getItemConfig(config, "HazmatHead", baseID++);
        photonID = getItemConfig(config, "Hammer", baseID++);
		pillzID = getItemConfig(config, "EmptyPills", baseID++);
    }

    private static int getItemConfig(Configuration config, String key, int defaultID) {
        return config.getItem(Configuration.CATEGORY_ITEM, key, defaultID).getInt(defaultID);
    }

    public static void registerItems() {
        element = new ItemElement(elementID);
        molecule = new ItemMolecule(moleculeID);
        lens = new ItemLens(lensID);
        atomicManipulator = new ItemAtomicManipulator(atomicManipulatorID);
        fusionStar = new ItemFusionStar(fusionStarID);
        blueprint = new ItemBlueprint(blueprintID);
        testTube = new ItemTestTube(testTubeID);
        journal = new ItemChemistJournal(journalID);
        hazmatFeet = new ItemArmorRadiationShield(hazmatFeetID, 3, 0.1F, ConstantValue.HAZMAT_FEET_TEX);
        hazmatLegs = new ItemArmorRadiationShield(hazmatLegsID, 2, 0.1F, ConstantValue.HAZMAT_LEGS_TEX);
        hazmatTorso = new ItemArmorRadiationShield(hazmatTorsoID, 1, 0.5F, ConstantValue.HAZMAT_TORSO_TEX);
        hazmatHead = new ItemArmorRadiationShield(hazmatHeadID, 0, 0.2F, ConstantValue.HAZMAT_HEAD_TEX);
        IAintAvinit = new PhotonicInduction(photonID, EnumToolMaterial.IRON, 5F);
		EmptyPillz = new ItemPills( pillzID,0);
        LanguageRegistry.addName(atomicManipulator, MinechemHelper.getLocalString("item.name.atomicmanipulator"));
        LanguageRegistry.addName(fusionStar, MinechemHelper.getLocalString("item.name.fusionStar"));
        LanguageRegistry.addName(testTube, MinechemHelper.getLocalString("item.name.testtube"));
        LanguageRegistry.addName(journal, MinechemHelper.getLocalString("item.name.chemistJournal"));
        LanguageRegistry.addName(hazmatFeet, MinechemHelper.getLocalString("item.name.hazmatFeet"));
        LanguageRegistry.addName(hazmatLegs, MinechemHelper.getLocalString("item.name.hazmatLegs"));
        LanguageRegistry.addName(hazmatTorso, MinechemHelper.getLocalString("item.name.hazmatTorso"));
        LanguageRegistry.addName(hazmatHead, MinechemHelper.getLocalString("item.name.hazmatHead"));
        LanguageRegistry.addName(IAintAvinit, "PhotonicInduction's Hammer");
		LanguageRegistry.addName(EmptyPillz, "Pills");
        concaveLens = new ItemStack(lens, 1, 0);
        convexLens = new ItemStack(lens, 1, 1);
        microscopeLens = new ItemStack(lens, 1, 2);
        projectorLens = new ItemStack(lens, 1, 3);
		minechempills = new ItemStack(EmptyPillz, 1, 0);
    }

    public static void registerToOreDictionary() {
        for (EnumElement element : EnumElement.values()) {
            OreDictionary.registerOre("element" + element.descriptiveName(), new ItemStack(MinechemItems.element, 1, element.ordinal()));
        }
    }
}