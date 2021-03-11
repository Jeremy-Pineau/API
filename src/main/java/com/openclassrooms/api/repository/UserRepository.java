package com.openclassrooms.api.repository;

import com.openclassrooms.api.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    @Query("FROM User WHERE id = :id")
    Optional<User> findById(int id);

    @Query("FROM User WHERE mail = :mail")
    Optional<User> findByMail(String mail);

    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id = :id")
    int deleteById(int id);

    @Transactional
    @Modifying
    @Query(value = "insert into Users (id, mail, nom, prenom, adresse, mdp) values (:id, :mail, :nom, :prenom, :adresse, :mdp)",
            nativeQuery = true)
    int create(int id, String mail, String nom, String prenom, String adresse, String mdp);
}
