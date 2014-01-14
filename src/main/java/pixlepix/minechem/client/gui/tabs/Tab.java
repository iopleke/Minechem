package pixlepix.minechem.client.gui.tabs;

import pixlepix.minechem.client.gui.GuiContainerTabbed;
import pixlepix.minechem.common.utils.ConstantValue;
import pixlepix.minechem.common.utils.SessionVars;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;

public abstract class Tab {

    public FontRenderer tabFontRenderer = FMLClientHandler.instance().getClient().fontRenderer;
    public SoundManager tabSoundManager = FMLClientHandler.instance().getClient().sndManager;

    private boolean open;

    protected Gui myGui;
    public boolean leftSide;
    protected int overlayColor = 0xffffff;

    public int currentShiftX = 0;
    public int currentShiftY = 0;

    protected int limitWidth = 128;
    protected int maxWidth = 124;
    protected int minWidth = 22;
    protected int currentWidth = minWidth;

    protected int maxHeight = 22;
    protected int minHeight = 22;
    protected int currentHeight = minHeight;
    protected FontRenderer fontRenderer;
    protected TextureManager renderEngine;

    public Tab(Gui gui) {
        myGui = gui;
        fontRenderer = Minecraft.getMinecraft().fontRenderer;
        renderEngine = Minecraft.getMinecraft().renderEngine;
    }

    public abstract void draw(int x, int y);

    protected void drawBackground(int x, int y) {

        float colorR = (overlayColor >> 16 & 255) / 255.0F;
        float colorG = (overlayColor >> 8 & 255) / 255.0F;
        float colorB = (overlayColor & 255) / 255.0F;

        GL11.glColor4f(colorR, colorG, colorB, 1.0F);

        if (leftSide) {

            Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(ConstantValue.MOD_ID,ConstantValue.TAB_LEFT));

            myGui.drawTexturedModalRect(x - currentWidth, y + 4, 0, 256 - currentHeight + 4, 4, currentHeight - 4);
            myGui.drawTexturedModalRect(x - currentWidth + 4, y, 256 - currentWidth + 4, 0, currentWidth - 4, 4);
            myGui.drawTexturedModalRect(x - currentWidth, y, 0, 0, 4, 4);
            myGui.drawTexturedModalRect(x - currentWidth + 4, y + 4, 256 - currentWidth + 4, 256 - currentHeight + 4, currentWidth - 4, currentHeight - 4);
        } else {

        	 Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(ConstantValue.MOD_ID,ConstantValue.TAB_RIGHT));

            myGui.drawTexturedModalRect(x, y, 0, 256 - currentHeight, 4, currentHeight);
            myGui.drawTexturedModalRect(x + 4, y, 256 - currentWidth + 4, 0, currentWidth - 4, 4);
            myGui.drawTexturedModalRect(x, y, 0, 0, 4, 4);
            myGui.drawTexturedModalRect(x + 4, y + 4, 256 - currentWidth + 4, 256 - currentHeight + 4, currentWidth - 4, currentHeight - 4);
        }

        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
    }

    protected void drawIcon( int x, int y) {
    	ResourceLocation resource=this.getIcon();
    	 //Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(ConstantValue.MOD_ID,"textures/gui/allitems.png"));
    	 if(myGui instanceof GuiContainerTabbed){
    		 ((GuiContainerTabbed)myGui).drawTexture(x, y, resource);
    	 }else{
    		 System.out.println("Failed to draw tab icons on a minechem gui that was not GuiContainerTabbed. This is a bug");
    	 }
        //myGui.drawTexturedModelRectFromIcon(x, y, Block.cobblestone.getIcon(0, 0), 16, 16);
    
    }

    public int getHeight() {

        return currentHeight;
    }
    
    public abstract ResourceLocation getIcon();

    public abstract String getTooltip();

    public boolean handleMouseClicked(int x, int y, int mouseButton) {

        return false;
    }

    public boolean intersectsWith(int mouseX, int mouseY, int shiftX, int shiftY) {

        if (leftSide) {
            if (mouseX <= shiftX && mouseX >= shiftX - currentWidth && mouseY >= shiftY && mouseY <= shiftY + currentHeight) { return true; }
        } else if (mouseX >= shiftX && mouseX <= shiftX + currentWidth && mouseY >= shiftY && mouseY <= shiftY + currentHeight) { return true; }
        return false;
    }

    protected boolean isFullyOpened() {

        return currentWidth >= maxWidth;
    }

    public boolean isOpen() {

        return open;
    }

    public boolean isVisible() {

        return true;
    }

    public void setFullyOpen() {

        open = true;
        currentWidth = maxWidth;
        currentHeight = maxHeight;
    }

    public void toggleOpen() {

        if (open) {
            open = false;
            SessionVars.setOpenedTab(null);
        } else {
            open = true;
            SessionVars.setOpenedTab(this.getClass());
        }
    }

    public void update() {

        if (open && currentWidth < maxWidth) {
            currentWidth += 8;
        } else if (!open && currentWidth > minWidth) {
            currentWidth -= 8;
        }

        if (currentWidth > maxWidth) {
            currentWidth = maxWidth;
        } else if (currentWidth < minWidth) {
            currentWidth = minWidth;
        }

        if (open && currentHeight < maxHeight) {
            currentHeight += 8;
        } else if (!open && currentHeight > minHeight) {
            currentHeight -= 8;
        }

        if (currentHeight > maxHeight) {
            currentHeight = maxHeight;
        } else if (currentHeight < minHeight) {
            currentHeight = minHeight;
        }
    }
}