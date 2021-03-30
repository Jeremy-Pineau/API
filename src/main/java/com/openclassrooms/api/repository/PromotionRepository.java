package com.openclassrooms.api.repository;

import com.openclassrooms.api.model.Promotion;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface PromotionRepository extends CrudRepository<Promotion, Integer> {

    @Query("FROM Promotion")
    List<Promotion> findAll();

    Optional<Promotion> findById(int id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Promotion WHERE id = :id")
    int deleteById(int id);

    @Transactional
    @Modifying
    @Query(value = "insert into promotions (id, code_promo, detail) values (:id, :codePromo, :detail)",
            nativeQuery = true)
    int create(int id, String codePromo, String detail);

}
