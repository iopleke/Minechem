package minechem.utils;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

/** Automatically gets the next available ID for a mod.
 * 
 * @author Calclavia */
public class IDManager
{
    /** Auto ID Management */
    private final int blockIDBase;
    private final int itemIDBase;

    private int nextBlockID;
    private int nextItemID;

    public IDManager(int blockIDBase, int itemIDBase)
    {
        nextBlockID = this.blockIDBase = blockIDBase;
        nextItemID = this.itemIDBase = itemIDBase;
    }

    public int getNextBlockID()
    {
        int id = nextBlockID;

        while (id > 255 && id < (Block.blocksList.length - 1))
        {
            Block block = Block.blocksList[id];
            if (block == null)
            {
                break;
            }
            id++;
        }
        nextBlockID = id + 1;
        return id;
    }

    public int getNextItemID()
    {
        int id = nextItemID;
        while (id > 255 && id < (Item.itemsList.length - 1))
        {
            Item item = Item.itemsList[id];
            if (item == null)
            {
                break;
            }
            id++;
        }
        nextItemID = id + 1;
        return id;
    }

}
