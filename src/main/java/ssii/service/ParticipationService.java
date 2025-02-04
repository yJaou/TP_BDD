package ssii.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssii.dao.ParticipationRepository;
import ssii.dao.PersonneRepository;
import ssii.dao.ProjetRepository;
import ssii.entity.Participation;
import ssii.entity.Personne;
import ssii.entity.Projet;

@Service
public class ParticipationService {

    private final ParticipationRepository participationRepository;
    private final PersonneRepository personneRepository;
    private final ProjetRepository projetRepository;

    public ParticipationService(ParticipationRepository participationRepository,
                                PersonneRepository personneRepository,
                                ProjetRepository projetRepository) {
        this.participationRepository = participationRepository;
        this.personneRepository = personneRepository;
        this.projetRepository = projetRepository;
    }

    @Transactional
    public Participation ajouterParticipation(Integer matricule, Integer codeProjet, String role, Float pourcentage) {
        Personne personne = personneRepository.findById(matricule)
                .orElseThrow(() -> new RuntimeException("Personne non trouvée"));

        Projet projet = projetRepository.findById(codeProjet)
                .orElseThrow(() -> new RuntimeException("Projet non trouvé"));

        if (participationRepository.existsByContributeurAndProjet(personne, projet)) {
            throw new RuntimeException("Cette personne participe déjà à ce projet.");
        }

        Participation participation = new Participation(role, pourcentage);
        participation.setContributeur(personne);
        participation.setProjet(projet);

        return participationRepository.save(participation);
    }
}
