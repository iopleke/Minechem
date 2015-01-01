package minechem.item.polytool;

import minechem.item.element.ElementAlloyEnum;
import minechem.item.element.ElementEnum;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.oredict.OreDictionary;

public class PolytoolTypeAlloy extends PolytoolUpgradeType
{

    private ElementAlloyEnum alloy;

    public PolytoolTypeAlloy(ElementAlloyEnum alloy, float power)
    {

        super(true);
        this.power = power;
        this.alloy = alloy;
    }

    public float getStrOre()
    {
        return this.alloy.pickaxe * this.power;
    }

    public float getStrStone()
    {
        return this.alloy.stone * this.power;
    }

    public float getStrAxe()
    {
        return this.alloy.axe * this.power;
    }

    public float getStrSword()
    {
        return this.alloy.sword * this.power;
    }

    public float getStrShovel()
    {
        return this.alloy.shovel * this.power;
    }

    @Override
    public float getStrVsBlock(ItemStack itemStack, Block block)
    {
        // There must be a better way to do this
        if (ForgeHooks.isToolEffective(new ItemStack(Items.diamond_pickaxe), block, 0))
        {

            if (block == Blocks.stone || block == Blocks.cobblestone || OreDictionary.getOreName(OreDictionary.getOreID(block.getUnlocalizedName())).contains("stone"))
            {

                return this.getStrStone();
            }
            return this.getStrOre();
        } else if (ForgeHooks.isToolEffective(new ItemStack(Items.diamond_shovel), block, 0))
        {

            return this.getStrShovel();
        } else if (ForgeHooks.isToolEffective(new ItemStack(Items.diamond_sword), block, 0))
        {

            return this.getStrSword();
        } else if (ForgeHooks.isToolEffective(new ItemStack(Items.diamond_axe), block, 0))
        {

            return this.getStrAxe();
        }
        return 0;
    }

    @Override
    public void hitEntity(ItemStack itemStack, EntityLivingBase target, EntityLivingBase player)
    {
    }

    @Override
    public void onBlockDestroyed(ItemStack itemStack, World world, Block block, int x, int y, int z, EntityLivingBase entityLiving)
    {
    }

    @Override
    public ElementEnum getElement()
    {

        return alloy.element;
    }

    @Override
    public void onTick()
    {
    }

    @Override
    public String getDescription()
    {

        String result = "";

        result += "Ore: " + this.getStrOre() + " ";
        result += "Stone: " + this.getStrStone() + " ";
        result += "Sword: " + this.getStrSword() + " ";
        result += "Axe: " + this.getStrAxe() + " ";
        result += "Shovel: " + this.getStrShovel() + " ";

        return result;
    }

}
