package minechem.asm;

import minechem.achievement.IAchievementRenderer;
import minechem.helper.ColourHelper;
import net.minecraft.stats.Achievement;
import org.lwjgl.opengl.GL11;

public class MinechemHooks
{
    public static void recolourAchievement(Achievement achievement, float greyscale)
    {
        if (achievement instanceof IAchievementRenderer)
        {
            int colour = ((IAchievementRenderer) achievement).recolourBackground(greyscale);
            GL11.glColor3f(ColourHelper.getRed(colour), ColourHelper.getGreen(colour), ColourHelper.getBlue(colour));
        }
    }
    
    public static void resetGreyscale(float greyscale)
    {
        GL11.glColor4f(greyscale, greyscale, greyscale, 1.0F);
    }
}
