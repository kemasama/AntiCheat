package kemq.cheat.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerQuitEvent;

import kemq.cheat.API;
import kemq.cheat.util.UserData;

public class JoinListener implements Listener {

	@EventHandler
	public void onLogin(PlayerLoginEvent event) {
		Player pl = event.getPlayer();
		if (pl != null) {
			if (!event.getResult().equals(Result.ALLOWED)) {
				API.getOperator().sendMessage("wc.notify.login", "§c[WD] §eLogin try §a" + pl.getName() + "§e! But " + event.getResult().name() + " §e" + event.getRealAddress().getHostAddress() + " §7(" + event.getRealAddress().getHostName() + ")");
			}else {
				API.getOperator().sendMessage("wc.notify.login", "§c[WD] §eLogin success? §a" + pl.getName() + " §e! §e" + event.getRealAddress().getHostAddress() + " §7(" + event.getRealAddress().getHostName() + ")");
			}
		}else {
			API.getOperator().sendMessage("wc.notify.login", "§c[WD] §eLogin new player! §e" + event.getRealAddress().getHostAddress() + " §7(" + event.getRealAddress().getHostName() + ")");
		}
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player pl = event.getPlayer();

		API.getOperator().sendMessage("wc.notify.join", "§c[WD] §eJoin §a" + pl.getName() + " §e" + pl.getAddress().getAddress().getHostAddress() + " §7(" + pl.getAddress().getAddress().getHostName() + ")");
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player pl = event.getPlayer();

		UserData.clean(pl.getUniqueId());

		API.getOperator().sendMessage("wc.notify.join", "§c[WD] §eQuit §a" + pl.getName() + " §e" + pl.getAddress().getAddress().getHostAddress() + " §7(" + pl.getAddress().getAddress().getHostName() + ")");
	}
}
