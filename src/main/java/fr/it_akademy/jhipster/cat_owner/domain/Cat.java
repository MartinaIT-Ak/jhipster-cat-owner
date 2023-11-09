package fr.it_akademy.jhipster.cat_owner.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Cat.
 */
@Entity
@Table(name = "cat")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Cat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "race")
    private String race;

    @Column(name = "age")
    private Long age;

    @Column(name = "healt_status")
    private String healtStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "cats", "dogs", "owners" }, allowSetters = true)
    private Veterinary veterinary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "cats", "dogs", "veterinaries" }, allowSetters = true)
    private Owner owner;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Cat id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Cat name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRace() {
        return this.race;
    }

    public Cat race(String race) {
        this.setRace(race);
        return this;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public Long getAge() {
        return this.age;
    }

    public Cat age(Long age) {
        this.setAge(age);
        return this;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getHealtStatus() {
        return this.healtStatus;
    }

    public Cat healtStatus(String healtStatus) {
        this.setHealtStatus(healtStatus);
        return this;
    }

    public void setHealtStatus(String healtStatus) {
        this.healtStatus = healtStatus;
    }

    public Veterinary getVeterinary() {
        return this.veterinary;
    }

    public void setVeterinary(Veterinary veterinary) {
        this.veterinary = veterinary;
    }

    public Cat veterinary(Veterinary veterinary) {
        this.setVeterinary(veterinary);
        return this;
    }

    public Owner getOwner() {
        return this.owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Cat owner(Owner owner) {
        this.setOwner(owner);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cat)) {
            return false;
        }
        return getId() != null && getId().equals(((Cat) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cat{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", race='" + getRace() + "'" +
            ", age=" + getAge() +
            ", healtStatus='" + getHealtStatus() + "'" +
            "}";
    }
}
