package minechem.computercraft.lua;

import cpw.mods.fml.common.Optional;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;

@Optional.InterfaceList(
        {
            @Optional.Interface(iface = "dan200.computercraft.api.peripheral.IComputerAccess", modid = "ComputerCraft"),
            @Optional.Interface(iface = "dan200.computercraft.api.lua.ILuaException", modid = "ComputerCraft"),
            @Optional.Interface(iface = "dan200.computercraft.api.lua.ILuaContext", modid = "ComputerCraft")
        })
public interface ILuaMethod
{
    public String getMethodName();

    @Optional.Method(modid = "ComputerCraft")
    public Object[] call(IComputerAccess computer, ILuaContext context, Object[] args) throws LuaException, InterruptedException;

    public String getArgs();

    public String[] getDetails();
}
