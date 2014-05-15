package pixlepix.minechem.common.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import pixlepix.minechem.api.BaseParticle;
import pixlepix.minechem.common.ModMinechem;

public class PotentialReader extends Item {

    public PotentialReader(int par1) {
        super(par1);
	    setMaxStackSize(1);
	    setCreativeTab(ModMinechem.CREATIVE_TAB);
        setUnlocalizedName("potentialReader");
    }

    @Override
    public void registerIcons(IconRegister register) {
	    this.itemIcon = register.registerIcon("minechem:potentialReader");
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {

        if (entity instanceof BaseParticle) {
            BaseParticle particle = (BaseParticle) entity;
            player.addChatMessage("Potential: " + particle.potential);
            return true;
        }
        return false;
    }


}
