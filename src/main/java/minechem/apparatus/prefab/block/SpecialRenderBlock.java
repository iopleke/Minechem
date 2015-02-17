package minechem.apparatus.prefab.block;

import minechem.proxy.client.ClientProxy;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public abstract class SpecialRenderBlock extends Block
{

    protected SpecialRenderBlock(Material material)
    {
        super(material);
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public int getRenderType()
    {
        return ClientProxy.ISBRH_ID;
    }
}
