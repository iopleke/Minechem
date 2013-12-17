package ljdp.minechem.common.network;

import buildcraft.api.power.IPowerReceptor;
import cpw.mods.fml.common.network.Player;
import ljdp.easypacket.EasyPacketData;
import ljdp.minechem.common.tileentity.MinechemTileEntity;
import ljdp.minechem.common.tileentity.TileEntityDecomposer;

public class PacketDecomposerUpdate extends PacketPowerReceptorUpdate {

    protected TileEntityDecomposer decomposer;

    @EasyPacketData
    int state;
    @EasyPacketData
    float energyUsage;

    public PacketDecomposerUpdate(TileEntityDecomposer decomposer) {
        super((MinechemTileEntity) decomposer);
        this.decomposer = decomposer;
        this.state = decomposer.getState().ordinal();
        this.energyUsage = decomposer.getRequest();
    }

    public PacketDecomposerUpdate() {
        super();
    }

    @Override
    public boolean isChunkDataPacket() {
        return super.isChunkDataPacket();
    }

    @Override
    public void onReceive(Player player) {
        super.onReceive(player);
        if (this.tileEntity instanceof TileEntityDecomposer) {
            this.decomposer = (TileEntityDecomposer) this.tileEntity;
            this.decomposer.setState(this.state);
            this.decomposer.setEnergyUsage(this.energyUsage);
        }
    }

}
