import mods.minechem.Decomposer;
import mods.minechem.Synthesiser;
import mods.minechem.Chemicals;

//###########Example Items#######################
val redstone = <minecraft:redstone>;
val ice = <minecraft:ice>;
val lead = <ore:ingotLead>;
val glass = <minecraft:glass>;
val water = <minechem:minechemMolecule:1>;
val H = <minechem:minechemElement:0>;
val He = <ore:element_He>;

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

//###########Chemical Functions################### NOT FULLY IMPLEMENTED
//Chemicals.addMolecule("hilburnium",id,"Solid",[H*2,water*11])