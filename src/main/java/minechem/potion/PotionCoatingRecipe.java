package minechem.potion;

import minechem.Settings;
import minechem.item.molecule.MoleculeEnum;
import minechem.item.molecule.MoleculeItem;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public class PotionCoatingRecipe implements IRecipe
{

    @Override
    public boolean matches(InventoryCrafting inv, World world)
    {
        if (Settings.SwordEffects)
        {
            for (int i = 0; i < inv.getSizeInventory(); i++)
            {
                ItemStack s = inv.getStackInSlot(i);
                if (s != null && s.getItem() instanceof ItemSword)
                {
                    for (int j = 0; j < inv.getSizeInventory(); j++)
                    {
                        ItemStack s2 = inv.getStackInSlot(j);
                        if (s2 != null && s2.getItem() instanceof MoleculeItem)
                        {
                            if (PharmacologyEffectRegistry.hasEffect(MoleculeEnum.getById(s2.getItemDamage())))
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
            ItemStack s = inv.getStackInSlot(i);
            if (s != null && s.getItem() instanceof ItemSword)
            {
                for (int j = 0; j < inv.getSizeInventory(); j++)
                {
                    ItemStack s2 = inv.getStackInSlot(j);
                    if (s2 != null && s2.getItem() instanceof MoleculeItem && PharmacologyEffectRegistry.hasEffect(MoleculeEnum.getById(s2.getItemDamage())))
                    {
                        NBTTagList l = s2.getEnchantmentTagList();
                        int level = 0;
                        if (l != null)
                        {
                            for (int k = 0; k < l.tagCount(); k++)
                            {
                                NBTTagCompound tag = l.getCompoundTagAt(k);
                                if (tag.getShort("id") == PotionEnchantmentCoated.chemLookup.get(MoleculeEnum.getById(s2.getItemDamage())).effectId)
                                {
                                    level = tag.getShort("lvl");
                                    ItemStack result = s.copy();
                                    result.getEnchantmentTagList().getCompoundTagAt(k).setInteger("lvl", level + 1);
                                }
                            }
                        }
                        ItemStack result = s.copy();
                        result.addEnchantment(PotionEnchantmentCoated.chemLookup.get(MoleculeEnum.getById(s2.getItemDamage())), 1);
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
        return null;
    }

}
