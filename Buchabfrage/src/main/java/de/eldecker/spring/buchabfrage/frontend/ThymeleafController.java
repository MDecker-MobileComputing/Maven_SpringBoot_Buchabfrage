package de.eldecker.spring.buchabfrage.frontend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class ThymeleafController {
	
	private static Logger LOG = LoggerFactory.getLogger( ThymeleafController.class );

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
	 *               werden sollen.
	 *
	 * @param model Objekt für Platzhalterwerte in Template-Datei
	 *
	 * @return Name der Template-Datei, die für Anzeige verwendet
	 *         werden soll (ohne Datei-Endung)
	 */         	
    @GetMapping("/buch/{isbn13}")
    public String buchanfrage( @PathVariable String isbn13,
                               Model model ) {
    	
    	isbn13 = isbn13.trim().replace( "-", "" );
    	
    	LOG.info( "Anfrage für Buch mit ISBN13={} erhalten.", isbn13 );
    	    	    	
    	model.addAttribute( "isbn", isbn13 );
    	
    	model.addAttribute( "preis", 59.99 );
    	
    	return "ergebnis";
    }
	
}
