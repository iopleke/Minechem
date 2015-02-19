package minechem.handler;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import cpw.mods.fml.common.registry.GameRegistry;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import minechem.Compendium;
import minechem.Config;
import minechem.helper.FileHelper;
import minechem.helper.Jenkins;
import minechem.helper.LogHelper;
import minechem.item.journal.pages.EntryPage;
import minechem.item.journal.pages.IJournalPage;
import minechem.item.journal.pages.SectionPage;
import minechem.item.journal.pages.elements.IJournalElement;
import minechem.item.journal.pages.elements.JournalImage;
import minechem.item.journal.pages.elements.JournalText;
import minechem.registry.JournalRegistry;
import net.afterlifelochie.fontbox.document.Element;
import net.afterlifelochie.fontbox.document.property.AlignmentMode;
import net.afterlifelochie.fontbox.document.property.FloatMode;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.logging.log4j.Level;

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

        SectionPage journal = getJournal(parser.parse(jReader).getAsJsonObject());
        LogHelper.info("Total of " + journal.getSubPages() + " pages registered");
        JournalRegistry.journal = journal;
    }

    public static SectionPage getJournal(JsonObject object)
    {
        SectionPage result  = new SectionPage("");
        for (IJournalPage page : getPagesFromJsonObject("", object))
        {
            result.addSubPage(page);
        }
        return result;
    }

    public static IJournalPage getPageFromJSONObject(String name, String chapter, JsonObject object)
    {
        IJournalPage result;
        if (object.has("section"))
        {
            result  = new SectionPage(name, chapter,  new ArrayList<IJournalPage>());
            for (IJournalPage page : getPagesFromJsonObject((chapter.isEmpty() ? "" : chapter + ".") + name, object.getAsJsonObject("section")))
            {
                result.addSubPage(page);
            }
        } else
        {
            List<IJournalElement> elements = getElementsFromJsonObject((chapter.isEmpty() ? "" : chapter + ".") + name, object);
            result = new EntryPage(name, chapter, elements);
        }
        return result;
    }

    public static List<IJournalPage> getPagesFromJsonObject(String chapter, JsonObject object)
    {
        List<IJournalPage> pages = new ArrayList<IJournalPage>();
        for (Map.Entry<String, JsonElement> pageEntry : object.entrySet())
        {
            if (pageEntry.getValue().isJsonNull())
            {
                pages.add(new EntryPage(pageEntry.getKey(),chapter,new JournalText(chapter + "." + pageEntry.getKey())));
            }
            else if (!pageEntry.getValue().isJsonObject())
            {
                continue;
            }
            else
            {
                pages.add(getPageFromJSONObject(pageEntry.getKey(), chapter, pageEntry.getValue().getAsJsonObject()));
            }
        }
        return pages;
    }

    public static List<IJournalElement> getElementsFromJsonObject(String page, JsonObject object)
    {
        List<IJournalElement> pages = new ArrayList<IJournalElement>();
        Set<Map.Entry<String, JsonElement>> entrySet = object.entrySet();
        if (entrySet.size() == 0)
        {
            pages.add(new JournalText(page));
        } else
        {
            for (Map.Entry<String, JsonElement> elementEntry : entrySet)
            {
                IJournalElement element = getElementFromJsonElement(page, elementEntry.getKey(), elementEntry.getValue());
                if (element != null)
                {
                    pages.add(element);
                } else
                {
                    LogHelper.info(page + " object " + elementEntry.getKey() + " failed to parse");
                }
            }
        }
        return pages;
    }

    public static IJournalElement getElementFromJsonElement(String page, String key, JsonElement element)
    {
        if (element.isJsonNull())
        {
            return new JournalText(page, page + "." + key);
        }
        if (element.isJsonObject())
        {
            return getElementFromJsonObject(page, key, element.getAsJsonObject());
        }
        try
        {
            String unlockString = element.getAsString();
            return new JournalText(unlockString, page + "." + key);
        } catch (Exception e)
        {
        }
        return null;
    }

    private static final String prefix = "textures/journal/";
    private static final String suffix = ".png";

    public static IJournalElement getElementFromJsonObject(String page, String key, JsonObject object)
    {
        String unlock = page;
        if (object.has("unlock"))
        {
            unlock = object.get("unlock").getAsString();
        }
        if (object.entrySet().size() == 0)
        {
            return new JournalText(page, page + "." + key);
        }
        if (object.entrySet().size() == 1 && object.has("unlock"))
        {
            return new JournalText(unlock, page + "." + key);
        }
        if (object.has("width"))
        {
            int width = object.get("width").getAsInt();
            int height = object.get("height").getAsInt();
            AlignmentMode align = object.has("align") ? AlignmentMode.valueOf(object.get("align").getAsString()) : AlignmentMode.JUSTIFY;
            FloatMode floatMode = object.has("float") ? FloatMode.valueOf(object.get("float").getAsString()) : FloatMode.NONE;
            if (object.has("image") || key.endsWith(".png"))
            {
                String imageDir = object.has("image") ? object.get("image").getAsString() : key;
                if (!imageDir.startsWith(prefix))
                {
                    imageDir = prefix + imageDir;
                }
                if (!imageDir.endsWith(suffix))
                {
                    imageDir += suffix;
                }
                return new JournalImage(unlock, imageDir, width, height, align, floatMode);
            } else
            {
                ItemStack stack = null;
                String id;
                String[] split;
                int damage = object.has("damage") ? object.get("damage").getAsInt() : 0;
                NBTTagCompound tagCompound;
                try
                {
                    tagCompound = object.has("nbt") ? (NBTTagCompound) JsonToNBT.func_150315_a(object.get("nbt").getAsString()) : null;
                } catch (Exception e)
                {
                    tagCompound = null;
                }
                if (object.has("item"))
                {
                    id = object.get("item").getAsString();
                    if (!id.contains(":"))
                    {
                        return null;
                    }
                    split = id.split(":");
                    if (split.length != 2)
                    {
                        return null;
                    }
                    Item item = GameRegistry.findItem(split[0], split[1]);
                    if (item != null)
                    {
                        stack = new ItemStack(item, 1, damage);
                    } else
                    {
                        stack = GameRegistry.findItemStack(split[0], split[1], 1);
                    }
                } else if (object.has("block"))
                {
                    id = object.get("block").getAsString();
                    if (!id.contains(":"))
                    {
                        return null;
                    }
                    split = id.split(":");
                    if (split.length != 2)
                    {
                        return null;
                    }
                    Block block = GameRegistry.findBlock(split[0], split[1]);
                    if (block != null)
                    {
                        stack = new ItemStack(block, 1, damage % 16);
                    }
                } else if (object.has("chemical"))
                {
                    stack = Jenkins.getStack(object.get("chemical").getAsString());
                }
                if (stack != null)
                {
                    if (tagCompound != null)
                    {
                        stack.setTagCompound(tagCompound);
                    }
                    return new JournalImage(unlock, stack, width, height, align, floatMode);
                }
            }
        }
        return null;
    }
}
