package ssii.dao;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ssii.dao.ParticipationRepository;
import ssii.dao.PersonneRepository;
import ssii.dao.ProjetRepository;
import ssii.entity.Participation;
import ssii.entity.Personne;
import ssii.entity.Projet;
import ssii.service.ParticipationService;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParticipationServiceTest {

    @Test
    void testAjouterParticipation() {
        // Mocks
        Personne personne = new Personne("Dupont", "Jean", "Développeur");
        personne.setMatricule(1);

        Projet projet = new Projet("Projet X", LocalDate.now());
        projet.setCode(1);

        ParticipationRepository participationRepository = mock(ParticipationRepository.class);
        PersonneRepository personneRepository = mock(PersonneRepository.class);
        ProjetRepository projetRepository = mock(ProjetRepository.class);

        when(personneRepository.findById(1)).thenReturn(Optional.of(personne));
        when(projetRepository.findById(1)).thenReturn(Optional.of(projet));
        when(participationRepository.existsByContributeurAndProjet(any(), any())).thenReturn(false);
        when(participationRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        ParticipationService service = new ParticipationService(participationRepository, personneRepository, projetRepository);

        Participation participation = service.ajouterParticipation(1, 1, "Manager", 50f);
        assertNotNull(participation);
        assertEquals("Manager", participation.getRole());
        assertEquals(50f, participation.getPourcentage());
        assertEquals(personne, participation.getContributeur());
        assertEquals(projet, participation.getProjet());

        verify(participationRepository, times(1)).save(any(Participation.class));
    }

    @Test
    void testAjouterParticipation_DejaExistante() {
        // Mocks
        Personne personne = new Personne("Dupont", "Jean", "Développeur");
        personne.setMatricule(1);

        Projet projet = new Projet("Projet X", LocalDate.now());
        projet.setCode(1);

        ParticipationRepository participationRepository = mock(ParticipationRepository.class);
        PersonneRepository personneRepository = mock(PersonneRepository.class);
        ProjetRepository projetRepository = mock(ProjetRepository.class);

        when(personneRepository.findById(1)).thenReturn(Optional.of(personne));
        when(projetRepository.findById(1)).thenReturn(Optional.of(projet));
        when(participationRepository.existsByContributeurAndProjet(personne, projet)).thenReturn(true);

        ParticipationService service = new ParticipationService(participationRepository, personneRepository, projetRepository);

        // Vérifie que l'exception est bien levée
        Exception exception = assertThrows(RuntimeException.class, () ->
                service.ajouterParticipation(1, 1, "Manager", 50f)
        );

        assertEquals("Cette personne participe déjà à ce projet.", exception.getMessage());

        verify(participationRepository, never()).save(any(Participation.class));
    }

    @Test
    void testAjouterParticipation_PersonneNonExistante() {
        // Mocks
        Projet projet = new Projet("Projet Y", LocalDate.now());
        projet.setCode(2);

        ParticipationRepository participationRepository = mock(ParticipationRepository.class);
        PersonneRepository personneRepository = mock(PersonneRepository.class);
        ProjetRepository projetRepository = mock(ProjetRepository.class);

        when(personneRepository.findById(2)).thenReturn(Optional.empty());
        when(projetRepository.findById(2)).thenReturn(Optional.of(projet));

        ParticipationService service = new ParticipationService(participationRepository, personneRepository, projetRepository);

        // Vérifie que l'exception est bien levée
        Exception exception = assertThrows(RuntimeException.class, () ->
                service.ajouterParticipation(2, 2, "Analyste", 40f)
        );

        assertEquals("Personne non trouvée", exception.getMessage());

        verify(participationRepository, never()).save(any(Participation.class));
    }


}
