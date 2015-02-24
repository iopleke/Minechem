package minechem.handler;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.graph.ConnectivityChecker;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.interfaces.IMoleculeSet;
import org.openscience.cdk.layout.StructureDiagramGenerator;
import org.openscience.cdk.renderer.AtomContainerRenderer;
import org.openscience.cdk.renderer.RendererModel;
import org.openscience.cdk.renderer.font.AWTFontManager;
import org.openscience.cdk.renderer.generators.BasicAtomGenerator;
import org.openscience.cdk.renderer.generators.BasicBondGenerator;
import org.openscience.cdk.renderer.generators.BasicSceneGenerator;
import org.openscience.cdk.renderer.visitor.AWTDrawVisitor;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesParser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MoleculeImageParser
{
    static int WIDTH = 3000;
    static int HEIGHT = 2000;
    static int MIN_HEIGHT = 200;

    public static int[] parser(String name, String smiles)
    {
        try
        {
            SmilesParser parser = new SmilesParser(SilentChemObjectBuilder.getInstance());
            IMolecule molecule = parser.parseSmiles(smiles);
            BufferedImage image = moleculeToBufferedImage(molecule, false);
            //ImageIO.write(image, "PNG", new File("C:\\Users\\Charlie\\Documents\\Modding\\Minechem\\Minechem\\run\\compounds\\"+smiles.replaceAll("\\.","`").replaceAll("\\\\","~").replaceAll("\\/",";")+".png"));
            ImageIO.write(image, "PNG", new File("C:\\Users\\Charlie\\Documents\\Modding\\Minechem\\Minechem\\run\\compounds\\"+name.toLowerCase().replaceAll("\\s","_")+".png"));
            return new int[]{image.getHeight(),image.getWidth()};
        } catch (Exception e)
        {
            return null;
        }
    }

    private static BufferedImage moleculeToBufferedImage(IMolecule molecule, boolean freeSize) throws CDKException
    {
        BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D)image.getGraphics();
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, WIDTH, HEIGHT);
        List generators = new ArrayList();
        generators.add(new BasicSceneGenerator());
        generators.add(new BasicBondGenerator());
        generators.add(new BasicAtomGenerator());
        AtomContainerRenderer renderer = new AtomContainerRenderer(generators, new AWTFontManager());
        if (ConnectivityChecker.isConnected(molecule))
        {
            StructureDiagramGenerator sdg = new StructureDiagramGenerator();
            sdg.setMolecule(molecule);
            sdg.generateCoordinates(); //TODO: handle disparate molecules
            molecule = sdg.getMolecule();
            Rectangle bounds = new Rectangle(WIDTH, HEIGHT);
            renderer.setup(molecule, bounds);
            RendererModel model = renderer.getRenderer2DModel();
            model.set(BasicSceneGenerator.ZoomFactor.class, 1.3D);
            renderer.paint(molecule, new AWTDrawVisitor(g2));
        }
        else
        {
            List<BufferedImage> bufferedImages = new ArrayList<BufferedImage>();
            for (IAtomContainer mol : ConnectivityChecker.partitionIntoMolecules(molecule).molecules())
            {
                bufferedImages.add(moleculeToBufferedImage((IMolecule)mol, true));
            }
            int width = (int)Math.round(Math.sqrt(bufferedImages.size()));
            int rows = (int)Math.ceil((double)bufferedImages.size()/width);
            int totalWidth = 0;
            for (BufferedImage bufferedImage : bufferedImages) totalWidth+=bufferedImage.getWidth();
            int lineWidth = totalWidth / rows;

            int posX=0;
            int posY=100;
            int maxY = 0;
            for (BufferedImage bufferedImage : bufferedImages)
            {
                g2.drawImage(bufferedImage, 100+posX, posY,null);
                posX+=bufferedImage.getWidth();
                if (bufferedImage.getHeight()>maxY) maxY = bufferedImage.getHeight();
                if (posX>lineWidth)
                {
                    posX = 0;
                    posY+=maxY;
                    maxY=0;
                }
            }
        }
        image = makeColorTransparent(image, Color.WHITE);
        return getCroppedImage(image, 0, freeSize ? 50 : MIN_HEIGHT);
    }

    private static BufferedImage imageToBufferedImage(Image image) {

        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bufferedImage.createGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        return bufferedImage;
    }

    public static BufferedImage makeColorTransparent(BufferedImage im, final Color color)
    {
        ImageFilter filter = new RGBImageFilter() {

            // the color we are looking for... Alpha bits are set to opaque
            public int markerRGB = color.getRGB() | 0xFF000000;

            public final int filterRGB(int x, int y, int rgb) {
                //if ((rgb | 0xFF000000)== markerRGB) {
                if (!colorWithinTolerance((rgb | 0xFF000000),markerRGB,0.1D)) {
                    // Mark the alpha bits as zero - transparent
                    return 0x00FFFFFF & rgb;
                } else {
                    // nothing to do
                    return rgb;
                }
            }
        };

        ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
        return imageToBufferedImage(Toolkit.getDefaultToolkit().createImage(ip));
    }

    public static BufferedImage getCroppedImage(BufferedImage source, double tolerance, int min_dimension) {
        // Get our top-left pixel color as our "baseline" for cropping
        min_dimension--;
        int baseColor = source.getRGB(0, 0);

        int width = source.getWidth();
        int height = source.getHeight();

        int topY = Integer.MAX_VALUE, topX = Integer.MAX_VALUE;
        int bottomY = -1, bottomX = -1;
        for(int y=0; y<height; y++) {
            for(int x=0; x<width; x++) {
                if (colorWithinTolerance(baseColor, source.getRGB(x, y), tolerance)) {
                    if (x < topX) topX = x;
                    if (y < topY) topY = y;
                    if (x > bottomX) bottomX = x;
                    if (y > bottomY) bottomY = y;
                }
            }
        }
        int dif = 20;
        if (bottomX - topX < min_dimension)
        {
            dif = min_dimension - (bottomX - topX);
        }
        int difT = dif/2;
        dif -= difT;
        topX-=difT;
        bottomX+=dif;
        if (bottomX-topX==min_dimension-2) topX++;
        dif = 20;
        if (bottomY - topY < min_dimension)
        {
            dif = min_dimension - (bottomY - topY);
        }
        difT = dif/2;
        dif-=difT;
        topY-=difT;
        bottomY+=dif;
        if (bottomY-topY==min_dimension-2) topY++;
        
        BufferedImage destination = new BufferedImage( (bottomX-topX+1),
                (bottomY-topY+1), BufferedImage.TYPE_INT_ARGB);

        destination.getGraphics().drawImage(source, 0, 0,
                destination.getWidth(), destination.getHeight(),
                topX, topY, bottomX, bottomY, null);

        return destination;
    }

    private static boolean colorWithinTolerance(int a, int b, double tolerance) {
        int aAlpha  = (int)((a & 0xFF000000) >>> 24);   // Alpha level
        int aRed    = (int)((a & 0x00FF0000) >>> 16);   // Red level
        int aGreen  = (int)((a & 0x0000FF00) >>> 8);    // Green level
        int aBlue   = (int)(a & 0x000000FF);            // Blue level

        int bAlpha  = (int)((b & 0xFF000000) >>> 24);   // Alpha level
        int bRed    = (int)((b & 0x00FF0000) >>> 16);   // Red level
        int bGreen  = (int)((b & 0x0000FF00) >>> 8);    // Green level
        int bBlue   = (int)(b & 0x000000FF);            // Blue level

        double distance = Math.sqrt((aAlpha-bAlpha)*(aAlpha-bAlpha) +
                (aRed-bRed)*(aRed-bRed) +
                (aGreen-bGreen)*(aGreen-bGreen) +
                (aBlue-bBlue)*(aBlue-bBlue));

        // 510.0 is the maximum distance between two colors 
        // (0,0,0,0 -> 255,255,255,255)
        double percentAway = distance / 510.0d;

        return (percentAway > tolerance);
    }
}
