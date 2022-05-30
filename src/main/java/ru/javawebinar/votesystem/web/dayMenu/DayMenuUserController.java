package ru.javawebinar.votesystem.web.dayMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.votesystem.model.DayMenu;
import ru.javawebinar.votesystem.repository.DayMenuRepository;
import ru.javawebinar.votesystem.web.AuthorizedUser;

import java.util.List;

@RestController
@RequestMapping(value = DayMenuUserController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DayMenuUserController {
    static final String REST_URL = "/profile/today-menus";

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private DayMenuRepository dayMenuRepository;

    @GetMapping
    public List<DayMenu> getTodayMenus() {
        log.info("getTodayMenus");
        return dayMenuRepository.getTodayMenus();
    }

    @PostMapping("/{menuId}/vote")
    public void voteForMenu(@PathVariable int menuId, @AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("user{} votes for menu {}", authUser.getId(), menuId);
        dayMenuRepository.voteForMenu(menuId, authUser.getId());
    }

}
