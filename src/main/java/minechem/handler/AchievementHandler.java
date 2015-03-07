package minechem.handler;

import minechem.chemical.Element;
import minechem.registry.AchievementRegistry;
import minechem.registry.BlockRegistry;
import minechem.registry.ElementRegistry;
import minechem.registry.ItemRegistry;
import net.minecraft.stats.Achievement;

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
        {
            AchievementRegistry.getInstance().addAchievement(element);
        }
        AchievementRegistry.getInstance().registerElementAchievements();
    }

    private static void initMinechem()
    {
        Achievement journal = AchievementRegistry.getInstance().addAchievement(ItemRegistry.journal.getUnlocalizedName(), 0, 0, ItemRegistry.journal);
        Achievement microscope = AchievementRegistry.getInstance().addAchievement(BlockRegistry.opticalMicroscope.getUnlocalizedName(), 1, 3, BlockRegistry.electrolysisBlock, journal);
        AchievementRegistry.getInstance().registerMinechemAchievements();
    }
}
