package minechem.apparatus.tier1.opticalMicroscope;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class MicroscopeRenderItem extends RenderItem
{
    public OpticalMicroscopeGUI microscopeGui;

    public MicroscopeRenderItem(OpticalMicroscopeGUI microscopeGui)
    {
        super();
        this.microscopeGui = microscopeGui;
    }

    private void setScissor(float x, float y, int w, int h)
    {
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution scaledRes = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        int scale = scaledRes.getScaleFactor();
        x *= scale;
        y *= scale;
        w *= scale;
        h *= scale;
        float guiScaledWidth = (microscopeGui.getXSize() * scale);
        float guiScaledHeight = (microscopeGui.getYSize() * scale);
        float guiLeft = ((mc.displayWidth / 2) - guiScaledWidth / 2);
        float guiTop = ((mc.displayHeight / 2) + guiScaledHeight / 2);
        int scissorX = Math.round((guiLeft + x));
        int scissorY = Math.round(((guiTop - h) - y));
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor(scissorX, scissorY, w, h);
    }

    private void stopScissor()
    {
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

    @Override
    public void renderItemAndEffectIntoGUI(FontRenderer fontRenderer, TextureManager textureManager, ItemStack itemStack, int x, int y)
    {
        if (itemStack == null)
        {
            return;
        }

        RenderHelper.enableGUIStandardItemLighting();

        Slot slot = microscopeGui.inventorySlots.getSlotFromInventory(microscopeGui.opticalMicroscope, 0);
        if (slot.getStack() != null)
        {
            GL11.glPushMatrix();
            setScissor(OpticalMicroscopeGUI.eyePieceX, OpticalMicroscopeGUI.eyePieceY, OpticalMicroscopeGUI.eyePieceW, OpticalMicroscopeGUI.eyePieceH);
            int renderX = microscopeGui.getGuiLeft() + slot.xDisplayPosition;
            int renderY = microscopeGui.getGuiTop() + slot.yDisplayPosition;
            GL11.glTranslatef(renderX, renderY, 0.0F);
            GL11.glScalef(3.0F, 3.0F, 1.0F);
            GL11.glTranslatef(-renderX - 5.4F, -renderY - 4.5F, 540.0F);
            super.renderItemAndEffectIntoGUI(fontRenderer, textureManager, slot.getStack(), renderX, renderY);
            stopScissor();
            GL11.glPopMatrix();
        }

        if (itemStack == microscopeGui.getContainer().getInventoryPlayer().getItemStack() && microscopeGui.isMouseInMicroscope())
        {
            GL11.glPushMatrix();
            setScissor(OpticalMicroscopeGUI.eyePieceX, OpticalMicroscopeGUI.eyePieceY, OpticalMicroscopeGUI.eyePieceW, OpticalMicroscopeGUI.eyePieceH);
            GL11.glTranslatef(x, y, 0.0F);
            GL11.glScalef(3.0F, 3.0F, 1.0F);
            GL11.glTranslatef(-x - 8.0F, -y - 8.0F, 540.0F);
            super.renderItemAndEffectIntoGUI(fontRenderer, textureManager, itemStack, x, y);
            stopScissor();
            GL11.glPopMatrix();
        }
    }
}
