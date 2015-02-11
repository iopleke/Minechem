package minechem.potion;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import minechem.Settings;
import minechem.item.molecule.MoleculeEnum;
import net.minecraft.entity.EntityLivingBase;

public class PharmacologyEffectRegistry
{
    private static Map<MoleculeEnum, List<PharmacologyEffect>> effects = new HashMap<MoleculeEnum, List<PharmacologyEffect>>();

    public static void addEffect(MoleculeEnum molecule, PharmacologyEffect effect)
    {
        List<PharmacologyEffect> list = effects.get(molecule);
        if (list == null)
        {
            list = new LinkedList<PharmacologyEffect>();
        }
        for (PharmacologyEffect existingEffect : list)
        {
            if (existingEffect.equals(effect))
            {
                return;
            }
        }
        list.add(effect);
        effects.put(molecule, list);
    }

    public static void addEffects(MoleculeEnum molecule, List<PharmacologyEffect> toAdd)
    {
        for (PharmacologyEffect effect : toAdd)
        {
            addEffect(molecule, effect);
        }
    }

    public static void addEffect(List<MoleculeEnum> molecules, PharmacologyEffect effect)
    {
        for (MoleculeEnum molecule : molecules)
        {
            addEffect(molecule, effect);
        }
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
            {
                effect.applyEffect(entityLivingBase);
            }
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
        {
            effects.get(molecule).remove(effect);
        }
    }

