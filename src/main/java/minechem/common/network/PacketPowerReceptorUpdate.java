package minechem.common.network;

import cpw.mods.fml.common.network.Player;
import ljdp.easypacket.EasyPacketData;
import minechem.common.tileentity.MinechemTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

public class PacketPowerReceptorUpdate extends PacketTileEntityUpdate
{

    MinechemTileEntity powerReceptor;

    @EasyPacketData
    long energyStored;

    public PacketPowerReceptorUpdate(MinechemTileEntity powerReceptor)
    {
        super((TileEntity) powerReceptor);
        this.powerReceptor = powerReceptor;
        this.energyStored = this.powerReceptor.getEnergy(ForgeDirection.UNKNOWN);
    }

    public PacketPowerReceptorUpdate()
    {
        super();
    }

    @Override
    public boolean isChunkDataPacket()
    {
        return super.isChunkDataPacket();
    }

    @Override
    public void onReceive(Player player)
    {
        super.onReceive(player);
        if (this.tileEntity instanceof MinechemTileEntity)
        {
            this.powerReceptor = (MinechemTileEntity) this.tileEntity;
            this.powerReceptor.setEnergy(ForgeDirection.UNKNOWN, this.energyStored);
        }
    }

}
