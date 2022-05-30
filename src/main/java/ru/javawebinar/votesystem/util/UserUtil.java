package ru.javawebinar.votesystem.util;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import ru.javawebinar.votesystem.model.Role;
import ru.javawebinar.votesystem.model.User;
import ru.javawebinar.votesystem.to.UserTo;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class UserUtil {

    public static User createNewFromTo(UserTo userTo) {
        return new User(null, userTo.getName(), userTo.getEmail().toLowerCase(), userTo.getPassword(), Role.USER);
    }

    public static UserTo createTo(User user) {
        return new UserTo(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getRegistered(), user.getRoles());
    }

    public static List<UserTo> getTos(Collection<User> users) {
        return users.stream()
                .map(user -> createTo(user))
                .collect(Collectors.toList());
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setPassword(userTo.getPassword());
        user.setRoles(userTo.getRoles());
        return user;
    }

    public static User prepareToSave(User user, PasswordEncoder passwordEncoder) {
        String password = user.getPassword();
        user.setPassword(StringUtils.hasText(password) ? passwordEncoder.encode(password) : password);
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }
}