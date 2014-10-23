package minechem.radiation;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class RadiationFluidTileEntity extends TileEntity {
	
	public RadiationInfo info;
	
	public RadiationFluidTileEntity() {
		this(null);
	}
	
	public RadiationFluidTileEntity(RadiationInfo info) {
		this.info=info;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag){
		super.readFromNBT(tag);
		if (tag.getBoolean("isNull")){
			info=null;
		}else{
			info=new RadiationInfo(ItemStack.loadItemStackFromNBT(tag.getCompoundTag("item")), tag.getLong("decayStart"), tag.getLong("lastUpdate"), tag.getInteger("dimensionID"), RadiationEnum.values()[tag.getInteger("radioactivity")]);
			info.radiationDamage=tag.getInteger("radiationDamage");
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag){
		super.writeToNBT(tag);
		tag.setBoolean("isNull", info==null);
		
		if (info!=null){
			tag.setLong("lastUpdate", info.lastDecayUpdate);
			tag.setLong("decayStart", info.decayStarted);
			tag.setInteger("dimensionID", info.dimensionID);
			tag.setInteger("radiationDamage", info.radiationDamage);
			tag.setInteger("radioactivity", info.radioactivity.ordinal());
			NBTTagCompound item=new NBTTagCompound();
			info.itemstack.writeToNBT(item);
			tag.setTag("item", item);
		}
	}
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound tagCompound = new NBTTagCompound();
		this.writeToNBT(tagCompound);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, tagCompound);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.func_148857_g());
	}
}
