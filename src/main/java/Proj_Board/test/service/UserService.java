package Proj_Board.test.service;

import Proj_Board.test.model.User;

public interface UserService {
    void createUser(User user);

    User findByUser(String id, String pw);

    User findByUserId(String id);
}
