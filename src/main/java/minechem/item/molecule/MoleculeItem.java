package minechem.item.molecule;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import minechem.MinechemItemsRegistration;
import minechem.fluid.FluidHelper;
import minechem.gui.CreativeTabMinechem;
import minechem.item.element.ElementEnum;
import minechem.item.element.ElementItem;
import minechem.potion.PotionPharmacologyEffect;
import minechem.radiation.RadiationEnum;
import minechem.radiation.RadiationFluidTileEntity;
import minechem.radiation.RadiationInfo;
import minechem.reference.Textures;
import minechem.utils.Constants;
import minechem.utils.MinechemUtil;
import minechem.utils.TimeHelper;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MoleculeItem extends Item
{
	public IIcon render_pass1, render_pass2, filledMolecule;

	public MoleculeItem()
	{
		setCreativeTab(CreativeTabMinechem.CREATIVE_TAB_ELEMENTS);
		setHasSubtypes(true);
		setUnlocalizedName("itemMolecule");
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemStack)
	{
		return MinechemUtil.getLocalString(getMolecule(itemStack).getUnlocalizedName());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		itemIcon = ir.registerIcon(Textures.IIcon.FILLED_TESTTUBE);
		render_pass1 = ir.registerIcon(Textures.IIcon.MOLECULE_PASS1);
		render_pass2 = ir.registerIcon(Textures.IIcon.MOLECULE_PASS2);
		filledMolecule = ir.registerIcon(Textures.IIcon.FILLED_MOLECULE);
	}

	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack)
	{
		return getUnlocalizedName() + "." + getMolecule(par1ItemStack).name();
	}

	public String getFormulaWithSubscript(ItemStack itemstack)
	{
		String formula = getMolecule(itemstack).getFormula();
		return MinechemUtil.subscriptNumbers(formula);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add("\u00A79" + getFormulaWithSubscript(itemstack));

		String radioactivityColor;
		RadiationEnum radioactivity = RadiationInfo.getRadioactivity(itemstack);
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

		String radioactiveName = MinechemUtil.getLocalString("element.property." + radioactivity.name());
		String timeLeft = "";
		if (RadiationInfo.getRadioactivity(itemstack) != RadiationEnum.stable && itemstack.getTagCompound() != null)
		{
			long worldTime = player.worldObj.getTotalWorldTime();
			timeLeft = TimeHelper.getTimeFromTicks(RadiationInfo.getRadioactivity(itemstack).getLife() - (worldTime - itemstack.getTagCompound().getLong("decayStart")));
		}
		list.add(radioactivityColor + radioactiveName + (timeLeft.equals("") ? "" : " (" + timeLeft + ")"));
		list.add(getRoomState(itemstack));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs par2CreativeTabs, List par3List)
	{
		for (MoleculeEnum molecule : MoleculeEnum.molecules.values())
		{
			if (molecule != null)
			{
				par3List.add(new ItemStack(item, 1, molecule.id()));
			}
		}
	}

	
	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		TileEntity te = world.getTileEntity(x, y, z);
		boolean result = !world.isRemote;
		if (te != null && te instanceof IFluidHandler  && !player.isSneaking() && !(te instanceof IInventory))
		{
			int filled=0;
			for (int i=0;i<6;i++)
			{
				FluidStack fluidStack = new FluidStack(FluidRegistry.WATER, 125);
				if (getMolecule(stack) != MoleculeEnum.water) fluidStack = new FluidStack(FluidHelper.molecules.get(getMolecule(stack)), 125);
				filled = ((IFluidHandler)te).fill(ForgeDirection.getOrientation(i), fluidStack , false);
				if (filled>0)
				{
					if (result)
						((IFluidHandler) te).fill(ForgeDirection.getOrientation(i), fluidStack, true);
					if (!player.capabilities.isCreativeMode)
						MinechemUtil.incPlayerInventory(stack, -1, player, new ItemStack(MinechemItemsRegistration.element, 1, ElementEnum.heaviestMass));
					return result || stack.stackSize <= 0;
				}
			}
			return result;
		}
		return super.onItemUseFirst(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
	}
	
	public static MoleculeEnum getMolecule(ItemStack itemstack)
	{
		int itemDamage = itemstack.getItemDamage();
		MoleculeEnum mol = MoleculeEnum.getById(itemDamage);
		if (mol == null)
		{
			itemstack.setItemDamage(0);
			mol = MoleculeEnum.getById(0);
		}
		return mol;
	}

	/**
	 * returns the action that specifies what animation to play when the items is being used
	 */
	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.drink;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 16;
	}

	@Override
	public ItemStack onEaten(ItemStack itemStack, World world, EntityPlayer entityPlayer)
	{
		if (!entityPlayer.capabilities.isCreativeMode)
		{
			--itemStack.stackSize;
		}

		if (world.isRemote)

		{
			return itemStack;
		}

		MoleculeEnum molecule = getMolecule(itemStack);
		PotionPharmacologyEffect.triggerPlayerEffect(molecule, entityPlayer);
		world.playSoundAtEntity(entityPlayer, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F); // Thanks mDiyo!
		return itemStack;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
	{
		player.setItemInUse(itemStack, getMaxItemUseDuration(itemStack));

		MovingObjectPosition movingObjectPosition = this.getMovingObjectPositionFromPlayer(world, player, false);
		if (movingObjectPosition == null || player.isSneaking())
		{
			return itemStack;
		}

		if (movingObjectPosition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
		{
			int blockX = movingObjectPosition.blockX;
			int blockY = movingObjectPosition.blockY;
			int blockZ = movingObjectPosition.blockZ;

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
			RadiationInfo radioactivity = ElementItem.getRadiationInfo(itemStack, world);
			long worldtime=world.getTotalWorldTime();
			long leftTime=radioactivity.radioactivity.getLife()-(worldtime-radioactivity.decayStarted);

			if (!player.capabilities.isCreativeMode){
				if (itemStack.stackSize>=8)
                {
					itemStack.stackSize-=8;
				} else
                {
					int needs=8-itemStack.stackSize;
					Set<ItemStack> otherItemsStacks=MinechemUtil.findItemStacks(player.inventory, itemStack.getItem(), itemStack.getItemDamage());
					otherItemsStacks.remove(itemStack);
					int free=0;
					Iterator<ItemStack> it2=otherItemsStacks.iterator();
					while (it2.hasNext())
                    {
						ItemStack stack = it2.next();
						free+=stack.stackSize;
					}
					if (free<needs)
                    {
						return itemStack;
					}
					itemStack.stackSize=0;
					
					Iterator<ItemStack> it=otherItemsStacks.iterator();
					while (it.hasNext())
                    {
						ItemStack stack = it.next();
						RadiationInfo anotherRadiation=ElementItem.getRadiationInfo(stack, world);
						long anotherLeft=anotherRadiation.radioactivity.getLife()-(worldtime-anotherRadiation.decayStarted);
						if (anotherLeft<leftTime)
                        {
							radioactivity=anotherRadiation;
							leftTime=anotherLeft;
						}
						
						if (stack.stackSize>=needs)
                        {
							stack.stackSize-=needs;
							needs=0;
						}else
                        {
							needs-=stack.stackSize;
							stack.stackSize=0;
						}
						
						if (stack.stackSize<=0)
                        {
							MinechemUtil.removeStackInInventory(player.inventory, stack);
						}
						
						if (needs==0)
                        {
							break;
						}
					}
				}
				ItemStack empties=MinechemUtil.addItemToInventory(player.inventory, new ItemStack(MinechemItemsRegistration.element, 8, ElementEnum.heaviestMass));
				MinechemUtil.throwItemStack(world, empties, x, y, z);
			}
			
			Block block = Blocks.flowing_water;
            if (getMolecule(itemStack) != MoleculeEnum.water) block = FluidHelper.moleculeBlocks.get(FluidHelper.molecules.get(getMolecule(itemStack)));
			world.setBlock(x, y, z, block, 0, 3);
			TileEntity tile = world.getTileEntity(x, y, z);
			if (radioactivity.isRadioactive() && tile instanceof RadiationFluidTileEntity)
			{
				((RadiationFluidTileEntity) tile).info = radioactivity;
			}
		}
		return itemStack;
	}

	public static String getRoomState(ItemStack itemstack)
	{
		int id = itemstack.getItemDamage();
		return (MoleculeEnum.molecules.get(id) == null) ? "null" : MoleculeEnum.molecules.get(id).roomState().descriptiveName();
	}

    @Override
    public void onCreated(ItemStack itemStack, World world, EntityPlayer player)
    {
        super.onCreated(itemStack, world, player);
        if (RadiationInfo.getRadioactivity(itemStack) != RadiationEnum.stable && itemStack.stackTagCompound == null)
        {
            RadiationInfo.setRadiationInfo(new RadiationInfo(itemStack, world.getTotalWorldTime(), world.getTotalWorldTime(), world.provider.dimensionId, RadiationInfo.getRadioactivity(itemStack)), itemStack);
        }
    }
}
