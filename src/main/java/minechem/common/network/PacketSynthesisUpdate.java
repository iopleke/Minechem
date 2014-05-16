package minechem.common.network;

import minechem.common.tileentity.MinechemTileEntity;
import minechem.common.tileentity.TileEntitySynthesis;

public class PacketSynthesisUpdate extends PacketPowerReceptorUpdate
{

    public PacketSynthesisUpdate(TileEntitySynthesis synthesis)
    {
        super(synthesis);
    }

    public PacketSynthesisUpdate()
    {
        super();
    }

}
