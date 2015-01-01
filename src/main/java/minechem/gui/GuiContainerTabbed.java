package minechem.gui;

import codechicken.nei.VisiblityData;
import codechicken.nei.api.INEIGuiHandler;
import codechicken.nei.api.TaggedInventoryArea;
import cpw.mods.fml.common.Optional;
import java.util.ArrayList;
import java.util.List;
import minechem.utils.Rect;
import minechem.utils.SessionVars;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

@Optional.Interface(iface = "codechicken.nei.api.INEIGuiHandler", modid = "NotEnoughItems")
public abstract class GuiContainerTabbed extends GuiMinechemContainer implements INEIGuiHandler
{

    protected static enum SlotColor
    {
        BLUE, RED, YELLOW, ORANGE, GREEN, PURPLE
    }

    protected static enum SlotType
    {
        SINGLE, OUTPUT, DOUBLEOUTPUT
    }

    protected static enum SlotRender
    {
        TOP, BOTTOM, FULL
    }

    protected static int SCALE_ENERGY = 42;
    protected static int SCALE_LIQUID = 60;
    protected static int SCALE_PROGRESS = 24;
    protected static int SCALE_SPEED = 16;

    protected ArrayList<GuiTab> tabListLeft = new ArrayList<GuiTab>();
    protected ArrayList<GuiTab> tabListRight = new ArrayList<GuiTab>();

    public int mouseX = 0;
    public int mouseY = 0;

    public static boolean drawBorders;

    private int _zLevel = 3;

