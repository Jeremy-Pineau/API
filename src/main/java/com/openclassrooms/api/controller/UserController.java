package com.openclassrooms.api.controller;

import com.openclassrooms.api.model.Promotion;
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
            return userService.getUser(user.getMail());
        } else {
            return Optional.empty();
        }
    }

    @PostMapping("/login")
    public Optional<User> loginUser(@RequestBody User user) {
        Optional<User> u = userService.getUser(user.getMail());
        /*if (u.isPresent()) {
            if (user.getMdp().equals(u.get().getMdp())) {
                return u;
            }
        }*/
        return u;
    }

    @GetMapping("/all")
    public Iterable<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{mail}")
    public User getUser(@PathVariable("mail") final String mail) {
        Optional<User> user = userService.getUser(mail);
        return user.orElse(null);
    }

    @GetMapping("/historique/{mail}")
    public List<Promotion> getHistoriqueFromUser(@PathVariable("mail") final String mail) {
        Optional<User> u = userService.getUser(mail);
        return u.<List<Promotion>>map(user -> user.getHistorique()).orElse(null);
    }

    @PutMapping("/{mail}")
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

    @DeleteMapping("/{mail}")
    public void deleteUser(@PathVariable("mail") final String mail) {
        userService.deleteUser(mail);
    }
}
