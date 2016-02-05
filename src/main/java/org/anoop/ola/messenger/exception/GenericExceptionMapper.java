package org.anoop.ola.messenger.exception;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.anoop.ola.messenger.model.ErrorMessage;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable>{
	 @Context
	 private HttpHeaders headers;
	 
	@Override
	public Response toResponse(Throwable exc) {
		ErrorMessage errorMessage = new ErrorMessage(exc.getMessage(), 500, "http://google.com/");
		return Response.status(Status.INTERNAL_SERVER_ERROR)
				.entity(errorMessage)
				.type(headers.getMediaType())
				.build();
	}

}
