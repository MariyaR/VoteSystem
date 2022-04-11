package ru.javawebinar.votesystem.web.dayMenu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.votesystem.model.DayMenu;
import ru.javawebinar.votesystem.repository.DayMenuRepository;
import ru.javawebinar.votesystem.web.AbstractControllerTest;

import javax.annotation.PostConstruct;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.votesystem.RestoTestData.*;
import static ru.javawebinar.votesystem.UserTestData.USER;
import static ru.javawebinar.votesystem.UserTestData.USER_ID;
import static ru.javawebinar.votesystem.web.DayMenuTestData.*;

public class DayMenuUserControllerTest extends AbstractControllerTest {

    private DayMenu todayMenu1, todayMenu2;

    DayMenuUserControllerTest() {
        super(DayMenuUserController.REST_URL);
    }

    @Autowired
    private DayMenuRepository repository;

    @PostConstruct
    private void postConstruct() {
        todayMenu1 = repository.save(DAY_MENU_TODAY_1, RESTO_1.getId());
        todayMenu2 = repository.save(DAY_MENU_TODAY_2, RESTO_2.getId());
        repository.save(DAY_MENU_TODAY_3, RESTO_3.getId());
        DAY_MENU_TODAY_1.setResto(RESTO_1);
        DAY_MENU_TODAY_2.setResto(RESTO_2);
        DAY_MENU_TODAY_3.setResto(RESTO_3);
    }

    @Test
    void getTodayMenues() throws Exception {
        perform(doGet().basicAuth(USER))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DayMenu_MATCHERS.contentJson(DAY_MENU_TODAY_1, DAY_MENU_TODAY_2, DAY_MENU_TODAY_3));
    }

    @Test
    void voteForMenu() throws Exception {
        perform(doGet("{menuId}/vote", todayMenu1.getId()).basicAuth(USER))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string("Thank you! You have voted for the menu " + todayMenu1.getId()));
        DayMenu menu = repository.get(todayMenu1.getId(), USER_ID);
        assertThat(menu.getCounter()).isEqualTo(1);
    }

    @Test
    void reVoteForMenu() throws Exception {
        perform(doGet("{menuId}/vote", todayMenu1.getId()).basicAuth(USER))
                .andExpect(status().isOk());

        final ResultActions result = perform(doGet("{menuId}/vote", todayMenu2.getId()).basicAuth(USER))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        DayMenu menu1 = repository.get(todayMenu1.getId(), USER_ID);
        DayMenu menu2 = repository.get(todayMenu2.getId(), USER_ID);

        if (LocalTime.now().isBefore(LocalTime.of(11, 0))){
            result.andExpect(content().string("Thank you! You have voted for the menu " + todayMenu2.getId()));
            assertThat(menu1.getCounter()).isEqualTo(0);
            assertThat(menu2.getCounter()).isEqualTo(1);
        } else {
            result.andExpect(content().string("Sorry, it is too late to change your mind"));
            assertThat(menu1.getCounter()).isEqualTo(1);
            assertThat(menu2.getCounter()).isEqualTo(0);
        }
    }


}
