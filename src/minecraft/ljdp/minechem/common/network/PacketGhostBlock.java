package ljdp.minechem.common.network;

import ljdp.easypacket.EasyPacketData;
import ljdp.minechem.common.blueprint.MinechemBlueprint;
import ljdp.minechem.common.tileentity.TileEntityGhostBlock;
import cpw.mods.fml.common.network.Player;

public class PacketGhostBlock extends PacketTileEntityUpdate {

    TileEntityGhostBlock ghostBlock;

    @EasyPacketData
    int blueprintID;
    @EasyPacketData
    int ghostBlockID;

    public PacketGhostBlock(TileEntityGhostBlock ghostBlock) {
        super(ghostBlock);
        this.ghostBlock = ghostBlock;
        this.blueprintID = ghostBlock.getBlueprint().id;
        this.ghostBlockID = ghostBlock.getBlockID();
    }

    public PacketGhostBlock() {
        super();
    }

    @Override
    public void onReceive(Player player) {
        super.onReceive(player);
        if (this.tileEntity instanceof TileEntityGhostBlock) {
            this.ghostBlock = (TileEntityGhostBlock) this.tileEntity;
            MinechemBlueprint blueprint = MinechemBlueprint.blueprints.get(this.blueprintID);
            this.ghostBlock.setBlueprint(blueprint);
            this.ghostBlock.setBlockID(this.ghostBlockID);
        }
    }
}
