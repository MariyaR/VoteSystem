package ru.javawebinar.votesystem.web;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javawebinar.votesystem.web.user.AdminController;

@RestController
@RequestMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
public class RootController {

    @GetMapping("/")
    public String root() {
        return "redirect:todayMenus";
    }

//    @Secured("ROLE_ADMIN")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/testusers") //, produces = MediaType.TEXT_PLAIN_VALUE)
    public String getUsers() {
        return "users";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping("/history")
    public String getHistory() {
        return "records";
    }
}
