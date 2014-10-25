package minechem.gui;

import cpw.mods.fml.client.config.GuiConfig;
import minechem.Settings;
import minechem.reference.Reference;
import net.minecraft.client.gui.GuiScreen;

public class ModGuiConfig extends GuiConfig
{
	public ModGuiConfig(GuiScreen guiScreen)
	{
		super(guiScreen,
				Settings.getConfigElements(),
                Reference.ID,
				false,
				false,
				GuiConfig.getAbridgedConfigPath(Settings.config.toString()));
	}
}
