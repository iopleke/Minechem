package pixlepix.minechem.common.assembler;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class AssemblerRecipe implements IRecipe {
	int type;
	public AssemblerRecipe(int type){
		this.type=type;
	}
	@Override
	public boolean matches(InventoryCrafting inv, World world) {
		
		boolean found=false;
		int count=0;
		for(int i=0;i<inv.getSizeInventory();i++){
			if(inv.getStackInSlot(i)==null){
				count++;
			}
			if(inv.getStackInSlot(i).getItem() instanceof ItemAssembler){
				found=true;
			}
		}
		switch(this.type){
			case 1:
				if(count==1&&found){
					return true;
				}
				return false;
			case 2:
				if(count==2&&found){
					return true;
				}
				return false;
			
			default:
				return false;
		}
		
		
		
	}
	
	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		switch(type){
			
			case 1:
				ItemStack out=null;
				for(int i=0;i<inv.getSizeInventory();i++){
					
					if(inv.getStackInSlot(i).getItem() instanceof ItemAssembler){
						out = inv.getStackInSlot(i);
						
					}
				}
				if(out.stackTagCompound==null){
					out.stackTagCompound=ItemAssembler.createData();
				}
				out.stackTagCompound.setInteger("Size", out.stackTagCompound.getInteger("Size"));
				return out;
			case 2:
				ItemStack result = null;
				ItemStack mod = null;
				for(int i=0;i<inv.getSizeInventory();i++){
					
					if(inv.getStackInSlot(i).getItem() instanceof ItemAssembler){
						result = inv.getStackInSlot(i);
					}else{
						if(inv.getStackInSlot(i)!=null){
							mod=inv.getStackInSlot(i);
						}
					}
				}
				if(result.stackTagCompound==null){
					result.stackTagCompound=ItemAssembler.createData();
				}
				result.stackTagCompound.setInteger("Block",mod.itemID);
				return result;
			default:
				return null;
		}
	}

	@Override
	public int getRecipeSize() {
		switch(type){
		case 1:
			return 1;
		case 2:
			return 2;
		default:
			return 1;
		}
	}

	@Override
	public ItemStack getRecipeOutput() {
		return null;
	}

}
