package com.openclassrooms.api.service;

import com.openclassrooms.api.model.Historique;
import com.openclassrooms.api.model.Promotion;
import com.openclassrooms.api.repository.HistoriqueRepository;
import com.openclassrooms.api.repository.PromotionRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class HistoriqueService {

    @Autowired
    private HistoriqueRepository historiqueRepository;

    public Optional<Historique> getHistorique(final int id) {
        return historiqueRepository.findById(id);
    }

    public Iterable<Historique> getHistoriques() {
        return historiqueRepository.findAll();
    }

    public void deleteHistorique(final int id) {
        historiqueRepository.deleteById(id);
    }

    public Historique saveHistorique(Historique historique) {
        return historiqueRepository.save(historique);
    }

    public int createHistorique(Historique historique) {
        return historiqueRepository.create(historique.getId(), historique.getDateScan(), historique.getIdPromo(), historique.getIdUser());
    }

    public Iterable<Historique> getHistoriquesByUserId(int userId) {
        return historiqueRepository.findAllByUserId(userId);
    }

    public Iterable<Historique> getHistoriquesByPromoId(int promoId) {
        return historiqueRepository.findAllByPromoId(promoId);
    }

    public Historique getHistoriquesByPromoIdAndUserId(int idUser, int idPromo) {
        return historiqueRepository.findAllByPromoIdAndUserId(idUser, idPromo);
    }
}
