package minechem.fluid;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import minechem.utils.Reference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraftforge.client.event.TextureStitchEvent;

public class FluidTextureStitchHandler
{

	@SubscribeEvent
	public void onStitch(TextureStitchEvent.Pre event)
	{
		if (event.map.getTextureType() == 0)
		{
			IIconRegister ir = event.map;
			for (FluidElement fluidElement : FluidHelper.elements.values())
			{
				fluidElement.setIcons(ir.registerIcon(Reference.TEXTURE_MOD_ID + "fluid_still"), ir.registerIcon(Reference.TEXTURE_MOD_ID + "fluid_flow"));
			}
			for (FluidChemical fluidChemical : FluidHelper.molecules.values())
			{
				fluidChemical.setIcons(ir.registerIcon(Reference.TEXTURE_MOD_ID + "fluid_still"), ir.registerIcon(Reference.TEXTURE_MOD_ID + "fluid_flow"));
			}
		}
	}
}
