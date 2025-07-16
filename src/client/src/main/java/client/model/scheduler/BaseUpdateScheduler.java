package client.model.scheduler;

import javafx.concurrent.ScheduledService;
import javafx.util.Duration;

/**
 * Classe base per gli scheduler
 *
 * @param <T> il tipo di dato restituito dal task di aggiornamento
 */
public abstract class BaseUpdateScheduler<T> extends ScheduledService<T> {

    /**
     * Etichetta descrittiva del servizio, utilizzata principalmente nel logging.
     */
    private final String label;

    /**
     * Costruttore della classe base.
     * Imposta l'intervallo di esecuzione periodica e abilita il riavvio in caso di
     * errore.
     *
     * @param label          nome descrittivo del scheduler (es. " Daily market data
     *                       for BTC, 5s")
     * @param updateInterval periodo di aggiornamento
     */
    protected BaseUpdateScheduler(String label, Duration updateInterval) {
        this.label = label;
        setPeriod(updateInterval); // Imposta l'intervallo di esecuzione
        setRestartOnFailure(true); // Riavvia automaticamente in caso di fallimento
    }

    /**
     * Richiamato quando il task  termina con successo.
     * Logga sulla console indicando il completamento dell'aggiornamento.
     */
    @Override
    protected void succeeded() {
        super.succeeded();
        System.out.println(label + " update succeeded");
    }

    /**
     * Richiamato quando il task periodico fallisce.
     * Logga l'errore con il messaggio dell'eccezione.
     */
    @Override
    protected void failed() {
        super.failed();
        Throwable ex = getException();
        System.out.println(label + " update failed\nException: "
                + (ex != null ? ex.getMessage() : "Unknown"));
    }

    /**
     * Richiamato quando il servizio viene cancellato.
     * Logga l'annullamento dello scheduler.
     */
    @Override
    protected void cancelled() {
        super.cancelled();
        System.out.println(label + " update scheduler cancelled");
    }

    /**
     * Metodo astratto da implementare per creare il Task di aggiornamento.
     * Ogni sottoclasse deve fornire la logica di fetch o calcolo del dato di tipo
     * T.
     */
    @Override
    protected abstract javafx.concurrent.Task<T> createTask();
}
