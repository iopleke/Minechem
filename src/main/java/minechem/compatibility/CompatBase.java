package minechem.compatibility;

import minechem.helper.LogHelper;

public abstract class CompatBase
{
    protected ModList mod;

    public boolean load(ModList mod)
    {
        this.mod = mod;
        if (mod.isLoaded())
        {
            LogHelper.info("Loading compatibility for " + mod.getModName());
            init();
            return true;
        } else
        {
            LogHelper.info(mod.getModName() + " not loaded - skipping");
        }
        return false;
    }

    protected abstract void init();
}
