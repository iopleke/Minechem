package pixlepix.minechem.minechem.api.recipe;

import java.util.ArrayList;

import pixlepix.minechem.minechem.api.core.Chemical;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDecomposerRecipe {

	//Looks like there is already something for this
	//Not used for now
	
	
	public OreDecomposerRecipe(String name,Chemical... chemicals){
		ArrayList<ItemStack> ores=OreDictionary.getOres(name);
		for(int i=0;i<ores.size();i++){
			ItemStack stack=ores.get(i);
		}
		
		
	}
	
	
}
