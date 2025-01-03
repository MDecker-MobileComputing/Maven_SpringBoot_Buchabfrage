package de.eldecker.spring.buchabfrage.helferlein;

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
public class BohnenFabrik {

	/**
	 * Objekt für REST-Abfrage (REST-Client), konfiguriert  
	 * für client-seitiges Load Balancing.
	 * 
	 * @return REST-Client-Objekt
	 */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
    	
        return new RestTemplate();
    }
    
}
