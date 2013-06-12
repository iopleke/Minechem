package ljdp.minechem.common.network;

import ljdp.easypacket.EasyPacketData;
import ljdp.minechem.common.tileentity.TileEntityBluePrintPrinter;
import buildcraft.api.power.IPowerReceptor;
import cpw.mods.fml.common.network.Player;

public class PacketPrinterUpdate extends PacketPowerReceptorUpdate {

    protected TileEntityBluePrintPrinter decomposer;

    @EasyPacketData
    int state;
    @EasyPacketData
    float energyUsage;

    public PacketPrinterUpdate(TileEntityBluePrintPrinter decomposer) {
        super((IPowerReceptor) decomposer);
        this.decomposer = decomposer;
        this.energyUsage = this.powerProvider.getCurrentEnergyUsage();
    }

    public PacketPrinterUpdate() {
        super();
    }

    @Override
    public boolean isChunkDataPacket() {
        return super.isChunkDataPacket();
    }

    @Override
    public void onReceive(Player player) {
        super.onReceive(player);
        if (this.tileEntity instanceof TileEntityBluePrintPrinter) {
            this.decomposer = (TileEntityBluePrintPrinter) this.tileEntity;
            this.powerProvider.setCurrentEnergyUsage(this.energyUsage);
        }
    }

}
