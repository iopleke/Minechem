package minechem.handler;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import minechem.Compendium;
import minechem.Config;
import minechem.helper.FileHelper;
import minechem.helper.LogHelper;
import minechem.helper.WikipediaHelper;
import minechem.registry.MoleculeRegistry;
import org.apache.logging.log4j.Level;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Set;

public class CompoundHandler
{
    public static void init()
    {
        String[] fileDestSource = new String[2];
        fileDestSource[0] = Compendium.Config.dataJsonPrefix + "wikioutput.json";
        fileDestSource[1] = Compendium.Config.configPrefix + Compendium.Config.dataJsonPrefix + "output.json";
        InputStream inputStream = FileHelper.getJsonFile(CompoundHandler.class, fileDestSource, Config.useDefaultMolecules);
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
        Gson gson = new Gson();
        String json = gson.toJson(readFromObject(parser.parse(jReader).getAsJsonObject().entrySet()));
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

    private static JsonObject readFromObject(Set<Map.Entry<String, JsonElement>> elementSet)
    {
        JsonObject result = new JsonObject();
        int test=0, added = 0;
        int elements = elementSet.size();
        for (Map.Entry<String, JsonElement> moleculeEntry : elementSet)
        {

            if (!moleculeEntry.getValue().isJsonObject())
            {
                continue;
            }
            JsonObject elementObject = WikipediaHelper.getObject(moleculeEntry.getKey(),moleculeEntry.getValue().getAsJsonObject());
            if (elementObject != null)
            {
                String name = elementObject.get("name").getAsString();
                elementObject.remove("name");
                result.add(name, elementObject);
                added++;
            }
            System.out.println("parsed: "+ ++test +"/"+elements + ", "+added+" added");
        }
        return result;
    }
}
