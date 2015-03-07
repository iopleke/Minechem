package minechem.registry;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import net.minecraft.entity.player.EntityPlayer;

public class ResearchRegistry
{
    private Map<UUID, Set<String>> playerResearchMap;
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
    }

    /**
     * Adds research with given key to the player if key does not exist nothing happens
     *
     * @param playerUUID the players UUID
     * @param pageName   the page name
     */
    public void addResearch(UUID playerUUID, String pageName)
    {
        if (!JournalRegistry.hasPage(pageName))
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
     * Gets the whole player research map used when saving to the json
     *
     * @return
     */
    public Map<UUID, Set<String>> getPlayerResearchMap()
    {
        return playerResearchMap;
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
        Set<String> keys = getResearchFor(player);
        return keys != null && keys.contains(key);
    }
}
