package minechem.potion;

import minechem.Settings;
import minechem.item.molecule.MoleculeEnum;
import minechem.item.molecule.MoleculeItem;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class PotionSpikingRecipe implements IRecipe
{
    ItemStack result = null;

    @Override
    public boolean matches(InventoryCrafting inv, World world)
    {
        if (Settings.FoodSpiking)
        {
            for (int i = 0; i < inv.getSizeInventory(); i++)
            {
                ItemStack s = inv.getStackInSlot(i);
                if (s != null && s.getItem() instanceof ItemFood)
                {
                    for (int j = 0; j < inv.getSizeInventory(); j++)
                    {
                        ItemStack s2 = inv.getStackInSlot(j);
                        if (s2 != null && s2.getItem() instanceof MoleculeItem)
                        {
                            if (PotionPharmacologyEffect.givesEffect(MoleculeEnum.getById(s2.getItemDamage())))
                            {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv)
    {
        for (int i = 0; i < inv.getSizeInventory(); i++)
        {
            ItemStack foodItem = inv.getStackInSlot(i);
            if (foodItem != null && foodItem.getItem() instanceof ItemFood)
            {
                for (int j = 0; j < inv.getSizeInventory(); j++)
                {
                    ItemStack moleculeStack = inv.getStackInSlot(j);
                    if (moleculeStack != null && moleculeStack.getItem() instanceof MoleculeItem && PotionPharmacologyEffect.givesEffect(MoleculeEnum.getById(moleculeStack.getItemDamage())))
                    {
                        ItemStack result = foodItem.copy();
                        result.stackSize = 1;
                        if (result.stackTagCompound == null) // empty NBT
                        {
                            NBTTagCompound tagCompound = new NBTTagCompound();
                            tagCompound.setBoolean("minechem.isPoisoned", true);
                            tagCompound.setIntArray("minechem.effectTypes", new int[] {MoleculeItem.getMolecule(moleculeStack).id()});
                            result.setTagCompound(tagCompound);
                        }
                        else if (result.stackTagCompound.hasKey("minechem.isPoisoned")) // has been poisoned before
                        {
                            int[] arrayOld = result.stackTagCompound.getIntArray("minechem.effectTypes");
                            int[] arrayNew = new int[arrayOld.length+1];
                            for (int index = 0; index < arrayOld.length; index++)
                            {
                                arrayNew[index] = arrayOld[index];
                            }
                            arrayNew[arrayOld.length] = MoleculeItem.getMolecule(moleculeStack).id();
                            result.stackTagCompound.setIntArray("minechem.effectTypes", arrayNew);
                        }
                        else // has NBT but no poison
                        {
                            result.stackTagCompound.setBoolean("minechem.isPoisoned", true);
                            result.stackTagCompound.setIntArray("minechem.effectTypes", new int[] {MoleculeItem.getMolecule(moleculeStack).id()});
                        }
                        this.result = result.copy();
                        return result;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public int getRecipeSize()
    {
        return 2;
    }

    @Override
    public ItemStack getRecipeOutput()
    {
        return result;
    }
}
