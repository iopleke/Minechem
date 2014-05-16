package minechem.api;

import net.minecraft.tileentity.TileEntity;

public interface IBlock
{

    public Class<TileEntity> getTileEntityClass();

    public void addRecipe();

    public String getName();

    public boolean hasItemBlock();

    public Class getItemBlock();

    public boolean inCreativeTab();

}
