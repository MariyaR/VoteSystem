package ru.javawebinar.votesystem.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.javawebinar.votesystem.model.DayMenu;
import ru.javawebinar.votesystem.model.Record;
import ru.javawebinar.votesystem.model.Resto;
import ru.javawebinar.votesystem.model.User;
import ru.javawebinar.votesystem.util.exception.LateVoteException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static ru.javawebinar.votesystem.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.votesystem.util.ValidationUtil.checkNotFoundWithId;

@Repository
public class DayMenuRepository implements ru.javawebinar.votesystem.repository.Repository<DayMenu> {

    public static final LocalDateTime TIME_LIMIT = LocalDate.now().atTime(11, 00);

    @Autowired
    private CrudDayMenuRepository crudDayMenuRepository;

    @Autowired
    private RecordRepository recordRepository;

    @PersistenceContext
    private EntityManager em;

    @CacheEvict(value = "todayMenus", allEntries = true)
    @Transactional
    public DayMenu save(DayMenu menu, int restoId) {
        Assert.notNull(menu, "menu must not be null");
        if (!menu.isNew() && get(menu.getId()).isEmpty()) {
            return null;
        }
        menu.setResto(em.getReference(Resto.class, restoId));
        return crudDayMenuRepository.save(menu);
    }

    @CacheEvict(value = "todayMenus", allEntries = true)
    public void delete(int id, int userId) {
        checkNotFoundWithId(crudDayMenuRepository.delete(id)!=0, id);
    }

    public Optional<DayMenu> get(int id) {
        return crudDayMenuRepository.findById(id);
    }

    public List<DayMenu> getAllEntries(int restoId) {
        return crudDayMenuRepository.getAll(restoId);
    }

    public Optional<DayMenu> getWithResto(int id) {
        return crudDayMenuRepository.getWithResto(id);
    }

    public List<DayMenu> getByDate(LocalDate date) {
        return checkNotFound(crudDayMenuRepository.getByDate(date), "date= " + date);
    }

    @Cacheable("todayMenus")
    public List<DayMenu> getTodayMenus() {
        return crudDayMenuRepository.getByDate(LocalDate.now());
    }

    @Transactional
    public void voteForMenu(int menuId, int userId) {
        Optional<Record> todayUserVote = recordRepository.getUserVote(userId);

        todayUserVote.ifPresent(vote -> {
                    if (LocalDateTime.now().isAfter(TIME_LIMIT)) {
                        throw new LateVoteException("attempt to vote after 11.00");
                    } else {
                        DayMenu unvotedMenu = crudDayMenuRepository.getMenuByRestoId(vote.getResto().getId())
                                .orElseThrow(() -> new IllegalArgumentException("unvoted Menu must not be null"));
                        unvotedMenu.unvote();
                        crudDayMenuRepository.save(unvotedMenu);
                        recordRepository.delete(vote.getId(), userId);
                    }
                }
        );

        Optional<DayMenu> votedMenu = get(menuId);
        votedMenu.ifPresent(menu -> {
            menu.vote();
            crudDayMenuRepository.save(menu);
            recordRepository.save(new Record(LocalDate.now(),
                    em.getReference(Resto.class, menu.getResto().getId()),
                    em.getReference(User.class, userId)), userId);
        });
    }
}
