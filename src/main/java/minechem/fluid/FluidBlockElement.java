package minechem.fluid;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minechem.utils.Reference;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class FluidBlockElement extends BlockFluidClassic
{
    @SideOnly(Side.CLIENT)
    protected IIcon stillIcon;
    @SideOnly(Side.CLIENT)
    protected IIcon flowingIcon;

    public FluidBlockElement(Fluid fluid, Material material)
    {
        super(fluid, material);
    }

    public FluidBlockElement(Fluid fluid)
    {
        super(fluid, Material.water);
        this.setBlockName(fluidName);
    }

    @Override
    public String getUnlocalizedName()
    {
        return "fluid." + fluidName;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister ir)
    {
        stillIcon = ir.registerIcon(Reference.TEXTURE_MOD_ID + "fluid_still");
        flowingIcon = ir.registerIcon(Reference.TEXTURE_MOD_ID + "fluid_flow");
    }

    @Override
    public IIcon getIcon(int side, int meta)
    {
        return (side > 1) ? flowingIcon : stillIcon;
    }
}
