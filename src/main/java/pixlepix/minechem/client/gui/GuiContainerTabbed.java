package pixlepix.minechem.client.gui;

import java.util.ArrayList;
import java.util.List;

import pixlepix.minechem.client.gui.tabs.Tab;
import pixlepix.minechem.common.utils.SessionVars;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import codechicken.nei.VisiblityData;
import codechicken.nei.api.INEIGuiHandler;
import codechicken.nei.api.TaggedInventoryArea;

public abstract class GuiContainerTabbed extends GuiMinechemContainer implements INEIGuiHandler {

    protected static enum SlotColor {
        BLUE,
        RED,
        YELLOW,
        ORANGE,
        GREEN,
        PURPLE;
    }

    protected static enum SlotType {
        SINGLE,
        OUTPUT,
        DOUBLEOUTPUT;
    }

    protected static enum SlotRender {
        TOP,
        BOTTOM,
        FULL;
    }

    protected static int SCALE_ENERGY = 42;
    protected static int SCALE_LIQUID = 60;
    protected static int SCALE_PROGRESS = 24;
    protected static int SCALE_SPEED = 16;

    protected ArrayList<Tab> tabListLeft = new ArrayList<Tab>();
    protected ArrayList<Tab> tabListRight = new ArrayList<Tab>();

    public int mouseX = 0;
    public int mouseY = 0;

    public static boolean drawBorders;

    public void drawTabIcon(){
    }

