package minechem.helper;

import cpw.mods.fml.client.FMLClientHandler;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNoCallback;
import org.apache.logging.log4j.Level;

/**
 * Clickable link helper class, respecting user settings
 */
public class LinkHelper
{

    /**
     * Private implementation of GuiYesNoCallback for prompting on links
     */
    private static class OpenURLCallback implements GuiYesNoCallback
    {
        private GuiScreen gui;
        private String url;

        /**
         * URL callback instantiation
         *
         * @param url link or URL to open
         * @param gui instance of the GUI
         */
        public OpenURLCallback(String url, GuiScreen gui)
        {
            this.url = url;
            this.gui = gui;
        }

        /**
         * Check if the user confirmed that they'd like to view the link
         *
         * @param confirm boolean based on user confirmation
         * @param id
         */
        @Override
        public void confirmClicked(boolean confirm, int id)
        {
            if (id == 0)
            {
                if (confirm)
                {
                    LinkHelper.openLink(url);
                }

                FMLClientHandler.instance().getClient().displayGuiScreen(gui);
            }
        }
    }

    /**
     * Open a link in user's default browser
     *
     * @param link link or URL to open
     */
    private static void openLink(String link)
    {
        try
        {
            Desktop.getDesktop().browse(new URI(link));
        } catch (IOException e)
        {
            LogHelper.exception("Cannot open URL: %s", e, Level.WARN, link);
        } catch (URISyntaxException e)
        {
            LogHelper.exception("Cannot open URL: %s", e, Level.WARN, link);
        }
    }

    /**
     * Check to see if the user settings require prompting on links
     *
     * @return boolean based on config setting
     */
    public static boolean promptOnChatLinks()
    {
        return FMLClientHandler.instance().getClient().gameSettings.chatLinksPrompt;
    }

    /**
     * Check if the user allows links to display at all
     *
     * @return boolean based on config setting
     */
    public static boolean allowLinks()
    {
        return FMLClientHandler.instance().getClient().gameSettings.chatLinks;
    }

    /**
     * Open a link or URL in the user's browser
     *
     * @param link link or URL to open
     * @param gui  instance of the GUI
     */
    public static void openLink(String link, GuiScreen gui)
    {
        if (allowLinks())
        {
            if (promptOnChatLinks())
            {
                OpenURLCallback callback = new OpenURLCallback(link, gui);
                GuiConfirmOpenLink confirmOpenLink = new GuiConfirmOpenLink(callback, link, 0, false);
                FMLClientHandler.instance().getClient().displayGuiScreen(confirmOpenLink);
            } else
            {
                LinkHelper.openLink(link);
            }
        }
    }
}
