package pixlepix.minechem.common.tileentity;

import java.util.ArrayList;
import java.util.HashMap;

import pixlepix.minechem.common.items.ItemElement;
import pixlepix.minechem.common.items.ItemMolecule;
import pixlepix.minechem.fluid.FluidHelper;
import pixlepix.minechem.fluid.IMinechemFluid;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityChemicalStorage extends TileEntityChest implements IFluidHandler {

	 public HashMap<Fluid,Integer>  partialFluids=new HashMap();
		
		@Override
		public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
			Fluid fluid=FluidRegistry.getFluid(resource.fluidID);
			
			if(fluid instanceof IMinechemFluid){

				IMinechemFluid minechemFluid=(IMinechemFluid) fluid;
				if(!doFill){
					return resource.amount;
				}
				int previousFluid=0;
				int currentFluid=0;
					if(!partialFluids.containsKey(fluid)){
						partialFluids.put(fluid, 0);
					}
					previousFluid=partialFluids.get(fluid);
					currentFluid= (2*resource.amount)+previousFluid;

					if(currentFluid<FluidHelper.FLUID_CONSTANT){


						partialFluids.put(fluid,currentFluid);
						return resource.amount;
					}
					
					
					while(currentFluid>=FluidHelper.FLUID_CONSTANT){

						ItemStack output=minechemFluid.getOutputStack();
						this.addStackToInventory(output);
						currentFluid-=FluidHelper.FLUID_CONSTANT;
					}
					partialFluids.put(fluid, currentFluid);

					return resource.amount;
				
			}
			return 0;
			
		}
		@Override
		public void updateEntity(){
			super.updateEntity();
			for (Object obj:this.partialFluids.keySet().toArray()){
				if(obj instanceof IMinechemFluid){
					IMinechemFluid fluid=(IMinechemFluid) obj;
					int currentFluid=this.partialFluids.get(fluid);
					while(currentFluid>=FluidHelper.FLUID_CONSTANT){
		
						ItemStack output=fluid.getOutputStack();
						this.addStackToInventory(output);
						currentFluid-=FluidHelper.FLUID_CONSTANT;
					}
					partialFluids.put(((Fluid)obj), currentFluid);
				}
			}
		}
		public void addStackToInventory(ItemStack newStack){
			for(int i=0;i<this.getSizeInventory();i++){
				ItemStack stack=this.getStackInSlot(i);
				if(stack==null){
					this.setInventorySlotContents(i, newStack);
					return;
				}
				if(stack.stackSize<64&&stack.getItem()==newStack.getItem()&&stack.getItemDamage()==newStack.getItemDamage()){
					stack.stackSize++;
					return;
				}
			}
			worldObj.spawnEntityInWorld(new EntityItem(worldObj,xCoord,yCoord+2,zCoord, newStack));
		}
		@Override
		public FluidStack drain(ForgeDirection from, FluidStack resource,
				boolean doDrain) {
			return this.drain(from, resource.amount,doDrain);
		}

		@Override
		public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
			int toDrain=maxDrain;
			FluidStack toReturn=null;
				for(int i=0;i<this.getSizeInventory();i++){
					ItemStack stack=this.getStackInSlot(i);
					if(stack==null){
						continue;
					}
					Item item=stack.getItem();
					if(item instanceof ItemElement){
						ItemElement itemMolecule=(ItemElement) item;
						Fluid fluid=FluidHelper.elements.get(ItemElement.getElement(stack));
						if(toReturn==null||fluid.getID()==toReturn.fluidID){
							this.decrStackSize(i, 1);
							if(toReturn==null){
								toReturn=new FluidStack(fluid,0);
							}
							toReturn.amount+=FluidHelper.FLUID_CONSTANT;
						}
					}
					if(item instanceof ItemMolecule){
						ItemMolecule itemMolecule=(ItemMolecule) item;
						Fluid fluid=FluidHelper.molecule.get(ItemMolecule.getMolecule(stack));
						if(toReturn==null||fluid.getID()==toReturn.fluidID){
							this.decrStackSize(i, 1);
							if(toReturn==null){
								toReturn=new FluidStack(fluid,0);
							}
							toReturn.amount+=FluidHelper.FLUID_CONSTANT;
						}
					}
				
			}
			
			return toReturn;
		}

		@Override
		public boolean canFill(ForgeDirection from, Fluid fluid) {
			return fluid instanceof IMinechemFluid;
		}

		@Override
		public boolean canDrain(ForgeDirection from, Fluid fluid) {
			System.out.println("Checkng CanDrain");
			return fluid instanceof IMinechemFluid;
		}
		public FluidTankInfo getTankInfo(int i){
			
			ItemStack stack=this.getStackInSlot(i);
			if(stack==null){
				return null;
			}
			Item item=stack.getItem();
			if(item instanceof ItemElement){
				ItemElement itemMolecule=(ItemElement) item;
				Fluid fluid=FluidHelper.elements.get(ItemElement.getElement(stack));
				if(fluid==null){
					return null;
					//This should never happen.
				}
				return new FluidTankInfo(new FluidStack(fluid,stack.stackSize*FluidHelper.FLUID_CONSTANT),6400);
				
				
			}
			
			if(item instanceof ItemMolecule){
				ItemMolecule itemMolecule=(ItemMolecule) item;
				Fluid fluid=FluidHelper.molecule.get(ItemMolecule.getMolecule(stack));
				if(fluid==null){
					return null;
					//This should never happen.
				}
				return new FluidTankInfo(new FluidStack(fluid,stack.stackSize*FluidHelper.FLUID_CONSTANT),6400);
				}
			
			return null;
				
		}
		@Override
		public FluidTankInfo[] getTankInfo(ForgeDirection from) {
			ArrayList fluids=new ArrayList();
			for(int i=0;i<this.getSizeInventory();i++){
				FluidTankInfo newFluid=this.getTankInfo(i);
				if(newFluid!=null){
					fluids.add(newFluid);
				}
			}
			return (FluidTankInfo[]) fluids.toArray();
		}
	
}
