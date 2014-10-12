package minechem.fluid;

import minechem.MinechemItemsRegistration;
import minechem.item.MinechemChemicalType;
import minechem.item.element.ElementEnum;
import minechem.item.element.ElementItem;
import minechem.item.molecule.MoleculeEnum;
import minechem.item.molecule.MoleculeItem;
import minechem.tick.ChemicalFluidReactionHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidBlock;

import java.util.Random;

public class FluidChemicalDispenser implements IBehaviorDispenseItem {
	
	private static final Random ran=new Random();
	
	@Override
	public ItemStack dispense(IBlockSource blockSource,ItemStack itemStack) {
		EnumFacing enumfacing = BlockDispenser.func_149937_b(blockSource.getBlockMetadata());
		World world = blockSource.getWorld();
		int x = blockSource.getXInt() + enumfacing.getFrontOffsetX();
		int y = blockSource.getYInt() + enumfacing.getFrontOffsetY();
		int z = blockSource.getZInt() + enumfacing.getFrontOffsetZ();
		
		if (itemStack.getItem() instanceof ElementItem&&itemStack.getItemDamage()>=ElementEnum.heaviestMass){
			Block frontBlock=world.getBlock(x, y, z);
			MinechemChemicalType chemical=ChemicalFluidReactionHandler.getChemical(frontBlock);
			
			if (chemical!=null&&canDrain(world,frontBlock, x, y, z)){
				ItemStack stack=createItemStack(chemical, 1);
				
				if (stack!=null){
					TileEntity tile=blockSource.getBlockTileEntity();
					if (tile instanceof IInventory){
						stack=addItemToInventory((IInventory)tile, stack);
					}
					throwItemStack(world, stack, x, y, z);
					
					--itemStack.stackSize;
					world.setBlockToAir(x, y, z);
				}
			}
		}else{
			Block block=null;
			if (itemStack.getItem() instanceof ElementItem){
				block=FluidHelper.elementsBlocks.get(FluidHelper.elements.get(ElementItem.getElement(itemStack)));
			}else if (itemStack.getItem() instanceof MoleculeItem&&itemStack.getItemDamage()<MoleculeEnum.molecules.length){
				block=FluidHelper.moleculeBlocks.get(FluidHelper.molecule.get(MoleculeEnum.molecules[itemStack.getItemDamage()]));
			}
			
	    	if (!world.isAirBlock(x, y, z)&&!world.getBlock(x, y, z).getMaterial().isSolid()){
	    		world.func_147480_a(x, y, z, true);
	    		world.setBlockToAir(x, y, z);
	    	}
			
			if (world.isAirBlock(x, y, z)&&block!=null){
				world.setBlock(x, y, z, block, 0, 3);
				--itemStack.stackSize;
				ItemStack elementStack=new ItemStack(MinechemItemsRegistration.element, 1, ElementEnum.heaviestMass);
				TileEntity tile=blockSource.getBlockTileEntity();
				if (tile instanceof IInventory){
					elementStack=addItemToInventory((IInventory)tile, elementStack);
				}
				throwItemStack(world, elementStack, x, y, z);
			}
		}
		
		return itemStack;
	}
	
	public static ItemStack addItemToInventory(IInventory inventory,ItemStack itemStack){
		if (itemStack==null){
			return null;
		}
		
		for (int i=0,l=inventory.getSizeInventory();i<l;i++){
			ItemStack stack=inventory.getStackInSlot(i);
			if (stack==null){
				int append=itemStack.stackSize>inventory.getInventoryStackLimit()?inventory.getInventoryStackLimit():itemStack.stackSize;
				inventory.setInventorySlotContents(i, new ItemStack(itemStack.getItem(),append,itemStack.getItemDamage()));
				itemStack.stackSize-=append;
			}else if (stack.getItem()==itemStack.getItem()&&stack.getItemDamage()==itemStack.getItemDamage()){
				int free=inventory.getInventoryStackLimit()-stack.stackSize;
				int append=itemStack.stackSize>free?free:itemStack.stackSize;
				itemStack.stackSize-=append;
				stack.stackSize+=append;
				inventory.setInventorySlotContents(i, stack);
			}
			
			if (itemStack.stackSize<=0){
				itemStack=null;
				break;
			}
		}
		return itemStack;
	}
	
	public static void throwItemStack(World world,ItemStack itemStack,double x,double y,double z){
		if (itemStack!=null){
			float f = ran.nextFloat() * 0.8F + 0.1F;
			float f1 = ran.nextFloat() * 0.8F + 0.1F;
			float f2 = ran.nextFloat() * 0.8F + 0.1F;
			
			EntityItem entityitem = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), itemStack);
			float f3 = 0.05F;
			entityitem.motionX = (double)((float)ran.nextGaussian() * f3);
			entityitem.motionY = (double)((float)ran.nextGaussian() * f3 + 0.2F);
			entityitem.motionZ = (double)((float)ran.nextGaussian() * f3);
			world.spawnEntityInWorld(entityitem);
		}
	}
	
	public static ItemStack createItemStack(MinechemChemicalType chemical,int amount){
		ItemStack itemStack=null;
		if (chemical instanceof ElementEnum){
			itemStack=ElementItem.createStackOf(ElementEnum.elements[((ElementEnum)chemical).ordinal()], 1);
		}else if (chemical instanceof MoleculeEnum){
			itemStack=new ItemStack(MinechemItemsRegistration.molecule, 1, ((MoleculeEnum)chemical).id());
		}
		return itemStack;
	}
	
	public static boolean canDrain(World world,Block block,int x,int y,int z){
		if ((block==Blocks.water||block==Blocks.flowing_water)&&world.getBlockMetadata(x, y, z)==0){
			return true;
		}else if (block instanceof IFluidBlock){
			return ((IFluidBlock) block).canDrain(world, x, y, z);
		}
		
		return false;
	}
}
