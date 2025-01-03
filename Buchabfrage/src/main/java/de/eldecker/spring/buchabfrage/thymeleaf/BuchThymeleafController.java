package de.eldecker.spring.buchabfrage.thymeleaf;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import de.eldecker.spring.buchabfrage.rest.PreisAbfrageClient;


/**
 * Controller für Thymeleaf-Templates.
 */
@Controller
public class BuchThymeleafController {

	/** 
	 * Client mit Load Balancing, um bei anderem Microservice
	 * Preis von Buch abzufragen. 
	 */
	@Autowired
	private PreisAbfrageClient _preisAbfrageClient;
	
	
	/**
	 * Seite mit Details zu einem Buch anzeigen. 
	 * 
	 * @param isbn13 ISBN von Buch, für die Details abgefragt
	 *               werden sollen.
	 * 
	 * @param model Objekt für Platzhalterwerte in Template-Datei
	 * 
	 * @return Name der Template-Datei, die für Anzeige verwendet
	 *         werden soll, z.B. "ergebnis" für "ergebnis.html"
	 */
    @GetMapping( "/buch/{isbn13}" )
    public String slaWertUmrechnen( @PathVariable String isbn13,
                                    Model model ) {
    	
    	model.addAttribute( "isbn13", isbn13 );
    	
    	final Optional<Double> preisOptional = _preisAbfrageClient.holePreis(isbn13);
    	preisOptional.ifPresentOrElse( preis -> model.addAttribute( "preis", preis ),
    			                       ()    -> model.addAttribute( "preis", ""    ) 
    			                     );
    	
    	return "ergebnis";
    }
	
}
