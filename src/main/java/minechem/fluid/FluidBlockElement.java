package minechem.fluid;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minechem.Settings;
import minechem.reference.Textures;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class FluidBlockElement extends MinechemFluidBlock
{
	@SideOnly(Side.CLIENT)
	protected IIcon stillIcon;
	@SideOnly(Side.CLIENT)
	protected IIcon flowingIcon;

	public FluidBlockElement(MinechemFluid fluid, Material material)
	{
		super(fluid, material);
	}

	public FluidBlockElement(MinechemFluid fluid)
	{
		super(fluid, materialFluidBlock);
		this.setBlockName(fluidName);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister ir)
	{
		stillIcon = ir.registerIcon(Textures.IIcon.FUILD_STILL);
		flowingIcon = ir.registerIcon(Textures.IIcon.FLUID_FLOW);
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
			int power = ((FluidElement) getFluid()).element.radioactivity().ordinal();
			if (power > 0)
			{
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.poison.getId(), 10, power - 1));
			}
		}
	}

}
