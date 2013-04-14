package ljdp.minechem.client.gui;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class GuiVerticalScrollBar extends Gui {

    Minecraft mc;
    IVerticalScrollContainer container;
    int mouseX;
    int mouseY;
    int width = 12;
    int height = 15;
    int xpos;
    int ypos;
    int startingYPos;
    int maxDisplacement;
    float scaleFactor;
    float scrollValue = 0.0F;
    boolean isDragging = false;
    int activeU = 232;
    int activeV = 0;
    int unactiveU = 244;
    int unactiveV = 0;

    public GuiVerticalScrollBar(IVerticalScrollContainer container, int x, int y, int maxDisplacement, int parentWidth, int parentHeight) {
        this.container = container;
        this.xpos = x;
        this.ypos = y;
        this.startingYPos = y;
        this.maxDisplacement = maxDisplacement - height;
        this.scaleFactor = 1.0F / this.maxDisplacement;
        this.mc = FMLClientHandler.instance().getClient();
    }

    public void handleMouseInput() {
        int screenWidth = container.getScreenWidth();
        int screenHeight = container.getScreenHeight();
        int parentWidth = container.getGuiWidth();
        int parentHeight = container.getGuiHeight();
        int i = Mouse.getEventX() * screenWidth / this.mc.displayWidth;
        int j = screenHeight - Mouse.getEventY() * screenHeight / this.mc.displayHeight - 1;
        mouseX = i - (screenWidth - parentWidth) / 2;
        mouseY = j - (screenHeight - parentHeight) / 2;
        int eventButton = Mouse.getEventButton();
        if (Mouse.getEventButtonState()) {
            onMouseClick();
        } else if (eventButton == -1) {
            onMouseMoved(Mouse.getDX(), Mouse.getDY());
        } else if (eventButton == 1 || eventButton == 0) {
            onMouseRelease();
        }
        int wheelValue = Mouse.getEventDWheel();
        if (wheelValue != 0) {
            if (wheelValue > 0)
                onMouseScroll(wheelValue, true);
            else if (wheelValue < 0)
                onMouseScroll(wheelValue, false);
        }

    }

    public boolean pointIntersects(int x, int y) {
        return x >= xpos && x <= xpos + width && y >= ypos && y <= ypos + height;
    }

    public void setYPos(int y) {
        this.ypos = y;
        if (ypos < startingYPos)
            ypos = startingYPos;
        if (ypos > startingYPos + maxDisplacement)
            ypos = startingYPos + maxDisplacement;
        scrollValue = (ypos - startingYPos) * scaleFactor;
    }

    private void onMouseClick() {
        if (container.isScrollBarActive() && pointIntersects(mouseX, mouseY)) {
            isDragging = true;
        }
    }

    private void onMouseRelease() {
        isDragging = false;
    }

    private void onMouseMoved(int dx, int dy) {
        if (container.isScrollBarActive() && isDragging) {
            setYPos(mouseY);
        }
    }

    private void onMouseScroll(int value, boolean up) {
        if (!container.isScrollBarActive())
            return;
        if (up)
            setYPos(ypos - container.getScrollAmount());
        else
            setYPos(ypos + container.getScrollAmount());
    }

    public float getScrollValue() {
        return this.scrollValue;
    }

    public void draw() {
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture("/gui/allitems.png");
        if (this.container.isScrollBarActive()) {
            drawTexturedModalRect(xpos, ypos, activeU, activeV, width, height);
        } else {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.3F);
            drawTexturedModalRect(xpos, ypos, unactiveU, unactiveV, width, height);
        }
    }
}
