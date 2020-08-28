package ru.javawebinar.votesystem.web.dayMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.votesystem.model.DayMenu;
import ru.javawebinar.votesystem.repository.DayMenuRepository;
import java.util.List;
import static ru.javawebinar.votesystem.web.SecurityUtil.authUserId;

@RestController
@RequestMapping(value = DayMenuUserController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DayMenuUserController {
    static final String REST_URL = "/user/daymenu";

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private DayMenuRepository dayMenuRepository;

    @GetMapping
    public List<DayMenu> getTodayMenus() {
        log.info("getTodayMenus");
        return dayMenuRepository.getTodayMenus();
    }

    @GetMapping("/{menuId}")
    public String voteForMenu(@PathVariable int menuId) {
        log.info("user{} votes for menu {}", authUserId(), menuId);
        return dayMenuRepository.voteForMenu(menuId, authUserId());
    }

}
