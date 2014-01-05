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
            if (cmd.equalsIgnoreCase("transprev")){
            	/* Translate a previous message */
            	if (args.length == 0){
            		// Translate the last message in chat
            		String lastMessage = "";
            	}else{
            		// Check if valid player
            		String playerName = args[0];
            		Player targetPlayer = ChatTranslate.getInstance().getServer().getPlayer(playerName);
            		if (targetPlayer != null){
            			String lastMessage = ChatMessages.getLastMessage(targetPlayer);
            			String translation = ChatMessages.translateMessage(lastMessage, "en");
            			player.sendMessage("§e " + targetPlayer.getName() + " (Translation):§b " + translation);
            		}
            	}
            }
            
            return true; // Return true
    }
}
