package pixlepix.minechem.common.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import pixlepix.minechem.api.core.IRadiationShield;
import pixlepix.minechem.common.ModMinechem;
import pixlepix.minechem.common.utils.ConstantValue;

import java.util.List;

public class ItemArmorRadiationShield extends ItemArmor implements IRadiationShield {

    private float radiationShieldFactor;
    private String textureFile;

    public ItemArmorRadiationShield(int id, int part, float radiationShieldFactor, String texture) {
        super(id, EnumArmorMaterial.CHAIN, 2, part);
        this.radiationShieldFactor = radiationShieldFactor;
        setUnlocalizedName("minechem.itemArmorRadiationShield");
        setCreativeTab(ModMinechem.CREATIVE_TAB);
        textureFile = texture;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister ir) {
        itemIcon = ir.registerIcon(textureFile);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean par4) {
        int percentile = (int) (radiationShieldFactor * 100);
        String info = String.format("%d%% Radiation Shielding", percentile);
        list.add(info);
    }

    @Override
    public float getRadiationReductionFactor(int baseDamage, ItemStack itemstack, EntityPlayer player) {
        itemstack.damageItem(baseDamage / 4, player);
        return radiationShieldFactor;
    }

    @Override

    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        return ConstantValue.HAZMAT_TEX;
    }

}
