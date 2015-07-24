package minechem.radiation;

import minechem.item.bucket.MinechemBucketItem;
import minechem.item.element.ElementItem;
import minechem.item.molecule.MoleculeItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.eventhandler.Event;

public class RadiationDecayEvent extends Event
{

    private ItemStack before;
    private ItemStack[] after;
    private int damage;
    private long time;
    private IInventory inventory;
    private EntityPlayer player;
    private String afterStr;

    public RadiationDecayEvent(IInventory inventory, int damage, long time, ItemStack before, ItemStack[] after, EntityPlayer player)
    {
        this.inventory = inventory;
        this.after = after;
        this.before = before;
        this.damage = damage;
        this.time = time;
        this.player = player;

        // calculate afterStr
        if ((after == null) || (after.length == 0))
        {
            afterStr = "";
        } else
        {
            StringBuilder sb = new StringBuilder();
            for (ItemStack itemstack : after)
            {
                sb.append(RadiationHandler.getLongName(itemstack)).append(',');
            }
            // delete the ',' at the end of the string
            sb.deleteCharAt(sb.length() - 1);
            afterStr = sb.toString();
        }
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

    /**
     * Items in these stacks may be {@link ElementItem}, {@link MoleculeItem}, {@link MinechemBucketItem}.
     * For tubes with elements or buckets with elements, there's only one output.
     * For tubes with molecules or buckets with molecules, there may be more than one output.
     *
     * @return
     */
    public ItemStack[] getAfter()
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

    /**
     * Gets the decay output as a string in CSV(Comma-Separated Values).
     * <p>
     * This method uses the return value of {@link RadiationHandler#getLongName(ItemStack)} as each element of the CSV.
     *
     * @return
     * @deprecated This method is for forward compatibility.
     */
    @Deprecated
    public String getAfterAsString()
    {
        return afterStr;
    }
}
