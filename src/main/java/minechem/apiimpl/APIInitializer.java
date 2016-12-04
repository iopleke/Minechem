package minechem.apiimpl;

import java.lang.reflect.Field;
import org.apache.logging.log4j.Level;
import minechem.api.MinechemAPI;
import minechem.helper.LogHelper;

public final class APIInitializer
{

    /**
     * Initialize the API
     */
    public static final void init()
    {
        try
        {
            Field instanceField = MinechemAPI.Instance.class.getDeclaredField("INSTANCE");
            instanceField.setAccessible(true);
            instanceField.set(null, new MinechemAPIImpl());
        } catch (Exception e)
        {
            LogHelper.exception("Failed to initialize API", e, Level.ERROR);
        }
    }

    private APIInitializer()
    {
    }

}
