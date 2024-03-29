package ru.javawebinar.votesystem.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import ru.javawebinar.votesystem.model.User;


import java.util.List;

import static ru.javawebinar.votesystem.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.votesystem.util.ValidationUtil.checkNotFoundWithId;

@Repository
public class UserRepository implements ru.javawebinar.votesystem.repository.Repository<User> {
    private static final Sort SORT_NAME_EMAIL = Sort.by(Sort.Direction.ASC, "name", "email");

    @Autowired
    private CrudUserRepository crudRepository;

    public User save(User user, int userId) {
        Assert.notNull(user, "user must not be null");
        return crudRepository.save(user);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(crudRepository.delete(id) != 0, id);
    }

    public User get(int id, int userId) {
        return checkNotFoundWithId(crudRepository.findById(id).orElse(null), id);
    }

    public User getByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(crudRepository.getByEmail(email), "email=" + email);
    }

    public List<User> getAllEntries(int userId) {
        return crudRepository.findAll(SORT_NAME_EMAIL);
    }

    public User getWithHistory(int id) {
        return checkNotFoundWithId(crudRepository.getWithRecords(id), id);
    }
}
