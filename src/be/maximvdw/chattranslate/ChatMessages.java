package be.maximvdw.chattranslate;

import java.io.UnsupportedEncodingException;
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
	 * 
	 * @param player
	 *            Minecraft Player
	 */
	public static void deletePlayer(Player player) {
		if (players.contains(player)) {
			int idx = players.indexOf(player);
			players.remove(idx);
			LinkedList<Integer> ids = messageIDs.get(idx);
			LinkedList<Message> msgs = new LinkedList<Message>(messages);
			for (Message msg : msgs) {
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
			ChatTranslate.getInstance().getLogger().info("Total stored messages: " + messages.size());
			ChatTranslate.getInstance().getLogger().info("Messages from total players: " + players.size());
			ChatTranslate.getInstance().getLogger().info("Last message ID: " + lastID);
			int idx = players.indexOf(player);
			LinkedList<Integer> ids = messageIDs.get(idx);
			int oldID = ids.get(0);
			ids.remove(0); // Remove first id
			int newID = lastID;
			ids.add(newID); // Add last id
			lastID++; // Count up the last id
			LinkedList<Message> msgs = new LinkedList<Message>(messages);
			for (Message msg : msgs) {
				if (msg.id == oldID) {
					messages.remove(msg); // Remove old message
					break;
				}
			}
			Message msg = new Message(newID, player, message);
			messages.add(msg); // Add new message
		} else {
			// Player's first message
			players.add(player);
			LinkedList<Integer> ids = new LinkedList<Integer>();
			for (int i = 0; i < keep; i++) {
				ids.add(-1);
			}
			messageIDs.add(ids); // Save empty message ids
			addMessage(player, message); // Add the message (first message)
		}
	}

	/**
	 * Get the last message of a specific player
	 * 
	 * @param player
	 *            Minecraft Player
	 * @return Message id
	 */
	public static int getLastMessage(Player player) {
		if (players.contains(player)) {
			int idx = players.indexOf(player);
			LinkedList<Integer> ids = messageIDs.get(idx);
			int id = ids.get(keep - 1);
			return id;
		} else {
			// Player is not yet added
			return -1;
		}
	}

	/**
	 * Get the last chat message in the server
	 * 
	 * @return Message id
	 */
	public static int getLastMessage() {
		return lastID - 1;
	}

	/**
	 * Get the player from the id
	 * 
	 * @param messageid
	 *            Message id
	 * @return Player
	 */
	public static Player getPlayer(int messageid) {
		LinkedList<Message> msgs = new LinkedList<Message>(messages);
		for (Message msg : msgs) {
			if (msg.id == messageid) {
				return msg.player;
			}
		}
		return null;
	}

	/**
	 * Get the message from the id
	 * 
	 * @param messageid
	 *            Message id
	 * @return Message
	 */
	public static String getMessage(int messageid) {
		LinkedList<Message> msgs = new LinkedList<Message>(messages);
		for (Message msg : msgs) {
			if (msg.id == messageid) {
				return msg.message;
			}
		}
		return "";
	}

	/**
	 * Translate a specific message
	 * 
	 * @param message
	 *            Message to translate
	 * @return Translated message
	 */
	public static String translateMessage(String message, String language) {
		try {
			return GTransUtil.getTranslation(message, language);
		} catch (UnsupportedEncodingException e) {
			return message; // Unsupported
		}
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
	Player player = null;

	/**
	 * Create a new message
	 * 
	 * @param id
	 *            Message ID
	 * @param player
	 *            Minecraft Player
	 * @param message
	 *            Message
	 */
	public Message(int id, Player player, String message) {
		this.id = id;
		this.message = message;
		this.player = player;
	}
}
