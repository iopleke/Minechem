package ljdp.minechem.client.gui;

import ljdp.minechem.client.gui.tabs.TabEnergy;
import ljdp.minechem.client.gui.tabs.TabEnergySynthesis;
import ljdp.minechem.client.gui.tabs.TabHelp;
import ljdp.minechem.client.gui.tabs.TabStateControlSynthesis;
import ljdp.minechem.common.containers.ContainerBluePrintPrinter;
import ljdp.minechem.common.network.PacketHandler;
import ljdp.minechem.common.network.PacketSwapItem;
import ljdp.minechem.common.tileentity.TileEntityBluePrintPrinter;
import ljdp.minechem.common.utils.ConstantValue;
import ljdp.minechem.common.utils.MinechemHelper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class GuiPrinter extends GuiContainerTabbed {
	 public int mouseX = 0;
	    public int mouseY = 0;
    int guiWidth = 176;
    int guiHeight = 187;
    private GuiButton _settingSilkTouch;
	private GuiButton _settingSmallShrooms;
	private TileEntityBluePrintPrinter synthesis;
    public GuiPrinter(InventoryPlayer inventoryPlayer, TileEntityBluePrintPrinter tileEntity) {
    	 super(new ContainerBluePrintPrinter(inventoryPlayer, tileEntity));
    	 this.synthesis = tileEntity;
    	    this.xSize = guiWidth;
            this.ySize = guiHeight;
            this.addTab(new TabEnergy(this, synthesis));
            
            addTab(new TabHelp(this, MinechemHelper.getLocalString("help.synthesis")));
    }



    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
    	String info = "BluePrint Printer";
        int infoWidth = fontRenderer.getStringWidth(info);
        fontRenderer.drawString(info, (guiWidth - infoWidth) / 2, 5, 0x000000);
        _settingSilkTouch = new GuiButton(1, guiWidth + 100, guiHeight - 110, 50, 20, "Fusion!");
        _settingSmallShrooms = new GuiButton(2, guiWidth + 100, guiHeight - 80, 70, 20, "Direwolf20");
        buttonList.add(_settingSilkTouch);
    	buttonList.add(_settingSmallShrooms);
    	drawTabs(mouseX, mouseY);
    }


	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2,
			int var3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.renderEngine.func_110581_b(new ResourceLocation(ConstantValue.MOD_ID,ConstantValue.PRINT_GUI));
        int x = (width - guiWidth) / 2;
        int y = (height - guiHeight) / 2;
        drawTexturedModalRect(x, y, 0, 0, guiWidth, guiHeight);
		
	}
	@Override
	public void actionPerformed(GuiButton button)
	{
	switch(button.id)
	{
	case 1:  PacketSwapItem packet = new PacketSwapItem(synthesis.xCoord, synthesis.yCoord, synthesis.zCoord, 0);
    PacketHandler.getInstance().swapItemHandler.sendToServer(packet);
	break;
	case 2:PacketSwapItem packet1 = new PacketSwapItem(synthesis.xCoord, synthesis.yCoord, synthesis.zCoord, 1);
    PacketHandler.getInstance().swapItemHandler.sendToServer(packet1);
		
    break;
	default:
	}
	}
	
}
