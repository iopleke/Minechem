package pixlepix.minechem.client.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import pixlepix.minechem.common.ModMinechem;
import pixlepix.minechem.common.tileentity.EmitterTileEntity;

public class GuiHandler implements IGuiHandler
{

    public GuiHandler()
    {
        NetworkRegistry.instance().registerGuiHandler(ModMinechem.INSTANCE, this);
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID)
        {
        case 0:
            return new ContainerEmitter(player.inventory, (EmitterTileEntity) world.getBlockTileEntity(x, y, z));
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID)
        {
        case 0:
            return new GuiEmitter(player.inventory, (EmitterTileEntity) world.getBlockTileEntity(x, y, z));
        }
        return null;
    }

}
