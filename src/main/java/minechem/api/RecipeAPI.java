package minechem.api;

import java.lang.reflect.Array;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class RecipeAPI
{

	/**
	 * Adds a chemical decomposition recipe to Minechem.
	 * <p/>
	 * The outputs are parsed in the form "number symbol", for example, addDecompositionRecipe(myItemStack, "1 H", "2 ethanol") would cause the created recipe to output one hydrogen and two ethanol
	 * when decomposed.
	 *
	 * @param input The stack to be decomposed.
	 * @param outputs The molecules or elements produced by the recipe.
	 * @return Whether the decomposition recipe was added.
	 */
	public static boolean addDecompositionRecipe(ItemStack input, String... outputs)
	{
		try
		{
			Object[] potions = (Object[]) Array.newInstance(MinechemClassesAccess.classPotionChemical, outputs.length);
			int idx = 0;
			for (String s : outputs)
			{
				String elementName = s.split(" ")[1];
				int count = Integer.parseInt(s.split(" ")[0]);
				try
				{//try to add element
					Object elementEnum = MinechemClassesAccess.classElementEnum.getField(elementName).get(null);
					Object element = MinechemClassesAccess.classElement.getConstructor(MinechemClassesAccess.classElementEnum, int.class).newInstance(elementEnum, count);
					potions[idx] = element;
				} catch (NoSuchFieldException e)
				{//else add molecules
					Object elementEnum = MinechemClassesAccess.classMoleculeEnum.getField(elementName).get(null);
					Object element = MinechemClassesAccess.classMolecule.getConstructor(MinechemClassesAccess.classMoleculeEnum, int.class).newInstance(elementEnum, count);
					potions[idx] = element;
				}
				idx++;
			}
			Object drInst = MinechemClassesAccess.classDecomposerRecipe.getConstructor(ItemStack.class, potions.getClass()).newInstance(input, potions);
			MinechemClassesAccess.classDecomposerRecipe.getMethod("add", MinechemClassesAccess.classDecomposerRecipe).invoke(null, drInst);
			return true;
		} catch (Exception e)
		{
			return false;
		}
	}
	
	/**
	 * Adds a chemical synthesis recipe to Minechem.
	 * <p/>
	 * The inputs are parsed in the form "number symbol", for example, addSynthesisRecipe(stackOfGrass, "1 H", "2 ethanol") would cause the created recipe to require one hydrogen and two ethanol when
	 * decomposed.
	 * <p/>
	 * Inputs may be the empty string; this leaves a gap in a shaped recipe and is ignored in a shapeless recipe.
	 *
	 * @param output The stack to be synthesised.
	 * @param shaped Is the recipe shaped?
	 * @param energyCost The energy cost to synthesise.
	 * @param inputs The molecules or elements used by the recipe.
	 * @return Whether the synthesise recipe was added.
	 */
	public static boolean addSynthesisRecipe(ItemStack output, boolean shaped, int energyCost, String... inputs)
	{
		try
		{
			Object[] potions = (Object[]) Array.newInstance(MinechemClassesAccess.classPotionChemical, inputs.length);
			int idx = 0;
			for (String s : inputs)
			{
				if (s.equals(""))
				{
					potions[idx] = null;
				} else
				{
					String elementName = s.split(" ")[1];
					int count = Integer.parseInt(s.split(" ")[0]);
					try
					{//try to add element
						Object elementEnum = MinechemClassesAccess.classElementEnum.getField(elementName).get(null);
						Object element = MinechemClassesAccess.classElement.getConstructor(MinechemClassesAccess.classElementEnum, int.class).newInstance(elementEnum, count);
						potions[idx] = element;
					} catch (NoSuchFieldException e)
					{//else add molecules
						Object elementEnum = MinechemClassesAccess.classMoleculeEnum.getField(elementName).get(null);
						Object element = MinechemClassesAccess.classMolecule.getConstructor(MinechemClassesAccess.classMoleculeEnum, int.class).newInstance(elementEnum, count);
						potions[idx] = element;
					}
				}
				idx++;
			}
			Object recipe = MinechemClassesAccess.classSynthesisRecipe.getConstructor(ItemStack.class, boolean.class, int.class, potions.getClass()).newInstance(output, shaped, energyCost, potions);
			MinechemClassesAccess.classSynthesisRecipe.getMethod("add", MinechemClassesAccess.classSynthesisRecipe).invoke(null, recipe);
			return true;
		} catch (Exception e)
		{
			return false;
		}
	}

	/**
	 * Adds a chemical decomposition fluid recipe to Minechem.
	 * <p/>
	 * The outputs are parsed in the form "number symbol", for example, addDecompositionRecipe(myFluidStack, "1 H", "2 ethanol") would cause the created recipe to output one hydrogen and two ethanol
	 * when decomposed.
	 *
	 * @param input The fluidStack to be decomposed.
	 * @param outputs The molecules or elements produced by the recipe.
	 * @return Whether the decomposition recipe was added.
	 */
	public static boolean addDecompositionFluidRecipe(FluidStack input, String... outputs)
	{
		try
		{
			Object[] potions = (Object[]) Array.newInstance(MinechemClassesAccess.classPotionChemical, outputs.length);
			int idx = 0;
			for (String s : outputs)
			{
				String elementName = s.split(" ")[1];
				int count = Integer.parseInt(s.split(" ")[0]);
				try
				{//try to add element
					Object elementEnum = MinechemClassesAccess.classElementEnum.getField(elementName).get(null);
					Object element = MinechemClassesAccess.classElement.getConstructor(MinechemClassesAccess.classElementEnum, int.class).newInstance(elementEnum, count);
					potions[idx] = element;
				} catch (NoSuchFieldException e)
				{//else add molecules
					Object elementEnum = minechem.api.MinechemClassesAccess.classMoleculeEnum.getField(elementName).get(null);
					Object element = MinechemClassesAccess.classMolecule.getConstructor(minechem.api.MinechemClassesAccess.classMoleculeEnum, int.class).newInstance(elementEnum, count);
					potions[idx] = element;
				}
				idx++;
			}
			Object dfrInst = MinechemClassesAccess.classDecomposerFluidRecipe.getConstructor(FluidStack.class, potions.getClass()).newInstance(input, potions);
			MinechemClassesAccess.classDecomposerRecipe.getMethod("add", MinechemClassesAccess.classDecomposerRecipe).invoke(null, dfrInst);
			return true;
		} catch (Exception e)
		{
			return false;
		}
	}

	/**
	 * Removes a Decomposition recipe
	 *
	 * @param itemStack the item that has to be removed in ItemStack form
	 * @return Whether the decomposition recipe was removed
	 */
	public static boolean removeDecompositionRecipe(ItemStack itemStack)
	{
		try
		{
			MinechemClassesAccess.classDecomposerRecipe.getMethod("remove", ItemStack.class).invoke(null, itemStack);
			return true;
		} catch (Exception e)
		{
			return false;
		}
	}

	/**
	 * Removes all Decomposition recipes for a given oreDict name
	 *
	 * @param oreName oerDict name of the item
	 * @return Whether the decomposition recipe was removed
	 */
	public static boolean removeDecompositionRecipe(String oreName)
	{
		try
		{
			MinechemClassesAccess.classDecomposerRecipe.getMethod("removeRecipeSafely", String.class).invoke(null, oreName);
			return true;
		} catch (Exception e)
		{
			return false;
		}
	}

	/**
	 * Removes a Synthesis recipe
	 *
	 * @param itemStack the item that has to be removed in ItemStack form
	 * @return Whether the Synthesis recipe was removed
	 */
	public static boolean removeSynthesisRecipe(ItemStack itemStack)
	{
		try
		{
			MinechemClassesAccess.classSynthesisRecipe.getMethod("remove", ItemStack.class).invoke(null, itemStack);
			return true;
		} catch (Exception e)
		{
			return false;
		}
	}

	/**
	 * Removes all Synthesis recipes for a given oreDict name
	 *
	 * @param oreName oerDict name of the item
	 * @return Whether the Synthesis recipes were removed
	 */
	public static boolean removeSynthesisRecipe(String oreName)
	{
		try
		{
			MinechemClassesAccess.classSynthesisRecipe.getMethod("removeRecipeSafely", String.class).invoke(null, oreName);
			return true;
		} catch (Exception e)
		{
			return false;
		}
	}
}
