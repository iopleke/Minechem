package minechem.handler;

import minechem.Minechem;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;

/**
 * Runs method on reload of the resources
 */
public class ResourceReloadListener implements IResourceManagerReloadListener
{
    @Override
    public void onResourceManagerReload(IResourceManager p_110549_1_)
    {
        JournalHandler.init(Minechem.proxy.getCurrentLanguage());
    }
}
