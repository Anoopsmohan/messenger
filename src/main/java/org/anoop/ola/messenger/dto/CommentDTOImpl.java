package org.anoop.ola.messenger.dto;

import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.anoop.ola.messenger.model.Comment;
import org.anoop.ola.messenger.model.ErrorMessage;
import org.anoop.ola.messenger.model.Message;
import org.anoop.ola.messenger.utils.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javassist.bytecode.stackmap.TypeData.ClassName;

public class CommentDTOImpl implements CommentDTO{
	private static final Logger log = Logger.getLogger(ClassName.class.getName());

	@Override
	public Comment getComment(long messageId, long commentId) {
		
		ErrorMessage errorMessage = new ErrorMessage("Not Found", 404, "http://google.com/");
		Response res = Response.status(Status.NOT_FOUND)
							.entity(errorMessage)
							.build();
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("from Comment where id=:commentId and message.id=:messageId");
		query.setParameter("commentId", commentId);
		query.setParameter("messageId", messageId);
		
		List records = query.list();
		Comment comment = null;
		Integer count = records.size();
		log.info("FETCHED RAW COUNT::::::::: " + count);

		if (count > 0) {
			comment = (Comment) query.uniqueResult();
			comment.getMessage().setComment(null);
		} else{
			session.close();
			throw new WebApplicationException(res);
			//throw new NotFoundException(res);
		}
		/*MessageDTO messageDTO = new MessageDTOImpl();
		Message message = messageDTO.getMessage(messageId);
		
		Comment comment = (Comment) session.get(Comment.class, commentId);
		if (message == null || comment == null){
			throw new WebApplicationException(res);
		}
		comment.getMessage().setComment(null);*/
		session.close();
		return comment;
	}

	@Override
	public Comment insertComment(long messageId, Comment comment) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction txn = null;
		try {
			txn = session.beginTransaction();
			Message message = (Message) session.get(Message.class, messageId);
			comment.setMessage(message);
			session.save(comment);
			txn.commit();
		} catch(Exception exc) {
			if(txn != null){
				txn.rollback();
			}
			exc.printStackTrace();
		}finally{
			session.close();
		}
		comment.setMessage(null);
		return comment;
	}

	@Override
	public List<Comment> getAllComments(long messageId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("from Comment com where com.message.id=:messageId");
		query.setParameter("messageId", messageId);
		List<Comment> comments = query.list();
		session.close();
 		return comments;
	}

	@Override
	public Comment updateComment(long messageId, Comment comment, long commentId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction txn = null;
		Comment com = null;
		try{
			txn = session.beginTransaction();
			Message message = (Message) session.get(Message.class, messageId);
			comment.setMessage(message);
			session.update(comment);
			txn.commit();
			
		} catch (Exception exc) {
			if (txn != null){
				txn.rollback();
			}
			exc.printStackTrace();
		} finally{
			session.close();
		}
		comment.getMessage().setComment(null);
		return comment;
	}

	@Override
	public void removeComment(long messageId, long commentId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Transaction txn = null;
		try{
			txn = session.beginTransaction();
			Comment comment = (Comment) session.get(Comment.class, commentId);
			session.delete(comment);
			txn.commit();
		} catch(Exception exc) {
			if(txn != null) {
				txn.rollback();
			}
			exc.printStackTrace();
		} finally{
			session.close();
		}
	}
}
