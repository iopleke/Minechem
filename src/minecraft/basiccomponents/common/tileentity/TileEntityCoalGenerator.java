package basiccomponents.common.tileentity;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import universalelectricity.core.block.IConductor;
import universalelectricity.core.electricity.ElectricityNetworkHelper;
import universalelectricity.core.electricity.IElectricityNetwork;
import universalelectricity.core.vector.Vector3;
import universalelectricity.core.vector.VectorHelper;
import universalelectricity.prefab.network.IPacketReceiver;
import universalelectricity.prefab.network.PacketManager;
import universalelectricity.prefab.tile.TileEntityElectrical;
import basiccomponents.common.BasicComponents;
import basiccomponents.common.block.BlockBasicMachine;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class TileEntityCoalGenerator extends TileEntityElectrical implements IInventory, ISidedInventory, IPacketReceiver
{
	/**
	 * Maximum amount of energy needed to generate electricity
	 */
	public static final int MAX_GENERATE_WATTS = 10000;

	/**
	 * Amount of heat the coal generator needs before generating electricity.
	 */
	public static final int MIN_GENERATE_WATTS = 100;

	private static final float BASE_ACCELERATION = 0.3f;

	/**
	 * Per second
	 */
	public double prevGenerateWatts, generateWatts = 0;

	public IConductor connectedElectricUnit = null;
	/**
	 * The number of ticks that a fresh copy of the currently-burning item would keep the furnace
	 * burning for
	 */
	public int itemCookTime = 0;
	/**
	 * The ItemStacks that hold the items currently being used in the battery box
	 */
	private ItemStack[] containingItems = new ItemStack[1];

	public final Set<EntityPlayer> playersUsing = new HashSet<EntityPlayer>();

	@Override
	public boolean canConnect(ForgeDirection direction)
	{
		return direction == ForgeDirection.getOrientation(this.getBlockMetadata() - BlockBasicMachine.COAL_GENERATOR_METADATA + 2);
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();

		if (!this.worldObj.isRemote)
		{
			this.prevGenerateWatts = this.generateWatts;

			// Check nearby blocks and see if the conductor is full. If so, then it is connected
			ForgeDirection outputDirection = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockBasicMachine.COAL_GENERATOR_METADATA + 2);
			TileEntity outputTile = VectorHelper.getConnectorFromSide(this.worldObj, new Vector3(this.xCoord, this.yCoord, this.zCoord), outputDirection);

			IElectricityNetwork network = ElectricityNetworkHelper.getNetworkFromTileEntity(outputTile, outputDirection);

			if (network != null)
			{
				if (network.getRequest().getWatts() > 0)
				{
					this.connectedElectricUnit = (IConductor) outputTile;
				}
				else
				{
					this.connectedElectricUnit = null;
				}
			}
			else
			{
				this.connectedElectricUnit = null;
			}

			if (!this.isDisabled())
			{
				if (this.itemCookTime > 0)
				{
					this.itemCookTime--;

					if (this.connectedElectricUnit != null)
					{
						this.generateWatts = Math.min(this.generateWatts + Math.min((this.generateWatts * 0.005 + BASE_ACCELERATION), 5), TileEntityCoalGenerator.MAX_GENERATE_WATTS);
					}
				}

				if (this.containingItems[0] != null && this.connectedElectricUnit != null)
				{
					if (this.containingItems[0].getItem().itemID == Item.coal.itemID)
					{
						if (this.itemCookTime <= 0)
						{
							this.itemCookTime = 320;
							this.decrStackSize(0, 1);
						}
					}
				}

				if (this.connectedElectricUnit == null || this.itemCookTime <= 0)
				{
					this.generateWatts = Math.max(this.generateWatts - 8, 0);
				}

				if (this.connectedElectricUnit != null)
				{
					if (this.generateWatts > MIN_GENERATE_WATTS)
					{
						this.connectedElectricUnit.getNetwork().startProducing(this, (this.generateWatts / this.getVoltage()) / 20, this.getVoltage());
					}
					else
					{
						this.connectedElectricUnit.getNetwork().stopProducing(this);
					}
				}
			}

			if (this.ticks % 3 == 0)
			{
				for (EntityPlayer player : this.playersUsing)
				{
					PacketDispatcher.sendPacketToPlayer(getDescriptionPacket(), (Player) player);
				}
			}

			if (this.prevGenerateWatts <= 0 && this.generateWatts > 0 || this.prevGenerateWatts > 0 && this.generateWatts <= 0)
			{
				PacketManager.sendPacketToClients(getDescriptionPacket(), this.worldObj);
			}
		}
	}

	@Override
	public Packet getDescriptionPacket()
	{
		return PacketManager.getPacket(BasicComponents.CHANNEL, this, this.generateWatts, this.itemCookTime, this.disabledTicks);
	}

	@Override
	public void handlePacketData(INetworkManager network, int type, Packet250CustomPayload packet, EntityPlayer player, ByteArrayDataInput dataStream)
	{
		try
		{
			if (this.worldObj.isRemote)
			{
				this.generateWatts = dataStream.readDouble();
				this.itemCookTime = dataStream.readInt();
				this.disabledTicks = dataStream.readInt();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void openChest()
	{
	}

	@Override
	public void closeChest()
	{
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		this.itemCookTime = par1NBTTagCompound.getInteger("itemCookTime");
		this.generateWatts = par1NBTTagCompound.getDouble("generateRate");
		NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
		this.containingItems = new ItemStack[this.getSizeInventory()];

		for (int var3 = 0; var3 < var2.tagCount(); ++var3)
		{
			NBTTagCompound var4 = (NBTTagCompound) var2.tagAt(var3);
			byte var5 = var4.getByte("Slot");

			if (var5 >= 0 && var5 < this.containingItems.length)
			{
				this.containingItems[var5] = ItemStack.loadItemStackFromNBT(var4);
			}
		}
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("itemCookTime", this.itemCookTime);
		par1NBTTagCompound.setDouble("generateRate", this.generateWatts);
		NBTTagList var2 = new NBTTagList();

		for (int var3 = 0; var3 < this.containingItems.length; ++var3)
		{
			if (this.containingItems[var3] != null)
			{
				NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("Slot", (byte) var3);
				this.containingItems[var3].writeToNBT(var4);
				var2.appendTag(var4);
			}
		}

		par1NBTTagCompound.setTag("Items", var2);
	}

	@Override
	public int getSizeInventory()
	{
		return this.containingItems.length;
	}

	@Override
	public ItemStack getStackInSlot(int par1)
	{
		return this.containingItems[par1];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2)
	{
		if (this.containingItems[par1] != null)
		{
			ItemStack var3;

			if (this.containingItems[par1].stackSize <= par2)
			{
				var3 = this.containingItems[par1];
				this.containingItems[par1] = null;
				return var3;
			}
			else
			{
				var3 = this.containingItems[par1].splitStack(par2);

				if (this.containingItems[par1].stackSize == 0)
				{
					this.containingItems[par1] = null;
				}

				return var3;
			}
		}
		else
		{
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int par1)
	{
		if (this.containingItems[par1] != null)
		{
			ItemStack var2 = this.containingItems[par1];
			this.containingItems[par1] = null;
			return var2;
		}
		else
		{
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		this.containingItems[par1] = par2ItemStack;

		if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
		{
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public String getInvName()
	{
		return LanguageRegistry.instance().getStringLocalization("tile." + BasicComponents.TEXTURE_NAME_PREFIX + "bcMachine.0.name");
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
	{
		return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public boolean isInvNameLocalized()
	{
		return true;
	}

	@Override
	public boolean isStackValidForSlot(int slotID, ItemStack itemstack)
	{
		return itemstack.itemID == Item.coal.itemID;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1)
	{
		return new int[] { 0 };
	}

	@Override
	public boolean canInsertItem(int slotID, ItemStack itemstack, int j)
	{
		return this.isStackValidForSlot(slotID, itemstack);
	}

	@Override
	public boolean canExtractItem(int slotID, ItemStack itemstack, int j)
	{
		return slotID == 0;
	}
}