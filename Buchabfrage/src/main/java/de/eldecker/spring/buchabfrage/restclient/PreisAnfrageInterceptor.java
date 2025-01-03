package de.eldecker.spring.buchabfrage.restclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.net.URI;


public class PreisAnfrageInterceptor implements ClientHttpRequestInterceptor {

	private static Logger LOG = LoggerFactory.getLogger( PreisAnfrageInterceptor.class );
	
    @Override
    public ClientHttpResponse intercept( HttpRequest request, 
    		                             byte[] body, 
    		                             ClientHttpRequestExecution execution
    		                            ) throws IOException {
    	
    	final URI uri = request.getURI();
    	
    	LOG.info( "Von Load Balancer ausgewählter Client: {}", uri ); 
    	
        return execution.execute(request, body);
    }

}
