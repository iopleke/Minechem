package minechem.computercraft;

import java.util.ArrayList;
import java.util.HashMap;

import minechem.item.element.ElementItem;
import minechem.item.molecule.MoleculeEnum;
import minechem.item.molecule.MoleculeItem;
import minechem.potion.PotionChemical;
import minechem.radiation.RadiationEnum;
import minechem.radiation.RadiationInfo;
import minechem.tileentity.decomposer.DecomposerTileEntity;
import minechem.tileentity.synthesis.SynthesisRecipe;
import minechem.tileentity.synthesis.SynthesisTileEntity;
import minechem.utils.Compare;
import minechem.utils.MinechemHelper;
import minechem.utils.TimeHelper;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.ITurtleAccess;

public class MinechemComputerPeripheral extends TileEntity implements IPeripheral {
	private ITurtleAccess turtle;
	private ArrayList<SynthesisRecipe> recipes = new ArrayList<SynthesisRecipe>();
	private static final String[] methods = new String[]{"getMethods","getChemicalName",
		"getChemicalFormula","getChemicalsAsTable",	"getAtomicMass", "getRadiactivity",	"getTicksUntilDecay","getTimeUntilDecay",
		"storeSynthesisRecipe",	"getSynthesisRecipe","clearSynthesisRecipe","placeSynthesisRecipe",
		"getMachineState"};
	public MinechemComputerPeripheral(ITurtleAccess turtle) {
		this.turtle = turtle;
	}

	@Override
	public String getType() {
		return null;
	}

	@Override
	public String[] getMethodNames() {
		return methods;
	}

	
	//DON'T LOOK AT THIS - IT'S HIDEOUS! complete rewrite coming after dinner
	
