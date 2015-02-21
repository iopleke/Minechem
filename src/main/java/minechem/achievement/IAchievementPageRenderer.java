package minechem.achievement;

import net.minecraft.client.Minecraft;

public interface IAchievementPageRenderer
{
    public void drawBackground(Minecraft mc, float z, float scale, int columnWidth, int rowHeight);
    
    public float setScaleOnLoad();
    
    public float getMaxZoomOut();

    public float getMaxZoomIn();
}
