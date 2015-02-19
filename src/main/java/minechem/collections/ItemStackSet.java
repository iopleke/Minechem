package minechem.collections;

import gnu.trove.set.hash.TCustomHashSet;
import gnu.trove.strategy.HashingStrategy;
import minechem.collections.strategy.FlatItemStackHashingStrategy;
import minechem.collections.strategy.ItemStackHashingStrategy;
import net.minecraft.item.ItemStack;

public class ItemStackSet extends TCustomHashSet<ItemStack>
{
    private static final HashingStrategy HASHING_STRATEGY = new ItemStackHashingStrategy();
    private static final HashingStrategy FLAT_HASHING_STRATEGY = new FlatItemStackHashingStrategy();

    public ItemStackSet()
    {
        this(false);
    }

    public ItemStackSet(boolean flat)
    {
        super(flat ? FLAT_HASHING_STRATEGY : HASHING_STRATEGY);
    }

    public ItemStackSet copy()
    {
        ItemStackSet copy = new ItemStackSet();
        copy.strategy = this.strategy;
        copy.addAll(this);
        return copy;
    }
}
