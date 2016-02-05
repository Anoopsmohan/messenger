package org.anoop.ola.messenger.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.print.attribute.standard.Media;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.anoop.ola.messenger.domain.MessageResponse;
import org.anoop.ola.messenger.dto.CommentDTO;
import org.anoop.ola.messenger.dto.CommentDTOImpl;
import org.anoop.ola.messenger.model.Comment;
import org.anoop.ola.messenger.model.Message;
import org.anoop.ola.messenger.resources.beans.MessageFilterBean;
import org.anoop.ola.messenger.service.MessageService;

import javassist.bytecode.stackmap.TypeData.ClassName;

@Path("/messages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MessageResource {
	
	MessageService messageService = new MessageService();
	private static final Logger log = Logger.getLogger(ClassName.class.getName());
	private Message message;
	
	public List<MessageResponse> getMessageResponse(List<Message> messages){
		List<MessageResponse> messageResponse = new ArrayList<MessageResponse>();
		
		CommentDTO commentDTO = new CommentDTOImpl();
		for (Message msg : messages){
			MessageResponse msgres = new MessageResponse();
			msgres.setId(msg.getId());
			msgres.setAuthor(msg.getAuthor());
			msgres.setCreated(msg.getCreated());
			msgres.setMessage(msg.getMessage());
			List<Comment> comments = commentDTO.getAllComments(msg.getId());
			for (Comment comment : comments)
				comment.setMessage(null);
			msgres.setComment(comments);
			messageResponse.add(msgres);
		}
		return messageResponse;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public  List<MessageResponse> getJsonMessages(@BeanParam MessageFilterBean filterBean){
		System.out.println("*****Json function called******************");
		if (filterBean.getYear() > 0){
			return getMessageResponse(messageService.getAllMessagesForYear(filterBean.getYear()));
		}
		
		if (filterBean.getStart() > 0 && filterBean.getSize() >= 0){
			return getMessageResponse(messageService.getAllMessagesPaginates(filterBean.getStart(), filterBean.getSize()));
		}
		return getMessageResponse(messageService.getAllMessages());
	}
	
	@GET
	@Produces(MediaType.TEXT_XML)
	public  List<MessageResponse> getXmlMessages(@BeanParam MessageFilterBean filterBean){
		System.out.println("*****XML function called******************");
		if (filterBean.getYear() > 0){
			return getMessageResponse(messageService.getAllMessagesForYear(filterBean.getYear()));
		}
		
		if (filterBean.getStart() > 0 && filterBean.getSize() >= 0){
			return getMessageResponse(messageService.getAllMessagesPaginates(filterBean.getStart(), filterBean.getSize()));
		}
		return getMessageResponse(messageService.getAllMessages());
	}
	
	
	@GET
	@Path("{messageId}")
	public Message getMessage(@PathParam("messageId") long id, @Context UriInfo uriInfo){
		 Message message = messageService.getMessage(id);
		 
		 message.addLink(getUriForSelf(uriInfo, message), "self");
		 message.addLink(getUriForProfile(uriInfo, message), "profile");
		 message.addLink(getUriForComment(uriInfo, message), "comment");
		 return message;
	}

	private String getUriForComment(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder()
				.path(MessageResource.class)
				 .path(MessageResource.class, "getComentResource")
				 .path(CommentResource.class)
				 .resolveTemplate("messageId", message.getId())
				 .build()
				 .toString();
		return uri;
	}

	private String getUriForProfile(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder()
				 .path(ProfileResource.class)
				 .path(message.getAuthor())
				 .build()
				 .toString();
		return uri;
	}

	private String getUriForSelf(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder()
				 .path(MessageResource.class)
				 .path(Long.toString(message.getId()))
				 .build()
				 .toString();
		return uri;
	}
	
	@POST
	public Response createMessage(Message msg, @Context UriInfo uriInfo){
		Message message = messageService.addMessage(msg);
		String msgId = String.valueOf(message.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(msgId).build();

//		return Response.created(uri)
//				.entity(message)
//				.build();
		
		return Response.status(Status.CREATED)
				.entity(message)
				.build();
	}
	
	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") long id, Message msg){
		msg.setId(id);
		return messageService.updateMessage(msg);
	}
	
	@DELETE
	@Path("{messageId}")
	public void deleteMessage(@PathParam("messageId") long id){
		messageService.removeMessage(id);
	}
	
	@Path("/{messageId}/comments")
	public CommentResource getComentResource(){
		return new CommentResource();
	}
}
