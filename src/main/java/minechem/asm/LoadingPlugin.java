package minechem.asm;

import codechicken.core.launch.DepLoader;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.classloading.FMLForgePlugin;

import java.util.Map;

@IFMLLoadingPlugin.TransformerExclusions({"minechem.asm."})
public class LoadingPlugin implements IFMLLoadingPlugin
{
    public static boolean runtimeDeobfEnabled = FMLForgePlugin.RUNTIME_DEOBF;
    
    public LoadingPlugin()
    {
        DepLoader.load();        
    }
    
    @Override
    public String[] getASMTransformerClass()
    {
        return new String[]{ getAccessTransformerClass() };
    }

    @Override
    public String getModContainerClass()
    {
        return null;
    }

    @Override
    public String getSetupClass()
    {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data)
    {

    }

    @Override
    public String getAccessTransformerClass()
    {
        return "minechem.asm.MinechemTransformer";
    }
}
