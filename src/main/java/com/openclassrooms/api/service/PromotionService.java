package com.openclassrooms.api.service;

import com.openclassrooms.api.model.Promotion;
import com.openclassrooms.api.repository.PromotionRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    public Optional<Promotion> getPromotion(final int id) {
        return promotionRepository.findById(id);
    }

    public Iterable<Promotion> getPromotions() {
        return promotionRepository.findAll();
    }

    public void deletePromotion(final int id) {
        promotionRepository.deleteById(id);
    }

    public Promotion savePromotion(Promotion promotion) {
        return promotionRepository.save(promotion);
    }

    public int createPromotion(Promotion promotion) {
        return promotionRepository.create(promotion.getId(), promotion.getCodePromo(), promotion.getDetail());
    }
}
