package server.model.service;

public interface RandomGeneratorService<K> {
    private void generateAndUpdate(K dao) throws Exception{};
    public void start();
    public void stop();
}
