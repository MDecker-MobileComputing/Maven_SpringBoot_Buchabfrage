package de.eldecker.spring.isbn2preis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Konfiguration für Log-Nachrichten, die zu ELK-Stack (genauer: Logstash)
 * geschickt werden.
 */
@Component
public class LoggingKonfig {

    private static Logger LOG = LoggerFactory.getLogger(LoggingKonfig.class);

    /**
     * Eigenes Feld im "Mapped Diagnostic Context (MDC)" mit "Instanz-Name" in
     * Logback setzen. Der Wert endet auf die Portnummer des HTTP-Servers.
     * Beispielwert: "ISBN13Preis-8010".
     * <br><br>
     * 
     * Zugehöriger Eintrag unter Key "includeMdcKeyName" in Datei {@code logback.xml}
     */
    @EventListener
    public void onWebServerReady( WebServerInitializedEvent event ) {

        final int portNummer = event.getWebServer().getPort();
        final String instanzName = "ISBN13Preis-" + portNummer;
        MDC.put( "Instanzname", instanzName );

        LOG.info( "Instanz-Name in Logging-Kontext gesetzt: \"{}\"", 
                  instanzName );
    }

}
