package minechem.tileentity.microscope;

import minechem.client.gui.GuiContainerTabbed;
import minechem.client.gui.GuiToggleSwitch;
import minechem.client.gui.GuiToggleSwitch.ToggleButton;
import minechem.common.ModMinechem;
import minechem.utils.Reference;
import net.minecraft.util.ResourceLocation;

public class GuiMicroscopeSwitch extends GuiToggleSwitch
{

    public GuiMicroscopeSwitch(GuiContainerTabbed container)
    {
        super();
        this.container = container;
        this.width = 13;
        this.height = 13;
        this.numStates = 2;
        this.texture = new ResourceLocation(ModMinechem.ID, Reference.MICROSCOPE_GUI);
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
