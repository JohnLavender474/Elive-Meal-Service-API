package main.api.db.tables;

import main.api.exceptions.InvalidInputException;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * A database table holding the usernames and passwords for admins where the username is the id
 * and the passwords are the value.
 */
public class Admins implements DatabaseTable<String> {

    private final Map<String, String> admins = new HashMap<>();

    @Override
    public void add(String username, String password) {
        String encodedPassword = Base64.getEncoder().withoutPadding().encodeToString(password.getBytes());
        admins.put(username, encodedPassword);
    }

    @Override
    public void remove(String username) {
        admins.remove(username);
    }

    @Override
    public String get(String username) {
        if (!admins.containsKey(username)) {
            throw new InvalidInputException("No admin with username: " + username);
        }
        String encodedPassword = admins.get(username);
        byte[] decodedBytes = Base64.getDecoder().decode(encodedPassword);
        return new String(decodedBytes);
    }

}
