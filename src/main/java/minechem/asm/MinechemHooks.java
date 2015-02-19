package minechem.asm;

import minechem.achievement.IAchievementRenderer;
import minechem.proxy.client.render.RenderHelper;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import org.lwjgl.opengl.GL11;

public class MinechemHooks
{
    public static void recolourAchievement(Achievement achievement, float greyscale)
    {
        if (achievement instanceof IAchievementRenderer)
        {
            int colour = ((IAchievementRenderer) achievement).recolourBackground(greyscale);
            RenderHelper.setOpenGLColour(colour);
        }
    }

    public static void resetGreyscale(float greyscale)
    {
        GL11.glColor4f(greyscale, greyscale, greyscale, 1.0F);
    }

    public static void drawIconAchievement(RenderItem renderItem, FontRenderer fontRenderer, TextureManager textureManager, final ItemStack itemStack, int x, int y, Achievement achievement)
    {
        if (achievement instanceof IAchievementRenderer && ((IAchievementRenderer) achievement).hasSpecialIconRenderer())
        {
            ((IAchievementRenderer) achievement).renderIcon(fontRenderer, textureManager, itemStack, x, y);
        } else
        {
            renderItem.renderItemAndEffectIntoGUI(fontRenderer, textureManager, itemStack, x, y);
        }
    }
}
