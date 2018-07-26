package ftg.ps.project.ms.acteur.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Animateur.
 */
@Entity
@Table(name = "animateur")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Animateur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "animateur_id")
    private Long animateurId;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "email")
    private String email;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "user_created")
    private Long userCreated;

    @Column(name = "user_last_modif")
    private Long userLastModif;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @Column(name = "date_last_modif")
    private LocalDate dateLastModif;

    @OneToOne
    @JoinColumn(unique = true)
    private ActeurType acteurTypeAnimateur;

    @OneToMany(mappedBy = "animateur")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Contact> ancontacts = new HashSet<>();

    @ManyToOne
    private Agreement agreement;

    @ManyToOne
    private AnimateurFournisseur animateurFournisseur;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAnimateurId() {
        return animateurId;
    }

    public Animateur animateurId(Long animateurId) {
        this.animateurId = animateurId;
        return this;
    }

    public void setAnimateurId(Long animateurId) {
        this.animateurId = animateurId;
    }

    public String getType() {
        return type;
    }

    public Animateur type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNom() {
        return nom;
    }

    public Animateur nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Animateur prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public Animateur email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public Animateur telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public Animateur userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserLastModif() {
        return userLastModif;
    }

    public Animateur userLastModif(Long userLastModif) {
        this.userLastModif = userLastModif;
        return this;
    }

    public void setUserLastModif(Long userLastModif) {
        this.userLastModif = userLastModif;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Animateur dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateLastModif() {
        return dateLastModif;
    }

    public Animateur dateLastModif(LocalDate dateLastModif) {
        this.dateLastModif = dateLastModif;
        return this;
    }

    public void setDateLastModif(LocalDate dateLastModif) {
        this.dateLastModif = dateLastModif;
    }

    public ActeurType getActeurTypeAnimateur() {
        return acteurTypeAnimateur;
    }

    public Animateur acteurTypeAnimateur(ActeurType acteurType) {
        this.acteurTypeAnimateur = acteurType;
        return this;
    }

    public void setActeurTypeAnimateur(ActeurType acteurType) {
        this.acteurTypeAnimateur = acteurType;
    }

    public Set<Contact> getAncontacts() {
        return ancontacts;
    }

    public Animateur ancontacts(Set<Contact> contacts) {
        this.ancontacts = contacts;
        return this;
    }

    public Animateur addAncontacts(Contact contact) {
        this.ancontacts.add(contact);
        contact.setAnimateur(this);
        return this;
    }

    public Animateur removeAncontacts(Contact contact) {
        this.ancontacts.remove(contact);
        contact.setAnimateur(null);
        return this;
    }

    public void setAncontacts(Set<Contact> contacts) {
        this.ancontacts = contacts;
    }

    public Agreement getAgreement() {
        return agreement;
    }

    public Animateur agreement(Agreement agreement) {
        this.agreement = agreement;
        return this;
    }

    public void setAgreement(Agreement agreement) {
        this.agreement = agreement;
    }

    public AnimateurFournisseur getAnimateurFournisseur() {
        return animateurFournisseur;
    }

    public Animateur animateurFournisseur(AnimateurFournisseur animateurFournisseur) {
        this.animateurFournisseur = animateurFournisseur;
        return this;
    }

    public void setAnimateurFournisseur(AnimateurFournisseur animateurFournisseur) {
        this.animateurFournisseur = animateurFournisseur;
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
        Animateur animateur = (Animateur) o;
        if (animateur.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), animateur.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Animateur{" +
            "id=" + getId() +
            ", animateurId=" + getAnimateurId() +
            ", type='" + getType() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", email='" + getEmail() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userLastModif=" + getUserLastModif() +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateLastModif='" + getDateLastModif() + "'" +
            "}";
    }
}
