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

    /** Wert für eigenes Log-Feld "Instanzname". */
    private String _instanzName = "ISBN13Preis-???";

    /**
     * Instanzname bestimmen.
     */
    @EventListener
    public void onWebServerReady( WebServerInitializedEvent event ) {

        final int portNummer = event.getWebServer().getPort();
        _instanzName = "ISBN13Preis-" + portNummer;

        LOG.info( "Instanz-Name für Logging ermittelt: \"{}\"", _instanzName );
    }


    /**
     * MDC-Feld mit Instanzname im Log-Context für jeden HTTP-Request setzen.
     */
    @Override
    public void doFilter( ServletRequest request, 
                          ServletResponse response, 
                          FilterChain chain ) throws IOException, ServletException {
        
        MDC.put( "Instanzname", _instanzName );
        try {
            chain.doFilter( request, response );
        } finally {
            MDC.remove( "Instanzname" ); // Cleanup nach Request
        }
    }

}
