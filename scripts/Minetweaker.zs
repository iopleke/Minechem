import mods.minechem.Decomposer;
import mods.minechem.Synthesiser;
import mods.minechem.Chemicals;
import mods.minechem.Fuels;

//###########Example Items#######################
val redstone = <minecraft:redstone>;
val liquidWater = <liquid:water>;
val ice = <minecraft:ice>;
val lead = <ore:ingotLead>;
val glass = <minecraft:glass>;
val water = <minechem:minechemMolecule:1>;
val ethanol = <minechem:minechemMolecule:62>;
val phosgene = <minechem:minechemMolecule:91>;
val hilburnium = <minechem:minechemMolecule:180>;
val H = <minechem:minechemElement:1>;
val He = <ore:element_He>;
val Au = <ore:element_Au>;

//###########Chemical Functions###################
//Chemicals.addElement(114,"Hi","hilburnium","alkali Metal","gaseous","highly radioactive");
//Chemicals.addElement(atomicNumber,Symbol,Full Name,Element Type,Room State,Radioactivity);
//Chemicals.addMolecule("hilburnium awesomeus",180,"Solid",[He*3,ethanol*11,H*5,Au*5]);
//Chemicals.addMolecule(name,id,Room State,[Array of components]);
//Chemicals.removeMoleculeEffects(water);                                //-- Removes all effects for given molecule
//Chemicals.removeMoleculeEffects(molecule);

//Chemicals.addDamageEffect(water,5);                        //-- Adds a damage effect to a molecule
//Chemicals.addDamageEffect(molecule,damageValue);
//Chemicals.addFoodEffect(water,1,0.1);                      //-- Adds a food value to a molecule
//Chemicals.addFoodEffect(molecule,foodLevel,saturation);
//Chemicals.addPotionEffect(water,"poison",20,1);            //-- Adds a potion effect to a molecule
//Chemicals.addPotionEffect(molecule,"potionName",duration in s, [powerLevel] default is zero);
//Chemicals.addBurnEffect(water,5);                          //-- Adds a burn effect to a molecule
//Chemicals.addBurnEffect(molecule,burnTime);
//Chemicals.addCureEffect(water,"confusion");                //-- Adds a cure effect to a molecule
//Chemicals.addCureEffect(molecule,["potionName"] default is cure all);

//###########Decomposer Functions#################  
//Decomposer.removeRecipe(ice);                            				-- Remove Ore Dict or Item recipe
//Decomposer.removeRecipe(liquidWater*1000);							-- Remove Fluid Recipe
//Decomposer.addRecipe(redstone,0.5,[[H*64, He]]); 						-- Add recipe (chance optional) - Item or Ore Dict input
//Decomposer.addRecipe(ice,0.5,[[H*5, water*10],[H*20, water*5]]);		-- Add multi-output recipe (chance optional)
//Decomposer.addRecipe(glass,[lead*10,ice*5,H])							-- Add decomposition for crafting recipe output,[inputs]
//Decomposer.addFluid(liquidWater*1000,[H*10, water*10]); 				-- Add fluid recipe


//###########Synthesiser Functions################
//Synthesiser.removeRecipe(glass);										-- Remove Recipe
//Synthesiser.addRecipe([Au*10,H*5],glass,true,100);				//-- Add recipe ([inputs],output,shaped?, energy)

//###########Fuel Functions#######################
//Fuels.addFuel(H,200);													-- Add/Change Fuel (Item, burn time in ticks)
//Fuels.removeFuel(Au);													-- Remove Fuel

//############Input variables#####################
//Element Types = "Non-metal","Inert gas","Halogen","Alkali metal","Alkaline Earth Metal","Metalloid","Other metal",
//				  "Transition metal","Lanthanide","Actinide";
//Room States   = "Liquid","Solid","Gaseous";
//Radioactivity = "Stable","Hardly Radioactive","Slightly Radioactive","Radioactive","Highly Radioactive","Extremely Radioactive";