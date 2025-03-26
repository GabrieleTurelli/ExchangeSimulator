package server.model.db;

import java.util.TreeMap;

public interface KeyValueDAO<K, V> {
    void addData(K key, V value);
    TreeMap<K, V> getData();
    void deleteAllData();
}
