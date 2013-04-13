package ljdp.minechem.common.entity;

import java.util.List;

import ljdp.minechem.common.MinechemItems;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityTableOfElements extends Entity {
	
	private int tickCounter1;
    public int direction;
    public int xPosition;
    public int yPosition;
    public int zPosition;
    public static int tableSizeX = 2048/16;
    public static int tableSizeY = 1024/16;
	
	public EntityTableOfElements(World world) {
		super(world);
        tickCounter1 = 0;
        direction = 0;
        yOffset = 0.0F;
        setSize(0.5F, 0.5F);
	}
	
	public EntityTableOfElements(World world, int i, int j, int k, int l)
    {
        this(world);
        xPosition = i;
        yPosition = j;
        zPosition = k;
        setFacing(l);
    }
	
	public void setFacing(int i)
    {
        direction = i;
        prevRotationYaw = rotationYaw = i * 90;
        float f = tableSizeX;
        float f1 = tableSizeY;
        float f2 = tableSizeX;
        if (i == 0 || i == 2)
        {
            f2 = 0.5F;
        }
        else
        {
            f = 0.5F;
        }
        f /= 32F;
        f1 /= 32F;
        f2 /= 32F;
        float f3 = (float)xPosition + 0.5F;
        float f4 = (float)yPosition + 0.5F;
        float f5 = (float)zPosition + 0.5F;
        float f6 = 0.5625F;
        //float f6 = 0.0F;
        if (i == 0)
        {
            f5 -= f6;
        }
        if (i == 1)
        {
            f3 -= f6;
        }
        if (i == 2)
        {
            f5 += f6;
        }
        if (i == 3)
        {
            f3 += f6;
        }
        if (i == 0)
        {
            f3 -= func_411_c(tableSizeX);
        }
        if (i == 1)
        {
            f5 += func_411_c(tableSizeX);
        }
        if (i == 2)
        {
            f3 += func_411_c(tableSizeX);
        }
        if (i == 3)
        {
            f5 -= func_411_c(tableSizeX);
        }
        f4 += func_411_c(tableSizeY);
        setPosition(f3, f4, f5);
        float f7 = -0.00625F;
        boundingBox.setBounds(f3 - f - f7, f4 - f1 - f7, f5 - f2 - f7, f3 + f + f7, f4 + f1 + f7, f5 + f2 + f7);
    }
	
	private float func_411_c(int i)
    {
        if (i == 32)
        {
            return 0.5F;
        }
        return i != 64 ? 0.0F : 0.0F;
    }
	
	public boolean canBeCollidedWith()
    {
        return true;
    }
	
	public boolean attackEntityFrom(DamageSource damagesource, int i)
    {
        if (!isDead && !worldObj.isRemote)
        {
            setDead();
            setBeenAttacked();
            worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY, posZ, new ItemStack((MinechemItems.tableelements))));
        }
        return true;
    }
	
	public void onUpdate()
    {
        if (tickCounter1++ == 100 && !worldObj.isRemote)
        {
            tickCounter1 = 0;
            if (!canStay())
            {
                setDead();
                worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY, posZ, new ItemStack((MinechemItems.tableelements))));
            }
        }
    }
	
	public boolean canStay()
    {
        if (worldObj.getCollidingBoundingBoxes(this, boundingBox).size() > 0)
        {
            return false;
        }
        int i = tableSizeX / 16;
        int j = tableSizeY / 16;
        int k = xPosition;
        int l = yPosition;
        int i1 = zPosition;
        if (direction == 0)
        {
            k = MathHelper.floor_double(posX - (double)((float)tableSizeX / 32F));
        }
        if (direction == 1)
        {
            i1 = MathHelper.floor_double(posZ - (double)((float)tableSizeX / 32F));
        }
        if (direction == 2)
        {
            k = MathHelper.floor_double(posX - (double)((float)tableSizeX / 32F));
        }
        if (direction == 3)
        {
            i1 = MathHelper.floor_double(posZ - (double)((float)tableSizeX / 32F));
        }
        l = MathHelper.floor_double(posY - (double)((float)tableSizeY / 32F));
        for (int j1 = 0; j1 < i; j1++)
        {
            for (int k1 = 0; k1 < j; k1++)
            {
                Material material;
                if (direction == 0 || direction == 2)
                {
                    material = worldObj.getBlockMaterial(k + j1, l + k1, zPosition);
                }
                else
                {
                    material = worldObj.getBlockMaterial(xPosition, l + k1, i1 + j1);
                }
                if (!material.isSolid())
                {
                    return false;
                }
            }
        }

        List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox);
        for (int l1 = 0; l1 < list.size(); l1++)
        {
            if (list.get(l1) instanceof EntityTableOfElements)
            {
                return false;
            }
        }

        return true;
    }
	
	public void moveEntity(double d, double d1, double d2)
    {
        if (!worldObj.isRemote && d * d + d1 * d1 + d2 * d2 > 0.0D)
        {
            setDead();
            worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY, posZ, new ItemStack((MinechemItems.tableelements))));
        }
    }

    public void addVelocity(double d, double d1, double d2)
    {
        if (!worldObj.isRemote && d * d + d1 * d1 + d2 * d2 > 0.0D)
        {
            setDead();
            worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY, posZ, new ItemStack((MinechemItems.tableelements))));
        }
    }

	@Override
	protected void entityInit() {
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		direction = nbttagcompound.getByte("Dir");
        xPosition = nbttagcompound.getInteger("TileX");
        yPosition = nbttagcompound.getInteger("TileY");
        zPosition = nbttagcompound.getInteger("TileZ");
        setFacing(direction);
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		nbttagcompound.setByte("Dir", (byte)direction);
        nbttagcompound.setInteger("TileX", xPosition);
        nbttagcompound.setInteger("TileY", yPosition);
        nbttagcompound.setInteger("TileZ", zPosition);
	}

}
