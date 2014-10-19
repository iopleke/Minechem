package minechem.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

public class ScissorHelper
{

	public static void startScissor(Minecraft minecraft, int x, int y, int w, int h)
	{
		ScaledResolution scaledRes = new ScaledResolution(minecraft, minecraft.displayWidth, minecraft.displayHeight);
		int scale = scaledRes.getScaleFactor();

		int scissorWidth = w * scale;
		int scissorHeight = h * scale;
		int scissorX = x * scale;
		int scissorY = minecraft.displayHeight - scissorHeight - (y * scale);

		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		GL11.glScissor(scissorX, scissorY, scissorWidth, scissorHeight);
	}

	public static void endScissor()
	{
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
	}
}
