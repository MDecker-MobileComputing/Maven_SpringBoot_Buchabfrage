package de.eldecker.spring.buchabfrage.db;

import org.springframework.stereotype.Repository;


/**
 * Diese Klasse simuliert eine Datenbank für Buchdaten.
 */
@Repository
public class BuchDatenbank {
    
    private static final String[] ARRAY_VORNAMEN = { 
            "Alice", "Bob", "Carl", "David", "Eva", "Frank", "Gerhard", "Herbert",
            "Ingrid", "Johanna", "Karl", "Lena", "Maria", "Nora", "Otto", "Paula",
            "Quirin", "Renate", "Stefan", "Thea", "Ursula", "Viktor", "Walter",
            "Xaver", "Yvonne", "Zelena" };
    
    private static final String[] ARRAY_NACHNAME = {
            "Acker", "Bauer", "Cramer", "Dorn", "Eck", "Fischer", "Gebert", "Hubert", 
            "Issel", "Jäger", "Koch", "Leber", "Müller", "Nest", "Ochs", "Petermann",
            "Qendling", "Rabe", "Schmidt", "Tiger", "Uhl", "Vogel", "Weber", 
            "Xantner", "Yager", "Zander" };
    
    private static final String[] ARRAY_FACHGEBIET = {
            "Anorganische Chemie", "Biochemie", "Chemieingenieurwesen", "Didaktik",
            "Elektrochemie", "Festkörperchemie", "Geo-Informatik", "Hochschuldidaktik",
            "Informatik", "Katalyse", "Lebensmittelchemie", "Medizinische Chemie",
            "Neurologie", "Organische Chemie", "Physikalische Chemie",
            "Quantenchemie", "Röntgenstrukturanalyse", "Strahlenchemie",
            "Theoretische Informatik", "Umweltökonomie", "Verfahrenstechnik",
            "Ultraschalldiagnostik", "Werkstoffkunde", "Xenobiologie", "Zoologie" };
 
    
    /**
     * Buchdaten für ISBN aus Datenbank abfragen.
     * 
     * @param isbn13 ISBN des Buchs; ISBN wird nicht auf Gültigkeit geprüft
     * 
     * @return Buchdaten für ISBN mit Autor und Titel
     */
    public BuchRecord getBuch( long isbn13 ) {
        
        final String autor = getVorname( isbn13 ) + " " + getNachname( isbn13 );
        final String titel = getFachgebiet( isbn13 );
        
        return new BuchRecord( autor, titel );
    }
    
    
    /**
     * Fachgebiet für Titel des Buchs.
     * 
     * @param isbn13 ISBN des Buchs
     * 
     * @return Anhand ISBN ausgewähltes Fachgebiet, z.B. "Biochemie"
     */
    private String getFachgebiet( long isbn13 ) {

        final long isbn13malSieben = Math.abs( isbn13 * 7 ); // multipliziert mit Primzahl
        final int index = (int) ( isbn13malSieben % ARRAY_FACHGEBIET.length );
        return ARRAY_FACHGEBIET[ index ];
    }

    
    /**
     * Vorname für Autor des Buchs.
     * 
     * @param isbn13 ISBN des Buchs
     * 
     * @return Anhand ISBN ausgewählter Vorname, z.B. "Alice"
     */
    private String getVorname( long isbn13 ) {
        
        // Betragsfunktion um negativen ISBNs bei Überlauf von Wertebereich long zu vermeiden
        final long isbn13Quadrat = Math.abs( isbn13 * isbn13 ); 
        final int index = (int) ( isbn13Quadrat % ARRAY_VORNAMEN.length );
        return ARRAY_VORNAMEN[ index ];
    }
    
    
    /**
     * Nachname für Autor des Buchs.
     * 
     * @param isbn13 ISBN des Buchs
     * 
     * @return Anhand ISBN ausgewählter Nachname, z.B. "Bauer"
     */
    private String getNachname( long isbn13 ) {

        final long isbn13malDrei = Math.abs( isbn13 * 3 ); // multipliziert mit Primzahl
        final int index = (int) ( isbn13malDrei % ARRAY_NACHNAME.length );
        return ARRAY_NACHNAME[ index ];
    }
    
}
