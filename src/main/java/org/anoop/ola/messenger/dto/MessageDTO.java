package org.anoop.ola.messenger.dto;

import java.util.List;

import org.anoop.ola.messenger.model.Message;

public interface MessageDTO {
	public Message getMessage(long id);
	public Message insertMessage(Message msg);
	public List<Message> getAllMessages();
	public Message updateMessage(Message msg);
	public void removeMessage(long id);
	public List<Message> getAllMessagesForYear(int year);
}
