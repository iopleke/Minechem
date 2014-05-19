package minechem.network;

import minechem.common.ModMinechem;
import minechem.network.MinechemPackets.ProtocolException;
import minechem.tileentity.decomposer.TileEntityDecomposer;
import minechem.tileentity.synthesis.TileEntitySynthesis;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.ForgeDirection;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class PacketSynthesisUpdate extends MinechemPackets
{
    /** Client only reference to the tile entity that is the chemical synthesis machine. */
    private TileEntitySynthesis tileEntity;
    
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
    
    /** Current amount of time that has been spent processing current item. */
    private int lastItemCookTimeValue;
    
    /** Total amount of time that will be spent processing current item. */
    private int lastItemCookTimeMaximum;

    public PacketSynthesisUpdate(TileEntitySynthesis tileSynthesis)
    {
        // World position information.
        tilePosX = tileSynthesis.xCoord;
        tilePosY = tileSynthesis.yCoord;
        tilePosZ = tileSynthesis.zCoord;
        
        // Cook time.
        lastItemCookTimeValue = tileSynthesis.currentItemCookingValue;
        lastItemCookTimeMaximum = tileSynthesis.currentItemCookingMaximum;
        
        // Energy.
        lastItemStoredEnergy = tileSynthesis.getEnergy(ForgeDirection.UNKNOWN);
        lastItemStoredEnergyMaximum = tileSynthesis.getEnergyCapacity(ForgeDirection.UNKNOWN);
        
        // Tile entity specific.
        this.tileEntity = tileSynthesis;
    }

    public PacketSynthesisUpdate()
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
            tileEntity = (TileEntitySynthesis) player.worldObj.getBlockTileEntity(tilePosX, tilePosY, tilePosZ);
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
    }
}
