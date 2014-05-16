package minechem.computercraft;

import minechem.api.recipe.SynthesisRecipe;

public interface IMinechemTurtlePeripheral {

    /**
     * Get the stored synthesis recipe.
     *
     * @return
     */
    public SynthesisRecipe getSynthesisRecipe();

    /**
     * Store a synthesis recipe
     *
     * @param synthesisRecipe
     */
    public void setSynthesisRecipe(SynthesisRecipe synthesisRecipe);

    /**
     * Get an array of methods the peripheral implements.
     *
     * @return
     */
    public ICCMethod[] getMethods();

}
