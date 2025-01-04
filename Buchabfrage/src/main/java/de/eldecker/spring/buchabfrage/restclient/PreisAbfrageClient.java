package de.eldecker.spring.buchabfrage.restclient;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


/**
 * Bean, um Preis für ein Buch von REST-API mit client-seitigem Load Balancing abzufragen.
 */
@Component
public class PreisAbfrageClient {

	private static Logger LOG = LoggerFactory.getLogger( PreisAbfrageClient.class );

	/**
	 * REST-Client-Objekt, konfiguriert für Load-Balancing mit Annotation
	 * {@code LoadBalanced} in Klasse {@link Bohnenfabrik#restTemplateMitLoadBalancing()).
	 */
	@Autowired
	private RestTemplate _restTemplate;


	/**
	 * Bei REST-API mit Load Balancing Preis für Buch abfragen
	 *
	 * @param isbn13 ISBN13 des abzufragenden Buchs
	 *
	 * @return Optional enthält bei erfolgreicher Abfrage den Buchpreis in Euro
	 */
	public Optional<Double> holePreis( long isbn13 ) {

        final String url =
        		UriComponentsBuilder.fromUriString( "http://isbn-abfrage/api/v1/isbn2preis" ) // Pseudo-Domain "isbn-abfrage"
                                    .queryParam( "isbn13", isbn13 )
                                    .toUriString();
        try {

        	final ResponseEntity<Double> responseEntity =
        									_restTemplate.getForEntity( url, Double.class );

            final HttpStatusCode statusCode = responseEntity.getStatusCode();
            if ( statusCode.is2xxSuccessful() ) {

            	final Double preisEuro = responseEntity.getBody();

            	LOG.info( "Preis für Buch mit ISBN13={} von REST-API erhalten: {}",
            			  isbn13, preisEuro );

            	return Optional.of( preisEuro );

            } else {

            	LOG.error( "HTTP-GET-Request für Abfrage Buchpreis lieferte Fehlercode {} zurück.",
            			   statusCode );
            	return Optional.empty();
            }
        }
        catch ( RestClientException ex ) {

        	LOG.error( "HTTP-GET-Request für Abfrage Buchpreis für ISBN13={} hat Ausnahme geworfen.",
        			   isbn13, ex );
        	return Optional.empty();
        }
	}

}
