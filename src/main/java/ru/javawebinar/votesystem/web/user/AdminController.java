package ru.javawebinar.votesystem.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.votesystem.model.User;
import ru.javawebinar.votesystem.repository.UserRepository;
import ru.javawebinar.votesystem.to.UserTo;
import ru.javawebinar.votesystem.util.UserUtil;


import java.util.List;

import static ru.javawebinar.votesystem.web.SecurityUtil.authUserId;

@RestController
@RequestMapping(value = AdminController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController extends AbstractUserController {

    static final String REST_URL = "/admin/users";

    @GetMapping
    public List<UserTo> getAll() {
        return UserUtil.getTos(super.getAllEntries(authUserId()));
    }

    @GetMapping("/{id}")
    public UserTo get(@PathVariable int id) {
        User user = super.getById(id, id);
        return UserUtil.createTo(user);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createWithLocation(@RequestBody UserTo userTo) {
        return super.createWithLocation(userTo, REST_URL);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.deleteById(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody UserTo userTo, @PathVariable int id) {
        super.update(userTo, id);
    }

    @GetMapping("/by")
    public UserTo getByMail(@RequestParam String email) {
        log.info("getByEmail {}", email);
        return UserUtil.createTo(userRepository.getByEmail(email));
    }
}