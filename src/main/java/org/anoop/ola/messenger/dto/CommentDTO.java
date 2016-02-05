package org.anoop.ola.messenger.dto;

import java.util.List;

import org.anoop.ola.messenger.model.Comment;

public interface CommentDTO {
	
	public Comment getComment(long messageId, long commentId);
	public Comment insertComment(long messageId, Comment comment);
	public List<Comment> getAllComments(long messageId);
	public Comment updateComment(long messageId, Comment comment, long commentId);
	public void removeComment(long messageId, long commentId);
}
