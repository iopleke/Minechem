package minechem.fluid;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minechem.Settings;
import minechem.potion.PotionPharmacologyEffect;
import minechem.utils.Reference;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class FluidBlockChemical extends BlockFluidClassic
{
    @SideOnly(Side.CLIENT)
    protected IIcon stillIcon;
    @SideOnly(Side.CLIENT)
    protected IIcon flowingIcon;

    public FluidBlockChemical(Fluid fluid, Material material)
    {
        super(fluid, material);
    }

    public FluidBlockChemical(Fluid fluid)
    {
        super(fluid, Material.water);
        this.setBlockName(fluidName);
    }

    @Override
    public String getUnlocalizedName()
    {
        return "fluid." + fluidName;
    }

    @Override
    public String getLocalizedName()
    {
        return StatCollector.translateToLocal("fluid." + fluidName + ".name");
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

    @Override
    public int colorMultiplier(IBlockAccess block, int x, int y, int z)
    {
        return getFluid().getColor();
    }

    @Override
    public int getRenderColor(int i)
    {
        return getFluid().getColor();
    }

    @Override
    public int getBlockColor()
    {
        return getFluid().getColor();
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
    {
        if (entity instanceof EntityLivingBase && Settings.fluidEffects)
        {
            PotionPharmacologyEffect.triggerPlayerEffect(((FluidChemical) getFluid()).molecule, (EntityLivingBase) entity);
        }
    }
}
