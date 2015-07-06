package minechem.fluid;

import minechem.MinechemItemsRegistration;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemStack;

public class FluidChemicalDispenser implements IBehaviorDispenseItem
{

    public static final FluidChemicalDispenser INSTANCE = new FluidChemicalDispenser();

    public static void init()
    {
        BlockDispenser.dispenseBehaviorRegistry.putObject(MinechemItemsRegistration.element, INSTANCE);
        BlockDispenser.dispenseBehaviorRegistry.putObject(MinechemItemsRegistration.molecule, INSTANCE);
    }

    @Override
    public ItemStack dispense(IBlockSource blockSource, ItemStack itemStack)
    {
        return FluidDispenseHelper.dispenseOnDispenser(blockSource, itemStack);
    }
}
