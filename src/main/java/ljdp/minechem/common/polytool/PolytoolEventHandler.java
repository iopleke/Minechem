package ljdp.minechem.common.polytool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import ljdp.minechem.api.core.EnumElement;
import ljdp.minechem.common.recipe.DecomposerRecipeHandler;
import ljdp.minechem.common.utils.MinechemHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class PolytoolEventHandler {

	public void addDrops (LivingDropsEvent event, ItemStack dropStack)
	{
		EntityItem entityitem = new EntityItem(event.entityLiving.worldObj, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, dropStack);
		entityitem.delayBeforeCanPickup = 10;
		event.drops.add(entityitem);
	}
	@ForgeSubscribe
	public void breakSpeed(PlayerEvent.BreakSpeed event){
		//Again, there must be a better way to do this
		if(event.entityPlayer.inventory.getCurrentItem()!=null&&event.entityPlayer.inventory.getCurrentItem().getItem() instanceof ItemPolytool){
			event.newSpeed=event.entityPlayer.inventory.getCurrentItem().getItem().getStrVsBlock(event.entityPlayer.inventory.getCurrentItem(),event.block);
		}
	}


	@ForgeSubscribe
	public void onHit(LivingHurtEvent event){

		if (event.source.damageType.equals("player"))
		{
			EntityPlayer player = (EntityPlayer) event.source.getEntity();
			ItemStack stack = player.getCurrentEquippedItem();

		
		if (event.entityLiving instanceof EntitySpider)
		{

			
				if (stack != null && stack.hasTagCompound() && stack.getItem() instanceof ItemPolytool)
				{
					double damage = .8*ItemPolytool.getPowerOfType(stack, EnumElement.B);
					
					event.ammount+=damage;

				}

			
		}
		if (event.entityLiving instanceof EntityEnderman)
		{

			
				if (stack != null && stack.hasTagCompound() && stack.getItem() instanceof ItemPolytool)
				{
					double damage = .8*ItemPolytool.getPowerOfType(stack, EnumElement.Ag);
					event.ammount+=damage;

				}

			
		}
		}
	}
	@ForgeSubscribe
	public void onDrop(LivingDropsEvent event){


		//Large page of the beheading code based off TiC code
		//Thanks to mDiyo

		if (event.source.damageType.equals("player"))
		{

			EntityPlayer player = (EntityPlayer) event.source.getEntity();
			ItemStack stack = player.getCurrentEquippedItem();
			Random random=new Random();
			float powerSilicon = ItemPolytool.getPowerOfType(stack, EnumElement.Si);
			if(powerSilicon>0){
				int amount=(int) Math.ceil(random.nextDouble()*powerSilicon);
				Iterator iter=event.drops.iterator();
				if(random.nextInt(16)<1+powerSilicon){
					ArrayList<EntityItem> trueResult=new ArrayList();
					while(iter.hasNext()){
						EntityItem entityItem=(EntityItem) iter.next();
						ItemStack item=entityItem.getEntityItem();
						while(item.stackSize>0){
							//Always avoid chances
							ArrayList items=MinechemHelper.convertChemicalsIntoItemStacks(DecomposerRecipeHandler.instance.getRecipe(item).output);

							//ArrayList items=DecomposerRecipeHandler.instance.getRecipeOutputForInput(item);
							if(items!=null){

								for(int i=0;i<items.size();i++){
									trueResult.add(new EntityItem(entityItem.worldObj,entityItem.posX,entityItem.posY,entityItem.posZ,(ItemStack) items.get(i)));
								}
							}else{
								trueResult.add(entityItem);
								break;
							}
							item.stackSize--;
						}

					}
					event.drops.clear();
					event.drops.addAll(trueResult);
				}
			}
			if (event.entityLiving instanceof EntitySkeleton || event.entityLiving instanceof EntityZombie || event.entityLiving instanceof EntityPlayer)
			{

				EntityLivingBase enemy = event.entityLiving;


				if (stack != null&&stack.getItem() instanceof ItemPolytool)
				{

					float powerTitanium = ItemPolytool.getPowerOfType(stack, EnumElement.Ti);

					if(powerTitanium>0){
						for (int j = 0; j < enemy.getLastActiveItems().length; ++j)
						{
							ItemStack itemstack = enemy.getCurrentItemOrArmor(j);
							if (itemstack != null )
							{

								enemy.entityDropItem(itemstack, 0.0F);
							}
						}

					}

					//Nitrogen preservation
					if(enemy instanceof EntityZombie){

						float power = ItemPolytool.getPowerOfType(stack, EnumElement.N);
						if(power>0){
							int amount=(int) Math.ceil(random.nextDouble()*power);
							addDrops(event, new ItemStack(Item.beefCooked,amount,0));
							Iterator iter=event.drops.iterator();
							while(iter.hasNext()){
								EntityItem entityItem=(EntityItem) iter.next();
								if(entityItem.getEntityItem().itemID==Item.rottenFlesh.itemID){
									iter.remove();
								}
							}
						}
					}
					//Calcium bonus
					if(enemy instanceof EntitySkeleton){

						float power = ItemPolytool.getPowerOfType(stack, EnumElement.Ca);
						if(power>0){
							int amount=(int) Math.ceil(random.nextDouble()*power);
							Iterator iter=event.drops.iterator();
							while(iter.hasNext()){
								EntityItem entityItem=(EntityItem) iter.next();
								if(entityItem.getEntityItem().itemID==Item.bone.itemID){
									entityItem.getEntityItem().stackSize+=amount;
								}
							}
						}
					}
					//Beryllium beheading
					float beheading = ItemPolytool.getPowerOfType(stack, EnumElement.Be);
					while(beheading > 5){
						if (beheading > 0 && random.nextInt(5) < beheading * 10)
						{
							if(event.entityLiving instanceof EntitySkeleton){
								EntitySkeleton skeleton=(EntitySkeleton) enemy;
								addDrops(event, new ItemStack(Item.skull.itemID, 1, skeleton.getSkeletonType()));
							}else if(event.entityLiving instanceof EntityZombie){
								addDrops(event, new ItemStack(Item.skull.itemID, 1, 2));
							}else if(event.entityLiving instanceof EntityPlayer){
								ItemStack dropStack = new ItemStack(Item.skull.itemID, 1, 3);
								NBTTagCompound nametag = new NBTTagCompound();
								nametag.setString("SkullOwner", player.username);
								addDrops(event, dropStack);
							}
						}

						//More head drops if level>5
						beheading--;
					}
				}
			}
		}
	}

}