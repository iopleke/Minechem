package ljdp.minechem.client.gui;

import java.util.HashMap;

import ljdp.minechem.common.utils.MinechemHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.client.FMLClientHandler;

public abstract class GuiToggleSwitch {
    class ToggleButton {
        int u;
        int v;
        String tooltip;
    }

    int numStates;
    int state = 0;
    int zLevel = 100;
    int width;
    int height;
    int x;
    int y;
    int mouseX;
    int mouseY;
    Minecraft mc;
    GuiContainerTabbed container;
    ResourceLocation texture;
    HashMap<Integer, ToggleButton> buttons = new HashMap<Integer, ToggleButton>();

    public GuiToggleSwitch() {
        this.mc = FMLClientHandler.instance().getClient();
    }

    public void draw(TextureManager renderEngine) {
        renderEngine.func_110581_b(texture);
        ToggleButton button = buttons.get(state);
        drawTexturedModalRect(x, y, button.u, button.v, width, height);
        String tooltip = MinechemHelper.getLocalString(button.tooltip);
        int cx = (container.width - container.xSize) / 2;
        int cy = (container.height - container.ySize) / 2;
        int tooltipWidth = mc.fontRenderer.getStringWidth(tooltip);

        if (isMoverOver())
            container.drawCreativeTabHoveringText(tooltip, cx + 77 - (tooltipWidth / 2), cy + 100);
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void mouseClicked(int x, int y, int mouseButton) {
        if (isMoverOver()) {
            onClicked();
        }
    }

    public boolean isMoverOver() {
        mouseX = container.getMouseX();
        mouseY = container.getMouseY();
        if (mouseX > this.x && mouseX < this.x + width && mouseY > this.y && mouseY < this.y + height) { return true; }
        return false;
    }

    public int getState() {
        return this.state;
    }

    private void onClicked() {
        this.state++;
        if (this.state == this.numStates)
            this.state = 0;
    }

    /**
     * Draws a textured rectangle at the stored z-value. Args: x, y, u, v, width, height
     */
    public void drawTexturedModalRect(int par1, int par2, int par3, int par4, int par5, int par6) {
        float var7 = 0.00390625F;
        float var8 = 0.00390625F;
        Tessellator var9 = Tessellator.instance;
        var9.startDrawingQuads();
        var9.addVertexWithUV((double) (par1 + 0), (double) (par2 + par6), (double) this.zLevel, (double) ((float) (par3 + 0) * var7),
                (double) ((float) (par4 + par6) * var8));
        var9.addVertexWithUV((double) (par1 + par5), (double) (par2 + par6), (double) this.zLevel, (double) ((float) (par3 + par5) * var7),
                (double) ((float) (par4 + par6) * var8));
        var9.addVertexWithUV((double) (par1 + par5), (double) (par2 + 0), (double) this.zLevel, (double) ((float) (par3 + par5) * var7),
                (double) ((float) (par4 + 0) * var8));
        var9.addVertexWithUV((double) (par1 + 0), (double) (par2 + 0), (double) this.zLevel, (double) ((float) (par3 + 0) * var7),
                (double) ((float) (par4 + 0) * var8));
        var9.draw();
    }

}
