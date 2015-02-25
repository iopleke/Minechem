package minechem.compatibility.IGW;

import igwmod.api.WikiRegistry;
import igwmod.gui.tabs.BaseWikiTab;
import tv.twitch.broadcast.GameInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.util.Session;
import net.minecraft.world.WorldSettings.GameType;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minechem.compatibility.CompatBase;
import minechem.compatibility.IGW.tabs.MachinesTab;
import minechem.compatibility.IGW.tabs.MainMinechemTab;

public class IGWCompat extends CompatBase {

	public static final BaseWikiTab main = new MainMinechemTab();
	public static final BaseWikiTab machines = new MachinesTab();
	@Override
	protected void init() {
		if(FMLCommonHandler.instance().getSide().isClient() == true) {
			FMLInterModComms.sendMessage("IGWMod", "minechem.compatibility.IGW.IGWCompat", "igwinit");
		}
	}
	
	public static void igwinit() {
		WikiRegistry.registerWikiTab(main);
		WikiRegistry.registerWikiTab(machines);
	}

}
