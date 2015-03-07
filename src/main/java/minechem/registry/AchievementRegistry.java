package minechem.registry;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import minechem.Compendium;
import minechem.achievement.ElementAchievement;
import minechem.achievement.ElementAchievementPage;
import minechem.chemical.Element;
import minechem.helper.PeriodicTableHelper;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

public class AchievementRegistry
{
    private static AchievementRegistry instance;
    private Map<Element, Achievement> elementsAchievementMap;
    private Map<String, Achievement> minechemAchievementMap;
    public AchievementPage elementsPage, minechemPage;

    private static Achievement nullAchievement = null;

    public static AchievementRegistry getInstance()
    {
        if (instance == null)
        {
            instance = new AchievementRegistry();
        }
        return instance;
    }

    private AchievementRegistry()
    {
        elementsAchievementMap = new TreeMap<Element, Achievement>();
        minechemAchievementMap = new TreeMap<String, Achievement>();
    }

    /**
     * Adds default achievement for an {@link minechem.chemical.Element}
     *
     * @param element the element to create an achievement for
     * @return the added {@link net.minecraft.stats.Achievement}
     */
    public Achievement addAchievement(Element element)
    {
        int[] position = PeriodicTableHelper.getPosition(element);
        ElementAchievement achievement = new ElementAchievement(element, position[0] - 5, position[1] - 8);
        elementsAchievementMap.put(element, achievement);
        return achievement;
    }

    /**
     * Creates and adds an {@link net.minecraft.stats.Achievement} to the {@link net.minecraftforge.common.AchievementPage} with given prerequisite {@link net.minecraft.stats.Achievement}
     *
     * @param name             the name for the achievement
     * @param row              the row to display
     * @param column           the column display
     * @param displayItemStack the {@link net.minecraft.item.ItemStack} to display
     * @param prerequisite     the prerequisite {@link net.minecraft.stats.Achievement}
     * @return the added {@link net.minecraft.stats.Achievement}
     */
    public Achievement addAchievement(String name, int row, int column, ItemStack displayItemStack, Achievement prerequisite)
    {
        return addAchievement(name, row, column, displayItemStack, prerequisite, false);
    }

    public Achievement addAchievement(String name, int row, int column, Item displayItem, Achievement prerequisite)
    {
        return addAchievement(name, row, column, new ItemStack(displayItem), prerequisite);
    }

    public Achievement addAchievement(String name, int row, int column, Block displayBlock, Achievement prerequisite)
    {
        return addAchievement(name, row, column, new ItemStack(displayBlock), prerequisite);
    }

    /**
     * Creates and adds an {@link net.minecraft.stats.Achievement} to the {@link net.minecraftforge.common.AchievementPage} with given prerequisite {@link net.minecraft.stats.Achievement}
     *
     * @param name             the name for the achievement
     * @param row              the row to display
     * @param column           the column display
     * @param displayItemStack the {@link net.minecraft.item.ItemStack} to display
     * @param prerequisite     the prerequisite {@link net.minecraft.stats.Achievement}
     * @param isSpecial        is this a special {@link net.minecraft.stats.Achievement} ?
     * @return the added {@link net.minecraft.stats.Achievement}
     */
    public Achievement addAchievement(String name, int row, int column, ItemStack displayItemStack, Achievement prerequisite, boolean isSpecial)
    {
        String statName = Compendium.Naming.id + "." + name;
        Achievement achievement = new Achievement("achievement." + statName, statName, column, row, displayItemStack, prerequisite);
        if (isSpecial)
        {
            achievement.setSpecial();
        }
        if (prerequisite == null)
        {
            achievement.initIndependentStat();
        }
        return addAchievement(achievement);
    }

    /**
     * Creates and adds an {@link net.minecraft.stats.Achievement} to the {@link net.minecraftforge.common.AchievementPage}
     *
     * @param name             the name for the achievement
     * @param row              the row to display
     * @param column           the column display
     * @param displayItemStack the {@link net.minecraft.item.ItemStack} to display
     * @return the added {@link net.minecraft.stats.Achievement}
     */
    public Achievement addAchievement(String name, int row, int column, ItemStack displayItemStack)
    {
        return addAchievement(name, row, column, displayItemStack, nullAchievement, false);
    }

    public Achievement addAchievement(String name, int row, int column, Item displayItem)
    {
        return addAchievement(name, row, column, new ItemStack(displayItem), nullAchievement, false);
    }

    public Achievement addAchievement(String name, int row, int column, Block displayBlock)
    {
        return addAchievement(name, row, column, new ItemStack(displayBlock), nullAchievement, false);
    }

    /**
     * Add an {@link net.minecraft.stats.Achievement} to the minechem {@link net.minecraftforge.common.AchievementPage}
     *
     * @param achievement the {@link net.minecraft.stats.Achievement} to add
     * @return the added {@link net.minecraft.stats.Achievement}
     */
    public Achievement addAchievement(Achievement achievement)
    {
        minechemAchievementMap.put(achievement.statId, achievement);
        return achievement;
    }

    /**
     * Gets the achievement for a specific {@link minechem.chemical.Element}
     *
     * @param element the element to find an {@link net.minecraft.stats.Achievement for}
     * @return can be null if the {@link minechem.chemical.Element} has no {@link net.minecraft.stats.Achievement}
     */
    public Achievement getAchievement(Element element)
    {
        return elementsAchievementMap.get(element);
    }

    /**
     * Get a minechem {@link net.minecraft.stats.Achievement} for the given name
     *
     * @param name the name of the achievement
     * @return can be null if the name does not exist in the registry
     */
    public Achievement getAchievement(String name)
    {
        return minechemAchievementMap.get("achievement." + Compendium.Naming.id + "." + name);
    }

    /**
     * Registers {@link minechem.achievement.ElementAchievement}s to the periodic table page
     */
    public void registerElementAchievements()
    {
        Collection<Achievement> achievements = elementsAchievementMap.values();
        for (Achievement achievement : achievements)
        {
            achievement.registerStat();
        }
        elementsPage = new ElementAchievementPage("Periodic Table", achievements.toArray(new Achievement[achievements.size()]));
        AchievementPage.registerAchievementPage(elementsPage);
    }

    /**
     * Registers {@link minechem.achievement.ElementAchievement}s to the minechem page
     */
    public void registerMinechemAchievements()
    {
        Collection<Achievement> achievements = minechemAchievementMap.values();
        for (Achievement achievement : achievements)
        {
            achievement.registerStat();
        }
        minechemPage = new AchievementPage(Compendium.Naming.name, achievements.toArray(new Achievement[achievements.size()]));
        AchievementPage.registerAchievementPage(minechemPage);
    }
}
