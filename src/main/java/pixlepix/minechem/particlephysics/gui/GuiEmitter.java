package pixlepix.particlephysics.common.gui;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import pixlepix.particlephysics.common.api.BaseParticle;
import pixlepix.particlephysics.common.helper.PacketHandler;
import pixlepix.particlephysics.common.helper.ParticleRegistry;
import pixlepix.particlephysics.common.tile.EmitterTileEntity;

//Thanks to VSWE for assorted bits of the code
public class GuiEmitter extends GuiContainer {
	private static final ResourceLocation texture = new ResourceLocation(
			"particlephysics", "textures/gui/emitter.png");
	private EmitterTileEntity tile;

	public static final GuiRectangle bar = new GuiRectangle(50, 50, 86, 6);

	public static final GuiRectangle slider = new GuiRectangle(75, 47, 6, 11);
	public GuiEmitter(InventoryPlayer invPlayer, EmitterTileEntity tile) {
		super(new ContainerEmitter(invPlayer, tile));
		this.tile=tile;

		xSize = 176;
		ySize = 218;


	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		float filled = (float) (tile.fuelStored *.01);
		int barHeight = (int)(filled*29);
		if (barHeight > 0) {
			int srcX = xSize;
			int srcY = 29 - barHeight;
			drawTexturedModalRect(guiLeft+4, guiTop+ 46+29 - barHeight, srcX, srcY, 28, barHeight);
		}
		
		if(!this.isDragging){

			this.tempHeightSetting=tile.interval;
		}
		
		bar.draw(this, 0, 250);
		this.updateSliderPosition();
		slider.draw(this, 0, 239);

		fontRenderer.drawString((this.tempHeightSetting+1)+" Seconds", 190, 70, 0x404040);

		//Render selected particle face
		Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
		if(tile.getStackInSlot(0)!=null){
			BaseParticle particle=tile.getParticleFromFuel(tile.getStackInSlot(0).itemID, tile.getStackInSlot(0).getItemDamage());
			
			if(particle!=null){
				Icon icon=ParticleRegistry.getIconFromInstance(particle);
				if(icon!=null){
					this.drawTexturedModelRectFromIcon(guiLeft+30, guiTop+16, icon, 16, 16);
				}
			}
		}


	}


	public int getLeft(){
		return this.guiLeft;
	}

	public int getTop(){
		return this.guiTop;
	}

	public void drawHoverString(List l, int w, int h){
		this.drawHoveringText(l, w, h,fontRenderer);
	}
	@Override
	protected void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
		PacketHandler.sendInterfacePacket((byte)0,(byte) button.id);

	}

	private int tempHeightSetting=25;
	private boolean isDragging;
	@Override
	public void initGui() {
		super.initGui();
		buttonList.clear();
		GuiButton clearButton = new GuiButton(0, guiLeft + 80, guiTop + 14,80, 20, "Clear Fuel");
		buttonList.add(clearButton);

	}



	@Override
	public void mouseClicked(int x, int y, int button) {
		super.mouseClicked(x, y, button);
		if(slider.inRect(this, x, y)){
			isDragging=true;
			tempHeightSetting=tile.interval;
		}
	}

	@Override
	public void mouseClickMove(int x, int y, int button,
			long timeSinceClicked) {
		super.mouseClickMove(x, y, button, timeSinceClicked);
		if(isDragging){
			tempHeightSetting = (x-getLeft()-50);
			if(tempHeightSetting<0){
				tempHeightSetting=00;
			}
			if(tempHeightSetting>85){
				tempHeightSetting=85;
			}
		}
	}

	@Override
	public void mouseMovedOrUp(int x, int y, int button) {
		super.mouseMovedOrUp(x, y, button);
		if(isDragging){
			PacketHandler.sendInterfacePacket((byte)1,(byte)tempHeightSetting);

			tile.interval=tempHeightSetting;
			this.isDragging=false;
		}
	}

	private void updateSliderPosition(){

		slider.setX(50+(isDragging ? tempHeightSetting : tile.interval));
	}
}
