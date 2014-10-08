package minechem.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

public class NEIMinechemConfig implements IConfigureNEI
{

    @Override
    public String getName()
    {
        return "Minechem NEI Plugin";
    }

    @Override
    public String getVersion()
    {
        return "v1";
    }

    @Override
    public void loadConfig()
    {
        DecomposerNEIRecipeHandler decomposerRecipeHandler = new DecomposerNEIRecipeHandler();
        API.registerRecipeHandler(decomposerRecipeHandler);
        API.registerUsageHandler(decomposerRecipeHandler);

        SynthesisNEIRecipeHandler synthesisRecipeHandler = new SynthesisNEIRecipeHandler();
        API.registerRecipeHandler(synthesisRecipeHandler);
        API.registerUsageHandler(synthesisRecipeHandler);
    }

}
