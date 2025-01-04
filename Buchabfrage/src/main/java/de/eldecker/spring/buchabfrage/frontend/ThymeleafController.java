package de.eldecker.spring.buchabfrage.frontend;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import de.eldecker.spring.buchabfrage.restclient.PreisAbfrageClient;


/**
 * Controller für Thymeleaf-Templates: jede mit einem Mapping annotierte Methode
 * gibt einen String mit dem Namen der anzuzeigenden Template-Datei zurück.
 */
@Controller
public class ThymeleafController {

	private static Logger LOG = LoggerFactory.getLogger( ThymeleafController.class );

	/**
	 * Client um Buchpreis von REST-API mit Load Balancing abzufragen.
	 */
	@Autowired
	private PreisAbfrageClient _preisAbfrageClient;


	/**
	 * Seite mit Details zu einem Buch anzeigen.
	 * <br><br>
	 *
	 * Beispiel-URL für lokalen Aufruf:
	 * <pre>
	 * http://localhost:8080/buch/9780201038040
	 * </pre>
	 *
	 * @param isbn13 ISBN von Buch, für die Details abgefragt
	 *               werden sollen; als {@code long}-Variable,
	 *               damit keine Bindestriche enthalten sein
	 *               können.
	 *
	 * @param model Objekt für Platzhalterwerte in Template-Datei
	 *
	 * @return Name der Template-Datei, die für Anzeige verwendet
	 *         werden soll (ohne Datei-Endung)
	 */
    @GetMapping( "/buch/{isbn13}" )
    public String buchanfrage( @PathVariable long isbn13,
                               Model model ) {

    	LOG.info( "Anfrage für Buch mit ISBN13={} erhalten.", isbn13 );
    	model.addAttribute( "isbn", isbn13 );
    	
    	final int laenge = Long.toString( isbn13 ).length();
        if ( laenge != 13 ) {
            
            LOG.warn( "Ungültige ISBN13-Länge: {}.", laenge );
            return "fehler";
        }

        
    	final Optional<Double> preisOptional = _preisAbfrageClient.holePreis( isbn13 );
    	preisOptional.ifPresentOrElse( preisEuro -> model.addAttribute( "preis", preisEuro ),
    			                       ()        -> model.addAttribute( "preis", -1.0      )
    			                     );

    	return "ergebnis";
    }

}
