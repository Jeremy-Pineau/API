package com.openclassrooms.api.controller;

import com.openclassrooms.api.model.User;
import com.openclassrooms.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public Optional<User> createUser(@RequestBody User user) {
        int res = userService.createUser(user);
        if (res == 1) {
            return userService.getUserByMail(user.getMail());
        } else {
            return Optional.empty();
        }
    }

    @PostMapping("/login")
    public Optional<User> loginUser(@RequestBody User user) {
        Optional<User> u = userService.getUserByMail(user.getMail());
        if (u.isPresent() && u.get().getMdp().equals(user.getMdp())) {
            return u;
        }
        return Optional.empty();
    }

    @GetMapping("/all")
    public List<User> getUsers() {
        return (List<User>) userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") final int id) {
        Optional<User> user = userService.getUser(id);
        return user.orElse(null);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable("id") final int id, @RequestBody User user) {
        Optional<User> e = userService.getUser(id);
        if(e.isPresent()) {
            User currentUser = e.get();

            String email = user.getMail();
            if(email != null) {
                currentUser.setMail(email);
            }
            String nom = user.getNom();
            if(nom != null) {
                currentUser.setNom(nom);
            }
            String prenom = user.getPrenom();
            if(prenom != null) {
                currentUser.setPrenom(prenom);
            }
            String adresse = user.getAdresse();
            if(adresse != null) {
                currentUser.setAdresse(adresse);
            }
            String mdp = user.getMdp();
            if(mdp != null) {
                currentUser.setMdp(mdp);
            }

            return userService.saveUser(currentUser);
        } else {
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") final int id) {
        userService.deleteUser(id);
    }
}
