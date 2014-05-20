package minechem.tileentity.synthesis;

import minechem.ModMinechem;
import minechem.network.MinechemPackets;
import minechem.network.MinechemPackets.ProtocolException;
import minechem.tileentity.decomposer.DecomposerTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.ForgeDirection;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class SynthesisPacketUpdate extends MinechemPackets
{
    /** Client only reference to the tile entity that is the chemical synthesis machine. */
    private SynthesisTileEntity tileEntity;
    
    /** X coordinates of tile entity source. */
    private int tilePosX;
    
    /** Y coordinates of tile entity source. */
    private int tilePosY;
    
    /** Z coordinates of tile entity source. */
    private int tilePosZ;
    
    /** Energy stored */
    private long lastItemStoredEnergy;
    
    /** Maximum amount of energy. */
    private long lastItemStoredEnergyMaximum;

    public SynthesisPacketUpdate(SynthesisTileEntity tileSynthesis)
    {
        // World position information.
        tilePosX = tileSynthesis.xCoord;
        tilePosY = tileSynthesis.yCoord;
        tilePosZ = tileSynthesis.zCoord;
        
        // Energy.
        lastItemStoredEnergy = tileSynthesis.getEnergy(ForgeDirection.UNKNOWN);
        lastItemStoredEnergyMaximum = tileSynthesis.getEnergyCapacity(ForgeDirection.UNKNOWN);
        
        // Tile entity specific.
        this.tileEntity = tileSynthesis;
    }

    public SynthesisPacketUpdate()
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
            tileEntity = (SynthesisTileEntity) player.worldObj.getBlockTileEntity(tilePosX, tilePosY, tilePosZ);
            if (tileEntity == null)
            {
                return;
            }

            // Energy.
            this.tileEntity.setEnergy(ForgeDirection.UNKNOWN, lastItemStoredEnergy);
            this.tileEntity.setEnergyCapacity(lastItemStoredEnergyMaximum);
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
        out.writeLong(lastItemStoredEnergy);
        out.writeLong(lastItemStoredEnergyMaximum);
    }
}
