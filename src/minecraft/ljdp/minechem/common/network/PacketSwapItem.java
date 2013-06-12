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
import buildcraft.api.power.IPowerProvider;
import buildcraft.api.power.PowerProvider;
import cpw.mods.fml.common.network.Player;
 
public class PacketSwapItem extends EasyPacket {
    @EasyPacketData
    private int x;
    @EasyPacketData
    private int y;
    @EasyPacketData
    private int z;
 
    public PacketSwapItem(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
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
     IPowerProvider power = ((TileEntityBluePrintPrinter) te).getPowerProvider();
     if(((TileEntityBluePrintPrinter) te).inventory[23] == null){
    	 
    	 
     }else{
     if(((TileEntityBluePrintPrinter) te).inventory[23].itemID == new ItemStack(MinechemItems.blueprint, 1 , 2).itemID)
    	 if(power.getEnergyStored() >= 400){
    	 power.useEnergy(400, 400, true);
            ((TileEntityBluePrintPrinter) te).setInventorySlotContents(23,
                    new ItemStack(MinechemItems.blueprint, 1 , 0));
        }}}
    }}
