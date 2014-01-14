package pixlepix.particlephysics.common.blocks;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import pixlepix.particlephysics.common.ParticlePhysics;
import pixlepix.particlephysics.common.helper.BasicComplexBlock;
import pixlepix.particlephysics.common.helper.ParticleRegistry;
import pixlepix.particlephysics.common.tile.EmitterTileEntity;
import cpw.mods.fml.common.registry.GameRegistry;

public class Emitter extends BasicComplexBlock {

	
	
	public Emitter() {
		super(1178);
	}
	public Emitter(int i) {
		super(i);
	}
	@Override
	public String getFront() {
		// TODO Auto-generated method stub
		return "Emitter";
	}
	@Override
	public boolean hasModel(){
		return true;
	}
	@Override
	public String getTop() {
		// TODO Auto-generated method stub
		return "EmitterTop";
	}

	
	@Override
	public Class getTileEntityClass() {
		return EmitterTileEntity.class;
	}

	@Override
	public void addRecipe() {
		GameRegistry.addRecipe(new ItemStack(this),"I  ","IID","I  ",'I',new ItemStack(Item.ingotIron),'D',new ItemStack(Item.diamond));
		
	}

	@Override
	public String getName() {
		return "Emitter";
	}

	@Override
	public boolean hasItemBlock() {
		return true;
	}

	@Override
	public Class getItemBlock() {
		return null;
		
	}
	@Override
	public boolean topSidedTextures(){
		return true;
	}
	
	@Override
	public void registerIcons(IconRegister icon){
		super.registerIcons(icon);
		//This is so hacky it makes me ashamed
		ParticleRegistry.populateIcons(icon);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float hitX, float hitY, float hitZ){
			/*
			if(world.getBlockTileEntity(x,y,z) instanceof EmitterTileEntity){
				if(!world.isRemote&&entityPlayer.inventory.getCurrentItem()!=null&&((EmitterTileEntity) world.getBlockTileEntity(x,y,z)).isValidFuel(entityPlayer.inventory.getCurrentItem().itemID)){
					
				EmitterTileEntity emitter=(EmitterTileEntity) world.getBlockTileEntity(x,y,z);
				if(emitter.inventory==null||(entityPlayer.getHeldItem()!=null&&emitter.inventory.getItem()!=entityPlayer.getHeldItem().getItem())&&emitter.inventory.getItemDamage()!=entityPlayer.getHeldItem().getItemDamage()){
					if(emitter.inventory!=null){
						world.spawnEntityInWorld(new EntityItem(world,x+0.5,y+0.5,z+0.5,emitter.inventory));
					}
					emitter.inventory=new ItemStack(entityPlayer.getHeldItem().getItem(),1,entityPlayer.getHeldItem().getItemDamage());
				}else{
					emitter.inventory.stackSize++;
				}
				entityPlayer.inventory.consumeInventoryItem(entityPlayer.getHeldItem().itemID);
				return true;
			}
		}
		*/
		TileEntity te=world.getBlockTileEntity(x, y, z);
		if(te != null && te instanceof EmitterTileEntity){
			entityPlayer.openGui(ParticlePhysics.instance, 0, world, x, y, z);
			return true;
		}
		return false;
	}
	
	

	

}
