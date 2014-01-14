package pixlepix.minechem.common.polytool;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.oredict.OreDictionary;
import pixlepix.minechem.api.core.EnumElement;

public class PolytoolTypeAlloy extends PolytoolUpgradeType {


    private EnumAlloy alloy;

    public PolytoolTypeAlloy(EnumAlloy alloy, float power) {

        super(true);
        this.power = power;
        this.alloy = alloy;
    }

    public float getStrOre() {
        return this.alloy.pickaxe * this.power;
    }

    public float getStrStone() {
        return this.alloy.stone * this.power;
    }

    public float getStrAxe() {
        return this.alloy.axe * this.power;
    }

    public float getStrSword() {
        return this.alloy.sword * this.power;
    }

    public float getStrShovel() {
        return this.alloy.shovel * this.power;
    }


    @Override
    public float getStrVsBlock(ItemStack itemStack, Block block) {
        //There must be a better way to do this
        if (ForgeHooks.isToolEffective(new ItemStack(Item.pickaxeDiamond), block, 0)) {

            if (block.blockID == Block.stone.blockID || block.blockID == Block.cobblestone.blockID || OreDictionary.getOreName(OreDictionary.getOreID(new ItemStack(block))).contains("stone")) {

                return this.getStrStone();
            }
            return this.getStrOre();
        } else if (ForgeHooks.isToolEffective(new ItemStack(Item.shovelDiamond), block, 0)) {

            return this.getStrShovel();
        } else if (ForgeHooks.isToolEffective(new ItemStack(Item.swordDiamond), block, 0)) {

            return this.getStrSword();
        } else if (ForgeHooks.isToolEffective(new ItemStack(Item.axeDiamond), block, 0)) {

            return this.getStrAxe();
        }
        return 0;
    }

    @Override
    public void hitEntity(ItemStack itemStack, EntityLivingBase target,
                          EntityLivingBase player) {
    }

    @Override
    public void onBlockDestroyed(ItemStack itemStack, World world, int id,
                                 int x, int y, int z, EntityLivingBase entityLiving) {
    }

    @Override
    public EnumElement getElement() {

        return alloy.element;
    }

    @Override
    public void onTick() {
    }

    @Override
    public String getDescription() {

        String result = "";

        result += "Ore: " + this.getStrOre() + "\n";
        result += "Stone: " + this.getStrStone() + "\n";
        result += "Sword: " + this.getStrSword() + "\n";
        result += "Axe: " + this.getStrAxe() + "\n";
        result += "Shovel: " + this.getStrShovel() + "\n";

        return result;
    }

}
