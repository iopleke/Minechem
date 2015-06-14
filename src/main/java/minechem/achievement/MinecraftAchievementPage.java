package minechem.achievement;

import java.util.Random;
import minechem.asm.MinechemHooks;
import minechem.proxy.client.render.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public class MinecraftAchievementPage
{
    public static void drawBackground(Minecraft mc, float z, float scale, int columnWidth, int rowHeight)
    {
        int i = columnWidth + 288 >> 4;
        int j = rowHeight + 288 >> 4;
        int k = (columnWidth + 288) % 16;
        int l = (rowHeight + 288) % 16;
        Random random = new Random();
        float scaled = 16.0F / scale;
        int row, column, icon;

        for (row = 0; row * scaled - l < 155.0F; ++row)
        {
            float grayScale = 0.6F - (j + row) / 25.0F * 0.3F;
            MinechemHooks.resetGreyscale(grayScale);

            for (column = 0; column * scaled - k < 224.0F; ++column)
            {
                random.setSeed(mc.getSession().getPlayerID().hashCode() + i + column + (j + row) * 16);
                icon = random.nextInt(1 + j + row) + (j + row) / 2;
                IIcon iicon = Blocks.bedrock.getIcon(0, 0);

                if (icon <= 37 && j + row != 35)
                {
                    if (icon == 22)
                    {
                        if (random.nextInt(2) == 0)
                        {
                            iicon = Blocks.diamond_ore.getIcon(0, 0);
                        } else
                        {
                            iicon = Blocks.redstone_ore.getIcon(0, 0);
                        }
                    } else if (icon == 10)
                    {
                        iicon = Blocks.iron_ore.getIcon(0, 0);
                    } else if (icon == 8)
                    {
                        iicon = Blocks.coal_ore.getIcon(0, 0);
                    } else if (icon > 4)
                    {
                        iicon = Blocks.stone.getIcon(0, 0);
                    } else if (icon > 0)
                    {
                        iicon = Blocks.dirt.getIcon(0, 0);
                    }
                }

                mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
                RenderHelper.drawTexturedRectUV(column * 16 - k, row * 16 - l, z, 16, 16, iicon);
            }
        }
    }
}
