package minechem.compatibility.computercraft;

import net.minecraft.client.Minecraft;
import minechem.compatibility.CompatBase;
import minechem.helper.LogHelper;

public class ComputerCraftCompat extends CompatBase
{
    @Override
    public void init()
    {
        PeripheralProvider.register();
    }
}
