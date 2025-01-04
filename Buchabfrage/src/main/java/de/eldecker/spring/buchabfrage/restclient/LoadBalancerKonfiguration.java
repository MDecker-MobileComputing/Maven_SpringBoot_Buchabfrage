package de.eldecker.spring.buchabfrage.restclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.core.RandomLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
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


    /**
     * Diese Methode sorgt dafür, dass statt dem standardmäßig verwendeten {@code RoundRobinLoadBalancer}
     * der {@code RandomLoadBalancer} verwendet wird. Wird eingeschaltet, wenn in der Datei 
     * {@code application.properties} der Wert {@code de.eldecker.buchabfrage.loadbalancer.random=true}
     * gesetzt ist.
     * 
     * @param lbcFactory Factor für Load Balancer Clients
     * 
     * @return Load Balancer für Zufallsauswahl von Service-Instanzen
     */
    @Bean
    @ConditionalOnProperty(name = "de.eldecker.buchabfrage.loadbalancer.random", havingValue = "true")
    public ReactorLoadBalancer<ServiceInstance> zufallsLoadBalancer( LoadBalancerClientFactory lbcFactory ) {
                                            
        LOG.info( "Load-Balancer verwendet Zufallsauswahl von Service-Instanzen." );
        
        final ObjectProvider<ServiceInstanceListSupplier> provider = 
                               lbcFactory.getLazyProvider( "isbn-abfrage", 
                                                           ServiceInstanceListSupplier.class );
        
        return new RandomLoadBalancer( provider, "isbn-abfrage" );
    }
    
}
