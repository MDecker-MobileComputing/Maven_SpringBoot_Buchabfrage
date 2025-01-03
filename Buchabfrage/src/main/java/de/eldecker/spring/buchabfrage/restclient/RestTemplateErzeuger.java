package de.eldecker.spring.buchabfrage.restclient;

import static java.util.Collections.singletonList;

import java.util.Collections;
import java.util.List;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;


/**
 * Diese Klasse ist mit {@code Configuration} annotiert und enthält
 * deshalb Bean-Erzeuger-Methoden (welche selbst wieder mit
 * {@code Bean} annotiert sind).
 */
@Configuration
public class RestTemplateErzeuger {

	/**
	 * Objekt für REST-Abfrage (REST-Client), konfiguriert für client-seitiges Load Balancing.  
	 * Siehe Einträge unter {@code spring.cloud.discovery.client.simple.instances} in Datei
	 * {@code application.properties} für Liste der möglichen Clients.
	 * 
	 * @return REST-Client-Objekt
	 */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplateMitLoadBalancing( LoadBalancerClient loadBalancerClient ) {
    	
    	final RestTemplate restTemplate = new RestTemplate();
    	
    	final LoggingLoadBalancerInterceptor interceptor = new LoggingLoadBalancerInterceptor( loadBalancerClient );     	
    	final List<ClientHttpRequestInterceptor> interceptorListe = singletonList( interceptor );    	
    	restTemplate.setInterceptors( interceptorListe );
    	
        return restTemplate;
    }

}
