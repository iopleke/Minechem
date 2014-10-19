package minechem.render;

import minechem.fluid.MinechemFluidBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.RenderBlockFluid;

public class ChemicalFluidBlockRenderingHandler extends RenderBlockFluid
{

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		super.renderWorldBlock(world, x, y, z, block, modelId, renderer);
		checkToRender(world, x, y, z - 1, 3, renderer, block);
		checkToRender(world, x, y, z + 1, 2, renderer, block);
		checkToRender(world, x, y - 1, z, 1, renderer, block);
		checkToRender(world, x, y + 1, z, 0, renderer, block);
		checkToRender(world, x - 1, y, z, 5, renderer, block);
		checkToRender(world, x + 1, y, z, 4, renderer, block);
		return true;
	}

	public void checkToRender(IBlockAccess world, int x, int y, int z, int side, RenderBlocks renderer, Block rendererBlock)
	{
		Block block = world.getBlock(x, y, z);
		if (block.getRenderType() == 4)
		{
			Tessellator tessellator = Tessellator.instance;
			int l = block.colorMultiplier(world, x, y, z);
			float f = (float) (l >> 16 & 255) / 255.0F;
			float f1 = (float) (l >> 8 & 255) / 255.0F;
			float f2 = (float) (l & 255) / 255.0F;

			float f4 = 1.0F;
			float f5 = 0.8F;
			float f6 = 0.6F;
			Material material = block.getMaterial();
			int i1 = world.getBlockMetadata(x, y, z);
			double d2 = (double) renderer.getLiquidHeight(x, y, z, material);
			double d3 = (double) renderer.getLiquidHeight(x, y, z + 1, material);
			double d4 = (double) renderer.getLiquidHeight(x + 1, y, z + 1, material);
			double d5 = (double) renderer.getLiquidHeight(x + 1, y, z, material);
			double d6 = 0.0010000000474974513D;
			float f9;
			float f10;
			float f11;
			int k1 = side - 2;
			int l1 = x;
			int j1 = z;

			if (k1 == 0)
			{
				j1 = z - 1;
			}

			if (k1 == 1)
			{
				++j1;
			}

			if (k1 == 2)
			{
				l1 = x - 1;
			}

			if (k1 == 3)
			{
				++l1;
			}

			IIcon iicon1 = renderer.getBlockIconFromSideAndMetadata(block, k1 + 2, i1);

			double d9;
			double d11;
			double d13;
			double d15;
			double d17;
			double d19;

			if (k1 == 0)
			{
				d9 = d2;
				d11 = d5;
				d13 = (double) x;
				d17 = (double) (x + 1);
				d15 = (double) z + d6;
				d19 = (double) z + d6;
			} else if (k1 == 1)
			{
				d9 = d4;
				d11 = d3;
				d13 = (double) (x + 1);
				d17 = (double) x;
				d15 = (double) (z + 1) - d6;
				d19 = (double) (z + 1) - d6;
			} else if (k1 == 2)
			{
				d9 = d3;
				d11 = d2;
				d13 = (double) x + d6;
				d17 = (double) x + d6;
				d15 = (double) (z + 1);
				d19 = (double) z;
			} else
			{
				d9 = d5;
				d11 = d4;
				d13 = (double) (x + 1) - d6;
				d17 = (double) (x + 1) - d6;
				d15 = (double) z;
				d19 = (double) (z + 1);
			}

			float f8 = iicon1.getInterpolatedU(0.0D);
			f9 = iicon1.getInterpolatedU(8.0D);
			f10 = iicon1.getInterpolatedV((1.0D - d9) * 16.0D * 0.5D);
			f11 = iicon1.getInterpolatedV((1.0D - d11) * 16.0D * 0.5D);
			float f12 = iicon1.getInterpolatedV(8.0D);
			tessellator.setBrightness(block.getMixedBrightnessForBlock(world, l1, y, j1));
			float f13 = 1.0F;
			f13 *= k1 < 2 ? f5 : f6;
			tessellator.setColorOpaque_F(f4 * f13 * f, f4 * f13 * f1, f4 * f13 * f2);
			tessellator.addVertexWithUV(d13, (double) y + d9, d15, (double) f8, (double) f10);
			tessellator.addVertexWithUV(d17, (double) y + d11, d19, (double) f9, (double) f11);
			tessellator.addVertexWithUV(d17, (double) (y + 0), d19, (double) f9, (double) f12);
			tessellator.addVertexWithUV(d13, (double) (y + 0), d15, (double) f8, (double) f12);
			tessellator.addVertexWithUV(d13, (double) (y + 0), d15, (double) f8, (double) f12);
			tessellator.addVertexWithUV(d17, (double) (y + 0), d19, (double) f9, (double) f12);
			tessellator.addVertexWithUV(d17, (double) y + d11, d19, (double) f9, (double) f11);
			tessellator.addVertexWithUV(d13, (double) y + d9, d15, (double) f8, (double) f10);

		}
	}

	@Override
	public int getRenderId()
	{
		return MinechemFluidBlock.RENDER_ID;
	}
}
