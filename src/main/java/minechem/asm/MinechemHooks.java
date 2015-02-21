package minechem.asm;

import minechem.achievement.IAchievementPageRenderer;
import minechem.achievement.IAchievementRenderer;
import minechem.achievement.MinecraftAchievementPage;
import minechem.item.IOverlay;
import minechem.proxy.client.render.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

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
        RenderHelper.setGreyscaleOpenGLColour(greyscale);
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

    public static void drawAchievementPageBackground(Minecraft mc, float scale, int columnWidth, int rowHeight, int currentPage)
    {
        if (currentPage != -1)
        {
            AchievementPage achievementPage = AchievementPage.getAchievementPage(currentPage);
            if (achievementPage instanceof IAchievementPageRenderer)
            {
                ((IAchievementPageRenderer) achievementPage).drawBackground(mc, 0, scale, columnWidth, rowHeight);
                return;
            }
        }
        MinecraftAchievementPage.drawBackground(mc, 0, scale, columnWidth, rowHeight);
    }
    
    public static float setScaleOnLoad(int currentPage)
    {
        if (currentPage != -1)
        {
            AchievementPage achievementPage = AchievementPage.getAchievementPage(currentPage);
            if (achievementPage instanceof IAchievementPageRenderer)
                return ((IAchievementPageRenderer) achievementPage).setScaleOnLoad();
        }
        return 1.0F;
    }
    
    public static float getMaxZoomOut(int currentPage)
    {
        if (currentPage != -1)
        {
            AchievementPage achievementPage = AchievementPage.getAchievementPage(currentPage);
            if (achievementPage instanceof IAchievementPageRenderer)
                return ((IAchievementPageRenderer) achievementPage).getMaxZoomOut();
        }
        return 2.0F;
    }

    public static float getMaxZoomIn(int currentPage)
    {
        if (currentPage != -1)
        {
            AchievementPage achievementPage = AchievementPage.getAchievementPage(currentPage);
            if (achievementPage instanceof IAchievementPageRenderer)
                return ((IAchievementPageRenderer) achievementPage).getMaxZoomIn();
        }
        return 1.0F;
    }
    
    public static void renderOverlay(FontRenderer fontRenderer, TextureManager textureManager, ItemStack itemStack, int left, int top, float z)
    {
        if (itemStack.getItem() instanceof IOverlay)
        {
            ((IOverlay) itemStack.getItem()).renderOverlay(fontRenderer, textureManager, itemStack, left, top, z);
        }
    }
}