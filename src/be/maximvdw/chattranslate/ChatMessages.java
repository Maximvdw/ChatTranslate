package be.maximvdw.chattranslate;

import java.util.LinkedList;

import org.bukkit.entity.Player;

import be.maximvdw.chattranslate.utils.GTransUtil;

public class ChatMessages {
	static LinkedList<Player> players = new LinkedList<Player>();
	static LinkedList<LinkedList<Integer>> messageIDs = new LinkedList<LinkedList<Integer>>();
	static LinkedList<Message> messages = new LinkedList<Message>();

	static int lastID = 0;
	static int keep = 1;

	/**
	 * Delete all messages from a player
	 * @param player Minecraft Player
	 */
	public static void deletePlayer(Player player) {
		if (players.contains(player)) {
			int idx = players.indexOf(player);
			players.remove(idx);
			LinkedList<Integer> ids = messageIDs.get(idx);
			for (Message msg : messages) {
				if (ids.contains(msg.id)) {
					messages.remove(msg); // Remove old message
				}
			}
			messageIDs.remove(idx);
		}
	}

	/**
	 * Add a message to the list
	 * 
	 * @param player
	 *            Minecraft Player
	 * @param message
	 *            Chat Message
	 */
	public static void addMessage(Player player, String message) {
		if (players.contains(player)) {
			int idx = players.indexOf(player);
			LinkedList<Integer> ids = messageIDs.get(idx);
			int oldID = ids.get(0);
			ids.remove(0); // Remove first id
			int newID = lastID;
			ids.add(newID); // Add last id
			lastID++; // Count up the last id
			for (Message msg : messages) {
				if (msg.id == oldID) {
					messages.remove(msg); // Remove old message
					break;
				}
			}
			Message msg = new Message(newID, message);
			messages.add(msg); // Add new message
		} else {
			// Player's first message
			players.add(player);
			LinkedList<Integer> ids = new LinkedList<Integer>();
			for (int i = 0; i < keep; i++) {
				ids.add(-1);
			}
			messageIDs.add(ids); // Save empty message ids
		}
	}

	/**
	 * Get the last message of a specific player
	 * 
	 * @param player
	 *            Minecraft Player
	 * @return Message
	 */
	public static String getLastMessage(Player player) {
		if (players.contains(player)) {
			int idx = players.indexOf(player);
			LinkedList<Integer> ids = messageIDs.get(idx);
			int id = ids.get(keep - 1);
			if (id != -1) {
				for (Message msg : messages) {
					if (msg.id == id) {
						return msg.message;
					}
				}
			}
			return ""; // Error, message id not found
		} else {
			// Player is not yet added
			return "";
		}
	}

	/**
	 * Translate a specific message
	 * 
	 * @param message
	 *            Message to translate
	 * @return Translated message
	 */
	public static String translateMessage(String message, String language) {
		return GTransUtil.getTranslation(message, language);
	}
}

/**
 * Chat message
 * 
 * @author Maxim Van de Wynckel
 */
class Message {
	int id = 0;
	String message = "";

	/**
	 * Create a new message
	 * 
	 * @param id
	 *            Message ID
	 * @param message
	 *            Message
	 */
	public Message(int id, String message) {
		this.id = id;
		this.message = message;
	}
}
