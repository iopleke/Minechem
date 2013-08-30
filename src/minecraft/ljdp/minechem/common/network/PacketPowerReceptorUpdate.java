package ljdp.minechem.common.network;

import ljdp.easypacket.EasyPacketData;
import ljdp.minechem.common.tileentity.MinechemTileEntity;
import net.minecraft.tileentity.TileEntity;
import buildcraft.api.power.IPowerReceptor;
import cpw.mods.fml.common.network.Player;

public class PacketPowerReceptorUpdate extends PacketTileEntityUpdate {

    MinechemTileEntity powerReceptor;

    @EasyPacketData
    float energyStored;

    public PacketPowerReceptorUpdate(MinechemTileEntity powerReceptor) {
        super((TileEntity) powerReceptor);
        this.powerReceptor = powerReceptor;
        this.energyStored = this.powerReceptor.getEnergyStored();
    }

    public PacketPowerReceptorUpdate() {
        super();
    }

    @Override
    public boolean isChunkDataPacket() {
        return super.isChunkDataPacket();
    }

    @Override
    public void onReceive(Player player) {
        super.onReceive(player);
        if (this.tileEntity instanceof MinechemTileEntity) {
            this.powerReceptor = (MinechemTileEntity) this.tileEntity;
            this.powerReceptor.setEnergyStored(this.energyStored);
        }
    }

}
