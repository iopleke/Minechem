package minechem.item.bucket;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minechem.gui.CreativeTabMinechem;
import minechem.reference.Textures;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;

public class MinechemBucketItem extends ItemBucket
{
    @SideOnly(Side.CLIENT)
    public IIcon[] icons;

    public final Fluid fluid;

    public MinechemBucketItem(Block block, Fluid fluid)
    {
        super(block);
        setCreativeTab(CreativeTabMinechem.CREATIVE_TAB_ITEMS);
        setContainerItem(Items.bucket);
        setUnlocalizedName("minechemBucket");
        this.fluid = fluid;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir)
    {
        icons = new IIcon[2];
        icons[0] = ir.registerIcon(Textures.IIcon.BUCKET_EMPTY);
        icons[1] = ir.registerIcon(Textures.IIcon.BUCKET_CONTENT);
    }
}