    public void drawTexture(int x, int y, ResourceLocation resource)
    {
        int w = 16;
        int h = 16;
        this.mc.getTextureManager().bindTexture(resource);
        GL11.glColor4f(1F, 1F, 1F, 1F);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x + 0, y + h, this._zLevel, 0D, 1D);
        tessellator.addVertexWithUV(x + w, y + h, this._zLevel, 1D, 1D);
        tessellator.addVertexWithUV(x + w, y + 0, this._zLevel, 1D, 0D);
        tessellator.addVertexWithUV(x + 0, y + 0, this._zLevel, 0D, 0D);
        tessellator.draw();
    }

    public GuiContainerTabbed(Container container)
    {
        super(container);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int i, int j)
    {
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        drawTabs(mouseX, mouseY);

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    /* DRAW FUNCTIONS */
    protected void drawColoredSlot(int x, int y, SlotColor color, SlotType type, SlotRender render)
    {

        if (drawBorders)
        {
            drawColoredSlotWithBorder(x, y, color, type, render);
        } else
        {
            drawColoredSlotNoBorder(x, y, color, type, render);
        }
    }

    protected void drawColoredSlotNoBorder(int x, int y, SlotColor color, SlotType type, SlotRender render)
    {

        int sizeX = 0;
        int sizeY = 0;
        int offsetX = color.ordinal() / 3 * 128;
        int offsetY = color.ordinal() % 3 * 32;

        switch (type)
        {
            case SINGLE:
                sizeX = 16;
                sizeY = 16;
                offsetX += 8;
                offsetY += 8;
                break;
            case OUTPUT:
                sizeX = 24;
                sizeY = 24;
                offsetX += 36;
                offsetY += 4;
                break;
            case DOUBLEOUTPUT:
                sizeX = 42;
                sizeY = 24;
                offsetX += 75;
                offsetY += 4;
                break;
        }

        switch (render)
        {
            case TOP:
                sizeY /= 2;
                break;
            case BOTTOM:
                sizeY /= 2;
                y += sizeY;
                offsetY += sizeY;
                break;
            case FULL:
                break;
        }

        drawTexturedModalRect(x, y, offsetX, offsetY, sizeX, sizeY);
    }

    protected void drawColoredSlotWithBorder(int x, int y, SlotColor color, SlotType type, SlotRender render)
    {

        int sizeX = 32;
        int sizeY = 32;
        int offsetX = color.ordinal() / 3 * 128;
        int offsetY = color.ordinal() % 3 * 32;

        offsetX += type.ordinal() * 32;

        if (type.ordinal() == 2)
        {
            sizeX = 64;
        }

        switch (type)
        {
            case SINGLE:
                x -= 8;
                y -= 8;
                break;
            case OUTPUT:
                x -= 4;
                y -= 4;
                break;
            case DOUBLEOUTPUT:
                x -= 11;
                y -= 4;
                break;
        }

        switch (render)
        {
            case TOP:
                sizeY /= 2;
                break;
            case BOTTOM:
                sizeY /= 2;
                y += sizeY;
                offsetY += sizeY;
                break;
            case FULL:
                break;
        }

        drawTexturedModalRect(x, y, offsetX, offsetY, sizeX, sizeY);
    }

    protected void drawColoredLiquidSlot(int x, int y, SlotColor color)
    {

        if (drawBorders)
        {
            drawColoredLiquidSlotWithBorder(x, y, color);
        } else
        {
            drawColoredLiquidSlotNoBorder(x, y, color);
        }
    }

    protected void drawColoredLiquidSlotNoBorder(int x, int y, SlotColor color)
    {

        int sizeX = 16;
        int sizeY = 60;
        int offsetX = color.ordinal() * 32;
        int offsetY = 96;

        drawTexturedModalRect(x, y, offsetX + 8, offsetY + 2, sizeX, sizeY);
    }

    protected void drawColoredLiquidSlotWithBorder(int x, int y, SlotColor color)
    {

        int sizeX = 32;
        int sizeY = 64;
        int offsetX = color.ordinal() * 32;
        int offsetY = 96;

        drawTexturedModalRect(x - 8, y - 2, offsetX, offsetY, sizeX, sizeY);
    }

    protected void drawTooltip(String tooltip)
    {
        drawHoveringText(tooltip, mouseX, mouseY);
    }

    /* UTILITY FUNCTIONS */
    protected int getCenteredOffset(String string)
    {

        return this.getCenteredOffset(string, xSize);
    }

    protected int getCenteredOffset(String string, int xWidth)
    {
        FontRenderer fontRenderer = RenderManager.instance.getFontRenderer();

        return (xWidth - fontRenderer.getStringWidth(string)) / 2;
    }

    @Override
    protected void mouseClicked(int x, int y, int mouseButton)
    {

        super.mouseClicked(x, y, mouseButton);

        GuiTab guiTab = getTabAtPosition(mouseX, mouseY);

        if (guiTab != null)
        {

            if (guiTab.leftSide)
            {
                for (GuiTab other : tabListLeft)
                {
                    if (other != guiTab && other.isOpen())
                    {
                        other.toggleOpen();
                    }
                }
            } else
            {
                for (GuiTab other : tabListRight)
                {
                    if (other != guiTab && other.isOpen())
                    {
                        other.toggleOpen();
                    }
                }
            }
            guiTab.toggleOpen();
        }
    }

    @Override
    public void handleMouseInput()
    {
        super.handleMouseInput();
        int i = Mouse.getEventX() * this.width / this.mc.displayWidth;
        int j = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
        mouseX = i - (width - xSize) / 2;
        mouseY = j - (height - ySize) / 2;
    }

    /* TAB MANAGEMENT */
    public void addTab(GuiTab guiTab)
    {

        if (guiTab.leftSide)
        {
            tabListLeft.add(guiTab);
        } else
        {
            tabListRight.add(guiTab);
        }
        if (SessionVars.getOpenedTab() != null && guiTab.getClass().equals(SessionVars.getOpenedTab()))
        {
            guiTab.setFullyOpen();
        }
    }

    protected void drawTabs(int mX, int mY)
    {

        int yPos = 4;

        for (GuiTab guiTab : tabListLeft)
        {

            guiTab.update();
            if (!guiTab.isVisible())
            {
                continue;
            }
            guiTab.drawTab(0, yPos);
            yPos += guiTab.getHeight();
        }

        yPos = 4;
        for (GuiTab guiTab : tabListRight)
        {

            guiTab.update();
            if (!guiTab.isVisible())
            {
                continue;
            }

            guiTab.drawTab(xSize, yPos);
            yPos += guiTab.getHeight();
        }

        GuiTab guiTab = getTabAtPosition(mX, mY);
        if (guiTab != null)
        {
            String tooltip = guiTab.getTooltip();
            if (tooltip != null)
            {
                drawTooltip(tooltip);
            }
        }
    }

    protected GuiTab getTabAtPosition(int mX, int mY)
    {

        int xShift = 0;
        int yShift = 4;

        for (int i = 0; i < tabListLeft.size(); ++i)
        {
            GuiTab guiTab = tabListLeft.get(i);
            if (!guiTab.isVisible())
            {
                continue;
            }

            guiTab.currentX = xShift;
            guiTab.currentY = yShift;
            if (guiTab.intersectsWith(mX, mY, xShift, yShift))
            {
                return guiTab;
            }
            yShift += guiTab.getHeight();
        }

        xShift = xSize;
        yShift = 4;

        for (int i = 0; i < tabListRight.size(); ++i)
        {
            GuiTab guiTab = tabListRight.get(i);
            if (!guiTab.isVisible())
            {
                continue;
            }

            guiTab.currentX = xShift;
            guiTab.currentY = yShift;
            if (guiTab.intersectsWith(mX, mY, xShift, yShift))
            {
                return guiTab;
            }
            yShift += guiTab.getHeight();
        }

        return null;
    }

    public int getMouseX()
    {
        return (Mouse.getX() * this.width / this.mc.displayWidth);
    }

    public int getMouseY()
    {
        return this.height - (Mouse.getY() * this.height / this.mc.displayHeight - 1);
    }

    /* INEIGuiHandler */
    @Optional.Method(modid = "NotEnoughItems")
    @Override
    public VisiblityData modifyVisiblity(GuiContainer gui, VisiblityData currentVisibility)
    {
        return currentVisibility;
    }

    @Optional.Method(modid = "NotEnoughItems")
    @Override
    public Iterable<Integer> getItemSpawnSlots(GuiContainer gui, ItemStack item)
    {
        return null;
    }

    @Optional.Method(modid = "NotEnoughItems")
    @Override
    public boolean hideItemPanelSlot(GuiContainer gui, int x, int y, int w, int h)
    {
        if (gui instanceof GuiContainerTabbed)
        {
            Rect item = new Rect(x, y, w, h);
            GuiContainerTabbed container = (GuiContainerTabbed) gui;
            for (GuiTab tab : tabListRight)
            {
                Rect tabRect = new Rect(tab.currentX + container.guiLeft, tab.currentY + container.guiTop, tab.currentWidth, tab.currentHeight);
                if (item.intersectsWith(tabRect))
                {
                    return true;
                }
            }
        }
        return false;
    }

    @Optional.Method(modid = "NotEnoughItems")
    @Override
    public List<TaggedInventoryArea> getInventoryAreas(GuiContainer gui)
    {
        return null;
    }

    @Optional.Method(modid = "NotEnoughItems")
    @Override
    public boolean handleDragNDrop(GuiContainer gui, int mousex, int mousey, ItemStack draggedStack, int button)
    {
        return false;
    }
}
