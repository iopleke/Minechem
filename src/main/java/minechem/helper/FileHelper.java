package minechem.helper;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import minechem.Config;
import minechem.reference.Compendium;
import org.apache.commons.io.FileUtils;

/**
 * Various functions to help with files
 */
public class FileHelper
{
	/**
	 * Thanks to @tterrag1098 for letting me have this bit of code
	 *
	 * @param classFromJar    A class from the jar in question
	 * @param fileSource      File to copy, prepended with "/assets/minechem/"
	 * @param fileDestination File to copy to
	 */
	public static void copyFromJar(Class<?> classFromJar, String fileSource, String fileDestination)
	{
		LogHelper.debug("Copying file " + fileSource + " from jar");

		URL source = classFromJar.getResource(Compendium.Config.assetPrefix + fileSource);
		File destination = new File(Compendium.Config.configPrefix + fileDestination);

		try
		{
			FileUtils.copyURLToFile(source, destination);
		} catch (IOException e)
		{
			LogHelper.info("Couldn't load file from jar!");
			LogHelper.info("This is a bug, please report it to the mod author!");

			if (Config.debugMode)
			{
				throw new RuntimeException(e);
			}
		}
	}

}
