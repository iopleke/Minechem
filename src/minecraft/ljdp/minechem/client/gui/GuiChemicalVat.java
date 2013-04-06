package ljdp.minechem.client.gui;

import org.lwjgl.opengl.GL11;

import ljdp.minechem.client.gui.tabs.TabHelp;
import ljdp.minechem.common.containers.ContainerVat;
import ljdp.minechem.common.tileentity.TileEntityVat;
import ljdp.minechem.common.utils.ConstantValue;
import ljdp.minechem.common.utils.MinechemHelper;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiChemicalVat extends GuiContainerTabbed {

    public static int GUI_WIDTH = 196;
    public static int GUI_HEIGHT = 166;
    public static int GUI_STORAGE_BAR_X = 36;
    public static int GUI_STORAGE_BAR_Y = 46;
    public static int GUI_MAX_STORAGE_WIDTH = 124;
    private TileEntityVat vat;
    private int storageBarWidth;

    public GuiChemicalVat(InventoryPlayer inventoryPlayer, TileEntityVat vat) {
        super(new ContainerVat(inventoryPlayer, vat));
        this.vat = vat;
        this.xSize = GUI_WIDTH;
        this.ySize = GUI_HEIGHT;
        addTab(new TabHelp(this, MinechemHelper.getLocalString("help.chemicalVat")));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int i, int j) {
        super.drawGuiContainerForegroundLayer(i, j);
        GL11.glDisable(GL11.GL_LIGHTING);
        int x = GUI_STORAGE_BAR_X + storageBarWidth + 1;
        int y = GUI_STORAGE_BAR_Y + 7;
        String info;
        int color = 0xFFFFFF;
        if (vat.isSpoiled) {
            info = "Chemical Waste";
        } else if (vat.chemical != null) {
            info = String.format("%s x %d", MinechemHelper.getChemicalName(vat.chemical), vat.amountOfChemical);
        } else {
            info = "Empty";
            fontRenderer.drawStringWithShadow("empty", x, y, 0xFFFFFF);
        }
        int infoWidth = fontRenderer.getStringWidth(info);
        int endX = GUI_STORAGE_BAR_X + GUI_MAX_STORAGE_WIDTH;
        if (endX - x <= infoWidth) {
            x = GUI_STORAGE_BAR_X + 1;
        }
        fontRenderer.drawStringWithShadow(info, x, y, color);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(ConstantValue.VAT_GUI);

        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 0.0F);
        drawTexturedModalRect(0, 0, 0, 0, xSize, ySize);
        drawStorageBar();
        drawBarMeters();
        GL11.glPopMatrix();
    }

    private void drawStorageBar() {
        int storageBarHeight = 24;
        storageBarWidth = (int) MinechemHelper.translateValue(vat.amountOfChemical, 0, TileEntityVat.MAX_CHEMICAL_AMOUNT, 0, GUI_MAX_STORAGE_WIDTH);

        drawTexturedModalRect(36, 46, 0, 166, storageBarWidth, storageBarHeight);
    }

    private void drawBarMeters() {
        drawTexturedModalRect(36, 67, 0, 190, 124, 3);
    }

}
