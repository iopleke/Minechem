package pixlepix.minechem.common.network;

import pixlepix.minechem.common.tileentity.MinechemTileEntity;
import pixlepix.minechem.common.tileentity.TileEntitySynthesis;

public class PacketSynthesisUpdate extends PacketPowerReceptorUpdate {

    public PacketSynthesisUpdate(TileEntitySynthesis synthesis) {
        super((MinechemTileEntity) synthesis);
    }

    public PacketSynthesisUpdate() {
        super();
    }

}
