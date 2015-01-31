package minechem.helper;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
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

    /*
     * Opens passed in URL, MUST check
     * FMLClientHandler.instance().getClient(),mc.gameSettings.chatLinksPrompt
     * before using.
     */
    public static void openURL(String url)
    {
        try
        {
            Class oclass = Class.forName("java.awt.Desktop");
            Object object = oclass.getMethod("getDesktop", new Class[0]).invoke((Object) null, new Object[0]);
            oclass.getMethod("browse", new Class[]
            {
                URI.class
            }).invoke(object, new Object[]
            {
                new URI(url)
            });
        } catch (ClassNotFoundException e)
        {
            LogHelper.debug("Couldn\'t open link: " + url);
            LogHelper.debug("Caused by: " + e.getLocalizedMessage());
        } catch (NoSuchMethodException e)
        {
            LogHelper.debug("Couldn\'t open link: " + url);
            LogHelper.debug("Caused by: " + e.getLocalizedMessage());
        } catch (SecurityException e)
        {
            LogHelper.debug("Couldn\'t open link: " + url);
            LogHelper.debug("Caused by: " + e.getLocalizedMessage());
        } catch (IllegalAccessException e)
        {
            LogHelper.debug("Couldn\'t open link: " + url);
            LogHelper.debug("Caused by: " + e.getLocalizedMessage());
        } catch (IllegalArgumentException e)
        {
            LogHelper.debug("Couldn\'t open link: " + url);
            LogHelper.debug("Caused by: " + e.getLocalizedMessage());
        } catch (InvocationTargetException e)
        {
            LogHelper.debug("Couldn\'t open link: " + url);
            LogHelper.debug("Caused by: " + e.getLocalizedMessage());
        } catch (URISyntaxException e)
        {
            LogHelper.debug("Couldn\'t open link: " + url);
            LogHelper.debug("Caused by: " + e.getLocalizedMessage());
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
