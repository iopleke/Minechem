package minechem.common.assembler;

import minechem.common.utils.CoordTuple;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;

public class ItemAssembler extends Item {

    public ItemAssembler(int par1) {
        super(par1);
    }

    public static NBTTagCompound createData() {
        NBTTagCompound result = new NBTTagCompound();

        result.setString("Type", "Cube");
        result.setInteger("Size", 1);
        result.setInteger("Block", 1);
        return result;

    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (stack.stackTagCompound == null) {
            return false;
        }
        if (!(stack.stackTagCompound.hasKey("Type") && stack.stackTagCompound.hasKey("Size") && stack.stackTagCompound.hasKey("Block"))) {
            return false;
        }
        IShapeType type = this.getType(stack.stackTagCompound.getString("Type"));
        ArrayList<CoordTuple> places = type.getCoords(new CoordTuple(x, y, z), stack.stackTagCompound.getInteger("Size"));
        Iterator iter = places.iterator();
        while (iter.hasNext()) {
            CoordTuple tuple = (CoordTuple) iter.next();
            if (player.inventory.hasItem(stack.stackTagCompound.getInteger("Block"))) {
                for (ItemStack block : player.inventory.mainInventory) {
                    if ((block.getItem() instanceof ItemBlock) && block.itemID == stack.stackTagCompound.getInteger("Block")) {
                        ((ItemBlock) block.getItem()).placeBlockAt(stack, player, world, x, y, z, 0, hitX, hitY, hitZ, block.getItem().getMetadata(block.getItemDamage()));
                        player.inventory.consumeInventoryItem(stack.stackTagCompound.getInteger("Block"));
                    }
                }
            }


        }
        return false;
    }

    public IShapeType getType(String s) {
        return null;

    }


}
