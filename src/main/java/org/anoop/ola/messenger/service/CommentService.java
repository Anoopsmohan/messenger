package org.anoop.ola.messenger.service;

import java.util.Date;
import java.util.List;

import org.anoop.ola.messenger.dto.CommentDTO;
import org.anoop.ola.messenger.dto.CommentDTOImpl;
import org.anoop.ola.messenger.model.Comment;

public class CommentService {

	public List<Comment> getAllComment(long messageId){
		CommentDTO commentDTO = new CommentDTOImpl();
		return commentDTO.getAllComments(messageId);
	}
	
	public Comment addComment(long messageId, Comment comment) {
		CommentDTO commentDTO = new CommentDTOImpl();
		comment.setCreated(new Date());
		return commentDTO.insertComment(messageId, comment);
	}
	
	public Comment updateComment(long messageId, Comment comment, long commentId) {
		CommentDTO commentDTO = new CommentDTOImpl();
		comment.setCreated(new Date());
		return commentDTO.updateComment(messageId, comment, commentId);
	}
	
	public Comment getComment(long messageId, long commentId) {
		CommentDTO commentDTO = new CommentDTOImpl();
		return commentDTO.getComment(messageId, commentId);
	}
	
	public void removeComment(long messageId, long commentId){
		CommentDTO commentDTO = new CommentDTOImpl();
		commentDTO.removeComment(messageId, commentId);
	}
}
