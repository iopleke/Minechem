package minechem.tileentity.synthesis;

import minechem.gui.GuiContainerTabbed;
import minechem.gui.GuiTabHelp;
import minechem.gui.GuiTabPatreon;
import minechem.reference.Resources;
import minechem.utils.MinechemUtil;
import net.minecraft.entity.player.InventoryPlayer;
import org.lwjgl.opengl.GL11;

public class SynthesisGui extends GuiContainerTabbed
{
    int guiWidth = 176;
    int guiHeight = 205;

    public SynthesisGui(InventoryPlayer inventoryPlayer, SynthesisTileEntity synthesis)
    {
        super(new SynthesisContainer(inventoryPlayer, synthesis));
        this.xSize = guiWidth;
        this.ySize = guiHeight;
        addTab(new SynthesisTabStateControl(this, synthesis));
        addTab(new GuiTabHelp(this, MinechemUtil.getLocalString("help.synthesis")));
        addTab(new GuiTabPatreon(this));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        super.drawGuiContainerForegroundLayer(par1, par2);
        String info = MinechemUtil.getLocalString("gui.title.synthesis");
        int infoWidth = fontRendererObj.getStringWidth(info);
        fontRendererObj.drawString(info, (guiWidth - infoWidth) / 2, 5, 0x000000);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.renderEngine.bindTexture(Resources.Gui.SYNTHESIS);
        int x = (width - guiWidth) / 2;
        int y = (height - guiHeight) / 2;
        drawTexturedModalRect(x, y, 0, 0, guiWidth, guiHeight);
    }

}
