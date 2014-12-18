package minechem.potion;

import minechem.item.molecule.MoleculeEnum;
import net.minecraft.entity.EntityLivingBase;

import java.util.*;

public class PharmacologyEffectRegistry
{
    private static Map<MoleculeEnum, List<PharmacologyEffect>> effects = new HashMap<MoleculeEnum, List<PharmacologyEffect>>();

    public static void addEffect(MoleculeEnum molecule, PharmacologyEffect effect)
    {
        List<PharmacologyEffect> list = effects.get(molecule);
        if (list == null)
            list = new LinkedList<PharmacologyEffect>();
        for (PharmacologyEffect existingEffect:list)
        {
            if (existingEffect.equals(effect)) return;
        }
        list.add(effect);
        effects.put(molecule, list);
    }

    public static void addEffects(MoleculeEnum molecule, List<PharmacologyEffect> toAdd)
    {
        for (PharmacologyEffect effect:toAdd)
        {
            addEffect(molecule,effect);
        }
    }

    public static void addEffect(List<MoleculeEnum> molecules, PharmacologyEffect effect)
    {
        for (MoleculeEnum molecule : molecules)
            addEffect(molecule, effect);
    }

    public static boolean hasEffect(MoleculeEnum molecule)
    {
        return effects.containsKey(molecule);
    }

    public static List<PharmacologyEffect> getEffects(MoleculeEnum molecule)
    {
        return effects.get(molecule);
    }

    public static void applyEffect(MoleculeEnum molecule, EntityLivingBase entityLivingBase)
    {
        if (hasEffect(molecule))
        {
            for (PharmacologyEffect effect : effects.get(molecule))
                effect.applyEffect(entityLivingBase);
        }
    }

    public static List<PharmacologyEffect> removeEffects(MoleculeEnum molecule)
    {
        List<PharmacologyEffect> list = effects.get(molecule);
        effects.remove(molecule);
        return list;
    }

    public static void removeEffect(MoleculeEnum molecule, PharmacologyEffect effect)
    {
        if (hasEffect(molecule))
            effects.get(molecule).remove(effect);
    }

