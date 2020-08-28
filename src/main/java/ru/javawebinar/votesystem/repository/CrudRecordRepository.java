package ru.javawebinar.votesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.votesystem.model.Record;

import java.util.List;

@Transactional(readOnly = true)
public interface CrudRecordRepository extends JpaRepository<Record, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Record r WHERE r.id=:id AND r.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Override
    @Transactional
    Record save(Record item);

    @Query("SELECT r FROM Record r WHERE r.user.id=:userId ORDER BY r.date DESC")
    List<Record> getAll(@Param("userId") int userId);


    @Query("SELECT r FROM Record r JOIN FETCH r.user WHERE r.id = ?1 and r.user.id = ?2")
    Record getWithUser(int id, int userId);

    @Query("SELECT r FROM Record r WHERE r.user.id =:userId and r.date = CURRENT_DATE ")
    Record getUserVote(@Param("userId") int userId);

    @Query("SELECT r FROM Record r WHERE r.date = CURRENT_DATE ")
    List<Record> getTodayRecords();
}