package pixlepix.particlephysics.common.helper;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BasicComplexBlock extends Block implements IBlock {

	public Icon connectorIcon;
	public Icon topIcon;
	static int blockIdIncrement;
	public String textureBase="particlephysics:";
	public abstract String getFront();
	public abstract String getTop();
	
	@Override
	public boolean renderAsNormalBlock(){
		return true;
	}
	
	@Override
	public boolean isOpaqueCube(){
		return false;
	}

	public boolean hasModel(){
		return false;
	}
	
	public BasicComplexBlock(int id, Material material) {
		super(id,material);

		this.setUnlocalizedName(this.getName());
		
	}
	public BasicComplexBlock(int id) {
		super(id, Material.iron);

		this.setUnlocalizedName(this.getName());
		
	}
	
	public boolean inCreativeTab(){
		return true;
	}
	 @SideOnly(Side.CLIENT)
	 @Override
	 public int getRenderType()
	    {
	    	if(this.hasModel()){
	    		return ClientProxy.RENDER_ID;
	    	}
	    	return 0;
	    }
	


	 
	 
	 @Override
		@SideOnly(Side.CLIENT)
		public void registerIcons(IconRegister par1IconRegister) {

			blockIcon = par1IconRegister
					.registerIcon(textureBase+this.getFront());
			topIcon = par1IconRegister.registerIcon(textureBase+this.getTop());
		}

		@Override
		public Icon getIcon(int side, int meta) {
		if(topSidedTextures()){
				 
				if (side == 1 || side == 0) {
					return topIcon;
				}
			}
			return blockIcon;
			
		}
	
	
	@Override
	public void onBlockPlacedBy(World par1World, int x,int y, int z, EntityLivingBase par5EntityLiving, ItemStack par6ItemStack){

		int angle = MathHelper.floor_double((par5EntityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		int change = 0;

		switch (angle)
		{
			case 0:
				change = 1;
				break;
			case 1:
				change = 2;
				break;
			case 2:
				change = 0;
				break;
			case 3:
				change = 3;
				break;
		}
		par1World.setBlockMetadataWithNotify(x, y, z, change, 2);
		par1World.notifyBlocksOfNeighborChange(x, y, z, this.blockID);
	}
	
	@Override
	public TileEntity createTileEntity(World var1, int metadata) {
		try {
			return getTileEntityClass().newInstance();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Error while creating tile entity");
		return null;

	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return getTileEntityClass()!=null;
	}
	
	@Override
	public void addRecipe() {
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "BasicComplexBlock";
	}

	@Override
	public boolean hasItemBlock() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Class getItemBlock() {
		// TODO Auto-generated method stub
		
		return ItemBlock.class;
	}
	
	public boolean topSidedTextures(){
		return true;
	}
	
	@Override
	public Class<TileEntity> getTileEntityClass() {
		return null;
	}
	public boolean isPlantMaterial() {
		return false;
	}

}
