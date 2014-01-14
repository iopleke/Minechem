package pixlepix.minechem.minechem.common.gates;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraftforge.common.ForgeDirection;
import buildcraft.api.core.IIconProvider;
import buildcraft.api.gates.ActionManager;
import buildcraft.api.gates.ITrigger;
import buildcraft.api.gates.ITriggerParameter;
import buildcraft.api.gates.TriggerParameter;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class MinechemTrigger implements ITrigger {
    @SideOnly(Side.CLIENT)
    public Icon icon;
    public IIconProvider provider = new MineChemIconProvider();
    protected String desc;
    protected int id;

    public MinechemTrigger(int id, String dsc) {
        this.id = id;

        this.desc = dsc;
        ActionManager.triggers.put(desc, this);
    }

    @Override
    public int getLegacyId() {
        return this.id;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon() {
    
		return provider.getIcon(0);
        
    }

    @Override
    public boolean hasParameter() {
        return false;
    }

    @Override
    public String getDescription() {
        return desc;
    }

    @Override
    public abstract boolean isTriggerActive(ForgeDirection side, TileEntity tile, ITriggerParameter parameter);

    @Override
    public final ITriggerParameter createParameter() {
        return new TriggerParameter();
    }
    
    
    @Override
	public String getUniqueTag() {
		return this.desc;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		
	}
}
