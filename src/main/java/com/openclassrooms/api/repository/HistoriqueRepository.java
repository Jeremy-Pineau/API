package com.openclassrooms.api.repository;

import com.openclassrooms.api.model.Historique;
import com.openclassrooms.api.model.Promotion;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface HistoriqueRepository extends CrudRepository<Historique, Long> {

    @Query("FROM Promotion")
    List<Historique> findAll();

    Optional<Historique> findById(int id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Historique WHERE id = :id")
    int deleteById(int id);

    @Transactional
    @Modifying
    @Query(value = "insert into historique (id, idPromo, mail) values (:id, :idPromo, :idUser)",
            nativeQuery = true)
    int create(int id, int idPromo, int idUser);

}
