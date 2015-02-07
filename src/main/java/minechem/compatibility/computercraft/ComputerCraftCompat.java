package minechem.compatibility.computercraft;

import minechem.compatibility.CompatBase;

public class ComputerCraftCompat extends CompatBase
{
    @Override
    public void init()
    {
        PeripheralProvider.register();
    }
}
