package ljdp.minechem.common.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import ljdp.minechem.common.ModMinechem;
import ljdp.minechem.common.entity.EntityTableOfElements;
import ljdp.minechem.common.utils.ConstantValue;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHangingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.world.World;

public class ItemHangableTableOfElements extends Item{

	private final Class hangingEntityClass;
	
	public ItemHangableTableOfElements(int par1, Class par2) {
		super(par1);
		this.hangingEntityClass = par2;
		setCreativeTab(ModMinechem.minechemTab);
		setUnlocalizedName("minechem.itemBlueprint");
	}
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (par7 == 0)
        {
            return false;
        }
        else if (par7 == 1)
        {
            return false;
        }
        else
        {
            int i1 = Direction.facingToDirection[par7];
            EntityHanging entityhanging = this.createHangingEntity(par3World, par4, par5, par6, i1);

            if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack))
            {
                return false;
            }
            else
            {
                if (entityhanging != null && entityhanging.onValidSurface())
                {
                    if (!par3World.isRemote)
                    {
                        par3World.spawnEntityInWorld(entityhanging);
                    }

                    --par1ItemStack.stackSize;
                }

                return true;
            }
        }
    }

    /**
     * Create the hanging entity associated to this item.
     */
    private EntityHanging createHangingEntity(World par1World, int par2, int par3, int par4, int par5)
    {
        return (EntityHanging)(this.hangingEntityClass == EntityTableOfElements.class ? new EntityTableOfElements(par1World, par2, par3, par4, par5) : (this.hangingEntityClass == EntityItemFrame.class ? new EntityItemFrame(par1World, par2, par3, par4, par5) : null));
    }
	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister ir) {
        itemIcon = ir.registerIcon(ConstantValue.table_HEX);
    }

}
