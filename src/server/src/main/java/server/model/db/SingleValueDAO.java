package server.model.db;

import java.util.ArrayList;

public interface SingleValueDAO<T> {
    void addData(T data);
    ArrayList<T> getData();
    void deleteAllData();
}
