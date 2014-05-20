package minechem.gui;

import minechem.container.CotainerTable;
import minechem.item.chemistjournal.ContainerChemistJournal;
import minechem.item.chemistjournal.GuiChemistJournal;
import minechem.item.polytool.ContainerPolytool;
import minechem.item.polytool.GuiPolytool;
import minechem.tileentity.blueprintprojector.ContainerProjector;
import minechem.tileentity.blueprintprojector.GuiProjector;
import minechem.tileentity.blueprintprojector.TileEntityBlueprintProjector;
import minechem.tileentity.chemicalstorage.ContainerChemicalStorage;
import minechem.tileentity.chemicalstorage.GuiChemicalStorage;
import minechem.tileentity.chemicalstorage.TileEntityChemicalStorage;
import minechem.tileentity.decomposer.DecomposerContainer;
import minechem.tileentity.decomposer.DecomposerGui;
import minechem.tileentity.decomposer.DecomposerTileEntity;
import minechem.tileentity.leadedchest.ContainerLeadedChest;
import minechem.tileentity.leadedchest.GuiLeadedChest;
import minechem.tileentity.leadedchest.TileEntityLeadedChest;
import minechem.tileentity.microscope.ContainerMicroscope;
import minechem.tileentity.microscope.GuiMicroscope;
import minechem.tileentity.microscope.TileEntityMicroscope;
import minechem.tileentity.multiblock.ContainerFission;
import minechem.tileentity.multiblock.ContainerFusion;
import minechem.tileentity.multiblock.GuiFission;
import minechem.tileentity.multiblock.GuiFusion;
import minechem.tileentity.multiblock.TileEntityFission;
import minechem.tileentity.multiblock.TileEntityFusion;
import minechem.tileentity.prefab.TileEntityProxy;
import minechem.tileentity.synthesis.ContainerSynthesis;
import minechem.tileentity.synthesis.GuiSynthesis;
import minechem.tileentity.synthesis.TileEntitySynthesis;
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
        if (tileEntity instanceof DecomposerTileEntity)
        {
            return new DecomposerContainer(player.inventory, (DecomposerTileEntity) tileEntity);
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
        
        if (tileEntity instanceof DecomposerTileEntity)
        {
            return new DecomposerGui(player.inventory, (DecomposerTileEntity) tileEntity);
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
