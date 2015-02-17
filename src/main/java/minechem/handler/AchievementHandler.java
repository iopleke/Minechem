package minechem.handler;

import minechem.chemical.Element;
import minechem.registry.AchievementRegistry;
import minechem.registry.ElementRegistry;
import minechem.registry.ItemRegistry;

public class AchievementHandler
{
    public static void init()
    {
        initMinechem();
        initElements();
    }
    
    private static void initElements()
    {
        for (Element element : ElementRegistry.getInstance().getElements())
            AchievementRegistry.getInstance().addAchievement(element);
        AchievementRegistry.getInstance().registerElementAchievements();
    }
    
    private static void initMinechem()
    {
        AchievementRegistry.getInstance().addAchievement(ItemRegistry.journal.getUnlocalizedName(), 0, 0, ItemRegistry.journal);
        AchievementRegistry.getInstance().registerMinechemAchievements();
    }
}
