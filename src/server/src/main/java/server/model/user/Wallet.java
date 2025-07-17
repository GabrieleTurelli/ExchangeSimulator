/**
 * Rappresenta il portafoglio di un utente, contenente le quantità di ciascuna moneta.
 * @author Gabriele Turelli     
 * @version 1.0
 */
package server.model.user;

import java.util.HashMap;

public class Wallet extends HashMap<String, Double> {
    public Wallet(){
        super();
    }
}
