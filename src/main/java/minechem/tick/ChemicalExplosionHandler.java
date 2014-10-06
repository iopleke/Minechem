package minechem.tick;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import minechem.MinechemItemsRegistration;
import minechem.Settings;
import minechem.fluid.FluidChemical;
import minechem.fluid.FluidElement;
import minechem.item.element.ElementEnum;
import minechem.item.element.ElementItem;
import minechem.item.molecule.MoleculeEnum;
import minechem.item.molecule.MoleculeItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;

public class ChemicalExplosionHandler
{
	
	public static final Set<ChemicalExplosionReactionRule> explosionReactionRules=new HashSet<ChemicalExplosionReactionRule>();
	
    public void transmuteWaterToPortal(World world, int dx, int dy, int dz)
    {
        int px = dx;
        int pz = dz;

        if (world.getBlock(px - 1, dy, pz) == Blocks.water)
        {
            px--;
        }
        if (world.getBlock(px, dy, pz - 1) == Blocks.water)
        {
            pz--;
        }

        world.setBlock(px + 0, dy, pz + 0, Blocks.stone, 0, 2);
    }

    @SubscribeEvent
    public void tick(TickEvent.WorldTickEvent event)
    {
    	if (!Settings.explosionItemMeetFluid){
    		return;
    	}
    	
        World world=event.world;
        
        for (Object p : world.playerEntities){
            EntityPlayer player=(EntityPlayer) p;
            double rangeToCheck = 32.0D;
            
            List<EntityItem> itemList = world.getEntitiesWithinAABB(EntityItem.class, player.boundingBox.expand(rangeToCheck, rangeToCheck, rangeToCheck));
            for (EntityItem entityItem : itemList)
            {
            	ItemStack itemStack=entityItem.getEntityItem();
            	Item item=itemStack.getItem();
            	Enum chemA=null;
            	if (item==MinechemItemsRegistration.element){
            		chemA=ElementItem.getElement(itemStack);
            	}else if (item==MinechemItemsRegistration.molecule){
            		chemA=MoleculeItem.getMolecule(itemStack);
            	}
            	
            	if (chemA!=null&&world.isMaterialInBB(entityItem.boundingBox, Material.water)){
            		Block block=world.getBlock(MathHelper.floor_double(entityItem.posX), MathHelper.floor_double(entityItem.posY), MathHelper.floor_double(entityItem.posZ));
            		Enum chemB=getChemical(block);
            			
            		if (chemB!=null){
            			ChemicalExplosionReactionRule rule=new ChemicalExplosionReactionRule(chemA, chemB);
            			if (explosionReactionRules.contains(rule)){
            				explosionReaction(world,entityItem);
            				world.removeEntity(entityItem);
            			}
            		}
            		
            	}
            }
        }   
    }

    public static void initExplodableChemical(){
    	// TODO Add more explosion rules
    	explosionReactionRules.add(new ChemicalExplosionReactionRule(MoleculeEnum.water, ElementEnum.Li));
    	explosionReactionRules.add(new ChemicalExplosionReactionRule(MoleculeEnum.water, ElementEnum.Na));
    	explosionReactionRules.add(new ChemicalExplosionReactionRule(MoleculeEnum.water, ElementEnum.K));
    	explosionReactionRules.add(new ChemicalExplosionReactionRule(MoleculeEnum.water, ElementEnum.Rb));
    	explosionReactionRules.add(new ChemicalExplosionReactionRule(MoleculeEnum.water, ElementEnum.Cs));
    	explosionReactionRules.add(new ChemicalExplosionReactionRule(MoleculeEnum.water, ElementEnum.Fr));
    	explosionReactionRules.add(new ChemicalExplosionReactionRule(MoleculeEnum.water, ElementEnum.Mg));
    	explosionReactionRules.add(new ChemicalExplosionReactionRule(MoleculeEnum.water, ElementEnum.Ca));
    	explosionReactionRules.add(new ChemicalExplosionReactionRule(MoleculeEnum.water, ElementEnum.Sr));
    	explosionReactionRules.add(new ChemicalExplosionReactionRule(MoleculeEnum.water, ElementEnum.Ba));
    	explosionReactionRules.add(new ChemicalExplosionReactionRule(MoleculeEnum.water, ElementEnum.Ra));
    	
    	explosionReactionRules.add(new ChemicalExplosionReactionRule(MoleculeEnum.water, MoleculeEnum.sulfuricAcid));
    	explosionReactionRules.add(new ChemicalExplosionReactionRule(MoleculeEnum.water, MoleculeEnum.calciumOxide));
    	explosionReactionRules.add(new ChemicalExplosionReactionRule(MoleculeEnum.water, MoleculeEnum.potassiumOxide));
    	explosionReactionRules.add(new ChemicalExplosionReactionRule(MoleculeEnum.water, MoleculeEnum.sodiumOxide));
    	
    }
    
    private void explosionReaction(World world,EntityItem entityItem){
        world.createExplosion(entityItem, entityItem.posX, entityItem.posY, entityItem.posZ, 0.9F, true);
        int dx = MathHelper.floor_double(entityItem.posX);
        int dy = MathHelper.floor_double(entityItem.posY);
        int dz = MathHelper.floor_double(entityItem.posZ);
        transmuteWaterToPortal(world, dx, dy, dz);
        return;
    }

    public static Enum getChemical(Block block){
		Enum chemical=null;
		if (block instanceof IFluidBlock){
			Fluid fluid=((IFluidBlock)block).getFluid();
			if (fluid instanceof FluidElement){
				chemical=((FluidElement)fluid).element;
			}else if(fluid instanceof FluidChemical){
				chemical=((FluidChemical)fluid).molecule;
			}else if(fluid==FluidRegistry.WATER){
				chemical=MoleculeEnum.water;
			}
		}else if (block==Blocks.water||block==Blocks.flowing_water){
			chemical=MoleculeEnum.water;
		}
		
		return chemical;
    }
    
    public static boolean checkToExplode(Block source,Block destination,World world,int x,int y,int z){
    	Enum chemicalA=getChemical(source);
    	Enum chemicalB=getChemical(destination);
    	if (chemicalA!=null&&chemicalB!=null&&explosionReactionRules.contains(new ChemicalExplosionReactionRule(chemicalA, chemicalB))){
    		world.createExplosion(null, x, y, z, 0.9F, true);
    		return true;
    	}
    	
    	return false;
    }
}