	@Override
	public Object[] callMethod(IComputerAccess computer, ILuaContext context,int method, Object[] arguments) throws LuaException,InterruptedException {
		int selectedSlot = turtle.getSelectedSlot();
		ItemStack current = turtle.getInventory().getStackInSlot(selectedSlot);
		ForgeDirection direction = ForgeDirection.getOrientation(turtle.getDirection());
		TileEntity te = turtle.getWorld().getTileEntity(turtle.getPosition().posX+direction.offsetX,turtle.getPosition().posY+direction.offsetY, turtle.getPosition().posZ+direction.offsetZ);
		int recipeIndex;
		HashMap<String,Object> map = new HashMap<String,Object>();
		switch (method)
		{
		case 0:
			return methods;
		case 1: //"getChemicalName",
			if (Compare.isStackAnElement(current))
				return new Object[] {current.getDisplayName()};
			else if (Compare.isStackAMolecule(current))
				return new Object[] {current.getDisplayName()};
			break;
		case 2: //"getChemicalFormula",
			if (Compare.isStackAnElement(current))
				return new Object[] {ElementItem.getElement(current).name()};
			if (Compare.isStackAMolecule(current))
				return new Object[] {MoleculeItem.getMolecule(current).getFormula()};
			break;
		case 3: //"getChemicalsAsTable",
			if (Compare.isStackAnElement(current))
			{
				HashMap<String,Object> chemMap = new HashMap<String,Object>();
				chemMap.put("Chemical",current.getDisplayName());
				chemMap.put("Amount",1);
				map.put("1", chemMap);
			}
			if (Compare.isStackAMolecule(current))
			{
				MoleculeEnum molecule = MoleculeItem.getMolecule(current);
				ArrayList<PotionChemical> chemicals = molecule.getChemicals();
				for (int i=0;i<chemicals.size();i++)
				{
					PotionChemical chemical = chemicals.get(i);
					HashMap<String,Object> chemMap = new HashMap<String,Object>();
					chemMap.put("Chemical",MinechemHelper.getChemicalName(chemical));
					chemMap.put("Amount",chemical.amount);
					map.put(String.valueOf(i+1), chemMap);
				}
			}
			return new Object[]{map};
		case 4: //"getAtomicMass",
			if (Compare.isStackAnElement(current))
			{
				return new Object[]{ElementItem.getElement(current).atomicNumber()};
			}
			break;
		case 5: //"getRadiactivity",
			if (Compare.isStackAnElement(current))
			{
				return new Object[]{ElementItem.getElement(current).radioactivity().name()};
			}
			else if (Compare.isStackAMolecule(current))
			{
				return new Object[]{MoleculeItem.getMolecule(current).radioactivity().name()};
			}
			break;
		case 6: //"getTicksUntilDecay",
			if (Compare.isStackAChemical(current))
			{
				if (RadiationInfo.getRadioactivity(current) != RadiationEnum.stable && current.getTagCompound() != null && current.getTagCompound().hasKey("decayStart"))
		        {
		            long worldTime = turtle.getWorld().getTotalWorldTime();
		            return new Object[]{ RadiationInfo.getRadioactivity(current).getLife() - (worldTime - current.getTagCompound().getLong("decayStart"))};
		        }
				return new Object[]{"Infinite"};
			}
			break;
		case 7:
			if (Compare.isStackAChemical(current))
			{
				if (RadiationInfo.getRadioactivity(current) != RadiationEnum.stable && current.getTagCompound() != null && current.getTagCompound().hasKey("decayStart"))
		        {
		            long worldTime = turtle.getWorld().getTotalWorldTime();
		            return new Object[]{ TimeHelper.getTimeFromTicks(RadiationInfo.getRadioactivity(current).getLife() - (worldTime - current.getTagCompound().getLong("decayStart")))};
		        }
				return new Object[]{"Infinite"};
			}
			break;
		case 8: //"storeSynthesisRecipe",
			if (te!=null && te instanceof SynthesisTileEntity)
			{
				SynthesisRecipe recipe = ((SynthesisTileEntity)te).getCurrentRecipe();
				if (recipe!=null)
				{
					recipes.add(recipe);
					return new Object[]{true};
				}
			}
			return new Object[]{false};
		case 9: //"getSynthesisRecipe",
			if (arguments.length == 1 && arguments[0] instanceof Double) 
			{
				recipeIndex = ((Number) arguments[0]).intValue();
				if (recipeIndex<recipes.size() && recipeIndex>=0)
				{
					HashMap<String,Object> recipeMap = new HashMap<String,Object>();
					recipeMap.put("Output", recipes.get(recipeIndex).getOutput().getDisplayName());
					recipeMap.put("Quantity",recipes.get(recipeIndex).getOutput().stackSize);
					return new Object[]{recipeMap};
				}
				return new Object[] {"Invalid Argument"};
			}
			else if (arguments.length == 0)
			{
				recipeIndex = 1;
				for (SynthesisRecipe recipe:recipes)
				{
					HashMap<String,Object> recipeMap = new HashMap<String,Object>();
					recipeMap.put("Output", recipe.getOutput().getDisplayName());
					recipeMap.put("Quantity",recipe.getOutput().stackSize);
					map.put(String.valueOf(recipeIndex++), recipeMap);
				}
			}
			return new Object[]{map};
		case 10: //"clearSynthesisRecipe",
			if (arguments.length == 1 && arguments[0] instanceof Double) 
			{
				recipeIndex = ((Number) arguments[0]).intValue();
				if (recipeIndex<recipes.size() && recipeIndex>=0)
				{
					recipes.remove(recipeIndex);
					return new Object[]{true};
				}
			}
			else if (arguments.length == 1 && arguments[0] instanceof String)
			{
				if (((String)arguments[0]).equals("all")) 
				{
					recipes.clear();
					return new Object[]{true};
				}
			}
			else if (arguments.length == 0)
			{
				if (te!=null && te instanceof SynthesisTileEntity)
				{
					((SynthesisTileEntity)te).clearRecipeMatrix();
					return new Object[]{true};
				}
			}
			return new Object[]{false};
		case 11: //"placeSynthesisRecipe",
			if (arguments.length == 1 && arguments[0] instanceof Double) recipeIndex = ((Number) arguments[0]).intValue();
			else recipeIndex=0;
			if (recipeIndex<recipes.size() && recipeIndex>=0)
			{
				if (te!=null && te instanceof SynthesisTileEntity)
				{
					((SynthesisTileEntity)te).setRecipe(recipes.get(recipeIndex));
					return new Object[]{true};
				}
			}
			return new Object[]{false};
//		case 12: //"takeOutput",
////			if (te!=null && te instanceof SynthesisTileEntity)
////			{
////				if (((SynthesisTileEntity)te).canExtractItem(0, null, 0))
////				{
////					SynthesisRecipe recipe = ((SynthesisTileEntity)te).getCurrentRecipe();
////					if (canTake(recipe.getOutput()))
////					{
////						((SynthesisTileEntity)te).canTakeOutputStack(true);
////						return new Object[]{true};
////					}
////				}
////			}
////			else if (te!=null && te instanceof DecomposerTileEntity)
////			{
////				return new Object[]{"Decomposer handling is unimplemented currently, sorry"};
////			}
//			return new Object[]{false};
//		case 13: //"putInput",
////			if (te!=null && te instanceof SynthesisTileEntity)
////			{
////				if (Compare.isStackAChemical(current))
////				{
////					
////				}
////				if (((SynthesisTileEntity)te).canInsertItem(slot, itemstack, side)
////				
////			}
////			else if (te!=null && te instanceof DecomposerTileEntity)
////			{
////				return new Object[]{"Decomposer handling is unimplemented currently, sorry"};
////			}
//			return new Object[]{false};
		case 12: //"getMachineState"
			if (te!=null && te instanceof SynthesisTileEntity)
			{
				return new Object[]{((SynthesisTileEntity)te).getState()};
			}
			if (te!=null && te instanceof DecomposerTileEntity)
			{
				return new Object[]{((DecomposerTileEntity)te).getState().name()};
			}
			break;
		}
		return null;
	}

	private boolean canPut(ItemStack stack, ISidedInventory inventory)
	{
		int size = inventory.getSizeInventory();
		
		return false;
	}
	
	private boolean canTake(ItemStack stack)
	{
		for (int i=0;i<turtle.getInventory().getSizeInventory();i++)
		{
			ItemStack thisStack = turtle.getInventory().getStackInSlot(i);
			if (thisStack == null)
			{
				thisStack=stack;
				return true;
			}
			else if (thisStack.isItemEqual(stack)&&thisStack.stackSize+stack.stackSize<=thisStack.getMaxStackSize())
			{
				thisStack.stackSize+=stack.stackSize;
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void attach(IComputerAccess computer) {
		
	}

	@Override
	public void detach(IComputerAccess computer) {
		
	}

	@Override
	public boolean equals(IPeripheral other) {
		return false;
	}

}
