package ru.javawebinar.votesystem;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import ru.javawebinar.votesystem.repository.DayMenuRepository;

import static java.time.LocalDate.of;
import static ru.javawebinar.votesystem.web.DayMenuTestData.*;

import java.util.Arrays;


public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management
        try (ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            DayMenuRepository repository = (DayMenuRepository) appCtx.getBean("dayMenuRepository");
          //  DayMenuRepository rep2 = appCtx.getBean(DataJpaDayMenuRepository.class);
 //           DataJpaUserRepository rep3 = appCtx.getBean(DataJpaUserRepository.class);
//            Map<Long, String> menu = new HashMap<>();
//            menu.put(10000L, "burger");
//            menu.put(500L, "cake");
//            Resto resto = new Resto(100004, "resto1", "address1");
//            DayMenu daymenu = new DayMenu( LocalDate.now(), menu);
//            System.out.println(repository.save(daymenu, 100005));
//            System.out.println("----------get---------------");
//            System.out.println(repository.get(100024).getMenu().containsKey(500L));
//            System.out.println("----------getWithResto---------------");
//            System.out.println(repository.getWithResto(100024).getResto());
//            System.out.println("----------getAll---------------");
//            System.out.println(repository.getAll(100004));
//            System.out.println("----------delete---------------");
//            System.out.println(repository.getAll(100024));
//            System.out.println("----------getByDate---------------");
//            System.out.println(repository.getByDate(of(2000,1,3)));

//--------------------------restoRepository
//            RestoRepository repository = (RestoRepository) appCtx.getBean("restoRepository");
//            System.out.println("----------getAll---------------");
//            System.out.println(repository.getAll());
//
//            System.out.println("----------get---------------");
//            System.out.println(repository.get(100005));
//
//            System.out.println("----------save---------------");
//            System.out.println(repository.save(new Resto("sushi-shop", "address7")));

//            System.out.println("----------update---------------");
//            Resto updated = repository.get(100006);
//            updated.setName("wok");
//            System.out.println(repository.save(updated));
//
//            System.out.println("----------delete---------------");
//            System.out.println(repository.delete(100024));
//
//            System.out.println("----------getWithHistory---------------");
//            System.out.println(repository.getWithHistory(100005).getHistory());

//            System.out.println("----------voteForResto---------------");
//            System.out.println(repository.voteForResto(100006));
//            System.out.println(repository.voteForResto(100006));
//            System.out.println(repository.voteForResto(100006));

// --------------------------userRepository
//            UserRepository repository = (UserRepository) appCtx.getBean("userRepository");
//
//            System.out.println("----------getAll---------------");
//            System.out.println(repository.getAll());
//
//            System.out.println("----------save---------------");
//            User newUser = UserTestData.getNew();
//            User created = repository.save(new User(newUser));
//            System.out.println(created);
//
//            System.out.println("----------getAll---------------");
//            //repository.delete(100024);
//            System.out.println(repository.getAll());


//            System.out.println("----------get---------------");
//            System.out.println(repository.get(100001));
//
//            System.out.println("----------getByEmail---------------");
//            System.out.println(repository.getByEmail("user1@yandex.ru"));
//
//            System.out.println("----------getwithHistory---------------");
//            System.out.println(repository.getWithHistory(100001));
//
//            System.out.println("----------update---------------");
//            User updated = repository.get(100003);
//            updated.setName("updated");
//            System.out.println(repository.save(updated));
//

// -----------------votingHistory---------recordRepository
//            RecordRepository repository = (RecordRepository) appCtx.getBean("recordRepository");
//            UserRepository repUser = (UserRepository) appCtx.getBean("userRepository");
//            RestoRepository repResto = (RestoRepository) appCtx.getBean("restoRepository");
//
//            System.out.println("----------getAll---------------");
//            System.out.println(repository.getAll(100003));
//
//            System.out.println("----------get---------------");
//            System.out.println(repository.get(100023,100003));
//
//            System.out.println("----------getWithUser---------------");
//            System.out.println(repository.getWithUser(100023,100003).getUser());
//
//            System.out.println("----------save---------------");
//            System.out.println(repository.save(new Record(LocalDate.now(), repResto.get(100006), repUser.get(100000)), 100000));

            // -----------------voteForMenu---------recordRepository

//            RecordRepository repository = (RecordRepository) appCtx.getBean("recordRepository");
//            UserRepository repUser = (UserRepository) appCtx.getBean("userRepository");
//            RestoRepository repResto = (RestoRepository) appCtx.getBean("restoRepository");
//            DayMenuRepository menuRepo = (DayMenuRepository) appCtx.getBean("dayMenuRepository");
//            menuRepo.save(daymenu, 100004);
//            System.out.println(menuRepo.voteForMenu(100024, 100001));
//            System.out.println(menuRepo.voteForMenu(100024, 100002));
//            System.out.println(menuRepo.getTodayMenus());
//            System.out.println(repository.getTodayRecords());
//            Map<Long, String> menu2 = new HashMap<>();
//            menu.put(10000L, "sushi");
//            menu.put(500L, "cake");
//            DayMenu daymenu2 = new DayMenu( LocalDate.now(), menu);
//            menuRepo.save(daymenu2, 100005);
//           System.out.println(menuRepo.voteForMenu(100027, 100001));
//            System.out.println(menuRepo.getTodayMenus());
//            System.out.println(repository.getTodayRecords());

           // DayMenuTestData dt = new DayMenuTestData();
            System.out.println(DAY_MENU_TODAY_1);


        }
    }
}


