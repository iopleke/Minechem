package minechem.item.molecule;

import java.util.List;
import minechem.MinechemItemsRegistration;
import minechem.Settings;
import minechem.fluid.FluidDispenseHelper;
import minechem.fluid.FluidHelper;
import minechem.fluid.FluidMolecule;
import minechem.item.ChemicalTubeItem;
import minechem.item.element.ElementItem;
import minechem.item.polytool.PolytoolHelper;
import minechem.potion.PharmacologyEffect;
import minechem.potion.PharmacologyEffectRegistry;
import minechem.radiation.RadiationEnum;
import minechem.radiation.RadiationInfo;
import minechem.reference.Textures;
import minechem.utils.EnumColour;
import minechem.utils.MinechemUtil;
import minechem.utils.TimeHelper;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
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
import org.lwjgl.input.Keyboard;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MoleculeItem extends ChemicalTubeItem
{
    public IIcon render_pass1, render_pass2, filledMolecule;

    public MoleculeItem()
    {
        setUnlocalizedName("itemMolecule");
    }

    @Override
    public String getItemStackDisplayName(ItemStack itemStack)
    {
        return MinechemUtil.getLocalString(getMolecule(itemStack).getUnlocalizedName(), true);
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
        list.add(getRoomState(itemstack));
        MoleculeEnum molecule = MoleculeEnum.getById(itemstack.getItemDamage());
        if (PharmacologyEffectRegistry.hasEffect(molecule) && Settings.displayMoleculeEffects)
        {

            if (PolytoolHelper.getTypeFromElement(ElementItem.getElement(itemstack), 1) != null)
            {
                // Polytool Detail
                if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
                {
                    for (PharmacologyEffect effect : PharmacologyEffectRegistry.getEffects(molecule))
                    {
                        list.add(effect.getColour() + effect.toString());
                    }

                } else
                {
                    list.add(EnumColour.DARK_GREEN + MinechemUtil.getLocalString("effect.information", true));
                }
            }
        }
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
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        TileEntity te = world.getTileEntity(x, y, z);
        boolean result = !world.isRemote;
        if ((te != null) && (te instanceof IFluidHandler) && !player.isSneaking() && !(te instanceof IInventory))
        {
            int filled = 0;
            for (int i = 0; i < 6; i++)
            {
                FluidStack fluidStack = new FluidStack(FluidRegistry.WATER, 125);
                if (getMolecule(stack) != MoleculeEnum.water)
                {
                    FluidMolecule fluid = FluidHelper.molecules.get(getMolecule(stack));
                    if (fluid == null)
                    {
                        return super.onItemUseFirst(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
                    }
                    fluidStack = new FluidStack(fluid, 125);

                }
                filled = ((IFluidHandler) te).fill(ForgeDirection.getOrientation(i), fluidStack, false);
                if (filled > 0)
                {
                    if (result)
                    {
                        ((IFluidHandler) te).fill(ForgeDirection.getOrientation(i), fluidStack, true);
                    }
                    if (!player.capabilities.isCreativeMode)
                    {
                        MinechemUtil.incPlayerInventory(stack, -1, player, new ItemStack(MinechemItemsRegistration.element, 1, 0));
                    }
                    return result || (stack.stackSize <= 0);
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
        PharmacologyEffectRegistry.applyEffect(molecule, entityPlayer);
        world.playSoundAtEntity(entityPlayer, "random.burp", 0.5F, (world.rand.nextFloat() * 0.1F) + 0.9F); // Thanks mDiyo!
        return itemStack;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        MovingObjectPosition movingObjectPosition = this.getMovingObjectPositionFromPlayer(world, player, false);
        if (movingObjectPosition == null)
        {
            player.setItemInUse(itemStack, getMaxItemUseDuration(itemStack));
            return itemStack;
        }

        return FluidDispenseHelper.dispenseOnItemUse(itemStack, world, player, movingObjectPosition, false);
    }

    public static String getRoomState(ItemStack itemstack)
    {
        int id = itemstack.getItemDamage();
        return (MoleculeEnum.molecules.get(id) == null) ? "null" : MoleculeEnum.molecules.get(id).roomState().descriptiveName();
    }
}
