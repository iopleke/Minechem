package ljdp.minechem.common;

import ljdp.minechem.client.gui.GuiChemicalStorage;
import ljdp.minechem.client.gui.GuiChemicalVat;
import ljdp.minechem.client.gui.GuiChemistJournal;
import ljdp.minechem.client.gui.GuiDecomposer;
import ljdp.minechem.client.gui.GuiFusion;
import ljdp.minechem.client.gui.GuiMicroscope;
import ljdp.minechem.client.gui.GuiProjector;
import ljdp.minechem.client.gui.GuiSynthesis;
import ljdp.minechem.client.gui.GuiTableOfElements;
import ljdp.minechem.common.containers.ContainerChemicalStorage;
import ljdp.minechem.common.containers.ContainerChemistJournal;
import ljdp.minechem.common.containers.ContainerDecomposer;
import ljdp.minechem.common.containers.ContainerFusion;
import ljdp.minechem.common.containers.ContainerMicroscope;
import ljdp.minechem.common.containers.ContainerProjector;
import ljdp.minechem.common.containers.ContainerSynthesis;
import ljdp.minechem.common.containers.ContainerVat;
import ljdp.minechem.common.containers.CotainerTable;
import ljdp.minechem.common.tileentity.TileEntityBlueprintProjector;
import ljdp.minechem.common.tileentity.TileEntityChemicalStorage;
import ljdp.minechem.common.tileentity.TileEntityDecomposer;
import ljdp.minechem.common.tileentity.TileEntityFusion;
import ljdp.minechem.common.tileentity.TileEntityMicroscope;
import ljdp.minechem.common.tileentity.TileEntityProxy;
import ljdp.minechem.common.tileentity.TileEntitySynthesis;
import ljdp.minechem.common.tileentity.TileEntityVat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    public static final int GUI_ID_TILEENTITY = 0;
    public static final int GUI_ID_JOURNAL = 1;
    public static final int GUI_TABLE = 2;
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == GUI_ID_JOURNAL) { return getServerGuiElementForJournal(player, world); }
        if (ID == GUI_TABLE){ return new CotainerTable(); }
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if (tileEntity instanceof TileEntityDecomposer)
            return new ContainerDecomposer(player.inventory, (TileEntityDecomposer) tileEntity);
        if (tileEntity instanceof TileEntityMicroscope)
            return new ContainerMicroscope(player.inventory, (TileEntityMicroscope) tileEntity);
        if (tileEntity instanceof TileEntitySynthesis)
            return new ContainerSynthesis(player.inventory, (TileEntitySynthesis) tileEntity);
        if (tileEntity instanceof TileEntityFusion)
            return new ContainerFusion(player.inventory, (TileEntityFusion) tileEntity);
        if (tileEntity instanceof TileEntityProxy)
            return getServerGuiElementFromProxy((TileEntityProxy) tileEntity, player);
        if (tileEntity instanceof TileEntityBlueprintProjector)
            return new ContainerProjector(player.inventory, (TileEntityBlueprintProjector) tileEntity);
        if (tileEntity instanceof TileEntityChemicalStorage)
            return new ContainerChemicalStorage(player.inventory, (TileEntityChemicalStorage) tileEntity);
        if (tileEntity instanceof TileEntityVat)
            return new ContainerVat(player.inventory, (TileEntityVat) tileEntity);
        return null;
    }

    public Object getServerGuiElementFromProxy(TileEntityProxy proxy, EntityPlayer player) {
        TileEntity tileEntity = proxy.getManager();
        if (tileEntity instanceof TileEntityFusion)
            return new ContainerFusion(player.inventory, (TileEntityFusion) tileEntity);
        return null;
    }

    public Object getServerGuiElementForJournal(EntityPlayer entityPlayer, World world) {
        return new ContainerChemistJournal(entityPlayer.inventory);
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == GUI_ID_JOURNAL)
            return getClientGuiElementForJournal(player, world);
        if (ID == GUI_TABLE)
        	return getClientGuiForJournal(player, world);
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if (tileEntity instanceof TileEntityDecomposer)
            return new GuiDecomposer(player.inventory, (TileEntityDecomposer) tileEntity);
        if (tileEntity instanceof TileEntityMicroscope)
            return new GuiMicroscope(player.inventory, (TileEntityMicroscope) tileEntity);
        if (tileEntity instanceof TileEntitySynthesis)
            return new GuiSynthesis(player.inventory, (TileEntitySynthesis) tileEntity);
        if (tileEntity instanceof TileEntityFusion)
            return new GuiFusion(player.inventory, (TileEntityFusion) tileEntity);
        if (tileEntity instanceof TileEntityProxy)
            return getClientGuiElementFromProxy((TileEntityProxy) tileEntity, player);
        if (tileEntity instanceof TileEntityBlueprintProjector)
            return new GuiProjector(player.inventory, (TileEntityBlueprintProjector) tileEntity);
        if (tileEntity instanceof TileEntityChemicalStorage)
            return new GuiChemicalStorage(player.inventory, (TileEntityChemicalStorage) tileEntity);
        if (tileEntity instanceof TileEntityVat)
            return new GuiChemicalVat(player.inventory, (TileEntityVat) tileEntity);
        return null;
    }

    public Object getClientGuiElementFromProxy(TileEntityProxy proxy, EntityPlayer player) {
        TileEntity tileEntity = proxy.getManager();
        if (tileEntity instanceof TileEntityFusion)
            return new GuiFusion(player.inventory, (TileEntityFusion) tileEntity);
        return null;
    }

    public Object getClientGuiElementForJournal(EntityPlayer player, World world) {
        return new GuiChemistJournal(player);
    }
    public Object getClientGuiForJournal(EntityPlayer player, World world) {
        return new GuiTableOfElements(player);
    }

}
