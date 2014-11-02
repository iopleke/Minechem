package minechem.item.bucket;

import java.util.List;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minechem.fluid.FluidChemical;
import minechem.fluid.FluidElement;
import minechem.gui.CreativeTabMinechem;
import minechem.item.MinechemChemicalType;
import minechem.item.molecule.MoleculeEnum;
import minechem.radiation.RadiationFluidTileEntity;
import minechem.radiation.RadiationInfo;
import minechem.reference.Textures;
import minechem.utils.Constants;
import minechem.utils.MinechemHelper;
import minechem.utils.MinechemUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class MinechemBucketItem extends ItemBucket
{

	@SideOnly(Side.CLIENT)
    public IIcon[] icons;

    public final Fluid fluid;
    public final Block blcok;
    public final MinechemChemicalType chemical;

    public MinechemBucketItem(Block block, Fluid fluid,MinechemChemicalType chemical)
    {
        super(block);
        setCreativeTab(CreativeTabMinechem.CREATIVE_TAB_BUCKETS);
        setContainerItem(Items.bucket);
        setUnlocalizedName("minechemBucket");
        this.fluid = fluid;
        this.blcok=block;
        this.chemical=chemical;
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
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir)
    {
        icons = new IIcon[2];
        icons[0] = ir.registerIcon(Textures.IIcon.BUCKET_EMPTY);
        icons[1] = ir.registerIcon(Textures.IIcon.BUCKET_CONTENT);
    }
	
	public boolean placeLiquid(World world,ItemStack itemstack, int x, int y, int z) {
		Material material = world.getBlock(x, y, z).getMaterial();
		boolean flag = !material.isSolid();
		
		if (!world.isAirBlock(x, y, z) && !flag) {
			return false;
		} else {
			if (!world.isRemote && flag && !material.isLiquid()) {
				world.func_147480_a(x, y, z, true);
			}
			
			world.setBlock(x, y, z, this.blcok, 0, 3);
			
			TileEntity tile=world.getTileEntity(x, y, z);
			
			int dimensionID = itemstack.stackTagCompound.getInteger("dimensionID");
			long lastUpdate = itemstack.stackTagCompound.getLong("lastUpdate");
			long decayStart = itemstack.stackTagCompound.getLong("decayStart");
			RadiationInfo radioactivity = new RadiationInfo(itemstack, decayStart, lastUpdate, dimensionID, chemical.radioactivity());
			
			if (radioactivity.isRadioactive() && tile instanceof RadiationFluidTileEntity)
			{
				((RadiationFluidTileEntity) tile).info = radioactivity;
			}
			return true;
		}
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, false);
		
		if (movingobjectposition == null) {
			return itemStack;
		} else {
			if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
				int x = movingobjectposition.blockX;
				int y = movingobjectposition.blockY;
				int z = movingobjectposition.blockZ;
				
				if (!world.canMineBlock(player, x, y, z)) {
					return itemStack;
				}
				
				switch(movingobjectposition.sideHit){
					case 0:
						y--;
						break;
						
					case 1:
						y++;
						break;
						
					case 2:
						z--;
						break;
						
					case 3:
						z++;
						break;
						
					case 4:
						x--;
						break;
						
					case 5:
						x++;
						break;
				}
				
				if (!player.canPlayerEdit(x, y, z, movingobjectposition.sideHit, itemStack)) {
					return itemStack;
				}
				
				if (this.placeLiquid(world, itemStack, x, y, z) && !player.capabilities.isCreativeMode) {
					return new ItemStack(Items.bucket);
				}
			}
			
			return itemStack;
		}
	}
}
