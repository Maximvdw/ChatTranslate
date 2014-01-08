package be.maximvdw.chattranslate.listeners;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import be.maximvdw.chattranslate.ChatMessages;
import be.maximvdw.chattranslate.ChatTranslate;

public class CommandListener implements CommandExecutor {
	/**
	 * Perform a command
	 * 
	 * @param sender
	 *            The sender
	 * @param comamnd
	 *            The command
	 * @param label
	 *            The command string
	 * @param args
	 *            Arguments
	 */
	public boolean onCommand(CommandSender sender, Command command, String cmd,
			String[] args) {
		Player player = null;

		// Check if it is a real player
		if (sender instanceof Player) {
			// Add playerID to player
			player = (Player) sender;
		}

		// Check if the player entered a valid command
		if (cmd.equalsIgnoreCase("transprev")) {
			/* Translate a previous message */
			if (args.length == 0) {
				// Translate the last message in chat
				int msgID = ChatMessages.getLastMessage();
				String lastMessage = ChatMessages.getMessage(msgID);
				Player targetPlayer = ChatMessages.getPlayer(msgID);
				String translation = ChatMessages.translateMessage(lastMessage,
						"en");
				player.sendMessage("§e " + targetPlayer.getName()
						+ " (Translation):§b " + translation);
			} else {
				// Check if valid player
				String playerName = args[0];
				Player targetPlayer = ChatTranslate.getInstance().getServer()
						.getPlayer(playerName);
				if (targetPlayer != null) {
					int msgID = ChatMessages.getLastMessage(targetPlayer);
					String lastMessage = ChatMessages.getMessage(msgID);
					String translation = ChatMessages.translateMessage(
							lastMessage, "en");
					player.sendMessage("§e" + targetPlayer.getName()
							+ " (Translation):§b " + translation);
				}
			}
		} else if (cmd.equalsIgnoreCase("trans")) {
			if (args.length >= 1) {
				String langTo = "";
				if (args[0].length() == 2) {
					langTo = args[0];
				}
				String message = args[1];
				String commandStr = "";
				int pos = 2;
				if (message.startsWith("/")) {
					commandStr = message;
					if (args.length >= 2) {
						message = args[2];
						pos++;
					}
				}
				for (int i = pos; i < args.length; i++) {
					message += " " + args[i];
				}
				String translation = ChatMessages.translateMessage(message,
						langTo);
				player.chat(commandStr + " " + translation);
			}
		}

		return true; // Return true
	}
}
