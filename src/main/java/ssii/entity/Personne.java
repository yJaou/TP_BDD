package ssii.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
public class Personne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer matricule;

    @NonNull
    private String nom;

    @NonNull
    private String prenom;

    @NonNull
    private String poste;

    @OneToMany(mappedBy = "contributeur", cascade = CascadeType.ALL)
    private List<Participation> participations;

    @ManyToOne
    @JoinColumn(name = "superieur_id")
    private Personne superieur;

    @OneToMany(mappedBy = "superieur")
    private List<Personne> subordonnes;
}