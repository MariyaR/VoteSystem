package ru.javawebinar.votesystem.web.record;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.votesystem.model.Record;
import ru.javawebinar.votesystem.repository.RecordRepository;
import ru.javawebinar.votesystem.to.RecordTo;
import ru.javawebinar.votesystem.util.Util;
import ru.javawebinar.votesystem.web.AbstractController;

import javax.annotation.PostConstruct;
import java.util.List;

import static ru.javawebinar.votesystem.web.SecurityUtil.authUserId;

@RestController
@RequestMapping(value = RecordController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RecordController extends AbstractController<Record> {
    static final String REST_URL = "/profile/history";
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RecordRepository recordRepository;

    @PostConstruct
    public void init(){
        this.repository = recordRepository;
    }

    @GetMapping("/{id}")
    public RecordTo get(@PathVariable int id) {
        return Util.createTo(super.getById(id, authUserId()));
    }

    @GetMapping
    public List<RecordTo> getAll() {
       return Util.getRecordTos(super.getAllEntries(authUserId()));
    }
}