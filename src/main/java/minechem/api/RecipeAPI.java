package minechem.api;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.lang.reflect.Array;

public class RecipeAPI
{

    /**
     * Adds a chemical decomposition recipe to Minechem.
     * <p/>
     * The outputs are parsed in the form "number symbol", for example,
     * addDecompositionRecipe(myItemStack, "1 H", "2 ethanol") would
     * cause the created recipe to output one hydrogen and two ethanol
     * when decomposed.
     *
     * @param input   The stack to be decomposed.
     * @param outputs The molecules or elements produced by the recipe.
     * @return Whether the decomposition recipe was added.
     */
    public static boolean addDecompositionRecipe(ItemStack input, String... outputs)
    {
        try
        {
            Class dr = Class.forName("minechem.tileentity.decomposer.DecomposerRecipe");
            Class ee = Class.forName("minechem.item.element.ElementEnum");
            Class el = Class.forName("minechem.item.element.Element");

            Class me = Class.forName("minechem.item.molecule.MoleculeEnum");
            Class mo = Class.forName("minechem.item.molecule.Molecule");

            Object[] potions = (Object[]) Array.newInstance(Class.forName("minechem.potion.PotionChemical"), outputs.length);
            int idx = 0;
            for (String s : outputs)
            {
                String elementName = s.split(" ")[1];
                int count = Integer.parseInt(s.split(" ")[0]);
                try
                {//try to add element
                    Object elementEnum = ee.getField(elementName).get(null);
                    Object element = el.getConstructor(ee, int.class).newInstance(elementEnum, count);
                    potions[idx] = element;
                } catch (NoSuchFieldException e)
                {//else add molecule
                    Object elementEnum = me.getField(elementName).get(null);
                    Object element = mo.getConstructor(me, int.class).newInstance(elementEnum, count);
                    potions[idx] = element;
                }
                idx++;
            }
            Object drInst = dr.getConstructor(ItemStack.class, potions.getClass()).newInstance(input, potions);
            dr.getMethod("add", dr).invoke(null, drInst);
            return true;
        } catch (Exception e)
        {
            return false;
        }
    }

    /**
     * Adds a chemical synthesis recipe to Minechem.
     * <p/>
     * The inputs are parsed in the form "number symbol", for example,
     * addSynthesisRecipe(stackOfGrass, "1 H", "2 ethanol") would
     * cause the created recipe to require one hydrogen and two ethanol
     * when decomposed.
     * <p/>
     * Inputs may be the empty string; this leaves a gap in a shaped
     * recipe and is ignored in a shapeless recipe.
     *
     * @param output     The stack to be synthesised.
     * @param shaped     Is the recipe shaped?
     * @param energyCost The energy cost to synthesise.
     * @param inputs     The molecules or elements used by the recipe.
     * @return Whether the synthesise recipe was added.
     */
    public static boolean addSynthesisRecipe(ItemStack output, boolean shaped, int energyCost, String... inputs)
    {
        try
        {
            Class synth = Class.forName("minechem.tileentity.synthesis.SynthesisRecipe");
            Class ee = Class.forName("minechem.item.element.ElementEnum");
            Class el = Class.forName("minechem.item.element.Element");

            Class me = Class.forName("minechem.item.molecule.MoleculeEnum");
            Class mo = Class.forName("minechem.item.molecule.Molecule");

            Object[] potions = (Object[]) Array.newInstance(Class.forName("minechem.potion.PotionChemical"), inputs.length);
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
                        Object elementEnum = ee.getField(elementName).get(null);
                        Object element = el.getConstructor(ee, int.class).newInstance(elementEnum, count);
                        potions[idx] = element;
                    } catch (NoSuchFieldException e)
                    {//else add molecule
                        Object elementEnum = me.getField(elementName).get(null);
                        Object element = mo.getConstructor(me, int.class).newInstance(elementEnum, count);
                        potions[idx] = element;
                    }
                }
                idx++;
            }
            Object recipe = synth.getConstructor(ItemStack.class, boolean.class, int.class, potions.getClass()).newInstance(output, shaped, energyCost, potions);
            synth.getMethod("add", synth).invoke(null, recipe);
            return true;
        } catch (Exception e)
        {
            return false;
        }
    }

    /**
     * Adds a chemical decomposition fluid recipe to Minechem.
     * <p/>
     * The outputs are parsed in the form "number symbol", for example,
     * addDecompositionRecipe(myFluidStack, "1 H", "2 ethanol") would
     * cause the created recipe to output one hydrogen and two ethanol
     * when decomposed.
     *
     * @param input   The fluidStack to be decomposed.
     * @param outputs The molecules or elements produced by the recipe.
     * @return Whether the decomposition recipe was added.
     */
    public static boolean addDecompositionFluidRecipe(FluidStack input, String... outputs)
    {
        try
        {
            Class dfr = Class.forName("minechem.tileentity.decomposer.DecomposerFluidRecipe");
            Class dr = Class.forName("minechem.tileentity.decomposer.DecomposerRecipe");
            Class ee = Class.forName("minechem.item.element.ElementEnum");
            Class el = Class.forName("minechem.item.element.Element");

            Class me = Class.forName("minechem.item.molecule.MoleculeEnum");
            Class mo = Class.forName("minechem.item.molecule.Molecule");

            Object[] potions = (Object[]) Array.newInstance(Class.forName("minechem.potion.PotionChemical"), outputs.length);
            int idx = 0;
            for (String s : outputs)
            {
                String elementName = s.split(" ")[1];
                int count = Integer.parseInt(s.split(" ")[0]);
                try
                {//try to add element
                    Object elementEnum = ee.getField(elementName).get(null);
                    Object element = el.getConstructor(ee, int.class).newInstance(elementEnum, count);
                    potions[idx] = element;
                } catch (NoSuchFieldException e)
                {//else add molecule
                    Object elementEnum = me.getField(elementName).get(null);
                    Object element = mo.getConstructor(me, int.class).newInstance(elementEnum, count);
                    potions[idx] = element;
                }
                idx++;
            }
            Object dfrInst = dfr.getConstructor(FluidStack.class, potions.getClass()).newInstance(input, potions);
            dr.getMethod("add", dr).invoke(null, dfrInst);
            return true;
        } catch (Exception e)
        {
            return false;
        }
    }
}