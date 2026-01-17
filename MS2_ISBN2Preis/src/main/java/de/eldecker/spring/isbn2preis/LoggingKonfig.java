package de.eldecker.spring.isbn2preis;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Konfiguration für Log-Nachrichten, die zu ELK-Stack (genauer: Logstash)
 * geschickt werden.
 * <br><br>
 * 
 * Dieser Filter setzt den MDC-Wert für jeden HTTP-Request, da MDC thread-lokal ist.
 */
@Component
public class LoggingKonfig implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(LoggingKonfig.class);

    private String instanzName = "ISBN13Preis-unbekannt";

    
    /**
     * Instanz-Name aus Portnummer ermitteln und speichern.
     * Beispielwert für Instanz auf Port 8030: "ISBN13Preis-8030".
     */
    @EventListener
    public void onWebServerReady( WebServerInitializedEvent event ) {

        final int portNummer = event.getWebServer().getPort();
        instanzName = "ISBN13Preis-" + portNummer;

        LOG.info( "Instanz-Name für Logging ermittelt: \"{}\"", instanzName );
    }

    
    /**
     * MDC-Wert für jeden Request setzen, da MDC thread-lokal ist.
     * Nach dem Request wird der Wert automatisch aus dem MDC entfernt.
     */
    @Override
    public void doFilter( ServletRequest request, 
                          ServletResponse response, 
                          FilterChain chain ) throws IOException, ServletException {
        
        MDC.put( "Instanzname", instanzName );
        try {
            chain.doFilter( request, response );
        } finally {
            MDC.remove( "Instanzname" ); // Cleanup nach Request
        }
    }

}
