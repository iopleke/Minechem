package minechem.helper;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.logging.log4j.Level;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNoCallback;
import cpw.mods.fml.client.FMLClientHandler;

public class HTTPHelper
{

    private static class OpenURLCallback implements GuiYesNoCallback
    {
        private String url;
        private GuiScreen gui;

        public OpenURLCallback(String url, GuiScreen gui)
        {
            this.url = url;
            this.gui = gui;
        }

        @Override
        public void confirmClicked(boolean confirm, int id)
        {
            if (id == 0)
            {
                if (confirm)
                {
                    HTTPHelper.openURL(url);
                }

                FMLClientHandler.instance().getClient().displayGuiScreen(gui);
            }
        }

    }

    /**
     * Open a link in browse.
     * 
     * @param url the URL
     * @deprecated please use {@link HTTPHelper#tipToOpenURL(String, GuiScreen)}
     */
    @Deprecated
    public static void openURL(String url)
    {
        try
        {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException e)
        {
            LogHelper.exception("Cannot open URL:"+url, e, Level.WARN);
        } catch (URISyntaxException e)
        {
            LogHelper.exception("Cannot open URL:"+url, e, Level.WARN);
        }
    }

    public static boolean isChatLinksPrompt()
    {
        return FMLClientHandler.instance().getClient().gameSettings.chatLinksPrompt;
    }

    public static boolean canOpenURL()
    {
        return FMLClientHandler.instance().getClient().gameSettings.chatLinks;
    }

    public static void tipToOpenURL(String url, GuiScreen gui)
    {
        if (!canOpenURL())
        {
            return;
        }

        if (isChatLinksPrompt())
        {
            OpenURLCallback callback = new OpenURLCallback(url, gui);
            GuiConfirmOpenLink confirmOpenLink = new GuiConfirmOpenLink(callback, url, 0, false);
            FMLClientHandler.instance().getClient().displayGuiScreen(confirmOpenLink);
        } else
        {
            HTTPHelper.openURL(url);
        }
    }
}
