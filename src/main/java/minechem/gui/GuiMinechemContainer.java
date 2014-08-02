package minechem.gui;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.RenderManager;

@SideOnly(Side.CLIENT)
public abstract class GuiMinechemContainer extends GuiScreen
{
    /**
     * Stacks renderer. Icons, stack size, health, etc...
     */
    protected static RenderItem vanillaItemRenderer = new RenderItem();

    public RenderItem itemRenderer = vanillaItemRenderer;

    private final FontRenderer fontRenderer;

    /**
     * The X size of the inventory window in pixels.
     */
    protected int xSize = 176;

    /**
     * The Y size of the inventory window in pixels.
     */
    protected int ySize = 166;

    /**
     * A list of the players inventory slots.
     */
    public Container inventorySlots;

    /**
     * Starting X position for the Gui. Inconsistent use for Gui backgrounds.
     */
    protected int guiLeft;

    /**
     * Starting Y position for the Gui. Inconsistent use for Gui backgrounds.
     */
    protected int guiTop;
    private Slot theSlot;

    /**
     * Used when touchscreen is enabled
     */
    private Slot clickedSlot = null;
    private boolean field_90018_r = false;

    /**
     * Used when touchscreen is enabled
     */
    private ItemStack draggedStack = null;
    private int guiXOffset = 0;
    private int guiYOffset = 0;
    private Slot returningStackDestSlot = null;
    private long returningStackTime = 0L;

    /**
     * Used when touchscreen is enabled
     */
    private ItemStack returningStack = null;
    private Slot field_92033_y = null;
    private long field_92032_z = 0L;

