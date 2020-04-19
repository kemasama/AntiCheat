package kemq.cheat.nums;

import java.util.List;

import org.bukkit.entity.Player;

public interface UserList {
	public void sendMessage(String msg);
	public void sendMessage(String perm, String... msg);
	public void addPlayer(Player pl);
	public void removePlayer(Player pl);
	public List<Player> getPlayer();
	public boolean hasPlayer(Player pl);
}
