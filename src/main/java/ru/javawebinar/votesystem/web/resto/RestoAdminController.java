package ru.javawebinar.votesystem.web.resto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.votesystem.model.Resto;
import ru.javawebinar.votesystem.repository.RestoRepository;
import ru.javawebinar.votesystem.to.RestoTo;
import ru.javawebinar.votesystem.util.Util;
import ru.javawebinar.votesystem.web.AbstractController;
import ru.javawebinar.votesystem.web.AuthorizedUser;

import javax.annotation.PostConstruct;
import java.util.List;

import static ru.javawebinar.votesystem.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(value = RestoAdminController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestoAdminController extends AbstractController<Resto> {
    static final String REST_URL = "/admin/restos";

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestoRepository restoRepository;

    @PostConstruct
    public void init(){
        this.repository = restoRepository;
    }

    @GetMapping
    public List<RestoTo> getAll(@AuthenticationPrincipal AuthorizedUser authUser) {
        return Util.getRestoTos(super.getAllEntries(authUser.getId()));
    }

    @GetMapping("/{id}")
    public RestoTo get(@PathVariable int id) {
        return Util.createTo(super.getById(id, id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resto> createWithLocation(@RequestBody RestoTo restoTo) {
       return super.createWithUrl(Util.createNewFromToResto(restoTo), REST_URL, 0);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.deleteById(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody RestoTo restoTo, @PathVariable int id) {
        log.info("update {} with id={}", restoTo, id);
        assureIdConsistent(restoTo, id);
        Resto resto = restoRepository.get(restoTo.id(), id);
        restoRepository.save(Util.updateFromToResto(resto, restoTo), id);

    }

    @GetMapping("/{id}/history")
    public Resto getWithHistory(@PathVariable int id) {
        log.info("getwithHistory {}", id);
        return restoRepository.getWithHistory(id);
    }
}
