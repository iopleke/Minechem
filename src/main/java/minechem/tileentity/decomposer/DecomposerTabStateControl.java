package minechem.tileentity.decomposer;

import minechem.ModMinechem;
import minechem.gui.GuiTabStateControl;
import minechem.tileentity.decomposer.DecomposerTileEntity.State;
import minechem.utils.MinechemHelper;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;

public class DecomposerTabStateControl extends GuiTabStateControl
{
    private DecomposerTileEntity decomposer;

    enum TabState
    {
        jammed(MinechemHelper.getLocalString("tab.tooltip.jammed"), 0xAA0000, ModMinechem.ICON_JAMMED), noBottles(MinechemHelper.getLocalString("tab.tooltip.nobottles"), 0xAA0000, ModMinechem.ICON_NO_BOTTLES), powered(MinechemHelper
                .getLocalString("tab.tooltip.powered"), 0x00CC00, null), unpowered(MinechemHelper.getLocalString("tab.tooltip.unpowered"), 0xAA0000, ModMinechem.ICON_NO_ENERGY);
        public String tooltip;
        public int color;
        public Icon icon;
        public ResourceLocation resource;

        private TabState(String tooltip, int color, ResourceLocation resource)
        {
            this.tooltip = tooltip;
            this.color = color;
            this.resource = resource;
        }
    }

    TabState state;

    public DecomposerTabStateControl(Gui gui, DecomposerTileEntity decomposer)
    {
        super(gui);
        this.decomposer = decomposer;
        this.state = TabState.unpowered;
        this.minWidth = 16 + 9;
        this.minHeight = 16 + 10;
        this.maxHeight = this.minHeight;
        this.maxWidth = this.minWidth;
    }

    @Override
    public void update()
    {
        super.update();
        State state = decomposer.getState();
        if (state == State.kProcessJammed)
        {
            this.state = TabState.jammed;
        }
        else if (decomposer.isPowered())
        {
            this.state = TabState.powered;
        }
        else
        {
            this.state = TabState.unpowered;
        }

        this.overlayColor = this.state.color;
    }

    @Override
    public void draw(int x, int y)
    {
        drawBackground(x, y);
        if (this.state.resource != null)
            drawIcon(x + 3, y + 5);
        if (!isFullyOpened())
            return;
    }

    @Override
    public String getTooltip()
    {
        return this.state.tooltip;
    }

    @Override
    public ResourceLocation getIcon()
    {
        return this.state.resource;
    }

}
