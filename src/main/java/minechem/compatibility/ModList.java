package minechem.compatibility;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.common.Loader;
import minechem.Compendium;
import minechem.compatibility.IGW.IGWCompat;
import minechem.compatibility.computercraft.ComputerCraftCompat;
import minechem.compatibility.openblocks.OpenBlocksCompat;
import minechem.helper.LogHelper;

/**
 * Example Enum Implementation of ModCompat to automatically register compatibility modules
 */
public enum ModList
{
    computercraft(Compendium.Naming.Mods.computerCraft, "ComputerCraft", new ComputerCraftCompat()),
    opencomputers(Compendium.Naming.Mods.openComputers, "OpenComputers"),
    openblocks(Compendium.Naming.Mods.openBlocks, "OpenBlocks", new OpenBlocksCompat()),
    igwmod(Compendium.Naming.Mods.igwmod, "IGWMod", new IGWCompat());

    private final String modId;
    private final String modName;
    private final CompatBase compatClass;
    private final boolean loaded;

    ModList(CompatBase compatClass)
    {
        this("minecraft", "Minecraft", compatClass, true);
    }

    ModList(String modId, CompatBase compatClass)
    {
        this(modId, modId, compatClass);
    }

    ModList(String modId)
    {
        this(modId, modId, null, Loader.isModLoaded(modId));
    }

    ModList(String modId, String modName)
    {
        this(modId, modName, null, Loader.isModLoaded(modId));
    }

    ModList(String modId, String modName, CompatBase compatClass)
    {
        this(modId, modName, compatClass, Loader.isModLoaded(modId));
    }

    ModList(String modId, String modName, CompatBase compatClass, boolean loaded)
    {
        this.modId = modId;
        this.modName = modName;
        this.compatClass = compatClass;
        this.loaded = loaded;
    }

    public String getModId()
    {
        return modId;
    }

    public String getModName()
    {
        return modName;
    }

    public boolean isLoaded()
    {
        return loaded;
    }

    private void load()
    {
    	try {
    		compatClass.load(this);
    	} catch(NullPointerException e) {
    		LogHelper.info(modName+" compatability not loaded, - skipping");
    	}
    }

    public static void loadCompatibility()
    {
        for (ModList mod : values())
        {
            mod.load();
        }
    }
}
