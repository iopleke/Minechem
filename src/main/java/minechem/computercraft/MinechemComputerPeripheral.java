package minechem.computercraft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import minechem.MinechemItemsRegistration;
import minechem.computercraft.lua.LuaMethod;
import minechem.item.element.ElementItem;
import minechem.item.molecule.MoleculeEnum;
import minechem.item.molecule.MoleculeItem;
import minechem.potion.PotionChemical;
import minechem.radiation.RadiationEnum;
import minechem.radiation.RadiationInfo;
import minechem.tileentity.decomposer.DecomposerRecipe;
import minechem.tileentity.decomposer.DecomposerTileEntity;
import minechem.tileentity.microscope.MicroscopeTileEntity;
import minechem.tileentity.synthesis.SynthesisRecipe;
import minechem.tileentity.synthesis.SynthesisTileEntity;
import minechem.utils.Compare;
import minechem.utils.MinechemHelper;
import minechem.utils.TimeHelper;
import net.minecraft.init.Items;
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
	private ArrayList<ItemStack> known = new ArrayList<ItemStack>();
	private static final ArrayList<LuaMethod> luaMethods = new ArrayList<LuaMethod>();
	
	public MinechemComputerPeripheral(ITurtleAccess turtle) {
		this.turtle = turtle;
		if (luaMethods.isEmpty()) addLuaMethods();
	}

	@Override
	public String getType() {
		return null;
	}

	@Override
	public String[] getMethodNames() {
		String[] result = new String[luaMethods.size()];
		int i=0;
		for (LuaMethod method:luaMethods)
			result[i++]=method.getMethodName();
		return result;
	}

	
	//DON'T LOOK AT THIS - IT'S HIDEOUS! complete rewrite coming after dinner
	
    protected void addLuaMethods()
    {
    	luaMethods.add(new LuaMethod("getMethods"){

			@Override
			public Object[] call(IComputerAccess computer, ILuaContext context, Object[] args) throws LuaException, InterruptedException {
				if(args.length == 0) {
					String[] result = new String[luaMethods.size()];
					int i=0;
					for (LuaMethod method:luaMethods)
						result[i++]=method.getMethodName()+method.getArgs();
					return result;
				}else {
                    throw new IllegalArgumentException("getMethods does not take any arguments");
				}
			}
    		@Override
    		public String[] getDetails() {
    			return new String[]{super.getDetails()[0],"Returns: List of Method Names and Arguments"};
    		}
    	});
    	
    	luaMethods.add(new LuaMethod("getDetails"){

			@Override
			public Object[] call(IComputerAccess computer, ILuaContext context, Object[] args) throws LuaException, InterruptedException {
				if(args.length == 1)
				{
					String methodName =(String)args[0];
					if (methodName!=null)
					{
						for (LuaMethod method:luaMethods)
						{
							if (method.getMethodName().equalsIgnoreCase(methodName))
							{
								return method.getDetails();
							}
						}
					}
					throw new IllegalArgumentException("Invalid Method Name - do not include brackets");
				}else {
                    throw new IllegalArgumentException("getDetails takes a single argument");
				}
			}
    		
			@Override
			public String getArgs() {
				return "(Method Name)";
			}
			
			@Override
			public String[] getDetails() {
				return new String[]{super.getDetails()[0],"Arg: Method Name - from getMethods()","Returns: Details of arguments and results"};
			}
    	});
    	
    	luaMethods.add(new LuaMethod("getChemicalName"){
            @Override
            public Object[] call(IComputerAccess computer, ILuaContext context, Object[] args) throws LuaException, InterruptedException{
                if(args.length == 0) {
                	String result = getName(turtle.getSelectedSlot());
                	if (result!=null)
                		return new Object[]{result};
                } else if(args.length == 1) {
                	Integer slot = getInt(args[0]);
                	if (validateInteger(slot,turtle.getInventory().getSizeInventory()))
                	{
                		String result = getName(slot);
                    	if (result!=null)
                    		return new Object[]{result};
                	}
                	else 
                		throw new IllegalArgumentException("Invalid Slot Number.");
                } else {
                    throw new IllegalArgumentException("Maximum 1 argument for slot number.");
                }
				return null;
            }
            
            private String getName(int slot)
            {
            	ItemStack current = turtle.getInventory().getStackInSlot(slot);
            	if (Compare.isStackAnElement(current))
        			return current.getDisplayName();
        		else if (Compare.isStackAMolecule(current))
        			return current.getDisplayName();
        		return null;
            }
            
            @Override
			public String getArgs() {
				return "(?Slot)";
			}
			
			@Override
			public String[] getDetails() {
				return new String[]{super.getDetails()[0],"Arg: Optional Slot Number, defaults to current slot","Returns: Chemical name of stack in slot, or nil if not a chemical"};
			}
            
        });
    	
    	luaMethods.add(new LuaMethod("getChemicalFormula"){
            @Override
            public Object[] call(IComputerAccess computer, ILuaContext context, Object[] args) throws LuaException, InterruptedException{
                if(args.length == 0) {
                	String result = getChemicalFormula(turtle.getSelectedSlot());
                	if (result!=null)
                		return new Object[]{result};
                } else if(args.length == 1) {
                	Integer slot = getInt(args[0]);
                	if (validateInteger(slot,turtle.getInventory().getSizeInventory()))
                	{
                		String result = getChemicalFormula(slot);
                    	if (result!=null)
                    		return new Object[]{result};
                	}
                	else 
                		throw new IllegalArgumentException("Invalid Slot Number.");
                } else {
                    throw new IllegalArgumentException("Maximum 1 argument for slot number.");
                }
				return null;
            }
            
            private String getChemicalFormula(int slot)
            {
            	ItemStack current = turtle.getInventory().getStackInSlot(slot);
            	if (Compare.isStackAnElement(current))
        			return ElementItem.getElement(current).name();
        		if (Compare.isStackAMolecule(current))
        			return MoleculeItem.getMolecule(current).getFormula();
        		return null;
            }
            
            @Override
			public String getArgs() {
				return "(?Slot)";
			}
			
			@Override
			public String[] getDetails() {
				return new String[]{super.getDetails()[0],"Arg: Optional Slot Number, defaults to current slot","Returns: Chemical formula of stack in slot, or nil if not a chemical"};
			}
        });
    	
    	luaMethods.add(new LuaMethod("getChemicalsAsTable"){
            @Override
            public Object[] call(IComputerAccess computer, ILuaContext context, Object[] args) throws LuaException, InterruptedException{
                if(args.length == 0) {
                	HashMap<Number, Object> result = getChemicalsAsTable(turtle.getSelectedSlot());
                	if (result!=null)
                		return new Object[]{result};
                } else if(args.length == 1) {
                	Integer slot = getInt(args[0]);
                	if (validateInteger(slot,turtle.getInventory().getSizeInventory()))
                	{
                		HashMap<Number, Object> result = getChemicalsAsTable(slot);
                    	if (result!=null)
                    		return new Object[]{result};
                	}
                	else 
                		throw new IllegalArgumentException("Invalid Slot Number.");
                } else {
                    throw new IllegalArgumentException("Maximum 1 argument for slot number.");
                }
				return null;
            }
            
            private HashMap<Number, Object> getChemicalsAsTable(int slot)
            {
            	ItemStack current = turtle.getInventory().getStackInSlot(slot);
            	HashMap<Number, Object> map = new HashMap<Number, Object>();
            	if (Compare.isStackAnElement(current))
        		{
        			HashMap<String,Object> chemMap = new HashMap<String,Object>();
        			chemMap.put("Chemical",current.getDisplayName());
        			chemMap.put("Amount",1);
        			map.put(1, chemMap);
        			return map;
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
        				map.put(i+1, chemMap);
        			}
        			return map;
        		}
        		return null;
            }
            
            @Override
			public String getArgs() {
				return "(?Slot)";
			}
			
			@Override
			public String[] getDetails() {
				return new String[]{super.getDetails()[0],"Arg: Optional Slot Number, defaults to current slot","Returns: Chemical formula of stack in slot as a table, or nil if not a chemical"};
			}
        });
    	
    	luaMethods.add(new LuaMethod("getAtomicMass"){
            @Override
            public Object[] call(IComputerAccess computer, ILuaContext context, Object[] args) throws LuaException, InterruptedException{
                if(args.length == 0) {
                	Integer result = getAtomicMass(turtle.getSelectedSlot());
                	if (result!=null)
                		return new Object[]{result};
                } else if(args.length == 1) {
                	Integer slot = getInt(args[0]);
                	if (validateInteger(slot,turtle.getInventory().getSizeInventory()))
                	{
                		Integer result = getAtomicMass(slot);
                    	if (result!=null)
                    		return new Object[]{result};
                	}
                	else 
                		throw new IllegalArgumentException("Invalid Slot Number.");
                } else {
                    throw new IllegalArgumentException("Maximum 1 argument for slot number.");
                }
				return null;
            }

			private Integer getAtomicMass(Integer slot) {
				ItemStack current = turtle.getInventory().getStackInSlot(slot);
				if (Compare.isStackAnElement(current))
				{
					return ElementItem.getElement(current).atomicNumber();
				}
				return null;
			}

			@Override
			public String getArgs() {
				return "(?Slot)";
			}
			
			@Override
			public String[] getDetails() {
				return new String[]{super.getDetails()[0],"Arg: Optional Slot Number, defaults to current slot","Returns: Atomic Mass of elements"};
			}
        });
    	
    	luaMethods.add(new LuaMethod("getRadioactivity"){
            @Override
            public Object[] call(IComputerAccess computer, ILuaContext context, Object[] args) throws LuaException, InterruptedException{
                if(args.length == 0) {
                	String result = getRadioactivity(turtle.getSelectedSlot());
                	if (result!=null)
                		return new Object[]{result};
                } else if(args.length == 1) {
                	Integer slot = getInt(args[0]);
                	if (validateInteger(slot,turtle.getInventory().getSizeInventory()))
                	{
                		String result = getRadioactivity(slot);
                    	if (result!=null)
                    		return new Object[]{result};
                	}
                	else 
                		throw new IllegalArgumentException("Invalid Slot Number.");
                } else {
                    throw new IllegalArgumentException("Maximum 1 argument for slot number.");
                }
				return null;
            }

			private String getRadioactivity(Integer slot) {
				ItemStack current = turtle.getInventory().getStackInSlot(slot);
				if (Compare.isStackAnElement(current))
				{
					return ElementItem.getElement(current).radioactivity().name();
				}
				else if (Compare.isStackAMolecule(current))
				{
					return MoleculeItem.getMolecule(current).radioactivity().name();
				}
				return null;
			}

			@Override
			public String getArgs() {
				return "(?Slot)";
			}
			
			@Override
			public String[] getDetails() {
				return new String[]{super.getDetails()[0],"Arg: Optional Slot Number, defaults to current slot","Returns: Radioactivity level of chemicals"};
			}
        });
    	
    	luaMethods.add(new LuaMethod("getDecayTimeInTicks"){
            @Override
            public Object[] call(IComputerAccess computer, ILuaContext context, Object[] args) throws LuaException, InterruptedException{
                if(args.length == 0) {
                	Object result = getDecayTime(turtle.getSelectedSlot());
                	if (result!=null)
                		return new Object[]{result};
                } else if(args.length == 1) {
                	Integer slot = getInt(args[0]);
                	if (validateInteger(slot,turtle.getInventory().getSizeInventory()))
                	{
                		Object result = getDecayTime(slot);
                		if (result!=null)
                    		return new Object[]{result};
                	}
                	else 
                		throw new IllegalArgumentException("Invalid Slot Number.");
                } else {
                    throw new IllegalArgumentException("Maximum 1 argument for slot number.");
                }
				return null;
            }

			@Override
			public String getArgs() {
				return "(?Slot)";
			}
			
			@Override
			public String[] getDetails() {
				return new String[]{super.getDetails()[0],"Arg: Optional Slot Number, defaults to current slot","Returns: Time left to Radioactive Decay in Ticks - Infinite for stable chemicals, null for non-chemicals"};
			}
        });
    	
    	luaMethods.add(new LuaMethod("getDecayTime"){
            @Override
            public Object[] call(IComputerAccess computer, ILuaContext context, Object[] args) throws LuaException, InterruptedException{
                if(args.length == 0) {
                	Object result = getDecayTime(turtle.getSelectedSlot());
                	if (result!=null)
                	{
                		if (result instanceof Long)
                			return new Object[]{TimeHelper.getTimeFromTicks((Long)result)};
                		else if (result instanceof String)
                			return new Object[]{result};
                	}
                } else if(args.length == 1) {
                	Integer slot = getInt(args[0]);
                	if (validateInteger(slot,turtle.getInventory().getSizeInventory()))
                	{
                		Object result = getDecayTime(slot);
                		if (result!=null)
                    	{
                    		if (result instanceof Long)
                    			return new Object[]{TimeHelper.getTimeFromTicks((Long)result)};
                    		else if (result instanceof String)
                    			return new Object[]{result};
                    	}
                	}
                	else 
                		throw new IllegalArgumentException("Invalid Slot Number.");
                } else {
                    throw new IllegalArgumentException("Maximum 1 argument for slot number.");
                }
				return null;
            }

			@Override
			public String getArgs() {
				return "(?Slot)";
			}
			
			@Override
			public String[] getDetails() {
				return new String[]{super.getDetails()[0],"Arg: Optional Slot Number, defaults to current slot","Returns: Time left to Radioactive Decay - Infinite for stable chemicals, null for non-chemicals"};
			}
        });
    	
    	luaMethods.add(new LuaMethod("syncJournal"){
            @Override
            public Object[] call(IComputerAccess computer, ILuaContext context, Object[] args) throws LuaException, InterruptedException{
                if(args.length == 0) {
                	String result = sync(turtle.getSelectedSlot());
                	if (result!=null)
                			return new Object[]{result};
                } else if(args.length == 1) {
                	Integer slot = getInt(args[0]);
                	if (validateInteger(slot,turtle.getInventory().getSizeInventory()))
                	{
                		String result = sync(slot);
                    	if (result!=null)
                    		return new Object[]{result};
                	}
                	else 
                		throw new IllegalArgumentException("Invalid Slot Number.");
                } else {
                    throw new IllegalArgumentException("Maximum 1 argument for slot number.");
                }
				return null;
            }

			private String sync(int slot) {
				ItemStack journal = getJournal(slot);
				if (journal!=null)
				{
					return upload(journal)+" - "+download(journal);
				}
				ItemStack book = getBook(slot);
				if (book!=null)
				{
					return download(book);
				}
				throw new IllegalArgumentException("Invalid Stack - not a journal or a book");
			}
			
			private String upload(ItemStack journal)
			{
				List<ItemStack> journalItems=MinechemItemsRegistration.journal.getItemList(journal);
				if (journalItems==null) journalItems=new ArrayList<ItemStack>();
				ArrayList<ItemStack> addItems=new ArrayList<ItemStack>();
				for (ItemStack journalItem:journalItems)
				{
					boolean knownItem = false;
					for (ItemStack currentItem:known)
					{
						if (journalItem.isItemEqual(currentItem))
						{
							knownItem = true;
							break;
						}
					}
					if (!knownItem) addItems.add(journalItem);
				}
				known.addAll(addItems);
				int added = addItems.size();
				return "Loaded "+added+" recipe"+(added!=1?"s":"");
			}
			
			private String download(ItemStack journal)
			{
				if (journal.getItem()==Items.book)
					journal = new ItemStack(MinechemItemsRegistration.journal);
				
				List<ItemStack> journalItems=MinechemItemsRegistration.journal.getItemList(journal);
				if (journalItems==null) journalItems=new ArrayList<ItemStack>();
				ArrayList<ItemStack> addItems=new ArrayList<ItemStack>();
				for (ItemStack currentItem:known)
				{
					boolean knownItem = false;
					for (ItemStack journalItem:journalItems)
					{
						if (journalItem.isItemEqual(currentItem))
						{
							knownItem = true;
							break;
						}
					}
					if (!knownItem) addItems.add(currentItem);
				}
				for (ItemStack item:addItems)
				{
					MinechemItemsRegistration.journal.addItemStackToJournal(item, journal, turtle.getWorld());
				}
				int added = addItems.size();
				return "Saved "+added+" recipe"+(added!=1?"s":"");
			}
			
			private ItemStack getBook(int slot) {
				ItemStack result = turtle.getInventory().getStackInSlot(slot);
				if (result.getItem()==Items.book && result.stackSize==1)
					return result;
				return null;
			}

			@Override
			public String getArgs() {
				return "(?Slot)";
			}
			
			@Override
			public String[] getDetails() {
				return new String[]{super.getDetails()[0],"Arg: Optional Slot Number, defaults to current slot","Returns: Number of Journal Entries loaded and saved"};
			}
        });
    	
    	luaMethods.add(new LuaMethod("readRecipe"){
            @Override
            public Object[] call(IComputerAccess computer, ILuaContext context, Object[] args) throws LuaException, InterruptedException{
                ForgeDirection dir=null;
            	if(args.length == 0)
                	dir = getDirForString("front", turtle);
                else if(args.length == 1)
                	dir = getDirForString((String)args[0], turtle);
                if (dir==null)
                	throw new IllegalArgumentException("Invalid Arguments");
                TileEntity te = turtle.getWorld().getTileEntity(turtle.getPosition().posX+dir.offsetX, turtle.getPosition().posY+dir.offsetY, turtle.getPosition().posZ+dir.offsetZ);
                if (te!=null)
                {
                	if (te instanceof SynthesisTileEntity)
                	{
                		SynthesisRecipe thisRecipe = ((SynthesisTileEntity)te).getCurrentRecipe();
                		if (thisRecipe!=null)
                		{
                			if (addStackToKnown(thisRecipe.getOutput()))
                				return new Object[]{"Synthesis Recipe Scanned",stackToMap(thisRecipe.getOutput())};
            				else 
                				return new Object[]{"Item already known"};
                		}
                	}
                	else if (te instanceof DecomposerTileEntity)
                	{
                		DecomposerRecipe thisRecipe = DecomposerRecipe.get(((DecomposerTileEntity)te).inventory[((DecomposerTileEntity)te).kInputSlot]);
                		if (thisRecipe!=null)
                		{
                			if (addStackToKnown(thisRecipe.getInput()))
                				return new Object[]{"Decomposer Recipe Scanned",stackToMap(thisRecipe.getInput())};
                			else 
                				return new Object[]{"Item already known"};
                			
                		}
                	}
                	else if (te instanceof MicroscopeTileEntity)
                	{
                		ItemStack thisStack = ((MicroscopeTileEntity)te).inventory[0];
                		if (thisStack!=null)
                		{
                			if (addStackToKnown(thisStack))
                				return new Object[]{"Microscope scan complete",stackToMap(thisStack)};
                			else 
                				return new Object[]{"Item already known"};
                		}
                	}
                }
				return null;
            }

			@Override
			public String getArgs() {
				return "(?Direction)";
			}
			
			@Override
			public String[] getDetails() {
				return new String[]{super.getDetails()[0],"Arg: Optional Direction, defaults to front","Returns: Recipe read"};
			}
        });
    	
    }
	
    private HashMap<String,Object> stackToMap(ItemStack stack)
    {
    	HashMap<String,Object> result = new HashMap<String,Object>();
    	result.put("Item Name", stack.getDisplayName());
    	result.put("Item", GameRegistry.findUniqueIdentifierFor(stack.getItem())+":"+stack.getItemDamage());
    	return result;
    }
    
    private boolean addStackToKnown(ItemStack stack)
    {
    	for (ItemStack oldStack:known)
    	{
    		if (oldStack.isItemEqual(stack)) return false;
    	}
    	known.add(stack);
    	return true;
    }
    
    private ItemStack getJournal(int slot)
    {
    	ItemStack result = turtle.getInventory().getStackInSlot(slot);
    	if (result != null)
    		if (result.getItem()==MinechemItemsRegistration.journal)
    			return result;
    	return null;
    }
    
    private boolean validateInteger(Integer input, int max)
    {
    	if (input==null) return false;
    	if (input>=max) return false;
    	return true;
    }
    
    private Integer getInt(Object arg)
    {
    	return ((Number) arg).intValue();
    }
    
    private Object getDecayTime(int slot)
    {
    	ItemStack current = turtle.getInventory().getStackInSlot(slot);
    	if (Compare.isStackAChemical(current))
		{
			if (RadiationInfo.getRadioactivity(current) != RadiationEnum.stable && current.getTagCompound() != null && current.getTagCompound().hasKey("decayStart"))
	        {
	            long worldTime = turtle.getWorld().getTotalWorldTime();
	            return RadiationInfo.getRadioactivity(current).getLife() - (worldTime - current.getTagCompound().getLong("decayStart"));
	        }
			return "Infinite";
		}
    	return null;
    }
    
	@Override
	public Object[] callMethod(IComputerAccess computer, ILuaContext context,int method, Object[] arguments) throws LuaException,InterruptedException {
		return luaMethods.get(method).call(computer, context, arguments);

//		case 8: //"storeSynthesisRecipe",
//			if (te!=null && te instanceof SynthesisTileEntity)
//			{
//				SynthesisRecipe recipe = ((SynthesisTileEntity)te).getCurrentRecipe();
//				if (recipe!=null)
//				{
//					recipes.add(recipe);
//					return new Object[]{true};
//				}
//			}
//			return new Object[]{false};
//		case 9: //"getSynthesisRecipe",
//			if (arguments.length == 1 && arguments[0] instanceof Double) 
//			{
//				recipeIndex = ((Number) arguments[0]).intValue();
//				if (recipeIndex<recipes.size() && recipeIndex>=0)
//				{
//					HashMap<String,Object> recipeMap = new HashMap<String,Object>();
//					recipeMap.put("Output", recipes.get(recipeIndex).getOutput().getDisplayName());
//					recipeMap.put("Quantity",recipes.get(recipeIndex).getOutput().stackSize);
//					return new Object[]{recipeMap};
//				}
//				return new Object[] {"Invalid Argument"};
//			}
//			else if (arguments.length == 0)
//			{
//				recipeIndex = 1;
//				for (SynthesisRecipe recipe:recipes)
//				{
//					HashMap<String,Object> recipeMap = new HashMap<String,Object>();
//					recipeMap.put("Output", recipe.getOutput().getDisplayName());
//					recipeMap.put("Quantity",recipe.getOutput().stackSize);
//					map.put(String.valueOf(recipeIndex++), recipeMap);
//				}
//			}
//			return new Object[]{map};
//		case 10: //"clearSynthesisRecipe",
//			if (arguments.length == 1 && arguments[0] instanceof Double) 
//			{
//				recipeIndex = ((Number) arguments[0]).intValue();
//				if (recipeIndex<recipes.size() && recipeIndex>=0)
//				{
//					recipes.remove(recipeIndex);
//					return new Object[]{true};
//				}
//			}
//			else if (arguments.length == 1 && arguments[0] instanceof String)
//			{
//				if (((String)arguments[0]).equals("all")) 
//				{
//					recipes.clear();
//					return new Object[]{true};
//				}
//			}
//			else if (arguments.length == 0)
//			{
//				if (te!=null && te instanceof SynthesisTileEntity)
//				{
//					((SynthesisTileEntity)te).clearRecipeMatrix();
//					return new Object[]{true};
//				}
//			}
//			return new Object[]{false};
//		case 11: //"placeSynthesisRecipe",
//			if (arguments.length == 1 && arguments[0] instanceof Double) recipeIndex = ((Number) arguments[0]).intValue();
//			else recipeIndex=0;
//			if (recipeIndex<recipes.size() && recipeIndex>=0)
//			{
//				if (te!=null && te instanceof SynthesisTileEntity)
//				{
//					((SynthesisTileEntity)te).setRecipe(recipes.get(recipeIndex));
//					return new Object[]{true};
//				}
//			}
//			return new Object[]{false};
////		case 12: //"takeOutput",
//////			if (te!=null && te instanceof SynthesisTileEntity)
//////			{
//////				if (((SynthesisTileEntity)te).canExtractItem(0, null, 0))
//////				{
//////					SynthesisRecipe recipe = ((SynthesisTileEntity)te).getCurrentRecipe();
//////					if (canTake(recipe.getOutput()))
//////					{
//////						((SynthesisTileEntity)te).canTakeOutputStack(true);
//////						return new Object[]{true};
//////					}
//////				}
//////			}
//////			else if (te!=null && te instanceof DecomposerTileEntity)
//////			{
//////				return new Object[]{"Decomposer handling is unimplemented currently, sorry"};
//////			}
////			return new Object[]{false};
////		case 13: //"putInput",
//////			if (te!=null && te instanceof SynthesisTileEntity)
//////			{
//////				if (Compare.isStackAChemical(current))
//////				{
//////					
//////				}
//////				if (((SynthesisTileEntity)te).canInsertItem(slot, itemstack, side)
//////				
//////			}
//////			else if (te!=null && te instanceof DecomposerTileEntity)
//////			{
//////				return new Object[]{"Decomposer handling is unimplemented currently, sorry"};
//////			}
////			return new Object[]{false};
//		case 12: //"getMachineState"
//			if (te!=null && te instanceof SynthesisTileEntity)
//			{
//				return new Object[]{((SynthesisTileEntity)te).getState()};
//			}
//			if (te!=null && te instanceof DecomposerTileEntity)
//			{
//				return new Object[]{((DecomposerTileEntity)te).getState().name()};
//			}
//			break;
//		}
//		return null;
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
