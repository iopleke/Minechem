package ljdp.minechem.common.gates;

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
    private String desc;
    protected int id;

    public MinechemTrigger(int id, String dsc) {
        this.id = id;
        ActionManager.triggers[id] = this;
        this.desc = dsc;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getTextureIcon() {
        return icon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIconProvider getIconProvider() {
    
		return provider;
        
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
}
