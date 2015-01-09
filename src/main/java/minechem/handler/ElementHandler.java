package minechem.handler;

import java.io.File;
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
		File elementsDataFile = new File(Compendium.Config.elementsDataJson);

		if (!elementsDataFile.isFile())
		{
			FileHelper.copyFromJar(ElementHandler.class, Compendium.Config.elementsDataJsonSource, Compendium.Config.elementsDataJson);
		}

		// Get the file again
		elementsDataFile = new File(Compendium.Config.elementsDataJson);
		if (elementsDataFile.isFile())
		{
			LogHelper.debug("JSON file exists");
		}

	}

}
