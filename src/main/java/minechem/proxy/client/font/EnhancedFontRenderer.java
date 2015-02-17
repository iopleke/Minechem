package minechem.proxy.client.font;

import com.ibm.icu.text.ArabicShaping;
import com.ibm.icu.text.ArabicShapingException;
import com.ibm.icu.text.Bidi;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class EnhancedFontRenderer implements IResourceManagerReloadListener
{
    private static final ResourceLocation[] unicodePageLocations = new ResourceLocation[256];
    /**
     * Array of width of all the characters in default.png
     */
    private int[] charWidth = new int[256];
    /**
     * the height in pixels of default text
     */
    private int FONT_HEIGHT = 9;
    private static final float FONT_REFERENCE_HEIGHT = 9.0F;
    private float fontScale;
    public Random fontRandom = new Random();
    /**
     * Array of the start/end column (in upper/lower nibble) for every glyph in the /font directory.
     */
    private byte[] glyphWidth = new byte[65536];
    /**
     * Array of RGB triplets defining the 16 standard chat colors followed by 16 darker version of the same colors for
     * drop shadows.
     */
    private int[] colorCode = new int[32];
    private final ResourceLocation locationFontTexture;
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
     * If true, the Unicode Bidirectional Algorithm should be run before rendering any string.
     */
    private boolean bidiFlag;
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
     * Text color of the currently rendering string.
     */
    private int textColor;
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
    /**
     * Constant containing all unicode characters
     */
    private static final String UNICHARS = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000";
    /**
     * The zLevel
     */
    private float zLevel = 0.0F;

    public EnhancedFontRenderer(GameSettings gameSettings, ResourceLocation resourceLocation, TextureManager textureManager, boolean unicode)
    {
        this.locationFontTexture = resourceLocation;
        this.renderEngine = textureManager;
        this.unicodeFlag = unicode;
        textureManager.bindTexture(this.locationFontTexture);
        this.initBaseColors(gameSettings);
        this.readGlyphSizes();
    }

    public EnhancedFontRenderer setZLevel(float zLevel)
    {
        this.zLevel = zLevel;
        return this;
    }

    public EnhancedFontRenderer incZLevel(float inc)
    {
        this.zLevel += inc;
        return this;
    }

    public EnhancedFontRenderer decZLevel(float dec)
    {
        this.zLevel -= dec;
        return this;
    }

    public EnhancedFontRenderer incZLevel()
    {
        this.zLevel++;
        return this;
    }

    public EnhancedFontRenderer decZLevel()
    {
        this.zLevel--;
        return this;
    }
    
    public EnhancedFontRenderer resetFontSize()
    {
        this.FONT_HEIGHT = (int)FONT_REFERENCE_HEIGHT;
        this.setFontScale();
        return this;
    }
    
    public EnhancedFontRenderer incFontSize()
    {
        this.FONT_HEIGHT++;
        this.setFontScale();
        return this;
    }

    public EnhancedFontRenderer decFontSize()
    {
        this.FONT_HEIGHT--;
        this.setFontScale();
        return this;
    }

    public EnhancedFontRenderer incFontSize(int inc)
    {
        this.FONT_HEIGHT += inc;
        this.setFontScale();
        return this;
    }

    public EnhancedFontRenderer decFontSize(int dec)
    {
        this.FONT_HEIGHT -= dec;
        this.setFontScale();
        return this;
    }

    public EnhancedFontRenderer setFontSize(int size)
    {
        this.FONT_HEIGHT = size;
        this.setFontScale();
        return this;
    }

    public float getZLevel()
    {
        return this.zLevel;
    }
    
    public int getFontSize()
    {
        return this.FONT_HEIGHT;
    }
    
    private float getFontScale()
    {
        return fontScale;
    }

    private void setFontScale()
    {
        this.fontScale = FONT_HEIGHT/FONT_REFERENCE_HEIGHT;
    }

    public void onResourceManagerReload(IResourceManager resourceManager)
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
        float f = 8.0F / (float)l;
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
                this.charWidth[i1] = (int)(0.5D + (double)((float)l1 * f)) + b0;
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

    private void initBaseColors(GameSettings gameSettings)
    {
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

            if (gameSettings.anaglyph)
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
    }

    /**
     * Pick how to render a single character and return the width used.
     */
    private float renderCharAtPos(int charId, char c, boolean italics)
    {
        // 32 is the integer value for the space char
        return c == 32 ? 4.0F * getFontScale() : UNICHARS.indexOf(c) != -1 && !this.unicodeFlag ? this.renderDefaultChar(charId, italics) : this.renderUnicodeChar(c, italics);
    }

    /**
     * Render a single character with the default.png font at current (posX,posY) location...
     */
    private float renderDefaultChar(int charId, boolean italics)
    {
        float textureXPos = (float)(charId % 16 * 8);
        float textureYPos = (float)(charId / 16 * 8);
        float italicsScaleFactor = italics ? 1.0F * getFontScale() : 0.0F;
        this.renderEngine.bindTexture(this.locationFontTexture);
        float fontTextureWidth = (float)this.charWidth[charId] - 1.01F;
        float fontDrawWidth = fontTextureWidth * getFontScale();
        float fontTextureHeight = 7.99F;
        float fontDrawHeight = fontTextureHeight * getFontScale();
        float fontTextureScaling = 128.0F;
        GL11.glBegin(GL11.GL_TRIANGLE_STRIP);
        GL11.glTexCoord2f(textureXPos / fontTextureScaling, textureYPos / fontTextureScaling);
        GL11.glVertex3f(this.posX + italicsScaleFactor, this.posY, this.zLevel);
        GL11.glTexCoord2f(textureXPos / fontTextureScaling, (textureYPos + fontTextureHeight) / fontTextureScaling);
        GL11.glVertex3f(this.posX - italicsScaleFactor, this.posY + fontDrawHeight, this.zLevel);
        GL11.glTexCoord2f((textureXPos + fontTextureWidth) / fontTextureScaling, textureYPos / fontTextureScaling);
        GL11.glVertex3f(this.posX + fontDrawWidth + italicsScaleFactor, this.posY, this.zLevel);
        GL11.glTexCoord2f((textureXPos + fontTextureWidth) / fontTextureScaling, (textureYPos + fontTextureHeight) / fontTextureScaling);
        GL11.glVertex3f(this.posX + fontDrawWidth - italicsScaleFactor, this.posY + fontDrawHeight, this.zLevel);
        GL11.glEnd();
        return (float)this.charWidth[charId] * getFontScale();
    }

    private ResourceLocation getUnicodePageLocation(int location)
    {
        if (unicodePageLocations[location] == null)
        {
            unicodePageLocations[location] = new ResourceLocation(String.format("textures/font/unicode_page_%02x.png", Integer.valueOf(location)));
        }

        return unicodePageLocations[location];
    }

    /**
     * Load one of the /font/glyph_XX.png into a new GL texture and store the texture ID in glyphTextureName array.
     */
    private void loadGlyphTexture(int id)
    {
        this.renderEngine.bindTexture(this.getUnicodePageLocation(id));
    }

    /**
     * Render a single Unicode character at current (posX,posY) location using one of the /font/glyph_XX.png files...
     */
    private float renderUnicodeChar(char unicodeChar, boolean italics)
    {
        if (this.glyphWidth[unicodeChar] == 0)
        {
            return 0.0F;
        } else
        {
            int i = unicodeChar / 256;
            this.loadGlyphTexture(i);
            int j = this.glyphWidth[unicodeChar] >>> 4;
            int k = this.glyphWidth[unicodeChar] & 15;
            float f = (float)j;
            float f1 = (float)(k + 1);
            float unicodeChatScale = 2.0F;
            float texturePosX = (float)(unicodeChar % 16 * 16) + f;
            float texturePosY = (float)((unicodeChar & 255) / 16 * 16);
            float fontTextureWidth = f1 - f - 0.02F;
            float fontDrawWidth = fontTextureWidth / unicodeChatScale * getFontScale();
            float italicsScaleFactor = italics ? 1.0F * getFontScale() : 0.0F;
            float fontTextureScaling = 256.0F;
            float fontTextureHeight = 15.98F;
            float fontDrawHeight = fontTextureHeight / unicodeChatScale * getFontScale();
            GL11.glBegin(GL11.GL_TRIANGLE_STRIP);
            GL11.glTexCoord2f(texturePosX / fontTextureScaling, texturePosY / fontTextureScaling);
            GL11.glVertex3f(this.posX + italicsScaleFactor, this.posY, this.zLevel);
            GL11.glTexCoord2f(texturePosX / fontTextureScaling, (texturePosY + fontTextureHeight) / fontTextureScaling);
            GL11.glVertex3f(this.posX - italicsScaleFactor, this.posY + fontDrawHeight, this.zLevel);
            GL11.glTexCoord2f((texturePosX + fontTextureWidth) / fontTextureScaling, texturePosY / fontTextureScaling);
            GL11.glVertex3f(this.posX + fontDrawWidth + italicsScaleFactor, this.posY, this.zLevel);
            GL11.glTexCoord2f((texturePosX + fontTextureWidth) / fontTextureScaling, (texturePosY + fontTextureHeight) / fontTextureScaling);
            GL11.glVertex3f(this.posX + fontDrawWidth - italicsScaleFactor, this.posY + fontDrawHeight, this.zLevel);
            GL11.glEnd();
            return ((f1 - f) / unicodeChatScale + 1.0F) * getFontScale();
        }
    }

    /**
     * Draws the specified string with a shadow.
     */
    public int drawStringWithShadow(String string, int x, int y, int color)
    {
        return this.drawString(string, x, y, color, true);
    }

    /**
     * Draws the specified string.
     */
    public int drawString(String string, int x, int y, int color)
    {
        return this.drawString(string, x, y, color, false);
    }

    /**
     * Draws the specified string. Args: string, x, y, color, dropShadow
     */
    public int drawString(String string, int x, int y, int color, boolean dropShadow)
    {
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        this.resetStyles();
        int l;

        if (dropShadow)
        {
            l = this.renderString(string, x + 1, y + 1, color, true);
            l = Math.max(l, this.renderString(string, x, y, color, false));
        } else
        {
            l = this.renderString(string, x, y, color, false);
        }

        return l;
    }

    /**
     * Apply Unicode Bidirectional Algorithm to string and return a new possibly reordered string for visual rendering.
     */
    private String bidiReorder(String string)
    {
        try
        {
            Bidi bidi = new Bidi((new ArabicShaping(8)).shape(string), 127);
            bidi.setReorderingMode(0);
            return bidi.writeReordered(2);
        } catch (ArabicShapingException arabicshapingexception)
        {
            return string;
        }
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
     * Render a single line string at the current (posX,posY) and update posX
     */
    private void renderStringAtPos(String sting, boolean p_78255_2_)
    {
        for (int i = 0; i < sting.length(); ++i)
        {
            char c0 = sting.charAt(i);
            int j;
            int k;

            if (c0 == 167 && i + 1 < sting.length())
            {
                j = "0123456789abcdefklmnor".indexOf(sting.toLowerCase().charAt(i + 1));

                if (j < 16)
                {
                    this.resetStyles();

                    if (j < 0 || j > 15)
                    {
                        j = 15;
                    }

                    if (p_78255_2_)
                    {
                        j += 16;
                    }

                    k = this.colorCode[j];
                    this.textColor = k;
                    GL11.glColor4f((float)(k >> 16) / 255.0F, (float)(k >> 8 & 255) / 255.0F, (float)(k & 255) / 255.0F, this.alpha);
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
                    this.resetStyles();
                    GL11.glColor4f(this.red, this.blue, this.green, this.alpha);
                }

                ++i;
            } else
            {
                j = UNICHARS.indexOf(c0);

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
                    tessellator.addVertex((double)this.posX, (double)(this.posY + (float)(this.FONT_HEIGHT / 2)), 0.0D);
                    tessellator.addVertex((double)(this.posX + f), (double)(this.posY + (float)(this.FONT_HEIGHT / 2)), 0.0D);
                    tessellator.addVertex((double)(this.posX + f), (double)(this.posY + (float)(this.FONT_HEIGHT / 2) - 1.0F), 0.0D);
                    tessellator.addVertex((double)this.posX, (double)(this.posY + (float)(this.FONT_HEIGHT / 2) - 1.0F), 0.0D);
                    tessellator.draw();
                    GL11.glEnable(GL11.GL_TEXTURE_2D);
                }

                if (this.underlineStyle)
                {
                    tessellator = Tessellator.instance;
                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                    tessellator.startDrawingQuads();
                    int l = this.underlineStyle ? -1 : 0;
                    tessellator.addVertex((double)(this.posX + (float)l), (double)(this.posY + (float)this.FONT_HEIGHT), 0.0D);
                    tessellator.addVertex((double)(this.posX + f), (double)(this.posY + (float)this.FONT_HEIGHT), 0.0D);
                    tessellator.addVertex((double)(this.posX + f), (double)(this.posY + (float)this.FONT_HEIGHT - 1.0F), 0.0D);
                    tessellator.addVertex((double)(this.posX + (float)l), (double)(this.posY + (float)this.FONT_HEIGHT - 1.0F), 0.0D);
                    tessellator.draw();
                    GL11.glEnable(GL11.GL_TEXTURE_2D);
                }

                this.posX += (float)((int)f);
            }
        }
    }

    /**
     * Render string either left or right aligned depending on bidiFlag
     */
    private int renderStringAligned(String string, int x, int y, int maxWidth, int color, boolean dropShadow)
    {
        if (this.bidiFlag)
        {
            int i1 = this.getStringWidth(this.bidiReorder(string));
            x = x + maxWidth - i1;
        }

        return this.renderString(string, x, y, color, dropShadow);
    }

    /**
     * Render single line string by setting GL color, current (posX,posY), and calling renderStringAtPos()
     */
    private int renderString(String string, int x, int y, int color, boolean p_78258_5_)
    {
        if (string == null)
        {
            return 0;
        } else
        {
            if (this.bidiFlag)
            {
                string = this.bidiReorder(string);
            }

            if ((color & -67108864) == 0)
            {
                color |= -16777216;
            }

            if (p_78258_5_)
            {
                color = (color & 16579836) >> 2 | color & -16777216;
            }

            this.red = (float)(color >> 16 & 255) / 255.0F;
            this.blue = (float)(color >> 8 & 255) / 255.0F;
            this.green = (float)(color & 255) / 255.0F;
            this.alpha = (float)(color >> 24 & 255) / 255.0F;
            GL11.glColor4f(this.red, this.blue, this.green, this.alpha);
            this.posX = (float)x;
            this.posY = (float)y;
            this.renderStringAtPos(string, p_78258_5_);
            return (int)this.posX;
        }
    }

    /**
     * Returns the width of this string. Equivalent of FontMetrics.stringWidth(String s).
     */
    public int getStringWidth(String string)
    {
        if (string == null)
        {
            return 0;
        } else
        {
            int i = 0;
            boolean flag = false;

            for (int j = 0; j < string.length(); ++j)
            {
                char c0 = string.charAt(j);
                int k = this.getCharWidth(c0);

                if (k < 0 && j < string.length() - 1)
                {
                    ++j;
                    c0 = string.charAt(j);

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
    public int getCharWidth(char c)
    {
        if (c == 167) //§ is not drawn since it is the modifier tag
        {
            return -1;
        } else if (c == 32) //size of a space
        {
            return 4;
        } else
        {
            int i = UNICHARS.indexOf(c);

            if (c > 0 && i != -1 && !this.unicodeFlag)
            {
                return this.charWidth[i];
            } else if (this.glyphWidth[c] != 0)
            {
                int j = this.glyphWidth[c] >>> 4;
                int k = this.glyphWidth[c] & 15;

                if (k > 7)
                {
                    k = 15;
                    j = 0;
                }

                ++k;
                return (k - j) / 2 + 1;
            } else
            {
                return 0;
            }
        }
    }

    /**
     * Trims a string to fit a specified Width.
     */
    public String trimStringToWidth(String string, int newLength)
    {
        return this.trimStringToWidth(string, newLength, false);
    }

    /**
     * Trims a string to a specified width, and will reverse it if par3 is set.
     */
    public String trimStringToWidth(String string, int newLength, boolean reverse)
    {
        StringBuilder stringbuilder = new StringBuilder();
        int j = 0;
        int k = reverse ? string.length() - 1 : 0;
        int l = reverse ? -1 : 1;
        boolean flag1 = false;
        boolean flag2 = false;

        for (int i1 = k; i1 >= 0 && i1 < string.length() && j < newLength; i1 += l)
        {
            char c0 = string.charAt(i1);
            int j1 = this.getCharWidth(c0);

            if (flag1)
            {
                flag1 = false;

                if (c0 != 108 && c0 != 76)
                {
                    if (c0 == 114 || c0 == 82)
                    {
                        flag2 = false;
                    }
                } else
                {
                    flag2 = true;
                }
            } else if (j1 < 0)
            {
                flag1 = true;
            } else
            {
                j += j1;

                if (flag2)
                {
                    ++j;
                }
            }

            if (j > newLength)
            {
                break;
            }

            if (reverse)
            {
                stringbuilder.insert(0, c0);
            } else
            {
                stringbuilder.append(c0);
            }
        }

        return stringbuilder.toString();
    }

    /**
     * Remove all newline characters from the end of the string
     */
    private String trimStringNewline(String string)
    {
        while (string != null && string.endsWith("\n"))
        {
            string = string.substring(0, string.length() - 1);
        }

        return string;
    }

    /**
     * Splits and draws a String with wordwrap
     */
    public void drawSplitString(String string, int x, int y, int maxWidth, int color)
    {
        this.resetStyles();
        this.textColor = color;
        string = this.trimStringNewline(string);
        this.renderSplitString(string, x, y, maxWidth, false);
    }

    /**
     * Perform actual work of rendering a multi-line string with wordwrap and with darker drop shadow color if flag is
     * set
     */
    private void renderSplitString(String string, int x, int y, int maxWidth, boolean dropShadow)
    {
        List list = this.listFormattedStringToWidth(string, maxWidth);

        for (Iterator iterator = list.iterator(); iterator.hasNext(); y += this.FONT_HEIGHT)
        {
            String s1 = (String)iterator.next();
            this.renderStringAligned(s1, x, y, maxWidth, this.textColor, dropShadow);
        }
    }

    /**
     * Returns the width of the wordwrapped String (maximum length is parameter k)
     */
    public int splitStringWidth(String string, int maxWidth)
    {
        return this.FONT_HEIGHT * this.listFormattedStringToWidth(string, maxWidth).size();
    }

    /**
     * Set unicodeFlag controlling whether strings should be rendered with Unicode fonts instead of the default.png
     * font.
     */
    public void setUnicodeFlag(boolean unicodeFlag)
    {
        this.unicodeFlag = unicodeFlag;
    }

    /**
     * Get unicodeFlag controlling whether strings should be rendered with Unicode fonts instead of the default.png
     * font.
     */
    public boolean getUnicodeFlag()
    {
        return this.unicodeFlag;
    }

    /**
     * Set bidiFlag to control if the Unicode Bidirectional Algorithm should be run before rendering any string.
     */
    public void setBidiFlag(boolean bidiFlag)
    {
        this.bidiFlag = bidiFlag;
    }

    /**
     * Breaks a string into a list of pieces that will fit a specified width.
     */
    public List<String> listFormattedStringToWidth(String string, int maxWidth)
    {
        return Arrays.asList(this.wrapFormattedStringToWidth(string, maxWidth).split("\n"));
    }

    /**
     * Inserts newline and formatting into a string to wrap it within the specified width.
     */
    private String wrapFormattedStringToWidth(String string, int maxWidth)
    {
        int j = this.sizeStringToWidth(string, maxWidth);

        if (string.length() <= j)
        {
            return string;
        } else
        {
            String s1 = string.substring(0, j);
            char c0 = string.charAt(j);
            boolean flag = c0 == 32 || c0 == 10;
            String s2 = getFormatFromString(s1) + string.substring(j + (flag ? 1 : 0));
            return s1 + "\n" + this.wrapFormattedStringToWidth(s2, maxWidth);
        }
    }

    /**
     * Determines how many characters from the string will fit into the specified width.
     */
    private int sizeStringToWidth(String string, int width)
    {
        int stringLength = string.length();
        int k = 0;
        int l = 0;
        int i1 = -1;

        for (boolean flag = false; l < stringLength; ++l)
        {
            char c0 = string.charAt(l);

            switch (c0)
            {
                case 10:
                    --l;
                    break;
                case 167:
                    if (l < stringLength - 1)
                    {
                        ++l;
                        char c1 = string.charAt(l);

                        if (c1 != 108 && c1 != 76)
                        {
                            if (c1 == 114 || c1 == 82 || isFormatColor(c1))
                            {
                                flag = false;
                            }
                        } else
                        {
                            flag = true;
                        }
                    }

                    break;
                case 32:
                    i1 = l;
                default:
                    k += this.getCharWidth(c0);

                    if (flag)
                    {
                        ++k;
                    }
            }

            if (c0 == 10)
            {
                ++l;
                i1 = l;
                break;
            }

            if (k > width)
            {
                break;
            }
        }

        return l != stringLength && i1 != -1 && i1 < l ? i1 : l;
    }

    /**
     * Checks if the char code is a hexadecimal character, used to set colour.
     */
    private static boolean isFormatColor(char c)
    {
        return c >= 48 && c <= 57 || c >= 97 && c <= 102 || c >= 65 && c <= 70;
    }

    /**
     * Checks if the char code is O-K...lLrRk-o... used to set special formatting.
     */
    private static boolean isFormatSpecial(char c)
    {
        return c >= 107 && c <= 111 || c >= 75 && c <= 79 || c == 114 || c == 82;
    }

    /**
     * Digests a string for nonprinting formatting characters then returns a string containing only that formatting.
     */
    private static String getFormatFromString(String string)
    {
        String s1 = "";
        int i = -1;
        int j = string.length();

        while ((i = string.indexOf(167, i + 1)) != -1)
        {
            if (i < j - 1)
            {
                char c0 = string.charAt(i + 1);

                if (isFormatColor(c0))
                {
                    s1 = "\u00a7" + c0;
                } else if (isFormatSpecial(c0))
                {
                    s1 = s1 + "\u00a7" + c0;
                }
            }
        }

        return s1;
    }

    /**
     * Get bidiFlag that controls if the Unicode Bidirectional Algorithm should be run before rendering any string
     */
    public boolean getBidiFlag()
    {
        return this.bidiFlag;
    }
}
