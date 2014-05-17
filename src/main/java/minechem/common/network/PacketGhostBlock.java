package minechem.common.network;

import minechem.common.blueprint.MinechemBlueprint;
import minechem.common.tileentity.TileEntityGhostBlock;
import net.minecraft.entity.player.EntityPlayer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class PacketGhostBlock extends PacketTileEntityUpdate
{

    TileEntityGhostBlock ghostBlock;
    int blueprintID;
    int ghostBlockID;

    public PacketGhostBlock(TileEntityGhostBlock ghostBlock)
    {
        super(ghostBlock);
        this.ghostBlock = ghostBlock;
        this.blueprintID = ghostBlock.getBlueprint().id;
        this.ghostBlockID = ghostBlock.getBlockID();
    }

    public PacketGhostBlock()
    {
        super();
    }

    @Override
    public void execute(EntityPlayer player, Side side) throws ProtocolException
    {
        super.execute(player, side);
        if (this.tileEntity instanceof TileEntityGhostBlock)
        {
            this.ghostBlock = (TileEntityGhostBlock) this.tileEntity;
            MinechemBlueprint blueprint = MinechemBlueprint.blueprints.get(this.blueprintID);
            this.ghostBlock.setBlueprint(blueprint);
            this.ghostBlock.setBlockID(this.ghostBlockID);
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
