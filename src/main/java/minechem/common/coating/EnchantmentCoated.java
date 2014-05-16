package minechem.common.coating;

import java.util.HashMap;

import minechem.api.core.EnumMolecule;
import minechem.common.PharmacologyEffect;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class EnchantmentCoated extends Enchantment
{

    private EnumMolecule chemical;
    public static HashMap<EnumMolecule, EnchantmentCoated> chemLookup = new HashMap();

    protected EnchantmentCoated(EnumMolecule chem, int id)
    {
        super(id, 0, EnumEnchantmentType.weapon);
        this.chemical = chem;
        this.setName(chem.descriptiveName() + " Coated");
        EnchantmentCoated.chemLookup.put(chem, this);
    }

    public void applyEffect(EntityLivingBase entity)
    {
        PharmacologyEffect.triggerPlayerEffect(this.chemical, entity);
    }

    /** Returns the minimum level that the enchantment can have. */
    @Override
    public int getMinLevel()
    {
        return 1;
    }

    /** Returns the maximum level that the enchantment can have. */
    @Override
    public int getMaxLevel()
    {
        return 10;
    }

    @Override
    public boolean canApply(ItemStack par1ItemStack)
    {
        return false;
    }

    /** This applies specifically to applying at the enchanting table. The other method {@link #canApply(ItemStack)} applies for <i>all possible</i> enchantments.
     * 
     * @param stack
     * @return */
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack)
    {
        return false;
    }

    @Override
    public String getTranslatedName(int par1)
    {
        return this.chemical.descriptiveName() + " Coated";
    }

    public static void registerCoatings()
    {
        for (EnumMolecule molecule : EnumMolecule.values())
        {
            if (PharmacologyEffect.givesEffect(molecule))
            {
                for (int i = 0; i < Enchantment.enchantmentsList.length; i++)
                {
                    if (Enchantment.enchantmentsList[i] == null)
                    {

                        new EnchantmentCoated(molecule, i);
                        break;
                    }
                }
            }
        }

    }
}
