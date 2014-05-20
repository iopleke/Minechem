package minechem.gui;

import minechem.ModMinechem;
import minechem.utils.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;

public class GuiVerticalScrollBar extends Gui
{

    Minecraft mc;
    IVerticalScrollContainer container;
    int mouseX;
    int mouseY;
    // Width, height of the scrollbar slider.
    int width = 12;
    int height = 15;
    // Current X, Y position of the scrollbar slider.
    int xpos;
    int ypos;
    int startingYPos;
    // Max Y Displacement of the scrollbar slider.
    int maxDisplacement;
    float scaleFactor;
    float scrollValue = 0.0F;
    boolean isDragging = false;
    int activeU = 232;
    int activeV = 0;
    int unactiveU = 244;
    int unactiveV = 0;

    public GuiVerticalScrollBar(IVerticalScrollContainer container, int x, int y, int maxDisplacement, int parentWidth, int parentHeight)
    {
        this.container = container;
        this.xpos = x;
        this.ypos = y;
        this.startingYPos = y;
        this.maxDisplacement = maxDisplacement - height;
        this.scaleFactor = 1.0F / this.maxDisplacement;
        this.mc = FMLClientHandler.instance().getClient();
    }

    public void handleMouseInput()
    {
        int screenWidth = container.getScreenWidth();
        int screenHeight = container.getScreenHeight();
        int parentWidth = container.getGuiWidth();
        int parentHeight = container.getGuiHeight();
        int i = Mouse.getEventX() * screenWidth / this.mc.displayWidth;
        int j = screenHeight - Mouse.getEventY() * screenHeight / this.mc.displayHeight - 1;
        mouseX = i - (screenWidth - parentWidth) / 2;
        mouseY = j - (screenHeight - parentHeight) / 2;
        int eventButton = Mouse.getEventButton();
        if (Mouse.getEventButtonState())
        {
            onMouseClick();
        }
        else if (eventButton == -1)
        {
            onMouseMoved(Mouse.getDX(), Mouse.getDY());
        }
        else if (eventButton == 1 || eventButton == 0)
        {
            onMouseRelease();
        }
        int wheelValue = Mouse.getEventDWheel();
        if (wheelValue != 0)
        {
            if (wheelValue > 0)
                onMouseScroll(wheelValue, true);
            else if (wheelValue < 0)
                onMouseScroll(wheelValue, false);
        }

    }

    public boolean pointIntersects(int x, int y)
    {
        return x >= xpos && x <= xpos + width && y >= ypos && y <= ypos + height;
    }

    /** Returns true iff the given GUI-relative point is within the scrollbar.
     * 
     * @param x X coordinate relative to the parent container.
     * @param y Y coordinate relative to the parent container.
     * @return True iff the coordinates are within this scrollbar. */
    public boolean pointInScrollBar(int x, int y)
    {
        return x >= xpos && x <= xpos + width && y >= startingYPos && y <= startingYPos + maxDisplacement + height;
    }

    public void setYPos(int y)
    {
        this.ypos = y;
        if (ypos < startingYPos)
            ypos = startingYPos;
        if (ypos > startingYPos + maxDisplacement)
            ypos = startingYPos + maxDisplacement;
        scrollValue = (ypos - startingYPos) * scaleFactor;
    }

    private void onMouseClick()
    {
        if (container.isScrollBarActive())
        {
            // Clicking on the slider starts dragging it.
            if (pointIntersects(mouseX, mouseY))
            {
                isDragging = true;
            }
            else if (pointInScrollBar(mouseX, mouseY))
            {
                // Move the slider one slider-height up or down.
                int scrollAmount = height;
                if (mouseY < ypos)
                {
                    // Up.
                    setYPos(ypos - scrollAmount);
                }
                else if (mouseY > ypos + height)
                {
                    // Down.
                    setYPos(ypos + scrollAmount);
                }
            }
        }
    }

    private void onMouseRelease()
    {
        isDragging = false;
    }

    private void onMouseMoved(int dx, int dy)
    {
        if (container.isScrollBarActive() && isDragging)
        {
            setYPos(mouseY);
        }
    }

    private void onMouseScroll(int value, boolean up)
    {
        if (!container.isScrollBarActive())
            return;
        if (up)
            setYPos(ypos - container.getScrollAmount());
        else
            setYPos(ypos + container.getScrollAmount());
    }

    public float getScrollValue()
    {
        return this.scrollValue;
    }

    public void draw()
    {
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.renderEngine.bindTexture(new ResourceLocation(Reference.TEXTURE_MOD_ID + Reference.TEXTURE_GUI_DIR + "allitems.png"));
        if (this.container.isScrollBarActive())
        {
            drawTexturedModalRect(xpos, ypos, activeU, activeV, width, height);
        }
        else
        {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.3F);
            drawTexturedModalRect(xpos, ypos, unactiveU, unactiveV, width, height);
        }
    }
}
