package com.openclassrooms.api.controller;

import com.openclassrooms.api.model.Promotion;
import com.openclassrooms.api.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/promotion")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @PostMapping
    public Optional<Promotion> createPromotion(@RequestBody Promotion promotion) {
        int res = promotionService.createPromotion(promotion);
        if (res == 1) {
            return promotionService.getPromotion(promotion.getId());
        } else {
            return Optional.empty();
        }
    }

    @GetMapping("/all")
    public Iterable<Promotion> getPromotions() {
        return promotionService.getPromotions();
    }

    @GetMapping("/{id}")
    public Promotion getPromotion(@PathVariable("id") final int id) {
        Optional<Promotion> promo = promotionService.getPromotion(id);
        return promo.orElse(null);
    }

    @PutMapping("/{id}")
    public Promotion updatePromotion(@PathVariable("id") final int id, @RequestBody Promotion promotion) {
        Optional<Promotion> p = promotionService.getPromotion(id);
        if(p.isPresent()) {
            Promotion currentPromotion = p.get();

            String codePromo = promotion.getCodePromo();
            if(codePromo != null) {
                currentPromotion.setCodePromo(codePromo);
            }
            String detail = promotion.getDetail();
            if(detail != null) {
                currentPromotion.setDetail(detail);
            }

            return promotionService.savePromotion(currentPromotion);
        } else {
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public void deletePromotion(@PathVariable("id") final int id) {
        promotionService.deletePromotion(id);
    }
}
