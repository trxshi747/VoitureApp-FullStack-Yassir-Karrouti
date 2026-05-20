package org.cours.springdatarest.web;

import java.util.Optional;

import org.cours.springdatarest.modele.Proprietaire;
import org.cours.springdatarest.modele.ProprietaireRepo;
import org.cours.springdatarest.modele.Voiture;
import org.cours.springdatarest.modele.VoitureRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/voitures")
public class VoitureController {
    @Autowired
    private VoitureRepo voitureRepo;
    @Autowired
    private ProprietaireRepo proprietaireRepo;

    @GetMapping
    public Iterable<Voiture> getVoitures(){
        return voitureRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Voiture> getVoiture(@PathVariable Long id) {
        return voitureRepo.findById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Voiture> addVoiture(@RequestBody Voiture voiture) {
        Proprietaire proprietaire = findDefaultProprietaire();
        if (proprietaire == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        voiture.setProprietaire(proprietaire);
        Voiture savedVoiture = voitureRepo.save(voiture);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVoiture);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Voiture> updateVoiture(@PathVariable Long id, @RequestBody Voiture voiture) {
        Optional<Voiture> voitureData = voitureRepo.findById(id);

        if (voitureData.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Voiture voitureToUpdate = voitureData.get();
        voitureToUpdate.setMarque(voiture.getMarque());
        voitureToUpdate.setModele(voiture.getModele());
        voitureToUpdate.setCouleur(voiture.getCouleur());
        voitureToUpdate.setAnnee(voiture.getAnnee());
        voitureToUpdate.setPrix(voiture.getPrix());

        Voiture updatedVoiture = voitureRepo.save(voitureToUpdate);
        return ResponseEntity.ok(updatedVoiture);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Voiture> deleteVoiture(@PathVariable Long id) {
        Optional<Voiture> voitureData = voitureRepo.findById(id);

        if (voitureData.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Voiture voiture = voitureData.get();
        voitureRepo.deleteById(id);
        return ResponseEntity.ok(voiture);
    }

    private Proprietaire findDefaultProprietaire() {
        var proprietaires = proprietaireRepo.findAll().iterator();
        return proprietaires.hasNext() ? proprietaires.next() : null;
    }
}
