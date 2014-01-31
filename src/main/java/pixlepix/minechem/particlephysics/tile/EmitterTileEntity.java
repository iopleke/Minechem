package pixlepix.minechem.particlephysics.tile;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import pixlepix.minechem.api.core.EnumElement;
import pixlepix.minechem.api.core.EnumMolecule;
import pixlepix.minechem.common.MinechemItems;
import pixlepix.minechem.common.items.ItemMolecule;
import pixlepix.minechem.particlephysics.api.BaseParticle;
import pixlepix.minechem.particlephysics.blocks.InfiniteEmitter;
import pixlepix.minechem.particlephysics.entity.*;

public class EmitterTileEntity extends TileEntity implements IInventory {

    public int interval = 40;
    public ItemStack[] inventory = new ItemStack[7];

    public int fuelType;

    public int fuelMeta;
    public int fuelStored = 0;

	public int getMaxFuelFromItems(){
		if(this.inventory[0]!=null && this.inventory[0].getItem() instanceof ItemMolecule){
			return 100*ItemMolecule.getMolecule(this.inventory[0]).getSize();
		}else{
			return 100;
		}
	}

    public void updateEntity() {
        if (!worldObj.isRemote && worldObj.getTotalWorldTime() % ((20 * interval) + 20) == 0) {
            if (fuelStored < 1) {
                if (this.inventory != null) {
                    if (this.inventory[0] != null && isValidFuel(this.inventory[0].itemID)) {

	                    this.fuelStored=this.getMaxFuelFromItems();

                        this.fuelType = this.inventory[0].itemID;
                        this.fuelMeta = this.inventory[0].getItemDamage();
                        this.decrStackSize(0, 1);
                        if (this.inventory[0] == null) {
                            for (int i = 1; i < getSizeInventory(); i++) {
                                ItemStack item = getStackInSlot(i);
                                if (item != null && this.isValidFuel(item.itemID)) {
                                    this.setInventorySlotContents(0, item);
                                    this.setInventorySlotContents(i, null);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            if (fuelStored > 0) {
	            if (!(Block.blocksList[worldObj.getBlockId(xCoord, yCoord, zCoord)] instanceof InfiniteEmitter && getStackInSlot(0) != null && getStackInSlot(0).stackSize >= 63)) {
		            this.fuelStored--;
                }
                ForgeDirection[] outputDirections = {ForgeDirection.SOUTH, ForgeDirection.NORTH, ForgeDirection.WEST, ForgeDirection.EAST};
                for (ForgeDirection dir : outputDirections) {

                    BaseParticle particle = getParticleFromFuel(fuelType, fuelMeta);
                    if (particle == null) {
                        return;
                    }
                    particle.addVelocity(dir.offsetX, dir.offsetY, dir.offsetZ);
                    particle.setPosition(xCoord + dir.offsetX + 0.375, yCoord + dir.offsetY + 0.375, zCoord + dir.offsetZ + 0.375);
                    worldObj.spawnEntityInWorld(particle);

                }
            }
        }
    }

    public BaseParticle getParticleFromFuel(int fuel, int meta) {
        if(fuel==MinechemItems.element.itemID){
            if(meta == EnumElement.C.ordinal()){
                return new CoalParticle(worldObj);
            }

            if(meta == EnumElement.H.ordinal()){
                return new CharcoalParticle(worldObj);
            }

            if(meta==EnumElement.O.ordinal()){
                return new LeafParticle(worldObj);
            }

            if(meta == EnumElement.Si.ordinal()){
                return new GlassParticle(worldObj);
            }

            if(meta == EnumElement.Pu.ordinal()){
                return new BlazepowderParticle(worldObj);
            }

        }

        if(fuel==MinechemItems.molecule.itemID){
            if(meta == EnumMolecule.cellulose.ordinal()){
                return new PaperParticle(worldObj);
            }

            if(meta == EnumMolecule.kaolinite.ordinal()){
                return new ClayParticle(worldObj);
            }

            if(meta == EnumMolecule.nod.ordinal()){
                return new SeedParticle(worldObj);
            }

            if(meta == EnumMolecule.siliconDioxide.ordinal()){
                return new SandParticle(worldObj);
            }

            if(meta == EnumMolecule.potassiumNitrate.ordinal()){
                return new GunpowderParticle(worldObj);
            }


        }
        return null;
    }
    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.fuelStored = nbt.getInteger("Fuel");

        this.fuelType = nbt.getInteger("FuelType");
        this.interval = nbt.getInteger("Interval");
        this.fuelMeta = nbt.getInteger("FuelMeta");
        NBTTagList tagList = nbt.getTagList("Inventory");
        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound compound = (NBTTagCompound) tagList.tagAt(i);
            int slot = compound.getInteger("Slot");
            inventory[slot] = ItemStack.loadItemStackFromNBT(compound);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("Interval", this.interval);
        nbt.setInteger("Fuel", this.fuelStored);
        nbt.setInteger("FuelType", this.fuelType);

        nbt.setInteger("FuelMeta", this.fuelMeta);
        NBTTagCompound inv = new NBTTagCompound();
        NBTTagList tagList = new NBTTagList();
        for (int i = 0; i < inventory.length; i++) {
            ItemStack item = getStackInSlot(i);
            if (item != null) {
                NBTTagCompound compound = new NBTTagCompound();
                item.writeToNBT(compound);
                compound.setInteger("Slot", i);
                tagList.appendTag(compound);
            }
        }
        nbt.setTag("Inventory", tagList);
    }

    @Override
    public int getSizeInventory() {
        return 7;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return this.inventory[i];
    }

    @Override
    public ItemStack decrStackSize(int i, int j) {
        ItemStack itemstack = getStackInSlot(i);

        if (itemstack != null) {
            if (itemstack.stackSize <= j) {
                setInventorySlotContents(i, null);
            } else {
                itemstack = itemstack.splitStack(j);
            }
        }

        onInventoryChanged();
        return itemstack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        ItemStack item = getStackInSlot(i);
        setInventorySlotContents(i, null);
        return item;
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemStack) {
        this.inventory[i] = itemStack;
        this.onInventoryChanged();
    }

    @Override
    public String getInvName() {
        return "Emitter";
    }

    @Override
    public boolean isInvNameLocalized() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return entityplayer.getDistanceSq(xCoord, yCoord, zCoord) < 64;
    }

    @Override
    public void openChest() {

    }

    @Override
    public void closeChest() {

    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return isValidFuel(itemstack.itemID);
    }

    public boolean isValidFuel(int itemstack) {
        return (itemstack == MinechemItems.molecule.itemID || itemstack == MinechemItems.element.itemID);
    }

    public void receiveButton(byte type, byte value) {
        switch (type) {
            case 0:
                switch (value) {
                    case 0:
                        this.fuelStored = 0;
                }
            case 1:
                this.interval = value;
        }
    }

}
