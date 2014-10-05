package minechem.fluid;

import java.util.Random;
import minechem.MinechemItemsRegistration;
import minechem.item.element.ElementEnum;
import minechem.item.element.ElementItem;
import minechem.item.molecule.MoleculeEnum;
import minechem.item.molecule.MoleculeItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;

public class FluidChemicalDispenser implements IBehaviorDispenseItem {
	
	private final Random ran=new Random();
	
	@Override
	public ItemStack dispense(IBlockSource blockSource,ItemStack itemStack) {
		EnumFacing enumfacing = BlockDispenser.func_149937_b(blockSource.getBlockMetadata());
		World world = blockSource.getWorld();
		int x = blockSource.getXInt() + enumfacing.getFrontOffsetX();
		int y = blockSource.getYInt() + enumfacing.getFrontOffsetY();
		int z = blockSource.getZInt() + enumfacing.getFrontOffsetZ();
		
		if (itemStack.getItem() instanceof ElementItem&&itemStack.getItemDamage()>=ElementEnum.heaviestMass){
			Block frontBlock=world.getBlock(x, y, z);
			
			if (frontBlock instanceof IFluidBlock){
				Fluid fluid=((IFluidBlock)frontBlock).getFluid();
				
				ItemStack stack=null;
				if (fluid instanceof FluidElement){
					stack=ElementItem.createStackOf(ElementEnum.elements[((FluidElement)fluid).element.ordinal()], 1);
				}else if (fluid instanceof FluidChemical){
					stack=new ItemStack(MinechemItemsRegistration.molecule, 1, ((FluidChemical)fluid).molecule.ordinal());
				}else if (fluid==FluidRegistry.WATER){
					stack=new ItemStack(MinechemItemsRegistration.molecule, 1,MoleculeEnum.water.ordinal());
				}
				
				if (stack==null){
					// it is not a chemical fluid
				}else{
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
			
			if (!world.getBlock(x, y, z).getMaterial().isSolid()&&block!=null){
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
	
	public ItemStack addItemToInventory(IInventory inventory,ItemStack itemStack){
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
	
	public void throwItemStack(World world,ItemStack itemStack,int x,int y,int z){
		if (itemStack!=null){
			float f = ran.nextFloat() * 0.8F + 0.1F;
			float f1 = ran.nextFloat() * 0.8F + 0.1F;
			float f2 = ran.nextFloat() * 0.8F + 0.1F;
			
			EntityItem entityitem = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), itemStack);
			float f3 = 0.05F;
			entityitem.motionX = (double)((float)this.ran.nextGaussian() * f3);
			entityitem.motionY = (double)((float)this.ran.nextGaussian() * f3 + 0.2F);
			entityitem.motionZ = (double)((float)this.ran.nextGaussian() * f3);
			world.spawnEntityInWorld(entityitem);
		}
	}
}