    public static void init()
    {
        addEffect(MoleculeEnum.water, new PharmacologyEffect.Food(1, .1F));
        addEffect(MoleculeEnum.starch, new PharmacologyEffect.Food(6, .2F));
        addEffect(MoleculeEnum.xylitol, new PharmacologyEffect.Food(6, .2F));
        addEffect(MoleculeEnum.sucrose, new PharmacologyEffect.Potion("moveSpeed", 20, 1));
        addEffect(MoleculeEnum.sucrose, new PharmacologyEffect.Food(1, .1F));
        addEffect(MoleculeEnum.psilocybin, new PharmacologyEffect.Potion("confusion", 10, 5));
        addEffect(MoleculeEnum.psilocybin, new PharmacologyEffect.Damage(2));
        addEffect(MoleculeEnum.psilocybin, new PharmacologyEffect.Potion("nightVision", 20, 5));
        addEffect(MoleculeEnum.dimethyltryptamine, new PharmacologyEffect.Potion("confusion", 30, 5));
        addEffect(MoleculeEnum.dimethyltryptamine, new PharmacologyEffect.Damage(2));
        addEffect(MoleculeEnum.dimethyltryptamine, new PharmacologyEffect.Potion("nightVision", 60, 5));
        addEffect(MoleculeEnum.amphetamine, new PharmacologyEffect.Potion("confusion", 20, 5));
        addEffect(MoleculeEnum.amphetamine, new PharmacologyEffect.Damage(4));
        addEffect(MoleculeEnum.amphetamine, new PharmacologyEffect.Potion("moveSpeed", 30, 15));
        addEffect(MoleculeEnum.methamphetamine, new PharmacologyEffect.Potion("confusion", 40, 5));
        addEffect(MoleculeEnum.methamphetamine, new PharmacologyEffect.Damage(4));
        addEffect(MoleculeEnum.methamphetamine, new PharmacologyEffect.Potion("moveSpeed", 60, 7));
        addEffect(MoleculeEnum.mycotoxin, new PharmacologyEffect.Potion("wither", 8, 7));
        addEffect(MoleculeEnum.mycotoxin, new PharmacologyEffect.Potion("weakness", 12, 1));
        addEffect(MoleculeEnum.ethanol, new PharmacologyEffect.Potion("confusion", 30, 5));
        addEffect(MoleculeEnum.ethanol, new PharmacologyEffect.Food(1, .1F));
        addEffect(MoleculeEnum.cyanide, new PharmacologyEffect.Potion("wither", 40, 3));
        addEffect(MoleculeEnum.penicillin, new PharmacologyEffect.Cure());
        addEffect(MoleculeEnum.penicillin, new PharmacologyEffect.Potion("regeneration", 40, 3));
        addEffect(MoleculeEnum.testosterone, new PharmacologyEffect.Potion("damageBoost", 20));
        addEffect(MoleculeEnum.testosterone, new PharmacologyEffect.Potion("moveSpeed", 20, 1));
        addEffect(MoleculeEnum.xanax, new PharmacologyEffect.Potion("confusion", 30, 3));
        addEffect(MoleculeEnum.xanax, new PharmacologyEffect.Potion("moveSpeed", 20, 1));
        addEffect(MoleculeEnum.pantherine, new PharmacologyEffect.Potion("confusion", 30, 3));
        addEffect(MoleculeEnum.pantherine, new PharmacologyEffect.Potion("moveSlowdown", 40, 2));
        addEffect(MoleculeEnum.mescaline, new PharmacologyEffect.Damage(2));
        addEffect(MoleculeEnum.mescaline, new PharmacologyEffect.Potion("confusion", 60, 4));
        addEffect(MoleculeEnum.mescaline, new PharmacologyEffect.Potion("blindness", 30, 4));
        addEffect(MoleculeEnum.asprin, new PharmacologyEffect.Cure());
        addEffect(MoleculeEnum.asprin, new PharmacologyEffect.Potion("regeneration", 2, 1));
        addEffect(Arrays.asList(MoleculeEnum.phosgene, MoleculeEnum.aalc, MoleculeEnum.sulfuricAcid, MoleculeEnum.buli), new PharmacologyEffect.Burn(2));
        addEffect(MoleculeEnum.tetrodotoxin, new PharmacologyEffect.Potion("moveSlowdown", 5, 8));
        addEffect(MoleculeEnum.tetrodotoxin,  new PharmacologyEffect.Potion("weakness", 2, 1));
        addEffect(MoleculeEnum.fingolimod, new PharmacologyEffect.Potion("damageBoost", 60, 1));
        addEffect(MoleculeEnum.fingolimod, new PharmacologyEffect.Potion("moveSpeed", 60, 1));
        addEffect(MoleculeEnum.fingolimod, new PharmacologyEffect.Potion("regeneration", 60, 1));
        addEffect(MoleculeEnum.fingolimod, new PharmacologyEffect.Potion("hunger", 300, 1));
        addEffect(MoleculeEnum.thc, new PharmacologyEffect.Potion("regeneration", 60, 1));
        addEffect(MoleculeEnum.thc, new PharmacologyEffect.Potion("confusion", 60, 5));
        addEffect(MoleculeEnum.thc, new PharmacologyEffect.Potion("moveSlowDown", 60, 3));
        addEffect(MoleculeEnum.thc, new PharmacologyEffect.Potion("hunger", 120, 20));
        addEffect(MoleculeEnum.nodularin, new PharmacologyEffect.Potion("poison", 30, 3));
        addEffect(MoleculeEnum.nodularin, new PharmacologyEffect.Potion("hunger", 60, 3));
        addEffect(MoleculeEnum.hist, new PharmacologyEffect.Cure());
        addEffect(MoleculeEnum.hist, new PharmacologyEffect.Potion("confusion", 20, 5));
        addEffect(MoleculeEnum.pal2, new PharmacologyEffect.Potion("moveSlowdown", 5, 7));
        addEffect(MoleculeEnum.pal2, new PharmacologyEffect.Potion("wither", 5));
        addEffect(MoleculeEnum.theobromine, new PharmacologyEffect.Potion("digSpeed", 30, 10));
        addEffect(MoleculeEnum.theobromine, new PharmacologyEffect.Potion("moveSpeed", 30, 10));
        addEffect(MoleculeEnum.retinol, new PharmacologyEffect.Potion("nightVision", 30 ,5));
        List<MoleculeEnum> aminoAcids = Arrays.asList(MoleculeEnum.glycine, MoleculeEnum.alinine,
                MoleculeEnum.arginine, MoleculeEnum.proline, MoleculeEnum.leucine,
                MoleculeEnum.isoleucine, MoleculeEnum.cysteine, MoleculeEnum.valine,
                MoleculeEnum.threonine, MoleculeEnum.histidine, MoleculeEnum.methionine,
                MoleculeEnum.tyrosine, MoleculeEnum.asparagine, MoleculeEnum.asparticAcid,
                MoleculeEnum.phenylalanine, MoleculeEnum.serine);
        addEffect(aminoAcids, new PharmacologyEffect.Food(2, .1F));
        addEffect(aminoAcids, new PharmacologyEffect.Potion("digSpeed", 20, 1));
        addEffect(aminoAcids, new PharmacologyEffect.Potion("jump", 20, 1));
        addEffect(MoleculeEnum.cocaine, new PharmacologyEffect.Potion("confusion", 120 ,5));
        addEffect(MoleculeEnum.cocaine, new PharmacologyEffect.Damage(4));
        addEffect(MoleculeEnum.cocaine, new PharmacologyEffect.Potion("nightVision", 120 ,5));
        addEffect(MoleculeEnum.cocaine, new PharmacologyEffect.Potion("moveSpeed", 120 ,12));
        addEffect(MoleculeEnum.cocainehcl, new PharmacologyEffect.Potion("confusion", 60 ,5));
        addEffect(MoleculeEnum.cocainehcl, new PharmacologyEffect.Damage(4));
        addEffect(MoleculeEnum.cocainehcl, new PharmacologyEffect.Potion("nightVision", 60 ,5));
        addEffect(MoleculeEnum.cocainehcl, new PharmacologyEffect.Potion("moveSpeed", 60 ,10));
        addEffect(MoleculeEnum.metblue, new PharmacologyEffect.Cure());
        addEffect(MoleculeEnum.metblue, new PharmacologyEffect.Potion("regeneration", 30, 4));
        addEffect(MoleculeEnum.metblue, new PharmacologyEffect.Potion("weakness", 30, 4));
        addEffect(MoleculeEnum.meoh, new PharmacologyEffect.Potion("blindness", 20, 6));
        addEffect(MoleculeEnum.meoh, new PharmacologyEffect.Potion("wither", 10, 2));
        addEffect(MoleculeEnum.radchlor, new PharmacologyEffect.Potion("weakness", 120, 6));
        addEffect(MoleculeEnum.radchlor, new PharmacologyEffect.Potion("poison", 20, 6));
        addEffect(MoleculeEnum.radchlor, new PharmacologyEffect.Potion("wither", 30, 1));
        addEffect(MoleculeEnum.caulerpenyne, new PharmacologyEffect.Potion("weakness", 2, 6));
        addEffect(MoleculeEnum.caulerpenyne, new PharmacologyEffect.Potion("confusion", 2, 4));
        addEffect(MoleculeEnum.latropine, new PharmacologyEffect.Damage(4));
        addEffect(MoleculeEnum.latropine, new PharmacologyEffect.Potion("confusion", 12, 2));
        addEffect(MoleculeEnum.latropine, new PharmacologyEffect.Potion("Delirium", 18, 1));
        addEffect(MoleculeEnum.latropine, new PharmacologyEffect.Potion("moveSlowdown", 45, 2));
    }
}
