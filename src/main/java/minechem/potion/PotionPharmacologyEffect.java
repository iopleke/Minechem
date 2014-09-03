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
        { MoleculeEnum.water, MoleculeEnum.starch, MoleculeEnum.xylitol, MoleculeEnum.sucrose, MoleculeEnum.psilocybin, MoleculeEnum.amphetamine, MoleculeEnum.methamphetamine, MoleculeEnum.mycotoxin, MoleculeEnum.ethanol, MoleculeEnum.cyanide,
                MoleculeEnum.penicillin, MoleculeEnum.testosterone, MoleculeEnum.xanax, MoleculeEnum.mescaline, MoleculeEnum.asprin, MoleculeEnum.shikimicAcid, MoleculeEnum.phosgene, MoleculeEnum.tetrodotoxin, MoleculeEnum.fingolimod, MoleculeEnum.thc,
                MoleculeEnum.nodularin, MoleculeEnum.hist, MoleculeEnum.pal2, MoleculeEnum.theobromine, MoleculeEnum.retinol, MoleculeEnum.glycine, MoleculeEnum.alinine, MoleculeEnum.serine, MoleculeEnum.arginine, MoleculeEnum.cocaine, MoleculeEnum.cocainehcl,
                MoleculeEnum.metblue, MoleculeEnum.meoh, MoleculeEnum.radchlor, MoleculeEnum.caulerpenyne, MoleculeEnum.latropine };

        for (MoleculeEnum molecule : possibles)
        {
            if (toTest == molecule)
            {
                return true;
            }
        }
        return false;
    }

    public static void triggerPlayerEffect(MoleculeEnum molecule, EntityLivingBase entityPlayer)
    {
        World world = entityPlayer.worldObj;
        switch (molecule)
        {
        case water:
            if (entityPlayer instanceof EntityPlayer)
            {
                ((EntityPlayer) entityPlayer).getFoodStats().addStats(1, .1F);
            }
            break;
        case starch:
            if (entityPlayer instanceof EntityPlayer)
            {
                ((EntityPlayer) entityPlayer).getFoodStats().addStats(6, .2F);
            }
            break;
        case xylitol:
            if (entityPlayer instanceof EntityPlayer)
            {
                ((EntityPlayer) entityPlayer).getFoodStats().addStats(6, .2F);
            }
            break;
        case sucrose:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), Constants.TICKS_PER_SECOND * 50, 1));
            if (entityPlayer instanceof EntityPlayer)
            {
                ((EntityPlayer) entityPlayer).getFoodStats().addStats(1, .1F);
            }
            break;
        case psilocybin:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.getId(), Constants.TICKS_PER_SECOND * 60, 5));
            entityPlayer.attackEntityFrom(DamageSource.generic, 2);
            entityPlayer.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), Constants.TICKS_PER_SECOND * 60, 5));
            break;
        case amphetamine:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.getId(), Constants.TICKS_PER_SECOND * 30, 5));
            entityPlayer.attackEntityFrom(DamageSource.generic, 4);
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), Constants.TICKS_PER_SECOND * 60, 15));
            break;
        case methamphetamine:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.getId(), Constants.TICKS_PER_SECOND * 40, 5));
            entityPlayer.attackEntityFrom(DamageSource.generic, 4);
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), Constants.TICKS_PER_SECOND * 60, 7));
            break;
        case mycotoxin:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.wither.getId(), Constants.TICKS_PER_SECOND * 5, 7));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.weakness.getId(), Constants.TICKS_PER_SECOND * 10, 1));
            break;
        case ethanol:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.getId(), Constants.TICKS_PER_SECOND * 30, 5));
            if (entityPlayer instanceof EntityPlayer)
            {
                ((EntityPlayer) entityPlayer).getFoodStats().addStats(1, .1F);
            }
            break;
        case cyanide:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.wither.getId(), Constants.TICKS_PER_SECOND * 120, 3));
            break;
        case penicillin:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration.getId(), Constants.TICKS_PER_MINUTE * 5, 1));
            break;
        case testosterone:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.damageBoost.getId(), Constants.TICKS_PER_SECOND * 20, 0));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), Constants.TICKS_PER_SECOND * 20, 1));
            break;
        case xanax:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.getId(), Constants.TICKS_PER_SECOND * 200, 3));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), Constants.TICKS_PER_SECOND * 200, 3));
            break;
        case pantherine:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.getId(), Constants.TICKS_PER_SECOND * 200, 5));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), Constants.TICKS_PER_SECOND * 200, 2));
            break;
        case mescaline: // In 1.7 we should do better trips!
            entityPlayer.attackEntityFrom(DamageSource.generic, 2);
            entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.getId(), Constants.TICKS_PER_SECOND * 60, 4));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.blindness.getId(), Constants.TICKS_PER_SECOND * 30, 4));
            break;
        case asprin:
            if (entityPlayer instanceof EntityPlayer)
            {
                cureAllPotions(world, (EntityPlayer) entityPlayer);
            }
            entityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration.getId(), Constants.TICKS_PER_SECOND * 10, 1));
            break;
        case shikimicAcid: // This is for all the items who are either nontoxic or I have not found a good effect for!
        case salt:

            break;
        case phosgene: // All of these cause some form of damage to flesh. So lets set players on fire!
        case aalc:
        case sulfuricAcid:
        case buli:
            entityPlayer.setFire(1000);
            break;

        case tetrodotoxin:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), Constants.TICKS_PER_SECOND * 5, 8));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.weakness.getId(), Constants.TICKS_PER_SECOND * 2, 1));
            break;

        case fingolimod:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.damageBoost.getId(), Constants.TICKS_PER_MINUTE * 1, 1));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), Constants.TICKS_PER_MINUTE * 1, 1));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration.getId(), Constants.TICKS_PER_MINUTE * 1, 1));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.hunger.getId(), Constants.TICKS_PER_SECOND * 300, 1));
            break;
        case thc:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration.getId(), Constants.TICKS_PER_MINUTE * 1, 1));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.getId(), Constants.TICKS_PER_MINUTE * 60, 5));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), Constants.TICKS_PER_MINUTE * 60, 3));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.hunger.getId(), Constants.TICKS_PER_MINUTE * 120, 20));
            break;
        case nodularin:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.poison.getId(), Constants.TICKS_PER_MINUTE * 1, 3));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.hunger.getId(), Constants.TICKS_PER_MINUTE * 8, 3));
            break;
        case hist:
            if (entityPlayer instanceof EntityPlayer)
            {
                cureAllPotions(world, (EntityPlayer) entityPlayer);
            }
            entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.getId(), Constants.TICKS_PER_SECOND * 20, 5));
            break;
        case pal2:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), Constants.TICKS_PER_SECOND * 5, 7));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.wither.getId(), Constants.TICKS_PER_SECOND * 5, 0));
            break;
        case theobromine:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.digSpeed.getId(), Constants.TICKS_PER_MINUTE * 5, 10));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), Constants.TICKS_PER_MINUTE * 5, 5));
            break;
        case retinol:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), Constants.TICKS_PER_MINUTE * 5, 0));
            break;
        case glycine:
        case alinine:
        case serine:
        case arginine:
        case proline:
        case leucine:
        case isoleucine:
        case cysteine:
        case valine:
        case threonine:
        case histidine:
        case methionine:
        case tyrosine:
        case asparagine:
        case asparticAcid:
        case phenylalanine:
            if (entityPlayer instanceof EntityPlayer)
            {
                ((EntityPlayer) entityPlayer).getFoodStats().addStats(2, .1F);
            }
            entityPlayer.addPotionEffect(new PotionEffect(Potion.digSpeed.getId(), Constants.TICKS_PER_SECOND * 120, 3));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.jump.getId(), Constants.TICKS_PER_SECOND * 120, 3));
            break;
        case cocaine:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.getId(), Constants.TICKS_PER_SECOND * 120, 5));
            entityPlayer.attackEntityFrom(DamageSource.generic, 4);
            entityPlayer.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), Constants.TICKS_PER_SECOND * 120, 5));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), Constants.TICKS_PER_SECOND * 120, 12));
            break;
        case cocainehcl:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.getId(), Constants.TICKS_PER_MINUTE * 60, 5));
            entityPlayer.attackEntityFrom(DamageSource.generic, 4);
            entityPlayer.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), Constants.TICKS_PER_MINUTE * 60, 5));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), Constants.TICKS_PER_MINUTE * 60, 10));
            break;
        case metblue:
            if (entityPlayer instanceof EntityPlayer)
            {
                cureAllPotions(world, (EntityPlayer) entityPlayer);
            }
            entityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration.getId(), Constants.TICKS_PER_SECOND * 30, 4));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.weakness.getId(), Constants.TICKS_PER_SECOND * 30, 6));
            break;
        case meoh:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.blindness.getId(), Constants.TICKS_PER_MINUTE * 2, 6));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.wither.getId(), Constants.TICKS_PER_MINUTE * 2, 2));
            break;
        case radchlor:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.weakness.getId(), Constants.TICKS_PER_MINUTE * 1, 6));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.poison.getId(), Constants.TICKS_PER_MINUTE * 1, 6));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.wither.getId(), Constants.TICKS_PER_SECOND * 30, 1));
            break;
        case caulerpenyne: // While not a very strong toxin. It has been known to cause weakness and headaches in people who have consumed it.
            entityPlayer.addPotionEffect(new PotionEffect(Potion.weakness.getId(), Constants.TICKS_PER_MINUTE * 2, 6));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.getId(), Constants.TICKS_PER_MINUTE * 2, 4));
            break;
        case latropine:
            entityPlayer.attackEntityFrom(DamageSource.generic, 4);
            entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.getId(), Constants.TICKS_PER_MINUTE * 2, 2));
            entityPlayer.addPotionEffect(new PotionEffect(PotionInjector.atropineHigh.getId(), Constants.TICKS_PER_MINUTE * 2, 1));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), Constants.TICKS_PER_MINUTE * 2, 2));
            break;
        default:
            entityPlayer.attackEntityFrom(DamageSource.generic, 5); // Don't drink random shit kids, A message from
            break;
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
