package minechem.common.network;

import minechem.common.tileentity.TileEntityDecomposer;
import minechem.common.tileentity.TileEntitySynthesis;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.ForgeDirection;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class PacketSynthesisUpdate extends PacketPowerReceptorUpdate
{
    protected TileEntitySynthesis tileEntity;
    long energyUsage;

    public PacketSynthesisUpdate(TileEntitySynthesis decomposer)
    {
        super(decomposer);
        this.tileEntity = decomposer;
        this.energyUsage = decomposer.getEnergy(ForgeDirection.UNKNOWN);
    }

    public PacketSynthesisUpdate()
    {
        // Required for reflection.
    }

    @Override
    public void execute(EntityPlayer player, Side side) throws ProtocolException
    {
        super.execute(player, side);
        if (this.tileEntity instanceof TileEntitySynthesis)
        {
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