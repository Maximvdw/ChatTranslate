package be.maximvdw.chattranslate.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import be.maximvdw.chattranslate.ChatMessages;

public class PlayerListener implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		/* Extract event data */
		Player player = event.getPlayer();
		String message = event.getMessage();
		ChatMessages.addMessage(player, message);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerQuit(PlayerQuitEvent event) {
		/* Extract event data */
		Player player = event.getPlayer();
		ChatMessages.deletePlayer(player);
	}
}