    public int zLevel=3;
    public void drawTexture(int x, int y, ResourceLocation resource)
    {
    	int w=16;
    	int h=16;
    	this.mc.getTextureManager().bindTexture(resource);
        GL11.glColor4f(1F, 1F, 1F, 1F);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x + 0, y + h, this.zLevel, 0D, 1D);
        tessellator.addVertexWithUV(x + w, y + h, this.zLevel, 1D, 1D);
        tessellator.addVertexWithUV(x + w, y + 0, this.zLevel, 1D, 0D);
        tessellator.addVertexWithUV(x + 0, y + 0, this.zLevel, 0D, 0D);
        tessellator.draw();
    }
    
    
    
    public GuiContainerTabbed(Container container) {
        super(container);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int i, int j) {
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        drawTabs(mouseX, mouseY);
        // drawTooltips(mouseX, mouseY);

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    /* DRAW FUNCTIONS */
    protected void drawColoredSlot(int x, int y, SlotColor color, SlotType type, SlotRender render) {

        if (drawBorders) {
            drawColoredSlotWithBorder(x, y, color, type, render);
        } else {
            drawColoredSlotNoBorder(x, y, color, type, render);
        }
    }

    protected void drawColoredSlotNoBorder(int x, int y, SlotColor color, SlotType type, SlotRender render) {

        int sizeX = 0;
        int sizeY = 0;
        int offsetX = color.ordinal() / 3 * 128;
        int offsetY = color.ordinal() % 3 * 32;

        switch (type) {
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

        switch (render) {
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

    protected void drawColoredSlotWithBorder(int x, int y, SlotColor color, SlotType type, SlotRender render) {

        int sizeX = 32;
        int sizeY = 32;
        int offsetX = color.ordinal() / 3 * 128;
        int offsetY = color.ordinal() % 3 * 32;

        offsetX += type.ordinal() * 32;

        if (type.ordinal() == 2) {
            sizeX = 64;
        }

        switch (type) {
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

        switch (render) {
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

    protected void drawColoredLiquidSlot(int x, int y, SlotColor color) {

        if (drawBorders) {
            drawColoredLiquidSlotWithBorder(x, y, color);
        } else {
            drawColoredLiquidSlotNoBorder(x, y, color);
        }
    }

    protected void drawColoredLiquidSlotNoBorder(int x, int y, SlotColor color) {

        int sizeX = 16;
        int sizeY = 60;
        int offsetX = color.ordinal() * 32;
        int offsetY = 96;

        drawTexturedModalRect(x, y, offsetX + 8, offsetY + 2, sizeX, sizeY);
    }

    protected void drawColoredLiquidSlotWithBorder(int x, int y, SlotColor color) {

        int sizeX = 32;
        int sizeY = 64;
        int offsetX = color.ordinal() * 32;
        int offsetY = 96;

        drawTexturedModalRect(x - 8, y - 2, offsetX, offsetY, sizeX, sizeY);
    }

    protected void drawTooltip(String tooltip) {

        drawCreativeTabHoveringText(tooltip, mouseX, mouseY);

    }

    protected void drawLiquid(int j, int k, int liquidId, int liquidMeta, int width, int height) {

        Icon liquidImg = null;

        if (liquidId < Block.blocksList.length && Block.blocksList[liquidId] != null) {
            liquidImg = Block.blocksList[liquidId].getBlockTextureFromSide(0);
        } else if (Item.itemsList[liquidId] != null) {
            liquidImg = Item.itemsList[liquidId].getIconFromDamage(liquidMeta);
        }
        if (liquidImg == null)
            return;
        int x = 0;
        int y = 0;

        int drawHeight = 0;
        int drawWidth = 0;

        for (x = 0; x < width; x += 16) {
            for (y = 0; y < height; y += 16) {

                drawWidth = Math.min(width - x, 16);
                drawHeight = Math.min(height - y, 16);

                drawTexturedModelRectFromIcon(j + x, k + y, liquidImg, drawWidth, drawHeight);
            }
        }
    }

    /* UTILITY FUNCTIONS */
    protected int getCenteredOffset(String string) {

        return this.getCenteredOffset(string, xSize);
    }

    protected int getCenteredOffset(String string, int xWidth) {

        return (xWidth - fontRenderer.getStringWidth(string)) / 2;
    }

    @Override
    protected void mouseClicked(int x, int y, int mouseButton) {

        super.mouseClicked(x, y, mouseButton);

        Tab tab = getTabAtPosition(mouseX, mouseY);

        if (tab != null && !tab.handleMouseClicked(mouseX, mouseY, mouseButton)) {

            if (tab.leftSide) {
                for (Tab other : tabListLeft) {
                    if (other != tab && other.isOpen()) {
                        other.toggleOpen();
                    }
                }
            } else {
                for (Tab other : tabListRight) {
                    if (other != tab && other.isOpen()) {
                        other.toggleOpen();
                    }
                }
            }
            tab.toggleOpen();
        }
    }

    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
        int i = Mouse.getEventX() * this.width / this.mc.displayWidth;
        int j = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
        mouseX = i - (width - xSize) / 2;
        mouseY = j - (height - ySize) / 2;
    }

    /* TAB MANAGEMENT */
    public void addTab(Tab tab) {

        if (tab.leftSide) {
            tabListLeft.add(tab);
        } else {
            tabListRight.add(tab);
        }
        if (SessionVars.getOpenedTab() != null && tab.getClass().equals(SessionVars.getOpenedTab())) {
            tab.setFullyOpen();
        }
    }

    protected void drawTabs(int mX, int mY) {

        int yPosRight = 4;

        for (Tab tab : tabListLeft) {

            tab.update();
            if (!tab.isVisible()) {
                continue;
            }
            tab.draw(0, yPosRight);
        }

        for (Tab tab : tabListRight) {

            tab.update();
            if (!tab.isVisible()) {
                continue;
            }

            tab.draw(xSize, yPosRight);
            yPosRight += tab.getHeight();
        }

        Tab tab = getTabAtPosition(mX, mY);
        if (tab != null) {
            String tooltip = tab.getTooltip();
            if (tooltip != null) {
                drawTooltip(tooltip);
            }
        }
    }

    protected Tab getTabAtPosition(int mX, int mY) {

        int xShift = 0;
        int yShift = 4;

        for (int i = 0; i < tabListLeft.size(); ++i) {
            Tab tab = tabListLeft.get(i);
            if (!tab.isVisible()) {
                continue;
            }

            tab.currentShiftX = xShift;
            tab.currentShiftY = yShift;
            if (tab.intersectsWith(mX, mY, xShift, yShift)) { return tab; }
            yShift += tab.getHeight();
        }

        xShift = xSize;
        yShift = 4;

        for (int i = 0; i < tabListRight.size(); ++i) {
            Tab tab = tabListRight.get(i);
            if (!tab.isVisible()) {
                continue;
            }

            tab.currentShiftX = xShift;
            tab.currentShiftY = yShift;
            if (tab.intersectsWith(mX, mY, xShift, yShift)) { return tab; }
            yShift += tab.getHeight();
        }

        return null;
    }

    public int getMouseX() {
        return (Mouse.getX() * this.width / this.mc.displayWidth);
    }

    public int getMouseY() {
        return this.height - (Mouse.getY() * this.height / this.mc.displayHeight - 1);
    }

    /* INEIGuiHandler */
    @Override
    public VisiblityData modifyVisiblity(GuiContainer gui, VisiblityData currentVisibility) {
        currentVisibility.showItemPanel = true;
        currentVisibility.showItemSection = true;
        /*
         * UNCOMMENT ME WHEN TUTORIAL TABS ARE COMPLETED!
         */
        // currentVisibility.showStateButtons = false;
        return currentVisibility;
    }

    @Override
    public int getItemSpawnSlot(GuiContainer gui, ItemStack item) {
        return -1;
    }

    @Override
    public List<TaggedInventoryArea> getInventoryAreas(GuiContainer gui) {
        return null;
    }

    @Override
    public boolean handleDragNDrop(GuiContainer gui, int mousex, int mousey, ItemStack draggedStack, int button) {
        return false;
    }
}