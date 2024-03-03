package services;

import java.util.List;

public interface IService <T>{
    boolean add(T t);
    boolean delete(int id);
    boolean update (T t);
    List<T> readAll();
    T readById(int id);

}
