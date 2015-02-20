package minechem.achievement;

import minechem.Compendium;
import minechem.proxy.client.render.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

public class ElementAchievementPage extends AchievementPage implements IAchievementPageRenderer
{
    public ElementAchievementPage(String name, Achievement... achievements)
    {
        super(name, achievements);
    }

    @Override
    public void drawBackground(Minecraft mc, float z, float scale, int columnWidth, int rowHeight)
    {
        RenderHelper.drawTexturedRectUV(0, 0, z, 0, 0, 640, 480, Compendium.Resource.GUI.achievements);
    }

    @Override
    public float setScaleOnLoad()
    {
        return 2.0F;
    }
}
