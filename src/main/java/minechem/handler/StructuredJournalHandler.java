package minechem.handler;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import minechem.Compendium;
import minechem.Config;
import minechem.helper.FileHelper;
import minechem.helper.LogHelper;
import minechem.item.journal.pages.IJournalPage;
import minechem.item.journal.pages.SectionPage;
import minechem.item.journal.pages.TextPage;
import org.apache.logging.log4j.Level;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StructuredJournalHandler
{
    public static void init()
    {
        String[] fileDestSource = new String[2];
        fileDestSource[0] = Compendium.Config.dataJsonPrefix + Compendium.Config.researchPagesJsonPrefix + "pages.json";
        fileDestSource[1] = Compendium.Config.configPrefix + Compendium.Config.dataJsonPrefix + Compendium.Config.researchPagesJsonPrefix + "pages.json";

        InputStream inputStream = FileHelper.getJsonFile(JournalHandler.class, fileDestSource, Config.useDefaultResearchPages);
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

    private static void readFromStream(InputStream inputStream)
    {
        JsonReader jReader = new JsonReader(new InputStreamReader(inputStream));
        JsonParser parser = new JsonParser();


        SectionPage journal = (SectionPage) getPageFromJSONObject("", "", parser.parse(jReader).getAsJsonObject());
        LogHelper.info("Total of " + journal.getSubPages() + " pages registered");
        return;
    }

    public static IJournalPage getPageFromJSONObject(String name, String chapter, JsonObject object)
    {
        IJournalPage result = new SectionPage(name);
        if (name.isEmpty())
        {
            for (IJournalPage page : getPagesFromJSONObject("", object)) result.addSubPage(page);
        }
        else if (object.has("section"))
        {
            for (IJournalPage page : getPagesFromJSONObject((chapter.isEmpty()? "": chapter + ".") + name, object.getAsJsonObject("section"))) result.addSubPage(page);
        }
        else
        {
            result = new TextPage(name);
            result.setChapter(chapter);
        }
        return result;
    }

    public static List<IJournalPage> getPagesFromJSONObject(String chapter, JsonObject object)
    {
        Set<Map.Entry<String, JsonElement>> pagesSet = object.entrySet();
        List<IJournalPage> pages = new ArrayList<IJournalPage>();
        for (Map.Entry<String, JsonElement> pageEntry : pagesSet)
        {
            if (!pageEntry.getValue().isJsonObject())
            {
                continue;
            }
            pages.add(getPageFromJSONObject(pageEntry.getKey(), chapter, pageEntry.getValue().getAsJsonObject()));
        }
        return pages;
    }
}
