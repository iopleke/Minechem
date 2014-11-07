package minechem.radiation;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.eventhandler.Event;

public class RadiationDecayEvent extends Event{

	private ItemStack before;
	private ItemStack after;
	private int damage;
	private long time;
	private IInventory inventory;
	private EntityPlayer player;
	
	public RadiationDecayEvent(IInventory inventory, int damage, long time, ItemStack before, ItemStack after, EntityPlayer player)
	{
		this.inventory=inventory;
		this.after=after;
		this.before=before;
		this.damage=damage;
		this.time=time;
		this.player=player;
	}
	
	public EntityPlayer getPlayer()
	{
		return player;
	}
	
	public IInventory getInventory()
	{
		return inventory;
	}
	
	public ItemStack getBefore()
	{
		return before;
	}
	
	public ItemStack getAfter()
	{
		return after;
	}
	
	public int getDamage()
	{
		return damage;
	}
	
	public long getTime()
	{
		return time;
	}
}
