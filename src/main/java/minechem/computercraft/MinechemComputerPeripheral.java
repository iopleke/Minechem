package minechem.computercraft;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import minechem.MinechemItemsRegistration;
import minechem.computercraft.lua.LuaMethod;
import minechem.item.element.Element;
import minechem.item.element.ElementItem;
import minechem.item.molecule.Molecule;
import minechem.item.molecule.MoleculeEnum;
import minechem.item.molecule.MoleculeItem;
import minechem.potion.PotionChemical;
import minechem.radiation.RadiationEnum;
import minechem.radiation.RadiationInfo;
import minechem.tileentity.decomposer.DecomposerRecipe;
import minechem.tileentity.decomposer.DecomposerTileEntity;
import minechem.tileentity.microscope.MicroscopeTileEntity;
import minechem.tileentity.synthesis.SynthesisRecipe;
import minechem.tileentity.synthesis.SynthesisRecipeHandler;
import minechem.tileentity.synthesis.SynthesisTileEntity;
import minechem.utils.Compare;
import minechem.utils.MinechemHelper;
import minechem.utils.TimeHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.registry.GameRegistry;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.ITurtleAccess;

@Optional.InterfaceList({
    @Optional.Interface(iface = "dan200.computercraft.api.lua.ILuaContext", modid = "ComputerCraft"),
    @Optional.Interface(iface = "dan200.computercraft.api.lua.LuaException", modid = "ComputerCraft"),
    @Optional.Interface(iface = "dan200.computercraft.api.peripheral.IComputerAccess", modid = "ComputerCraft"),
    @Optional.Interface(iface = "dan200.computercraft.api.peripheral.IPeripheral", modid = "ComputerCraft"),
    @Optional.Interface(iface = "dan200.computercraft.api.turtle.ITurtleAccess", modid = "ComputerCraft")})
public class MinechemComputerPeripheral implements IPeripheral
{
	private ITurtleAccess turtle;
	private ArrayList<ItemStack> known;
	private static final ArrayList<LuaMethod> luaMethods = new ArrayList<LuaMethod>();

	public MinechemComputerPeripheral(ITurtleAccess turtle) {
		this.turtle = turtle;
		this.known = new ArrayList<ItemStack>();
		if (luaMethods.isEmpty()) addLuaMethods();
	}
	
	@Optional.Method(modid = "ComputerCraft")
    @Override
    public String getType()
    {
        return "chemical";
    }
	
    @Optional.Method(modid = "ComputerCraft")
	@Override
	public String[] getMethodNames() {
		String[] result = new String[luaMethods.size()];
		int i=0;
		for (LuaMethod method:luaMethods)
			result[i++]=method.getMethodName();
		return result;
	}
	
