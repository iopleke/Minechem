package minechem.helper;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;

public class HTTPHelper
{
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
}