    public static void init()
    {
        addEffect(MoleculeEnum.water, new PharmacologyEffect.Food(1, .1F));
        addEffect(MoleculeEnum.starch, new PharmacologyEffect.Food(6, .2F));
        addEffect(MoleculeEnum.xylitol, new PharmacologyEffect.Food(6, .2F));
        addEffect(MoleculeEnum.sucrose, new PharmacologyEffect.Potion("moveSpeed", 1, 20));
        addEffect(MoleculeEnum.sucrose, new PharmacologyEffect.Food(1, .1F));
        addEffect(MoleculeEnum.mycotoxin, new PharmacologyEffect.Potion("wither", 7, 8));
        addEffect(MoleculeEnum.mycotoxin, new PharmacologyEffect.Potion("weakness", 1, 12));
        addEffect(MoleculeEnum.ethanol, new PharmacologyEffect.Potion("confusion", 5, 30));
        addEffect(MoleculeEnum.ethanol, new PharmacologyEffect.Food(1, .1F));
        addEffect(MoleculeEnum.cyanide, new PharmacologyEffect.Potion("wither", 3, 40));
        addEffect(MoleculeEnum.penicillin, new PharmacologyEffect.Cure());
        addEffect(MoleculeEnum.penicillin, new PharmacologyEffect.Potion("regeneration", 3, 40));
        addEffect(MoleculeEnum.testosterone, new PharmacologyEffect.Potion("damageBoost", 20));
        addEffect(MoleculeEnum.testosterone, new PharmacologyEffect.Potion("moveSpeed", 1, 20));
        addEffect(MoleculeEnum.xanax, new PharmacologyEffect.Potion("confusion", 3, 30));
        addEffect(MoleculeEnum.xanax, new PharmacologyEffect.Potion("moveSpeed", 1, 20));
        addEffect(MoleculeEnum.pantherine, new PharmacologyEffect.Potion("confusion", 3, 30));
        addEffect(MoleculeEnum.pantherine, new PharmacologyEffect.Potion("moveSlowdown", 2, 40));
        addEffect(MoleculeEnum.mescaline, new PharmacologyEffect.Damage(2));
        addEffect(MoleculeEnum.mescaline, new PharmacologyEffect.Potion("confusion", 4, 60));
        addEffect(MoleculeEnum.mescaline, new PharmacologyEffect.Potion("blindness", 4, 30));
        addEffect(MoleculeEnum.asprin, new PharmacologyEffect.Cure());
        addEffect(MoleculeEnum.asprin, new PharmacologyEffect.Potion("regeneration", 1, 2));
        addEffect(Arrays.asList(MoleculeEnum.phosgene, MoleculeEnum.aalc, MoleculeEnum.sulfuricAcid, MoleculeEnum.buli), new PharmacologyEffect.Burn(2));
        addEffect(MoleculeEnum.tetrodotoxin, new PharmacologyEffect.Potion("moveSlowdown", 8, 5));
        addEffect(MoleculeEnum.tetrodotoxin, new PharmacologyEffect.Potion("weakness", 1, 2));
        addEffect(MoleculeEnum.fingolimod, new PharmacologyEffect.Potion("damageBoost", 1, 60));
        addEffect(MoleculeEnum.fingolimod, new PharmacologyEffect.Potion("moveSpeed", 1, 60));
        addEffect(MoleculeEnum.fingolimod, new PharmacologyEffect.Potion("regeneration", 1, 60));
        addEffect(MoleculeEnum.fingolimod, new PharmacologyEffect.Potion("hunger", 1, 300));
        addEffect(MoleculeEnum.nodularin, new PharmacologyEffect.Potion("poison", 3, 30));
        addEffect(MoleculeEnum.nodularin, new PharmacologyEffect.Potion("hunger", 3, 60));
        addEffect(MoleculeEnum.hist, new PharmacologyEffect.Cure());
        addEffect(MoleculeEnum.hist, new PharmacologyEffect.Potion("confusion", 5, 20));
        addEffect(MoleculeEnum.pal2, new PharmacologyEffect.Potion("moveSlowdown", 7, 5));
        addEffect(MoleculeEnum.pal2, new PharmacologyEffect.Potion("wither", 5));
        addEffect(MoleculeEnum.theobromine, new PharmacologyEffect.Potion("digSpeed", 10, 30));
        addEffect(MoleculeEnum.theobromine, new PharmacologyEffect.Potion("moveSpeed", 10, 30));
        addEffect(MoleculeEnum.retinol, new PharmacologyEffect.Potion("nightVision", 5, 30));
        List<MoleculeEnum> aminoAcids = Arrays.asList(MoleculeEnum.glycine, MoleculeEnum.alinine,
            MoleculeEnum.arginine, MoleculeEnum.proline, MoleculeEnum.leucine,
            MoleculeEnum.isoleucine, MoleculeEnum.cysteine, MoleculeEnum.valine,
            MoleculeEnum.threonine, MoleculeEnum.histidine, MoleculeEnum.methionine,
            MoleculeEnum.tyrosine, MoleculeEnum.asparagine, MoleculeEnum.asparticAcid,
            MoleculeEnum.phenylalanine, MoleculeEnum.serine);
        addEffect(aminoAcids, new PharmacologyEffect.Food(2, .1F));
        addEffect(aminoAcids, new PharmacologyEffect.Potion("digSpeed", 1, 20));
        addEffect(aminoAcids, new PharmacologyEffect.Potion("jump", 1, 20));

        if (Settings.recreationalChemicalEffects)
        {
            addEffect(MoleculeEnum.amphetamine, new PharmacologyEffect.Damage(4));
            addEffect(MoleculeEnum.amphetamine, new PharmacologyEffect.Potion("confusion", 5, 20));
            addEffect(MoleculeEnum.amphetamine, new PharmacologyEffect.Potion("moveSpeed", 15, 30));
            addEffect(MoleculeEnum.cocaine, new PharmacologyEffect.Damage(4));
            addEffect(MoleculeEnum.cocaine, new PharmacologyEffect.Potion("confusion", 5, 120));
            addEffect(MoleculeEnum.cocaine, new PharmacologyEffect.Potion("moveSpeed", 12, 120));
            addEffect(MoleculeEnum.cocaine, new PharmacologyEffect.Potion("nightVision", 5, 120));
            addEffect(MoleculeEnum.cocainehcl, new PharmacologyEffect.Damage(4));
            addEffect(MoleculeEnum.cocainehcl, new PharmacologyEffect.Potion("confusion", 5, 60));
            addEffect(MoleculeEnum.cocainehcl, new PharmacologyEffect.Potion("moveSpeed", 10, 60));
            addEffect(MoleculeEnum.cocainehcl, new PharmacologyEffect.Potion("nightVision", 5, 60));
            addEffect(MoleculeEnum.dimethyltryptamine, new PharmacologyEffect.Damage(2));
            addEffect(MoleculeEnum.dimethyltryptamine, new PharmacologyEffect.Potion("confusion", 5, 30));
            addEffect(MoleculeEnum.dimethyltryptamine, new PharmacologyEffect.Potion("nightVision", 5, 60));
            addEffect(MoleculeEnum.methamphetamine, new PharmacologyEffect.Damage(4));
            addEffect(MoleculeEnum.methamphetamine, new PharmacologyEffect.Potion("confusion", 5, 40));
            addEffect(MoleculeEnum.methamphetamine, new PharmacologyEffect.Potion("moveSpeed", 7, 60));
            addEffect(MoleculeEnum.psilocybin, new PharmacologyEffect.Damage(2));
            addEffect(MoleculeEnum.psilocybin, new PharmacologyEffect.Potion("confusion", 5, 10));
            addEffect(MoleculeEnum.psilocybin, new PharmacologyEffect.Potion("nightVision", 5, 20));
            addEffect(MoleculeEnum.thc, new PharmacologyEffect.Potion("confusion", 5, 60));
            addEffect(MoleculeEnum.thc, new PharmacologyEffect.Potion("hunger", 20, 120));
            addEffect(MoleculeEnum.thc, new PharmacologyEffect.Potion("moveSlowDown", 3, 60));
            addEffect(MoleculeEnum.thc, new PharmacologyEffect.Potion("regeneration", 1, 60));
        }

        addEffect(MoleculeEnum.metblue, new PharmacologyEffect.Cure());
        addEffect(MoleculeEnum.metblue, new PharmacologyEffect.Potion("regeneration", 4, 30));
        addEffect(MoleculeEnum.metblue, new PharmacologyEffect.Potion("weakness", 4, 30));
        addEffect(MoleculeEnum.meoh, new PharmacologyEffect.Potion("blindness", 6, 20));
        addEffect(MoleculeEnum.meoh, new PharmacologyEffect.Potion("wither", 2, 10));
        addEffect(MoleculeEnum.radchlor, new PharmacologyEffect.Potion("weakness", 6, 120));
        addEffect(MoleculeEnum.radchlor, new PharmacologyEffect.Potion("poison", 6, 20));
        addEffect(MoleculeEnum.radchlor, new PharmacologyEffect.Potion("wither", 1, 30));
        addEffect(MoleculeEnum.caulerpenyne, new PharmacologyEffect.Potion("weakness", 6, 2));
        addEffect(MoleculeEnum.caulerpenyne, new PharmacologyEffect.Potion("confusion", 4, 2));
        addEffect(MoleculeEnum.latropine, new PharmacologyEffect.Damage(4));
        addEffect(MoleculeEnum.latropine, new PharmacologyEffect.Potion("confusion", 2, 12));
        addEffect(MoleculeEnum.latropine, new PharmacologyEffect.Potion("Delirium", 1, 18));
        addEffect(MoleculeEnum.latropine, new PharmacologyEffect.Potion("moveSlowdown", 2, 45));
    }
}
