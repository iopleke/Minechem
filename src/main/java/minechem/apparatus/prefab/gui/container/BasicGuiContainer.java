package minechem.apparatus.prefab.gui.container;

import java.util.List;

import cofh.lib.gui.GuiBase;
import minechem.apparatus.prefab.gui.tab.PatreonGuiTab;
import minechem.handler.IconHandler;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.inventory.Container;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Mouse;

/**
 *
 * @author jakimfett
 */
public class BasicGuiContainer extends GuiBase
{
    protected IIcon tabIcon;
    protected IIconRegister register;
    protected ResourceLocation backgroundTexture;

    public BasicGuiContainer(Container container)
    {
        super(container);

    }

    @Override
    public void initGui()
    {
        super.initGui();
        addTab(new PatreonGuiTab(this));
    }

    @Override
    public IIcon getIcon(String paramString)
    {
        Mouse.setGrabbed(false);
        return IconHandler.getIcon(paramString);
    }

    @Override
    public void drawTooltip(List<String> paramList)
    {
        drawTooltipHoveringText(paramList, mouseX, mouseY, fontRendererObj);
        this.tooltip.clear();
    }
}
