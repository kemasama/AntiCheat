package kemq.cheat.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class LocationHelper {
	public static Player getMostNear(Location from) {
		Player player = null;
		double diff = 0;

		for (Player pl : from.getWorld().getPlayers()) {
			if (player == null) {
				player = pl;
				diff = from.distanceSquared(pl.getLocation());
				continue;
			}


			double dis = from.distanceSquared(pl.getLocation());
			if (dis < diff) {
				player = pl;
				diff = dis;
			}
		}

		return player;
	}

	public static List<Player> getPlayersByLocation(Location from, double distance){
		List<Player> pl = new ArrayList<>();
		try {
			for (Player p : from.getWorld().getPlayers()) {
				if (from.distanceSquared(p.getLocation()) <= distance) {
					pl.add(p);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return pl;
	}

	public static List<LivingEntity> getEntityByLocation(Location from, double distance){
		List<LivingEntity> pl = new ArrayList<>();
		try {
			for (Entity l : from.getWorld().getEntities()) {
				if (l instanceof LivingEntity && from.distanceSquared(l.getLocation()) <= distance) {
					pl.add((LivingEntity) l);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return pl;
	}

	public static boolean equalsLocation(Location to, Location from) {
		return to.getWorld().getName().equals(from.getWorld().getName())
				&& to.getBlockX() == from.getBlockX()
				&& to.getBlockY() == from.getBlockY()
				&& to.getBlockZ() == from.getBlockZ();
	}

	public static Location lookAt(Location loc, Location look) {
		loc = loc.clone();
		double dx = look.getX() - loc.getX();
		double dy = look.getY() - loc.getY();
		double dz = look.getZ() - loc.getZ();

		if (dx != 0) {
			if (dx < 0) {
				loc.setYaw((float) (1.5 * Math.PI));
			}else {
				loc.setYaw((float) (0.5 * Math.PI));
			}
			loc.setYaw((float) loc.getYaw() - (float) Math.atan(dz / dx));
		}else if (dz < 0) {
			loc.setYaw((float) Math.PI);
		}

		double dxz = Math.sqrt(Math.pow(dx,  2) + Math.pow(dz, 2));

		loc.setPitch((float) -Math.atan(dy / dxz));

		loc.setYaw(-loc.getYaw() * 180f / (float) Math.PI);
		loc.setPitch(loc.getPitch() * 180f / (float) Math.PI);

		return loc;
	}

	public static void setLooking(Player p, Location target) {
		Vector direction = target.toVector().subtract(getVector(p)).normalize();
		double x = direction.getX(),
				y = direction.getY(),
				z = direction.getZ();

		Location changed = target.clone();
		changed.setYaw(180 - toDegree(Math.atan2(x, z)));
		changed.setPitch(90 - toDegree(Math.acos(y)));

		p.teleport(changed);
	}
	public static float toDegree(double angle) {
		return (float) Math.toDegrees(angle);
	}

	public static Vector getVector(Player p) {
		return p.getEyeLocation().toVector();
	}
}