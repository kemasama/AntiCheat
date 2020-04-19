package kemq.cheat.util;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class UserData {

	private static HashMap<UUID, UserData> _D = new HashMap<>();
	public static UserData getData(UUID key) {
		return _D.containsKey(key) ? _D.get(key) : new UserData(key);
	}

	public static void clean(UUID key) {
		if (_D.containsKey(key)) {
			_D.remove(key);
		}


	}

	public static void clean() {
		_D.clear();
	}

	private UUID own;
	public UserData(UUID own) {
		this.own = own;
		_D.put(own, this);
	}

	public UUID getOwn() {
		return own;
	}

	public Player getPlayer() {
		return Bukkit.getPlayer(own);
	}

	private Vector vector;
	public void reduceV(Vector v) {
		vector = v;
	}

	public Vector getVector() {
		return vector;
	}

}