    public GuiMinechemContainer(Container container)
    {
        this.inventorySlots = container;
        this.fontRenderer = RenderManager.instance.getFontRenderer();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    @Override
    public void initGui()
    {
        super.initGui();
        this.mc.thePlayer.openContainer = this.inventorySlots;
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
    }

    /**
     * Draws the screen and all the components in it.
     */
    @Override
    public void drawScreen(int mouseX, int mouseY, float f)
    {
        this.drawDefaultBackground();
        this.drawGuiContainerBackgroundLayer(f, mouseX, mouseY);

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        super.drawScreen(mouseX, mouseY, f);

        RenderHelper.enableGUIStandardItemLighting();
        GL11.glPushMatrix();
        GL11.glTranslatef(this.guiLeft, this.guiTop, 0.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        this.theSlot = null;

        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int slotYPosition;

        for (int var13 = 0; var13 < this.inventorySlots.inventorySlots.size(); ++var13)
        {
            Slot slot = (Slot) this.inventorySlots.inventorySlots.get(var13);
            this.drawSlotInventory(slot);

            if (this.isMouseOverSlot(slot, mouseX, mouseY))
            {
                this.theSlot = slot;
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                int var8 = slot.xDisplayPosition;
                slotYPosition = slot.yDisplayPosition;
                this.drawGradientRect(var8, slotYPosition, var8 + 16, slotYPosition + 16, -2130706433, -2130706433);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_DEPTH_TEST);
            }
        }

        this.drawGuiContainerForegroundLayer(mouseX, mouseY);
        InventoryPlayer playerInventory = this.mc.thePlayer.inventory;
        ItemStack theStack = (this.draggedStack == null ? playerInventory.getItemStack() : this.draggedStack);

        if (theStack != null)
        {
            byte eight = 8;
            slotYPosition = this.draggedStack == null ? 8 : 16;

            if (this.draggedStack != null && this.field_90018_r)
            {
                theStack = theStack.copy();
                theStack.stackSize = MathHelper.ceiling_float_int(theStack.stackSize / 2.0F);
            }

            this.drawItemStack(theStack, mouseX - this.guiLeft - eight, mouseY - this.guiTop - slotYPosition);
        }

        if (this.returningStack != null)
        {
            float stackTime = (Minecraft.getSystemTime() - this.returningStackTime) / 100.0F;

            if (stackTime >= 1.0F)
            {
                stackTime = 1.0F;
                this.returningStack = null;
            }

            slotYPosition = this.returningStackDestSlot.xDisplayPosition - this.guiXOffset;
            int drawX = this.guiXOffset + (int) (slotYPosition * stackTime);
            int drawY = this.guiYOffset + (int) ((this.returningStackDestSlot.yDisplayPosition - this.guiYOffset) * stackTime);
            this.drawItemStack(this.returningStack, drawX, drawY);
        }

        if (playerInventory.getItemStack() == null && this.theSlot != null && this.theSlot.getHasStack())
        {
            ItemStack var19 = this.theSlot.getStack();
            this.drawItemStackTooltip(var19, mouseX - this.guiLeft + 8, mouseY - this.guiTop + 8);
        }

        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        RenderHelper.enableStandardItemLighting();
    }

    private void drawItemStack(ItemStack theStack, int drawX, int drawY)
    {
        GL11.glTranslatef(0.0F, 0.0F, 32.0F);
        this.zLevel = 200.0F;
        itemRenderer.zLevel = 200.0F;
        itemRenderer.renderItemAndEffectIntoGUI(this.fontRenderer, this.mc.renderEngine, theStack, drawX, drawY);
        itemRenderer.renderItemOverlayIntoGUI(this.fontRenderer, this.mc.renderEngine, theStack, drawX, drawY - (this.draggedStack == null ? 0 : 8));
        this.zLevel = 0.0F;
        itemRenderer.zLevel = 0.0F;
    }

    protected void drawItemStackTooltip(ItemStack theStack, int drawX, int drawY)
    {
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        List<?> stackToolTip = theStack.getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips);

        if (!stackToolTip.isEmpty())
        {
            int variableStringWidth = 0;
            int count;
            int stringWidth;

            for (count = 0; count < stackToolTip.size(); count = count + 1)
            {
                stringWidth = this.fontRenderer.getStringWidth((String) stackToolTip.get(count));

                if (stringWidth > variableStringWidth)
                {
                    variableStringWidth = stringWidth;
                }
            }

            count = drawX + 12;
            stringWidth = drawY - 12;
            int adjustedWidth = 8;

            if (stackToolTip.size() > 1)
            {
                adjustedWidth += 2 + (stackToolTip.size() - 1) * 10;
            }

            if (this.guiTop + stringWidth + adjustedWidth + 6 > this.height)
            {
                stringWidth = this.height - adjustedWidth - this.guiTop - 6;
            }

            // when you enter here
            // you foolish mortal being
            // abandon all hope
            this.zLevel = 300.0F;
            itemRenderer.zLevel = 300.0F;
            int var10 = -267386864;
            this.drawGradientRect(count - 3, stringWidth - 4, count + variableStringWidth + 3, stringWidth - 3, var10, var10);
            this.drawGradientRect(count - 3, stringWidth + adjustedWidth + 3, count + variableStringWidth + 3, stringWidth + adjustedWidth + 4, var10, var10);
            this.drawGradientRect(count - 3, stringWidth - 3, count + variableStringWidth + 3, stringWidth + adjustedWidth + 3, var10, var10);
            this.drawGradientRect(count - 4, stringWidth - 3, count - 3, stringWidth + adjustedWidth + 3, var10, var10);
            this.drawGradientRect(count + variableStringWidth + 3, stringWidth - 3, count + variableStringWidth + 4, stringWidth + adjustedWidth + 3, var10, var10);
            int var11 = 1347420415;
            int var12 = (var11 & 16711422) >> 1 | var11 & -16777216;
            this.drawGradientRect(count - 3, stringWidth - 3 + 1, count - 3 + 1, stringWidth + adjustedWidth + 3 - 1, var11, var12);
            this.drawGradientRect(count + variableStringWidth + 2, stringWidth - 3 + 1, count + variableStringWidth + 3, stringWidth + adjustedWidth + 3 - 1, var11, var12);
            this.drawGradientRect(count - 3, stringWidth - 3, count + variableStringWidth + 3, stringWidth - 3 + 1, var11, var11);
            this.drawGradientRect(count - 3, stringWidth + adjustedWidth + 2, count + variableStringWidth + 3, stringWidth + adjustedWidth + 3, var12, var12);

            for (count = 0; count < stackToolTip.size(); ++count)
            {
                String var14 = (String) stackToolTip.get(count);

                if (count == 0)
                {
                    var14 = "\u00a7" + theStack.getRarity().rarityColor + var14;
                } else
                {
                    var14 = "\u00a77" + var14;
                }

                this.fontRenderer.drawStringWithShadow(var14, count, stringWidth, -1);

                if (count == 0)
                {
                    stringWidth += 2;
                }

                stringWidth += 10;
            }

            this.zLevel = 0.0F;
            itemRenderer.zLevel = 0.0F;
        }
    }

