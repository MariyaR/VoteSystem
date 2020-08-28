package ru.javawebinar.votesystem.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.javawebinar.votesystem.model.DayMenu;
import ru.javawebinar.votesystem.model.Record;
import ru.javawebinar.votesystem.model.Resto;
import ru.javawebinar.votesystem.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.votesystem.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.votesystem.util.ValidationUtil.checkNotFoundWithId;

@Repository
public class DayMenuRepository implements ru.javawebinar.votesystem.repository.Repository<DayMenu> {

    @Autowired
    private CrudDayMenuRepository crudDayMenuRepository;

    @Autowired
    private RecordRepository recordRepository;

    @PersistenceContext
    private EntityManager em;


    @Transactional
    public DayMenu save(DayMenu menu, int restoId) {
        Assert.notNull(menu, "menu must not be null");
        if (!menu.isNew() && get(menu.getId(), restoId) == null) {
            return null;
        }
        menu.setResto(em.getReference(Resto.class, restoId));
        return crudDayMenuRepository.save(menu);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(crudDayMenuRepository.delete(id)!=0, id);
    }

    public DayMenu get(int id, int userId) {
        return checkNotFoundWithId(crudDayMenuRepository.findById(id).orElse(null),id);
    }

    public List<DayMenu> getAllEntries(int restoId) {
        return crudDayMenuRepository.getAll(restoId);
    }

    public DayMenu getWithResto(int id) {
        return checkNotFoundWithId(crudDayMenuRepository.getWithResto(id), id);
    }

    public List<DayMenu> getByDate(LocalDate date) {
        return checkNotFound(crudDayMenuRepository.getByDate(date), "date= " + date);
    }

    public List<DayMenu> getTodayMenus() {
        return crudDayMenuRepository.getByDate(LocalDate.now());
    }

    @Transactional
    public String voteForMenu (int menuId, int userId) {
        Record todayUserVote = recordRepository.getUserVote(userId);

       if (todayUserVote!=null) {
           if (LocalDateTime.now().isAfter(LocalDate.now().atTime(11, 00))) {
               return "Sorry, it is too late to change your mind";
           } else {
               DayMenu unvotedMenu = crudDayMenuRepository.getMenuByRestoId(todayUserVote.getResto().getId());
               Assert.notNull(unvotedMenu, "unvotedMenu must not be null");
               unvotedMenu.unvote();
               crudDayMenuRepository.save(unvotedMenu);
               recordRepository.delete(todayUserVote.getId(), userId);
           }

       }
        DayMenu votedMenu = get(menuId, userId);
        votedMenu.vote();
        crudDayMenuRepository.save(votedMenu);
        recordRepository.save(new Record(LocalDate.now(),
                em.getReference(Resto.class, votedMenu.getResto().getId()),
                em.getReference(User.class, userId)), userId);
        return "Thank you! You voted for the menu " + votedMenu.getId();
    }
}
