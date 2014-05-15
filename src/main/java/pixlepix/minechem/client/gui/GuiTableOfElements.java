package pixlepix.minechem.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import pixlepix.minechem.client.gui.tabs.TabJournal;
import pixlepix.minechem.common.ModMinechem;
import pixlepix.minechem.common.containers.ContainerChemistJournal;
import pixlepix.minechem.common.utils.ConstantValue;

public class GuiTableOfElements extends GuiContainerTabbed {
    public static final int GUI_WIDTH = 876;
    public static final int GUI_HEIGHT = 600;

    public GuiTableOfElements(EntityPlayer entityPlayer) {
        super(new ContainerChemistJournal(entityPlayer.inventory));
        addTab(new TabJournal(this));
        this.xSize = GUI_WIDTH;
        this.ySize = GUI_HEIGHT;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2,
                                                   int var3) {
        int x = this.xSize;
        int y = this.ySize;
        GL11.glPushMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        GL11.glPushMatrix();
        GL11.glScalef(2.1F, 1.5F, 2.0F);

        this.mc.renderEngine.bindTexture(new ResourceLocation(ModMinechem.ID, ConstantValue.table_HEX));
        drawTexturedModalRect(0, 0, 0, 0, this.xSize / 2, this.ySize / 2);
        GL11.glPopMatrix();
        GL11.glPopMatrix();

    }

    @Override
    protected void mouseClicked(int x, int y, int mouseButton) {
        super.mouseClicked(x, y, mouseButton);
        if (x == GUI_WIDTH - 411) {
            if (y == GUI_HEIGHT - 411) {
                mc.thePlayer.openGui(ModMinechem.INSTANCE, 2, mc.theWorld, x, y, 0);
            }


        }
    }

    @Override
    public boolean hideItemPanelSlot(GuiContainer gui, int x, int y, int w,
                                     int h) {
        // TODO Auto-generated method stub
        return false;
    }
}
