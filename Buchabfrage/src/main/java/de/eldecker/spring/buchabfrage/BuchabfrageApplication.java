package de.eldecker.spring.buchabfrage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Klasse mit Einstiegsmethode für Programm.
 */
@SpringBootApplication
public class BuchabfrageApplication {

    /**
     * Einstiegsmethode für Programmausführung.
     *
     * @param args Kommandozeilenargumente, werden an Spring durchgereicht
     */	
	public static void main( String[] args ) {
		
		SpringApplication.run( BuchabfrageApplication.class, args );
	}

}
