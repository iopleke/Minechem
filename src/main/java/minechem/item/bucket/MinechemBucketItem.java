package minechem.item.bucket;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minechem.gui.CreativeTabMinechem;
import minechem.reference.Textures;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class MinechemBucketItem extends ItemBucket
{
    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    private int contentColor;

    public MinechemBucketItem(Block block, int contentColor)
    {
        super(block);
        setCreativeTab(CreativeTabMinechem.CREATIVE_TAB_ITEMS);
        setContainerItem(Items.bucket);
        setUnlocalizedName("minechemBucket");
        this.contentColor = contentColor;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir)
    {
        icons = new IIcon[2];
        icons[0] = ir.registerIcon(Textures.IIcon.BUCKET_EMPTY);
        icons[1] = ir.registerIcon(Textures.IIcon.BUCKET_CONTENT);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIconFromDamageForRenderPass(int i, int j) {
        if (j > 0 && contentColor >= 0)
            return icons[1];
        else
            return icons[0];
    }

    // Return true to enable second pass for contentColor
    @SideOnly(Side.CLIENT)
    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getColorFromItemStack(ItemStack itemstack, int j) {
        if (j > 0 && contentColor >= 0)
            return contentColor;
        else
            return 0xffffff;
    }
}
