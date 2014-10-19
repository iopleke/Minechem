package minechem.utils;

import java.util.ArrayList;

import minechem.item.element.Element;
import minechem.item.element.ElementEnum;
import minechem.item.molecule.Molecule;
import minechem.item.molecule.MoleculeEnum;
import minechem.potion.PotionChemical;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.oredict.IOreDictEntry;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

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
		for (ElementEnum val:ElementEnum.elements)
			if (val.name()==name) 
				return new Element(val,count);
		for (MoleculeEnum val:MoleculeEnum.molecules)
			if (val.name()==name) 
				return new Molecule(val,count);
		
		return null;
	}
}
