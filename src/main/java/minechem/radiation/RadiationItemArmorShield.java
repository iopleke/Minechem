package minechem.radiation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import minechem.Minechem;
import minechem.api.IRadiationShield;
import minechem.utils.Reference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class RadiationItemArmorShield extends ItemArmor implements IRadiationShield
{

	private float radiationShieldFactor;
	private String textureFile;

	public RadiationItemArmorShield(int id, int part, float radiationShieldFactor, String texture)
	{
		super(ArmorMaterial.CHAIN, 2, part);
		this.radiationShieldFactor = radiationShieldFactor;
		this.setUnlocalizedName("itemArmorRadiationShield");
		setCreativeTab(Minechem.CREATIVE_TAB_ITEMS);
		textureFile = texture;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		itemIcon = ir.registerIcon(textureFile);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean par4)
	{
		int percentile = (int) (radiationShieldFactor * 100);
		String info = String.format("%d%% Radiation Shielding", percentile);
		list.add(info);
	}

	@Override
	public float getRadiationReductionFactor(int baseDamage, ItemStack itemstack, EntityPlayer player)
	{
		itemstack.damageItem(baseDamage / 4, player);
		return radiationShieldFactor;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
		return Reference.HAZMAT_TEX;
	}

}
