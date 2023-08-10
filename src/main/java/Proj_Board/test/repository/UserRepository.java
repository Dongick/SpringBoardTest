package Proj_Board.test.repository;

import Proj_Board.test.model.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByUserId(String userId);

    Optional<User> findByUser(String id ,String pw);

    void save(User user);
}
