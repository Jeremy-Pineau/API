package com.openclassrooms.api.service;

import com.openclassrooms.api.model.User;
import com.openclassrooms.api.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUser(final String mail) {
        return userRepository.findByMail(mail);
    }

    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(final String mail) {
        userRepository.deleteByMail(mail);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

}
