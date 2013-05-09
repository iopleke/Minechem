package appeng.api.me.items;

import net.minecraft.item.ItemStack;

/**
 * Any item which implements this can be treated as an IMEInventory via Util.getCell / Util.isCell
 * It automatically handles the internals and NBT data, which is both nice, and bad for you!
 */
public interface IStorageCell
{
    // If this returns something where N % 8 != 0 Then you will be shot on sight, or your car will explode, something like that least...
    int getBytes( ItemStack is );

	int BytePerType( ItemStack is );
}
