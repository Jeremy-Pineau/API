package com.openclassrooms.api.controller;

import com.openclassrooms.api.model.Historique;
import com.openclassrooms.api.service.HistoriqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/historique")
public class HistoriqueController {

    @Autowired
    private HistoriqueService historiqueService;

    @PostMapping
    public Historique createHistorique(@RequestBody Historique historique) {
        if (historiqueService.createHistorique(historique) == 1) {
            return historiqueService.getHistoriquesByPromoIdAndUserId(historique.getIdUser(), historique.getIdPromo());
        }
        return null;
    }

    @GetMapping("/all")
    public List<Historique> getHistoriques() {
        return (List<Historique>) historiqueService.getHistoriques();
    }

    @GetMapping("/allByUserId/{userId}")
    public Iterable<Historique> getHistoriquesByUserId(@PathVariable("userId") final int userId) {
        return historiqueService.getHistoriquesByUserId(userId);
    }

    @GetMapping("/allByPromoId/{promoId}")
    public Iterable<Historique> getHistoriquesByPromoId(@PathVariable("promoId") final int promoId) {
        return historiqueService.getHistoriquesByPromoId(promoId);
    }

    @GetMapping("/allByPromoAndUser")
    public Historique getHistoriquesByPromoIdAndUserId(@PathParam("idUser") final int idUser, @PathParam("idPromo") final int idPromo) {
        return historiqueService.getHistoriquesByPromoIdAndUserId(idUser, idPromo);
    }

    @GetMapping("/{id}")
    public Historique getHistorique(@PathVariable("id") final int id) {
        Optional<Historique> historique = historiqueService.getHistorique(id);
        return historique.orElse(null);
    }

    @PutMapping("/{id}")
    public Historique updateHistorique(@PathVariable("id") final int id, @RequestBody Historique historique) {
        Optional<Historique> h = historiqueService.getHistorique(id);
        if(h.isPresent()) {
            Historique currentHistorique = h.get();

            int idUser = historique.getIdUser();
            if(idUser != 0) {
                currentHistorique.setIdUser(idUser);
            }
            int idPromo = historique.getIdPromo();
            if(idPromo != 0) {
                currentHistorique.setIdPromo(idPromo);
            }
            Date date = historique.getDateScan();
            if(date.before(Date.from(Instant.now()))) {
                currentHistorique.setDateScan(date);
            }

            return historiqueService.saveHistorique(currentHistorique);
        } else {
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public void deleteHistorique(@PathVariable("id") final int id) {
        historiqueService.deleteHistorique(id);
    }
}
