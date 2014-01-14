package pixlepix.minechem.common.network;

import cpw.mods.fml.common.network.Player;

import ljdp.easypacket.EasyPacket;
import ljdp.easypacket.EasyPacketData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PacketTileEntityUpdate extends EasyPacket {

    protected TileEntity tileEntity;

    @EasyPacketData
    int x;
    @EasyPacketData
    int y;
    @EasyPacketData
    int z;

    public PacketTileEntityUpdate(TileEntity tileEntity) {
        super();
        this.tileEntity = tileEntity;
        this.x = tileEntity.xCoord;
        this.y = tileEntity.yCoord;
        this.z = tileEntity.zCoord;
    }

    public PacketTileEntityUpdate() {
        super();
    }

    @Override
    public boolean isChunkDataPacket() {
        return true;
    }

    @Override
    public void onReceive(Player player) {
        EntityPlayer entityPlayer = (EntityPlayer) player;
        World world = entityPlayer.worldObj;
        this.tileEntity = world.getBlockTileEntity(x, y, z);
    }

}
