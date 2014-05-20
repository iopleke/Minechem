package minechem.tileentity.blueprintprojector;

import java.util.ArrayList;

import minechem.ModMinechem;
import minechem.block.BlockMinechemContainer;
import minechem.item.blueprint.ItemBlueprint;
import minechem.item.blueprint.MinechemBlueprint;
import minechem.network.server.CommonProxy;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockBlueprintProjector extends BlockMinechemContainer
{

    public BlockBlueprintProjector(int id)
    {
        super(id, Material.iron);
        setUnlocalizedName("minechem.blockBlueprintProjector");
        setCreativeTab(ModMinechem.CREATIVE_TAB);
        setLightValue(0.7F);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase el, ItemStack is)
    {
        super.onBlockPlacedBy(world, x, y, z, el, is);
        int facing = MathHelper.floor_double(el.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        world.setBlockMetadataWithNotify(x, y, z, facing, 2);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float par7, float par8, float par9)
    {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if (tileEntity instanceof TileEntityBlueprintProjector)
        {
            entityPlayer.openGui(ModMinechem.INSTANCE, 0, world, x, y, z);
            return true;
        }
        return false;
    }

    private ItemStack takeBlueprintFromProjector(TileEntityBlueprintProjector projector)
    {
        MinechemBlueprint blueprint = projector.takeBlueprint();
        ItemStack blueprintItem = ItemBlueprint.createItemStackFromBlueprint(blueprint);
        return blueprintItem;
    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileEntityBlueprintProjector();
    }

    @Override
    public void addStacksDroppedOnBlockBreak(TileEntity tileEntity, ArrayList<ItemStack> itemStacks)
    {
        if (tileEntity instanceof TileEntityBlueprintProjector)
        {
            TileEntityBlueprintProjector projector = (TileEntityBlueprintProjector) tileEntity;
            if (projector.hasBlueprint())
                itemStacks.add(takeBlueprintFromProjector(projector));
        }
        return;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public int getRenderType()
    {
        return CommonProxy.RENDER_ID;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

}