package de.eldecker.spring.buchabfrage.frontend;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import de.eldecker.spring.buchabfrage.db.BuchDatenbank;
import de.eldecker.spring.buchabfrage.db.BuchRecord;
import de.eldecker.spring.buchabfrage.restclient.PreisAbfrageClient;


/**
 * Controller für Thymeleaf-Templates: jede mit einem Mapping annotierte Methode
 * gibt einen String mit dem Namen der anzuzeigenden Template-Datei zurück.
 */
@Controller
public class ThymeleafController {

	private static Logger LOG = LoggerFactory.getLogger( ThymeleafController.class );

	/** Client um Buchpreis von REST-API mit Load Balancing abzufragen. */
	@Autowired
	private PreisAbfrageClient _preisAbfrageClient;

	/** Simulierte Buchdatenbank, gibt für jede {@code long}-Zahl ein Buch zurück. */
	@Autowired
	private BuchDatenbank _buchDatenbank;


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
	 *               werden sollen; als {@code Long}-Variable,
	 *               damit keine Bindestriche enthalten sein
	 *               können; muss genau 13 Ziffern haben (keine
	 *               führenden Nullen)
	 *
	 * @param model Objekt für Platzhalterwerte in Template-Datei
	 *
	 * @return Name der Template-Datei, die für Anzeige verwendet
	 *         werden soll (ohne Datei-Endung)
	 */
    @GetMapping( "/buch/{isbn13}" )
    public String buchanfrage( @PathVariable Long isbn13,
                               Model model ) {

    	LOG.info( "Anfrage für Buch mit ISBN13={} erhalten.", isbn13 );
    	model.addAttribute( "isbn", isbn13 );

    	final int laenge = isbn13.toString().length();
        if ( laenge != 13 ) {

            LOG.warn( "Ungültige ISBN13-Länge: {}.", laenge );
            return "fehler";
        }


        final BuchRecord buchRecord = _buchDatenbank.getBuch( isbn13 );
        model.addAttribute( "autor", buchRecord.autor() );
        model.addAttribute( "titel", buchRecord.titel() );


    	final Optional<Double> preisOptional = _preisAbfrageClient.holePreis( isbn13 );
    	preisOptional.ifPresentOrElse( preisEuro -> model.addAttribute( "preis", preisEuro ),
    			                       ()        -> model.addAttribute( "preis", -1.0      )
    			                     );
    	return "ergebnis";
    }

}
