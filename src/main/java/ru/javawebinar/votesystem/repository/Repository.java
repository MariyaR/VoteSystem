package ru.javawebinar.votesystem.repository;

import java.util.List;
import java.util.Optional;

public interface Repository <T>{

    //Optional<T> get(int id);

    T save (T item, int id);

    void delete (int id, int userId);

    List<T> getAllEntries(int userId);
}
