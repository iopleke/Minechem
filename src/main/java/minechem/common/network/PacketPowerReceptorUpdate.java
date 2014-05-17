package minechem.common.network;

import minechem.common.tileentity.MinechemTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.ForgeDirection;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class PacketPowerReceptorUpdate extends PacketTileEntityUpdate
{
    private MinechemTileEntity tileEntity;
    private long energyStored;
    private long energyCapacity;

    public PacketPowerReceptorUpdate(MinechemTileEntity powerReceptor)
    {
        this.tileEntity = powerReceptor;
        this.energyStored = this.tileEntity.getEnergy(ForgeDirection.UNKNOWN);
        this.energyCapacity = this.tileEntity.getEnergyCapacity(ForgeDirection.UNKNOWN);
    }

    public PacketPowerReceptorUpdate()
    {
        super();
    }

    @Override
    public void execute(EntityPlayer player, Side side) throws ProtocolException
    {
        super.execute(player, side);
        if (this.tileEntity instanceof MinechemTileEntity)
        {
            this.tileEntity.setEnergy(ForgeDirection.UNKNOWN, this.energyStored);
        }
    }

    @Override
    public void read(ByteArrayDataInput in) throws ProtocolException
    {
        super.read(in);
        this.energyStored = in.readLong();
        this.energyCapacity = in.readLong();
    }

    @Override
    public void write(ByteArrayDataOutput out)
    {
        super.write(out);
        out.writeLong(this.energyStored);
        out.writeLong(this.energyCapacity);
    }
}
