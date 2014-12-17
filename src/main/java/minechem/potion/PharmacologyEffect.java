package minechem.potion;

import minechem.utils.Constants;
import minechem.utils.PotionHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

import java.util.ArrayList;

public abstract class PharmacologyEffect
{
    public abstract void applyEffect(EntityLivingBase entityLivingBase);

    public static class Food extends PharmacologyEffect
    {
        private int level;
        private float saturation;

        public Food(int level, float saturation)
        {
            this.level = level;
            this.saturation = saturation;
        }

        @Override
        public void applyEffect(EntityLivingBase entityLivingBase)
        {
            if (entityLivingBase instanceof EntityPlayer)
                ((EntityPlayer) entityLivingBase).getFoodStats().addStats(level, saturation);
        }

        @Override
        public String toString()
        {
            return "FoodEffect: level=" + level + " saturation" + saturation;
        }
    }

    public static class Burn extends PharmacologyEffect
    {
        private int duration;

        public Burn(int duration)
        {
            this.duration = duration;
        }

        @Override
        public void applyEffect(EntityLivingBase entityLivingBase)
        {
            entityLivingBase.setFire(duration);
        }

        @Override
        public String toString()
        {
            return "BurnEffect: duration=" + duration;
        }
    }

    public static class Cure extends PharmacologyEffect
    {
        private int potionId;

        public Cure()
        {
            this.potionId = -1;
        }

        public Cure(int potionId)
        {
            this.potionId = potionId;
        }

        public Cure(String potionName)
        {
            this(PotionHelper.getPotionByName(potionName).getId());
        }

        @SuppressWarnings("unchecked")
        @Override
        public void applyEffect(EntityLivingBase entityLivingBase)
        {
            if (potionId == -1)
            {
                for (PotionEffect potionEffect : new ArrayList<PotionEffect>(entityLivingBase.getActivePotionEffects()))
                    entityLivingBase.removePotionEffect(potionEffect.getPotionID());
            }
            else
                entityLivingBase.removePotionEffect(potionId);
        }

        @Override
        public String toString()
        {
            return "CureEffect: cures=" + (potionId == -1 ? "all" : PotionHelper.getPotionNameById(potionId));
        }
    }

    public static class Damage extends PharmacologyEffect
    {
        private float damage;

        public Damage(float damage)
        {
            this.damage = damage;
        }

        @Override
        public void applyEffect(EntityLivingBase entityLivingBase)
        {
            entityLivingBase.attackEntityFrom(DamageSource.generic, damage);
        }

        @Override
        public String toString()
        {
            return "DamageEffect: damage=" + damage;
        }
    }

    public static class Potion extends PharmacologyEffect
    {
        private int duration;
        private int power;
        private int potionId;

        public Potion(String potionName, int power, int duration)
        {
            this(PotionHelper.getPotionByName(potionName).getId(), power, duration);
        }

        public Potion(String potionName, int duration)
        {
            this(potionName, 0, duration);
        }

        public Potion(int potionId, int duration)
        {
            this(potionId, 0, duration);
        }

        public Potion(int potionId, int power, int duration)
        {
            this.duration = duration;
            this.potionId = potionId;
            this.power = power;
        }

        @Override
        public void applyEffect(EntityLivingBase entityLivingBase)
        {
            entityLivingBase.addPotionEffect(new PotionEffect(potionId, duration * Constants.TICKS_PER_SECOND, power));
        }

        @Override
        public String toString()
        {
            return "PotionEffect: potion=" + PotionHelper.getPotionNameById(potionId) + " duration=" + duration + " power=" + power;
        }
    }
}
