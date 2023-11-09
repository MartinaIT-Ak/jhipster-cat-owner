package fr.it_akademy.jhipster.cat_owner.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Owner.
 */
@Entity
@Table(name = "owner")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Owner implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private Long phoneNumber;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "veterinary", "owner" }, allowSetters = true)
    private Set<Cat> cats = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "owner", "veterinary" }, allowSetters = true)
    private Set<Dog> dogs = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_owner__veterinary",
        joinColumns = @JoinColumn(name = "owner_id"),
        inverseJoinColumns = @JoinColumn(name = "veterinary_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "cats", "dogs", "owners" }, allowSetters = true)
    private Set<Veterinary> veterinaries = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Owner id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Owner firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Owner lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return this.address;
    }

    public Owner address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getPhoneNumber() {
        return this.phoneNumber;
    }

    public Owner phoneNumber(Long phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<Cat> getCats() {
        return this.cats;
    }

    public void setCats(Set<Cat> cats) {
        if (this.cats != null) {
            this.cats.forEach(i -> i.setOwner(null));
        }
        if (cats != null) {
            cats.forEach(i -> i.setOwner(this));
        }
        this.cats = cats;
    }

    public Owner cats(Set<Cat> cats) {
        this.setCats(cats);
        return this;
    }

    public Owner addCats(Cat cat) {
        this.cats.add(cat);
        cat.setOwner(this);
        return this;
    }

    public Owner removeCats(Cat cat) {
        this.cats.remove(cat);
        cat.setOwner(null);
        return this;
    }

    public Set<Dog> getDogs() {
        return this.dogs;
    }

    public void setDogs(Set<Dog> dogs) {
        if (this.dogs != null) {
            this.dogs.forEach(i -> i.setOwner(null));
        }
        if (dogs != null) {
            dogs.forEach(i -> i.setOwner(this));
        }
        this.dogs = dogs;
    }

    public Owner dogs(Set<Dog> dogs) {
        this.setDogs(dogs);
        return this;
    }

    public Owner addDog(Dog dog) {
        this.dogs.add(dog);
        dog.setOwner(this);
        return this;
    }

    public Owner removeDog(Dog dog) {
        this.dogs.remove(dog);
        dog.setOwner(null);
        return this;
    }

    public Set<Veterinary> getVeterinaries() {
        return this.veterinaries;
    }

    public void setVeterinaries(Set<Veterinary> veterinaries) {
        this.veterinaries = veterinaries;
    }

    public Owner veterinaries(Set<Veterinary> veterinaries) {
        this.setVeterinaries(veterinaries);
        return this;
    }

    public Owner addVeterinary(Veterinary veterinary) {
        this.veterinaries.add(veterinary);
        return this;
    }

    public Owner removeVeterinary(Veterinary veterinary) {
        this.veterinaries.remove(veterinary);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Owner)) {
            return false;
        }
        return getId() != null && getId().equals(((Owner) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Owner{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", address='" + getAddress() + "'" +
            ", phoneNumber=" + getPhoneNumber() +
            "}";
    }
}
