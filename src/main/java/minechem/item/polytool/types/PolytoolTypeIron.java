package minechem.item.polytool.types;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import minechem.item.element.ElementEnum;
import minechem.item.polytool.PolytoolUpgradeType;
import minechem.utils.CoordTuple;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;

public class PolytoolTypeIron extends PolytoolUpgradeType
{
	private static Map<String,Boolean> ores = new LinkedHashMap<String,Boolean>();
	
	public static void getOres(){
		for (String ore:OreDictionary.getOreNames())
		{
			if (ore.regionMatches(0, "ore", 0, 3))
			{
				for (ItemStack stack:OreDictionary.getOres(ore))
				{
					ores.put(blockHash(stack),true);
				}
			}
		}
	}
	
	private static String blockHash(ItemStack stack)
	{
		return blockHash(Block.getBlockFromItem(stack.getItem()),stack.getItemDamage());
	}
	
	private static String blockHash(Block block, int meta)
	{
		return block.getUnlocalizedName()+"@"+meta;
	}
	
	public PolytoolTypeIron()
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
	public void onBlockDestroyed(ItemStack itemStack, World world, Block id, int x1, int y1, int z1, EntityLivingBase entityLiving)
	{
		if (!world.isRemote&&entityLiving instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)entityLiving;
			ArrayList<CoordTuple> queue = new ArrayList<CoordTuple>(100);
			int meta = world.getBlockMetadata(x1, y1, z1);
			if (ores.containsKey(blockHash(id,meta)))
			{
	//			System.out.println("woop");
	//			
	//		}
	//		if (id == Blocks.coal_ore || id == Blocks.diamond_ore || id == Blocks.emerald_ore || id == Blocks.gold_ore || id == Blocks.iron_ore || id == Blocks.lapis_ore || id == Blocks.quartz_ore
	//				|| id == Blocks.redstone_ore || OreDictionary.getOreName(OreDictionary.getOreID(itemStack.getDisplayName())).contains("ore"))
	//		{
	
				int toMine = (int) power;
				queue.add(new CoordTuple(x1, y1, z1));
				while (!queue.isEmpty())
				{
					CoordTuple coord = queue.remove(0);
					int x = coord.x;
					int y = coord.y;
					int z = coord.z;
					for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
					{
						if (world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ) == id && world.getBlockMetadata(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ)==meta)
						{
							
							breakExtraBlock(world,x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ,player,id,meta);
							queue.add(new CoordTuple(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ));
							toMine--;
							if (toMine <= 0)
							{
								return;
							}
						}
					}
				}
			}
		}
	}

	@Override
	public ElementEnum getElement()
	{

		return ElementEnum.Fe;
	}

	@Override
	public void onTick()
	{
	}

	@Override
	public String getDescription()
	{

		return "Mines deposits of ores";
	}

	protected void breakExtraBlock(World world, int x, int y, int z, EntityPlayer player, Block block, int meta) {
		if (player.capabilities.isCreativeMode) {
		    block.onBlockHarvested(world, x, y, z, meta, player);
		    world.setBlockToAir(x, y, z);
		    if (!world.isRemote) {
		        ((EntityPlayerMP)player).playerNetServerHandler.sendPacket(new S23PacketBlockChange(x, y, z, world));
		    }
		    return;
		}
		
		if (!world.isRemote) {
		    block.onBlockHarvested(world, x,y,z, meta, player);
		
		    if(block.removedByPlayer(world, player, x,y,z, true))
		    {
		        block.harvestBlock(world, player, x,y,z, meta);
		    }
		
		    EntityPlayerMP mpPlayer = (EntityPlayerMP) player;
		    mpPlayer.playerNetServerHandler.sendPacket(new S23PacketBlockChange(x, y, z, world));
		}
	}
	
}
