package minechem.tileentity.blueprintprojector;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

<<<<<<< HEAD
<<<<<<< HEAD
import minechem.MinechemItemGeneration;
=======
import minechem.MinechemItemsGeneration;
>>>>>>> MaxwolfRewrite
=======
import minechem.MinechemItemsGeneration;
>>>>>>> MaxwolfRewrite
import minechem.ModMinechem;
import minechem.gui.GuiContainerTabbed;
import minechem.gui.GuiTabHelp;
import minechem.item.blueprint.BlueprintBlock;
import minechem.item.blueprint.MinechemBlueprint;
import minechem.utils.MinechemHelper;
import minechem.utils.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class GuiProjector extends GuiContainerTabbed
{

    TileEntityBlueprintProjector projector;

    public GuiProjector(InventoryPlayer inventoryPlayer, TileEntityBlueprintProjector projector)
    {
        super(new ContainerProjector(inventoryPlayer, projector));
        this.projector = projector;
        addTab(new GuiTabHelp(this, MinechemHelper.getLocalString("help.projector")));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.renderEngine.bindTexture(new ResourceLocation(ModMinechem.ID, Reference.PROJECTOR_GUI));
        ;
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 0.0F);
        ItemStack blueprintStack = projector.getStackInSlot(0);
        if (blueprintStack != null)
        {
            drawBlueprintInfo(blueprintStack);
        }
        GL11.glPopMatrix();
    }

    private HashMap<Integer, Integer> getBlockCount(MinechemBlueprint blueprint)
    {
        HashMap<Integer, Integer> blockCount = new HashMap<Integer, Integer>();

        for (int x = 0; x < blueprint.xSize; x++)
        {
            for (int y = 0; y < blueprint.ySize; y++)
            {
                for (int z = 0; z < blueprint.zSize; z++)
                {
                    int structureID = blueprint.getStructure()[y][x][z];
                    int count = 0;
                    if (blockCount.get(structureID) != null)
                        count = blockCount.get(structureID);
                    count++;
                    blockCount.put(structureID, count);
                }
            }
        }

        return blockCount;
    }

    private void drawBlueprintInfo(ItemStack blueprintStack)
    {
<<<<<<< HEAD
<<<<<<< HEAD
        MinechemBlueprint blueprint = MinechemItemGeneration.blueprint.getBlueprint(blueprintStack);
=======
        MinechemBlueprint blueprint = MinechemItemsGeneration.blueprint.getBlueprint(blueprintStack);
>>>>>>> MaxwolfRewrite
=======
        MinechemBlueprint blueprint = MinechemItemsGeneration.blueprint.getBlueprint(blueprintStack);
>>>>>>> MaxwolfRewrite
        if (blueprint == null)
        {
            return;
        }
        String name = blueprintStack.getDisplayName().replace("Blueprint", "");
        this.fontRenderer.drawStringWithShadow(name, 64, 12, 0xFFFFFF);
        HashMap<Integer, Integer> blockCount = getBlockCount(blueprint);
        HashMap<Integer, BlueprintBlock> blockLookup = blueprint.getBlockLookup();
        int y = 23;
        Iterator<Entry<Integer, Integer>> it = blockCount.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry<Integer, Integer> entry = it.next();
            BlueprintBlock block = blockLookup.get(entry.getKey());
            if (block != null)
            {
                ItemStack itemstack = new ItemStack(block.block, 1, block.metadata);
                String info = String.format("%dx%s", entry.getValue(), itemstack.getDisplayName());
                this.fontRenderer.drawString(info, 64, y, 0xDDDDDD);
                y += 10;
            }
        }
    }

    @Override
    public boolean hideItemPanelSlot(GuiContainer gui, int x, int y, int w, int h)
    {
        return false;
    }

}
