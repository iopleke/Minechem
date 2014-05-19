package minechem.common;

import minechem.client.gui.GuiChemicalStorage;
import minechem.client.gui.GuiChemistJournal;
import minechem.client.gui.GuiDecomposer;
import minechem.client.gui.GuiFission;
import minechem.client.gui.GuiFusion;
import minechem.client.gui.GuiLeadedChest;
import minechem.client.gui.GuiMicroscope;
import minechem.client.gui.GuiProjector;
import minechem.client.gui.GuiSynthesis;
import minechem.client.gui.GuiTableOfElements;
import minechem.common.containers.ContainerChemicalStorage;
import minechem.common.containers.ContainerChemistJournal;
import minechem.common.containers.ContainerDecomposer;
import minechem.common.containers.ContainerFission;
import minechem.common.containers.ContainerFusion;
import minechem.common.containers.ContainerLeadedChest;
import minechem.common.containers.ContainerMicroscope;
import minechem.common.containers.ContainerProjector;
import minechem.common.containers.ContainerSynthesis;
import minechem.common.containers.CotainerTable;
import minechem.common.polytool.ContainerPolytool;
import minechem.common.polytool.GuiPolytool;
import minechem.common.tileentity.TileEntityBlueprintProjector;
import minechem.common.tileentity.TileEntityChemicalStorage;
import minechem.common.tileentity.TileEntityDecomposer;
import minechem.common.tileentity.TileEntityFission;
import minechem.common.tileentity.TileEntityFusion;
import minechem.common.tileentity.TileEntityLeadedChest;
import minechem.common.tileentity.TileEntityMicroscope;
import minechem.common.tileentity.TileEntityProxy;
import minechem.common.tileentity.TileEntitySynthesis;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{

    public static final int GUI_ID_TILEENTITY = 0;
    public static final int GUI_ID_JOURNAL = 1;
    public static final int GUI_TABLE = 2;

    public static final int GUI_ID_POLYTOOL = 3;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if (ID == GUI_ID_JOURNAL)
        {
            return getServerGuiElementForJournal(player, world);
        }
        if (ID == GUI_ID_POLYTOOL)
        {
            return getServerGuiElementForPolytool(player, world);
        }
        if (ID == GUI_TABLE)
        {
            return new CotainerTable(player.inventory);
        }
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if (tileEntity instanceof TileEntityDecomposer)
        {
            return new ContainerDecomposer(player.inventory, (TileEntityDecomposer) tileEntity);
        }
        if (tileEntity instanceof TileEntityLeadedChest)
        {
            return new ContainerLeadedChest(player.inventory, (TileEntityLeadedChest) tileEntity);
        }
        if (tileEntity instanceof TileEntityMicroscope)
        {
            return new ContainerMicroscope(player.inventory, (TileEntityMicroscope) tileEntity);
        }
        if (tileEntity instanceof TileEntitySynthesis)
        {
            return new ContainerSynthesis(player.inventory, (TileEntitySynthesis) tileEntity);
        }
        if (tileEntity instanceof TileEntityFusion)
        {
            return new ContainerFusion(player.inventory, (TileEntityFusion) tileEntity);
        }
        if (tileEntity instanceof TileEntityFission)
        {
            return new ContainerFission(player.inventory, (TileEntityFission) tileEntity);
        }

        if (tileEntity instanceof TileEntityProxy)
        {
            return getServerGuiElementFromProxy((TileEntityProxy) tileEntity, player);
        }

        if (tileEntity instanceof TileEntityBlueprintProjector)
        {
            return new ContainerProjector(player.inventory, (TileEntityBlueprintProjector) tileEntity);
        }
        if (tileEntity instanceof TileEntityChemicalStorage)
        {
            return new ContainerChemicalStorage(player.inventory, (TileEntityChemicalStorage) tileEntity);
        }
        return null;
    }

    private Object getServerGuiElementForPolytool(EntityPlayer player, World world)
    {

        return new ContainerPolytool(player);
    }

    public Object getServerGuiElementFromProxy(TileEntityProxy proxy, EntityPlayer player)
    {
        TileEntity tileEntity = proxy.getManager();
        if (tileEntity instanceof TileEntityFusion)
        {
            return new ContainerFusion(player.inventory, (TileEntityFusion) tileEntity);
        }

        if (tileEntity instanceof TileEntityFission)
        {
            return new ContainerFission(player.inventory, (TileEntityFission) tileEntity);
        }
        return null;
    }

    public Object getServerGuiElementForJournal(EntityPlayer entityPlayer, World world)
    {
        return new ContainerChemistJournal(entityPlayer.inventory);
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if (ID == GUI_ID_JOURNAL)
        {
            return getClientGuiElementForJournal(player, world);
        }
        
        if (ID == GUI_TABLE)
        {
            return getClientGuiForJournal(player, world);
        }

        if (ID == GUI_ID_POLYTOOL)
        {
            return getClientGuiForPolytool(player, world);
        }
        
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        
        if (tileEntity instanceof TileEntityDecomposer)
        {
            return new GuiDecomposer(player.inventory, (TileEntityDecomposer) tileEntity);
        }
        if (tileEntity instanceof TileEntityLeadedChest)
        {
            return new GuiLeadedChest(player.inventory, (TileEntityLeadedChest) tileEntity);
        }
        if (tileEntity instanceof TileEntityMicroscope)
        {
            return new GuiMicroscope(player.inventory, (TileEntityMicroscope) tileEntity);
        }
        if (tileEntity instanceof TileEntitySynthesis)
        {
            return new GuiSynthesis(player.inventory, (TileEntitySynthesis) tileEntity);
        }
        if (tileEntity instanceof TileEntityFusion)
        {
            return new GuiFusion(player.inventory, (TileEntityFusion) tileEntity);
        }
        if (tileEntity instanceof TileEntityProxy)
        {
            return getClientGuiElementFromProxy((TileEntityProxy) tileEntity, player);
        }
        if (tileEntity instanceof TileEntityBlueprintProjector)
        {
            return new GuiProjector(player.inventory, (TileEntityBlueprintProjector) tileEntity);
        }
        if (tileEntity instanceof TileEntityChemicalStorage)
        {
            return new GuiChemicalStorage(player.inventory, (TileEntityChemicalStorage) tileEntity);
        }
        if (tileEntity instanceof TileEntityFission)
        {
            return new GuiFission(player.inventory, (TileEntityFission) tileEntity);
        }
        return null;
    }

    private GuiPolytool getClientGuiForPolytool(EntityPlayer player, World world)
    {

        return new GuiPolytool(new ContainerPolytool(player));
    }

    public Object getClientGuiElementFromProxy(TileEntityProxy proxy, EntityPlayer player)
    {
        TileEntity tileEntity = proxy.getManager();
        if (tileEntity instanceof TileEntityFusion)
        {
            return new GuiFusion(player.inventory, (TileEntityFusion) tileEntity);
        }

        if (tileEntity instanceof TileEntityFission)
        {
            return new GuiFission(player.inventory, (TileEntityFission) tileEntity);
        }
        return null;
    }

    public Object getClientGuiElementForJournal(EntityPlayer player, World world)
    {
        return new GuiChemistJournal(player);
    }

    public Object getClientGuiForJournal(EntityPlayer player, World world)
    {
        return new GuiTableOfElements(player);
    }

}
