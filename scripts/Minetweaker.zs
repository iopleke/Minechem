import mods.minechem.Decomposer;
import mods.minechem.Synthesiser;
import mods.minechem.Chemicals;

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

//###########Decomposer Functions#################
//Decomposer.removeRecipe(redstone);                             		-- Remove Ore Dict or Item recipe
//Decomposer.removeFluid(liquidWater*1000)								-- Remove Fluid Recipe
//Decomposer.addRecipe(redstone,[H*64, He],0.6); 						-- Add recipe (chance optional) - Item or Ore Dict input
//Decomposer.addRecipe(glass,[lead*10,ice*5,H])							-- Add decomposition for crafting recipe output,[inputs]
//Decomposer.addFluid(liquidWater*1000,[H*10, water*10]); 				-- Add fluid recipe
//Decomposer.addMultiRecipe(ice,[[H*5, water*10],[H*20, water*5]],0.5)	-- Add multi-output recipe (chance optional)

//###########Synthesiser Functions################
//Synthesiser.removeRecipe(glass);										-- Remove Recipe
//Synthesiser.addRecipe([lead*10,ice*5],glass,false,100);				-- Add recipe ([inputs],output,shaped?, energy)

//############Input variables#####################
//Element Types = "Non-metal","Inert gas","Halogen","Alkali metal","Alkaline Earth Metal","Metalloid","Other metal",
//				  "Transition metal","Lanthanide","Actinide";
//Room States   = "Liquid","Solid","Gaseous";
//Radioactivity = "Stable","Hardly Radioactive","Slightly Radioactive","Radioactive","Highly Radioactive","Extremely Radioactive";