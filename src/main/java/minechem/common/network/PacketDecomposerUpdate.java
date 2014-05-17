package minechem.common.network;

import minechem.common.tileentity.TileEntityDecomposer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.ForgeDirection;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class PacketDecomposerUpdate extends PacketPowerReceptorUpdate
{
    protected TileEntityDecomposer tileEntity;
    int state;
    long energyUsage;

    public PacketDecomposerUpdate(TileEntityDecomposer decomposer)
    {
        super(decomposer);
        this.tileEntity = decomposer;
        this.state = decomposer.getState().ordinal();
        this.energyUsage = decomposer.getEnergy(ForgeDirection.UNKNOWN);
    }

    public PacketDecomposerUpdate()
    {
        // Required for reflection.
    }

    @Override
    public void execute(EntityPlayer player, Side side) throws ProtocolException
    {
        super.execute(player, side);
        if (this.tileEntity instanceof TileEntityDecomposer)
        {
            this.tileEntity.setState(this.state);
            this.tileEntity.setEnergy(ForgeDirection.UNKNOWN, this.energyUsage);
        }
    }

    @Override
    public void read(ByteArrayDataInput in) throws ProtocolException
    {
        super.read(in);
    }

    @Override
    public void write(ByteArrayDataOutput out)
    {
        super.write(out);
    }
}
