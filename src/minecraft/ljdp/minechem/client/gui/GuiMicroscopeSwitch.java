package ljdp.minechem.client.gui;

import net.minecraft.util.ResourceLocation;
import ljdp.minechem.common.utils.ConstantValue;

public class GuiMicroscopeSwitch extends GuiToggleSwitch {

    public GuiMicroscopeSwitch(GuiContainerTabbed container) {
        super();
        this.container = container;
        this.width = 13;
        this.height = 13;
        this.numStates = 2;
        this.texture = new ResourceLocation(ConstantValue.MOD_ID,ConstantValue.MICROSCOPE_GUI);
        ToggleButton buttonSynthesis = new ToggleButton();
        buttonSynthesis.u = 176;
        buttonSynthesis.v = 124;
        buttonSynthesis.tooltip = "gui.title.synthesis";

        ToggleButton buttonDecomposer = new ToggleButton();
        buttonDecomposer.u = 189;
        buttonDecomposer.v = 124;
        buttonDecomposer.tooltip = "gui.title.decomposer";
        this.buttons.put(0, buttonSynthesis);
        this.buttons.put(1, buttonDecomposer);
    }
}
