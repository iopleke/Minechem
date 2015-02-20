package minechem.item.polytool;

import minechem.item.element.ElementEnum;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

public abstract class PolytoolUpgradeType
{
    protected static final Random rand = new Random(System.currentTimeMillis());
    public float power = 0;

    public float getStrVsBlock(ItemStack itemStack, Block block, int meta)
    {
        return 0F;
    }

    public void hitEntity(ItemStack itemStack, EntityLivingBase target, EntityLivingBase player)
    {
    }

    public void onBlockDestroyed(ItemStack itemStack, World world, Block block, int x, int y, int z, EntityLivingBase entityLiving)
    {
    }

    public abstract ElementEnum getElement();

    public void onTick()
    {
    }

    public float getDamageModifier()
    {
        return 0F;
    }

    public PolytoolUpgradeType()
    {
    }

    public void onTickFull(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
    {
        this.onTick();
    }

    public abstract String getDescription();

    public PolytoolUpgradeType setPower(float power)
    {
        this.power = power;
        return this;
    }
}
