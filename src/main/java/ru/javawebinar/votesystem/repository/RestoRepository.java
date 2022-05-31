package ru.javawebinar.votesystem.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import ru.javawebinar.votesystem.model.Resto;

import java.util.List;
import java.util.Optional;

import static ru.javawebinar.votesystem.util.ValidationUtil.checkNotFoundWithId;

@Repository
public class RestoRepository implements ru.javawebinar.votesystem.repository.Repository<Resto> {

    private static final Sort SORT_NAME_ADDRESS = Sort.by(Sort.Direction.ASC, "name", "address");

    @Autowired
    private CrudRestoRepository crudRepository;

    public Resto save(Resto resto, int userId) {
        Assert.notNull(resto, "Resto must not be null");
        return crudRepository.save(resto);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(crudRepository.delete(id) != 0, id);
    }

    public Optional<Resto> get(int id) {
        return crudRepository.findById(id);
    }

    public List<Resto> getAllEntries(int userId) {
        return crudRepository.findAll(SORT_NAME_ADDRESS);
    }

    public Optional<Resto> getWithHistory(int id) {
        return crudRepository.getWitHistory(id);
    }

}
