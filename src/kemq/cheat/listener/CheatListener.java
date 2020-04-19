package kemq.cheat.listener;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.event.server.ServerListPingEvent;

import kemq.cheat.API;
import kemq.cheat.nums.HackType;
import kemq.cheat.util.UserData;

public class CheatListener implements Listener {

	public static boolean debug = false;
	public static double MAX_REACH = 25.0;
	public static double MAX_BLOCK_REACH = 42.25;

	public CheatListener() {
	}

	public void onCheat(Player pl, HackType type) {
		if (pl.hasPermission("wc.bypass")) {
			return;
		}

		API.getOperator().sendMessage("§c[WC] §eDetect " + type.name() + " hack : §b" + pl.getName());
		//API.getOperator().sendMessage("§c[WC] §eIP  " + pl.getAddress().getAddress().getHostAddress() + "§7/" + SrvUtil.pingFormat(SrvUtil.getPing(pl)) + "ms");
	}

	@EventHandler
	public void onMove(PlayerMoveEvent event) {

		if (event.getPlayer().hasPermission("wc.bypass")) {
			return;
		}

		HackType type = MoveListener.check(event);
		if (!type.equals(HackType.NONE)) {
			onCheat(event.getPlayer(), type);
			event.getPlayer().teleport(event.getPlayer().getLocation());
		}
	}

	@EventHandler
	public void onKick(PlayerKickEvent event) {
		if (event.getReason().toLowerCase().contains("fly")) {
			onCheat(event.getPlayer(), HackType.FLIGHT_KICK);
			event.getPlayer().kickPlayer("Err: Movement");
			if (event.getPlayer().hasPermission("wc.bypass")) {
				event.setCancelled(true);
			}
			if (debug) {
				API.getOperator().sendMessage("§c[WCD] §b" + event.getPlayer().getName() + " §eKicked because Flight!");
			}
		}
	}

	@EventHandler
	public void onCheckDos(ServerListPingEvent event) {
		String format = "Ping Request From: " + event.getAddress().getHostAddress();
		System.out.println(format);
	}

	@EventHandler
	public void onKnockBack(PlayerVelocityEvent event) {
		UserData.getData(event.getPlayer().getUniqueId()).reduceV(event.getVelocity());
	}

	@EventHandler
	public void onReachCheck(PlayerInteractEvent event) {
		/*
		if (event.getClickedBlock() != null) {
			Player p = event.getPlayer();
			Location loc = event.getClickedBlock().getLocation();
			Location plc = p.getLocation();

			double distance = loc.distanceSquared(plc);
			if (distance >= MAX_BLOCK_REACH) {
				onCheat(p, HackType.BLOCK_REACH);
				event.setCancelled(true);

				if (debug) {
					API.getOperator().sendMessage("§c[WCD] §b" + p.getName() + " §ewrong block reach! (" + distance + ")");
				}
			}
		}
		*/
	}

	@EventHandler
	public void onReachCheck(EntityDamageByEntityEvent event) {
		if (!event.isCancelled()
				&& event.getDamager() instanceof Player
				&& event.getEntity() instanceof LivingEntity) {
			Player p = (Player) event.getDamager();
			LivingEntity live = (LivingEntity) event.getEntity();

			Location loc = p.getLocation();
			Location lcc = live.getLocation();

			double distance = loc.distanceSquared(lcc);

			// 25.0 => 5.0
			// MAX REACH => 5.0
			// DEFAULT => 4.5
			if (distance >= MAX_REACH) {
				onCheat(p, HackType.REACH);
				event.setCancelled(true);

				if (debug) {
					API.getOperator().sendMessage("§c[WCD] §b" + p.getName() + " §ewrong reach! (" + distance + ")");
				}

			}

		}
	}
}
