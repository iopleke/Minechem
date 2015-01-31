package minechem.apparatus.prefab.gui.container;

import codechicken.nei.VisiblityData;
import codechicken.nei.api.INEIGuiHandler;
import codechicken.nei.api.TaggedInventoryArea;
import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.TabBase;
import cpw.mods.fml.common.Optional;
import java.util.List;
import minechem.apparatus.prefab.gui.tab.PatreonGuiTab;
import minechem.handler.IconHandler;
import minechem.helper.GuiIntersectHelper;
import minechem.helper.HTTPHelper;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

/**
 *
 * @author jakimfett
 */
@Optional.Interface(iface = "codechicken.nei.api.INEIGuiHandler", modid = "NotEnoughItems")
public class BasicGuiContainer extends GuiBase implements INEIGuiHandler, GuiYesNoCallback
{
    protected ResourceLocation backgroundTexture;
    private String clickedURI;
    protected IIconRegister register;
    protected IIcon tabIcon;

    public BasicGuiContainer(Container container)
    {
        super(container);

    }

    @Override
    public void confirmClicked(boolean confirm, int id)
    {
        if (id == 0)
        {
            if (confirm)
            {
                HTTPHelper.openURL(this.clickedURI);
            }

            this.clickedURI = null;
            this.mc.displayGuiScreen(this);
        }
    }

    @Override
    public void drawTooltip(List<String> paramList)
    {
        drawTooltipHoveringText(paramList, mouseX, mouseY, fontRendererObj);
        this.tooltip.clear();
    }

    @Override
    public IIcon getIcon(String paramString)
    {
        Mouse.setGrabbed(false);
        return IconHandler.getIcon(paramString);
    }

    @Optional.Method(modid = "NotEnoughItems")
    @Override
    public List<TaggedInventoryArea> getInventoryAreas(GuiContainer gui)
    {
        return null;
    }

    @Optional.Method(modid = "NotEnoughItems")
    @Override
    public Iterable<Integer> getItemSpawnSlots(GuiContainer gui, ItemStack item)
    {
        return null;
    }

    @Optional.Method(modid = "NotEnoughItems")
    @Override
    public boolean handleDragNDrop(GuiContainer gui, int mouseX, int mouseY, ItemStack draggedStack, int button)
    {
        return false;
    }

    @Optional.Method(modid = "NotEnoughItems")
    @Override
    public boolean hideItemPanelSlot(GuiContainer gui, int x, int y, int w, int h)
    {
        if (gui instanceof BasicGuiContainer)
        {
            GuiIntersectHelper item = new GuiIntersectHelper(x, y, w, h);
            BasicGuiContainer container = (BasicGuiContainer) gui;
            for (TabBase tab : tabs)
            {
                GuiIntersectHelper tabRect = new GuiIntersectHelper(tab.currentShiftX + container.guiLeft, tab.currentShiftY + container.guiTop, tab.currentWidth, tab.currentHeight);
                if (item.intersectsWith(tabRect))
                {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        addTab(new PatreonGuiTab(this));
    }

    @Optional.Method(modid = "NotEnoughItems")
    @Override
    public VisiblityData modifyVisiblity(GuiContainer gui, VisiblityData visiblityData)
    {
        return visiblityData;
    }

    @Override
    protected void mouseClicked(int x, int y, int mouseButton)
    {

        TabBase guiTab = getTabAtPosition(mouseX, mouseY);
        Mouse.setGrabbed(false);
        if (guiTab instanceof PatreonGuiTab)
        {
            PatreonGuiTab patreonTab = (PatreonGuiTab) guiTab;

            if (patreonTab.isFullyOpened())
            {
                if (patreonTab.isLinkAtOffsetPosition(x - this.guiLeft, y - this.guiTop))
                {
                    this.clickedURI = patreonTab.getLink();
                    if (this.mc.gameSettings.chatLinksPrompt)
                    {
                        this.mc.displayGuiScreen(new GuiConfirmOpenLink(this, this.clickedURI, 0, false));
                    } else
                    {
                        HTTPHelper.openURL(this.clickedURI);
                    }
                    return;
                }
            }
        }
        super.mouseClicked(x, y, mouseButton);
    }
}
