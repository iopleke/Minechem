package minechem.gui;

import net.minecraft.client.gui.Gui;
import net.minecraft.util.IIcon;

public abstract class GuiTabStateControl extends GuiTab
{
    public static IIcon unpoweredIcon;

    public GuiTabStateControl(Gui gui)
    {
        super(gui);
    }

}
