package minechem.apparatus.prefab.gui.container;

import cofh.core.gui.element.TabInfo;
import cofh.lib.gui.GuiBase;
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
        TabInfo infoTab = new TabInfo(this, 0, "This is a test");
        addTab(infoTab);
    }

    @Override
    public IIcon getIcon(String paramString)
    {
        Mouse.setGrabbed(false);
        return IconHandler.getIcon(paramString);
    }
}
