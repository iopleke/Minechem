package minechem.collections;

import gnu.trove.map.hash.TCustomHashMap;
import gnu.trove.strategy.HashingStrategy;
import minechem.collections.strategy.FlatItemStackHashingStrategy;
import minechem.collections.strategy.ItemStackHashingStrategy;
import net.minecraft.item.ItemStack;

/**
 * A HashMap&lt;ItemStack, V&gt; that implements a proper hashing function on ItemStacks, allowing them to be stored and queried in a HashMap Example usage: ItemStackMap&lt;ItemStack>&gt; stackMap =
 * new ItemStackMap&lt;ItemStack&gt; stackMap.put(new ItemStack(Blocks.cobblestone), new ItemStack(Blocks.stone)); stackMap.contains(new ItemStack(Blocks.cobblestone)); (returns true)
 *
 * @param <V>
 */
public class ItemStackMap<V> extends TCustomHashMap<ItemStack, V>
{
    private static final HashingStrategy HASHING_STRATEGY = new ItemStackHashingStrategy();
    private static final HashingStrategy FLAT_HASHING_STRATEGY = new FlatItemStackHashingStrategy();

    public ItemStackMap()
    {
        this(false);
    }

    public ItemStackMap(boolean flat)
    {
        super(flat ? FLAT_HASHING_STRATEGY : HASHING_STRATEGY);
    }
}
