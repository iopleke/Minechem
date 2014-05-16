package minechem.common.polytool.types;

import java.util.Iterator;
import java.util.List;

import minechem.api.core.EnumElement;
import minechem.common.polytool.PolytoolUpgradeType;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class PolytoolTypeNickel extends PolytoolUpgradeType
{

    public PolytoolTypeNickel()
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

    }

    @Override
    public void onBlockDestroyed(ItemStack itemStack, World world, int id, int x, int y, int z, EntityLivingBase player)
    {
        List<EntityItem> items = player.worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(player.posX - power, player.posY - power, player.posZ - power, player.posX + power, player.posY + power, player.posZ + power));

        Iterator iter = items.iterator();
        while (iter.hasNext())
        {
            EntityItem entity = (EntityItem) iter.next();
            entity.motionX = -1 * (entity.posX - player.posX);

            entity.motionY = -1 * (entity.posY - player.posY);

            entity.motionZ = -1 * (entity.posZ - player.posZ);
        }
    }

    @Override
    public EnumElement getElement()
    {

        return EnumElement.Ni;
    }

    @Override
    public void onTick()
    {
    }

    @Override
    public String getDescription()
    {

        return "Sucks up nearby items when another block is mined";
    }

}
