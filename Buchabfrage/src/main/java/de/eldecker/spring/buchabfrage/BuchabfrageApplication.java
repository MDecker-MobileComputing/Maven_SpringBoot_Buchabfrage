package de.eldecker.spring.buchabfrage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Einstiegspunkt für Programm.
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
