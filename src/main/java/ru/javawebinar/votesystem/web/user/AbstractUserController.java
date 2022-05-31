package ru.javawebinar.votesystem.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import ru.javawebinar.votesystem.model.User;
import ru.javawebinar.votesystem.repository.UserRepository;
import ru.javawebinar.votesystem.to.UserTo;
import ru.javawebinar.votesystem.util.UserUtil;
import ru.javawebinar.votesystem.util.exception.NotFoundException;
import ru.javawebinar.votesystem.web.AbstractController;

import javax.annotation.PostConstruct;

import java.util.Optional;

import static ru.javawebinar.votesystem.util.ValidationUtil.assureIdConsistent;

public class AbstractUserController extends AbstractController<User> {

    @Autowired
     UserRepository userRepository;

    @PostConstruct
    public void init() {
        super.repository = userRepository;
    }

    public ResponseEntity<User> createWithLocation(UserTo userTo, String rest_url) {
        return super.createWithUrl(UserUtil.createNewFromTo(userTo), rest_url, 0);
    }

    public void update(UserTo userTo, int id) {
        log.info("update {} with id={}", userTo, id);
        assureIdConsistent(userTo, id);
        User user = userRepository.get(userTo.id()).orElseThrow(() -> new NotFoundException(String.format("user with id %d was not found", userTo.id())));
        userRepository.save(UserUtil.updateFromTo(user, userTo), id);
    }

    protected Optional<User> getById(int id) {
        log.info("getById {} with id={}", id);
        return userRepository.get(id);
    }
}
