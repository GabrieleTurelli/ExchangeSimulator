package client.view.components.ui.tradepanel;

import client.view.components.ui.HorizontalSeparator;
import client.view.components.ui.NumericInputEntry;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

public class OrderEntry extends VBox {
    private final NumericInputEntry sizeEntry;
    private final NumericInputEntry priceEntry; // può essere null se MARKET

    public OrderEntry(boolean isLimit) {
        super();
        setSpacing(10);

        // Campo per la quantità
        this.sizeEntry = new NumericInputEntry("Size", "enter size");

        if (isLimit) {
            // In caso sia in modalita LIMIT viene aggiunto anche il campo per il prezzo
            this.priceEntry = new NumericInputEntry("Price", "enter price");
            getChildren().addAll(priceEntry, sizeEntry);
        } else {
            this.priceEntry = null;
            getChildren().add(sizeEntry);
        }

        // separatore grafico
        HorizontalSeparator separator = new HorizontalSeparator(0);
        separator.endXProperty().bind(this.widthProperty().subtract(10));
        setMargin(separator, new Insets(10, 0, 10, 0));
        getChildren().add(separator);
    }

    /** @return quantità inserita dall’utente */
    public double getAmount() {
        return sizeEntry.getValue();
    }

    /**
     * @return prezzo inserito dall’utente, o 0.0 se MARKET
     * @throws IllegalStateException se invocato in modalità LIMIT senza priceEntry
     */
    public double getPrice() {
        if (priceEntry != null) {
            return priceEntry.getValue();
        } else {
            // Market order: non c’è campo prezzo
            return 0.0;
        }
    }
}
