package de.eldecker.spring.buchabfrage.restclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


/**
 * Diese Klasse ist mit {@code Configuration} annotiert und enthält
 * deshalb Bean-Erzeuger-Methoden (welche selbst wieder mit
 * {@code Bean} annotiert sind).
 * <br><br>
 * 
 * Siehe auch Konfigurationen in {@code application.properties}:
 * <ul>
 * <li>Mögliche Clients:  {@code spring.cloud.discovery.client.simple.instances}</li>
 * <li>Log-Output für Anzeige verwendeter Client für eine Abfrage:
 *     {logging.level.org.springframework.cloud=DEBUG}</li>
 * <li>Für Retry: {@code spring.cloud.loadbalancer.retry.*}</li>
 * </ul>
 */
@Configuration
public class LoadBalancerKonfiguration {

	private static Logger LOG = LoggerFactory.getLogger( LoadBalancerKonfiguration.class );
	

	/**
	 * Objekt für REST-Abfrage (REST-Client), konfiguriert für client-seitiges Load Balancing.  
	 * <br><br>
	 * 
	 * Offizielle Doku zu {@code RestTemplate} mit dem Spring Load Balancer:
	 * <a href="https://bit.ly/3Pmo2UV">siehe hier</a>
	 * 
	 * @return REST-Client-Objekt
	 */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplateMitLoadBalancing() {
    	    	
    	LOG.info( "Neue Instanz von RestTemplate mit Load Balancing erzeugt." );
    	
        return new RestTemplate();
    }

}