    /**
     * Draws the text when mouse is over creative inventory tab. Params: current creative tab to be checked, current mouse x position, current mouse y position.
     */
    protected void drawCreativeTabHoveringText(String creativeTab, int mouseX, int mouseY)
    {
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        int tabNameWidth = this.fontRenderer.getStringWidth(creativeTab);
        int mouseXOffset = mouseX + 12;
        int mouseYOffset = mouseY - 12;
        byte eight = 8;
        this.zLevel = 300.0F;
        itemRenderer.zLevel = 300.0F;
        int var9 = -267386864;
        this.drawGradientRect(mouseXOffset - 3, mouseYOffset - 4, mouseXOffset + tabNameWidth + 3, mouseYOffset - 3, var9, var9);
        this.drawGradientRect(mouseXOffset - 3, mouseYOffset + eight + 3, mouseXOffset + tabNameWidth + 3, mouseYOffset + eight + 4, var9, var9);
        this.drawGradientRect(mouseXOffset - 3, mouseYOffset - 3, mouseXOffset + tabNameWidth + 3, mouseYOffset + eight + 3, var9, var9);
        this.drawGradientRect(mouseXOffset - 4, mouseYOffset - 3, mouseXOffset - 3, mouseYOffset + eight + 3, var9, var9);
        this.drawGradientRect(mouseXOffset + tabNameWidth + 3, mouseYOffset - 3, mouseXOffset + tabNameWidth + 4, mouseYOffset + eight + 3, var9, var9);
        int var10 = 1347420415;
        int var11 = (var10 & 16711422) >> 1 | var10 & -16777216;
        this.drawGradientRect(mouseXOffset - 3, mouseYOffset - 3 + 1, mouseXOffset - 3 + 1, mouseYOffset + eight + 3 - 1, var10, var11);
        this.drawGradientRect(mouseXOffset + tabNameWidth + 2, mouseYOffset - 3 + 1, mouseXOffset + tabNameWidth + 3, mouseYOffset + eight + 3 - 1, var10, var11);
        this.drawGradientRect(mouseXOffset - 3, mouseYOffset - 3, mouseXOffset + tabNameWidth + 3, mouseYOffset - 3 + 1, var10, var10);
        this.drawGradientRect(mouseXOffset - 3, mouseYOffset + eight + 2, mouseXOffset + tabNameWidth + 3, mouseYOffset + eight + 3, var11, var11);
        this.fontRenderer.drawStringWithShadow(creativeTab, mouseXOffset, mouseYOffset, -1);
        this.zLevel = 0.0F;
        itemRenderer.zLevel = 0.0F;
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        RenderHelper.enableStandardItemLighting();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int drawX, int drawY)
    {
        // not sure what this does
        // it's empty and has no name
        // perhaps no-one knows?
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected abstract void drawGuiContainerBackgroundLayer(float var1, int var2, int var3);

    /**
     * Draws an inventory slot
     */
    protected void drawSlotInventory(Slot theSlot)
    {
        int slotXPosition = theSlot.xDisplayPosition;
        int slotYPosition = theSlot.yDisplayPosition;
        ItemStack stackInSlot = theSlot.getStack();
        boolean hasBackgroundIconIndex = (theSlot == this.clickedSlot && this.draggedStack != null && !this.field_90018_r);

        if (theSlot == this.clickedSlot && this.draggedStack != null && this.field_90018_r && stackInSlot != null)
        {
            stackInSlot = stackInSlot.copy();
            stackInSlot.stackSize /= 2;
        }

        this.zLevel = 100.0F;
        itemRenderer.zLevel = 100.0F;

        if (stackInSlot == null)
        {
            Icon slotBackgroundIconIndex = theSlot.getBackgroundIconIndex();

            if (slotBackgroundIconIndex != null)
            {
                GL11.glDisable(GL11.GL_LIGHTING);
                this.mc.renderEngine.bindTexture(theSlot.getBackgroundIconTexture());
                this.drawTexturedModalRect(slotXPosition, slotYPosition, 0, 0, 16, 16);
                GL11.glEnable(GL11.GL_LIGHTING);
                hasBackgroundIconIndex = true;
            }
        }

        if (!hasBackgroundIconIndex)
        {
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            itemRenderer.renderItemAndEffectIntoGUI(this.fontRenderer, this.mc.renderEngine, stackInSlot, slotXPosition, slotYPosition);
            itemRenderer.renderItemOverlayIntoGUI(this.fontRenderer, this.mc.renderEngine, stackInSlot, slotXPosition, slotYPosition);
        }

        itemRenderer.zLevel = 0.0F;
        this.zLevel = 0.0F;
    }

    /**
     * Returns the slot at the given coordinates or null if there is none.
     */
    private Slot getSlotAtPosition(int mouseX, int mouseY)
    {
        for (int count = 0; count < this.inventorySlots.inventorySlots.size(); ++count)
        {
            Slot slotToCheck = (Slot) this.inventorySlots.inventorySlots.get(count);

            if (this.isMouseOverSlot(slotToCheck, mouseX, mouseY))
            {
                return slotToCheck;
            }
        }

        return null;
    }

    /**
     * Called when the mouse is clicked.
     */
    @Override
    protected void mouseClicked(int x, int y, int buttonID)
    {
        super.mouseClicked(x, y, buttonID);

        boolean isPickBlockKey = buttonID == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100;

        if (buttonID == 0 || buttonID == 1 || isPickBlockKey)
        {
            Slot theSlot = this.getSlotAtPosition(x, y);
            boolean isClickOutsideGUI = x < this.guiLeft || y < this.guiTop || x >= this.guiLeft + this.xSize || y >= this.guiTop + this.ySize;
            int slotNumber = -1;

            if (theSlot != null)
            {
                slotNumber = theSlot.slotNumber;
            }

            if (isClickOutsideGUI)
            {
                slotNumber = -999;
            }

            if (this.mc.gameSettings.touchscreen && isClickOutsideGUI && this.mc.thePlayer.inventory.getItemStack() == null)
            {
                this.mc.displayGuiScreen((GuiScreen) null);
                return;
            }

            if (slotNumber != -1)
            {
                if (this.mc.gameSettings.touchscreen)
                {
                    if (theSlot != null && theSlot.getHasStack())
                    {
                        this.clickedSlot = theSlot;
                        this.draggedStack = null;
                        this.field_90018_r = buttonID == 1;
                    } else
                    {
                        this.clickedSlot = null;
                    }
                } else if (isPickBlockKey)
                {
                    this.handleMouseClick(theSlot, slotNumber, buttonID, 3);
                } else
                {
                    boolean var10 = slotNumber != -999 && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));
                    this.handleMouseClick(theSlot, slotNumber, buttonID, var10 ? 1 : 0);
                }
            }
        }
    }

    /**
     * Called when the mouse is moved or a mouse button is released. Signature: (mouseX, mouseY, which) which==-1 is mouseMove, which==0 or which==1 is mouseUp
     */
    @Override
    protected void mouseMovedOrUp(int mouseX, int mouseY, int which)
    {
        if (this.clickedSlot != null && this.mc.gameSettings.touchscreen)
        {
            if (which == 0 || which == 1)
            {
                Slot theSlot = this.getSlotAtPosition(mouseX, mouseY);
                int var5 = this.guiLeft;
                int var6 = this.guiTop;
                boolean var7 = mouseX < this.guiLeft || mouseY < this.guiTop || mouseX >= this.guiLeft + this.xSize || mouseY >= this.guiTop + this.ySize;
                int var8 = -1;

                if (theSlot != null)
                {
                    var8 = theSlot.slotNumber;
                }

                if (var7)
                {
                    var8 = -999;
                }

                if (this.draggedStack == null && theSlot != this.clickedSlot)
                {
                    this.draggedStack = this.clickedSlot.getStack();
                }

                boolean var9 = this.stacksCanMerge(theSlot);

                if (var8 != -1 && this.draggedStack != null && var9)
                {
                    this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, which, 0);
                    this.handleMouseClick(theSlot, var8, 0, 0);

                    if (this.mc.thePlayer.inventory.getItemStack() != null)
                    {
                        this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, which, 0);
                        this.guiXOffset = mouseX - this.guiLeft;
                        this.guiYOffset = mouseY - this.guiTop;
                        this.returningStackDestSlot = this.clickedSlot;
                        this.returningStack = this.draggedStack;
                        this.returningStackTime = Minecraft.getSystemTime();
                    } else
                    {
                        this.returningStack = null;
                    }
                } else if (this.draggedStack != null)
                {
                    this.guiXOffset = mouseX - this.guiLeft;
                    this.guiYOffset = mouseY - this.guiTop;
                    this.returningStackDestSlot = this.clickedSlot;
                    this.returningStack = this.draggedStack;
                    this.returningStackTime = Minecraft.getSystemTime();
                }

                this.draggedStack = null;
                this.clickedSlot = null;
            }
        }
    }

    private boolean stacksCanMerge(Slot theSlot)
    {
        boolean slotIsNull = (theSlot == null || !theSlot.getHasStack());

        if (theSlot != null && theSlot.getHasStack() && this.draggedStack != null && ItemStack.areItemStackTagsEqual(theSlot.getStack(), this.draggedStack))
        {
            slotIsNull |= theSlot.getStack().stackSize + this.draggedStack.stackSize <= this.draggedStack.getMaxStackSize();
        }

        return slotIsNull;
    }

    /**
     * Returns if the passed mouse position is over the specified slot.
     */
    private boolean isMouseOverSlot(Slot par1Slot, int par2, int par3)
    {
        return this.func_74188_c(par1Slot.xDisplayPosition, par1Slot.yDisplayPosition, 16, 16, par2, par3);
    }

    protected boolean func_74188_c(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        int var7 = this.guiLeft;
        int var8 = this.guiTop;
        par5 -= var7;
        par6 -= var8;
        return par5 >= par1 - 1 && par5 < par1 + par3 + 1 && par6 >= par2 - 1 && par6 < par2 + par4 + 1;
    }

    protected void handleMouseClick(Slot par1Slot, int par2, int par3, int par4)
    {
        if (par1Slot != null)
        {
            par2 = par1Slot.slotNumber;
        }

        this.mc.playerController.windowClick(this.inventorySlots.windowId, par2, par3, par4, this.mc.thePlayer);
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    @Override
    protected void keyTyped(char par1, int par2)
    {
        if (par2 == 1 || par2 == this.mc.gameSettings.keyBindInventory.keyCode)
        {
            this.mc.thePlayer.closeScreen();
        }

        this.func_82319_a(par2);

        if (par2 == this.mc.gameSettings.keyBindPickBlock.keyCode && this.theSlot != null && this.theSlot.getHasStack())
        {
            this.handleMouseClick(this.theSlot, this.theSlot.slotNumber, this.ySize, 3);
        }
    }

    protected boolean func_82319_a(int par1)
    {
        if (this.mc.thePlayer.inventory.getItemStack() == null && this.theSlot != null)
        {
            for (int var2 = 0; var2 < 9; ++var2)
            {
                if (par1 == 2 + var2)
                {
                    this.handleMouseClick(this.theSlot, this.theSlot.slotNumber, var2, 2);
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    @Override
    public void onGuiClosed()
    {
        if (this.mc.thePlayer != null)
        {
            this.inventorySlots.onContainerClosed(this.mc.thePlayer);
        }
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    @Override
    public void updateScreen()
    {
        super.updateScreen();

        if (!this.mc.thePlayer.isEntityAlive() || this.mc.thePlayer.isDead)
        {
            this.mc.thePlayer.closeScreen();
        }
    }
}
