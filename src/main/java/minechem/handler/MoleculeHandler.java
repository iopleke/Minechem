package minechem.handler;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import minechem.Compendium;
import minechem.Config;
import minechem.helper.FileHelper;
import minechem.helper.LogHelper;
import minechem.registry.MoleculeRegistry;
import org.apache.logging.log4j.Level;

public class MoleculeHandler
{
    public static void init()
    {
        String[] fileDestSource = new String[2];
        fileDestSource[0] = Compendium.Config.dataJsonPrefix + Compendium.Config.moleculesDataJson;
        fileDestSource[1] = Compendium.Config.configPrefix + Compendium.Config.dataJsonPrefix + Compendium.Config.moleculesDataJson;
        InputStream inputStream = FileHelper.getJsonFile(MoleculeHandler.class, fileDestSource, Config.useDefaultMolecules);
        readFromStream(inputStream);
        if (inputStream != null)
        {
            try
            {
                inputStream.close();
            } catch (IOException e)
            {
                LogHelper.exception("Cannot close stream!", e, Level.WARN);
            }
        }
    }

    private static void readFromStream(InputStream stream)
    {
        JsonReader jReader = new JsonReader(new InputStreamReader(stream));
        JsonParser parser = new JsonParser();

        readFromObject(parser.parse(jReader).getAsJsonObject().entrySet(), 0);
        LogHelper.info("Total of " + MoleculeRegistry.getInstance().getMolecules().size() + " molecules registered");
    }

    private static void readFromObject(Set<Map.Entry<String, JsonElement>> elementSet, int run)
    {
        if (elementSet.isEmpty()) return;
        else if (run>4)
        {
            for (Map.Entry<String, JsonElement> moleculeEntry : elementSet)
            {
                LogHelper.warn("Molecule Parsing Error: "+moleculeEntry.getKey()+" cannot be parsed.");
            }
        }
        else
        {
            Map<String, JsonElement> unparsed = new HashMap<String, JsonElement>();
            for (Map.Entry<String, JsonElement> moleculeEntry : elementSet)
            {
                if (!moleculeEntry.getValue().isJsonObject())
                {
                    continue;
                }
                JsonObject elementObject = moleculeEntry.getValue().getAsJsonObject();
                if (MoleculeRegistry.getInstance().registerMolecule(
                        moleculeEntry.getKey(),
                        elementObject.get("form").getAsString(),
                        elementObject.get("formula").getAsString()))
                {
                    unparsed.put(moleculeEntry.getKey(), moleculeEntry.getValue());
                }
            }
            readFromObject(unparsed.entrySet(), run+1);
        }
    }
}
