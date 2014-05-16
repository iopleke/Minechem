package minechem.common.network;

import ljdp.easypacket.EasyPacketData;
import minechem.common.tileentity.MinechemTileEntity;
import minechem.common.tileentity.TileEntityDecomposer;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.common.network.Player;

public class PacketDecomposerUpdate extends PacketPowerReceptorUpdate
{
    protected TileEntityDecomposer decomposer;

    @EasyPacketData
    int state;

    @EasyPacketData
    long energyUsage;

    public PacketDecomposerUpdate(TileEntityDecomposer decomposer)
    {
        super(decomposer);
        this.decomposer = decomposer;
        this.state = decomposer.getState().ordinal();
        this.energyUsage = decomposer.getEnergy(ForgeDirection.UNKNOWN);
    }

    public PacketDecomposerUpdate()
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
        if (this.tileEntity instanceof TileEntityDecomposer)
        {
            this.decomposer = (TileEntityDecomposer) this.tileEntity;
            this.decomposer.setState(this.state);
            this.decomposer.setEnergy(ForgeDirection.UNKNOWN, this.energyUsage);
        }
    }

}
