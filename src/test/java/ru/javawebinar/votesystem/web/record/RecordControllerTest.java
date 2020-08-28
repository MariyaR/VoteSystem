package ru.javawebinar.votesystem.web.record;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ru.javawebinar.votesystem.repository.RecordRepository;
import ru.javawebinar.votesystem.util.Util;
import ru.javawebinar.votesystem.web.AbstractControllerTest;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.votesystem.RecordTestData.*;
import static ru.javawebinar.votesystem.UserTestData.*;

public class RecordControllerTest extends AbstractControllerTest {

   // @Autowired
   // private RecordRepository recordRepository;

    RecordControllerTest() {
        super(RecordController.REST_URL);
    }

    @Test
    void get() throws Exception {
        perform(doGet(RECORD_ID).basicAuth(USER))
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
    void getNotOwn() throws Exception {
        perform(doGet(RECORD_ID+1).basicAuth(USER))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void getAll() throws Exception {
        perform(doGet().basicAuth(USER))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RECORD_MATCHERS.contentJson(Util.createTo(RECORD_2), Util.createTo(RECORD_1)));
    }
}
