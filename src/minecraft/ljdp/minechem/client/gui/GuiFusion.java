package ljdp.minechem.client.gui;

import org.lwjgl.opengl.GL11;

import ljdp.minechem.client.gui.tabs.TabHelp;
import ljdp.minechem.common.containers.ContainerFusion;
import ljdp.minechem.common.tileentity.TileEntityFusion;
import ljdp.minechem.common.utils.ConstantValue;
import ljdp.minechem.common.utils.MinechemHelper;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiFusion extends GuiContainerTabbed {

    static float increaseRate = .2F;
    static float decreaseRate = .4F;

    TileEntityFusion fusion;
    int guiWidth = 176;
    int guiHeight = 187;
    float energy = 0.0F;
    int targetEnergy = 0;

    public GuiFusion(InventoryPlayer inventoryPlayer, TileEntityFusion fusion) {
        super(new ContainerFusion(inventoryPlayer, fusion));
        this.fusion = fusion;
        energy = fusion.getEnergyStored();
        this.xSize = guiWidth;
        this.ySize = guiHeight;
        addTab(new TabHelp(this, MinechemHelper.getLocalString("help.fusion")));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        super.drawGuiContainerForegroundLayer(par1, par2);
        String info = MinechemHelper.getLocalString("block.name.fusion");
        int infoWidth = fontRenderer.getStringWidth(info);
        fontRenderer.drawString(info, (guiWidth - infoWidth) / 2, 5, 0xFFFFFF);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(ConstantValue.FUSION_GUI);
        int x = (width - guiWidth) / 2;
        int y = (height - guiHeight) / 2;
        // DRAW GUI
        drawTexturedModalRect(x, y, 0, 0, guiWidth, guiHeight);

        // DRAW ENERGY BAR
        updateEnergy();
        // energy = fusion.getEnergyStored();
        int energyBarWidth = (int) MinechemHelper.translateValue(energy, 0, fusion.getMaxEnergy(), 0, 160);
        drawTexturedModalRect(x + 8, y + 38, 0, 192, energyBarWidth, 3);

        // DRAW ENERGY BAR OVERLAY
        drawEnergyBarOverlay();
    }
    public void drawEnergyBarOverlay(){
    	
    	int x = (width - guiWidth) / 2;
        int y = (height - guiHeight) / 2;
        drawTexturedModalRect(x + 7, y + 37, 0, 187, 162, 5);
        
    }
    private void updateEnergy() {
        targetEnergy = fusion.getEnergyStored();
        if (energy < (targetEnergy)) {
            energy += increaseRate;
            if (energy > targetEnergy)
                energy = targetEnergy;
        } else if (energy > (targetEnergy)) {
            energy -= decreaseRate;
            if (energy < targetEnergy)
                energy = targetEnergy;
        }
    }

}
