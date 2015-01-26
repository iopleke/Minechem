package minechem.apparatus.tier1.opticalMicroscope;

import minechem.apparatus.prefab.renderer.BasicTileEntityRenderer;
import minechem.reference.Compendium;

public class OpticalMicroscopeTileEntityRenderer extends BasicTileEntityRenderer
{

    public OpticalMicroscopeTileEntityRenderer()
    {
        super(0.4F, 0.0625F, 0.6D);
        model = new OpticalMicroscopeModel();
        texture = Compendium.Resource.Model.microscope;
    }

}
