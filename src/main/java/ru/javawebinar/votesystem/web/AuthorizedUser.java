package ru.javawebinar.votesystem.web;


import org.springframework.lang.NonNull;
import ru.javawebinar.votesystem.model.User;
import ru.javawebinar.votesystem.to.UserTo;
import ru.javawebinar.votesystem.util.UserUtil;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User {
    private static final long serialVersionUID = 1L;

    private User user;

    public AuthorizedUser(@NonNull User user) {
        super(user.getEmail(), user.getPassword(), true, true, true, true, user.getRoles());
        this.user = user;
    }

    public int getId() {
        return user.id();
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return user.toString();
    }
}