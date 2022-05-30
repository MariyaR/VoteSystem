package ru.javawebinar.votesystem.web;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.votesystem.HasId;
import ru.javawebinar.votesystem.repository.Repository;
import java.net.URI;
import java.util.List;

public abstract class AbstractController <T extends HasId>{

    protected final Logger log = LoggerFactory.getLogger(getClass());

     protected Repository<T> repository;

    public T getById(int id, int userId) {
        log.info("get {}", id);
        return repository.get(id, userId);
    }

    public void deleteById(int id) {
        log.info("delete {}", id);
        repository.delete(id, id);
    }

    public ResponseEntity<T> createWithUrl(T item, String rest_url, int restoId) {
        log.info("create from item {}", item);
        T created = repository.save(item, restoId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(rest_url + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    public List<T> getAllEntries(int id) {
        log.info("getAll");
        return repository.getAllEntries(id);
    }
}
