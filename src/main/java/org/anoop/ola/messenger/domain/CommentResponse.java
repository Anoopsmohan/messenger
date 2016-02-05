package org.anoop.ola.messenger.domain;

import java.util.Date;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.anoop.ola.messenger.model.Message;

public class CommentResponse {
	private long id;
	private String comment;
	private Date created;
	private String author;
	private Message message;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
}
