package minechem.item.element;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import minechem.utils.EnumColour;
import minechem.utils.MinechemUtil;
import minechem.utils.TimeHelper;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
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
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
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
        return atomicNumber == 0 ? MinechemUtil.getLocalString("element.empty") : ElementEnum.getByID(atomicNumber).name();
    }

    public static String getLongName(ItemStack itemstack)
    {
        int atomicNumber = itemstack.getItemDamage();
        String longName = atomicNumber == 0 ? MinechemUtil.getLocalString("element.empty") : MinechemUtil.getLocalString(ElementEnum.getByID(atomicNumber).getUnlocalizedName());
        if (longName.contains("Element."))
        {
            ElementEnum element = ElementEnum.getByID(atomicNumber);
            if (element != null)
            {
                longName = element.getLongName();
            }
        }
        return longName;
    }

    public static String getClassification(ItemStack itemstack)
    {
        int atomicNumber = itemstack.getItemDamage();
        return atomicNumber != 0 ? ElementEnum.getByID(atomicNumber).classification().descriptiveName() : MinechemUtil.getLocalString("element.empty");
    }

    public static String getRoomState(ItemStack itemstack)
    {
        int atomicNumber = itemstack.getItemDamage();
        return atomicNumber != 0 ? ElementEnum.getByID(atomicNumber).roomState().descriptiveName() : MinechemUtil.getLocalString("element.empty");
    }

    public static ElementEnum getElement(ItemStack itemstack)
    {
        return itemstack.getItemDamage() != 0 ? ElementEnum.getByID(itemstack.getItemDamage()) : null;
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
        if (itemstack.getItemDamage() == 0)
        {
            return;
        }

        list.add(Constants.TEXT_MODIFIER + "9" + getShortName(itemstack) + " (" + (itemstack.getItemDamage()) + ")");

        RadiationEnum radioactivity = RadiationInfo.getRadioactivity(itemstack);
        String radioactivityColor = radioactivity.getColour();

        String radioactiveName = MinechemUtil.getLocalString("element.property." + radioactivity.name());
        String timeLeft = "";
        if (RadiationInfo.getRadioactivity(itemstack) != RadiationEnum.stable && itemstack.getTagCompound() != null)
        {
            long worldTime = player.worldObj.getTotalWorldTime();
            timeLeft = TimeHelper.getTimeFromTicks(RadiationInfo.getRadioactivity(itemstack).getLife() - (worldTime - itemstack.getTagCompound().getLong("decayStart")));
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

                list.add(EnumColour.AQUA + localizedDesc);

            } else
            {
                list.add(EnumColour.DARK_GREEN + MinechemUtil.getLocalString("polytool.information"));
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
        list.add(new ItemStack(item, 1, 0));
        for (int i = 1; i <= ElementEnum.heaviestMass; i++)
        {
            if (ElementEnum.getByID(i) == null)
            {
                continue;
            }
            list.add(new ItemStack(item, 1, i));
        }
    }

    public static ItemStack createStackOf(ElementEnum element, int amount)
    {
        return new ItemStack(MinechemItemsRegistration.element, amount, element.atomicNumber());
    }

    public static RadiationInfo getRadiationInfo(ItemStack element, World world)
    {
        RadiationEnum radioactivity = RadiationInfo.getRadioactivity(element);
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
        RadiationEnum radioactivity = RadiationInfo.getRadioactivity(element);
        int dimensionID = world.provider.dimensionId;
        long lastUpdate = world.getTotalWorldTime();
        RadiationInfo info = new RadiationInfo(element, lastUpdate, lastUpdate, dimensionID, radioactivity);
        RadiationInfo.setRadiationInfo(info, element);
        return info;
    }

    public static RadiationInfo decay(ItemStack element, World world)
    {
        int atomicMass = element.getItemDamage();
        element.setItemDamage(atomicMass - 1);
        return initiateRadioactivity(element, world);
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        TileEntity te = world.getTileEntity(x, y, z);
        boolean result = !world.isRemote;
        if (te != null && te instanceof IFluidHandler && !player.isSneaking() && !(te instanceof IInventory))
        {
            if (stack.getItemDamage() != 0)
            {
                int filled = 0;
                for (int i = 0; i < 6; i++)
                {
                    FluidElement fluid = FluidHelper.elements.get(getElement(stack));
                    if (fluid == null)
                    {
                        return super.onItemUseFirst(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
                    }
                    filled = ((IFluidHandler) te).fill(ForgeDirection.getOrientation(i), new FluidStack(fluid, 125), false);
                    if (filled > 0)
                    {
                        if (result)
                        {
                            ((IFluidHandler) te).fill(ForgeDirection.getOrientation(i), new FluidStack(FluidHelper.elements.get(getElement(stack)), 125), true);
                        }
                        if (!player.capabilities.isCreativeMode)
                        {
                            MinechemUtil.incPlayerInventory(stack, -1, player, new ItemStack(MinechemItemsRegistration.element, 1, 0));
                        }
                        return result || stack.stackSize <= 0;
                    }
                }
            } else
            {
                FluidStack drained = null;
                Fluid fluid = MinechemUtil.getFluid((IFluidHandler) te);
                ElementEnum element = MinechemUtil.getElement(fluid);
                if (element != null)
                {
                    for (int i = 0; i < 6; i++)
                    {
                        drained = ((IFluidHandler) te).drain(ForgeDirection.getOrientation(i), new FluidStack(fluid, 125), false);
                        if (drained != null && drained.amount > 0)
                        {
                            if (result)
                            {
                                ((IFluidHandler) te).drain(ForgeDirection.getOrientation(i), new FluidStack(fluid, 125), true);
                            }
                            if (!player.capabilities.isCreativeMode)
                            {
                                MinechemUtil.incPlayerInventory(stack, -1, player, new ItemStack(MinechemItemsRegistration.element, 1, element.atomicNumber()));
                            }
                            return result;
                        }
                    }
                } else
                {
                    MoleculeEnum molecule = MinechemUtil.getMolecule(fluid);
                    if (fluid == FluidRegistry.WATER)
                    {
                        molecule = MoleculeEnum.water;
                    }
                    if (molecule != null)
                    {
                        for (int i = 0; i < 6; i++)
                        {
                            drained = ((IFluidHandler) te).drain(ForgeDirection.getOrientation(i), new FluidStack(fluid, 125), false);
                            if (drained != null && drained.amount > 0)
                            {
                                if (result)
                                {
                                    ((IFluidHandler) te).drain(ForgeDirection.getOrientation(i), new FluidStack(fluid, 125), true);
                                }
                                if (!player.capabilities.isCreativeMode)
                                {
                                    MinechemUtil.incPlayerInventory(stack, -1, player, new ItemStack(MinechemItemsRegistration.molecule, 1, molecule.id()));
                                }
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
        boolean flag = itemStack.getItemDamage() == 0;
        MovingObjectPosition movingObjectPosition = this.getMovingObjectPositionFromPlayer(world, player, flag);
        if (movingObjectPosition == null)
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
                        stack.stackSize = 8;
                        TileEntity tile = world.getTileEntity(blockX, blockY, blockZ);
                        if (tile instanceof RadiationFluidTileEntity && ((RadiationFluidTileEntity) tile).info != null)
                        {
                            RadiationInfo.setRadiationInfo(((RadiationFluidTileEntity) tile).info, stack);
                        }

                        world.setBlockToAir(blockX, blockY, blockZ);
                        world.removeTileEntity(blockX, blockY, blockZ);
                        return fillTube(itemStack, player, stack);
                    }
                }
            } else
            {
                ForgeDirection dir = ForgeDirection.getOrientation(movingObjectPosition.sideHit);
                blockX += dir.offsetX;
                blockY += dir.offsetY;
                blockZ += dir.offsetZ;
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
        } else
        {
            MinechemUtil.incPlayerInventory(itemStack, -8, player, block);
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
            RadiationInfo radioactivity = getRadiationInfo(itemStack, world);
            long worldtime = world.getTotalWorldTime();
            long leftTime = radioactivity.radioactivity.getLife() - (worldtime - radioactivity.decayStarted);
            Fluid fluid = FluidHelper.elements.get(getElement(itemStack));
            if (fluid == null)
            {
                return itemStack;
            }
            if (!player.capabilities.isCreativeMode)
            {
                if (itemStack.stackSize >= 8)
                {
                    itemStack.stackSize -= 8;
                } else
                {
                    int needs = 8 - itemStack.stackSize;
                    Set<ItemStack> otherItemsStacks = MinechemUtil.findItemStacks(player.inventory, itemStack.getItem(), itemStack.getItemDamage());
                    otherItemsStacks.remove(itemStack);
                    int free = 0;
                    Iterator<ItemStack> it2 = otherItemsStacks.iterator();
                    while (it2.hasNext())
                    {
                        ItemStack stack = it2.next();
                        free += stack.stackSize;
                    }
                    if (free < needs)
                    {
                        return itemStack;
                    }
                    itemStack.stackSize = 0;

                    Iterator<ItemStack> it = otherItemsStacks.iterator();
                    while (it.hasNext())
                    {
                        ItemStack stack = it.next();
                        RadiationInfo anotherRadiation = getRadiationInfo(stack, world);
                        long anotherLeft = anotherRadiation.radioactivity.getLife() - (worldtime - anotherRadiation.decayStarted);
                        if (anotherLeft < leftTime)
                        {
                            radioactivity = anotherRadiation;
                            leftTime = anotherLeft;
                        }

                        if (stack.stackSize >= needs)
                        {
                            stack.stackSize -= needs;
                            needs = 0;
                        } else
                        {
                            needs -= stack.stackSize;
                            stack.stackSize = 0;
                        }

                        if (stack.stackSize <= 0)
                        {
                            MinechemUtil.removeStackInInventory(player.inventory, stack);
                        }

                        if (needs == 0)
                        {
                            break;
                        }
                    }
                }
                ItemStack empties = MinechemUtil.addItemToInventory(player.inventory, new ItemStack(MinechemItemsRegistration.element, 8, 0));
                MinechemUtil.throwItemStack(world, empties, x, y, z);
            }

            Block block = FluidHelper.elementsBlocks.get(fluid);
            world.setBlock(x, y, z, block, 0, 3);
            TileEntity tile = world.getTileEntity(x, y, z);
            if (radioactivity.isRadioactive() && tile instanceof RadiationFluidTileEntity)
            {
                ((RadiationFluidTileEntity) tile).info = radioactivity;
            }
        }
        return itemStack;
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
