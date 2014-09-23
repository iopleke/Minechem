package minechem.item.element;

import java.util.EnumMap;
import java.util.List;

import minechem.MinechemItemsRegistration;
import minechem.Minechem;
import minechem.item.polytool.PolytoolHelper;
import minechem.radiation.RadiationEnum;
import minechem.radiation.RadiationInfo;
import minechem.utils.Constants;
import minechem.utils.EnumColor;
import minechem.utils.MinechemHelper;
import minechem.utils.Reference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ElementItem extends Item // implements IStorageTank
{

    private final static ElementEnum[] elements = ElementEnum.values();
    private final EnumMap<ElementClassificationEnum, Integer> classificationIndexes = new EnumMap<ElementClassificationEnum, Integer>(ElementClassificationEnum.class);
    public final IIcon liquid[] = new IIcon[7], gas[] = new IIcon[7];
    public IIcon solid;

    public ElementItem()
    {
        setCreativeTab(Minechem.CREATIVE_TAB_ELEMENTS);
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
        classificationIndexes.put(ElementClassificationEnum.gas, 1);
        classificationIndexes.put(ElementClassificationEnum.solid, 17);
        classificationIndexes.put(ElementClassificationEnum.liquid, 33);
    }

    public static String getShortName(ItemStack itemstack)
    {
        int atomicNumber = itemstack.getItemDamage();
        return elements[atomicNumber].name();
    }

    public static String getLongName(ItemStack itemstack)
    {
        int atomicNumber = itemstack.getItemDamage();
        return elements[atomicNumber].descriptiveName();
    }

    public static String getClassification(ItemStack itemstack)
    {
        int atomicNumber = itemstack.getItemDamage();
        return elements[atomicNumber].classification().descriptiveName();
    }

    public static String getRoomState(ItemStack itemstack)
    {
        int atomicNumber = itemstack.getItemDamage();
        return elements[atomicNumber].roomState().descriptiveName();
    }

    public static RadiationEnum getRadioactivity(ItemStack itemstack)
    {
        int atomicNumber = itemstack.getItemDamage();
        return elements[atomicNumber].radioactivity();
    }

    public static ElementEnum getElement(ItemStack itemstack)
    {
        return ElementEnum.elements[itemstack.getItemDamage()];
    }

    public static void attackEntityWithRadiationDamage(ItemStack itemstack, int damage, Entity entity)
    {
        entity.attackEntityFrom(DamageSource.generic, damage);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir)
    {
        itemIcon = ir.registerIcon(Reference.FILLED_TESTTUBE_TEX);
        gas[0] = ir.registerIcon(Reference.ELEMENT_GAS1_TEX);
        gas[1] = ir.registerIcon(Reference.ELEMENT_GAS2_TEX);
        gas[2] = ir.registerIcon(Reference.ELEMENT_GAS3_TEX);
        gas[3] = ir.registerIcon(Reference.ELEMENT_GAS4_TEX);
        gas[4] = ir.registerIcon(Reference.ELEMENT_GAS5_TEX);
        gas[5] = ir.registerIcon(Reference.ELEMENT_GAS6_TEX);
        gas[6] = ir.registerIcon(Reference.ELEMENT_GAS7_TEX);
        liquid[0] = ir.registerIcon(Reference.ELEMENT_LIQUID1_TEX);
        liquid[1] = ir.registerIcon(Reference.ELEMENT_LIQUID2_TEX);
        liquid[2] = ir.registerIcon(Reference.ELEMENT_LIQUID3_TEX);
        liquid[3] = ir.registerIcon(Reference.ELEMENT_LIQUID4_TEX);
        liquid[4] = ir.registerIcon(Reference.ELEMENT_LIQUID5_TEX);
        liquid[5] = ir.registerIcon(Reference.ELEMENT_LIQUID6_TEX);
        liquid[6] = ir.registerIcon(Reference.ELEMENT_LIQUID7_TEX);
        solid = ir.registerIcon(Reference.ELEMENT_SOLID_TEX);
    }

    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        return "minechem.itemElement." + getShortName(par1ItemStack);
    }

    @Override
    public String getItemStackDisplayName(ItemStack p_77653_1_)
    {
        return getLongName(p_77653_1_);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemstack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        par3List.add(Constants.TEXT_MODIFIER + "9" + getShortName(itemstack) + " (" + (itemstack.getItemDamage() + 1) + ")");

        String radioactivityColor;
        String timeLeft = getRadioactiveLife(itemstack);
        if (!timeLeft.equals(""))
        {
            timeLeft = "(" + timeLeft + ")";
        }
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
        par3List.add(radioactivityColor + radioactiveName + " " + timeLeft);
        par3List.add(getClassification(itemstack));
        par3List.add(getRoomState(itemstack));

        if (PolytoolHelper.getTypeFromElement(ElementItem.getElement(itemstack), 1) != null)
        {
            // Polytool Detail
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
            {
                String polytoolDesc = PolytoolHelper.getTypeFromElement(ElementItem.getElement(itemstack), 1).getDescription();
                String localizedDesc=StatCollector.translateToLocal("polytool.description."+ElementItem.getShortName(itemstack));
              	 
                 if(!StatCollector.canTranslate("polytool.description."+ElementItem.getShortName(itemstack)))
                 {
                	 localizedDesc = polytoolDesc;
                 }
                 
                par3List.add(EnumColor.AQUA + localizedDesc);

            } else
            {
                par3List.add(EnumColor.DARK_GREEN + MinechemHelper.getLocalString("polytool.information"));
            }
        }

    }

    private String getRadioactiveLife(ItemStack itemstack)
    {
        String timeLeft = "";
        if (getRadioactivity(itemstack) != RadiationEnum.stable && itemstack.getTagCompound() != null)
        {
            NBTTagCompound tagCompound = itemstack.getTagCompound();
            long life = tagCompound.getLong("life");
            String hourabbr = MinechemHelper.getLocalString("minechem.hour.abbr");
            String minabbr = MinechemHelper.getLocalString("minechem.min.abbr");
            String secabbr = MinechemHelper.getLocalString("minechem.sec.abbr");
            
            if (life < Constants.TICKS_PER_DAY)
			{
				int hrs = (int) (life/Constants.TICKS_PER_HOUR);
				life = life - (Constants.TICKS_PER_HOUR * hrs);
				if (hrs>0)
				{
					if(hourabbr.isEmpty() || hourabbr == "minechem.hour.abbr")
						timeLeft = hrs + "hr ";
					else
						timeLeft = hrs + hourabbr + " ";
				}
			}
			if (life < Constants.TICKS_PER_HOUR)
			{
				int mins = (int) (life/Constants.TICKS_PER_MINUTE);
				life = life - (Constants.TICKS_PER_MINUTE * mins);
				if (mins>0)
				{
					if(minabbr.isEmpty() || minabbr == "minechem.min.abbr")
						timeLeft = timeLeft + mins + "m ";
					else
						timeLeft = timeLeft + mins + minabbr + " ";
				}
			}
			if (life < Constants.TICKS_PER_MINUTE)
			{
				int secs = (int) (life/Constants.TICKS_PER_SECOND);
				life = life - (Constants.TICKS_PER_SECOND * secs);
				if (secs>0)
				{
					if(secabbr.isEmpty() || secabbr == "minechem.sec.abbr")
						timeLeft = timeLeft + secs + "s";
					else
						timeLeft = timeLeft + secs + secabbr;
				}
			}
      }
        return timeLeft;
    }

    @Override
    public int getMetadata(int par1)
    {
        return par1;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (ElementEnum element : ElementEnum.values())
        {
            par3List.add(new ItemStack(item, 1, element.ordinal()));
        }
    }

    public static Object createStackOf(ElementEnum element, int amount)
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
                long life = stackTag.getLong("life");
                RadiationInfo info = new RadiationInfo(element, life, lastUpdate, dimensionID, radioactivity);
                return info;
            }
        }
    }

    public static RadiationInfo initiateRadioactivity(ItemStack element, World world)
    {
        RadiationEnum radioactivity = getRadioactivity(element);
        int dimensionID = world.provider.dimensionId;
        long lastUpdate = world.getTotalWorldTime();
        long life = radioactivity.getLife();
        RadiationInfo info = new RadiationInfo(element, life, lastUpdate, dimensionID, radioactivity);
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
        stackTag.setLong("lastUpdate", radiationInfo.lastRadiationUpdate);
        stackTag.setLong("life", radiationInfo.radiationLife);
        stackTag.setInteger("dimensionID", radiationInfo.dimensionID);
        element.setTagCompound(stackTag);
    }

    public static RadiationInfo decay(ItemStack element, World world)
    {
        int atomicMass = element.getItemDamage();
        element.setItemDamage(atomicMass - 1);
        return initiateRadioactivity(element, world);
    }
    /* @Override public int getGas(EnumGas type, Object... data) { if (type == EnumGas.HYDROGEN || type == EnumGas.OXYGEN) return Constants.GAS_PER_VIAL; else return 0; }
     * 
     * @Override public void setGas(EnumGas type, int amount, Object... data) { // Can't store gas here. }
     * 
     * @Override public int getMaxGas(EnumGas type, Object... data) { if (type == EnumGas.HYDROGEN || type == EnumGas.OXYGEN) return Constants.GAS_PER_VIAL; else return 0; }
     * 
     * @Override public int getRate() { return Constants.GAS_PER_VIAL; }
     * 
     * @Override public int addGas(ItemStack itemstack, EnumGas type, int amount) { return amount; }
     * 
     * @Override public int removeGas(ItemStack itemstack, EnumGas type, int amount) { if (!canProvideGas(itemstack, type)) return 0; int usedItems = Math.min(amount / Constants.GAS_PER_VIAL, itemstack.stackSize); itemstack.stackSize -= usedItems; if
     * (itemstack.stackSize < 0) itemstack.stackSize = 0;
     * 
     * return usedItems * Constants.GAS_PER_VIAL; }
     * 
     * @Override public boolean canReceiveGas(ItemStack itemstack, EnumGas type) { return false; }
     * 
     * @Override public boolean canProvideGas(ItemStack itemstack, EnumGas type) { EnumElement element = ItemElement.getElement(itemstack); if (element == EnumElement.H && type == EnumGas.HYDROGEN) return true; if (element == EnumElement.O && type ==
     * EnumGas.OXYGEN) return true; return false; }
     * 
     * @Override public EnumGas getGasType(ItemStack itemstack) { EnumElement element = ItemElement.getElement(itemstack); if (element == EnumElement.H) return EnumGas.HYDROGEN;
     * 
     * if (element == EnumElement.O) return EnumGas.OXYGEN;
     * 
     * return null; }
     * 
     * @Override public void setGasType(ItemStack itemstack, EnumGas type) { // Can't set gas type. } */

}
