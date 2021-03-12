package com.openclassrooms.api.repository;

import com.openclassrooms.api.model.Historique;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface HistoriqueRepository extends CrudRepository<Historique, Integer> {

    @Query("FROM Historique")
    List<Historique> findAll();

    Optional<Historique> findById(int id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Historique WHERE id = :id")
    int deleteById(int id);

    @Transactional
    @Modifying
    @Query(value = "insert into historique (id, date_scan, id_promo, id_user) values (:id, :dateScan, :idPromotion, :idUser)",
            nativeQuery = true)
    int create(int id, Date dateScan, int idPromotion, int idUser);

    @Query("FROM Historique where id_user = :userId order by date_scan desc")
    Iterable<Historique> findAllByUserId(int userId);

    @Query("FROM Historique where id_promo = :promoId")
    Iterable<Historique> findAllByPromoId(int promoId);

    @Query("FROM Historique where id_promo = :idPromo and id_user = :idUser")
    Historique findAllByPromoIdAndUserId(int idUser, int idPromo);
}
