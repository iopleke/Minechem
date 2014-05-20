package minechem.gui;

import minechem.container.CotainerTable;
import minechem.item.chemistjournal.ChemistJournalContainer;
import minechem.item.chemistjournal.ChemistJournalGui;
import minechem.item.polytool.ContainerPolytool;
import minechem.item.polytool.GuiPolytool;
import minechem.tileentity.blueprintprojector.BlueprintProjectorContainer;
import minechem.tileentity.blueprintprojector.BlueprintProjectorGui;
import minechem.tileentity.blueprintprojector.BlueprintProjectorTileEntity;
import minechem.tileentity.chemicalstorage.ChemicalStorageContainer;
import minechem.tileentity.chemicalstorage.ChemicalStorageGui;
import minechem.tileentity.chemicalstorage.ChemicalStorageTileEntity;
import minechem.tileentity.decomposer.DecomposerContainer;
import minechem.tileentity.decomposer.DecomposerGui;
import minechem.tileentity.decomposer.DecomposerTileEntity;
import minechem.tileentity.leadedchest.LeadedChestContainer;
import minechem.tileentity.leadedchest.LeadedChestGui;
import minechem.tileentity.leadedchest.LeadedChestTileEntity;
import minechem.tileentity.microscope.MicroscopeContainer;
import minechem.tileentity.microscope.MicroscopeGui;
import minechem.tileentity.microscope.MicroscopeTileEntity;
import minechem.tileentity.multiblock.fission.FissionContainer;
import minechem.tileentity.multiblock.fission.FissionGui;
import minechem.tileentity.multiblock.fission.FissionTileEntity;
import minechem.tileentity.multiblock.fusion.FusionContainer;
import minechem.tileentity.multiblock.fusion.FusionGui;
import minechem.tileentity.multiblock.fusion.FusionTileEntity;
import minechem.tileentity.prefab.TileEntityProxy;
import minechem.tileentity.synthesis.SynthesisContainer;
import minechem.tileentity.synthesis.SynthesisGui;
import minechem.tileentity.synthesis.SynthesisTileEntity;
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
        if (tileEntity instanceof LeadedChestTileEntity)
        {
            return new LeadedChestContainer(player.inventory, (LeadedChestTileEntity) tileEntity);
        }
        if (tileEntity instanceof MicroscopeTileEntity)
        {
            return new MicroscopeContainer(player.inventory, (MicroscopeTileEntity) tileEntity);
        }
        if (tileEntity instanceof SynthesisTileEntity)
        {
            return new SynthesisContainer(player.inventory, (SynthesisTileEntity) tileEntity);
        }
        if (tileEntity instanceof FusionTileEntity)
        {
            return new FusionContainer(player.inventory, (FusionTileEntity) tileEntity);
        }
        if (tileEntity instanceof FissionTileEntity)
        {
            return new FissionContainer(player.inventory, (FissionTileEntity) tileEntity);
        }

        if (tileEntity instanceof TileEntityProxy)
        {
            return getServerGuiElementFromProxy((TileEntityProxy) tileEntity, player);
        }

        if (tileEntity instanceof BlueprintProjectorTileEntity)
        {
            return new BlueprintProjectorContainer(player.inventory, (BlueprintProjectorTileEntity) tileEntity);
        }
        if (tileEntity instanceof ChemicalStorageTileEntity)
        {
            return new ChemicalStorageContainer(player.inventory, (ChemicalStorageTileEntity) tileEntity);
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
        if (tileEntity instanceof FusionTileEntity)
        {
            return new FusionContainer(player.inventory, (FusionTileEntity) tileEntity);
        }

        if (tileEntity instanceof FissionTileEntity)
        {
            return new FissionContainer(player.inventory, (FissionTileEntity) tileEntity);
        }
        return null;
    }

    public Object getServerGuiElementForJournal(EntityPlayer entityPlayer, World world)
    {
        return new ChemistJournalContainer(entityPlayer.inventory);
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
        if (tileEntity instanceof LeadedChestTileEntity)
        {
            return new LeadedChestGui(player.inventory, (LeadedChestTileEntity) tileEntity);
        }
        if (tileEntity instanceof MicroscopeTileEntity)
        {
            return new MicroscopeGui(player.inventory, (MicroscopeTileEntity) tileEntity);
        }
        if (tileEntity instanceof SynthesisTileEntity)
        {
            return new SynthesisGui(player.inventory, (SynthesisTileEntity) tileEntity);
        }
        if (tileEntity instanceof FusionTileEntity)
        {
            return new FusionGui(player.inventory, (FusionTileEntity) tileEntity);
        }
        if (tileEntity instanceof TileEntityProxy)
        {
            return getClientGuiElementFromProxy((TileEntityProxy) tileEntity, player);
        }
        if (tileEntity instanceof BlueprintProjectorTileEntity)
        {
            return new BlueprintProjectorGui(player.inventory, (BlueprintProjectorTileEntity) tileEntity);
        }
        if (tileEntity instanceof ChemicalStorageTileEntity)
        {
            return new ChemicalStorageGui(player.inventory, (ChemicalStorageTileEntity) tileEntity);
        }
        if (tileEntity instanceof FissionTileEntity)
        {
            return new FissionGui(player.inventory, (FissionTileEntity) tileEntity);
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
        if (tileEntity instanceof FusionTileEntity)
        {
            return new FusionGui(player.inventory, (FusionTileEntity) tileEntity);
        }

        if (tileEntity instanceof FissionTileEntity)
        {
            return new FissionGui(player.inventory, (FissionTileEntity) tileEntity);
        }
        return null;
    }

    public Object getClientGuiElementForJournal(EntityPlayer player, World world)
    {
        return new ChemistJournalGui(player);
    }

    public Object getClientGuiForJournal(EntityPlayer player, World world)
    {
        return new GuiTableOfElements(player);
    }

}
