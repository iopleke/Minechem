package ljdp.minechem.common.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import net.minecraft.entity.EntityHanging;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumArt;
import net.minecraft.world.World;

public class EntityTableOfElements extends EntityHanging
{
    public EnumArt art;

    public EntityTableOfElements(World par1World)
    {
        super(par1World);
    }

    public EntityTableOfElements(World par1World, int par2, int par3, int par4, int par5)
    {
        super(par1World, par2, par3, par4, par5);
        ArrayList arraylist = new ArrayList();
        EnumArt[] aenumart = EnumArt.values();
        int i1 = aenumart.length;

        for (int j1 = 0; j1 < i1; ++j1)
        {
            EnumArt enumart = aenumart[j1];
            this.art = enumart;
            this.setDirection(par5);

            if (this.onValidSurface())
            {
                arraylist.add(enumart);
            }
        }

        if (!arraylist.isEmpty())
        {
            this.art = (EnumArt)arraylist.get(this.rand.nextInt(arraylist.size()));
        }

        this.setDirection(par5);
    }

    @SideOnly(Side.CLIENT)
    public EntityTableOfElements(World par1World, int par2, int par3, int par4, int par5, String par6Str)
    {
        this(par1World, par2, par3, par4, par5);
        EnumArt[] aenumart = EnumArt.values();
        int i1 = aenumart.length;

        for (int j1 = 0; j1 < i1; ++j1)
        {
            EnumArt enumart = aenumart[j1];

            if (enumart.title.equals(par6Str))
            {
                this.art = enumart;
                break;
            }
        }

        this.setDirection(par5);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setString("Motive", this.art.title);
        super.writeEntityToNBT(par1NBTTagCompound);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        String s = par1NBTTagCompound.getString("Motive");
        EnumArt[] aenumart = EnumArt.values();
        int i = aenumart.length;

        for (int j = 0; j < i; ++j)
        {
            EnumArt enumart = aenumart[j];

            if (enumart.title.equals(s))
            {
                this.art = enumart;
            }
        }

        if (this.art == null)
        {
            this.art = EnumArt.Kebab;
        }

        super.readEntityFromNBT(par1NBTTagCompound);
    }

    public int func_82329_d()
    {
        return this.art.sizeX;
    }

    public int func_82330_g()
    {
        return this.art.sizeY;
    }

    /**
     * Drop the item currently on this item frame.
     */
    public void dropItemStack()
    {
        this.entityDropItem(new ItemStack(Item.painting), 0.0F);
    }
}
