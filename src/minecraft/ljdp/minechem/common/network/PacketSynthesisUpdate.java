package ljdp.minechem.common.network;

import buildcraft.api.power.IPowerReceptor;
import ljdp.minechem.common.tileentity.TileEntitySynthesis;

public class PacketSynthesisUpdate extends PacketPowerReceptorUpdate {

    public PacketSynthesisUpdate(TileEntitySynthesis synthesis) {
        super((IPowerReceptor) synthesis);
    }

    public PacketSynthesisUpdate() {
        super();
    }

}
