package minechem.api;

import java.lang.reflect.Array;

import net.minecraft.item.ItemStack;

public class RecipeAPI{

    /**
     * Adds a chemical decomposition recipe to Minechem.
     * 
     * The outputs are parsed in the form "number symbol", for example,
     *  addDecompositionRecipe(myItemStack, "1 H", "2 ethanol") would 
     *  cause the created recipe to output one hydrogen and two ethanol 
     *  when decomposed.
     * 
     * @param input The stack to be decomposed.
     * @param outputs The molecules or elements produced by the recipe.
     * @return Whether a decomposition recipe was added.
     */
    public static boolean addDecompositionRecipe(ItemStack input, String... outputs) {
        try{
            Class dr=Class.forName("minechem.tileentity.decomposer.DecomposerRecipe");
            Class ee=Class.forName("minechem.item.element.ElementEnum");
            Class el=Class.forName("minechem.item.element.Element");

            Class me=Class.forName("minechem.item.molecule.MoleculeEnum");
            Class mo=Class.forName("minechem.item.molecule.Molecule");

            Object[] potions=(Object[]) Array.newInstance(Class.forName("minechem.potion.PotionChemical"),outputs.length);
            int idx=0;
            for(String s:outputs){
                String elementName=s.split(" ")[1];
                String count=s.split(" ")[0];
                try{//try to add element
                    Object elementEnum=ee.getField(elementName).get(null);
                    Object element=el.getConstructor(ee, int.class).newInstance(elementEnum, 16);
                    potions[idx]=element;
                }catch(NoSuchFieldException e){//else add molecule
                    Object elementEnum=me.getField(elementName).get(null);
                    Object element=mo.getConstructor(me, int.class).newInstance(elementEnum, 16);
                    potions[idx]=element;
                }
                idx++;
            }
            Object drInst=dr.getConstructor(ItemStack.class,Class.forName("[Lminechem.potion.PotionChemical;"))
                    .newInstance(input,potions);
            dr.getMethod("add", dr).invoke(null, drInst);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    /**
     * Adds a chemical synthesis recipe to Minechem.
     * 
     * The inputs are parsed in the form "number symbol", for example,
     *  addSynthesisRecipe(stackOfGrass, "1 H", "2 ethanol") would 
     *  cause the created recipe to require one hydrogen and two ethanol 
     *  when decomposed.
     * 
     * Inputs may be the empty string; this leaves a gap in a shaped
     * recipe and is ignored in a shapeless recipe.
     * 
     * @return Whether a recipe was added.
     */
    public static boolean addSynthesisRecipe(ItemStack input, boolean shaped, int energyCost, String... outputs) {
        try{
            Class synth=Class.forName("minechem.tileentity.synthesis.SynthesisRecipe");
            Class ee=Class.forName("minechem.item.element.ElementEnum");
            Class el=Class.forName("minechem.item.element.Element");

            Class me=Class.forName("minechem.item.molecule.MoleculeEnum");
            Class mo=Class.forName("minechem.item.molecule.Molecule");

            Object[] potions=(Object[]) Array.newInstance(Class.forName("minechem.potion.PotionChemical"),outputs.length);
            int idx=0;
            for(String s:outputs){
                if(s.equals("")){
                    continue;
                }
                String elementName=s.split(" ")[1];
                String count=s.split(" ")[0];
                try{//try to add element
                    Object elementEnum=ee.getField(elementName).get(null);
                    Object element=el.getConstructor(ee, int.class).newInstance(elementEnum, 16);
                    potions[idx]=element;
                }catch(NoSuchFieldException e){//else add molecule
                    Object elementEnum=me.getField(elementName).get(null);
                    Object element=mo.getConstructor(me, int.class).newInstance(elementEnum, 16);
                    potions[idx]=element;
                }
                idx++;
            }
            Object recipe=synth.getConstructor(ItemStack.class, boolean.class, int.class, Class.forName("[Lminechem.potion.PotionChemical;"))
                    .newInstance(input,shaped,energyCost,potions);
            synth.getMethod("add",synth).invoke(null,recipe);
            return true;
        }catch(Exception e){
            return false;
        }
    }

}
