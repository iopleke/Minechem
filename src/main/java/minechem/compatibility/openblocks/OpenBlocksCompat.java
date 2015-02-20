package minechem.compatibility.openblocks;

import cpw.mods.fml.common.event.FMLInterModComms;
import minechem.Compendium;
import minechem.compatibility.CompatBase;
import minechem.helper.LogHelper;

import java.lang.reflect.Method;

public class OpenBlocksCompat extends CompatBase
{
    @Override
    protected void init()
    {
        FMLInterModComms.sendMessage(mod.getModId(), "donateUrl", Compendium.MetaData.patreon);
    }
}
