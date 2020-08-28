package ru.javawebinar.votesystem;



import ru.javawebinar.votesystem.model.Record;
import ru.javawebinar.votesystem.model.Resto;
import ru.javawebinar.votesystem.model.AbstractBaseEntity;
import ru.javawebinar.votesystem.to.RecordTo;

import java.time.LocalDate;
import java.util.List;

import static java.time.LocalDate.of;
import static ru.javawebinar.votesystem.RestoTestData.RESTO_1;
import static ru.javawebinar.votesystem.RestoTestData.RESTO_2;
import static ru.javawebinar.votesystem.UserTestData.USER;

public class RecordTestData {
    public static final int RECORD_ID = AbstractBaseEntity.START_SEQ + 16;
    public static final int ADMIN_RECORD_ID = AbstractBaseEntity.START_SEQ + 9;

    public static final Record RECORD_1 = new Record(RECORD_ID, LocalDate.of(2000, 01, 01), RESTO_1, USER );
    public static final Record RECORD_2 = new Record(RECORD_ID + 4, LocalDate.of(2000, 01, 02), RESTO_2, USER );


    public static TestMatchers<RecordTo> RECORD_MATCHERS = TestMatchers.useFieldsComparator(RecordTo.class);
    //public static TestMatchers<MealTo> MEAL_TO_MATCHERS = TestMatchers.useEquals(MealTo.class);
}
