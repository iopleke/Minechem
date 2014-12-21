package minechem.fluid;

import minechem.item.bucket.MinechemBucketItem;
import minechem.radiation.RadiationEnum;
import minechem.radiation.RadiationFluidTileEntity;
import minechem.radiation.RadiationInfo;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class MinechemBucketDispenser implements IBehaviorDispenseItem{

	public static final MinechemBucketDispenser dispenser=new MinechemBucketDispenser();
	
	@Override
	public ItemStack dispense(IBlockSource blockSource, ItemStack itemstack) {
		EnumFacing enumfacing = BlockDispenser.func_149937_b(blockSource.getBlockMetadata());
		World world = blockSource.getWorld();
		int x = blockSource.getXInt() + enumfacing.getFrontOffsetX();
		int y = blockSource.getYInt() + enumfacing.getFrontOffsetY();
		int z = blockSource.getZInt() + enumfacing.getFrontOffsetZ();
		Block front=world.getBlock(x, y, z);
		
		if (!front.getMaterial().isSolid()){
			world.func_147480_a(x, y, z, true);
			world.setBlockToAir(x, y, z);
			
			MinechemBucketItem item=(MinechemBucketItem)itemstack.getItem();
			Block block=item.block;
			world.setBlock(x, y, z, block, 0, 3);
			
			TileEntity tile=world.getTileEntity(x, y, z);
			if (tile!=null&&item.chemical.radioactivity()!=RadiationEnum.stable){
                int dimensionID = itemstack.stackTagCompound.getInteger("dimensionID");
                long lastUpdate = itemstack.stackTagCompound.getLong("lastUpdate");
                long decayStart = itemstack.stackTagCompound.getLong("decayStart");
                RadiationInfo radioactivity = new RadiationInfo(itemstack, decayStart, lastUpdate, dimensionID, item.chemical.radioactivity());

                ((RadiationFluidTileEntity) tile).info = radioactivity;
			}
			
			itemstack.func_150996_a(Items.bucket);
			return itemstack;
		}
		return itemstack;
	}

}
