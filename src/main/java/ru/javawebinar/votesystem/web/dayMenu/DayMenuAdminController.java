package ru.javawebinar.votesystem.web.dayMenu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.votesystem.model.DayMenu;
import ru.javawebinar.votesystem.repository.DayMenuRepository;
import ru.javawebinar.votesystem.to.DayMenuTo;
import ru.javawebinar.votesystem.util.Util;
import ru.javawebinar.votesystem.web.AbstractController;

import javax.annotation.PostConstruct;
import java.util.List;

import static ru.javawebinar.votesystem.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.votesystem.util.ValidationUtil.assureRestoIdConsistent;

@RestController
@RequestMapping(value = DayMenuAdminController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DayMenuAdminController extends AbstractController<DayMenu> {
    static final String REST_URL = "/admin/daymenu";

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private DayMenuRepository dayMenuRepository;

    @PostConstruct
    public void init(){
        this.repository = dayMenuRepository;
    }

    @GetMapping("/resto/{restoId}")
    public List<DayMenuTo> getAll(@PathVariable int restoId) {
        return Util.getDayMenuTos(dayMenuRepository.getAllEntries(restoId));
    }

    @GetMapping("/{id}")
    public DayMenuTo get(@PathVariable int id) {
      return Util.createTo(super.getById(id, id));
    }

    @PostMapping(value = "/{restoId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DayMenu> createWithLocation(@RequestBody DayMenuTo dayMenuTo, @PathVariable int restoId) {
       return super.createWithUrl(Util.createNewFromToDayMenu(dayMenuTo), REST_URL, restoId);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.deleteById(id);
    }


    @PutMapping(value = "/{restoId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody DayMenuTo dayMenuTo, @PathVariable int restoId) {
        log.info("update {} with restoId={}", dayMenuTo, restoId);
        assureRestoIdConsistent(dayMenuTo, restoId);
        DayMenu dayMenu = dayMenuRepository.get(dayMenuTo.getId(), restoId);
        dayMenuRepository.save(Util.updateFromToDayMenu(dayMenu, dayMenuTo), restoId);
    }

}
