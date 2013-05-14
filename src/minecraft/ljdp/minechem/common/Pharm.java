package ljdp.minechem.common;
import java.util.ArrayList;
import java.util.List;

import ljdp.minechem.api.core.EnumMolecule;
import ljdp.minechem.api.util.Constants;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
public class Pharm {
 public static void triggerPlayerEffect(EnumMolecule molecule, EntityPlayer entityPlayer) {
        World world = entityPlayer.worldObj;
        // Mandrake's Realm 
        switch (molecule) {
        case water:
            entityPlayer.getFoodStats().addStats(1, .1F);
            break;
        case starch: stevenk:
            entityPlayer.getFoodStats().addStats(2, .2F);
            break;
        case sucrose:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), Constants.TICKS_PER_SECOND * 5, 0));
            entityPlayer.getFoodStats().addStats(1, .1F);
            break;
        case psilocybin:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.getId(), Constants.TICKS_PER_SECOND * 30, 5));
            entityPlayer.attackEntityFrom(DamageSource.generic, 2);
            entityPlayer.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), Constants.TICKS_PER_SECOND * 30, 5));
            break;
        case amphetamine:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), Constants.TICKS_PER_SECOND * 20, 7));
            break;
        case methamphetamine:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), Constants.TICKS_PER_SECOND * 30, 7));
            break;
        case poison:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.wither.getId(), Constants.TICKS_PER_SECOND * 60, 2));
            break;
        case ethanol:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.getId(), Constants.TICKS_PER_SECOND * 10, 1));
            entityPlayer.getFoodStats().addStats(3, .1F);
            break;
        case cyanide:
            entityPlayer.attackEntityFrom(DamageSource.generic, 20);
            break;
        case penicillin:
            cureAllPotions(world, entityPlayer);
            entityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration.getId(), Constants.TICKS_PER_MINUTE * 2, 1));
            break;
        case testosterone:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.damageBoost.getId(), Constants.TICKS_PER_MINUTE * 5, 2));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), Constants.TICKS_PER_MINUTE * 5, 0));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.damageBoost.getId(), Constants.TICKS_PER_MINUTE * 5, 2));
            break;
        case xanax:
            cureAllPotions(world, entityPlayer);
            entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.getId(), Constants.TICKS_PER_SECOND * 30, 5));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), Constants.TICKS_PER_SECOND * 30, 2));
            break;
        case mescaline:
            entityPlayer.attackEntityFrom(DamageSource.generic, 2);
            entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.getId(), Constants.TICKS_PER_SECOND * 30, 2));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.blindness.getId(), Constants.TICKS_PER_SECOND * 30, 2));
            break;
        case quinine:
            cureAllPotions(world, entityPlayer);
            entityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration.getId(), Constants.TICKS_PER_MINUTE * 1, 1));
            break;
        case shikimicAcid:
            // No effect.
            break;
        case salt:
            // No effect.
            break;
        case sulfuricAcid:
            entityPlayer.attackEntityFrom(DamageSource.generic, 5);
            break;
        case buli:
            entityPlayer.attackEntityFrom(DamageSource.generic, 8);
            break;
        case phosgene:
            entityPlayer.setFire(100);
            break;
        case aalc:
            entityPlayer.attackEntityFrom(DamageSource.generic, 5);
            break;
        case ttx: muscarine:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), Constants.TICKS_PER_SECOND * 60, 10));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.wither.getId(), Constants.TICKS_PER_SECOND * 60, 1));
            break;
        case pkone: // Polyketides have many roles in medicine
            entityPlayer.addPotionEffect(new PotionEffect(Potion.damageBoost.getId(), Constants.TICKS_PER_MINUTE * 4, 2));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), Constants.TICKS_PER_MINUTE * 4, 0));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration.getId(), Constants.TICKS_PER_MINUTE * 4, 1));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.hunger.getId(), Constants.TICKS_PER_SECOND * 30, 1));
            break;
        case pkthree:
            break;
        case pkfour:
            break;
        case fingolimod:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.damageBoost.getId(), Constants.TICKS_PER_MINUTE * 5, 2));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), Constants.TICKS_PER_MINUTE * 5, 2));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration.getId(), Constants.TICKS_PER_MINUTE * 5, 2));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.hunger.getId(), Constants.TICKS_PER_SECOND * 80, 1));
            break;
        case afroman:
            cureAllPotions(world, entityPlayer);
            entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.getId(), Constants.TICKS_PER_SECOND * 60, 5));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), Constants.TICKS_PER_SECOND * 60, 1));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.hunger.getId(), Constants.TICKS_PER_SECOND * 120, 1));
            break;
        case nod:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.hunger.getId(), Constants.TICKS_PER_MINUTE * 8, 1));
            break;
        case hist:
            cureAllPotions(world, entityPlayer);
            entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.getId(), Constants.TICKS_PER_SECOND * 20, 5));
            entityPlayer.getFoodStats().addStats(2, .2F);
            break;
        case pal2: // this sh*t is real nasty
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), Constants.TICKS_PER_SECOND * 60, 20));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.wither.getId(), Constants.TICKS_PER_SECOND * 60, 2));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.getId(), Constants.TICKS_PER_SECOND * 60, 5));
            break;
        case theobromine: // Speed boost
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), Constants.TICKS_PER_MINUTE * 5, 1));
            break;
        case ret:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), Constants.TICKS_PER_SECOND * 120, 1));
            entityPlayer.getFoodStats().addStats(3, .1F);
            break;
		case glycine: alinine: serine: arginine:
		entityPlayer.addPotionEffect(new PotionEffect(Potion.digSpeed.getId(), Constants.TICKS_PER_SECOND * 120, 1));
		entityPlayer.addPotionEffect(new PotionEffect(Potion.jump.getId(), Constants.TICKS_PER_SECOND * 120, 1));
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
