package minechem.api;

import java.util.List;
import net.minecraft.item.ItemStack;

/**
 * To be implemented on any container that should stop elements or molecules from decaying
 *
 * @author way2muchnoise
 */
public interface INoDecay
{
    /**
     * @return A list of the itemStacks that will not decay
     */
    public List<ItemStack> getStorageInventory();

    /**
     * @return A list of itemStacks that will decay
     */
    public List<ItemStack> getPlayerInventory();
}
