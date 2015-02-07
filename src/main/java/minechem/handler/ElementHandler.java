package minechem.handler;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Set;
import minechem.Config;
import minechem.helper.FileHelper;
import minechem.helper.LogHelper;
import minechem.reference.Compendium;
import minechem.registry.ElementRegistry;
import org.apache.logging.log4j.Level;

/**
 *
 */
public class ElementHandler
{
    public void init()
    {
        if (Config.useDefaultElements)
        {
            InputStream stream = null;
            try
            {
                stream = FileHelper.getInputStreamFromJar(ElementHandler.class, Compendium.Config.elementsDataJsonSource);
                readFromStream(stream);
            }
            finally
            {
                if (stream != null)
                {
                    try
                    {
                        stream.close();
                    } catch (IOException e)
                    {
                        LogHelper.exception("Cannot close stream!", e, Level.WARN);
                    }
                }
            }
        } else
        {
            File elementsDataFile = new File(Compendium.Config.configPrefix + Compendium.Config.elementsDataJson);

            if (!elementsDataFile.isFile())
            {
                FileHelper.copyFromJar(ElementHandler.class, Compendium.Config.elementsDataJsonSource, Compendium.Config.elementsDataJson);

                // If the file was copied, get the file again
                elementsDataFile = new File(Compendium.Config.configPrefix + Compendium.Config.elementsDataJson);
            }

            if (elementsDataFile.isFile())
            {
                LogHelper.debug("JSON file exists");
                InputStream stream = null;
                try
                {
                    stream = new FileInputStream(elementsDataFile);
                    readFromStream(stream);
                } catch (FileNotFoundException e)
                {
                    throw new RuntimeException(e);
                }
                finally
                {
                    if (stream != null)
                    {
                        try
                        {
                            stream.close();
                        } catch (IOException e)
                        {
                            LogHelper.exception("Cannot close stream!", e, Level.WARN);
                        }
                    }
                }
            }
        }
    }

    public void readFromStream(InputStream stream)
    {
        JsonReader jReader = new JsonReader(new InputStreamReader(stream));
        JsonParser parser = new JsonParser();

        Set<Map.Entry<String, JsonElement>> elementsSet = parser.parse(jReader).getAsJsonObject().entrySet();
        int count = 0;
        for (Map.Entry<String, JsonElement> elementEntry : elementsSet)
        {
            if (!elementEntry.getValue().isJsonObject())
            {
                continue;
            }
            JsonObject elementObject = elementEntry.getValue().getAsJsonObject();
            ElementRegistry.getInstance().registerElement(
                Integer.parseInt(elementEntry.getKey()),
                elementObject.get("longName").getAsString(),
                elementObject.get("shortName").getAsString(),
                elementObject.get("form").getAsString(),
                Integer.parseInt(elementObject.get("neutrons").getAsString())
            );
            count++;
        }
        LogHelper.info("Total of " + count + " elements registered");
    }
}
