package minechem.gui;

import cpw.mods.fml.client.config.GuiConfig;
import minechem.Minechem;
import minechem.Settings;
import net.minecraft.client.gui.GuiScreen;

public class ModGuiConfig extends GuiConfig
{
	public ModGuiConfig(GuiScreen guiScreen)
	{
		super(guiScreen,
				Settings.getConfigElements(),
				Minechem.ID,
				false,
				false,
				GuiConfig.getAbridgedConfigPath(Settings.config.toString()));
	}
}
