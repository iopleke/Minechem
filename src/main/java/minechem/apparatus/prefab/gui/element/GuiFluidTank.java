package minechem.apparatus.prefab.gui.element;

import minechem.helper.ColourHelper;
import minechem.helper.LocalizationHelper;
import minechem.reference.Compendium;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.util.ArrayList;
import java.util.List;

/**
 * A tank to display in a GUI
 * TODO: when there is an Object for a tank bind the tank directly to this Object
 * @author way2muchnoise
 */
public class GuiFluidTank extends GuiElement
{
    private int capacity;
    private int posX, posY;
    private int width, height;
    private int colour;

    /**
     * Make a take with given properties
     * @param capacity
     * @param posX
     * @param posY
     * @param width
     * @param height
     */
    public GuiFluidTank(int capacity, int posX, int posY, int width, int height)
    {
        this.posX = posX;
        this.posY = posY;
        this.capacity = capacity;
        this.width = width;
        this.height = height;
        this.colour = ColourHelper.BLUE;
    }

    /**
     * Make a tank using the default width and height
     * @param capacity tank size
     * @param posX
     * @param posY
     */
    public GuiFluidTank(int capacity, int posX, int posY)
    {
        this(capacity, posX, posY, 18, 39);
    }

    /**
     * Set the colour of the line around the tank
     * @param colour in Integer form
     * @return the GuiFluidTank
     */
    public GuiFluidTank setColour(int colour)
    {
        this.colour = colour;
        return this;
    }

    /**
     * Draw the tank holding given
     * @param guiLeft x origin for drawing
     * @param guiTop y origin for drawing
     * @param fluidStack fluid to draw
     */
    public void draw(int guiLeft, int guiTop, FluidStack fluidStack)
    {
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glColor3f(ColourHelper.getRed(colour), ColourHelper.getGreen(colour), ColourHelper.getBlue(colour));

        bindTexture(Compendium.Resource.GUI.Element.fluidTank);
        drawTexturedModalRect(guiLeft + posX, guiTop + posY, 0, 0, 18, 39, width, height);
        
        GL11.glColor3f(1.0F, 1.0F, 1.0F);

        int iconHeightRemainder = (height - 4) % 16;
        int iconWidthRemainder = (width - 2) % 16;

        if (fluidStack != null && fluidStack.amount > 0)
        {
            bindTexture(TextureMap.locationBlocksTexture);

            IIcon fluidIcon = fluidStack.getFluid().getStillIcon();
            // Top left corner
            drawTexturedModelRectFromIcon(guiLeft + posX + 1, guiTop + posY + 2, fluidIcon, iconWidthRemainder, iconHeightRemainder);
            for (int i = 0; i <= (width - 4) / 16; i++)
            {
                // Top right only draw when more then 1 pass is needed
                if (i > 0) drawTexturedModelRectFromIcon(guiLeft + posX + 1 + (i-1) * 16 + iconWidthRemainder, guiTop + posY + 2 , fluidIcon, 16, iconHeightRemainder);
                for (int ii = 0; ii < (height - 6) / 16; ii++)
                {
                    // Bottom right only draw when more then 1 pass is needed
                    if (i > 0) drawTexturedModelRectFromIcon(guiLeft + posX + 1 + (i-1) * 16 + iconWidthRemainder, guiTop + posY + 2 + ii * 16 + iconHeightRemainder , fluidIcon, 16, 16);
                    // Bottom left
                    drawTexturedModelRectFromIcon(guiLeft + posX + 1, guiTop + posY + 2 + ii * 16 + iconHeightRemainder, fluidIcon, iconWidthRemainder, 16);
                }
            }

            bindTexture(Compendium.Resource.GUI.Element.fluidTank);
            drawTexturedModalRect(guiLeft + posX + 2, guiTop + posY + 1, 1, 1, 15, 37 - ((int) ((38) * ((float) fluidStack.amount / capacity))), width-3, height-2 - ((int) ((height-1) * ((float) fluidStack.amount / capacity))));
        }

        bindTexture(Compendium.Resource.GUI.Element.fluidTank);
        drawTexturedModalRect(guiLeft + posX + 1, guiTop + posY + 1, 19, 1, 16, 37, width-2, height-2);

        GL11.glEnable(GL11.GL_LIGHTING);
    }

    public void drawTooltip(int mouseX, int mouseY, FluidStack fluidStack)
    {
        if (!mouseInTank(mouseX, mouseY))
        {
            return;
        }

        List<String> description = new ArrayList<String>();

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
        return x >= this.posX && x < this.posX + width -2 && y >= this.posY && y < this.posY + height-2;
    }

    protected void drawHoveringText(List<String> tooltip, int x, int y, FontRenderer fontrenderer)
    {
        if (!tooltip.isEmpty())
        {
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            int k = 0;

            for (String line : tooltip)
            {
                int l = fontrenderer.getStringWidth(line);

                if (l > k)
                {
                    k = l;
                }
            }

            int i1 = x + 12;
            int j1 = y - 12;
            int k1 = 8;

            if (tooltip.size() > 1)
            {
                k1 += 2 + (tooltip.size() - 1) * 10;
            }

            this.zLevel = 300.0F;
            int l1 = -267386864;
            this.drawGradientRect(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l1, l1);
            this.drawGradientRect(i1 - 3, j1 + k1 + 3, i1 + k + 3, j1 + k1 + 4, l1, l1);
            this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 + k1 + 3, l1, l1);
            this.drawGradientRect(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1);
            this.drawGradientRect(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k1 + 3, l1, l1);
            int i2 = 1347420415;
            int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
            this.drawGradientRect(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
            this.drawGradientRect(i1 + k + 2, j1 - 3 + 1, i1 + k + 3, j1 + k1 + 3 - 1, i2, j2);
            this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 - 3 + 1, i2, i2);
            this.drawGradientRect(i1 - 3, j1 + k1 + 2, i1 + k + 3, j1 + k1 + 3, j2, j2);

            for (int k2 = 0; k2 < tooltip.size(); ++k2)
            {
                String s1 = tooltip.get(k2);
                fontrenderer.drawStringWithShadow(s1, i1, j1, -1);

                if (k2 == 0)
                {
                    j1 += 2;
                }

                j1 += 10;
            }

            this.zLevel = 0.0F;
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        }
    }
}
