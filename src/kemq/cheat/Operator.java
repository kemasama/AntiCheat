package kemq.cheat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import kemq.cheat.nums.UserList;

public class Operator implements UserList {

	@Override
	public void sendMessage(String msg) {
		sendMessage("wc.notify", msg);
	}

	@Override
	public void sendMessage(String perm, String... msg) {
		for (Player pl : Bukkit.getOnlinePlayers()) {
			if (pl.hasPermission(perm)) {
				pl.sendMessage(msg);
			}
		}
	}


	@Override
	public void addPlayer(Player pl) {
		pl.setOp(true);
	}

	@Override
	public void removePlayer(Player pl) {
		pl.setOp(false);
	}

	@Override
	public List<Player> getPlayer() {
		return new ArrayList<Player> (
			Bukkit.getOnlinePlayers().stream()
			.filter(o -> o.isOp()).collect(Collectors.toList())
			);
	}

	@Override
	public boolean hasPlayer(Player pl) {
		return pl.isOp();
	}

}
