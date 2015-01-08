package minechem.registry;

public class RecipeRegistry
{

    private static final RecipeRegistry recipes = new RecipeRegistry();

    public static RecipeRegistry getInstance()
    {
        return recipes;
    }

    public void init()
    {
    }

}
