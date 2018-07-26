package ftg.ps.project.ms.acteur.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A AnimateurFournisseur.
 */
@Entity
@Table(name = "animateur_fournisseur")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AnimateurFournisseur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "niveau_agreement")
    private Integer niveauAgreement;

    @OneToMany(mappedBy = "animateurFournisseur")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Fournisseur> anFournisseurs = new HashSet<>();

    @OneToMany(mappedBy = "animateurFournisseur")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Animateur> anAnimateurs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNiveauAgreement() {
        return niveauAgreement;
    }

    public AnimateurFournisseur niveauAgreement(Integer niveauAgreement) {
        this.niveauAgreement = niveauAgreement;
        return this;
    }

    public void setNiveauAgreement(Integer niveauAgreement) {
        this.niveauAgreement = niveauAgreement;
    }

    public Set<Fournisseur> getAnFournisseurs() {
        return anFournisseurs;
    }

    public AnimateurFournisseur anFournisseurs(Set<Fournisseur> fournisseurs) {
        this.anFournisseurs = fournisseurs;
        return this;
    }

    public AnimateurFournisseur addAnFournisseur(Fournisseur fournisseur) {
        this.anFournisseurs.add(fournisseur);
        fournisseur.setAnimateurFournisseur(this);
        return this;
    }

    public AnimateurFournisseur removeAnFournisseur(Fournisseur fournisseur) {
        this.anFournisseurs.remove(fournisseur);
        fournisseur.setAnimateurFournisseur(null);
        return this;
    }

    public void setAnFournisseurs(Set<Fournisseur> fournisseurs) {
        this.anFournisseurs = fournisseurs;
    }

    public Set<Animateur> getAnAnimateurs() {
        return anAnimateurs;
    }

    public AnimateurFournisseur anAnimateurs(Set<Animateur> animateurs) {
        this.anAnimateurs = animateurs;
        return this;
    }

    public AnimateurFournisseur addAnAnimateur(Animateur animateur) {
        this.anAnimateurs.add(animateur);
        animateur.setAnimateurFournisseur(this);
        return this;
    }

    public AnimateurFournisseur removeAnAnimateur(Animateur animateur) {
        this.anAnimateurs.remove(animateur);
        animateur.setAnimateurFournisseur(null);
        return this;
    }

    public void setAnAnimateurs(Set<Animateur> animateurs) {
        this.anAnimateurs = animateurs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AnimateurFournisseur animateurFournisseur = (AnimateurFournisseur) o;
        if (animateurFournisseur.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), animateurFournisseur.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AnimateurFournisseur{" +
            "id=" + getId() +
            ", niveauAgreement=" + getNiveauAgreement() +
            "}";
    }
}
