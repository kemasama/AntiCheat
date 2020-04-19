package kemq.cheat;

import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;

import kemq.cheat.listener.CheatListener;
import kemq.cheat.listener.JoinListener;
import kemq.cheat.util.UserData;

public class AntiCheat extends JavaPlugin {

	public static AntiCheat getInstance() {
		return AntiCheat.getPlugin(AntiCheat.class);
	}

	private Operator op;
	public static Operator getOperator() {
		return getInstance().op;
	}

	@Override
	public void onDisable() {
		super.onDisable();

		UserData.clean();

		op.sendMessage("§c[WD] §aEnable WatchCat");
		op = null;
	}

	@Override
	public void onEnable() {
		super.onEnable();

		addPerm("wc.bypass", PermissionDefault.FALSE);
		addPerm("wc.notify", PermissionDefault.OP);
		addPerm("wc.notify.join", PermissionDefault.FALSE);
		addPerm("wc.notify.login", PermissionDefault.FALSE);

		op = new Operator();

		Bukkit.getPluginManager().registerEvents(new CheatListener(), this);
		Bukkit.getPluginManager().registerEvents(new JoinListener(), this);

		op.sendMessage("§c[WD] §aEnable WatchCat");
	}

	public static void addPerm(String perm, PermissionDefault defaultPerm) {
		if (Bukkit.getPluginManager().getPermission(perm) == null) {
			Permission permission = new Permission(perm);
			permission.setDefault(defaultPerm);
			Bukkit.getPluginManager().addPermission(permission);
		}
	}

}
