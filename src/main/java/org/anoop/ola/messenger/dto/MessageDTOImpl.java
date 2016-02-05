package org.anoop.ola.messenger.dto;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.anoop.ola.messenger.model.Comment;
import org.anoop.ola.messenger.model.Message;
import org.anoop.ola.messenger.utils.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javassist.bytecode.stackmap.TypeData.ClassName;

public class MessageDTOImpl implements MessageDTO{
	private static final Logger log = Logger.getLogger(ClassName.class.getName());

	@Override
	public Message getMessage(long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Message message = null;
		try{
			message = (Message) session.get(Message.class, id);
			CommentDTO commentDTO = new CommentDTOImpl();
			List<Comment> comments = commentDTO.getAllComments(id);
			for (Comment comment : comments)
				comment.setMessage(null);
			message.setComment(comments);
		}catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			session.close();
		}
		return message;
	}

	@Override
	public Message insertMessage(Message msg) {
		long retval = 0;
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Transaction tx = null;
		try{
			tx = (Transaction) session.beginTransaction();
			retval = (long) session.save(msg);
			msg.setId(retval);
			tx.commit();
		} catch(Exception e){
			if (tx != null){
				tx.rollback();
			}
			e.printStackTrace();
		} finally{
			session.close();
		}
		return msg;
	}

	@Override
	public List<Message> getAllMessages() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("from Message");
		@SuppressWarnings("unchecked")
		List<Message> msgs = query.list();
		
		session.close();
		return msgs;
	}

	@Override
	public Message updateMessage(Message msg) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction txn = null;
		try {
			txn = (Transaction) session.beginTransaction();
			session.update(msg);
			txn.commit();
		} catch (Exception ex){
			if (txn != null){
				txn.rollback();
			}
			ex.printStackTrace();
		} finally {
			session.close();
		}
		return msg;
	}

	@Override
	public void removeMessage(long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction txn = null;
		try{
			txn = session.beginTransaction();
			Message msg = (Message) session.get(Message.class, id);
			session.delete(msg);
			txn.commit();
		} catch(Exception ex) {
			if (txn != null){
				txn.rollback();
			}
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}

	@Override
	public List<Message> getAllMessagesForYear(int year) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction txn = null;
		List<Message> messages = null;
		try{
			txn = session.beginTransaction();
			Query query = session.createQuery("from Message");
			messages = query.list();
			for(Message message : messages){
				log.info("Messages :::" + message);
			}
			
		}catch (Exception exc){
			exc.printStackTrace();
		} finally{
			session.close();
		}
		return messages;
	}
}
