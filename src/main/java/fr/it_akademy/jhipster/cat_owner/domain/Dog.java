package fr.it_akademy.jhipster.cat_owner.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Dog.
 */
@Entity
@Table(name = "dog")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Dog implements Serializable {

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

    @Column(name = "health_status")
    private String healthStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "cats", "dogs", "veterinaries" }, allowSetters = true)
    private Owner owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "cats", "dogs", "owners" }, allowSetters = true)
    private Veterinary veterinary;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Dog id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Dog name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRace() {
        return this.race;
    }

    public Dog race(String race) {
        this.setRace(race);
        return this;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public Long getAge() {
        return this.age;
    }

    public Dog age(Long age) {
        this.setAge(age);
        return this;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getHealthStatus() {
        return this.healthStatus;
    }

    public Dog healthStatus(String healthStatus) {
        this.setHealthStatus(healthStatus);
        return this;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public Owner getOwner() {
        return this.owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Dog owner(Owner owner) {
        this.setOwner(owner);
        return this;
    }

    public Veterinary getVeterinary() {
        return this.veterinary;
    }

    public void setVeterinary(Veterinary veterinary) {
        this.veterinary = veterinary;
    }

    public Dog veterinary(Veterinary veterinary) {
        this.setVeterinary(veterinary);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dog)) {
            return false;
        }
        return getId() != null && getId().equals(((Dog) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Dog{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", race='" + getRace() + "'" +
            ", age=" + getAge() +
            ", healthStatus='" + getHealthStatus() + "'" +
            "}";
    }
}
