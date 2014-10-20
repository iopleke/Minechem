import mods.minechem.Decomposer;
import mods.minechem.Synthesiser;

//Register commonly used items
val redstone = <minecraft:redstone>;
val iron = <ore:ingotIron>;
val copper = <ore:ingotCopper>;
val tin = <ore:ingotTin>;
val lead = <ore:ingotLead>;
val gold = <ore:ingotGold>;
val glass = <minecraft:glass>;

//Decomposer.remove(lead);                             				-- Ore Dict Removal
//Decomposer.remove(redstone);                         				-- Single Item Removal
//Decomposer.remove(liquidWater*1000);                 				-- Fluid Removal
//Decomposer.addRecipe(redstone,["1 H", "2 ethanol"]); 				-- Add basic recipe
//Decomposer.addRecipe(liquidWater*1000,"16 water");   				-- Add fluid recipe
//Decomposer.addRecipe(tin,0.5,["16 Sn","12 Sn"]);   				-- Add chance recipe
//Decomposer.addRecipe(tin,[["16 Sn"],["12 Sn","4 Ag"]]);			-- Add select recipe with guaranteed output
//Decomposer.addRecipe(tin,0.5,[["16 Sn"],["12 Sn","4 Ag"]]); 	  	-- Add select recipe with chance
//Decomposer.addRecipe(glass,[iron,gold,redstone]);					-- Add super recipe with ingredients 

//Synthesiser.remove(glass);										-- Remove Recipe (can also support oredict)
//Synthesiser.addRecipe(glass,false,100,["1 H", "2 ethanol"]);		-- Add recipe (item, shaped?, energy, output)
