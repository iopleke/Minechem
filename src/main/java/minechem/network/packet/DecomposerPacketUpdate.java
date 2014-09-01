package minechem.network.packet;

import minechem.network.PacketDispatcher;
import minechem.tileentity.decomposer.DecomposerTileEntity;
import net.minecraft.entity.player.EntityPlayer;

import net.minecraft.network.PacketBuffer;

import java.io.IOException;

public class DecomposerPacketUpdate extends PacketDispatcher.AbstractPacket<DecomposerPacketUpdate>
{
    /**
     * X coordinates of tile entity source.
     */
    private int tilePosX;

    /**
     * Y coordinates of tile entity source.
     */
    private int tilePosY;

    /**
     * Z coordinates of tile entity source.
     */
    private int tilePosZ;

    /**
     * Holds integer that is a reference to enumeration for current state machine is in.
     */
    private int state;

    /**
     * Energy stored
     */
    private double lastItemStoredEnergy;

    /**
     * Maximum amount of energy.
     */
    private double lastItemStoredEnergyMaximum;

    /**
     * Reference object that will be hooked on client side only
     */
    private DecomposerTileEntity tileEntity;

    public DecomposerPacketUpdate(DecomposerTileEntity serverDecomposer)
    {
        // World position information.
        tilePosX = serverDecomposer.xCoord;
        tilePosY = serverDecomposer.yCoord;
        tilePosZ = serverDecomposer.zCoord;

        // Tile entity specific information.
        this.state = serverDecomposer.getState().ordinal();
    }

    public DecomposerPacketUpdate()
    {
        // Required for reflection.
    }

    @Override
    public void sendPacket(PacketBuffer out) throws IOException
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

    @Override
    public void receivePacket(PacketBuffer in) throws IOException
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
    public void processPacket(EntityPlayer player)
    {
        tileEntity = (DecomposerTileEntity) player.worldObj.getTileEntity(tilePosX, tilePosY, tilePosZ);
        if (tileEntity == null)
        {
            return;
        }

        // Energy.
        // @TODO - implement an energy system
        // Machine state.
        this.tileEntity.setState(this.state);
    }
}
