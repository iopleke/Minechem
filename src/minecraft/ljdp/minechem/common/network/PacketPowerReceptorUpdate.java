package ljdp.minechem.common.network;

import net.minecraft.tileentity.TileEntity;
import buildcraft.api.power.IPowerReceptor;
import cpw.mods.fml.common.network.Player;
import ljdp.easypacket.EasyPacketData;
import ljdp.minechem.common.MinechemPowerProvider;

public class PacketPowerReceptorUpdate extends PacketTileEntityUpdate {

    IPowerReceptor powerReceptor;
    MinechemPowerProvider powerProvider;

    @EasyPacketData
    float energyStored;

    public PacketPowerReceptorUpdate(IPowerReceptor powerReceptor) {
        super((TileEntity) powerReceptor);
        this.powerReceptor = powerReceptor;
        this.powerProvider = (MinechemPowerProvider) powerReceptor.getPowerProvider();
        this.energyStored = this.powerProvider.getEnergyStored();
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
        if (this.tileEntity instanceof IPowerReceptor) {
            this.powerReceptor = (IPowerReceptor) this.tileEntity;
            this.powerProvider = (MinechemPowerProvider) this.powerReceptor.getPowerProvider();
            this.powerProvider.setEnergyStored(this.energyStored);
        }
    }

}
