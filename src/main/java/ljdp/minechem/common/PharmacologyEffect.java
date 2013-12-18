package ljdp.minechem.common;
import java.util.ArrayList;
import java.util.List;

import ljdp.minechem.api.core.EnumMolecule;
import ljdp.minechem.api.util.Constants;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
public class PharmacologyEffect {
	
 public static boolean givesEffect(EnumMolecule toTest){
	 EnumMolecule[] possibles={EnumMolecule.water,EnumMolecule.starch,EnumMolecule.stevenk,EnumMolecule.sucrose,EnumMolecule.psilocybin,EnumMolecule.amphetamine,EnumMolecule.methamphetamine,EnumMolecule.poison,EnumMolecule.ethanol,EnumMolecule.cyanide,EnumMolecule.penicillin,EnumMolecule.testosterone,EnumMolecule.xanax,EnumMolecule.mescaline,EnumMolecule.asprin,EnumMolecule.shikimicAcid,EnumMolecule.phosgene,EnumMolecule.ttx,EnumMolecule.fingolimod,EnumMolecule.afroman,EnumMolecule.nod,EnumMolecule.hist,EnumMolecule.pal2,EnumMolecule.theobromine,EnumMolecule.ret,EnumMolecule.glycine,EnumMolecule.alinine,EnumMolecule.serine,EnumMolecule.arginine,EnumMolecule.redrocks,EnumMolecule.coke,EnumMolecule.metblue,EnumMolecule.meoh,EnumMolecule.radchlor,EnumMolecule.ctaxifolia,EnumMolecule.latropine};
	 
	 for(EnumMolecule molecule:possibles){
		 if(toTest==molecule){
			 return true;
		 }
	 }
	 return false;
 }
 public static void triggerPlayerEffect(EnumMolecule molecule, EntityLivingBase entityPlayer) {
        World world = entityPlayer.worldObj;
        // Mandrake's Realm 
        switch (molecule) {
        case water:
        	if(entityPlayer instanceof EntityPlayer){
        		((EntityPlayer)entityPlayer).getFoodStats().addStats(1, .1F);
        	}
            break;
        case starch:
        	if(entityPlayer instanceof EntityPlayer){
        		((EntityPlayer)entityPlayer).getFoodStats().addStats(2, .2F);
        	}
            break;
        case stevenk:
        	if(entityPlayer instanceof EntityPlayer){
        		((EntityPlayer)entityPlayer).getFoodStats().addStats(2, .2F);
        	}
            break;
        case sucrose:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), Constants.TICKS_PER_SECOND * 5, 0));
            if(entityPlayer instanceof EntityPlayer){
        		((EntityPlayer)entityPlayer).getFoodStats().addStats(1, .1F);
            }
            break;
        case psilocybin:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.getId(), Constants.TICKS_PER_SECOND * 30, 5));
            entityPlayer.attackEntityFrom(DamageSource.generic, 2);
            entityPlayer.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), Constants.TICKS_PER_SECOND * 30, 5));
            break;
        case amphetamine:
		    entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.getId(), Constants.TICKS_PER_SECOND * 30, 5));
            entityPlayer.attackEntityFrom(DamageSource.generic, 4);
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), Constants.TICKS_PER_SECOND * 20, 7));
            break;
        case methamphetamine:
		    entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.getId(), Constants.TICKS_PER_SECOND * 40, 5));
            entityPlayer.attackEntityFrom(DamageSource.generic, 4);
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), Constants.TICKS_PER_SECOND * 30, 7));
            break;
        case poison:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.wither.getId(), Constants.TICKS_PER_SECOND * 60, 400));
            break;
        case ethanol:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.getId(), Constants.TICKS_PER_SECOND * 30, 2));
            if(entityPlayer instanceof EntityPlayer){
        		((EntityPlayer)entityPlayer).getFoodStats().addStats(1, .1F);
            }
            break;
        case cyanide:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.wither.getId(), Constants.TICKS_PER_SECOND * 120, 4));
            break;
        case penicillin:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration.getId(), Constants.TICKS_PER_MINUTE * 2, 1));
            break;
        case testosterone:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.damageBoost.getId(), Constants.TICKS_PER_MINUTE * 5, 2));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), Constants.TICKS_PER_MINUTE * 5, 0));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.damageBoost.getId(), Constants.TICKS_PER_MINUTE * 5, 2));
            break;
        case xanax:  
        	if(entityPlayer instanceof EntityPlayer){
        		cureAllPotions(world, (EntityPlayer)entityPlayer);
        	}
            entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.getId(), Constants.TICKS_PER_SECOND * 30, 5));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), Constants.TICKS_PER_SECOND * 30, 2));
            break;
        case pantherine: 
            entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.getId(), Constants.TICKS_PER_SECOND * 30, 5));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), Constants.TICKS_PER_SECOND * 30, 2));
            break;
        case mescaline:
            entityPlayer.attackEntityFrom(DamageSource.generic, 2);
            entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.getId(), Constants.TICKS_PER_SECOND * 60, 2));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.blindness.getId(), Constants.TICKS_PER_SECOND * 30, 2));
            break;
        case asprin:
        	if(entityPlayer instanceof EntityPlayer){
        		cureAllPotions(world, (EntityPlayer)entityPlayer);
        	}
            entityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration.getId(), Constants.TICKS_PER_MINUTE * 1, 1));
            break;
        case shikimicAcid:
             break;
		case salt:
            break;
       case phosgene:// all of these cause tons of damage to human flesh!!!!!
            case aalc:
            case sulfuricAcid:
            case buli:
			//entityPlayer.attackEntityFrom(DamageSource.inFire, 2);
            entityPlayer.setFire(100);
            break;
            // 
       case ttx: 
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), Constants.TICKS_PER_SECOND * 60, 10));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.wither.getId(), Constants.TICKS_PER_SECOND * 60, 2));
            break;
       // case nicotine: 
			// entityPlayer.attackEntityFrom(DamageSource.generic, 4);
			// entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), Constants.TICKS_PER_SECOND * 60, 10));
			// break; 
        case fingolimod:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.damageBoost.getId(), Constants.TICKS_PER_MINUTE * 5, 2));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), Constants.TICKS_PER_MINUTE * 5, 2));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration.getId(), Constants.TICKS_PER_MINUTE * 5, 2));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.hunger.getId(), Constants.TICKS_PER_SECOND * 80, 1));
            break;
        case afroman:
        	if(entityPlayer instanceof EntityPlayer){
        		cureAllPotions(world, (EntityPlayer)entityPlayer);
        	}
            entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.getId(), Constants.TICKS_PER_SECOND * 60, 5));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), Constants.TICKS_PER_SECOND * 60, 4));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.hunger.getId(), Constants.TICKS_PER_SECOND * 120, 5));
            break;
        case nod:
        	entityPlayer.attackEntityFrom(DamageSource.starve, 4);
            entityPlayer.addPotionEffect(new PotionEffect(Potion.hunger.getId(), Constants.TICKS_PER_MINUTE * 8, 1));
            break;
        case hist:
        	if(entityPlayer instanceof EntityPlayer){
        		((EntityPlayer)entityPlayer).getFoodStats().addStats(2, .2F);
        		cureAllPotions(world, (EntityPlayer)entityPlayer);
        	}
            entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.getId(), Constants.TICKS_PER_SECOND * 20, 5));
            break;
        case pal2: // this sh*t is real nasty
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), Constants.TICKS_PER_SECOND * 60, 20));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.wither.getId(), Constants.TICKS_PER_SECOND * 60, 2));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.getId(), Constants.TICKS_PER_SECOND * 60, 2));
            break;
        case theobromine: // Speed boost
		    entityPlayer.addPotionEffect(new PotionEffect(Potion.digSpeed.getId(), Constants.TICKS_PER_SECOND * 60, 5));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), Constants.TICKS_PER_MINUTE * 5, 2));
            break;
        case ret:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), Constants.TICKS_PER_SECOND * 120, 1));
            if(entityPlayer instanceof EntityPlayer){
        		((EntityPlayer)entityPlayer).getFoodStats().addStats(3, .1F);
            }
            break;
        case glycine:
			case alinine:
			case serine: 
			case arginine:
			entityPlayer.addPotionEffect(new PotionEffect(Potion.digSpeed.getId(), Constants.TICKS_PER_SECOND * 120, 1));
			entityPlayer.addPotionEffect(new PotionEffect(Potion.jump.getId(), Constants.TICKS_PER_SECOND * 120, 1));
			break; 
		case redrocks:
	        entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.getId(), Constants.TICKS_PER_SECOND * 120, 5));
            entityPlayer.attackEntityFrom(DamageSource.generic, 2);
            entityPlayer.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), Constants.TICKS_PER_SECOND * 120, 5));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), Constants.TICKS_PER_SECOND * 120, 7));
	        break; 
		case coke:
	        entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.getId(), Constants.TICKS_PER_SECOND * 60, 5));
            entityPlayer.attackEntityFrom(DamageSource.generic, 2);
            entityPlayer.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), Constants.TICKS_PER_SECOND * 60, 5));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), Constants.TICKS_PER_SECOND * 60, 10));
	        break; 
	    case metblue:
	    	if(entityPlayer instanceof EntityPlayer){
        		cureAllPotions(world, (EntityPlayer)entityPlayer);
	    	}
	        entityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration.getId(), Constants.TICKS_PER_SECOND * 30, 2));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.weakness.getId(), Constants.TICKS_PER_SECOND * 30, 2));
	        break;
		case meoh:
			entityPlayer.addPotionEffect(new PotionEffect(Potion.blindness.getId(), Constants.TICKS_PER_SECOND * 60, 4));
			entityPlayer.addPotionEffect(new PotionEffect(Potion.wither.getId(), Constants.TICKS_PER_SECOND * 60, 4));
			break; 
			case radchlor: 
		    entityPlayer.addPotionEffect(new PotionEffect(Potion.weakness.getId(), Constants.TICKS_PER_SECOND * 120, 4));
			entityPlayer.addPotionEffect(new PotionEffect(Potion.poison.getId(), Constants.TICKS_PER_SECOND * 60, 4));
			entityPlayer.addPotionEffect(new PotionEffect(Potion.wither.getId(), Constants.TICKS_PER_SECOND * 10, 1));
			break;
			case ctaxifolia: // While not a very strong toxin. It has been known to cause weakness and headaches in people who have consumed it.
		        entityPlayer.addPotionEffect(new PotionEffect(Potion.weakness.getId(), Constants.TICKS_PER_SECOND * 120, 4));
			entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.getId(), Constants.TICKS_PER_SECOND * 120, 4));
		        break;
			case latropine:
			entityPlayer.attackEntityFrom(DamageSource.generic, 2);
		    entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.getId(), Constants.TICKS_PER_SECOND * 120, 2));
			entityPlayer.addPotionEffect(new PotionEffect(PotionInjector.atropineHigh.id, Constants.TICKS_PER_SECOND * 120, 2));
		        break;
		default:
			entityPlayer.attackEntityFrom(DamageSource.generic, 5);
			break;
        }
    }

    public static void cureAllPotions(World world, EntityPlayer entityPlayer) {
        List<PotionEffect> activePotions = new ArrayList<PotionEffect>(entityPlayer.getActivePotionEffects());
        for (PotionEffect potionEffect : activePotions) {
            entityPlayer.removePotionEffect(potionEffect.getPotionID());
        }
    }
}
