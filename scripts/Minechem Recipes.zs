import mods.minechem.Decomposer;
import mods.minechem.Synthesiser;

//Register commonly used items
val HSLA = <RotaryCraft:rotarycraft_item_shaftcraft:1>;
val tungsten = <RotaryCraft:rotarycraft_item_compacts:5>;
val bedrock = <RotaryCraft:rotarycraft_item_compacts:3>;
val redstone = <minecraft:redstone>;
val diamond = <minecraft:diamond>;
val iron = <ore:ingotIron>;
val copper = <ore:ingotCopper>;
val tin = <ore:ingotTin>;
val lead = <ore:ingotLead>;
val gold = <ore:ingotGold>;
val glass = <minecraft:glass>;

//Decomposer.remove("ingotLead");
Decomposer.remove(bedrock);
//Decomposer.addRecipe(bedrock,["1 H", "2 ethanol"]);
//Decomposer.addRecipe(<ore:ingotLead>,"16 Pb");

//Synthesiser.addRecipe(bedrock,false,100,["1 H", "2 ethanol"]);
//Synthesiser.remove(bedrock);