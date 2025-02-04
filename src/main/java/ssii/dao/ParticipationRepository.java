package ssii.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ssii.entity.Participation;
import ssii.entity.Personne;
import ssii.entity.Projet;

public interface ParticipationRepository extends JpaRepository<Participation, Integer> {
    boolean existsByContributeurAndProjet(Personne contributeur, Projet projet);
}
