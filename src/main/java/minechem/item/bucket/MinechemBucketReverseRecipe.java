package minechem.item.bucket;

import minechem.MinechemItemsRegistration;
import minechem.item.MinechemChemicalType;
import minechem.item.element.ElementEnum;
import minechem.item.molecule.MoleculeEnum;
import minechem.radiation.RadiationEnum;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class MinechemBucketReverseRecipe implements IRecipe
{
    private MinechemChemicalType type;
    private ItemStack bucketStack;

    @Override
    public boolean matches(InventoryCrafting inv, World world)
    {
        int count = 0;
        type = null;
        for (int i = 0; i < inv.getSizeInventory(); i++)
        {
            ItemStack itemStack = inv.getStackInSlot(i);
            if (itemStack == null) continue;

            if (itemStack.getItem() instanceof MinechemBucketItem)
            {
                MinechemBucketItem bucket = (MinechemBucketItem) itemStack.getItem();
                if (type == null)
                {
                    type = bucket.chemical;
                    bucketStack = itemStack;
                }

                if (type == bucket.chemical) count++;
                else return false;
            }
            else if (itemStack.getItem() == Items.water_bucket)
            {
                if (type == null)
                {
                    type = MoleculeEnum.water;
                    bucketStack = itemStack;
                }

                if (type == MoleculeEnum.water) count++;
                else return false;
            }
            else
            {
                return false;
            }
        }
        return count == 1;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv)
    {
        if (type.radioactivity() == RadiationEnum.stable) return getRecipeOutput();

        ItemStack output = getRecipeOutput();
        output.stackTagCompound = bucketStack.stackTagCompound;
        return output;
    }

    @Override
    public int getRecipeSize()
    {
        return 1;
    }

    @Override
    public ItemStack getRecipeOutput()
    {
        if (type == MoleculeEnum.water) return new ItemStack(MinechemItemsRegistration.molecule, 8, MoleculeEnum.water.id());
        if (type instanceof ElementEnum) return new ItemStack(MinechemItemsRegistration.element, 8, ((ElementEnum) type).atomicNumber());
        else if (type instanceof MoleculeEnum) return new ItemStack(MinechemItemsRegistration.molecule, 8, ((MoleculeEnum) type).id());
        return null;
    }
}
