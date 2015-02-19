package minechem.registry;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import minechem.helper.ArrayHelper;
import minechem.item.journal.JournalPage;
import net.minecraft.entity.player.EntityPlayer;

public class ResearchRegistry
{
    private Map<UUID, Set<String>> playerResearchMap;
    private Map<String, JournalPage> researchPageMap;
    private static ResearchRegistry instance;

    public static ResearchRegistry getInstance()
    {
        if (instance == null)
        {
            instance = new ResearchRegistry();
        }
        return instance;
    }

    public ResearchRegistry()
    {
        playerResearchMap = new TreeMap<UUID, Set<String>>();
        researchPageMap = new TreeMap<String, JournalPage>();
    }

    /**
     * Adds research with given key to the player if key does not exist nothing happens
     *
     * @param playerUUID the players UUID
     * @param pageName   the page name
     */
    public void addResearch(UUID playerUUID, String pageName)
    {
        if (researchPageMap.get(pageName.toLowerCase()) == null)
        {
            return;
        }
        Set<String> pages = playerResearchMap.get(playerUUID);
        if (pages == null)
        {
            pages = new LinkedHashSet<String>();
        }
        pages.add(pageName);
        playerResearchMap.put(playerUUID, pages);
    }

    /**
     * Adds research with given key to the player if key does not exist nothing happens
     *
     * @param player   the player entity
     * @param pageName the page name
     */
    public void addResearch(EntityPlayer player, String pageName)
    {
        addResearch(player.getUniqueID(), pageName);
    }

    /**
     * Adds a new page to the registry
     *
     * @param pageName the page name
     * @param title    the page title
     * @param content  the page content
     */
    public void addResearchPage(String pageName, String title, String content)
    {
        addResearchPage(pageName, new JournalPage(title, content));
    }

    /**
     * Adds a new page to the registry
     *
     * @param pageName the page name
     * @param page     the JournalPage object
     */
    public void addResearchPage(String pageName, JournalPage page)
    {
        researchPageMap.put(pageName.toLowerCase(), page);
    }

    /**
     * Get the {@link minechem.item.journal.JournalPage} with given name
     *
     * @param pageName the page name
     * @return can be null if page name does not exist
     */
    public JournalPage getResearchPage(String pageName)
    {
        return researchPageMap.get(pageName.toLowerCase());
    }

    /**
     * Get {@link minechem.item.journal.JournalPage}s for given names
     *
     * @param pageNames the page names
     * @return can be an empty array
     */
    public JournalPage[] getResearchPages(String[] pageNames)
    {
        JournalPage[] pages = new JournalPage[pageNames.length];
        for (int i = 0; i < pageNames.length; i++)
        {
            pages[i] = getResearchPage(pageNames[i]);
        }

        pages = ArrayHelper.removeNulls(pages, JournalPage.class);

        if (pages.length % 2 != 0)
        {
            JournalPage[] expandedPages = new JournalPage[pages.length + 1];
            System.arraycopy(pages, 0, expandedPages, 0, pages.length);
            expandedPages[pages.length] = getResearchPage("blank");

            return expandedPages;
        }
        return pages;
    }

    /**
     * Gets the whole player research map used when saving to the json
     *
     * @return
     */
    public Map<UUID, Set<String>> getPlayerResearchMap()
    {
        return playerResearchMap;
    }

    /**
     * Get all researchPages for given {@link java.util.UUID}
     *
     * @param playerUUID
     * @return
     */
    public Set<JournalPage> getResearchPagesFor(UUID playerUUID)
    {
        Set<JournalPage> pages = new LinkedHashSet<JournalPage>();
        Set<String> pageKeys = playerResearchMap.get(playerUUID);
        if (pageKeys == null)
        {
            return pages;
        }
        for (String key : pageKeys)
        {
            pages.add(getResearchPage(key));
        }
        return pages;
    }

    /**
     * Get all researchPages for a given {@link net.minecraft.entity.player.EntityPlayer}
     *
     * @param player
     * @return
     */
    public Set<JournalPage> getResearchPagesFor(EntityPlayer player)
    {
        return getResearchPagesFor(player.getUniqueID());
    }

    /**
     * Get all researchKeys for given {@link java.util.UUID}
     *
     * @param playerUUID
     * @return
     */
    public Set<String> getResearchFor(UUID playerUUID)
    {
        return playerResearchMap.get(playerUUID);
    }

    /**
     * Get all researchKeys for a given {@link net.minecraft.entity.player.EntityPlayer}
     *
     * @param player
     * @return
     */
    public Set<String> getResearchFor(EntityPlayer player)
    {
        return getResearchFor(player.getUniqueID());
    }

    public boolean hasUnlockedResearch(EntityPlayer player, String key)
    {
        return player == null || getResearchFor(player).contains(key);
    }
}
