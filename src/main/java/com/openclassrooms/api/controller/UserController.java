package com.openclassrooms.api.controller;

import com.openclassrooms.api.model.User;
import com.openclassrooms.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PostMapping("/user/login")
    public Optional<User> loginUser(@RequestBody User user) {
        System.out.println(user);
        Optional<User> u = userService.getUser(user.getMail());
        /*if (u.isPresent()) {
            if (user.getMdp().equals(u.get().getMdp())) {
                return u;
            }
        }*/
        return u;
    }

    @GetMapping("/users")
    public Iterable<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/user/{mail}")
    public User getUser(@PathVariable("mail") final String mail) {
        Optional<User> employee = userService.getUser(mail);
        return employee.orElse(null);
    }

    @PutMapping("/user/{mail}")
    public User updateUser(@PathVariable("mail") final String mail, @RequestBody User user) {
        Optional<User> e = userService.getUser(mail);
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
            userService.saveUser(currentUser);
            return currentUser;
        } else {
            return null;
        }
    }

    @DeleteMapping("/user/{mail}")
    public void deleteUser(@PathVariable("mail") final String mail) {
        userService.deleteUser(mail);
    }
}
