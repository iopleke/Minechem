import mods.minechem.Decomposer;
import mods.minechem.Synthesiser;
import mods.minechem.Chemicals;
import mods.minechem.Fuels;

//###########Example Items#######################
val redstone = <minecraft:redstone>;
val ice = <minecraft:ice>;
val lead = <ore:ingotLead>;
val glass = <minecraft:glass>;
val water = <minechem:minechemMolecule:1>;
val ethanol = <minechem:minechemMolecule:62>;
val phosgene = <minechem:minechemMolecule:91>;
val hilburnium = <minechem:minechemMolecule:180>;
val H = <minechem:minechemElement:0>;
val He = <ore:element_He>;
val Au = <ore:element_Au>;

//###########Chemical Functions###################
//Chemicals.addElement(114,"Hi","hilburnium","alkali Metal","gaseous","highly radioactive");
//Chemicals.addElement(atomicNumber,Symbol,Full Name,Element Type,Room State,Radioactivity);
//Chemicals.addMolecule("hilburnium awesomeus",180,"Solid",[He*3,ethanol*11,H*5,Au*5]);
//Chemicals.addMolecule(name,id,Room State,[Array of components]);
//Chemicals.removeMoleculeEffects(water)                                -- Removes all effects for given molecule
//Chemicals.removeMoleculeEffects(molecule);

//Chemicals.addMoleculeEffect(molecule,"type",... params);              -- Params between [] are optional
//Chemicals.addMoleculeEffect(water,"damage",1);                        -- Adds a damage effect to a molecule
//Chemicals.addMoleculeEffect(molecule,"damage",damageValue);
//Chemicals.addMoleculeEffect(water,"food",1,0.1);                      -- Adds a food value to a molecule
//Chemicals.addMoleculeEffect(molecule,"food",foodLevel,saturation);
//Chemicals.addMoleculeEffect(water,"potion","poison",20,1);            -- Adds a potion effect to a molecule
//Chemicals.addMoleculeEffect(molecule,"potion","potionName",duration in s, [powerLevel] default is zero);
//Chemicals.addMoleculeEffect(water,"burn",5);                          -- Adds a burn effect to a molecule
//Chemicals.addMoleculeEffect(molecule,"burn",burnTime);
//Chemicals.addMoleculeEffect(water,"cure","confusion");                -- Adds a cure effect to a molecule
//Chemicals.addMoleculeEffect(molecule,"cure",["potionName"] default is cure all);

//###########Decomposer Functions#################  
//Decomposer.removeRecipe(ice);                            				-- Remove Ore Dict or Item recipe
//Decomposer.removeFluid(liquidWater*1000)								-- Remove Fluid Recipe
//Decomposer.addRecipe(redstone,0.5,[[H*64, He]]); 						-- Add recipe (chance optional) - Item or Ore Dict input
//Decomposer.addRecipe(ice,0.5,[[H*5, water*10],[H*20, water*5]]);		-- Add multi-output recipe (chance optional)
//Decomposer.addRecipe(glass,[lead*10,ice*5,H])							-- Add decomposition for crafting recipe output,[inputs]
//Decomposer.addFluid(liquidWater*1000,[H*10, water*10]); 				-- Add fluid recipe


//###########Synthesiser Functions################
//Synthesiser.removeRecipe(glass);										-- Remove Recipe
//Synthesiser.addRecipe([lead*10,ice*5],glass,false,100);				-- Add recipe ([inputs],output,shaped?, energy)

//###########Fuel Functions#######################
//Fuels.addFuel(H,200);													-- Add/Change Fuel (Item, burn time in ticks)
//Fuels.removeFuel(Au);													-- Remove Fuel

//############Input variables#####################
//Element Types = "Non-metal","Inert gas","Halogen","Alkali metal","Alkaline Earth Metal","Metalloid","Other metal",
//				  "Transition metal","Lanthanide","Actinide";
//Room States   = "Liquid","Solid","Gaseous";
//Radioactivity = "Stable","Hardly Radioactive","Slightly Radioactive","Radioactive","Highly Radioactive","Extremely Radioactive";