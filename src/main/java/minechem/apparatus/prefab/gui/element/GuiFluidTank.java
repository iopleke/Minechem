package minechem.apparatus.prefab.gui.element;

import java.util.ArrayList;
import java.util.List;
import minechem.helper.ColourHelper;
import minechem.helper.LocalizationHelper;
import minechem.reference.Compendium;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import org.lwjgl.opengl.GL11;

/**
 * A tank to display in a GUI
 *
 * @author way2muchnoise
 */
public class GuiFluidTank extends GuiElement
{
    private IFluidTank tank;
    private int colour;

    /**
     * Make a take with given properties
     *
     * @param tank
     * @param posX
     * @param posY
     * @param width
     * @param height
     */
    public GuiFluidTank(IFluidTank tank, int posX, int posY, int width, int height)
    {
        super(posX, posY, width, height);
        this.tank = tank;
        this.colour = ColourHelper.BLUE;
    }

    /**
     * Make a tank using the default width and height
     *
     * @param tank
     * @param posX
     * @param posY
     */
    public GuiFluidTank(IFluidTank tank, int posX, int posY)
    {
        this(tank, posX, posY, 18, 39);
    }

    /**
     * Set the colour of the line around the tank
     *
     * @param colour in Integer form
     * @return the GuiFluidTank
     */
    public GuiElement setColour(int colour)
    {
        this.colour = colour;
        return this;
    }

    /**
     * Draw the tank holding given
     *
     * @param guiLeft    x origin for drawing
     * @param guiTop     y origin for drawing
     * @param fluidStack fluid to draw
     */
    @Override
    public void draw(int guiLeft, int guiTop)
    {
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glColor3f(ColourHelper.getRed(colour), ColourHelper.getGreen(colour), ColourHelper.getBlue(colour));

        bindTexture(Compendium.Resource.GUI.Element.fluidTank);
        drawTexturedModalRect(guiLeft + posX, guiTop + posY, 0, 0, 18, 39, width, height);

        GL11.glColor3f(1.0F, 1.0F, 1.0F);

        int iconHeightRemainder = (height - 4) % 16;
        int iconWidthRemainder = (width - 2) % 16;
        FluidStack fluidStack = tank.getFluid();

        if (fluidStack != null && fluidStack.amount > 0)
        {
            bindTexture(TextureMap.locationBlocksTexture);

            IIcon fluidIcon = fluidStack.getFluid().getStillIcon();
            // Top left corner
            drawTexturedModelRectFromIcon(guiLeft + posX + 1, guiTop + posY + 2, fluidIcon, iconWidthRemainder, iconHeightRemainder);
            for (int i = 0; i <= (width - 4) / 16; i++)
            {
                // Top right only draw when more then 1 pass is needed
                if (i > 0)
                {
                    drawTexturedModelRectFromIcon(guiLeft + posX + 1 + (i - 1) * 16 + iconWidthRemainder, guiTop + posY + 2, fluidIcon, 16, iconHeightRemainder);
                }
                for (int ii = 0; ii < (height - 6) / 16; ii++)
                {
                    // Bottom right only draw when more then 1 pass is needed
                    if (i > 0)
                    {
                        drawTexturedModelRectFromIcon(guiLeft + posX + 1 + (i - 1) * 16 + iconWidthRemainder, guiTop + posY + 2 + ii * 16 + iconHeightRemainder, fluidIcon, 16, 16);
                    }
                    // Bottom left
                    drawTexturedModelRectFromIcon(guiLeft + posX + 1, guiTop + posY + 2 + ii * 16 + iconHeightRemainder, fluidIcon, iconWidthRemainder, 16);
                }
            }

            bindTexture(Compendium.Resource.GUI.Element.fluidTank);
            drawTexturedModalRect(guiLeft + posX + 2, guiTop + posY + 1, 1, 1, 15, 37 - ((int) ((38) * ((float) fluidStack.amount / tank.getCapacity()))), width - 3, height - 2 - ((int) ((height - 1) * ((float) fluidStack.amount / tank.getCapacity()))));
        }

        bindTexture(Compendium.Resource.GUI.Element.fluidTank);
        drawTexturedModalRect(guiLeft + posX + 1, guiTop + posY + 1, 19, 1, 16, 37, width - 2, height - 2);

        GL11.glEnable(GL11.GL_LIGHTING);
    }

    public void drawTooltip(int mouseX, int mouseY)
    {
        if (!mouseInTank(mouseX, mouseY))
        {
            return;
        }

        List<String> description = new ArrayList<String>();
        FluidStack fluidStack = tank.getFluid();

        if (fluidStack == null || fluidStack.getFluid() == null)
        {
            description.add(LocalizationHelper.getLocalString("gui.element.tank.empty"));
        } else
        {
            if (fluidStack.amount > 0)
            {
                String amountToText = fluidStack.amount + LocalizationHelper.getLocalString("gui.element.tank.mB");

                description.add(fluidStack.getLocalizedName());
                description.add(amountToText);
            }
        }
        drawHoveringText(description, mouseX, mouseY, getFontRenderer());
    }

    private boolean mouseInTank(int x, int y)
    {
        return x >= this.posX && x < this.posX + width - 2 && y >= this.posY && y < this.posY + height - 2;
    }
}
