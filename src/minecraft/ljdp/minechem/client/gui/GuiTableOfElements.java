package ljdp.minechem.client.gui;

import ljdp.minechem.client.gui.tabs.Tab;
import ljdp.minechem.client.gui.tabs.TabJournel;
import ljdp.minechem.common.GuiHandler;
import ljdp.minechem.common.ModMinechem;
import ljdp.minechem.common.containers.ContainerChemistJournal;
import ljdp.minechem.common.utils.ConstantValue;
import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.opengl.GL11;

public class GuiTableOfElements extends GuiContainerTabbed {
    private static final int GUI_WIDTH = 302;
    private static final int GUI_HEIGHT = 191;

    public GuiTableOfElements(InventoryPlayer inventory) {
        super(new ContainerChemistJournal(inventory));
        addTab(new TabJournel(this));
        this.xSize = GUI_WIDTH;
        this.ySize = GUI_HEIGHT;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2,
            int var3) {
        int x = (width - this.xSize) / 2;
        int y = (height - this.ySize) / 2;
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 0);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        GL11.glPushMatrix();
        GL11.glScalef(1.2F, 0.86F, 1.0F);
        mc.renderEngine.bindTexture(ConstantValue.table_HEX);
        drawTexturedModalRect(0, 0, 0, 0, 250, 220);
        GL11.glPopMatrix();

        GL11.glPopMatrix();
    }

    @Override
    protected void mouseClicked(int x, int y, int mouseButton) {
        super.mouseClicked(x, y, mouseButton);

        Tab tab = getTabAtPosition(mouseX, mouseY);

        if (tab != null && !tab.handleMouseClicked(mouseX, mouseY, mouseButton)) {

            if (tab.leftSide) {
                for (Tab other : tabListLeft) {
                    if (other != tab && other.isOpen()) {

                    }
                }
            } else {
                for (Tab other : tabListRight) {
                    if (other != tab && other.isOpen()) {

                    }
                }
            }
            mc.thePlayer.openGui(ModMinechem.instance, GuiHandler.GUI_ID_JOURNAL, mc.theWorld, x, y, 0);
        }
    }

}
