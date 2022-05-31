package ru.javawebinar.votesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.votesystem.model.DayMenu;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudDayMenuRepository extends JpaRepository<DayMenu, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM DayMenu dm WHERE dm.id=:id")
    int delete(@Param("id") int id);

    @Override
    @Transactional
    DayMenu save(DayMenu item);

    @Query("SELECT dm FROM DayMenu dm WHERE dm.resto.id=:restoId ORDER BY dm.date DESC")
    List<DayMenu> getAll(@Param("restoId") int restoId);

    @Query("SELECT dm FROM DayMenu dm JOIN FETCH dm.resto WHERE dm.id = ?1")
    Optional<DayMenu> getWithResto(int id);

    @Query("SELECT dm FROM DayMenu dm JOIN FETCH dm.resto WHERE dm.date=:date ORDER BY dm.resto.name")
    List<DayMenu> getByDate(@Param("date") LocalDate date);

    @Query("SELECT dm FROM DayMenu dm  WHERE dm.resto.id=:restoId AND dm.date = CURRENT_DATE")
    Optional<DayMenu> getMenuByRestoId(@Param("restoId") int restoId);
}
