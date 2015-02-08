package fontbox;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * A font metric digest. Contains information on the font, such as the orientation and size of each glyph supported, the texture coordinate for each glyph and the resource mappings to the font image
 * file.
 *
 * @author AfterLifeLochie
 *
 */
public class FontMetric
{
    /**
     * The font image
     */
    public final ResourceLocation fontImageName;
    /**
     * The font name
     */
    public final ResourceLocation fontMetricName;
    /**
     * The individual dimensions and u-v locations of each character in the set
     */
    public final HashMap<Integer, GlyphMetric> glyphs = new HashMap<Integer, GlyphMetric>();

    /**
     * The universal width of the font image.
     *
     * TODO: This could be generated using BufferedImage, but that requires resource fiddling which might be expensive(?).
     */
    public final float fontImageWidth;
    /**
     * The universal height of the font image.
     *
     * TODO: This could be generated using BufferedImage, but that requires resource fiddling which might be expensive(?).
     */
    public final float fontImageHeight;

    public FontMetric(ResourceLocation fontImageName, int fontImageWidth,
        int fontImageHeight, ResourceLocation fontMetricName)
    {
        this.fontImageName = fontImageName;
        this.fontImageWidth = (float) fontImageWidth;
        this.fontImageHeight = (float) fontImageHeight;
        this.fontMetricName = fontMetricName;
    }

    /**
     * Attempts to build the font
     *
     * @throws FontException If the font metric file cannot be read or parsed, a {@link FontException} may be thrown.
     */
    public void buildFont() throws FontException
    {
        try
        {
            IResource metricResource = Minecraft.getMinecraft()
                .getResourceManager().getResource(fontMetricName);
            InputStream stream = metricResource.getInputStream();
            if (stream == null)
            {
                throw new IOException("Could not open font metric file.");
            }
            DocumentBuilderFactory factory = DocumentBuilderFactory
                .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(stream);
            Element metrics = doc.getDocumentElement();

            NodeList list_character = metrics.getElementsByTagName("character");
            for (int i = 0; i < list_character.getLength(); i++)
            {
                Element character = (Element) list_character.item(i);
                int charcode = Integer.parseInt(character.getAttributes()
                    .getNamedItem("key").getNodeValue());
                if (0 > charcode || charcode > 255)
                {
                    throw new FontException(String.format(
                        "Unsupported character code %s", charcode));
                }
                int w = -1, h = -1, u = -1, v = -1;
                NodeList character_properties = character.getChildNodes();
                for (int k = 0; k < character_properties.getLength(); k++)
                {
                    Node property = character_properties.item(k);
                    if (!(property instanceof Element))
                    {
                        continue;
                    }
                    Element elem = (Element) property;
                    String name = elem.getNodeName().toLowerCase();
                    int val = Integer.parseInt(elem.getFirstChild()
                        .getNodeValue());
                    if (name.equals("width"))
                    {
                        w = val;
                    } else if (name.equals("height"))
                    {
                        h = val;
                    } else if (name.equals("x"))
                    {
                        u = val;
                    } else if (name.equals("y"))
                    {
                        v = val;
                    } else
                    {
                        throw new FontException(String.format(
                            "Unexpected metric command %s", name));
                    }
                }
                if (w == -1 || h == -1 || u == -1 || v == -1)
                {
                    throw new FontException(String.format(
                        "Invalid metric properties set for key %s",
                        charcode));
                }
                glyphs.put(charcode, new GlyphMetric(w, h, u, v));
            }
        } catch (IOException e)
        {
            throw new FontException("Cannot setup font.", e);
        } catch (ParserConfigurationException e)
        {
            throw new FontException("Cannot read font metric data.", e);
        } catch (SAXException e)
        {
            throw new FontException("Cannot read font metric data.", e);
        }
    }
}
