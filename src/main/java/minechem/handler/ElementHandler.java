package minechem.handler;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.logging.log4j.Level;

import minechem.Config;
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
    	if (Config.useDefaultElements){
    		InputStream stream=null;
    		try{
    			stream=FileHelper.getInputStreamFromJar(ElementHandler.class, Compendium.Config.elementsDataJsonSource);
    			readFromStream(stream);
    		} finally {
    			if (stream!=null){
    				try {
						stream.close();
					} catch (IOException e) {
						LogHelper.exception("Cannot close stream!", e, Level.WARN);
					}
    			}
    		}
		} else {
			File elementsDataFile = new File(Compendium.Config.configPrefix + Compendium.Config.elementsDataJson);

			if (!elementsDataFile.isFile()) {
				FileHelper.copyFromJar(ElementHandler.class, Compendium.Config.elementsDataJsonSource, Compendium.Config.elementsDataJson);
			}

			// Get the file again
			elementsDataFile = new File(Compendium.Config.configPrefix + Compendium.Config.elementsDataJson);
			if (elementsDataFile.isFile()) {
				LogHelper.debug("JSON file exists");
				InputStream stream=null;
				try {
					stream=new FileInputStream(elementsDataFile);
					readFromStream(stream);
				} catch (FileNotFoundException e) {
					throw new RuntimeException(e);
				} finally {
	    			if (stream!=null){
	    				try {
							stream.close();
						} catch (IOException e) {
							LogHelper.exception("Cannot close stream!", e, Level.WARN);
						}
	    			}
	    		}
			}
		}
	}

	public void readFromStream(InputStream stream) {
		JsonReader jReader = null;
		jReader = new JsonReader(new InputStreamReader(stream));
		JsonParser parser = new JsonParser();

		JsonObject elementsObject = parser.parse(jReader).getAsJsonObject();
		int count = 1;
		while (elementsObject.has(Integer.toString(count))) {
			// JsonObject element =
			// elementsObject.get(Integer.toString(count)).getAsJsonObject();
			// LogHelper.debug("Atomic Number: " + count);
			// LogHelper.debug("Element name: " + element.get("longName"));
			// LogHelper.debug("Element abbreviation: " +
			// element.get("shortName"));
			count++;
		}
		LogHelper.debug("Total of " + count + " elements loaded");
	}
}
