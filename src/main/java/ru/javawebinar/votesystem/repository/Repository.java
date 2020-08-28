package ru.javawebinar.votesystem.repository;

import java.util.List;

public interface Repository <T> {

    T save (T item, int id);

    void delete (int id, int userId);

    T get (int id, int userId);

    List<T> getAllEntries(int userId);
}
