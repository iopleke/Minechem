package minechem.potion;

import java.util.ArrayList;
import minechem.utils.Constants;
import minechem.utils.EnumColour;
import minechem.utils.MinechemUtil;
import minechem.utils.PotionHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

public abstract class PharmacologyEffect
{
    private String colour;

    PharmacologyEffect(EnumColour colour)
    {
        this.colour = colour.toString();
    }

    public abstract void applyEffect(EntityLivingBase entityLivingBase);

    public String getColour()
    {
        return colour;
    }

    public static class Food extends PharmacologyEffect
    {
        private int level;
        private float saturation;

        public Food(int level, float saturation)
        {
            super(EnumColour.DARK_GREEN);
            this.level = level;
            this.saturation = saturation;
        }

        @Override
        public void applyEffect(EntityLivingBase entityLivingBase)
        {
            if (entityLivingBase instanceof EntityPlayer)
            {
                ((EntityPlayer) entityLivingBase).getFoodStats().addStats(level, saturation);
            }
        }

        @Override
        public String toString()
        {
            return "Food Effect: " + level + ", Saturation: " + saturation;
        }

        @Override
        public boolean equals(Object obj)
        {
            if (obj instanceof Food)
            {
                Food other = (Food) obj;
                if (other.level == this.level && other.saturation == this.saturation)
                {
                    return true;
                }
            }
            return false;
        }
    }

    public static class Burn extends PharmacologyEffect
    {
        private int duration;

        public Burn(int duration)
        {
            super(EnumColour.RED);
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
            return "Burn Effect: " + duration + " s";
        }

        @Override
        public boolean equals(Object obj)
        {
            if (obj instanceof Burn)
            {
                Burn other = (Burn) obj;
                if (other.duration == this.duration)
                {
                    return true;
                }
            }
            return false;
        }
    }

    public static class Cure extends PharmacologyEffect
    {
        private int potionId;

        public Cure()
        {
            this(-1);
        }

        public Cure(int potionId)
        {
            super(EnumColour.AQUA);
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
                {
                    if (potionEffect.getCurativeItems().size() > 0)
                        entityLivingBase.removePotionEffect(potionEffect.getPotionID());
                }
            } else
            {
                entityLivingBase.removePotionEffect(potionId);
            }
        }

        @Override
        public String toString()
        {
            return "Cure Effect: " + (potionId == -1 ? "all" : MinechemUtil.getLocalString(PotionHelper.getPotionNameById(potionId)));
        }

        @Override
        public boolean equals(Object obj)
        {
            if (obj instanceof Cure)
            {
                Cure other = (Cure) obj;
                if (other.potionId == this.potionId)
                {
                    return true;
                }
            }
            return false;
        }
    }

    public static class Damage extends PharmacologyEffect
    {
        private float damage;

        public Damage(float damage)
        {
            super(EnumColour.ORANGE);
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
            float print = damage / 2;
            return "Damage Effect: " + print + " heart" + (print == 1 ? "" : "s");
        }

        @Override
        public boolean equals(Object obj)
        {
            if (obj instanceof Damage)
            {
                Damage other = (Damage) obj;
                if (other.damage == this.damage)
                {
                    return true;
                }
            }
            return false;
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
            super(EnumColour.PURPLE);
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
            return "Potion Effect: " + MinechemUtil.getLocalString(PotionHelper.getPotionNameById(potionId)) + ", Duration: " + duration + ", Power: " + power;
        }

        @Override
        public boolean equals(Object obj)
        {
            if (obj instanceof Potion)
            {
                Potion other = (Potion) obj;
                if (other.duration == this.duration && other.potionId == this.potionId && other.power == this.power)
                {
                    return true;
                }
            }
            return false;
        }
    }
}
