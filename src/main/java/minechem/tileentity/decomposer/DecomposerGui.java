package minechem.tileentity.decomposer;

import minechem.gui.GuiContainerTabbed;
import minechem.gui.GuiFluidTank;
import minechem.gui.GuiTabHelp;
import minechem.gui.GuiTabPatreon;
import minechem.reference.Resources;
import minechem.utils.MinechemUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import org.lwjgl.opengl.GL11;

public class DecomposerGui extends GuiContainerTabbed
{
    private DecomposerTileEntity decomposer;
    private GuiFluidTank guiFluidTank;
    private GuiButton dumpButton;
    int guiWidth = 176;
    int guiHeight = 186;

    public DecomposerGui(InventoryPlayer inventoryPlayer, DecomposerTileEntity decomposer)
    {
        super(new DecomposerContainer(inventoryPlayer, decomposer));
        this.xSize = guiWidth;
        this.ySize = guiHeight;
        this.decomposer = decomposer;
        addTab(new DecomposerTabStateControl(this, decomposer));
        addTab(new GuiTabHelp(this, MinechemUtil.getLocalString("help.decomposer")));
        addTab(new GuiTabPatreon(this));
        guiFluidTank = new GuiFluidTank(decomposer.capacity, 18, 16);
        dumpButton = new GuiButton(0, 20, 5, 12, 12, "x");
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        super.drawGuiContainerForegroundLayer(par1, par2);
        String info = MinechemUtil.getLocalString("gui.title.decomposer");
        int infoWidth = fontRendererObj.getStringWidth(info);
        fontRendererObj.drawString(info, (guiWidth - infoWidth) / 2, 5, 0x000000);

        guiFluidTank.drawTooltip(mouseX, mouseY, decomposer.tank);
        dumpButton.drawButton(mc, mouseX, mouseY);
        GL11.glDisable(GL11.GL_BLEND);

        if (mouseInButton(mouseX, mouseY))
        {
            drawHoveringText(MinechemUtil.getLocalString("gui.title.decomposer.dump"), mouseX, mouseY);
        }
    }

    @Override
    protected void mouseClicked(int x, int y, int mouseButton)
    {
        super.mouseClicked(x, y, mouseButton);
        if (mouseInButton(mouseX, mouseY))
        {
            decomposer.dumpFluid();
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.renderEngine.bindTexture(Resources.Gui.DECOMPOSER);
        int x = (width - guiWidth) / 2;
        int y = (height - guiHeight) / 2;
        drawTexturedModalRect(x, y, 0, 0, guiWidth, guiHeight);

        guiFluidTank.draw(x, y, decomposer.tank);
    }

    private boolean mouseInButton(int x, int y)
    {
        return x >= dumpButton.xPosition && x < dumpButton.xPosition + dumpButton.width && y >= dumpButton.yPosition && y < dumpButton.yPosition + dumpButton.height;
    }

}
