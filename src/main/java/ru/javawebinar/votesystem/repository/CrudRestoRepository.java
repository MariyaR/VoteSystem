package ru.javawebinar.votesystem.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import ru.javawebinar.votesystem.model.Resto;


@Transactional(readOnly = true)
public interface CrudRestoRepository extends JpaRepository<Resto, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Resto r WHERE r.id=:id")
    int delete(@Param("id") int id);

    //    https://stackoverflow.com/a/46013654/548473
    @EntityGraph(attributePaths = {"history"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT r FROM Resto r WHERE r.id=?1")
    Resto getWitHistory(int id);

}
