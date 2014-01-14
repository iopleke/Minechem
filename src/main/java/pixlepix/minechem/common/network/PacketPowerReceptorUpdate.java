package pixlepix.minechem.common.network;

import cpw.mods.fml.common.network.Player;
import ljdp.easypacket.EasyPacketData;
import net.minecraft.tileentity.TileEntity;
import pixlepix.minechem.common.tileentity.MinechemTileEntity;

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
            this.powerReceptor.energyStored = (int) this.energyStored;
        }
    }

}
