package org.anoop.ola.messenger.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import javax.xml.ws.spi.http.HttpHandler;

import org.glassfish.jersey.message.internal.HttpHeaderReader;

@Path("/injectdemo")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.TEXT_PLAIN)
public class InjectDemoResource {
	
	@GET
	@Path("annotation")
	public String getParamsUsingAnnotations(@MatrixParam("param") String matrxParam,
											@HeaderParam("customHeaderValue") String header,
											@CookieParam("name") String cookie){
		return "Matrix param: " + matrxParam + "Header param: " + header + "Cookies: " + cookie;
	}
	
	@GET
	@Path("context")
	public String getParamUsingContext(@Context UriInfo uriInfo,
										@Context HttpHeaders headers){
		String path = uriInfo.getAbsolutePathBuilder().toString();
		String header  = headers.getHeaderString("customHeaderValue");
		return "Path: " + path + " Header : " + header;
	}

}
