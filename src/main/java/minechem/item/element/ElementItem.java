package minechem.item.element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import minechem.MinechemItemsRegistration;
import minechem.fluid.FluidDispenseHelper;
import minechem.fluid.FluidElement;
import minechem.fluid.FluidHelper;
import minechem.item.ChemicalRoomStateEnum;
import minechem.item.ChemicalTubeItem;
import minechem.item.IDescriptiveName;
import minechem.item.molecule.MoleculeEnum;
import minechem.item.polytool.PolytoolHelper;
import minechem.radiation.RadiationEnum;
import minechem.radiation.RadiationInfo;
import minechem.reference.Textures;
import minechem.utils.Constants;
import minechem.utils.EnumColour;
import minechem.utils.MinechemUtil;
import minechem.utils.TimeHelper;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;
import org.lwjgl.input.Keyboard;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ElementItem extends ChemicalTubeItem
{

    // private final static ElementEnum[] elements = ElementEnum.elements;
    private final Map<IDescriptiveName, Integer> classificationIndexes = new HashMap<IDescriptiveName, Integer>();
    public final IIcon liquid[] = new IIcon[7], gas[] = new IIcon[7];
    public IIcon solid;

    public ElementItem()
    {
        setUnlocalizedName("itemElement");
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
        String longName = atomicNumber == 0 ? MinechemUtil.getLocalString("element.empty") : MinechemUtil.getLocalString(ElementEnum.getByID(atomicNumber).getUnlocalizedName(), true);
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

        String radioactiveName = MinechemUtil.getLocalString("element.property." + radioactivity.name(), true);
        String timeLeft = "";
        if ((RadiationInfo.getRadioactivity(itemstack) != RadiationEnum.stable) && (itemstack.getTagCompound() != null))
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

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        TileEntity te = world.getTileEntity(x, y, z);
        boolean result = !world.isRemote;
        if ((te != null) && (te instanceof IFluidHandler) && !player.isSneaking() && !(te instanceof IInventory))
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
                        return result || (stack.stackSize <= 0);
                    }
                }
            } else
            {
                FluidStack drained = null;
                Fluid fluid = MinechemUtil.getFluid((IFluidHandler) te);
                ElementEnum element = (ElementEnum) MinechemUtil.getChemical(fluid);
                if (element != null)
                {
                    for (int i = 0; i < 6; i++)
                    {
                        drained = ((IFluidHandler) te).drain(ForgeDirection.getOrientation(i), new FluidStack(fluid, 125), false);
                        if ((drained != null) && (drained.amount > 0))
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
                    MoleculeEnum molecule = (MoleculeEnum) MinechemUtil.getChemical(fluid);
                    if (molecule != null)
                    {
                        for (int i = 0; i < 6; i++)
                        {
                            drained = ((IFluidHandler) te).drain(ForgeDirection.getOrientation(i), new FluidStack(fluid, 125), false);
                            if ((drained != null) && (drained.amount > 0))
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
        boolean isEmptyTube = itemStack.getItemDamage() == 0;
        MovingObjectPosition movingObjectPosition = getMovingObjectPositionFromPlayer(world, player, isEmptyTube);
        return FluidDispenseHelper.dispenseOnItemUse(itemStack, world, player, movingObjectPosition, isEmptyTube);
    }
}
