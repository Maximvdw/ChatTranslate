package be.maximvdw.chattranslate;


import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import be.maximvdw.chattranslate.listeners.*;

public class ChatTranslate extends JavaPlugin {

	static ChatTranslate plugin;
	
	public void onEnable() {
		plugin = this;
		
		/* Register Listeners */
		PluginManager pm = this.getServer().getPluginManager();
		PlayerListener listenerPlayer = new PlayerListener();
		pm.registerEvents(listenerPlayer, this);
		
		/* Register Commands */
		getCommand("transprev").setExecutor(new CommandListener());
	}
	
	public static ChatTranslate getInstance(){
		return plugin;
	}
}
