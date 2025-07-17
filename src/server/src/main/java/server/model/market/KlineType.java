package server.model.market;

/**
 * Enum che definisce i possibili campi di una candela (kline) di prezzo.
 * Viene utilizzato come chiave nella mappa di {@link Kline}.
 */
enum KlineType {
    OPEN,
    HIGH,
    LOW,
    CLOSE;
}
