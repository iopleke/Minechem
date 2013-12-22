package ljdp.minechem.common.coating;

import java.util.HashMap;

import ljdp.minechem.api.core.EnumMolecule;
import ljdp.minechem.common.ModMinechem;
import ljdp.minechem.common.PharmacologyEffect;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class EnchantmentCoated extends Enchantment {

	private EnumMolecule chemical;
	public static HashMap<EnumMolecule,EnchantmentCoated> chemLookup=new HashMap();
	protected EnchantmentCoated(EnumMolecule chem) {
		super(enchantmentNextId++, 0, EnumEnchantmentType.weapon);
		this.chemical=chem;
		this.setName(chem.descriptiveName()+" Coated");
		EnchantmentCoated.chemLookup.put(chem, this);
	}
	
	
	public void applyEffect(EntityLivingBase entity){
		PharmacologyEffect.triggerPlayerEffect(this.chemical, entity);
	}
	//Config file if conflicts appear
	public static int enchantmentNextId=150;
	
	
	/**
     * Returns the minimum level that the enchantment can have.
     */
    public int getMinLevel()
    {
        return 1;
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel()
    {
        return 10;
    }
    public boolean canApply(ItemStack par1ItemStack)
    {
        return false;
    }

    /**
     * This applies specifically to applying at the enchanting table. The other method {@link #canApply(ItemStack)}
     * applies for <i>all possible</i> enchantments.
     * @param stack
     * @return
     */
    public boolean canApplyAtEnchantingTable(ItemStack stack)
    {
        return false;
    }
    public String getTranslatedName(int par1)
    {
    	return this.chemical.descriptiveName()+" Coated";
    }

	public static void registerCoatings() {
		enchantmentNextId=ModMinechem.enchantmentStartId;
		for(EnumMolecule molecule:EnumMolecule.values()){
			if(PharmacologyEffect.givesEffect(molecule)){
				new EnchantmentCoated(molecule);
			}
		}
		
	}
}
