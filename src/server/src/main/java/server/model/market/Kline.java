package server.model.market;

import java.util.HashMap;

public class Kline extends HashMap<KlineType, Double> {

    public Kline(
            Double open,
            Double high,
            Double low,
            Double close) {
        super();
        this.put(KlineType.OPEN, open);
        this.put(KlineType.HIGH, high);
        this.put(KlineType.LOW, low);
        this.put(KlineType.CLOSE, close);
    }

    public Double getOpen() {
        return this.get(KlineType.OPEN);
    }

    public Double getHigh() {
        return this.get(KlineType.HIGH);
    }

    public Double getLow() {
        return this.get(KlineType.LOW);
    }

    public Double getClose() {
        return this.get(KlineType.CLOSE);
    }

    public void setOpen(Double open) {
        this.put(KlineType.OPEN, open);
    }

    public void setHigh(Double high) {
        this.put(KlineType.HIGH, high);
    }

    public void setLow(Double low) {
        this.put(KlineType.LOW, low);
    }

    public void setClose(Double close) {
        this.put(KlineType.CLOSE, close);
    }

    @Override
    public String toString() {
        return "open=" + this.getOpen() + ", high=" + this.getHigh() + ", low=" + this.getLow() + ", close="
                + this.getClose();
    }
}