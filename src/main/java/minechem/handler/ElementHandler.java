package minechem.handler;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
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

			try
			{

				JsonReader jReader = new JsonReader(new InputStreamReader(new FileInputStream(elementsDataFile)));

				JsonParser parser = new JsonParser();

				JsonObject elementsObject = parser.parse(jReader).getAsJsonObject();
				int count = 1;

				LogHelper.debug("JSON Object:");
				while (elementsObject.has(Integer.toString(count)))
				{

					JsonObject element = elementsObject.get(Integer.toString(count)).getAsJsonObject();
					LogHelper.debug("Atomic Number: " + count);
					LogHelper.debug("Element name: " + element.get("longName"));
					LogHelper.debug("Element abbreviation: " + element.get("shortName"));
					LogHelper.debug("Element Mohs hardness: " + element.get("mohs"));
					count++;

				}
			} catch (Exception e)
			{
				throw new RuntimeException(e);
			}
		}
	}
}
