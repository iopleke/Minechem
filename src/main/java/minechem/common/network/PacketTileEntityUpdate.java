package minechem.common.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class PacketTileEntityUpdate extends MinechemPackets
{
    protected TileEntity tileEntity;
    private int x;
    private int y;
    private int z;

    public PacketTileEntityUpdate(TileEntity tileEntity)
    {
        this.tileEntity = tileEntity;
        this.x = tileEntity.xCoord;
        this.y = tileEntity.yCoord;
        this.z = tileEntity.zCoord;
    }

    public PacketTileEntityUpdate()
    {
        super();
    }

    @Override
    public void execute(EntityPlayer player, Side side) throws ProtocolException
    {
        EntityPlayer entityPlayer = player;
        World world = entityPlayer.worldObj;
        this.tileEntity = world.getBlockTileEntity(x, y, z);
    }

    @Override
    public void read(ByteArrayDataInput in) throws ProtocolException
    {
        this.x = in.readInt();
        this.y = in.readInt();
        this.z = in.readInt();
    }

    @Override
    public void write(ByteArrayDataOutput out)
    {
        out.writeInt(x);
        out.writeInt(y);
        out.writeInt(z);
    }
}
