package ljdp.minechem.common.network;

import ljdp.minechem.common.tileentity.MinechemTileEntity;
import ljdp.minechem.common.tileentity.TileEntitySynthesis;

public class PacketSynthesisUpdate extends PacketPowerReceptorUpdate {

    public PacketSynthesisUpdate(TileEntitySynthesis synthesis) {
        super((MinechemTileEntity) synthesis);
    }

    public PacketSynthesisUpdate() {
        super();
    }

}
