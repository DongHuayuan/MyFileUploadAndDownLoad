package tech.six6.service;

import tech.six6.entity.User;

public interface UserService {

    User login(String username, String password);

}
