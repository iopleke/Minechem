package ljdp.minechem.common.items;

import java.util.ArrayList;
import java.util.List;

import ljdp.minechem.api.core.EnumMolecule;
import ljdp.minechem.common.ModMinechem;
import ljdp.minechem.common.utils.ConstantValue;
import ljdp.minechem.common.utils.MinechemHelper;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMolecule extends Item {
    public Icon render_pass1, render_pass2, filledMolecule;

    public ItemMolecule(int par1) {
        super(par1);
        setCreativeTab(ModMinechem.minechemTab);
        setHasSubtypes(true);
        setUnlocalizedName("minechem.itemMolecule");
    }

    @Override
    public String getItemDisplayName(ItemStack par1ItemStack) {
        int itemDamage = par1ItemStack.getItemDamage();
        return EnumMolecule.getById(itemDamage).descriptiveName();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister ir) {
        itemIcon = ir.registerIcon(ConstantValue.FILLED_TESTTUBE_TEX);
        render_pass1 = ir.registerIcon(ConstantValue.MOLECULE_PASS1_TEX);
        render_pass2 = ir.registerIcon(ConstantValue.MOLECULE_PASS2_TEX);
        filledMolecule = ir.registerIcon(ConstantValue.FILLED_MOLECULE_TEX);
    }

    public ArrayList<ItemStack> getElements(ItemStack itemstack) {
        EnumMolecule molecule = EnumMolecule.getById(itemstack.getItemDamage());
        return MinechemHelper.convertChemicalsIntoItemStacks(molecule.components());
    }

    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack) {
        return getUnlocalizedName() + "." + getMolecule(par1ItemStack).name();
    }

    public String getFormula(ItemStack itemstack) {
        ArrayList<ItemStack> components = getElements(itemstack);
        String formula = "";
        for (ItemStack component : components) {
            if (component.getItem() instanceof ItemElement) {
                formula += ItemElement.getShortName(component);
                if (component.stackSize > 1)
                    formula += component.stackSize;
            } else if (component.getItem() instanceof ItemMolecule) {
                if (component.stackSize > 1)
                    formula += "(";
                formula += getFormula(component);
                if (component.stackSize > 1)
                    formula += ")" + component.stackSize;
            }
        }
        return formula;
    }

    public String getFormulaWithSubscript(ItemStack itemstack) {
        String formula = getFormula(itemstack);
        return subscriptNumbers(formula);
    }

    private static String subscriptNumbers(String string) {
        string = string.replace('0', '\u2080');
        string = string.replace('1', '\u2081');
        string = string.replace('2', '\u2082');
        string = string.replace('3', '\u2083');
        string = string.replace('4', '\u2084');
        string = string.replace('5', '\u2085');
        string = string.replace('6', '\u2086');
        string = string.replace('7', '\u2087');
        string = string.replace('8', '\u2088');
        string = string.replace('9', '\u2089');
        return string;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        par3List.add("\u00A79" + getFormulaWithSubscript(par1ItemStack));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(int itemID, CreativeTabs par2CreativeTabs, List par3List) {
        for (EnumMolecule molecule : EnumMolecule.molecules) {
            par3List.add(new ItemStack(itemID, 1, molecule.id()));
        }
    }

    public static EnumMolecule getMolecule(ItemStack itemstack) {
        return EnumMolecule.getById(itemstack.getItemDamage());
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        return EnumAction.drink;
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return 16;
    }

    @Override
    public ItemStack onEaten(ItemStack itemStack, World world, EntityPlayer entityPlayer) {

        --itemStack.stackSize;

        if (world.isRemote)

            return itemStack;

        EnumMolecule molecule = getMolecule(itemStack);
        MinechemHelper.triggerPlayerEffect(molecule, entityPlayer);

        return itemStack;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack));
        return par1ItemStack;
    }

    /**
     * Returns True is the item is renderer in full 3D when hold.
     */
    public boolean isFull3D() {
        return true;
    }

}
