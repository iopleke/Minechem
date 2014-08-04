package minechem.tileentity.decomposer;

import minechem.ModMinechem;
import minechem.Settings;
import minechem.network.MinechemPackets;
import minechem.network.MinechemPackets.ProtocolException;
import net.minecraft.entity.player.EntityPlayer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;
import net.minecraftforge.common.util.ForgeDirection;

public class DecomposerPacketUpdate extends MinechemPackets
{
    /** X coordinates of tile entity source. */
    private int tilePosX;
    
    /** Y coordinates of tile entity source. */
    private int tilePosY;
    
    /** Z coordinates of tile entity source. */
    private int tilePosZ;
    
    /** Holds integer that is a reference to enumeration for current state machine is in. */
    private int state;
    
    /** Energy stored */
    private double lastItemStoredEnergy;
    
    /** Maximum amount of energy. */
    private double lastItemStoredEnergyMaximum;
    
    /** Reference object that will be hooked on client side only */
    private DecomposerTileEntity tileEntity;

    public DecomposerPacketUpdate(DecomposerTileEntity serverDecomposer)
    {        
        // World position information.
        tilePosX = serverDecomposer.xCoord;
        tilePosY = serverDecomposer.yCoord;
        tilePosZ = serverDecomposer.zCoord;
        
        // Energy.
        lastItemStoredEnergy = serverDecomposer.getEnergy(ForgeDirection.UNKNOWN);
        lastItemStoredEnergyMaximum = serverDecomposer.getEnergyCapacity(ForgeDirection.UNKNOWN);
        
        // Tile entity specific information.
        this.state = serverDecomposer.getState().ordinal();
    }

    public DecomposerPacketUpdate()
    {
        // Required for reflection.
    }

    @Override
    public void execute(EntityPlayer player, Side side) throws ProtocolException
    {
        // ------
        // CLIENT
        // ------
        
        // Packet received by client, executing payload.
        if (side.isClient())
        {
            tileEntity = (DecomposerTileEntity) player.worldObj.getTileEntity(tilePosX, tilePosY, tilePosZ);
            if (tileEntity == null)
            {
                return;
            }
            
            // Energy.
            this.tileEntity.setEnergy(ForgeDirection.UNKNOWN, lastItemStoredEnergy);
            this.tileEntity.setEnergyCapacity((long) lastItemStoredEnergyMaximum);
            
            // Machine state.
            this.tileEntity.setState(this.state);
        }
        else
        {
            throw new ProtocolException("Cannot send this packet to the server!");
        }
    }

    @Override
    public void read(ByteArrayDataInput in) throws ProtocolException
    {
        // ------
        // CLIENT
        // ------

        // World coordinate information.
        this.tilePosX = in.readInt();
        this.tilePosY = in.readInt();
        this.tilePosZ = in.readInt();

        // Energy.
        this.lastItemStoredEnergy = in.readLong();
        this.lastItemStoredEnergyMaximum = in.readLong();
        
        // Machine state.
        this.state = in.readInt();
    }

    @Override
    public void write(ByteArrayDataOutput out)
    {
        // ------
        // SERVER
        // ------

        // World coordinate information.
        out.writeInt(tilePosX);
        out.writeInt(tilePosY);
        out.writeInt(tilePosZ);

        // Energy.
        out.writeLong((long) lastItemStoredEnergy);
        out.writeLong((long) lastItemStoredEnergyMaximum);
        
        // Machine state.
        out.writeInt(state);
    }
}
