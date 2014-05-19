package minechem.common.network;

import minechem.common.ModMinechem;
import minechem.common.Settings;
import minechem.common.tileentity.TileEntityDecomposer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.ForgeDirection;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class PacketDecomposerUpdate extends MinechemPackets
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
    private long lastItemStoredEnergy;
    
    /** Maximum amount of energy. */
    private long lastItemStoredEnergyMaximum;
    
    /** Current amount of time that has been spent processing current item. */
    private int lastItemCookTimeValue;
    
    /** Total amount of time that will be spent processing current item. */
    private int lastItemCookTimeMaximum;
    
    /** Reference object that will be hooked on client side only */
    private TileEntityDecomposer tileEntity;

    public PacketDecomposerUpdate(TileEntityDecomposer serverDecomposer)
    {        
        // World position information.
        tilePosX = serverDecomposer.xCoord;
        tilePosY = serverDecomposer.yCoord;
        tilePosZ = serverDecomposer.zCoord;
        
        // Cook time.
        lastItemCookTimeValue = serverDecomposer.currentItemCookingValue;
        lastItemCookTimeMaximum = serverDecomposer.currentItemCookingMaximum;
        
        // Energy.
        lastItemStoredEnergy = serverDecomposer.getEnergy(ForgeDirection.UNKNOWN);
        lastItemStoredEnergyMaximum = serverDecomposer.getEnergyCapacity(ForgeDirection.UNKNOWN);
        
        // Tile entity specific information.
        this.state = serverDecomposer.getState().ordinal();
    }

    public PacketDecomposerUpdate()
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
            tileEntity = (TileEntityDecomposer) player.worldObj.getBlockTileEntity(tilePosX, tilePosY, tilePosZ);
            if (tileEntity == null)
            {
                return;
            }
            
            // Cook time.
            this.tileEntity.currentItemCookingValue = lastItemCookTimeValue;
            this.tileEntity.currentItemCookingMaximum = lastItemCookTimeMaximum;

            // Energy.
            this.tileEntity.setEnergy(ForgeDirection.UNKNOWN, lastItemStoredEnergy);
            this.tileEntity.setEnergyCapacity(lastItemStoredEnergyMaximum);
            
            // Machine state.
            this.tileEntity.setState(this.state);
            
            // Debugging info to be printed if energy levels change.
            if (Settings.DebugMode && this.tileEntity.getEnergy(ForgeDirection.UNKNOWN) != lastItemStoredEnergy)
            {
                ModMinechem.LOGGER.info("[Chemical Decomposer Packet] Set Machine State: " + this.tileEntity.getState());
            }
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

        // Cook time.
        this.lastItemCookTimeValue = in.readInt();
        this.lastItemCookTimeMaximum = in.readInt();

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

        // Cook time.
        out.writeInt(lastItemCookTimeValue);
        out.writeInt(lastItemCookTimeMaximum);

        // Energy.
        out.writeLong(lastItemStoredEnergy);
        out.writeLong(lastItemStoredEnergyMaximum);
        
        // Machine state.
        out.writeInt(state);
    }
}
