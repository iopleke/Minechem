package pixlepix.minechem.minechem.client.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

public class NEIMinechemConfig implements IConfigureNEI {

    @Override
    public String getName() {
        return "MineChem NEI Plugin";
    }

    @Override
    public String getVersion() {
        return "test-1";
    }

    @Override
    public void loadConfig() {
        DecomposerNEIRecipeHandler decomposerRecipeHandler = 
                new DecomposerNEIRecipeHandler();
        API.registerRecipeHandler(decomposerRecipeHandler);
        API.registerUsageHandler(decomposerRecipeHandler);
        
        SynthesisNEIRecipeHandler synthesisRecipeHandler = 
                new SynthesisNEIRecipeHandler();
        API.registerRecipeHandler(synthesisRecipeHandler);
        API.registerUsageHandler(synthesisRecipeHandler);
    }

}
