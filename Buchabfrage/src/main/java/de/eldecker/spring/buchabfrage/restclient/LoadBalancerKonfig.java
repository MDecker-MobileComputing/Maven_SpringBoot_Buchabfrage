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
 */
@Configuration
public class LoadBalancerKonfig {

	private static Logger LOG = LoggerFactory.getLogger( LoadBalancerKonfig.class );
	

	/**
	 * Objekt für REST-Abfrage (REST-Client), konfiguriert für client-seitiges Load Balancing.  
	 * Siehe Einträge unter {@code spring.cloud.discovery.client.simple.instances} in Datei
	 * {@code application.properties} für Liste der möglichen Clients.
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
    	    	
    	LOG.info( "Neue Instanz von RestTemplate mit Load Balancing erzeugt" );
    	
        return new RestTemplate();
    }

}
