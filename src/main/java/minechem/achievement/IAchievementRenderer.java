package minechem.achievement;

/**
 * This can be applied and will be hooked into the render code of an {@link net.minecraft.stats.Achievement}
 * during the rendering in the {@link net.minecraft.client.gui.achievement.GuiAchievement}
 */
public interface IAchievementRenderer
{
    /**
     * Recolour the background of the {@link net.minecraft.stats.Achievement} icon in the {@link net.minecraft.client.gui.achievement.GuiAchievement}
     * @param greyScale the current gray scale
     * @return the int colour you want the background to be
     */
    public int recolourBackground(float greyScale);

    /**
     * Is there any special Icon rendering
     * @return if true renderIcon will be executed, if false the regular item rendering will be done
     */
    public boolean hasSpecialIconRenderer();

    /**
     * Any special render code
     * @param left left of the achievement icon
     * @param top top of the achievement icon
     */
    public void renderIcon(int left, int top);
}
