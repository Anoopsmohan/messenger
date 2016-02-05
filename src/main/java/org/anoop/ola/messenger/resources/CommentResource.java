package org.anoop.ola.messenger.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.anoop.ola.messenger.domain.CommentResponse;
import org.anoop.ola.messenger.model.Comment;
import org.anoop.ola.messenger.service.CommentService;

@Path("/")
public class CommentResource {

	CommentService commentService = new CommentService();
	
	public List<CommentResponse> getCommentResponse(List<Comment> comments){
		List<CommentResponse> commentResponse = new ArrayList<CommentResponse>();
		
		for (Comment comment : comments){
			CommentResponse cmtres = new CommentResponse();
			cmtres.setAuthor(comment.getAuthor());
			cmtres.setCreated(comment.getCreated());
			cmtres.setId(comment.getId());
			cmtres.setComment(comment.getComment());
			comment.getMessage().setComment(null);
			cmtres.setMessage(comment.getMessage());
			commentResponse.add(cmtres);
		}
		return commentResponse;
	}
	
	@GET
	public List<CommentResponse> getAllComments(@PathParam("messageId") long messageId){
		return getCommentResponse(commentService.getAllComment(messageId));
	}
	
	@POST
	public Comment addComment(@PathParam("messageId") long messageId, Comment comment){
		return commentService.addComment(messageId, comment);
	}
	
	@PUT
	@Path("/{commentId}")
	public Comment UpdateComment(@PathParam("messageId") long messageId,
								@PathParam("commentId") long commentId,
								Comment comment){
		comment.setId(commentId);
		return commentService.updateComment(messageId, comment, commentId);
	}
	
	@DELETE
	@Path("/{commentId}")
	public void deleteComment(@PathParam("messageId") long messageId,
							@PathParam("commentId") long commentId) {
		commentService.removeComment(messageId, commentId);
	}
	
	@GET
	@Path("/{commentId}")
	public Comment getComment(@PathParam("commentId") long commentId,
						@PathParam("messageId") long messageId){
		return commentService.getComment(messageId, commentId);
	}
}
