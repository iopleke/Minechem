package minechem.handler;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.io.FileWriter;
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

        JsonObject object = parser.parse(jReader).getAsJsonObject();

        readFromObject(object.entrySet(), 0);

        //saveJson(object);

        LogHelper.info("Total of " + MoleculeRegistry.getInstance().getMolecules().size() + " molecules registered");
    }

    public static void saveJson(JsonObject object)
    {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        json = json.replaceAll("\\{","{\n\t\t").replaceAll(",", ",\n\t\t").replaceAll("}","\n\t}");
        try
        {
            FileWriter writer = new FileWriter("C:\\Users\\Charlie\\Documents\\Modding\\Minechem\\Minechem\\src\\main\\resources\\assets\\minechem\\data\\output.json");
            writer.write(json);
            writer.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
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
                String form = "liquid";
                if (elementObject.has("MeltingPt") && elementObject.get("MeltingPt").getAsDouble() > 25) form = "solid";
                else if (elementObject.has("BoilingPt") && elementObject.get("BoilingPt").getAsDouble() < 25) form = "gas";
                if (MoleculeRegistry.getInstance().registerMolecule(
                        moleculeEntry.getKey(),
                        form,
                        elementObject.get("Formula").getAsString()))
                {
                    unparsed.put(moleculeEntry.getKey(), moleculeEntry.getValue());
                }
                else{
//                    if (elementObject.has("SMILES") && !elementObject.has("Height"))
//                    {
//                        int[] result = MoleculeImageParser.parser(moleculeEntry.getKey(), elementObject.get("SMILES").getAsString());
//                        if (result!=null)
//                        {
//                            elementObject.add("Height",new JsonPrimitive(result[0]));
//                            elementObject.add("Width",new JsonPrimitive(result[1]));
//                        }
//                    }
                }
            }
            readFromObject(unparsed.entrySet(), run+1);
        }
    }
}
