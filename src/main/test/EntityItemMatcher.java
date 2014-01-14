package pixlepix.minechem.minechem.common.utils;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

import org.easymock.EasyMock;
import org.easymock.IArgumentMatcher;

/**
 * EasyMock argument matcher that matches a supplied EntityItem's ItemStack
 * against an expected value. The other aspects of the EntityItem
 * (such as world and coordinates) are ignored.
 */
public class EntityItemMatcher implements IArgumentMatcher {
    private ItemStack expectedItemStack;

    public EntityItemMatcher(ItemStack expectedItemStack) {
        this.expectedItemStack = expectedItemStack;
    }

    @Override
    public void appendTo(StringBuffer sb) {
        sb.append("EntityItemMatcher(");
        sb.append(expectedItemStack.getDisplayName());
        if (expectedItemStack.stackSize > 1) {
            sb.append("x").append(expectedItemStack.stackSize);
        }
        if (expectedItemStack.hasTagCompound()) {
            sb.append("[");
            sb.append(expectedItemStack.getTagCompound());
            sb.append("]");
        }
        sb.append(")");
    }

    @Override
    public boolean matches(Object obj) {
        EntityItem entityItem = (EntityItem) obj;
        ItemStack itemStack = entityItem.getEntityItem();
        if (!expectedItemStack.isItemEqual(itemStack)) {
            return false;
        }
        if (expectedItemStack.hasTagCompound()) {
            if (!itemStack.hasTagCompound()) {
                return false;
            }
            if (!expectedItemStack.getTagCompound().equals(
                    itemStack.getTagCompound())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Invokes an EasyMock argument matcher that compares an EntityItem's
     * ItemStack against the expected value.
     * 
     * @param expectedItemStack
     *            Expected ItemStack against which an argument EntityItem's
     *            ItemStack will be compared.
     * @return null, as the return value isn't used.
     */
    public static EntityItem eqEntityItem(ItemStack expectedItemStack) {
        EasyMock.reportMatcher(new EntityItemMatcher(expectedItemStack));
        return null;
    }
}
