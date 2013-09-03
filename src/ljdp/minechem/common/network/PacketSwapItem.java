package ljdp.minechem.common.network;
 
import ljdp.easypacket.EasyPacket;
import ljdp.easypacket.EasyPacketData;
import ljdp.minechem.api.core.EnumMolecule;
import ljdp.minechem.common.MinechemItems;
import ljdp.minechem.common.blueprint.MinechemBlueprint;
import ljdp.minechem.common.tileentity.TileEntityBluePrintPrinter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.Player;
 
public class PacketSwapItem extends EasyPacket {
    @EasyPacketData
    private int x;
    @EasyPacketData
    private int y;
    @EasyPacketData
    private int z;
    @EasyPacketData
    private int itemID;
    public PacketSwapItem(int x, int y, int z, int itemID) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.itemID = itemID;
    }
 
    public PacketSwapItem() {
    }
    @Override
    public boolean isChunkDataPacket() {
        return false;
    }
 
    @Override
    public void onReceive(Player player) {
        System.out.print("Packet Recieved!");
        World world = ((EntityPlayer) player).worldObj;
        TileEntity te = world.getBlockTileEntity(x, y, z);
     System.out.print("Got TE!");
     if(te instanceof TileEntityBluePrintPrinter){       
     System.out.print("TE IS INSTANCEOF!!");
     if(((TileEntityBluePrintPrinter) te).inventory[23] == null){
    	 
    	 
     }else{
     if(((TileEntityBluePrintPrinter) te).inventory[23].itemID == new ItemStack(MinechemItems.blueprint, 1 , 2).itemID)
    	 if(((TileEntityBluePrintPrinter) te).getEnergyStored() >= 400){
    		 ((TileEntityBluePrintPrinter) te).energyStored-=400;
            ((TileEntityBluePrintPrinter) te).setInventorySlotContents(23,
                    new ItemStack(MinechemItems.blueprint, 1 , itemID));
        }}}
    }}
