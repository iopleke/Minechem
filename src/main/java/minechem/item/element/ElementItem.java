package minechem.item.element;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import minechem.MinechemItemsRegistration;
import minechem.fluid.FluidElement;
import minechem.fluid.FluidHelper;
import minechem.gui.CreativeTabMinechem;
import minechem.item.ChemicalRoomStateEnum;
import minechem.item.IDescriptiveName;
import minechem.item.MinechemChemicalType;
import minechem.item.molecule.MoleculeEnum;
import minechem.item.polytool.PolytoolHelper;
import minechem.radiation.RadiationEnum;
import minechem.radiation.RadiationFluidTileEntity;
import minechem.radiation.RadiationInfo;
import minechem.reference.Textures;
import minechem.utils.Constants;
import minechem.utils.EnumColor;
import minechem.utils.MinechemHelper;
import minechem.utils.MinechemUtil;
import minechem.utils.TimeHelper;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import org.lwjgl.input.Keyboard;

public class ElementItem extends Item
{

	//private final static ElementEnum[] elements = ElementEnum.elements;
	private final Map<IDescriptiveName, Integer> classificationIndexes = new HashMap<IDescriptiveName, Integer>();
	public final IIcon liquid[] = new IIcon[7], gas[] = new IIcon[7];
	public IIcon solid;

	public ElementItem()
	{
		setCreativeTab(CreativeTabMinechem.CREATIVE_TAB_ELEMENTS);
		setUnlocalizedName("itemElement");
		setHasSubtypes(true);
		classificationIndexes.put(ElementClassificationEnum.nonmetal, 0);
		classificationIndexes.put(ElementClassificationEnum.halogen, 1);
		classificationIndexes.put(ElementClassificationEnum.inertGas, 2);
		classificationIndexes.put(ElementClassificationEnum.semimetallic, 3);
		classificationIndexes.put(ElementClassificationEnum.otherMetal, 4);
		classificationIndexes.put(ElementClassificationEnum.alkaliMetal, 5);
		classificationIndexes.put(ElementClassificationEnum.alkalineEarthMetal, 6);
		classificationIndexes.put(ElementClassificationEnum.transitionMetal, 7);
		classificationIndexes.put(ElementClassificationEnum.lanthanide, 8);
		classificationIndexes.put(ElementClassificationEnum.actinide, 9);
		classificationIndexes.put(ChemicalRoomStateEnum.gas, 1);
		classificationIndexes.put(ChemicalRoomStateEnum.solid, 17);
		classificationIndexes.put(ChemicalRoomStateEnum.liquid, 33);
	}

	public static String getShortName(ItemStack itemstack)
	{
		int atomicNumber = itemstack.getItemDamage();
		return atomicNumber < ElementEnum.heaviestMass ? ElementEnum.getByID(atomicNumber).name() : MinechemHelper.getLocalString("element.empty");
	}

	public static String getLongName(ItemStack itemstack)
	{
		int atomicNumber = itemstack.getItemDamage();
		String longName = atomicNumber < ElementEnum.heaviestMass ? MinechemHelper.getLocalString(ElementEnum.getByID(atomicNumber).getUnlocalizedName()) : MinechemHelper.getLocalString("element.empty");
		if (longName.contains("Element."))
		{
			longName = ElementEnum.getByID(atomicNumber).getLongName();
		}
		return longName;
	}

	public static String getClassification(ItemStack itemstack)
	{
		int atomicNumber = itemstack.getItemDamage();
		return atomicNumber < ElementEnum.heaviestMass ? ElementEnum.getByID(atomicNumber).classification().descriptiveName() : MinechemHelper.getLocalString("element.empty");
	}

	public static String getRoomState(ItemStack itemstack)
	{
		int atomicNumber = itemstack.getItemDamage();
		return atomicNumber < ElementEnum.heaviestMass ? ElementEnum.getByID(atomicNumber).roomState().descriptiveName() : MinechemHelper.getLocalString("element.empty");
	}

	public static RadiationEnum getRadioactivity(ItemStack itemstack)
	{
		int id = itemstack.getItemDamage();
		Item item = itemstack.getItem();
		if (item == MinechemItemsRegistration.element)
		{
			return id < ElementEnum.heaviestMass ? ElementEnum.getByID(id).radioactivity() : RadiationEnum.stable;
		} else if (item == MinechemItemsRegistration.molecule)
		{
			if (id >= MoleculeEnum.molecules.size() || MoleculeEnum.molecules.get(id) == null)
			{
				return RadiationEnum.stable;
			}
			return MoleculeEnum.molecules.get(id).radioactivity();
		}
		return RadiationEnum.stable;
	}

