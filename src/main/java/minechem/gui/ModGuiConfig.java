package minechem.gui;

import cpw.mods.fml.client.config.GuiConfig;
import minechem.Minechem;
import minechem.Settings;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

public class ModGuiConfig extends GuiConfig
{
    public ModGuiConfig(GuiScreen guiScreen)
    {
        super(guiScreen,
                new ConfigElement(Settings.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
                Minechem.ID,
                false,
                false,
                GuiConfig.getAbridgedConfigPath(Settings.config.toString()));
    }
}
