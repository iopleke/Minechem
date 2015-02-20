package minechem.item.polytool;

import com.google.common.collect.Multimap;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import minechem.Minechem;
import minechem.gui.CreativeTabMinechem;
import minechem.gui.GuiHandler;
import minechem.item.element.ElementEnum;
import minechem.item.element.ElementItem;
import minechem.reference.Textures;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class PolytoolItem extends ItemPickaxe
{

    public static PolytoolItem instance;

    public PolytoolItem()
    {
        super(ToolMaterial.IRON);
        instance = this;
        setCreativeTab(CreativeTabMinechem.CREATIVE_TAB_ITEMS);
        setUnlocalizedName("Polytool");
    }

    public static boolean validAlloyInfusion(ItemStack polytool, ItemStack element)
    {
        if (element.getItem() instanceof ElementItem)
        {
            PolytoolUpgradeType upgrade = PolytoolHelper.getTypeFromElement((ElementItem.getElement(element)), 1);
            if (upgrade instanceof PolytoolTypeAlloy)
            {
                ItemStack toApply = polytool.copy();
                addTypeToNBT(toApply, upgrade);
                if (!(instance.getSwordStr(toApply) > 0 && instance.getPickaxeStr(toApply) > 0 && instance.getStoneStr(toApply) > 0 && instance.getAxeStr(toApply) > 0 && instance.getShovelStr(toApply) > 0))
                {
                    return false;

                }
            }
        }
        return true;

    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World world, EntityPlayer entityPlayer)
    {
        // Copied from journal code
        // I don't know why chunkCoordX is used
        // But LJDP probably knows, and he is smarter than me
        entityPlayer.openGui(Minechem.INSTANCE, GuiHandler.GUI_ID_POLYTOOL, world, entityPlayer.chunkCoordX, entityPlayer.chunkCoordY, entityPlayer.chunkCoordY);
        return par1ItemStack;
    }

    public float getSwordStr(ItemStack stack)
    {
        return this.getDigSpeed(stack, Blocks.web, 0);
    }

    public float getPickaxeStr(ItemStack stack)
    {
        return this.getDigSpeed(stack, Blocks.coal_ore, 0);
    }

    public float getStoneStr(ItemStack stack)
    {
        return this.getDigSpeed(stack, Blocks.stone, 0);
    }

    public float getAxeStr(ItemStack stack)
    {
        return this.getDigSpeed(stack, Blocks.planks, 0);
    }

    public float getShovelStr(ItemStack stack)
    {
        return this.getDigSpeed(stack, Blocks.dirt, 0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir)
    {
        itemIcon = ir.registerIcon(Textures.IIcon.POLYTOOL);
    }

    public static ArrayList<PolytoolUpgradeType> getUpgrades(ItemStack stack)
    {
        ensureNBT(stack);
        ArrayList<PolytoolUpgradeType> toReturn = new ArrayList<PolytoolUpgradeType>();
        NBTTagList list = stack.stackTagCompound.getTagList("Upgrades", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < list.tagCount(); i++)
        {
            NBTTagCompound nbt = list.getCompoundTagAt(i);
            toReturn.add(PolytoolHelper.getTypeFromElement(ElementEnum.getByID(nbt.getInteger("Element")), nbt.getFloat("Power")));
        }
        return toReturn;

    }

    @Override
    public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase)
    {
        par2EntityLivingBase.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) par3EntityLivingBase), getSwordStr(par1ItemStack));

        ArrayList upgrades = getUpgrades(par1ItemStack);
        Iterator iter = upgrades.iterator();
        while (iter.hasNext())
        {
            ((PolytoolUpgradeType) iter.next()).hitEntity(par1ItemStack, par2EntityLivingBase, par3EntityLivingBase);
        }
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack p_150894_1_, World p_150894_2_, Block p_150894_3_, int p_150894_4_, int p_150894_5_, int p_150894_6_, EntityLivingBase p_150894_7_)
    {
        ArrayList upgrades = getUpgrades(p_150894_1_);
        Iterator iter = upgrades.iterator();
        while (iter.hasNext())
        {
            ((PolytoolUpgradeType) iter.next()).onBlockDestroyed(p_150894_1_, p_150894_2_, p_150894_3_, p_150894_4_, p_150894_5_, p_150894_6_, p_150894_7_);
        }
        return true;
    }

    public static void addTypeToNBT(ItemStack stack, PolytoolUpgradeType type)
    {
        ensureNBT(stack);
        NBTTagCompound compound = new NBTTagCompound();
        NBTTagList list = stack.stackTagCompound.getTagList("Upgrades", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < list.tagCount(); i++)
        {
            if (list.getCompoundTagAt(i).getInteger("Element") == type.getElement().atomicNumber())
            {
                list.getCompoundTagAt(i).setFloat("Power", list.getCompoundTagAt(i).getFloat("Power") + type.power);

                return;
            }
        }
        compound.setFloat("Power", type.power);
        compound.setInteger("Element", type.getElement().atomicNumber());
        list.appendTag(compound);

    }

    @Override
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
    {
        ArrayList upgrades = getUpgrades(par1ItemStack);
        Iterator iter = upgrades.iterator();
        while (iter.hasNext())
        {
            ((PolytoolUpgradeType) iter.next()).onTickFull(par1ItemStack, par2World, par3Entity, par4, par5);
        }
    }

    public static void ensureNBT(ItemStack item)
    {
        if (item.stackTagCompound == null)
        {
            item.stackTagCompound = new NBTTagCompound();
        }
        if (!item.stackTagCompound.hasKey("Upgrades"))
        {
            item.stackTagCompound.setTag("Upgrades", new NBTTagList());
        }
        if (!item.stackTagCompound.hasKey("Energy"))
        {
            item.stackTagCompound.setInteger("Energy", 0);
        }
    }

    public static float getPowerOfType(ItemStack item, ElementEnum element)
    {
        ArrayList upgrades = getUpgrades(item);
        Iterator iter = upgrades.iterator();
        while (iter.hasNext())
        {
            PolytoolUpgradeType next = (PolytoolUpgradeType) iter.next();
            if (next.getElement().atomicNumber() == element.atomicNumber())
            {

                return next.power;
            }
        }
        return 0;
    }

    @Override
    public float getDigSpeed(ItemStack stack, Block block, int meta)
    {
        float result = 8F;
        for (Iterator<PolytoolUpgradeType> itr = getUpgrades(stack).iterator(); itr.hasNext(); result += itr.next().getStrVsBlock(stack, block, meta));
        return result;
    }

    @Override
    public Multimap getAttributeModifiers(ItemStack stack)
    {
        Multimap result = super.getAttributeModifiers(stack);
        for (Iterator<PolytoolUpgradeType> itr = getUpgrades(stack).iterator(); itr.hasNext();)
        {
            float modifier = itr.next().getDamageModifier();
            if (modifier!=0F)
            {
                result.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),new AttributeModifier(field_111210_e, "Tool modifier", modifier, 0));
            }
        }
        return result;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List list, boolean par4)
    {
        super.addInformation(par1ItemStack, par2EntityPlayer, list, par4);
    }

}
