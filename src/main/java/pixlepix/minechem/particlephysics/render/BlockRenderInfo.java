package pixlepix.particlephysics.common.render;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import pixlepix.particlephysics.common.helper.Vector3;

/** Used to store info on a block mainly used for rendering */
public class BlockRenderInfo
{
    /** Block lower corner size */
    public Vector3 min = new Vector3(0, 0, 0);
    /** Block higher corner size */
    public Vector3 max = new Vector3(1, 1, 1);
    /** Block to pull info from */
    public Block baseBlock = Block.sand;
    /** Override render texture */
    public Icon texture = null;
    /** meta data to use for block the block */
    public int meta = 0;
    public BlockRenderInfo(Icon texture){
    	this.texture=texture;
    }
    /** Gets the block brightness at the given location */
    public float getBlockBrightness(IBlockAccess iblockaccess, int i, int j, int k)
    {
        return baseBlock.getBlockBrightness(iblockaccess, i, j, k);
    }

    /** Gets the block texture from the given side */
    public Icon getBlockTextureFromSide(int side)
    {
        return this.texture;
    }

    /** Gets the block texture from side and meta */
    public Icon getBlockIconFromSideAndMetadata(int side, int meta)
    {
        return this.texture;
    }

    /** Gets the icon and does some safty checks */
    public Icon getIconSafe(Icon par1Icon)
    {
        Icon icon = par1Icon;
        if (this.texture != null)
        {
            icon = texture;
        }
        if (par1Icon == null)
        {
            icon = ((TextureMap) Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.locationBlocksTexture)).getAtlasSprite("missingno");
        }
        return icon;
    }
}