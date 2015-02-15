package minechem.achievement;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minechem.chemical.Element;
import minechem.helper.LocalizationHelper;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

/**
 * {@link net.minecraft.stats.Achievement} wrapper for {@link minechem.chemical.Element}s
 */
public class ElementAchievement extends Achievement
{
    private final static String achievementPrefix = "achievement.";
    private final static String defaultElementTitle = "achievement.minechem.element";
    private final static String defaultElementDescription = "achievement.minechem.element.desc";
    private final static Achievement nullAchievement = null;
    
    private final Element element;
    
    public ElementAchievement(Element element, int row, int column, ItemStack displayStack)
    {
        super(achievementPrefix + element.shortName, element.shortName, column, row, displayStack, nullAchievement);
        this.element = element;
    }

    public ElementAchievement(Element element, int row, int column, Item displayItem)
    {
        this(element, column, row, new ItemStack(displayItem));
    }

    public ElementAchievement(Element element, int row, int column, Block displayBlock)
    {
        this(element, column, row, new ItemStack(displayBlock));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public String getDescription()
    {
        return String.format(LocalizationHelper.getLocalString(defaultElementDescription), element.fullName);
    }

    /**
     * Returns the title
     * @return an {@link net.minecraft.util.IChatComponent}
     */
    @Override
    public IChatComponent func_150951_e()
    {
        IChatComponent iChatComponent = new ChatComponentTranslation(defaultElementTitle, element.shortName);
        iChatComponent.getChatStyle().setColor(this.getSpecial() ? EnumChatFormatting.DARK_PURPLE : EnumChatFormatting.GREEN);
        return iChatComponent;
    }
}
