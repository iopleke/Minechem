package minechem.registry;

import minechem.Compendium;
import minechem.achievement.ElementAchievement;
import minechem.chemical.Element;
import minechem.helper.PeriodicTableHelper;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class AchievementRegistry
{
    private static AchievementRegistry instance;
    private Map<Element, Achievement> elementsAchievementMap;
    private Map<String, Achievement> minechemAchievementMap;
    public AchievementPage elementsPage, minechemPage;
    
    private static Achievement nullAchievement = null;
    
    public static AchievementRegistry getInstance()
    {
        if (instance == null) instance = new AchievementRegistry();
        return instance;
    }
    
    private AchievementRegistry()
    {
        elementsAchievementMap = new TreeMap<Element, Achievement>();
        minechemAchievementMap = new TreeMap<String, Achievement>();
    }

    /**
     * Adds default achievement for an {@link minechem.chemical.Element}
     * @param element the element to create an achievement for
     */
    public void addAchievement(Element element)
    {
        int[] position = PeriodicTableHelper.getPosition(element);
        elementsAchievementMap.put(element, new ElementAchievement(element, position[1], position[0], ItemRegistry.augmentedItem));
    }

    /**
     * Creates and adds an {@link net.minecraft.stats.Achievement} to the  {@link net.minecraftforge.common.AchievementPage}
     * with given prerequisite {@link net.minecraft.stats.Achievement}
     * @param name the name for the achievement
     * @param row the row to display
     * @param column the column display
     * @param displayItemStack the {@link net.minecraft.item.ItemStack} to display
     * @param prerequisite the prerequisite {@link net.minecraft.stats.Achievement}
     */
    public void addAchievement(String name, int row, int column, ItemStack displayItemStack, Achievement prerequisite)
    {
        addAchievement(name, row, column, displayItemStack, prerequisite, false);
    }

    public void addAchievement(String name, int row, int column, Item displayItem, Achievement prerequisite)
    {
        addAchievement(name, row, column, new ItemStack(displayItem), prerequisite);
    }
    
    public void addAchievement(String name, int row, int column, Block displayBlock, Achievement prerequisite)
    {
        addAchievement(name, row, column, new ItemStack(displayBlock), prerequisite);
    }

    /**
     * Creates and adds an {@link net.minecraft.stats.Achievement} to the  {@link net.minecraftforge.common.AchievementPage}
     * with given prerequisite {@link net.minecraft.stats.Achievement}
     * @param name the name for the achievement
     * @param row the row to display
     * @param column the column display
     * @param displayItemStack the {@link net.minecraft.item.ItemStack} to display
     * @param prerequisite the prerequisite {@link net.minecraft.stats.Achievement}
     * @param isSpecial is this a special {@link net.minecraft.stats.Achievement} ?
     */
    public void addAchievement(String name, int row, int column, ItemStack displayItemStack, Achievement prerequisite, boolean isSpecial)
    {
        String statName = Compendium.Naming.id + "." + name;
        Achievement achievement = new Achievement("achievement." + statName, statName, column, row, displayItemStack, prerequisite);
        if (isSpecial) achievement.setSpecial();
        if (prerequisite == null) achievement.initIndependentStat();
        addAchievement(achievement);
    }

    /**
     * Creates and adds an {@link net.minecraft.stats.Achievement} to the  {@link net.minecraftforge.common.AchievementPage}
     * @param name the name for the achievement
     * @param row the row to display
     * @param column the column display
     * @param displayItemStack the {@link net.minecraft.item.ItemStack} to display
     */
    public void addAchievement(String name, int row, int column, ItemStack displayItemStack)
    {
        addAchievement(name, row, column, displayItemStack, nullAchievement, false);
    }

    public void addAchievement(String name, int row, int column, Item displayItem)
    {
        addAchievement(name, row, column, new ItemStack(displayItem), nullAchievement, false);
    }

    public void addAchievement(String name, int row, int column, Block displayBlock)
    {
        addAchievement(name, row, column, new ItemStack(displayBlock), nullAchievement, false);
    }

    /**
     * Add an {@link net.minecraft.stats.Achievement} to the minechem {@link net.minecraftforge.common.AchievementPage}
     * @param achievement the {@link net.minecraft.stats.Achievement} to add
     */
    public void addAchievement(Achievement achievement)
    {
        minechemAchievementMap.put(achievement.statId, achievement);
    }

    /**
     * Gets the achievement for a specific {@link minechem.chemical.Element}
     * @param element the element to find an {@link net.minecraft.stats.Achievement for}
     * @return can be null if the {@link minechem.chemical.Element} has no {@link net.minecraft.stats.Achievement}
     */
    public Achievement getAchievement(Element element)
    {
        return elementsAchievementMap.get(element);
    }

    /**
     * Get a minechem {@link net.minecraft.stats.Achievement} for the given name
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
            achievement.registerStat();
        elementsPage = new AchievementPage("Periodic Table", achievements.toArray(new Achievement[achievements.size()]));
        AchievementPage.registerAchievementPage(elementsPage);
    }

    /**
     * Registers {@link minechem.achievement.ElementAchievement}s to the minechem page
     */
    public void registerMinechemAchievements()
    {
        Collection<Achievement> achievements = minechemAchievementMap.values();
        for (Achievement achievement : achievements)
            achievement.registerStat();
        minechemPage = new AchievementPage(Compendium.Naming.name, achievements.toArray(new Achievement[achievements.size()]));
        AchievementPage.registerAchievementPage(minechemPage);
    }
}
