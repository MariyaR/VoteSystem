package ru.javawebinar.votesystem.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import ru.javawebinar.votesystem.model.User;
import ru.javawebinar.votesystem.repository.UserRepository;
import ru.javawebinar.votesystem.to.UserTo;
import ru.javawebinar.votesystem.util.UserUtil;
import ru.javawebinar.votesystem.web.AbstractController;

import javax.annotation.PostConstruct;

import static ru.javawebinar.votesystem.util.ValidationUtil.assureIdConsistent;

public class AbstractUserController extends AbstractController<User> {

    @Autowired
     UserRepository userRepository;

    @PostConstruct
    public void init() {
        this.repository = userRepository;
    }

    public ResponseEntity<User> createWithLocation(UserTo userTo, String rest_url) {
        return super.createWithUrl(UserUtil.createNewFromTo(userTo), rest_url, 0);
    }

    public void update(UserTo userTo, int id) {
        log.info("update {} with id={}", userTo, id);
        assureIdConsistent(userTo, id);
        User user = repository.get(userTo.id(), id);
        repository.save(UserUtil.updateFromTo(user, userTo), id); //UserUtil.updateFromTo(user, userTo)
    }

}
