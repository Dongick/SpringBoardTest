package Proj_Board.test.service;

import Proj_Board.test.model.User;
import Proj_Board.test.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByUserId(String id) {
        return userRepository.findByUserId(id).orElse(null);
    }

    @Override
    public User findByUser(String id, String pw) {
        return userRepository.findByUser(id, pw).orElse(null);
    }
}
