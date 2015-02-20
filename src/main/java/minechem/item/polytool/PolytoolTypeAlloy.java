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

    public PolytoolTypeAlloy(ElementAlloyEnum alloy)
    {
        this.alloy = alloy;
    }

    public PolytoolTypeAlloy(ElementAlloyEnum alloy, float power)
    {
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
    public float getStrVsBlock(ItemStack itemStack, Block block, int meta)
    {
        // There must be a better way to do this
        if (ForgeHooks.isToolEffective(new ItemStack(Items.diamond_pickaxe), block, meta))
        {
            for (int id : OreDictionary.getOreIDs(new ItemStack(block,1,meta))) if (OreDictionary.getOreName(id).contains("stone")) return this.getStrStone();
            if (block == Blocks.stone || block == Blocks.cobblestone)
            {
                return this.getStrStone();
            }
            return this.getStrOre();
        } else if (ForgeHooks.isToolEffective(new ItemStack(Items.diamond_shovel), block, meta))
        {
            return this.getStrShovel();
        } else if (ForgeHooks.isToolEffective(new ItemStack(Items.diamond_sword), block, meta))
        {
            return this.getStrSword();
        } else if (ForgeHooks.isToolEffective(new ItemStack(Items.diamond_axe), block, meta))
        {
            return this.getStrAxe();
        }
        return 0;
    }

    @Override
    public float getDamageModifier()
    {
        return getStrSword();
    }


    @Override
    public ElementEnum getElement()
    {
        return alloy.element;
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
