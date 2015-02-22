package minechem.apparatus.tier1.centrifuge;

import minechem.Compendium;
import minechem.apparatus.prefab.renderer.BasicTileEntityRenderer;

public class CentrifugeTileEntityRenderer extends BasicTileEntityRenderer
{
    public CentrifugeTileEntityRenderer()
    {
        super(0.24F, 0.0625F);

        setOffset(0.5D, 0.62D, 0.5D);

        model = new CentrifugeModel();
        texture = Compendium.Resource.Model.centrifuge;
    }

}
