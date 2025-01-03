package de.eldecker.spring.buchabfrage.restclient;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    public RestTemplate restTemplateMitLoadBalancing() {
    	    	
        return new RestTemplate();
    }

}