	public static ElementEnum getElement(ItemStack itemstack)
	{
		return itemstack.getItemDamage() < ElementEnum.heaviestMass ? ElementEnum.getByID(itemstack.getItemDamage()) : null;
	}

	public static void attackEntityWithRadiationDamage(ItemStack itemstack, int damage, Entity entity)
	{
		entity.attackEntityFrom(DamageSource.generic, damage);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		itemIcon = ir.registerIcon(Textures.IIcon.FILLED_TESTTUBE);
		gas[0] = ir.registerIcon(Textures.IIcon.ELEMENT_GAS1);
		gas[1] = ir.registerIcon(Textures.IIcon.ELEMENT_GAS2);
		gas[2] = ir.registerIcon(Textures.IIcon.ELEMENT_GAS3);
		gas[3] = ir.registerIcon(Textures.IIcon.ELEMENT_GAS4);
		gas[4] = ir.registerIcon(Textures.IIcon.ELEMENT_GAS5);
		gas[5] = ir.registerIcon(Textures.IIcon.ELEMENT_GAS6);
		gas[6] = ir.registerIcon(Textures.IIcon.ELEMENT_GAS7);
		liquid[0] = ir.registerIcon(Textures.IIcon.ELEMENT_LIQUID1);
		liquid[1] = ir.registerIcon(Textures.IIcon.ELEMENT_LIQUID2);
		liquid[2] = ir.registerIcon(Textures.IIcon.ELEMENT_LIQUID3);
		liquid[3] = ir.registerIcon(Textures.IIcon.ELEMENT_LIQUID4);
		liquid[4] = ir.registerIcon(Textures.IIcon.ELEMENT_LIQUID5);
		liquid[5] = ir.registerIcon(Textures.IIcon.ELEMENT_LIQUID6);
		liquid[6] = ir.registerIcon(Textures.IIcon.ELEMENT_LIQUID7);
		solid = ir.registerIcon(Textures.IIcon.ELEMENT_SOLID);
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack)
	{
		return "minechem.itemElement." + getShortName(itemStack);
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemStack)
	{
		return getLongName(itemStack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		if (itemstack.getItemDamage() == ElementEnum.heaviestMass)
		{
			return;
		}

		list.add(Constants.TEXT_MODIFIER + "9" + getShortName(itemstack) + " (" + (itemstack.getItemDamage() + 1) + ")");

		String radioactivityColor;
		RadiationEnum radioactivity = getRadioactivity(itemstack);
		switch (radioactivity)
		{
			case stable:
				radioactivityColor = Constants.TEXT_MODIFIER + "7";
				break;
			case hardlyRadioactive:
				radioactivityColor = Constants.TEXT_MODIFIER + "a";
				break;
			case slightlyRadioactive:
				radioactivityColor = Constants.TEXT_MODIFIER + "2";
				break;
			case radioactive:
				radioactivityColor = Constants.TEXT_MODIFIER + "e";
				break;
			case highlyRadioactive:
				radioactivityColor = Constants.TEXT_MODIFIER + "6";
				break;
			case extremelyRadioactive:
				radioactivityColor = Constants.TEXT_MODIFIER + "4";
				break;
			default:
				radioactivityColor = "";
				break;
		}

		String radioactiveName = MinechemHelper.getLocalString("element.property." + radioactivity.name());
		String timeLeft = "";
		if (getRadioactivity(itemstack) != RadiationEnum.stable && itemstack.getTagCompound() != null)
		{
			long worldTime = player.worldObj.getTotalWorldTime();
			timeLeft = TimeHelper.getTimeFromTicks(getRadioactivity(itemstack).getLife() - (worldTime - itemstack.getTagCompound().getLong("decayStart")));
		}
		list.add(radioactivityColor + radioactiveName + (timeLeft.equals("") ? "" : " (" + timeLeft + ")"));
		list.add(getClassification(itemstack));
		list.add(getRoomState(itemstack));

		if (PolytoolHelper.getTypeFromElement(ElementItem.getElement(itemstack), 1) != null)
		{
			// Polytool Detail
			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
			{
				String polytoolDesc = PolytoolHelper.getTypeFromElement(ElementItem.getElement(itemstack), 1).getDescription();
				String localizedDesc = StatCollector.translateToLocal("polytool.description." + ElementItem.getShortName(itemstack));

				if (!StatCollector.canTranslate("polytool.description." + ElementItem.getShortName(itemstack)))
				{
					localizedDesc = polytoolDesc;
				}

				list.add(EnumColor.AQUA + localizedDesc);

			} else
			{
				list.add(EnumColor.DARK_GREEN + MinechemHelper.getLocalString("polytool.information"));
			}
		}

	}

	@Override
	public int getMetadata(int par1)
	{
		return par1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTabs, List list)
	{
		list.add(new ItemStack(item, 1, ElementEnum.heaviestMass));
		for (int id = 0; id < ElementEnum.heaviestMass; id++)
		{
			ElementEnum element = ElementEnum.getByID(id);
			if (element != null)
			{
				list.add(new ItemStack(item, 1, id));
			}
		}
	}

	public static ItemStack createStackOf(ElementEnum element, int amount)
	{
		return new ItemStack(MinechemItemsRegistration.element, amount, element.ordinal());
	}

	public static RadiationInfo getRadiationInfo(ItemStack element, World world)
	{
		RadiationEnum radioactivity = getRadioactivity(element);
		if (radioactivity == RadiationEnum.stable)
		{
			return new RadiationInfo(element, radioactivity);
		} else
		{
			NBTTagCompound stackTag = element.getTagCompound();
			if (stackTag == null)
			{
				return initiateRadioactivity(element, world);
			} else
			{
				int dimensionID = stackTag.getInteger("dimensionID");
				long lastUpdate = stackTag.getLong("lastUpdate");
				long decayStart = stackTag.getLong("decayStart");
				RadiationInfo info = new RadiationInfo(element, decayStart, lastUpdate, dimensionID, radioactivity);
				return info;
			}
		}
	}

	public static RadiationInfo initiateRadioactivity(ItemStack element, World world)
	{
		RadiationEnum radioactivity = getRadioactivity(element);
		int dimensionID = world.provider.dimensionId;
		long lastUpdate = world.getTotalWorldTime();
		RadiationInfo info = new RadiationInfo(element, lastUpdate, lastUpdate, dimensionID, radioactivity);
		setRadiationInfo(info, element);
		return info;
	}

	public static void setRadiationInfo(RadiationInfo radiationInfo, ItemStack element)
	{
		NBTTagCompound stackTag = element.getTagCompound();
		if (stackTag == null)
		{
			stackTag = new NBTTagCompound();
		}
		stackTag.setLong("lastUpdate", radiationInfo.lastDecayUpdate);
		stackTag.setLong("decayStart", radiationInfo.decayStarted);
		stackTag.setInteger("dimensionID", radiationInfo.dimensionID);
		element.setTagCompound(stackTag);
	}

	public static RadiationInfo decay(ItemStack element, World world)
	{
		int atomicMass = element.getItemDamage();
		element.setItemDamage(atomicMass - 1);
		return initiateRadioactivity(element, world);
	}
	
	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		TileEntity te = world.getTileEntity(x, y, z);
		boolean result = !world.isRemote;
		if (te!=null && te instanceof IFluidHandler)
		{
			if (stack.getItemDamage()!=ElementEnum.heaviestMass)
			{
				int filled=0;
				for (int i=0;i<6;i++)
				{
					filled = ((IFluidHandler)te).fill(ForgeDirection.getOrientation(i), new FluidStack(FluidHelper.elements.get(getElement(stack)),125), false);
					if (filled>0)
					{
						if (result)
							((IFluidHandler)te).fill(ForgeDirection.getOrientation(i), new FluidStack(FluidHelper.elements.get(getElement(stack)),125), true);
						MinechemUtil.incPlayerInventory(stack,-1,player,new ItemStack(MinechemItemsRegistration.element, 1, ElementEnum.heaviestMass));
						return result;
					}
				}
			}
			else
			{
				FluidStack drained=null;
				Fluid fluid = MinechemUtil.getFluid((IFluidHandler)te);
				ElementEnum element = MinechemUtil.getElement(fluid);
				if (element!=null)
				{
					for (int i=0;i<6;i++)
					{
						drained = ((IFluidHandler)te).drain(ForgeDirection.getOrientation(i), new FluidStack(fluid,125), false);
						if (drained!=null && drained.amount>0)
						{
							if (result)
								((IFluidHandler)te).drain(ForgeDirection.getOrientation(i), new FluidStack(fluid,125), true);
							MinechemUtil.incPlayerInventory(stack,-1,player,new ItemStack(MinechemItemsRegistration.element, 1, element.ordinal()));
							return result;
						}
					}
				}
				else
				{
					MoleculeEnum molecule = MinechemUtil.getMolecule(fluid);
					if (molecule!=null)
					{
						for (int i=0;i<6;i++)
						{
							drained = ((IFluidHandler)te).drain(ForgeDirection.getOrientation(i), new FluidStack(fluid,125), false);
							if (drained!=null && drained.amount>0)
							{
								if (result)
									((IFluidHandler)te).drain(ForgeDirection.getOrientation(i), new FluidStack(fluid,125), true);
								MinechemUtil.incPlayerInventory(stack,-1,player,new ItemStack(MinechemItemsRegistration.molecule, 1, molecule.id()));
								return result;
							}
						}
					}
				}
			}
			return result;
		}
		return super.onItemUseFirst(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
		
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
	{
		boolean flag = itemStack.getItemDamage() == ElementEnum.heaviestMass;
		MovingObjectPosition movingObjectPosition = this.getMovingObjectPositionFromPlayer(world, player, flag);
		if (movingObjectPosition == null||itemStack.stackSize<8)
		{
			return itemStack;
		}

		if (movingObjectPosition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
		{
			int blockX = movingObjectPosition.blockX;
			int blockY = movingObjectPosition.blockY;
			int blockZ = movingObjectPosition.blockZ;

			Block block = world.getBlock(blockX, blockY, blockZ);

			if (flag)
			{
				MinechemChemicalType chemical = MinechemUtil.getChemical(block);
				if (chemical != null && MinechemUtil.canDrain(world, block, blockX, blockY, blockZ))
				{
					ItemStack stack = MinechemUtil.createItemStack(chemical, 1);
					
					if (stack != null)
					{
						stack.stackSize=8;
						TileEntity tile = world.getTileEntity(blockX, blockY, blockZ);
						if (tile instanceof RadiationFluidTileEntity && ((RadiationFluidTileEntity) tile).info != null)
						{
							setRadiationInfo(((RadiationFluidTileEntity) tile).info, stack);
						}

						world.setBlockToAir(blockX, blockY, blockZ);
						world.removeTileEntity(blockX, blockY, blockZ);
						return fillTube(itemStack, player, stack);
					}
				}
			} else
			{
				ForgeDirection dir = ForgeDirection.getOrientation(movingObjectPosition.sideHit);
				blockX+=dir.offsetX;
				blockY+=dir.offsetY;
				blockZ+=dir.offsetZ;
				if (!player.canPlayerEdit(blockX, blockY, blockZ, movingObjectPosition.sideHit, itemStack))
				{
					return itemStack;
				}

				return emptyTube(itemStack, player, world, blockX, blockY, blockZ);
			}
		}

		return itemStack;
	}

	private ItemStack fillTube(ItemStack itemStack, EntityPlayer player, ItemStack block)
	{
		if (player.capabilities.isCreativeMode)
		{
			return itemStack;
		} else if ((itemStack.stackSize-=8) <= 0)
		{
			return block;
		} else
		{
			if (!player.inventory.addItemStackToInventory(block))
			{
				player.dropPlayerItemWithRandomChoice(block, false);
			}
		}
		return itemStack;
	}

	private ItemStack emptyTube(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z)
	{
		if (!world.isAirBlock(x, y, z) && !world.getBlock(x, y, z).getMaterial().isSolid())
		{
			Block sourceBlock = world.getBlock(x, y, z);
			int metadata = world.getBlockMetadata(x, y, z);
			sourceBlock.harvestBlock(world, player, x, y, z, metadata);
			sourceBlock.breakBlock(world, x, y, z, sourceBlock, metadata);
			world.setBlockToAir(x, y, z);
		}

		if (world.isAirBlock(x, y, z))
		{
			Block block = FluidHelper.elementsBlocks.get(FluidHelper.elements.get(getElement(itemStack)));
			world.setBlock(x, y, z, block, 0, 3);
			RadiationEnum radioactivity = ElementEnum.elements.get(itemStack.getItemDamage()).radioactivity();
			TileEntity tile = world.getTileEntity(x, y, z);
			if (radioactivity != RadiationEnum.stable && tile instanceof RadiationFluidTileEntity)
			{
				((RadiationFluidTileEntity) tile).info = getRadiationInfo(itemStack, world);
			}
			if (player.capabilities.isCreativeMode)
			{
				return itemStack;
			} else if ((itemStack.stackSize-=8) <= 0)
			{
				return new ItemStack(MinechemItemsRegistration.element, 8, ElementEnum.heaviestMass);
			} else
			{
				if (!player.inventory.addItemStackToInventory(new ItemStack(MinechemItemsRegistration.element, 8, ElementEnum.heaviestMass)))
				{
					player.dropPlayerItemWithRandomChoice(new ItemStack(MinechemItemsRegistration.element, 8, ElementEnum.heaviestMass), false);
				}
			}
		}
		return itemStack;
	}

	@Override
	public void onCreated(ItemStack itemStack, World world, EntityPlayer player)
	{
		super.onCreated(itemStack, world, player);
		if (getRadioactivity(itemStack) != RadiationEnum.stable)
		{
			setRadiationInfo(new RadiationInfo(itemStack, world.getTotalWorldTime(), world.getTotalWorldTime(), world.provider.dimensionId, getRadioactivity(itemStack)), itemStack);
		}
	}
}
