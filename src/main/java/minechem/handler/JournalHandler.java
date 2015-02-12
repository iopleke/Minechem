package minechem.handler;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import minechem.Compendium;
import minechem.Config;
import minechem.Minechem;
import minechem.helper.FileHelper;
import minechem.helper.LogHelper;
import minechem.registry.ResearchRegistry;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Level;

import java.io.*;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class JournalHandler
{
    /**
     * Save the current research map
     */
    public static void saveResearch()
    {
        try
        {
            OutputStream outputStream = FileUtils.openOutputStream(new File(Minechem.proxy.getCurrentSaveDir() + Compendium.Config.playerResearchData));
            JsonWriter jWriter = new JsonWriter(new OutputStreamWriter(outputStream));
            jWriter.setIndent("    ");
            jWriter.beginObject();
            for (Map.Entry<UUID, Set<String>> entry : ResearchRegistry.getInstance().getPlayerResearchMap().entrySet())
            {
                jWriter.name(entry.getKey().toString()).beginObject();
                //jWriter.name("displayName").value(); TODO: maybe find a way to add this for readability
                jWriter.name("research").beginArray();
                for (String research : entry.getValue())
                    jWriter.value(research);
                jWriter.endArray();
                jWriter.endObject();
            }
            jWriter.endObject();
            jWriter.close();
            try
            {
                outputStream.close();
            } catch (IOException e)
            {
                LogHelper.exception("Cannot close stream!", e, Level.WARN);
            }
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Read pages for given lang
     * @param lang the lang code eg. "en_US" defaults to en_US if given lang does not exist
     */
    public static void init(String lang)
    {
        if(!FileHelper.doesFileExistInJar(JournalHandler.class, Compendium.Config.dataJsonPrefix + Compendium.Config.researchPagesJsonPrefix + lang + ".json")) lang = "en_US";
        InputStream inputStream = FileHelper.getJsonFile(JournalHandler.class, Compendium.Config.researchPagesJsonPrefix + lang + ".json", Config.useDefaultResearchPages);
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

    /**
     * Read existing research
     */
    public static void readPlayerResearch()
    {
        if(FileHelper.doesFileExistInCurrentSaveDir(Compendium.Config.playerResearchData))
        {
            InputStream inputStream = FileHelper.getFileFromCurrentSaveDir(Compendium.Config.playerResearchData);
            readPlayerResearch(inputStream);
        }
    }

    private static void readPlayerResearch(InputStream inputStream)
    {
        JsonReader jReader = new JsonReader(new InputStreamReader(inputStream));
        JsonParser parser = new JsonParser();

        Set<Map.Entry<String, JsonElement>> playerSet = parser.parse(jReader).getAsJsonObject().entrySet();
        int count = 0;
        for (Map.Entry<String, JsonElement> playerEntry : playerSet)
        {
            if (!playerEntry.getValue().isJsonObject())
            {
                continue;
            }
            JsonObject playerObject = playerEntry.getValue().getAsJsonObject();
            for (JsonElement research : playerObject.getAsJsonArray("research"))
            {
                ResearchRegistry.getInstance().addResearch(
                        UUID.fromString(playerEntry.getKey()),
                        research.getAsString()
                );
            }
            count++;
        }
        LogHelper.info("Total of " + count + " researchers registered");
    }

    private static void readFromStream(InputStream inputStream)
    {
        JsonReader jReader = new JsonReader(new InputStreamReader(inputStream));
        JsonParser parser = new JsonParser();

        Set<Map.Entry<String, JsonElement>> pagesSet = parser.parse(jReader).getAsJsonObject().entrySet();
        int count = 0;
        for (Map.Entry<String, JsonElement> pageEntry : pagesSet)
        {
            if (!pageEntry.getValue().isJsonObject())
            {
                continue;
            }
            JsonObject pageObject = pageEntry.getValue().getAsJsonObject();
            ResearchRegistry.getInstance().addResearchPage(
                    pageEntry.getKey(),
                    pageObject.get("title").getAsString(),
                    pageObject.get("content").getAsString()
            );
            count++;
        }
        LogHelper.info("Total of " + count + " pages registered");
    }
}
