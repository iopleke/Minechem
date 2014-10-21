package minechem.utils;

import java.util.ArrayList;

import minechem.item.element.Element;
import minechem.item.element.ElementEnum;
import minechem.item.molecule.Molecule;
import minechem.item.molecule.MoleculeEnum;
import minechem.potion.PotionChemical;
import minechem.tileentity.decomposer.DecomposerRecipe;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.oredict.IOreDictEntry;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class InputHelper {
    public static boolean isABlock(IItemStack block) {
        if (!(isABlock(toStack(block)))) {
            MineTweakerAPI.getLogger().logError("Item must be a block, or you must specify a block to render as when adding a TConstruct Melting recipe");
            return false;
        } else return true;
    }

    public static boolean isABlock(ItemStack block) {
        return block.getItem() instanceof ItemBlock;
    }

    public static ItemStack toStack(IItemStack iStack) {
        if (iStack == null) return null;
        else {
            Object internal = iStack.getInternal();
            if (internal == null || !(internal instanceof ItemStack)) {
                MineTweakerAPI.getLogger().logError("Not a valid item stack: " + iStack);
            }

            return (ItemStack) internal;
        }
    }

    public static ItemStack[] toStacks(IItemStack[] iStack) {
        if (iStack == null) return null;
        else {
            ItemStack[] output = new ItemStack[iStack.length];
            for (int i = 0; i < iStack.length; i++) {
                output[i] = toStack(iStack[i]);
            }

            return output;
        }
    }

    public static Object toObject(IIngredient iStack) {
        if (iStack == null) return null;
        else {
            if (iStack instanceof IOreDictEntry) {
                return toString((IOreDictEntry) iStack);
            } else if (iStack instanceof IItemStack) {
                return toStack((IItemStack) iStack);
            } else return null;
        }
    }

    public static Object[] toObjects(IIngredient[] ingredient) {
        if (ingredient == null) return null;
        else {
            Object[] output = new Object[ingredient.length];
            for (int i = 0; i < ingredient.length; i++) {
                if (ingredient[i] != null) {
                    output[i] = toObject(ingredient[i]);
                } else output[i] = "";
            }

            return output;
        }
    }

    public static Object[] toShapedObjects(IIngredient[][] ingredients) {
        if (ingredients == null) return null;
        else {
            ArrayList prep = new ArrayList();
            prep.add("abc");
            prep.add("def");
            prep.add("ghi");
            char[][] map = new char[][] { { 'a', 'b', 'c' }, { 'd', 'e', 'f' }, { 'g', 'h', 'i' } };
            for (int x = 0; x < ingredients.length; x++) {
                if (ingredients[x] != null) {
                    for (int y = 0; y < ingredients[x].length; y++) {
                        if (ingredients[x][y] != null && x < map.length && y < map[x].length) {
                            prep.add(map[x][y]);
                            prep.add(toObject(ingredients[x][y]));
                        }
                    }
                }
            }
            return prep.toArray();
        }
    }

    public static String toString(IOreDictEntry entry) {
        return ((IOreDictEntry) entry).getName();
    }

    public static FluidStack toFluid(ILiquidStack iStack) {
        if (iStack == null) {
            return null;
        } else return FluidRegistry.getFluidStack(iStack.getName(), iStack.getAmount());
    }

    public static FluidStack[] toFluids(IIngredient[] input) {
        return toFluids((IItemStack[]) input);
    }

    public static FluidStack[] toFluids(ILiquidStack[] iStack) {
        FluidStack[] stack = new FluidStack[iStack.length];
        for (int i = 0; i < stack.length; i++)
            stack[i] = toFluid(iStack[i]);
        return stack;
    }
    
    public static PotionChemical getPotionChemical(String input)
	{
		String[] inputs = input.split(" ");
		if (inputs.length<2) return null;
		String name = inputs[1];
		int count;
		try{
			count = Integer.parseInt(input.split(" ")[0]);
		}
		catch (NumberFormatException e){
			return null;
		}
		if (count<1) count=1;
		for (ElementEnum ele:ElementEnum.elements)
			if (ele!=null && ele.name()!=null && ele.name().equals(name)) 
				return new Element(ele,count);
		for (MoleculeEnum mol:MoleculeEnum.molecules)
		{
			if (mol!=null && mol.name()!=null && mol.name().equals(name))
				return new Molecule(mol,count);

		}
		return null;
	}
    
 // Utility functions
	
 	public static ArrayList<ItemStack> getInputs(IIngredient input)
 	{
 		ArrayList<ItemStack> toAdd = new ArrayList<ItemStack>();
 		if(input instanceof IOreDictEntry)
 			toAdd=OreDictionary.getOres(((IOreDictEntry) input).getName());
 		else if(input instanceof IItemStack)
 			toAdd.add(InputHelper.toStack((IItemStack) input));
 		return toAdd;
 	}
 	
 	public static ItemStack getInput(IIngredient input)
 	{
 		if (input==null) return null;
 		if(input instanceof IOreDictEntry)
			return OreDictionary.getOres(((IOreDictEntry) input).getName()).get(0);
		else if (input instanceof IItemStack)
			return InputHelper.toStack((IItemStack) input);
 		return null;
 	}
 	
 	public static ArrayList<PotionChemical> getChemicals(String... array)
 	{
 		ArrayList<PotionChemical> output = new ArrayList<PotionChemical>();
 		for (String outputString:array)
 		{
 			PotionChemical out = InputHelper.getPotionChemical(outputString);
 			if (out!=null) 
 				output.add(out);
 			else
 				throw new IllegalArgumentException(outputString +" is not a Chemical");
 		}
 		return output;
 	}
 	
 	public static ArrayList<PotionChemical> getChemicals(IItemStack... array)
 	{
 		ArrayList<PotionChemical> output = new ArrayList<PotionChemical>();
 		for (IItemStack outputStack:array)
 		{
 			PotionChemical out = MinechemHelper.itemStackToChemical(InputHelper.toStack(outputStack));
 			if (out!=null)
 				output.add(out);
 			else
 				throw new IllegalArgumentException(outputStack +" is not a Chemical");
 		}
 		return output;
 	}
 	
 	public static DecomposerRecipe[] getDecompArray(ArrayList<DecomposerRecipe> arrayList) {
 		DecomposerRecipe[] result = new DecomposerRecipe[arrayList.size()];
 		for (int i=0;i<arrayList.size();i++)
 			result[i] = arrayList.get(i);
 		return result;
 	}
 	
 	public static ItemStack[] getItemArray(ArrayList<ItemStack> arrayList) {
 		ItemStack[] result = new ItemStack[arrayList.size()];
 		for (int i=0;i<arrayList.size();i++)
 			result[i] = arrayList.get(i);
 		return result;
 	}
 	
 	public static PotionChemical[] getArray(ArrayList<PotionChemical> arrayList) {
 		PotionChemical[] result = new PotionChemical[arrayList.size()];
 		for (int i=0;i<arrayList.size();i++)
 			result[i] = arrayList.get(i);
 		return result;
 	}
    
}
