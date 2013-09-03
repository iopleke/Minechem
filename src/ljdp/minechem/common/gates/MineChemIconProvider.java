package ljdp.minechem.common.gates;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import buildcraft.api.core.IIconProvider;

public class MineChemIconProvider implements IIconProvider{

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int iconIndex) {
		
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		
	}

}
