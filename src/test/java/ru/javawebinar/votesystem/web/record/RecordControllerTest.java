package ru.javawebinar.votesystem.web.record;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import ru.javawebinar.votesystem.util.Util;
import ru.javawebinar.votesystem.web.AbstractControllerTest;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.votesystem.RecordTestData.*;
import static ru.javawebinar.votesystem.UserTestData.*;

public class RecordControllerTest extends AbstractControllerTest {

    RecordControllerTest() {
        super(RecordController.REST_URL);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(doGet(RECORD_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RECORD_MATCHERS.contentJson(Util.createTo(RECORD_1)));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(doGet(RECORD_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getNotOwn() throws Exception {
        perform(doGet(RECORD_ID+1))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAll() throws Exception {
        perform(doGet())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RECORD_MATCHERS.contentJson(Util.createTo(RECORD_2), Util.createTo(RECORD_1)));
    }
}
