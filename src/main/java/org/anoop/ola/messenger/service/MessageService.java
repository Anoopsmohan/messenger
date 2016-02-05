package org.anoop.ola.messenger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.anoop.ola.messenger.dto.MessageDTO;
import org.anoop.ola.messenger.dto.MessageDTOImpl;
import org.anoop.ola.messenger.exception.DataNotFoundException;
import org.anoop.ola.messenger.model.Message;

import javassist.bytecode.stackmap.TypeData.ClassName;

public class MessageService {
	
	private static final Logger log = Logger.getLogger(ClassName.class.getName());
	
	/*public List<Message> getAllMessages(){
		Message m1 = new Message(1L, "Hello World!", "Anoop");
		Message m2 = new Message(2L, "Hello Jersey", "Anoo");	
		List<Message> list = new ArrayList<>();
		
		list.add(m1);
		list.add(m2);
		return list;	
	}*/
	
	public List<Message> getAllMessages(){
		MessageDTO messageDTO = new MessageDTOImpl();
		return messageDTO.getAllMessages();
	}
	
	public Message addMessage(Message msg) {
		MessageDTO messageDTO = new MessageDTOImpl();
		msg.setCreated(new Date());
		return messageDTO.insertMessage(msg);
	}
	
	public Message updateMessage(Message msg) {
		MessageDTO messageDTO = new MessageDTOImpl();
		return messageDTO.updateMessage(msg);
	}
	
	public Message getMessage(long id) {
		MessageDTO messageDTO = new MessageDTOImpl();
		Message message = messageDTO.getMessage(id);
		
		if (message == null){
			throw new DataNotFoundException("ID does not exist!!");
		}
		return message;
	}
	
	public void removeMessage(long id){
		MessageDTO messageDTO = new MessageDTOImpl();
		messageDTO.removeMessage(id);
	}
	
	public List<Message> getAllMessagesForYear(int year){
		MessageDTO messageDTO = new MessageDTOImpl();
		List<Message> messages = messageDTO.getAllMessages();
		List<Message> messagesForYear = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		for (Message message : messages){
			cal.setTime(message.getCreated());
			if(cal.get(Calendar.YEAR) == year) {
				messagesForYear.add(message);
			}
		}
		return messagesForYear;
	}
	
	public List<Message> getAllMessagesPaginates(int start, int size){
		MessageDTO messageDTO = new MessageDTOImpl();
		List<Message> messages = messageDTO.getAllMessages();
		ArrayList<Message> list = new ArrayList<Message>(messages);
		if (start + size > list.size()) return new ArrayList<Message>();
		return list.subList(start, size);
	}
}
