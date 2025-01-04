package de.eldecker.spring.isbn2preis.rest;

import static java.lang.Double.NEGATIVE_INFINITY;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * RestController-Klasse mit REST-Endpunkt für Abfrage von Preis
 * von Buch anhand ISBN-13.
 */
@RestController
@RequestMapping( "/api/v1" )
public class Isbn2PreisRestController {
	
	private static Logger LOG = LoggerFactory.getLogger( Isbn2PreisRestController.class );
	
	/**
	 * Regexp-Muster um ISBN13 auf Gültigkeit zu überprüfen: sie muss (nach
	 * Entfernen evtl. vorhandener Bindestriche) auf genau 13 Ziffern bestehen.
	 */
	private static final Pattern ISBN13_REGEXP = Pattern.compile( "^\\d{13}$" );


	/**
	 * Methode für HTTP-GET-REST-Endpunkt um Preis für ein Buch anhand der
	 * ISBN13 abzufragen.
	 * <br><br>
	 * 
	 * Beispiel-URL für lokale Aufrufe an Port 8010 bzw. 8020: 
	 * <pre>
	 * http://localhost:8010/api/v1/isbn2preis?isbn13=978-3836290494
	 * http://localhost:8020/api/v1/isbn2preis?isbn13=978-3836290494
	 * </pre>
	 * 
	 * @param isbn13 ISBN13 für das Buch, dessen Preis abgefragt werden soll.
	 *               Beispiel: {@code 978-3446481220}
	 * 
	 * @return Preis in Euro; wird aus Hash-Code der von Bindestrichen 
	 *         bereinigen ISBN13 berechnet. Wenn keine gültige ISBN13 
	 *         übergeben wurde, dann wird HTTP-Status-Code 400 mit
	 *         einem Preis "unendlich negativ" zurückgegeben.
	 */
	@GetMapping( "/isbn2preis" )
	public ResponseEntity<Double> getPreis( @RequestParam("isbn13") String isbn13 ) {
		
		isbn13 = isbn13.replace( "-", "" );
		
		final Matcher matcher = ISBN13_REGEXP.matcher( isbn13 );
		if ( matcher.matches() == false ) {
			
			return ResponseEntity.status( BAD_REQUEST ).body( NEGATIVE_INFINITY );
		}
		
		final int    hashCodeQuadrat = Math.abs( isbn13.hashCode() * isbn13.hashCode() );
		final int    preisInEuroCent = hashCodeQuadrat % 10_000;
		final double preisInEuro     = preisInEuroCent / 100.0;
		
		LOG.info( "Antwort für ISBN13={}: {} Euro", isbn13, preisInEuro );
		
		return ResponseEntity.status( OK ).body( preisInEuro );
	}
	
}
