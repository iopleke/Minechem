package minechem.potion;

import java.util.ArrayList;
import java.util.List;
import minechem.item.molecule.MoleculeEnum;
import minechem.utils.Constants;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class PotionPharmacologyEffect
{

	public static boolean givesEffect(MoleculeEnum toTest)
	{ // This for the swords?
		MoleculeEnum[] possibles =
		{
			MoleculeEnum.water, MoleculeEnum.starch, MoleculeEnum.xylitol, MoleculeEnum.sucrose, MoleculeEnum.psilocybin, MoleculeEnum.amphetamine, MoleculeEnum.methamphetamine, MoleculeEnum.mycotoxin, MoleculeEnum.ethanol, MoleculeEnum.cyanide,
			MoleculeEnum.penicillin, MoleculeEnum.testosterone, MoleculeEnum.xanax, MoleculeEnum.mescaline, MoleculeEnum.asprin, MoleculeEnum.shikimicAcid, MoleculeEnum.phosgene, MoleculeEnum.tetrodotoxin, MoleculeEnum.fingolimod, MoleculeEnum.thc,
			MoleculeEnum.nodularin, MoleculeEnum.hist, MoleculeEnum.pal2, MoleculeEnum.theobromine, MoleculeEnum.retinol, MoleculeEnum.glycine, MoleculeEnum.alinine, MoleculeEnum.serine, MoleculeEnum.arginine, MoleculeEnum.cocaine, MoleculeEnum.cocainehcl,
			MoleculeEnum.metblue, MoleculeEnum.meoh, MoleculeEnum.radchlor, MoleculeEnum.caulerpenyne, MoleculeEnum.latropine
		};

		for (MoleculeEnum molecule : possibles)
		{
			if (toTest == molecule)
			{
				return true;
			}
		}
		return false;
	}

	public static void triggerPlayerEffect(MoleculeEnum molecule,
		EntityLivingBase entityPlayer) {
		World world = entityPlayer.worldObj;
		if (molecule == MoleculeEnum.water) {
			if (entityPlayer instanceof EntityPlayer) {
				((EntityPlayer) entityPlayer).getFoodStats().addStats(1, .1F);
			}
		} else if (molecule == MoleculeEnum.starch) {
			if (entityPlayer instanceof EntityPlayer) {
				((EntityPlayer) entityPlayer).getFoodStats().addStats(6, .2F);
			}
		} else if (molecule == MoleculeEnum.xylitol) {
			if (entityPlayer instanceof EntityPlayer) {
				((EntityPlayer) entityPlayer).getFoodStats().addStats(6, .2F);
			}
		} else if (molecule == MoleculeEnum.sucrose) {
			entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed
				.getId(), Constants.TICKS_PER_SECOND * 20, 1));
			if (entityPlayer instanceof EntityPlayer) {
				((EntityPlayer) entityPlayer).getFoodStats().addStats(1, .1F);
			}
		} else if (molecule == MoleculeEnum.psilocybin) {
			entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion
				.getId(), Constants.TICKS_PER_SECOND * 10, 5));
			entityPlayer.attackEntityFrom(DamageSource.generic, 2);
			entityPlayer.addPotionEffect(new PotionEffect(Potion.nightVision
				.getId(), Constants.TICKS_PER_SECOND * 20, 5));
		} else if (molecule == MoleculeEnum.amphetamine) {
			entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion
				.getId(), Constants.TICKS_PER_SECOND * 20, 5));
			entityPlayer.attackEntityFrom(DamageSource.generic, 4);
			entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed
				.getId(), Constants.TICKS_PER_SECOND * 30, 15));
		} else if (molecule == MoleculeEnum.methamphetamine) {
			entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion
				.getId(), Constants.TICKS_PER_SECOND * 40, 5));
			entityPlayer.attackEntityFrom(DamageSource.generic, 4);
			entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed
				.getId(), Constants.TICKS_PER_SECOND * 60, 7));
		} else if (molecule == MoleculeEnum.mycotoxin) {
			entityPlayer.addPotionEffect(new PotionEffect(
				Potion.wither.getId(), Constants.TICKS_PER_SECOND * 8, 7));
			entityPlayer.addPotionEffect(new PotionEffect(Potion.weakness
				.getId(), Constants.TICKS_PER_SECOND * 12, 1));
		} else if (molecule == MoleculeEnum.ethanol) {
			entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion
				.getId(), Constants.TICKS_PER_SECOND * 30, 5));
			if (entityPlayer instanceof EntityPlayer) {
				((EntityPlayer) entityPlayer).getFoodStats().addStats(1, .1F);
			}
		} else if (molecule == MoleculeEnum.cyanide) {
			entityPlayer.addPotionEffect(new PotionEffect(
				Potion.wither.getId(), Constants.TICKS_PER_SECOND * 40, 3));
		} else if (molecule == MoleculeEnum.penicillin) {
			if (entityPlayer instanceof EntityPlayer) {
				cureAllPotions(world, (EntityPlayer) entityPlayer);
			}
			entityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration
				.getId(), Constants.TICKS_PER_MINUTE * 5, 1));
		} else if (molecule == MoleculeEnum.testosterone) {
			entityPlayer.addPotionEffect(new PotionEffect(Potion.damageBoost
				.getId(), Constants.TICKS_PER_SECOND * 20, 0));
			entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed
				.getId(), Constants.TICKS_PER_SECOND * 20, 1));
		} else if (molecule == MoleculeEnum.xanax) {
			entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion
				.getId(), Constants.TICKS_PER_SECOND * 30, 3));
			entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSlowdown
				.getId(), Constants.TICKS_PER_SECOND * 40, 3));
		} else if (molecule == MoleculeEnum.pantherine) {
			entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion
				.getId(), Constants.TICKS_PER_SECOND * 30, 5));
			entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSlowdown
				.getId(), Constants.TICKS_PER_SECOND * 40, 2));
		} else if (molecule == MoleculeEnum.mescaline) { // In 1.7 we should do
															// better trips!
			entityPlayer.attackEntityFrom(DamageSource.generic, 2);
			entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion
				.getId(), Constants.TICKS_PER_SECOND * 60, 4));
			entityPlayer.addPotionEffect(new PotionEffect(Potion.blindness
				.getId(), Constants.TICKS_PER_SECOND * 30, 4));
		} else if (molecule == MoleculeEnum.asprin) {
			if (entityPlayer instanceof EntityPlayer) {
				entityPlayer.removePotionEffect(Potion.confusion.id);
			}
			entityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration
				.getId(), Constants.TICKS_PER_SECOND * 2, 1));
		} else if (molecule == MoleculeEnum.shikimicAcid
			|| molecule == MoleculeEnum.salt) { // This is for all the items who
												// are either nontoxic or I have
												// not found a good effect for!
		
		} else if (molecule == MoleculeEnum.phosgene) {
			entityPlayer.setFire(2);
		} else if (molecule == MoleculeEnum.aalc
			|| molecule == MoleculeEnum.sulfuricAcid
			|| molecule == MoleculeEnum.buli) {
			entityPlayer.setFire(2);
		}
		
		else if (molecule == MoleculeEnum.tetrodotoxin) {
			entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSlowdown
				.getId(), Constants.TICKS_PER_SECOND * 5, 8));
			entityPlayer.addPotionEffect(new PotionEffect(Potion.weakness
				.getId(), Constants.TICKS_PER_SECOND * 2, 1));
		}
		
		else if (molecule == MoleculeEnum.fingolimod) {
			entityPlayer.addPotionEffect(new PotionEffect(Potion.damageBoost
				.getId(), Constants.TICKS_PER_MINUTE * 1, 1));
			entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed
				.getId(), Constants.TICKS_PER_MINUTE * 1, 1));
			entityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration
				.getId(), Constants.TICKS_PER_MINUTE * 1, 1));
			entityPlayer.addPotionEffect(new PotionEffect(
				Potion.hunger.getId(), Constants.TICKS_PER_SECOND * 300, 1));
		} else if (molecule == MoleculeEnum.thc) {
			entityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration
				.getId(), Constants.TICKS_PER_SECOND * 60, 1));
			entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion
				.getId(), Constants.TICKS_PER_SECOND * 60, 5));
			entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSlowdown
				.getId(), Constants.TICKS_PER_SECOND * 60, 3));
			entityPlayer.addPotionEffect(new PotionEffect(
				Potion.hunger.getId(), Constants.TICKS_PER_SECOND * 120, 20));
		} else if (molecule == MoleculeEnum.nodularin) {
			entityPlayer.addPotionEffect(new PotionEffect(
				Potion.poison.getId(), Constants.TICKS_PER_SECOND * 30, 3));
			entityPlayer.addPotionEffect(new PotionEffect(
				Potion.hunger.getId(), Constants.TICKS_PER_SECOND * 60, 3));
		} else if (molecule == MoleculeEnum.hist) {
			if (entityPlayer instanceof EntityPlayer) {
				cureAllPotions(world, (EntityPlayer) entityPlayer);
			}
			entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion
				.getId(), Constants.TICKS_PER_SECOND * 20, 5));
		} else if (molecule == MoleculeEnum.pal2) {
			entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSlowdown
				.getId(), Constants.TICKS_PER_SECOND * 5, 7));
			entityPlayer.addPotionEffect(new PotionEffect(
				Potion.wither.getId(), Constants.TICKS_PER_SECOND * 5, 0));
		} else if (molecule == MoleculeEnum.theobromine) {
			entityPlayer.addPotionEffect(new PotionEffect(Potion.digSpeed
				.getId(), Constants.TICKS_PER_SECOND * 30, 10));
			entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed
				.getId(), Constants.TICKS_PER_SECOND * 30, 5));
		} else if (molecule == MoleculeEnum.retinol) {
			entityPlayer.addPotionEffect(new PotionEffect(Potion.nightVision
				.getId(), Constants.TICKS_PER_MINUTE * 5, 0));
		} else if (molecule == MoleculeEnum.glycine
			|| molecule == MoleculeEnum.alinine
			|| molecule == MoleculeEnum.serine
			|| molecule == MoleculeEnum.arginine
			|| molecule == MoleculeEnum.proline
			|| molecule == MoleculeEnum.leucine
			|| molecule == MoleculeEnum.isoleucine
			|| molecule == MoleculeEnum.cysteine
			|| molecule == MoleculeEnum.valine
			|| molecule == MoleculeEnum.threonine
			|| molecule == MoleculeEnum.histidine
			|| molecule == MoleculeEnum.methionine
			|| molecule == MoleculeEnum.tyrosine
			|| molecule == MoleculeEnum.asparagine
			|| molecule == MoleculeEnum.asparticAcid
			|| molecule == MoleculeEnum.phenylalanine) {
			if (entityPlayer instanceof EntityPlayer) {
				((EntityPlayer) entityPlayer).getFoodStats().addStats(2, .1F);
			}
			entityPlayer.addPotionEffect(new PotionEffect(Potion.digSpeed
				.getId(), Constants.TICKS_PER_SECOND * 120, 3));
			entityPlayer.addPotionEffect(new PotionEffect(Potion.jump.getId(),
				Constants.TICKS_PER_SECOND * 120, 3));
		} else if (molecule == MoleculeEnum.cocaine) {
			entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion
				.getId(), Constants.TICKS_PER_SECOND * 120, 5));
			entityPlayer.attackEntityFrom(DamageSource.generic, 4);
			entityPlayer.addPotionEffect(new PotionEffect(Potion.nightVision
				.getId(), Constants.TICKS_PER_SECOND * 120, 5));
			entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed
				.getId(), Constants.TICKS_PER_SECOND * 120, 12));
		} else if (molecule == MoleculeEnum.cocainehcl) {
			entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion
				.getId(), Constants.TICKS_PER_SECOND * 60, 5));
			entityPlayer.attackEntityFrom(DamageSource.generic, 4);
			entityPlayer.addPotionEffect(new PotionEffect(Potion.nightVision
				.getId(), Constants.TICKS_PER_SECOND * 60, 5));
			entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed
				.getId(), Constants.TICKS_PER_SECOND * 60, 10));
		} else if (molecule == MoleculeEnum.metblue) {
			if (entityPlayer instanceof EntityPlayer) {
				cureAllPotions(world, (EntityPlayer) entityPlayer);
			}
			entityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration
				.getId(), Constants.TICKS_PER_SECOND * 30, 4));
			entityPlayer.addPotionEffect(new PotionEffect(Potion.weakness
				.getId(), Constants.TICKS_PER_SECOND * 30, 6));
		} else if (molecule == MoleculeEnum.meoh) {
			entityPlayer.addPotionEffect(new PotionEffect(Potion.blindness
				.getId(), Constants.TICKS_PER_SECOND * 20, 6));
			entityPlayer.addPotionEffect(new PotionEffect(
				Potion.wither.getId(), Constants.TICKS_PER_SECOND * 10, 2));
		} else if (molecule == MoleculeEnum.radchlor) {
			entityPlayer.addPotionEffect(new PotionEffect(Potion.weakness
				.getId(), Constants.TICKS_PER_SECOND * 120, 6));
			entityPlayer.addPotionEffect(new PotionEffect(
				Potion.poison.getId(), Constants.TICKS_PER_SECOND * 20, 6));
			entityPlayer.addPotionEffect(new PotionEffect(
				Potion.wither.getId(), Constants.TICKS_PER_SECOND * 30, 1));
		} else if (molecule == MoleculeEnum.caulerpenyne) {
			entityPlayer.addPotionEffect(new PotionEffect(Potion.weakness
				.getId(), Constants.TICKS_PER_SECOND * 2, 6));
			entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion
				.getId(), Constants.TICKS_PER_SECOND * 2, 4));
		} else if (molecule == MoleculeEnum.latropine) {
			entityPlayer.attackEntityFrom(DamageSource.generic, 4);
			entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion
				.getId(), Constants.TICKS_PER_SECOND * 12, 2));
			entityPlayer.addPotionEffect(new PotionEffect(
				PotionInjector.atropineHigh.getId(),
				Constants.TICKS_PER_SECOND * 18, 1));
			entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSlowdown
				.getId(), Constants.TICKS_PER_SECOND * 45, 2));
		} else {
			entityPlayer.attackEntityFrom(DamageSource.generic, 1);
		}
	}

	public static void cureAllPotions(World world, EntityPlayer entityPlayer)
	{
		List<PotionEffect> activePotions = new ArrayList<PotionEffect>(entityPlayer.getActivePotionEffects());
		for (PotionEffect potionEffect : activePotions)
		{
			entityPlayer.removePotionEffect(potionEffect.getPotionID());
		}
	}
}
