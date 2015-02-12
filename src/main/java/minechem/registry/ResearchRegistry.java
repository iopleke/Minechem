package minechem.registry;

import minechem.item.journal.JournalPage;
import net.minecraft.entity.player.EntityPlayer;

import java.util.*;

public class ResearchRegistry
{
    private Map<UUID, Set<String>> playerResearchMap;
    private Map<String, JournalPage> researchPageMap;
    private static ResearchRegistry instance;
    
    public static ResearchRegistry getInstance()
    {
        if (instance == null) instance = new ResearchRegistry();
        return instance;
    }
    
    public ResearchRegistry()
    {
        playerResearchMap = new TreeMap<UUID, Set<String>>();
        researchPageMap = new TreeMap<String, JournalPage>();
    }

    /**
     * Adds research with given key to the player
     * if key does not exist nothing happens 
     * @param playerUUID the players UUID
     * @param pageName the page name
     */
    public void addResearch(UUID playerUUID, String pageName)
    {
        if (researchPageMap.get(pageName.toLowerCase()) == null) return;
        Set<String> pages = playerResearchMap.get(playerUUID);
        if (pages == null) pages = new LinkedHashSet<String>();
        pages.add(pageName);
        playerResearchMap.put(playerUUID, pages);
    }

    /**
     * Adds research with given key to the player
     * if key does not exist nothing happens
     * @param player the player entity
     * @param pageName the page name
     */
    public void addResearch(EntityPlayer player, String pageName)
    {
        addResearch(player.getUniqueID(), pageName);
    }

    /**
     * Adds a new page to the registry
     * @param pageName the page name
     * @param title the page title
     * @param content the page content
     */
    public void addResearchPage(String pageName, String title, String content)
    {
        addResearchPage(pageName, new JournalPage(title, content));
    }

    /**
     *  Adds a new page to the registry
     * @param pageName the page name
     * @param page the JournalPage object
     */
    public void addResearchPage(String pageName, JournalPage page)
    {
        researchPageMap.put(pageName.toLowerCase(), page);
    }

    /**
     * Gets the whole player research map
     * used when saving to the json
     * @return
     */
    public Map<UUID, Set<String>> getPlayerResearchMap()
    {
        return playerResearchMap;
    }

    /**
     * Get all research for given {@link java.util.UUID}
     * @param playerUUID
     * @return
     */
    public Set<JournalPage> getResearchFor(UUID playerUUID)
    {
        Set<JournalPage> pages = new LinkedHashSet<JournalPage>();
        Set<String> pageKeys = playerResearchMap.get(playerUUID);
        if (pageKeys == null) return pages;
        for (String key : pageKeys)
            pages.add(researchPageMap.get("key"));
        return pages;
    }

    /**
     * Get all research for a given {@link net.minecraft.entity.player.EntityPlayer} 
     * @param player
     * @return
     */
    public Set<JournalPage> getResearchFor(EntityPlayer player)
    {
        return getResearchFor(player.getUniqueID());
    }
}
