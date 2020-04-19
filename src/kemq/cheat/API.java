package kemq.cheat;

import org.bukkit.plugin.java.JavaPlugin;

public class API {
	public static JavaPlugin getAPI() {
		return AntiCheat.getInstance();
	}

	public static Operator getOperator() {
		return AntiCheat.getOperator();
	}
}
