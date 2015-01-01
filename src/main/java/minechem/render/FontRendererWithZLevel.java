package minechem.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class FontRendererWithZLevel implements IResourceManagerReloadListener
{
    private static FontRendererWithZLevel fontRendererWithZLevel;

    public static FontRendererWithZLevel getFontRenderer(float zLevel)
    {
        if (fontRendererWithZLevel == null)
        {
            fontRendererWithZLevel = new FontRendererWithZLevel();
        }
        return fontRendererWithZLevel.setZLevel(zLevel);
    }

    public FontRendererWithZLevel setZLevel(float zLevel)
    {
        this.zLevel = zLevel;
        return this;
    }

    // The zLevel used to print
    private float zLevel = 0.0F;

    private final ResourceLocation locationFontTexture;
    /**
     * Array of width of all the characters in default.png
     */
    private int[] charWidth = new int[256];
    /**
     * the height in pixels of default text
     */
    public int FONT_HEIGHT = 9;
    public Random fontRandom = new Random();
    /**
     * Array of the start/end column (in upper/lower nibble) for every glyph in the /font directory.
     */
    private byte[] glyphWidth = new byte[65536];
    /**
     * Array of RGB triplets defining the 16 standard chat colors followed by 16 darker version of the same colors for drop shadows.
     */
    private int[] colorCode = new int[32];
    /**
     * The RenderEngine used to load and setup glyph textures.
     */
    private final TextureManager renderEngine;
    /**
     * Current X coordinate at which to draw the next character.
     */
    private float posX;
    /**
     * Current Y coordinate at which to draw the next character.
     */
    private float posY;
    /**
     * If true, strings should be rendered with Unicode fonts instead of the default.png font
     */
    private boolean unicodeFlag;
    /**
     * Used to specify new red value for the current color.
     */
    private float red;
    /**
     * Used to specify new blue value for the current color.
     */
    private float blue;
    /**
     * Used to specify new green value for the current color.
     */
    private float green;
    /**
     * Used to speify new alpha value for the current color.
     */
    private float alpha;
    /**
     * Set if the "k" style (random) is active in currently rendering string
     */
    private boolean randomStyle;
    /**
     * Set if the "l" style (bold) is active in currently rendering string
     */
    private boolean boldStyle;
    /**
     * Set if the "o" style (italic) is active in currently rendering string
     */
    private boolean italicStyle;
    /**
     * Set if the "n" style (underlined) is active in currently rendering string
     */
    private boolean underlineStyle;
    /**
     * Set if the "m" style (strikethrough) is active in currently rendering string
     */
    private boolean strikethroughStyle;

    private FontRendererWithZLevel()
    {
        this.locationFontTexture = new ResourceLocation("textures/font/ascii.png");
        this.renderEngine = Minecraft.getMinecraft().renderEngine;
        this.unicodeFlag = false;
        Minecraft.getMinecraft().getTextureManager().bindTexture(this.locationFontTexture);

        for (int i = 0; i < 32; ++i)
        {
            int j = (i >> 3 & 1) * 85;
            int k = (i >> 2 & 1) * 170 + j;
            int l = (i >> 1 & 1) * 170 + j;
            int i1 = (i & 1) * 170 + j;

            if (i == 6)
            {
                k += 85;
            }

            if (Minecraft.getMinecraft().gameSettings.anaglyph)
            {
                int j1 = (k * 30 + l * 59 + i1 * 11) / 100;
                int k1 = (k * 30 + l * 70) / 100;
                int l1 = (k * 30 + i1 * 70) / 100;
                k = j1;
                l = k1;
                i1 = l1;
            }

            if (i >= 16)
            {
                k /= 4;
                l /= 4;
                i1 /= 4;
            }

            this.colorCode[i] = (k & 255) << 16 | (l & 255) << 8 | i1 & 255;
        }

        this.readGlyphSizes();
        ((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(this);
    }

    public void onResourceManagerReload(IResourceManager p_110549_1_)
    {
        this.readFontTexture();
    }

    private void readFontTexture()
    {
        BufferedImage bufferedimage;

        try
        {
            bufferedimage = ImageIO.read(Minecraft.getMinecraft().getResourceManager().getResource(this.locationFontTexture).getInputStream());
        } catch (IOException ioexception)
        {
            throw new RuntimeException(ioexception);
        }

        int i = bufferedimage.getWidth();
        int j = bufferedimage.getHeight();
        int[] aint = new int[i * j];
        bufferedimage.getRGB(0, 0, i, j, aint, 0, i);
        int k = j / 16;
        int l = i / 16;
        byte b0 = 1;
        float f = 8.0F / (float) l;
        int i1 = 0;

        while (i1 < 256)
        {
            int j1 = i1 % 16;
            int k1 = i1 / 16;

            if (i1 == 32)
            {
                this.charWidth[i1] = 3 + b0;
            }

            int l1 = l - 1;

            while (true)
            {
                if (l1 >= 0)
                {
                    int i2 = j1 * l + l1;
                    boolean flag = true;

                    for (int j2 = 0; j2 < k && flag; ++j2)
                    {
                        int k2 = (k1 * l + j2) * i;

                        if ((aint[i2 + k2] >> 24 & 255) != 0)
                        {
                            flag = false;
                        }
                    }

                    if (flag)
                    {
                        --l1;
                        continue;
                    }
                }

                ++l1;
                this.charWidth[i1] = (int) (0.5D + (double) ((float) l1 * f)) + b0;
                ++i1;
                break;
            }
        }
    }

    private void readGlyphSizes()
    {
        try
        {
            InputStream inputstream = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("font/glyph_sizes.bin")).getInputStream();
            inputstream.read(this.glyphWidth);
        } catch (IOException ioexception)
        {
            throw new RuntimeException(ioexception);
        }
    }

    /**
     * Pick how to render a single character and return the width used.
     */
    private float renderCharAtPos(int p_78278_1_, char p_78278_2_, boolean p_78278_3_)
    {
        return p_78278_2_ == 32 ? 4.0F : this.renderDefaultChar(p_78278_1_, p_78278_3_);
    }

    /**
     * Render a single character with the default.png font at current (posX,posY) location...
     */
    private float renderDefaultChar(int p_78266_1_, boolean p_78266_2_)
    {
        float f = (float) (p_78266_1_ % 16 * 8);
        float f1 = (float) (p_78266_1_ / 16 * 8);
        float f2 = p_78266_2_ ? 1.0F : 0.0F;
        this.renderEngine.bindTexture(this.locationFontTexture);
        float f3 = (float) this.charWidth[p_78266_1_] - 0.01F;
        GL11.glBegin(GL11.GL_TRIANGLE_STRIP);
        GL11.glTexCoord2f(f / 128.0F, f1 / 128.0F);
        GL11.glVertex3f(this.posX + f2, this.posY, this.zLevel);
        GL11.glTexCoord2f(f / 128.0F, (f1 + 7.99F) / 128.0F);
        GL11.glVertex3f(this.posX - f2, this.posY + 7.99F, this.zLevel);
        GL11.glTexCoord2f((f + f3 - 1.0F) / 128.0F, f1 / 128.0F);
        GL11.glVertex3f(this.posX + f3 - 1.0F + f2, this.posY, this.zLevel);
        GL11.glTexCoord2f((f + f3 - 1.0F) / 128.0F, (f1 + 7.99F) / 128.0F);
        GL11.glVertex3f(this.posX + f3 - 1.0F - f2, this.posY + 7.99F, this.zLevel);
        GL11.glEnd();
        return (float) this.charWidth[p_78266_1_];
    }

    /**
     * Reset all style flag fields in the class to false; called at the start of string rendering
     */
    private void resetStyles()
    {
        this.randomStyle = false;
        this.boldStyle = false;
        this.italicStyle = false;
        this.underlineStyle = false;
        this.strikethroughStyle = false;
    }

    /**
     * Draws the specified string.
     */
    public int drawString(String p_78276_1_, int p_78276_2_, int p_78276_3_, int p_78276_4_)
    {
        return this.drawString(p_78276_1_, p_78276_2_, p_78276_3_, p_78276_4_, false);
    }

    /**
     * Draws the specified string. Args: string, x, y, color, dropShadow
     */
    public int drawString(String p_85187_1_, int p_85187_2_, int p_85187_3_, int p_85187_4_, boolean p_85187_5_)
    {
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        this.resetStyles();
        int l;

        if (p_85187_5_)
        {
            l = this.renderString(p_85187_1_, p_85187_2_ + 1, p_85187_3_ + 1, p_85187_4_, true);
            l = Math.max(l, this.renderString(p_85187_1_, p_85187_2_, p_85187_3_, p_85187_4_, false));
        } else
        {
            l = this.renderString(p_85187_1_, p_85187_2_, p_85187_3_, p_85187_4_, false);
        }

        return l;
    }

    /**
     * Render a single line string at the current (posX,posY) and update posX
     */
    private void renderStringAtPos(String p_78255_1_, boolean p_78255_2_)
    {
        for (int i = 0; i < p_78255_1_.length(); ++i)
        {
            char c0 = p_78255_1_.charAt(i);
            int j;
            int k;

            if (c0 == 167 && i + 1 < p_78255_1_.length())
            {
                j = "0123456789abcdefklmnor".indexOf(p_78255_1_.toLowerCase().charAt(i + 1));

                if (j < 16)
                {
                    this.randomStyle = false;
                    this.boldStyle = false;
                    this.strikethroughStyle = false;
                    this.underlineStyle = false;
                    this.italicStyle = false;

                    if (j < 0 || j > 15)
                    {
                        j = 15;
                    }

                    if (p_78255_2_)
                    {
                        j += 16;
                    }

                    k = this.colorCode[j];
                    GL11.glColor4f((float) (k >> 16) / 255.0F, (float) (k >> 8 & 255) / 255.0F, (float) (k & 255) / 255.0F, this.alpha);
                } else if (j == 16)
                {
                    this.randomStyle = true;
                } else if (j == 17)
                {
                    this.boldStyle = true;
                } else if (j == 18)
                {
                    this.strikethroughStyle = true;
                } else if (j == 19)
                {
                    this.underlineStyle = true;
                } else if (j == 20)
                {
                    this.italicStyle = true;
                } else if (j == 21)
                {
                    this.randomStyle = false;
                    this.boldStyle = false;
                    this.strikethroughStyle = false;
                    this.underlineStyle = false;
                    this.italicStyle = false;
                    GL11.glColor4f(this.red, this.blue, this.green, this.alpha);
                }

                ++i;
            } else
            {
                j = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000".indexOf(c0);

                if (this.randomStyle && j != -1)
                {
                    do
                    {
                        k = this.fontRandom.nextInt(this.charWidth.length);
                    }
                    while (this.charWidth[j] != this.charWidth[k]);

                    j = k;
                }

                float f1 = this.unicodeFlag ? 0.5F : 1.0F;
                boolean flag1 = (c0 == 0 || j == -1 || this.unicodeFlag) && p_78255_2_;

                if (flag1)
                {
                    this.posX -= f1;
                    this.posY -= f1;
                }

                float f = this.renderCharAtPos(j, c0, this.italicStyle);

                if (flag1)
                {
                    this.posX += f1;
                    this.posY += f1;
                }

                if (this.boldStyle)
                {
                    this.posX += f1;

                    if (flag1)
                    {
                        this.posX -= f1;
                        this.posY -= f1;
                    }

                    this.renderCharAtPos(j, c0, this.italicStyle);
                    this.posX -= f1;

                    if (flag1)
                    {
                        this.posX += f1;
                        this.posY += f1;
                    }

                    ++f;
                }

                Tessellator tessellator;

                if (this.strikethroughStyle)
                {
                    tessellator = Tessellator.instance;
                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                    tessellator.startDrawingQuads();
                    tessellator.addVertex((double) this.posX, (double) (this.posY + (float) (this.FONT_HEIGHT / 2)), this.zLevel);
                    tessellator.addVertex((double) (this.posX + f), (double) (this.posY + (float) (this.FONT_HEIGHT / 2)), this.zLevel);
                    tessellator.addVertex((double) (this.posX + f), (double) (this.posY + (float) (this.FONT_HEIGHT / 2) - 1.0F), this.zLevel);
                    tessellator.addVertex((double) this.posX, (double) (this.posY + (float) (this.FONT_HEIGHT / 2) - 1.0F), this.zLevel);
                    tessellator.draw();
                    GL11.glEnable(GL11.GL_TEXTURE_2D);
                }

                if (this.underlineStyle)
                {
                    tessellator = Tessellator.instance;
                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                    tessellator.startDrawingQuads();
                    int l = this.underlineStyle ? -1 : 0;
                    tessellator.addVertex((double) (this.posX + (float) l), (double) (this.posY + (float) this.FONT_HEIGHT), this.zLevel);
                    tessellator.addVertex((double) (this.posX + f), (double) (this.posY + (float) this.FONT_HEIGHT), this.zLevel);
                    tessellator.addVertex((double) (this.posX + f), (double) (this.posY + (float) this.FONT_HEIGHT - 1.0F), this.zLevel);
                    tessellator.addVertex((double) (this.posX + (float) l), (double) (this.posY + (float) this.FONT_HEIGHT - 1.0F), this.zLevel);
                    tessellator.draw();
                    GL11.glEnable(GL11.GL_TEXTURE_2D);
                }

                this.posX += (float) ((int) f);
            }
        }
    }

    /**
     * Render single line string by setting GL color, current (posX,posY), and calling renderStringAtPos()
     */
    private int renderString(String p_78258_1_, int p_78258_2_, int p_78258_3_, int p_78258_4_, boolean p_78258_5_)
    {
        if (p_78258_1_ == null)
        {
            return 0;
        } else
        {
            if ((p_78258_4_ & -67108864) == 0)
            {
                p_78258_4_ |= -16777216;
            }

            if (p_78258_5_)
            {
                p_78258_4_ = (p_78258_4_ & 16579836) >> 2 | p_78258_4_ & -16777216;
            }

            this.red = (float) (p_78258_4_ >> 16 & 255) / 255.0F;
            this.blue = (float) (p_78258_4_ >> 8 & 255) / 255.0F;
            this.green = (float) (p_78258_4_ & 255) / 255.0F;
            this.alpha = (float) (p_78258_4_ >> 24 & 255) / 255.0F;
            GL11.glColor4f(this.red, this.blue, this.green, this.alpha);
            this.posX = (float) p_78258_2_;
            this.posY = (float) p_78258_3_;
            this.renderStringAtPos(p_78258_1_, p_78258_5_);
            return (int) this.posX;
        }
    }

    /**
     * Returns the width of this string. Equivalent of FontMetrics.stringWidth(String s).
     */
    public int getStringWidth(String p_78256_1_)
    {
        if (p_78256_1_ == null)
        {
            return 0;
        } else
        {
            int i = 0;
            boolean flag = false;

            for (int j = 0; j < p_78256_1_.length(); ++j)
            {
                char c0 = p_78256_1_.charAt(j);
                int k = this.getCharWidth(c0);

                if (k < 0 && j < p_78256_1_.length() - 1)
                {
                    ++j;
                    c0 = p_78256_1_.charAt(j);

                    if (c0 != 108 && c0 != 76)
                    {
                        if (c0 == 114 || c0 == 82)
                        {
                            flag = false;
                        }
                    } else
                    {
                        flag = true;
                    }

                    k = 0;
                }

                i += k;

                if (flag && k > 0)
                {
                    ++i;
                }
            }

            return i;
        }
    }

    /**
     * Returns the width of this character as rendered.
     */
    public int getCharWidth(char p_78263_1_)
    {
        if (p_78263_1_ == 167)
        {
            return -1;
        } else if (p_78263_1_ == 32)
        {
            return 4;
        } else
        {
            int i = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000".indexOf(p_78263_1_);
            return this.charWidth[i];
        }
    }
}
