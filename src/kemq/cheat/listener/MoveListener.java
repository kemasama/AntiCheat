package kemq.cheat.listener;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import kemq.cheat.API;
import kemq.cheat.nums.HackType;
import kemq.cheat.util.LocationHelper;
import kemq.cheat.util.Modifier;
import kemq.cheat.util.UserData;

public class MoveListener {
	public static double MAX_XZ_SPEED = 0.666;
	public static double LIMIT_AF = 1.5;
	//public static double BB_XZ = 1.1D;
	public static double BB_XZ = 0.777D;

	public static HackType check(PlayerMoveEvent event) {
		Player p = event.getPlayer();
		if (p.getGameMode().equals(GameMode.CREATIVE) || p.getAllowFlight()) {
			return HackType.NONE;
		}

		Location to = event.getTo();
		Location fm = event.getFrom();

		if (!to.getWorld().getName().equals(fm.getWorld().getName())) {
			return HackType.NONE;
		}

		if (LocationHelper.equalsLocation(to, fm)) {
			return HackType.NONE;
		}

		/*
		if (to.distanceSquared(fm) >= LIMIT_AF) {
			return HackType.MOVE;
		}
		*/

		if ((to.getY() - fm.getY()) > LIMIT_AF) {
			return HackType.HIGH_JUMP;
		}

		double xDiff = Math.abs(event.getTo().getX() - event.getFrom().getX());
		double zDiff = Math.abs(event.getTo().getZ() - event.getFrom().getZ());

		double mod = Modifier.getSpeed(p);

		double AA_XZ = BB_XZ + mod;

		UserData i = UserData.getData(p.getUniqueId());
		if (i.getVector() != null) {
			Vector kb = i.getVector();
			AA_XZ += kb.lengthSquared();
			(new BukkitRunnable() {
				@Override
				public void run() {
					i.reduceV(null);
				}
			}).runTaskLater(API.getAPI(), 10L);
		}

		if (xDiff > AA_XZ || zDiff > AA_XZ ) {

			return HackType.SPEED;
		}

		return HackType.NONE;
	}
}
