package minechem.potion;

import java.util.HashMap;

import minechem.item.molecule.MoleculeEnum;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class PotionEnchantmentCoated extends Enchantment
{

    private MoleculeEnum chemical;
    public static HashMap<MoleculeEnum, PotionEnchantmentCoated> chemLookup = new HashMap();

    protected PotionEnchantmentCoated(MoleculeEnum chem, int id)
    {
        super(id, 0, EnumEnchantmentType.weapon);
        this.chemical = chem;
		this.setName(chem.descriptiveName() + ".coated");
        PotionEnchantmentCoated.chemLookup.put(chem, this);
    }

    public void applyEffect(EntityLivingBase entity)
    {
        PotionPharmacologyEffect.triggerPlayerEffect(this.chemical, entity);
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
    public String getTranslatedName(int level)
    {
    	String enchantedName =this.chemical.descriptiveName() + " " + StatCollector.translateToLocal("enchantment.level." + level);
    	if(StatCollector.canTranslate("minechem.enchantment.coated"))
    	{
    		return StatCollector.translateToLocalFormatted("minechem.enchantment.coated", enchantedName);
    	}
    	else
    	{
            return enchantedName + " Coated";
    	}
    }

    public static void registerCoatings()
    {
        for (MoleculeEnum molecule : MoleculeEnum.molecules)
        {
            if (PotionPharmacologyEffect.givesEffect(molecule))
            {
                for (int i = 0; i < Enchantment.enchantmentsList.length; i++)
                {
                    if (Enchantment.enchantmentsList[i] == null)
                    {

                        new PotionEnchantmentCoated(molecule, i);
                        break;
                    }
                }
            }
        }

    }
}
