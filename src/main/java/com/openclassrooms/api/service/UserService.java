package com.openclassrooms.api.service;

import com.openclassrooms.api.model.Promotion;
import com.openclassrooms.api.model.User;
import com.openclassrooms.api.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Data
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUser(final int id) {
        return userRepository.findById(id);
    }

    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(final int id) {
        userRepository.deleteById(id);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public int createUser(User user) {
        return userRepository.create(user.getId(), user.getMail(), user.getNom(), user.getPrenom(), user.getAdresse(), user.getMdp());
    }
}
