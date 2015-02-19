package minechem.achievement;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minechem.Compendium;
import minechem.chemical.Element;
import minechem.helper.LocalizationHelper;
import minechem.proxy.client.font.Font;
import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

/**
 * {@link net.minecraft.stats.Achievement} wrapper for {@link minechem.chemical.Element}s
 */
public class ElementAchievement extends Achievement implements IAchievementRenderer
{
    private final static String achievementPrefix = "achievement.";
    private final static String defaultElementTitle = "achievement.minechem.element";
    private final static String defaultElementDescription = "achievement.minechem.element.desc";
    private final static Achievement nullAchievement = null;

    private final Element element;
    private static Font regularFont, smallFont;

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

    /**
     * Background colour for the achievement icon
     * @return an int representation of the colour
     */
    @Override
    public int recolourBackground(float greyScale)
    {
        return element.getColour();
    }

    @Override
    public boolean hasSpecialIconRenderer()
    {
        return true;
    }

    @Override
    public void renderIcon(FontRenderer fontRenderer, TextureManager textureManager, ItemStack itemStack, int left, int top)
    {
        if (regularFont == null) regularFont = new Font(fontRenderer);
        if (smallFont == null) smallFont = new Font(fontRenderer).setFontSize(8);
        regularFont.print(element.shortName, left+10 - (element.shortName.length()-1)*5, top+8, Compendium.Color.TrueColor.white, true);
        smallFont.print(element.atomicNumber, left, top, Compendium.Color.TrueColor.white, true);
    }
}
