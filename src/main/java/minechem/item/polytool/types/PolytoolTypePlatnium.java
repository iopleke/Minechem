package minechem.item.polytool.types;

import minechem.item.element.EnumElement;
import minechem.item.polytool.PolytoolUpgradeType;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PolytoolTypePlatnium extends PolytoolUpgradeType
{

    public PolytoolTypePlatnium()
    {
        super();
    }

    @Override
    public float getStrVsBlock(ItemStack itemStack, Block block)
    {

        return 0;
    }

    @Override
    public void hitEntity(ItemStack itemStack, EntityLivingBase target, EntityLivingBase player)
    {
        if (!target.worldObj.isRemote)
        {
            if (target.worldObj.rand.nextInt(50) < power + 1)
            {
                player.worldObj.playAuxSFX(2002, (int) Math.round(player.posX), (int) Math.round(player.posY), (int) Math.round(player.posZ), 0);
                int i = (int) (power + player.worldObj.rand.nextInt(5) + player.worldObj.rand.nextInt(5));

                while (i > 0)
                {
                    int j = EntityXPOrb.getXPSplit(i);
                    i -= j;
                    player.worldObj.spawnEntityInWorld(new EntityXPOrb(player.worldObj, player.posX, player.posY, player.posZ, j));
                }
            }
        }
    }

    @Override
    public void onBlockDestroyed(ItemStack itemStack, World world, int id, int x, int y, int z, EntityLivingBase entityLiving)
    {
    }

    @Override
    public EnumElement getElement()
    {

        return EnumElement.Pt;
    }

    @Override
    public void onTick()
    {
    }

    @Override
    public String getDescription()
    {

        return "Bonus XP";
    }

}
