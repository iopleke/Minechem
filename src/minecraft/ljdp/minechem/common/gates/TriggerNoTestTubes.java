package ljdp.minechem.common.gates;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import buildcraft.api.core.IIconProvider;
import buildcraft.api.gates.ITriggerParameter;

public class TriggerNoTestTubes extends MinechemTrigger {
    public TriggerNoTestTubes(int id) {
        super(id, "No Test Tubes");
    }

    @Override
    public boolean isTriggerActive(ForgeDirection fd, TileEntity tile, ITriggerParameter parameter) {
        if (!(tile instanceof IMinechemTriggerProvider))
            return false;
        return ((IMinechemTriggerProvider) tile).hasNoTestTubes();
    }
    @Override
    @SideOnly(Side.CLIENT)
    public IIconProvider getIconProvider() {
    
		return provider;
        
    }
}
