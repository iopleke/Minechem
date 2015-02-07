package minechem.apparatus.tier1.electrolysis;

import minechem.apparatus.prefab.renderer.BasicTileEntityRenderer;
import minechem.Compendium;

public class ElectrolysisTileEntityRenderer extends BasicTileEntityRenderer
{

    public ElectrolysisTileEntityRenderer()
    {
        super(0.4F, 0.0625F);

        setOffset(0.5D, 0.6D, 0.5D);

        model = new ElectrolysisModel();
        texture = Compendium.Resource.Model.electrolysis;
    }

}