	@Optional.Method(modid = "ComputerCraft")
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
                    throw new LuaException("getMethods does not take any arguments");
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
					throw new LuaException("Invalid Method Name - do not include brackets");
				}else {
                    throw new LuaException("getDetails takes a single argument");
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
                		throw new LuaException("Invalid Slot Number.");
                } else {
                    throw new LuaException("Maximum 1 argument for slot number.");
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
                		throw new LuaException("Invalid Slot Number.");
                } else {
                    throw new LuaException("Maximum 1 argument for slot number.");
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
                		throw new LuaException("Invalid Slot Number.");
                } else {
                    throw new LuaException("Maximum 1 argument for slot number.");
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
        			chemMap.put("chemical",current.getDisplayName());
        			chemMap.put("quantity",1);
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
        				chemMap.put("chemical",MinechemHelper.getChemicalName(chemical));
        				chemMap.put("quantity",chemical.amount);
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
                		throw new LuaException("Invalid Slot Number.");
                } else {
                    throw new LuaException("Maximum 1 argument for slot number.");
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
                		throw new LuaException("Invalid Slot Number.");
                } else {
                    throw new LuaException("Maximum 1 argument for slot number.");
                }
				return null;
            }

			private String getRadioactivity(Integer slot) {
				ItemStack current = turtle.getInventory().getStackInSlot(slot);
				if (Compare.isStackAnElement(current))
				{
					return ElementItem.getElement(current).radioactivity().toString();
				}
				else if (Compare.isStackAMolecule(current))
				{
					return MoleculeItem.getMolecule(current).radioactivity().toString();
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
                		throw new LuaException("Invalid Slot Number.");
                } else {
                    throw new LuaException("Maximum 1 argument for slot number.");
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
                		throw new LuaException("Invalid Slot Number.");
                } else {
                    throw new LuaException("Maximum 1 argument for slot number.");
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
                		throw new LuaException("Invalid Slot Number.");
                } else {
                    throw new LuaException("Maximum 1 argument for slot number.");
                }
				return null;
            }

			private String sync(int slot) throws LuaException {
				ItemStack journal = getJournal(slot);
				if (journal!=null)
				{
					return upload(journal)+" - "+download(journal);
				}
				ItemStack book = getBook(slot);
				if (book!=null)
				{
					return download(book,slot);
				}
				throw new LuaException("Invalid Stack - not a journal or a book");
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
				return download(journal,-1);
			}
			private String download(ItemStack journal, int slot)
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
				if (!(slot<0)) turtle.getInventory().setInventorySlotContents(slot, journal);
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
                	throw new LuaException("Invalid Arguments");
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
    	
    	luaMethods.add(new LuaMethod("getSynthesisRecipe"){
            @Override
            public Object[] call(IComputerAccess computer, ILuaContext context, Object[] args) throws LuaException, InterruptedException{
                if(args.length == 1||args.length == 2) {
                	String UUID = (String)args[0];
                	Integer metadata = args.length==2?getInt(args[1]):0;
                	if (metadata==null||metadata<0)metadata=0;
                	for (ItemStack current:known)
                	{
                		if (GameRegistry.findUniqueIdentifierFor(current.getItem()).toString().equals(UUID)&&current.getItemDamage()==metadata)
                		{
                			SynthesisRecipe output = SynthesisRecipeHandler.instance.getRecipeFromOutput(current);
                			if (output!=null)
                				return new Object[]{synthesisRecipeToMap(output)};
                			else
                				return new Object[]{"No Synthesiser recipe exists for "+UUID+":"+metadata};
                		}
                	}
                	return new Object[]{UUID+":"+metadata+ " is unknown."};
                } else {
                    throw new LuaException("Invalid arguments");
                }
            }

			@Override
			public String getArgs() {
				return "(name,?metadata)";
			}
			
			@Override
			public String[] getDetails() {
				return new String[]{super.getDetails()[0],"Args: Item Name and optional metadata, default 0","Returns: Synthesiser Recipes in table form"};
			}
        });
    	
    	luaMethods.add(new LuaMethod("getSynthesisRecipes"){
            @Override
            public Object[] call(IComputerAccess computer, ILuaContext context, Object[] args) throws LuaException, InterruptedException{
                if(args.length == 0) {
                	int i=1;
                	HashMap<Number,Object> result = new HashMap<Number,Object>();
                	for (ItemStack current:known)
                	{
            			SynthesisRecipe output = SynthesisRecipeHandler.instance.getRecipeFromOutput(current);
            			if (output!=null)
            				result.put(i++,synthesisRecipeToMap(output));
                	}
                	return new Object[]{result};
                } else {
                    throw new LuaException("getSynthesisRecipes does not take any arguments");
                }
            }

			@Override
			public String getArgs() {
				return "()";
			}
			
			@Override
			public String[] getDetails() {
				return new String[]{super.getDetails()[0],"Returns: All Synthesiser Recipes in table form"};
			}
        });
    	
    	luaMethods.add(new LuaMethod("setRecipe"){
            @Override
            public Object[] call(IComputerAccess computer, ILuaContext context, Object[] args) throws LuaException, InterruptedException{
                ForgeDirection dir=null;
            	if(args.length > 0 && args.length < 4)
            	{
            		String UUID = null;
            		dir = getDirForString((String)args[0], turtle);
	                if (dir==null && args.length<3)
	                {
	                	UUID = (String)args[0];
	                	dir = getDirForString("front",turtle);
	                }
	                else if (dir==null)
	                	throw new LuaException("Invalid Arguments");
	                
	                TileEntity te = turtle.getWorld().getTileEntity(turtle.getPosition().posX+dir.offsetX, turtle.getPosition().posY+dir.offsetY, turtle.getPosition().posZ+dir.offsetZ);
	                if (te instanceof SynthesisTileEntity)
	                {
		                Integer metadata = getInt(args[args.length-1]);
		                if (metadata==null||metadata<0)metadata=0;
		                if (UUID==null) UUID = (String)args[1];
		                for (ItemStack current:known)
	                	{
	                		if (GameRegistry.findUniqueIdentifierFor(current.getItem()).toString().equals(UUID)&&current.getItemDamage()==metadata)
	                		{
	                			SynthesisRecipe output = SynthesisRecipeHandler.instance.getRecipeFromOutput(current);
	                			if (output!=null)
	                			{
	                				((SynthesisTileEntity)te).setRecipe(output);
	                				return new Object[]{true};
	                			}
	                			else
	                				return new Object[]{false};
	                		}
	                	}
	                }
            	}

				return new Object[]{false};
            }

			@Override
			public String getArgs() {
				return "(?Direction,name,?metadata)";
			}
			
			@Override
			public String[] getDetails() {
				return new String[]{super.getDetails()[0],"Arg: Optional Direction, defaults to front","Arg: Item name","Arg: Optional metadata, defaults to 0","Returns: boolean success"};
			}
        });
    	
    	//luaMethods.add(new LuaMethod("getState"){
    		
    	//luaMethods.add(new LuaMethod("setRecipe"){
    }
	
    private HashMap<String, Object> synthesisRecipeToMap(SynthesisRecipe recipe)
    {
    	HashMap<String,Object> result = new HashMap<String,Object>();
    	result.put("output", stackToMap(recipe.getOutput()));
    	result.put("shaped", recipe.isShaped());
    	result.put("energyCost", recipe.energyCost());
    	HashMap<Number,Object> inputs = new HashMap<Number,Object>();
    	PotionChemical[] recipeInputs = recipe.isShaped()?recipe.getShapedRecipe():recipe.getShapelessRecipe();
    	int i=1;
    	for (PotionChemical chemical:recipeInputs)
		{
			if (chemical!=null)
			{
				HashMap<String,Object> input = new HashMap<String,Object>();
				if (chemical instanceof Element)
				{
					input.put("element",((Element)chemical).element.getLongName());
					input.put("quantity", chemical.amount);
				}
				else if (chemical instanceof Molecule)
				{
					input.put("element",((Molecule)chemical).molecule.name());
					input.put("quantity", chemical.amount);
				}
				inputs.put(i++, input);
			}		
    	}
    	result.put("ingredients", inputs);
    	return result;
    }
    
    
    
    private HashMap<String,Object> stackToMap(ItemStack stack)
    {
    	HashMap<String,Object> result = new HashMap<String,Object>();
    	result.put("name", GameRegistry.findUniqueIdentifierFor(stack.getItem()).toString());
    	result.put("metadata", stack.getItemDamage());
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
    
    @Optional.Method(modid = "ComputerCraft")
	@Override
	public Object[] callMethod(IComputerAccess computer, ILuaContext context,int method, Object[] arguments) throws LuaException,InterruptedException {
    	return luaMethods.get(method).call(computer, context, arguments);
	}


    @Optional.Method(modid = "ComputerCraft")
    @Override
    public void attach(IComputerAccess computer)
    {

    }

    @Optional.Method(modid = "ComputerCraft")
    @Override
    public void detach(IComputerAccess computer)
    {

    }

    @Optional.Method(modid = "ComputerCraft")
    @Override
    public boolean equals(IPeripheral other)
    {
        return false;
    }
}
