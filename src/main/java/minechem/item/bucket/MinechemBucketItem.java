package minechem.item.bucket;

import java.util.List;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minechem.fluid.FluidChemical;
import minechem.fluid.FluidElement;
import minechem.gui.CreativeTabMinechem;
import minechem.item.molecule.MoleculeEnum;
import minechem.reference.Textures;
import minechem.utils.Constants;
import minechem.utils.MinechemHelper;
import minechem.utils.MinechemUtil;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class MinechemBucketItem extends ItemBucket
{
    @SideOnly(Side.CLIENT)
    public IIcon[] icons;

    public final Fluid fluid;

    public MinechemBucketItem(Block block, Fluid fluid)
    {
        super(block);
        setCreativeTab(CreativeTabMinechem.CREATIVE_TAB_BUCKETS);
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
    
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool){
		list.add(Constants.TEXT_MODIFIER + "9" + getFillLocalizedName());
		list.add(Constants.TEXT_MODIFIER + "9" + MinechemUtil.subscriptNumbers(getFormula(itemstack)));
	}
	
	private String getFillLocalizedName(){
		if (fluid instanceof FluidElement){
			return MinechemHelper.getLocalString(((FluidElement) fluid).element.getUnlocalizedName());
		}else if (fluid instanceof FluidChemical){
			return MinechemHelper.getLocalString(((FluidChemical) fluid).molecule.getUnlocalizedName());
		}
		return fluid.getLocalizedName(null);
	}
	
	private String getFormula(ItemStack itemstack) {
		if (fluid instanceof FluidElement){
			return ((FluidElement) fluid).element.name();
		}else if (fluid instanceof FluidChemical){
			return ((FluidChemical) fluid).molecule.getFormula();
		}else if (fluid==FluidRegistry.WATER){
			return MoleculeEnum.water.getFormula();
		}
		
		return "";
	}
}
