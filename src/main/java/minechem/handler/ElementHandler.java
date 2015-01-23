package minechem.handler;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.logging.log4j.Level;
import minechem.helper.FileHelper;
import minechem.helper.LogHelper;
import minechem.reference.Compendium;

/**
 *
 */
public class ElementHandler
{
    public void init()
    {
        File elementsDataFile = new File(Compendium.Config.configPrefix + Compendium.Config.elementsDataJson);

        if (!elementsDataFile.isFile())
        {
            FileHelper.copyFromJar(ElementHandler.class, Compendium.Config.elementsDataJsonSource, Compendium.Config.elementsDataJson);
        }

        // Get the file again
        elementsDataFile = new File(Compendium.Config.configPrefix + Compendium.Config.elementsDataJson);
        if (elementsDataFile.isFile())
        {
            LogHelper.debug("JSON file exists");

            JsonReader jReader=null;
            try
            {

                jReader = new JsonReader(new InputStreamReader(new FileInputStream(elementsDataFile)));

                JsonParser parser = new JsonParser();

                JsonObject elementsObject = parser.parse(jReader).getAsJsonObject();
                int count = 1;
                while (elementsObject.has(Integer.toString(count)))
                {
//                    JsonObject element = elementsObject.get(Integer.toString(count)).getAsJsonObject();
//                    LogHelper.debug("Atomic Number: " + count);
//                    LogHelper.debug("Element name: " + element.get("longName"));
//                    LogHelper.debug("Element abbreviation: " + element.get("shortName"));
                    count++;

                }
                LogHelper.debug("Total of " + count + " elements loaded");
            } catch (Exception e)
            {
                throw new RuntimeException(e);
            } finally
            {
            	if (jReader!=null)
            	{
            		try
            		{
						jReader.close();
					} catch (IOException e)
            		{
						LogHelper.exception(e, Level.WARN);
					}
            	}
            }
        }
    }
}
